package com.example.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jwt.dao.User;

@Repository
public interface userrepo extends JpaRepository<User, Integer> {
	 	User findByUsername(String username);
	 	

	    boolean existsByUsername(String username);
	    
	    void deleteByUsername(String username);
	    
	    
}
