package net.es.topology.common.records.ts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This is useful to store generated elements while traversing any topology to create sLS records
 * <p/>
 * TODO (AH): decouple the interface and the implementation of this collection.
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */


public class RecordsCollection {

    private final Logger logger = LoggerFactory.getLogger(RecordsCollection.class);
    private Map<String, Node> nodes = null;
    private Map<String, Port> ports = null;
    private Map<String, Topology> topologies = null;
    /**
     * A Unique UUID to identify the log trace withing each instance.
     *
     * @see <a href="http://netlogger.lbl.gov/">Netlogger</a> best practices document.
     */
    private String logUUID;

    public RecordsCollection() {
        this.logUUID = UUID.randomUUID().toString();
        this.setNodes(new HashMap<String, Node>());
        this.setPorts(new HashMap<String, Port>());
        this.setTopologies(new HashMap<String, Topology>());
    }

    public RecordsCollection(String logUUID) {
        this.logUUID = logUUID;
        this.setNodes(new HashMap<String, Node>());
        this.setPorts(new HashMap<String, Port>());
        this.setTopologies(new HashMap<String, Topology>());
    }

    public Map<String, Node> getNodes() {

        return nodes;
    }

    public void setNodes(Map<String, Node> nodes) {
        this.nodes = nodes;
    }

    public Map<String, Port> getPorts() {
        return ports;
    }

    public void setPorts(Map<String, Port> ports) {
        this.ports = ports;
    }

    public Map<String, Topology> getTopologies() {
        return topologies;
    }

    public void setTopologies(Map<String, Topology> topologies) {
        this.topologies = topologies;
    }

    /**
     * Create/or return a Port instance
     * If the a Port with the same id already exists this method will return a reference to it
     * If there is not Port with the same id, a new instance will be created
     * <p/>
     * If the ID is null a UUID will be generated.
     *
     * @param id
     * @return Port instance
     */
    public Port portInstance(String id) {
        logger.trace("event=RecordsCollection.portInstance.start id=" + id + " guid=" + this.logUUID);
        Port port = null;
        if (id == null) {
            id = UUID.randomUUID().toString();
            logger.trace("event=RecordsCollection.portInstance.idCreated id=" + id + " status=0 guid=" + this.logUUID);
        }
        if (getPorts().containsKey(id)) {
            port = getPorts().get(id);
        } else {
            port = new Port();
            port.setId(id);
            getPorts().put(id, port);
            logger.trace("event=RecordsCollection.portInstance.newInstanceCreated id=" + id + " status=0 guid=" + this.logUUID);
        }
        logger.trace("event=RecordsCollection.portInstance.end id=" + id + " status=0 guid=" + this.logUUID);
        return port;
    }

    /**
     * Create/or return a Node instance
     * If the a Node with the same id already exists this method will return a reference to it
     * If there is not Node with the same id, a new instance will be created
     * <p/>
     * If the ID is null a UUID will be generated.
     *
     * @param id
     * @return Node instance
     */
    public Node nodeInstance(String id) {
        logger.trace("event=RecordsCollection.nodeInstance.start id=" + id + " guid=" + this.logUUID);
        Node node = null;
        if (id == null) {
            id = UUID.randomUUID().toString();
            logger.trace("event=RecordsCollection.nodeInstance.idCreated id=" + id + " status=0 guid=" + this.logUUID);
        }
        if (getNodes().containsKey(id)) {
            node = getNodes().get(id);
        } else {
            node = new Node();
            node.setId(id);
            getNodes().put(id, node);
            logger.trace("event=RecordsCollection.nodeInstance.newInstanceCreated id=" + id + " status=0 guid=" + this.logUUID);
        }
        logger.trace("event=RecordsCollection.nodeInstance.end id=" + id + " status=0 guid=" + this.logUUID);
        return node;
    }

    /**
     * Create/or return a Topology instance
     * If the a Topology with the same id already exists this method will return a reference to it
     * If there is not Node with the same id, a new instance will be created
     * <p/>
     * If the ID is null a UUID will be generated.
     *
     * @param id
     * @return Topology instance
     */
    public Topology topologyInstance(String id) {
        logger.trace("event=RecordsCollection.topologyInstance.start id=" + id + " guid=" + this.logUUID);
        Topology topology = null;
        if (id == null) {
            id = UUID.randomUUID().toString();
            logger.trace("event=RecordsCollection.topologyInstance.idCreated id=" + id + " status=0 guid=" + this.logUUID);
        }
        if (getTopologies().containsKey(id)) {
            topology = getTopologies().get(id);
        } else {
            topology = new Topology();
            topology.setId(id);
            getTopologies().put(id, topology);
            logger.trace("event=RecordsCollection.topologyInstance.newInstanceCreated id=" + id + " status=0 guid=" + this.logUUID);
        }
        logger.trace("event=RecordsCollection.topologyInstance.end id=" + id + " status=0 guid=" + this.logUUID);
        return topology;
    }

}
