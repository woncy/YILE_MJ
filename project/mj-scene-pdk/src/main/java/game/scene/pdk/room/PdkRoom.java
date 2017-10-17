package game.scene.pdk.room;

import com.isnowfox.core.net.message.Message;

import game.scene.msg.RoomEndMsg;
import game.scene.pdk.room.poker.PokerChapter;
import mj.data.ChapterEndResult;
import mj.data.Config;
import mj.data.PdkConfig;
import mj.net.handler.MessageHandler;
import mj.net.message.game.VoteDelSelect;
import mj.net.message.game.VoteDelSelectRet;
import mj.net.message.game.VoteDelStart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * 服务器帧率25帧率
 *
 * @author zuoge85@gmail.com on 16/10/7.
 */
public abstract class PdkRoom {
    protected static final Logger log = LoggerFactory.getLogger(PdkRoom.class);
    public static final int FRAME_DELAY_MILLISECONDS = 50;

    protected PdkRoomInfo roomInfo;
    private ScheduledFuture<?> scheduledFuture;
    private RoomAsyncService roomAsyncService;
    protected PdkConfig config;

    //投票记录
    protected Map<Integer, Integer> voteDelInfo = new HashMap<>();

    public PdkRoom(RoomAsyncService roomAsyncService) {
        this.roomAsyncService = roomAsyncService;
    }

    public PdkRoomInfo getRoomInfo() {
        return roomInfo;
    }

    public void setRoomInfo(PdkRoomInfo roomInfo) {
        this.roomInfo = roomInfo;
    }

    @SuppressWarnings("unchecked")
    public void handler(MessageHandler handler, Message message, PdkSceneUser sceneUser) {
        run(() -> {
            PdkSceneUser prevSceneUser = roomInfo.getUserInfo(sceneUser.getLocationIndex());
            if (prevSceneUser != sceneUser && prevSceneUser.getUserId() != sceneUser.getUserId()) {
                throw new RuntimeException(String.format("检查房间内玩家信息,发现冲突 %s", sceneUser));
            }
            handler.handler(message, sceneUser);
        });
    }
    
    

    public void start() {
        scheduledFuture = roomAsyncService.runFrame(roomInfo.getRoomId(), this::frame, FRAME_DELAY_MILLISECONDS);
    }
    public void close() {
        if (!scheduledFuture.cancel(false)) {
            throw new RuntimeException("frame 回调停止失败");
        }
    }

    protected abstract void frame();

    protected void run(Runnable runnable) {
        this.roomAsyncService.run(roomInfo.getRoomId(), runnable);
    }

    protected void checkThread() {
        this.roomAsyncService.checkThread(roomInfo.getRoomId());
    }

    public void sendMessage(Message msg) {
        checkThread();
        sendMessage(msg, null);
    }

    /**
     * 发送给自己和别人不一样的消息
     */
    public void sendMessage(int locationIndex, Message myMsg, Message otherMsg) {
        checkThread();
        for (PdkSceneUser u : roomInfo.getUsers()) {
            if (u != null && u.isJoinGame()) {
                if (u.getLocationIndex() == locationIndex) {
                    u.sendMessage(myMsg);
                } else {
                    u.sendMessage(otherMsg);
                }
            }
        }
    }

    /**
     * 不发送消息给自己
     */
    public void sendMessage(Message msg, PdkSceneUser self) {
        checkThread();
        for (PdkSceneUser u : roomInfo.getUsers()) {
            if (u != null && u.isJoinGame() && u != self) {
                u.sendMessage(msg);
            }
        }
    }

    public void sendMessage(int locationIndex, Message msg) {
        checkThread();
        PdkSceneUser sceneUser = roomInfo.getUserInfo(locationIndex);
        if (sceneUser != null && sceneUser.isJoinGame()) {
            sceneUser.sendMessage(msg);
        }
    }

    public abstract void endChapter(ChapterEndResult endResult, PokerChapter pokerChapter);

    public boolean isStart() {
        return roomInfo.isStart();
    }
    public boolean isEnd() {
        return roomInfo.isEnd();
    }
    public PdkConfig getConfig() {
        return config;
    }
	public void endChapter() {
		
	}
}
