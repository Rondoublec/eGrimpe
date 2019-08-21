package fr.rbo.service;

import fr.rbo.model.User;

public interface UserServiceInterface {
	public User findUserByEmail(String email);
	public void saveUser(User user);

}
