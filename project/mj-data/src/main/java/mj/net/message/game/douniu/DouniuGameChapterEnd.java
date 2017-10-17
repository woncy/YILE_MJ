package mj.net.message.game.douniu;

import java.io.IOException;
import java.util.ArrayList;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 斗牛牌局结束
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class DouniuGameChapterEnd extends AbstractMessage{
	public static final int TYPE			 = 5;
	public static final int ID				 = 22;
	
	private int zhuangIndex;  //牛牛位置，赢家的位置
	private ArrayList<CompareResult>  compareResultList;   //牌的信息

	public int getZhuangIndex() {
		return zhuangIndex;
	}

	public void setZhuangIndex(int zhuangIndex) {
		this.zhuangIndex = zhuangIndex;
	}

	public ArrayList<CompareResult> getCompareResultList() {
		return compareResultList;
	}

	public void setCompareResultList(ArrayList<CompareResult> compareResultList) {
		this.compareResultList = compareResultList;
	}

	public DouniuGameChapterEnd(){
		
	}
	
	public DouniuGameChapterEnd(int zhuangIndex, ArrayList<CompareResult>  compareResultList){
		this.zhuangIndex = zhuangIndex;
		this.compareResultList = compareResultList;
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		zhuangIndex = in.readInt();
		int count = in.readInt();
		if(count == -1){
			compareResultList = null ; 
		}else{
			compareResultList = new ArrayList<CompareResult>();
			for(int i =0 ; i < count ; i ++){
				CompareResult  result = new CompareResult();
				result.decode(in);
				compareResultList.add(result);
			}
		}
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeInt(zhuangIndex);
		if(compareResultList == null){
			out.writeInt(-1);
		}else{
			java.util.ArrayList<CompareResult> compareResultList = getCompareResultList(); 
			out.writeInt(compareResultList.size());
			 for(CompareResult  res : compareResultList){
				 res.encode(out);
			 }
		}
	}
	
	@Override
	public String toString() {
		return "DouniuGameChapterEnd [zhuangIndex=" + zhuangIndex
				+ ", compareResultList=" + compareResultList + "]";
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
