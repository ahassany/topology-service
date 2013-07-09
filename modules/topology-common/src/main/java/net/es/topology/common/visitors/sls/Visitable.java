package net.es.topology.common.visitors.sls;

/**
 * An interface that is implemented by the TS Records to make them visitable
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public interface Visitable {

    public void accept(Visitor aVisitor);
}
