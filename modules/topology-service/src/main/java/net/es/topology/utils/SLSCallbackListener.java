package net.es.topology.utils;

import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.websocket.WebSocketEventListener;
import org.ogf.schemas.nsi._2013._09.messaging.Message;

import java.util.UUID;

/**
 * Helper class to broadcast a message
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class SLSCallbackListener implements WebSocketEventListener {
    private Message msg;
    private String logGUID;

    /**
     * @param urn the URN of the resource to be called
     */
    public SLSCallbackListener(Message msg, String logGUID) {
        this.msg = msg;
        this.logGUID = logGUID;
    }

    /**
     * @param urn the URN of the resource to be called
     */
    public SLSCallbackListener(Message msg) {
        this(msg, UUID.randomUUID().toString());
    }

    public String getLogGUID() {
        return logGUID;
    }

    @Override
    public void onHandshake(WebSocketEvent webSocketEvent) {

    }

    @Override
    public void onMessage(WebSocketEvent webSocketEvent) {

    }

    @Override
    public void onClose(WebSocketEvent webSocketEvent) {

    }

    @Override
    public void onControl(WebSocketEvent webSocketEvent) {

    }

    @Override
    public void onDisconnect(WebSocketEvent webSocketEvent) {

    }

    @Override
    public void onConnect(WebSocketEvent webSocketEvent) {

    }

    @Override
    public void onPreSuspend(AtmosphereResourceEvent atmosphereResourceEvent) {

    }

    @Override
    public void onSuspend(AtmosphereResourceEvent atmosphereResourceEvent) {
        atmosphereResourceEvent.broadcaster().broadcast(msg);
    }

    @Override
    public void onResume(AtmosphereResourceEvent atmosphereResourceEvent) {

    }

    @Override
    public void onDisconnect(AtmosphereResourceEvent atmosphereResourceEvent) {

    }

    @Override
    public void onBroadcast(AtmosphereResourceEvent atmosphereResourceEvent) {

    }

    @Override
    public void onThrowable(AtmosphereResourceEvent atmosphereResourceEvent) {

    }
}
