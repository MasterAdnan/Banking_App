package com.Bank.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bank.Entity.User;
import com.Bank.Repo.UserRepo;

@Service
public class userServiceImpl implements userService {

	@Autowired
	private UserRepo urepo;
	
	@Override
	public User saveUser(User user) {
		return this.urepo.save(user);
	}

	@Override
	public List<User> getAllUser() {
		return urepo.findAll();
	}

	@Override
	public User getUserById(long id) {
		Optional<User> optional = urepo.findById(id);
		User user = null;
		if(optional.isPresent()) {
			user = optional.get();
		} else {
			throw new RuntimeException("User not found for id :: " + id);
		}
		return user;
	}

	@Override
	public void deleteUserById(long id) {
		this.urepo.deleteById(id);
	}
	
}
