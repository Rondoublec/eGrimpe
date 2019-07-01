package fr.rbo.service;

import fr.rbo.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
