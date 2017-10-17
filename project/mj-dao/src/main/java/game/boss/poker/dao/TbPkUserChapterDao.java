package game.boss.poker.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.stereotype.Component;

import game.boss.poker.dao.entiy.TbPkChapterDO;
import game.boss.poker.dao.entiy.TbPkUserChapterDO;

@SuppressWarnings("deprecation")
@Component
public class TbPkUserChapterDao  {
	private static Session session;
	private static Object lock = new Object();
	
	static {
		SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();
		session = factory.openSession();
	}
	
	public TbPkUserChapterDO get(int id){
		session.beginTransaction();
		TbPkUserChapterDO userChapter = (TbPkUserChapterDO) session.get(TbPkUserChapterDO.class, id);
		session.getTransaction().commit();
		return userChapter;
	}
	
	@SuppressWarnings("unchecked")
	public List<TbPkUserChapterDO> getByChapter(TbPkChapterDO chapterDO ){
		session.beginTransaction();
		List<TbPkUserChapterDO> list = session.createQuery("from game.boss.poker.dao.entiy.TbPkUserChapterDO where chapter =: chapter")
				.setEntity("chapter", chapterDO).list();
		session.getTransaction().commit();
		return list;
	}
	
	public  void update(TbPkUserChapterDO userChapter){
		synchronized (lock) {
			session.beginTransaction();
			session.update(userChapter);
			session.getTransaction().commit();
		}
		
	}
	
	public void save(TbPkUserChapterDO userChapter){
		synchronized (lock) {
			session.beginTransaction();
			session.save(userChapter);
			session.getTransaction().commit();
		}
	}
	
	public void revome(int id){
		synchronized (lock) {
			session.beginTransaction();
			session.delete(session.get(TbPkUserChapterDO.class, id));
			session.getTransaction().commit();
		}
	}
	
	public static void colse(){
		if(null!=session){
			session.close();
		}
	}

}
