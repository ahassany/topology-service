package net.es.topology.common.config.sls;

import net.es.lookup.client.SimpleLS;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class JsonClientProviderTest {
    private final Logger logger = LoggerFactory.getLogger(JsonClientProvider.class);
    private String logGUID = null;

    public String getLogGUID() {
        return this.logGUID;
    }

    @Before
    public void setup() {
        // Make sure that each test case has it's own ID to make it easier to trace.
        this.logGUID = UUID.randomUUID().toString();
        JsonClientProvider provider = JsonClientProvider.getInstance();
        provider.setLogGUID(getLogGUID());
    }

    @Test
    public void testLoadConfig() throws Exception {
        logger.debug("event=JsonClientProviderTest.testLoadConfig.start guid=" + getLogGUID());
        // Arrange
        String fileName = getClass().getClassLoader().getResource("config/sls.json").getFile();
        JsonClientProvider provider = JsonClientProvider.getInstance();
        provider.setFilename(fileName);

        // Act
        provider.loadConfig();

        // Assert
        // TODO (AH) read the config file manually and assert the provider has the same values
        logger.debug("event=JsonClientProviderTest.testLoadConfig.end guid=" + getLogGUID());
    }

    @Test
    public void testGetClient() throws Exception {
        logger.debug("event=JsonClientProviderTest.testGetClient.start guid=" + getLogGUID());
        // Arrange
        String fileName = getClass().getClassLoader().getResource("config/sls.json").getFile();
        JsonClientProvider provider = JsonClientProvider.getInstance();
        provider.setFilename(fileName);

        // Act
        SimpleLS client = provider.getClient();

        // Assert
        Assert.assertNotNull(client);
        // TODO (AH) read the config file manually and assert the client has the same values
        logger.debug("event=JsonClientProviderTest.testGetClient.end guid=" + getLogGUID());
    }

    @Test
    public void testGetFilename() throws Exception {
        logger.debug("event=JsonClientProviderTest.testGetFilename.start guid=" + getLogGUID());
        // Arrange
        String fileName = getClass().getClassLoader().getResource("config/sls.json").getFile();
        JsonClientProvider provider = JsonClientProvider.getInstance();

        // Act
        provider.setFilename(fileName);

        // Assert
        Assert.assertEquals(fileName, provider.getFilename());
        logger.debug("event=JsonClientProviderTest.testGetFilename.end guid=" + getLogGUID());
    }
}
