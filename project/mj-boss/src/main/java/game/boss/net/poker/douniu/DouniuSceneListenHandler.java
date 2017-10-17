package game.boss.net.poker.douniu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.isnowfox.core.net.Session;
import com.isnowfox.game.proxy.PxMsgHandler;
import com.isnowfox.game.proxy.message.PxMsg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mj.net.message.MessageFactoryImpi;
/**
 * 
 * @author 13323659953@163.com
 *
 */
public class DouniuSceneListenHandler implements PxMsgHandler<DouniuScene>{
	 private static final Logger log = LoggerFactory.getLogger(DouniuSceneListenHandler.class);
	 
	 private final MessageFactoryImpi messageFactory = MessageFactoryImpi.getInstance();
	 
	 private DouniuSceneMessageManager douniuMessageManager;
	 
	 @Autowired
	 private DouniuSceneManager douniuSceneManager;
	 
	 public DouniuSceneListenHandler() {
		 
	    }
	 
	@Override
	public void onConnect(Session<DouniuScene> session) throws Exception {
		 log.info("收到DouniuScene连接!{}", session);
	}
	 @Override
	public void onDisconnect(Session<DouniuScene> session) throws Exception {
		 if (session != null) {
			 douniuSceneManager.unreg(session.get());
	        }
	        log.info("DouniuScene断开!{}", session);
	}

	@Override
	public void onException(Session<DouniuScene> session, Throwable cause) throws Exception {
		 log.error("错误，DouniuScene网关！{}", session, cause);
	        session.channel.close();
	}

	@Override
	public Session<DouniuScene> createSession(ChannelHandlerContext ctx) throws Exception {
		 return new Session<>(ctx.channel());
	}

	@Override
	public boolean onIn(Session<DouniuScene> session, ByteBuf in) throws Exception {
		return true;
	}

	@Override
	public void onMessage(PxMsg msg) throws Exception {
		douniuMessageManager.handler(msg);
	}

	public void setDouniuMessageManager(DouniuSceneMessageManager douniuMessageManager) {
		this.douniuMessageManager = douniuMessageManager;
	}

}
