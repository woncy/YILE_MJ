package game.scene.room;

import java.util.ArrayList;
import java.util.List;

import mj.data.poker.Poker;
import mj.data.poker.PokerColorType;
import mj.data.poker.PokerUtil;
import mj.data.poker.douniu.DouniuPoker;

public class DNFaPaiUtil {

	public static void faPai(UserSeat[] seats) throws Exception{
		List<Poker> pokers = Poker.getPokers();
		List<DouniuPoker> dnPokers = new ArrayList<DouniuPoker>();
		for (int i = 0; i < pokers.size(); i++) {
			if(pokers.get(i).getPokerType()!=PokerColorType.WNAG){
				dnPokers.add(new DouniuPoker(pokers.get(i)));
			}
		}
		PokerUtil.xiPai(dnPokers);
		PokerUtil.xiPai(dnPokers);
		for (int i = 0; i < seats.length; i++) {
			UserSeat userSeat = seats[i];
			if(userSeat==null){
				continue;
			};
		
			DouniuPoker[] ps = new DouniuPoker[5];
			for (int j = 0; j < 5; j++) {
				ps[j] = dnPokers.remove(dnPokers.size()-1);
			}
			userSeat.setPokers(ps);
			
			
		}
	}
}
