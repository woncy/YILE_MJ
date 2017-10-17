package mj.data.poker.pdk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mj.data.poker.Poker;
import mj.data.poker.PokerColorType;
import mj.net.message.game.pdk.PdkUserPlaceMsg;
/**
 * 
    * @ClassName: UserPlace
    * @Description:  用户 在牌局中的映射
    * @author 13323659953@163.com
    * @date 2017年7月7日
    *
 */
public class PdkUserPlace {
	PdkPokerAdapter adapter = new PdkPokerAdapter();
	private List<PdkPoker> shouPai;//手牌
	private List<PdkPai> outPai;// 打出的牌
	private int shouPaiLength = 0;
	private int locationIndex;
	private List<List<PdkPoker>> pais;

	public PdkUserPlace(int localtionIndex){
		this.locationIndex = localtionIndex;
		shouPai = new ArrayList<PdkPoker>();
		outPai = new ArrayList<PdkPai>();
	}
	
	public void addShouPai(PdkPoker poker){
		if(shouPai==null){
			shouPai = new ArrayList<PdkPoker>();
		}
		
		if(shouPai.size()==0){
			shouPai.add(poker);
			
		}else{
			shouPai.add(poker);
		}
		
		
		shouPaiLength++;
		if(pais==null){
			initPais();
		}
		pais.get(poker.getPokerNum().getNum()-1).add(poker);
	}
	
	/**
	 * 
	    * @Title: isBaoDan
	    * @Description: 检查用户是不是报单了 也就是剩最后一张牌了
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	 */
	public boolean isBaoDan(){
		return shouPaiLength == 1;
	}
	
	public List<PdkPoker> getShouPai() {
		return shouPai;
	}

	public void setShouPai(List<PdkPoker> shouPai) {
		this.shouPai = shouPai;
		shouPaiLength = shouPai.size();
		updatePais();
	}

	public List<PdkPai> getOutPai() {
		return outPai;
	}
	
	public int getPaisLength() {
		return shouPaiLength;
	}
	
	public List<List<PdkPoker>> getPais() {
		return pais;
	}
	
	private void initPais(){
		if(pais==null || pais.size()!=14){
			pais = new ArrayList<List<PdkPoker>>();
			for (int i = 0; i < 14; i++) {
				pais.add(new ArrayList<PdkPoker>());
				
			}
		}
	}
	private void updatePais(){
		initPais();
		for (int i = 0; i < shouPaiLength; i++) {
			PdkPoker p = shouPai.get(i);
			if(p.getPokerType()!=PokerColorType.WNAG){
				pais.get(p.getPokerNum().getNum()-1).add(p);
			}else{
				pais.get(13).add(p);
			}
		}
	}
	
	public void removeShouPai(Poker poker){
		PdkPoker pdkpoker = adapter.pokerToPdkPoker(poker);
		Iterator<PdkPoker> it = shouPai.iterator();
		while(it.hasNext()){
			if(it.next().compareTo(pdkpoker) == 0){
				it.remove();
				shouPaiLength--;
			}
		}
		List<PdkPoker> pokers = pais.get(poker.getPokerNum().getNum()-1);
		Iterator<PdkPoker> it2 = pokers.iterator();
		while(it2.hasNext()){
			if(it2.next().compareTo(pdkpoker) == 0){
				it2.remove();
			}
		}
		
	}
	public void addOutPai(PdkPai outPai){
		this.outPai.add(outPai);
	}


	public void removeShouPai(ArrayList<Poker> pokers) {
		for (int i = 0; i < pokers.size(); i++) {
			removeShouPai(pokers.get(i));
		}
	}
	

	public PdkUserPlaceMsg toMessage(int locationIndex) {
		PdkUserPlaceMsg msg = new PdkUserPlaceMsg();
		
		ArrayList<Integer> outPais = new ArrayList<Integer>();
		ArrayList<Integer> shouPais = new ArrayList<Integer>();
		if(shouPai!=null && locationIndex==this.locationIndex){
			for (PdkPoker poker:shouPai) {
				shouPais.add(Poker.getIndexByPoker(poker));
			}
		}
		if(outPai!=null){
			for(PdkPai pai:outPai){
				for(Poker poker: pai.getPokers()){
					outPais.add(Poker.getIndexByPoker(poker));
				}
			}
		}
		msg.setOutPais(outPais);
		msg.setShouPais(shouPais);
		return msg;
	}

	public void outBaoDanPai(Poker poker) {
		
	}
	
	
	
}
