package mj.net.message.login;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

/**
    * @ClassName: Location
    * @Description: 更新经纬度
    * @author 13323659953@163.com
    * @date 2017年7月29日
    *
    */

public class Location extends AbstractMessage{
	public static final int TYPE = 7;
	public static final int ID = 40;
	private String longtitude;
	private String latitude;
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.longtitude = in.readString();
		this.latitude = in.readString();
	}
	    
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeString(longtitude);
		out.writeString(latitude);
	}

	    
	@Override
	public int getMessageId() {
		return ID;
	}

	
	@Override
	public int getMessageType() {
		return TYPE;
	}
	
	


	public String getLongtitude() {
		return longtitude;
	}


	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	@Override
	public String toString() {
		return "Location [longtitude=" + longtitude + ", latitude=" + latitude + "]";
	}

	
	
}

