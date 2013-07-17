package net.es.topology.common.records.ts;

import net.es.topology.common.records.ts.keys.ReservedKeys;
import net.es.topology.common.records.ts.keys.ReservedValues;
import net.es.topology.common.visitors.sls.Visitable;
import net.es.topology.common.visitors.sls.Visitor;

import java.util.List;

/**
 * A Topology is a set of connected Network Objects.
 * <p/>
 * TODO (AH): deal with nml:Service
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 * @see NML schema docs for the description of the fields
 */
public class Topology extends NetworkObject implements Visitable {

    public Topology() {
        super(ReservedValues.RECORD_TYPE_TOPOLOGY);
    }

    /**
     * To allow extending this class with custom record type
     *
     * @param recordType
     */
    public Topology(String recordType) {
        super(recordType);
    }

    /**
     * List of the URNs of nodes in the topology
     *
     * @return list of nodes' URNs
     */
    public List<String> getNodes() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_TS_NODE);
    }

    /**
     * List of the URNs nodes in the topology
     *
     * @param nodes list of nodes' URNs
     */
    public void setNodes(List<String> nodes) {
        this.add(ReservedKeys.RECORD_TS_NODE, nodes);
    }

    /**
     * List of the URNs of ports in the topology
     *
     * @return list of ports' URNs
     */
    public List<String> getPorts() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_TS_PORT);
    }

    /**
     * List of the URNs ports in the topology
     *
     * @param ports list of ports' URNs
     */
    public void setPorts(List<String> ports) {
        this.add(ReservedKeys.RECORD_TS_PORT, ports);
    }

    /**
     * List of the URNs of port groups in the topology
     *
     * @return list of port groups' URNs
     */
    public List<String> getPortGroups() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_TS_PORT_GROUP);
    }

    /**
     * List of the URNs port groups in the topology
     *
     * @param portGroups list of port groups' URNs
     */
    public void setPortGroups(List<String> portGroups) {
        this.add(ReservedKeys.RECORD_TS_PORT_GROUP, portGroups);
    }

    /**
     * List of the URNs of bidirectional ports in the topology
     *
     * @return list of bidirectional ports' URNs
     */
    public List<String> getBidirectionalPorts() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_TS_BIDIRECTIONAL_PORT);
    }

    /**
     * List of the URNs bidirectional ports in the topology
     *
     * @param biPorts list of bidirectional ports' URNs
     */
    public void setBidirectionalPorts(List<String> biPorts) {
        this.add(ReservedKeys.RECORD_TS_BIDIRECTIONAL_PORT, biPorts);
    }

    /**
     * List of the URNs of links in the topology
     *
     * @return list of links' URNs
     */
    public List<String> getLinks() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_TS_LINK);
    }

    /**
     * List of the URNs links in the topology
     *
     * @param links list of links' URNs
     */
    public void setLinks(List<String> links) {
        this.add(ReservedKeys.RECORD_TS_LINK, links);
    }

    /**
     * List of the URNs of link groups in the topology
     *
     * @return list of link groups' URNs
     */
    public List<String> getLinkGroups() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_TS_LINK_GROUP);
    }

    /**
     * List of the URNs link groups in the topology
     *
     * @param linkGroups list of link groups' URNs
     */
    public void setLinkGroups(List<String> linkGroups) {
        this.add(ReservedKeys.RECORD_TS_LINK_GROUP, linkGroups);
    }

    /**
     * List of the URNs SwitchingServices in the topology
     *
     * @return llist of SwitchingServices' URNs
     */
    public List<String> getSwitchingServices() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_TS_SWITCHING_SERVICE);
    }

    /**
     * List of the URNs SwitchingServices in the topology
     *
     * @param links list of SwitchingServices' URNs
     */
    public void setSwitchingServices(List<String> links) {
        this.add(ReservedKeys.RECORD_TS_SWITCHING_SERVICE, links);
    }

    /**
     * List of the URNs AdaptationServices in the topology
     *
     * @return llist of AdaptationServices' URNs
     */
    public List<String> getAdaptationServices() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_TS_ADAPTATION_SERVICE);
    }

    /**
     * List of the URNs AdaptationServices in the topology
     *
     * @param links list of AdaptationServices' URNs
     */
    public void setAdaptationServices(List<String> links) {
        this.add(ReservedKeys.RECORD_TS_ADAPTATION_SERVICE, links);
    }

    /**
     * List of the URNs DeadaptationServices in the topology
     *
     * @return llist of DeadaptationServices' URNs
     */
    public List<String> getDeadaptationServices() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_TS_DEADAPTATION_SERVICE);
    }

    /**
     * List of the URNs DeadaptationServices in the topology
     *
     * @param links list of DeadaptationServices' URNs
     */
    public void setDeadaptationServices(List<String> links) {
        this.add(ReservedKeys.RECORD_TS_DEADAPTATION_SERVICE, links);
    }

    /**
     * List of the URNs of topologies in the topology
     *
     * @return list of topologies' URNs
     */
    public List<String> getTopologies() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_TS_TOPOLOGY);
    }

    /**
     * List of the URNs topologies in the topology
     *
     * @param topologies list of Topologies' URNs
     */
    public void setTopologies(List<String> topologies) {
        this.add(ReservedKeys.RECORD_TS_TOPOLOGY, topologies);
    }

    /**
     * List of the URNs of bidirectional links in the topology
     *
     * @return list of bidirectional links' URNs
     */
    public List<String> getBidirectionalLinks() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_TS_BIDIRECTIONAL_LINK);
    }

    /**
     * List of the URNs bidirectional links in the topology
     *
     * @param bidirectionalLinks list of links' URNs
     */
    public void setBidirectionalLinks(List<String> bidirectionalLinks) {
        this.add(ReservedKeys.RECORD_TS_BIDIRECTIONAL_LINK, bidirectionalLinks);
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
     * This defines that the related Network Object has an inbound Port object.
     *
     * @param ports list of the URNs of inbound ports
     */
    public void setHasInboundPort(List<String> ports) {
        this.add(ReservedKeys.RECORD_RELATION_HAS_INBOUND_PORT, ports);
    }

    /**
     * This defines that the related Topology has an outbound Port object.
     *
     * @return list of the URNs of outbound ports
     */
    public List<String> getHasOutboundPort() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_HAS_OUTBOUND_PORT);
    }

    /**
     * This defines that the related Topology has an outbound Port object.
     *
     * @param ports list of the URNs of outbound ports
     */
    public void setHasOutboundPort(List<String> ports) {
        this.add(ReservedKeys.RECORD_RELATION_HAS_OUTBOUND_PORT, ports);
    }

    /**
     * This defines that the related Network Object has an inbound PortGroup object.
     *
     * @return list of the URNs of inbound ports
     */
    public List<String> getHasInboundPortGroup() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_HAS_INBOUND_PORT_GROUP);
    }

    /**
     * This defines that the related Network Object has an inbound PortGroup object.
     *
     * @param ports list of the URNs of inbound ports
     */
    public void setHasInboundPortGroup(List<String> ports) {
        this.add(ReservedKeys.RECORD_RELATION_HAS_INBOUND_PORT_GROUP, ports);
    }

    /**
     * This defines that the related Topology has an outbound Port or PortGroup object.
     *
     * @return list of the URNs of outbound ports
     */
    public List<String> getHasOutboundPortGroup() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_HAS_OUTBOUND_PORT_GROUP);
    }

    /**
     * This defines that the related Topology has an outbound PortGroup object.
     *
     * @param ports list of the URNs of outbound ports
     */
    public void setHasOutboundPortGroup(List<String> ports) {
        this.add(ReservedKeys.RECORD_RELATION_HAS_OUTBOUND_PORT_GROUP, ports);
    }

    /**
     * Topology related to SwitchingService, describing a switching ability of that Topology.
     *
     * @return List of SwitchingServices URNs
     */
    public List<String> getHasSwitchingService() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_HAS_SWITCHING_SERVICE);
    }

    /**
     * Node related to SwitchingService, describing a switching ability of that Topology
     *
     * @param services List of SwitchingServices URNs
     */
    public void setHasSwitchingService(List<String> services) {
        this.add(ReservedKeys.RECORD_RELATION_HAS_SWITCHING_SERVICE, services);
    }

    /**
     * Topology related to AdaptationService, describing a switching ability of that Topology.
     *
     * @return List of AdaptationServices URNs
     */
    public List<String> getHasAdaptationService() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_HAS_ADAPTATION_SERVICE);
    }

    /**
     * Node related to AdaptationServices, describing a switching ability of that Topology
     *
     * @param services List of AdaptationServices URNs
     */
    public void setHasAdaptationService(List<String> services) {
        this.add(ReservedKeys.RECORD_RELATION_HAS_ADAPTATION_SERVICE, services);
    }

    /**
     * Topology related to DeadaptationService, describing a switching ability of that Topology.
     *
     * @return List of DeadaptationService URNs
     */
    public List<String> getHasDeadaptationService() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_HAS_DEADAPTATION_SERVICE);
    }

    /**
     * Node related to DeadaptationService, describing a switching ability of that Topology
     *
     * @param services List of DeadaptationService URNs
     */
    public void setHasDeadaptationService(List<String> services) {
        this.add(ReservedKeys.RECORD_RELATION_HAS_DEADAPTATION_SERVICE, services);
    }

    /**
     * isAlias is a relation to another Topology describes that one can be used as the alias of another.
     *
     * @return list of the URNs of the other Nodes.
     */
    public List<String> getIsAlias() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_IS_ALIAS);
    }

    /**
     * isAlias is a relation to another Topology describes that one can be used as the alias of another.
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
