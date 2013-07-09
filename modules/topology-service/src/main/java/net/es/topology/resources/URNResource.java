package net.es.topology.resources;

import net.es.lookup.client.RegistrationClient;
import net.es.lookup.client.SimpleLS;
import net.es.lookup.common.exception.LSClientException;
import net.es.lookup.common.exception.ParserException;
import net.es.topology.common.converter.nml.NMLVisitor;
import net.es.topology.common.records.ts.RecordsCollection;
import net.es.topology.common.visitors.nml.DepthFirstTraverserImpl;
import net.es.topology.common.visitors.nml.TraversingVisitor;
import org.apache.commons.io.IOUtils;
import org.atmosphere.annotation.Broadcast;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.jersey.Broadcastable;
import org.atmosphere.jersey.SuspendResponse;
import org.ogf.schemas.nsi._2013._09.messaging.Message;

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
    public SuspendResponse<Message> subscribe(@Context HttpHeaders headers) {

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
        return new SuspendResponse.SuspendResponseBuilder<Message>()
                .broadcaster(urn)
                .resumeOnBroadcast(resumeOnBroadcast)
                .outputComments(true)
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
        SimpleLS client;
        try {
            // TODO (AH): define a better way to specify the sLS address
            client = new SimpleLS("localhost", 8090);
            client.connect();
            RegistrationClient rclient = new RegistrationClient(client);
            visitor.getRecordsCollection().sendTosLS(rclient);
        } catch (LSClientException e) {
            throw new WebApplicationException(Response.serverError().entity("LSClientException : '" + e.getMessage() + "'.\n").build());
        } catch (ParserException e) {
            throw new WebApplicationException(Response.serverError().entity("Parser exception: '" + e.getMessage() + "'.\n").build());
        }
        return new Broadcastable(message, "", urn);
    }

}
