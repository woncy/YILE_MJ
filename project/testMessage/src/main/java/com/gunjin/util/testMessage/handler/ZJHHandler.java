package com.gunjin.util.testMessage.handler;

import java.io.IOException;
import java.nio.channels.NotYetConnectedException;

import com.gunjin.util.testMessage.core.Client;
import com.gunjin.util.testMessage.core.MessageHandler;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.Message;

import mj.net.message.login.zjh.CreateZJHRoomResult;
import mj.net.message.login.zjh.JoinZJHRoom;
import mj.net.message.room.pdk.CreatePdkRoomRet;
import mj.net.message.room.pdk.JoinPdkRoom;

public class ZJHHandler extends MessageHandler{
	
	private boolean isGetMessage;
	private Client client; 

	@Override
	public void onMessage(Message message) {
		int type = message.getMessageType();
		int id = message.getMessageId();
		if(type==3){
			if(id==1){
				if(message instanceof CreateZJHRoomResult){
					String roomId = ((CreateZJHRoomResult) message).getRoomCheckId();
					joinRoom(roomId);
				}
			}
		}
	}
	
	private void joinRoom(String roomId){
		try {
			Thread.sleep(500);
			client.sendMessage(new JoinZJHRoom(roomId));
		} catch (InterruptedException | NotYetConnectedException | IOException | ProtocolException e) {
			e.printStackTrace();
		}
	}
	
	

	public Client getClient() {
		return client;
	}



	public void setClient(Client client) {
		this.client = client;
	}



	public boolean isGetMessage() {
		return isGetMessage;
	}

	public void setGetMessage(boolean isGetMessage) {
		this.isGetMessage = isGetMessage;
	}
	

}
