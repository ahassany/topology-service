package net.es.topology.common.records.ts.utils;

import net.es.lookup.client.SimpleLS;

/**
 * In case of using aggregate topology service, this interface is used
 * to determine the correct sLS to be contacted to get the record.
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public interface SLSClientDispatcher {
    /**
     * returns an instance of the SimpleLS client based on the URN of the network object.
     * @param urn
     * @return
     */
    public SimpleLS getClient(String urn);
}
