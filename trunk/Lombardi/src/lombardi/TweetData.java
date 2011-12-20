package lombardi;

import java.util.Date;

public class TweetData {
    String userName;
    String toUser;
    String twetText;
    String imageUrl;
    Date createDate;
    
    
    String searchTweet;
    int searchMonth;
    boolean isSearchTweet;
    
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public String getTwetText() {
		return twetText;
	}
	public void setTwetText(String twetText) {
		this.twetText = twetText;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getSearchTweet() {
		return searchTweet;
	}
	public void setSearchTweet(String searchTweet) {
		this.searchTweet = searchTweet;
	}
	public int getSearchMonth() {
		return searchMonth;
	}
	public void setSearchMonth(int searchMonth) {
		this.searchMonth = searchMonth;
	}
	public boolean isSearchTweet() {
		return isSearchTweet;
	}
	public void setSearchTweet(boolean isSearchTweet) {
		this.isSearchTweet = isSearchTweet;
	}
}
