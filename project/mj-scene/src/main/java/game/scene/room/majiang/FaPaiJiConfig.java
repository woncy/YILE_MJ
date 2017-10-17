package game.scene.room.majiang;

public class FaPaiJiConfig {
	public static final int shunWight = 10;
	private static final int duiWight = 10;
	private static final int  keziWight = 15;
	private static final int gangWight = 20;
	private static final int huierWight = 25;
	
	public static int executeWight(FaPaiJiControlConfig control){
		int res = shunWight*control.getShunNum()*control.getShunNum()+duiWight*(3-control.getDuiNum()) + 
				keziWight*control.getKeziNum()+gangWight*control.getGangNum()+ huierWight*control.getHunNum();
		return res;
	}
	
}
