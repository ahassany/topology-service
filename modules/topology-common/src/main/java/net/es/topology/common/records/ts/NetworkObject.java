package net.es.topology.common.records.ts;

import net.es.lookup.records.Record;
import net.es.topology.common.records.ts.keys.ReservedKeys;
import net.es.topology.common.records.ts.keys.ReservedValues;

/**
 *  NetworkObject is the parent abstract class of most objects in NML specification.
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public abstract  class NetworkObject extends Record {
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

    public void setId(String id){
        this.add(ReservedKeys.RECORD_TS_ID, id);
    }

    public void setLifeTime(Lifetime lifetime) {
        this.add(ReservedKeys.RECORD_NETWORKOBJECT_LIFETIME, lifetime);
    }

    public Lifetime getLifetime() {
        return (Lifetime) this.getValue(ReservedKeys.RECORD_NETWORKOBJECT_LIFETIME);
    }

    // TODO (AH): Convert verion to Java datetime.
    public String getVersion() {
        return (String) this.getValue(ReservedKeys.RECORD_NETWORKOBJECT_VERSION);
    }

    public void setVersion(String version){
        this.add(ReservedKeys.RECORD_NETWORKOBJECT_VERSION, version);
    }

    // TODO (AH): add Location
}
