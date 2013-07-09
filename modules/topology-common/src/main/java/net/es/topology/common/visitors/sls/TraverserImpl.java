package net.es.topology.common.visitors.sls;

import net.es.topology.common.records.ts.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class TraverserImpl implements Traverser {
    private final Logger logger = LoggerFactory.getLogger(TraverserImpl.class);
    /**
     * A Unique UUID to identify the log trace within each traverser instance.
     *
     * @see <a href="http://netlogger.lbl.gov/">Netlogger</a> best practices document.
     */
    private String logGUID;

    public String getLogGUID() {
        return logGUID;
    }

    public void setLogGUID(String logGUID) {
        this.logGUID = logGUID;
    }

    @Override
    public void traverse(NetworkObject record, Visitor visitor) {

    }

    @Override
    public void traverse(BidirectionalLink record, Visitor visitor) {

    }

    @Override
    public void traverse(BidirectionalPort record, Visitor visitor) {

    }

    @Override
    public void traverse(Link record, Visitor visitor) {

    }

    @Override
    public void traverse(LinkGroup record, Visitor visitor) {

    }

    @Override
    public void traverse(Node record, Visitor visitor) {

    }

    @Override
    public void traverse(NSA record, Visitor visitor) {
        for (String topology : record.getTopologies()) {
            //System.out.println("Found topology " + topology);
        }
    }

    @Override
    public void traverse(NSIService record, Visitor visitor) {

    }

    @Override
    public void traverse(Port record, Visitor visitor) {

    }

    @Override
    public void traverse(PortGroup record, Visitor visitor) {

    }

    @Override
    public void traverse(Topology record, Visitor visitor) {

    }
}
