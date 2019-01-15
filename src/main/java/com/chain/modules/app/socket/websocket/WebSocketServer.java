package com.chain.modules.app.socket.websocket;

import com.alibaba.fastjson.JSON;
import com.chain.common.cache.JedisAPI;
import com.chain.common.utils.StringUtils;
import com.chain.config.CommonConfig;
import com.chain.config.CommonDataDefine;
import com.chain.config.JedisNameConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;


/**
 * webSocket 服务端
 */
@ServerEndpoint(value = "/websocket")
@Component
public class WebSocketServer {
    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    private WebSocketClient client;

    @OnOpen
    public void onOpen(Session session) {
        log.info("Websocket Open:" + session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        log.info("Websocket Close:" + session.getAsyncRemote().toString());
    }

    @OnMessage
    public void onMessage(Session session, String msg) {
        log.info("from websocket客户端的信息：" + msg);
        String rcomm = null;
        String ruserid = null;
        String ruserno = null;
        String rtoken = null;
        String rctime = null;
        Map mapType = null;
        try {
            mapType = JSON.parseObject(msg, Map.class);
            for (Object obj : mapType.keySet()) {
                if (obj.toString().equals("comm")) {
                    rcomm = (String) mapType.get(obj);
                    continue;
                }
                if (obj.toString().equals("userid")) {
                    ruserid = (String) mapType.get(obj);
                    continue;
                }
                if (obj.toString().equals("ctime")) {
                    rctime = String.valueOf(mapType.get(obj));
                    continue;
                }
                if (obj.toString().equals("ruserno")) {
                    ruserno = String.valueOf(mapType.get(obj));
                    continue;
                }
                if (obj.toString().equals("token")) {
                    rtoken = (String) mapType.get(obj);
                    continue;
                }
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return;
        }
        if (StringUtils.isNull(rcomm)) {
            log.info("comm==null");
            return;
        }
        if (rcomm.equals("login")) {
            if (ruserid != null) {
                processLogin(ruserid, rcomm, rctime, rtoken, ruserno, session);
            } else {
                log.info("userid ==null");
                return;
            }
        } else if (rcomm.equals("logout")) {
            if (ruserid != null) {
                processLogout(ruserid, session);
            } else {
                log.info("userid ==null");
                return;
            }
        } else if (rcomm.equals("ping")) {
            processPing(rctime, rcomm, session);
        } else {
            log.info("comm: " + rcomm);
        }
    }

    @OnError
    public void onError(Session session, Throwable error){
        log.error("Websocket Error:" + error.toString());
    }

    /**
     * 处理客户端关闭
     *
     * @param session
     */
    private void processClose(Session session) {
        String userid = null;
        for (Map.Entry<String, Session> entry : CommonDataDefine.wsMgUserMap.entrySet()) {
            Session session_exist = entry.getValue();
            if (session_exist.equals(session)) {
                userid = entry.getKey();
                break;
            }
        }
        if (userid != null) {
            CommonDataDefine.wsMgUserMap.remove(userid);
        }
        JedisAPI.delete(JedisNameConstants.VAL_BIZPUSH_MGUSER_NODE + ":" + userid);
        log.info("UserID: " + userid + " WebSocket Session Closeed id : " + session.getId());
    }

    /**
     * ping 功能
     * <p>
     * {"comm":"ping","ctime":"34343"}
     */
    private void processPing(String ctime, String comm, Session session) {
        log.info("Ping : Session.id :   " + session.getId());
        String backMessage = "{\"comm\":\"" + comm + "\",\"ctime\":\"" + ctime + "\",\"value\":\"ok\"}";
        session.getAsyncRemote().sendText(backMessage);
    }

    /**
     * 登录功能
     * <p>
     * {"comm":"login","userid":"1111-2222-3333-4444","token":"3434","userno":"200000","ctime":"34343"}
     */
    private void processLogin(String userid, String comm, String ctime, String token, String userno, Session session) {
        if (StringUtils.isNull(userid)) {
            log.info("userid==null");
            return;
        }
        //创建与区块链间的通讯
        String url = CommonConfig.getWsUrl() + CommonConfig.getWsUrl();
        URI uri = null;
        try {
            uri = new URI(url);
            org.java_websocket.client.WebSocketClient client = new MyWebSocketClient(uri);
            client.connect();

            // 先删除
            CommonDataDefine.wsMgUserMap.remove(userid);
            CommonDataDefine.wsMgClientMap.remove(userid);

            // bizpush:val:mguser:node
            JedisAPI.delete(JedisNameConstants.VAL_BIZPUSH_MGUSER_NODE + ":" + userid);

            // 后绑定
            CommonDataDefine.wsMgUserMap.put(userid, session);
            CommonDataDefine.wsMgClientMap.put(userid,client);


            // bizpush:val:mguser:node:"userid"
            JedisAPI.set(JedisNameConstants.VAL_BIZPUSH_MGUSER_NODE + ":" + userid, CommonConfig.NODENO);
            String backMessage = "{\"comm\":\"" + comm + "\",\"userid\":\"" + userid + "\",\"ctime\":\"" + ctime
                    + "\",\"value\":\"ok\"}";
            session.getAsyncRemote().sendText(backMessage);
            log.info("Login Process End: Userid : " + userid);

        } catch (URISyntaxException e) {
            log.error("登录失败~!" ,e);
        }
    }

    /**
     * 登出功能
     * <p>
     * {"comm":"logout","userid":"b8f4f107323a465a82d3dd76e0fc3705"}
     */
    private void processLogout(String userid, Session session) {
        if (StringUtils.isNull(userid)) {
            log.info("userid==null");
            return;
        }
        CommonDataDefine.wsMgUserMap.remove(userid);
        // bz:val:mguser:node + 用户id
        JedisAPI.delete(JedisNameConstants.VAL_BIZPUSH_MGUSER_NODE + ":" + userid);
        log.info("Loginout End Process End: Userid : " + userid);
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
