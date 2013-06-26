package net.es.topology.common.converter.nml;

import net.es.lookup.client.RegistrationClient;
import net.es.lookup.client.SimpleLS;
import net.es.lookup.common.exception.LSClientException;
import net.es.lookup.common.exception.ParserException;
import net.es.lookup.records.Record;
import net.es.topology.common.records.nml.Node;
import net.es.topology.common.visitors.DepthFirstTraverserImpl;
import net.es.topology.common.visitors.TraversingVisitor;
import org.junit.Ignore;
import org.junit.Test;
import org.ogf.schemas.nml._2013._05.base.NodeType;
import org.ogf.schemas.nsi._2013._09.messaging.Message;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class VisitorTest {
    public final static String jaxb_bindings = "org.ogf.schemas.nsi._2013._09.topology:org.ogf.schemas.nsi._2013._09.messaging:org.ogf.schemas.nml._2013._05.base";

    @Test
    public void testVisitNodeType() throws JAXBException {
        InputStream in =
                getClass().getClassLoader().getResourceAsStream("xml-examples/example-message.xml");

        StreamSource ss = new StreamSource(in);
        JAXBContext context = JAXBContext.newInstance(jaxb_bindings);
        Unmarshaller um = context.createUnmarshaller();
        Message msg = (Message) um.unmarshal(ss);

        NMLVisitor visitor = new NMLVisitor();
        TraversingVisitor tv = new TraversingVisitor(new DepthFirstTraverserImpl(), visitor);
        tv.setTraverseFirst(true);
        msg.getBody().accept(tv);
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
