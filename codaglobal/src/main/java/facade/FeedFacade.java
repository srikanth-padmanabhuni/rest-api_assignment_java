package facade;

import java.util.List;

import dto.Feed;
import exceptions.InvalidDetailsException;

public interface FeedFacade {

	Long createFeed(Feed feed) throws InvalidDetailsException;

	void deleteFeed(Long id, Long userId) throws InvalidDetailsException;

	Feed getFeedById(Long id, Long userId) throws InvalidDetailsException;

	Feed upateFeed(Feed feed, Long userId) throws InvalidDetailsException;

	List<Feed> getFeedsByUserId(Long userId) throws InvalidDetailsException;

}
