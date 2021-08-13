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
public class UserRepositoryTests {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private UserRepository repo;
	
	@Test
	public void testCreateUser() {
		User user=new User();
		user.setFirstName("joe");
		user.setLastName("raymond");
		user.setEmail("joeraymond@virtusa.com");
		user.setMobileno("9629582956");
		user.setPassword("$2a$12$f8rUM44dvhObjd2DYmGOzu3bAfrqa8Und4q8KHqlTvsxa5VrAg/qS");
		user.setKeyword("name");
		
		User savedUser=repo.save(user);
		User existUser=entityManager.find(User.class, savedUser.getId());
		assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
	}
	@Test
	public void testFindUserByEmail() {
		String email="joeraymond@virtusa.com";
		User user=repo.findByEmail(email);
		assertThat(user).isNotNull();
	}
}
