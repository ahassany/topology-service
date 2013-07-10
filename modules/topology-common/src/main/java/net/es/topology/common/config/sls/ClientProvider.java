package net.es.topology.common.config.sls;

import net.es.lookup.client.SimpleLS;

/**
 * A interface for providing an instance of sLS client
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public interface ClientProvider {
    /**
     * Creates a new instance of SimpleLS client.
     * @return
     * @throws Exception
     */
    public SimpleLS getClient() throws Exception;

    /**
     * The port number of the sLS service
     * @return
     */
    public int getPort();

    /**
     * The host name (or IP address) of the sLS service
     * @return
     */
    public String getHost();
}
