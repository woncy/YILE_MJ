package mj.net.message.login;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
/**
 * 
    * @ClassName: Question
    * @Description: 用户反馈
    * @author 13323659953@163.com
    * @date 2017年7月18日
    *
 */
public class Question extends AbstractMessage{
	public static final int TYPE=7;
	public static final int ID=37;
	private String content;
	
	public Question() {
		super();
	}
	public Question(String content) {
		super();
		this.content = content;
	}
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.content = in.readString();
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeString(content);
	}
	
	
	@Override
	public int getMessageId() {
		return ID;
	}
	@Override
	public int getMessageType() {
		return TYPE;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Question [content=" + content + "]";
	}
	
	
	
	
	

}
