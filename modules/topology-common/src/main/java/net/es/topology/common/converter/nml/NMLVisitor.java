package net.es.topology.common.converter.nml;

import net.es.lookup.common.exception.ParserException;
import net.es.lookup.protocol.json.JSONParser;
import net.es.topology.common.records.nml.Lifetime;
import net.es.topology.common.records.nml.Node;
import net.es.topology.common.visitors.BaseVisitor;
import org.ogf.schemas.nml._2013._05.base.*;

import java.util.ArrayList;

/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class NMLVisitor extends BaseVisitor{
    // Relations
    public static final String RELATION_HAS_INBOUND_PORT = "http://schemas.ogf.org/nml/2013/05/base#hasInboundPort";
    public static final String RELATION_HAS_OUTBOUND_PORT = "http://schemas.ogf.org/nml/2013/05/base#hasOutboundPort";
    public static final String RELATION_HAS_SERVICE = "http://schemas.ogf.org/nml/2013/05/base#hasService";
    public static final String RELATION_IS_ALIAS = "http://schemas.ogf.org/nml/2013/05/base#isAlias";


    @Override
    public void visit(NodeType nodeType) {
        System.out.println("Visiting node: " + nodeType.getName());
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
        try {
            System.out.println(JSONParser.toString(sLSNode));
        } catch (ParserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    @Override
    public void visit(LifeTimeType lifetimeType) {
        System.out.println("Visiting lifetime: " + lifetimeType.toString());
        /*
        Lifetime lifetime = new Lifetime();
        lifetime.setStart(lifetimeType.getStart().toXMLFormat());
        lifetime.setEnd(lifetimeType.getEnd().toXMLFormat());
        */
    }
}
