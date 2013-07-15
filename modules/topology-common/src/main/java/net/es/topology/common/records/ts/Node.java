package net.es.topology.common.records.ts;

import net.es.topology.common.records.ts.keys.ReservedKeys;
import net.es.topology.common.records.ts.keys.ReservedValues;
import net.es.topology.common.visitors.sls.Visitable;
import net.es.topology.common.visitors.sls.Visitor;

import java.util.List;

/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class Node extends NetworkObject implements Visitable {

    public Node() {
        super(ReservedValues.RECORD_TYPE_NODE);
    }

    /**
     * To allow extending this class with custom record type
     *
     * @param recordType
     */
    public Node(String recordType) {
        super(recordType);
    }

    /**
     * List of the URNs of nodes in the node
     *
     * @return list of nodes' URNs
     */
    public List<String> getNodes() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_TS_NODE);
    }

    /**
     * List of the URNs nodes in the node
     *
     * @param nodes list of nodes' URNs
     */
    public void setNodes(List<String> nodes) {
        this.add(ReservedKeys.RECORD_TS_NODE, nodes);
    }

    /**
     * This defines that the related Network Object has an inbound Port or PortGroup object.
     *
     * @return list of the URNs of inbound ports
     */
    public List<String> getHasInboundPort() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_HAS_INBOUND_PORT);
    }

    /**
     * This defines that the related Network Object has an inbound Port or PortGroup object.
     *
     * @param ports list of the URNs of inbound ports
     */
    public void setHasInboundPort(List<String> ports) {
        this.add(ReservedKeys.RECORD_RELATION_HAS_INBOUND_PORT, ports);
    }

    /**
     * This defines that the related Network Object has an outbound Port or PortGroup object.
     *
     * @return list of the URNs of outbound ports
     */
    public List<String> getHasOutboundPort() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_HAS_OUTBOUND_PORT);
    }

    /**
     * This defines that the related Network Object has an outbound Port or PortGroup object.
     *
     * @param ports list of the URNs of outbound ports
     */
    public void setHasOutboundPort(List<String> ports) {
        this.add(ReservedKeys.RECORD_RELATION_HAS_OUTBOUND_PORT, ports);
    }

    /**
     * This defines that the related Node has an inbound PortGroup object.
     *
     * @return list of the URNs of inbound ports
     */
    public List<String> getHasInboundPortGroup() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_HAS_INBOUND_PORT_GROUP);
    }

    /**
     * This defines that the related Node has an inbound PortGroup object.
     *
     * @param ports list of the URNs of inbound ports
     */
    public void setHasInboundPortGroup(List<String> ports) {
        this.add(ReservedKeys.RECORD_RELATION_HAS_INBOUND_PORT_GROUP, ports);
    }

    /**
     * This defines that the related Node has an outbound Port or PortGroup object.
     *
     * @return list of the URNs of outbound ports
     */
    public List<String> getHasOutboundPortGroup() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_HAS_OUTBOUND_PORT_GROUP);
    }

    /**
     * This defines that the related Node has an outbound PortGroup object.
     *
     * @param ports list of the URNs of outbound ports
     */
    public void setHasOutboundPortGroup(List<String> ports) {
        this.add(ReservedKeys.RECORD_RELATION_HAS_OUTBOUND_PORT_GROUP, ports);
    }

    /**
     * Node related to SwitchingService, describing a switching ability of that Node.
     *
     * @return List of SwitchingServices URNs
     */
    public List<String> getHasService() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_HAS_SERVICE);
    }

    /**
     * Node related to SwitchingService, describing a switching ability of that Node
     *
     * @param services List of SwitchingServices URNs
     */
    public void setHasService(List<String> services) {
        this.add(ReservedKeys.RECORD_RELATION_HAS_SERVICE, services);
    }

    /**
     * isAlias is a relation to another Node describe that one can be used as the alias of another.
     *
     * @return list of the URNs of the other Nodes.
     */
    public List<String> getIsAlias() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_IS_ALIAS);
    }

    /**
     * isAlias is a relation to another Node describe that one can be used as the alias of another.
     *
     * @param aliases list of the URNs of the other Nodes.
     */
    public void setIsAlias(List<String> aliases) {
        this.add(ReservedKeys.RECORD_RELATION_IS_ALIAS, aliases);
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
