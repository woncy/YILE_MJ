package mj.net.message.login.zjh;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

import mj.net.message.login.OptionEntry;

public class CreateZJHRoom  extends AbstractMessage{
	public static final int TYPE			 = 3;
	public static final int ID				 = 0;
	private java.util.ArrayList<OptionEntry> options;
	
	public CreateZJHRoom(){
		
	}
	
	public CreateZJHRoom( java.util.ArrayList<OptionEntry> options){
		this.options = options;
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		
		int optionsLen = in.readInt();
		if(optionsLen == -1){
			options = null;
		}else{
			options = new java.util.ArrayList<OptionEntry>(optionsLen);
			for(int i = 0; i < optionsLen; i++){
				OptionEntry optionsItem = new OptionEntry();
				optionsItem.decode(in);
				options.add(optionsItem);
			}
		}
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		
		if(options == null){
			out.writeInt(-1);
		}else{
			java.util.ArrayList<OptionEntry> optionsList = getOptions();
			int optionsLen = optionsList.size();
			out.writeInt(optionsLen);
			for(OptionEntry optionsItem: optionsList){
				optionsItem.encode(out);
			}
		}
	}


	public java.util.ArrayList<OptionEntry> getOptions() {
		return options;
	}
	
	public void setOptions(java.util.ArrayList<OptionEntry> options) {
		this.options = options;
	}

	
	public void addOptions(OptionEntry options) {
		if(this.options == null){
			this.options = new java.util.ArrayList<OptionEntry>();
		}
		this.options.add(options);
	}
	
	@Override
	public String toString() {
		return "CreateRoom [options=" + options + ", ]";
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
