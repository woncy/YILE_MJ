package game.scene.pdk.room;

import com.isnowfox.core.net.message.Message;

import game.scene.pdk.net.PdkSceneService;
import game.type.NoticeType;
import mj.net.message.game.GameUserInfo;
import mj.net.message.game.pdk.PdkGameUserInfo;
import mj.net.message.login.Notice;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zuoge85@gmail.com on 16/10/7.
 */
public class PdkSceneUser {
    private int userId;
    private String userName;
    private int locationIndex;
    private int sessionId = -1;
    private int gatewayId = -1;
    private boolean joinGame;

    private String avatar;
    /**
     * 0:女生,1:男生,2:未知
     */
    private int sex;
    private int gold;
    private int score;

    private PdkRoomImpi room;
  
    @Autowired
    private PdkSceneService sceneService;
    private boolean online;
    private String ip;
    /**
     * 经度
     */
    private double longitude;
    /**
     * 纬度
     */
    private double latitude;

    public PdkSceneUser() {

    }

    public PdkGameUserInfo toMessage() {
    	PdkGameUserInfo m = new PdkGameUserInfo();
        m.setUserId(userId);
        m.setSex(sex);
        m.setHeadUrl(avatar);
        m.setSelfIndex(locationIndex);
        m.setNickName(userName);
        m.setSocre(score);
        m.setOnline(online);
        m.setIp(ip);
        return m;
    }


    public void sendMessage(Message msg) {
        if (gatewayId > -1) {
            sceneService.sendMessage(gatewayId, sessionId, msg);
        }
    }


    public void noticeError(String key) {
        noticeError(key, new String[0]);
    }

    public void noticeError(String key, String[] args) {
        notice(key, args, NoticeType.ERROR, false);
    }

    public void noticeMain(String key) {
        noticeMain(key, new String[0]);
    }

    public void noticeMain(String key, String[] args) {
        notice(key, args, NoticeType.MAIN, false);
    }

    public void notice(String key, String[] args, NoticeType type) {
        notice(key, args, type, false);
    }

    public void notice(String key, String[] args, NoticeType type, boolean reboot) {
        if(joinGame){
            sendMessage(new Notice(key, args, type.ordinal(), reboot));
        }
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLocationIndex() {
        return locationIndex;
    }

    public void setLocationIndex(int locationIndex) {
        this.locationIndex = locationIndex;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public void setGatewayId(int gatewayId) {
        this.gatewayId = gatewayId;
    }

    public int getGatewayId() {
        return gatewayId;
    }

    public PdkRoomImpi getRoom() {
        return room;
    }

    public void setRoom(PdkRoomImpi room) {
        this.room = room;
    }

    public boolean isJoinGame() {
        return joinGame;
    }

    public void setJoinGame(boolean joinGame) {
        this.joinGame = joinGame;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

  
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "SceneUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", locationIndex=" + locationIndex +
                ", sessionId=" + sessionId +
                ", gatewayId=" + gatewayId +
                ", joinGame=" + joinGame +
                ", avatar='" + avatar + '\'' +
                ", sex=" + sex +
                ", gold=" + gold +
                ", score=" + score +
                ", room=" + room +
                ", sceneService=" + sceneService +
                ", online=" + online +
                ", ip='" + ip + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    public void removeScore(int scoreNums) {
        score -= scoreNums;
    }

    public void addScore(int scoreNums) {
        score += scoreNums;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isOnline() {
        return online;
    }
    
}
