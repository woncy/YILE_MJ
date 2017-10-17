package game.scene.douniu.room;

import Douniu.data.DouniuConfig;
import Douniu.data.DouniuEndResult;
import Douniu.data.DouniuUserPlace;

import com.isnowfox.core.net.message.Message;

import game.douniu.scene.msg.DouniuChapterEndMsg;
import game.scene.douniu.room.poker.PokerChapter;
import mj.net.handler.MessageHandler;
import mj.net.message.game.VoteDelSelectRet;
import mj.net.message.game.VoteDelStart;
import mj.net.message.game.douniu.DouniuGameChapterEnd;
import mj.net.message.game.douniu.DouniuVoteDelSelectRet;
import mj.net.message.game.douniu.DouniuVoteDelStart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * 服务器帧率25帧率
 *
 * @author zuoge85@gmail.com on 16/10/7.
 */
public abstract class DouniuRoom {
    protected static final Logger log = LoggerFactory.getLogger(DouniuRoom.class);
    public static final int FRAME_DELAY_MILLISECONDS = 50;
     
    protected DouniuRoomInfo roomInfo;
    private ScheduledFuture<?> scheduledFuture;
    private RoomAsyncService roomAsyncService;
    protected DouniuConfig config;
    private  ArrayList<DouniuUserPlace> douniuUserPlaces;
    
    private boolean end = false;//牌局结束
    
    public void setEnd(boolean end) {
		this.end = end;
	}
	//投票记录
    protected Map<Integer, Integer> voteDelInfo = new HashMap<>();

    public DouniuRoom(RoomAsyncService roomAsyncService) {
        this.roomAsyncService = roomAsyncService;
    }

    public DouniuRoomInfo getRoomInfo() {
        return roomInfo;
    }

    public void setRoomInfo(DouniuRoomInfo roomInfo) {
        this.roomInfo = roomInfo;
    }

    @SuppressWarnings("unchecked")
    public void handler(MessageHandler handler, Message message, DouniuSceneUser sceneUser) {
        run(() -> {
            DouniuSceneUser prevSceneUser = roomInfo.getUserInfo(sceneUser.getLocationIndex());
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
        for (DouniuSceneUser u : roomInfo.getUsers()) {
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
    public void sendMessage(Message msg, DouniuSceneUser my) {
        checkThread();
        for (DouniuSceneUser u : roomInfo.getUsers()) {
            if (u != null && u.isJoinGame() && u != my) {
                u.sendMessage(msg);
            }
        }
    }

    public void sendMessage(int locationIndex, Message msg) {
        checkThread();
        DouniuSceneUser sceneUser = roomInfo.getUserInfo(locationIndex);
        if (sceneUser != null && sceneUser.isJoinGame()) {
            sceneUser.sendMessage(msg);
        }
    }

    public abstract void endChapter(DouniuChapterEndMsg endResult, PokerChapter pokerChapter);
    public abstract void checkDelRoom();
    public boolean isStart() {
        return roomInfo.isStart();
    }
    public boolean isEnd() {
        return roomInfo.isEnd();
    }
    public DouniuConfig getConfig() {
        return config;
    }
	public void endChapter() {
		
	}

	public abstract void voteDelStart(DouniuVoteDelStart msg, DouniuSceneUser user);

    public abstract void voteDelSelect(DouniuVoteDelSelectRet msg, DouniuSceneUser user);

	public ArrayList<DouniuUserPlace> getDouniuUserPlaces() {
		return douniuUserPlaces;
	}

	public void setDouniuUserPlaces(ArrayList<DouniuUserPlace> douniuUserPlaces) {
		this.douniuUserPlaces = douniuUserPlaces;
	}

	
	
}
