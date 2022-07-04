package com.example.demo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User u WHERE u.email = ?1")
	User findByEmail(String email);

	//Optional<User> findByEmail(String email);

	
	boolean existsByEmail(String email);

	// User findOne(int i);

}
