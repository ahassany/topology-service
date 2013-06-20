package net.es.topology.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

import org.ogf.schemas.nml._2013._05.base.*;

@RunWith(JUnit4.class)
public class AppTest {

    @Test
    public void testNode() {

        InputStream in =
                getClass().getClassLoader().getResourceAsStream("xml-examples/example-node.xml");

        StreamSource s = new StreamSource(in);

        JAXBContext jc = null;
        try {
            jc = JAXBContext.newInstance("org.ogf.schemas.nml._2013._05.base");
            Unmarshaller u = jc.createUnmarshaller();
            JAXBElement<NodeType> je = (JAXBElement<NodeType>) u.unmarshal(s, NodeType.class);
            in =
                    getClass().getClassLoader().getResourceAsStream("xml-examples/example-node.xml");

            s = new StreamSource(in);

            System.out.println("NAME" + ((JAXBElement)u.unmarshal(in)).getName());
            NodeType node = je.getValue();
            System.out.println( " Node type: " + node.getId());

        } catch (JAXBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }



    }

    @Test
    @Ignore
    public void thisIsIgnored() {
    }
}