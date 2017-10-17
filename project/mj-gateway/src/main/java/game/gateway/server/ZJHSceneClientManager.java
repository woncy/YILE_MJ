package game.gateway.server;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.google.common.collect.ArrayListMultimap;
import com.isnowfox.core.thread.FrameQueueContainer;
import com.isnowfox.game.proxy.message.SinglePxMsg;

public class ZJHSceneClientManager extends FrameQueueContainer implements ApplicationContextAware{
	
	 	private static final int FRAME_TIME_SPAN = 33;
	    private static final int RUN_QUEUE_MAX = 1024;

	    private static final Logger log = LoggerFactory.getLogger(ZJHSceneClientManager.class);

	    private ApplicationContext applicationContext;
	    private final ConcurrentHashMap<Integer, ZJHSceneClient> map = new ConcurrentHashMap<>();
	    private final ArrayListMultimap<Integer, Runnable> mapCallbacks = ArrayListMultimap.create();


	    public ZJHSceneClientManager() {
	        super(FRAME_TIME_SPAN, RUN_QUEUE_MAX);
	        start();
	    }
	    
	    public void checkConnect(int ZJHsceneId, String address, int port, Runnable callback) throws Exception {
	        run(() -> {
	        	ZJHSceneClient ZJHclient = map.get(ZJHsceneId);
	            if (ZJHclient == null) {
	                mapCallbacks.put(ZJHsceneId, callback);
	                add(ZJHsceneId, address, port);
	            } else {
	                if (ZJHclient.isConnect()) {
	                    callback.run();
	                } else {
	                    mapCallbacks.put(ZJHsceneId, callback);
	                }
	            }
	        });
	    }
	    
	    private void add(int ZJHsceneId, String address, int port) {
	        try {
	        	ZJHSceneClientHandler ZJHSceneClientHandler = new ZJHSceneClientHandler(() -> runConnectCallback(ZJHsceneId));
	            applicationContext.getAutowireCapableBeanFactory().autowireBean(ZJHSceneClientHandler);
	            ZJHSceneClient ZJHclient = new ZJHSceneClient(address, port, ZJHSceneClientHandler);
	            ZJHSceneClientHandler.setZJHSceneClient(ZJHclient);
	            ZJHclient.connect();
	            map.put(ZJHsceneId, ZJHclient);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }

	    private void runConnectCallback(int ZJHsceneId) {
	        run(() -> {
	            ZJHSceneClient ZJHclient = map.get(ZJHsceneId);
	            List<Runnable> callbacks = mapCallbacks.get(ZJHsceneId);
	            for (int i = 0; i < callbacks.size(); i++) {
	                callbacks.get(i).run();
	            }
	            ZJHclient.setConnect(true);
	        });
	    }
	    
	    public void forwardMessage(SinglePxMsg msg, ZJHSceneInfo ZJHSceneInfo) {
	        int ZJHSceneId = ZJHSceneInfo.getZJHsceneId();
	        ZJHSceneClient ZJHClient = map.get(ZJHSceneId);
	        ZJHClient.writeAndFlush(msg);
	    }

		@Override
		public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
			 this.applicationContext = applicationContext;
			
		}
		@Override
		protected void threadMethod(int arg0, long arg1, long arg3) {
			
		}
		@Override
		protected void errorHandler(Throwable t) {
			 log.error("严重异常", t);
			
		}
}
