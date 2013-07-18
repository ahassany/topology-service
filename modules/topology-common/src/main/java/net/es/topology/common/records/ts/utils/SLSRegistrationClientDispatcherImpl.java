package net.es.topology.common.records.ts.utils;

import net.es.lookup.client.RegistrationClient;
import net.es.lookup.client.SimpleLS;
import net.es.topology.common.config.sls.ClientProvider;

/**
 * Simple implementation that returns only one instance
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class SLSRegistrationClientDispatcherImpl implements SLSRegistrationClientDispatcher {

    private ClientProvider clientProvider;

    public SLSRegistrationClientDispatcherImpl(ClientProvider clientProvider) {
        this.clientProvider = clientProvider;
    }

    /**
     * Returns a RegistrationClient instance based ont the URN of the network object.
     *
     * @param urn
     * @return
     */
    @Override
    public RegistrationClient getRegistrationClient(String urn)  throws  Exception{
        SimpleLS client = clientProvider.getClient();
        RegistrationClient registrationClient = new RegistrationClient(client);
        return registrationClient;
    }
}
