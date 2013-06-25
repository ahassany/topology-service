package net.es.topology.common.records.nml;

import net.es.topology.common.records.nml.keys.ReservedValues;
import net.es.topology.common.records.nml.keys.ReservedKeys;

import java.util.List;

/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class Node extends NetworkObject {
    public Node(){
        super(ReservedValues.RECORD_TYPE_NODE);
    }

    public List<String> getHasInboundPort() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_HAS_INBOUND_PORT);
    }

    public void setHasInboundPort(List<String> ports) {
        this.add(ReservedKeys.RECORD_RELATION_HAS_INBOUND_PORT, ports);
    }

    public List<String> getHasOutboundPort() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_HAS_OUTBOUND_PORT);
    }

    public void setHasOutboundPort(List<String> ports) {
        this.add(ReservedKeys.RECORD_RELATION_HAS_OUTBOUND_PORT, ports);
    }

    public List<String> getHasService() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_HAS_SERVICE);
    }

    public void setHasService(List<String> services) {
        this.add(ReservedKeys.RECORD_RELATION_HAS_SERVICE, services);
    }

    public List<String> getIsAlias() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_IS_ALIAS);
    }

    public void setIsAlias(List<String> aliases) {
        this.add(ReservedKeys.RECORD_RELATION_IS_ALIAS, aliases);
    }

}
