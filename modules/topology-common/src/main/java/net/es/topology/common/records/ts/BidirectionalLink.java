package net.es.topology.common.records.ts;

import net.es.topology.common.records.ts.keys.ReservedKeys;
import net.es.topology.common.records.ts.keys.ReservedValues;
import net.es.topology.common.visitors.sls.Visitable;
import net.es.topology.common.visitors.sls.Visitor;

import java.util.List;

/**
 * A BidirectionalLink is a group of two (unidirectional) Links or
 * LinkGroups together forming a bidirectional link.
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 * @see NML schema docs for the description of the fields
 */
public class BidirectionalLink extends NetworkObject implements Visitable {

    public BidirectionalLink() {
        super(ReservedValues.RECORD_TYPE_BIDIRECTIONAL_LINK);
    }

    /**
     * To allow extending this class with custom record type
     *
     * @param recordType
     */
    public BidirectionalLink(String recordType) {
        super(recordType);
    }

    /**
     * NML specifies that a Bidirectional Link has exactly two Links or two LinkGroups
     * this is not enforced in the implementation
     *
     * @return list of the URNs of the other Links.
     */
    public List<String> getLinks() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_TS_LINK);
    }

    /**
     * NML specifies that a Bidirectional Link has exactly two Links or two LinkGroups
     * this is not enforced in the implementation
     *
     * @param links list of the URNs of the other Links.
     */
    public void setLinks(List<String> links) {
        this.add(ReservedKeys.RECORD_TS_LINK, links);
    }

    /**
     * NML specifies that a Bidirectional Link has exactly two Links or two LinkGroups
     * this is not enforced in the implementation
     *
     * @return list of the URNs of the other Link Groups.
     */
    public List<String> getLinkGroups() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_TS_LINK_GROUP);
    }

    /**
     * NML specifies that a Bidirectional Link has exactly two Links or two LinkGroups
     * this is not enforced in the implementation
     *
     * @param linkGroups list of the URNs of the other Link Groups.
     */
    public void setLinkGroups(List<String> linkGroups) {
        this.add(ReservedKeys.RECORD_TS_LINK_GROUP, linkGroups);
    }

    /**
     * calls the visit method at the visitor
     * @param aVisitor
     */
    public void accept(Visitor aVisitor) {
        aVisitor.visit(this);
    }
}
