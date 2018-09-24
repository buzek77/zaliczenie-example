package paw.news.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "news")
@XmlRootElement
@XmlType(	namespace = "http://paw.agh.edu.pl/types/", name = "News",
			propOrder = {
				"id", "title", "description", "content",
				"createdAtTimestamp" })
public class News {

	protected int id;
	protected String title;
	protected String description;
	protected String content;
	protected Date createdAt;
	protected Date updatedAt;

	public News() {
	}

	public News(int id, String title, String description, String content) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.content = content;
		createdAt = new Date();
		updatedAt = new Date();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable=false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "title", nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "description", nullable = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "content", nullable = false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false, length = 29)
	@XmlTransient
	public java.util.Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(java.util.Date createdAt) {
		this.createdAt = createdAt;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at", nullable = false, length = 29)
	@XmlTransient
	public Date getUpdatedAt() {
		return updatedAt;
	}
	
	@Transient
	@XmlElement(name = "createdAt")
	protected long getCreatedAtTimestamp() {
		return createdAt.getTime();
	}
	
	@Transient
	protected void setCreatedAtTimestamp(long createdAtTimestamp) {
		createdAt.setTime(createdAtTimestamp);
	}
	
	@Transient
	@XmlTransient
	protected long getUpdatedAtTimestamp() {
		return updatedAt.getTime();
	}
		
	@Transient
	protected void setUpdatedAtTimestamp(long updatedAtTimestamp) {
		updatedAt.setTime(updatedAtTimestamp);
	}
		
	public void setUpdatedAt(java.util.Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@XmlTransient
	@Transient	
	public String getTitleAbbr() {
		if (title != null)
			if (title.length() > 20)
				return title.substring(0, 20) + "...";
			else
				return title;
		return null;
	}

	@XmlTransient
	@Transient	
	public String getContentAbbr() {
		if (content != null)
			if (content.length() > 30)
				return content.substring(0, 30) + "...";
			else
				return content;
		return null;
	}

	@XmlTransient
	@Transient	
	public String getCreatedAtAbbr() {
		String ret = "" + createdAt;
		return ret.substring(0, 16);
	}
	
	@XmlTransient
	@Transient	
	public String getUpdatedAtAbbr() {
		String ret = "" + updatedAt;
		return ret.substring(0, 16);
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", title=" + title + ", description="
				+ description + ", content=" + content + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
}
