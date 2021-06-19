package controllers;

import dto.User;
import ninja.Result;

public interface UsersController {

	Result createUser(User user);
	Result getUserById(Long id);
	Result deleteUserById(Long id);
	Result updateUser(User userDto);
	
}
