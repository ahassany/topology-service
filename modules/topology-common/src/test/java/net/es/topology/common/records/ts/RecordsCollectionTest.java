package net.es.topology.common.records.ts;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class RecordsCollectionTest {
    private RecordsCollection collection;

    @Before
    public void createCollection() {
        this.collection = new RecordsCollection();
    }

    @After
    public void destroyCollection() {
        this.collection = null;
    }

    @Test
    public void testGetNodes() throws Exception {
        Assert.assertNotNull(this.collection.getNodes());
    }

    @Test
    public void testSetNodes() throws Exception {
        // Arrange
        Map<String, Node> nodes = new HashMap<String, Node>();

        // Act
        this.collection.setNodes(nodes);

        // Assert
        Assert.assertSame(nodes, this.collection.getNodes());
    }

    @Test
    public void testGetPorts() throws Exception {
        Assert.assertNotNull(this.collection.getPorts());
    }

    @Test
    public void testSetPorts() throws Exception {
        // Arrange
        Map<String, Port> ports = new HashMap<String, Port>();

        // Act
        this.collection.setPorts(ports);

        // Assert
        Assert.assertSame(ports, this.collection.getPorts());
    }

    @Test
    public void testGetLinks() throws Exception {
        Assert.assertNotNull(this.collection.getLinks());
    }

    @Test
    public void testSetLinks() throws Exception {
        // Arrange
        Map<String, Link> links = new HashMap<String, Link>();

        // Act
        this.collection.setLinks(links);

        // Assert
        Assert.assertSame(links, this.collection.getLinks());
    }

    @Test
    public void testGetTopologies() throws Exception {
        Assert.assertNotNull(this.collection.getTopologies());
    }

    @Test
    public void testSetTopologies() throws Exception {
        // Arrange
        Map<String, Topology> topologies = new HashMap<String, Topology>();

        // Act
        this.collection.setTopologies(topologies);

        // Assert
        Assert.assertSame(topologies, this.collection.getTopologies());
    }

    @Test
    public void testGetBidirectionalPorts() throws Exception {
        Assert.assertNotNull(this.collection.getBidirectionalPorts());
    }

    @Test
    public void testSetBidirectionalPorts() throws Exception {
        // Arrange
        Map<String, BidirectionalPort> biports = new HashMap<String, BidirectionalPort>();

        // Act
        this.collection.setBidirectionalPorts(biports);

        // Assert
        Assert.assertSame(biports, this.collection.getBidirectionalPorts());
    }

    @Test
    public void testGetBidirectionalLinks() throws Exception {
        Assert.assertNotNull(this.collection.getBidirectionalLinks());
    }

    @Test
    public void testSetBidirectionalLinks() throws Exception {
        // Arrange
        Map<String, BidirectionalLink> biLinks = new HashMap<String, BidirectionalLink>();

        // Act
        this.collection.setBidirectionalLinks(biLinks);

        // Assert
        Assert.assertSame(biLinks, this.collection.getBidirectionalLinks());
    }

    @Test
    public void testGetPortGroups() throws Exception {
        Assert.assertNotNull(this.collection.getPortGroups());
    }

    @Test
    public void testSetPortGroups() throws Exception {
        // Arrange
        Map<String, PortGroup> ports = new HashMap<String, PortGroup>();

        // Act
        this.collection.setPortGroups(ports);

        // Assert
        Assert.assertSame(ports, this.collection.getPortGroups());
    }

    @Test
    public void testPortInstance() throws Exception {
        // Arrange
        String id1 = "urn:ogf:network:example.net:2013:portA";
        String id2 = "urn:ogf:network:example.net:2013:portB";
        Port portA = new Port();
        portA.setId(id1);
        this.collection.getPorts().put(id1, portA);

        // Act
        Port portA2 = this.collection.portInstance(id1);
        Port portB = this.collection.portInstance(id2);
        Port portNull = this.collection.portInstance(null);

        // Assert
        Assert.assertSame(portA, portA2);
        Assert.assertSame(portB, this.collection.portInstance(id2));
        Assert.assertSame(portNull, this.collection.portInstance(portNull.getId()));
    }

    @Test
    public void testLinkInstance() throws Exception {
        // Arrange
        String id1 = "urn:ogf:network:example.net:2013:linkA";
        String id2 = "urn:ogf:network:example.net:2013:linkB";
        Link linkA = new Link();
        linkA.setId(id1);
        this.collection.getLinks().put(id1, linkA);

        // Act
        Link linkA2 = this.collection.linkInstance(id1);
        Link linkB = this.collection.linkInstance(id2);
        Link linkNull = this.collection.linkInstance(null);

        // Assert
        Assert.assertSame(linkA, linkA2);
        Assert.assertSame(linkB, this.collection.linkInstance(id2));
        Assert.assertSame(linkNull, this.collection.linkInstance(linkNull.getId()));
    }

    @Test
    public void testNodeInstance() throws Exception {
        // Arrange
        String id1 = "urn:ogf:network:example.net:2013:nodeA";
        String id2 = "urn:ogf:network:example.net:2013:nodeB";
        Node nodeA = new Node();
        nodeA.setId(id1);
        this.collection.getNodes().put(id1, nodeA);

        // Act
        Node nodeA2 = this.collection.nodeInstance(id1);
        Node nodeB = this.collection.nodeInstance(id2);
        Node nodeNull = this.collection.nodeInstance(null);

        // Assert
        Assert.assertSame(nodeA, nodeA2);
        Assert.assertSame(nodeB, this.collection.nodeInstance(id2));
        Assert.assertSame(nodeNull, this.collection.nodeInstance(nodeNull.getId()));
    }

    @Test
    public void testTopologyInstance() throws Exception {
        // Arrange
        String id1 = "urn:ogf:network:example.net:2013:topologyA";
        String id2 = "urn:ogf:network:example.net:2013:topologyB";
        Topology topologyA = new Topology();
        topologyA.setId(id1);
        this.collection.getTopologies().put(id1, topologyA);

        // Act
        Topology topologyA2 = this.collection.topologyInstance(id1);
        Topology topologyB = this.collection.topologyInstance(id2);
        Topology topologyNull = this.collection.topologyInstance(null);

        // Assert
        Assert.assertSame(topologyA, topologyA2);
        Assert.assertSame(topologyB, this.collection.topologyInstance(id2));
        Assert.assertSame(topologyNull, this.collection.topologyInstance(topologyNull.getId()));
    }

    @Test
    public void testBidirectionalPortInstance() throws Exception {
        // Arrange
        String id1 = "urn:ogf:network:example.net:2013:biportA";
        String id2 = "urn:ogf:network:example.net:2013:biportB";
        BidirectionalPort biportA = new BidirectionalPort();
        biportA.setId(id1);
        this.collection.getBidirectionalPorts().put(id1, biportA);

        // Act
        BidirectionalPort biportA2 = this.collection.bidirectionalPortInstance(id1);
        BidirectionalPort biportB = this.collection.bidirectionalPortInstance(id2);
        BidirectionalPort biportNull = this.collection.bidirectionalPortInstance(null);

        // Assert
        Assert.assertSame(biportA, biportA2);
        Assert.assertSame(biportB, this.collection.bidirectionalPortInstance(id2));
        Assert.assertSame(biportNull, this.collection.bidirectionalPortInstance(biportNull.getId()));
    }

    @Test
    public void testBidirectionalLinkInstance() throws Exception {
        // Arrange
        String id1 = "urn:ogf:network:example.net:2013:biportA";
        String id2 = "urn:ogf:network:example.net:2013:biportB";
        BidirectionalLink biLinkA = new BidirectionalLink();
        biLinkA.setId(id1);
        this.collection.getBidirectionalLinks().put(id1, biLinkA);

        // Act
        BidirectionalLink biLinkA2 = this.collection.bidirectionalLinkInstance(id1);
        BidirectionalLink biLinkB = this.collection.bidirectionalLinkInstance(id2);
        BidirectionalLink biLinkNull = this.collection.bidirectionalLinkInstance(null);

        // Assert
        Assert.assertSame(biLinkA, biLinkA2);
        Assert.assertSame(biLinkB, this.collection.bidirectionalLinkInstance(id2));
        Assert.assertSame(biLinkNull, this.collection.bidirectionalLinkInstance(biLinkNull.getId()));
    }

    @Test
    public void testPortGroupInstance() throws Exception {
        // Arrange
        String id1 = "urn:ogf:network:example.net:2013:portA";
        String id2 = "urn:ogf:network:example.net:2013:portB";
        PortGroup portA = new PortGroup();
        portA.setId(id1);
        this.collection.getPortGroups().put(id1, portA);

        // Act
        PortGroup portA2 = this.collection.portGroupInstance(id1);
        PortGroup portB = this.collection.portGroupInstance(id2);
        PortGroup portNull = this.collection.portGroupInstance(null);

        // Assert
        Assert.assertSame(portA, portA2);
        Assert.assertSame(portB, this.collection.portGroupInstance(id2));
        Assert.assertSame(portNull, this.collection.portGroupInstance(portNull.getId()));
    }
}
