package com.Bank.Controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.Bank.Entity.Account;
import com.Bank.Entity.PaymentHistory;
import com.Bank.Entity.User;
import com.Bank.Repo.payment_history_repo;

import jakarta.mail.Session;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/app")
public class AppController {
	
	@Autowired
	private com.Bank.Repo.account_repo account_repo;
	
	@Autowired
	private payment_history_repo payment_history_repo;
	
	User user;
	
	@GetMapping("/dashboard")
	public ModelAndView getDashboard(HttpSession session) {
		ModelAndView getDashboardPage = new ModelAndView("dashboard");
		
		// Get the details of the logged in user:
		user = (User)session.getAttribute("user");
		
		// Get the accounts of the logged in user:
		List<Account> getUserAccounts = account_repo.getUserAccountsById(user.getUser_id());
		
		// Get Balance:
		BigDecimal totalAccountsBalance = account_repo.getTotalBalance(user.getUser_id());
		
		// Set Objects :
		getDashboardPage.addObject("userAccounts", getUserAccounts);
		getDashboardPage.addObject("totalBalance", totalAccountsBalance);
		
		return getDashboardPage;
	}
	
	@GetMapping("/payment_history")
	public ModelAndView getPaymentHistory(HttpSession session) {
		
		// Set View:
		ModelAndView getPaymentHistoryPage = new ModelAndView("paymentHistory");
		
		// Get Logged In User:\
		user = (User) session.getAttribute("user");
		
		// Get payment History / Records :
		List<PaymentHistory> userPaymentHistory = payment_history_repo.getPaymentRecordsById(user.getUser_id());
		
		getPaymentHistoryPage.addObject("payment_history", userPaymentHistory);
		
		return getPaymentHistoryPage;
		
		
		
		
	}
	
}
