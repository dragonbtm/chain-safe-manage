package com.chain.modules.app.socket.websocket;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

/**
 * @Author: zz
 * @Description: webSocket 客户端
 * @Date: 上午 10:40 2018/12/3 0003
 * @Modified By
 */

public class MyWebSocketClient extends WebSocketClient {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private Session session;

    public MyWebSocketClient(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public MyWebSocketClient(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        send("Hello, it is me. Mario :)");
        System.out.println("new connection opened");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(String message) {
        System.out.println("received message: " + message);
    }

    @Override
    public void onMessage(ByteBuffer message) {
        System.out.println("received ByteBuffer");
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("an error occurred:" + ex);
    }

    public static void main(String[] args) throws URISyntaxException {

        WebSocketClient client = new MyWebSocketClient(new URI("ws://192.168.5.149:8286"));
        client.connect();
    }



}
