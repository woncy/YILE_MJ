package com.gunjin.util.testMessage.test;

public class PdkMessageTestMain {
	public static void main(String[] args) throws InterruptedException {
		PdkMessageTest test = new PdkMessageTest();
		test.login();
		Thread.sleep(2000);
		test.createRoom();
	}
}
