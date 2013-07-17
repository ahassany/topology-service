package net.es.topology.common.records.ts;

import net.es.topology.common.records.ts.keys.ReservedKeys;
import net.es.topology.common.records.ts.keys.ReservedValues;
import net.es.topology.common.visitors.sls.Visitable;
import net.es.topology.common.visitors.sls.Visitor;

import java.util.List;

/**
 * An AdaptationService describes the ability that data from one or more Ports can be embedded in the data encoding
 * of one other Port.
 * This is commonly referred to as the embedding of client layer (higher network layer) ports in a server layer
 * (lower network layer) port.
 * <p/>
 * The AdaptationService describes a multiplexing adaptation function,
 * meaning that different channels (the client layer ports) can be embedded in
 * a single data stream (the server layer port).
 * For example multiplexing several VLANs over a single trunk port.
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class AdaptationService extends NetworkObject implements Visitable {
    public AdaptationService() {
        super(ReservedValues.RECORD_TYPE_ADAPTATION_SERVICE);
    }

    public AdaptationService(String recordType) {
        super(recordType);
    }

    /**
     * Assigns an adaptation technology identifier.
     *
     * @return encoding URI specified by a GFD
     */
    public String getAdaptationFunction() {
        return arrayToString(this.getValue(ReservedKeys.RECORD_ADAPTATION_SERVICE_FUNCTION));
    }

    /**
     * Assigns an adaptation technology identifier.
     *
     * @param uri URI specified by a GFD
     */
    public void setAdaptationFunction(String uri) {
        this.add(ReservedKeys.RECORD_ADAPTATION_SERVICE_FUNCTION, uri);
    }

    /**
     * @return list of the URNs of ports CAN provided by the adaptation service
     */
    public List<String> getCanProvidePort() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_CAN_PROVIDE_PORT);
    }

    /**
     * list of the URNs of ports CAN provided by the adaptation service
     *
     * @param ports list of the URNs of ports
     */
    public void setCanProvidePort(List<String> ports) {
        this.add(ReservedKeys.RECORD_RELATION_CAN_PROVIDE_PORT, ports);
    }

    /**
     * @return list of the URNs of port groups CAN provided by the adaptation service
     */
    public List<String> getCanProvidePortGroup() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_CAN_PROVIDE_PORT_GROUP);
    }

    /**
     * list of the URNs of port groups CAN provided by the adaptation service
     *
     * @param ports list of the URNs of port groups
     */
    public void setCanProvidePortGroup(List<String> ports) {
        this.add(ReservedKeys.RECORD_RELATION_CAN_PROVIDE_PORT_GROUP, ports);
    }

    /**
     * @return list of the URNs of ports provided by the adaptation service
     */
    public List<String> getProvidesPort() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_PROVIDES_PORT);
    }

    /**
     * list of the URNs of ports provided by the adaptation service
     *
     * @param ports list of the URNs of ports
     */
    public void setProvidesPort(List<String> ports) {
        this.add(ReservedKeys.RECORD_RELATION_PROVIDES_PORT, ports);
    }

    /**
     * @return list of the URNs of port groups CAN provided by the adaptation service
     */
    public List<String> getProvidesPortGroup() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_PROVIDES_PORT_GROUP);
    }

    /**
     * list of the URNs of port groups provided by the adaptation service
     *
     * @param ports list of the URNs of port groups
     */
    public void setProvidesPortGroup(List<String> ports) {
        this.add(ReservedKeys.RECORD_RELATION_PROVIDES_PORT_GROUP, ports);
    }

    /**
     * isAlias is a relation to another AdaptationService describes that one can be used as the alias of another.
     *
     * @return list of the URNs of the other Nodes.
     */
    public List<String> getIsAlias() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_IS_ALIAS);
    }

    /**
     * isAlias is a relation to another AdaptationService describes that one can be used as the alias of another.
     *
     * @param aliases list of the URNs of the other Nodes.
     */
    public void setIsAlias(List<String> aliases) {
        this.add(ReservedKeys.RECORD_RELATION_IS_ALIAS, aliases);
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
