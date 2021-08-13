package com.example.demo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

		@Query("SELECT u FROM User u WHERE u.email=?1")
		User findByEmail(String email);
		
		@Modifying
		@Query("UPDATE User u SET u.password=?1")
		void setUserinfo(String password);
}
