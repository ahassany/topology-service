package net.es.topology.common.records.ts;

import net.es.topology.common.records.ts.keys.ReservedKeys;
import net.es.topology.common.records.ts.keys.ReservedValues;
import net.es.topology.common.visitors.sls.Visitable;
import net.es.topology.common.visitors.sls.Visitor;

import java.util.List;

/**
 * A DeadaptationService describes the ability that data of one or more ports can be extracted
 * from the data encoding of one other port. This is commonly referred to as the extraction of
 * client layer (higher network layer) ports from the server layer (lower network layer) port.
 * The DeadaptationService describes a demultiplexing adaptation function, meaning that different channels
 * (the client layer ports) can be extracted from a single data stream (the server layer port).
 * <p/>
 * For example demultiplexing several VLANs from a single trunk port.
 * Like Port and Link, AdaptationService describes a unidirectional transport function.
 * <p/>
 * For the inverse transport function, see AdaptationService.
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class DeadaptationService extends NetworkObject implements Visitable {
    public DeadaptationService() {
        super(ReservedValues.RECORD_TYPE_DEADAPTATION_SERVICE);
    }

    public DeadaptationService(String recordType) {
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
     * list of the URNs of ports CAN provided by the deadaptation service
     *
     * @param ports list of the URNs of ports
     */
    public void setCanProvidePort(List<String> ports) {
        this.add(ReservedKeys.RECORD_RELATION_CAN_PROVIDE_PORT, ports);
    }

    /**
     * @return list of the URNs of port groups CAN provided by the deadaptation service
     */
    public List<String> getCanProvidePortGroup() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_CAN_PROVIDE_PORT_GROUP);
    }

    /**
     * list of the URNs of port groups CAN provided by the deadaptation service
     *
     * @param ports list of the URNs of port groups
     */
    public void setCanProvidePortGroup(List<String> ports) {
        this.add(ReservedKeys.RECORD_RELATION_CAN_PROVIDE_PORT_GROUP, ports);
    }

    /**
     * @return list of the URNs of ports provided by the deadaptation service
     */
    public List<String> getProvidesPort() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_PROVIDES_PORT);
    }

    /**
     * list of the URNs of ports provided by the deadaptation service
     *
     * @param ports list of the URNs of ports
     */
    public void setProvidesPort(List<String> ports) {
        this.add(ReservedKeys.RECORD_RELATION_PROVIDES_PORT, ports);
    }

    /**
     * @return list of the URNs of port groups CAN provided by the deadaptation service
     */
    public List<String> getProvidesPortGroup() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_PROVIDES_PORT_GROUP);
    }

    /**
     * list of the URNs of port groups provided by the deadaptation service
     *
     * @param ports list of the URNs of port groups
     */
    public void setProvidesPortGroup(List<String> ports) {
        this.add(ReservedKeys.RECORD_RELATION_PROVIDES_PORT_GROUP, ports);
    }

    /**
     * isAlias is a relation to another DeadaptationService describes that one can be used as the alias of another.
     *
     * @return list of the URNs of the other Nodes.
     */
    public List<String> getIsAlias() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_IS_ALIAS);
    }

    /**
     * isAlias is a relation to another DeadaptationService describes that one can be used as the alias of another.
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
