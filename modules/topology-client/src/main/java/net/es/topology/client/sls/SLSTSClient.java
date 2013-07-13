package net.es.topology.client.sls;

import net.es.lookup.common.exception.LSClientException;
import net.es.lookup.common.exception.ParserException;
import net.es.topology.common.converter.nml.SLSVisitor;
import net.es.topology.common.records.ts.utils.RecordsCache;
import net.es.topology.common.visitors.sls.SLSTraverserImpl;
import net.es.topology.common.visitors.sls.SLSTraversingVisitor;
import org.ogf.schemas.nml._2013._05.base.NetworkObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class to get NML/NSI NetworkObjects from sLS service, without worrying about
 * creating the traverser objects.
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 * @see net.es.topology.common.visitors.sls.SLSTraversingVisitor
 * @see net.es.topology.common.converter.nml.SLSVisitor;
 */
public class SLSTSClient {
    private final Logger logger = LoggerFactory.getLogger(RecordsCache.class);
    private RecordsCache recordsCache;
    private SLSTraversingVisitor traversingVisitor;
    private SLSVisitor slsVisitor;
    /**
     * A Unique UUID to identify the log trace withing each instance.
     *
     * @see <a href="http://netlogger.lbl.gov/">Netlogger</a> best practices document.
     */
    private String logGUID;

    public SLSTSClient(RecordsCache recordsCache, SLSTraversingVisitor traversingVisitor, String logGUID) {
        this.recordsCache = recordsCache;
        this.traversingVisitor = traversingVisitor;
        this.logGUID = logGUID;
    }

    public SLSTSClient(RecordsCache recordsCache, String logGUID) {
        this.recordsCache = recordsCache;
        this.slsVisitor = new SLSVisitor();
        this.traversingVisitor = new SLSTraversingVisitor(
                new SLSTraverserImpl(recordsCache, logGUID),
                this.slsVisitor, logGUID);
        this.logGUID = logGUID;
    }

    public String getLogGUID() {
        return this.logGUID;
    }

    public Logger getLogger() {
        return logger;
    }

    public RecordsCache getRecordsCache() {
        return recordsCache;
    }

    public void setRecordsCache(RecordsCache recordsCache) {
        this.recordsCache = recordsCache;
    }

    public SLSTraversingVisitor getTraversingVisitor() {
        return traversingVisitor;
    }

    public void setTraversingVisitor(SLSTraversingVisitor traversingVisitor) {
        this.traversingVisitor = traversingVisitor;
    }

    public SLSVisitor getSlsVisitor() {
        return slsVisitor;
    }

    public void setSlsVisitor(SLSVisitor slsVisitor) {
        this.slsVisitor = slsVisitor;
    }

    public NetworkObject getNetworkObject(String urn) throws LSClientException, ParserException {
        net.es.topology.common.records.ts.NetworkObject record = recordsCache.getNetworkObject(urn);
        record.accept(getTraversingVisitor());
        return getSlsVisitor().getNetworkObject(urn);
    }

    public Map<String, NetworkObject> getNetworkObjects(List<String> urns) throws LSClientException, ParserException {
        if (urns == null) {
            return null;
        }
        Map<String, NetworkObject> objectMap = new HashMap<String, NetworkObject>();
        for (String urn : urns) {
            objectMap.put(urn, getNetworkObject(urn));
        }
        return objectMap;
    }
}
