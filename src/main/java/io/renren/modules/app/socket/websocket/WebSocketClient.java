package io.renren.modules.app.socket.websocket;


import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Author: zz
 * @Description: webSocket 客户端
 * @Date: 上午 10:40 2018/12/3 0003
 * @Modified By
 */

public class WebSocketClient {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private Session session;

    @OnOpen
    public void open(Session session){
        log.info("打开链接~!");
        this.session = session;
    }

    @OnMessage
    public void onMessage(String message){
        log.info("Server send message: " + message);
    }

    @OnClose
    public void onClose(){
        log.info("链接关闭~!");
    }


    @OnError
    public void onError(Session session, Throwable t) {
        log.info("链接异常中断~!");
        t.printStackTrace();
    }

    public void send(String message){
        this.session.getAsyncRemote().sendText(message);
    }

    public void close() throws IOException {
        if(this.session.isOpen()){
            this.session.close();
        }
    }


    public static void main(String[] args) throws URISyntaxException, IOException, DeploymentException, InterruptedException {
        String ip = "192.168.5.149";
        int port = 4000;
        URI uri = new URI("ws://"+ip+":"+port );

        IO.Options options = new IO.Options();
        options.forceNew = true;

        final OkHttpClient client2 = new OkHttpClient();
        options.webSocketFactory = client2;
        options.callFactory = client2;

        final Socket socket = IO.socket("http://192.168.5.149:4000", options);
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("connect");
                socket.close();
            }
        });
        socket.io().on(io.socket.engineio.client.Socket.EVENT_CLOSE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("engine close");
                client2.dispatcher().executorService().shutdown();
            }
        });
        socket.open();




//        WebSocketContainer conmtainer = ContainerProvider.getWebSocketContainer();
//        WebSocketClient client = new WebSocketClient();
//        Session session = conmtainer.connectToServer(client, uri);
//        session.getBasicRemote().sendText("ping");
//        client.send("ping");
//        int turn = 0;
//        while(turn++ < 10){
//            client.send("send text: " + turn);
//            Thread.sleep(1000);
//        }
//        client.close();
    }
}
