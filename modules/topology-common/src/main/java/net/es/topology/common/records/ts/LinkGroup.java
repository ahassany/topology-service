package net.es.topology.common.records.ts;

import net.es.topology.common.records.ts.keys.ReservedKeys;
import net.es.topology.common.records.ts.keys.ReservedValues;
import net.es.topology.common.visitors.sls.Visitable;
import net.es.topology.common.visitors.sls.Visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * A LinkGroup is an unordered set of LInk
 * <p/>
 * <p/>
 * FIXME (AH): fix handling of labelgroup
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 * @see: NML schema docs for the meaning of the fields
 */
public class LinkGroup extends NetworkObject implements Visitable {
    private LabelGroup labelGroup;

    public LinkGroup() {
        super(ReservedValues.RECORD_TYPE_LINK_GROUP);
    }

    public LinkGroup(String recordType) {
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
     * The URNs of the links in the group
     *
     * @return List of links URNs
     */
    public List<String> getLinks() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_TS_LINK);
    }

    /**
     * The URNs of the links in the group
     *
     * @param links List of links URNs
     */
    public void setLinks(List<String> links) {
        this.add(ReservedKeys.RECORD_TS_LINK, links);
    }

    /**
     * The URNs of the link groups in the group
     *
     * @return List of link groups URNs
     */
    public List<String> getLinkGroups() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_TS_LINK_GROUP);
    }

    /**
     * The URNs of the link groups in the group
     *
     * @param linkGroups List of link URNs
     */
    public void setLinkGroups(List<String> linkGroups) {
        this.add(ReservedKeys.RECORD_TS_LINK_GROUP, linkGroups);
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
     * calls the visit method at the visitor
     *
     * @param aVisitor
     */
    public void accept(Visitor aVisitor) {
        aVisitor.visit(this);
    }
}
