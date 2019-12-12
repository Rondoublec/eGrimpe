package fr.rbo.service;

import fr.rbo.model.User;

/**
 * gestion des comptes utilisateurs
 */
public interface UserServiceInterface {
	/**
	 *
	 * Retourne les informations du compte d'un utilisateur correspondant au mail
	 * @param mailUser
	 * @return User
	 */
	public User findUserByEmail(String mailUser);

	/**
	 *
	 * Sauvegarde des informations du compte utilisateur
	 * @param user
	 */
	public void saveUser(User user);

}
