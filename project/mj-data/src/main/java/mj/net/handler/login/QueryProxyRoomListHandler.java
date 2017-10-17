package mj.net.handler.login;

import mj.net.handler.MessageHandler;
import mj.net.message.login.QueryProxyRoomList;

public interface QueryProxyRoomListHandler<U> extends MessageHandler<QueryProxyRoomList, U>{

	@Override
	public boolean handler(QueryProxyRoomList msg, U user);
	
}
