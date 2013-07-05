package net.es.topology.common.records.ts;

import net.es.lookup.records.Record;
import net.es.topology.common.records.ts.keys.ReservedKeys;
import net.es.topology.common.records.ts.keys.ReservedValues;

/**
 * NetworkObject is the parent abstract class of most objects in NML specification.
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public abstract class NetworkObject extends Record {
    public NetworkObject() {
        super(ReservedValues.RECORD_TYPE_NETWORKOBJECT);
    }

    public NetworkObject(String recordType) {
        super(recordType);
    }

    public String getName() {
        return (String) this.getValue(ReservedKeys.RECORD_NETWORKOBJECT_NAME);
    }

    public void setName(String name) {
        this.add(ReservedKeys.RECORD_NETWORKOBJECT_NAME, name);
    }

    public String getId() {
        return (String) this.getValue(ReservedKeys.RECORD_TS_ID);
    }

    public void setId(String id) {
        this.add(ReservedKeys.RECORD_TS_ID, id);
    }

    public void setLifeTime(Lifetime lifetime) {
        this.add(ReservedKeys.RECORD_NETWORKOBJECT_LIFETIME, lifetime);
    }

    public Lifetime getLifetime() {
        return (Lifetime) this.getValue(ReservedKeys.RECORD_NETWORKOBJECT_LIFETIME);
    }

    // TODO (AH): Convert version to Java datetime.
    public String getVersion() {
        return (String) this.getValue(ReservedKeys.RECORD_NETWORKOBJECT_VERSION);
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
        Location location = new Location(this);
        if (this.getValue(ReservedKeys.RECORD_LOCATION_ID) != null) {
            location.setId((String) this.getValue(ReservedKeys.RECORD_LOCATION_ID));
        }
        if (this.getValue(ReservedKeys.RECORD_LOCATION_NAME) != null) {
            location.setName((String) this.getValue(ReservedKeys.RECORD_LOCATION_NAME));
        }
        if (this.getValue(ReservedKeys.RECORD_LOCATION_ALTITUDE) != null) {
            location.setAltitude((Float) this.getValue(ReservedKeys.RECORD_LOCATION_ALTITUDE));
        }
        if (this.getValue(ReservedKeys.RECORD_LOCATION_LATITUDE) != null) {
            location.setLatitude((Float) this.getValue(ReservedKeys.RECORD_LOCATION_LATITUDE));
        }
        if (this.getValue(ReservedKeys.RECORD_LOCATION_LONGITUDE) != null) {
            location.setLongitude((Float) this.getValue(ReservedKeys.RECORD_LOCATION_LONGITUDE));
        }
        if (this.getValue(ReservedKeys.RECORD_LOCATION_UNLOCODE) != null) {
            location.setUnlocode((String) this.getValue(ReservedKeys.RECORD_LOCATION_UNLOCODE));
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
        // TODO (AH): deal with address
    }
}
