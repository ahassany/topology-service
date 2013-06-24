package net.es.topology.resources;

import javax.ws.rs.Path;

/**
 * RootResource Resource to handle requests to "/"
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */

@Path("/")
public class RootResource {
    /**
     * Delegating the URNs handling to the URNSResource class
     *
     * @return URNSResource instance
     */
    @Path("/urns")
    public URNSResource getURNS() {
        return new URNSResource();
    }

}
