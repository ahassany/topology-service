package net.es.topology.common.records.ts.utils;

import net.es.lookup.client.RegistrationClient;

/**
 * In case of using aggregate topology service, this interface is used
 * to determine the correct sLS to be contacted to save the record.
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public interface SLSRegistrationClientDispatcher {

    /**
     * Returns a RegistrationClient instance based ont the URN of the network object.
     * @param urn
     * @return
     */
    public RegistrationClient getRegistrationClient(String urn);
}
