package game.boss.msg;

import com.isnowfox.game.proxy.PxMsgFactory;

import game.boss.ZJH.msg.DelZJHRoomMsg;
import game.boss.ZJH.msg.JoinZJHRoomMsg;
import game.boss.douniu.msg.DeDouniuRoomMsg;
import game.boss.douniu.msg.DouniuExitRoomMsg;
import game.boss.douniu.msg.JoinDouniuRoomMsg;


public class BossPxMsgFactory extends PxMsgFactory {

    @Override
    protected void init() {
        super.init();
        super.add(RegGatewayMsg.ID, RegGatewayMsg.class);
        super.add(RegSessionMsg.ID, RegSessionMsg.class);
        super.add(JoinDouniuRoomMsg.ID, JoinDouniuRoomMsg.class); //斗牛游戏加入
        super.add(DeDouniuRoomMsg.ID, DeDouniuRoomMsg.class); //斗牛删除房间
        super.add(DouniuExitRoomMsg.ID, DouniuExitRoomMsg.class); //斗牛退出房间
        super.add(JoinRoomMsg.ID, JoinRoomMsg.class);
        super.add(ExitRoomMsg.ID, ExitRoomMsg.class);
        super.add(DelRoomMsg.ID, DelRoomMsg.class);
        super.add(WXPayPxMsg.ID, WXPayPxMsg.class);
        super.add(JoinZJHRoomMsg.ID, JoinZJHRoomMsg.class);
        super.add(DelZJHRoomMsg.ID, DelZJHRoomMsg.class);
       
    }
}
