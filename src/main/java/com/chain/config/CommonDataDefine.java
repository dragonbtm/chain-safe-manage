package com.chain.config;

import io.socket.client.Socket;
import org.java_websocket.client.WebSocketClient;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommonDataDefine {

    public static Map<String, Session> wsMgUserMap = new ConcurrentHashMap<>();

    public static Map<String, WebSocketClient> wsMgClientMap = new ConcurrentHashMap<>();

    public static Map<String, Socket> socketIOClientMap = new ConcurrentHashMap<>();
}
