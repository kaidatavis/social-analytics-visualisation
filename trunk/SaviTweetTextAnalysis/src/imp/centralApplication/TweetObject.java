package imp.centralApplication;

import java.sql.Timestamp;
import java.util.Date;
/**
 * This class is a simplebean with getter and setter methods 
 * for tweet data. 
 * @author Harish Muppalla
 *
 */
public class TweetObject{
	
	// instance variables
	private Long tweetID; // twitter id 
	private Long userID; // sender id
	private Long toUserid = null; // receiver user id 
	private String userNme; // sender's username
	private String toUser = null; // receivers username
	private Date createdAt; // date of tweet creation
	private Timestamp timeat; // time of tweet creation
	private String profilImage; // profile image of sender
	private String source; // source from which the sender accessed twitter
	private Double longitude= 0.0; 
	private Double latitude = 0.0;
	private String place; // place mentioned in the tweet (address)
	private String searchloc = null; 
	private String TweetText; // original text of the tweet 
	private String annotations = " "; 
	private double sentiScore = 0;
	private String phrases = "  ";
	private double[] geocords;
    public TweetObject(){
    	
    }
	
	/**
	 * getter method to get tweet id
	 * @return
	 */
	public Long getTweetID() {
		return tweetID;
	}
	/**
	 * setter method for assigning the tweet id
	 * @param tweetID
	 * 
	 */
	
	public void setTweetID(Long tweetID) {
		this.tweetID = tweetID;
	}
	/**
	 * getter method for sender user id 
	 * @return Long 
	 *         tweet sender id
	 */
	public Long getUserID() {
		return userID;
	}
	/**
	 * setter method to assign the sender id
	 * @param userID
	 *        tweet sender id
	 */
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	/**
	 * getter method to get receiver user id 
	 * @return Long 
	 *          receivr user id
	 */
	public Long getToUserid() {
		return toUserid;
	}
	/**
	 * setter method to set receiver user id
	 * @param toUserid
	 *        receiver user id
	 */
	public void setToUserid(Long toUserid) {
		this.toUserid = toUserid;
	}
	/**
	 * getter method to get sender user name
	 * @return String
	 *           sender user id
	 */
	public String getUserNme() {
		return userNme;
	}
	/**
	 * setter method to set the sender user name 
	 * @param userNme
	 */
	public void setUserNme(String userNme) {
		this.userNme = userNme;
	}
	/**
	 * getter method to get the reciver user name
	 * @return String 
	 *         receiver userr name 
	 */
	public String getToUser() {
		return toUser;
	}
	/**
	 * setter method to set receiver user name 
	 * @param toUser
	 */
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	/**
	 * getter method to set the tweet creation date
	 * @return Date 
	 */
	public Date getCreatedAt() {
		return createdAt;
	}
	/**
	 * setter method to set the tweet creation date
	 * @param createdAt
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	/**
	 * getter method to get the tweet sended time 
	 * @return TimeStamp 
	 *         time of tweet sended
	 */
	public Timestamp getTimeat() {
		return timeat;
	}
	/**
	 * sette method to set the tweet sended time 
	 * @param timeat
	 */
	public void setTimeat(Timestamp timeat) {
		this.timeat = timeat;
	}
	/**
	 * getter method to get the profile image of tweet sender
	 * @return String 
	 *           prifile image of tweet sender
	 */
	public String getProfilImage() {
		return profilImage;
	}
	/**
	 * setter method to set the profile image of tweet sender
	 * @param profilImage
	 */
	public void setProfilImage(String profilImage) {
		this.profilImage = profilImage;
	}
	/**
	 * setter method to set the source of a tweet 
	 * @return String
	 *         source url 
	 */
	public String getSource() {
		return source;
	}
	/**
	 * setter method to set the source of a tweet
	 * @param source
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * getter method to ge the longitude of a tweet
	 * @return double 
	 *         longitude 
	 */
	public Double getLongitude() {
		return longitude;
	}
	/**
	 * setter method to set the longitude of a tweet
	 * @param longitude
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	/**
	 * getter method to get latitude coordinate of tweet
	 * @return double
	 *          latitude
	 */
	public Double getLatitude() {
		return latitude;
	}
	/**
	 * setter method to set the lattitude coordinate 
	 * @param latitude
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	/**
	 * getter method to get place of a tweet
	 * @return string 
	 *         place of a tweet
	 */
	public String getPlace() {
		return place;
	}
	/**
	 * setter method to set place of atweet
	 * @param place
	 */
	public void setPlace(String place) {
		this.place = place;
	}
	/**
	 * getter method to get geolocation 
	 * @return String
	 *         geolocation of tweet
	 */
	public String getSearchloc() {
		return searchloc;
	}
	/**
	 * setter method to set geolocation
	 * @param searchloc
	 */
	public void setSearchloc(String searchloc) {
		this.searchloc = searchloc;
	}
	/**
	 * getter method to get tweet text
	 * @return String
	 *         tweet text
	 */
	public String getTweetText() {
		return TweetText;
	}
	/**
	 * setter method to set tweet text
	 * @param tweetText
	 */
	public void setTweetText(String tweetText) {
		TweetText = tweetText;
	}
	/**
	 * getter method to get Annotations
	 * @return String
	 *         annotations in a string
	 */
	public String getAnnotations() {
		return annotations;
	}
	/**
	 * setter method to set Annotations
	 * @param annotations
	 */
	public void setAnnotations(String annotations) {
		this.annotations = annotations;
	}
	/**
	 * getter method to get the sentiment score 
	 * @return double
	 *          sentiment score
	 */
	public double getSentiScore() {
		return sentiScore;
	}
	/**
	 * setter method to set the sentiment score
	 * @param sentiScore
	 */
	public void setSentiScore(double sentiScore) {
		this.sentiScore = sentiScore;
	}
	/**
	 * getter method to get significant phrases
	 * @return String
	 *        significant phrases
	 */
	public String getPhrases() {
		return phrases;
	}
	/**
	 * setter method to set significant phrases
	 * @param phrases
	 */
	
	public void setPhrases(String phrases) {
		this.phrases = phrases;
	}
	/**
	 * getter method to get geocoordinated 
	 * @return double[]
	 *          latitude and longitude
	 */
	public double[] getGeocords() {
		return geocords;
	}
	/**
	 * setter method to set the geo coordinates
	 * @param geocords
	 */
	public void setGeocords(double[] geocords) {
		this.geocords = geocords;
	}
		

}
