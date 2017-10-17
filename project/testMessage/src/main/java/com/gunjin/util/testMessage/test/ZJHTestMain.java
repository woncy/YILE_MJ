package com.gunjin.util.testMessage.test;

public class ZJHTestMain {
public static void main(String[] args) {
	ZJHMessageTest test = new ZJHMessageTest();
	test.login();
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	test.createRoom();
	
}
}
