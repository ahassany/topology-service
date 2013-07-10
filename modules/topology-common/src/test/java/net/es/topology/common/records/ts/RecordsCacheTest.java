package net.es.topology.common.records.ts;

import net.es.lookup.client.RegistrationClient;
import net.es.lookup.client.SimpleLS;
import net.es.lookup.records.Record;
import net.es.topology.common.config.sls.JsonClientProvider;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class RecordsCacheTest {

    private final String sLSConfigFile = getClass().getClassLoader().getResource("config/sls.json").getFile();
    private final Logger logger = LoggerFactory.getLogger(RecordsCacheTest.class);
    private final String nsa1URN = "urn:ogf:network:example.org:2013:nsa1";
    private final String nsa2URN = "urn:ogf:network:example.org:2013:nsa2";
    private String logGUID = null;

    /**
     * get the UUID of the current test case
     *
     * @return
     */
    public String getLogGUID() {
        return this.logGUID;
    }

    @Test
    public void testGetClient() throws Exception {
        logger.debug("event=RecordsCacheTest.testGetClient.start guid=" + getLogGUID());
        // Arrange
        JsonClientProvider sLSConfig = new JsonClientProvider(getLogGUID());
        sLSConfig.setFilename(sLSConfigFile);
        SimpleLS client = sLSConfig.getClient();

        // Act
        RecordsCache cache = new RecordsCache(client, getLogGUID());

        // Assert
        Assert.assertSame(client, cache.getClient());
        logger.debug("event=RecordsCacheTest.testGetClient.end status=0 guid=" + getLogGUID());
    }

    /**
     * TODO (AH): to implement a mock for SimpleLS and move this test to integration test
     *
     * @throws Exception
     */
    @Test
    @Ignore
    public void testGetRecord() throws Exception {
        logger.debug("event=RecordsCacheTest.testGetClient.start guid=" + getLogGUID());
        // Arrange
        JsonClientProvider sLSConfig = new JsonClientProvider(getLogGUID());
        sLSConfig.setFilename(sLSConfigFile);
        SimpleLS client = sLSConfig.getClient();
        RecordsCache cache = new RecordsCache(client, getLogGUID());

        RegistrationClient registrationClient = new RegistrationClient(sLSConfig.getClient());

        // Register the first NSA
        NSA nsa1 = new NSA();
        nsa1.setId(nsa1URN);
        nsa1.setVersion("2013-05-30T09:30:10Z");
        registrationClient.setRecord(nsa1);
        registrationClient.register();

        // Register the second NSA
        NSA nsa2 = new NSA();
        nsa2.setId(nsa2URN);
        nsa2.setVersion("2013-05-30T09:30:10Z");
        registrationClient.setRecord(nsa2);
        registrationClient.register();

        // Register another version of the second NSA
        NSA nsa2V2 = new NSA();
        nsa2V2.setId(nsa2URN);
        nsa2V2.setVersion("2013-06-30T09:30:10Z");
        registrationClient.setRecord(nsa2V2);
        registrationClient.register();

        // Act
        Record nullRecord = cache.getRecord("doesNotExistRecord");
        Record singleRecord = cache.getRecord(nsa1URN);
        Record multiRecord = cache.getRecord(nsa2URN);

        // Assert
        Assert.assertNull(nullRecord);
        Assert.assertNotNull(singleRecord);
        Assert.assertNotNull(multiRecord);
        // TODO (AH) assert that the records returned correctly
        logger.debug("event=RecordsCacheTest.testGetClient.end status=0 guid=" + getLogGUID());
    }
}
