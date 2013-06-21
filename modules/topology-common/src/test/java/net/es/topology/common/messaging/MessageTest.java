package net.es.topology.common.messaging;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.InputStreamReader;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.hamcrest.CoreMatchers;

import org.ogf.schemas.nml._2013._05.base.NodeType;
import org.ogf.schemas.nsi._2013._09.messaging.Message;



/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */

@RunWith(JUnit4.class)
public class MessageTest {
    @Test
    public void testMessageCreate() throws JAXBException{
        final String nodeName = "Node1";

        // First create a message
        Message msg = new Message();
        NodeType node = new NodeType();
        node.setName(nodeName);
        msg.setBody(node);

        // make sure it marshals correctly
        JAXBContext context = JAXBContext.newInstance("org.ogf.schemas.nsi._2013._09.messaging:org.ogf.schemas.nml._2013._05.base");
        Marshaller marshaller = context.createMarshaller();
        StringWriter out = new StringWriter();
        marshaller.marshal(msg, out);

        // Read it again
        Unmarshaller um = context.createUnmarshaller();
        Message umsg = (Message) um.unmarshal(new InputStreamReader(IOUtils.toInputStream(out.toString())));

        // Make sure we have the same value
        Assert.assertThat(umsg.getBody(), CoreMatchers.instanceOf(NodeType.class));
        Assert.assertEquals(((NodeType)umsg.getBody()).getName(), nodeName);
    }
}
