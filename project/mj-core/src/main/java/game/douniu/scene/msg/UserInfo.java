package game.douniu.scene.msg;

import io.netty.buffer.ByteBuf;

public class UserInfo {
	private int userId;
	private int userIndex;
	private String name;
	
	
	public UserInfo() {
		super();
		// TODO 自动生成的构造函数存根
	}
	
	public UserInfo(int userId, int userIndex, String name) {
		super();
		this.userId = userId;
		this.userIndex = userIndex;
		this.name = name;
	}
	
	public void encode(ByteBuf out){
		out.writeInt(userId);
		out.writeInt(userIndex);
		int len = -1;
		if(name!=null){
			byte[] bytes = name.getBytes();
			len = bytes.length;
			out.writeInt(len);
			out.writeBytes(bytes);
		}
		out.writeInt(len);
	}
	
	public void decode(ByteBuf in){
		this.userId = in.readInt();
		this.userIndex = in.readInt();
		int len = in.readInt();
		if(len>-1){
			byte[] bytes = new byte[len];
			in.readBytes(bytes);
			this.name = new String(bytes);
		}
		
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getUserIndex() {
		return userIndex;
	}
	public void setUserIndex(int userIndex) {
		this.userIndex = userIndex;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", userIndex=" + userIndex + ", name=" + name + "]";
	}
	
	
}
