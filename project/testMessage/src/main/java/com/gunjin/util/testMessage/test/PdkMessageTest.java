package com.gunjin.util.testMessage.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;
import java.util.ArrayList;

import com.gunjin.util.testMessage.core.Client;
import com.gunjin.util.testMessage.handler.PdkHandler;
import com.isnowfox.core.io.ProtocolException;

import mj.net.message.login.Login;
import mj.net.message.login.OptionEntry;
import mj.net.message.room.pdk.CreatePdkRoom;

public class PdkMessageTest {
	Client client;
	private String loginToken;
	PdkHandler handler;
	public PdkMessageTest() {
		handler = new PdkHandler();
		loginToken = "d1f43cc0-99c5-4b7d-9b11-992149a909a8";
		try {
			client = new Client(handler);
			handler.setClient(client);
		} catch (URISyntaxException e) {
		}
	}
	
	

	
	public PdkHandler getHandler() {
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
			CreatePdkRoom createPdkRoom = new CreatePdkRoom();
			createPdkRoom.setOptions(opt);
			client.sendMessage(createPdkRoom);
		} catch (NotYetConnectedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
}
