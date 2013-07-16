package net.es.topology.common.converter.nml;

import net.es.lookup.client.RegistrationClient;
import net.es.lookup.client.SimpleLS;
import net.es.lookup.common.exception.LSClientException;
import net.es.lookup.common.exception.ParserException;
import net.es.lookup.records.Record;
import net.es.topology.common.records.ts.*;
import net.es.topology.common.records.ts.utils.RecordsCollection;
import net.es.topology.common.visitors.nml.DepthFirstTraverserImpl;
import net.es.topology.common.visitors.nml.TraversingVisitor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.ogf.schemas.nsi._2013._09.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

/**
 * Tests the NML Visitor
 * Loads messages from src/test/resources/xml-examples/*.xml
 * <p/>
 * Note: try to use arrange-act-assert testing pattern as mush as possible
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class NMLVisitorTest {
    public final static String jaxb_bindings = "org.ogf.schemas.nsi._2013._09.topology:org.ogf.schemas.nsi._2013._09.messaging:org.ogf.schemas.nml._2013._05.base";
    // Commonly used value in the tests
    private final String VLAN_URI = "http://schemas.ogf.org/nml/2013/05/ethernet#vlan";
    private final Logger logger = LoggerFactory.getLogger(NMLVisitorTest.class);
    // Used to assert object versions in tests
    private final String version = "2013-05-30T09:30:10Z";
    /**
     * this UUID changes for each test case
     */
    private String logGUID = null;

    @Before
    public void setup() {
        // Make sure that each test case has it's own ID to make it easier to trace.
        this.logGUID = UUID.randomUUID().toString();
    }

    /**
     * get the UUID of the current test case
     *
     * @return
     */
    public String getLogGUID() {
        return this.logGUID;
    }

    @Test
    public void testVisitNodeType() throws JAXBException {
        // Arrange
        // Prepare logger
        logger.debug("event=NMLVisitorTest.testVisitNodeType.start guid=" + getLogGUID());
        // Create a visitor
        RecordsCollection collection = new RecordsCollection(getLogGUID());
        NMLVisitor visitor = new NMLVisitor(collection, getLogGUID());
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
        msg.getBody().accept(tv);

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
        logger.debug("event=NMLVisitorTest.testVisitNodeType.end status=0 guid=" + getLogGUID());
        // TODO (AH): Test version
    }

    @Test
    public void testVisitLocationType() throws JAXBException {
        // Arrange
        // Prepare logger
        logger.debug("event=NMLVisitorTest.testVisitLocationType.start guid=" + getLogGUID());
        // Create a visitor
        RecordsCollection collection = new RecordsCollection(getLogGUID());
        NMLVisitor visitor = new NMLVisitor(collection, getLogGUID());
        TraversingVisitor tv = new TraversingVisitor(new DepthFirstTraverserImpl(), visitor);
        // tv.setTraverseFirst(true);

        // Prepare for by reading the example message
        InputStream in =
                getClass().getClassLoader().getResourceAsStream("xml-examples/example-message-location.xml");

        StreamSource ss = new StreamSource(in);
        JAXBContext context = JAXBContext.newInstance(jaxb_bindings);
        Unmarshaller um = context.createUnmarshaller();
        Message msg = (Message) um.unmarshal(ss);

        // Act
        msg.getBody().accept(tv);

        // Assert
        Assert.assertEquals(1, visitor.getRecordsCollection().getNodes().size());
        Assert.assertTrue(visitor.getRecordsCollection().getNodes().containsKey("urn:ogf:network:example.net:2013:nodeA"));
        Node node = visitor.getRecordsCollection().getNodes().get("urn:ogf:network:example.net:2013:nodeA");
        Location loc = node.getLocation();
        Assert.assertSame(node, loc.getNetworkObject());
        Assert.assertEquals("Red City", loc.getName());
        Assert.assertEquals("US", loc.getUnlocode());
        Assert.assertEquals("urn:ogf:network:example.net:2013:redcity", loc.getId());
        Assert.assertEquals(Float.valueOf(10.000f), loc.getAltitude());
        Assert.assertEquals(Float.valueOf(30.600f), loc.getLatitude());
        Assert.assertEquals(Float.valueOf(12.640f), loc.getLongitude());

        logger.debug("event=NMLVisitorTest.testVisitLocationType.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testVisitPortType() throws JAXBException {
        // Prepare
        // Prepare logger
        logger.debug("event=NMLVisitorTest.testVisitPortType.start guid=" + getLogGUID());
        // Create a visitor
        RecordsCollection collection = new RecordsCollection(getLogGUID());
        NMLVisitor visitor = new NMLVisitor(collection, getLogGUID());
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
        msg.getBody().accept(tv);

        // Assert
        Assert.assertEquals(1, collection.getPorts().size());
        Port ports[] = collection.getPorts().values().toArray(new Port[collection.getPorts().size()]);
        Port sLSPort = ports[0];
        Assert.assertNotNull(sLSPort);
        Assert.assertNotNull(sLSPort.getId());
        Assert.assertEquals("urn:ogf:network:example.net:2013:portA", sLSPort.getId());
        Assert.assertEquals(null, sLSPort.getName());
        Assert.assertEquals(this.version, sLSPort.getVersion());
        Assert.assertEquals("1501", sLSPort.getLabel());
        Assert.assertEquals(VLAN_URI, sLSPort.getLabelType());
        logger.debug("event=NMLVisitorTest.testVisitPortType.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testVisitPortGroupType() throws JAXBException {
        // Prepare
        // Prepare logger
        logger.debug("event=NMLVisitorTest.testVisitPortGroupType.start guid=" + getLogGUID());
        // Create a visitor
        RecordsCollection collection = new RecordsCollection(getLogGUID());
        NMLVisitor visitor = new NMLVisitor(collection, getLogGUID());
        TraversingVisitor tv = new TraversingVisitor(new DepthFirstTraverserImpl(), visitor);
        // tv.setTraverseFirst(true);

        // Prepare for by reading the example message
        InputStream in =
                getClass().getClassLoader().getResourceAsStream("xml-examples/example-message-port-group.xml");

        StreamSource ss = new StreamSource(in);
        JAXBContext context = JAXBContext.newInstance(jaxb_bindings);
        Unmarshaller um = context.createUnmarshaller();
        Message msg = (Message) um.unmarshal(ss);

        // Act
        msg.getBody().accept(tv);

        // Assert
        Assert.assertEquals(3, collection.getPortGroups().size());
        PortGroup ports[] = collection.getPortGroups().values().toArray(new PortGroup[collection.getPortGroups().size()]);
        PortGroup sLSPortGroup = ports[0];
        Assert.assertNotNull(sLSPortGroup);
        Assert.assertNotNull(sLSPortGroup.getId());
        Assert.assertEquals("urn:ogf:network:example.net:2013:portGroup", sLSPortGroup.getId());
        Assert.assertEquals(null, sLSPortGroup.getName());
        Assert.assertEquals(this.version, sLSPortGroup.getVersion());
        Assert.assertEquals(VLAN_URI, sLSPortGroup.getEncoding());

        Assert.assertEquals(2, sLSPortGroup.getLabelGroup().getLabels().size());
        LabelGroup labelGroup = sLSPortGroup.getLabelGroup();
        Assert.assertEquals(VLAN_URI, labelGroup.getLabel(0).get(0));
        Assert.assertEquals("1780-1783", labelGroup.getLabel(0).get(1));
        Assert.assertEquals(VLAN_URI, labelGroup.getLabel(1).get(0));
        Assert.assertEquals("1880-1883", labelGroup.getLabel(1).get(1));

        Assert.assertEquals(2, sLSPortGroup.getPorts().size());
        Assert.assertTrue(sLSPortGroup.getPorts().contains("urn:ogf:network:example.net:2013:portA"));
        Assert.assertTrue(sLSPortGroup.getPorts().contains("urn:ogf:network:example.net:2013:portB"));

        Assert.assertEquals(1, sLSPortGroup.getPortGroups().size());
        Assert.assertTrue(sLSPortGroup.getPortGroups().contains("urn:ogf:network:example.net:2013:portGroupC"));

        Assert.assertEquals(1, sLSPortGroup.getIsSink().size());
        Assert.assertTrue(sLSPortGroup.getIsSink().contains("urn:ogf:network:example.net:2013:linkA"));

        Assert.assertEquals(1, sLSPortGroup.getIsSource().size());
        Assert.assertTrue(sLSPortGroup.getIsSource().contains("urn:ogf:network:example.net:2013:linkB"));

        Assert.assertEquals(1, sLSPortGroup.getIsAlias().size());
        Assert.assertTrue(sLSPortGroup.getIsAlias().contains("urn:ogf:network:example.net:2013:portGroupB"));
        logger.debug("event=NMLVisitorTest.testVisitPortGroupType.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testVisitLinkType() throws JAXBException {
        // Prepare
        // Prepare logger
        logger.debug("event=NMLVisitorTest.testVisitLinkType.start guid=" + getLogGUID());
        // Create a visitor
        RecordsCollection collection = new RecordsCollection(getLogGUID());
        NMLVisitor visitor = new NMLVisitor(collection, getLogGUID());
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
        msg.getBody().accept(tv);

        // Assert
        Assert.assertEquals(6, collection.getLinks().size());
        Assert.assertTrue(collection.getLinks().containsKey(linkURN));
        Link record = collection.getLinks().get(linkURN);

        Assert.assertEquals(VLAN_URI, record.getEncoding());
        Assert.assertFalse(record.getNoReturnTraffic());
        Assert.assertEquals(this.version, record.getVersion());
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
        logger.debug("event=NMLVisitorTest.testVisitLinkType.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testVisitLinkGroupType() throws JAXBException {
        // Prepare
        // Prepare logger
        logger.debug("event=NMLVisitorTest.testVisitLinkGroupType.start guid=" + getLogGUID());
        // Create a visitor
        RecordsCollection collection = new RecordsCollection(getLogGUID());
        NMLVisitor visitor = new NMLVisitor(collection, getLogGUID());
        TraversingVisitor tv = new TraversingVisitor(new DepthFirstTraverserImpl(), visitor);
        // tv.setTraverseFirst(true);

        // Prepare for by reading the example message
        InputStream in =
                getClass().getClassLoader().getResourceAsStream("xml-examples/example-message-link-group.xml");

        StreamSource ss = new StreamSource(in);
        JAXBContext context = JAXBContext.newInstance(jaxb_bindings);
        Unmarshaller um = context.createUnmarshaller();
        Message msg = (Message) um.unmarshal(ss);

        // Act
        msg.getBody().accept(tv);

        // Assert
        // TODO (AH): this should assert only one port group is in there
        Assert.assertEquals(4, collection.getLinkGroups().size());
        Assert.assertTrue(collection.getLinkGroups().containsKey("urn:ogf:network:example.net:2013:linkGroup"));
        LinkGroup sLSLinkGroup = collection.getLinkGroups().get("urn:ogf:network:example.net:2013:linkGroup");
        Assert.assertNotNull(sLSLinkGroup);
        Assert.assertNotNull(sLSLinkGroup.getId());
        Assert.assertEquals(this.version, sLSLinkGroup.getVersion());
        Assert.assertEquals("urn:ogf:network:example.net:2013:linkGroup", sLSLinkGroup.getId());
        Assert.assertEquals(null, sLSLinkGroup.getName());

        Assert.assertEquals(2, sLSLinkGroup.getLabelGroup().getLabels().size());
        LabelGroup labelGroup = sLSLinkGroup.getLabelGroup();
        Assert.assertEquals(VLAN_URI, labelGroup.getLabel(0).get(0));
        Assert.assertEquals("1780-1783", labelGroup.getLabel(0).get(1));
        Assert.assertEquals(VLAN_URI, labelGroup.getLabel(1).get(0));
        Assert.assertEquals("1880-1883", labelGroup.getLabel(1).get(1));

        Assert.assertEquals(2, sLSLinkGroup.getLinks().size());
        Assert.assertTrue(sLSLinkGroup.getLinks().contains("urn:ogf:network:example.net:2013:linkA:XY"));
        Assert.assertTrue(sLSLinkGroup.getLinks().contains("urn:ogf:network:example.net:2013:linkA:YX"));

        Assert.assertEquals(1, sLSLinkGroup.getLinkGroups().size());
        Assert.assertTrue(sLSLinkGroup.getLinkGroups().contains("urn:ogf:network:example.net:2013:linkGroupB"));

        Assert.assertEquals(1, sLSLinkGroup.getIsSerialCompoundLink().size());
        Assert.assertTrue(sLSLinkGroup.getIsSerialCompoundLink().contains("urn:ogf:network:example.net:2013:linkA:YX"));

        Assert.assertEquals(1, sLSLinkGroup.getIsAlias().size());
        Assert.assertTrue(sLSLinkGroup.getIsAlias().contains("urn:ogf:network:example.net:2013:linkGroupC"));
        logger.debug("event=NMLVisitorTest.testVisitLinkGroupType.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testVisitTopologyType() throws JAXBException {
        // Prepare
        // Prepare logger
        logger.debug("event=NMLVisitorTest.testVisitTopologyType.start guid=" + getLogGUID());
        // Create a visitor
        RecordsCollection collection = new RecordsCollection(getLogGUID());
        NMLVisitor visitor = new NMLVisitor(collection, getLogGUID());
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
        msg.getBody().accept(tv);

        // Assert
        Map<String, Topology> topologyMap = collection.getTopologies();
        Map<String, Port> portMap = collection.getPorts();
        Map<String, Node> nodeMap = collection.getNodes();

        Assert.assertEquals(2, topologyMap.size());
        Assert.assertEquals(6, portMap.size());
        Assert.assertEquals(1, nodeMap.size());

        Assert.assertTrue(topologyMap.containsKey(topologyURN));
        Topology topology = topologyMap.get(topologyURN);
        Assert.assertEquals(this.version, topology.getVersion());

        Assert.assertTrue(topology.getNodes().contains("urn:ogf:network:example.net:2013:nodeA"));

        for (String urn : portURNs) {
            Assert.assertTrue(portMap.containsKey(urn));
            Assert.assertTrue(topology.getPorts().contains(urn));
        }

        Assert.assertTrue(topology.getHasInboundPort().contains(portURNs[2]));
        Assert.assertTrue(topology.getHasInboundPort().contains(portURNs[4]));
        Assert.assertTrue(topology.getHasOutboundPort().contains(portURNs[3]));
        Assert.assertTrue(topology.getHasOutboundPort().contains(portURNs[5]));

        Assert.assertEquals(2, topology.getBidirectionalPorts().size());
        Assert.assertTrue(topology.getBidirectionalPorts().contains("urn:ogf:network:example.net:2013:portC"));
        Assert.assertTrue(topology.getBidirectionalPorts().contains("urn:ogf:network:example.net:2013:portC"));

        Assert.assertEquals(1, topology.getBidirectionalLinks().size());
        Assert.assertTrue(topology.getBidirectionalLinks().contains("urn:ogf:network:example.net:2013:linkBidir"));

        Assert.assertTrue(topology.getPortGroups().contains("urn:ogf:network:example.net:2013:portGroupA"));
        Assert.assertTrue(topology.getLinkGroups().contains("urn:ogf:network:example.net:2013:linkGroupA"));
        Assert.assertTrue(topology.getTopologies().contains("urn:ogf:network:example.net:2013:topologyA"));
        // TODO (AH): test for all other elements in Topology
        logger.debug("event=NMLVisitorTest.testVisitTopologyType.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testVisitBidirectionalPortType() throws JAXBException {
        // Prepare
        // Prepare logger
        logger.debug("event=NMLVisitorTest.testVisitBidirectionalPortType.start guid=" + getLogGUID());
        // Create a visitor
        RecordsCollection collection = new RecordsCollection(getLogGUID());
        NMLVisitor visitor = new NMLVisitor(collection, getLogGUID());
        TraversingVisitor tv = new TraversingVisitor(new DepthFirstTraverserImpl(), visitor);
        // tv.setTraverseFirst(true);

        // Prepare for by reading the example message
        InputStream in =
                getClass().getClassLoader().getResourceAsStream("xml-examples/example-message-bidirectional-port.xml");

        StreamSource ss = new StreamSource(in);
        JAXBContext context = JAXBContext.newInstance(jaxb_bindings);
        Unmarshaller um = context.createUnmarshaller();
        Message msg = (Message) um.unmarshal(ss);

        // Act
        msg.getBody().accept(tv);

        // Assert
        Assert.assertEquals(1, collection.getBidirectionalPorts().size());
        BidirectionalPort ports[] = collection.getBidirectionalPorts().values().toArray(new BidirectionalPort[collection.getBidirectionalPorts().size()]);
        BidirectionalPort sLSBiPort = ports[0];
        Assert.assertNotNull(sLSBiPort);
        Assert.assertNotNull(sLSBiPort.getId());
        Assert.assertEquals("urn:ogf:network:example.net:2013:portA", sLSBiPort.getId());
        Assert.assertEquals("PortA", sLSBiPort.getName());
        Assert.assertEquals(this.version, sLSBiPort.getVersion());


        Assert.assertNull(sLSBiPort.getPortGroups());
        Assert.assertEquals(2, sLSBiPort.getPorts().size());
        Assert.assertTrue(sLSBiPort.getPorts().contains("urn:ogf:network:example.net:2013:portA:out"));
        Assert.assertTrue(sLSBiPort.getPorts().contains("urn:ogf:network:example.net:2013:portA:in"));
        logger.debug("event=NMLVisitorTest.testVisitBidirectionalPortType.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testVisitBidirectionalLinkType() throws JAXBException {
        // Prepare
        // Prepare logger
        logger.debug("event=NMLVisitorTest.testVisitBidirectionalLinkType.start guid=" + getLogGUID());
        // Create a visitor
        RecordsCollection collection = new RecordsCollection(getLogGUID());
        NMLVisitor visitor = new NMLVisitor(collection, getLogGUID());
        TraversingVisitor tv = new TraversingVisitor(new DepthFirstTraverserImpl(), visitor);
        // tv.setTraverseFirst(true);

        // Prepare for by reading the example message
        InputStream in =
                getClass().getClassLoader().getResourceAsStream("xml-examples/example-message-bidirectional-link.xml");

        StreamSource ss = new StreamSource(in);
        JAXBContext context = JAXBContext.newInstance(jaxb_bindings);
        Unmarshaller um = context.createUnmarshaller();
        Message msg = (Message) um.unmarshal(ss);

        // Act
        msg.getBody().accept(tv);

        // Assert
        Assert.assertEquals(1, collection.getBidirectionalLinks().size());
        BidirectionalLink links[] = collection.getBidirectionalLinks().values().toArray(new BidirectionalLink[collection.getBidirectionalLinks().size()]);
        BidirectionalLink sLSBiLink = links[0];
        Assert.assertNotNull(sLSBiLink);
        Assert.assertNotNull(sLSBiLink.getId());
        Assert.assertEquals("urn:ogf:network:example.net:2013:linkA", sLSBiLink.getId());
        Assert.assertEquals("LinkA", sLSBiLink.getName());
        Assert.assertEquals(this.version, sLSBiLink.getVersion());


        Assert.assertNull(sLSBiLink.getLinkGroups());
        Assert.assertNotNull(sLSBiLink.getLinks());
        Assert.assertEquals(2, sLSBiLink.getLinks().size());
        Assert.assertTrue(sLSBiLink.getLinks().contains("urn:ogf:network:example.net:2013:linkA:XY"));
        Assert.assertTrue(sLSBiLink.getLinks().contains("urn:ogf:network:example.net:2013:linkA:YX"));
        logger.debug("event=NMLVisitorTest.testVisitBidirectionalLinkType.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testVisitSwitchingServiceType() throws JAXBException {
        // Prepare
        // Prepare logger
        logger.debug("event=NMLVisitorTest.testVisitSwitchingServiceType.start guid=" + getLogGUID());
        // Create a visitor
        RecordsCollection collection = new RecordsCollection(getLogGUID());
        NMLVisitor visitor = new NMLVisitor(collection, getLogGUID());
        TraversingVisitor tv = new TraversingVisitor(new DepthFirstTraverserImpl(), visitor);
        // tv.setTraverseFirst(true);

        // Prepare for by reading the example message
        InputStream in =
                getClass().getClassLoader().getResourceAsStream("xml-examples/example-message-switching-service.xml");

        StreamSource ss = new StreamSource(in);
        JAXBContext context = JAXBContext.newInstance(jaxb_bindings);
        Unmarshaller um = context.createUnmarshaller();
        Message msg = (Message) um.unmarshal(ss);

        // Act
        msg.getBody().accept(tv);

        // Assert
        String urn = "urn:ogf:network:example.net:2013:switchingServiceA";
        Assert.assertEquals(2, collection.getSwitchingServices().size());
        Assert.assertTrue(collection.getSwitchingServices().containsKey(urn));
        SwitchingService slsSwitchingService = collection.getSwitchingServices().get(urn);

        Assert.assertEquals(urn, slsSwitchingService.getId());
        Assert.assertEquals("SwitchingServiceA", slsSwitchingService.getName());
        Assert.assertEquals(this.version, slsSwitchingService.getVersion());
        Assert.assertEquals(true, slsSwitchingService.getLabelSwaping());
        Assert.assertEquals(VLAN_URI, slsSwitchingService.getEncoding());

        Assert.assertEquals(2, slsSwitchingService.getHasInboundPort().size());
        Assert.assertEquals(2, slsSwitchingService.getHasOutboundPort().size());
        Assert.assertEquals(1, slsSwitchingService.getProvidesLink().size());
        Assert.assertEquals(1, slsSwitchingService.getIsAlias().size());

        logger.debug("event=NMLVisitorTest.testVisitSwitchingServiceType.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testVisitNSAType() throws JAXBException {
        // Prepare
        // Prepare logger
        logger.debug("event=NMLVisitorTest.testVisitNSAType.start guid=" + getLogGUID());
        // Create a visitor
        RecordsCollection collection = new RecordsCollection(getLogGUID());
        NMLVisitor visitor = new NMLVisitor(collection, getLogGUID());
        TraversingVisitor tv = new TraversingVisitor(new DepthFirstTraverserImpl(), visitor);
        // tv.setTraverseFirst(true);

        // Prepare for by reading the example message
        InputStream in =
                getClass().getClassLoader().getResourceAsStream("xml-examples/example-message-nsa.xml");

        StreamSource ss = new StreamSource(in);
        JAXBContext context = JAXBContext.newInstance(jaxb_bindings);
        Unmarshaller um = context.createUnmarshaller();
        Message msg = (Message) um.unmarshal(ss);

        // Act
        msg.getBody().accept(tv);

        // Assert
        Assert.assertEquals(3, collection.getNSAs().size());
        Assert.assertTrue(collection.getNSAs().containsKey("urn:ogf:network:example.org:2013:nsa"));
        Assert.assertTrue(collection.getNSAs().containsKey("urn:ogf:network:example.com:2013:nsa"));
        Assert.assertTrue(collection.getNSAs().containsKey("urn:ogf:network:example.net:2013:nsa"));
        NSA nsa = collection.getNSAs().get("urn:ogf:network:example.org:2013:nsa");
        Assert.assertEquals("urn:ogf:network:example.org:2013:nsa", nsa.getId());
        Assert.assertNull(nsa.getName());
        Assert.assertEquals(this.version, nsa.getVersion());


        Assert.assertEquals(1, nsa.getTopologies().size());
        Assert.assertTrue(nsa.getTopologies().contains("urn:ogf:network:example.org:2013:topology"));

        Assert.assertEquals(1, nsa.getPeersWith().size());
        Assert.assertTrue(nsa.getPeersWith().contains("urn:ogf:network:example.org:2013:nsa"));

        Assert.assertEquals(1, nsa.getManagedBy().size());
        Assert.assertTrue(nsa.getManagedBy().contains("urn:ogf:network:example.net:2013:nsa"));

        Location loc = nsa.getLocation();
        Assert.assertSame(nsa, loc.getNetworkObject());
        Assert.assertEquals("Red City", loc.getName());
        Assert.assertEquals("US", loc.getUnlocode());
        Assert.assertEquals("urn:ogf:network:example.net:2013:redcity", loc.getId());
        Assert.assertEquals(Float.valueOf(10.000f), loc.getAltitude());
        Assert.assertEquals(Float.valueOf(30.600f), loc.getLatitude());
        Assert.assertEquals(Float.valueOf(12.640f), loc.getLongitude());
        logger.debug("event=NMLVisitorTest.testVisitNSAType.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testVisitNSIServiceType() throws JAXBException {
        // Prepare
        // Prepare logger
        logger.debug("event=NMLVisitorTest.testVisitNSIServiceType.start guid=" + getLogGUID());
        // Create a visitor
        RecordsCollection collection = new RecordsCollection(getLogGUID());
        NMLVisitor visitor = new NMLVisitor(collection, getLogGUID());
        TraversingVisitor tv = new TraversingVisitor(new DepthFirstTraverserImpl(), visitor);
        // tv.setTraverseFirst(true);

        // Prepare for by reading the example message
        InputStream in =
                getClass().getClassLoader().getResourceAsStream("xml-examples/example-message-nsi-service.xml");

        StreamSource ss = new StreamSource(in);
        JAXBContext context = JAXBContext.newInstance(jaxb_bindings);
        Unmarshaller um = context.createUnmarshaller();
        Message msg = (Message) um.unmarshal(ss);

        // Act
        msg.getBody().accept(tv);

        // Assert
        Assert.assertEquals(1, collection.getNSIServices().size());
        Assert.assertTrue(collection.getNSIServices().containsKey("urn:ogf:network:example.com:2013:nsa-provserv"));

        Assert.assertEquals(1, collection.getNSAs().size());
        Assert.assertTrue(collection.getNSAs().containsKey("urn:ogf:network:example.com:2013:nsa"));

        NSIService nsiService = collection.getNSIServices().get("urn:ogf:network:example.com:2013:nsa-provserv");
        Assert.assertEquals("urn:ogf:network:example.com:2013:nsa-provserv", nsiService.getId());
        Assert.assertNull(nsiService.getName());
        Assert.assertEquals(this.version, nsiService.getVersion());


        Assert.assertEquals("http://nsa.example.com/provisioning/wsdl", nsiService.getDescribedBy());
        Assert.assertEquals("application/vnd.org.ogf.nsi.cs.v2+soap", nsiService.getType());
        Assert.assertEquals("http://nsa.example.com/provisioning", nsiService.getLink());

        Assert.assertEquals(1, nsiService.getProvidedBy().size());
        Assert.assertTrue(nsiService.getProvidedBy().contains("urn:ogf:network:example.com:2013:nsa"));
        logger.debug("event=NMLVisitorTest.testVisitNSIServiceType.end status=0 guid=" + getLogGUID());
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
