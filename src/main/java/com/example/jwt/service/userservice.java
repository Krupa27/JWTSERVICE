package com.example.jwt.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.jwt.dao.User;
//import com.example.jwt.repository.userrepo;
//
//@Service
//public class userservice {
//	
//	@Autowired
//	userrepo rob;
//
//	public String saveUser(User ob) {
//		
//		rob.save(ob);
//		return "Registered";
//	}
//
//}
// In com.example.jwt.service.userservice.java
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder; // Import this
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.jwt.dao.User;
import com.example.jwt.repository.userrepo;

@Service
public class userservice implements UserDetailsService {

    @Autowired
    private userrepo userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder; // Autowire the PasswordEncoder
    
    public String saveHR(User user) {
        // Encode the password before saving!
    	System.out.println(!userRepository.existsByUsername(user.getUsername()));
    	if(!userRepository.existsByUsername(user.getUsername())) {
    		if(user.getRole().equalsIgnoreCase("HR")) {
    			user.setPassword(passwordEncoder.encode(user.getPassword()));
    			userRepository.save(user);
    			
    		}else {
    			return "Trainer cannot be registered";
    		}
    	}else {
    		return "HR already exists";
    	}
        return "HR Registerd successfully";
    }
    
    
    public String resetTrainerPassword(String username, String newPassword) {
        if (username == null || username.trim().isEmpty() || newPassword == null || newPassword.trim().isEmpty()) {
            return "Error: Username and new password must be provided.";
        }

        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // Encrypt new password before saving
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);

            return "Password reset successfully!";
        } else {
            return "Error: User with username " + username + " not found.";
        }
    }
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // ... (your existing logic to find user by username)
        User user = userRepository.findByUsername(username);
        System.out.println(user);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        // Return Spring Security's UserDetails.User (ensure password is already encoded in DB)
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
    
    public String encodePassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }
    
    public boolean matches(String plainPassword, String encodedPassword) {
        return passwordEncoder.matches(plainPassword, encodedPassword);
    }
    

	
	
	public String deleteTrainer( String id) {
		Optional<User> otf = Optional.ofNullable(userRepository.findByUsername(id));
		
		if(otf.isPresent()) {
			userRepository.deleteByUsername(id);
		  return id+" deleted Trainer Successfully";
		}
		return id+" not found";
	}


	public String save(User ob) {
		
		ob.setPassword(passwordEncoder.encode(ob.getPassword()));
		userRepository.save(ob);
		return "Registered";
	}
}