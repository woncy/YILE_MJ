package game.gateway.server;

import java.util.concurrent.ExecutionException;

import com.isnowfox.game.proxy.PxClient;

import game.boss.msg.BossPxMsgFactory;

public final class ZJHSceneClient {
	public static final int THREAND_NUMS = 1;
    private final PxClient pxClient;
    private boolean isConnect = false;

    public ZJHSceneClient(String sceneAddress, int scenePort, ZJHSceneClientHandler ZJHsceneClientHandler) throws Exception {
        pxClient = PxClient.create(new BossPxMsgFactory(), sceneAddress, scenePort, ZJHsceneClientHandler, THREAND_NUMS);
    }

    public void connect() throws Exception {
        pxClient.connectRetry();
    }

    public void close() throws ExecutionException, InterruptedException {
        pxClient.close();
    }

    public void writeAndFlush(Object msg) {
        pxClient.writeAndFlush(msg);
    }

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }
}
