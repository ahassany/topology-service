package net.es.topology.common.records.ts;

import net.es.lookup.records.Record;
import net.es.topology.common.records.ts.keys.ReservedKeys;
import net.es.topology.common.records.ts.keys.ReservedValues;

/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class Location extends Record {

    public Location() {
        super(ReservedValues.RECORD_TYPE_LOCATION);
    }

    public Location(String recordType) {
        super(recordType);
    }

    public String getName() {
        return (String) this.getValue(ReservedKeys.RECORD_LOCATION_NAME);
    }

    public void setName(String name) {
        this.add(ReservedKeys.RECORD_LOCATION_NAME, name);
    }

    public float getLong() {
        return Float.valueOf((String) this.getValue(ReservedKeys.RECORD_LOCATION_LONG));
    }

    public void setLong(float l) {
        this.add(ReservedKeys.RECORD_LOCATION_LONG, Float.toString(l));
    }

    public float getLat() {
        return Float.valueOf((String) this.getValue(ReservedKeys.RECORD_LOCATION_LAT));
    }

    public void setLat(float lat) {
        this.add(ReservedKeys.RECORD_LOCATION_LAT, Float.toString(lat));
    }


    public String getUnlocode() {
        return (String) this.getValue(ReservedKeys.RECORD_LOCATION_UNLOCODE);
    }

    public void setUnlocode(String unlocode) {
        this.add(ReservedKeys.RECORD_LOCATION_UNLOCODE, unlocode);
    }

    public String getId() {
        return (String) this.getValue(ReservedKeys.RECORD_TS_ID);
    }

    public void setId(String id){
        this.add(ReservedKeys.RECORD_TS_ID, id);
    }


    // TODO (AH): parse address! which is completely generic!
}
