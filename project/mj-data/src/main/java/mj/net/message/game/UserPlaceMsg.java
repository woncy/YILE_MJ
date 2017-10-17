package mj.net.message.game;

import java.io.IOException;
import java.util.Arrays;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 一局麻将的信息
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class UserPlaceMsg extends AbstractMessage{
	public static final int TYPE			 = 1;
	public static final int ID				 = 20;
	
	private int[] shouPai;
	/**
	 * 别人的信息只显示手牌数量
	 */
	private int shouPaiLen;
	/**
	 * 已经显示的暗杠，如果是自己的则全部显示,不是自己的且如果还不能显示，那么传递-1
	 */
	private int[] anGang;
	private int[] xiaoMingGang;
	private int[] daMingGang;
	private int[] peng;
	/**
	 * 3个一组一组
	 */
	private int[] chi;
	/**
	 * 打出的牌
	 */
	private int[] outPai;
	private int xuanPaoCount;
	private int[]chiIndex;     //暂时未用此属性，但有定义，也需解码
    private int[]pengIndex;    //  和上面的peng[]一一对应
    private int[]daMingGangIndex;    //和上面的daMingGang[]一一对应
    private int[]xiaoMingGangIndex;    //和上面的xiaoMingGang[]一一对应
	
	public UserPlaceMsg(){
		
	}
	
	public UserPlaceMsg(int[] shouPai, int shouPaiLen, int[] anGang, int[] xiaoMingGang, int[] daMingGang, int[] peng, int[] chi, int[] outPai,int xuanPaoCount){
		this.shouPai = shouPai;
		this.shouPaiLen = shouPaiLen;
		this.anGang = anGang;
		this.xiaoMingGang = xiaoMingGang;
		this.daMingGang = daMingGang;
		this.peng = peng;
		this.chi = chi;
		this.outPai = outPai;
		this.xuanPaoCount = xuanPaoCount;
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		shouPai = in.readIntArray();
		shouPaiLen = in.readInt();
		anGang = in.readIntArray();
		xiaoMingGang = in.readIntArray();
		daMingGang = in.readIntArray();
		peng = in.readIntArray();
		chi = in.readIntArray();
		outPai = in.readIntArray();
		this.xuanPaoCount = in.readInt();
		this.chiIndex = in.readIntArray();
		this.pengIndex = in.readIntArray();
		this.daMingGangIndex = in.readIntArray();
		this.xiaoMingGangIndex = in.readIntArray();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeIntArray(getShouPai());
		out.writeInt(getShouPaiLen());
		out.writeIntArray(getAnGang());
		out.writeIntArray(getXiaoMingGang());
		out.writeIntArray(getDaMingGang());
		out.writeIntArray(getPeng());
		out.writeIntArray(getChi());
		out.writeIntArray(getOutPai());
		out.writeInt(xuanPaoCount);
		out.writeIntArray(this.chiIndex);
		out.writeIntArray(this.pengIndex);
		out.writeIntArray(this.daMingGangIndex);
		out.writeIntArray(this.xiaoMingGangIndex);
	}

	public int[] getShouPai() {
		return shouPai;
	}
	
	public void setShouPai(int[] shouPai) {
		this.shouPai = shouPai;
	}

	/**
	 * 别人的信息只显示手牌数量
	 */
	public int getShouPaiLen() {
		return shouPaiLen;
	}
	
	/**
	 * 别人的信息只显示手牌数量
	 */
	public void setShouPaiLen(int shouPaiLen) {
		this.shouPaiLen = shouPaiLen;
	}
	
	

	public int getXuanPaoCount() {
		return xuanPaoCount;
	}

	public void setXuanPaoCount(int xuanPaoCount) {
		this.xuanPaoCount = xuanPaoCount;
	}

	/**
	 * 已经显示的暗杠，如果是自己的则全部显示,不是自己的且如果还不能显示，那么传递-1
	 */
	public int[] getAnGang() {
		return anGang;
	}
	
	/**
	 * 已经显示的暗杠，如果是自己的则全部显示,不是自己的且如果还不能显示，那么传递-1
	 */
	public void setAnGang(int[] anGang) {
		this.anGang = anGang;
	}

	public int[] getXiaoMingGang() {
		return xiaoMingGang;
	}
	
	public void setXiaoMingGang(int[] xiaoMingGang) {
		this.xiaoMingGang = xiaoMingGang;
	}

	public int[] getDaMingGang() {
		return daMingGang;
	}
	
	public void setDaMingGang(int[] daMingGang) {
		this.daMingGang = daMingGang;
	}

	public int[] getPeng() {
		return peng;
	}
	
	public void setPeng(int[] peng) {
		this.peng = peng;
	}

	/**
	 * 3个一组一组
	 */
	public int[] getChi() {
		return chi;
	}
	
	/**
	 * 3个一组一组
	 */
	public void setChi(int[] chi) {
		this.chi = chi;
	}

	/**
	 * 打出的牌
	 */
	public int[] getOutPai() {
		return outPai;
	}
	
	/**
	 * 打出的牌
	 */
	public void setOutPai(int[] outPai) {
		this.outPai = outPai;
	}

	
	
	public int[] getChiIndex() {
		return chiIndex;
	}

	public void setChiIndex(int[] chiIndex) {
		this.chiIndex = chiIndex;
	}

	public int[] getPengIndex() {
		return pengIndex;
	}

	public void setPengIndex(int[] pengIndex) {
		this.pengIndex = pengIndex;
	}

	public int[] getDaMingGangIndex() {
		return daMingGangIndex;
	}

	public void setDaMingGangIndex(int[] daMingGangIndex) {
		this.daMingGangIndex = daMingGangIndex;
	}

	public int[] getXiaoMingGangIndex() {
		return xiaoMingGangIndex;
	}

	public void setXiaoMingGangIndex(int[] xiaoMingGangIndex) {
		this.xiaoMingGangIndex = xiaoMingGangIndex;
	}

	@Override
	public String toString() {
		return "UserPlaceMsg [shouPai=" + Arrays.toString(shouPai) + ", shouPaiLen=" + shouPaiLen + ", anGang="
				+ Arrays.toString(anGang) + ", xiaoMingGang=" + Arrays.toString(xiaoMingGang) + ", daMingGang="
				+ Arrays.toString(daMingGang) + ", peng=" + Arrays.toString(peng) + ", chi=" + Arrays.toString(chi)
				+ ", outPai=" + Arrays.toString(outPai) + ", xuanPaoCount=" + xuanPaoCount + ", chiIndex="
				+ Arrays.toString(chiIndex) + ", pengIndex=" + Arrays.toString(pengIndex) + ", daMingGangIndex="
				+ Arrays.toString(daMingGangIndex) + ", xiaoMingGangIndex=" + Arrays.toString(xiaoMingGangIndex) + "]";
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
