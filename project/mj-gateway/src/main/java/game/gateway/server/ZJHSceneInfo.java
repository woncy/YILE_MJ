package game.gateway.server;

public class ZJHSceneInfo {
	  private short sessionId;
	    private short ZJHsceneId;

	    private String sceneAddress;
	    private int scenePort;

	    public short getSessionId() {
	        return sessionId;
	    }

	    public void setSessionId(short sessionId) {
	        this.sessionId = sessionId;
	    }

	    public short getZJHsceneId() {
	        return ZJHsceneId;
	    }

	    public void setZJHsceneId(short ZJHsceneId) {
	        this.ZJHsceneId = ZJHsceneId;
	    }

	    public String getSceneAddress() {
	        return sceneAddress;
	    }

	    public void setSceneAddress(String sceneAddress) {
	        this.sceneAddress = sceneAddress;
	    }

	    public int getScenePort() {
	        return scenePort;
	    }

	    public void setScenePort(int scenePort) {
	        this.scenePort = scenePort;
	    }

	    @Override
	    public String toString() {
	        return "SceneUserInfo{" +
	                "sessionId=" + sessionId +
	                ", ZJHsceneId=" + ZJHsceneId +
	                ", sceneAddress='" + sceneAddress + '\'' +
	                ", scenePort=" + scenePort +
	                '}';
	    }
}
