package net.es.topology.common.config.sls;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.es.lookup.client.SimpleLS;
import net.es.topology.common.config.JsonConfigProvider;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Load a config file and return new instance of sLS client
 * <p/>
 * This configuration structure is borrowed from nsi-pce project
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class JsonClientProvider extends JsonConfigProvider implements ClientProvider {

    private final Logger logger = LoggerFactory.getLogger(JsonClientProvider.class);
    private Map<String, String> configs = new HashMap<String, String>();
    private String logGUID = null;
    private static JsonClientProvider instance;

    private JsonClientProvider() {
        this.logGUID = UUID.randomUUID().toString();
    }

    public static JsonClientProvider getInstance() {
        if (instance == null) {
            instance = new JsonClientProvider();
        }
        return instance;
    }

    public void setLogGUID(String logGUID) {
        this.logGUID = logGUID;
    }

    public String getLogGUID() {
        return this.logGUID;
    }

    @Override
    public void loadConfig() throws Exception {
        logger.info("event=JsonClientProvider.loadConfig.start configFile=" + getFilename() + " guid=" + getLogGUID());
        File configFile = new File(getFilename());
        String ap = configFile.getAbsolutePath();
        if (isFileUpdated(configFile)) {
            String jsonConfig = FileUtils.readFileToString(configFile);
            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, String>>() {
            }.getType();
            configs = gson.fromJson(jsonConfig, type);
            // TODO (AH): define custom exceptions
            if (configs.containsKey("host") == false) {
                logger.info("event=JsonClientProvider.loadConfig.error status=-1 error=\"File doesn't have 'host'\" configFile=" + getFilename() + " guid=" + getLogGUID());
                throw new Exception("Config file does NOT contain 'host', file=\"" + getFilename() + "\"");
            }

            if (configs.containsKey("port") == false) {
                logger.info("event=JsonClientProvider.loadConfig.error status=-1 error=\"File doesn't have 'port'\" configFile=" + getFilename() + " guid=" + getLogGUID());
                throw new Exception("Config file does NOT contain 'port', file=\"" + getFilename() + "\"");
            }

            if (Integer.parseInt(configs.get("port")) == 0) {
                logger.info("event=JsonClientProvider.loadConfig.error status=-1 error=\"'port' must be an integer: found='" + configs.get("port") + "'\" configFile=" + getFilename() + " guid=" + getLogGUID());
                throw new Exception("'port' must be an integer, file=\"" + getFilename() + "\"");
            }
        }
        logger.info("event=JsonClientProvider.loadConfig.end status=0 configFile=" + getFilename() + " guid=" + getLogGUID());
    }

    /**
     * The port number of the sLS service
     *
     * @return port number
     */
    @Override
    public int getPort() {
        try {
            loadConfig();
        } catch (Exception ex) {
            return 0;
        }
        return Integer.parseInt(configs.get("port"));
    }

    /**
     * The host name (or IP address) of the sLS service
     *
     * @return hostname
     */
    @Override
    public String getHost() {
        try {
            loadConfig();
        } catch (Exception ex) {
            return null;
        }
        return configs.get("host");
    }

    @Override
    public SimpleLS getClient() throws Exception {
        logger.info("event=JsonClientProvider.getClient.start configFile=" + getFilename() + " guid=" + getLogGUID());
        loadConfig();
        SimpleLS client = new SimpleLS(getHost(), getPort());
        logger.info("event=JsonClientProvider.getClient.end status=0 configFile=" + getFilename() + " guid=" + getLogGUID());
        return client;
    }
}
