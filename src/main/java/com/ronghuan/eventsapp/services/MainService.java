package com.ronghuan.eventsapp.services;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ronghuan.eventsapp.models.Comment;
import com.ronghuan.eventsapp.models.Event;
import com.ronghuan.eventsapp.models.User;
import com.ronghuan.eventsapp.repositories.CommentRepository;
import com.ronghuan.eventsapp.repositories.EventRepository;
import com.ronghuan.eventsapp.repositories.UserRepository;

@Service
public class MainService {
	@Autowired
	public UserRepository userRepo;
	
	@Autowired
	public EventRepository eventRepo;
	
	@Autowired
	public CommentRepository commentRepo;
	
	
	// ----------------------- CRUD USER ----------------------- 
	// CREATE
    public User registerUser(User user) {
    	// GET PLAIN TEXT PASSWORD AND TURNING INTO HASHED PASSWORD
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return userRepo.save(user);
    }
    
    // GET USER BY EMAIL
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
    
    // GET USER BY ID
    public User findUserById(Long id) {
    	return userRepo.findById(id).orElse(null);
    }
    
    // AUTHENTICATE USER
    public boolean authenticateUser(String email, String password) {
        // first find the user by email
        User user = userRepo.findByEmail(email);
        // if we can't find it by email, return false
        if(user == null) {
            return false;
        } else {
            // if the passwords match, return true, else, return false
            if(BCrypt.checkpw(password, user.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }
	
		
	// ----------------------- CRUD EVENT ----------------------- 
	
	// CERAET & UPDATE
	public Event saveEvent(Event e) {
		return eventRepo.save(e);
	}
	
	// READ ONE EVENT
	public Event getEvent(Long id) {
		return eventRepo.findById(id).orElse(null);
	}
	
	// READ ALL EVENTS
	public List<Event> getAllEvents(){
		return eventRepo.findAll();
	}

	// DELETE ONE EVNET
	public void deleteEvent(Long event_id) {
		eventRepo.deleteById(event_id);
	}
	
	// GET ONE EVENT BY ID
    // GET USER BY ID
    public Event findEventById(Long event_id) {
    	return eventRepo.findById(event_id).orElse(null);
    }
    
	
	
	// ----------------------- CRUD COMMENT ----------------------- 
	// CREATE COMMENT
	public Comment saveComment(Comment c) {
		return commentRepo.save(c);
	}
	
	// GET ALL COMMENTS(NEED FOR PARTICULAR EVENT'S COMMENT, NOT JUST ALL COMMENTS)

	
		
}
