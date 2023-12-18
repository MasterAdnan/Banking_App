package com.Bank.Service;

import java.util.List;

import com.Bank.Entity.User;

public interface userService {
	
	List<User> getAllUser();
	
	public User saveUser(User user);
	
	User getUserById(long id);
	
	void deleteUserById(long id);
	
}
