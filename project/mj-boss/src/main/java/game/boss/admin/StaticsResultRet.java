//package game.boss.admin;
//
//import java.io.IOException;
//
//import com.isnowfox.core.io.Input;
//import com.isnowfox.core.io.Output;
//import com.isnowfox.core.io.ProtocolException;
//import com.isnowfox.core.net.message.AbstractMessage;
//
//public class StaticsResultRet extends AbstractMessage {
//	
//	public static final int TYPE			 = 1;
//	public static final int ID				 = 27;
//	
//	private int locationIndex0;
//	private int zimo0;
//	private int fangpao0;
//	private int jiepao0;
//	private int angang0;
//	private int minggang0;
//	
//	private int locationIndex1;
//	private int zimo1;
//	private int fangpao1;
//	private int jiepao1;
//	private int angang1;
//	private int minggang1;
//	
//	private int locationIndex2;
//	private int zimo2;
//	private int fangpao2;
//	private int jiepao2;
//	private int angang2;
//	private int minggang2;
//	
//	private int locationIndex3;
//	private int zimo3;
//	private int fangpao3;
//	private int jiepao3;
//	private int angang3;
//	private int minggang3;
//
//	@Override
//	public void decode(Input in) throws IOException, ProtocolException {
//		// TODO Auto-generated method stub
////		chatContent = in.readString();
//		
//	}
//
//	@Override
//	public void encode(Output out) throws IOException, ProtocolException {
//		// TODO Auto-generated method stub
//		out.writeInt(locationIndex0);
//		out.writeInt(zimo0);
//		out.writeInt(fangpao0);
//		out.writeInt(jiepao0);
//		out.writeInt(angang0);
//		out.writeInt(minggang0);
//		
//		out.writeInt(locationIndex1);
//		out.writeInt(zimo1);
//		out.writeInt(fangpao1);
//		out.writeInt(jiepao1);
//		out.writeInt(angang1);
//		out.writeInt(minggang1);
//		
//		out.writeInt(locationIndex2);
//		out.writeInt(zimo2);
//		out.writeInt(fangpao2);
//		out.writeInt(jiepao2);
//		out.writeInt(angang2);
//		out.writeInt(minggang2);
//		
//		out.writeInt(locationIndex3);
//		out.writeInt(zimo3);
//		out.writeInt(fangpao3);
//		out.writeInt(jiepao3);
//		out.writeInt(angang3);
//		out.writeInt(minggang3);
//	}
//
//	@Override
//	public int getMessageId() {
//		// TODO Auto-generated method stub
//		return ID;
//	}
//
//	@Override
//	public int getMessageType() {
//		// TODO Auto-generated method stub
//		return TYPE;
//	}
//	
//	@Override
//	public String toString() {
//		
//		return "StaticsResultRet [locationIndex0=" + locationIndex0 + 
//				",zimo0 "+zimo0+",fangpao0="+fangpao0+",jiepao0="+jiepao0+",angang0="+angang0+",minggang0="+minggang0+
//				
//				"locationIndex1=" + locationIndex1 + 
//				",zimo1 "+zimo1+",fangpao1="+fangpao1+",jiepao1="+jiepao1+",angang1="+angang1+",minggang1="+minggang1+
//				
//				"locationIndex2=" + locationIndex2 + 
//				",zimo2 "+zimo2+",fangpao2="+fangpao2+",jiepao2="+jiepao2+",angang2="+angang2+",minggang2="+minggang2+
//				
//				"locationIndex3=" + locationIndex3 + 
//				",zimo3 "+zimo3+",fangpao3="+fangpao3+",jiepao3="+jiepao3+",angang3="+angang3+",minggang3="+minggang3+
//				
//				"]";
//	}
//
//	public int getLocationIndex0() {
//		return locationIndex0;
//	}
//
//	public void setLocationIndex0(int locationIndex0) {
//		this.locationIndex0 = locationIndex0;
//	}
//
//	public int getZimo0() {
//		return zimo0;
//	}
//
//	public void setZimo0(int zimo0) {
//		this.zimo0 = zimo0;
//	}
//
//	public int getFangpao0() {
//		return fangpao0;
//	}
//
//	public void setFangpao0(int fangpao0) {
//		this.fangpao0 = fangpao0;
//	}
//
//	public int getJiepao0() {
//		return jiepao0;
//	}
//
//	public void setJiepao0(int jiepao0) {
//		this.jiepao0 = jiepao0;
//	}
//
//	public int getAngang0() {
//		return angang0;
//	}
//
//	public void setAngang0(int angang0) {
//		this.angang0 = angang0;
//	}
//
//	public int getMinggang0() {
//		return minggang0;
//	}
//
//	public void setMinggang0(int minggang0) {
//		this.minggang0 = minggang0;
//	}
//
//	public int getLocationIndex1() {
//		return locationIndex1;
//	}
//
//	public void setLocationIndex1(int locationIndex1) {
//		this.locationIndex1 = locationIndex1;
//	}
//
//	public int getZimo1() {
//		return zimo1;
//	}
//
//	public void setZimo1(int zimo1) {
//		this.zimo1 = zimo1;
//	}
//
//	public int getFangpao1() {
//		return fangpao1;
//	}
//
//	public void setFangpao1(int fangpao1) {
//		this.fangpao1 = fangpao1;
//	}
//
//	public int getJiepao1() {
//		return jiepao1;
//	}
//
//	public void setJiepao1(int jiepao1) {
//		this.jiepao1 = jiepao1;
//	}
//
//	public int getAngang1() {
//		return angang1;
//	}
//
//	public void setAngang1(int angang1) {
//		this.angang1 = angang1;
//	}
//
//	public int getMinggang1() {
//		return minggang1;
//	}
//
//	public void setMinggang1(int minggang1) {
//		this.minggang1 = minggang1;
//	}
//
//	public int getLocationIndex2() {
//		return locationIndex2;
//	}
//
//	public void setLocationIndex2(int locationIndex2) {
//		this.locationIndex2 = locationIndex2;
//	}
//
//	public int getZimo2() {
//		return zimo2;
//	}
//
//	public void setZimo2(int zimo2) {
//		this.zimo2 = zimo2;
//	}
//
//	public int getFangpao2() {
//		return fangpao2;
//	}
//
//	public void setFangpao2(int fangpao2) {
//		this.fangpao2 = fangpao2;
//	}
//
//	public int getJiepao2() {
//		return jiepao2;
//	}
//
//	public void setJiepao2(int jiepao2) {
//		this.jiepao2 = jiepao2;
//	}
//
//	public int getAngang2() {
//		return angang2;
//	}
//
//	public void setAngang2(int angang2) {
//		this.angang2 = angang2;
//	}
//
//	public int getMinggang2() {
//		return minggang2;
//	}
//
//	public void setMinggang2(int minggang2) {
//		this.minggang2 = minggang2;
//	}
//
//	public int getLocationIndex3() {
//		return locationIndex3;
//	}
//
//	public void setLocationIndex3(int locationIndex3) {
//		this.locationIndex3 = locationIndex3;
//	}
//
//	public int getZimo3() {
//		return zimo3;
//	}
//
//	public void setZimo3(int zimo3) {
//		this.zimo3 = zimo3;
//	}
//
//	public int getFangpao3() {
//		return fangpao3;
//	}
//
//	public void setFangpao3(int fangpao3) {
//		this.fangpao3 = fangpao3;
//	}
//
//	public int getJiepao3() {
//		return jiepao3;
//	}
//
//	public void setJiepao3(int jiepao3) {
//		this.jiepao3 = jiepao3;
//	}
//
//	public int getAngang3() {
//		return angang3;
//	}
//
//	public void setAngang3(int angang3) {
//		this.angang3 = angang3;
//	}
//
//	public int getMinggang3() {
//		return minggang3;
//	}
//
//	public void setMinggang3(int minggang3) {
//		this.minggang3 = minggang3;
//	}
//
//	
//
//	
//}
