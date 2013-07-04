package net.es.topology.common.messaging;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.ogf.schemas.nml._2013._05.base.NodeType;
import org.ogf.schemas.nml._2013._05.base.ObjectFactory;
import org.ogf.schemas.nsi._2013._09.messaging.Message;

import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;


/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */

@RunWith(JUnit4.class)
public class MessageTest {
    public final static String jaxb_bindings = "org.ogf.schemas.nsi._2013._09.topology:org.ogf.schemas.nsi._2013._09.messaging:org.ogf.schemas.nml._2013._05.base";

    @Test
    public void testMessageCreate() throws JAXBException {
        final String nodeName = "Node1";

        // First create a message
        Message msg = new Message();
        Message.Body body = new Message.Body();
        JAXBElement<NodeType> node = new ObjectFactory().createNode(new NodeType());
        node.getValue().setName(nodeName);
        body.getNode().add(node.getValue());
        msg.setBody(body);

        // make sure it marshals correctly
        JAXBContext context = JAXBContext.newInstance(jaxb_bindings);
        Marshaller marshaller = context.createMarshaller();
        StringWriter out = new StringWriter();
        marshaller.marshal(msg, out);

        // Read it again
        Unmarshaller um = context.createUnmarshaller();
        Message umsg = (Message) um.unmarshal(new InputStreamReader(IOUtils.toInputStream(out.toString())));

        // Make sure we have the same value
        Message.Body ubody = umsg.getBody();
        Assert.assertEquals(1, ubody.getNode().size());
        Assert.assertEquals(nodeName, ubody.getNode().get(0).getName());
    }

    @Test
    public void testComplexMessage() throws JAXBException {
        InputStream in =
                getClass().getClassLoader().getResourceAsStream("xml-examples/example-message.xml");

        StreamSource ss = new StreamSource(in);
        JAXBContext context = JAXBContext.newInstance(jaxb_bindings);
        Unmarshaller um = context.createUnmarshaller();
        Message msg = (Message) um.unmarshal(ss);
        Assert.assertEquals(2, msg.getBody().getNode().size());
        Assert.assertEquals("Node_A", msg.getBody().getNode().get(0).getName());
    }
}
