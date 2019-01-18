package com.chain.modules.app.socket.socketio;

import com.chain.common.utils.StringUtils;
import com.chain.config.CommonConfig;
import com.chain.config.CommonDataDefine;
import com.chain.modules.app.entity.message.WebSocketMessage;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.time.Instant;

/**
 * @Author: zz
 * @Description: 与区块链浏览器进行通讯的socketIO
 * @Date: 下午 4:02 2019/1/10 0010
 * @Modified By
 */
public class SocketIoClient {

    private Logger log = LoggerFactory.getLogger(this.getClass());


    private static String[] onTypes = new String[] {"start","next","prev","new","info","highlightNode","nextPageTransactions"};


    public static Socket getConnect() {
        Socket client = CommonDataDefine.socketIOClientMap.get(CommonConfig.ExplorerName);
        if(client != null) {
            return client;
        }else {
            new SocketIoClient(CommonConfig.getSocketio());
            return getConnect();
        }
    }

    public static Socket getConnect(String url) {
        Socket client = CommonDataDefine.socketIOClientMap.get(CommonConfig.ExplorerName);
        if(client != null) {
            return client;
        }else {
            new SocketIoClient(url);
            return getConnect();
        }
    }


    private SocketIoClient(String uri) {
        if(StringUtils.isNull(uri)) {
            log.error("uri connot be null~!");
        }else
            connect(uri);

    }


//    String backMessage = new WebSocketMessage.Builder()
//            .userid(ruserid)
//            .type(type)
//            .subject(subject)
//            .vers(vers)
//            .ctime(Instant.now().toString())
//            .value(args)
//            .build()
//            .backUpMessage();
//            session.getAsyncRemote().sendText(backMessage);

    /**
     * 链接浏览器  通讯
     * @param uri
     */
    private void connect(String uri) {
        IO.Options options = new IO.Options();
        options.forceNew = true;

        final OkHttpClient client2 = new OkHttpClient();
        options.webSocketFactory = client2;
        options.callFactory = client2;

        final Socket socket;
        try {
            socket = IO.socket(uri, options);
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    log.info("socketio [explore] 链接成功~!");
                    System.out.println("connect");
//                    socket.close();
                }
            });
            socket.on(Socket.EVENT_PING,(args)->{
                socket.send("pong");
            });

            socket.io().on(io.socket.engineio.client.Socket.EVENT_CLOSE, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("engine close");
                    client2.dispatcher().executorService().shutdown();
                }
            });

            socket.on("start",(args)->{
                sendtoexplore(args);
            });
            socket.on("next",(args)->{
                sendtoexplore(args);
            });
            socket.on("prev",(args)->{
                sendtoexplore(args);
            });
            socket.on("new",(args)->{
                sendtoexplore(args);
            });
            socket.on("info",(args)->{
                sendtoexplore(args);
            });
            socket.on("highlightNode",(args)->{
                sendtoexplore(args);
            });
            socket.on("nextPageTransactions",(args)->{
                sendtoexplore(args);
            });
            socket.on("addressInfo",(args)->{
                sendtoexplore(args);
            });


            socket.open();

            CommonDataDefine.socketIOClientMap.put(CommonConfig.ExplorerName,socket);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    /**
     * 发送数据到浏览器
     * @param args
     */
    private void sendtoexplore(Object[] args) {
        CommonDataDefine.wsMgUserMap.forEach((key, session)->{
            String backMessage = new WebSocketMessage.Builder()
                    .userid(key)
                    .ctime(Instant.now().toString())
                    .subject("login")
                    .type("login")
                    .value(args)
                    .vers(CommonConfig.getVers())
                    .build()
                    .backUpMessage();
            session.getAsyncRemote().sendText(backMessage);
        });
    }


    public static void main(String[] args) throws URISyntaxException {

        Socket socket = SocketIoClient.getConnect("http://192.168.5.149:8089");
            socket.emit("start","",(Ack)(parms)->{
                System.out.println(parms);
                socket.on("start",(p)->{
                    System.out.println(p);
                });
            });



//        IO.Options options = new IO.Options();
//        options.forceNew = true;
//
//        final OkHttpClient client2 = new OkHttpClient();
//        options.webSocketFactory = client2;
//        options.callFactory = client2;
//
//        final Socket socket = IO.socket("http://192.168.5.149:8089", options);
//        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                System.out.println("connect");
//                socket.close();
//            }
//        });
//        socket.io().on(io.socket.engineio.client.Socket.EVENT_CLOSE, new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                System.out.println("engine close");
//                client2.dispatcher().executorService().shutdown();
//            }
//        });
//        socket.open();
    }


}
