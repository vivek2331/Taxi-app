package com.example.demo;

import java.util.Optional;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class AppController {
	
	@Autowired
	private UserRepository repo;
	
	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}
 
	@GetMapping("/register")
	public String showSignUpForm(Model model){
		model.addAttribute("user", new User());

		return "signup_form";
	}
	
	@PostMapping("/process_register")
	public String processRegistration(@Validated User user, BindingResult result, Model model) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		System.out.println("okk");
		
		repo.save(user);
		
		return "register_success";
	}



	@GetMapping("/list_users")
	public String viewUsersList(@ModelAttribute User user, Model model){
		/*List<User> listUsers = repo.findAll();
		model.addAttribute("listUsers",listUsers); */

		return "users";
	}

	@GetMapping("/book")
	public String viewBookingPage(@ModelAttribute User user, Model model) {

		model.addAttribute("user", user);


		return "homepage";
	}

	@GetMapping("/ride")
	public String processRide(@ModelAttribute User user, Model model) {

		model.addAttribute("user", user);


		return "Ride";
	}

	// @PutMapping("/ride")
	// public String processRide(User user) {


	// 	System.out.println("hello");


	// 	repo.save(user);

	// 	return "register_success";
	// }

	// @PutMapping("/update/{id}")
	// public String updateUser(@PathVariable("id") long id,User user) {


	// 	repo.save(user);
	// 	return "register_success";
	// }
//	@PostMapping("/update/{id}")
//	public String viewRide(@PathVariable("id") long id,@ModelAttribute User user) {
//		Logger logger = LoggerFactory.getLogger(vi.class);
//		Optional<User> user1 = repo.findById(id);
//		logger.info("An INFO Message");
//


//		repo.save(user);
//		return "homepage" ;

//	}


}
