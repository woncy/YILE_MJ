package game.scene.room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

import game.scene.model.UserState;
import mj.data.Config;
import mj.data.UserPaiInfo;
import mj.data.poker.douniu.DouniuPaiType;
import mj.net.message.game.GameUserInfo;
import mj.net.message.game.douniu.DNChapterPKResult;
import mj.net.message.game.douniu.DNDismissUserResult;
import mj.net.message.game.douniu.DNGameRoomInfo;
import mj.net.message.game.douniu.UserPkResult;

/**
 * @author zuoge85@gmail.com on 16/10/6.
 */
public class RoomInfo {
	
	public static final long Default_Del_Room_Time = 1000*60*3;
	
	/**
	 * 
	* @ClassName: STATE
	* @Description: 第一次创建此房间时，准备中，抢庄中，选倍中，比牌中
	* @author 13323659953@163.com   
	* @date 2017年11月8日 上午10:59:18
	*
	 */
	public static enum STATE{FIRSTSTART,READYING,QIANG_ZHUANG,XUAN_BEI,PKING};
    private short sceneId;
    /**
     * 牌局id
     */
    private int roomId;
    /**
     * 创建用户id
     */
    private int createUserId;
    /**
     * 用户数
     */
    private int userMax;

    private String roomCheckId;
    private boolean start;

    private final SceneUser[] users;
	private boolean isfirstStart = true;	//是不是第一次进入房间
	private int currentChapterNum = 0;
	private int maxChapterNum;
	private SceneUser controlUser;
	private Chapter chapter;
	private UserState[] userStates;
	private STATE state;
	private boolean isBeforeFirstStart = true;
	private String zhuang;
	
	private DNChapterPKResult[] pkResults;
    public Chapter getChapter() {
		return chapter;
	}

	public RoomInfo(Room room,int userNum,int roomId) {
		state = STATE.FIRSTSTART;
        users =  new SceneUser[userNum];
        chapter = new Chapter(userNum,room);
        this.roomId = roomId;
        userStates = new UserState[userNum];
        for (int i = 0; i < userStates.length; i++) {
			userStates[i] = new UserState();
		}
        Config config = room.getConfig();
        this.maxChapterNum = config.getInt("jushu");
        this.zhuang = config.getString("zhuang");
//        System.out.println("最大局数是："+maxChapterNum);
        this.currentChapterNum = 0;
        pkResults = new DNChapterPKResult[maxChapterNum];
        voteDelSelect = new boolean[userNum];
    }


	public DNGameRoomInfo toMessage(int index) {
		DNGameRoomInfo roomInfo = new DNGameRoomInfo();
		ArrayList<GameUserInfo> userInfos = new ArrayList<GameUserInfo>();
		for (int i = 0; i < users.length; i++) {
			SceneUser sceneUser = users[i];
			if(sceneUser!=null){
				userInfos.add(sceneUser.toMessage());
			}
			
		}
		if("KPQZ".equals(zhuang)&&(state!=STATE.PKING)){
			roomInfo.setChapterInfo(chapter.toMessage(index,true));
			
		}else{
			roomInfo.setChapterInfo(chapter.toMessage(index,false));
		}
		roomInfo.setUsers(userInfos);
		roomInfo.setCreateUserId(createUserId);
		roomInfo.setCurrentChapterNum(currentChapterNum);
		roomInfo.setMaxChapterNum(maxChapterNum);
		roomInfo.setRoomId(this.roomId);
		roomInfo.setRoomNo(this.roomCheckId);
		roomInfo.setBeforeFirstStart(isBeforeFirstStart);
		if(state == STATE.READYING){
			roomInfo.setState(1);
		}
		if(state == STATE.QIANG_ZHUANG){
			roomInfo.setState(2);
		}
		if(state == STATE.XUAN_BEI){
			roomInfo.setState(3);
		}
		if(state == STATE.PKING){
			roomInfo.setState(4);
		}
		return roomInfo;
    }
	

    public STATE getState() {
		return state;
	}

	public void setState(STATE state) {
		this.state = state;
	}

	public boolean isBeforeFirstStart() {
		return isBeforeFirstStart;
	}





	public void setBeforeFirstStart(boolean isBeforeFirstStart) {
		this.isBeforeFirstStart = isBeforeFirstStart;
	}





	public void updateUserInfo(SceneUser sceneUser,boolean isFirst) {
        SceneUser oldUser = users[sceneUser.getLocationIndex()];
        if (oldUser == null) {
            users[sceneUser.getLocationIndex()] = sceneUser;
        } else {
            oldUser.setAvatar(sceneUser.getAvatar());
            oldUser.setLocationIndex(sceneUser.getLocationIndex());
            oldUser.setSex(sceneUser.getSex());
            oldUser.setUserId(sceneUser.getUserId());
        }
        if(isFirst){
        	sceneUser.setScore(0);
        }
        chapter.updateUser(sceneUser);

    }

    public SceneUser getUserInfo(int locationIndex) {
        return users[locationIndex];
    }


    public boolean isIsfirstStart() {
		return isfirstStart;
	}

	public void changeIsfirstStart() {
		this.isfirstStart = false;
	}

    public boolean removeUserInfo(int userId) {
        for (int i = 0; i < users.length; i++) {
            SceneUser u = users[i];
            if (u != null && u.getUserId() == userId) {
                users[i] = null;
                return true;
            }
        }
        return false;
    }

    public SceneUser[] getUsers() {
        return users;
    }
    

    public short getSceneId() {
        return sceneId;
    }

    public void setSceneId(short sceneId) {
        this.sceneId = sceneId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public int getUserMax() {
        return userMax;
    }

    public void setUserMax(int userMax) {
        this.userMax = userMax;
    }

    public String getRoomCheckId() {
        return roomCheckId;
    }

    public void setRoomCheckId(String roomCheckId) {
        this.roomCheckId = roomCheckId;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return "RoomInfo{" +
                "start=" + start +
                ", sceneId=" + sceneId +
                ", roomId=" + roomId +
                ", createUserId=" + createUserId +
                ", userMax=" + userMax +
                ", roomCheckId='" + roomCheckId + '\'' +
                '}';
    }

    public SceneUser getUserInfoByUserId(int userId) {
        for (int i = 0; i < users.length; i++) {
            SceneUser u = users[i];
            if (u != null && u.getUserId() == userId) {
                return u;
            }
        }
        return null;
    }


    public void addCurrentChapter(){
    	this.currentChapterNum ++;
    }

	public void ready(SceneUser user) {
		userStates[user.getLocationIndex()].setReady(true);
	}
	
	
	public boolean isAllReady(){
		int userNum = 0;
		for (int i = 0; i < userStates.length; i++) {
			UserState userState = userStates[i];
			SceneUser sceneUser = users[i];
			if(sceneUser!=null){
				userNum++;
				if(!sceneUser.isOnline()){
					return false;
				}
				if(!userState.isReady()){
					return false;
				}
			}
			
		}
		if(userNum<=1){
			return false;
		}
		return true;
	}
	
	
	public void gameStartClear(){
		for (int i = 0; i < userStates.length; i++) {
			userStates[i].setReady(false);
			userStates[i].setQiangZhuang(false);
		}
	}



	/**
	 * 
	* @Title: qingZhuang 
	* @Description: TODO(用户的抢庄缓存) 
	* @param @param user
	* @param @return    设定文件
	* @return boolean    返回类型 	//是不是最后一个人抢庄
	* @throws
	 */
	public boolean qingZhuang(SceneUser user) {
		userStates[user.getLocationIndex()].setQiangZhuang(true);
		for (int i = 0; i < userStates.length; i++) {
			SceneUser sceneUser = users[i];
			UserState userState = userStates[i];
			if(sceneUser!=null){
				if(!userState.isQiangZhuang()){
					return false;
				}
			}
			
		}
		return true;
		
	}

	public int getCurrentChapterNum() {
		return currentChapterNum;
	}

	public void setCurrentChapterNum(int currentChapterNum) {
		this.currentChapterNum = currentChapterNum;
	}
	
	

	public UserState[] getUserStates() {
		return userStates;
	}

	public void setUserStates(UserState[] userStates) {
		this.userStates = userStates;
	}

	public DNChapterPKResult[] getPkResults() {
		return pkResults;
	}

	public void setPkResults(DNChapterPKResult[] pkResults) {
		this.pkResults = pkResults;
	}

	public void addPkResult(DNChapterPKResult result) {
		
		int zhuangIndex = result.getZhuangIndex();
		if(users[zhuangIndex]!=null){
			users[zhuangIndex].addScore(result.getSocre());
			UserState zhuangState = userStates[zhuangIndex];
			zhuangState.addScore(result.getSocre());
			switch (result.getPaiType()) {
				case 10:
					zhuangState.addNiuNiuCount();
					break;
				case 11:
					zhuangState.addSiHuaNiuCount();
					break;
				case 12:
					zhuangState.addWuHuaNiuCount();
					break;
				case 13:
					zhuangState.addZhaDanCount();
					break;
				case 14:
					zhuangState.addWuXiaoNiuCount();
					break;
				default:
					break;
			}
		}
		
		List<UserPkResult> userResults = result.getUserResults();
		if(userResults!=null){
			for (int i = 0; i < userResults.size(); i++) {
				UserPkResult userPkResult = userResults.get(i);
				if(users[userPkResult.getIndex()]!=null){
					users[userPkResult.getIndex()].addScore(userPkResult.getScore());
					UserState uState = userStates[userPkResult.getIndex()];
					uState.addScore(userPkResult.getScore());
					switch (result.getPaiType()) {
						case 10:
							uState.addNiuNiuCount();
							break;
						case 11:
							uState.addSiHuaNiuCount();
							break;
						case 12:
							uState.addWuHuaNiuCount();
							break;
						case 13:
							uState.addZhaDanCount();
							break;
						case 14:
							uState.addWuXiaoNiuCount();
							break;
						default:
							break;
					}
				}
			}
		}
		
		
		pkResults[currentChapterNum-1] = result;
	}

	public int getMaxChapterNum() {
		return maxChapterNum;
	}

	public void setMaxChapterNum(int maxChapterNum) {
		this.maxChapterNum = maxChapterNum;
	}

	public DNChapterPKResult[] getRoomPkResult() {
		return pkResults;
	}

	public void exitRoom(int userId) {
		for (int i = 0; i < users.length; i++) {
			SceneUser sceneUser = users[i];
			if(sceneUser!=null && sceneUser.getUserId() == userId){
				users[i] = null;
			}
		}
		chapter.exitUser(userId);
	}
	private boolean voteDelTime = false;
	private boolean[] voteDelSelect;
	

	public boolean isVoteTime(){
		return voteDelTime;
	}
	private boolean isAllCommitDelRoom = false;
	public void startVoteDelRoom() {
		voteDelTime = true;
		Arrays.fill(voteDelSelect, false);
		isAllCommitDelRoom = false;
		for (int i = 0; i < userStates.length; i++) {
			userStates[i].setCommitDelRoom(false);
		}
	}
	
	
	public boolean voteUserResult(SceneUser user,DNDismissUserResult ret){
		userStates[user.getLocationIndex()].setCommitDelRoom(true);
		voteDelSelect[user.getLocationIndex()]=ret.isResult();
		boolean isAll = true;
		int refuseCount = 0;
		for (int i = 0; i < users.length; i++) {
			SceneUser u = users[i];
			if(u!=null && u.isOnline()){
				if(!voteDelSelect[u.getLocationIndex()]){
					refuseCount++;
				}
				if(refuseCount>=1){
					return false;
				}
			}
			
		}
		return true;
	}
	
	public void endVoteDelRoom(){
		voteDelTime = false;
		Arrays.fill(voteDelSelect, false);
	}

	public boolean isAllCommitVoteDel() {
		for (int i = 0; i < users.length; i++) {
			if(users[i]!=null){
				boolean commitDelRoom = userStates[users[i].getLocationIndex()].isCommitDelRoom();
				if(!commitDelRoom){
					return false;
				}
			}
		}
		return true;
		
	}

	public boolean isAllReady2() {
		int userNum = 0;
		for (int i = 1; i < userStates.length; i++) {
			UserState userState = userStates[i];
			SceneUser sceneUser = users[i];
			if(sceneUser!=null){
				userNum++;
				if(!sceneUser.isOnline()){
					return false;
				}
				if(!userState.isReady()){
					return false;
				}
			}
			
		}
		if(userNum<1){
			return false;
		}
		return true;
	}
	
	
	
	
	
	


}
