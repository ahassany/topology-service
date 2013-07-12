package net.es.topology.common.records.ts.utils;

import net.es.lookup.client.RegistrationClient;

/**
 * Simple implementation that returns only one instance
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class SLSRegistrationClientDispatcherImpl implements SLSRegistrationClientDispatcher {

    private RegistrationClient registrationClient;

    public SLSRegistrationClientDispatcherImpl(RegistrationClient registrationClient) {
        this.registrationClient = registrationClient;
    }

    /**
     * Returns a RegistrationClient instance based ont the URN of the network object.
     *
     * @param urn
     * @return
     */
    @Override
    public RegistrationClient getRegistrationClient(String urn) {
        return this.registrationClient;
    }
}
