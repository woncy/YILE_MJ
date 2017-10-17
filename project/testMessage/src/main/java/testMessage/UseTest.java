package testMessage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;
import java.util.Date;

import com.gunjin.util.testMessage.core.Client;
import com.gunjin.util.testMessage.handler.PdkHandler;
import com.isnowfox.core.io.ProtocolException;

import mj.net.message.login.Ping;

public class UseTest {
	public static void main(String[] args) throws URISyntaxException, NotYetConnectedException, IOException, ProtocolException {
		PdkHandler handler = new PdkHandler();
		Client client = new Client(handler);
		
		client.sendMessage(new Ping(new Date(System.currentTimeMillis()).toLocaleString()));
	}
	
	
}
