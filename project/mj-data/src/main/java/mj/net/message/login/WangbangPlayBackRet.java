package mj.net.message.login;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 服务端发送给客户端 结果
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class WangbangPlayBackRet extends AbstractMessage{
	public static final int TYPE			 = 7;
	public static final int ID				 = 39;
	
	private String recordDetail;
	
	public WangbangPlayBackRet(){
		
	}
	
	public WangbangPlayBackRet(String recordDetail){
		this.recordDetail = recordDetail;
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		recordDetail = in.readString();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeString(getRecordDetail());
	}

	public String getRecordDetail() {
		return recordDetail;
	}
	
	public void setRecordDetail(String recordDetail) {
		this.recordDetail = recordDetail;
	}

	
	@Override
	public String toString() {
		return "WaGuanRoomRet [recordDetail=" + recordDetail + ", ]";
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
