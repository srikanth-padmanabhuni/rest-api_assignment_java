package facade;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import dto.Feed;
import dto.Mapper;
import dto.MinimalUserDetail;
import entities.Users;
import exceptions.InvalidDetailsException;

public class FeedFacadeImpl implements FeedFacade {
	private static Logger log = LogManager.getLogger(FeedFacadeImpl.class);
	@Inject 
	Provider<EntityManager> entityManagerProvider;
	@Inject
	private UserFacade userFacade;
	
	@Override
	@Transactional
	public Long createFeed(Feed feed) throws InvalidDetailsException {
		log.debug("create feed is called");
		checkFeedDetails(feed);
		Users user = checkForUserExistance(feed.getUserDetails().getUserId());
		return saveFeedToDb(feed, user);
	}
	
	public void checkFeedDetails(Feed feed) throws InvalidDetailsException {
		log.debug("checkFeedDetails is called");
		if(feed == null) {
			throw new InvalidDetailsException("Please provide valid feed details to create a feed");
		}
		String feedPostUrl = feed.getFeedPostUrl();
		if(feedPostUrl == null || feedPostUrl.trim().length() == 0) {
			throw new InvalidDetailsException("Please provide valid picture or video url to post a feed");
		}
		String feedDescription = feed.getFeedDescription();
		if(feedDescription == null || feedDescription.trim().length() == 0) {
			throw new InvalidDetailsException("Please provide valid description to post a feed");
		}
		
		MinimalUserDetail minimalUserDetail = feed.getUserDetails();
		if(minimalUserDetail == null || minimalUserDetail.getUserId() == null) {
			throw new InvalidDetailsException("Please provide valid userId to create a post");
		}
	}
	
	@Transactional
	public Long saveFeedToDb(Feed feed, Users user) {
		log.debug("saveFeedToDb is called");
		EntityManager e = entityManagerProvider.get();
		entities.Feed feedEntity = new entities.Feed();
		feedEntity.setDescription(feed.getFeedDescription());
		feedEntity.setPosturl(feed.getFeedPostUrl());
		feedEntity.setPrivacy(feed.isFeedPrivacy());
		feedEntity.setUsers(user);
		e.persist(feedEntity);
		return feedEntity.getId();
	}
	
	@Override
	@Transactional
	public void deleteFeed(Long id, Long userId) throws InvalidDetailsException {
		log.debug("deleteFeed is called with id :"+id);
		if(id == null || userId == null) {
			throw new InvalidDetailsException("Please provide valid post id and userId to delet a post");
		}
		EntityManager e = entityManagerProvider.get();
		entities.Feed feedEntity = e.find(entities.Feed.class, id);
		if(feedEntity == null) {
			throw new InvalidDetailsException("No post found with the given post id");
		}
		boolean isAuthorizedUser = feedEntity.getUsers().getId().equals(userId);
		if(!isAuthorizedUser) {
			throw new InvalidDetailsException("User is not authorized user to delete this post");
		}
		e.remove(feedEntity);
	}
	
	@Override
	public Feed getFeedById(Long id, Long userId) throws InvalidDetailsException {
		log.debug("getFeedById is called with id :"+id);
		if(id == null) {
			throw new InvalidDetailsException("Please provide valid post id to fetch a post");
		}
		entities.Feed feed = getFeedFromDb(id);
		if(feed == null) {
			throw new InvalidDetailsException("NO post found for the given id");
		}
		if(feed.isPrivacy() && !feed.getUsers().getId().equals(userId)) {
			throw new InvalidDetailsException("This post is private only authorized user can view this");
		}
		return Mapper.mapFeedToFeedDto(feed);
	}
	
	@Transactional
	public entities.Feed getFeedFromDb(Long id) {
		log.debug("getFeedFromDb is called with id :"+id);
		EntityManager e = entityManagerProvider.get();
		entities.Feed feedEntity = e.find(entities.Feed.class, id);
		return feedEntity;
	}
	
	@Override
	public List<Feed> getFeedsByUserId(Long userId) throws InvalidDetailsException {
		log.debug("getFeedsByUserId is called with userId : "+userId);
		if(userId == null) {
			throw new InvalidDetailsException("Please provide valid userId to fetch a posts");
		}
		Users user = checkForUserExistance(userId);
		return getFeedsFromDbByUserId(userId);
	}
	
	@Transactional
	public List<Feed> getFeedsFromDbByUserId(Long userId) {
		log.debug("getFeedsFromDbByUserId is called with userId : "+userId);
		EntityManager e = entityManagerProvider.get();
		List<entities.Feed> feeds = e.createNamedQuery("Feed.findFeedsByUser", entities.Feed.class)
										.setParameter("userId", userId)
										.getResultList();
		List<Feed> feedsDto = new ArrayList<Feed>();
		if(feeds != null && feeds.size() != 0) {
			for (entities.Feed feed : feeds) {
				Feed feedDto = Mapper.mapFeedToFeedDto(feed);
				feedsDto.add(feedDto);
			}
		}
		return feedsDto;
	}
	
	@Transactional
	public Users checkForUserExistance(Long userId) throws InvalidDetailsException {
		log.debug("checkForUserExistance is called with userId : "+userId);
		Users user = userFacade.getUserFromDbById(userId);
		if(user == null) {
			throw new InvalidDetailsException("No user found for given userId to create a post");
		}
		return user;
	}
	
	@Override
	public Feed upateFeed(Feed feed, Long userId) throws InvalidDetailsException {
		log.debug("update feed is called");
		checkFeedDetails(feed);
		Users user = checkForUserExistance(feed.getUserDetails().getUserId());
		authorizeFeedByUserId(feed.getFeedId(), userId);
		entities.Feed feedEntity = updateFeedNdSaveToDb(feed);
		if(feedEntity == null) {
			throw new InvalidDetailsException("No feed found for the given feedId");
		}
		return Mapper.mapFeedToFeedDto(feedEntity);
	}
	
	@Transactional
	public void authorizeFeedByUserId(Long feedId, Long userId) throws InvalidDetailsException {
		log.debug("authorizeFeedByUserId is called with feedId : "+feedId+" | userId : "+userId);
		EntityManager e = entityManagerProvider.get();
		List<entities.Feed> feeds = e.createNamedQuery("Feed.findFeedByUserIdNdFeedId", entities.Feed.class)
										.setParameter("userId", userId)
										.setParameter("feedId", feedId)
										.getResultList();
		if(feeds == null || feeds.size() == 0) {
			throw new InvalidDetailsException("User is not authorized to perform operation on post with id : "+feedId);
		}
	}
	
	@Transactional
	public entities.Feed updateFeedNdSaveToDb(Feed feed) {
		log.debug("updateFeedNdSaveToDb is called");
		EntityManager e = entityManagerProvider.get();
		entities.Feed f = e.find(entities.Feed.class, feed.getFeedId());
		if(f != null) {
			f.setDescription(feed.getFeedDescription());
			f.setPosturl(feed.getFeedPostUrl());
			f.setPrivacy(feed.isFeedPrivacy());
			e.persist(f);
		}
		return f;
	}
}
