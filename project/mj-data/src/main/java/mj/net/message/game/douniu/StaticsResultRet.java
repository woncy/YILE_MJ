package mj.net.message.game.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class StaticsResultRet extends AbstractMessage {
	
	public static final int TYPE			 = 5;
	public static final int ID				 = 31;
	
	private int locationIndex0;
	private int niunin0;
	private int xiaoniu0;
	private int meiniu0;
	private int zhaniu0;
	private int niuNum0;
	private int score0 = 0  ;
	
	private int locationIndex1;
	private int niunin1;
	private int xiaoniu1;
	private int meiniu1;
	private int zhaniu1;
	private int niuNum1;
	private int score1 = 0  ;
	
	private int locationIndex2;
	private int niunin2;
	private int xiaoniu2;
	private int meiniu2;
	private int zhaniu2;
	private int niuNum2;
	private int score2 = 0 ;
	
	private int locationIndex3;
	private int niunin3;
	private int xiaoniu3;
	private int meiniu3;
	private int zhaniu3;
	private int niuNum3;
	private int score3 = 0 ;

	private int locationIndex4;
	private int niunin4;
	private int xiaoniu4;
	private int meiniu4;
	private int zhaniu4;
	private int niuNum4;
	private int score4 = 0 ;
	
	private int locationIndex5;
	private int niunin5;
	private int xiaoniu5;
	private int meiniu5;
	private int zhaniu5;
	private int niuNum5;
	private int score5 = 0 ;
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		// TODO Auto-generated method stub
//		chatContent = in.readString();
		
	}

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		// TODO Auto-generated method stub
		out.writeInt(locationIndex0);
		out.writeInt(niunin0);
		out.writeInt(xiaoniu0);
		out.writeInt(meiniu0);
		out.writeInt(zhaniu0);
		out.writeInt(niuNum0);
		out.writeInt(score0);
		
		out.writeInt(locationIndex1);
		out.writeInt(niunin1);
		out.writeInt(xiaoniu1);
		out.writeInt(meiniu1);
		out.writeInt(zhaniu1);
		out.writeInt(niuNum1);
		out.writeInt(score1);
		
		out.writeInt(locationIndex2);
		out.writeInt(niunin2);
		out.writeInt(xiaoniu2);
		out.writeInt(meiniu2);
		out.writeInt(zhaniu2);
		out.writeInt(niuNum2);
		out.writeInt(score2);
		
		out.writeInt(locationIndex3);
		out.writeInt(niunin3);
		out.writeInt(xiaoniu3);
		out.writeInt(meiniu3);
		out.writeInt(zhaniu3);
		out.writeInt(niuNum3);
		out.writeInt(score3);
		
		out.writeInt(locationIndex4);
		out.writeInt(niunin4);
		out.writeInt(xiaoniu4);
		out.writeInt(meiniu4);
		out.writeInt(zhaniu4);
		out.writeInt(niuNum4);
		out.writeInt(score4);
		
		out.writeInt(locationIndex5);
		out.writeInt(niunin5);
		out.writeInt(xiaoniu5);
		out.writeInt(meiniu5);
		out.writeInt(zhaniu5);
		out.writeInt(niuNum5);
		out.writeInt(score5);
	}

	public int getLocationIndex0() {
		return locationIndex0;
	}

	public void setLocationIndex0(int locationIndex0) {
		this.locationIndex0 = locationIndex0;
	}

	public int getNiunin0() {
		return niunin0;
	}

	public void setNiunin0(int niunin0) {
		this.niunin0 = niunin0;
	}

	public int getXiaoniu0() {
		return xiaoniu0;
	}

	public void setXiaoniu0(int xiaoniu0) {
		this.xiaoniu0 = xiaoniu0;
	}

	public int getMeiniu0() {
		return meiniu0;
	}

	public void setMeiniu0(int meiniu0) {
		this.meiniu0 = meiniu0;
	}

	public int getZhaniu0() {
		return zhaniu0;
	}

	public void setZhaniu0(int zhaniu0) {
		this.zhaniu0 = zhaniu0;
	}

	public int getNiuNum0() {
		return niuNum0;
	}

	public void setNiuNum0(int niuNum0) {
		this.niuNum0 = niuNum0;
	}

	public int getScore0() {
		return score0;
	}

	public void setScore0(int score0) {
		this.score0 = score0;
	}

	public int getLocationIndex1() {
		return locationIndex1;
	}

	public void setLocationIndex1(int locationIndex1) {
		this.locationIndex1 = locationIndex1;
	}

	public int getNiunin1() {
		return niunin1;
	}

	public void setNiunin1(int niunin1) {
		this.niunin1 = niunin1;
	}

	public int getXiaoniu1() {
		return xiaoniu1;
	}

	public void setXiaoniu1(int xiaoniu1) {
		this.xiaoniu1 = xiaoniu1;
	}

	public int getMeiniu1() {
		return meiniu1;
	}

	public void setMeiniu1(int meiniu1) {
		this.meiniu1 = meiniu1;
	}

	public int getZhaniu1() {
		return zhaniu1;
	}

	public void setZhaniu1(int zhaniu1) {
		this.zhaniu1 = zhaniu1;
	}

	public int getNiuNum1() {
		return niuNum1;
	}

	public void setNiuNum1(int niuNum1) {
		this.niuNum1 = niuNum1;
	}

	public int getScore1() {
		return score1;
	}

	public void setScore1(int score1) {
		this.score1 = score1;
	}

	public int getLocationIndex2() {
		return locationIndex2;
	}

	public void setLocationIndex2(int locationIndex2) {
		this.locationIndex2 = locationIndex2;
	}

	public int getNiunin2() {
		return niunin2;
	}

	public void setNiunin2(int niunin2) {
		this.niunin2 = niunin2;
	}

	public int getXiaoniu2() {
		return xiaoniu2;
	}

	public void setXiaoniu2(int xiaoniu2) {
		this.xiaoniu2 = xiaoniu2;
	}

	public int getMeiniu2() {
		return meiniu2;
	}

	public void setMeiniu2(int meiniu2) {
		this.meiniu2 = meiniu2;
	}

	public int getZhaniu2() {
		return zhaniu2;
	}

	public void setZhaniu2(int zhaniu2) {
		this.zhaniu2 = zhaniu2;
	}

	public int getNiuNum2() {
		return niuNum2;
	}

	public void setNiuNum2(int niuNum2) {
		this.niuNum2 = niuNum2;
	}

	public int getScore2() {
		return score2;
	}

	public void setScore2(int score2) {
		this.score2 = score2;
	}

	public int getLocationIndex3() {
		return locationIndex3;
	}

	public void setLocationIndex3(int locationIndex3) {
		this.locationIndex3 = locationIndex3;
	}

	public int getNiunin3() {
		return niunin3;
	}

	public void setNiunin3(int niunin3) {
		this.niunin3 = niunin3;
	}

	public int getXiaoniu3() {
		return xiaoniu3;
	}

	public void setXiaoniu3(int xiaoniu3) {
		this.xiaoniu3 = xiaoniu3;
	}

	public int getMeiniu3() {
		return meiniu3;
	}

	public void setMeiniu3(int meiniu3) {
		this.meiniu3 = meiniu3;
	}

	public int getZhaniu3() {
		return zhaniu3;
	}

	public void setZhaniu3(int zhaniu3) {
		this.zhaniu3 = zhaniu3;
	}

	public int getNiuNum3() {
		return niuNum3;
	}

	public void setNiuNum3(int niuNum3) {
		this.niuNum3 = niuNum3;
	}

	public int getScore3() {
		return score3;
	}

	public void setScore3(int score3) {
		this.score3 = score3;
	}

	public int getLocationIndex4() {
		return locationIndex4;
	}

	public void setLocationIndex4(int locationIndex4) {
		this.locationIndex4 = locationIndex4;
	}

	public int getNiunin4() {
		return niunin4;
	}

	public void setNiunin4(int niunin4) {
		this.niunin4 = niunin4;
	}

	public int getXiaoniu4() {
		return xiaoniu4;
	}

	public void setXiaoniu4(int xiaoniu4) {
		this.xiaoniu4 = xiaoniu4;
	}

	public int getMeiniu4() {
		return meiniu4;
	}

	public void setMeiniu4(int meiniu4) {
		this.meiniu4 = meiniu4;
	}

	public int getZhaniu4() {
		return zhaniu4;
	}

	public void setZhaniu4(int zhaniu4) {
		this.zhaniu4 = zhaniu4;
	}

	public int getNiuNum4() {
		return niuNum4;
	}

	public void setNiuNum4(int niuNum4) {
		this.niuNum4 = niuNum4;
	}

	public int getScore4() {
		return score4;
	}

	public void setScore4(int score4) {
		this.score4 = score4;
	}

	public int getLocationIndex5() {
		return locationIndex5;
	}

	public void setLocationIndex5(int locationIndex5) {
		this.locationIndex5 = locationIndex5;
	}

	public int getNiunin5() {
		return niunin5;
	}

	public void setNiunin5(int niunin5) {
		this.niunin5 = niunin5;
	}

	public int getXiaoniu5() {
		return xiaoniu5;
	}

	public void setXiaoniu5(int xiaoniu5) {
		this.xiaoniu5 = xiaoniu5;
	}

	public int getMeiniu5() {
		return meiniu5;
	}

	public void setMeiniu5(int meiniu5) {
		this.meiniu5 = meiniu5;
	}

	public int getZhaniu5() {
		return zhaniu5;
	}

	public void setZhaniu5(int zhaniu5) {
		this.zhaniu5 = zhaniu5;
	}

	public int getNiuNum5() {
		return niuNum5;
	}

	public void setNiuNum5(int niuNum5) {
		this.niuNum5 = niuNum5;
	}

	public int getScore5() {
		return score5;
	}

	public void setScore5(int score5) {
		this.score5 = score5;
	}

	@Override
	public String toString() {
		return "StaticsResultRet [locationIndex0=" + locationIndex0
				+ ", niunin0=" + niunin0 + ", xiaoniu0=" + xiaoniu0
				+ ", meiniu0=" + meiniu0 + ", zhaniu0=" + zhaniu0
				+ ", niuNum0=" + niuNum0 + ", score0=" + score0
				+ ", locationIndex1=" + locationIndex1 + ", niunin1=" + niunin1
				+ ", xiaoniu1=" + xiaoniu1 + ", meiniu1=" + meiniu1
				+ ", zhaniu1=" + zhaniu1 + ", niuNum1=" + niuNum1 + ", score1="
				+ score1 + ", locationIndex2=" + locationIndex2 + ", niunin2="
				+ niunin2 + ", xiaoniu2=" + xiaoniu2 + ", meiniu2=" + meiniu2
				+ ", zhaniu2=" + zhaniu2 + ", niuNum2=" + niuNum2 + ", score2="
				+ score2 + ", locationIndex3=" + locationIndex3 + ", niunin3="
				+ niunin3 + ", xiaoniu3=" + xiaoniu3 + ", meiniu3=" + meiniu3
				+ ", zhaniu3=" + zhaniu3 + ", niuNum3=" + niuNum3 + ", score3="
				+ score3 + ", locationIndex4=" + locationIndex4 + ", niunin4="
				+ niunin4 + ", xiaoniu4=" + xiaoniu4 + ", meiniu4=" + meiniu4
				+ ", zhaniu4=" + zhaniu4 + ", niuNum4=" + niuNum4 + ", score4="
				+ score4 + ", locationIndex5=" + locationIndex5 + ", niunin5="
				+ niunin5 + ", xiaoniu5=" + xiaoniu5 + ", meiniu5=" + meiniu5
				+ ", zhaniu5=" + zhaniu5 + ", niuNum5=" + niuNum5 + ", score5="
				+ score5 + "]";
	}

	@Override
	public int getMessageId() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public int getMessageType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	

	
}
