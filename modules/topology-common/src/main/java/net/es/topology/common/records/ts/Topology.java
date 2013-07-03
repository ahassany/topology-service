package net.es.topology.common.records.ts;

import net.es.topology.common.records.ts.keys.ReservedKeys;
import net.es.topology.common.records.ts.keys.ReservedValues;

import java.util.List;

/**
 * A Topology is a set of connected Network Objects.
 * <p/>
 * TODO (AH): deal with xs:group nml:Service
 * TODO (AH): deal with xs:group nml:Group
 * TODO (AH): deal with relations to portgroup
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 * @see NML schema docs for the description of the fields
 */
public class Topology extends NetworkObject {

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
}
