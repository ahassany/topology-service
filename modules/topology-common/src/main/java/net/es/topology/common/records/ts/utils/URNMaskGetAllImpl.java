package net.es.topology.common.records.ts.utils;

/**
 * This is a simple implementation that allows the topology service
 * to save objects from all domains.
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class URNMaskGetAllImpl implements URNMask {

    /**
     * Returns true if the topology instance has authority over the URN and it does need to be saved.
     *
     * @param urn
     * @return The URN of the network object
     */
    @Override
    public boolean sendToSLS(String urn) {
        return true;
    }

    /**
     * Returns true if the topology instance has authority over the URN and it does need to be retrieved.
     *
     * @param urn
     * @return The URN of the network object
     */
    @Override
    public boolean getFromSLS(String urn) {
        return true;
    }
}
