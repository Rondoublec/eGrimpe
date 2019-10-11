package fr.rbo.service;

import fr.rbo.model.User;

public interface UserServiceInterface {
	public User findUserByEmail(String mailUser);
	public void saveUser(User user);

}
