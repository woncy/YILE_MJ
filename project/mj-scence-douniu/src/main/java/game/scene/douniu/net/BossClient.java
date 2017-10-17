package game.scene.douniu.net;

import com.isnowfox.game.proxy.PxClient;

import game.douniu.scene.msg.DouniuScenePxMsgFactory;
import game.scene.msg.ScenePxMsgFactory;

import java.util.concurrent.ExecutionException;

/**
 * 
    * @ClassName: BossClient
    * @Description: 11001端口 bossServer的client端  不需修改
    * @author 13323659953@163.com
    * @date 2017年6月28日
    *
 */
public final class BossClient {
    private static final int THREAND_NUMS = 1;
    private final PxClient pxClient;

    public BossClient(String bossAddress, int bossPort, BossClientHandler bossClientHandler) throws Exception {
        pxClient = PxClient.create(new DouniuScenePxMsgFactory(), bossAddress, bossPort, bossClientHandler, THREAND_NUMS);
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
}
