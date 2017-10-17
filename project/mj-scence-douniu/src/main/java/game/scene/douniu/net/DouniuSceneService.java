package game.scene.douniu.net;

import com.isnowfox.core.net.message.Message;
import com.isnowfox.game.proxy.message.PxMsg;

import game.douniu.scene.msg.CheckDelDouniuRoomMsg;
import game.douniu.scene.msg.CheckExitDouniuRoomMsg;
import game.douniu.scene.msg.CheckJoinDouniuRoomMsg;
import game.douniu.scene.msg.RegDouniuSceneMsg;
import game.scene.douniu.net.BossClient;
import game.scene.douniu.room.DouniuRoomService;
import game.scene.msg.CheckOfflineRoomMsg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 暴露net包内 的较为安全的方法
 * @author zuoge85@gmail.com on 16/10/5.
 */
@Service
public class DouniuSceneService {
    @Value("${sceneId}")
    private int sceneId;

    @Value("${address}")
    private String address;

    @Value("${port}")
    private int port;

    @Autowired
    private BossClient bossClient;
    @Autowired
    private DouniuRoomService douniuRoomService;
    @Autowired 
    GatewayManager gatewayManager;

    public void onBossConnect() {
        bossClient.writeAndFlush(new RegDouniuSceneMsg((short) sceneId, address, port));
    }

    public void onBossMessage(PxMsg msg) {
        switch (msg.getType()) {
            case CheckJoinDouniuRoomMsg.ID: {
            	douniuRoomService.checkJoinRoom((CheckJoinDouniuRoomMsg) msg);
                break;
            }
             case CheckOfflineRoomMsg.ID: {
            	 douniuRoomService.checkOfflineRoom((CheckOfflineRoomMsg) msg);
                break;
            }
            case CheckExitDouniuRoomMsg.ID: {  //斗牛退出房间
            	douniuRoomService.checkExitRoom((CheckExitDouniuRoomMsg) msg);
                break;
            }
            case CheckDelDouniuRoomMsg.ID: {  //斗牛删除房间消息
            	douniuRoomService.checkDelRoom((CheckDelDouniuRoomMsg) msg);
                break;
            }
        }
    }

    public void sendMessage(int gatewayId, int sessionId, Message msg) {
        gatewayManager.sendMessage(gatewayId, sessionId, msg);
    }
}
