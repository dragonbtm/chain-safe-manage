package io.renren.config;

import com.corundumstudio.socketio.SocketIOClient;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommonDataDefine {
    public static Map<String, Session> wsMgUserMap = new ConcurrentHashMap<String, Session>();
    public static Map<String, SocketIOClient> socketIOClientMap = new ConcurrentHashMap<>();
}
