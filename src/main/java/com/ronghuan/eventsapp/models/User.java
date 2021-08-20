package com.ronghuan.eventsapp.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    
    // MEMBER VARIABLES
    @NotEmpty(message="Must have first name")
    private String firstName;
    
    @NotEmpty(message="Must have last name")
    private String lastName;
    
    @Email(message="Must have valid email format")
    @NotEmpty(message="Must have email")
    private String email;
    
    @NotEmpty(message="Must have location")
    private String location;
    
    @NotEmpty(message="Must have state")
    private String state;
    
    @NotEmpty(message="Must have password")
    private String password;
    
    @Transient
    private String passwordConfirmation;

    
	// ------------------- RELATIONSHIPS -------------------
    // ONE TO MANY RELATIONSHIP (USER-EVENTS)
	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<Event> createdEvents;
	
    // MANY TO MANY RELATIONSHIP (USER-COMMENTS)
    @OneToMany(mappedBy="author", fetch=FetchType.LAZY)
    private List<Comment> comments;
    
    
    // MtoM WITHOUT MIDDLE TABLE
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    	name = "users_events",
    	joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> attendingEvents;
    
	// ------------------- RELATIONSHIPS -------------------

    // CONSTRUCTORS
    // EMPTY
    public User() {
    	
    }
    
    // FULL
    public User(String firstName, String lastName, String email, String location, String password, String passwordConfirmation) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.location = location;
		this.password = password;
    	this.passwordConfirmation = passwordConfirmation;
	}
    
    
 
    // GETTERS/SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public List<Event> getCreatedEvents() {
		return createdEvents;
	}

	public void setCreatedEvents(List<Event> createdEvents) {
		this.createdEvents = createdEvents;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Event> getAttendingEvents() {
		return attendingEvents;
	}

	public void setAttendingEvents(List<Event> attendingEvents) {
		this.attendingEvents = attendingEvents;
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
