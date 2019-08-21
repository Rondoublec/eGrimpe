package fr.rbo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.rbo.repository.UserRepository;

@SpringBootApplication
public class EGrimpe implements CommandLineRunner{

	private static final Logger log = LoggerFactory.getLogger(EGrimpe.class);

	@Autowired
	private UserRepository userRepository;
	
	/*@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;*/

	public static void main(String[] args) {
		SpringApplication.run(EGrimpe.class, args);
	}

	public void run(String... args) throws Exception {
		log.info("args : " +  args);
		/*
		User user = new User();
		user.setName("ADMIN");
		user.setLastName("ADMIN");
		user.setEmail("admin@cnss.ne");
		//BCryptPasswordEncoder bCryptPasswordEncoderLocal = new BCryptPasswordEncoder();
		//String encodepwd = bCryptPasswordEncoderLocal.encode("admin2017");
		//user.setPassword(encodepwd);
		//System.out.println("admin2017  encoder = " +encodepwd);
		user.setPassword("$2a$10$fE7BKQcc.tesDzaptjL8luXZB6MV5rvUJ13ub5aVYKqnoPmMqYd8m");
		user.setActive(true);
		//Role
		HashSet<Role> roles = new HashSet<Role>();
		Role role = new Role();
		role.setRole("ADMIN");
		roles.add(role);
		user.setRoles(roles);
		userRepository.save(user);
		 */
	}
}
