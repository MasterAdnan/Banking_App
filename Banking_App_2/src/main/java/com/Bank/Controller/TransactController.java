package com.Bank.Controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Bank.Entity.User;
import com.Bank.Repo.Transact_Repo;
import com.Bank.Repo.account_repo;
import com.Bank.Repo.payment_repo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/transact")
public class TransactController {
	
	
	@Autowired
	private account_repo accountRepository;
	
	@Autowired
	private payment_repo paymentRepository;
	
	@Autowired
	private Transact_Repo transactRepository;
	
	User user;
	double currentBalance;
	double newBalance;
	
	// Start of Deposit
	
	@PostMapping("/deposit")
	public String deposit(@RequestParam("deposit_amount") String deposit_amount,
						  @RequestParam("account_name") String account_id,
						  HttpSession session,
						  RedirectAttributes redirectAttributes) {
		
		
		//todo: check for empty strings:
		if(deposit_amount.isEmpty() || account_id.isEmpty()) {
			redirectAttributes.addFlashAttribute("error", "Deposit amount or account depositing cannot be empty");
			return "redirect:/dashboard";
		}
		
		//todo get logged in user:
		User user = (User)session.getAttribute("user");
		
		//todo get current account balance
		int acc_id = Integer.parseInt(account_id);
		
		double depositAmountValue = Double.parseDouble(deposit_amount);
		
		
		//TODO : CHECK IF DEPOSIT AMOUNT IS 0(zero):
		if(depositAmountValue == 0) {
			redirectAttributes.addFlashAttribute("error", "Deposit amount cannot be of 0 value ");
			return "redirect:/dashboard";
		}
		
		// TODO Update Balance:
		currentBalance = accountRepository.getAccountBalance(user.getUser_id(), acc_id);
		newBalance = currentBalance + depositAmountValue;

		// UPDATE ACCOUNT :
		accountRepository.ChangeAccountBalanceById(newBalance, acc_id);
		
		// TRANSACTREPOSITORY section content adding in update account :
		// LOG SUCCESSFUL TRANSACTION : 
		LocalDateTime currentDateTime = LocalDateTime.now();
		transactRepository.logTransaction(acc_id, "deposit", depositAmountValue, "online", "success", "Deposit Transaction Successfully", currentDateTime);
		
		redirectAttributes.addFlashAttribute("success", "AMOUNT DEPOSITED SUCCESSFULLY");
		return "redirect:/dashboard";
	}
	
	// End of Deposit
	
	
	@PostMapping("/transfer")
	public String transfer(@RequestParam("transfer_from") String transfer_from,
				          @RequestParam("transfer_to") String transfer_to,
				          @RequestParam("transfer_amount") String transfer_amount,
				          HttpSession session,
				          RedirectAttributes redirectAttributes) 
	{
		
		
		// TODO: Check for empty fields
		if(transfer_from.isEmpty() || transfer_to.isEmpty() || transfer_amount.isEmpty()) {
			redirectAttributes.addFlashAttribute("error", "The Account Transferring from and to along with amount cannot be empty");
			return "redirect:/dashboard";
		}
		
		// TODO: Convert Variables
		int transferFromId = Integer.parseInt(transfer_from);
		int transferToId = Integer.parseInt(transfer_to);
		double transferAmount = Double.parseDouble(transfer_amount);
		
		// TODO: Check if transferring into the same Account
		if(transferFromId == transferToId) {
			redirectAttributes.addFlashAttribute("error", "Cannot transfer into the same amount, Please select the appropriate amount to perform transfer");
			return "redirect:/dashboard";
		}
		
		// TODO: Check for 0 values
		if(transferAmount == 0) {
			redirectAttributes.addFlashAttribute("error", "Cannot transfer an amount of 0 value, please enter a value greater than 0");
			return "redirect:/dashboard";
		}
		
		// TODO: Get logged in user
		user = (User)session.getAttribute("user");
		
		// TODO: Get current Balance
		double currentBalanceofAccountTransferringFrom = accountRepository.getAccountBalance(user.getUser_id(), transferFromId);
		
		// CHECK IF TRANSFER AMOUNT IS MORE THAN CURRENT BALANCE
		if(currentBalanceofAccountTransferringFrom < transferAmount) {
			String errorMessage = "You have insufficient funds to perform this transfer!";
			LocalDateTime currentDateTime3 = LocalDateTime.now();
			// Log Failed Transaction
			transactRepository.logTransaction(transferFromId, "transfer", transferAmount, "online", "failed", "insuffient Funds", currentDateTime3);
			redirectAttributes.addFlashAttribute("error", errorMessage);
			return "redirect:/dashboard";
		}
		
		double currentBalanceofAccountTransferringTo = accountRepository.getAccountBalance(user.getUser_id(), transferToId);
		
		// TODO: Set New Balance
		double newBalanceofAccountTransferringFrom = currentBalanceofAccountTransferringFrom - transferAmount;
		double newBalanceofAccountTransferringTo = currentBalanceofAccountTransferringTo + transferAmount;
		
		// Changed Balance of the account Transferring from:
		accountRepository.ChangeAccountBalanceById(newBalanceofAccountTransferringFrom, transferFromId);
		
		// Changed Balance of the account Transferring to:
		accountRepository.ChangeAccountBalanceById(newBalanceofAccountTransferringTo, transferToId);
		LocalDateTime currentDateTime4 = LocalDateTime.now();
		// Log Successful Transaction:
		transactRepository.logTransaction(transferFromId, "Transfer", transferAmount, "online", "success", "Transfer Transaction Successful!", currentDateTime4);
		
		redirectAttributes.addFlashAttribute("success", "Amount transfer Successfully");
		return "redirect:/dashboard";
	
	}
	// END OF TRANSFER METHOD
	
	
	@PostMapping("/withdraw")
	public String withdraw(@RequestParam("withdrawal_amount") String withdrawal_amount,
						   @RequestParam("account_id") String account_id,
						   HttpSession session,
						   RedirectAttributes redirectAttributes
			) {
		
		String successMessage;
		String errorMessage;
		
		// TODO : Check for Empty Values
		if(withdrawal_amount.isEmpty() == account_id.isEmpty()) {
			errorMessage = "Withdrawal Amount and amount withdrawing from cannot be empty!";
			redirectAttributes.addFlashAttribute("error", errorMessage);
			return "redirect:/dashboard";
		}
		
		// TODO : Convert Variables
		double withdrawalAmount = Double.parseDouble(withdrawal_amount);
		int accountId = Integer.parseInt(account_id);
		
		// TODO : Check for 0 values
		if(withdrawalAmount == 0) {
			errorMessage = "Withdrawal Amount cannot be of 0 value! Please enter a value greater than 0";
			redirectAttributes.addFlashAttribute("error", errorMessage);
			return "redirect:/dashboard";
		}
		
		// TODO : Get Logged In User
		user = (User) session.getAttribute("User");
		
		// TODO : Get current balance 
		currentBalance = accountRepository.getAccountBalance(user.getUser_id(), accountId);
		
		// CHECK IF TRANSFER AMOUNT IS MORE THAN CURRENT BALANCE
		if(currentBalance < withdrawalAmount) {
			errorMessage = "You have insufficient funds to perform this transfer!";
			LocalDateTime currentDateTime6 = LocalDateTime.now();
			// Log Failed Transaction
			transactRepository.logTransaction(accountId, "Withdrawal", withdrawalAmount, "online", "failed", "insuffient Funds", currentDateTime6);
			redirectAttributes.addFlashAttribute("error", errorMessage);
			return "redirect:/dashboard";
		}
		
		
		// TODO : Set new Balance
		newBalance = currentBalance - withdrawalAmount;
		
		// TODO : Update Account Balance
		accountRepository.ChangeAccountBalanceById(newBalance, accountId);
		
		// LOG SUCCESSFUL TRANSACTION :
		LocalDateTime currentDateTime5 = LocalDateTime.now();
		transactRepository.logTransaction(accountId, "Withdrawal", withdrawalAmount, "online", "success", "Withdrawal Transaction Successfully", currentDateTime5);
		
		successMessage = "Withdrawal Successful";
		redirectAttributes.addFlashAttribute("error", successMessage);
		return "redirect:/dashboard";
		
	}
	
	// End of Withdrawal
	
	
	@PostMapping("/payment")
	public String payment(@RequestParam("benefieciary") String benefieciary,
						  @RequestParam("account_number") String account_number,
						  @RequestParam("account_id") String account_id,
						  @RequestParam("reference") String reference,
						  @RequestParam("payment_amount") String payment_amount,
					      HttpSession session,
					      RedirectAttributes redirectAttributes) {
		
		String successMessage;
		String errorMessage;
		
		// TODO : Check For Empty Values
		if(benefieciary.isEmpty() || account_number.isEmpty() || account_id.isEmpty() || payment_amount.isEmpty()) {
			errorMessage = "Benefieciary , Account Paying From and Payment AmountCannot be Empty! ";
			redirectAttributes.addFlashAttribute("error", errorMessage);
			return "redirect:/dashboard";
		}
		
		// TODO : Convert Variables
		int accountID = Integer.parseInt(account_id);
		double paymentAmount = Double.parseDouble(payment_amount);
		
		// TODO : Check For 0 Values
		if(paymentAmount == 0) {
			errorMessage = "Payment Amount Cannot be of 0 value, Please enter a value greater than 0 ";
			redirectAttributes.addFlashAttribute("error", errorMessage);
			return "redirect:/dashboard";
		}
		
		// TODO : Get Looged in User
		user = (User) session.getAttribute("user");
		
		// TODO : Get Current Balance
		currentBalance = accountRepository.getAccountBalance(user.getUser_id(), accountID);
		 	
		// TODO : Check If Payment Amount is More Than Current Balance
		if(currentBalance < paymentAmount) {
			errorMessage = "You have insufficient funds to perform this payment ";
			String reasonCode = "Could not Processed Payment due to insuffient funds!";
			LocalDateTime currentDateTime2 = LocalDateTime.now();
			paymentRepository.makePayment(accountID,  benefieciary, account_number, paymentAmount, reference, "failed", reasonCode, currentDateTime2);
			
			
			LocalDateTime currentDateTime6 = LocalDateTime.now();
			// Log Failed Transaction
			transactRepository.logTransaction(accountID, "Payment", paymentAmount, "online", "failed", "insuffient Funds", currentDateTime6);
			
			redirectAttributes.addFlashAttribute("error", errorMessage);
			return "redirect:/dashboard";
		}
		
		// TODO : Set NEW BALANCE FOR ACCOUNT PAYING FORM
		 newBalance = currentBalance - paymentAmount;
		 
		 // TODO : Make Payment 
		 String reasonCode = "Payment Processed Successfully";
		 LocalDateTime currentDateTime2 = LocalDateTime.now();
		 paymentRepository.makePayment(accountID,  benefieciary, account_number, paymentAmount, reference, "success", reasonCode, currentDateTime2);
		
		 // TODO : UPDATE ACCOUNT PAYING FROM :
		 accountRepository.ChangeAccountBalanceById(newBalance, accountID);
		 
		// LOG SUCCESSFUL TRANSACTION :
		LocalDateTime currentDateTime5 = LocalDateTime.now();
		transactRepository.logTransaction(accountID, "Payment", paymentAmount, "online", "success", "Payment Transaction Successfully", currentDateTime5);
		 
		 successMessage = reasonCode;
		 redirectAttributes.addFlashAttribute("success", successMessage);
		 return "redirect:/dashboard";
		 
	}
	
	// End of Payment Method
	
	
}








