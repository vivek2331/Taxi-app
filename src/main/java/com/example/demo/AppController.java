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
import org.springframework.http.ResponseEntity;

// import antlr.collections.List;

@Controller
public class AppController {
	
	@Autowired
	private UserRepository repo;

	@Autowired
	private RideDetailsRepository rideRepo;

	@Autowired
	private DriverRepository driverRepo;
	
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
		// if(repo.existsByEmail(user.getEmail())){
		// 	return ResponseEntity
		// 			.badRequest()
		// 			.body(new MessageResponse("Error: Email is already taken"));
		// }
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		// System.out.println("okk");

		//User user1 = repo.findByEmail(user.getEmail());

		 if(repo.existsByEmail(user.getEmail())){
		 	throw new RuntimeException("This Email has already taken");
		 }
		
		repo.save(user);
	
		return "register_success";
		
		 
		// return ResponseEntity.ok(new MessageResponse("User registered successfully"))
	}

	@PostMapping("/newRide")
	public String bookRide(@Validated RideDetails rideDetails, BindingResult result, Model model, Principal principal){
		String email = principal.getName();
		User user = repo.findByEmail(email);

		String pickupLoc = rideDetails.getPickupLoc();
		String destination = rideDetails.getDestination();
		String vehicleType = rideDetails.getVehicleType();
		long price = 100;
		if(pickupLoc != "Chennai"){
			price++;
		}
		

		RideDetails newRideDetails = new RideDetails(pickupLoc, destination, vehicleType, price, user);

		rideRepo.save(newRideDetails);

		model.addAttribute("rideDetails", newRideDetails);

		return "ride";
	}

// 	@RequestMapping("/process_register")
// public String showModel(@ModelAttribute User user, Model model){


//     User existedUsername = repo.findByEmail(user.getEmail());
//     if(existedUsername != null){
//         model.addAttribute("existedUsername",existedUsername);
//     }

//     return "registrationstatus";

// }

	// @RequestMapping(value = "/user", method = RequestMethod.GET)
  	// @ResponseBody
  	// public ModelAndView currentUserName(Principal principal) {
	// 	String email = principal.getName();
	// 	User newUser = repo.findByEmail(email);
	// 	List<RideDetails> rideDetails = rideRepo.findByUserId(newUser.getId());
	// 	// rideDetails.setpickupLoc("richmond");
	// 	// rideDetails.setDestination("bangalore");
	// 	// rideDetails.setVehicleType("auto");
	// 	//rideDetails.setUser(newUser);

	// 	//rideRepo.save(rideDetails);
	// 	//long id = newUser.getId();
	// 	// Optional<User> listUsers = repo.findById(id); 
    //     //  model.addAttribute("listUsers", listUsers);
    //     //  return "users";
	// 	ModelAndView modelAndView = new ModelAndView("users");
	// 	ModelAndView mav = modelAndView;
    // 	//Optional<User> user = repo.findById(id);
	// 	// newUser.setpickupLoc(null);
	// 	// newUser.setDestination(null);
	// 	// newUser.setVehicleType(null);
	// 	mav.addObject("rideDetails", rideDetails);
	// 	return mav;
  	// }



	@GetMapping("/user")
    public String listAllUsers(Model model) {
		// String email = principal.getName();
		// User user = repo.findByEmail(email);
		// List<RideDetails> rideDetails = rideRepo.findByUserId(user.getId());
        //List<RideDetails> listRides = rideRepo.findAll();
        //model.addAttribute("listRides", rideDetails);
		model.addAttribute("rideDetails", new RideDetails());
        return "users";
    }

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

	@RequestMapping("/edit")
	public ModelAndView showEditProductPage(Principal principal) {
		String email = principal.getName();
		User user = repo.findByEmail(email);

    ModelAndView modelAndView = new ModelAndView("homepage");
	ModelAndView mav = modelAndView;
    //Optional<User> user = repo.findById(id);
	//user.get().setPassword(null);
	
	mav.addObject("user", user);
	return mav;
    
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveProduct(@ModelAttribute("user") User user) {
		//repo.save(user);
		long id = user.getId();
		Optional<User> newUser = repo.findById(id);

		// newUser.get().setpickupLoc(user.getpickupLoc());
		// newUser.get().setDestination(user.getDestination());
		// newUser.get().setVehicleType(user.getVehicleType());

		repo.save(newUser.get());

		ModelAndView modelAndView = new ModelAndView("ride");
		ModelAndView mav = modelAndView;
    	//Optional<User> user = repo.findById(id);
	
		mav.addObject("user", newUser);
		return mav;
		//return "ride";
	}
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView editProfile(@ModelAttribute("user") User user) {
		//repo.save(user);
		long id = user.getId();
		Optional<User> newUser = repo.findById(id);

		newUser.get().setFirstName(user.getFirstName());
		newUser.get().setLastName(user.getLastName());

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		newUser.get().setPassword(encodedPassword);
		// System.out.println("okk");

		repo.save(newUser.get());

		ModelAndView modelAndView = new ModelAndView("update_success");
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



	@RequestMapping("/history")
	public String bookingHistory(Principal principal, Model model) {
		String email = principal.getName();
		User user = repo.findByEmail(email);

		List<RideDetails> rideDetails = rideRepo.findByUserId(user.getId());
        
        model.addAttribute("listRides", rideDetails);

	
		return "booking history";

	}

	@RequestMapping("/driverDetails")
	public String driverDetails(Model model){

		List<Driver> driver = driverRepo.findAll();

		model.addAttribute("listDrivers", driver);

		return "driverslist";
	}
}
