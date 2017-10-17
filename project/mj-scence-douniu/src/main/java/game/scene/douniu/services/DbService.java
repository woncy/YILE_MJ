package game.scene.douniu.services;

import game.boss.dao.dao.RoomDao;
import game.boss.dao.dao.RoomResultDao;
import game.boss.dao.dao.UserDao;
import game.boss.dao.entity.RoomDO;
import game.boss.dao.entity.RoomResultDO;
import game.boss.dao.entity.UserDO;
import game.boss.poker.dao.TbPkRoomUserDao;
import game.boss.poker.dao.dao.TbPkRoomDao;
import game.boss.poker.dao.entity.TbPkRoomUserDO;
import game.boss.poker.dao.entity.TbPkRoomDO;






import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import mj.data.ChapterEndResult;
import mj.data.Config;
import mj.data.Pai;
import mj.data.UserPaiInfo;
import mj.net.message.game.douniu.CompareResult;
import mj.net.message.game.douniu.DouniuGameChapterEnd;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Douniu.data.DouniuConfig;
import Douniu.data.DouniuEndResult;
import Douniu.data.DouniuUserPaiInfo;

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
    private TbPkRoomUserDao roomResultDao;
    
    @Autowired
    private TbPkRoomDao douniuRoomDao;
    @Autowired
    private RoomDao roomDao;
    
    @Autowired
    private UserDao userDao;


    public TbPkRoomUserDO save(DouniuGameChapterEnd endResult, int roomId) {
        TbPkRoomUserDO result = new TbPkRoomUserDO();
        TbPkRoomDO room =new TbPkRoomDO();
        room.setId(roomId);
        result.setLocationIndex(endResult.getZhuangIndex());
       ArrayList<CompareResult> userPaiInfos = endResult.getCompareResultList();
        for (int i = 0; i < userPaiInfos.size(); i++) {
        	CompareResult info = userPaiInfos.get(i);
            try {
//                BeanUtils.getPropertyDescriptor(TbPkRoomUserDO.class, "score" + i).getWriteMethod().invoke(
//                        result, info.getWinCountNum()
//                );
//              //  BeanUtils.getPropertyDescriptor(RoomResultDO.class, "user" + i).getWriteMethod().invoke(
//             //           result, info.getUserId()
//                );
//           //     BeanUtils.getPropertyDescriptor(RoomResultDO.class, "userName" + i).getWriteMethod().invoke(
//             //           result, info.getUserName()
//                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
      
        asyncDbService.excueteRoom(roomId, () -> {
//            roomResultDao.save(result);
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
            int gold = (int) Math.ceil(max / 15);
            if (userId > 0 ) {
                UserDO userDO = userDao.get(userId);
                userDO.setGold(userDO.getGold() - gold);
            }
            asyncDbService.excuete(() -> {
                userDao.incrementUpdatePartial(
                        UserDO.Table.GOLD, -gold,
                        new UserDO.Key(userId)
                );
            });
        }
    }   
    
    public void delGoldDouniu(int roomId, int userId) {
      if(roomId > 0 ){
      	TbPkRoomDO room = douniuRoomDao.get(roomId);
      	int max = Integer.parseInt(room.getConfig().getString(DouniuConfig.CHAPTER_MAX));
          int gold = (int) Math.ceil(max / 10);
          System.out.println("房卡数：：："+gold);
          if (userId > 0 ) {
              UserDO userDO = userDao.get(userId);
              userDO.setGold(userDO.getGold() - gold);
          }
          asyncDbService.excuete(() -> {
              userDao.incrementUpdatePartial(
                      UserDO.Table.GOLD, -gold,
                      new UserDO.Key(userId)
              );
          });
      }
  }    
}
