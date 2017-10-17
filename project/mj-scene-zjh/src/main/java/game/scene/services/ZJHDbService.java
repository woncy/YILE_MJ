package game.scene.services;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import ZJH.data.ZJHConfig;
import game.boss.dao.dao.RoomDao;
import game.boss.dao.dao.UserDao;
import game.boss.dao.entity.RoomDO;
import game.boss.dao.entity.UserDO;
import game.boss.poker.dao.TbPkChapterDao;
import game.boss.poker.dao.TbPkRoomDao;
import game.boss.poker.dao.TbPkRoomUserDao;
import game.boss.poker.dao.TbPkUserChapterDao;
import game.boss.poker.dao.entiy.TbPkChapterDO;
import game.boss.poker.dao.entiy.TbPkRoomDO;
import game.boss.poker.dao.entiy.TbPkRoomUserDO;
import game.boss.poker.dao.entiy.TbPkUserChapterDO;
import game.boss.poker.dao.entiy.UserDO2;
import mj.data.Config;
import mj.data.ZJHUserPlace;
import mj.data.poker.zjh.ZJHPai;

public class ZJHDbService {

	@Autowired
	private ZJHAsyncDbService zjhAsyncDbService;
	
	@Autowired
    private TbPkRoomDao roomDao;
	@Autowired
    private TbPkChapterDao chapterDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private TbPkRoomUserDao roomUserDao;
    @Autowired
    private TbPkUserChapterDao userChapterDao;
    private TbPkRoomUserDO roomUser = new TbPkRoomUserDO();
    private TbPkUserChapterDO userChapter = new TbPkUserChapterDO();
    private TbPkChapterDO chapter = new TbPkChapterDO();
    private TbPkRoomDO room = new TbPkRoomDO();
    private UserDO2 user = new UserDO2();
    /*
     * 减去用户的gold.
     */
    public void delGold(int roomId, int userId) {
    	
        if(roomId > 0 ){
        	 TbPkRoomDO tbPkRoomDO = roomDao.get(roomId);
        	 int max = tbPkRoomDO.getPokerConfig().getInt(ZJHConfig.CHAPTERMAX);
            int gold = (int) Math.ceil(max / 10);
            if (userId > 0 ) {
                UserDO userDO = userDao.get(userId);
                userDO.setGold(userDO.getGold() - gold);
            }
            zjhAsyncDbService.excuete(() -> {
                userDao.incrementUpdatePartial(
                        UserDO.Table.GOLD, -gold,
                        new UserDO.Key(userId)
                );
            });
        }
    }
    /**
     * 房间结束后保存数据
     * @param roomId 
     *@param zjhUserPlace
     *@return
     * 2017年7月12日
     */
	public void save(ArrayList<ZJHUserPlace> zjhUserPlaces, int roomId) {
		room.setId(roomId);
		room.setEndDate(new Date());
		room.setEnd(true);
		for (int i = 0; i < zjhUserPlaces.size(); i++) {
			ZJHUserPlace zjhUserPlace = zjhUserPlaces.get(i);
			roomUser.setEndScore(zjhUserPlace.getScore());//玩家最后分数
			roomUser.setWinCount(zjhUserPlace.getWinCount());
			ArrayList<ZJHPai> allPai = zjhUserPlace.getAllPai();
			user.setId(zjhUserPlace.getUserId());
			for (int j = 0; j < allPai.size(); j++) {
				chapter.setNum(i);
				chapter.setRoom(room);
				
				userChapter.setPais(allPai.get(j).toString());
				userChapter.setScore(allPai.get(j).getScore());
				userChapter.setUser(user);
				 zjhAsyncDbService.excuete(() -> {
					 roomDao.update(room);
					 chapterDao.save(chapter);
					 userChapter.setChapter(chapter);
					 userChapterDao.save(userChapter);
		             roomUserDao.update(roomUser);
		         });
			}
			
		}
	}
}
