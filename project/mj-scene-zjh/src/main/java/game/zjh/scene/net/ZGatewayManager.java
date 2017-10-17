package game.zjh.scene.net;

import com.isnowfox.core.net.message.Message;
import game.scene.ServerException;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 现在支持注册多个网关
 */
final class ZGatewayManager implements ApplicationContextAware {
    private final static Logger log = LoggerFactory.getLogger(ZGatewayManager.class);

    /**
     * 网关锁,对网关注册,注销都需要这个锁
     */
    private final ReentrantLock lock = new ReentrantLock();
    private ConcurrentHashMap<Integer, ZGateway> gatewayMap = new ConcurrentHashMap<>();
    private int gatewayUserMaxConnect;
    private ApplicationContext applicationContext;

    ZGateway reg(Channel channel, int gatewayId) throws ServerException {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (gatewayMap.containsKey(gatewayId)) {
                ZGateway gateway = gatewayMap.get(gatewayId);
                gateway.close();
                clear(gateway);
                log.info("重复的网管,立即关闭", channel);
            }
            log.info("网关注册成功:{}", channel);
            ZGateway gateway = new ZGateway();
            applicationContext.getAutowireCapableBeanFactory().autowireBean(gateway);

            gateway.setChannel(channel);
            gateway.setId(gatewayId);
            gatewayMap.put(gatewayId, gateway);
            return gateway;
        } finally {
            lock.unlock();
        }
    }

    void unreg(ZGateway gateway) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if(gateway!=null){
                gateway.close();
                clear(gateway);
            }
            log.info("网关注销成功!{}", gateway);
        } finally {
            lock.unlock();
        }
    }

    private void clear(ZGateway gateway) {
        if (gateway != null) {
            gatewayMap.remove(gateway.getId());
        }
    }

    void sendMessage(int gatewayId, int sessionId, Message msg) {
        ZGateway gateway = gatewayMap.get(gatewayId);
        if (gateway == null) {
            throw new RuntimeException(String.format(
                    "gateway不存在,gatewayId:%d",
                    gatewayId
            ));
        }
        gateway.send((short) sessionId, msg);
    }

    public void setGatewayUserMaxConnect(int gatewayUserMaxConnect) {
        this.gatewayUserMaxConnect = gatewayUserMaxConnect;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
