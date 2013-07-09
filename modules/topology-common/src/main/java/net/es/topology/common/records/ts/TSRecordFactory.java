package net.es.topology.common.records.ts;

import net.es.lookup.common.ReservedKeys;
import net.es.lookup.common.exception.ParserException;
import net.es.lookup.common.exception.RecordException;
import net.es.lookup.records.Record;
import net.es.lookup.records.RecordFactory;
import net.es.topology.common.records.ts.keys.ReservedValues;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Helper class to parse and create sLS Topology records
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class TSRecordFactory {

    /**
     * Logger
     */
    private final static Logger logger = LoggerFactory.getLogger(TSRecordFactory.class);

    /**
     * Return the appropriate record type java instance.
     *
     * @param recordType the record type
     * @param logGUID    log trace ID to help with netlogger
     * @return instance of Record
     * @see ReservedValues for the record types
     */
    public static Record getRecord(String recordType, String logGUID) {
        logger.debug("event=TSRecordFactory.getRecord.start recordType=" + recordType + " guid=" + logGUID);
        Record record = null;
        if (recordType.equals(ReservedValues.RECORD_TYPE_BIDIRECTIONAL_LINK)) {
            record = new BidirectionalLink();
        } else if (recordType.equals(ReservedValues.RECORD_TYPE_BIDIRECTIONAL_PORT)) {
            record = new BidirectionalPort();
        } else if (recordType.equals(ReservedValues.RECORD_TYPE_LINK)) {
            record = new Link();
        } else if (recordType.equals(ReservedValues.RECORD_TYPE_LINK_GROUP)) {
            record = new LinkGroup();
        } else if (recordType.equals(ReservedValues.RECORD_TYPE_NODE)) {
            record = new Node();
        } else if (recordType.equals(ReservedValues.RECORD_TYPE_NSA)) {
            record = new NSA();
        } else if (recordType.equals(ReservedValues.RECORD_TYPE_NSI_SERVICE)) {
            record = new NSIService();
        } else if (recordType.equals(ReservedValues.RECORD_TYPE_PORT)) {
            record = new Port();
        } else if (recordType.equals(ReservedValues.RECORD_TYPE_PORT_GROUP)) {
            record = new PortGroup();
        } else if (recordType.equals(ReservedValues.RECORD_TYPE_TOPOLOGY)) {
            record = new Topology();
        } else {
            // not TS record go to the parent to figure it out
            logger.trace("event=TSRecordFactory.getRecord.calling_recordFactory.start recordType=" + recordType + " guid=" + logGUID);
            record = RecordFactory.getRecord(recordType);
            logger.trace("event=TSRecordFactory.getRecord.calling_recordFactory.end status=0 recordType=" + recordType + " guid=" + logGUID);
        }
        logger.debug("event=TSRecordFactory.getRecord.end status=0 recordType=" + recordType + " guid=" + logGUID);
        return record;
    }

    /**
     * This method will parse the returned result from sLS and generate the appropriate record types
     *
     * @param jsonString
     * @param logGUID    log trace ID to help with netlogger
     * @return List of Records
     * @throws ParserException
     */
    public static List<Record> toRecords(String jsonString, String logGUID) throws ParserException {
        logger.debug("event=TSRecordFactory.toRecords.start guid=" + logGUID);
        List<Record> result = new ArrayList<Record>();

        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        Iterator it = jsonArray.iterator();

        while (it.hasNext()) {
            JSONObject jobj = JSONObject.fromObject(it.next());
            try {
                String type;
                if (jobj.get(net.es.lookup.common.ReservedKeys.RECORD_TYPE) instanceof List) {
                    logger.trace("event=TSRecordFactory.toRecords.getRecordType.start type=list guid=" + logGUID);
                    type = (String) ((List) jobj.get(net.es.lookup.common.ReservedKeys.RECORD_TYPE)).get(0);
                } else {
                    logger.trace("event=TSRecordFactory.toRecords.getRecordType.start type=string guid=" + logGUID);
                    type = (String) jobj.get(net.es.lookup.common.ReservedKeys.RECORD_TYPE);
                }
                logger.trace("event=TSRecordFactory.toRecords.getRecordType.end status=0 recordType="+ type+" guid=" + logGUID);
                Record r = TSRecordFactory.getRecord(type, logGUID);
                r.setMap(jobj);
                result.add(r);
            } catch (RecordException e) {
                logger.debug("event=TSRecordFactory.toRecords.error status=-1 error=\"" + e.getMessage() + "\" guid=" + logGUID);
                throw new ParserException("Error parsing String. Cannot convert to Records");
            }
        }
        logger.debug("event=TSRecordFactory.toRecords.end status=0 guid=" + logGUID);
        return result;
    }

    /**
     * This method will parse the returned result from sLS and generate the appropriate record type
     *
     * @param jsonString
     * @param logGUID    log trace ID to help with netlogger
     * @return List of Records
     * @throws ParserException
     */
    public static Record toRecord(String jsonString, String logGUID) throws ParserException {
        logger.debug("event=TSRecordFactory.toRecord.start guid=" + logGUID);
        JSONObject jsonObject = JSONObject.fromObject(jsonString);

        String type;
        if (jsonObject.get(ReservedKeys.RECORD_TYPE) instanceof List) {
            logger.trace("event=TSRecordFactory.toRecord.getRecordType.start type=list guid=" + logGUID);
            type = (String) ((List) jsonObject.get(ReservedKeys.RECORD_TYPE)).get(0);
        } else {
            logger.trace("event=TSRecordFactory.toRecord.getRecordType.start type=string guid=" + logGUID);
            type = (String) jsonObject.get(ReservedKeys.RECORD_TYPE);
        }
        logger.trace("event=TSRecordFactory.toRecord.getRecordType.end status=0 recordType="+ type+" guid=" + logGUID);
        Record result;
        try {
            result = TSRecordFactory.getRecord(type, logGUID);
            result.setMap(jsonObject);
        } catch (RecordException e) {
            logger.debug("event=TSRecordFactory.toRecord.error status=-1 error=\"" + e.getMessage() + "\" guid=" + logGUID);
            throw new ParserException(e.getMessage());
        }
        logger.debug("event=TSRecordFactory.toRecord.end status=0 guid=" + logGUID);
        return result;
    }
}
