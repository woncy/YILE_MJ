package game.douniu.scene.msg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

import com.isnowfox.game.proxy.message.AbstractSessionPxMsg;

public class DouniuRecordRoomMsg extends AbstractSessionPxMsg {
	public static final int ID = CheckExitDouniuRoomMsg.ID+1;
    private int roomId;
    private List<DouniuRecordUserRoomMsg> userRoomMsgs;

    public DouniuRecordRoomMsg() {
		super(ID);
	}
    @Override
	public void decode(ByteBuf in, ChannelHandlerContext ctx) throws Exception {
    	roomId = in.readInt();
    	int len = in.readInt();
    	if(len!=-1){
    		userRoomMsgs = new ArrayList<DouniuRecordUserRoomMsg>();
    	}
        for (int i = 0; i < len; i++) {
        	DouniuRecordUserRoomMsg userRoomMsg = new DouniuRecordUserRoomMsg();
        	userRoomMsg.decode(in, ctx);
        	userRoomMsgs.add(userRoomMsg);
        }
    }
    @Override
    public void encode(ByteBuf out) throws Exception {
    	out.writeInt(roomId);
    	int len = -1;
    	if(userRoomMsgs!=null){
    		len = userRoomMsgs.size();
    	}
    	out.writeInt(len);
    	for (int i = 0; i < len; i++) {
    		DouniuRecordUserRoomMsg userRoomMsg = userRoomMsgs.get(i);
    		userRoomMsg.encode(out);
        }
    }
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public List<DouniuRecordUserRoomMsg> getUserRoomMsgs() {
		return userRoomMsgs;
	}
	public void setUserRoomMsgs(List<DouniuRecordUserRoomMsg> userRoomMsgs) {
		this.userRoomMsgs = userRoomMsgs;
	}
	
}
