package paw.news.ejb;

import paw.news.jpa.News;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;


@Stateless
@WebService
public class MobileBean {

	private Logger logger = Logger.getLogger("PawNewsExample");

	@EJB
	protected NewsBean nb;

	@WebMethod
	@WebResult
	public News[] getNews(@WebParam(name = "timestamp") long lastTimestamp) {
		logger.info("MobileBean::getNews invoked.");
		Date from = new Date(lastTimestamp);
		logger.info("MobileBean::getNewsFrom invoked. Date from: " + from + ", " + from.toString());
		List<News> result = nb.getNewsFromDate(from);
		if (result != null) {
			logger.info("Returning " + result.size() + " news");
			return result.toArray(new News[result.size()]);
		}
		logger.info("MobileBean::getNews null received from NewsBean.");
		return null;
	}

}
