package net.es.topology.common.converter.nml;

import net.es.lookup.client.RegistrationClient;
import net.es.lookup.client.SimpleLS;
import net.es.lookup.common.exception.LSClientException;
import net.es.lookup.common.exception.ParserException;
import net.es.lookup.records.Record;
import net.es.topology.common.records.nml.Node;
import net.es.topology.common.records.nml.Port;
import net.es.topology.common.visitors.DepthFirstTraverserImpl;
import net.es.topology.common.visitors.TraversingVisitor;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
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

    @Test
    public void testVisitNodeType() throws JAXBException {
        // Arrange
        // Create a visitor
        NMLVisitor visitor = new NMLVisitor();
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
        Assert.assertEquals(1, visitor.getNodes().size());
        Node record = visitor.getNodes().get(0);
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

        NMLVisitor visitor = new NMLVisitor();
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
        NMLVisitor visitor = new NMLVisitor();
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
        Assert.assertEquals(1, visitor.getPorts().size());
        Port sLSPort = visitor.getPorts().get(0);
        Assert.assertEquals("urn:ogf:network:example.net:2013:portA", sLSPort.getId());
        Assert.assertEquals(null, sLSPort.getName());
        Assert.assertEquals("1501", sLSPort.getLabel());
        Assert.assertEquals("http://schemas.ogf.org/nml/2013/05/ethernet#vlan", sLSPort.getLabelType());
    }

    @Test
    public void testVisitTopologyType() throws JAXBException {
        InputStream in =
                getClass().getClassLoader().getResourceAsStream("xml-examples/example-message-topology.xml");

        StreamSource ss = new StreamSource(in);
        JAXBContext context = JAXBContext.newInstance(jaxb_bindings);
        Unmarshaller um = context.createUnmarshaller();
        Message msg = (Message) um.unmarshal(ss);

        NMLVisitor visitor = new NMLVisitor();
        TraversingVisitor tv = new TraversingVisitor(new DepthFirstTraverserImpl(), visitor);
        tv.setTraverseFirst(true);
        msg.getBody().accept(tv);
        JAXBElement<TopologyType> element = (JAXBElement<TopologyType>) msg.getBody().getAny().get(0);
        Assert.assertNotNull(element.getValue());
        TopologyType object = element.getValue();
        Assert.assertNotNull(object.getId());
        object.accept(tv);
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
