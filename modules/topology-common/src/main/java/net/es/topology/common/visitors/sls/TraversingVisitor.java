package net.es.topology.common.visitors.sls;

import net.es.topology.common.records.ts.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class TraversingVisitor implements Visitor {
    private final Logger logger = LoggerFactory.getLogger(TraversingVisitor.class);
    private Visitor visitor;
    private Traverser traverser;
    private boolean traverseFirst;
    private TraversingVisitorProgressMonitor progressMonitor;
    /**
     * A Unique UUID to identify the log trace within each visitor instance.
     *
     * @see <a href="http://netlogger.lbl.gov/">Netlogger</a> best practices document.
     */
    private String logGUID;

    public TraversingVisitor(Traverser traverser, Visitor visitor, String logGUID) {
        this.traverser = traverser;
        this.visitor = visitor;
        this.logGUID = logGUID;
    }

    public TraversingVisitor(Traverser traverser, Visitor visitor) {
        this(traverser, visitor, UUID.randomUUID().toString());
    }

    public String getLogGUID() {
        return logGUID;
    }

    public void setLogGUID(String logGUID) {
        this.logGUID = logGUID;
    }

    public boolean getTraverseFirst() {
        return traverseFirst;
    }

    public void setTraverseFirst(boolean aVisitor) {
        traverseFirst = aVisitor;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor aVisitor) {
        visitor = aVisitor;
    }

    public Traverser getTraverser() {
        return traverser;
    }

    public void setTraverser(Traverser aVisitor) {
        traverser = aVisitor;
    }

    public TraversingVisitorProgressMonitor getProgressMonitor() {
        return progressMonitor;
    }

    public void setProgressMonitor(TraversingVisitorProgressMonitor aVisitor) {
        progressMonitor = aVisitor;
    }

    @Override
    public void visit(NetworkObject record) {
        logger.trace("event=TraversingVisitor.visit.NetworkObject.start recordURI=" + record.getURI() + " guid=" + getLogGUID());
        if (traverseFirst == true) {
            getTraverser().traverse(record, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(record);
            }
        }
        record.accept(getVisitor());
        if (progressMonitor != null) {
            progressMonitor.visited(record);
        }
        if (traverseFirst == false) {
            getTraverser().traverse(record, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(record);
            }
        }
        logger.trace("event=TraversingVisitor.visit.NetworkObject.end status=0 recordURI=" + record.getURI() + " guid=" + getLogGUID());
    }

    @Override
    public void visit(BidirectionalLink record) {
        logger.trace("event=TraversingVisitor.visit.BidirectionalLink.start recordURI=" + record.getURI() + " guid=" + getLogGUID());
        if (traverseFirst == true) {
            getTraverser().traverse(record, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(record);
            }
        }
        record.accept(getVisitor());
        if (progressMonitor != null) {
            progressMonitor.visited(record);
        }
        if (traverseFirst == false) {
            getTraverser().traverse(record, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(record);
            }
        }
        logger.trace("event=TraversingVisitor.visit.BidirectionalLink.end status=0 recordURI=" + record.getURI() + " guid=" + getLogGUID());
    }

    @Override
    public void visit(BidirectionalPort record) {
        logger.trace("event=TraversingVisitor.visit.BidirectionalPort.start recordURI=" + record.getURI() + " guid=" + getLogGUID());
        if (traverseFirst == true) {
            getTraverser().traverse(record, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(record);
            }
        }
        record.accept(getVisitor());
        if (progressMonitor != null) {
            progressMonitor.visited(record);
        }
        if (traverseFirst == false) {
            getTraverser().traverse(record, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(record);
            }
        }
        logger.trace("event=TraversingVisitor.visit.BidirectionalPort.end status=0 recordURI=" + record.getURI() + " guid=" + getLogGUID());
    }

    @Override
    public void visit(Link record) {
        logger.trace("event=TraversingVisitor.visit.Link.start recordURI=" + record.getURI() + " guid=" + getLogGUID());
        if (traverseFirst == true) {
            getTraverser().traverse(record, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(record);
            }
        }
        record.accept(getVisitor());
        if (progressMonitor != null) {
            progressMonitor.visited(record);
        }
        if (traverseFirst == false) {
            getTraverser().traverse(record, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(record);
            }
        }
        logger.trace("event=TraversingVisitor.visit.Link.end status=0 recordURI=" + record.getURI() + " guid=" + getLogGUID());
    }

    @Override
    public void visit(LinkGroup record) {
        logger.trace("event=TraversingVisitor.visit.LinkGroup.start recordURI=" + record.getURI() + " guid=" + getLogGUID());
        if (traverseFirst == true) {
            getTraverser().traverse(record, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(record);
            }
        }
        record.accept(getVisitor());
        if (progressMonitor != null) {
            progressMonitor.visited(record);
        }
        if (traverseFirst == false) {
            getTraverser().traverse(record, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(record);
            }
        }
        logger.trace("event=TraversingVisitor.visit.LinkGroup.end status=0 recordURI=" + record.getURI() + " guid=" + getLogGUID());
    }

    @Override
    public void visit(Node record) {
        logger.trace("event=TraversingVisitor.visit.Node.start recordURI=" + record.getURI() + " guid=" + getLogGUID());
        if (traverseFirst == true) {
            getTraverser().traverse(record, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(record);
            }
        }
        record.accept(getVisitor());
        if (progressMonitor != null) {
            progressMonitor.visited(record);
        }
        if (traverseFirst == false) {
            getTraverser().traverse(record, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(record);
            }
        }
        logger.trace("event=TraversingVisitor.visit.Node.end status=0 recordURI=" + record.getURI() + " guid=" + getLogGUID());
    }

    @Override
    public void visit(NSA record) {
        logger.trace("event=TraversingVisitor.visit.NSA.start recordURI=" + record.getURI() + " guid=" + getLogGUID());
        if (traverseFirst == true) {
            getTraverser().traverse(record, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(record);
            }
        }
        record.accept(getVisitor());
        if (progressMonitor != null) {
            progressMonitor.visited(record);
        }
        if (traverseFirst == false) {
            getTraverser().traverse(record, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(record);
            }
        }
        logger.trace("event=TraversingVisitor.visit.NSA.end status=0 recordURI=" + record.getURI() + " guid=" + getLogGUID());
    }

    @Override
    public void visit(NSIService record) {
        logger.trace("event=TraversingVisitor.visit.NSIService.start recordURI=" + record.getURI() + " guid=" + getLogGUID());
        if (traverseFirst == true) {
            getTraverser().traverse(record, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(record);
            }
        }
        record.accept(getVisitor());
        if (progressMonitor != null) {
            progressMonitor.visited(record);
        }
        if (traverseFirst == false) {
            getTraverser().traverse(record, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(record);
            }
        }
        logger.trace("event=TraversingVisitor.visit.NSIService.end status=0 recordURI=" + record.getURI() + " guid=" + getLogGUID());
    }

    @Override
    public void visit(Port record) {
        logger.trace("event=TraversingVisitor.visit.Port.start recordURI=" + record.getURI() + " guid=" + getLogGUID());
        if (traverseFirst == true) {
            getTraverser().traverse(record, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(record);
            }
        }
        record.accept(getVisitor());
        if (progressMonitor != null) {
            progressMonitor.visited(record);
        }
        if (traverseFirst == false) {
            getTraverser().traverse(record, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(record);
            }
        }
        logger.trace("event=TraversingVisitor.visit.Port.end status=0 recordURI=" + record.getURI() + " guid=" + getLogGUID());
    }

    @Override
    public void visit(PortGroup record) {
        logger.trace("event=TraversingVisitor.visit.PortGroup.start recordURI=" + record.getURI() + " guid=" + getLogGUID());
        if (traverseFirst == true) {
            getTraverser().traverse(record, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(record);
            }
        }
        record.accept(getVisitor());
        if (progressMonitor != null) {
            progressMonitor.visited(record);
        }
        if (traverseFirst == false) {
            getTraverser().traverse(record, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(record);
            }
        }
        logger.trace("event=TraversingVisitor.visit.PortGroup.end status=0 recordURI=" + record.getURI() + " guid=" + getLogGUID());
    }

    @Override
    public void visit(Topology record) {
        logger.trace("event=TraversingVisitor.visit.Topology.start recordURI=" + record.getURI() + " guid=" + getLogGUID());
        if (traverseFirst == true) {
            getTraverser().traverse(record, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(record);
            }
        }
        record.accept(getVisitor());
        if (progressMonitor != null) {
            progressMonitor.visited(record);
        }
        if (traverseFirst == false) {
            getTraverser().traverse(record, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(record);
            }
        }
        logger.trace("event=TraversingVisitor.visit.Topology.end status=0 recordURI=" + record.getURI() + " guid=" + getLogGUID());
    }
}
