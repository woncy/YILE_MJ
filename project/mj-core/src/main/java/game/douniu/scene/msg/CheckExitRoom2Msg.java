package game.douniu.scene.msg;

import com.isnowfox.game.proxy.message.AbstractSessionPxMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class CheckExitRoom2Msg extends AbstractSessionPxMsg {
    public static final int ID = 8;

    private int sceneId;
    /**
     * 牌局id
     */
    private int roomId;

    private int userId;

    private int joinSessionId;
    private int joinGatewayId;
    private boolean result;
    private int score;

    public CheckExitRoom2Msg() {
        super(ID);
    }


    @Override
    public void encode(ByteBuf out) throws Exception {
        out.writeShort(sceneId);
        /**牌局id*/
        out.writeInt(roomId);
        /**用户id*/
        out.writeInt(userId);

        out.writeInt(joinSessionId);
        out.writeInt(joinGatewayId);

        out.writeBoolean(result);
        out.writeInt(score);
    }

    @Override
    public void decode(ByteBuf in, ChannelHandlerContext ctx) throws Exception {
        sceneId = in.readShort();
        /**
         * 牌局id
         */
        roomId = in.readInt();
        /**
         * 创建用户id
         */
        userId = in.readInt();

        joinSessionId = in.readInt();
        joinGatewayId = in.readInt();

        result = in.readBoolean();
        this.score = in.readInt();
    }
    
    

    public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}


	public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public int getJoinSessionId() {
        return joinSessionId;
    }

    public void setJoinSessionId(int joinSessionId) {
        this.joinSessionId = joinSessionId;
    }

    public int getJoinGatewayId() {
        return joinGatewayId;
    }

    public void setJoinGatewayId(int joinGatewayId) {
        this.joinGatewayId = joinGatewayId;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "CheckExitDouniuRoomMsg{" +
                "sceneId=" + sceneId +
                ", roomId=" + roomId +
                ", userId=" + userId +
                ", joinSessionId=" + joinSessionId +
                ", joinGatewayId=" + joinGatewayId +
                ", result=" + result +
                '}';
    }
}
