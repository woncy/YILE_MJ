package testMessage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import com.isnowfox.core.io.MarkCompressInput;
import com.isnowfox.core.io.MarkCompressOutput;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.Message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import mj.net.message.MessageFactoryImpi;
import mj.net.message.login.Login;

@SuppressWarnings("deprecation")
public class WebSocketClientTest {
	public static void main(String[] args) throws URISyntaxException, NotYetConnectedException, IOException, ProtocolException {
		WebSocketClient client = new WebSocketClient(new URI("ws://127.0.0.1:8010/g"),new Draft_17()) {

	        @Override
	        public void onOpen(ServerHandshake arg0) {
	            System.out.println("打开链接");
	        }

	        @Override
	        public void onMessage(String msg) {
	        	
	        
	        }

	        @Override
	        public void onError(Exception arg0) {
	            arg0.printStackTrace();
	            System.out.println("发生错误已关闭");
	        }

	        @Override
	        public void onClose(int arg0, String arg1, boolean arg2) {
	            System.out.println("链接已关闭");
	        }

	        @Override
	        public void onMessage(ByteBuffer bytes) {
	            ByteBuf buffer = ByteBufAllocator.DEFAULT.heapBuffer();
				buffer.writeBytes(bytes.array());
				MarkCompressInput input = (MarkCompressInput) MarkCompressInput.create(buffer);
				try {
					int type = input.readInt();
					int id = input.readInt();
					Message message = MessageFactoryImpi.getInstance().getMessage(type, id);
					
					
					if(type==3){
						forwardToZJH(message);
					}
					
					System.out.println("收到消息:"+message.toString());
					System.out.println("收到消息:[type="+type+"id="+id+"]");
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (ProtocolException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
	        }



	    };
	    client.connect();
//	    while(true){
	    	try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
	    	client.send(write(new Login("ANONYMOUS", null, null, "0", "0")));
//	    	client.send(write(new Ping("")));
//	    }
	}
	
	

	private static void forwardToZJH(Message message) {
		
	}
	
	public static byte[] write(Message msg) throws IOException, ProtocolException
    {
	
		ByteBuf buffer = ByteBufAllocator.DEFAULT.heapBuffer();
		buffer.retain();
        Output _out = MarkCompressOutput.create(buffer);
        _out.writeInt(msg.getMessageType());
        _out.writeInt(msg.getMessageId());
        //_out.writeString(msg.toString());
        msg.encode(_out);
        
        byte[] byteArr = new byte[buffer.readableBytes()];
        buffer.readBytes(byteArr);
//        bo.write(byteArr);
        String msgString = msg.toString();
        int type =msg.getMessageType();
        int id = msg.getMessageId();
        System.out.println("发送消息:"+msgString);
        System.out.println("发送消息:[type="+type+",id="+id+"]");
        return byteArr;
               
        //websocket1.Send(byteArr);
    }
}
