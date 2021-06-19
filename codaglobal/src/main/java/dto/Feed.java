package dto;

public class Feed {

	private Long feedId;
	private String feedPostUrl;
	private String feedDescription;
	private boolean feedPrivacy;
	private MinimalUserDetail userDetails;
	
	public Long getFeedId() {
		return feedId;
	}
	public void setFeedId(Long feedId) {
		this.feedId = feedId;
	}
	public String getFeedPostUrl() {
		return feedPostUrl;
	}
	public void setFeedPostUrl(String feedPostUrl) {
		this.feedPostUrl = feedPostUrl;
	}
	public String getFeedDescription() {
		return feedDescription;
	}
	public void setFeedDescription(String feedDescription) {
		this.feedDescription = feedDescription;
	}
	public boolean isFeedPrivacy() {
		return feedPrivacy;
	}
	public void setFeedPrivacy(boolean feedPrivacy) {
		this.feedPrivacy = feedPrivacy;
	}
	public MinimalUserDetail getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(MinimalUserDetail userDetails) {
		this.userDetails = userDetails;
	}
	
}
