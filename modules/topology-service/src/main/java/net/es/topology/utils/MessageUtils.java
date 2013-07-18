package net.es.topology.utils;

import org.ogf.schemas.nml._2013._05.base.*;
import org.ogf.schemas.nsi._2013._09.messaging.Message;
import org.ogf.schemas.nsi._2013._09.messaging.ObjectFactory;
import org.ogf.schemas.nsi._2013._09.topology.NSAType;
import org.ogf.schemas.nsi._2013._09.topology.NsiServiceType;

import java.util.Collection;
import java.util.List;

/**
 * Helper class to make messages
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class MessageUtils {

    /**
     * Encapsulate a NetworkObject in NSI Message
     *
     * @param networkObject
     * @param msg           the message carrying the contect
     * @return
     * @throws Exception
     */
    public static Message makeMessage(NetworkObject networkObject, Message msg) throws Exception {

        Message.Body body;
        // Make sure body is initialized
        if (msg.getBody() == null) {
            ObjectFactory msgFactory = new ObjectFactory();
            msg.setBody(msgFactory.createMessageBody());
        }
        body = msg.getBody();

        if (networkObject instanceof NSAType) {
            body.getNSA().add((NSAType) networkObject);
        } else if (networkObject instanceof NsiServiceType) {
            body.getService().add((NsiServiceType) networkObject);
        } else if (networkObject instanceof TopologyType) {
            body.getTopology().add((TopologyType) networkObject);
        } else if (networkObject instanceof PortGroupType) {
            body.getPortGroup().add((PortGroupType) networkObject);
        } else if (networkObject instanceof AdaptationServiceType) {
            body.getAdaptationService().add((AdaptationServiceType) networkObject);
        } else if (networkObject instanceof BidirectionalPortType) {
            body.getBidirectionalPort().add((BidirectionalPortType) networkObject);
        } else if (networkObject instanceof BidirectionalLinkType) {
            body.getBidirectionalLink().add((BidirectionalLinkType) networkObject);
        } else if (networkObject instanceof DeadaptationServiceType) {
            body.getDeadaptationService().add((DeadaptationServiceType) networkObject);
        } else if (networkObject instanceof LinkGroupType) {
            body.getLinkGroup().add((LinkGroupType) networkObject);
        } else if (networkObject instanceof NodeType) {
            body.getNode().add((NodeType) networkObject);
        } else if (networkObject instanceof SwitchingServiceType) {
            body.getSwitchingService().add((SwitchingServiceType) networkObject);
        } else if (networkObject instanceof AdaptationServiceType) {
            body.getAdaptationService().add((AdaptationServiceType) networkObject);
        } else if (networkObject instanceof DeadaptationServiceType) {
            body.getDeadaptationService().add((DeadaptationServiceType) networkObject);
        } else if (networkObject instanceof PortType) {
            body.getPort().add((PortType) networkObject);
        } else if (networkObject instanceof LinkType) {
            body.getLink().add((LinkType) networkObject);
        } else
            throw new Exception("Unable to handle networkobject in a message");

        return msg;
    }

    /**
     * Encapsulate a NetworkObject in NSI Message
     *
     * @param networkObject
     * @return
     * @throws Exception
     */
    public static Message makeMessage(NetworkObject networkObject) throws Exception {
        ObjectFactory msgFactory = new ObjectFactory();
        Message msg = msgFactory.createMessage();
        Message.Body body = msgFactory.createMessageBody();
        msg.setBody(body);
        return makeMessage(networkObject, msg);
    }

    /**
     * @param networkObjects
     * @return
     * @throws Exception
     */
    public static Message makeMessageWithListOfSameType(Collection<NetworkObject> networkObjects, Message msg) throws Exception {
        for (NetworkObject networkObject : networkObjects) {
            makeMessage(networkObject, msg);
        }
        return msg;
    }
}
