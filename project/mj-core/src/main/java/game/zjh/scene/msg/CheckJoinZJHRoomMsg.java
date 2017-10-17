package game.zjh.scene.msg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.isnowfox.game.proxy.message.AbstractSessionPxMsg;

import game.scene.msg.RegSceneMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class CheckJoinZJHRoomMsg  extends AbstractSessionPxMsg{
	 public static final int ID = 13;

	    private short sceneId;
	    /**
	     * 牌局id
	     */
	    private int roomId;
	    /**
	     * 创建用户id
	     */
	    private int createUserId;
	    /**
	     * 用户数
	     */
	    private int userMax;
	    /**
	     * 牌局uuid
	     */
	    private String uuid;
	    /**
	     * 用户
	     */
	    private Map<Integer, Integer> USMap = new HashMap<Integer, Integer>();
	    
	   
	    /**
	     * 房间的check-id,进入id,可以重复,但是不允许同时活跃状态的id 相同
	     */
	    private String roomCheckId;

	    private int joinSessionId;
	    private int joinGatewayId;
	    private int joinUserId;


	    private List<ZJHUserInfo> ZJHuserInfos = new ArrayList<>();
	    private Map<String, String> options = new TreeMap<>();

	    public CheckJoinZJHRoomMsg() {
	        super(ID);
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
	        createUserId = in.readInt();
	        /**
	         * 用户数
	         */
	        userMax = in.readInt();
	        /**
	         * 牌局uuid
	         */
	        uuid = readString(in);
	    
	        /**
	         * 房间的check-id,进入id,可以重复,但是不允许同时活跃状态的id 相同
	         */
	        roomCheckId = readString(in);

	        joinGatewayId = in.readInt();
	        joinSessionId = in.readInt();
	        joinUserId = in.readInt();


	        int size = in.readByte();
	        for (int i = 0; i < size; i++) {
	            ZJHUserInfo userInfo = new ZJHUserInfo();
	            userInfo.decode(in, ctx);
	            ZJHuserInfos.add(userInfo);
	        }
	        size = in.readShort();
	        for (int i = 0; i < size; i++) {
	            options.put(readString(in), readString(in));
	        }
	        
	        size = in.readShort();
	        for (int i = 0; i < size; i++) {
	        	USMap.put(in.readInt(), in.readInt());
	        }
		
	}

	@Override
	public void encode(ByteBuf out) throws Exception {
		 out.writeShort(sceneId);
	        /**牌局id*/
	        out.writeInt(roomId);
	        /**创建用户id*/
	        out.writeInt(createUserId);
	        /**用户数*/
	        out.writeInt(userMax);
	        /**牌局uuid*/
	        writeString(out, uuid);
	      
	        /**房间的check-id,进入id,可以重复,但是不允许同时活跃状态的id 相同*/
	        writeString(out, roomCheckId);
	        out.writeInt(joinGatewayId);
	        out.writeInt(joinSessionId);
	        out.writeInt(joinUserId);

	        out.writeByte(ZJHuserInfos.size());
	        for (ZJHUserInfo u : ZJHuserInfos) {
	            u.encode(out);
	        }
	        out.writeShort(options.size());
	        for (Map.Entry<String, String> entry : options.entrySet()) {
	            writeString(out, entry.getKey());
	            writeString(out, entry.getValue());
	        }
	        
	        out.writeShort(USMap.size());
	        for (Map.Entry<Integer, Integer> entry : USMap.entrySet()) {
//	        	writeString(out, entry.getKey());
//	        	writeString(out, entry.getValue());
	        	out.writeInt(entry.getKey());
	        	out.writeInt(entry.getValue());
	        }
		
	}
	public short getSceneId() {
		return sceneId;
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
	public int getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	public int getUserMax() {
		return userMax;
	}
	public void setUserMax(int userMax) {
		this.userMax = userMax;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public Map<Integer, Integer> getUSMap() {
		return USMap;
	}
	public void setUSMap(Map<Integer, Integer> uSMap) {
		USMap = uSMap;
	}
	public String getRoomCheckId() {
		return roomCheckId;
	}
	public void setRoomCheckId(String roomCheckId) {
		this.roomCheckId = roomCheckId;
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
	
	public int getJoinUserId() {
		return joinUserId;
	}
	
	public void setJoinUserId(int joinUserId) {
		this.joinUserId = joinUserId;
	}
	
	public List<ZJHUserInfo> getZJHuserInfos() {
		return ZJHuserInfos;
	}
	
	public void setZJHuserInfos(List<ZJHUserInfo> zJHuserInfos) {
		ZJHuserInfos = zJHuserInfos;
	}
	
	public Map<String, String> getOptions() {
		return options;
	}
	public void setOptions(Map<String, String> options) {
		this.options = options;
	}
	@Override
	public String toString() {
		return "CheckJoinZJHRoomMsg [sceneId=" + sceneId + ", roomId=" + roomId + ", createUserId=" + createUserId
				+ ", userMax=" + userMax + ", uuid=" + uuid + ", roomCheckId="
				+ roomCheckId + ", joinSessionId=" + joinSessionId + ", joinGatewayId=" + joinGatewayId
				+ ", joinUserId=" + joinUserId + ", ZJHuserInfos=" + ZJHuserInfos + ", options=" + options + "]";
	}
	
	

}
