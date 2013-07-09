package net.es.topology.common.records.ts;

import net.es.topology.common.records.ts.keys.ReservedKeys;
import net.es.topology.common.records.ts.keys.ReservedValues;
import net.es.topology.common.visitors.sls.Visitable;
import net.es.topology.common.visitors.sls.Visitor;

import java.util.List;

/**
 * A BidirectionalPort is a group of two (unidirectional) Ports or PortGroups together
 * forming a bidirectional representation of a physical or virtual port.
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 * @see NML schema docs for the description of the fields
 */
public class BidirectionalPort extends NetworkObject implements Visitable {

    public BidirectionalPort() {
        super(ReservedValues.RECORD_TYPE_BIDIRECTIONAL_PORT);
    }

    /**
     * To allow extending this class with custom record type
     *
     * @param recordType
     */
    public BidirectionalPort(String recordType) {
        super(recordType);
    }

    /**
     * NML specifies that a Bidirectional Port has exactly two Ports or two PortGroups
     * this is not enforced in the implementation
     *
     * @return list of the URNs of the other Ports.
     */
    public List<String> getPorts() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_TS_PORT);
    }

    /**
     * NML specifies that a Bidirectional Port has exactly two Ports or two PortGroups
     * this is not enforced in the implementation
     *
     * @return list of the URNs of the other Ports.
     */
    public void setPorts(List<String> ports) {
        this.add(ReservedKeys.RECORD_TS_PORT, ports);
    }

    /**
     * NML specifies that a Bidirectional Port has exactly two Ports or two PortGroups
     * this is not enforced in the implementation
     *
     * @return list of the URNs of the other Ports.
     */
    public List<String> getPortGroups() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_TS_PORT_GROUP);
    }

    /**
     * NML specifies that a Bidirectional Port has exactly two Ports or two PortGroups
     * this is not enforced in the implementation
     *
     * @return list of the URNs of the other Ports.
     */
    public void setPortGroups(List<String> portGroups) {
        this.add(ReservedKeys.RECORD_TS_PORT_GROUP, portGroups);
    }

    /**
     * calls the visit method at the visitor
     *
     * @param aVisitor
     */
    public void accept(Visitor aVisitor) {
        aVisitor.visit(this);
    }
}
