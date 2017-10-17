package mj.net.message.login;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class ClubInfo extends AbstractMessage{
	private static final int TYPE = 7;
	private static final int ID = 36;
	private String clubId;    //俱乐部Id
    private String createUserName;//创建者真实姓名
    private String wxNo;    // 微信号
    private String notice;    //公告
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.clubId = in.readString();
		this.createUserName = in.readString();
		this.wxNo = in.readString();
		this.notice = in.readString();
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeString(clubId);
		out.writeString(createUserName);
		out.writeString(wxNo);
		out.writeString(notice);
	}
	@Override
	public int getMessageId() {
		return ID;
	}
	@Override
	public int getMessageType() {
		return TYPE;
	}
	public String getClubId() {
		return clubId;
	}
	public void setClubId(String clubId) {
		this.clubId = clubId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getWxNo() {
		return wxNo;
	}
	public void setWxNo(String wxNo) {
		this.wxNo = wxNo;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	@Override
	public String toString() {
		return "ClubInfo [clubId=" + clubId + ", createUserName=" + createUserName + ", wxNo=" + wxNo + ", notice="
				+ notice + "]";
	}
	
	
	
	
}
