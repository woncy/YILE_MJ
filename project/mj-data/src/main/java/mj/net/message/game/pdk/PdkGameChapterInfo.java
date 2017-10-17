package mj.net.message.game.pdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class PdkGameChapterInfo extends AbstractMessage{
	public static final int TYPE = 4;
	public static final int ID = 4;
	
//	List<>
	private int fistChupaiUserIndex;//第一个出牌人的位置
	private int currentChupaiUserIndex; //当前出牌人位置
//	private int current;//  当场出牌人倒计时
	private List<PdkUserPlaceMsg> userPlaces;
	
	public PdkGameChapterInfo() {
		super();
	}
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.fistChupaiUserIndex = in.readInt();
		this.currentChupaiUserIndex = in.readInt();
		
		int usersLength = in.readInt();
		if(usersLength!=-1){
			this.userPlaces = new ArrayList<PdkUserPlaceMsg>();
		}
		for(int i=0;i<usersLength;i++){
			PdkUserPlaceMsg msg = new PdkUserPlaceMsg();
			msg.decode(in);
			userPlaces.add(msg);
		}
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(fistChupaiUserIndex);
		out.writeInt(currentChupaiUserIndex);
		int userPlacesLength = -1;
		if(userPlaces!=null){
			userPlacesLength = userPlaces.size();
		}
		out.writeInt(userPlacesLength);
		for (int i = 0; i < userPlacesLength; i++) {
			this.userPlaces.get(i).encode(out);
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
	public int getFistChupaiUserIndex() {
		return fistChupaiUserIndex;
	}
	public void setFistChupaiUserIndex(int fistChupaiUserIndex) {
		this.fistChupaiUserIndex = fistChupaiUserIndex;
	}
	public int getCurrentChupaiUserIndex() {
		return currentChupaiUserIndex;
	}
	public void setCurrentChupaiUserIndex(int currentChupaiUserIndex) {
		this.currentChupaiUserIndex = currentChupaiUserIndex;
	}
	public List<PdkUserPlaceMsg> getUserPlaces() {
		return userPlaces;
	}
	public void setUserPlaces(List<PdkUserPlaceMsg> userPlaces) {
		this.userPlaces = userPlaces;
	}
	@Override
	public String toString() {
		return "PdkGameChapterInfo [fistChupaiUserIndex=" + fistChupaiUserIndex + ", currentChupaiUserIndex="
				+ currentChupaiUserIndex + ", userPlaces=" + userPlaces + "]";
	}
	
	
	
}
