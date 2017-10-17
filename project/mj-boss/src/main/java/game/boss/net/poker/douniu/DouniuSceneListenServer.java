package game.boss.net.poker.douniu;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import com.isnowfox.game.proxy.PxServer;

import game.douniu.scene.msg.DouniuScenePxMsgFactory;


public class DouniuSceneListenServer {
	 public static final int BOSS_THREAD_NUMS = 2;
	 public static final int WORKER_THREAD_NUMS = 4;

	 private int port;
	 private DouniuSceneListenHandler douniuSceneListenHandler;
	 
	 private PxServer pxServer;

	    public void start() throws Exception {
	        pxServer = PxServer.create(new DouniuScenePxMsgFactory(), port, douniuSceneListenHandler, BOSS_THREAD_NUMS, WORKER_THREAD_NUMS);
	        pxServer.start();
	    }
	    public void close() throws ExecutionException, InterruptedException {
	        if(pxServer != null){
	            pxServer.close();
	        }
	    }
	    public void setPort(int port) {
	        this.port = port;
	    }
		public DouniuSceneListenHandler getDouniuSceneListenHandler() {
			return douniuSceneListenHandler;
		}
		public void setDouniuSceneListenHandler(DouniuSceneListenHandler douniuSceneListenHandler) {
			this.douniuSceneListenHandler = douniuSceneListenHandler;
		}
	   	 	    
}

