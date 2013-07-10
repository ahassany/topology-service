package net.es.topology.common.records.ts;

import net.es.topology.common.records.ts.keys.ReservedKeys;
import net.es.topology.common.records.ts.keys.ReservedValues;
import net.es.topology.common.visitors.sls.Visitable;
import net.es.topology.common.visitors.sls.Visitor;

import java.util.List;

/**
 * Represents a Port
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 * @see: NML schema docs for the meaning of the fields
 */
public class Port extends NetworkObject implements Visitable {

    public Port() {
        super(ReservedValues.RECORD_TYPE_PORT);
    }

    public Port(String recordType) {
        super(recordType);
    }

    /**
     * A Label is the technology-specific value that distinguishes a single data
     * stream (a channel) embedded in a larger data stream.
     *
     * @return one specific value taken from the labelset, e.g. a VLAN number
     */
    public String getLabel() {
        return arrayToString(this.getValue(ReservedKeys.RECORD_LABEL));
    }

    /**
     * A Label is the technology-specific value that distinguishes a single data
     * stream (a channel) embedded in a larger data stream.
     *
     * @param label one specific value taken from the labelset, e.g. a VLAN number
     */
    public void setLabel(String label) {
        this.add(ReservedKeys.RECORD_LABEL, label);
    }

    /**
     * To refer to a technology-specific labelset, e.g. a URI for VLANs
     *
     * @return URI to refer to a technology-specific labelset, e.g. a URI for VLANs
     */
    public String getLabelType() {
        return arrayToString(this.getValue(ReservedKeys.RECORD_LABEL_TYPE));
    }

    /**
     * @param labelType to refer to a technology-specific labelset, e.g. a URI for VLANs
     */
    public void setLabelType(String labelType) {
        this.add(ReservedKeys.RECORD_LABEL_TYPE, labelType);
    }

    /**
     * Port to AdaptationService, relating one server-layer Port to an adaptation function.
     * Port to DeadaptationService, relating one server-layer Port to a deadaptation function.
     *
     * @return List of services URNs
     */
    public List<String> getHasService() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_HAS_SERVICE);
    }

    /**
     * Port to AdaptationService, relating one server-layer Port to an adaptation function.
     * Port to DeadaptationService, relating one server-layer Port to a deadaptation function.
     *
     * @param services List of services URNs
     */
    public void setHasService(List<String> services) {
        this.add(ReservedKeys.RECORD_RELATION_HAS_SERVICE, services);
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
