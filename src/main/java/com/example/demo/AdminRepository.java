package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin, Long> {
	
	@Query("SELECT a FROM Admin a WHERE a.email=?1")
	Admin findByemailadmin(String email);
	
	@Modifying
	@Query("UPDATE Admin a SET a.password=?1")
	void setAdmininfo(String password);
}
