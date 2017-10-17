package game.douniu.scene.msg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.isnowfox.game.proxy.message.AbstractSessionPxMsg;

import game.scene.msg.RegSceneMsg;
import game.scene.msg.UserInfo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class CheckJoinDouniuRoomMsg  extends AbstractSessionPxMsg{
	 public static final int ID = RegDouniuSceneMsg.ID + 1;

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
	  /*  private int user0;
	    private int user1;
	    private int user2;
	    private int user3;
	    private int user4;
	    private int user5;
	    
	    private int score0;
	    private int score1;
	    private int score2;
	    private int score3;
	    private int score4;
	    private int score5;*/
	    /**
	     * 房间的check-id,进入id,可以重复,但是不允许同时活跃状态的id 相同
	     */
	    private String roomCheckId;

	    private int joinSessionId;
	    private int joinGatewayId;
	    private int joinUserId;


	    private List<DouniuUserInfo> DouniuUserInfos = new ArrayList<>();
	    private DouniuUserInfo controlUser;
	    private Map<String, String> options = new TreeMap<>();

	    public CheckJoinDouniuRoomMsg() {
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
//	        *//**
//	         * 牌局uuid
//	         *//*
	        uuid = readString(in);
//	        *//**
//	         * 用户
//	         *//*
//	        user0 = in.readInt();
//	        user1 = in.readInt();
//	        user2 = in.readInt();
//	        user3 = in.readInt();
//	        user4 = in.readInt();
//	        user5 = in.readInt();
//	        
//	        score0 = in.readInt();
//	        score1 = in.readInt();
//	        score2 = in.readInt();
//	        score3 = in.readInt();
//	        score4 = in.readInt();
//	        score5 = in.readInt();
	    
	     /*   *//**
	         * 房间的check-id,进入id,可以重复,但是不允许同时活跃状态的id 相同
	         *//*
*/	        roomCheckId = readString(in);
	        joinGatewayId = in.readInt();
	        joinSessionId = in.readInt();
	        joinUserId = in.readInt();


	        int size = in.readByte();
	        for (int i = 0; i < size; i++) {
	            DouniuUserInfo userInfo = new DouniuUserInfo();
	            userInfo.decode(in, ctx);
	            DouniuUserInfos.add(userInfo);
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
	      
//	        **
//	         * 用户
//	         *
//	        out.writeInt(user0);
//	        out.writeInt(user1);
//	        out.writeInt(user2);
//	        out.writeInt(user3);
//	        out.writeInt(user4);
//	        out.writeInt(user5);
//	        
//	        out.writeInt(score0);
//	        out.writeInt(score1);
//	        out.writeInt(score2);
//	        out.writeInt(score3);
//	        out.writeInt(score4);
//	        out.writeInt(score5);
//	        /**房间的check-id,进入id,可以重复,但是不允许同时活跃状态的id 相同*/
	        writeString(out, roomCheckId);
	        out.writeInt(joinGatewayId);
	        out.writeInt(joinSessionId);
	        out.writeInt(joinUserId);

	        out.writeByte(DouniuUserInfos.size());
	        for (DouniuUserInfo u : DouniuUserInfos) {
	            u.encode(out);
	        }
	        out.writeShort(options.size());
	        for (Map.Entry<String, String> entry : options.entrySet()) {
	            writeString(out, entry.getKey());
	            writeString(out, entry.getValue());
	        }
	        
	        out.writeShort(USMap.size());
	        for (Map.Entry<Integer, Integer> entry : USMap.entrySet()) {
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
	
	public List<DouniuUserInfo> getDouniuUserInfos() {
		return DouniuUserInfos;
	}
	
	public void setDouniuUserInfos(List<DouniuUserInfo> douniuUserInfos) {
		DouniuUserInfos = douniuUserInfos;
	}
	
	public Map<String, String> getOptions() {
		return options;
	}
	public void setOptions(Map<String, String> options) {
		this.options = options;
	}
	

	public DouniuUserInfo getControlUser() {
		return controlUser;
	}
	public void setControlUser(DouniuUserInfo controlUser) {
		this.controlUser = controlUser;
	}
	
	@Override
	public String toString() {
		return "CheckJoinDouniuRoomMsg [sceneId=" + sceneId + ", roomId="
				+ roomId + ", createUserId=" + createUserId + ", userMax="
				+ userMax + ", uuid=" + uuid + ", USMap=" + USMap
				+ ", roomCheckId=" + roomCheckId + ", joinSessionId="
				+ joinSessionId + ", joinGatewayId=" + joinGatewayId
				+ ", joinUserId=" + joinUserId + ", DouniuUserInfos="
				+ DouniuUserInfos + ", options=" + options + "]";
	}
	
//	public int getUser0() {
//		return user0;
//	}
//	public void setUser0(int user0) {
//		this.user0 = user0;
//	}
//	public int getUser1() {
//		return user1;
//	}
//	public void setUser1(int user1) {
//		this.user1 = user1;
//	}
//	public int getUser2() {
//		return user2;
//	}
//	public void setUser2(int user2) {
//		this.user2 = user2;
//	}
//	public int getUser3() {
//		return user3;
//	}
//	public void setUser3(int user3) {
//		this.user3 = user3;
//	}
//	public int getUser4() {
//		return user4;
//	}
//	public void setUser4(int user4) {
//		this.user4 = user4;
//	}
//	public int getUser5() {
//		return user5;
//	}
//	public void setUser5(int user5) {
//		this.user5 = user5;
//	}
//	public int getScore0() {
//		return score0;
//	}
//	public void setScore0(int score0) {
//		this.score0 = score0;
//	}
//	public int getScore1() {
//		return score1;
//	}
//	public void setScore1(int score1) {
//		this.score1 = score1;
//	}
//	public int getScore2() {
//		return score2;
//	}
//	public void setScore2(int score2) {
//		this.score2 = score2;
//	}
//	public int getScore3() {
//		return score3;
//	}
//	public void setScore3(int score3) {
//		this.score3 = score3;
//	}
//	public int getScore4() {
//		return score4;
//	}
//	public void setScore4(int score4) {
//		this.score4 = score4;
//	}
//	public int getScore5() {
//		return score5;
//	}
//	public void setScore5(int score5) {
//		this.score5 = score5;
//	}
//	@Override
//	public String toString() {
//		return "CheckJoinDouniuRoomMsg [sceneId=" + sceneId + ", roomId="
//				+ roomId + ", createUserId=" + createUserId + ", userMax="
//				+ userMax + ", uuid=" + uuid + ", user0=" + user0 + ", user1="
//				+ user1 + ", user2=" + user2 + ", user3=" + user3 + ", user4="
//				+ user4 + ", user5=" + user5 + ", score0=" + score0
//				+ ", score1=" + score1 + ", score2=" + score2 + ", score3="
//				+ score3 + ", score4=" + score4 + ", score5=" + score5
//				+ ", roomCheckId=" + roomCheckId + ", joinSessionId="
//				+ joinSessionId + ", joinGatewayId=" + joinGatewayId
//				+ ", joinUserId=" + joinUserId + ", DouniuUserInfos="
//				+ DouniuUserInfos + ", options=" + options + "]";
//	}
	
	
	

}
