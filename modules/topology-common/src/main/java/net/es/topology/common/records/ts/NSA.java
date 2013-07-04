package net.es.topology.common.records.ts;

import net.es.topology.common.records.ts.keys.ReservedKeys;
import net.es.topology.common.records.ts.keys.ReservedValues;

import java.util.List;

/**
 * An NSA object represents a Network Service Agent which can accept Connection Service requests and manages a network.
 *
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 * @see: Network Service Interface Topology Representation
 */
public class NSA extends NetworkObject {

    public NSA() {
        super(ReservedValues.RECORD_TYPE_NSA);
    }

    /**
     * To allow extending this class with custom record type
     *
     * @param recordType
     */
    public NSA(String recordType) {
        super(recordType);
    }

    /**
     * List of the URNs of topologies managed by the NSA
     *
     * @return list of topologies' URNs
     */
    public List<String> getTopologies() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_TS_TOPOLOGY);
    }

    /**
     * List of the URNs topologies managed by the NSA
     *
     * @param topologies list of topologies' URNs
     */
    public void setTopologies(List<String> topologies) {
        this.add(ReservedKeys.RECORD_TS_TOPOLOGY, topologies);
    }

    /**
     * The peersWith relation defines the control-plane connections between different NSAs.
     *
     * @return peer URN
     */
    public List<String> getPeersWith() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_PEERS_WITH);
    }

    /**
     * The peersWith relation defines the control-plane connections between different NSAs.
     *
     * @param peers URNs
     */
    public void setPeersWith(List<String> peers) {
        this.add(ReservedKeys.RECORD_RELATION_PEERS_WITH, peers);
    }

    public List<String> getManagedBy() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_MANAGED_BY);
    }

    public void setManagedBy(List<String> managers) {
        this.add(ReservedKeys.RECORD_RELATION_MANAGED_BY, managers);
    }


    /**
     * List of the URNs of services provided by the NSA
     *
     * @return list of services' URNs
     */
    public List<String> getNSIServices() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_NSI_SERVICE);
    }

    /**
     * List of the URNs of services provided by the NSA
     *
     * @param services list of services' URNs
     */
    public void setNSIServices(List<String> services) {
        this.add(ReservedKeys.RECORD_NSI_SERVICE, services);
    }
}
