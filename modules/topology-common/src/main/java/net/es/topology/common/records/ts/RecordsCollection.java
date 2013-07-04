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
    private Map<String, Link> links = null;
    private Map<String, Topology> topologies = null;
    private Map<String, BidirectionalPort> bidirectionalPorts = null;
    private Map<String, BidirectionalLink> bidirectionalLinks = null;
    private Map<String, PortGroup> portGroups = null;
    private Map<String, LinkGroup> linkGroups = null;
    private Map<String, NSA> NSAs = null;
    private Map<String, NSIService> NSIServices = null;
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
        this.setLinks(new HashMap<String, Link>());
        this.setTopologies(new HashMap<String, Topology>());
        this.setBidirectionalPorts(new HashMap<String, BidirectionalPort>());
        this.setBidirectionalLinks(new HashMap<String, BidirectionalLink>());
        this.setPortGroups(new HashMap<String, PortGroup>());
        this.setLinkGroups(new HashMap<String, LinkGroup>());
        this.setNSAs(new HashMap<String, NSA>());
        this.setNSIServices(new HashMap<String, NSIService>());
    }

    public RecordsCollection(String logUUID) {
        this.logUUID = logUUID;
        this.setNodes(new HashMap<String, Node>());
        this.setPorts(new HashMap<String, Port>());
        this.setLinks(new HashMap<String, Link>());
        this.setTopologies(new HashMap<String, Topology>());
        this.setBidirectionalPorts(new HashMap<String, BidirectionalPort>());
        this.setBidirectionalLinks(new HashMap<String, BidirectionalLink>());
        this.setPortGroups(new HashMap<String, PortGroup>());
        this.setLinkGroups(new HashMap<String, LinkGroup>());
        this.setNSAs(new HashMap<String, NSA>());
        this.setNSIServices(new HashMap<String, NSIService>());
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

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    public Map<String, Topology> getTopologies() {
        return topologies;
    }

    public void setTopologies(Map<String, Topology> topologies) {
        this.topologies = topologies;
    }

    public Map<String, BidirectionalPort> getBidirectionalPorts() {
        return bidirectionalPorts;
    }

    public void setBidirectionalPorts(Map<String, BidirectionalPort> bidirectionalPorts) {
        this.bidirectionalPorts = bidirectionalPorts;
    }

    public Map<String, BidirectionalLink> getBidirectionalLinks() {
        return bidirectionalLinks;
    }

    public void setBidirectionalLinks(Map<String, BidirectionalLink> bidirectionalLinks) {
        this.bidirectionalLinks = bidirectionalLinks;
    }

    public Map<String, PortGroup> getPortGroups() {
        return portGroups;
    }

    public void setPortGroups(Map<String, PortGroup> portGroups) {
        this.portGroups = portGroups;
    }

    public Map<String, LinkGroup> getLinkGroups() {
        return linkGroups;
    }

    public void setLinkGroups(Map<String, LinkGroup> linkGroups) {
        this.linkGroups = linkGroups;
    }

    public Map<String, NSA> getNSAs() {
        return NSAs;
    }

    public void setNSAs(Map<String, NSA> NSAs) {
        this.NSAs = NSAs;
    }

    public Map<String, NSIService> getNSIServices() {
        return NSIServices;
    }

    public void setNSIServices(Map<String, NSIService> services) {
        this.NSIServices = services;
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

    /**
     * Create/or return a Link instance
     * If the a Link with the same id already exists this method will return a reference to it
     * If there is not Link with the same id, a new instance will be created
     * <p/>
     * If the ID is null a UUID will be generated.
     *
     * @param id
     * @return Link instance
     */
    public Link linkInstance(String id) {
        logger.trace("event=RecordsCollection.linkInstance.start id=" + id + " guid=" + this.logUUID);
        Link link = null;
        if (id == null) {
            id = UUID.randomUUID().toString();
            logger.trace("event=RecordsCollection.linkInstance.idCreated id=" + id + " status=0 guid=" + this.logUUID);
        }
        if (getLinks().containsKey(id)) {
            link = getLinks().get(id);
        } else {
            link = new Link();
            link.setId(id);
            getLinks().put(id, link);
            logger.trace("event=RecordsCollection.linkInstance.newInstanceCreated id=" + id + " status=0 guid=" + this.logUUID);
        }
        logger.trace("event=RecordsCollection.linkInstance.end id=" + id + " status=0 guid=" + this.logUUID);
        return link;
    }

    /**
     * Create/or return a BidirectionalPort instance
     * If the a BidirectionalPort with the same id already exists this method will return a reference to it
     * If there is not BidirectionalPort with the same id, a new instance will be created
     * <p/>
     * If the ID is null a UUID will be generated.
     *
     * @param id
     * @return BidirectionalPort instance
     */
    public BidirectionalPort bidirectionalPortInstance(String id) {
        logger.trace("event=RecordsCollection.bidirectionalPortInstance.start id=" + id + " guid=" + this.logUUID);
        BidirectionalPort biPort = null;
        if (id == null) {
            id = UUID.randomUUID().toString();
            logger.trace("event=RecordsCollection.bidirectionalPortInstance.idCreated id=" + id + " status=0 guid=" + this.logUUID);
        }
        if (getBidirectionalPorts().containsKey(id)) {
            biPort = getBidirectionalPorts().get(id);
        } else {
            biPort = new BidirectionalPort();
            biPort.setId(id);
            getBidirectionalPorts().put(id, biPort);
            logger.trace("event=RecordsCollection.bidirectionalPortInstance.newInstanceCreated id=" + id + " status=0 guid=" + this.logUUID);
        }
        logger.trace("event=RecordsCollection.bidirectionalPortInstance.end id=" + id + " status=0 guid=" + this.logUUID);
        return biPort;
    }

    /**
     * Create/or return a BidirectionalLink instance
     * If the a BidirectionalLink with the same id already exists this method will return a reference to it
     * If there is not BidirectionalLink with the same id, a new instance will be created
     * <p/>
     * If the ID is null a UUID will be generated.
     *
     * @param id
     * @return BidirectionalLink instance
     */
    public BidirectionalLink bidirectionalLinkInstance(String id) {
        logger.trace("event=RecordsCollection.bidirectionalLinkInstance.start id=" + id + " guid=" + this.logUUID);
        BidirectionalLink biLink = null;
        if (id == null) {
            id = UUID.randomUUID().toString();
            logger.trace("event=RecordsCollection.bidirectionalLinkInstance.idCreated id=" + id + " status=0 guid=" + this.logUUID);
        }
        if (getBidirectionalLinks().containsKey(id)) {
            biLink = getBidirectionalLinks().get(id);
        } else {
            biLink = new BidirectionalLink();
            biLink.setId(id);
            getBidirectionalLinks().put(id, biLink);
            logger.trace("event=RecordsCollection.bidirectionalLinkInstance.newInstanceCreated id=" + id + " status=0 guid=" + this.logUUID);
        }
        logger.trace("event=RecordsCollection.bidirectionalLinkInstance.end id=" + id + " status=0 guid=" + this.logUUID);
        return biLink;
    }

    /**
     * Create/or return a PortGroup instance
     * If the a PortGroup with the same id already exists this method will return a reference to it
     * If there is not PortGroup with the same id, a new instance will be created
     * <p/>
     * If the ID is null a UUID will be generated.
     *
     * @param id
     * @return PortGroup instance
     */
    public PortGroup portGroupInstance(String id) {
        logger.trace("event=RecordsCollection.portGroupInstance.start id=" + id + " guid=" + this.logUUID);
        PortGroup portGroup = null;
        if (id == null) {
            id = UUID.randomUUID().toString();
            logger.trace("event=RecordsCollection.portGroupInstance.idCreated id=" + id + " status=0 guid=" + this.logUUID);
        }
        if (getPortGroups().containsKey(id)) {
            portGroup = getPortGroups().get(id);
        } else {
            portGroup = new PortGroup();
            portGroup.setId(id);
            getPortGroups().put(id, portGroup);
            logger.trace("event=RecordsCollection.portGroupInstance.newInstanceCreated id=" + id + " status=0 guid=" + this.logUUID);
        }
        logger.trace("event=RecordsCollection.portGroupInstance.end id=" + id + " status=0 guid=" + this.logUUID);
        return portGroup;
    }

    /**
     * Create/or return a LinkGroup instance
     * If the a LinkGroup with the same id already exists this method will return a reference to it
     * If there is not LinkGroup with the same id, a new instance will be created
     * <p/>
     * If the ID is null a UUID will be generated.
     *
     * @param id
     * @return PortGroup instance
     */
    public LinkGroup linkGroupInstance(String id) {
        logger.trace("event=RecordsCollection.linkGroupInstance.start id=" + id + " guid=" + this.logUUID);
        LinkGroup linkGroup = null;
        if (id == null) {
            id = UUID.randomUUID().toString();
            logger.trace("event=RecordsCollection.linkGroupInstance.idCreated id=" + id + " status=0 guid=" + this.logUUID);
        }
        if (getLinkGroups().containsKey(id)) {
            linkGroup = getLinkGroups().get(id);
        } else {
            linkGroup = new LinkGroup();
            linkGroup.setId(id);
            getLinkGroups().put(id, linkGroup);
            logger.trace("event=RecordsCollection.linkGroupInstance.newInstanceCreated id=" + id + " status=0 guid=" + this.logUUID);
        }
        logger.trace("event=RecordsCollection.linkGroupInstance.end id=" + id + " status=0 guid=" + this.logUUID);
        return linkGroup;
    }

    /**
     * Create/or return a NSA instance
     * If the a NSA with the same id already exists this method will return a reference to it
     * If there is not NSA with the same id, a new instance will be created
     * <p/>
     * If the ID is null a UUID will be generated.
     *
     * @param id
     * @return NSA instance
     */
    public NSA NSAInstance(String id) {
        logger.trace("event=RecordsCollection.NSAInstance.start id=" + id + " guid=" + this.logUUID);
        NSA nsa = null;
        if (id == null) {
            id = UUID.randomUUID().toString();
            logger.trace("event=RecordsCollection.NSAInstance.idCreated id=" + id + " status=0 guid=" + this.logUUID);
        }
        if (getNSAs().containsKey(id)) {
            nsa = getNSAs().get(id);
        } else {
            nsa = new NSA();
            nsa.setId(id);
            getNSAs().put(id, nsa);
            logger.trace("event=RecordsCollection.NSAInstance.newInstanceCreated id=" + id + " status=0 guid=" + this.logUUID);
        }
        logger.trace("event=RecordsCollection.NSAInstance.end id=" + id + " status=0 guid=" + this.logUUID);
        return nsa;
    }


    /**
     * Create/or return a NSI Service instance
     * If the a NSI Service  with the same id already exists this method will return a reference to it
     * If there is not NSI Service  with the same id, a new instance will be created
     * <p/>
     * If the ID is null a UUID will be generated.
     *
     * @param id
     * @return NSI Service instance
     */
    public NSIService NSIServiceInstance(String id) {
        logger.trace("event=RecordsCollection.NSIServiceInstance.start id=" + id + " guid=" + this.logUUID);
        NSIService nsiService = null;
        if (id == null) {
            id = UUID.randomUUID().toString();
            logger.trace("event=RecordsCollection.NSIServiceInstance.idCreated id=" + id + " status=0 guid=" + this.logUUID);
        }
        if (getNSIServices().containsKey(id)) {
            nsiService = getNSIServices().get(id);
        } else {
            nsiService = new NSIService();
            nsiService.setId(id);
            getNSIServices().put(id, nsiService);
            logger.trace("event=RecordsCollection.NSIServiceInstance.newInstanceCreated id=" + id + " status=0 guid=" + this.logUUID);
        }
        logger.trace("event=RecordsCollection.NSIServiceInstance.end id=" + id + " status=0 guid=" + this.logUUID);
        return nsiService;
    }
}
