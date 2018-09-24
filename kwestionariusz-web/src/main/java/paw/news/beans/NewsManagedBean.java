package paw.news.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import paw.news.jpa.News;


@ManagedBean
@SessionScoped
public class NewsManagedBean implements Serializable {

	private static final long serialVersionUID = -7962404280558108365L;

	protected Logger logger = Logger.getLogger("PawNewsExample");
	
	protected News news;
	protected int id;
	protected String title;
	protected String content;
	protected String description;

	@ManagedProperty(value="#{newsLookupDatabaseBean}")
	private NewsLookupService newsLookupService;
	
	public NewsManagedBean() {
		super();
		logger.info("News bean created...");
		news = new News();
	}

	public NewsLookupService getNewsLookupService() {
		return newsLookupService;
	}

	public void setNewsLookupService(NewsLookupService newsLookupService) {
		this.newsLookupService = newsLookupService;
 	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		if (id == 0) {
			this.news = null;
			this.title = "";
			this.content = "";
			this.description = "";			
		}
		if (this.newsLookupService != null && id != 0) {
			this.news = this.newsLookupService.getNews(id);
			if (this.news != null) {
				this.title = this.news.getTitle();
				this.content = this.news.getContent();
				this.description = this.news.getDescription();
			}
		}		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String update() {
		FacesContext context = FacesContext.getCurrentInstance();
		this.news.setUpdatedAt(new Date());
		this.news.setContent(content);
		this.news.setTitle(title);
		this.news.setDescription(description);
		if (this.newsLookupService.merge(this.news)) {
			context.addMessage(null, new FacesMessage("Edycja newsa przebiegła poprawnie."));
		}
		else {
			context.addMessage(null, new FacesMessage("Błąd podczas edycji."));			
		}
		return null;
	}
	
	public String add() {
		FacesContext context = FacesContext.getCurrentInstance();
		this.news = new News();
		this.news.setUpdatedAt(new Date());
		this.news.setCreatedAt(new Date());
		this.news.setContent(content);
		this.news.setTitle(title);
		this.news.setDescription(description);		
		if (this.newsLookupService.persist(this.news)) {
			context.addMessage(null, new FacesMessage("Dodano nowego newsa."));
		}
		else {
			context.addMessage(null, new FacesMessage("Błąd podczas dodania newsa."));
			return null;
		}
		return "news-list";//"edit.xhtml?id=" + news.getId();
	}
	
	
	
}
