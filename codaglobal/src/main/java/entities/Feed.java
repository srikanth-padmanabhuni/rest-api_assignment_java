package entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


@Entity
@Table(name = "feed")
@NamedQueries({ 
	@NamedQuery(name = "Feed.findFeedsByUser", query = "SELECT f FROM Feed f WHERE f.users.id = :userId"),
	@NamedQuery(name = "Feed.findFeedByUserIdNdFeedId", query = "SELECT f FROM Feed f WHERE f.users.id = :userId AND f.id = :feedId")
})
public class Feed {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Type(type="text")
	private String posturl;
	
	@Type(type="text")
	private String description;
	
	private boolean privacy;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Users users;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPosturl() {
		return posturl;
	}

	public void setPosturl(String posturl) {
		this.posturl = posturl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPrivacy() {
		return privacy;
	}

	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Feed [id=" + id + ", posturl=" + posturl + ", description=" + description + ", privacy=" + privacy
				+ "]";
	}
	
}
