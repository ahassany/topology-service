package net.es.topology.common.converter.nml;

import net.es.topology.common.records.ts.*;
import net.es.topology.common.records.ts.utils.RecordsCollection;
import net.es.topology.common.visitors.nml.BaseVisitor;
import org.ogf.schemas.nml._2013._05.base.*;
import org.ogf.schemas.nml._2013._05.base.NetworkObject;
import org.ogf.schemas.nsi._2013._09.messaging.Message;
import org.ogf.schemas.nsi._2013._09.topology.NSARelationType;
import org.ogf.schemas.nsi._2013._09.topology.NSAType;
import org.ogf.schemas.nsi._2013._09.topology.NsiServiceRelationType;
import org.ogf.schemas.nsi._2013._09.topology.NsiServiceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Implements a Visitor pattern to travers NML objects and generate sLS records.
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class NMLVisitor extends BaseVisitor {
    // Relations
    public static final String RELATION_HAS_INBOUND_PORT = "http://schemas.ogf.org/nml/2013/05/base#hasInboundPort";
    public static final String RELATION_HAS_OUTBOUND_PORT = "http://schemas.ogf.org/nml/2013/05/base#hasOutboundPort";
    public static final String RELATION_HAS_SERVICE = "http://schemas.ogf.org/nml/2013/05/base#hasService";
    public static final String RELATION_IS_ALIAS = "http://schemas.ogf.org/nml/2013/05/base#isAlias";
    public static final String RELATION_IS_SINK = "http://schemas.ogf.org/nml/2013/05/base#isSink";
    public static final String RELATION_IS_SOURCE = "http://schemas.ogf.org/nml/2013/05/base#isSource";
    public static final String RELATION_IS_SERIAL_COMPOUND_LINK = "http://schemas.ogf.org/nml/2013/05/base#isSerialCompoundLink";
    public static final String RELATION_NEXT = "http://schemas.ogf.org/nml/2013/05/base#next";
    public static final String RELATION_PEERS_WITH = "http://schemas.ogf.org/nsi/2013/09/topology#peersWith";
    public static final String RELATION_MANAGED_BY = "http://schemas.ogf.org/nsi/2013/09/topology#managedBy";
    public static final String RELATION_PROVIDED_BY = "http://schemas.ogf.org/nsi/2013/09/topology#providedBy";
    // JAXB Bindings
    public final static String jaxb_bindings = "org.ogf.schemas.nsi._2013._09.topology:org.ogf.schemas.nsi._2013._09.messaging:org.ogf.schemas.nml._2013._05.base";
    /**
     * Logger
     */
    private final Logger logger = LoggerFactory.getLogger(NMLVisitor.class);
    private RecordsCollection recordsCollection = null;
    /**
     * A Unique UUID to identify the log trace withing each visitor instance.
     *
     * @see <a href="http://netlogger.lbl.gov/">Netlogger</a> best practices document.
     */
    private String logUUID;


    public NMLVisitor(RecordsCollection recordsCollection) {
        this.logUUID = UUID.randomUUID().toString();
        this.recordsCollection = recordsCollection;
    }

    /**
     * Initialize the visitor with specific log trace ID.
     *
     * @param logUUID
     */
    public NMLVisitor(RecordsCollection recordsCollection, String logUUID) {
        this.logUUID = logUUID;
        this.recordsCollection = recordsCollection;
    }

    /**
     * Get the Unique UUID to identify the log trace withing each visitor instance.
     * <p/>
     * * @see <a href="http://netlogger.lbl.gov/">Netlogger</a> best practices document.
     */
    public String getLogUUID() {
        return this.logUUID;
    }

    public RecordsCollection getRecordsCollection() {
        return this.recordsCollection;
    }

    /**
     * A common method to parse location objects
     *
     * @param networkObject the NML network object
     * @param slSObject     the sLS record
     */
    protected void setLocation(NetworkObject networkObject, net.es.topology.common.records.ts.NetworkObject slSObject) {
        if (networkObject.getLocation() == null)
            return;
        LocationType locationType = networkObject.getLocation();
        Location location = new Location(slSObject);
        if (locationType.getId() != null) {
            location.setId(locationType.getId());
        }
        if (locationType.getName() != null) {
            location.setName(locationType.getName());
        }
        if (locationType.getUnlocode() != null) {
            location.setUnlocode(locationType.getUnlocode());
        }
        if (locationType.getAlt() != null) {
            location.setAltitude(locationType.getAlt());
        }
        if (locationType.getLat() != null) {
            location.setLatitude(locationType.getLat());
        }
        if (locationType.getLong() != null) {
            location.setLongitude(locationType.getLong());
        }
        // TODO(AH): deal with address
    }

    /**
     * A common method to parse network objects
     *
     * @param networkObject the NML network object
     * @param slSObject     the sLS record
     */
    protected void setNetworkObject(NetworkObject networkObject, net.es.topology.common.records.ts.NetworkObject slSObject) {
        this.setLocation(networkObject, slSObject);
        if (networkObject.getName() != null)
            slSObject.setName(networkObject.getName());

        if (networkObject.getVersion() != null)
            slSObject.setVersion(networkObject.getVersion().toString());
    }

    /**
     * Visit nml node to generate sLS Node record
     *
     * @param nodeType
     */
    @Override
    public void visit(NodeType nodeType) {
        logger.trace("event=NMLVisitor.visit.NodeType.start id=" + nodeType.getId() + " guid=" + this.logUUID);
        Node sLSNode = getRecordsCollection().nodeInstance(nodeType.getId());

        this.setNetworkObject(nodeType, sLSNode);

        for (NodeRelationType relation : nodeType.getRelation()) {
            if (relation.getType().equalsIgnoreCase(RELATION_HAS_INBOUND_PORT)) {
                if (sLSNode.getHasInboundPort() == null) {
                    sLSNode.setHasInboundPort(new ArrayList<String>());
                }
                for (PortType port : relation.getPort()) {
                    sLSNode.getHasInboundPort().add(port.getId());
                }
            } else if (relation.getType().equalsIgnoreCase(RELATION_HAS_OUTBOUND_PORT)) {
                if (sLSNode.getHasOutboundPort() == null) {
                    sLSNode.setHasOutboundPort(new ArrayList<String>());
                }
                for (PortType port : relation.getPort()) {
                    sLSNode.getHasOutboundPort().add(port.getId());
                }
            } else if (relation.getType().equalsIgnoreCase(RELATION_HAS_SERVICE)) {
                if (sLSNode.getHasService() == null) {
                    sLSNode.setHasService(new ArrayList<String>());
                }
                for (SwitchingServiceType service : relation.getSwitchingService()) {
                    sLSNode.getHasService().add(service.getId());
                }
            } else if (relation.getType().equalsIgnoreCase(RELATION_IS_ALIAS)) {
                if (sLSNode.getIsAlias() == null) {
                    sLSNode.setHasService(new ArrayList<String>());
                }
                for (NodeType nodes : relation.getNode()) {
                    sLSNode.getIsAlias().add(nodes.getId());
                }
            }
        }
        logger.trace("event=NMLVisitor.visit.NodeType.end status=0 guid=" + this.logUUID);
    }

    /**
     * Visit nml port to generate sLS port record
     *
     * @param portType
     */
    @Override
    public void visit(PortType portType) {
        logger.trace("event=NMLVisitor.visit.PortType.start id=" + portType.getId() + " guid=" + this.logUUID);
        Port sLSPort = recordsCollection.portInstance(portType.getId());

        this.setNetworkObject(portType, sLSPort);

        if (portType.getEncoding() != null)
            sLSPort.setEncoding(portType.getEncoding());

        if (portType.getLabel() != null) {
            if (portType.getLabel().getValue() != null)
                sLSPort.setLabel(portType.getLabel().getValue());
            if (portType.getLabel().getLabeltype() != null)
                sLSPort.setLabelType(portType.getLabel().getLabeltype());
        }

        for (PortRelationType relation : portType.getRelation()) {
            if (relation.getType().equalsIgnoreCase(RELATION_IS_SINK)) {
                if (sLSPort.getIsSink() == null) {
                    sLSPort.setIsSink(new ArrayList<String>());
                }
                for (LinkType link : relation.getLink()) {
                    sLSPort.getIsSink().add(link.getId());
                }
            } else if (relation.getType().equalsIgnoreCase(RELATION_IS_SOURCE)) {
                if (sLSPort.getIsSource() == null) {
                    sLSPort.setIsSource(new ArrayList<String>());
                }
                for (LinkType link : relation.getLink()) {
                    sLSPort.getIsSource().add(link.getId());
                }
            } else if (relation.getType().equalsIgnoreCase(RELATION_HAS_SERVICE)) {
                if (sLSPort.getHasService() == null) {
                    sLSPort.setHasService(new ArrayList<String>());
                }
                for (NetworkObject service : relation.getService()) {
                    sLSPort.getHasService().add(service.getId());
                }
            } else if (relation.getType().equalsIgnoreCase(RELATION_IS_ALIAS)) {
                if (sLSPort.getIsAlias() == null) {
                    sLSPort.setHasService(new ArrayList<String>());
                }
                for (PortType port : relation.getPort()) {
                    sLSPort.getIsAlias().add(port.getId());
                }
            }
        }
        logger.trace("event=NMLVisitor.visit.PortType.end status=0 guid=" + this.logUUID);
    }

    /**
     * Visit nml bidirectional port to generate sLS port record
     *
     * @param bidirectionalPortType
     */
    public void visit(BidirectionalPortType bidirectionalPortType) {

        logger.trace("event=NMLVisitor.visit.BidirectionalPortType.start id=" + bidirectionalPortType.getId() + " guid=" + this.logUUID);
        BidirectionalPort sLSBiPort = recordsCollection.bidirectionalPortInstance(bidirectionalPortType.getId());

        this.setNetworkObject(bidirectionalPortType, sLSBiPort);

        for (JAXBElement<? extends NetworkObject> element : bidirectionalPortType.getRest()) {
            if (element.getValue() instanceof PortType) {
                PortType port = (PortType) element.getValue();
                if (sLSBiPort.getPorts() == null) {
                    sLSBiPort.setPorts(new ArrayList<String>());
                }
                sLSBiPort.getPorts().add(port.getId());
            } else if (element.getValue() instanceof PortGroupType) {
                PortGroupType portGroup = (PortGroupType) element.getValue();
                if (sLSBiPort.getPortGroups() == null) {
                    sLSBiPort.setPortGroups(new ArrayList<String>());
                }
                sLSBiPort.getPortGroups().add(portGroup.getId());
            }
        }
        logger.trace("event=NMLVisitor.visit.BidirectionalPortType.end status=0 guid=" + this.logUUID);
    }

    /**
     * Visit nml link to generate sLS link record
     *
     * @param linkType
     */
    @Override
    public void visit(LinkType linkType) {
        logger.trace("event=NMLVisitor.visit.LinkType.start id=" + linkType.getId() + " guid=" + this.logUUID);
        Link sLSLink = recordsCollection.linkInstance(linkType.getId());

        this.setNetworkObject(linkType, sLSLink);

        if (linkType.getEncoding() != null)
            sLSLink.setEncoding(linkType.getEncoding());
        if (linkType.isNoReturnTraffic() != null)
            sLSLink.setNoReturnTraffic(linkType.isNoReturnTraffic());

        if (linkType.getLabel() != null) {
            if (linkType.getLabel().getValue() != null)
                sLSLink.setLabel(linkType.getLabel().getValue());
            if (linkType.getLabel().getLabeltype() != null)
                sLSLink.setLabelType(linkType.getLabel().getLabeltype());
        }

        for (LinkRelationType relation : linkType.getRelation()) {
            if (relation.getType().equalsIgnoreCase(RELATION_IS_ALIAS)) {
                if (sLSLink.getIsAlias() == null) {
                    sLSLink.setIsAlias(new ArrayList<String>());
                }
                for (LinkType link : relation.getLink()) {
                    sLSLink.getIsAlias().add(link.getId());
                }
            }

            if (relation.getType().equalsIgnoreCase(RELATION_IS_SERIAL_COMPOUND_LINK)) {
                if (sLSLink.getIsSerialCompoundLink() == null) {
                    sLSLink.setIsSerialCompoundLink(new ArrayList<String>());
                }
                for (LinkType link : relation.getLink()) {
                    sLSLink.getIsSerialCompoundLink().add(link.getId());
                }
            }

            if (relation.getType().equalsIgnoreCase(RELATION_NEXT)) {
                if (sLSLink.getNext() == null) {
                    sLSLink.setNext(new ArrayList<String>());
                }
                for (LinkType link : relation.getLink()) {
                    sLSLink.getNext().add(link.getId());
                }
            }
        }
        logger.trace("event=NMLVisitor.visit.LinkType.end status=0 guid=" + this.logUUID);
    }

    /**
     * Visit nml bidirectional link to generate sLS link record
     *
     * @param bidirectionalLinkType
     */
    public void visit(BidirectionalLinkType bidirectionalLinkType) {
        logger.trace("event=NMLVisitor.visit.BidirectionalLinkType.start id=" + bidirectionalLinkType.getId() + " guid=" + this.logUUID);
        BidirectionalLink sLSBiLink = recordsCollection.bidirectionalLinkInstance(bidirectionalLinkType.getId());

        this.setNetworkObject(bidirectionalLinkType, sLSBiLink);

        for (JAXBElement<? extends NetworkObject> element : bidirectionalLinkType.getRest()) {
            if (element.getValue() instanceof LinkType) {
                LinkType link = (LinkType) element.getValue();
                if (sLSBiLink.getLinks() == null) {
                    sLSBiLink.setLinks(new ArrayList<String>());
                }
                sLSBiLink.getLinks().add(link.getId());
            } else if (element.getValue() instanceof LinkGroupType) {
                LinkGroupType linkGroup = (LinkGroupType) element.getValue();
                if (sLSBiLink.getLinkGroups() == null) {
                    sLSBiLink.setLinkGroups(new ArrayList<String>());
                }
                sLSBiLink.getLinkGroups().add(linkGroup.getId());
            }
        }
        logger.trace("event=NMLVisitor.visit.BidirectionalLinkType.end status=0 guid=" + this.logUUID);
    }

    /**
     * Visit nml port group to generate sLS port group record
     *
     * @param portGroupType
     */
    @Override
    public void visit(PortGroupType portGroupType) {
        logger.trace("event=NMLVisitor.visit.PortGroupType.start id=" + portGroupType.getId() + " guid=" + this.logUUID);
        PortGroup sLSPortGroup = recordsCollection.portGroupInstance(portGroupType.getId());

        this.setNetworkObject(portGroupType, sLSPortGroup);

        if (portGroupType.getEncoding() != null)
            sLSPortGroup.setEncoding(portGroupType.getEncoding());

        if (portGroupType.getLabelGroup() != null) {
            LabelGroup labelGroup = sLSPortGroup.getLabelGroup();
            for (LabelGroupType labelGroupType : portGroupType.getLabelGroup()) {
                labelGroup.addLabel(labelGroupType.getLabeltype(), labelGroupType.getValue());
            }
        }

        // ports in the group
        if (portGroupType.getPort() != null && portGroupType.getPort().size() != 0)
            sLSPortGroup.setPorts(new ArrayList<String>());
        for (PortType port : portGroupType.getPort()) {
            // This make sure an ID is generated if it wasn't provided
            sLSPortGroup.getPorts().add(recordsCollection.portInstance(port.getId()).getId());
        }

        // port groups in the group
        if (portGroupType.getPortGroup() != null && portGroupType.getPortGroup().size() != 0)
            sLSPortGroup.setPortGroups(new ArrayList<String>());
        for (PortGroupType port : portGroupType.getPortGroup()) {
            // This make sure an ID is generated if it wasn't provided
            sLSPortGroup.getPortGroups().add(recordsCollection.portGroupInstance(port.getId()).getId());
        }

        for (PortGroupRelationType relation : portGroupType.getRelation()) {
            if (relation.getType().equalsIgnoreCase(RELATION_IS_SINK)) {
                if (sLSPortGroup.getIsSink() == null) {
                    sLSPortGroup.setIsSink(new ArrayList<String>());
                }
                for (LinkGroupType link : relation.getLinkGroup()) {
                    sLSPortGroup.getIsSink().add(link.getId());
                }
            } else if (relation.getType().equalsIgnoreCase(RELATION_IS_SOURCE)) {
                if (sLSPortGroup.getIsSource() == null) {
                    sLSPortGroup.setIsSource(new ArrayList<String>());
                }
                for (LinkGroupType link : relation.getLinkGroup()) {
                    sLSPortGroup.getIsSource().add(link.getId());
                }
            } else if (relation.getType().equalsIgnoreCase(RELATION_IS_ALIAS)) {
                if (sLSPortGroup.getIsAlias() == null) {
                    sLSPortGroup.setIsAlias(new ArrayList<String>());
                }
                for (PortGroupType port : relation.getPortGroup()) {
                    sLSPortGroup.getIsAlias().add(port.getId());
                }
            }
        }
        logger.trace("event=NMLVisitor.visit.PortGroupType.end status=0 guid=" + this.logUUID);
    }

    /**
     * Visit nml link group to generate sLS link group record
     *
     * @param linkGroupType
     */
    @Override
    public void visit(LinkGroupType linkGroupType) {
        logger.trace("event=NMLVisitor.visit.LinkGroupType.start id=" + linkGroupType.getId() + " guid=" + this.logUUID);
        LinkGroup sLSLinkGroup = recordsCollection.linkGroupInstance(linkGroupType.getId());

        this.setNetworkObject(linkGroupType, sLSLinkGroup);

        if (linkGroupType.getLabelGroup() != null) {
            LabelGroup labelGroup = sLSLinkGroup.getLabelGroup();
            for (LabelGroupType labelGroupType : linkGroupType.getLabelGroup()) {
                labelGroup.addLabel(labelGroupType.getLabeltype(), labelGroupType.getValue());
            }
        }

        // Links in the group
        if (linkGroupType.getLink() != null && linkGroupType.getLink().size() != 0) {
            sLSLinkGroup.setLinks(new ArrayList<String>());
        }
        for (LinkType link : linkGroupType.getLink()) {
            // This make sure an ID is generated if it wasn't provided
            sLSLinkGroup.getLinks().add(recordsCollection.linkInstance(link.getId()).getId());
        }

        // LinkGroups in the group
        if (linkGroupType.getLinkGroup() != null && linkGroupType.getLinkGroup().size() != 0) {
            sLSLinkGroup.setLinkGroups(new ArrayList<String>());
        }
        for (LinkGroupType link : linkGroupType.getLinkGroup()) {
            // This make sure an ID is generated if it wasn't provided
            sLSLinkGroup.getLinkGroups().add(recordsCollection.linkGroupInstance(link.getId()).getId());
        }

        // Parse relations
        for (LinkGroupRelationType relation : linkGroupType.getRelation()) {
            if (relation.getType().equalsIgnoreCase(RELATION_IS_SERIAL_COMPOUND_LINK)) {
                if (sLSLinkGroup.getIsSerialCompoundLink() == null) {
                    sLSLinkGroup.setIsSerialCompoundLink(new ArrayList<String>());
                }
                for (LinkGroupType link : relation.getLinkGroup()) {
                    sLSLinkGroup.getIsSerialCompoundLink().add(link.getId());
                }
            } else if (relation.getType().equalsIgnoreCase(RELATION_IS_ALIAS)) {
                if (sLSLinkGroup.getIsAlias() == null) {
                    sLSLinkGroup.setIsAlias(new ArrayList<String>());
                }
                for (LinkGroupType link : relation.getLinkGroup()) {
                    sLSLinkGroup.getIsAlias().add(link.getId());
                }
            }
        }
        logger.trace("event=NMLVisitor.visit.LinkGroupType.end status=0 guid=" + this.logUUID);
    }

    /**
     * Visit NSI NSA to generate sLS NSA record
     *
     * @param nsaType
     */
    public void visit(NSAType nsaType) {
        logger.trace("event=NMLVisitor.visit.NSAType.start id=" + nsaType.getId() + " guid=" + this.logUUID);
        NSA sLSNSA = recordsCollection.NSAInstance(nsaType.getId());

        this.setNetworkObject(nsaType, sLSNSA);

        if (nsaType.getTopology() != null && nsaType.getTopology().size() != 0)
            sLSNSA.setTopologies(new ArrayList<String>());
        for (TopologyType topologyType : nsaType.getTopology()) {
            // This make sure an ID is generated if it wasn't provided
            sLSNSA.getTopologies().add(recordsCollection.topologyInstance(topologyType.getId()).getId());
        }

        for (NSARelationType relation : nsaType.getRelation()) {
            if (relation.getType().equalsIgnoreCase(RELATION_PEERS_WITH)) {
                if (sLSNSA.getPeersWith() == null) {
                    sLSNSA.setPeersWith(new ArrayList<String>());
                }
                for (NSAType nsa : relation.getNSA()) {
                    sLSNSA.getPeersWith().add(nsa.getId());
                }
            } else if (relation.getType().equalsIgnoreCase(RELATION_MANAGED_BY)) {
                if (sLSNSA.getManagedBy() == null) {
                    sLSNSA.setManagedBy(new ArrayList<String>());
                }
                for (NSAType nsa : relation.getNSA()) {
                    sLSNSA.getManagedBy().add(nsa.getId());
                }
            }
        }

        if (nsaType.getService() != null && nsaType.getService().size() != 0)
            sLSNSA.setNSIServices(new ArrayList<String>());
        for (NsiServiceType service : nsaType.getService()) {
            sLSNSA.getNSIServices().add(recordsCollection.NSIServiceInstance(service.getId()).getId());
        }

        logger.trace("event=NMLVisitor.visit.NSAType.end status=0 guid=" + this.logUUID);
    }

    /**
     * Visit NSI Service to generate sLS Service record
     *
     * @param nsiServiceType
     */
    public void visit(NsiServiceType nsiServiceType) {
        logger.trace("event=NMLVisitor.visit.NsiServiceType.start id=" + nsiServiceType.getId() + " guid=" + this.logUUID);
        NSIService nsiService = recordsCollection.NSIServiceInstance(nsiServiceType.getId());

        this.setNetworkObject(nsiServiceType, nsiService);

        if (nsiServiceType.getType() != null)
            nsiService.setType(nsiServiceType.getType());

        if (nsiServiceType.getDescribedBy() != null)
            nsiService.setDescribedBy(nsiServiceType.getDescribedBy());

        if (nsiServiceType.getLink() != null)
            nsiService.setLink(nsiServiceType.getLink());

        for (NsiServiceRelationType relation : nsiServiceType.getRelation()) {
            if (relation.getType().equalsIgnoreCase(RELATION_PROVIDED_BY)) {
                if (nsiService.getProvidedBy() == null) {
                    nsiService.setProvidedBy(new ArrayList<String>());
                }
                for (NSAType nsa : relation.getNSA()) {
                    nsiService.getProvidedBy().add(nsa.getId());
                }
            }
        }
        logger.trace("event=NMLVisitor.visit.NsiServiceType.end status=0 guid=" + this.logUUID);
    }

    /**
     * Visit nml lifetime to generate sLS lifetime record
     *
     * @param lifetimeType
     */
    @Override
    public void visit(LifeTimeType lifetimeType) {
        logger.trace("event=NMLVisitor.visit.LifeTimeType.start guid=" + this.logUUID);
        Lifetime lifetime = new Lifetime();
        lifetime.setStart(lifetimeType.getStart().toXMLFormat());
        lifetime.setEnd(lifetimeType.getEnd().toXMLFormat());
        logger.trace("event=NMLVisitor.visit.LifeTimeType.end status=0 guid=" + this.logUUID);
    }

    /**
     * Visit NSI Message will NOT generate sLS mapping
     *
     * @param body
     */
    @Override
    public void visit(Message.Body body) {
        logger.trace("event=NMLVisitor.visit.Body.start guid=" + this.logUUID);
        logger.trace("event=NMLVisitor.visit.Body.end status=0 guid=" + this.logUUID);
    }

    /**
     * Visit NML Topology to generate sLS topology record
     *
     * @param topologyType
     */
    @Override
    public void visit(TopologyType topologyType) {
        logger.trace("event=NMLVisitor.visit.TopologyType.start id=" + topologyType.getId() + " guid=" + this.logUUID);
        Topology sLSTopo = getRecordsCollection().topologyInstance(topologyType.getId());

        this.setNetworkObject(topologyType, sLSTopo);

        // Parse relations
        for (TopologyRelationType relation : topologyType.getRelation()) {
            if (relation.getType().equalsIgnoreCase(RELATION_HAS_INBOUND_PORT)) {
                if (relation.getPort().size() > 0 && sLSTopo.getHasInboundPort() == null) {
                    sLSTopo.setHasInboundPort(new ArrayList<String>());
                }
                for (PortType port : relation.getPort()) {
                    sLSTopo.getHasInboundPort().add(port.getId());
                }
                if (relation.getPortGroup().size() > 0 && sLSTopo.getHasOutboundPort() == null) {
                    sLSTopo.setHasInboundPortGroup(new ArrayList<String>());
                }
                for (PortGroupType port : relation.getPortGroup()) {
                    sLSTopo.getHasInboundPortGroup().add(port.getId());
                }
            } else if (relation.getType().equalsIgnoreCase(RELATION_HAS_OUTBOUND_PORT)) {
                if (relation.getPort().size() > 0 && sLSTopo.getHasOutboundPort() == null) {
                    sLSTopo.setHasOutboundPort(new ArrayList<String>());
                }
                for (PortType port : relation.getPort()) {
                    sLSTopo.getHasOutboundPort().add(port.getId());
                }

                if (relation.getPortGroup().size() > 0 && sLSTopo.getHasOutboundPort() == null) {
                    sLSTopo.setHasOutboundPortGroup(new ArrayList<String>());
                }
                for (PortGroupType port : relation.getPortGroup()) {
                    sLSTopo.getHasOutboundPortGroup().add(port.getId());
                }
            } else if (relation.getType().equalsIgnoreCase(RELATION_HAS_SERVICE)) {
                if (sLSTopo.getHasService() == null) {
                    sLSTopo.setHasService(new ArrayList<String>());
                }
                for (NetworkObject service : relation.getService()) {
                    sLSTopo.getHasService().add(service.getId());
                }
            } else if (relation.getType().equalsIgnoreCase(RELATION_IS_ALIAS)) {
                if (sLSTopo.getIsAlias() == null) {
                    sLSTopo.setHasService(new ArrayList<String>());
                }
                for (TopologyType topologies : relation.getTopology()) {
                    sLSTopo.getIsAlias().add(topologies.getId());
                }
            }
        }

        // Add Refs to the Ports in the Topology
        if (topologyType.getPort() != null && topologyType.getPort().size() != 0) {
            if (sLSTopo.getPorts() == null)
                sLSTopo.setPorts(new ArrayList<String>());
        }
        for (PortType port : topologyType.getPort()) {
            sLSTopo.getPorts().add(port.getId());
        }

        // Add Refs to the Nodes in the Topology
        if (topologyType.getNode() != null && topologyType.getNode().size() != 0) {
            if (sLSTopo.getNodes() == null)
                sLSTopo.setNodes(new ArrayList<String>());
        }
        for (NodeType node : topologyType.getNode()) {
            sLSTopo.getNodes().add(node.getId());
        }

        // Add Refs to the Links in the Topology
        if (topologyType.getLink() != null && topologyType.getLink().size() != 0) {
            if (sLSTopo.getLinks() == null)
                sLSTopo.setLinks(new ArrayList<String>());
        }
        for (LinkType link : topologyType.getLink()) {
            sLSTopo.getLinks().add(link.getId());
        }

        // Add Refs to the Group elements in the Topology
        for (NetworkObject object : topologyType.getGroup()) {
            // Add Refs to the Bidirectional Ports in the Topology
            if (object instanceof BidirectionalPortType) {
                if (sLSTopo.getBidirectionalPorts() == null)
                    sLSTopo.setBidirectionalPorts(new ArrayList<String>());
                sLSTopo.getBidirectionalPorts().add(object.getId());
            }

            if (object instanceof BidirectionalLinkType) {
                if (sLSTopo.getBidirectionalLinks() == null)
                    sLSTopo.setBidirectionalLinks(new ArrayList<String>());
                sLSTopo.getBidirectionalLinks().add(object.getId());
            }

            if (object instanceof PortGroupType) {
                if (sLSTopo.getPortGroups() == null) {
                    sLSTopo.setPortGroups(new ArrayList<String>());
                }
                sLSTopo.getPortGroups().add(object.getId());
            }

            if (object instanceof LinkGroupType) {
                if (sLSTopo.getLinkGroups() == null) {
                    sLSTopo.setLinkGroups(new ArrayList<String>());
                }
                sLSTopo.getLinkGroups().add(object.getId());
            }

            if (object instanceof TopologyType) {
                if (sLSTopo.getTopologies() == null) {
                    sLSTopo.setTopologies(new ArrayList<String>());
                }
                sLSTopo.getTopologies().add(object.getId());
            }
        }

        // TODO (AH): deal with services.
        logger.trace("event=NMLVisitor.visit.TopologyType.end status=0 guid=" + this.logUUID);
    }
}
