package twitterSearchExperiements;

import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterResponse;
import twitter4j.User;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.mysql.jdbc.PreparedStatement;
/**
* this class ia an experimental class for searching tweet
* from the twitter by using scheduler for repatative intervals
* of a specific time delay. geo-based search query used to 
* access the tweets.
* 
* @author Harish Muppalla
*/


	public class TweetAll {
	    
	    public static void main(String[] args) {
	    	ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);   
	        scheduler.scheduleWithFixedDelay(new Runnable() {        @Override        public void run() {
	       Long tweetID;
	       Long userID;
	       Long toUserid = null;
	       String userNme;
	       String toUser = null;
	       Date createdAt;
	       Timestamp timeat;
	       String profilImage;
	       String source;
	       Double longitude;
	       Double latitude;
	       String place;
	       String searchloc = null;
	       String TweetText;
	       //java.util.date date1;
	       Connection conn = null;
	       java.sql.PreparedStatement st;
			    String url = "jdbc:mysql://localhost/tweettest";
		   Statement stmt;
		    String driver = "com.mysql.jdbc.Driver";
		    String userName = "root";
		    String password = "";
	   	GeoLocation loc;
	   	
	   	GeoLocation locsearch = new GeoLocation(51.3026, 0.739);
	   	
	   	
	       
	        Twitter twitter = new TwitterFactory().getInstance();
	        try {
	        	 
	        	 int x=0;
	        	 
	        	 for(int i=1;i<16;i++)
	        	 {
	        		 
	        		 Query qy = new Query();
	        		 qy.setRpp(100);
	            	 qy.setPage(i);
	            	qy.resultType("Mixed");
	            	qy.geoCode(locsearch, 100,"mi");
	        	 if(qy.getRpp()>10)    
	        	 {
	            QueryResult result = twitter.search(qy);
	          
	            
	            List<Tweet> tweets = result.getTweets();
	            
	            for (Tweet tweet : tweets) {
	                //System.out.println("@" + tweet.getFromUser() + " - " + tweet.getText());
	              
	            	 loc=tweet.getGeoLocation();
	            	 System.out.printf(""+tweet.getLocation());
	            	  
	            	
	            		  tweetID = tweet.getId(); 
	                       userID = tweet.getFromUserId();
	                      userNme = tweet.getFromUser();
	                      createdAt =  tweet.getCreatedAt();
	                      java.sql.Date createdAt1 = new java.sql.Date(createdAt.getTime());
	                      timeat = new java.sql.Timestamp(createdAt.getTime());
	                      profilImage = tweet.getProfileImageUrl();
	                       source=tweet.getSource();
	                       if(tweet.getToUser()!=null)
	                       {
	                        toUserid = tweet.getToUserId();
	                        toUser = tweet.getToUser();
	                       }
	                       if(loc!=null)
		   	            	{   
	                      longitude = loc.getLongitude();
	                      latitude = loc.getLatitude();
		   	            	}
	                       else
	                       {
	                    	   longitude = 0.0;
	 	                      latitude = 0.0;
	                    	   
	                       }
	                       place = tweet.getLocation();
	                       TweetText = tweet.getText();
	                       try {
	                 	     Class.forName(driver).newInstance();
	                 	      conn = DriverManager.getConnection(url,userName,password);
	                 	      System.out.println("Connected to the database");
	                 	      stmt = conn.createStatement();
	                 	    ResultSet res1= stmt.executeQuery("SELECT * from  TweetAllData where tweet_id = '" + tweetID + "'");
	                         
	                          if(!res1.next())
	                          {
	                 	      st =  conn.prepareStatement("INSERT INTO `tweettest`.`TweetAllData` (`tweet_id`,`username`, `userid`,`touser`,`touserid`,`createdat`,`time`,`profile_image_source`,`geolatitude`,`geolongitude`,`place`,`geolocation`,`source`,`tweettext`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	                 	      st.setLong(1, tweetID);
	                 	      st.setString(2, userNme);
	                 	      st.setLong(3, userID);
	                 	      st.setString(4, toUser);
	                 	      st.setLong(5, toUserid);
	                 	      st.setDate(6,   createdAt1 );
	                 	      st.setTimestamp(7, timeat );
	                 	      st.setString(8, profilImage);
	                 	      st.setDouble(9, longitude);
	                 	      st.setDouble(10, latitude);
	                 	      st.setString(11,place );
	                 	      st.setString(12,searchloc);
	                 	      st.setString(13, source);
	                 	      st.setString(14,TweetText );
	                          st.executeUpdate();
	                          }
	                 	    /* int flag = stmt.executeUpdate ("INSERT INTO `tweettest`.`tweetdata` (`tweet_id`,`username`, `userid`,`createdat`,`profile_image_source`,`geolatitude`,`geolongitude`,`place`,`source`,`tweettext`) VALUES ( tweetID,userNme,userID,createdAt,profilImage,source,longitude,latitude,place,TweetText)");
	                 	     if(flag==1){
	                 	    	 System.out.println("suucess insert");
	                 	     }
	*/
	                 	      conn.close();
	                 	      System.out.println("Disconnected from database");
	                 	    } catch (Exception e) {
	                 	    	System.out.println("connection failed" +e);
	                 	    }
	                 	   if(loc!=null)
	   	            	{   
	          		x++;
	            	}
	            	
	            	         
	                
	              
	            }
	            }
	        	 }
	            
	            System.out.println(x);
	           // System.exit(0);
	        } catch (TwitterException te) {
	            te.printStackTrace();
	            System.out.println("Failed to search tweets: " + te.getMessage());
	            System.exit(-1);
	        }
	        }    },1,1,TimeUnit.MINUTES);
	    }
	



}
