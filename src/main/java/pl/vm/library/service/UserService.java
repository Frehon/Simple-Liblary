package pl.vm.library.service;

import java.util.List;

import pl.vm.library.to.UserTo;

/**
 * The Service which contains business logic for User.
 */
public interface UserService {

	/**
	 * Returns all Users.
	 * 
	 * @return all Users
	 */
	public List<UserTo> findAll();

	/**
	 * Returns the User with the given ID.
	 * 
	 * @return the found User
	 */
	public UserTo findById(Long id);

	/**
	 * Creates a new Entity for the given object.
	 * 
	 * @param userTo
	 * @return the persisted User
	 */
	public UserTo create(UserTo userTo);

}