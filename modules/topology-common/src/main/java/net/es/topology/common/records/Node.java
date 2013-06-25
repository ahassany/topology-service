package net.es.topology.common.records;

import net.es.topology.common.records.keys.ReservedValues;

/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class Node extends NetworkObject {
    public Node(){
        super(ReservedValues.RECORD_TYPE_NODE);
    }
}
