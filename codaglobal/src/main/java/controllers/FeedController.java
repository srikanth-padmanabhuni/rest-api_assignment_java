package controllers;

import dto.Feed;
import ninja.Result;

public interface FeedController {

	Result createFeed(Feed feed);
	Result getFeedById(Long id, Long userId);
	Result updateFeed(Feed feed, Long userId);
	Result deleteFeedById(Long feedId, Long userId);
	Result getFeedsByUserId(Long userId);
	
}
