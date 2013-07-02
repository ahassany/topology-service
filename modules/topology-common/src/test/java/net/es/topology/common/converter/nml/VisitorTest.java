package net.es.topology.common.converter.nml;

import net.es.lookup.client.RegistrationClient;
import net.es.lookup.client.SimpleLS;
import net.es.lookup.common.exception.LSClientException;
import net.es.lookup.common.exception.ParserException;
import net.es.lookup.records.Record;
import net.es.topology.common.records.ts.*;
import net.es.topology.common.visitors.DepthFirstTraverserImpl;
import net.es.topology.common.visitors.TraversingVisitor;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.ogf.schemas.nml._2013._05.base.LinkType;
import org.ogf.schemas.nml._2013._05.base.NodeType;
import org.ogf.schemas.nml._2013._05.base.PortType;
import org.ogf.schemas.nml._2013._05.base.TopologyType;
import org.ogf.schemas.nsi._2013._09.messaging.Message;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.util.Map;

/**
 * Tests the NML Visitor
 * Loads messages from src/test/resources/xml-examples/*.xml
 * <p/>
 * Note: try to use arrange-act-assert testing pattern as mush as possible
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class VisitorTest {
    public final static String jaxb_bindings = "org.ogf.schemas.nsi._2013._09.topology:org.ogf.schemas.nsi._2013._09.messaging:org.ogf.schemas.nml._2013._05.base";

    // Commonly used value in the tests
    private final String VLAN_URI = "http://schemas.ogf.org/nml/2013/05/ethernet#vlan";

    @Test
    public void testVisitNodeType() throws JAXBException {
        // Arrange
        // Create a visitor
        RecordsCollection collection = new RecordsCollection();
        NMLVisitor visitor = new NMLVisitor(collection);
        TraversingVisitor tv = new TraversingVisitor(new DepthFirstTraverserImpl(), visitor);
        // tv.setTraverseFirst(true);

        // Prepare for by reading the example message
        InputStream in =
                getClass().getClassLoader().getResourceAsStream("xml-examples/example-message-node.xml");

        StreamSource ss = new StreamSource(in);
        JAXBContext context = JAXBContext.newInstance(jaxb_bindings);
        Unmarshaller um = context.createUnmarshaller();
        Message msg = (Message) um.unmarshal(ss);

        // Act
        // For some reason this doesn't work
        msg.getBody().accept(tv);

        // This is a work around that the visitor is not traversing elements in Body
        JAXBElement<NodeType> element = (JAXBElement<NodeType>) msg.getBody().getAny().get(0);
        Assert.assertNotNull(element.getValue());
        NodeType obj = element.getValue();
        obj.accept(tv);

        // Assert
        Assert.assertEquals(1, collection.getNodes().size());
        Node nodes[] = collection.getNodes().values().toArray(new Node[collection.getNodes().size()]);
        Node record = nodes[0];
        Assert.assertEquals("urn:ogf:network:example.net:2013:nodeA", record.getId());
        Assert.assertEquals("Node_A", record.getName());
        Assert.assertEquals(1, record.getHasService().size());
        Assert.assertEquals("urn:ogf:network:example.net:2013:nodeA:switchingService", record.getHasService().get(0));
        Assert.assertEquals(2, record.getHasInboundPort().size());
        Assert.assertEquals("urn:ogf:network:example.net:2013:nodeA:port_X:in", record.getHasInboundPort().get(0));
        Assert.assertEquals("urn:ogf:network:example.net:2013:nodeA:port_Y:in", record.getHasInboundPort().get(1));
        Assert.assertEquals(2, record.getHasOutboundPort().size());
        Assert.assertEquals("urn:ogf:network:example.net:2013:nodeA:port_X:out", record.getHasOutboundPort().get(0));
        Assert.assertEquals("urn:ogf:network:example.net:2013:nodeA:port_Y:out", record.getHasOutboundPort().get(1));
        // TODO (AH): Test version
    }

    @Test
    @Ignore
    public void testVisitLocationType() throws JAXBException {
        InputStream in =
                getClass().getClassLoader().getResourceAsStream("xml-examples/example-message-location.xml");

        StreamSource ss = new StreamSource(in);
        JAXBContext context = JAXBContext.newInstance(jaxb_bindings);
        Unmarshaller um = context.createUnmarshaller();
        Message msg = (Message) um.unmarshal(ss);

        RecordsCollection collection = new RecordsCollection();
        NMLVisitor visitor = new NMLVisitor(collection);
        TraversingVisitor tv = new TraversingVisitor(new DepthFirstTraverserImpl(), visitor);
        tv.setTraverseFirst(true);
        msg.getBody().accept(tv);
        JAXBElement<NodeType> nodeElement = (JAXBElement<NodeType>) msg.getBody().getAny().get(0);
        Assert.assertNotNull(nodeElement.getValue());
        NodeType nodeType = nodeElement.getValue();
        Assert.assertNotNull(nodeType.getLocation());
    }

    @Test
    public void testVisitPortType() throws JAXBException {
        // Prepare
        // Create a visitor
        RecordsCollection collection = new RecordsCollection();
        NMLVisitor visitor = new NMLVisitor(collection);
        TraversingVisitor tv = new TraversingVisitor(new DepthFirstTraverserImpl(), visitor);
        // tv.setTraverseFirst(true);

        // Prepare for by reading the example message
        InputStream in =
                getClass().getClassLoader().getResourceAsStream("xml-examples/example-message-port.xml");

        StreamSource ss = new StreamSource(in);
        JAXBContext context = JAXBContext.newInstance(jaxb_bindings);
        Unmarshaller um = context.createUnmarshaller();
        Message msg = (Message) um.unmarshal(ss);

        // Act
        // For some reason this doesn't work
        msg.getBody().accept(tv);

        // This is a work around that the visitor is not traversing elements in Body
        JAXBElement<PortType> portElement = (JAXBElement<PortType>) msg.getBody().getAny().get(0);
        Assert.assertNotNull(portElement.getValue());
        PortType portType = portElement.getValue();
        Assert.assertNotNull(portType.getLabel());
        portType.accept(tv);

        // Assert
        Assert.assertEquals(1, collection.getPorts().size());
        Port ports[] = collection.getPorts().values().toArray(new Port[collection.getPorts().size()]);
        Port sLSPort = ports[0];
        Assert.assertNotNull(sLSPort);
        Assert.assertNotNull(sLSPort.getId());
        Assert.assertEquals("urn:ogf:network:example.net:2013:portA", sLSPort.getId());
        Assert.assertEquals(null, sLSPort.getName());
        Assert.assertEquals("1501", sLSPort.getLabel());
        Assert.assertEquals(VLAN_URI, sLSPort.getLabelType());
    }

    @Test
    public void testVisitLinkType() throws JAXBException {
        // Prepare
        // Create a visitor
        RecordsCollection collection = new RecordsCollection();
        NMLVisitor visitor = new NMLVisitor(collection);
        TraversingVisitor tv = new TraversingVisitor(new DepthFirstTraverserImpl(), visitor);
        // tv.setTraverseFirst(true);
        String linkURN = "urn:ogf:network:example.net:2013:linkA:XY";

        // Prepare for by reading the example message
        InputStream in =
                getClass().getClassLoader().getResourceAsStream("xml-examples/example-message-link.xml");

        StreamSource ss = new StreamSource(in);
        JAXBContext context = JAXBContext.newInstance(jaxb_bindings);
        Unmarshaller um = context.createUnmarshaller();
        Message msg = (Message) um.unmarshal(ss);

        // Act
        // For some reason this doesn't work
        msg.getBody().accept(tv);

        // This is a work around that the visitor is not traversing elements in Body
        JAXBElement<LinkType> element = (JAXBElement<LinkType>) msg.getBody().getAny().get(0);
        Assert.assertNotNull(element.getValue());
        LinkType linkType = element.getValue();
        linkType.accept(tv);

        // Assert
        // FIXME (AH): this should be 1 link only
        Assert.assertEquals(6, collection.getLinks().size());
        Assert.assertTrue(collection.getLinks().containsKey(linkURN));
        Link record = collection.getLinks().get(linkURN);

        Assert.assertEquals(VLAN_URI, record.getEncoding());
        Assert.assertFalse(record.getNoReturnTraffic());
        Assert.assertEquals("1501", record.getLabel());
        Assert.assertEquals(VLAN_URI, record.getLabelType());

        Assert.assertEquals(1, record.getIsAlias().size());
        Assert.assertTrue(record.getIsAlias().contains("urn:ogf:network:example.net:2013:linkB"));

        Assert.assertEquals(1, record.getNext().size());
        Assert.assertTrue(record.getNext().contains("urn:ogf:network:example.net:2013:linkC"));

        Assert.assertEquals(3, record.getIsSerialCompoundLink().size());
        Assert.assertTrue(record.getIsSerialCompoundLink().contains("urn:ogf:network:example.net:2013:linkD"));
        Assert.assertTrue(record.getIsSerialCompoundLink().contains("urn:ogf:network:example.net:2013:linkE"));
        Assert.assertTrue(record.getIsSerialCompoundLink().contains("urn:ogf:network:example.net:2013:linkF"));
    }

    @Test
    public void testVisitTopologyType() throws JAXBException {
        // Prepare
        // Create a visitor
        RecordsCollection collection = new RecordsCollection();
        NMLVisitor visitor = new NMLVisitor(collection);
        TraversingVisitor tv = new TraversingVisitor(new DepthFirstTraverserImpl(), visitor);
        String topologyURN = "urn:ogf:network:example.net:2013:org";
        String portURNs[] = {"urn:ogf:network:example.net:2013:portA", "urn:ogf:network:example.net:2013:portB",
                "urn:ogf:network:example.net:2013:portC:in", "urn:ogf:network:example.net:2013:portC:out",
                "urn:ogf:network:example.net:2013:portD:in", "urn:ogf:network:example.net:2013:portD:out"};

        // Prepare for by reading the example message
        InputStream in =
                getClass().getClassLoader().getResourceAsStream("xml-examples/example-message-topology.xml");

        StreamSource ss = new StreamSource(in);
        JAXBContext context = JAXBContext.newInstance(jaxb_bindings);
        Unmarshaller um = context.createUnmarshaller();
        Message msg = (Message) um.unmarshal(ss);

        // Act
        // For some reason this doesn't work
        msg.getBody().accept(tv);

        JAXBElement<TopologyType> element = (JAXBElement<TopologyType>) msg.getBody().getAny().get(0);
        Assert.assertNotNull(element.getValue());
        TopologyType object = element.getValue();
        Assert.assertNotNull(object.getId());
        object.accept(tv);

        // Assert
        Map<String, Topology> topologyMap = collection.getTopologies();
        Map<String, Port> portMap = collection.getPorts();
        Map<String, Node> nodeMap = collection.getNodes();

        Assert.assertEquals(1, topologyMap.size());
        Assert.assertEquals(6, portMap.size());
        Assert.assertEquals(0, nodeMap.size());

        Assert.assertTrue(topologyMap.containsKey(topologyURN));
        Topology topology = topologyMap.get(topologyURN);

        for (String urn : portURNs) {
            Assert.assertTrue(portMap.containsKey(urn));
            Assert.assertTrue(topology.getPorts().contains(urn));
        }

        Assert.assertTrue(topology.getHasInboundPort().contains(portURNs[2]));
        Assert.assertTrue(topology.getHasInboundPort().contains(portURNs[4]));
        Assert.assertTrue(topology.getHasOutboundPort().contains(portURNs[3]));
        Assert.assertTrue(topology.getHasOutboundPort().contains(portURNs[5]));

        // TODO (AH): test for all other elements in Topology
    }

    @Test
    @Ignore
    public void testsLSSend() throws JAXBException {

        try {
            SimpleLS client = new SimpleLS("localhost", 8090);
            client.connect();
            RegistrationClient rclient = new RegistrationClient(client);
            rclient.setRecord(new Node());
            rclient.register();

            rclient.setRelativeUrl("lookup/nml-node/983770ed-d959-4cb2-8714-29790d1b7cea");
            Record r = rclient.getRecord();
            System.out.println("Got : " + r.getRecordType());

        } catch (LSClientException ex) {
            System.out.println("Client exception " + ex.getMessage());
        } catch (ParserException ex) {
            System.out.println("Parser exception " + ex.getMessage());
        }

    }
}
