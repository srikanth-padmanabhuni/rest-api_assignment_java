package facade;

import dto.User;
import entities.Users;
import exceptions.InvalidDetailsException;

public interface UserFacade {

	Long createUser(User user) throws InvalidDetailsException;
	
	User getUserById(Long id) throws InvalidDetailsException;
	
	User updateUserById(User user) throws InvalidDetailsException;
	
	void deleteUser(Long id) throws InvalidDetailsException;

	Users getUserFromDbById(Long id);
}
