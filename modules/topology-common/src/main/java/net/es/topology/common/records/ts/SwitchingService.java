package net.es.topology.common.records.ts;

import net.es.topology.common.records.ts.keys.ReservedKeys;
import net.es.topology.common.records.ts.keys.ReservedValues;
import net.es.topology.common.visitors.sls.Visitable;
import net.es.topology.common.visitors.sls.Visitor;

import java.util.List;

/**
 * A SwitchingService describes the ability to create new Links from any of its inbound Ports to any of its outbound Ports
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class SwitchingService extends NetworkObject implements Visitable{
    public SwitchingService() {
        super(ReservedValues.RECORD_TYPE_SWITCHING_SERVICE);
    }

    public SwitchingService(String recordType) {
        super(recordType);
    }

    /**
     * The encoding attribute defines the format of the data streaming through the SwitchingService.
     * The identifier for the encoding must be a URI
     *
     * @return encoding URI specified by a GFD
     */
    public String getEncoding() {
        return arrayToString(this.getValue(ReservedKeys.RECORD_PORT_ENCODING));
    }

    /**
     * The encoding attribute defines the format of the data streaming through the SwitchingService.
     * The identifier for the encoding must be a URI
     *
     * @param encoding URI specified by a GFD
     */
    public void setEncoding(String encoding) {
        this.add(ReservedKeys.RECORD_PORT_ENCODING, encoding);
    }

    /**
     * A value of false adds a restriction to the SwitchingService: it is only able to create cross connects from an
     * inbound Port to an outbound Port if the Label of the connected Ports have the same value.
     * The default value is false.
     */
    public Boolean getLabelSwaping() {
        Object value = this.getValue(ReservedKeys.RECORD_SERVICE_LABEL_SWAPPING);
        if (value != null)
            return Boolean.valueOf(this.arrayToString(value));
        else
            return null;
    }

    /**
     * A value of false adds a restriction to the SwitchingService: it is only able to create cross connects from an
     * inbound Port to an outbound Port if the Label of the connected Ports have the same value.
     * The default value is false.
     *
     * @param labelSwaping
     */
    public void setLabelSwaping(Boolean labelSwaping) {
        this.add(ReservedKeys.RECORD_SERVICE_LABEL_SWAPPING, labelSwaping.toString());
    }

    /**
     * This defines that the related SwitchingService has an inbound Port object.
     *
     * @return list of the URNs of inbound ports
     */
    public List<String> getHasInboundPort() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_HAS_INBOUND_PORT);
    }

    /**
     * This defines that the related SwitchingService Object has an inbound Port object.
     *
     * @param ports list of the URNs of inbound ports
     */
    public void setHasInboundPort(List<String> ports) {
        this.add(ReservedKeys.RECORD_RELATION_HAS_INBOUND_PORT, ports);
    }

    /**
     * This defines that the related SwitchingService has an outbound Port object.
     *
     * @return list of the URNs of outbound ports
     */
    public List<String> getHasOutboundPort() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_HAS_OUTBOUND_PORT);
    }

    /**
     * This defines that the related SwitchingService has an outbound Port object.
     *
     * @param ports list of the URNs of outbound ports
     */
    public void setHasOutboundPort(List<String> ports) {
        this.add(ReservedKeys.RECORD_RELATION_HAS_OUTBOUND_PORT, ports);
    }

    /**
     * This defines that the related SwitchingService has an inbound PortGroup object.
     *
     * @return list of the URNs of inbound ports
     */
    public List<String> getHasInboundPortGroup() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_HAS_INBOUND_PORT_GROUP);
    }

    /**
     * This defines that the related SwitchingService has an inbound PortGroup object.
     *
     * @param ports list of the URNs of inbound ports
     */
    public void setHasInboundPortGroup(List<String> ports) {
        this.add(ReservedKeys.RECORD_RELATION_HAS_INBOUND_PORT_GROUP, ports);
    }

    /**
     * This defines that the related SwitchingService has an outbound PortGroup object.
     *
     * @return list of the URNs of outbound ports
     */
    public List<String> getHasOutboundPortGroup() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_HAS_OUTBOUND_PORT_GROUP);
    }

    /**
     * This defines that the related SwitchingService has an outbound PortGroup object.
     *
     * @param ports list of the URNs of outbound ports
     */
    public void setHasOutboundPortGroup(List<String> ports) {
        this.add(ReservedKeys.RECORD_RELATION_HAS_OUTBOUND_PORT_GROUP, ports);
    }

    /**
     * isAlias is a relation to another SwitchingService describes that one can be used as the alias of another.
     *
     * @return list of the URNs of the other Nodes.
     */
    public List<String> getIsAlias() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_IS_ALIAS);
    }

    /**
     * isAlias is a relation to another SwitchingService describes that one can be used as the alias of another.
     *
     * @param aliases list of the URNs of the other Nodes.
     */
    public void setIsAlias(List<String> aliases) {
        this.add(ReservedKeys.RECORD_RELATION_IS_ALIAS, aliases);
    }

    /**
     * The providesLink relation points to Links which describe the currently configured cross connects in a SwitchingService.
     *
     * @return list of the URNs of the other links.
     */
    public List<String> getProvidesLink() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_PROVIDES_LINK);
    }

    /**
     * The providesLink relation points to Links which describe the currently configured cross connects in a SwitchingService.
     *
     * @param links
     */
    public void setProvidesLink(List<String> links) {
        this.add(ReservedKeys.RECORD_RELATION_PROVIDES_LINK, links);
    }

    /**
     * The providesLink relation points to Link Groups which describe the currently configured cross connects in a SwitchingService.
     *
     * @return list of the URNs of the other links.
     */
    public List<String> getProvidesLinkGroup() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_PROVIDES_LINK_GROUP);
    }

    /**
     * The providesLink relation points to Link Groups which describe the currently configured cross connects in a SwitchingService.
     *
     * @param links
     */
    public void setProvidesLinkGroup(List<String> links) {
        this.add(ReservedKeys.RECORD_RELATION_PROVIDES_LINK_GROUP, links);
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
