package fr.rbo.archijee.service;

import fr.rbo.archijee.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
