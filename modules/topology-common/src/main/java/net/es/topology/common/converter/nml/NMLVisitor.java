package net.es.topology.common.converter.nml;

import net.es.topology.common.records.nml.Lifetime;
import net.es.topology.common.records.nml.Node;
import net.es.topology.common.records.nml.Port;
import net.es.topology.common.records.nml.Topology;
import net.es.topology.common.visitors.BaseVisitor;
import org.ogf.schemas.nml._2013._05.base.*;
import org.ogf.schemas.nsi._2013._09.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
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
    public final static String jaxb_bindings = "org.ogf.schemas.nsi._2013._09.topology:org.ogf.schemas.nsi._2013._09.messaging:org.ogf.schemas.nml._2013._05.base";
    /**
     * Logger
     */
    private final Logger logger = LoggerFactory.getLogger(NMLVisitor.class);
    /**
     * I'm not sure if those are needed
     * TODO (AH): define proper handling for generated objects
     */
    List<Node> nodes = new ArrayList<Node>();
    List<Port> ports = new ArrayList<Port>();
    /**
     * A Unique UUID to identify the log trace withing each visitor instance.
     *
     * @see Netlogger best practices document.
     */
    private String logUUID;


    public NMLVisitor() {
        this.logUUID = UUID.randomUUID().toString();
    }


    /**
     * Initialize the visitor with specific log trace ID.
     *
     * @param logUUID
     */
    public NMLVisitor(String logUUID) {
        this.logUUID = logUUID;
    }

    @Override
    public void visit(NodeType nodeType) {
        logger.info("event=NMLVisitor.visit.NodeType.start guid=" + this.logUUID);
        Node sLSNode = new Node();
        sLSNode.setId(nodeType.getId());
        sLSNode.setName(nodeType.getName());

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
        this.nodes.add(sLSNode);
        logger.info("event=NMLVisitor.visit.NodeType.end guid=" + this.logUUID);
    }

    @Override
    public void visit(PortType portType) {
        logger.info("event=NMLVisitor.visit.PortType.start guid=" + this.logUUID);
        Port sLSPort = new Port();
        sLSPort.setId(portType.getId());
        sLSPort.setName(portType.getName());

        for (PortRelationType relation : portType.getRelation()) {
            if (relation.getType().equalsIgnoreCase(RELATION_IS_SINK)) {
                if (sLSPort.getIsSink() == null) {
                    sLSPort.setIsSink(new ArrayList<String>());
                }
                for (LinkType link : relation.getLink()) {
                    sLSPort.getIsSink().add(link.getId());
                }
            } else if (relation.getType().equalsIgnoreCase(RELATION_IS_SINK)) {
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
        this.ports.add(sLSPort);
        logger.info("event=NMLVisitor.visit.PortType.end guid=" + this.logUUID);
    }

    @Override
    public void visit(LifeTimeType lifetimeType) {
        logger.info("event=NMLVisitor.visitLifeTimeType.start guid=" + this.logUUID);
        Lifetime lifetime = new Lifetime();
        lifetime.setStart(lifetimeType.getStart().toXMLFormat());
        lifetime.setEnd(lifetimeType.getEnd().toXMLFormat());
        logger.info("event=NMLVisitor.visitLifeTimeType.end guid=" + this.logUUID);
    }

    @Override
    public void visit(Message.Body body) {
        logger.info("event=NMLVisitor.visitBody.start guid=" + this.logUUID);
        logger.info("event=NMLVisitor.visitBody.end guid=" + this.logUUID);
    }

    @Override
    public void visit(TopologyType topologyType) {
        logger.info("event=NMLVisitor.visit.TopologyType.start guid=" + this.logUUID);
        Topology sLSTopo = new Topology();
        sLSTopo.setId(topologyType.getId());
        sLSTopo.setName(topologyType.getName());
        for (TopologyRelationType relation : topologyType.getRelation()) {
            if (relation.getType().equalsIgnoreCase(RELATION_HAS_INBOUND_PORT)) {
                if (sLSTopo.getHasInboundPort() == null) {
                    sLSTopo.setHasInboundPort(new ArrayList<String>());
                }
                for (PortType port : relation.getPort()) {
                    sLSTopo.getHasInboundPort().add(port.getId());
                }
            } else if (relation.getType().equalsIgnoreCase(RELATION_HAS_OUTBOUND_PORT)) {
                if (sLSTopo.getHasOutboundPort() == null) {
                    sLSTopo.setHasOutboundPort(new ArrayList<String>());
                }
                for (PortType port : relation.getPort()) {
                    sLSTopo.getHasOutboundPort().add(port.getId());
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
        logger.info("event=NMLVisitor.visit.TopologyType.end guid=" + this.logUUID);
    }
}
