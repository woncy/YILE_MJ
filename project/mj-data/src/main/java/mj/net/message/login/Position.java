package mj.net.message.login;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class Position extends AbstractMessage {
	public static final int TYPE = 1;
	public static final int	ID = 35;
	
	private int locationIndex;
	private double lontitude;	//经度
	private double latitude;	//纬度
	
	
	public Position() {
		super();
	}

	public Position(int locationIndex, double lontitude, double latitude) {
		super();
		this.locationIndex = locationIndex;
		this.lontitude = lontitude;
		this.latitude = latitude;
	}

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.locationIndex = in.readInt();
		this.lontitude = Double.parseDouble(in.readString());
		this.latitude = Double.parseDouble(in.readString());
	}

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(locationIndex);
		if(lontitude == 0L){
			lontitude = -1L;
		}
		if(latitude == 0L){
			latitude = -1L;
		}
		out.writeString(lontitude+"");
		out.writeString(latitude+"");
	}

	@Override
	public int getMessageId() {
		return ID;
	}
	
	

	@Override
	public String toString() {
		return "Position [locationIndex=" + locationIndex + ", lontitude=" + lontitude + ", latitude=" + latitude + "]";
	}

	public int getLocationIndex() {
		return locationIndex;
	}

	public void setLocationIndex(int locationIndex) {
		this.locationIndex = locationIndex;
	}


	public double getLontitude() {
		return lontitude;
	}

	public void setLontitude(double lontitude) {
		this.lontitude = lontitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	@Override
	public int getMessageType() {
		return TYPE;
	}

}
