package com.gunjin.util.testMessage.handler;

import java.io.IOException;
import java.nio.channels.NotYetConnectedException;

import com.gunjin.util.testMessage.core.Client;
import com.gunjin.util.testMessage.core.MessageHandler;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.Message;

import mj.net.message.room.pdk.CreatePdkRoom;
import mj.net.message.room.pdk.CreatePdkRoomRet;
import mj.net.message.room.pdk.JoinPdkRoom;
import mj.net.message.room.pdk.JoinPdkRoomReadyDone;

public class PdkHandler extends MessageHandler{
	
	private boolean isGetMessage;
	private Client client; 

	@Override
	public void onMessage(Message message) {
		System.out.println("这是跑得快项目消息处理器");
		int type = message.getMessageType();
		int id = message.getMessageId();
		if(type==4){
			if(id==1){
				if(message instanceof CreatePdkRoomRet){
					String roomId = ((CreatePdkRoomRet) message).getRoomCheckId();
					joinRoom(roomId);
				}
			}else if(id==8){
				try {
					client.sendMessage(new JoinPdkRoomReadyDone());
				} catch (NotYetConnectedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ProtocolException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void joinRoom(String roomId){
		try {
			Thread.sleep(500);
			client.sendMessage(new JoinPdkRoom(roomId));
		} catch (InterruptedException | NotYetConnectedException | IOException | ProtocolException e) {
			// TODO 自动生成的 catch 块
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
