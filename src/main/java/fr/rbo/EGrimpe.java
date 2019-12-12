package fr.rbo;

import fr.rbo.model.Role;
import fr.rbo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.rbo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;

@SpringBootApplication
public class EGrimpe implements CommandLineRunner{

	private static final Logger log = LoggerFactory.getLogger(EGrimpe.class);
	private static final String CREATE_CPT = "CREATE_CPT";
	private static final String CREATE_MEMBRE = "CREATE_MEMBRE";
	private static final String CREATE_ADMIN = "CREATE_ADMIN";

	@Qualifier("userRepository")
	@Autowired
	private UserRepository userRepository;
	
	/*@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;*/

	public static void main(String[] args) {
		SpringApplication.run(EGrimpe.class, args);
	}

	public void run(String... args) throws Exception {
		if (args != null){
			log.info(" argument(s) : ");
			for (String arg:args) {
				log.info(arg);
				// initialisation de comptes avec privilèges
				if (CREATE_CPT.equals(arg) || CREATE_MEMBRE.equals(arg)) {
					initCPT("pm","nm", "m0@a.a", "MEMBRE");
				}
				if (CREATE_CPT.equals(arg) || CREATE_ADMIN.equals(arg)) {
					initCPT("pa","na", "a0@a.a", "ADMIN");
				}
			}
		}
		log.debug("niveau de debogage");
		log.info("niveau d'information");
		log.warn("niveau d'avertissement");
		log.error("niveau d'erreur");

	}

		private void initCPT(String p, String n, String m, String r){
			User user = new User();
			user.setName(p);
			user.setLastName(n);
			user.setEmail(m);
			BCryptPasswordEncoder bCryptPasswordEncoderLocal = new BCryptPasswordEncoder();
			user.setPassword(bCryptPasswordEncoderLocal.encode("12345"));
			user.setActive(true);
			//Role
			HashSet<Role> roles = new HashSet<Role>();
			Role role = new Role();
			role.setRole(r);
			roles.add(role);
			user.setRoles(roles);
			log.info("CREATE_CPT : " + p + " " + n + " " + m + " " + r);
			userRepository.save(user);
		}

}
