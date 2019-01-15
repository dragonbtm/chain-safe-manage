package com.chain.modules.app.socket.socketio;

import com.chain.common.utils.StringUtils;
import com.chain.config.CommonDataDefine;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;

/**
 * @Author: zz
 * @Description: 与区块链浏览器进行通讯的socketIO
 * @Date: 下午 4:02 2019/1/10 0010
 * @Modified By
 */
public class SocketIoClient {

    private Logger log = LoggerFactory.getLogger(this.getClass());


    public SocketIoClient(String uri) {
        if(StringUtils.isNull(uri)) {
            log.error("uri connot be null~!");
        }else
            connect(uri);

    }

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

            CommonDataDefine.socketIOClientMap.put("explore",socket);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


    }


    public static void main(String[] args) throws URISyntaxException {

      /*  IO.Options options = new IO.Options();
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
        socket.open();*/
    }


}
