package net.es.topology.common.converter.nml;

import net.es.topology.common.records.ts.*;
import net.es.topology.common.records.ts.NetworkObject;
import net.es.topology.common.visitors.sls.Visitor;
import org.ogf.schemas.nml._2013._05.base.*;
import org.ogf.schemas.nsi._2013._09.topology.NSAType;
import org.ogf.schemas.nsi._2013._09.topology.NsiServiceType;
import org.ogf.schemas.nsi._2013._09.topology.ObjectFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class SLSVisitor implements Visitor {
    private ObjectFactory nsiFactory = new ObjectFactory();
    private org.ogf.schemas.nml._2013._05.base.ObjectFactory nmlFactory = new org.ogf.schemas.nml._2013._05.base.ObjectFactory();
    private Map<String, NSAType> nsaTypeMap = new HashMap<String, NSAType>();
    private Map<String, NsiServiceType> nsiServiceTypeMap = new HashMap<String, NsiServiceType>();
    private Map<String, TopologyType> topologyTypeMap = new HashMap<String, TopologyType>();
    private Map<String, PortType> portTypeMap = new HashMap<String, PortType>();
    private Map<String, PortGroupType> portGroupTypeMap = new HashMap<String, PortGroupType>();
    private Map<String, LinkType> linkTypeMap = new HashMap<String, LinkType>();
    private Map<String, NodeType> nodeTypeMap = new HashMap<String, NodeType>();
    /**
     * to make sure each object serialized once.
     */
    private List<String> serializedURNS = new ArrayList<String>();

    public Map<String, NSAType> getNsaTypeMap() {
        return nsaTypeMap;
    }

    public Map<String, NsiServiceType> getNsiServiceTypeMap() {
        return nsiServiceTypeMap;
    }

    public Map<String, TopologyType> getTopologyTypeMap() {
        return topologyTypeMap;
    }

    public Map<String, PortType> getPortTypeMap() {
        return portTypeMap;
    }

    public Map<String, PortGroupType> getPortGroupTypeMap() {
        return portGroupTypeMap;
    }

    public Map<String, LinkType> getLinkTypeMap() {
        return linkTypeMap;
    }

    public Map<String, NodeType> getNodeTypeMap() {
        return nodeTypeMap;
    }

    @Override
    public void visit(NetworkObject record) {

    }

    @Override
    public void visit(BidirectionalLink record) {

    }

    @Override
    public void visit(BidirectionalPort record) {

    }

    @Override
    public void visit(Link record) {
        LinkType obj = nmlFactory.createLinkType();
        obj.setId(record.getId());
        //obj.setVersion(new XMLGregorianCalendar().set record.getVersion());
        obj.setName(record.getName());


        getLinkTypeMap().put(obj.getId(), obj);
    }

    @Override
    public void visit(LinkGroup record) {

    }

    @Override
    public void visit(Node record) {

    }

    @Override
    public void visit(NSA record) {
        NSAType obj = nsiFactory.createNSAType();
        obj.setId(record.getId());
        //obj.setVersion(new XMLGregorianCalendar().set record.getVersion());
        obj.setName(record.getName());

        if (record.getTopologies() != null) {
            for (String urn : record.getTopologies()) {
                if (getTopologyTypeMap().containsKey(urn) == false || serializedURNS.contains(urn) == true) {
                    TopologyType obj2 = nmlFactory.createTopologyType();
                    obj.getTopology().add(obj2);
                } else {
                    obj.getTopology().add(getTopologyTypeMap().get(urn));
                    serializedURNS.add(urn);
                }
            }
        }

        getNsaTypeMap().put(obj.getId(), obj);
    }

    @Override
    public void visit(NSIService record) {

    }

    @Override
    public void visit(Port record) {
        PortType obj = nmlFactory.createPortType();
        obj.setId(record.getId());
        //obj.setVersion(new XMLGregorianCalendar().set record.getVersion());
        obj.setName(record.getName());
        if (record.getEncoding() != null)
            obj.setEncoding(record.getEncoding());
        if (record.getLabelType() != null) {
            obj.setLabel(nmlFactory.createLabelType());
            obj.getLabel().setLabeltype(record.getLabelType());
            obj.getLabel().setValue(record.getLabel());
        }

        if (record.getIsSource() != null) {
            for (String urn : record.getIsSource()) {
                PortRelationType relation = nmlFactory.createPortRelationType();
                relation.setType(NMLVisitor.RELATION_IS_SOURCE);
                LinkType objR = null;
                if (getLinkTypeMap().containsKey(urn) == false || serializedURNS.contains(urn) == true) {

                    objR = nmlFactory.createLinkType();
                    objR.setId(urn);
                } else {
                    objR = getLinkTypeMap().get(urn);
                    serializedURNS.add(urn);
                }
                relation.getLink().add(objR);
                obj.getRelation().add(relation);
            }
        }

        if (record.getIsSink() != null) {
            for (String urn : record.getIsSink()) {
                PortRelationType relation = nmlFactory.createPortRelationType();
                relation.setType(NMLVisitor.RELATION_IS_SINK);
                LinkType objR = null;
                if (getLinkTypeMap().containsKey(urn) == false || serializedURNS.contains(urn) == true) {

                    objR = nmlFactory.createLinkType();
                    objR.setId(urn);
                } else {
                    objR = getLinkTypeMap().get(urn);
                    serializedURNS.add(urn);
                }
                relation.getLink().add(objR);
                obj.getRelation().add(relation);
            }
        }

        getPortTypeMap().put(obj.getId(), obj);

        if (record.getIsAlias() != null) {
            for (String urn : record.getIsAlias()) {
                PortRelationType relation = nmlFactory.createPortRelationType();
                relation.setType(NMLVisitor.RELATION_IS_ALIAS);
                PortType objR = null;
                if (getPortTypeMap().containsKey(urn) == false || serializedURNS.contains(urn) == true || urn.equalsIgnoreCase(record.getId())) {
                    objR = nmlFactory.createPortType();
                    objR.setId(urn);
                } else {
                    objR = getPortTypeMap().get(urn);
                    serializedURNS.add(urn);
                }
                relation.getPort().add(objR);
                obj.getRelation().add(relation);
            }
        }
    }

    @Override
    public void visit(PortGroup record) {

    }

    @Override
    public void visit(Topology record) {
        TopologyType obj = nmlFactory.createTopologyType();
        obj.setId(record.getId());
        //nsa.setVersion(new XMLGregorianCalendar().set record.getVersion());
        obj.setName(record.getName());

        if (record.getPorts() != null) {
            for (String urn : record.getPorts()) {
                if (getPortTypeMap().containsKey(urn) == false || serializedURNS.contains(urn) == true) {
                    PortType obj2 = nmlFactory.createPortType();
                    obj.getPort().add(obj2);
                } else {
                    obj.getPort().add(getPortTypeMap().get(urn));
                    serializedURNS.add(urn);
                }
            }
        }

        if (record.getLinks() != null) {
            for (String urn : record.getLinks()) {
                if (getLinkTypeMap().containsKey(urn) == false || serializedURNS.contains(urn) == true) {
                    LinkType obj2 = nmlFactory.createLinkType();
                    obj.getLink().add(obj2);
                } else {
                    obj.getLink().add(getLinkTypeMap().get(urn));
                    serializedURNS.add(urn);
                }
            }
        }


        if (record.getHasInboundPortGroup() != null) {
            for (String urn : record.getHasInboundPortGroup()) {
                TopologyRelationType relation = nmlFactory.createTopologyRelationType();
                relation.setType(NMLVisitor.RELATION_HAS_INBOUND_PORT);
                PortGroupType objR = null;
                if (getPortGroupTypeMap().containsKey(urn) == false || serializedURNS.contains(urn) == true) {
                    objR = nmlFactory.createPortGroupType();
                    objR.setId(urn);
                } else {
                    objR = getPortGroupTypeMap().get(urn);
                    serializedURNS.add(urn);
                }
                relation.getPortGroup().add(objR);
                obj.getRelation().add(relation);
            }
        }

        if (record.getHasOutboundPortGroup() != null) {
            for (String urn : record.getHasOutboundPortGroup()) {
                TopologyRelationType relation = nmlFactory.createTopologyRelationType();
                relation.setType(NMLVisitor.RELATION_HAS_OUTBOUND_PORT);
                PortGroupType objR = null;
                if (getPortGroupTypeMap().containsKey(urn) == false || serializedURNS.contains(urn) == true) {
                    objR = nmlFactory.createPortGroupType();
                    objR.setId(urn);
                } else {
                    objR = getPortGroupTypeMap().get(urn);
                    serializedURNS.add(urn);
                }
                relation.getPortGroup().add(objR);
                obj.getRelation().add(relation);
            }
        }

        getTopologyTypeMap().put(obj.getId(), obj);

        if (record.getIsAlias() != null) {
            for (String urn : record.getIsAlias()) {
                if (getTopologyTypeMap().containsKey(urn)) {
                    org.ogf.schemas.nml._2013._05.base.ObjectFactory factory = new org.ogf.schemas.nml._2013._05.base.ObjectFactory();
                    TopologyRelationType relation = factory.createTopologyRelationType();
                    relation.setType(NMLVisitor.RELATION_IS_ALIAS);
                    TopologyType topo = factory.createTopologyType();
                    topo.setId(urn);
                    relation.getTopology().add(topo);
                    obj.getRelation().add(relation);
                }
            }
        }
    }
}
