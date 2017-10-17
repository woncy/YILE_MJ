package game.scene.douniu.net;

import java.util.concurrent.ExecutionException;

import com.isnowfox.game.proxy.PxServer;

import game.boss.msg.BossPxMsgFactory;

/**
 * 
* @ClassName: DouniuSceneServer
* @Description: 已修改完毕，scene服务器，监听12333端口
* @author 13323659953@163.com
* @date 2017年6月28日
*
 */
public class DouniuSceneServer {
    public static final int BOSS_THREAD_NUMS = 2;
    public static final int WORKER_THREAD_NUMS = 4;

    private int port;

    private DouniuSceneHandler messageHandler;
    private PxServer pxServer;

    public void start() throws Exception {
        pxServer = PxServer.create(new BossPxMsgFactory(), port, messageHandler, BOSS_THREAD_NUMS, WORKER_THREAD_NUMS);
        pxServer.start();
    }

    public void close() throws ExecutionException, InterruptedException {
        pxServer.close();
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setMessageHandler(DouniuSceneHandler messageHandler) {
        this.messageHandler = messageHandler;
    }
}
