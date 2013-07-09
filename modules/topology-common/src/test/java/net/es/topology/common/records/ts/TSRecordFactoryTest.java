package net.es.topology.common.records.ts;

import net.es.lookup.records.Record;
import net.es.topology.common.records.ts.keys.ReservedValues;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class TSRecordFactoryTest {

    private final Logger logger = LoggerFactory.getLogger(TSRecordFactoryTest.class);
    private String logGUID = null;

    /**
     * get the UUID of the current test case
     *
     * @return
     */
    public String getLogGUID() {
        return this.logGUID;
    }

    @Before
    public void setup() {
        // Make sure that each test case has it's own ID to make it easier to trace.
        this.logGUID = UUID.randomUUID().toString();
    }

    @Test
    public void testGetRecord() throws Exception {
        logger.debug("event=TSRecordFactoryTest.testGetRecord.start guid=" + getLogGUID());
        Assert.assertTrue(TSRecordFactory.getRecord(ReservedValues.RECORD_TYPE_NODE, getLogGUID()) instanceof Node);
        Assert.assertTrue(TSRecordFactory.getRecord(ReservedValues.RECORD_TYPE_PORT, getLogGUID()) instanceof Port);
        Assert.assertTrue(TSRecordFactory.getRecord(ReservedValues.RECORD_TYPE_PORT_GROUP, getLogGUID()) instanceof PortGroup);
        Assert.assertTrue(TSRecordFactory.getRecord(ReservedValues.RECORD_TYPE_LINK, getLogGUID()) instanceof Link);
        Assert.assertTrue(TSRecordFactory.getRecord(ReservedValues.RECORD_TYPE_LINK_GROUP, getLogGUID()) instanceof LinkGroup);
        Assert.assertTrue(TSRecordFactory.getRecord(ReservedValues.RECORD_TYPE_BIDIRECTIONAL_LINK, getLogGUID()) instanceof BidirectionalLink);
        Assert.assertTrue(TSRecordFactory.getRecord(ReservedValues.RECORD_TYPE_BIDIRECTIONAL_PORT, getLogGUID()) instanceof BidirectionalPort);
        Assert.assertTrue(TSRecordFactory.getRecord(ReservedValues.RECORD_TYPE_TOPOLOGY, getLogGUID()) instanceof Topology);
        Assert.assertTrue(TSRecordFactory.getRecord(ReservedValues.RECORD_TYPE_NSA, getLogGUID()) instanceof NSA);
        Assert.assertTrue(TSRecordFactory.getRecord(ReservedValues.RECORD_TYPE_NSI_SERVICE, getLogGUID()) instanceof NSIService);
        Assert.assertTrue(TSRecordFactory.getRecord("Blah", getLogGUID()) instanceof Record);
        logger.debug("event=TSRecordFactoryTest.testGetRecord.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testToRecords() throws Exception {
        logger.debug("event=TSRecordFactoryTest.testToRecords.start guid=" + getLogGUID());
        // TODO (AH): create testes once the getRecordType is fixed at the sLS client
        logger.debug("event=TSRecordFactoryTest.testToRecords.end status=0 guid=" + getLogGUID());
    }

    @Test
    public void testToRecord() throws Exception {
        logger.debug("event=TSRecordFactoryTest.testToRecord.start guid=" + getLogGUID());
        // TODO (AH): create testes once the getRecordType is fixed at the sLS client
        logger.debug("event=TSRecordFactoryTest.testToRecord.end status=0 guid=" + getLogGUID());
    }
}
