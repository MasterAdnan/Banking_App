package com.Bank.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Bank.Entity.User;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	com.Bank.Service.userService userv;
	
	@GetMapping("/")
	public String index() {
		return "index"; 
	}
	
	@GetMapping("/aboutus")
	public String aboutus() {
		return "aboutus";
	}
	
	@GetMapping("/contact")
	public String contact() {
		return "contact";
	}
	
	@GetMapping("/features")
	public String features() {
		return "features";
	}
	
	@GetMapping("/services")
	public String services() {
		return "services";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@PostMapping("saveUser")
	public String saveUser(User user,Model model) {
		this.userv.saveUser(user);
		
//		if(u!=null) {
//			session.setAttribute("msg", "Account 1");
//		}
//		else {
//			session.setAttribute("msg", "register Un Success-fully");
//		}
		
		return "redirect:/";
	}
	
	@GetMapping("/dashboard")
	public String dashboard() {
		return "dashboard";
	}
	
	@GetMapping("/account_profile")
	public String account_profile() {
		return "account_profile";
	}
	
}
