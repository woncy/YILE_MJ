package mj.net.message.login;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

public class UserIF {
	private String headUrl;
	private String name;
	
	
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "UserIF [headUrl=" + headUrl + ", name=" + name + "]";
	}
	public void decode(Input in) throws IOException, ProtocolException {
		this.headUrl = in.readString();
		this.name = in.readString();
		
	}
	public void encode(Output out) throws IOException {
		out.writeString(this.headUrl);
		out.writeString(this.name);
	}
	
	
	
	
}
