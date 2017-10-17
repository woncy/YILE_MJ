package game.boss.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.isnowfox.core.net.Session;
import com.isnowfox.core.net.message.Message;
import com.isnowfox.core.net.message.MessageFactory;
import com.isnowfox.game.proxy.message.LogoutPxMsg;
import com.isnowfox.game.proxy.message.PxMsg;
import com.isnowfox.game.proxy.message.SinglePxMsg;

import game.boss.ServerRuntimeException;
import game.boss.ZJH.msg.DelZJHRoomMsg;
import game.boss.ZJH.msg.JoinZJHRoomMsg;
import game.boss.douniu.msg.DeDouniuRoomMsg;
import game.boss.douniu.msg.DouniuExitRoomMsg;
import game.boss.douniu.msg.JoinDouniuRoomMsg;
import game.boss.model.User;
import game.boss.msg.DelRoomMsg;
import game.boss.msg.ExitRoomMsg;
import game.boss.msg.JoinRoomMsg;
import game.boss.msg.RegGatewayMsg;
import game.boss.msg.RegSessionMsg;
import game.boss.msg.WXPayPxMsg;
import game.boss.services.AsyncService;
import game.boss.services.RoomService;
import game.boss.services.SettingService;
import game.boss.services.UserService;
import game.boss.services.ZJH.ZJHRoomService;
import game.boss.services.douniu.DouniuRoomService;
import game.boss.services.pdk.PdkRoomService;
import mj.net.handler.MessageHandler;
import mj.net.handler.MessageHandlerFactory;
import mj.net.message.login.Login;
import mj.net.message.login.Ping;
import mj.net.message.login.douniu.JoinDouniuRoomReady;
import mj.net.message.room.pdk.CreatePdkRoom;
import mj.net.message.room.pdk.JoinPdkRoomReady;

/**
 * @author zuoge85@gmail.com on 16/9/27.
 */
public class MessageManager {
    private static final Logger log = LoggerFactory.getLogger(MessageManager.class);

    @Autowired
    private MessageFactory messageFactory;
    @Autowired
    private GatewayManager gatewayManager;
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private MessageHandlerFactory messageHandlerFactory;
    @Autowired
    private UserService userService;
    
    @Autowired
    private PdkRoomService pdkRoomService;
    @Autowired
    private ZJHRoomService zjhRoomService;
    @Autowired
    private DouniuRoomService douniuRoomService;
    
    @Autowired
    private RoomService roomService;

    @Autowired
    private SettingService settingService;

    void handlerGatewayMessage(PxMsg msg) {
        short sessionId = -1;
        try {
        	
            if (msg instanceof RegGatewayMsg) {
                RegGatewayMsg regGatewayMsg = (RegGatewayMsg) msg;
                log.info("注册网关", regGatewayMsg);
                Gateway gateway = gatewayManager.reg(msg.getSession().channel, regGatewayMsg.getGatewayId());
                if (gateway != null) {
                    msg.getSession().set(gateway);
                }
            } else if (msg instanceof RegSessionMsg) {
                final Gateway gateway = checkGatway(msg);
                final RegSessionMsg regSessionMsg = (RegSessionMsg) msg;
                sessionId = regSessionMsg.getSessionId();

                if (gateway.getSession(sessionId) != null) {
                    log.error("错误消息:{}重复登陆！");
                    return;
                }
                gateway.reg(sessionId, regSessionMsg.getIp());
            } else if (msg instanceof SinglePxMsg) {
                final Gateway gateway = checkGatway(msg);

                SinglePxMsg fm = (SinglePxMsg) msg;
                sessionId = fm.getSessionId();
                Session<UserImpi> userSession = gateway.getSession(sessionId);
                if (userSession == null) {
                    log.error("错误消息:{}没有注册的用户！", sessionId);
                    throw new ServerRuntimeException("没有注册的用户" + sessionId);
                }
                final Message rawMsg = fm.toMessage(messageFactory);
                rawMsg.setSession(userSession);
                handlerMessage(rawMsg, userSession.get());
            } else if (msg instanceof LogoutPxMsg) {
                LogoutPxMsg lm = (LogoutPxMsg) msg;
                sessionId = lm.getSessionId();
                Gateway gateway = checkGatway(msg);
                gateway.unReg(lm.getSessionId()); 
            } else if(msg instanceof JoinDouniuRoomMsg){                 //斗牛加入房间消息boss处理
            	JoinDouniuRoomMsg lm = (JoinDouniuRoomMsg) msg;
                final Gateway gateway = checkGatway(msg);  
                sessionId = lm.getSessionId();
                Session<UserImpi> userSession = gateway.getSession(sessionId);
                if (userSession == null) {
                    log.error("错误消息:{}没有注册的用户！", sessionId);
                    throw new ServerRuntimeException("没有注册的用户" + sessionId);
                }
                UserImpi userImpi = userSession.get();
                if (userImpi == null) {
                    log.error("错误消息:{}不存在用户！", sessionId);
                    return;
                }
                	 douniuRoomService.joinRoomGatewaySuccess(userImpi);            
            } else if(msg instanceof DeDouniuRoomMsg){     //斗牛删除房间消息
            	DeDouniuRoomMsg lm = (DeDouniuRoomMsg) msg;
                final Gateway gateway = checkGatway(msg);
                sessionId = lm.getSessionId();
                Session<UserImpi> userSession = gateway.getSession(sessionId);
                if (userSession == null) {
                    log.error("错误消息:{}没有注册的用户！", sessionId);
                    return;
                }
                UserImpi userImpi = userSession.get();
                if (userImpi == null) {
                    log.error("错误消息:{}不存在用户！", sessionId);
                    return;
                }
                douniuRoomService.delRoomGatewaySuccess(userImpi);        
            }else if (msg instanceof DouniuExitRoomMsg) {  //斗牛退出房间
            	DouniuExitRoomMsg lm = (DouniuExitRoomMsg) msg;
                final Gateway gateway = checkGatway(msg);
                sessionId = lm.getSessionId();
                Session<UserImpi> userSession = gateway.getSession(sessionId);
                if (userSession == null) {
                    log.error("错误消息:{}没有注册的用户！", sessionId);
                    throw new ServerRuntimeException("没有注册的用户" + sessionId);
                }
                UserImpi userImpi = userSession.get();
                if (userImpi == null) {
                    log.error("错误消息:{}不存在用户！", sessionId);
                    return;
                }
                douniuRoomService.exitDouniuRoomGatewaySuccess(userImpi);
            }else if (msg instanceof JoinRoomMsg) {
                JoinRoomMsg lm = (JoinRoomMsg) msg;
                final Gateway gateway = checkGatway(msg);
                sessionId = lm.getSessionId();
                Session<UserImpi> userSession = gateway.getSession(sessionId);
                if (userSession == null) {
                    log.error("错误消息:{}没有注册的用户！", sessionId);
                    throw new ServerRuntimeException("没有注册的用户" + sessionId);
                }
                UserImpi userImpi = userSession.get();
                if (userImpi == null) {
                    log.error("错误消息:{}不存在用户！", sessionId);
                    return;
                }
                if(((JoinRoomMsg) msg).getSceneId()==1002){
                	userImpi.send(new JoinPdkRoomReady());
                }else{
                	roomService.joinRoomGatewaySuccess(userImpi);
                }
                
            }else if(msg instanceof JoinZJHRoomMsg){//扎金花
            	JoinZJHRoomMsg lm = (JoinZJHRoomMsg) msg;
                 final Gateway gateway = checkGatway(msg);
                 sessionId = lm.getSessionId();
                 Session<UserImpi> userSession = gateway.getSession(sessionId);
                 if (userSession == null) {
                     log.error("错误消息:{}没有注册的用户！", sessionId);
                     throw new ServerRuntimeException("没有注册的用户" + sessionId);
                 }
                 UserImpi userImpi = userSession.get();
                 if (userImpi == null) {
                     log.error("错误消息:{}不存在用户！", sessionId);
                     return;
                 }
                 zjhRoomService.joinRoomGatewaySuccess(userImpi);
            	 
            }else if (msg instanceof ExitRoomMsg) {
                ExitRoomMsg lm = (ExitRoomMsg) msg;

                final Gateway gateway = checkGatway(msg);
                sessionId = lm.getSessionId();
                Session<UserImpi> userSession = gateway.getSession(sessionId);
                if (userSession == null) {
                    log.error("错误消息:{}没有注册的用户！", sessionId);
                    throw new ServerRuntimeException("没有注册的用户" + sessionId);
                }
                UserImpi userImpi = userSession.get();
                if (userImpi == null) {
                    log.error("错误消息:{}不存在用户！", sessionId);
                    return;
                }
                roomService.exitRoomGatewaySuccess(userImpi);
            } else if (msg instanceof DelRoomMsg) {
                DelRoomMsg lm = (DelRoomMsg) msg;
                final Gateway gateway = checkGatway(msg);
                sessionId = lm.getSessionId();
                Session<UserImpi> userSession = gateway.getSession(sessionId);
                if (userSession == null) {
                    log.error("错误消息:{}没有注册的用户！", sessionId);
                    return;
                }
                UserImpi userImpi = userSession.get();
                if (userImpi == null) {
                    log.error("错误消息:{}不存在用户！", sessionId);
                    return;
                }
                roomService.delRoomGatewaySuccess(userImpi);
            }else if(msg instanceof WXPayPxMsg){
            	log.info("Boss 收到消息:"+msg);
            	userService.wxPayHuiDiao((WXPayPxMsg)msg);
            }else if(msg.getType()==4){
        		handlerPdkMessage(msg);
        	}else if(msg.getType() ==3){//扎金花
            	handlerZJHMessage(msg,sessionId);
            }else if(msg.getType()==5){
            	handlerDouniuMessage(msg,sessionId); //斗牛
            }else if(msg instanceof DelZJHRoomMsg){//扎金花删除房间
            	DelZJHRoomMsg dm = (DelZJHRoomMsg)msg;
            	 final Gateway gateway = checkGatway(msg);
                 sessionId = dm.getSessionId();
                 Session<UserImpi> userSession = gateway.getSession(sessionId);
                 if (userSession == null) {
                     log.error("错误消息:{}没有注册的用户！", sessionId);
                     return;
                 }
                 UserImpi userImpi = userSession.get();
                 if (userImpi == null) {
                     log.error("错误消息:{}不存在用户！", sessionId);
                     return;
                 }
                 zjhRoomService.delRoomGatewaySuccess(userImpi);
            } 
            
            
            
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
    /**
     * @throws Exception 
     * 
        * @Title: handlerPdkMessage
        * @Description: 处理type为4的跑得快模块的消息
        * @param @param msg    参数
        * @return void    返回类型
        * @throws
     */
    private void handlerPdkMessage(PxMsg msg) throws Exception {
    	 final Gateway gateway = checkGatway(msg);
		if(msg instanceof CreatePdkRoom){
			SinglePxMsg fm = (SinglePxMsg) msg;
	         short sessionId = fm.getSessionId();
	         Session<UserImpi> userSession = gateway.getSession(sessionId);
	         if (userSession == null) {
	             log.error("错误消息:{}没有注册的用户！", sessionId);
	             throw new ServerRuntimeException("没有注册的用户" + sessionId);
	         }
	         final Message rawMsg = fm.toMessage(messageFactory);
	         rawMsg.setSession(userSession);
			handlerMessage(rawMsg, userSession.get());
		}
	}
    /**
     * 创建扎金花房间
     *@param msg
     *@throws Exception
     *@return
     * 2017年7月1日
     */
    private void handlerZJHMessage(PxMsg msg, short sessionId) throws Exception {
    	 final Gateway gateway = checkGatway(msg);

         SinglePxMsg fm = (SinglePxMsg) msg;
         sessionId = fm.getSessionId();
         Session<UserImpi> userSession = gateway.getSession(sessionId);
         if (userSession == null) {
             log.error("错误消息:{}没有注册的用户！", sessionId);
             throw new ServerRuntimeException("没有注册的用户" + sessionId);
         }
         final Message rawMsg = fm.toMessage(messageFactory);
         rawMsg.setSession(userSession);
         handlerMessage(rawMsg, userSession.get());
    }
/**
 * 斗牛消息处理
 * @param msg
 * @param sessionId
 * @throws Exception
 */
    private void handlerDouniuMessage(PxMsg msg,short sessionId) throws Exception {
   	 final Gateway gateway = checkGatway(msg);

        SinglePxMsg fm = (SinglePxMsg) msg;
         sessionId = fm.getSessionId();
        Session<UserImpi> userSession = gateway.getSession(sessionId);
        if (userSession == null) {
            log.error("错误消息:{}没有注册的用户！", sessionId);
            throw new ServerRuntimeException("没有注册的用户" + sessionId);
        }
        final Message rawMsg = fm.toMessage(messageFactory);
        rawMsg.setSession(userSession);
        handlerMessage(rawMsg, userSession.get());
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
    @SuppressWarnings("unchecked")
    private void handlerMessage(Message message, User user) {
       if (!(message instanceof Ping)) {
            log.info("收到消息:{}", message);
        }
        
        @SuppressWarnings("rawtypes")
		MessageHandler handler = messageHandlerFactory.getHandler(
                message.getMessageType(), message.getMessageId()
        );
  
        if (handler != null) {
            if (Login.TYPE == message.getMessageType() && Login.ID == message.getMessageId()) {
                handler.handler(message, user);
            }else {
                if (user.getUserDO() == null) {
                    if (!(message instanceof Ping)) {
                        log.info("还未登录!:{}", message);
                    }
                }
//                else {
                asyncService.excuete(handler, message, user);
//                }
            }
        } else {
            log.info("没有处理器的消息:{}", message);
        }
    }
}
