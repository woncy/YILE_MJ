package game.douniu.scene.msg;

import java.util.ArrayList;
import java.util.List;

import com.isnowfox.game.proxy.message.AbstractSessionPxMsg;

import game.boss.SceneUserInfo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class DelRoom2Msg extends AbstractSessionPxMsg {
	
	public static final int ID = 13;
	
	public DelRoom2Msg( ){
		super(ID);
		// TODO 自动生成的构造函数存根
	}

    private short sceneId;
    /**
     * 牌局id
     */
    private int roomId;
    private int createUserId;
  
    private List<SceneUserInfo> infos = new ArrayList<>();
    
    
    
	@Override
	public void decode(ByteBuf in, ChannelHandlerContext ctx) throws Exception {
		sceneId = in.readShort();
        /**
         * 牌局id
         */
        roomId = in.readInt();
        this.createUserId = in.readInt();

        int len = in.readInt();
        if(len!=-1){
        	infos = new ArrayList<SceneUserInfo>();
        }
        for (int i = 0; i < len; i++) {
            SceneUserInfo item = new SceneUserInfo();
            item.decode(in, ctx);
            infos.add(item);
        }
		
	}
	@Override
	public void encode(ByteBuf out) throws Exception {
		 out.writeShort(sceneId);
	        /**牌局id*/
	        out.writeInt(roomId);
	        out.writeInt(createUserId);
	        int len = -1;
	        if(infos!=null){
	        	len = infos.size();
	        }
	        out.writeInt(len);

	        for (int i = 0; i < len; i++) {
	        	SceneUserInfo item = infos.get(i);
	            item.encode(out);
	        }
		
	}


	public short getSceneId() {
		return sceneId;
	}


	public int getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	public void setSceneId(short sceneId) {
		this.sceneId = sceneId;
	}


	public int getRoomId() {
		return roomId;
	}


	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}



	public List<SceneUserInfo> getInfos() {
		return infos;
	}


	public void setInfos(List<SceneUserInfo> infos) {
		this.infos = infos;
	}

	@Override
	public String toString() {
		return "DouniuDelRoomMsg [sceneId=" + sceneId + ", roomId=" + roomId + ", infos=" + infos
				+ "]";
	}

}
