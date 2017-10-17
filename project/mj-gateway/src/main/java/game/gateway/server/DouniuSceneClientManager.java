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

public class DouniuSceneClientManager extends FrameQueueContainer implements ApplicationContextAware{
	
	 	private static final int FRAME_TIME_SPAN = 33;
	    private static final int RUN_QUEUE_MAX = 1024;

	    private static final Logger log = LoggerFactory.getLogger(DouniuSceneClientManager.class);

	    private ApplicationContext applicationContext;
	    private final ConcurrentHashMap<Integer, DouniuSceneClient> map = new ConcurrentHashMap<>();
	    private final ArrayListMultimap<Integer, Runnable> mapCallbacks = ArrayListMultimap.create();


	    public DouniuSceneClientManager() {
	        super(FRAME_TIME_SPAN, RUN_QUEUE_MAX);
	        start();
	    }
	    
	    public void checkConnect(int douniuSceneId, String address, int port, Runnable callback) throws Exception {
//	        run(() -> {
	        	DouniuSceneClient douniuClient = map.get(douniuSceneId);
	            if (douniuClient == null) {
	                mapCallbacks.put(douniuSceneId, callback);
	                add(douniuSceneId, address, port);
	            } else {
	            
	            	if (douniuClient.isConnect()) {
	                    callback.run();
	                } else {
	                    mapCallbacks.put(douniuSceneId, callback);
	                }
	            }
//	        });
	    }
	    
	    private void add(int douniuSceneId, String address, int port) {
	        try {
	        	DouniuSceneClientHandler douniuSceneClientHandler = new DouniuSceneClientHandler(() -> runConnectCallback(douniuSceneId));
	            applicationContext.getAutowireCapableBeanFactory().autowireBean(douniuSceneClientHandler);
	            DouniuSceneClient douniuclient = new DouniuSceneClient(address, port, douniuSceneClientHandler);
	            douniuSceneClientHandler.setDouniuSceneClient(douniuclient);
	            douniuclient.connect();

	            map.put(douniuSceneId, douniuclient);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }

	    private void runConnectCallback(int douniuSceneId) {
	        run(() -> {
	            DouniuSceneClient douniuClient = map.get(douniuSceneId);
	            List<Runnable> callbacks = mapCallbacks.get(douniuSceneId);
	            for (int i = 0; i < callbacks.size(); i++) {
	                callbacks.get(i).run();
	            }
	            douniuClient.setConnect(true);
	      });
	    }
	    
	    public void forwardMessage(SinglePxMsg msg, DouniuSceneInfo douniuSceneInfo) {
	        int douniuSceneId = douniuSceneInfo.getDouniuSceneId();
	        System.out.println("-------------"+douniuSceneId);
	        DouniuSceneClient douniuClient = map.get(douniuSceneId);
	        douniuClient.writeAndFlush(msg);  //youwentiu
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
