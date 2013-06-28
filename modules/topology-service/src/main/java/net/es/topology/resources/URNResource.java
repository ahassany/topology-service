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
            //NetworkObject obj = (NetworkObject) msg.getBody();
            //System.out.println("Netobj: " + obj.getId() );

        } catch (JAXBException e) {
            throw new WebApplicationException(Response.serverError().entity("Cannot unmarshall the message: '" + e.getMessage() + "'.\n").build());
        }

        System.out.println("Net obj: " + inspect.getElementName(msg).toString());
        try {
            System.out.println("Net obj: " + inspect.getElementName(msg.getBody()));
        } catch (Exception e) {
            System.out.println("Inspection error: " + e.getMessage());
        }
        return new Broadcastable(message, "", urn);
    }

}
