package mj.net.message.game.zjh;

import java.io.IOException;
import java.util.ArrayList;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
/**
 * 所有房间战绩
 * @author 13323659953@163.com
 *
 */
public class ZJHRoomHistoryList extends  AbstractMessage{
	public static final int TYPE = 3;
	public static final int ID = 29;
	
	
	private ArrayList<ZJHRoomHistory> roomHistorys;
	
	public ZJHRoomHistoryList() {
	}
	
	public ZJHRoomHistoryList(ArrayList<ZJHRoomHistory> roomHistorys) {
		super();
		this.roomHistorys = roomHistorys;
	}

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		
		int listLen = in.readInt();
		if(listLen == -1){
			roomHistorys = null;
		}else{
			roomHistorys = new ArrayList<ZJHRoomHistory>(listLen);
			for(int i = 0; i < listLen; i++){
				ZJHRoomHistory listItem = new ZJHRoomHistory();
				listItem.decode(in);
				roomHistorys.add(listItem);
			}
		}
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		if(roomHistorys == null){
			out.writeInt(-1);
		}else{
			ArrayList<ZJHRoomHistory> listList =getRoomHistorys();
			int listLen = listList.size();
			out.writeInt(listLen);
			for(ZJHRoomHistory listItem: listList){
				listItem.encode(out);
			}
		}
	}
	
	public ArrayList<ZJHRoomHistory> getRoomHistorys() {
		return roomHistorys;
	}

	public void setRoomHistorys(ArrayList<ZJHRoomHistory> roomHistorys) {
		this.roomHistorys = roomHistorys;
	}

	@Override
	public int getMessageId() {
		return ID;
	}
	@Override
	public int getMessageType() {
		return TYPE;
	}

}
