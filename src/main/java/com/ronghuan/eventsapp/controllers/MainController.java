package com.ronghuan.eventsapp.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ronghuan.eventsapp.models.Comment;
import com.ronghuan.eventsapp.models.Event;
import com.ronghuan.eventsapp.models.User;
import com.ronghuan.eventsapp.services.MainService;
import com.ronghuan.eventsapp.validators.UserValidator;

@Controller
public class MainController {
	// mainServ includes user/event/comment services
	@Autowired
	private MainService mainServ;
	
	@Autowired
	private UserValidator userValidator;
	
	// -------------------------- Registration/Login Page --------------------------------------
	@GetMapping("/")
	public String home(@ModelAttribute("userObj") User emptyUser) {
		return "index.jsp";
	}
	
	@PostMapping("/registration")
	public String login(
		@Valid @ModelAttribute("userObj") User filledUser,
		BindingResult result,
		HttpSession session) {
		
		userValidator.validate(filledUser, result);
		
		if (result.hasErrors()) {
			return "index.jsp";
		} else {
			// save user info in db and save user_id in session
			User newUser = mainServ.registerUser(filledUser);
			session.setAttribute("user_id", newUser.getId());
			return "redirect:/events";
		}
	}
	
    @PostMapping("/login")
    public String loginUser(
    	@RequestParam("email") String email, @RequestParam("password") String password, 
    	Model model, 
    	HttpSession session,
    	RedirectAttributes flashMessages) {
        // if the user is authenticated, save their user id in session
    	// else, add error messages and return the login page
    	if (mainServ.authenticateUser(email, password)) {
    		User loggedUser = mainServ.findByEmail(email);
    		session.setAttribute("user_id", loggedUser.getId());
    		return "redirect:/events";
    	} else {
    		flashMessages.addFlashAttribute("error", "INVALID LOGIN");
    		return "redirect:/";
    	}
    }
    
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // invalidate session and redirect to login page
    	session.invalidate();
    	return "redirect:/";
    }
	
	
	// --------------------- DASHBOARD, CRUD EVENT ---------------------
	@GetMapping("/events")
	public String dashboard(
		Model model, @ModelAttribute("eventObj") Event emptyEvent,
		HttpSession session,
		RedirectAttributes flashMessages) {
		// CHECK IF USER LOGINS,IF NOT, RETURN REGISTER/LOGIN PAGE
		if (session.getAttribute("user_id") == null) {
    		flashMessages.addFlashAttribute("error", "YOU HAVE TO LOGIN FIRST");
			return "redirect:/";
		}
		
		// get all events and pass them to jsp
		List<Event> allEvents = mainServ.getAllEvents();
		model.addAttribute("events", allEvents);

		// get current user object and pass it to jsp
		model.addAttribute("user", mainServ.findUserById((Long)session.getAttribute("user_id")));
		return "dashboard.jsp";
	}
	
	
	// CREATE AN EVENT
	@PostMapping("/events")
	public String createEvent(
		Model model,
		@Valid @ModelAttribute("eventObj") Event filledEvent,
		BindingResult result, HttpSession session) {

		if (result.hasErrors()) {
			model.addAttribute("user", mainServ.findUserById((Long)session.getAttribute("user_id")));
			model.addAttribute("events", mainServ.getAllEvents());
			return "dashboard.jsp";
		} else {
			mainServ.saveEvent(filledEvent);
			return "redirect:/events";			
		}		
	}
	
	
	// READ ONE EVENT
	@GetMapping("/events/{id}")
	public String readEvent(
		@PathVariable("id") Long event_id, Model model, 
		@ModelAttribute("commentObj") Comment emptyComment,
		HttpSession session,
		RedirectAttributes flashMessages) {
		// CHECK IF USER LOGINS,IF NOT, RETURN REGISTER/LOGIN PAGE
		if (session.getAttribute("user_id") == null) {
    		flashMessages.addFlashAttribute("error", "YOU HAVE TO LOGIN FIRST");
			return "redirect:/";
		}
		// GET ONE EVENT AND PASS IT TO JSP(INCLUDES ALL COMMENTS FOR THE EVENT)
		model.addAttribute("event", mainServ.getEvent(event_id));
	
		return "event.jsp";
	}
	
	
	// CREATE A MESSAGE AND SHOW THEM IN THE SAME PAGE
	@PostMapping("/comment/create")
	public String createComment(@ModelAttribute("commentObj") Comment filledComment) {
		// * get event id via commentObj
		Long event_id = filledComment.getEvent().getId();
		mainServ.saveComment(filledComment);
		return "redirect:/events/" + event_id;
	}
	
	
	// SHOW EDIT EVENT PAGE
	@GetMapping("/events/{id}/edit")
	public String editEvent(@ModelAttribute("eventObj") Event filledEvent,
		@PathVariable("id") Long event_id, Model model,
		HttpSession session,
		RedirectAttributes flashMessages) {
		// CHECK IF USER LOGINS,IF NOT, RETURN REGISTER/LOGIN PAGE
		if (session.getAttribute("user_id") == null) {
    		flashMessages.addFlashAttribute("error", "YOU HAVE TO LOGIN FIRST");
			return "redirect:/";
		}
		
		// GET THE PARTICULAR EVENT AND PASS IT TO JSP
		model.addAttribute("event", mainServ.getEvent(event_id));
		
		model.addAttribute("user", mainServ.findUserById((Long)session.getAttribute("user_id")));		
		return "edit.jsp";
	}
	
	
	// UPDATE EVENT INFO
	@PutMapping("/events/{id}/edit")
	public String updateEvent(
		@Valid @ModelAttribute("eventObj") Event filledEvent, 
		BindingResult result) {
		if (result.hasErrors()) {
			return "edit.jsp";
		} else {
			mainServ.saveEvent(filledEvent);
			return "redirect:/events";
		}
	}
	
	
	// DELETE ONE EVENT
	@GetMapping("/events/{id}/delete")
	public String deleteEvent(@PathVariable("id") Long event_id,
		HttpSession session,
		RedirectAttributes flashMessages) {
		// CHECK IF USER LOGINS, IF NOT, RETURN REGISTER/LOGIN PAGE
		if (session.getAttribute("user_id") == null) {
    		flashMessages.addFlashAttribute("error", "YOU HAVE TO LOGIN FIRST");
			return "redirect:/";
		}
	
		// DELETE A PARTICULAR EVENT
		mainServ.deleteEvent(event_id);
		return "redirect:/events";
	}
	
	
	// JOIN A EVENT(create relationship between user and event)
	@GetMapping("/events/{id}/join")
	public String joinEvent(
		@PathVariable("id") Long event_id,
		HttpSession session, RedirectAttributes flashMessages) {
		// CHECK IF USER LOGINS, IF NOT, RETURN REGISTER/LOGIN PAGE
		if (session.getAttribute("user_id") == null) {
    		flashMessages.addFlashAttribute("error", "YOU HAVE TO LOGIN FIRST");
			return "redirect:/";
		}
		
		// USING ID TO FIND BINDING EVENT AND USER		
		User oneAttendee = mainServ.findUserById((Long) session.getAttribute("user_id"));
		Event oneEvent = mainServ.findEventById(event_id);
		
		// ADD THE ATTENDEE OBJECT TO eventAttendee List
		oneEvent.getAttendees().add(oneAttendee);
		
		// SAVE NEW EVENT TO DB
		mainServ.saveEvent(oneEvent);
		return "redirect:/events";
	}
	
	
	// CANCEL EVENT THAT USER HAS JOINED
	@GetMapping("/events/{id}/cancel")
	public String cancelEvent(
		@PathVariable("id") Long event_id,
		HttpSession session, RedirectAttributes flashMessages) {
		// CHECK IF USER LOGINS, IF NOT, RETURN REGISTER/LOGIN PAGE
		if (session.getAttribute("user_id") == null) {
    		flashMessages.addFlashAttribute("error", "YOU HAVE TO LOGIN FIRST");
			return "redirect:/";
		}
		
		User oneAttendee = mainServ.findUserById((Long) session.getAttribute("user_id"));
		Event oneEvent = mainServ.findEventById(event_id);
		
		// REMOVE THE ATTENDEE OBJECT FROM eventAttendee List
		oneEvent.getAttendees().remove(oneAttendee);
		
		// SAVE NEW EVENT TO DB
		mainServ.saveEvent(oneEvent);		
			
		return "redirect:/events";
	}
}
