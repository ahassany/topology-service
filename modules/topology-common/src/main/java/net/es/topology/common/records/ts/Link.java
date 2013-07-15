package net.es.topology.common.records.ts;

import net.es.topology.common.records.ts.keys.ReservedKeys;
import net.es.topology.common.records.ts.keys.ReservedValues;
import net.es.topology.common.visitors.sls.Visitable;
import net.es.topology.common.visitors.sls.Visitor;

import java.util.List;

/**
 * A Link object describes a unidirectional data transport from each of its sources to all of its sinks.
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 * @see NML schema docs for the description of the fields
 */
public class Link extends NetworkObject implements Visitable {

    public Link() {
        super(ReservedValues.RECORD_TYPE_LINK);
    }

    /**
     * To allow extending this class with custom record type
     *
     * @param recordType
     */
    public Link(String recordType) {
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
     * The encoding attribute defines the format of the data streaming through the Link.
     * The identifier for the encoding must be a URI
     *
     * @return encoding URI specified by a GFD
     */
    public String getEncoding() {
        return arrayToString(this.getValue(ReservedKeys.RECORD_PORT_ENCODING));
    }

    /**
     * The encoding attribute defines the format of the data streaming through the Link.
     * The identifier for the encoding must be a URI
     *
     * @param encoding URI specified by a GFD
     */
    public void setEncoding(String encoding) {
        this.add(ReservedKeys.RECORD_PORT_ENCODING, encoding);
    }

    /**
     * A value of true changes the definition of Link to: data transport from each sources to all sinks,
     * except that there is no data transport from a source to a sink if the source and sink are grouped
     * together in a BidirectionalPort group. The default value of noReturnTraffic is false.
     *
     * @return
     */
    public Boolean getNoReturnTraffic() {
        Object value = this.getValue(ReservedKeys.RECORD_LINK_NORETURN_TRAFFIC);
        if (value != null)
            return Boolean.valueOf(this.arrayToString(value));
        else
            return null;
    }

    /**
     * A value of true changes the definition of Link to: data transport from each sources to all sinks,
     * except that there is no data transport from a source to a sink if the source and sink are grouped
     * together in a BidirectionalPort group. The default value of noReturnTraffic is false.
     *
     * @param noReturnTraffic
     */
    public void setNoReturnTraffic(Boolean noReturnTraffic) {
        this.add(ReservedKeys.RECORD_LINK_NORETURN_TRAFFIC, noReturnTraffic.toString());
    }

    /**
     * isAlias is a relation to another Link describe that one can be used as the alias of another.
     *
     * @return list of the URNs of the other Links.
     */
    public List<String> getIsAlias() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_IS_ALIAS);
    }

    /**
     * isAlias is a relation to another Link describe that one can be used as the alias of another.
     *
     * @param aliases list of the URNs of the other Links.
     */
    public void setIsAlias(List<String> aliases) {
        this.add(ReservedKeys.RECORD_RELATION_IS_ALIAS, aliases);
    }

    /**
     * The composition of links into a path, and decomposition into link segments is described
     * by the isSerialCompoundLink relation
     *
     * @return list of the URNs of the other Links.
     */
    public List<String> getIsSerialCompoundLink() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_IS_SERIAL_COMPOUND_LINK);
    }

    /**
     * The composition of links into a path, and decomposition into link segments is described
     * by the isSerialCompoundLink relation
     *
     * @param links list of the URNs of the other Links.
     */
    public void setIsSerialCompoundLink(List<String> links) {
        this.add(ReservedKeys.RECORD_RELATION_IS_SERIAL_COMPOUND_LINK, links);
    }

    /**
     * @return list of the URNs of the other Links.
     */
    public List<String> getNext() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_NEXT);
    }

    /**
     * @param links list of the URNs of the other Links.
     */
    public void setNext(List<String> links) {
        this.add(ReservedKeys.RECORD_RELATION_NEXT, links);
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
