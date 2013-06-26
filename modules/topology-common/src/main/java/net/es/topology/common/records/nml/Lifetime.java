package net.es.topology.common.records.nml;

import net.es.lookup.records.Record;
import net.es.topology.common.records.nml.keys.ReservedKeys;
import net.es.topology.common.records.nml.keys.ReservedValues;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * A Lifetime is an interval between which the object is said to be active
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class Lifetime extends Record {

    public Lifetime() {
        super(ReservedValues.RECORD_TYPE_LIFETIME);
    }

    public Lifetime(String recordType) {
        super(recordType);
    }

    // TODO Make the start and end an actual datetime
    public String getStart() {
        return (String) this.getValue(ReservedKeys.RECORD_LIFETIME_START);
    }

    /**
     * the start time and date formatted as ISO 8601 calendar date, and should be a
     * basic (compact) representation with UTC timezone (YYYYMMDDThhmmssZ)
     *
     * @param start
     */
    public void setStart(String start) {
        this.add(ReservedKeys.RECORD_LIFETIME_START, start);
    }

    public String getEnd() {
        return (String) this.getValue(ReservedKeys.RECORD_LIFETIME_END);
    }

    /**
     * the end time and date formatted as ISO 8601 calendar date, and should be a
     * basic (compact) representation with UTC timezone (YYYYMMDDThhmmssZ)
     *
     * @param end
     */
    public void setEnd(String end) {
        this.add(ReservedKeys.RECORD_LIFETIME_END, end);
    }

}
