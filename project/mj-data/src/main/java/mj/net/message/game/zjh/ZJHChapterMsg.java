package mj.net.message.game.zjh;

import java.io.IOException;
import java.util.List;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
/**
 * 一局牌的信息
 * @author 13323659953@163.com
 *
 */
public class ZJHChapterMsg extends AbstractMessage{
	public static final int TYPE			 = 3;
	public static final int ID				 = 35;
	/**
	 * 当前操作用户
	 */
	private int currentIndex;
	/**
	 * 当前局数, 0开始
	 */
	private int chapterNums;
	/**
	 * 总局数, 0开始
	 */
	private int chapterNumsMax;
	/**
	 * 是否出局
	 */
	private boolean[] isOuts;
	/**
	 * 是否看牌
	 */
	private boolean[] isKan;
	/**
	 * 弃牌
	 */
	private boolean[] isQi;
	//牌局是否开始
	private boolean isStart;
	
	//庄家位置
	private int zhuangIndex;
	
	public ZJHChapterMsg() {
			
		}



public ZJHChapterMsg(int currentIndex, int chapterNums, int chapterNumsMax, boolean[] isOuts, boolean[] isKan,
			boolean[] isQi, boolean isStart, int zhuangIndex) {
		super();
		this.currentIndex = currentIndex;
		this.chapterNums = chapterNums;
		this.chapterNumsMax = chapterNumsMax;
		this.isOuts = isOuts;
		this.isKan = isKan;
		this.isQi = isQi;
		this.isStart = isStart;
		this.zhuangIndex = zhuangIndex;
	}







@Override
public void decode(Input in) throws IOException, ProtocolException {
	this.currentIndex = in.readInt();
	this.chapterNums = in.readInt();
	this.chapterNumsMax = in.readInt();
	this.isOuts = in.readBooleanArray();
	this.isKan = in.readBooleanArray();
	this.isQi = in.readBooleanArray();
	this.isStart = in.readBoolean();
	this.zhuangIndex = in.readInt();
}

@Override
public void encode(Output out) throws IOException, ProtocolException {
	out.writeInt(getChapterNums());
	out.writeInt(getCurrentIndex());
	out.writeInt(getChapterNumsMax());
	out.writeBooleanArray(getIsOuts());
	out.writeBooleanArray(getIsKan());
	out.writeBooleanArray(getIsQi());
	out.writeBoolean(isStart());
	out.writeInt(getZhuangIndex());
}

public int getCurrentIndex() {
	return currentIndex;
}


public void setCurrentIndex(int currentIndex) {
	this.currentIndex = currentIndex;
}


public int getChapterNums() {
	return chapterNums;
}


public void setChapterNums(int chapterNums) {
	this.chapterNums = chapterNums;
}


public int getChapterNumsMax() {
	return chapterNumsMax;
}


public void setChapterNumsMax(int chapterNumsMax) {
	this.chapterNumsMax = chapterNumsMax;
}


public boolean[] getIsOuts() {
	return isOuts;
}



public void setIsOuts(boolean[] isOuts) {
	this.isOuts = isOuts;
}



public boolean[] getIsKan() {
	return isKan;
}



public void setIsKan(boolean[] isKan) {
	this.isKan = isKan;
}



public boolean[] getIsQi() {
	return isQi;
}



public void setIsQi(boolean[] isQi) {
	this.isQi = isQi;
}



public boolean isStart() {
	return isStart;
}


public void setStart(boolean isStart) {
	this.isStart = isStart;
}


public int getZhuangIndex() {
	return zhuangIndex;
}


public void setZhuangIndex(int zhuangIndex) {
	this.zhuangIndex = zhuangIndex;
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
