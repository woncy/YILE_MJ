package game.gateway.server;

public class DouniuSceneInfo {
	  private short sessionId;
	    private short douniuSceneId;

	    private String sceneAddress;
	    private int scenePort;

	    public short getSessionId() {
	        return sessionId;
	    }

	    public void setSessionId(short sessionId) {
	        this.sessionId = sessionId;
	    }



	    public short getDouniuSceneId() {
			return douniuSceneId;
		}

		public void setDouniuSceneId(short douniuSceneId) {
			this.douniuSceneId = douniuSceneId;
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
			return "DouniuSceneInfo [sessionId=" + sessionId
					+ ", douniuSceneId=" + douniuSceneId + ", sceneAddress="
					+ sceneAddress + ", scenePort=" + scenePort + "]";
		}

	   
}
