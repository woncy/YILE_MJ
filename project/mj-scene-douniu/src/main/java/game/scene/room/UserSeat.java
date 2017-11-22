package game.scene.room;

import org.slf4j.Logger;

import mj.data.poker.Poker;
import mj.data.poker.douniu.DouniuPai;
import mj.data.poker.douniu.DouniuPoker;
import mj.net.message.game.douniu.SeatUserInfo;

public class UserSeat {
	public static final Logger log = org.slf4j.LoggerFactory.getLogger(UserSeat.class);
	private DouniuPoker[] pokers = new DouniuPoker[5];
	private DouniuPai pai;
	private int index;
	private int userid;
	private int zhu;       //注数
	private boolean isKaiPai;// 是否开牌了
	private boolean isPkDone;
	public UserSeat() {
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getUserid() {
		return userid;
	}
	
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	
	
	
	public DouniuPai getPai() {
		return pai;
	}
	public void setPai(DouniuPai pai) {
		this.pai = pai;
	}
	public boolean isPkDone() {
		return isPkDone;
	}
	public void setPkDone(boolean isPkDone) {
		this.isPkDone = isPkDone;
	}
	public DouniuPoker[] getPokers() {
		return pokers;
	}
	public void setPokers(DouniuPoker[] pokers) {
		this.pokers = pokers;
		try {
			this.pai = new DouniuPai(pokers);
		} catch (Throwable e) {
			log.error(e.getMessage());
		}
	}
	public int getZhu() {
		return zhu;
	}
	public void setZhu(int zhu) {
		this.zhu = zhu;
	}
	public boolean isKaiPai() {
		return isKaiPai;
	}
	public void setKaiPai(boolean isKaiPai) {
		this.isKaiPai = isKaiPai;
	}
	public SeatUserInfo toMessage(int index,boolean isKPQZ) {
		SeatUserInfo info = new SeatUserInfo();
		if(index == this.index){
			if(pokers[0]==null){
				info.setPais(new int[]{-2,-2,-2,-2,-2});
			}else{
				if(isKPQZ){
					int[] poker = DouniuPoker.toIntArrayFromDouniuArray(pokers);
					poker[3] = -1;
					poker[4] = -1;
					info.setPais(poker);
					info.setPaiType(-1);
				}else{
					info.setPais(DouniuPoker.toIntArrayFromDouniuArray(pokers));
					info.setPaiType(pai.getType().getIndex());
				}
			}
		}else{
			if(isKaiPai){
				info.setPais(DouniuPoker.toIntArrayFromDouniuArray(pokers));
				info.setPaiType(pai.getType().getIndex());
			}else{
				if(pokers[0] != null){
					info.setPais(new int[]{-1,-1,-1,-1,-1});
					info.setPaiType(-1);
				}
				else{
					info.setPais(new int[]{-2,-2,-2,-2,-2});
					info.setPaiType(-2);
				}
			}
		}
		info.setIndex(this.index);
		info.setUserId(this.userid);
		info.setKaiPai(isKaiPai);
		info.setZhu(zhu);
		return info;
	}
	
	
	
	
}
