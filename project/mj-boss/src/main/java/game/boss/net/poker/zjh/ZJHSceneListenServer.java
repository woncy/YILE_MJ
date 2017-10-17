package game.boss.net.poker.zjh;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;

import com.isnowfox.game.proxy.PxServer;

import game.zjh.scene.msg.ZJHScenePxMsgFactory;

public class ZJHSceneListenServer {
	 public static final int BOSS_THREAD_NUMS = 2;
	 public static final int WORKER_THREAD_NUMS = 4;

	 private int port;
	 private ZJHSceneListenHandler ZJHSceneListenHandler;
	 
	 private PxServer pxServer;

	    public void start() throws Exception {
	        pxServer = PxServer.create(new ZJHScenePxMsgFactory(), port, ZJHSceneListenHandler, BOSS_THREAD_NUMS, WORKER_THREAD_NUMS);
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
	    public void setMessageHandler(ZJHSceneListenHandler ZJHSceneListenHandler) {
	        this.ZJHSceneListenHandler = ZJHSceneListenHandler;
	    }
		public void setZJHSceneListenHandler(ZJHSceneListenHandler zJHSceneListenHandler) {
			ZJHSceneListenHandler = zJHSceneListenHandler;
		}
	    
	    
	    
	    
}
