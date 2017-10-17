//package game.boss.admin;
//
//import game.boss.dao.dao.RoomResultDao;
//import game.boss.dao.entity.RoomResultDO;
//
//import java.util.List;
//
//import mj.data.UserPaiInfo;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * @Package:game.boss.admin
// * @ClassName: asfdas
// * @Description: TODO
// * @author XuKaituo
// * @date May 20, 2017  6:31:09 PM
// */
//public class asfdas {
//
//    @Autowired
//    private RoomResultDao roomResultDao;
//    
//    public void bosw(int roomId){
//        
//        StaticsResultRet staticsResultRet =  new StaticsResultRet() ;
//        staticsResultRet.setLocationIndex0(0);
//        staticsResultRet.setLocationIndex1(1);
//        staticsResultRet.setLocationIndex2(2);
//        staticsResultRet.setLocationIndex3(3);
//        
//        List<RoomResultDO> roomResultDOs=roomResultDao.getAllList(roomId);
//        
//        for(RoomResultDO roomResultDO:roomResultDOs){
//            
//            int fangPaoIndex = roomResultDO.getFangPaoIndex();
//            int huPaiIndex = roomResultDO.getHuPaiIndex();
//            //判断放炮
//            switch (fangPaoIndex) {
//            //这种情况有流局和自摸两种情况
//            case -1:
//                switch (huPaiIndex) {
//                case -1:
//                    //流局不用管
//                    break;
//                case 0:
//                   staticsResultRet.setZimo0(staticsResultRet.getZimo0()+1);
//                    break;
//                case 1:
//                    staticsResultRet.setZimo1(staticsResultRet.getZimo1()+1);
//                    break;
//                case 2:
//                    staticsResultRet.setZimo2(staticsResultRet.getZimo2()+1);
//                    break;
//                case 3:
//                    staticsResultRet.setZimo3(staticsResultRet.getZimo3()+1);
//                    break;
//                default:
//                    break;
//                }
//                break;
//            case 0:
//                staticsResultRet.setFangpao0(staticsResultRet.getFangpao0()+1);
//                break;
//            case 1:
//                staticsResultRet.setFangpao1(staticsResultRet.getFangpao1()+1);
//                break;
//            case 2:
//                staticsResultRet.setFangpao2(staticsResultRet.getFangpao2()+1);
//                break;
//            case 3:
//                staticsResultRet.setFangpao3(staticsResultRet.getFangpao3()+1);
//                break;
//            default:
//                break;
//            }
//            //判断接炮
//            if(fangPaoIndex>-1){
//                switch (huPaiIndex) {
//                case 0:
//                    staticsResultRet.setJiepao0(staticsResultRet.getJiepao0()+1);
//                    break;
//                case 1:
//                    staticsResultRet.setJiepao1(staticsResultRet.getJiepao1()+1);
//                    break;
//                case 2:
//                    staticsResultRet.setJiepao2(staticsResultRet.getJiepao2()+1);
//                    break;
//                case 3:
//                    staticsResultRet.setJiepao3(staticsResultRet.getJiepao3()+1);
//                    break;
//
//                default:
//                    break;
//                }
//            }
//            //杠的数量
//            UserPaiInfo[] userPaiInfos=roomResultDO.getUserPaiInfos();
//            for(UserPaiInfo userPaiInfo:userPaiInfos){
//                switch (userPaiInfo.getLocationIndex()) {
//                case 0:
//                    staticsResultRet.setAngang0(staticsResultRet.getAngang0() + userPaiInfo.getAnGang().size());
//                    staticsResultRet.setMinggang0(staticsResultRet.getMinggang0()+userPaiInfo.getDaMingGang().size()+userPaiInfo.getXiaoMingGang().size());
//                    break;
//                case 1:
//                    staticsResultRet.setAngang1(staticsResultRet.getAngang1() + userPaiInfo.getAnGang().size());
//                    staticsResultRet.setMinggang1(staticsResultRet.getMinggang1()+userPaiInfo.getDaMingGang().size()+userPaiInfo.getXiaoMingGang().size());
//                    break;
//                case 2:
//                    staticsResultRet.setAngang2(staticsResultRet.getAngang2() + userPaiInfo.getAnGang().size());
//                    staticsResultRet.setMinggang2(staticsResultRet.getMinggang2()+userPaiInfo.getDaMingGang().size()+userPaiInfo.getXiaoMingGang().size());
//                    break;
//                case 3:
//                    staticsResultRet.setAngang3(staticsResultRet.getAngang3() + userPaiInfo.getAnGang().size());
//                    staticsResultRet.setMinggang3(staticsResultRet.getMinggang3()+userPaiInfo.getDaMingGang().size()+userPaiInfo.getXiaoMingGang().size());
//                    break;
//                default:
//                    break;
//                }
//            }
//        }
//    }
//}
