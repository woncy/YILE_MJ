package mj.net.message.login.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

public class DouniuRoomHistoryListRet extends AbstractMessage{
	public static final int TYPE			 = 5;
	public static final int ID				 = 49;
	
	private java.util.ArrayList<DouniuRoomHistory> list;
	
	public DouniuRoomHistoryListRet(){
		
	}
	
	public DouniuRoomHistoryListRet(java.util.ArrayList<DouniuRoomHistory> list){
		this.list = list;
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		
		int listLen = in.readInt();
		if(listLen == -1){
			list = null;
		}else{
			list = new java.util.ArrayList<DouniuRoomHistory>(listLen);
			for(int i = 0; i < listLen; i++){
				DouniuRoomHistory listItem = new DouniuRoomHistory();
				listItem.decode(in);
				list.add(listItem);
			}
		}
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		
		if(list == null){
			out.writeInt(-1);
		}else{
			java.util.ArrayList<DouniuRoomHistory> listList = getList();
			int listLen = listList.size();
			out.writeInt(listLen);
			for(DouniuRoomHistory listItem: listList){
				listItem.encode(out);
			}
		}
	}

	public java.util.ArrayList<DouniuRoomHistory> getList() {
		return list;
	}

	public void setList(java.util.ArrayList<DouniuRoomHistory> list) {
		this.list = list;
	}

	public void addList(DouniuRoomHistory list) {
		if(this.list == null){
			this.list = new java.util.ArrayList<DouniuRoomHistory>();
		}
		this.list.add(list);
	}
	
	
	
	@Override
	public String toString() {
		return "DouniuRoomHistoryListRet [list=" + list + "]";
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
