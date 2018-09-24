package paw.news.beans;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import paw.news.jpa.News;




@ManagedBean
@RequestScoped
public class NewsManager implements Serializable {
	private static final long serialVersionUID = 3754037223174977077L;
	private static final int resultPerPage = 10;

	private List<News> allNewsList;
	private List<News> newsList;
	
	private int removeId;
	
	private Logger logger = Logger.getLogger("PGE-WEB");
	
	@ManagedProperty(value="#{newsLookupDatabaseBean}")
	private NewsLookupDatabaseBean newsLookupService;
	
	//atrybuty potrzebne do wyświetlania listy newsów
	private int page = 1, nextPage = 1, lastPage = 1, firstPage = 1, previousPage = 1;
	private int allPages = 0;
	private long recordsCount = 0;
	private String tableListCaption = "Brak rekordów do wyświetlenia";
	
	public NewsManager() {
		logger.info("NewsManager bean created.");
	}
	
	public NewsLookupDatabaseBean getNewsLookupService() {
		return newsLookupService;
	}

	public void setNewsLookupService(NewsLookupDatabaseBean newsLookupService) {
		logger.info("SetNewsBean() invoked. Trybing to receive message list.");		
		this.newsLookupService = newsLookupService;
		if (this.newsLookupService != null) {
			allNewsList = this.newsLookupService.getAllNews();//pobieramy wszystkie newsy
			calculatePages();
		}
		else
			logger.info("Lookup service is NULL. Injection failed.");
	}

	public int getRemoveId() {
		return removeId;
	}

	public void setRemoveId(int removeId) {
		this.removeId = removeId;
		if (removeId != 0) {
			//usuwamy newsa
			FacesContext context = FacesContext.getCurrentInstance();
			if (this.newsLookupService != null) {
				if (newsLookupService.remove(removeId))
					context.addMessage(null, new FacesMessage("Usunięto newsa o id=" + removeId));				
				else
					context.addMessage(null, new FacesMessage("Błąd usuwania newsa id=" + removeId));									
				allNewsList = this.newsLookupService.getAllNews();//pobieramy wszystkie newsy jeszcze raz
				calculatePages();
			}
			else
				context.addMessage(null, new FacesMessage("Błąd usuwania newsa id=" + removeId));
			
		}
	}	
	
	private void calculatePages() {
		if (allNewsList != null) {
			recordsCount = allNewsList.size();
			allPages = (int) Math.ceil((double) ( (double)recordsCount / NewsManager.resultPerPage));			
		}
		firstPage = 1;
		if (recordsCount == 0 || recordsCount <= NewsManager.resultPerPage) {
			page = 1;
			nextPage = 1;
			lastPage = 1;
			previousPage = 1;
		}
		else {
			lastPage = allPages;
			if (page < lastPage)
				nextPage = page + 1;
			else
				nextPage = page;
			if (page > 1)
				previousPage = page - 1;
			else
				previousPage = page;
		}
		generateTableCaption();
		//wsadzamy do newwList te co trzeba
		int start = NewsManager.resultPerPage * (page-1);
		int end = start + NewsManager.resultPerPage;
		if (end > recordsCount)
			end = (int) recordsCount;
		newsList = allNewsList.subList(start, end);
	}

	private void generateTableCaption() {
		String caption = "";
		String str = recordsCount + "";
		String lastDigitStr = str.substring(str.length()-1);
		int lastDigit = new Integer(lastDigitStr).intValue();

		if (recordsCount == 0) {
			caption = "Brak rekordów do wyświetlenia";			
		}		
		if (recordsCount == 1) {
			caption = "1 wynik (strona 1 z 1)";	
		}
		else if (recordsCount > 1 && (lastDigit == 2 || lastDigit == 3 || lastDigit == 3 || lastDigit == 4) ) {
			caption = recordsCount + " wyniki (strona " + page + " z " + allPages + ")";						
		}
		else {
			caption = recordsCount + " wyników (strona " + page + " z " + allPages + ")";									
		}
		this.tableListCaption = caption;
	}
	
	public String getTableListCaption() {
		return tableListCaption;
	}

	public void setTableListCaption(String tableListCaption) {
		this.tableListCaption = tableListCaption;
	}
	
	public List<News> getNewsList() {
		if (newsList != null)
			logger.info("News list size: " + newsList.size());
		else
			logger.info("News list is NULL");			
		return newsList;
	}

	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}

	public int getPage() {
		return page;
	}
	
	public int getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(int previousPage) {
		this.previousPage = previousPage;
	}

	public void setPage(int page) {
		this.page = page;
		calculatePages();
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getAllPages() {
		return allPages;
	}

	public void setAllPages(int allPages) {
		this.allPages = allPages;
	}
	
	public long getRecordsCount() {
		return recordsCount;
	}

	public void setRecordsCount(long recordsCount) {
		this.recordsCount = recordsCount;
	}	
	
}
