package com.example.demo;

import java.util.Optional;
import java.util.logging.Logger;
import java.util.List;
import java.security.Principal;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

// import antlr.collections.List;

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
		// System.out.println("okk");
		
		repo.save(user);
		
		return "register_success";
	}


	@RequestMapping(value = "/list_users", method = RequestMethod.GET)
  	@ResponseBody
  	public ModelAndView currentUserName(Principal principal) {
		String email = principal.getName();
		User newUser = repo.findByEmail(email);
		//long id = newUser.getId();
		// Optional<User> listUsers = repo.findById(id); 
        //  model.addAttribute("listUsers", listUsers);
        //  return "users";
		ModelAndView modelAndView = new ModelAndView("users");
		ModelAndView mav = modelAndView;
    	//Optional<User> user = repo.findById(id);
	
		mav.addObject("user", newUser);
		return mav;
  	}
	// @GetMapping("/list_users")
    // public String listAllUsers(Model model) {
    //     List<User> listUsers = repo.findAll();
    //     model.addAttribute("listUsers", listUsers);
    //     return "users";
    // }

	// @GetMapping("/book")
	// public String viewBookingPage(@ModelAttribute User user, Model model) {

	// 	model.addAttribute("user", user);


	// 	return "homepage";
	// }

	// @GetMapping("/ride")
	// public String processRide(@ModelAttribute User user, Model model) {

	// 	// model.addAttribute("user", user);
		
	// 	// System.out.println("u");
	// 	// user.getFirstName();
	// 	// repo.save(user);
	// 	// User currentUser = repo.findById(user.getId());
		
		
		

	// 	return "ride";
	// }

	@RequestMapping("/edit/{id}")
	public ModelAndView showEditProductPage(@PathVariable(name = "id") Long id) {

    ModelAndView modelAndView = new ModelAndView("homepage");
	ModelAndView mav = modelAndView;
    Optional<User> user = repo.findById(id);
	
	mav.addObject("user", user);
	return mav;
    
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveProduct(@ModelAttribute("user") User user) {
		//repo.save(user);
		long id = user.getId();
		Optional<User> newUser = repo.findById(id);

		newUser.get().setDestination(user.getDestination());
		newUser.get().setVehicleType(user.getVehicleType());

		repo.save(newUser.get());

		ModelAndView modelAndView = new ModelAndView("ride");
		ModelAndView mav = modelAndView;
    	//Optional<User> user = repo.findById(id);
	
		mav.addObject("user", newUser);
		return mav;
		//return "ride";
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
