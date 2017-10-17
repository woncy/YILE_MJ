package game.boss.net.poker.zjh;

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
public class ZJHSceneListenHandler implements PxMsgHandler<ZJHScene>{
	 private static final Logger log = LoggerFactory.getLogger(ZJHSceneListenHandler.class);
	 
	 private final MessageFactoryImpi messageFactory = MessageFactoryImpi.getInstance();
	 
	 private ZJHSceneMessageManager ZJHmessageManager;
	 
	 @Autowired
	 private ZJHSceneManager ZJHsceneManager;
	 
	 public ZJHSceneListenHandler() {
		 
	    }
	 
	@Override
	public void onConnect(Session<ZJHScene> session) throws Exception {
		 log.info("收到ZJHScene连接!{}", session);
	}
	 @Override
	public void onDisconnect(Session<ZJHScene> session) throws Exception {
		 if (session != null) {
			 ZJHsceneManager.unreg(session.get());
	        }
	        log.info("ZJHScene断开!{}", session);
	}

	@Override
	public void onException(Session<ZJHScene> session, Throwable cause) throws Exception {
		 log.error("错误，ZJHScene网关！{}", session, cause);
	        session.channel.close();
	}

	@Override
	public Session<ZJHScene> createSession(ChannelHandlerContext ctx) throws Exception {
		 return new Session<>(ctx.channel());
	}

	@Override
	public boolean onIn(Session<ZJHScene> session, ByteBuf in) throws Exception {
		return true;
	}

	@Override
	public void onMessage(PxMsg msg) throws Exception {
		ZJHmessageManager.handler(msg);
	}

	public void setZJHmessageManager(ZJHSceneMessageManager zJHmessageManager) {
		this.ZJHmessageManager = zJHmessageManager;
	}

}
