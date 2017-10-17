package com.gunjin.util.testMessage.core;

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

public class Client {

	public  final String uri = "ws://127.0.0.1:8010/g";
	WebSocketClient client;
	MessageHandler handler;
	
	public Client(MessageHandler handler) throws URISyntaxException {
		super();
		this.handler = handler;
		initClient();
	}
	public void setHandler(MessageHandler handler) {
		this.handler = handler;
	}
	private void initClient() throws URISyntaxException{
		client = new WebSocketClient(new URI(uri),new Draft_17()) {
	        @Override
	        public void onOpen(ServerHandshake sh) {
	           handler.onOpen(sh);
	        }
	        @Override
	        public void onMessage(String msg) {
	        	
	        }
	        @Override
	        public void onError(Exception e) {
	        	handler.onError(e);
	        }
	
	        @Override
	        public void onClose(int arg0, String arg1, boolean arg2) {
	        	handler.onClose(arg0, arg1, arg2);
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
					message.decode(input);
					System.out.println("收到消息:"+message.toString());
					System.out.println("消息类型:[type="+type+"id="+id+"]");
					handler.onMessage(message);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ProtocolException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
        	}
        

		};
		client.connect();
	}
	
	
	public void sendMessage(Message message) throws NotYetConnectedException, IOException, ProtocolException{
		client.send(write(message));
	}
	private static byte[] write(Message msg) throws IOException, ProtocolException
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
