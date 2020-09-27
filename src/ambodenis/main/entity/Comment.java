package ambodenis.main.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Comment {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String name;
	@Column
	private String email;
	@Column
	private String message;
	@Column
	private boolean isOnPage;
	@Column
	private String date;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "idBlog")
	private Blog idBlog;
	
	public Comment() {
	}
	
	public Comment(String name, String email, String message, boolean isOnPage, String date, Blog idBlog) {
		super();
		this.name = name;
		this.email = email;
		this.message = message;
		this.isOnPage = isOnPage;
		this.idBlog = idBlog;
		this.date = date;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean getIsOnPage() {
		return isOnPage;
	}
	public void setIsOnPage(boolean isOnPage) {
		this.isOnPage = isOnPage;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Blog getIdBlog() {
		return idBlog;
	}
	public void setIdBlog(Blog idBlog) {
		this.idBlog = idBlog;
	}
	
	public void setDate() {
		Date date = new Date();
		SimpleDateFormat simpleDate = new SimpleDateFormat("EEEE, MMMM d, yyyy");
		this.setDate(simpleDate.format(date));
	}
	
	@Override
	public String toString() {
		return "("+id+") - "+name;
	}

}
