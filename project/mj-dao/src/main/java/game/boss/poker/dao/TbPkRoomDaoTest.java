package game.boss.poker.dao;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.Test;

import game.boss.dao.dao.UserDao;
import game.boss.poker.dao.entiy.TbPkChapterDO;
import game.boss.poker.dao.entiy.TbPkRoomDO;
import game.boss.poker.dao.entiy.TbPkRoomUserDO;
import game.boss.poker.dao.entiy.UserDO2;


public class TbPkRoomDaoTest {

	
	
	@SuppressWarnings("deprecation")
	@Test
	public void test() {
		SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		TbPkRoomDO room = new TbPkRoomDO();
		
		room.setChapterNums(1);
		room.setConfig("2");
		room.setEnd(false);
		room.setRoomCheckId("123456");
		room.setSceneId(10000);
		
		room.setUser((UserDO2)session.get(UserDO2.class, 30));
		
		session.save(room);
		
		System.out.println(room.getId());
		session.getTransaction().commit();
		session.close();
	}
	@Test
	public void test2(){
		TbPkRoomUserDao dao = new TbPkRoomUserDao();
		TbPkRoomUserDO roomUser = new TbPkRoomUserDO();
		UserDO2 user = new PokerUserDao().get(100121);
		
		dao.save(roomUser);
		
		
	}
	@Test
	public void testRoomUserDao(){
		
		TbPkRoomUserDao dao = new TbPkRoomUserDao();
		UserDO2 user = new PokerUserDao().get(30);
		System.out.println(user.getRooms());
		
		TbPkRoomDO tbPkRoomDO = new TbPkRoomDO();
		tbPkRoomDO.setSceneId(10000);;
		
		System.out.println("房间信息："+dao.getByUserAndRoom(user, tbPkRoomDO));
	}
	@Test
	public void test1(){
		TbPkRoomDao dao = new TbPkRoomDao();
		List<TbPkRoomDO> roomsByRoomCheckId = dao.getRoomsByRoomCheckId("081494",false);
		System.out.println(roomsByRoomCheckId.size());
	}

	@Test
	public void getByRoom(){
		TbPkChapterDao dao = new TbPkChapterDao();
		TbPkRoomDO room = new TbPkRoomDO();
		room.setId(354);
		List<TbPkChapterDO> byRoom = dao.getByRoom(room);
		System.out.println(byRoom);
	}
	
	@Test
	public void testRoomUsers(){
		int userId=100838;
		PokerUserDao dao = new PokerUserDao();
		List<TbPkRoomUserDO> userRooms = dao.get(userId).getRooms();
		System.out.println("-------------------------------------------------");
		System.out.println("user[100838]的所有房间：");
		for (int i = 0; i < userRooms.size(); i++) {
			TbPkRoomDO roomUser = userRooms.get(i).getRoom();
			System.out.print(roomUser.getId()+"  ");
			
			System.out.println(roomUser.getId()+"的所有用户:");
			for (int j = 0; j < roomUser.getRoomUsers().size(); j++) {
				System.out.print(roomUser.getRoomUsers().get(j).getUser().getId()+" ");
			}
		}
	}
}
