package net.es.topology.common.records.nml;

import net.es.lookup.records.Record;
import net.es.topology.common.records.nml.keys.ReservedKeys;
import net.es.topology.common.records.nml.keys.ReservedValues;

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
        return (String) this.getValue(ReservedKeys.RECORD_NML_ID);
    }

    public void setId(String id){
        this.add(ReservedKeys.RECORD_NML_ID, id);
    }


    // TODO (AH): add lifetime
    // TODO (AH): add Location
    // TODO (AH): add version
}
