package net.es.topology.resources;

import org.atmosphere.annotation.Broadcast;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.jersey.Broadcastable;
import org.atmosphere.jersey.SuspendResponse;
import org.ogf.schemas.nsi._2013._09.messaging.Message;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;


public class URNSResource {


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
    public Broadcastable publish(Message message) {
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
