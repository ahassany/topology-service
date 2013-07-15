package net.es.topology.common.records.ts;

import net.es.lookup.records.Record;
import net.es.topology.common.records.ts.keys.ReservedKeys;
import net.es.topology.common.records.ts.keys.ReservedValues;
import net.es.topology.common.visitors.sls.Visitable;
import net.es.topology.common.visitors.sls.Visitor;
import net.sf.json.JSONArray;

/**
 * NetworkObject is the parent abstract class of most objects in NML specification.
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public abstract class NetworkObject extends Record implements Visitable {
    private Location location = null;

    public NetworkObject() {
        super(ReservedValues.RECORD_TYPE_NETWORKOBJECT);
    }

    public NetworkObject(String recordType) {
        super(recordType);
    }

    /**
     * A work around the fact that sLS changes everything to a list of strings
     *
     * @param value
     * @return
     */
    protected String arrayToString(Object value) {
        if (value instanceof String)
            return (String) value;
        else if (value instanceof JSONArray)
            return (String) ((JSONArray) value).get(0);
        else
            return null;
    }

    public String getName() {
        return arrayToString(this.getValue(ReservedKeys.RECORD_NETWORKOBJECT_NAME));
    }

    public void setName(String name) {
        this.add(ReservedKeys.RECORD_NETWORKOBJECT_NAME, name);
    }

    public String getId() {
        return arrayToString(this.getValue(ReservedKeys.RECORD_TS_ID));
    }

    public void setId(String id) {
        this.add(ReservedKeys.RECORD_TS_ID, id);
    }

    /**
     * the start time and date formatted as ISO 8601 calendar date, and should be a
     * basic (compact) representation with UTC timezone (YYYYMMDDThhmmssZ)
     */
    public String getLifetimeStart() {
        return arrayToString(this.getValue(ReservedKeys.RECORD_LIFETIME_START));
    }

    /**
     * the start time and date formatted as ISO 8601 calendar date, and should be a
     * basic (compact) representation with UTC timezone (YYYYMMDDThhmmssZ)
     *
     * @param start
     */
    public void setLifetimeStart(String start) {
        this.add(ReservedKeys.RECORD_LIFETIME_START, start);
    }

    /**
     * the end time and date formatted as ISO 8601 calendar date, and should be a
     * basic (compact) representation with UTC timezone (YYYYMMDDThhmmssZ)
     *
     * @return
     */
    public String getLifetimeEnd() {
        return arrayToString(this.getValue(ReservedKeys.RECORD_LIFETIME_END));
    }

    /**
     * the end time and date formatted as ISO 8601 calendar date, and should be a
     * basic (compact) representation with UTC timezone (YYYYMMDDThhmmssZ)
     *
     * @param end
     */
    public void setLifetimeEnd(String end) {
        this.add(ReservedKeys.RECORD_LIFETIME_END, end);
    }

    public String getVersion() {
        return arrayToString(this.getValue(ReservedKeys.RECORD_NETWORKOBJECT_VERSION));
    }

    public void setVersion(String version) {
        this.add(ReservedKeys.RECORD_NETWORKOBJECT_VERSION, version);
    }

    /**
     * A reference to a geographical location or area
     * <p/>
     * Though one java object is returned, the value of the location
     * is actually stored in the same record as the network object
     *
     * @return
     */
    public Location getLocation() {
        if (this.location == null) {
            this.location = new Location(this);
        }
        // TODO (AH): deal with address
        return location;
    }

    /**
     * Set a proxy location object
     * <p/>
     * This method copy the location values from the previous networkobject to this
     *
     * @param location
     */
    public void setLocation(Location location) {
        if (location.getId() != null) {
            this.add(ReservedKeys.RECORD_LOCATION_ID, location.getId());
        }
        if (location.getName() != null) {
            this.add(ReservedKeys.RECORD_LOCATION_NAME, location.getName());
        }
        if (location.getAltitude() != null) {
            this.add(ReservedKeys.RECORD_LOCATION_ALTITUDE, location.getAltitude());
        }
        if (location.getLongitude() != null) {
            this.add(ReservedKeys.RECORD_LOCATION_LONGITUDE, location.getLongitude());
        }
        if (location.getLatitude() != null) {
            this.add(ReservedKeys.RECORD_LOCATION_LATITUDE, location.getLatitude());
        }
        if (location.getUnlocode() != null) {
            this.add(ReservedKeys.RECORD_LOCATION_UNLOCODE, location.getUnlocode());
        }

        location.setNetworkObject(this);
        this.location = location;
        // TODO (AH): deal with address
    }

    /**
     * calls the visit method at the visitor
     *
     * @param aVisitor
     */
    public void accept(Visitor aVisitor) {
        aVisitor.visit(this);
    }
}
