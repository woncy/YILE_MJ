package game.scene.services;

import java.util.Arrays;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import game.boss.dao.dao.RoomDao;
import game.boss.dao.dao.RoomDetailDao;
import game.boss.dao.dao.RoomResultDao;
import game.boss.dao.dao.TbWinProDao;
import game.boss.dao.dao.UserDao;
import game.boss.dao.entity.RoomDO;
import game.boss.dao.entity.RoomDetailDO;
import game.boss.dao.entity.RoomResultDO;
import game.boss.dao.entity.TbWinProDO;
import game.boss.dao.entity.UserDO;
import game.scene.room.SceneUser;
import mj.data.ChapterEndResult;
import mj.data.Config;
import mj.data.Pai;
import mj.data.UserPaiInfo;

/**
 * 数据库操作相关
 *
 * @author zuoge85@gmail.com on 2016/12/28.
 */
@Service
public class DbService {
    @Autowired
    private AsyncDbService asyncDbService;

    @Autowired
    private RoomResultDao roomResultDao;
    
    @Autowired
    private RoomDao roomDao;
    
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoomDetailDao roomDetailDao;

    @Autowired
    private TbWinProDao tbWinProDao;

    public RoomResultDO save(ChapterEndResult endResult, int roomId, String checkId, short sceneId) {
        RoomResultDO result = new RoomResultDO();
        result.setLastPai(endResult.isLastPai());
        result.setRoomId(roomId);
        result.setCreateDate(new Date());
        result.setFangPaoIndex(endResult.getFangPaoIndex());
        result.setGangShangHua(endResult.isGangShangHua());
        if (endResult.getHuiEr() != null) {
            result.setHuiEr(Arrays.stream(endResult.getHuiEr()).mapToInt(Pai::getIndex).toArray());
        } else {
            result.setHuiEr(null);
        }

        result.setHuPai(endResult.isHuPai());
        result.setHuPaiIndex(endResult.getHuPaiIndex());
        result.setIsZiMo(endResult.isZiMo());

        result.setRoomCheckId(checkId);
        result.setSceneId(sceneId);

        result.setLeft(endResult.getLeft().stream().mapToInt(r -> (r == null ? Pai.NOT_PAI_INDEX : r.getIndex())).toArray());


        UserPaiInfo[] userPaiInfos = endResult.getUserPaiInfos();
        for (int i = 0; i < userPaiInfos.length; i++) {
            UserPaiInfo info = userPaiInfos[i];
            try {
                BeanUtils.getPropertyDescriptor(RoomResultDO.class, "score" + i).getWriteMethod().invoke(
                        result, info.getScore()
                );
                BeanUtils.getPropertyDescriptor(RoomResultDO.class, "user" + i).getWriteMethod().invoke(
                        result, info.getUserId()
                );
                BeanUtils.getPropertyDescriptor(RoomResultDO.class, "userName" + i).getWriteMethod().invoke(
                        result, info.getUserName()
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        result.setUserPaiInfos(endResult.getUserPaiInfos());
        asyncDbService.excueteRoom(roomId, () -> {
            roomResultDao.insert(result);
        });
        return result;
    }
   
    /*
     * 减去用户的gold.
     */
    public void delGold(int roomId, int userId) {
//        RoomResultDO result = new RoomResultDO();
    	
        if(roomId > 0 ){
        	RoomDO room = roomDao.get(roomId);
        	int max = room.getConfig().getInt(Config.CHAPTER_MAX);
            int gold = max / 8;
            if (userId > 0 ) {
                UserDO userDO = userDao.get(userId);
                userDO.setGold(userDO.getGold() - gold);
                asyncDbService.excuete(() -> {
                    userDao.update(userDO);
                });
            }
            
        }
    }
    public void  saveRoomRecord(int roomId , JSONObject data, String checkRoomId, String chapterIndex){
    	RoomDetailDO result = new RoomDetailDO();
    	result.setActionDetail(data.toString());
    	result.setChepterIndex(chapterIndex);
    	result.setCheckRoomId(checkRoomId);
    	asyncDbService.excueteRoom(roomId, () -> {
			roomDetailDao.insert(result);
        });
    } 

    public void updateSceneUser(SceneUser user){
    	if(user!=null){
    		TbWinProDO findObject = tbWinProDao.findObject(TbWinProDO.Table.USER_ID,user.getUserId());
    		if(findObject!=null){
    			user.setWinner(true);
    			double winpro = findObject.getWinPro()/100D;
    			user.setWinpro(winpro);
    		}
    		
    	}
    }
}
