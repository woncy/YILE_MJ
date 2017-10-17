package mj.net.message.game.pdk;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

import mj.net.message.login.OptionEntry;

public class CreatePdkRoom extends AbstractMessage{
	public static int TYPE = 3;
	public static int ID = 1;
	
	private String profile;
	private java.util.ArrayList<mj.net.message.login.OptionEntry> options;
	
	public CreatePdkRoom(){
		
	}
	
	public CreatePdkRoom(String profile, java.util.ArrayList<mj.net.message.login.OptionEntry> options){
		this.profile = profile;
		this.options = options;
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		profile = in.readString();
		
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
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeString(getProfile());
		
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

	public String getProfile() {
		return profile;
	}
	
	public void setProfile(String profile) {
		this.profile = profile;
	}

	public java.util.ArrayList<mj.net.message.login.OptionEntry> getOptions() {
		return options;
	}
	
	public void setOptions(java.util.ArrayList<mj.net.message.login.OptionEntry> options) {
		this.options = options;
	}

	
	public void addOptions(OptionEntry options) {
		if(this.options == null){
			this.options = new java.util.ArrayList<mj.net.message.login.OptionEntry>();
		}
		this.options.add(options);
	}
	
	@Override
	public String toString() {
		return "CreateRoom [profile=" + profile + ",options=" + options + ", ]";
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
