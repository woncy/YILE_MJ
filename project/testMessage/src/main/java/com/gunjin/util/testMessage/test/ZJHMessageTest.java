package com.gunjin.util.testMessage.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;
import java.util.ArrayList;

import com.gunjin.util.testMessage.core.Client;
import com.gunjin.util.testMessage.handler.ZJHHandler;
import com.isnowfox.core.io.ProtocolException;

import mj.net.message.login.Login;
import mj.net.message.login.OptionEntry;
import mj.net.message.login.zjh.CreateZJHRoom;
import mj.net.message.login.zjh.JoinZJHRoom;

public class ZJHMessageTest {
	Client client;
	private String loginToken;
	ZJHHandler handler;
	public ZJHMessageTest() {
		handler = new ZJHHandler();
		loginToken = "d1f43cc0-99c5-4b7d-9b11-992149a909a8";
		try {
			client = new Client(handler);
			handler.setClient(client);
		} catch (URISyntaxException e) {
		}
	}
	
	

	
	public ZJHHandler getHandler() {
		return handler;
	}


	public void login(){
		try {
			client.sendMessage(new Login("ANONYMOUS", (String)null, (String)null, "34.7545", "0"));
		} catch (NotYetConnectedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		}
	}
	
	public void createRoom(){
		try {
			ArrayList<OptionEntry> opt = new ArrayList<OptionEntry>();
			CreateZJHRoom CreateZJHRoom = new CreateZJHRoom();
			CreateZJHRoom.setOptions(opt);
			client.sendMessage(CreateZJHRoom);
		} catch (NotYetConnectedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		}
	}
	
	public void joinRoom(){
		try {
			JoinZJHRoom join = new JoinZJHRoom("477408");
			client.sendMessage(join);
		} catch (NotYetConnectedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		}
	}
}
