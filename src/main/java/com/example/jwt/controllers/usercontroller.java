package com.example.jwt.controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.dao.CohortDTO;
import com.example.jwt.dao.User;
import com.example.jwt.repository.userrepo;
import com.example.jwt.service.Jwtservice;
import com.example.jwt.service.userservice;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin("*")

public class usercontroller {
	
	@Autowired
	userservice sob;
	

	@Autowired
	userrepo rob;
	
	@Autowired
	Jwtservice jwob;
	
	@Autowired 
	AuthenticationManager authenticationManager;
	
	@GetMapping("/hello")
		public String fun() {
			return "hi";
		}
	
	
	@PostMapping("/login")
	public String login(@RequestBody User ob) {
		
		Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(ob.getUsername(),ob.getPassword()));
		
		if(auth.isAuthenticated()) {
			if(!rob.findByUsername(ob.getUsername()).getRole().equals(ob.getRole())){
//				System.out.println(rob.get);
				return "Failed";
			}
			String res = jwob.generateToken(ob.getUsername());
			System.out.println(res);
			return res;
		}
		
		return "Failed";
	}
	@PostMapping("/registerHR")
	public String RegisterHR(@RequestBody User ob) {
		return sob.saveHR(ob);
	}
	@PostMapping("/register")
	public String Register(@RequestBody User ob) {
		return sob.save(ob);
	}
	
	@PostMapping("/validatehr")
	public boolean validatehr(@RequestBody CohortDTO c) {
		
		if(rob.existsByUsername(c.getHr_id()) == true) {
			return true;
		}
		return false;
		
	}

	

	@Transactional
	@DeleteMapping("/deleteTrainer/{id}")
	public String deleteTrainer(@PathVariable String id) {
		System.out.println(sob.deleteTrainer(id));
		return sob.deleteTrainer(id);
	}
	
	
	
	
	
	
	@PostMapping("/generateHashPassword")
	public String generateHashedPassword(@RequestBody Map<String, String> payload) {
	    String password = payload.get("password");
	    return sob.encodePassword(password);
	}
	
	@PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String newPassword = payload.get("password");

        String result = sob.resetTrainerPassword(username, newPassword);
        
        if (result.startsWith("Error")) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }



}
