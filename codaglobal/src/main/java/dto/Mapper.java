package dto;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import entities.Feed;
import entities.Users;

public class Mapper {

	private static Logger log =  LogManager.getLogger(Mapper.class);
	
	public static User mapUsersToUsersDto(Users userEntity) {
		User userDto = new User();
		userDto.setuId(userEntity.getId());
		userDto.setuName(userEntity.getUserName());
		userDto.setuBio(userEntity.getBio());
		userDto.setuProfilePic(userEntity.getProfilePicUrl());
		return userDto;
	}
	
	public static dto.Feed  mapFeedToFeedDto(Feed feedEntity) {
		dto.Feed feedDto = new dto.Feed();
		feedDto.setFeedId(feedEntity.getId());
		feedDto.setFeedPostUrl(feedEntity.getPosturl());
		feedDto.setFeedDescription(feedEntity.getDescription());
		feedDto.setFeedPrivacy(feedEntity.isPrivacy());
		MinimalUserDetail minimalUserDetails = Mapper.mapUserToMinimalUserDetails(feedEntity.getUsers());
		feedDto.setUserDetails(minimalUserDetails);
		return feedDto;
	}
	
	public static MinimalUserDetail mapUserToMinimalUserDetails(Users userEntity) {
		MinimalUserDetail minimalUser = new MinimalUserDetail();
		minimalUser.setUserId(userEntity.getId());
		minimalUser.setUserName(userEntity.getUserName());
		minimalUser.setProfilePicUrl(userEntity.getProfilePicUrl());
		return minimalUser;
	}
}
