package dto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import entities.Feed;
import entities.Users;
import ninja.utils.NinjaProperties;

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
	
	public static String encryptAES(NinjaProperties ninjaProperties,String strToEncrypt) {
		try{
			
			String keyString = ninjaProperties.get("aeskey");
			byte[] keyBytes = keyString.getBytes();;
			SecretKey secretKey = new SecretKeySpec(keyBytes,"AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return new String(Base64.encodeBase64(cipher.doFinal(strToEncrypt.getBytes()),false,true));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static String decryptAES(NinjaProperties ninjaProperties,String strToDecrypt) {
		try{
			String keyString = ninjaProperties.get("aeskey");
			byte[] keyBytes = keyString.getBytes();;
			SecretKey secretKey = new SecretKeySpec(keyBytes,"AES");
            
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt)));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
