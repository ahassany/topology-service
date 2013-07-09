package net.es.topology.common.visitors.sls;

/**
 * Useful interface to monitor the behaviour of the traverser.
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public interface TraversingVisitorProgressMonitor {

    public void visited(Visitable aVisitable);

    public void traversed(Visitable aVisitable);
}
