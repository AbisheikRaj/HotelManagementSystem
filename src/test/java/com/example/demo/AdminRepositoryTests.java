package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class AdminRepositoryTests {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private AdminRepository rep;
	
	@Test
	public void testCreateAdmin() {
		Admin admin=new Admin();
		admin.setEmail("joeraymond@virtusa.com");
	    admin.setPassword("joeraymond");
	    admin.setEmpId("C123456");
		
		Admin savedAdmin=rep.save(admin);
		Admin existAdmin=entityManager.find(Admin.class, savedAdmin.getId());
		assertThat(admin.getEmail()).isEqualTo(existAdmin.getEmail());
	}

}
