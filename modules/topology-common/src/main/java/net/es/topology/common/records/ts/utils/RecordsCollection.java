package net.es.topology.common.records.ts.utils;

import net.es.lookup.client.RegistrationClient;
import net.es.lookup.client.SimpleLS;
import net.es.lookup.common.exception.LSClientException;
import net.es.lookup.common.exception.ParserException;
import net.es.lookup.records.Record;
import net.es.topology.common.records.ts.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * This is useful to store generated elements while traversing any topology to create sLS records
 * <p/>
 * TODO (AH): decouple the interface and the implementation of this collection.
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 * @see net.es.topology.common.converter.nml.NMLVisitor for example use
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
    private Map<String, SwitchingService> switchingServices = null;
    private Map<String, AdaptationService> adaptationServices = null;
    private Map<String, DeadaptationService> deadaptationServices = null;
    /**
     * A Unique UUID to identify the log trace withing each instance.
     *
     * @see <a href="http://netlogger.lbl.gov/">Netlogger</a> best practices document.
     */
    private String logGUID;

    public RecordsCollection() {
        this(UUID.randomUUID().toString());
    }

    public RecordsCollection(String logGUID) {
        this.logGUID = logGUID;
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
        this.setSwitchingServices(new HashMap<String, SwitchingService>());
        this.setAdaptationServices(new HashMap<String, AdaptationService>());
        this.setDeadaptationServices(new HashMap<String, DeadaptationService>());
    }

    public String getLogGUID() {
        return this.logGUID;
    }

    public Logger getLogger() {
        return logger;
    }

    public Map<String, SwitchingService> getSwitchingServices() {
        return switchingServices;
    }

    public void setSwitchingServices(Map<String, SwitchingService> switchingServices) {
        this.switchingServices = switchingServices;
    }

    public Map<String, AdaptationService> getAdaptationServices() {
        return this.adaptationServices;
    }

    public void setAdaptationServices(Map<String, AdaptationService> services) {
        this.adaptationServices = services;
    }

    public Map<String, DeadaptationService> getDeadaptationServices() {
        return this.deadaptationServices;
    }

    public void setDeadaptationServices(Map<String, DeadaptationService> services) {
        this.deadaptationServices = services;
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
        logger.trace("event=RecordsCollection.portInstance.start id=" + id + " guid=" + this.logGUID);
        Port port = null;
        if (id == null) {
            id = UUID.randomUUID().toString();
            logger.trace("event=RecordsCollection.portInstance.idCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        if (getPorts().containsKey(id)) {
            port = getPorts().get(id);
        } else {
            port = new Port();
            port.setId(id);
            getPorts().put(id, port);
            logger.trace("event=RecordsCollection.portInstance.newInstanceCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        logger.trace("event=RecordsCollection.portInstance.end id=" + id + " status=0 guid=" + this.logGUID);
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
        logger.trace("event=RecordsCollection.nodeInstance.start id=" + id + " guid=" + this.logGUID);
        Node node = null;
        if (id == null) {
            id = UUID.randomUUID().toString();
            logger.trace("event=RecordsCollection.nodeInstance.idCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        if (getNodes().containsKey(id)) {
            node = getNodes().get(id);
        } else {
            node = new Node();
            node.setId(id);
            getNodes().put(id, node);
            logger.trace("event=RecordsCollection.nodeInstance.newInstanceCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        logger.trace("event=RecordsCollection.nodeInstance.end id=" + id + " status=0 guid=" + this.logGUID);
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
        logger.trace("event=RecordsCollection.topologyInstance.start id=" + id + " guid=" + this.logGUID);
        Topology topology = null;
        if (id == null) {
            id = UUID.randomUUID().toString();
            logger.trace("event=RecordsCollection.topologyInstance.idCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        if (getTopologies().containsKey(id)) {
            topology = getTopologies().get(id);
        } else {
            topology = new Topology();
            topology.setId(id);
            getTopologies().put(id, topology);
            logger.trace("event=RecordsCollection.topologyInstance.newInstanceCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        logger.trace("event=RecordsCollection.topologyInstance.end id=" + id + " status=0 guid=" + this.logGUID);
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
        logger.trace("event=RecordsCollection.linkInstance.start id=" + id + " guid=" + this.logGUID);
        Link link = null;
        if (id == null) {
            id = UUID.randomUUID().toString();
            logger.trace("event=RecordsCollection.linkInstance.idCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        if (getLinks().containsKey(id)) {
            link = getLinks().get(id);
        } else {
            link = new Link();
            link.setId(id);
            getLinks().put(id, link);
            logger.trace("event=RecordsCollection.linkInstance.newInstanceCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        logger.trace("event=RecordsCollection.linkInstance.end id=" + id + " status=0 guid=" + this.logGUID);
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
        logger.trace("event=RecordsCollection.bidirectionalPortInstance.start id=" + id + " guid=" + this.logGUID);
        BidirectionalPort biPort = null;
        if (id == null) {
            id = UUID.randomUUID().toString();
            logger.trace("event=RecordsCollection.bidirectionalPortInstance.idCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        if (getBidirectionalPorts().containsKey(id)) {
            biPort = getBidirectionalPorts().get(id);
        } else {
            biPort = new BidirectionalPort();
            biPort.setId(id);
            getBidirectionalPorts().put(id, biPort);
            logger.trace("event=RecordsCollection.bidirectionalPortInstance.newInstanceCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        logger.trace("event=RecordsCollection.bidirectionalPortInstance.end id=" + id + " status=0 guid=" + this.logGUID);
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
        logger.trace("event=RecordsCollection.bidirectionalLinkInstance.start id=" + id + " guid=" + this.logGUID);
        BidirectionalLink biLink = null;
        if (id == null) {
            id = UUID.randomUUID().toString();
            logger.trace("event=RecordsCollection.bidirectionalLinkInstance.idCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        if (getBidirectionalLinks().containsKey(id)) {
            biLink = getBidirectionalLinks().get(id);
        } else {
            biLink = new BidirectionalLink();
            biLink.setId(id);
            getBidirectionalLinks().put(id, biLink);
            logger.trace("event=RecordsCollection.bidirectionalLinkInstance.newInstanceCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        logger.trace("event=RecordsCollection.bidirectionalLinkInstance.end id=" + id + " status=0 guid=" + this.logGUID);
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
        logger.trace("event=RecordsCollection.portGroupInstance.start id=" + id + " guid=" + this.logGUID);
        PortGroup portGroup = null;
        if (id == null) {
            id = UUID.randomUUID().toString();
            logger.trace("event=RecordsCollection.portGroupInstance.idCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        if (getPortGroups().containsKey(id)) {
            portGroup = getPortGroups().get(id);
        } else {
            portGroup = new PortGroup();
            portGroup.setId(id);
            getPortGroups().put(id, portGroup);
            logger.trace("event=RecordsCollection.portGroupInstance.newInstanceCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        logger.trace("event=RecordsCollection.portGroupInstance.end id=" + id + " status=0 guid=" + this.logGUID);
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
        logger.trace("event=RecordsCollection.linkGroupInstance.start id=" + id + " guid=" + this.logGUID);
        LinkGroup linkGroup = null;
        if (id == null) {
            id = UUID.randomUUID().toString();
            logger.trace("event=RecordsCollection.linkGroupInstance.idCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        if (getLinkGroups().containsKey(id)) {
            linkGroup = getLinkGroups().get(id);
        } else {
            linkGroup = new LinkGroup();
            linkGroup.setId(id);
            getLinkGroups().put(id, linkGroup);
            logger.trace("event=RecordsCollection.linkGroupInstance.newInstanceCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        logger.trace("event=RecordsCollection.linkGroupInstance.end id=" + id + " status=0 guid=" + this.logGUID);
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
        logger.trace("event=RecordsCollection.NSAInstance.start id=" + id + " guid=" + this.logGUID);
        NSA nsa = null;
        if (id == null) {
            id = UUID.randomUUID().toString();
            logger.trace("event=RecordsCollection.NSAInstance.idCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        if (getNSAs().containsKey(id)) {
            nsa = getNSAs().get(id);
        } else {
            nsa = new NSA();
            nsa.setId(id);
            getNSAs().put(id, nsa);
            logger.trace("event=RecordsCollection.NSAInstance.newInstanceCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        logger.trace("event=RecordsCollection.NSAInstance.end id=" + id + " status=0 guid=" + this.logGUID);
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
        logger.trace("event=RecordsCollection.NSIServiceInstance.start id=" + id + " guid=" + this.logGUID);
        NSIService nsiService = null;
        if (id == null) {
            id = UUID.randomUUID().toString();
            logger.trace("event=RecordsCollection.NSIServiceInstance.idCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        if (getNSIServices().containsKey(id)) {
            nsiService = getNSIServices().get(id);
        } else {
            nsiService = new NSIService();
            nsiService.setId(id);
            getNSIServices().put(id, nsiService);
            logger.trace("event=RecordsCollection.NSIServiceInstance.newInstanceCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        logger.trace("event=RecordsCollection.NSIServiceInstance.end id=" + id + " status=0 guid=" + this.logGUID);
        return nsiService;
    }

    /**
     * Create/or return a SwitchingService instance
     * If the a SwitchingService  with the same id already exists this method will return a reference to it
     * If there is not SwitchingService with the same id, a new instance will be created
     * <p/>
     * If the ID is null a UUID will be generated.
     *
     * @param id
     * @return NSI Service instance
     */
    public SwitchingService switchingServiceInstance(String id) {
        logger.trace("event=RecordsCollection.switchingServiceInstance.start id=" + id + " guid=" + this.logGUID);
        SwitchingService switchingService = null;
        if (id == null) {
            id = UUID.randomUUID().toString();
            logger.trace("event=RecordsCollection.switchingServiceInstance.idCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        if (getSwitchingServices().containsKey(id)) {
            switchingService = getSwitchingServices().get(id);
        } else {
            switchingService = new SwitchingService();
            switchingService.setId(id);
            getSwitchingServices().put(id, switchingService);
            logger.trace("event=RecordsCollection.switchingServiceInstance.newInstanceCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        logger.trace("event=RecordsCollection.switchingServiceInstance.end id=" + id + " status=0 guid=" + this.logGUID);
        return switchingService;
    }

    /**
     * Create/or return a AdaptationService instance
     * If the a SwitchingService  with the same id already exists this method will return a reference to it
     * If there is not SwitchingService with the same id, a new instance will be created
     * <p/>
     * If the ID is null a UUID will be generated.
     *
     * @param id
     * @return NSI Service instance
     */
    public AdaptationService adaptationServiceInstance(String id) {
        logger.trace("event=RecordsCollection.adaptationServiceInstance.start id=" + id + " guid=" + this.logGUID);
        AdaptationService adaptationService = null;
        if (id == null) {
            id = UUID.randomUUID().toString();
            logger.trace("event=RecordsCollection.adaptationServiceInstance.idCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        if (getAdaptationServices().containsKey(id)) {
            adaptationService = getAdaptationServices().get(id);
        } else {
            adaptationService = new AdaptationService();
            adaptationService.setId(id);
            getAdaptationServices().put(id, adaptationService);
            logger.trace("event=RecordsCollection.adaptationServiceInstance.newInstanceCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        logger.trace("event=RecordsCollection.adaptationServiceInstance.end id=" + id + " status=0 guid=" + this.logGUID);
        return adaptationService;
    }

    /**
     * Create/or return a DeadaptationService instance
     * If the a SwitchingService  with the same id already exists this method will return a reference to it
     * If there is not SwitchingService with the same id, a new instance will be created
     * <p/>
     * If the ID is null a UUID will be generated.
     *
     * @param id
     * @return NSI Service instance
     */
    public DeadaptationService deadaptationServiceInstance(String id) {
        logger.trace("event=RecordsCollection.deadaptationServiceInstance.start id=" + id + " guid=" + this.logGUID);
        DeadaptationService deadaptationService = null;
        if (id == null) {
            id = UUID.randomUUID().toString();
            logger.trace("event=RecordsCollection.deadaptationServiceInstance.idCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        if (getDeadaptationServices().containsKey(id)) {
            deadaptationService = getDeadaptationServices().get(id);
        } else {
            deadaptationService = new DeadaptationService();
            deadaptationService.setId(id);
            getDeadaptationServices().put(id, deadaptationService);
            logger.trace("event=RecordsCollection.deadaptationServiceInstance.newInstanceCreated id=" + id + " status=0 guid=" + this.logGUID);
        }
        logger.trace("event=RecordsCollection.deadaptationServiceInstance.end id=" + id + " status=0 guid=" + this.logGUID);
        return deadaptationService;
    }

    /**
     * Send the record collection to the sLS(es) services. The registration client dispatcher is used to determine per
     * URN which sLS to be sent to. URNMask is used to mask certain URNS from being saved.
     * <p/>
     * In SLSClientDispatcher clientDispatcher is defined, it will be used to delete existing records to avoid duplicates.
     *
     * @param registrationClientDispatcher
     * @param clientDispatcher
     * @param urnMask
     * @throws LSClientException
     * @throws ParserException
     */
    public void sendTosLS(SLSRegistrationClientDispatcher registrationClientDispatcher, SLSClientDispatcher clientDispatcher, URNMask urnMask) throws LSClientException, ParserException, Exception {
        logger.info("event=RecordsCollection.sendTosLS.start guid=" + this.logGUID);
        // Will be handy to do the rollbacks
        Map<String, NetworkObject> registeredRecords = new HashMap<String, NetworkObject>();

        Collection<NetworkObject> records = new ArrayList<NetworkObject>();
        // Probably there is a better way than this
        for (NetworkObject record : getNodes().values()) {
            records.add(record);
        }
        for (NetworkObject record : getLinks().values()) {
            records.add(record);
        }
        for (NetworkObject record : getLinkGroups().values()) {
            records.add(record);
        }
        for (NetworkObject record : getPorts().values()) {
            records.add(record);
        }
        for (NetworkObject record : getPortGroups().values()) {
            records.add(record);
        }
        for (NetworkObject record : getBidirectionalPorts().values()) {
            records.add(record);
        }
        for (NetworkObject record : getBidirectionalLinks().values()) {
            records.add(record);
        }
        for (NetworkObject record : getTopologies().values()) {
            records.add(record);
        }
        for (NetworkObject record : getNSAs().values()) {
            records.add(record);
        }
        for (NetworkObject record : getNSIServices().values()) {
            records.add(record);
        }
        for (NetworkObject record : getSwitchingServices().values()) {
            records.add(record);
        }
        for (NetworkObject record : getAdaptationServices().values()) {
            records.add(record);
        }
        for (NetworkObject record : getDeadaptationServices().values()) {
            records.add(record);
        }

        for (NetworkObject record : records) {
            if (urnMask.sendToSLS(record.getId()) == false) {
                continue;
            }
            logger.info("event=RecordsCollection.sendTosLS.register.start id=" + record.getId() + " guid=" + this.logGUID);
            boolean isReferenceRecord = false;

            if (clientDispatcher != null) {
                logger.trace("event=RecordsCollection.sendTosLS.register.checkExists.start id=" + record.getId() + " guid=" + this.logGUID);
                SimpleLS client = clientDispatcher.getClient(record.getId());
                String encodedURN = record.getId();
                try {
                    encodedURN = URLEncoder.encode(encodedURN, "utf8");
                } catch (UnsupportedEncodingException e) {
                }
                client.setRelativeUrl("lookup/records?ts-id=" + encodedURN);
                client.connect();
                client.send();
                String resp = client.getResponse();
                List<Record> returnedRecords = TSRecordFactory.toRecords(resp, getLogGUID());


                for (Record record1 : returnedRecords) {
                    logger.trace("event=RecordsCollection.sendTosLS.register.checkExists.delete.start relativeURL=" + record1.getURI() + " id=" + record.getId() + " guid=" + this.logGUID);
                    if (record1.getMap().keySet().size() > record.getMap().keySet().size()) {
                        getLogger().info("don't delete old record");
                        isReferenceRecord = true;
                        break;
                    }
                    SimpleLS deleteClient = clientDispatcher.getClient(record.getId());
                    deleteClient.setConnectionType("DELETE");
                    deleteClient.setRelativeUrl(record1.getURI());
                    deleteClient.send();
                    logger.trace("event=RecordsCollection.sendTosLS.register.checkExists.delete.end status=0 relativeURL=" + record1.getURI() + " id=" + record.getId() + " guid=" + this.logGUID);
                }

                logger.trace("event=RecordsCollection.sendTosLS.register.checkExists.end status=0 id=" + record.getId() + " guid=" + this.logGUID);
            }

            if (isReferenceRecord == true) {
                continue;
            }
            RegistrationClient registrationClient;
            try {
                registrationClient = registrationClientDispatcher.getRegistrationClient(record.getId());
            } catch (Exception e) {
                throw new LSClientException("Couldn't load sls client config: " + e.getMessage());
            }
            registrationClient.setRecord(record);
            registrationClient.register();
            registeredRecords.put(registrationClient.getRelativeUrl(), record);
            logger.info("event=RecordsCollection.sendTosLS.register.end id=" + record.getId() + " relativeURL=" + registrationClient.getRelativeUrl() + " status=0 guid=" + this.logGUID);
        }

        logger.info("event=RecordsCollection.sendTosLS.end status=0 guid=" + this.logGUID);
    }

    /**
     * Send the record collection to the sLS(es) services. The registration client dispatcher is used to determine per
     * URN which sLS to be sent to. URNMask is used to mask certain URNS from being saved.
     *
     * This method wont try to delete the existing records before inserting new ones.
     * @param registrationClientDispatcher
     * @param urnMask
     * @throws LSClientException
     * @throws ParserException
     */
    public void sendTosLSNoDelete(SLSRegistrationClientDispatcher registrationClientDispatcher, URNMask urnMask) throws LSClientException, ParserException, Exception {
        sendTosLS(registrationClientDispatcher, null, urnMask);
    }
}
