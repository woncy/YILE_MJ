package game.boss.poker.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.stereotype.Component;

import game.boss.poker.dao.entiy.UserDO2;


@Component
public class PokerUserDao {
	private  Session session;
	private  Object lock = new Object();
	
	public  PokerUserDao(){
		SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();
		session = factory.openSession();
	}
	
	public UserDO2 get(int id){
		session.beginTransaction();
		UserDO2 user = (UserDO2) session.get(UserDO2.class, id);
		session.getTransaction().commit();
		return user;
	}
	
	public  void update(UserDO2 user){
		synchronized (lock) {
			session.beginTransaction();
			session.update(user);
			session.getTransaction().commit();
		}
		
	}
	
	public void save(UserDO2 user){
		synchronized (lock) {
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
		}
	}
	
	public void revome(int id){
		synchronized (lock) {
			session.beginTransaction();
			session.delete(session.get(UserDO2.class, id));
			session.getTransaction().commit();
		}
	}
	
	
	public  void colse(){
		if(null!=session){
			session.close();
		}
	}

}
