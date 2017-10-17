package com.gunjin.util.testMessage.core;

import org.java_websocket.handshake.ServerHandshake;

import com.isnowfox.core.net.message.Message;

public abstract class MessageHandler{
    public void onOpen(ServerHandshake sh) {
        System.out.println("打开链接");
    }

    public void onError(Exception e) {
        e.printStackTrace();
        System.out.println("发生错误已关闭");
    }

    public void onClose(int arg0, String arg1, boolean arg2) {
        System.out.println("链接已关闭");
    }

    public abstract void onMessage(Message message);

}
