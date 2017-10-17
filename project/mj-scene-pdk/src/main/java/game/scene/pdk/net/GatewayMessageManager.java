package game.scene.pdk.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.isnowfox.core.net.Session;
import com.isnowfox.core.net.message.Message;
import com.isnowfox.core.net.message.MessageFactory;
import com.isnowfox.game.proxy.message.PxMsg;
import com.isnowfox.game.proxy.message.SinglePxMsg;

import game.boss.msg.RegGatewayMsg;
import game.scene.pdk.ServerRuntimeException;
import game.scene.pdk.room.PdkRoomService;
import game.scene.pdk.services.AsyncService;
import mj.net.handler.MessageHandler;
import mj.net.handler.MessageHandlerFactory;

/**
 * @author zuoge85@gmail.com on 16/9/27.
 */
public class GatewayMessageManager {
    private static final Logger log = LoggerFactory.getLogger(GatewayMessageManager.class);

    @Autowired
    private MessageFactory messageFactory;
    @Autowired
    private GatewayManager gatewayManager;
    @SuppressWarnings("unused")
	@Autowired
    private AsyncService asyncService;
    @Autowired
    private MessageHandlerFactory messageHandlerFactory;
    @Autowired
    private PdkRoomService roomService;

    void handler(PxMsg msg) {
        short sessionId = -1;
        try {
            if (msg instanceof RegGatewayMsg) {
                RegGatewayMsg regGatewayMsg = (RegGatewayMsg) msg;
                log.info("注册网关", regGatewayMsg);
                Gateway gateway = gatewayManager.reg(msg.getSession().channel, regGatewayMsg.getGatewayId());
                if (gateway != null) {
                    msg.getSession().set(gateway);
                }
            } else if (msg instanceof SinglePxMsg) {
                final Gateway gateway = checkGatway(msg);
                SinglePxMsg fm = (SinglePxMsg) msg;
                sessionId = fm.getSessionId();
                final Message rawMsg = fm.toMessage(messageFactory);
                handlerMessage(rawMsg, sessionId, gateway);
            }
//            else if (msg instanceof LogoutPxMsg) {
//                LogoutPxMsg lm = (LogoutPxMsg) msg;
//                sessionId = lm.getSessionId();
//                Gateway gateway = checkGatway(msg);
//                gateway.unReg(lm.getSessionId());
//            }
        } catch (Throwable th) {
            log.error("严重消息错误 " + msg, th);
            Gateway gateway = (Gateway) msg.getSession().get();
            if (sessionId > -1 && gateway != null) {
//                Net.getInstance().closeSession(sessionId, SystemErrorCode.PROTOCAL_ERROR);
                log.error("踢掉session:" + sessionId);
                gateway.unReg(sessionId);
            }
        }
    }


    private Gateway checkGatway(PxMsg msg) {
        Session<Gateway> gatewaySession = msg.getSession();
        final Gateway gateway = gatewaySession.get();
        if (gateway == null) {
            log.error("未注册的网关:{},gatewaySession:{}", msg, gatewaySession);
            throw new ServerRuntimeException("未注册的网关:" + msg + ",gatewaySession:" + gatewaySession);
        }
        return gateway;
    }

    /**
     * 消息处理机制
     */
    private void handlerMessage(Message message, short sessionId, Gateway gateway) {
        log.info("收到消息:{}", message);
        @SuppressWarnings("rawtypes")
		MessageHandler handler = messageHandlerFactory.getHandler(
                message.getMessageType(), message.getMessageId()
        );

        if (handler != null) {
            roomService.handler(handler, message, sessionId, (short) gateway.getId());
        } else {
            log.info("没有处理器的消息:{}", message);
        }
    }
}
