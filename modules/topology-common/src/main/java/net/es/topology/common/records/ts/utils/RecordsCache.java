package net.es.topology.common.records.ts.utils;

import net.es.lookup.client.SimpleLS;
import net.es.lookup.common.exception.LSClientException;
import net.es.lookup.common.exception.ParserException;
import net.es.lookup.records.Record;
import net.es.topology.common.records.ts.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Retrieves records from sLS and caches them.
 * Also this class makes it easy not have to cast java types all the time!
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class RecordsCache {
    private final Logger logger = LoggerFactory.getLogger(RecordsCache.class);
    /**
     * A Unique UUID to identify the log trace withing each instance.
     *
     * @see <a href="http://netlogger.lbl.gov/">Netlogger</a> best practices document.
     */
    private String logGUID;
    private SLSClientDispatcher clientDispatcher;
    private URNMask urnMask;
    private Map<String, Record> recordMap = new HashMap<String, Record>();

    public RecordsCache(SLSClientDispatcher clientDispatcher, URNMask urnMask, String logGUID) {
        this.clientDispatcher = clientDispatcher;
        this.urnMask = urnMask;
        this.logGUID = logGUID;
    }

    public RecordsCache(SLSClientDispatcher clientDispatcher, URNMask urnMask) {
        this(clientDispatcher, urnMask, UUID.randomUUID().toString());
    }

    public SLSClientDispatcher getClientDispatcher() {
        return this.clientDispatcher;
    }

    public URNMask getUrnMask() {
        return this.urnMask;
    }

    public String getLogGUID() {
        return this.logGUID;
    }

    public Logger getLogger() {
        return logger;
    }

    /**
     * Returns the record from the cache if it's already cached, or request it directly
     *
     * @param urn          the urn of the record
     * @param getFreshCopy allows to override the cache and contact the sLS directly
     * @return
     * @throws LSClientException
     * @throws ParserException
     */
    public Record getRecord(String urn, boolean getFreshCopy) throws LSClientException, ParserException {
        getLogger().info("event=RecordsCache.getRecord.start getFreshCopy=" + getFreshCopy + " urn=" + urn + " guid=" + getLogGUID());
        Record record = null;
        boolean isCached = recordMap.containsKey(urn);
        if (getUrnMask().getFromSLS(urn) == false) {
            getLogger().info("event=RecordsCache.getRecord.end status=1 message=\"URN is not allowed\" getFreshCopy=" + getFreshCopy + " urn=" + urn + "guid=" + getLogGUID());
            return null;
        }
        if (getFreshCopy == true || isCached == false) {
            SimpleLS client = getClientDispatcher().getClient(urn);
            client.setRelativeUrl("lookup/records?ts-id=" + urn);
            client.connect();
            client.send();
            String resp = client.getResponse();
            List<Record> records = TSRecordFactory.toRecords(resp, getLogGUID());
            if (records.size() > 0) {
                recordMap.put(urn, records.get(records.size() - 1));
                record = records.get(records.size() - 1);
            }
        } else if (isCached == true) {
            record = recordMap.get(urn);
        }
        getLogger().info("event=RecordsCache.getRecord.end getFreshCopy=" + getFreshCopy + " urn=" + urn + " guid=" + getLogGUID());
        return record;
    }

    /**
     * Returns the record from the cache if it's already cached, or request it directly
     *
     * @param urn
     * @return
     * @throws LSClientException
     * @throws ParserException
     * @see getRecord
     */
    public Record getRecord(String urn) throws LSClientException, ParserException {
        return getRecord(urn, false);
    }

    public NSA getNSA(String urn, boolean getFreshCopy) throws LSClientException, ParserException {
        Record record = getRecord(urn, getFreshCopy);
        if (record instanceof NSA) {
            return (NSA) record;
        } else {
            return null;
        }
    }

    public NSA getNSA(String urn) throws LSClientException, ParserException {
        return getNSA(urn, false);
    }

    public BidirectionalPort getBidirectionalPort(String urn, boolean getFreshCopy) throws LSClientException, ParserException {
        Record record = getRecord(urn, getFreshCopy);
        if (record instanceof BidirectionalPort) {
            return (BidirectionalPort) record;
        } else {
            return null;
        }
    }

    public BidirectionalPort getBidirectionalPort(String urn) throws LSClientException, ParserException {
        return getBidirectionalPort(urn, false);
    }

    public BidirectionalLink getBidirectionalLink(String urn, boolean getFreshCopy) throws LSClientException, ParserException {
        Record record = getRecord(urn, getFreshCopy);
        if (record instanceof BidirectionalLink) {
            return (BidirectionalLink) record;
        } else {
            return null;
        }
    }

    public BidirectionalLink getBidirectionalLink(String urn) throws LSClientException, ParserException {
        return getBidirectionalLink(urn, false);
    }

    public Link getLink(String urn, boolean getFreshCopy) throws LSClientException, ParserException {
        Record record = getRecord(urn, getFreshCopy);
        if (record instanceof Link) {
            return (Link) record;
        } else {
            return null;
        }
    }

    public Link getLink(String urn) throws LSClientException, ParserException {
        return getLink(urn, false);
    }

    public LinkGroup getLinkGroup(String urn, boolean getFreshCopy) throws LSClientException, ParserException {
        Record record = getRecord(urn, getFreshCopy);
        if (record instanceof LinkGroup) {
            return (LinkGroup) record;
        } else {
            return null;
        }
    }

    public LinkGroup getLinkGroup(String urn) throws LSClientException, ParserException {
        return getLinkGroup(urn, false);
    }

    public Node getNode(String urn, boolean getFreshCopy) throws LSClientException, ParserException {
        Record record = getRecord(urn, getFreshCopy);
        if (record instanceof Node) {
            return (Node) record;
        } else {
            return null;
        }
    }

    public Node getNode(String urn) throws LSClientException, ParserException {
        return getNode(urn, false);
    }

    public NSIService getNSIService(String urn, boolean getFreshCopy) throws LSClientException, ParserException {
        Record record = getRecord(urn, getFreshCopy);
        if (record instanceof NSIService) {
            return (NSIService) record;
        } else {
            return null;
        }
    }

    public NSIService getNSIService(String urn) throws LSClientException, ParserException {
        return getNSIService(urn, false);
    }

    public Port getPort(String urn, boolean getFreshCopy) throws LSClientException, ParserException {
        Record record = getRecord(urn, getFreshCopy);
        if (record instanceof Port) {
            return (Port) record;
        } else {
            return null;
        }
    }

    public Port getPort(String urn) throws LSClientException, ParserException {
        return getPort(urn, false);
    }

    public PortGroup getPortGroup(String urn, boolean getFreshCopy) throws LSClientException, ParserException {
        Record record = getRecord(urn, getFreshCopy);
        if (record instanceof PortGroup) {
            return (PortGroup) record;
        } else {
            return null;
        }
    }

    public PortGroup getPortGroup(String urn) throws LSClientException, ParserException {
        return getPortGroup(urn, false);
    }

    public Topology getTopology(String urn, boolean getFreshCopy) throws LSClientException, ParserException {
        Record record = getRecord(urn, getFreshCopy);
        if (record instanceof Topology) {
            return (Topology) record;
        } else {
            return null;
        }
    }

    public Topology getTopology(String urn) throws LSClientException, ParserException {
        return getTopology(urn, false);
    }


    // TODO (AH) add support for the different services defined in NML


}
