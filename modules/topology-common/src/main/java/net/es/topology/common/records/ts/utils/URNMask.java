package net.es.topology.common.records.ts.utils;

/**
 * This interface to control which URNs to be saved/or retrieved as records from
 * the sLS
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public interface URNMask {
    /**
     * Returns true if the topology instance has authority over the URN and it does need to be saved.
     * @param urn The URN of the network object
     * @return
     */
    public boolean sendToSLS(String urn);

    /**
     * Returns true if the topology instance has authority over the URN and it does need to be retrieved.
     *
     * @param urn
     * @return The URN of the network object
     */
    public boolean getFromSLS(String urn);
}
