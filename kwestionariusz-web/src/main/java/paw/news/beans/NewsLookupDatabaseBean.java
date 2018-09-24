package paw.news.beans;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import paw.news.ejb.NewsBean;
import paw.news.jpa.News;


@ManagedBean(eager = true)
@ApplicationScoped
public class NewsLookupDatabaseBean implements NewsLookupService, Serializable {

	private static final long serialVersionUID = -5442331602203781978L;

	private Logger logger = Logger.getLogger("PawNewsExample");

	@EJB(name = "NewsBean")
	private NewsBean newsBean;

	public NewsLookupDatabaseBean() {
		logger.info("NewsLookupDatabaseBean created");
	}

	public NewsBean getNewsBean() {
		return newsBean;
	}

	public void setNewsBean(NewsBean newsBean) {
		this.newsBean = newsBean;
	}

	@Override
	public List<News> getAllNews() {
		return newsBean.getLatestNews(0);
	}

	@Override
	public News getNews(int id) {
		// TODO Auto-generated method stub
		News n = newsBean.getNews(id);
		if (n == null)
			logger.info("LookupService returning null for id=" + id);
		else
			logger.info("News " + id + "title: " + n.getTitle());
		return n;
	}

	@Override
	public boolean merge(News news) {
		return newsBean.merge(news);
	}

	@Override
	public boolean persist(News news) {
		return newsBean.persist(news);
	}

	@Override
	public boolean remove(int id) {
		return newsBean.remove(id);
 	}

}
