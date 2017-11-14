package game.scene.msg;

import com.isnowfox.game.proxy.message.AbstractSessionPxMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CheckJoinRoomMsg extends AbstractSessionPxMsg {
    public static final int ID = RegSceneMsg.ID + 1;

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
     * 用户0
     */
   
    /**
     * 房间的check-id,进入id,可以重复,但是不允许同时活跃状态的id 相同
     */
    private String roomCheckId;

    private int joinSessionId;
    private int joinGatewayId;
    private int joinUserId;


    private List<UserInfo> userInfos = new ArrayList<>();
    private Map<String, String> options = new TreeMap<>();
    
    public CheckJoinRoomMsg() {
        super(ID);
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

        out.writeByte(userInfos.size());
        for (UserInfo u : userInfos) {
            u.encode(out);
        }

        out.writeShort(options.size());
        for (Map.Entry<String, String> entry : options.entrySet()) {
            writeString(out, entry.getKey());
            writeString(out, entry.getValue());
        }
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
         * 用户0
         */
      
        /**
         * 房间的check-id,进入id,可以重复,但是不允许同时活跃状态的id 相同
         */
        roomCheckId = readString(in);

        joinGatewayId = in.readInt();
        joinSessionId = in.readInt();
        joinUserId = in.readInt();


        int size = in.readByte();
        for (int i = 0; i < size; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.decode(in, ctx);
            userInfos.add(userInfo);
        }

        size = in.readShort();
        for (int i = 0; i < size; i++) {
            options.put(readString(in), readString(in));
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

   

    public String getRoomCheckId() {
        return roomCheckId;
    }

    public void setRoomCheckId(String roomCheckId) {
        this.roomCheckId = roomCheckId;
    }


    public List<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<UserInfo> userInfos) {
        this.userInfos = userInfos;
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

   

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "CheckJoinRoomMsg{" +
                "sceneId=" + sceneId +
                ", roomId=" + roomId +
                ", createUserId=" + createUserId +
                ", userMax=" + userMax +
                ", uuid='" + uuid + '\'' +
                ", roomCheckId='" + roomCheckId + '\'' +
                ", joinSessionId=" + joinSessionId +
                ", joinGatewayId=" + joinGatewayId +
                ", joinUserId=" + joinUserId +
                ", userInfos=" + userInfos +
                ", options=" + options +
                '}';
    }
}
