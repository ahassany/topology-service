package net.es.topology.common.records.ts.utils;

import net.es.lookup.client.SimpleLS;
import net.es.topology.common.config.sls.ClientProvider;
import net.es.topology.common.config.sls.JsonClientProvider;

/**
 * This is a simple implementation for using one sLS backend
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class SLSClientDispatcherImpl implements SLSClientDispatcher {

    private ClientProvider clientProvider;

    public SLSClientDispatcherImpl(ClientProvider clientProvider) {
        this.clientProvider = clientProvider;
    }

    @Override
    public SimpleLS getClient(String urn) throws Exception{
        return clientProvider.getClient();
    }
}
