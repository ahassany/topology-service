package net.es.topology.client.sls;

import net.es.lookup.client.RegistrationClient;
import net.es.lookup.client.SimpleLS;
import net.es.topology.common.config.JAXBConfig;
import net.es.topology.common.config.sls.JsonClientProvider;
import net.es.topology.common.converter.nml.NMLVisitor;
import net.es.topology.common.records.ts.utils.*;
import net.es.topology.common.visitors.nml.DepthFirstTraverserImpl;
import net.es.topology.common.visitors.nml.TraversingVisitor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ogf.schemas.nml._2013._05.base.NetworkObject;
import org.ogf.schemas.nml._2013._05.base.PortGroupType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class SLSTSClientTest {
    private final String sLSConfigFile = getClass().getClassLoader().getResource("config/sls.json").getFile();
    private final Logger logger = LoggerFactory.getLogger(SLSTSClientTest.class);
    private String logGUID = null;
    private JsonClientProvider sLSConfig;

    /**
     * get the UUID of the current test case
     *
     * @return
     */
    public String getLogGUID() {
        return this.logGUID;
    }

    @Before
    public void setup() {
        // Make sure that each test case has it's own ID to make it easier to trace.
        this.logGUID = UUID.randomUUID().toString();
        this.sLSConfig = JsonClientProvider.getInstance();
        sLSConfig.setFilename(this.sLSConfigFile);
        sLSConfig.setLogGUID(this.getLogGUID());
    }

    @Test
    public void testGetNetworkObject() throws Exception {
        // Prepare
        logger.debug("event=SLSTSClientTest.testGetNetworkObject.start guid=" + getLogGUID());
        // Prepare client
        // Load data to sLS
        String filename = "xml-examples/example-port-group.xml";

        // Read the example and send it to sLS
        RecordsCollection collection = new RecordsCollection(getLogGUID());
        NMLVisitor nmlVisitor = new NMLVisitor(collection, getLogGUID());
        TraversingVisitor nmlTraversingVisitor = new TraversingVisitor(new DepthFirstTraverserImpl(), nmlVisitor);

        // Prepare for by reading the example message
        InputStream in = getClass().getClassLoader().getResourceAsStream(filename);

        StreamSource ss = new StreamSource(in);
        Unmarshaller um = JAXBConfig.getUnMarshaller();
        JAXBElement<PortGroupType> msg = (JAXBElement<PortGroupType>) um.unmarshal(ss);
        msg.getValue().accept(nmlTraversingVisitor);

        /**
         * register with sLS
         */
        collection.sendTosLS(new SLSRegistrationClientDispatcherImpl(sLSConfig), new SLSClientDispatcherImpl(sLSConfig), new URNMaskGetAllImpl());
        RecordsCache recordsCache = new RecordsCache(new SLSClientDispatcherImpl(sLSConfig), new URNMaskGetAllImpl(), getLogGUID());

        SLSTSClient slsTSClient = new SLSTSClient(recordsCache, getLogGUID());

        String urn = "urn:ogf:network:example.net:2013:portGroup2";

        // Act
        NetworkObject obj = slsTSClient.getNetworkObject(urn);

        // Assert
        Assert.assertTrue(obj instanceof PortGroupType);

        logger.debug("event=SLSTSClientTest.testGetNetworkObject.end status=0 guid=" + getLogGUID());
    }
}
