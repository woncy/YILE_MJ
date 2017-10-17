package game.boss.poker.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.stereotype.Component;

import game.boss.poker.dao.entiy.TbPkChapterDO;
import game.boss.poker.dao.entiy.TbPkRoomDO;

@SuppressWarnings("deprecation")
@Component
public class TbPkChapterDao  {
	
	private static Session session;
	private static Object lock = new Object();
	
	static {
		SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();
		session = factory.openSession();
	}
	
	public TbPkChapterDO get(int id){
		session.beginTransaction();
		TbPkChapterDO chapter = (TbPkChapterDO) session.get(TbPkChapterDO.class, id);
		session.getTransaction().commit();
		return chapter;
	}
	public List<TbPkChapterDO> getByRoom(TbPkRoomDO roomDO){
		session.beginTransaction();
		String hql = "from game.boss.poker.dao.entiy.TbPkChapterDO chapter where chapter.room=:room";
		List<TbPkChapterDO> list = session.createQuery(hql).setEntity("room", roomDO).list();
		session.getTransaction().commit();
		return list;
	}
	
	public  void update(TbPkChapterDO chapter){
		synchronized (lock) {
			session.beginTransaction();
			session.update(chapter);
			session.getTransaction().commit();
		}
		
	}
	
	public void save(TbPkChapterDO chapter){
		synchronized (lock) {
			session.beginTransaction();
			session.save(chapter);
			session.getTransaction().commit();
		}
	}
	
	public void revome(int id){
		synchronized (lock) {
			session.beginTransaction();
			session.delete(session.get(TbPkChapterDO.class, id));
			session.getTransaction().commit();
		}
	}
	
	public static void colse(){
		if(null!=session){
			session.close();
		}
	}
	
	
}
