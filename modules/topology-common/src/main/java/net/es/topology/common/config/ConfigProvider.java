package net.es.topology.common.config;

/**
 * Using the same configuration structure used in NSI-PCE code
 */
public interface ConfigProvider {
    public String getFilename();

    public void setFilename(String filename);

    public void loadConfig() throws Exception;
}
