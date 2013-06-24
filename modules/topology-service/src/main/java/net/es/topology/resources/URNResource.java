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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;


public class URNResource {
    private
    @PathParam("urn")
    Broadcaster urn;

    private final String _bindings = "org.ogf.schemas.nsi._2013._09.topology:org.ogf.schemas.nsi._2013._09.messaging:org.ogf.schemas.nml._2013._05.base";


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
    public Broadcastable publish(Message message) {
        try {
            JAXBContext jc = JAXBContext.newInstance(_bindings);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            marshaller.marshal(message, sw);
            return new Broadcastable(sw.toString(), "", urn);

        } catch (JAXBException e) {
            System.out.println("*** Marshalling error ******");
        }
        return null;

    }

}
