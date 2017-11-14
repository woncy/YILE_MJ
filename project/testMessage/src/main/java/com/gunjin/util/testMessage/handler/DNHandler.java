package com.gunjin.util.testMessage.handler;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.channels.NotYetConnectedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.google.common.cache.Cache;
import com.gunjin.util.testMessage.core.Client;
import com.gunjin.util.testMessage.core.MessageHandler;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.Message;

import mj.data.poker.Poker;
import mj.net.message.game.GameUserInfo;
import mj.net.message.game.douniu.DNChapterInfo;
import mj.net.message.game.douniu.DNChapterPKResult;
import mj.net.message.game.douniu.DNGameReady;
import mj.net.message.game.douniu.DNGameRoomInfo;
import mj.net.message.game.douniu.DNGameStart;
import mj.net.message.game.douniu.DNGameZhuang;
import mj.net.message.game.douniu.DNKaiPai;
import mj.net.message.game.douniu.DNQiangZhuang;
import mj.net.message.game.douniu.DNShowXiaZhu;
import mj.net.message.game.douniu.DNXiaZhu;
import mj.net.message.game.douniu.SeatUserInfo;
import mj.net.message.login.CreateRoom;
import mj.net.message.login.CreateRoomRet;
import mj.net.message.login.Login;
import mj.net.message.login.LoginRet;
import mj.net.message.login.OptionEntry;
import mj.net.message.login.douniu.JoinDouniuRoom;
import mj.net.message.login.douniu.JoinDouniuRoomReadyDone;
import mj.net.message.login.douniu.JoinRoomResult;

public class DNHandler extends MessageHandler{
	int id;
	Client client;
	String name;
	String roomNo;
	DNGameRoomInfo roomInfo;
	private int selfIndex;
	Scanner scaner = new Scanner(System.in);
	Thread gameStartThread = null;
	private boolean gameStartThreadControl = true;
	@Override
	public void onMessage(Message message) {
		
		if(message instanceof LoginRet){
			this.id = ((LoginRet) message).getId();
			this.name = ((LoginRet) message).getName();
			System.out.println("你上线了,你的id:"+this.id);
			homeOperation();
			
		}else if(message instanceof CreateRoomRet){
			if(((CreateRoomRet) message).getResult()){
				joinRoom(((CreateRoomRet) message).getRoomCheckId());
				
			}
		}else if(message instanceof JoinRoomResult){
			if(((JoinRoomResult) message).getResult()){
				joinGame();
			}
		}else if(message instanceof DNGameRoomInfo){
			DNGameRoomInfo roomInfo = ((DNGameRoomInfo)message);
			System.out.println("***********************************************");
			System.out.println("**你已经加入了房间["+roomInfo.getRoomNo()+"]");
			System.out.println("***********************************************");
			this.roomNo = roomInfo.getRoomNo();
			this.roomInfo = roomInfo;
			for (int i = 0; i < roomInfo.getUsers().size(); i++) {
				GameUserInfo gameUserInfo = roomInfo.getUsers().get(i);
				if(gameUserInfo!=null && this.id == gameUserInfo.getUserId()){
					selfIndex = gameUserInfo.getLocationIndex();
				}
			}
			ShowRoomInfo(roomInfo);
			if(selfIndex!=0){
				showReadyOpt();
			}
			
		}else if(message instanceof GameUserInfo){
			System.out.println("***********************************************");
			System.out.println("*"+((GameUserInfo)message).getUserName()+"加入了房间["+roomInfo.getRoomNo()+"]");
			System.out.println("***********************************************");
			this.roomInfo.getUsers().add((GameUserInfo)message);
			SeatUserInfo seat = new SeatUserInfo();
			seat.setPaiType(-1);
			seat.setPais(new int[]{-2,-2,-2,-2,-2});
			roomInfo.getChapterInfo().getSeats().add(seat);
			if(selfIndex == 0){
				gameStartOpt();
			}
			ShowRoomInfo(roomInfo);
		}else if(message instanceof DNChapterInfo){
			DNChapterInfo msg = (DNChapterInfo) message;
			roomInfo.setChapterInfo(msg);
			ShowRoomInfo(roomInfo);
			showKaiPaiOpt();
			
		}else if(message instanceof DNGameReady){
			showUserReadyMsg((DNGameReady)message);
		}else if(message instanceof DNGameStart){
			if(((DNGameStart) message).isShowQiangZhuang()){
				showqiangzhuangOpt();
			}
		}else if(message instanceof DNShowXiaZhu){
			showXiazhuOpt();
		}else if(message instanceof DNGameZhuang){
			showZhuang((DNGameZhuang)message);
		}else if(message instanceof SeatUserInfo){
			int index = ((SeatUserInfo) message).getIndex();
			boolean tihuan = false;
			for (int i = 0; i < roomInfo.getChapterInfo().getSeats().size(); i++) {
				 SeatUserInfo seatUserInfo = roomInfo.getChapterInfo().getSeats().get(i);
				if(seatUserInfo.getIndex()==index){
					roomInfo.getChapterInfo().getSeats().set(i,(SeatUserInfo) message);
					tihuan = true;
				}
				
			}
			if(!tihuan){
				roomInfo.getChapterInfo().getSeats().add((SeatUserInfo)message);
			}
			showKaiPai((SeatUserInfo)message);
			ShowRoomInfo(this.roomInfo);
		}else if(message instanceof DNChapterPKResult){
			showReadyOpt();
		}
	}
	
	private void showKaiPai(SeatUserInfo message) {
		System.out.println("***********************************************");
		System.out.println("用户["+roomInfo.getUsers().get(message.getIndex()).getUserName()+"]开牌了");
		System.out.println("***********************************************");
		
	}

	private boolean showKaiPai = false;
	private void showKaiPaiOpt() {
		if(!showKaiPai){
			showKaiPai = true;
			System.out.println("你可以有以下操作:");
			System.out.println("***********************************************");
			System.out.println("***                  1.开牌                                                        ***");
			System.out.println("***********************************************");
			
			try{
				int next = -1;
				while(next!=1){
					System.out.print("请选择:");
					next = Integer.parseInt(scaner.next());
				}
				showKaiPai = false;
				kaiPai();
		
			}catch (Exception e) {
				
			}
		}
		
	}

	private void kaiPai() {
		try {
			client.sendMessage(new DNKaiPai());
		} catch (NotYetConnectedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}

	private void showZhuang(DNGameZhuang message) {
		System.out.println("***********************************************");
		System.out.println("庄家是:["+roomInfo.getUsers().get(message.getIndex()).getUserName()+"]");
		System.out.println("***********************************************");
		
	}

	private boolean showXiazhu = false;
	private void showXiazhuOpt() {
		if(!showXiazhu){
			showXiazhu = true;
			System.out.println("你可以有以下操作:");
			System.out.println("***********************************************");
			System.out.println("***         1.一倍     2.二倍 3.三倍                                               ***");
			System.out.println("***********************************************");
			
			try{
				int next = -1;
				while(next>3 || next<=0){
					System.out.print("请选择:");
					next = Integer.parseInt(scaner.next());
				}
				showXiazhu = false;
				xiazhu(next);
			
		
			}catch (Exception e) {
				
			}
		}
		
	}
	
	
	private void xiazhu(int next) {
		try {
			client.sendMessage(new DNXiaZhu(selfIndex, next));
		} catch (NotYetConnectedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}

	private boolean showqiangzhuang = false;
	private void showqiangzhuangOpt() {
		if(!showqiangzhuang){
			showqiangzhuang = true;
			System.out.println("你可以有以下操作:");
			System.out.println("***********************************************");
			System.out.println("***         0.不抢     1.抢                                                                   ***");
			System.out.println("***********************************************");
			
			try{
				int next = -1;
				while(next!=1 && next!=0){
					System.out.print("请选择:");
					next = Integer.parseInt(scaner.next());
					
				}if(next==1){
					showqiangzhuang = false;
					qiangzhuang(true);
				}if(next==0){
					showqiangzhuang = false;
					qiangzhuang(false);
				}
		
			}catch (Exception e) {
				
			}
		}
		
	}
	
	private void qiangzhuang(boolean isqiang) {
		try {
			client.sendMessage(new DNQiangZhuang(selfIndex, isqiang));
		} catch (NotYetConnectedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}


	private boolean readyopt = false;
	private void showReadyOpt() {
		if(!readyopt){
			readyopt = true;
			System.out.println("你可以有以下操作:");
			System.out.println("***********************************************");
			System.out.println("***              1.准备                                                                   ***");
			System.out.println("***********************************************");
			
			try{
				int next = -1;
				while(next!=1){
					System.out.print("请选择:");
					next = Integer.parseInt(scaner.next());
					
				}if(next==1){
					readyopt = false;
					gameReady();
				}
		
			}catch (Exception e) {
				
			}
		}
		
	}
	private void gameReady() {
		try {
			client.sendMessage(new DNGameReady(selfIndex));
		} catch (NotYetConnectedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	private void showUserReadyMsg(DNGameReady message) {
		System.out.println("***********************************************");
		System.out.println("用户【"+roomInfo.getUsers().get(message.getIndex()).getUserName()+"】已经准备了");
		System.out.println("***********************************************");
		
	}
	private boolean startopt = false;
	private void gameStartOpt() {
		if(!startopt){
			startopt = true;
			System.out.println("你可以有以下操作:");
			System.out.println("***********************************************");
			System.out.println("***           1.开始游戏                                                                   ***");
			System.out.println("***********************************************");
			
			try{
				
				int next = -1;
				
				while(next!=1){
					System.out.print("请选择:");
					next = Integer.parseInt(scaner.next());
					
				}if(next==1){
					startopt = false;
					gameStart();
				}
		
			}catch (Exception e) {
				
			}
		}
		
		
	}

	private void gameStart() {
		try {
			client.sendMessage(new DNGameStart());
		} catch (NotYetConnectedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}

	private  void homeOperation(){
		System.out.println("你可以有以下操作:");
		System.out.println("***********************************************");
		System.out.println("***           1.创建房间             2.加入房间                                 ***");
		System.out.println("***********************************************");
		int next = -1;
		try{
			while((next!=1 && next!=2)){
				System.out.print("请选择:");
				next = Integer.parseInt(scaner.next());
			}
		}catch (Exception e) {
			
			while((next!=1 && next!=2)){
				System.out.print("输入错误,请选择:");
				next = Integer.parseInt(scaner.next());
			}
		}
		
		if(next==1)
			createRoom();
		else if(next == 2){
			String roomNo = null;
			try{
				System.out.print("请输入房间号:");
				while(roomNo==null || roomNo.length()!=6 || Integer.parseInt(roomNo)<100000){
					roomNo = scaner.next();
				}
			}catch (Exception e) {
				System.out.print("输入错误，请输入房间号:");
				while(roomNo==null || roomNo.length()!=6 || Integer.parseInt(roomNo)<100000){
					roomNo = scaner.next();
				}
			}
			joinRoom(roomNo);
		}
	}
	
	
	private void joinGame() {
		try {
			client.sendMessage(new JoinDouniuRoomReadyDone());
		} catch (NotYetConnectedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	private void joinRoom(String roomNo) {
		JoinDouniuRoom msg = new JoinDouniuRoom(roomNo);
		try {
			client .sendMessage(msg);
		} catch (NotYetConnectedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public void login() {
		try {
			client.sendMessage(new Login("ANONYMOUS", (String)null, (String)null, "34.7545", "0"));
		} catch (NotYetConnectedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		}
	}
	
	private void createRoom(){
		try {
			client.sendMessage(new CreateRoom("DN", new ArrayList<OptionEntry>()));
		} catch (NotYetConnectedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	private void ShowRoomInfo(DNGameRoomInfo roomInfo){
		
		String roomNo = roomInfo.getRoomNo();
		int currentNum = roomInfo.getCurrentChapterNum();
		int maxNum = roomInfo.getMaxChapterNum();
		
		HashMap<String, String>[] infos = new HashMap[5];
		DNChapterInfo chapterInfo = roomInfo.getChapterInfo();
		for (int i = 0; i < roomInfo.getUsers().size(); i++) {
			GameUserInfo userInfo = null;
			userInfo = roomInfo.getUsers().get(i);
			if(userInfo==null){
				continue;
			}
			SeatUserInfo seat = null;
			if(userInfo!=null)
				seat= chapterInfo.getSeats().get(userInfo.getLocationIndex());
			HashMap<String, String> map  = null;
			int resivi = -1;
			resivi = toResivi(userInfo.getLocationIndex());
			if(infos[resivi]==null){
				infos[resivi] = new HashMap<String, String>();
			}
	
			map = infos[resivi];
			
		
			int zhu = seat.getZhu();
			map.put("beishu",toBeiShuString(zhu, true, true));
			int paiType = seat.getPaiType();
			map.put("niu",toNiuString(paiType, true, (seat.isKaiPai()||resivi==0)));
			for (int j = 0; j < 5; j++) {
				int p = -2;
				p = seat.getPais()[j];
				map.put("p"+(j+1),toPai(p, true));
			}
			String name = "";
			
			name = userInfo.getUserName();

			map.put("name",toName(name, true));
		}
		
		for (int i = 0; i < infos.length; i++) {
			if(infos[i]==null)
				infos[i] = new HashMap<String, String>();
			else{
				continue;
			}
			HashMap<String,String> map = infos[i];
			map.put("beishu",toBeiShuString(-1, false, true));
			map.put("niu",toNiuString(-1, false, false));
			for (int j = 0; j < 5; j++) {
				map.put("p"+(j+1),toPai(-2, false));
			}
			map.put("name",toName(name, false));
		}
		
		String seats = 
					"      **************************                                                                               \n"+
					"      *** 房间号:"+roomNo+"   "+currentNum+"/"+maxNum+"局   ***                                                                               \n"+
					"      **************************                                                                               \n"+
					"               ************************                       ************************                       \n"+
					"               ***  "+infos[3].get("beishu")+"      "+infos[3].get("niu")+"           ***                      ***  "+infos[2].get("beishu")+"      "+infos[2].get("niu")+"           ***                       \n"+
					"               ***"+infos[3].get("p1")+""+infos[3].get("p2")+""+infos[3].get("p3")+" ***                      ***"+infos[2].get("p1")+""+infos[2].get("p2")+""+infos[2].get("p3")+" ***                       \n"+
					"               ***   "+infos[3].get("p4")+" "+infos[3].get("p5")+"   ***                      ***   "+infos[2].get("p4")+" "+infos[2].get("p5")+"   ***                       \n"+
					"               ***      "+infos[3].get("name")+"          ***                      ***     "+infos[2].get("name")+"          ***                       \n"+
					"               ************************                       ************************                       \n"+
					"                                                                                                             \n"+
					"                                                                                                             \n"+
					"     ************************                                          ************************              \n"+
					"     ***  "+infos[4].get("beishu")+"      "+infos[4].get("niu")+"           ***                                         ***  "+infos[1].get("beishu")+"      "+infos[1].get("niu")+"           ***              \n"+
					"     ***"+infos[4].get("p1")+""+infos[4].get("p2")+""+infos[4].get("p3")+" ***                                         ***"+infos[1].get("p1")+""+infos[1].get("p2")+""+infos[1].get("p3")+" ***              \n"+
					"     ***   "+infos[4].get("p4")+" "+infos[4].get("p5")+"   ***                                         ***   "+infos[1].get("p4")+" "+infos[1].get("p5")+"   ***              \n"+
					"     ***      "+infos[4].get("name")+"          ***                                         ***      "+infos[1].get("name")+"          ***              \n"+
					"     ************************                                          ************************              \n"+
					"                                                                                                             \n"+
					"                                     ************************                                                \n"+
					"                                     ***  "+infos[0].get("beishu")+"      "+infos[0].get("niu")+"           ***                                                \n"+
					"                                     ***"+infos[0].get("p1")+""+infos[0].get("p2")+""+infos[0].get("p3")+" ***                                                \n"+
					"                                     ***   "+infos[0].get("p4")+" "+infos[0].get("p5")+"   ***                                                \n"+
					"                                     ***      "+infos[0].get("name")+"          ***                                                \n"+
					"                                     ************************                                                \n";
		System.out.print(seats);	
	}
	
	private int toResivi(int otherIndex){
		if(otherIndex>=selfIndex){
			return otherIndex-selfIndex;
		}else{
			return otherIndex+5-selfIndex;
		}
	}
	
	private String toPai(int pai,boolean hasUser){
		if(hasUser){
			if(pai>-1){
				return "["+Poker.getPokerFromIndex(pai).getName()+"]";
			}else if(pai==-1){
				return "[背   面]";
			}else if(pai == -2);{
				return "      ";
			}
		}else{
//			return "[　　　]";
			return "      ";
		}
	}
	
	private String toName(String name,boolean hasUser){
		if(hasUser){
			String tname = "";
			
			String space = "";
			for (int i = 0; i < 6-name.length(); i++) {
				space+=" ";
				
			}
			if(name.length()>=6)
				tname = name.substring(0,6);
			else{
				tname = name+space;
			}
		
			return "【"+tname+"】";
		}else{
//			return "【AAAAAA】";
			return "   ";  
		}
	}
	private String toBeiShuString(int zhu,boolean hasUser,boolean sync){
		if(!hasUser){
			return "";
		}
		if(sync){
			String ret = "【"+zhu+"倍】";
			return ret;
		}else{
			return "";
		}
		
	}
	private static String niuString[] = new String[]{"没牛","牛一","牛二","牛三","牛四","牛五","牛六","牛七","牛八","牛九","牛牛","四花","五花","四炸","五小"};
	private String toNiuString(int paiType,boolean hasUser,boolean sync){
		if(!hasUser){
			return "";
		}
		if(sync && paiType>-1){
			String ret = "【"+niuString[paiType]+"】";
			return ret;
		}else{
			return "【　　 】";
		}
		
	}
	
	
	
}


