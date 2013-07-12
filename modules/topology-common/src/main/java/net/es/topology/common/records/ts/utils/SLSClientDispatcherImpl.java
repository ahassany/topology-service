package net.es.topology.common.records.ts.utils;

import net.es.lookup.client.SimpleLS;

/**
 * This is a simple implementation for using one sLS backend
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class SLSClientDispatcherImpl implements SLSClientDispatcher {

    private SimpleLS client;

    public SLSClientDispatcherImpl(SimpleLS client) {
        this.client = client;
    }

    @Override
    public SimpleLS getClient(String urn) {
        return client;
    }
}
