package net.es.topology.common.visitors.sls;

import net.es.lookup.records.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple monitor that logges the visited and traversed records
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class TraversingVisitorProgressMonitorLoggingImpl implements TraversingVisitorProgressMonitor {

    private final Logger logger = LoggerFactory.getLogger(TraversingVisitorProgressMonitorLoggingImpl.class);
    /**
     * A Unique UUID to identify the log trace within each traverser monitor instance.
     *
     * @see <a href="http://netlogger.lbl.gov/">Netlogger</a> best practices document.
     */
    private String logGUID;


    public TraversingVisitorProgressMonitorLoggingImpl(String logGUID) {
        this.logGUID = logGUID;
    }

    public String getLogGUID() {
        return logGUID;
    }

    public void setLogGUID(String logGUID) {
        this.logGUID = logGUID;
    }

    @Override
    public void visited(Visitable aVisitable) {
        if (aVisitable instanceof Record)
            logger.trace("event=TraversingVisitorProgressMonitorLoggingImpl.visited recordType=" + ((Record) aVisitable).getRecordType() + " recordURI=" + ((Record) aVisitable).getURI() + " guid=" + getLogGUID());
        else
            logger.trace("event=TraversingVisitorProgressMonitorLoggingImpl.visited object=" + aVisitable.toString() + " guid=" + getLogGUID());

    }

    @Override
    public void traversed(Visitable aVisitable) {
        if (aVisitable instanceof Record)
            logger.trace("event=TraversingVisitorProgressMonitorLoggingImpl.traversed recordType=" + ((Record) aVisitable).getRecordType() + " recordURI=" + ((Record) aVisitable).getURI() + " guid=" + getLogGUID());
        else
            logger.trace("event=TraversingVisitorProgressMonitorLoggingImpl.traversed object=" + aVisitable.toString() + " guid=" + getLogGUID());

    }
}
