package mj.net.message.game;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

/**
 * Transfer
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class SettingInfoRet extends AbstractMessage{
	public static final int TYPE			 = 1;
	public static final int ID				 = 26;
	
	public SettingInfoRet(){
		
	}
	
	public  int chapterMax = 0;//局数
    public  boolean isHuiEr = false;//带混
    public  boolean isGangDaiPao = false;//杠带跑 ``
    public  boolean isDaiZiPai = false;//带字牌 ``
    public  boolean isQiDuiFanBei = false;//7对翻倍 7对胡本来就是有的 ``
    public  boolean isZhuangJiaDi = false;//庄加底 ``
    public  boolean isGangKaiFan = false;//杠开翻倍 ``
    public  int xuanPaoCount = 0 ;//选跑局数 4局 每局``
    public  boolean isFangPao = false;//点炮胡  `
	

	public int getChaptermax() {
		return chapterMax;
	}

	public boolean getIshuier() {
		return isHuiEr;
	}

	public boolean getIsgangdaipao() {
		return isGangDaiPao;
	}

	public boolean getIsdaizipai() {
		return isDaiZiPai;
	}

	public boolean getIsqiduifanbei() {
		return isQiDuiFanBei;
	}

	public boolean getIszhuangjiadi() {
		return isZhuangJiaDi;
	}

	public boolean getIsgangkaifan() {
		return isGangKaiFan;
	}

	public int getXuanpaocount() {
		return xuanPaoCount;
	}

	public boolean getIsfangpao() {
		return isFangPao;
	}
	
	public void  setChaptermax(int chapterMax) {
		this.chapterMax = chapterMax;
	}

	public void  setIshuier(boolean isHuiEr) {
		this.isHuiEr = isHuiEr;
	}

	public void setIsgangdaipao(boolean isGangDaiPao) {
		this.isGangDaiPao = isGangDaiPao;
	}

	public void setIsdaizipai(boolean isDaiZiPai) {
		this.isDaiZiPai = isDaiZiPai;
	}

	public void setIsqiduifanbei(boolean isQiDuiFanBei) {
		this.isQiDuiFanBei = isQiDuiFanBei;
	}

	public void setIszhuangjiadi(boolean isZhuangJiaDi) {
		this.isZhuangJiaDi = isZhuangJiaDi;
	}

	public void  setIsgangkaifan(boolean isGangKaiFan) {
		this.isGangKaiFan = isGangKaiFan;
	}

	public void  getXuanpaocount(int xuanPaoCount) {
		this.xuanPaoCount = xuanPaoCount;
	}

	public  void setIsfangpao(boolean isFangPao) {
		 this.isFangPao = isFangPao;
	}
	

	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		
	}

	/*
	 * 
	 */
	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeInt(chapterMax);
		out.writeBoolean(isHuiEr);
		out.writeBoolean(isGangDaiPao);
		out.writeBoolean(isDaiZiPai);
		out.writeBoolean(isQiDuiFanBei);
		out.writeBoolean(isZhuangJiaDi);
		out.writeBoolean(isGangKaiFan);
		out.writeInt(xuanPaoCount);
		out.writeBoolean(isFangPao);
	}

	
	@Override
	public String toString() {
		return "SettingInfoRet  [chapterMax = "+chapterMax + ",  isHuiEr = "+ isHuiEr
				 + ",  isGangDaiPao = "+ isGangDaiPao 
				 + ",  isDaiZiPai = "+ isDaiZiPai
				 + ",  isQiDuiFanBei = "+ isQiDuiFanBei
				 + ",  isZhuangJiaDi = "+ isZhuangJiaDi
				 + ",  isGangKaiFan = "+ isGangKaiFan
				 + ",  xuanPaoCount = "+ xuanPaoCount
				 + ",  isFangPao = "+ isFangPao + "] ";
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
