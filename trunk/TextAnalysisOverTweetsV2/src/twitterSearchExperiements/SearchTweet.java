package twitterSearchExperiements;


import twitter4j.Annotations;
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
* of a specific time delay 
* 
* @author Harish Muppalla
*/

public class SearchTweet {
   
    public static void main(String[] args) {
    	ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);   
        scheduler.scheduleWithFixedDelay(new Runnable() {        @Override        public void run() {
    	
       Long tweetID;
       Long userID;
       String userNme;
       Date createdAt;
       Timestamp timeat;
       String profilImage;
       String source;
       Double longitude;
       Double latitude;
       String place;
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
   	
    
        Twitter twitter = new TwitterFactory().getInstance();
        try {
        	 
        	 int x=0;
        	 
        	 for(int i=1;i<16;i++)
        	 {
        		 
        		 Query qy = new Query("shift");
        		 qy.setRpp(100);
            	 qy.setPage(i);
            	qy.resultType("Mixed");
        	 if(qy.getRpp()>10)    
        	 {
            QueryResult result = twitter.search(qy);
          
            
            List<Tweet> tweets = result.getTweets();
            
            for (Tweet tweet : tweets) {
                //System.out.println("@" + tweet.getFromUser() + " - " + tweet.getText());
                Annotations tweet1=   tweet.getAnnotations();
                System.out.println(tweet1.toString());
            	 loc=tweet.getGeoLocation();
            	
            if(loc!=null)
            	{     // System.out.printf(""+tweet.getId());
            	//System.out.printf(""+tweet.getText());
            		  tweetID = tweet.getId(); 
                       userID = tweet.getFromUserId();
                      userNme = tweet.getFromUser();
                      createdAt =  tweet.getCreatedAt();
                      java.sql.Date createdAt1 = new java.sql.Date(createdAt.getTime());
                      timeat = new java.sql.Timestamp(createdAt.getTime());
                      profilImage = tweet.getProfileImageUrl();
                       source=tweet.getSource();
                      longitude = loc.getLongitude();
                      latitude = loc.getLatitude();
                       place = tweet.getLocation();
                       TweetText = tweet.getText();
                       try {
                 	    /*  Class.forName(driver).newInstance();
                 	      conn = DriverManager.getConnection(url,userName,password);
                 	      System.out.println("Connected to the database");
                 	      stmt = conn.createStatement();
                 	    ResultSet res1= stmt.executeQuery("SELECT * from tweetdata where tweet_id = '" + tweetID + "'");
                         
                          if(!res1.next())
                          {
                 	      st =  conn.prepareStatement("INSERT INTO `tweettest`.`tweetdata` (`tweet_id`,`username`, `userid`,`createdat`,`time`,`profile_image_source`,`geolatitude`,`geolongitude`,`place`,`source`,`tweettext`) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
                 	      st.setLong(1, tweetID);
                 	      st.setString(2, userNme);
                 	      st.setLong(3, userID);
                 	      st.setDate(4,   createdAt1 );
                 	     st.setTimestamp(5, timeat );
                 	      st.setString(6, profilImage);
                 	      st.setDouble(7, longitude);
                 	      st.setDouble(8, latitude);
                 	      st.setString(9,place );
                 	      st.setString(10, source);
                 	      st.setString(11,TweetText );
                          st.executeUpdate(); 
                          }
                 	   
                 	      conn.close();
                 	      System.out.println("Disconnected from database"); 
                 	      */
                 	    } catch (Exception e) {
                 	    	System.out.println("connection failed" +e);
                 	    } 
                
          		x++; 
            	}
            	
            	         
                
              
            } 
            }
        	 }
            
            System.out.println(x);
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        } 
    }    }, 1,2, TimeUnit.MINUTES);
    }
}


