package net.es.topology.common.config;

import java.io.File;

/**
 * Using the same configuration structure used in NSI-PCE code
 */
public abstract class JsonConfigProvider implements ConfigProvider {
    private String filename;
    private long timeStamp;
    private File file;

    public JsonConfigProvider() {

    }
    /**
     * Check if the file has changed sine the last time it was read.
     *
     * @param file the config file
     * @return
     */
    protected boolean isFileUpdated(File file) {
        this.file = file;
        long lastModified = file.lastModified();

        if (this.timeStamp != lastModified) {
            this.timeStamp = lastModified;
            //Yes, file is updated
            return true;
        }
        //No, file is not updated
        return false;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}