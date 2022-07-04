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
		
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		// System.out.println("okk");

		

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



	@GetMapping("/user")
    public String listAllUsers(Model model) {
		
		model.addAttribute("rideDetails", new RideDetails());
        return "users";
    }


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
