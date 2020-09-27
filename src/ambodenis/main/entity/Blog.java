package ambodenis.main.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table
public class Blog {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	@Size(min = 20, max = 255, message = "min 20, max 255 characters")
	private String title;
	@Column
	@Size(min = 50, max = 500, message = "min 50, max 500 characters")
	private String description;
	@Column
	private String image;
	@Column
	private String date;
	@Column
	private int review;
	@Column
	private boolean important;
	@Column
	private String textArea;
	@Transient
	private int dateDifference;
	
	@JoinColumn(name = "authorName")
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private User user;
	
	@JoinColumn(name = "categoryId")
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	private Category category;
	
	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinTable(name = "blog_tag", joinColumns = @JoinColumn(name = "id_blog"), inverseJoinColumns = @JoinColumn(name = "id_tag"))
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Tag> tags;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idBlog", fetch = FetchType.EAGER)
	private List<Comment> comments;
	
	public Blog() {
	}
	
	public Blog(String title, String description, String introduction, String image, String subtitle, String textArea,
			String date, int review, boolean important, Category category, List<Tag> tags, List<Comment> comments) {
		super();
		this.title = title;
		this.description = description;
		this.image = image;
		this.date = date;
		this.review = review;
		this.important = important;
		this.category = category;
		this.tags = tags;
		this.comments = comments;
		this.textArea = textArea;
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getReview() {
		return review;
	}
	public void setReview(int review) {
		this.review = review;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public boolean getImportant() {
		return important;
	}
	public void setImportant(boolean important) {
		this.important = important;
	}	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}	
	public int getDateDifference() {
		return dateDifference;
	}
	public void setDateDifference(int dateDifference) {
		this.dateDifference = dateDifference;
	}
	public String getTextArea() {
		return textArea;
	}
	public void setTextArea(String textArea) {
		this.textArea = textArea;
	}

	
	public void setDate() {
		Date date = new Date();
		SimpleDateFormat simpleDate = new SimpleDateFormat("EEEE, MMMM d, yyyy");
		this.setDate(simpleDate.format(date));
	}

	@Override
	public String toString() {
		return "("+id+") - "+title;
	}
}
