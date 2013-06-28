package net.es.topology.resources;

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
import javax.xml.bind.Unmarshaller;
import java.io.InputStreamReader;


public class URNSResource {
    public final static String jaxb_bindings = "org.ogf.schemas.nsi._2013._09.topology:org.ogf.schemas.nsi._2013._09.messaging:org.ogf.schemas.nml._2013._05.base";
    Broadcaster topic;

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
                .broadcaster(topic).resumeOnBroadcast(resumeOnBroadcast)
                .outputComments(true)
                .build();

    }

    @POST
    @Broadcast
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Broadcastable publish(String message) {
        JAXBContext jc = null;
        Unmarshaller um = null;
        Message msg = null;

        // The try and catch are separate to better error reporting to the user
        try {
            jc = JAXBContext.newInstance(jaxb_bindings);
        } catch (JAXBException e) {
            throw new WebApplicationException(Response.serverError().entity("Cannot create JAXB Context: '" + e.getMessage() + "'.\n").build());
        }

        try {
            um = jc.createUnmarshaller();
        } catch (JAXBException e) {
            throw new WebApplicationException(Response.serverError().entity("Cannot create JAXB Unmarshaller: '" + e.getMessage() + "'.\n").build());
        }

        try {
            msg = (Message) um.unmarshal(new InputStreamReader(IOUtils.toInputStream(message)));
        } catch (JAXBException e) {
            throw new WebApplicationException(Response.serverError().entity("Cannot unmarshall the message: '" + e.getMessage() + "'.\n").build());
        }

        return new Broadcastable(message, "", topic);
    }

    /**
     * Delegating the individual URN handling to the URNResource class
     *
     * @return URNResource instance
     */
    @Path("/{urn}")
    public URNResource getURN() {
        return new URNResource();
    }
}
