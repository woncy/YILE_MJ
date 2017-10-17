package game.zjh.scene.room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isnowfox.core.net.message.Message;

import ZJH.data.ZJHConfig;
import game.zjh.scene.room.poker.ZJHChapter;
import mj.data.ZJHUserPlace;
import mj.net.handler.MessageHandler;
import mj.net.message.game.zjh.VoteDelZJHSelectResult;
import mj.net.message.game.zjh.VoteStart;

/**
 * 服务器帧率25帧率
 *
 * @author zuoge85@gmail.com on 16/10/7.
 */
public abstract class ZJHRoom {
	 protected static final Logger log = LoggerFactory.getLogger(ZJHRoom.class);
	    public static final int FRAME_DELAY_MILLISECONDS = 50;
	    protected ZJHRoomInfo zjhRoomInfo;
	    private ScheduledFuture<?> scheduledFuture;
	    private ZJHRoomAsyncService zjhRoomAsyncService;
	  //投票记录
	    protected Map<Integer, Integer> voteDelInfo = new HashMap<>();
	    protected ZJHConfig config;
	    private boolean end = false;
	    private ArrayList<ZJHUserPlace> userPlases; 
	    public ZJHRoom(ZJHRoomAsyncService roomAsyncService) {
	        this.zjhRoomAsyncService = roomAsyncService;
	    }
	    public ZJHRoomInfo getRoomInfo() {
	        return zjhRoomInfo;
	    }
	    public void setRoomInfo(ZJHRoomInfo roomInfo) {
	        this.zjhRoomInfo = roomInfo;
	    }
	    @SuppressWarnings("unchecked")
	    public void handler(MessageHandler handler, Message message, ZJHSceneUser sceneUser) {
	        run(() -> {
	            ZJHSceneUser prevSceneUser = zjhRoomInfo.getUserInfo(sceneUser.getLocationIndex());
	            if (prevSceneUser != sceneUser && prevSceneUser.getUserId() != sceneUser.getUserId()) {
	                throw new RuntimeException(String.format("检查房间内玩家信息,发现冲突 %s", sceneUser));
	            }
	            handler.handler(message, sceneUser);
	        });
	    }
	    public void start() {
	        scheduledFuture = zjhRoomAsyncService.runFrame(zjhRoomInfo.getRoomId(), this::frame, FRAME_DELAY_MILLISECONDS);
	    }
	    public void close() {
	        if (!scheduledFuture.cancel(false)) {
	            throw new RuntimeException("frame 回调停止失败");
	        }
	    }
	    protected abstract void frame();
	    protected void run(Runnable runnable) {
	        this.zjhRoomAsyncService.run(zjhRoomInfo.getRoomId(), runnable);
	    }
	    protected void checkThread() {
	        this.zjhRoomAsyncService.checkThread(zjhRoomInfo.getRoomId());
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
	        for (ZJHSceneUser u : zjhRoomInfo.getzjhUsers()) {
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
	    public void sendMessage(Message msg, ZJHSceneUser my) {
	        checkThread();
	        for (ZJHSceneUser u : zjhRoomInfo.getzjhUsers()) {
	            if (u != null && u.isJoinGame() && u != my) {
	                u.sendMessage(msg);
	            }
	        }
	    }
	    public void sendMessage(int locationIndex, Message msg) {
	        checkThread();
	        ZJHSceneUser sceneUser = zjhRoomInfo.getUserInfo(locationIndex);
	        if (sceneUser != null && sceneUser.isJoinGame()) {
	            sceneUser.sendMessage(msg);
	        }
	    }
	    public abstract void endChapter(ZJHChapter zjhChapter);
	   // public abstract void oneChapter(ZJHChapter zjhChapter, ZJHSceneUser user, int[] shouPaiNum);
	    public boolean isStart() {
	        return zjhRoomInfo.isStart();
	    }
	    public boolean isEnd() {
	        return end;
	    }
	    public void setEnd(boolean end) {
	        this.end = end;
	    }
	    public ZJHConfig getConfig() {
	        return config;
	    }
	    
	    public ArrayList<ZJHUserPlace> getUserPlases() {
			return userPlases;
		}
		public void setUserPlases(ArrayList<ZJHUserPlace> userPlases) {
			this.userPlases = userPlases;
		}
		
		public ZJHRoomInfo getZjhRoomInfo() {
			return zjhRoomInfo;
		}
		public void setZjhRoomInfo(ZJHRoomInfo zjhRoomInfo) {
			this.zjhRoomInfo = zjhRoomInfo;
		}
		public static int getFrameDelayMilliseconds() {
			return FRAME_DELAY_MILLISECONDS;
		}
		public abstract void VoteResult(VoteDelZJHSelectResult msg, ZJHSceneUser user);
	    public abstract void VoteStart(VoteStart msg, ZJHSceneUser user);
}
