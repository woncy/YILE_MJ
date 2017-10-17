package game.boss.net.poker.douniu;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.isnowfox.game.proxy.message.PxMsg;
import com.isnowfox.util.collect.ConcurrentArrayList;

import game.boss.ServerException;
import game.boss.ServerRuntimeException;
import io.netty.channel.Channel;
/***
  * 现在支持注册多个网关0.0
 *
 */
public final class DouniuSceneManager implements ApplicationContextAware {
	private final static Logger log = LoggerFactory.getLogger(DouniuSceneManager.class);
    /**
     * 网关锁,对网关注册,注销都需要这个锁
     */
    private final ReentrantLock lock = new ReentrantLock();
    private ConcurrentHashMap<Integer, DouniuScene> douniuSceneMap = new ConcurrentHashMap<>();
    private ConcurrentArrayList<DouniuScene> douniuSceneList = new ConcurrentArrayList<>();
    private ApplicationContext applicationContext;
    
    DouniuScene reg(Channel channel, int DouniusceneId, String DouniusceneAddress, int DouniuscenePort) throws ServerException {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
        	boolean contains = douniuSceneMap.containsKey(DouniusceneId);
            if (contains) {
            	DouniuScene scene = douniuSceneMap.get(DouniusceneId);
                clear(scene);
                scene.close();
                log.info("重复的DouniuScene,立即关闭", channel);
            }
            log.info("DouniuScene注册成功:{}", channel);
            DouniuScene Douniuscene = new DouniuScene();
            applicationContext.getAutowireCapableBeanFactory().autowireBean(Douniuscene);

            Douniuscene.setChannel(channel);
            Douniuscene.setId(DouniusceneId);
            Douniuscene.setAddress(DouniusceneAddress);
            Douniuscene.setPort(DouniuscenePort);

            douniuSceneMap.put(DouniusceneId, Douniuscene);
            douniuSceneList.add(Douniuscene);
            return Douniuscene;
        } finally {
            lock.unlock();
        }
    }

    public void send(int DouniusceneId, PxMsg pxMsg) {
    	DouniuScene Douniuscene = douniuSceneMap.get(DouniusceneId);
        if (Douniuscene != null) {
        	Douniuscene.send(pxMsg);
        } else {
            throw new ServerRuntimeException("场景不存在!DouniusceneId:" + DouniusceneId);
        }
        
    }
	 void unreg(DouniuScene Douniuscene) {
	        final ReentrantLock lock = this.lock;
	        lock.lock();
	        try {
	        	Douniuscene.close();
	            clear(Douniuscene);
	            log.info("DouniuScene注销成功!{}", Douniuscene);
	        } finally {
	            lock.unlock();
	        }
	    }
	 
	 private void clear(DouniuScene Douniuscene) {
	        if (Douniuscene != null) {
	        	douniuSceneMap.remove(Douniuscene.getId());
	        	douniuSceneList.remove(Douniuscene);
	        }
	    }
	 
	 @Override
		public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		 this.applicationContext = applicationContext;
		}
	 
	  public int getRandomDouniuSceneId() {
	        Object[] objects = douniuSceneList.toArray();
	        System.out.println("----++-----------"+objects.length);
	        DouniuScene Douniuscene = (DouniuScene) objects[RandomUtils.nextInt(objects.length)];
	        return Douniuscene.getId();
		 // return 1005;
	    }

	 public DouniuScene getDouniuScene(int DouniusceneId) {
	        return douniuSceneMap.get(DouniusceneId);
	    }

}
