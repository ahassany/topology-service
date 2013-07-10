package net.es.topology.common.converter.nml;

import net.es.lookup.client.RegistrationClient;
import net.es.lookup.client.SimpleLS;
import net.es.lookup.common.exception.LSClientException;
import net.es.lookup.common.exception.ParserException;
import net.es.lookup.records.Record;
import net.es.topology.common.config.sls.JsonClientProvider;
import net.es.topology.common.records.ts.NSA;
import net.es.topology.common.records.ts.RecordsCache;
import net.es.topology.common.records.ts.TSRecordFactory;
import net.es.topology.common.records.ts.Topology;
import net.es.topology.common.visitors.sls.TraverserImpl;
import net.es.topology.common.visitors.sls.TraversingVisitor;
import net.es.topology.common.visitors.sls.TraversingVisitorProgressMonitorLoggingImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public void testTraverser() throws Exception{
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
        topology.setVersion("2013-05-30T09:30:10Z");
        registrationClient.setRecord(topology);
        registrationClient.register();

        // Prepare the visitor
        SLSVisitor visitor = new SLSVisitor();
        RecordsCache recordsCache = new RecordsCache(client, getLogGUID());
        TraversingVisitor tv = new TraversingVisitor(new TraverserImpl(recordsCache, getLogGUID()), visitor, getLogGUID());
        TraversingVisitorProgressMonitorLoggingImpl monitorLogging = new TraversingVisitorProgressMonitorLoggingImpl(getLogGUID());
        tv.setProgressMonitor(monitorLogging);


        // Act
        NSA receivedNSA = recordsCache.getNSA(urn);
        Assert.assertNotNull(receivedNSA);
        receivedNSA.accept(tv);
    }
}
