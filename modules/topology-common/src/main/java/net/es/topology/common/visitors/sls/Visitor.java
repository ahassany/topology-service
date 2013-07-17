package net.es.topology.common.visitors.sls;

import net.es.topology.common.records.ts.*;

/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public interface Visitor {

    public void visit(NetworkObject record);
    public void visit(BidirectionalLink record);
    public void visit(BidirectionalPort record);
    public void visit(Link record);
    public void visit(LinkGroup record);
    public void visit(Node record);
    public void visit(NSA record);
    public void visit(NSIService record);
    public void visit(Port record);
    public void visit(PortGroup record);
    public void visit(Topology record);
    public void visit(SwitchingService record);
    public void visit(AdaptationService record);

}
