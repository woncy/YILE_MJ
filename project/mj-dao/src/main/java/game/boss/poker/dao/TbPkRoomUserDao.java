package game.boss.poker.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.stereotype.Component;

import game.boss.poker.dao.entiy.TbPkRoomDO;
import game.boss.poker.dao.entiy.TbPkRoomUserDO;
import game.boss.poker.dao.entiy.UserDO2;

@SuppressWarnings("deprecation")
@Component
public class TbPkRoomUserDao  {
	private static Session session;
	private static Object lock = new Object();
	
	static {
		SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();
		session = factory.openSession();
	}
	
	public TbPkRoomUserDO get(int id){
		session.beginTransaction();
		TbPkRoomUserDO roomUser = (TbPkRoomUserDO) session.get(TbPkRoomUserDO.class, id);
		session.getTransaction().commit();
		return roomUser;
	}
	
	public  void update(TbPkRoomUserDO roomUser){
		synchronized (lock) {
			session.beginTransaction();
			session.update(roomUser);
			session.getTransaction().commit();
		}
		
	}
	
	
	
	public void save(TbPkRoomUserDO roomUser){
		synchronized (lock) {
			session.beginTransaction();
			session.save(roomUser);
			session.getTransaction().commit();
		}
	}
	
	public void saveOrUpdate(TbPkRoomUserDO roomUser){
		synchronized (lock) {
			session.beginTransaction();
			session.saveOrUpdate(roomUser);
			session.getTransaction().commit();
			
			
		}
	}
	
	public void revome(int id){
		synchronized (lock) {
		session.beginTransaction();
			session.delete(session.get(TbPkRoomUserDO.class, id));
			session.getTransaction().commit();
		}
	}
	
	public static void colse(){
		if(null!=session){
			session.close();
		}
	}

	public TbPkRoomUserDO getByUserAndRoom(UserDO2 user,TbPkRoomDO room) {
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<TbPkRoomUserDO> lists = session.createQuery("from game.boss.poker.dao.entiy.TbPkRoomUserDO as roomUser "
				+ "where roomUser.user=:user and roomUser.room=:room")
				.setEntity("user", user)
				.setEntity("room", room).list();
		TbPkRoomUserDO roomUserDo = null;
		if(lists != null && lists.size()==1){
			roomUserDo =  lists.get(0);
		}
		session.getTransaction().commit();
		return roomUserDo;
		
	}
	
	public List<TbPkRoomUserDO> getListByUserAndRoom(UserDO2 user,TbPkRoomDO room) {
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<TbPkRoomUserDO> lists = session.createQuery("from game.boss.poker.dao.entiy.TbPkRoomUserDO as roomUser "
				+ "where roomUser.user=:user and roomUser.room=:room")
		.setEntity("user", user)
		.setEntity("room", room).list();
		session.getTransaction().commit();
		return lists;
		
	}

}
