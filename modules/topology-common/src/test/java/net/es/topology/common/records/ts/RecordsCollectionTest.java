package net.es.topology.common.records.ts;

import net.es.lookup.client.RegistrationClient;
import net.es.lookup.client.SimpleLS;
import net.es.topology.common.config.sls.JsonClientProvider;
import net.es.topology.common.converter.nml.NMLVisitor;
import net.es.topology.common.records.ts.utils.RecordsCollection;
import net.es.topology.common.records.ts.utils.SLSRegistrationClientDispatcherImpl;
import net.es.topology.common.records.ts.utils.URNMaskGetAllImpl;
import net.es.topology.common.visitors.nml.DepthFirstTraverserImpl;
import net.es.topology.common.visitors.nml.TraversingVisitor;
import org.junit.*;
import org.ogf.schemas.nsi._2013._09.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class RecordsCollectionTest {
    public final static String jaxb_bindings = "org.ogf.schemas.nsi._2013._09.topology:org.ogf.schemas.nsi._2013._09.messaging:org.ogf.schemas.nml._2013._05.base";
    private final Logger logger = LoggerFactory.getLogger(RecordsCollectionTest.class);
    private final String sLSConfigFile = getClass().getClassLoader().getResource("config/sls.json").getFile();
    private RecordsCollection collection;
    /**
     * this UUID changes for each test case
     */
    private String logGUID = null;

    @Before
    public void createCollection() {
        // Make sure that each test case has it's own ID to make it easier to trace.
        this.logGUID = UUID.randomUUID().toString();
        this.collection = new RecordsCollection(getLogGUID());
    }

    /**
     * get the UUID of the current test case
     *
     * @return
     */
    public String getLogGUID() {
        return this.logGUID;
    }

    @After
    public void destroyCollection() {
        this.collection = null;
    }

    @Test
    public void testGetNodes() throws Exception {
        logger.debug("event=RecordsCollectionTest.testGetNodes.start guid=" + getLogGUID());
        Assert.assertNotNull(this.collection.getNodes());
        logger.debug("event=RecordsCollectionTest.testGetNodes.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testSetNodes() throws Exception {
        // Arrange
        logger.debug("event=RecordsCollectionTest.testSetNodes.start guid=" + getLogGUID());
        Map<String, Node> nodes = new HashMap<String, Node>();

        // Act
        this.collection.setNodes(nodes);

        // Assert
        Assert.assertSame(nodes, this.collection.getNodes());
        logger.debug("event=RecordsCollectionTest.testSetNodes.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testGetPorts() throws Exception {
        logger.debug("event=RecordsCollectionTest.testGetPorts.start guid=" + getLogGUID());
        Assert.assertNotNull(this.collection.getPorts());
        logger.debug("event=RecordsCollectionTest.testGetPorts.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testSetPorts() throws Exception {
        // Arrange
        logger.debug("event=RecordsCollectionTest.testSetPorts.start guid=" + getLogGUID());
        Map<String, Port> ports = new HashMap<String, Port>();

        // Act
        this.collection.setPorts(ports);

        // Assert
        Assert.assertSame(ports, this.collection.getPorts());
        logger.debug("event=RecordsCollectionTest.testSetPorts.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testGetLinks() throws Exception {
        logger.debug("event=RecordsCollectionTest.testGetLinks.start guid=" + getLogGUID());
        Assert.assertNotNull(this.collection.getLinks());
        logger.debug("event=RecordsCollectionTest.testGetLinks.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testSetLinks() throws Exception {
        // Arrange
        logger.debug("event=RecordsCollectionTest.testSetLinks.start guid=" + getLogGUID());
        Map<String, Link> links = new HashMap<String, Link>();

        // Act
        this.collection.setLinks(links);

        // Assert
        Assert.assertSame(links, this.collection.getLinks());
        logger.debug("event=RecordsCollectionTest.testSetLinks.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testGetTopologies() throws Exception {
        logger.debug("event=RecordsCollectionTest.testGetTopologies.start guid=" + getLogGUID());
        Assert.assertNotNull(this.collection.getTopologies());
        logger.debug("event=RecordsCollectionTest.testGetTopologies.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testSetTopologies() throws Exception {
        // Arrange
        logger.debug("event=RecordsCollectionTest.testSetTopologies.start guid=" + getLogGUID());
        Map<String, Topology> topologies = new HashMap<String, Topology>();

        // Act
        this.collection.setTopologies(topologies);

        // Assert
        Assert.assertSame(topologies, this.collection.getTopologies());
        logger.debug("event=RecordsCollectionTest.testSetTopologies.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testGetBidirectionalPorts() throws Exception {
        logger.debug("event=RecordsCollectionTest.testGetBidirectionalPorts.start guid=" + getLogGUID());
        Assert.assertNotNull(this.collection.getBidirectionalPorts());
        logger.debug("event=RecordsCollectionTest.testGetBidirectionalPorts.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testSetBidirectionalPorts() throws Exception {
        // Arrange
        logger.debug("event=RecordsCollectionTest.testSetBidirectionalPorts.start guid=" + getLogGUID());
        Map<String, BidirectionalPort> biports = new HashMap<String, BidirectionalPort>();

        // Act
        this.collection.setBidirectionalPorts(biports);

        // Assert
        Assert.assertSame(biports, this.collection.getBidirectionalPorts());
        logger.debug("event=RecordsCollectionTest.testSetBidirectionalPorts.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testGetBidirectionalLinks() throws Exception {
        logger.debug("event=RecordsCollectionTest.testGetBidirectionalLinks.start guid=" + getLogGUID());
        Assert.assertNotNull(this.collection.getBidirectionalLinks());
        logger.debug("event=RecordsCollectionTest.testGetBidirectionalLinks.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testSetBidirectionalLinks() throws Exception {
        // Arrange
        logger.debug("event=RecordsCollectionTest.testSetBidirectionalLinks.start guid=" + getLogGUID());
        Map<String, BidirectionalLink> biLinks = new HashMap<String, BidirectionalLink>();

        // Act
        this.collection.setBidirectionalLinks(biLinks);

        // Assert
        Assert.assertSame(biLinks, this.collection.getBidirectionalLinks());
        logger.debug("event=RecordsCollectionTest.testSetBidirectionalLinks.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testGetPortGroups() throws Exception {
        logger.debug("event=RecordsCollectionTest.testGetPortGroups.start guid=" + getLogGUID());
        Assert.assertNotNull(this.collection.getPortGroups());
        logger.debug("event=RecordsCollectionTest.testGetPortGroups.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testSetPortGroups() throws Exception {
        // Arrange
        logger.debug("event=RecordsCollectionTest.testSetPortGroups.start guid=" + getLogGUID());
        Map<String, PortGroup> ports = new HashMap<String, PortGroup>();

        // Act
        this.collection.setPortGroups(ports);

        // Assert
        Assert.assertSame(ports, this.collection.getPortGroups());
        logger.debug("event=RecordsCollectionTest.testSetPortGroups.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testGetNSAs() throws Exception {
        logger.debug("event=RecordsCollectionTest.testGetNSAs.start guid=" + getLogGUID());
        Assert.assertNotNull(this.collection.getNSAs());
        logger.debug("event=RecordsCollectionTest.testGetNSAs.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testSetNSAs() throws Exception {
        // Arrange
        logger.debug("event=RecordsCollectionTest.testSetNSAs.start guid=" + getLogGUID());
        Map<String, NSA> nsas = new HashMap<String, NSA>();

        // Act
        this.collection.setNSAs(nsas);

        // Assert
        Assert.assertSame(nsas, this.collection.getNSAs());
        logger.debug("event=RecordsCollectionTest.testSetNSAs.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testGetNSIService() throws Exception {
        logger.debug("event=RecordsCollectionTest.testGetNSIService.start guid=" + getLogGUID());
        Assert.assertNotNull(this.collection.getNSIServices());
        logger.debug("event=RecordsCollectionTest.testGetNSIService.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testSetNSIService() throws Exception {
        // Arrange
        logger.debug("event=RecordsCollectionTest.testSetNSIService.start guid=" + getLogGUID());
        Map<String, NSIService> services = new HashMap<String, NSIService>();

        // Act
        this.collection.setNSIServices(services);

        // Assert
        Assert.assertSame(services, this.collection.getNSIServices());
        logger.debug("event=RecordsCollectionTest.testSetNSIService.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testPortInstance() throws Exception {
        // Arrange
        logger.debug("event=RecordsCollectionTest.testPortInstance.start guid=" + getLogGUID());
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
        logger.debug("event=RecordsCollectionTest.testPortInstance.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testLinkInstance() throws Exception {
        // Arrange
        logger.debug("event=RecordsCollectionTest.testLinkInstance.start guid=" + getLogGUID());
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
        logger.debug("event=RecordsCollectionTest.testLinkInstance.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testNodeInstance() throws Exception {
        // Arrange
        logger.debug("event=RecordsCollectionTest.testNodeInstance.start guid=" + getLogGUID());
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
        logger.debug("event=RecordsCollectionTest.testNodeInstance.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testTopologyInstance() throws Exception {
        // Arrange
        logger.debug("event=RecordsCollectionTest.testTopologyInstance.start guid=" + getLogGUID());
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
        logger.debug("event=RecordsCollectionTest.testTopologyInstance.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testBidirectionalPortInstance() throws Exception {
        // Arrange
        logger.debug("event=RecordsCollectionTest.testBidirectionalPortInstance.start guid=" + getLogGUID());
        String id1 = "urn:ogf:network:example.net:2013:biportA";
        String id2 = "urn:ogf:network:example.net:2013:biportB";
        BidirectionalPort biportA = new BidirectionalPort();
        biportA.setId(id1);
        this.collection.getBidirectionalPorts().put(id1, biportA);

        // Act
        logger.debug("event=RecordsCollectionTest.testBidirectionalPortInstance.start guid=" + getLogGUID());
        BidirectionalPort biportA2 = this.collection.bidirectionalPortInstance(id1);
        BidirectionalPort biportB = this.collection.bidirectionalPortInstance(id2);
        BidirectionalPort biportNull = this.collection.bidirectionalPortInstance(null);

        // Assert
        Assert.assertSame(biportA, biportA2);
        Assert.assertSame(biportB, this.collection.bidirectionalPortInstance(id2));
        Assert.assertSame(biportNull, this.collection.bidirectionalPortInstance(biportNull.getId()));
        logger.debug("event=RecordsCollectionTest.testBidirectionalPortInstance.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testBidirectionalLinkInstance() throws Exception {
        // Arrange
        logger.debug("event=RecordsCollectionTest.testBidirectionalLinkInstance.start guid=" + getLogGUID());
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
        logger.debug("event=RecordsCollectionTest.testBidirectionalLinkInstance.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testPortGroupInstance() throws Exception {
        // Arrange
        logger.debug("event=RecordsCollectionTest.testPortGroupInstance.start guid=" + getLogGUID());
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
        logger.debug("event=RecordsCollectionTest.testPortGroupInstance.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testLinkGroupInstance() throws Exception {
        // Arrange
        logger.debug("event=RecordsCollectionTest.testLinkGroupInstance.start guid=" + getLogGUID());
        String id1 = "urn:ogf:network:example.net:2013:linkA";
        String id2 = "urn:ogf:network:example.net:2013:linkB";
        LinkGroup linkA = new LinkGroup();
        linkA.setId(id1);
        this.collection.getLinkGroups().put(id1, linkA);

        // Act
        LinkGroup linkA2 = this.collection.linkGroupInstance(id1);
        LinkGroup linkB = this.collection.linkGroupInstance(id2);
        LinkGroup linkNull = this.collection.linkGroupInstance(null);

        // Assert
        Assert.assertSame(linkA, linkA2);
        Assert.assertSame(linkB, this.collection.linkGroupInstance(id2));
        Assert.assertSame(linkNull, this.collection.linkGroupInstance(linkNull.getId()));
        logger.debug("event=RecordsCollectionTest.testLinkGroupInstance.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testNSAInstance() throws Exception {
        // Arrange
        logger.debug("event=RecordsCollectionTest.testNSAInstance.start guid=" + getLogGUID());
        String id1 = "urn:ogf:network:example.net:2013:nsaA";
        String id2 = "urn:ogf:network:example.net:2013:nsaB";
        NSA nsaA = new NSA();
        nsaA.setId(id1);
        this.collection.getNSAs().put(id1, nsaA);

        // Act
        NSA nsaA2 = this.collection.NSAInstance(id1);
        NSA nsaB = this.collection.NSAInstance(id2);
        NSA nsaNull = this.collection.NSAInstance(null);

        // Assert
        Assert.assertSame(nsaA, nsaA2);
        Assert.assertSame(nsaB, this.collection.NSAInstance(id2));
        Assert.assertSame(nsaNull, this.collection.NSAInstance(nsaNull.getId()));
        logger.debug("event=RecordsCollectionTest.testNSAInstance.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testNSIServiceInstance() throws Exception {
        // Arrange
        logger.debug("event=RecordsCollectionTest.testNSIServiceInstance.start guid=" + getLogGUID());
        String id1 = "urn:ogf:network:example.net:2013:serviceA";
        String id2 = "urn:ogf:network:example.net:2013:serviceB";
        NSIService serviceA = new NSIService();
        serviceA.setId(id1);
        this.collection.getNSIServices().put(id1, serviceA);

        // Act
        NSIService serviceA2 = this.collection.NSIServiceInstance(id1);
        NSIService serviceB = this.collection.NSIServiceInstance(id2);
        NSIService serviceNull = this.collection.NSIServiceInstance(null);

        // Assert
        Assert.assertSame(serviceA, serviceA2);
        Assert.assertSame(serviceB, this.collection.NSIServiceInstance(id2));
        Assert.assertSame(serviceNull, this.collection.NSIServiceInstance(serviceNull.getId()));
        logger.debug("event=RecordsCollectionTest.testNSIServiceInstance.end status=0 guid=" + getLogGUID());
    }

    @Test
    @Ignore
    public void testSendTosLS() throws Exception {
        // Arrange
        logger.debug("event=RecordsCollectionTest.testSendTosLS.start guid=" + getLogGUID());
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

        // Load sLS client
        JsonClientProvider sLSConfig = new JsonClientProvider(getLogGUID());
        sLSConfig.setFilename(sLSConfigFile);
        SimpleLS client = sLSConfig.getClient();

        // Prepare client
        client.connect();
        RegistrationClient rclient = new RegistrationClient(client);

        // Act
        msg.getBody().accept(tv);
        visitor.getRecordsCollection().sendTosLS(new SLSRegistrationClientDispatcherImpl(rclient), new URNMaskGetAllImpl());

        // TODO test the registered records
        logger.debug("event=RecordsCollectionTest.testSendTosLS.end status=0 guid=" + getLogGUID());
    }
}
