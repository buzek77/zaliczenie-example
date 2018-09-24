package paw.news.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * Ta klasa jest tylko po to żeby bean do dodawania miał inny okres życia niz do edycji.
 *
 */
@ManagedBean
@RequestScoped
public class NewsManagedBean2 extends NewsManagedBean {

	public NewsManagedBean2() {
		super();
	}
	
}
