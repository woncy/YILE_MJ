package game.zjh.scene.net;

import com.isnowfox.core.net.Session;
import com.isnowfox.game.proxy.PxMsgHandler;
import com.isnowfox.game.proxy.message.PxMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mj.net.message.MessageFactoryImpi;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ZJHSceneHandler implements PxMsgHandler<ZGateway> {
    private static final Logger log = LoggerFactory.getLogger(ZJHSceneHandler.class);

    private final MessageFactoryImpi messageFactory = MessageFactoryImpi.getInstance();

    private ZGatewayMessageManager messageManager;
    @Autowired
    private ZGatewayManager gatewayManager;

    public ZJHSceneHandler() {
    }

    @Override
    public void onConnect(Session<ZGateway> session) throws Exception {
        log.info("收到网关连接!{}", session);
    }

    @Override
    public void onDisconnect(Session<ZGateway> session) throws Exception {
        if (session != null) {
            gatewayManager.unreg(session.get());
        }
        log.info("网关断开!{}", session);
    }

    @Override
    public void onException(Session<ZGateway> session, Throwable cause) throws Exception {
        log.error("错误，断开网关！{}", session, cause);
        session.channel.close();
    }

    @Override
    public Session<ZGateway> createSession(ChannelHandlerContext ctx) throws Exception {
        return new Session<>(ctx.channel());
    }

    @Override
    public boolean onIn(Session<ZGateway> session, ByteBuf in) throws Exception {
        return true;
    }

    @Override
    public void onMessage(PxMsg msg) throws Exception {
        messageManager.handler(msg);
    }
    @Resource
    public void setMessageManager(ZGatewayMessageManager messageManager) {
        this.messageManager = messageManager;
    }
}
