package net.es.topology.common.converter.nml;

import net.es.lookup.common.exception.ParserException;
import net.es.lookup.protocol.json.JSONParser;
import net.es.topology.common.visitors.BaseVisitor;
import org.ogf.schemas.nml._2013._05.base.NodeType;
import net.es.topology.common.records.*;

/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class NMLVisitor extends BaseVisitor{

    @Override
    public void visit(NodeType aBean) {
        System.out.println("Visiting node: "  + aBean.getName());
        Node n = new Node();
        n.setId(aBean.getId());
        n.setName(aBean.getName());
        try {
            System.out.println(JSONParser.toString(n));
        } catch (ParserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
