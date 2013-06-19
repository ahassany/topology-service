package net.es.topology.resources;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.atmosphere.annotation.Broadcast;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.jersey.Broadcastable;
import org.atmosphere.jersey.SuspendResponse;
import org.atmosphere.websocket.WebSocketEventListener;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

import org.atmosphere.annotation.Asynchronous;
import java.util.concurrent.Callable;

@Path("/pubsub/{topic}")
public class URNSResource {
	private @PathParam("topic")
		Broadcaster topic;

	/*
	 * Handles client HTTP GET Requests
	 * 
	 * Testing:
	 * 
	 * If the client want the connection to be immediately closed after the 
	 * request has been served try:
	 *  curl -v -XGET -N http://localhost:8080/pubsub/topic1
	 *  
	 *  IF the client want to subscribe try
	 *  curl -v -XGET -N -H"Connection: keep-alive" http://localhost:8080/pubsub/topic1
	 *  
	 *  
	 *  Note:
	 *  'curl -N' means curl will not buffer the data and output results to terminal
	 *  immediately after it receives it from the server.
	 */
	@GET
		@Produces(MediaType.APPLICATION_JSON)
		public SuspendResponse<String> subscribe(@Context HttpHeaders headers) {

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

		return new SuspendResponse.SuspendResponseBuilder<String>()
			.broadcaster(topic).resumeOnBroadcast(resumeOnBroadcast)
			.outputComments(true)
			//.addListener(new EventsLogger())
			//.addListener(new HTTPCallBackListener()) // This listener will
			// call external
			// HTTP page before
			// responding to the
			// client
			.build();

	}

	/*
	 * Handles POST request from the client, and broadcast the results to all
	 * listeners on the same topic
	 * 
	 * Try: 
	 * curl -XPOST -H"Content-Type: application/json" -d'{"message": "Sent from curl"}' http://localhost:8080/pubsub/topic1
	 */
	@POST
		@Broadcast
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Broadcastable publish(String message) {
		return new Broadcastable(message, "", topic);
	}
}
