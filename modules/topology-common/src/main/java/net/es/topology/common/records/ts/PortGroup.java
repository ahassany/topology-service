package net.es.topology.common.records.ts;

import net.es.topology.common.records.ts.keys.ReservedKeys;
import net.es.topology.common.records.ts.keys.ReservedValues;
import net.es.topology.common.visitors.sls.Visitable;
import net.es.topology.common.visitors.sls.Visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * A PortGroup is an unordered set of Ports
 * <p/>
 * <p/>
 * FIXME (AH): fix handling of labelgroup
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 * @see: NML schema docs for the meaning of the fields
 */
public class PortGroup extends NetworkObject implements Visitable {

    private LabelGroup labelGroup;

    public PortGroup() {
        super(ReservedValues.RECORD_TYPE_PORT_GROUP);
    }

    public PortGroup(String recordType) {
        super(recordType);
    }

    /**
     * A LabelGroup is an unordered set of Labels.
     * <p/>
     * A Label is the technology-specific value that distinguishes a single data
     * stream (a channel) embedded in a larger data stream.
     *
     * @return one specific value taken from the labelset, e.g. a VLAN number
     */
    public LabelGroup getLabelGroup() {
        //return arrayToString(this.getValue(ReservedKeys.RECORD_LABEL_GROUP));
        if (labelGroup == null)
            this.labelGroup = new LabelGroup(this);
        return this.labelGroup;
    }

    /**
     * A LabelGroup is an unordered set of Labels.
     * <p/>
     * A Label is the technology-specific value that distinguishes a single data
     * stream (a channel) embedded in a larger data stream.
     *
     * @param labelGroup one specific value taken from the labelset, e.g. a VLAN number
     */
    public void setLabelGroup(LabelGroup labelGroup) {
        if (labelGroup.getNetworkObject() == this)
            return;

        this.add(ReservedKeys.RECORD_LABEL_GROUP, new ArrayList<String>());
        this.add(ReservedKeys.RECORD_LABEL_GROUP_TYPE, new ArrayList<String>());
        for (List<String> label : labelGroup.getLabels()) {
            ((List<String>) this.getValue(ReservedKeys.RECORD_LABEL_GROUP_TYPE)).add(label.get(0));
            ((List<String>) this.getValue(ReservedKeys.RECORD_LABEL_GROUP)).add(label.get(1));
        }
    }

    /**
     * The URNs of the ports in the group
     *
     * @return List of ports URNs
     */
    public List<String> getPorts() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_TS_PORT);
    }

    /**
     * The URNs of the ports in the group
     *
     * @param ports List of services URNs
     */
    public void setPorts(List<String> ports) {
        this.add(ReservedKeys.RECORD_TS_PORT, ports);
    }

    /**
     * The URNs of the port groups in the group
     *
     * @return List of port groups URNs
     */
    public List<String> getPortGroups() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_TS_PORT_GROUP);
    }

    /**
     * The URNs of the port groups in the group
     *
     * @param portGroups List of services URNs
     */
    public void setPortGroups(List<String> portGroups) {
        this.add(ReservedKeys.RECORD_TS_PORT_GROUP, portGroups);
    }

    /**
     * isAlias is a relation to another Port describe that one can be used as the alias of another.
     *
     * @return list of the URNs of the other Ports.
     */
    public List<String> getIsAlias() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_IS_ALIAS);
    }

    /**
     * isAlias is a relation to another Port describe that one can be used as the alias of another.
     *
     * @param aliases list of the URNs of the other Ports.
     */
    public void setIsAlias(List<String> aliases) {
        this.add(ReservedKeys.RECORD_RELATION_IS_ALIAS, aliases);
    }

    /**
     * isSink relates a Port to one Link to define the outgoing traffic port, and similarly for PortGroup and LinkGroup
     *
     * @return list of the URNs of the other Links.
     */
    public List<String> getIsSink() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_IS_SINK);
    }

    /**
     * isSink relates a Port to one Link to define the outgoing traffic port, and similarly for PortGroup and LinkGroup
     *
     * @param links list of the URNs of the other Links.
     */
    public void setIsSink(List<String> links) {
        this.add(ReservedKeys.RECORD_RELATION_IS_SINK, links);
    }

    /**
     * isSink relates a Port to one Link to define the incoming traffic port, and similarly for PortGroup and LinkGroup
     *
     * @return list of the URNs of the other Links.
     */
    public List<String> getIsSource() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_IS_SOURCE);
    }

    /**
     * isSink relates a Port to one Link to define the incoming traffic port, and similarly for PortGroup and LinkGroup
     *
     * @param links list of the URNs of the other Links.
     */
    public void setIsSource(List<String> links) {
        this.add(ReservedKeys.RECORD_RELATION_IS_SOURCE, links);
    }

    /**
     * The encoding attribute defines the format of the data streaming through the Port.
     * The identifier for the encoding must be a URI
     *
     * @return encoding URI specified by a GFD
     */
    public String getEncoding() {
        return arrayToString(this.getValue(ReservedKeys.RECORD_PORT_ENCODING));
    }

    /**
     * The encoding attribute defines the format of the data streaming through the Port.
     * The identifier for the encoding must be a URI
     *
     * @param encoding URI specified by a GFD
     */
    public void setEncoding(String encoding) {
        this.add(ReservedKeys.RECORD_PORT_ENCODING, encoding);
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
