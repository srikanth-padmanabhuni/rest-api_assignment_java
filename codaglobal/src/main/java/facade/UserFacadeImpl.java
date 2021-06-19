package facade;

import javax.persistence.EntityManager;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import dto.Mapper;
import dto.User;
import entities.Users;
import exceptions.InvalidDetailsException;
import ninja.utils.NinjaProperties;

public class UserFacadeImpl implements UserFacade{
	private static Logger log = LogManager.getLogger(UserFacadeImpl.class);
	@Inject 
	Provider<EntityManager> entityManagerProvider;
	@Inject
	private NinjaProperties ninjaProperties;
	
	@Override
	public Long createUser(User user) throws InvalidDetailsException {
		log.info("createUser is called");
		checkUserDetails(user);
		checkIsUserNameExists(user.getuName());
		return saveUserToDb(user);
	}
	
	private void checkIsUserNameExists(String userName) throws InvalidDetailsException {
		log.info("checkIsUserNameExists is called userName : "+userName);
		Integer count = checkForUniqueUserName(userName);
		if (count > 0) {
			throw new InvalidDetailsException("Username is already in use. Please select any other username");
		}
	}
	
	@Transactional
	public Long saveUserToDb(User user) {
		EntityManager entityManager = entityManagerProvider.get();
		log.info("saveUserToDb is called");
		Users users = new Users();
		users.setUserName(user.getuName());
		users.setBio(user.getuBio());
		users.setProfilePicUrl(user.getuProfilePic());
		String encryptedPassword = Mapper.encryptAES(ninjaProperties, user.getPassword());
		users.setPassword(encryptedPassword);
		entityManager.persist(users);
		return users.getId();
	}

	private void checkUserDetails(User user) throws InvalidDetailsException {
		log.info("checkUserDetails is called");
		if(user == null) {
			throw new InvalidDetailsException("Please provide valid user details to create the user");
		}
		
		String userName = user.getuName();
		if(userName == null || userName.trim().length() == 0) {
			throw new InvalidDetailsException("Please provide valid username to create the user");
		}
		
		String bio = user.getuBio();
		if(bio == null || bio.trim().length() == 0) {
			throw new InvalidDetailsException("Please provide valid bio to create the user");
		}
	}
	
	@Override
	public User getUserById(Long id) throws InvalidDetailsException {
		log.info("getUserById is called with id: "+id);
		if(id == null) {
			throw new InvalidDetailsException("Please provide valid user id to get user details");
		}
		Users user = getUserFromDbById(id);
		if(user == null) {
			throw new InvalidDetailsException("No user found with the userId : "+id);
		}
		
		User userDto = Mapper.mapUsersToUsersDto(user);
		return userDto;
	}
	
	@Override
	@Transactional
	public Users getUserFromDbById(Long id) {
		log.info("getUserFromDbById is called with id : "+id);
		EntityManager entityManager = entityManagerProvider.get();
		Users user = entityManager.find(Users.class, id);
		return user;
	}
	
	@Override
	public User updateUserById(User user) throws InvalidDetailsException {
		log.info("updateUserById is called");
		if(user == null) {
			throw new InvalidDetailsException("Please provide valid user details to update user");
		}
		checkUserDetails(user);
		Users userEntity = updateUser(user);
		User userDto = Mapper.mapUsersToUsersDto(userEntity);
		return userDto;
	}
	
	@Transactional
	public Users updateUser(User user) throws InvalidDetailsException {
		log.info("getUserFromDbToUpdateUser is called with id : "+user.getuId());
		EntityManager entityManager = entityManagerProvider.get();
		Users userEntity = entityManager.find(Users.class, user.getuId());
		if(userEntity != null) {
			if(!userEntity.getUserName().equals(user.getuName())) {
				checkIsUserNameExists(user.getuName());
			}
			userEntity.setUserName(user.getuName());
			userEntity.setBio(user.getuBio());
			userEntity.setProfilePicUrl(user.getuProfilePic());
			entityManager.persist(userEntity);
		}
		return userEntity;
	}
	
	@Transactional
	public Integer checkForUniqueUserName(String userName) {
		log.info("checkForUniqueUserName is called with userName : "+userName);
		EntityManager entityManager = entityManagerProvider.get();
		Long count = entityManager.createNamedQuery("Users.findUserNameCount", Long.class)
									.setParameter("uName", userName)
									.getSingleResult();
		return Integer.parseInt(count.toString());
	}
	
	@Override
	@Transactional
	public void deleteUser(Long id) throws InvalidDetailsException {
		log.info("deleteUser is called with id : "+id);
		if(id == null) {
			throw new InvalidDetailsException("Please provide valid user id to delete user");
		}
		EntityManager entityManager = entityManagerProvider.get();
		Users user = entityManager.find(Users.class, id);
		if(user == null) {
			throw new InvalidDetailsException("No user found with the given id to delete the user");
		}
		entityManager.remove(user);
	}
}
