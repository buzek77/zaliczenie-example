package paw.news.ejb;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import paw.news.jpa.News;



@Stateless(mappedName = "NewsBean")
public class NewsBean {

	public static final int DEFAULT_COUNT = 20;
	
	private Logger logger = Logger.getLogger("PawNewsExample");

	@PersistenceUnit(unitName = "PawNewsExample")
	EntityManagerFactory emf;
	
	public NewsBean() {
		logger.info("News bean created...");
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean persist(Object o) {
		EntityManager entityManager = emf.createEntityManager();
		try {
			logger.info("Saving object: " + o);
			entityManager.persist(o);
			logger.info("Object saved: " + o);
			return true;
		} catch (Exception e) {
			logger.severe("NewsBean::persist: Error writing to DB: " + e);
			logger.severe("" + e.fillInStackTrace());
			return false;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean remove(int id) {
		EntityManager entityManager = emf.createEntityManager();
		try {
			Query q = entityManager.createQuery("DELETE FROM News n WHERE n.id = " + id);
			q.executeUpdate();
			logger.info("Object removed");
			return true;
		} catch (Exception e) {
			logger.severe("NewsBean::remove: " + e);
			logger.severe("" + e.fillInStackTrace());
			return false;
		}
	}	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean merge(Object o) {
		EntityManager entityManager = emf.createEntityManager();
		try {
			logger.info("Updating object: " + o);
			entityManager.merge(o);
			logger.info("Object updated: ");
			return true;
		} catch (Exception e) {
			logger.severe("NewsBean::merge: Error writing to DB: " + e);
			logger.severe("" + e.fillInStackTrace());
			return false;
		}
	}
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public List<News> getLatestNews(int count) {
		if (count < 0)
			count = DEFAULT_COUNT;
		EntityManager em = emf.createEntityManager();
		try {
			Query query = em.createQuery("FROM News n ORDER BY n.createdAt DESC", News.class);
			if (count > 0)
				query.setMaxResults(count);
			return query.getResultList();
		} catch (Exception e) {
			logger.warning("NewsBean::getLatestNews, error while executing query: " + e);
		}
		return null;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)		
	public News getNews(int id) {
		EntityManager em = emf.createEntityManager();
		return em.find(News.class, id);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public List<News> getNewsFromDate(Date fromDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		EntityManager em = emf.createEntityManager();
		try {
			Query query = em.createQuery("FROM News n WHERE n.updatedAt > '" + sdf.format(fromDate) + "' ORDER BY n.createdAt DESC", News.class);	
			return query.getResultList();
		} catch (Exception e) {
			logger.warning("NewsBean::getNewsFromDate, error while executing query: " + e);
		}		
		return null;
	}
	
}
