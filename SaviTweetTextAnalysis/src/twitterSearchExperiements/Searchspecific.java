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
import java.util.FormatFlagsConversionMismatchException;
import java.util.IllegalFormatFlagsException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.MissingFormatArgumentException;
import java.util.NoSuchElementException;
import java.util.UnknownFormatConversionException;
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
 * this is an experimental class for searching the tweets 
 * from twitter through search API
 * @author Harish Muppalla
 */



	public class Searchspecific {
	   
		Map<Long,String> myMap;
		 public Searchspecific()
		{
			
		}

	    public static void main(String[] args) {
	    	
	       //java.util.date date1;
	    	Searchspecific sf =new Searchspecific();
	      sf.fillingMap();
	                 	
	            
	              
	     }
	           public Connection dbconnection() 
	           {           Connection conn= null;
	        	   try{
	        	   java.sql.PreparedStatement st;
				    String url = "jdbc:mysql://localhost/tweettest";
				   			        String driver = "com.mysql.jdbc.Driver";
			        String userName = "root";
			        String password = "";
	        	     Class.forName(driver).newInstance();
          	        conn = DriverManager.getConnection(url,userName,password);
          	        System.out.println("Connected to the database");
          	         
          	       
	           }
	           catch (Exception e) {
        	    	System.out.println("connection failed" +e);
        	    }
	           return conn;
			
	           }
	           public void fillingMap()
	           {   
	        	   Connection conn1 = dbconnection(); 
	            	 
                   try {
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
                	 
                	 Statement stmt1=conn1.createStatement();
             	      ResultSet res1= stmt1.executeQuery("SELECT * from  TweetAllData where tweettext like '%riots%'");
             	      myMap = new LinkedHashMap<Long,String>();
             	      int x=0;
             	     java.sql.PreparedStatement st11;
                     while(res1.next())
                     {   x= x+1;
                    	 System.out.println("" + res1.getLong(1));
                    	 System.out.println("" + res1.getString(14));
                          tweetID = res1.getLong(1); 
                         userNme = res1.getString(2);
	                     userID = res1.getLong(3);
	                     
	                     createdAt =  res1.getDate(6);
	                     java.sql.Date createdAt1 = new java.sql.Date(createdAt.getTime());
	                     timeat = new java.sql.Timestamp(createdAt.getTime());
	                     profilImage = res1.getString(8);
	                    source= res1.getString(13);
	                      
	                        toUserid = res1.getLong(5);
	                        toUser = res1.getString(4);
	                       
	                       
	                      longitude = res1.getDouble(9);
	                      latitude = res1.getDouble(10);
		   	            	
	                       
	                       place = res1.getString(11);
	                       TweetText = res1.getString(14);
                    	 
                    	 myMap.put(res1.getLong(1),res1.getString(14));
                    	Statement stmt2 = conn1.createStatement();
                    	 ResultSet res2 = stmt2.executeQuery("SELECT * from  Tweetsearch where tweettext like 'TweetText'");
                    	 if(!res2.next()){
                    		 st11 =  conn1.prepareStatement("INSERT INTO `tweettest`.`Tweetsearch` (`tweet_id`,`username`, `userid`,`touser`,`touserid`,`createdat`,`time`,`profile_image_source`,`geolatitude`,`geolongitude`,`place`,`geolocation`,`source`,`tweettext`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	                 	      st11.setLong(1, tweetID);
	                 	      st11.setString(2, userNme);
	                 	      st11.setLong(3, userID);
	                 	      st11.setString(4, toUser);
	                 	      st11.setLong(5, toUserid);
	                 	      st11.setDate(6,   createdAt1 );
	                 	     st11.setTimestamp(7, timeat );
	                 	      st11.setString(8, profilImage);
	                 	      st11.setDouble(9, longitude);
	                 	      st11.setDouble(10, latitude);
	                 	      st11.setString(11,place );
	                 	      st11.setString(12,searchloc);
	                 	      st11.setString(13, source);
	                 	      st11.setString(14,TweetText );
	                          st11.executeUpdate(); 
                    		 
                    		 
                    		 
                    		 
                    		 
                    		 
                    		 
                    		 
                    	 }
                    	 
                    	 
                    	 
                    	 
                    	 
                    	 
                    	 
                    	 
                    	 
                     }
                     System.out.println(""+x);
                     Iterator<Long> hashIterator = myMap.keySet().iterator();
                     System.out.print( myMap.size());
                     int y=0;
                     while(hashIterator.hasNext())
                     {  try{
                    	 y++;
                    	 String variable = (String)myMap.get(hashIterator.next());
                    	 System.out.printf("\n " + y+"["  + hashIterator.next() + " ]"+ "." +variable );
                     }
                     catch(MissingFormatArgumentException ufce){
                    	 System.out.println(ufce.getFormatSpecifier());
                     }
                     catch(UnknownFormatConversionException uc)
                     {
                    	 System.out.println(uc.getConversion());
                     }
                     catch(IllegalFormatFlagsException ife)
                     {
                    	 System.out.println(ife.getFlags());
                     }
                     catch(FormatFlagsConversionMismatchException ff)
                     {
                    	 System.out.println(ff.getConversion());
                     }
                     catch(NoSuchElementException ne)
                     {
                    	 System.out.println(ne.getLocalizedMessage());
                     }
                     }
                    System.out.print( myMap.size());
                     
             	      conn1.close();
             	     
             	        } 
                   catch (Exception e) {
             	    	System.out.println("connection failed" +e);
             	    }
                   
	           }
	       
	          
	        
	    }
	




