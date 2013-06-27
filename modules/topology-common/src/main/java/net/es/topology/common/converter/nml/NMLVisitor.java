package net.es.topology.common.converter.nml;

import net.es.lookup.common.exception.ParserException;
import net.es.lookup.protocol.json.JSONParser;
import net.es.topology.common.records.nml.Lifetime;
import net.es.topology.common.records.nml.Node;
import net.es.topology.common.visitors.BaseVisitor;
import org.ogf.schemas.nml._2013._05.base.*;
import org.ogf.schemas.nsi._2013._09.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.StringWriter;
import java.util.*;

/**
 * Implements a Visitor pattern to travers NML objects and generate sLS records.
 *
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class NMLVisitor extends BaseVisitor{
    // Relations
    public static final String RELATION_HAS_INBOUND_PORT = "http://schemas.ogf.org/nml/2013/05/base#hasInboundPort";
    public static final String RELATION_HAS_OUTBOUND_PORT = "http://schemas.ogf.org/nml/2013/05/base#hasOutboundPort";
    public static final String RELATION_HAS_SERVICE = "http://schemas.ogf.org/nml/2013/05/base#hasService";
    public static final String RELATION_IS_ALIAS = "http://schemas.ogf.org/nml/2013/05/base#isAlias";
    public final static String jaxb_bindings = "org.ogf.schemas.nsi._2013._09.topology:org.ogf.schemas.nsi._2013._09.messaging:org.ogf.schemas.nml._2013._05.base";

    /**
     * Loggger
     */
    private final Logger logger = LoggerFactory.getLogger(NMLVisitor.class);
    /**
     * A Unique UUID to identify the log trace withing each visitor instance.
     *
     * @see Netlogger best practices document.
     */
    private String logUUID;

    List < Node > nodes = new ArrayList<Node>();


    public NMLVisitor() {
        this.logUUID = UUID.randomUUID().toString();
    }


    /**
     *  Initialize the visitor with specifc log trace ID.
     * @param logUUID
     */
    public NMLVisitor(String logUUID) {
        this.logUUID = logUUID;
    }

    @Override
    public void visit(NodeType nodeType) {
        logger.info("event=NMLVisitor.visitNodeType.start guid=" + this.logUUID);
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
        System.out.println("ASdf");
        logger.info("event=NMLVisitor.visitNodeType.end guid=" + this.logUUID);
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
}
