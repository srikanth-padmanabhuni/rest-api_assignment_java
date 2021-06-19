package controllers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.inject.Inject;

import dto.User;
import exceptions.InvalidDetailsException;
import facade.UserFacade;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;

public class UsersControllerImpl implements UsersController {

	private static Logger log = LogManager.getLogger(UsersControllerImpl.class);
	
	@Inject
	private UserFacade userFacade;

	@Override
	public Result createUser(User user) {
		log.debug("createUser is called");
		try {
			Long userId = userFacade.createUser(user);
			return Results.status(200).json().render("User has been created successfully with userId : "+userId);
		} catch(InvalidDetailsException e) {
			e.printStackTrace();
			return Results.status(500).json().render(e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
			return Results.status(500).json().render("An error has been occurred please try again later!!!");
		}
	}

	@Override
	public Result getUserById(@PathParam("userId") Long id) {
		log.debug("getUserById is called");
		try {
			User user = userFacade.getUserById(id);
			return Results.status(200).json().render(user);
		} catch(InvalidDetailsException e) {
			e.printStackTrace();
			return Results.status(500).json().render(e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
			return Results.status(500).json().render("An error has been occurred please try again later!!!");
		}
	}

	@Override
	public Result updateUser(User userDto) {
		log.debug("updateUser is called");
		try {
			userDto.toString();
			User user = userFacade.updateUserById(userDto);
			return Results.status(200).json().render(user);
		} catch(InvalidDetailsException e) {
			e.printStackTrace();
			return Results.status(500).json().render(e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
			return Results.status(500).json().render("An error has been occurred please try again later!!!");
		}
	}

	@Override
	public Result deleteUserById(@PathParam("userId") Long id) {
		log.debug("deleteUserById is called");
		try {
			userFacade.deleteUser(id);
			return Results.status(200).json().render("User has been deleted successfully!!!");
		} catch(InvalidDetailsException e) {
			e.printStackTrace();
			return Results.status(500).json().render(e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
			return Results.status(500).json().render("An error has been occurred please try again later!!!");
		}
	}
	
}
