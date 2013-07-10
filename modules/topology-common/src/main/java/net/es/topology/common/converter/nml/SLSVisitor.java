package net.es.topology.common.converter.nml;

import net.es.topology.common.records.ts.*;
import net.es.topology.common.visitors.sls.Visitor;
import org.ogf.schemas.nml._2013._05.base.LinkType;
import org.ogf.schemas.nml._2013._05.base.NodeType;
import org.ogf.schemas.nml._2013._05.base.PortType;
import org.ogf.schemas.nml._2013._05.base.TopologyType;
import org.ogf.schemas.nsi._2013._09.topology.NSAType;
import org.ogf.schemas.nsi._2013._09.topology.NsiServiceType;
import org.ogf.schemas.nsi._2013._09.topology.ObjectFactory;

import java.util.HashMap;
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
    private Map<String, LinkType> linkTypeMap = new HashMap<String, LinkType>();
    private Map<String, NodeType> nodeTypeMap = new HashMap<String, NodeType>();

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
        getNsaTypeMap().put(obj.getId(), obj);
    }

    @Override
    public void visit(NSIService record) {

    }

    @Override
    public void visit(Port record) {

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
        getTopologyTypeMap().put(obj.getId(), obj);
    }
}
