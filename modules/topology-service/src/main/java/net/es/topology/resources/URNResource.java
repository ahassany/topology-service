package net.es.topology.resources;

import net.es.lookup.common.exception.LSClientException;
import net.es.lookup.common.exception.ParserException;
import net.es.topology.client.sls.SLSTSClient;
import net.es.topology.common.config.sls.JsonClientProvider;
import net.es.topology.common.converter.nml.NMLVisitor;
import net.es.topology.common.records.ts.utils.*;
import net.es.topology.common.visitors.nml.DepthFirstTraverserImpl;
import net.es.topology.common.visitors.nml.TraversingVisitor;
import net.es.topology.utils.MessageUtils;
import net.es.topology.utils.SLSCallbackListener;
import org.apache.commons.io.IOUtils;
import org.atmosphere.annotation.Broadcast;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.jersey.Broadcastable;
import org.atmosphere.jersey.SuspendResponse;
import org.ogf.schemas.nml._2013._05.base.NetworkObject;
import org.ogf.schemas.nsi._2013._09.messaging.Message;
import org.ogf.schemas.nsi._2013._09.messaging.ObjectFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Unmarshaller;
import java.io.InputStreamReader;

/**
 * URNResource to handle requests to individual URNs at "/urns/{urn}"
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */


public class URNResource {
    private
    @PathParam("urn")
    Broadcaster urn;

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public SuspendResponse<Message> subscribe(@PathParam("urn") String urnPath, @Context HttpHeaders headers) {

        // Check if the client want to keep the connection open for HTTP
        // streaming
        boolean resumeOnBroadcast = false;

        if (headers.getRequestHeader("Connection") != null
                && headers.getRequestHeader("Connection")
                .contains("keep-alive")) {
            resumeOnBroadcast = false;
        } else {
            resumeOnBroadcast = true;
        }

        RecordsCache cache;
        try {
            cache = new RecordsCache(new SLSClientDispatcherImpl(JsonClientProvider.getInstance()), new URNMaskGetAllImpl(), "");
        } catch (Exception e) {
            throw new WebApplicationException(Response.serverError().entity("Unable to init sLS client: '" + e.getMessage() + "'.\n").build());
        }

        SLSTSClient tsClient = new SLSTSClient(cache, "");
        NetworkObject networkObject;
        try {
            networkObject = tsClient.getNetworkObject(urnPath);
        } catch (LSClientException e) {
            throw new WebApplicationException(Response.serverError().entity("LSClient exception: '" + e.getMessage() + "'.\n").build());
        } catch (ParserException e) {
            throw new WebApplicationException(Response.serverError().entity("Unable to parse sLS output: '" + e.getMessage() + "'.\n").build());
        }
        Message msg;

        if (networkObject == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity("NOT FOUND URN='" + urnPath + "'.\n").build());
            //atmosphereResourceEvent.broadcaster().broadcast("");
        } else {
            ObjectFactory objectFactory = new ObjectFactory();
            msg = objectFactory.createMessage();
            msg.setBody(objectFactory.createMessageBody());
            try {
                msg = MessageUtils.makeMessage(networkObject);
            } catch (Exception e) {
                throw new WebApplicationException(Response.serverError().entity("Unable pack response message: '" + e.getMessage() + "'.\n").build());
            }
        }

        return new SuspendResponse.SuspendResponseBuilder<Message>()
                .broadcaster(urn)
                .resumeOnBroadcast(resumeOnBroadcast)
                .outputComments(true)
                .addListener(new SLSCallbackListener(msg))
                .build();
    }

    @PUT
    @Broadcast
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Broadcastable publish(String message) {
        JAXBContext jc = null;
        Unmarshaller um = null;
        Message msg = null;
        JAXBIntrospector inspect = null;

        // The try and catch are separate to better error reporting to the user
        try {
            jc = JAXBContext.newInstance(URNSResource.jaxb_bindings);
        } catch (JAXBException e) {
            throw new WebApplicationException(Response.serverError().entity("Cannot create JAXB Context: '" + e.getMessage() + "'.\n").build());
        }

        try {
            um = jc.createUnmarshaller();
        } catch (JAXBException e) {
            throw new WebApplicationException(Response.serverError().entity("Cannot create JAXB Unmarshaller: '" + e.getMessage() + "'.\n").build());
        }

        inspect = jc.createJAXBIntrospector();


        try {
            msg = (Message) um.unmarshal(new InputStreamReader(IOUtils.toInputStream(message)));
        } catch (JAXBException e) {
            throw new WebApplicationException(Response.serverError().entity("Cannot unmarshall the message: '" + e.getMessage() + "'.\n").build());
        }

        RecordsCollection collection = new RecordsCollection();
        NMLVisitor visitor = new NMLVisitor(collection);
        TraversingVisitor tv = new TraversingVisitor(new DepthFirstTraverserImpl(), visitor);

        msg.getBody().accept(tv);
        try {
            JsonClientProvider sLSConfig = JsonClientProvider.getInstance();
            visitor.getRecordsCollection().sendTosLS(new SLSRegistrationClientDispatcherImpl(sLSConfig),
                    new SLSClientDispatcherImpl(sLSConfig), new URNMaskGetAllImpl());
        } catch (LSClientException e) {
            throw new WebApplicationException(Response.serverError().entity("LSClientException : '" + e.getMessage() + "'.\n").build());
        } catch (ParserException e) {
            throw new WebApplicationException(Response.serverError().entity("Parser exception: '" + e.getMessage() + "'.\n").build());
        } catch (Exception e) {
            throw new WebApplicationException(Response.serverError().entity("Unknown exception: '" + e.getMessage() + "'.\n").build());
        }
        return new Broadcastable(message, "", urn);
    }

}
