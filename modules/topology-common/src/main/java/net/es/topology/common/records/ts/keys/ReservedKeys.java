package net.es.topology.common.records.ts.keys;

/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class ReservedKeys {

    // General
    public static final String RECORD_TS_ID = "ts-id";
    public static final String RECORD_TS_RELATION = "ts-relation";
    public static final String RECORD_TS_NODE = "ts-node";
    public static final String RECORD_TS_PORT = "ts-port";
    public static final String RECORD_TS_PORT_GROUP = "ts-port-group";
    public static final String RECORD_TS_LINK = "ts-link";
    public static final String RECORD_TS_LINK_GROUP = "ts-link-group";
    public static final String RECORD_TS_BIDIRECTIONAL_PORT = "ts-bidirectional-port";
    public static final String RECORD_TS_BIDIRECTIONAL_LINK = "ts-bidirectional-link";
    public static final String RECORD_TS_TOPOLOGY = "ts-topology";
    public static final String RECORD_TS_SWITCHING_SERVICE = "ts-switching-service";
    public static final String RECORD_TS_ADAPTATION_SERVICE = "ts-adaptation-service";
    public static final String RECORD_TS_DEADAPTATION_SERVICE = "ts-deadaptation-service";

    // NetworkObject Keys
    public static final String RECORD_NETWORKOBJECT_NAME = "networkobject-name";
    public static final String RECORD_NETWORKOBJECT_VERSION = "networkobject-version";
    public static final String RECORD_NETWORKOBJECT_LIFETIME = "networkobject-lifetime";

    // Port keys
    public static final String RECORD_PORT_ENCODING = "port-encoding";

    // Link Keys
    public static final String RECORD_LINK_NORETURN_TRAFFIC = "link-noreturntraffic";

    // Services
    public static final String RECORD_SERVICE_LABEL_SWAPPING = "service-label-swapping";
    public static final String RECORD_ADAPTATION_SERVICE_FUNCTION = "service-adaptation-function";

    // Location Keys
    public static final String RECORD_LOCATION_ID = "location-id";
    public static final String RECORD_LOCATION_NAME = "location-name";
    public static final String RECORD_LOCATION_LATITUDE = "location-latitude";
    public static final String RECORD_LOCATION_LONGITUDE = "location-longitude";
    public static final String RECORD_LOCATION_ALTITUDE = "location-altitude";
    public static final String RECORD_LOCATION_UNLOCODE = "location-unlocode";
    public static final String RECORD_LOCATION_ADDRESS = "location-address";


    // LifeTimeType
    public static final String RECORD_LIFETIME_START = "lifetime-start";
    public static final String RECORD_LIFETIME_END = "lifetime-end";


    // Relations
    public static final String RECORD_RELATION_HAS_INBOUND_PORT = "relation-has-inbound-port";
    public static final String RECORD_RELATION_HAS_OUTBOUND_PORT = "relation-has-outbound-port";
    public static final String RECORD_RELATION_HAS_INBOUND_PORT_GROUP = "relation-has-inbound-port-group";
    public static final String RECORD_RELATION_HAS_OUTBOUND_PORT_GROUP = "relation-has-outbound-port-group";
    public static final String RECORD_RELATION_HAS_SERVICE = "relation-has-service";
    public static final String RECORD_RELATION_IS_ALIAS = "relation-is-alias";
    public static final String RECORD_RELATION_IS_SINK = "relation-is-sink";
    public static final String RECORD_RELATION_IS_SOURCE = "relation-is-source";
    public static final String RECORD_RELATION_IS_SERIAL_COMPOUND_LINK = "relation-is-serial-compound-link";
    public static final String RECORD_RELATION_NEXT = "relation-is-next";
    public static final String RECORD_RELATION_PEERS_WITH = "relation-peers-with";
    public static final String RECORD_RELATION_MANAGED_BY = "relation-managed-by";
    public static final String RECORD_RELATION_PROVIDED_BY = "relation-provided-by";
    public static final String RECORD_RELATION_PROVIDES_LINK = "relation-provides-link";
    public static final String RECORD_RELATION_PROVIDES_LINK_GROUP = "relation-provides-link-group";
    public static final String RECORD_RELATION_CAN_PROVIDE_PORT = "relation-can-provide-port";
    public static final String RECORD_RELATION_CAN_PROVIDE_PORT_GROUP = "relation-can-provide-port-group";
    public static final String RECORD_RELATION_PROVIDES_PORT = "relation-provides-port";
    public static final String RECORD_RELATION_PROVIDES_PORT_GROUP = "relation-provides-port-group";
    public static final String RECORD_RELATION_HAS_SWITCHING_SERVICE = "relation-has-switching-service";
    public static final String RECORD_RELATION_HAS_ADAPTATION_SERVICE = "relation-has-adaptation-service";
    public static final String RECORD_RELATION_HAS_DEADAPTATION_SERVICE = "relation-has-deadaptation-service";

    // Label Keys
    public static final String RECORD_LABEL = "ts-label";
    public static final String RECORD_LABEL_TYPE = "ts-label-type";
    public static final String RECORD_LABEL_GROUP = "ts-label-group";
    public static final String RECORD_LABEL_GROUP_TYPE = "ts-label-group-type";

    // NSI Keys
    public static final String RECORD_NSI_LINK = "nsi-link";
    public static final String RECORD_NSI_SERVICE = "nsi-service";
    public static final String RECORD_NSI_SERVICE_TYPE = "nsi-service-type";
    public static final String RECORD_NSI_DESCRIBED_BY = "nsi-described-by";
}
