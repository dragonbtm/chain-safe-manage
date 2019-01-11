package io.renren.common.utils;


import com.ch.bizapp.modules.app.websocket.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.URI;

/**
 * @Author: zz
 * @Description:
 * @Date: 下午 5:30 2018/12/1 0001
 * @Modified By
 */
public class SocketHelper {

    private static Logger              log             = LoggerFactory.getLogger(SocketHelper.class);
    private String              ip              = null;
    private int                 port            = 0;
    private Socket              socket          = null;
    private DataOutputStream    outputStream    = null;
    private DataInputStream     inputStream     = null;

    public SocketHelper(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void createConnection() throws Exception {
        try {
            socket = new Socket(ip, port);
            socket.setKeepAlive(false);
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    public synchronized void sendMessage(String sendMessage) throws Exception {
        try {
            if (outputStream == null) {
                outputStream = new DataOutputStream(socket.getOutputStream());
            }
            byte b[] = sendMessage.getBytes("GBK");
            outputStream.write(b);
            outputStream.flush();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    public synchronized String getMessage() throws Exception {
        return getMessage(-1);
    }

    public synchronized String getMessage(int readSize) throws Exception {
        try {
            if (inputStream == null) {
                inputStream =
                        new DataInputStream(
                                new BufferedInputStream(
                                        socket.getInputStream()));
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if (inputStream != null) {
                if (readSize != -1 && readSize > 0) {
                    for (int i = 0; i < readSize; i++) {
                        int read = inputStream.read();
                        if (read == -1) {
                            break;
                        } else {
                            byteArrayOutputStream.write(read);
                        }
                    }
                } else {
                    while (true) {
                        int read = inputStream.read();
                        if (read <= 0) {
                            break;
                        } else {
                            byteArrayOutputStream.write(read);
                        }
                    }
                }
                return new String(byteArrayOutputStream.toByteArray(), "GBK");

            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return null;
    }

    public void shutDownConnection() {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (socket != null) {
                socket.shutdownInput();
                socket.shutdownOutput();
                socket.close();
            }
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) throws Exception {
        String ip = "192.168.5.149";
        int port = 8286;
        URI uri = new URI("ws://"+ip+":"+port);

//        WebSocketClient client = new WebSocketClient(uri) {
//            @Override
//            public void onOpen(ServerHandshake serverHandshake) {
//                log.info("打开链接~!");
//            }
//
//            @Override
//            public void onMessage(String s) {
//                if("pong".equals(s)) {
//                    log.info("有心跳");
//
//                }
//            }
//
//            @Override
//            public void onClose(int i, String s, boolean b) {
//                log.info("链接关闭~!");
//            }
//
//            @Override
//            public void onError(Exception e) {
//                log.info("链接异常中断~!");
//            }
//        };
//
//        client.connect();
//        String msg = null;
//        if(client.getReadyState().equals(WebSocket.READYSTATE.OPEN)){
//            client.send("ping");
//        }
//
//        System.out.println(msg);

//        while(!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)){
//            System.out.println("还没有打开");
//        }
//        System.out.println("打开了");
//        client.send("hello world".getBytes("utf-8"));
//
//
//
//        //websocket通信
        WebSocketClient client = new WebSocketClient() {
            @Override
            public void onMessage(String message) {
                if("pong".equals(message)) {
                    log.info("有心跳~!");

                }
            }
            @Override
            public void onError(Session session, Throwable t) {
                super.onError(session, t);
                log.error("链接异常,无心跳~!");
            }
        };
//        WebSocketClient clint = new WebSocketClient() {
//            @Override
//            public void onMessage(String s) {
//                if("pong".equals(s)) {
//                    log.info("有心跳~!");
//
//                }
//            }
//
//            @Override
//            public void onError(javax.websocket.Session session, Throwable t) {
//                super.onError(session, t);
//                log.error("链接异常,无心跳~!");
//            }
//        };

        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        Session session = container.connectToServer(client,uri);
        session.getBasicRemote().sendText("ping");
//
//
//        //socket通讯
//        Socket socket = new Socket(ip,port);
//        BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        String readline;
//        readline = is.readLine(); //从系统标准输入读入一字符串
//
//
//        String send = "message";
//
//        SocketHelper socketHelper = null;
//        String recvMsg = null;
//        try {
//            socketHelper = new SocketHelper(ip, port);
//            socketHelper.createConnection();
//            socketHelper.sendMessage(send);
//            recvMsg = socketHelper.getMessage();
//        } catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            socketHelper.shutDownConnection();
//        }
//        System.out.println("服务器返回信息：" + recvMsg);
    }

}
