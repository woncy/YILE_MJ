package game.gateway.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.isnowfox.core.net.Session;
import com.isnowfox.game.proxy.PxMsgHandler;
import com.isnowfox.game.proxy.message.PxMsg;

import game.gateway.GatewayService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
/**
 * 69
 * @author 13323659953@163.com
 *
 */
public class DouniuSceneClientHandler implements PxMsgHandler<Object>{
	 private final static Logger log = LoggerFactory.getLogger(GameServerHandler.class);

	    @Autowired
	    private GatewayService gatewayService;
	    
	    private Runnable connectCallback;
	    
	    private DouniuSceneClient douniuSceneClient;
	    
	    public DouniuSceneClientHandler(Runnable connectCallback) {
	        this.connectCallback = connectCallback;
	    }

	 /**
     * TODO: 这儿有个风险,就是连接上了场景服务器,但是还没有注册成功!!,虽然发生问题的可能性较小,还是应该重构!
     */
    @Override
    public void onConnect(Session<Object> session) throws Exception {
        log.info("连接场景服务器成功！");
        gatewayService.onSceneConnectDouniu(douniuSceneClient);
        connectCallback.run();
    }

	@Override
	public void onDisconnect(Session<Object> arg0) throws Exception {
		 	log.info("场景连接断开!！");
	        gatewayService.onSceneDisconnect(douniuSceneClient);
	}
	
	@Override
	public Session<Object> createSession(ChannelHandlerContext ctx) throws Exception {
		return new Session<>(ctx.channel());
	}
	
	@Override
	public void onException(Session<Object> session, Throwable cause) throws Exception {
		  log.info("错误!{}", session, cause);
	}

	@Override
	public boolean onIn(Session<Object> arg0, ByteBuf arg1) throws Exception {
		return true;
	}

	@Override
	public void onMessage(PxMsg pxMsg) throws Exception {
		 gatewayService.handlerDouniuScene(pxMsg);//未完成
	}

	public DouniuSceneClient getDouniuSceneClient() {
		return douniuSceneClient;
	}

	public void setDouniuSceneClient(DouniuSceneClient douniuSceneClient) {
		this.douniuSceneClient = douniuSceneClient;
	}
	
	

}
