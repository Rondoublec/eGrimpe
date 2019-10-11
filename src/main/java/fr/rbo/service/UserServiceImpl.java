package fr.rbo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fr.rbo.model.Role;
import fr.rbo.model.User;
import fr.rbo.repository.RoleRepository;
import fr.rbo.repository.UserRepository;

@Service("UserServiceInterface")
public class UserServiceImpl implements UserServiceInterface, UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	
	public User findUserByEmail(String mailUser) {
		return userRepository.findByEmail(mailUser);
	}

	
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(true);
		HashSet<Role> roles = new HashSet<Role>();
		Role role = new Role();
		role.setRole("USER");
		roles.add(role);
		user.setRoles(roles);
		userRepository.save(user);
	}
	
	/*
	 * INSERT INTO `role` VALUES (1,'ADMIN');
	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}
	*/
	
	
	@Transactional
	public UserDetails loadUserByUsername(String mailUser) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(mailUser);
		List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
		return buildUserForAuthentication(user, authorities);
	}

	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		for (Role role : userRoles) {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(roles);
		return grantedAuthorities;
	}

	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isActive(), true, true, true, authorities);
	}
}
