package mj.net.message.room.pdk;

import java.io.IOException;
import java.util.ArrayList;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

import mj.net.message.login.OptionEntry;
/**
 * 
    * @ClassName: CreatePdkRoom
    * @Description: 创建房间的消息
    * @author 13323659953@163.com
    * @date 2017年6月30日
    *
 */
public class CreatePdkRoom extends AbstractMessage{
	
	public static final int TYPE = 4;
	public static final int ID = 0;
	
	private ArrayList<OptionEntry> options;//创建房间所需要的一些配置
	
	public CreatePdkRoom() {
		super();
	}

	public ArrayList<OptionEntry> getOptions() {
		return options;
	}

	public void setOptions(ArrayList<OptionEntry> options) {
		this.options = options;
	}


	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		int optionsLen = in.readInt();
		if(optionsLen == -1){
			options = null;
		}else{
			options = new java.util.ArrayList<mj.net.message.login.OptionEntry>(optionsLen);
			for(int i = 0; i < optionsLen; i++){
				OptionEntry optionsItem = new OptionEntry();
				optionsItem.decode(in);
				options.add(optionsItem);
			}
		}
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		if(options == null){
			out.writeInt(-1);
		}else{
			java.util.ArrayList<mj.net.message.login.OptionEntry> optionsList = getOptions();
			int optionsLen = optionsList.size();
			out.writeInt(optionsLen);
			for(OptionEntry optionsItem: optionsList){
				optionsItem.encode(out);
			}
		}
		
	}
	@Override
	public int getMessageId() {
		return ID;
	}
	@Override
	public int getMessageType() {
		return TYPE;
	}


	@Override
	public String toString() {
		return "CreatePdkRoom [options=" + options + "]";
	}


}
