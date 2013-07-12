package net.es.topology.common.converter.nml;

import net.es.topology.common.records.ts.*;
import net.es.topology.common.records.ts.NetworkObject;
import net.es.topology.common.visitors.sls.Visitor;
import org.ogf.schemas.nml._2013._05.base.*;
import org.ogf.schemas.nsi._2013._09.topology.NSARelationType;
import org.ogf.schemas.nsi._2013._09.topology.NSAType;
import org.ogf.schemas.nsi._2013._09.topology.NsiServiceRelationType;
import org.ogf.schemas.nsi._2013._09.topology.NsiServiceType;

import javax.xml.bind.DatatypeConverter;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.*;

/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class SLSVisitor implements Visitor {
    private org.ogf.schemas.nsi._2013._09.topology.ObjectFactory nsiFactory = new org.ogf.schemas.nsi._2013._09.topology.ObjectFactory();
    private org.ogf.schemas.nml._2013._05.base.ObjectFactory nmlFactory = new org.ogf.schemas.nml._2013._05.base.ObjectFactory();
    private Map<String, NSAType> nsaTypeMap = new HashMap<String, NSAType>();
    private Map<String, NsiServiceType> nsiServiceTypeMap = new HashMap<String, NsiServiceType>();
    private Map<String, TopologyType> topologyTypeMap = new HashMap<String, TopologyType>();
    private Map<String, PortType> portTypeMap = new HashMap<String, PortType>();
    private Map<String, PortGroupType> portGroupTypeMap = new HashMap<String, PortGroupType>();
    private Map<String, LinkType> linkTypeMap = new HashMap<String, LinkType>();
    private Map<String, LinkGroupType> linkGroupTypeMap = new HashMap<String, LinkGroupType>();
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

    public Map<String, LinkGroupType> getLinkGroupTypeMap() {
        return this.linkGroupTypeMap;
    }

    public Map<String, NodeType> getNodeTypeMap() {
        return nodeTypeMap;
    }

    /**
     * A helper method to read the values from the sLS record and setup the NML network object values
     *
     * @param nmlNetObj
     * @param slsNetObj
     */
    protected void setNetworkObejctValues(org.ogf.schemas.nml._2013._05.base.NetworkObject nmlNetObj, NetworkObject slsNetObj) {
        if (slsNetObj.getId() != null) {
            nmlNetObj.setId(slsNetObj.getId());
        }
        if (slsNetObj.getName() != null) {
            nmlNetObj.setName(slsNetObj.getName());
        }

        // to check if the network object has location information
        boolean locationSet = false;
        LocationType nmlLocation = null;
        if (slsNetObj.getLocation() != null) {
            if (nmlNetObj.getLocation() == null) {
                nmlLocation = nmlFactory.createLocationType();
            } else {
                nmlLocation = nmlNetObj.getLocation();
            }
            Location slsLocation = slsNetObj.getLocation();
            if (slsLocation.getId() != null) {
                nmlLocation.setId(slsLocation.getId());
                locationSet = true;
            }
            if (slsLocation.getName() != null) {
                nmlLocation.setName(slsLocation.getName());
                locationSet = true;
            }
            if (slsLocation.getAltitude() != null) {
                nmlLocation.setAlt(slsLocation.getAltitude());
                locationSet = true;
            }
            if (slsLocation.getLongitude() != null) {
                nmlLocation.setLong(slsLocation.getLongitude());
                locationSet = true;
            }
            if (slsLocation.getLatitude() != null) {
                nmlLocation.setLat(slsLocation.getLatitude());
                locationSet = true;
            }
            if (slsLocation.getUnlocode() != null) {
                nmlLocation.setUnlocode(slsLocation.getUnlocode());
                locationSet = true;
            }
            // TODO (AH): set address
        }
        if (locationSet) {
            nmlNetObj.setLocation(nmlLocation);
        }

        // TODO (AH): set lifetime
        if (slsNetObj.getVersion() != null) {
            Calendar calendar = DatatypeConverter.parseDateTime(slsNetObj.getVersion());
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(calendar.getTime());

            try {
                XMLGregorianCalendar xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
                nmlNetObj.setVersion(xmlCalendar);
            } catch (DatatypeConfigurationException e) {
                // TODO (AH): handle error in version date format
                e.printStackTrace();
            }

        }

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
        setNetworkObejctValues(obj, record);
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
        setNetworkObejctValues(obj, record);

        if (record.getNSIServices() != null) {
            for (String urn : record.getNSIServices()) {
                if (getNsiServiceTypeMap().containsKey(urn) == false || serializedURNS.contains(urn) == true) {
                    NsiServiceType obj2 = nsiFactory.createNsiServiceType();
                    obj.getService().add(obj2);
                } else {
                    obj.getService().add(getNsiServiceTypeMap().get(urn));
                    serializedURNS.add(urn);
                }
            }
        }

        // TODO (AH): Parse admin contacts

        if (record.getPeersWith() != null) {
            for (String urn : record.getPeersWith()) {
                NSARelationType relation = nsiFactory.createNSARelationType();
                relation.setType(NMLVisitor.RELATION_PEERS_WITH);
                NSAType objR = null;
                if (getNsaTypeMap().containsKey(urn) == false || serializedURNS.contains(urn) == true || urn.equalsIgnoreCase(record.getId())) {
                    objR = nsiFactory.createNSAType();
                    objR.setId(urn);
                } else {
                    objR = getNsaTypeMap().get(urn);
                    serializedURNS.add(urn);
                }
                relation.getNSA().add(objR);
                obj.getRelation().add(relation);
            }
        }

        if (record.getManagedBy() != null) {
            for (String urn : record.getManagedBy()) {
                NSARelationType relation = nsiFactory.createNSARelationType();
                relation.setType(NMLVisitor.RELATION_MANAGED_BY);
                NSAType objR = null;
                if (getNsaTypeMap().containsKey(urn) == false || serializedURNS.contains(urn) == true || urn.equalsIgnoreCase(record.getId())) {
                    objR = nsiFactory.createNSAType();
                    objR.setId(urn);
                } else {
                    objR = getNsaTypeMap().get(urn);
                    serializedURNS.add(urn);
                }
                relation.getNSA().add(objR);
                obj.getRelation().add(relation);
            }
        }


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
        NsiServiceType obj = nsiFactory.createNsiServiceType();
        setNetworkObejctValues(obj, record);

        if (record.getLink() != null) {
            obj.setLink(record.getLink());
        }

        if (record.getDescribedBy() != null) {
            obj.setDescribedBy(record.getDescribedBy());
        }

        if (record.getType() != null) {
            obj.setType(record.getType());
        }

        if (record.getProvidedBy() != null) {
            for (String urn : record.getProvidedBy()) {
                NsiServiceRelationType relation = nsiFactory.createNsiServiceRelationType();
                relation.setType(NMLVisitor.RELATION_PROVIDED_BY);
                NSAType objR = null;
                if (getNsaTypeMap().containsKey(urn) == false || serializedURNS.contains(urn) == true) {
                    objR = nsiFactory.createNSAType();
                    objR.setId(urn);
                } else {
                    objR = getNsaTypeMap().get(urn);
                    serializedURNS.add(urn);
                }
                relation.getNSA().add(objR);
                obj.getRelation().add(relation);
            }
        }
        getNsiServiceTypeMap().put(obj.getId(), obj);
    }

    @Override
    public void visit(Port record) {
        PortType obj = nmlFactory.createPortType();
        setNetworkObejctValues(obj, record);

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
        PortGroupType obj = nmlFactory.createPortGroupType();
        setNetworkObejctValues(obj, record);

        if (record.getEncoding() != null)
            obj.setEncoding(record.getEncoding());

        // TODO (AH): convert label group

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

        if (record.getPortGroups() != null) {
            for (String urn : record.getPortGroups()) {
                if (getPortGroupTypeMap().containsKey(urn) == false || serializedURNS.contains(urn) == true || urn.equalsIgnoreCase(record.getId())) {
                    PortGroupType obj2 = nmlFactory.createPortGroupType();
                    obj.getPortGroup().add(obj2);
                } else {
                    obj.getPortGroup().add(getPortGroupTypeMap().get(urn));
                    serializedURNS.add(urn);
                }
            }
        }

        if (record.getIsSink() != null) {
            for (String urn : record.getIsSink()) {
                PortGroupRelationType relation = nmlFactory.createPortGroupRelationType();
                relation.setType(NMLVisitor.RELATION_IS_SINK);
                LinkGroupType objR = null;
                if (getLinkTypeMap().containsKey(urn) == false || serializedURNS.contains(urn) == true) {

                    objR = nmlFactory.createLinkGroupType();
                    objR.setId(urn);
                } else {
                    objR = getLinkGroupTypeMap().get(urn);
                    serializedURNS.add(urn);
                }
                relation.getLinkGroup().add(objR);
                obj.getRelation().add(relation);
            }
        }

        if (record.getIsSource() != null) {
            for (String urn : record.getIsSource()) {
                PortGroupRelationType relation = nmlFactory.createPortGroupRelationType();
                relation.setType(NMLVisitor.RELATION_IS_SOURCE);
                LinkGroupType objR = null;
                if (getLinkTypeMap().containsKey(urn) == false || serializedURNS.contains(urn) == true) {

                    objR = nmlFactory.createLinkGroupType();
                    objR.setId(urn);
                } else {
                    objR = getLinkGroupTypeMap().get(urn);
                    serializedURNS.add(urn);
                }
                relation.getLinkGroup().add(objR);
                obj.getRelation().add(relation);
            }
        }

        getPortGroupTypeMap().put(obj.getId(), obj);

        if (record.getIsAlias() != null) {
            for (String urn : record.getIsAlias()) {
                PortGroupRelationType relation = nmlFactory.createPortGroupRelationType();
                relation.setType(NMLVisitor.RELATION_IS_ALIAS);
                PortGroupType objR = null;
                if (getPortGroupTypeMap().containsKey(urn) == false || serializedURNS.contains(urn) == true || urn.equalsIgnoreCase(record.getId())) {
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
    }

    @Override
    public void visit(Topology record) {
        TopologyType obj = nmlFactory.createTopologyType();
        setNetworkObejctValues(obj, record);

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
