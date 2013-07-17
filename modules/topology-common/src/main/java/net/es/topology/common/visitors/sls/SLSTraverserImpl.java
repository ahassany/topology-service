package net.es.topology.common.visitors.sls;

import net.es.lookup.common.exception.LSClientException;
import net.es.lookup.common.exception.ParserException;
import net.es.topology.common.records.ts.*;
import net.es.topology.common.records.ts.utils.RecordsCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class SLSTraverserImpl implements Traverser {
    private final Logger logger = LoggerFactory.getLogger(SLSTraverserImpl.class);
    RecordsCache cache;
    /**
     * A Unique UUID to identify the log trace within each traverser instance.
     *
     * @see <a href="http://netlogger.lbl.gov/">Netlogger</a> best practices document.
     */
    private String logGUID;

    public SLSTraverserImpl(RecordsCache cache, String logGUID) {
        this.cache = cache;
        this.logGUID = logGUID;
    }

    public SLSTraverserImpl(RecordsCache cache) {
        this(cache, UUID.randomUUID().toString());
    }

    public String getLogGUID() {
        return logGUID;
    }

    public void setLogGUID(String logGUID) {
        this.logGUID = logGUID;
    }

    public RecordsCache getCache() {
        return this.cache;
    }

    public Logger getLogger() {
        return this.logger;
    }

    @Override
    public void traverse(NetworkObject record, Visitor visitor) {

    }

    @Override
    public void traverse(BidirectionalLink record, Visitor visitor) {
        getLogger().trace("event=SLSTraverserImpl.traverse.BidirectionalLink.start recordURN=" + record.getId() + " guid=" + getLogGUID());
        try {

            if (record.getLinks() != null) {
                for (String urn : record.getLinks()) {
                    Port sLSRecord = getCache().getPort(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            if (record.getLinkGroups() != null) {
                for (String urn : record.getLinkGroups()) {
                    Port sLSRecord = getCache().getPort(urn);
                    // to stop cyclic dependencies
                    if (sLSRecord != null && !sLSRecord.getId().equalsIgnoreCase(record.getId())) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
        } catch (LSClientException ex) {
            getLogger().warn("event=SLSTraverserImpl.traverse.BidirectionalLink.warning reason=LSClientException message=\"" + ex.getMessage() + "\" recordURN=" + record.getId() + " guid=" + getLogGUID());
        } catch (ParserException ex) {
            getLogger().warn("event=SLSTraverserImpl.traverse.BidirectionalLink.warning reason=ParserException message=\"" + ex.getMessage() + "\" recordURN=" + record.getId() + " guid=" + getLogGUID());
        }
        getLogger().trace("event=SLSTraverserImpl.traverse.BidirectionalLink.end status=0 recordURN=" + record.getId() + " guid=" + getLogGUID());
    }

    @Override
    public void traverse(BidirectionalPort record, Visitor visitor) {
        getLogger().trace("event=SLSTraverserImpl.traverse.BidirectionalPort.start recordURN=" + record.getId() + " guid=" + getLogGUID());
        try {

            if (record.getPorts() != null) {
                for (String urn : record.getPorts()) {
                    Port sLSRecord = getCache().getPort(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            if (record.getPortGroups() != null) {
                for (String urn : record.getPortGroups()) {
                    Port sLSRecord = getCache().getPort(urn);
                    // to stop cyclic dependencies
                    if (sLSRecord != null && !sLSRecord.getId().equalsIgnoreCase(record.getId())) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
        } catch (LSClientException ex) {
            getLogger().warn("event=SLSTraverserImpl.traverse.BidirectionalPort.warning reason=LSClientException message=\"" + ex.getMessage() + "\" recordURN=" + record.getId() + " guid=" + getLogGUID());
        } catch (ParserException ex) {
            getLogger().warn("event=SLSTraverserImpl.traverse.BidirectionalPort.warning reason=ParserException message=\"" + ex.getMessage() + "\" recordURN=" + record.getId() + " guid=" + getLogGUID());
        }
        getLogger().trace("event=SLSTraverserImpl.traverse.BidirectionalPort.end status=0 recordURN=" + record.getId() + " guid=" + getLogGUID());
    }

    @Override
    public void traverse(Link record, Visitor visitor) {
        getLogger().trace("event=SLSTraverserImpl.traverse.Link.start recordURN=" + record.getId() + " guid=" + getLogGUID());
        try {

            if (record.getNext() != null) {
                for (String urn : record.getNext()) {
                    Port sLSRecord = getCache().getPort(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            if (record.getIsSerialCompoundLink() != null) {
                for (String urn : record.getIsSerialCompoundLink()) {
                    Port sLSRecord = getCache().getPort(urn);
                    // to stop cyclic dependencies
                    if (sLSRecord != null && !sLSRecord.getId().equalsIgnoreCase(record.getId())) {
                        sLSRecord.accept(visitor);
                    }
                }
            }

            if (record.getIsAlias() != null) {
                for (String urn : record.getIsAlias()) {
                    Node sLSRecord = getCache().getNode(urn);
                    // to stop cyclic dependencies
                    if (sLSRecord != null && !sLSRecord.getId().equalsIgnoreCase(record.getId())) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
        } catch (LSClientException ex) {
            getLogger().warn("event=SLSTraverserImpl.traverse.Link.warning reason=LSClientException message=\"" + ex.getMessage() + "\" recordURN=" + record.getId() + " guid=" + getLogGUID());
        } catch (ParserException ex) {
            getLogger().warn("event=SLSTraverserImpl.traverse.Link.warning reason=ParserException message=\"" + ex.getMessage() + "\" recordURN=" + record.getId() + " guid=" + getLogGUID());
        }
        getLogger().trace("event=SLSTraverserImpl.traverse.Link.end status=0 recordURN=" + record.getId() + " guid=" + getLogGUID());
    }

    @Override
    public void traverse(LinkGroup record, Visitor visitor) {
        getLogger().trace("event=SLSTraverserImpl.traverse.LinkGroup.start recordURN=" + record.getId() + " guid=" + getLogGUID());
        try {
            if (record.getLinks() != null) {
                for (String urn : record.getLinks()) {
                    Link sLSRecord = getCache().getLink(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            if (record.getLinkGroups() != null) {
                for (String urn : record.getLinkGroups()) {
                    LinkGroup sLSRecord = getCache().getLinkGroup(urn);
                    // to stop cyclic dependencies
                    if (sLSRecord != null && !sLSRecord.getId().equalsIgnoreCase(record.getId())) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            if (record.getIsSerialCompoundLink() != null) {
                for (String urn : record.getIsSerialCompoundLink()) {
                    LinkGroup sLSRecord = getCache().getLinkGroup(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }

            if (record.getIsAlias() != null) {
                for (String urn : record.getIsAlias()) {
                    Port sLSRecord = getCache().getPort(urn);
                    // to stop cyclic dependencies
                    if (sLSRecord != null && !sLSRecord.getId().equalsIgnoreCase(record.getId())) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
        } catch (LSClientException ex) {
            getLogger().warn("event=SLSTraverserImpl.traverse.LinkGroup.warning reason=LSClientException message=\"" + ex.getMessage() + "\" recordURN=" + record.getId() + " guid=" + getLogGUID());
        } catch (ParserException ex) {
            getLogger().warn("event=SLSTraverserImpl.traverse.LinkGroup.warning reason=ParserException message=\"" + ex.getMessage() + "\" recordURN=" + record.getId() + " guid=" + getLogGUID());
        }
        getLogger().trace("event=SLSTraverserImpl.traverse.LinkGroup.end status=0 recordURN=" + record.getId() + " guid=" + getLogGUID());
    }

    @Override
    public void traverse(Node record, Visitor visitor) {
        getLogger().trace("event=SLSTraverserImpl.traverse.Node.start recordURN=" + record.getId() + " guid=" + getLogGUID());
        try {

            if (record.getHasInboundPort() != null) {
                for (String urn : record.getHasInboundPort()) {
                    Port sLSRecord = getCache().getPort(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            if (record.getHasOutboundPort() != null) {
                for (String urn : record.getHasOutboundPort()) {
                    Port sLSRecord = getCache().getPort(urn);
                    // to stop cyclic dependencies
                    if (sLSRecord != null && !sLSRecord.getId().equalsIgnoreCase(record.getId())) {
                        sLSRecord.accept(visitor);
                    }
                }
            }

            if (record.getHasInboundPortGroup() != null) {
                for (String urn : record.getHasInboundPortGroup()) {
                    PortGroup sLSRecord = getCache().getPortGroup(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }

            if (record.getHasOutboundPortGroup() != null) {
                for (String urn : record.getHasOutboundPortGroup()) {
                    PortGroup sLSRecord = getCache().getPortGroup(urn);
                    // to stop cyclic dependencies
                    if (sLSRecord != null && !sLSRecord.getId().equalsIgnoreCase(record.getId())) {
                        sLSRecord.accept(visitor);
                    }
                }
            }

            if (record.getNodes() != null) {
                for (String urn : record.getNodes()) {
                    Node sLSRecord = getCache().getNode(urn);
                    // to stop cyclic dependencies
                    if (sLSRecord != null && !sLSRecord.getId().equalsIgnoreCase(record.getId())) {
                        sLSRecord.accept(visitor);
                    }
                }
            }

            if (record.getHasService() != null) {
                for (String urn : record.getHasService()) {
                    SwitchingService sLSRecord = getCache().getSwitchingService(urn);
                    // to stop cyclic dependencies
                    if (sLSRecord != null && !sLSRecord.getId().equalsIgnoreCase(record.getId())) {
                        sLSRecord.accept(visitor);
                    }
                }
            }

            if (record.getIsAlias() != null) {
                for (String urn : record.getIsAlias()) {
                    Node sLSRecord = getCache().getNode(urn);
                    // to stop cyclic dependencies
                    if (sLSRecord != null && !sLSRecord.getId().equalsIgnoreCase(record.getId())) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
        } catch (LSClientException ex) {
            getLogger().warn("event=SLSTraverserImpl.traverse.Node.warning reason=LSClientException message=\"" + ex.getMessage() + "\" recordURN=" + record.getId() + " guid=" + getLogGUID());
        } catch (ParserException ex) {
            getLogger().warn("event=SLSTraverserImpl.traverse.Node.warning reason=ParserException message=\"" + ex.getMessage() + "\" recordURN=" + record.getId() + " guid=" + getLogGUID());
        }
        getLogger().trace("event=SLSTraverserImpl.traverse.Node.end status=0 recordURN=" + record.getId() + " guid=" + getLogGUID());
    }

    @Override
    public void traverse(SwitchingService record, Visitor visitor) {
        getLogger().trace("event=SLSTraverserImpl.traverse.SwitchingService.start recordURN=" + record.getId() + " guid=" + getLogGUID());
        try {

            if (record.getHasInboundPort() != null) {
                for (String urn : record.getHasInboundPort()) {
                    Port sLSRecord = getCache().getPort(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            if (record.getHasOutboundPort() != null) {
                for (String urn : record.getHasOutboundPort()) {
                    Port sLSRecord = getCache().getPort(urn);
                    // to stop cyclic dependencies
                    if (sLSRecord != null && !sLSRecord.getId().equalsIgnoreCase(record.getId())) {
                        sLSRecord.accept(visitor);
                    }
                }
            }

            if (record.getHasInboundPortGroup() != null) {
                for (String urn : record.getHasInboundPortGroup()) {
                    PortGroup sLSRecord = getCache().getPortGroup(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }

            if (record.getHasOutboundPortGroup() != null) {
                for (String urn : record.getHasOutboundPortGroup()) {
                    PortGroup sLSRecord = getCache().getPortGroup(urn);
                    // to stop cyclic dependencies
                    if (sLSRecord != null && !sLSRecord.getId().equalsIgnoreCase(record.getId())) {
                        sLSRecord.accept(visitor);
                    }
                }
            }

            if (record.getProvidesLink() != null) {
                for (String urn : record.getProvidesLink()) {
                    Link sLSRecord = getCache().getLink(urn);
                    // to stop cyclic dependencies
                    if (sLSRecord != null && !sLSRecord.getId().equalsIgnoreCase(record.getId())) {
                        sLSRecord.accept(visitor);
                    }
                }
            }

            if (record.getProvidesLinkGroup() != null) {
                for (String urn : record.getProvidesLinkGroup()) {
                    LinkGroup sLSRecord = getCache().getLinkGroup(urn);
                    // to stop cyclic dependencies
                    if (sLSRecord != null && !sLSRecord.getId().equalsIgnoreCase(record.getId())) {
                        sLSRecord.accept(visitor);
                    }
                }
            }

            if (record.getIsAlias() != null) {
                for (String urn : record.getIsAlias()) {
                    Node sLSRecord = getCache().getNode(urn);
                    // to stop cyclic dependencies
                    if (sLSRecord != null && !sLSRecord.getId().equalsIgnoreCase(record.getId())) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
        } catch (LSClientException ex) {
            getLogger().warn("event=SLSTraverserImpl.traverse.SwitchingService.warning reason=LSClientException message=\"" + ex.getMessage() + "\" recordURN=" + record.getId() + " guid=" + getLogGUID());
        } catch (ParserException ex) {
            getLogger().warn("event=SLSTraverserImpl.traverse.SwitchingService.warning reason=ParserException message=\"" + ex.getMessage() + "\" recordURN=" + record.getId() + " guid=" + getLogGUID());
        }
        getLogger().trace("event=SLSTraverserImpl.traverse.SwitchingService.end status=0 recordURN=" + record.getId() + " guid=" + getLogGUID());
    }

    @Override
    public void traverse(AdaptationService record, Visitor visitor) {
        getLogger().trace("event=SLSTraverserImpl.traverse.AdaptationService.start recordURN=" + record.getId() + " guid=" + getLogGUID());
        try {

            if (record.getCanProvidePort() != null) {
                for (String urn : record.getCanProvidePort()) {
                    Port sLSRecord = getCache().getPort(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }

            if (record.getCanProvidePortGroup() != null) {
                for (String urn : record.getCanProvidePortGroup()) {
                    PortGroup sLSRecord = getCache().getPortGroup(urn);
                    // to stop cyclic dependencies
                    if (sLSRecord != null && !sLSRecord.getId().equalsIgnoreCase(record.getId())) {
                        sLSRecord.accept(visitor);
                    }
                }
            }


            if (record.getProvidesPort() != null) {
                for (String urn : record.getProvidesPort()) {
                    Port sLSRecord = getCache().getPort(urn);
                    // to stop cyclic dependencies
                    if (sLSRecord != null && !sLSRecord.getId().equalsIgnoreCase(record.getId())) {
                        sLSRecord.accept(visitor);
                    }
                }
            }

            if (record.getProvidesPortGroup() != null) {
                for (String urn : record.getProvidesPortGroup()) {
                    PortGroup sLSRecord = getCache().getPortGroup(urn);
                    // to stop cyclic dependencies
                    if (sLSRecord != null && !sLSRecord.getId().equalsIgnoreCase(record.getId())) {
                        sLSRecord.accept(visitor);
                    }
                }
            }

            if (record.getIsAlias() != null) {
                for (String urn : record.getIsAlias()) {
                    Node sLSRecord = getCache().getNode(urn);
                    // to stop cyclic dependencies
                    if (sLSRecord != null && !sLSRecord.getId().equalsIgnoreCase(record.getId())) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
        } catch (LSClientException ex) {
            getLogger().warn("event=SLSTraverserImpl.traverse.AdaptationService.warning reason=LSClientException message=\"" + ex.getMessage() + "\" recordURN=" + record.getId() + " guid=" + getLogGUID());
        } catch (ParserException ex) {
            getLogger().warn("event=SLSTraverserImpl.traverse.AdaptationService.warning reason=ParserException message=\"" + ex.getMessage() + "\" recordURN=" + record.getId() + " guid=" + getLogGUID());
        }
        getLogger().trace("event=SLSTraverserImpl.traverse.AdaptationService.end status=0 recordURN=" + record.getId() + " guid=" + getLogGUID());
    }

    @Override
    public void traverse(NSA record, Visitor visitor) {
        getLogger().trace("event=SLSTraverserImpl.traverse.NSA.start recordURN=" + record.getId() + " guid=" + getLogGUID());
        try {
            if (record.getTopologies() != null) {
                for (String urn : record.getTopologies()) {
                    Topology sLSRecord = getCache().getTopology(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            if (record.getPeersWith() != null) {
                for (String urn : record.getPeersWith()) {
                    NSA sLSRecord = getCache().getNSA(urn);
                    if (sLSRecord != null && sLSRecord.getId().equalsIgnoreCase(record.getId()) == false) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            if (record.getManagedBy() != null) {
                for (String urn : record.getManagedBy()) {
                    NSA sLSRecord = getCache().getNSA(urn);
                    if (sLSRecord != null && sLSRecord.getId().equalsIgnoreCase(record.getId()) == false) {
                        sLSRecord.accept(visitor);
                    }
                }

            }
            if (record.getNSIServices() != null) {
                for (String urn : record.getNSIServices()) {
                    NSIService sLSRecord = getCache().getNSIService(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            // TODO (AH) travers admin contact
        } catch (LSClientException ex) {
            getLogger().warn("event=SLSTraverserImpl.traverse.NSA.warning reason=LSClientException message=\"" + ex.getMessage() + "\" recordURN=" + record.getId() + " guid=" + getLogGUID());
        } catch (ParserException ex) {
            getLogger().warn("event=SLSTraverserImpl.traverse.NSA.warning reason=ParserException message=\"" + ex.getMessage() + "\" recordURN=" + record.getId() + " guid=" + getLogGUID());
        }
        getLogger().trace("event=SLSTraverserImpl.traverse.NSA.end status=0 recordURN=" + record.getId() + " guid=" + getLogGUID());
    }

    @Override
    public void traverse(NSIService record, Visitor visitor) {

    }

    @Override
    public void traverse(Port record, Visitor visitor) {
        getLogger().trace("event=SLSTraverserImpl.traverse.Port.start recordURN=" + record.getId() + " guid=" + getLogGUID());
        try {
            if (record.getIsSink() != null) {
                for (String urn : record.getIsSink()) {
                    Link sLSRecord = getCache().getLink(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            if (record.getIsSource() != null) {
                for (String urn : record.getIsSource()) {
                    Link sLSRecord = getCache().getLink(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            // TODO (AH) travers hasService
        } catch (LSClientException ex) {
            getLogger().warn("event=SLSTraverserImpl.traverse.Port.warning reason=LSClientException message=\"" + ex.getMessage() + "\" recordURN=" + record.getId() + " guid=" + getLogGUID());
        } catch (ParserException ex) {
            getLogger().warn("event=SLSTraverserImpl.traverse.Port.warning reason=ParserException message=\"" + ex.getMessage() + "\" recordURN=" + record.getId() + " guid=" + getLogGUID());
        }
        getLogger().trace("event=SLSTraverserImpl.traverse.Port.end status=0 recordURN=" + record.getId() + " guid=" + getLogGUID());
    }

    @Override
    public void traverse(PortGroup record, Visitor visitor) {
        getLogger().trace("event=SLSTraverserImpl.traverse.PortGroup.start recordURN=" + record.getId() + " guid=" + getLogGUID());
        try {
            if (record.getPorts() != null) {
                for (String urn : record.getPorts()) {
                    Port sLSRecord = getCache().getPort(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            if (record.getPortGroups() != null) {
                for (String urn : record.getPortGroups()) {
                    PortGroup sLSRecord = getCache().getPortGroup(urn);
                    // to stop cyclic dependencies
                    if (sLSRecord != null && !sLSRecord.getId().equalsIgnoreCase(record.getId())) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            if (record.getIsSink() != null) {
                for (String urn : record.getIsSink()) {
                    LinkGroup sLSRecord = getCache().getLinkGroup(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            if (record.getIsSource() != null) {
                for (String urn : record.getIsSource()) {
                    LinkGroup sLSRecord = getCache().getLinkGroup(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            if (record.getIsAlias() != null) {
                for (String urn : record.getIsAlias()) {
                    Port sLSRecord = getCache().getPort(urn);
                    // to stop cyclic dependencies
                    if (sLSRecord != null && !sLSRecord.getId().equalsIgnoreCase(record.getId())) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
        } catch (LSClientException ex) {
            getLogger().warn("event=SLSTraverserImpl.traverse.PortGroup.warning reason=LSClientException message=\"" + ex.getMessage() + "\" recordURN=" + record.getId() + " guid=" + getLogGUID());
        } catch (ParserException ex) {
            getLogger().warn("event=SLSTraverserImpl.traverse.PortGroup.warning reason=ParserException message=\"" + ex.getMessage() + "\" recordURN=" + record.getId() + " guid=" + getLogGUID());
        }
        getLogger().trace("event=SLSTraverserImpl.traverse.PortGroup.end status=0 recordURN=" + record.getId() + " guid=" + getLogGUID());
    }

    @Override
    public void traverse(Topology record, Visitor visitor) {
        getLogger().trace("event=SLSTraverserImpl.traverse.Topology.start recordURN=" + record.getId() + " guid=" + getLogGUID());
        try {
            if (record.getTopologies() != null) {
                for (String urn : record.getTopologies()) {
                    Topology sLSRecord = getCache().getTopology(urn);
                    if (sLSRecord != null && sLSRecord.getId().equalsIgnoreCase(record.getId()) == false) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            if (record.getPorts() != null) {
                for (String urn : record.getPorts()) {
                    Port sLSRecord = getCache().getPort(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            if (record.getNodes() != null) {
                for (String urn : record.getNodes()) {
                    Node sLSRecord = getCache().getNode(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }

            }
            if (record.getLinks() != null) {
                for (String urn : record.getLinks()) {
                    Link sLSRecord = getCache().getLink(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            if (record.getBidirectionalLinks() != null) {
                for (String urn : record.getBidirectionalLinks()) {
                    BidirectionalLink sLSRecord = getCache().getBidirectionalLink(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            if (record.getBidirectionalPorts() != null) {
                for (String urn : record.getBidirectionalPorts()) {
                    BidirectionalPort sLSRecord = getCache().getBidirectionalPort(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            if (record.getLinkGroups() != null) {
                for (String urn : record.getLinkGroups()) {
                    LinkGroup sLSRecord = getCache().getLinkGroup(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            if (record.getPortGroups() != null) {
                for (String urn : record.getPortGroups()) {
                    PortGroup sLSRecord = getCache().getPortGroup(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            if (record.getHasInboundPort() != null) {
                for (String urn : record.getHasInboundPort()) {
                    Port sLSRecord = getCache().getPort(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            if (record.getHasOutboundPort() != null) {
                for (String urn : record.getHasOutboundPort()) {
                    Port sLSRecord = getCache().getPort(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            if (record.getHasInboundPortGroup() != null) {
                for (String urn : record.getHasInboundPortGroup()) {
                    PortGroup sLSRecord = getCache().getPortGroup(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            if (record.getHasOutboundPortGroup() != null) {
                for (String urn : record.getHasOutboundPortGroup()) {
                    PortGroup sLSRecord = getCache().getPortGroup(urn);
                    if (sLSRecord != null) {
                        sLSRecord.accept(visitor);
                    }
                }
            }
            // TODO (AH): travers services and hasService
        } catch (LSClientException ex) {
            getLogger().warn("event=SLSTraverserImpl.traverse.Topology.warning reason=LSClientException message=\"" + ex.getMessage() + "\" recordURN=" + record.getId() + " guid=" + getLogGUID());
        } catch (ParserException ex) {
            getLogger().warn("event=SLSTraverserImpl.traverse.Topology.warning reason=ParserException message=\"" + ex.getMessage() + "\" recordURN=" + record.getId() + " guid=" + getLogGUID());
        }
        getLogger().trace("event=SLSTraverserImpl.traverse.Topology.end status=0 recordURN=" + record.getId() + " guid=" + getLogGUID());
    }
}
