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
}