package net.es.topology.common.visitors.sls;

import net.es.topology.common.records.ts.*;

/**
 * Traverse the sLS records and call the Visitor visit method on each of them.
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public interface Traverser {

    public void traverse(NetworkObject record, Visitor visitor);

    public void traverse(BidirectionalLink record, Visitor visitor);

    public void traverse(BidirectionalPort record, Visitor visitor);

    public void traverse(Link record, Visitor visitor);

    public void traverse(LinkGroup record, Visitor visitor);

    public void traverse(Node record, Visitor visitor);

    public void traverse(NSA record, Visitor visitor);

    public void traverse(NSIService record, Visitor visitor);

    public void traverse(Port record, Visitor visitor);

    public void traverse(PortGroup record, Visitor visitor);

    public void traverse(Topology record, Visitor visitor);

    public void traverse(SwitchingService record, Visitor visitor);

}
