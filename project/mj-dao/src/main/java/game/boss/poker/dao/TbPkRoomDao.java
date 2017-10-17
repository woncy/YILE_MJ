package game.boss.poker.dao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.stereotype.Component;

import game.boss.poker.dao.entiy.TbPkRoomDO;
import game.boss.poker.dao.entiy.UserDO2;

@SuppressWarnings("deprecation")
@Component
public class TbPkRoomDao   {

	private static Session session;
	private static Object lock = new Object();
	
	
	static {
		SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();
		session = factory.openSession();
	}
	
	public TbPkRoomDO get(int id){
		session.beginTransaction();
		TbPkRoomDO room = (TbPkRoomDO) session.get(TbPkRoomDO.class, id);
		session.getTransaction().commit();
		return room;
	}
	
	public  void update(TbPkRoomDO room){
		synchronized (lock) {
			session.beginTransaction();
			session.update(room);
			session.getTransaction().commit();
		}
		
	}
	
	public void save(TbPkRoomDO room){
		synchronized (lock) {
			session.beginTransaction();
			session.save(room);
			session.getTransaction().commit();
		}
	}
	
	@SuppressWarnings("unchecked")
	public TbPkRoomDO getByCreateUserId(int userId){
		ArrayList<TbPkRoomDO> list = new ArrayList<TbPkRoomDO>();
		synchronized (lock) {
			session.beginTransaction();
			 list = (ArrayList<TbPkRoomDO>) session.createSQLQuery("select * from tb_pk_room where create_user_id = "+userId).list();
			session.getTransaction().commit();
		}
		if(list == null || list.size() == 0){
			return null;
		}
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<TbPkRoomDO> getRoomsByRoomCheckId(String roomCheckId,boolean start){
			session.beginTransaction();
			//List<TbPkRoomDO> rooms = session.createSQLQuery("select * from tb_pk_room where room_check_id ="+roomCheckId).list();
//			List<TbPkRoomDO> rooms = session.createQuery("from game.boss.poker.dao.entiy.TbPkRoomDO as room where room.roomCheckId= ? and room.start="+start).list();
			 Query query = session.createQuery("from game.boss.poker.dao.entiy.TbPkRoomDO as room where room.roomCheckId= ? and room.start="+start);
				query.setString(0, roomCheckId);
				List<TbPkRoomDO> rooms = query.list();
				System.out.println(rooms.size());
			session.getTransaction().commit();
		return rooms;
		
	}
	
	
	public void revome(int id){
		synchronized (lock) {
		session.beginTransaction();
			session.delete(session.get(TbPkRoomDO.class, id));
			session.getTransaction().commit();
		}
	}
	
	public static void colse(){
		if(null!=session){
			session.close();
		}
	}
	
}
