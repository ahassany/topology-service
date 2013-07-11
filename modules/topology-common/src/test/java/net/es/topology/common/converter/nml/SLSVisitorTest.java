package net.es.topology.common.converter.nml;

import net.es.lookup.client.RegistrationClient;
import net.es.lookup.client.SimpleLS;
import net.es.lookup.common.exception.LSClientException;
import net.es.lookup.common.exception.ParserException;
import net.es.lookup.records.Record;
import net.es.topology.common.config.JAXBConfig;
import net.es.topology.common.config.sls.JsonClientProvider;
import net.es.topology.common.records.ts.*;
import net.es.topology.common.visitors.nml.DepthFirstTraverserImpl;
import net.es.topology.common.visitors.nml.TraversingVisitor;
import net.es.topology.common.visitors.sls.SLSTraverserImpl;
import net.es.topology.common.visitors.sls.SLSTraversingVisitor;
import net.es.topology.common.visitors.sls.TraversingVisitorProgressMonitorLoggingImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.ogf.schemas.nml._2013._05.base.ObjectFactory;
import org.ogf.schemas.nml._2013._05.base.TopologyType;
import org.ogf.schemas.nsi._2013._09.messaging.Message;
import org.ogf.schemas.nsi._2013._09.topology.NSAType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class SLSVisitorTest {
    private final String sLSConfigFile = getClass().getClassLoader().getResource("config/sls.json").getFile();
    private final Logger logger = LoggerFactory.getLogger(SLSVisitorTest.class);
    private String logGUID = null;

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
    }

    @Test
    @Ignore
    public void testReceive() {
        String urn = "urn:ogf:network:example.org:2013:nsa";
        try {
            SimpleLS client = new SimpleLS("localhost", 8090);
            client.setRelativeUrl("lookup/records?ts-id=" + urn);
            client.connect();
            client.send();
            String resp = client.getResponse();
            List<Record> records = TSRecordFactory.toRecords(resp, getLogGUID());
            System.out.println(records.get(0).getRecordType());
            for (Record record : records) {
                System.out.println(((NSA) record).getTopologies());
            }
        } catch (LSClientException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTraverser() throws Exception {
        // Arrange
        String urn = "urn:ogf:network:example.org:2013:nsa1";
        String topoURN = "urn:ogf:network:example.org:2013:topo";
        JsonClientProvider sLSConfig = new JsonClientProvider(getLogGUID());
        sLSConfig.setFilename(sLSConfigFile);
        SimpleLS client = sLSConfig.getClient();
        RecordsCache cache = new RecordsCache(client, getLogGUID());

        RegistrationClient registrationClient = new RegistrationClient(sLSConfig.getClient());

        // Register the first NSA
        NSA nsa1 = new NSA();
        nsa1.setId(urn);
        nsa1.setVersion("2013-05-30T09:30:10Z");
        nsa1.setName("nsa1");
        nsa1.setTopologies(new ArrayList<String>());
        nsa1.getTopologies().add(topoURN);
        registrationClient.setRecord(nsa1);
        registrationClient.register();

        Topology topology = new Topology();
        topology.setId(topoURN);
        topology.setName(topoURN);
        topology.setVersion("2013-05-30T09:30:10Z");
        topology.setIsAlias(new ArrayList<String>());
        topology.getIsAlias().add(topoURN);
        registrationClient.setRecord(topology);
        registrationClient.register();

        // Prepare the visitor
        SLSVisitor visitor = new SLSVisitor();
        RecordsCache recordsCache = new RecordsCache(client, getLogGUID());
        SLSTraversingVisitor tv = new SLSTraversingVisitor(new SLSTraverserImpl(recordsCache, getLogGUID()), visitor, getLogGUID());
        TraversingVisitorProgressMonitorLoggingImpl monitorLogging = new TraversingVisitorProgressMonitorLoggingImpl(getLogGUID());
        tv.setProgressMonitor(monitorLogging);
        tv.setTraverseFirst(true);


        // Act
        NSA receivedNSA = recordsCache.getNSA(urn);
        Assert.assertNotNull(receivedNSA);
        receivedNSA.accept(tv);

        // Assert
        Assert.assertTrue(visitor.getNsaTypeMap().containsKey(urn));
        NSAType topo = visitor.getNsaTypeMap().get(urn);
        JAXBConfig.getMarshaller().marshal(new org.ogf.schemas.nsi._2013._09.topology.ObjectFactory().createNSA(topo), System.out);
    }

    /**
     * Helper method to read NML/NSI XML then convert it and register it with sLS instance
     *
     * @param filename
     * @throws Exception
     */
    protected void registerSLS(String filename) throws Exception {
        logger.debug("event=SLSVisitorTest.registerSLS.start guid=" + getLogGUID());
        // Read the example and send it to sLS
        RecordsCollection collection = new RecordsCollection(getLogGUID());
        NMLVisitor visitor = new NMLVisitor(collection, getLogGUID());
        TraversingVisitor nmlTraversingVisitor = new TraversingVisitor(new DepthFirstTraverserImpl(), visitor);

        // Prepare for by reading the example message
        InputStream in = getClass().getClassLoader().getResourceAsStream(filename);

        StreamSource ss = new StreamSource(in);
        Unmarshaller um = JAXBConfig.getUnMarshaller();
        Message msg = (Message) um.unmarshal(ss);
        msg.getBody().accept(nmlTraversingVisitor);

        /**
         * register with sLS
         */
        JsonClientProvider sLSConfig = new JsonClientProvider(getLogGUID());
        sLSConfig.setFilename(sLSConfigFile);
        RegistrationClient registrationClient = new RegistrationClient(sLSConfig.getClient());
        collection.sendTosLS(registrationClient);

        logger.debug("event=SLSVisitorTest.registerSLS.end guid=" + getLogGUID());

    }

    @Test
    public void testVisitPort() throws Exception {
        // Prepare
        logger.debug("event=SLSVisitorTest.testVisitPort.start guid=" + getLogGUID());
        // Load data to sLS
        registerSLS("xml-examples/example-message-port.xml");

        // Prepare the sLS client
        JsonClientProvider sLSConfig = new JsonClientProvider(getLogGUID());
        sLSConfig.setFilename(sLSConfigFile);
        SimpleLS client = sLSConfig.getClient();
        RecordsCache cache = new RecordsCache(client, getLogGUID());

        // Prepare the visitor
        SLSVisitor visitor = new SLSVisitor();
        RecordsCache recordsCache = new RecordsCache(client, getLogGUID());
        SLSTraversingVisitor tv = new SLSTraversingVisitor(new SLSTraverserImpl(recordsCache, getLogGUID()), visitor, getLogGUID());
        TraversingVisitorProgressMonitorLoggingImpl monitorLogging = new TraversingVisitorProgressMonitorLoggingImpl(getLogGUID());
        tv.setProgressMonitor(monitorLogging);
        tv.setTraverseFirst(true);

        String urn = "urn:ogf:network:example.net:2013:portA";
        // Act
        Port received = recordsCache.getPort(urn);
        Assert.assertNotNull(received);
        received.accept(tv);

    }

    @Test
    public void testVisitTopology() throws Exception {
        // Prepare
        logger.debug("event=SLSVisitorTest.testVisitTopology.start guid=" + getLogGUID());
        // Load data to sLS
        registerSLS("xml-examples/example-message-topology.xml");

        // Prepare the sLS client
        JsonClientProvider sLSConfig = new JsonClientProvider(getLogGUID());
        sLSConfig.setFilename(sLSConfigFile);
        SimpleLS client = sLSConfig.getClient();
        RecordsCache cache = new RecordsCache(client, getLogGUID());

        // Prepare the visitor
        SLSVisitor visitor = new SLSVisitor();
        RecordsCache recordsCache = new RecordsCache(client, getLogGUID());
        SLSTraversingVisitor tv = new SLSTraversingVisitor(new SLSTraverserImpl(recordsCache, getLogGUID()), visitor, getLogGUID());
        TraversingVisitorProgressMonitorLoggingImpl monitorLogging = new TraversingVisitorProgressMonitorLoggingImpl(getLogGUID());
        tv.setProgressMonitor(monitorLogging);
        tv.setTraverseFirst(true);

        String urn = "urn:ogf:network:example.net:2013:org";
        // Act
        Topology received = recordsCache.getTopology(urn);
        Assert.assertNotNull(received);
        received.accept(tv);

        // Assert
        Assert.assertTrue(visitor.getTopologyTypeMap().containsKey(urn));
        TopologyType topo = visitor.getTopologyTypeMap().get(urn);
        JAXBConfig.getMarshaller().marshal(new ObjectFactory().createTopology(topo), System.out);
    }

    @Test
    public void testVisitNSA() throws Exception {
        // Prepare
        logger.debug("event=SLSVisitorTest.testVisitNSA.start guid=" + getLogGUID());
        // Load data to sLS
        registerSLS("xml-examples/example-message-nsa.xml");

        // Prepare the sLS client
        JsonClientProvider sLSConfig = new JsonClientProvider(getLogGUID());
        sLSConfig.setFilename(sLSConfigFile);
        SimpleLS client = sLSConfig.getClient();
        RecordsCache cache = new RecordsCache(client, getLogGUID());

        // Prepare the visitor
        SLSVisitor visitor = new SLSVisitor();
        RecordsCache recordsCache = new RecordsCache(client, getLogGUID());
        SLSTraversingVisitor tv = new SLSTraversingVisitor(new SLSTraverserImpl(recordsCache, getLogGUID()), visitor, getLogGUID());
        TraversingVisitorProgressMonitorLoggingImpl monitorLogging = new TraversingVisitorProgressMonitorLoggingImpl(getLogGUID());
        tv.setProgressMonitor(monitorLogging);
        tv.setTraverseFirst(true);

        String urn = "urn:ogf:network:example.org:2013:nsa";
        // Act
        NSA received = recordsCache.getNSA(urn);
        Assert.assertNotNull(received);
        received.accept(tv);

        // Assert
        Assert.assertTrue(visitor.getNsaTypeMap().containsKey(urn));
        NSAType nsa = visitor.getNsaTypeMap().get(urn);
        JAXBConfig.getMarshaller().marshal(new org.ogf.schemas.nsi._2013._09.topology.ObjectFactory().createNSA(nsa), System.out);
    }
}
