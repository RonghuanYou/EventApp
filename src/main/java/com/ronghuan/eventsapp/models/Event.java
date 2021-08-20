package com.ronghuan.eventsapp.models;

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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="events")
public class Event {
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    
    // MEMBER VARIABLES
    @NotEmpty(message="Must have event name")
    private String name;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message="Must have event date")
    private Date date;
    
    @NotEmpty(message="Must have event location")
    private String location;
    
    private String host;
    
    @NotEmpty(message="Must have event state")
    private String state;
    
    
  
	// ------------------- RELATIONSHIPS -------------------
    // ONE TO MANY RELATIONSHIP (EVENTS-USER)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User creator;

    // MANY TO MANY RELATIONSHIP (EVENT-COMMENTS)
    @OneToMany(mappedBy="event", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;

    
    // MtoM WITHOUT MIDDLE TABLE
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    	name = "users_events",
    	joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> attendees;
	// ------------------- RELATIONSHIPS -------------------

    // CONSTRUCTORS
    // EMPTY
    public Event() {
    	
    }
    
    // FULL
    public Event(String name, Date date, String location, String host, String state) {
    	this.name = name;
    	this.date = date;
    	this.location = location;
    	this.host = host;
    	this.state = state;
    }
    
    
    // GETTERS/SETTERS
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	

	public List<User> getAttendees() {
		return attendees;
	}

	public void setAttendees(List<User> attendees) {
		this.attendees = attendees;
	}




	@Column(updatable=false)
    private Date createdAt;
	private Date updatedAt;
	
    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
}
