package mj.net.message.login;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 客户端发送给服务端 房间号 局数
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class WangbangPlayBack extends AbstractMessage{
	public static final int TYPE			 = 7;
	public static final int ID				 = 38;
	
	/**
	 * 房间号
	 */
	private String checkRoomId;
	/**
	 * 局数
	 */
	private String chapterIndex;
	
	public WangbangPlayBack(){
		
	}
	
	public WangbangPlayBack(String checkRoomId, String chapterIndex){
		this.checkRoomId = checkRoomId;
		this.chapterIndex = chapterIndex;
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		checkRoomId = in.readString();
		chapterIndex = in.readString();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeString(getCheckRoomId());
		out.writeString(getChapterIndex());
	}

	/**
	 * 房间号
	 */
	public String getCheckRoomId() {
		return checkRoomId;
	}
	
	/**
	 * 房间号
	 */
	public void setCheckRoomId(String checkRoomId) {
		this.checkRoomId = checkRoomId;
	}

	/**
	 * 局数
	 */
	public String getChapterIndex() {
		return chapterIndex;
	}
	
	/**
	 * 局数
	 */
	public void setChapterIndex(String chapterIndex) {
		this.chapterIndex = chapterIndex;
	}

	
	@Override
	public String toString() {
		return "WaGuanRoom [checkRoomId=" + checkRoomId + ",chapterIndex=" + chapterIndex + ", ]";
	}
	
	@Override
	public final int getMessageType() {
		return TYPE;
	}

	@Override
	public final int getMessageId() {
		return ID;
	}
}
