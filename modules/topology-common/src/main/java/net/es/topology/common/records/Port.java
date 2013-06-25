package net.es.topology.common.records;

import net.es.topology.common.records.keys.ReservedValues;

/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class Port extends NetworkObject {

    public Port(){
        super(ReservedValues.RECORD_TYPE_PORT);
    }

    public Port(String recordType){
        super(recordType);
    }

}
