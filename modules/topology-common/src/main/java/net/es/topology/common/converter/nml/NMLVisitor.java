package net.es.topology.common.converter.nml;

import net.es.topology.common.visitors.BaseVisitor;
import org.ogf.schemas.nml._2013._05.base.NodeType;

/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class NMLVisitor extends BaseVisitor{

    @Override
    public void visit(NodeType aBean) {
        System.out.println("Visiting node: "  + aBean.getName());
    }
}
