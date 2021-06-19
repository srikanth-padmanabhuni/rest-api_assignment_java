package controllers;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.inject.Inject;

import dto.Feed;
import exceptions.InvalidDetailsException;
import facade.FeedFacade;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.params.PathParam;

public class FeedControllerImpl implements FeedController {

	private static Logger log = LogManager.getLogger(FeedControllerImpl.class);
	
	@Inject
	private FeedFacade feedFacade;
	
	@Override
	public Result createFeed(Feed feed) {
		try {
			Long feedId = feedFacade.createFeed(feed);
			return Results.status(200).json().render("Feed has been created with id : "+feedId);
		} catch(InvalidDetailsException e) {
			e.printStackTrace();
			return Results.status(500).json().render(e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
			return Results.status(500).json().render("An error has been occurred please try again later !!!");
		}
	}

	@Override
	public Result getFeedById(@PathParam("feedId") Long id, @PathParam("userId") Long userId) {
		try {
			Feed feed = feedFacade.getFeedById(id, userId);
			return Results.status(200).json().render(feed);
		} catch(InvalidDetailsException e) {
			e.printStackTrace();
			return Results.status(500).json().render(e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
			return Results.status(500).json().render("An error has been occurred please try again later !!!");
		}
	}

	@Override
	public Result updateFeed(Feed feed, @PathParam("userId") Long userId) {
		try {
			Feed feedDto = feedFacade.upateFeed(feed, userId);
			return Results.status(200).json().render(feedDto);
		} catch(InvalidDetailsException e) {
			e.printStackTrace();
			return Results.status(500).json().render(e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
			return Results.status(500).json().render("An error has been occurred please try again later !!!");
		}
	}

	@Override
	public Result deleteFeedById(@PathParam("feedId") Long feedId, @PathParam("userId") Long userId) {
		try {
			feedFacade.deleteFeed(feedId, userId);
			return Results.status(200).json().render("Feed has been deleted sucessfully !!!");
		} catch(InvalidDetailsException e) {
			e.printStackTrace();
			return Results.status(500).json().render(e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
			return Results.status(500).json().render("An error has been occurred please try again later !!!");
		}
	}
	
	@Override
	public Result getFeedsByUserId(@Param("userId") Long userId) {
		try {
			List<Feed> feeds = feedFacade.getFeedsByUserId(userId);
			return Results.status(200).json().render(feeds);
		} catch(InvalidDetailsException e) {
			e.printStackTrace();
			return Results.status(500).json().render(e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
			return Results.status(500).json().render("An error has been occurred please try again later !!!");
		}
	}
}
