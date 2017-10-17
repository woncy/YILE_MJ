package game.boss.net.poker.zjh;

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
public final class ZJHSceneManager implements ApplicationContextAware {
	private final static Logger log = LoggerFactory.getLogger(ZJHSceneManager.class);
    /**
     * 网关锁,对网关注册,注销都需要这个锁
     */
    private final ReentrantLock lock = new ReentrantLock();
    private ConcurrentHashMap<Integer, ZJHScene> ZJHsceneMap = new ConcurrentHashMap<>();
    private ConcurrentArrayList<ZJHScene> ZJHsceneList = new ConcurrentArrayList<>();
    private ApplicationContext applicationContext;
    
    ZJHScene reg(Channel channel, int ZJHsceneId, String ZJHsceneAddress, int ZJHscenePort) throws ServerException {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (ZJHsceneMap.containsKey(ZJHsceneId)) {
            	ZJHScene scene = ZJHsceneMap.get(ZJHsceneId);
                clear(scene);
                scene.close();
                log.info("重复的ZJHScene,立即关闭", channel);
            }
            log.info("ZJHScene注册成功:{}", channel);
            ZJHScene ZJHscene = new ZJHScene();
            applicationContext.getAutowireCapableBeanFactory().autowireBean(ZJHscene);

            ZJHscene.setChannel(channel);
            ZJHscene.setId(ZJHsceneId);
            ZJHscene.setAddress(ZJHsceneAddress);
            ZJHscene.setPort(ZJHscenePort);

            ZJHsceneMap.put(ZJHsceneId, ZJHscene);
            ZJHsceneList.add(ZJHscene);
            return ZJHscene;
        } finally {
            lock.unlock();
        }
    }

    public void send(int ZJHsceneId, PxMsg pxMsg) {
    	ZJHScene ZJHscene = ZJHsceneMap.get(ZJHsceneId);
        if (ZJHscene != null) {
        	ZJHscene.send(pxMsg);
        } else {
            throw new ServerRuntimeException("场景不存在!ZJHsceneId:" + ZJHsceneId);
        }
    }
	 void unreg(ZJHScene ZJHscene) {
	        final ReentrantLock lock = this.lock;
	        lock.lock();
	        try {
	            ZJHscene.close();
	            clear(ZJHscene);
	            log.info("ZJHScene注销成功!{}", ZJHscene);
	        } finally {
	            lock.unlock();
	        }
	    }
	 
	 private void clear(ZJHScene ZJHscene) {
	        if (ZJHscene != null) {
	            ZJHsceneMap.remove(ZJHscene.getId());
	            ZJHsceneList.remove(ZJHscene);
	        }
	    }
	 
	 @Override
		public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		 this.applicationContext = applicationContext;
		}
	 
	  public int getRandomZJHSceneId() {
	        Object[] objects = ZJHsceneList.toArray();
	        ZJHScene ZJHscene = (ZJHScene) objects[RandomUtils.nextInt(objects.length)];
	        return ZJHscene.getId();
	    }

	 public ZJHScene getZJHScene(int ZJHsceneId) {
	        return ZJHsceneMap.get(ZJHsceneId);
	    }

}
