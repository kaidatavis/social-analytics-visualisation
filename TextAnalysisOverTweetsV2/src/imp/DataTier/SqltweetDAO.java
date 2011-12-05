package imp.DataTier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
/**
 * This class implements abstract methods of DataInterfaceDAO interface
 * which data access object class, responsible for data transaction in 
 * between CentralTweetCollector1 class and database.
 * 
 * @author Harish Muppalla
 */
public class SqltweetDAO implements DataInterfaceDAO {
	 
	 Long tweetID; // twitter id 
	 Long userID; // sender id
	 Long toUserid; // receiver user id 
	 String userNme; // sender's username
	 String toUser = "null"; // receivers username
	 Date createdAt; // date of tweet creation
	 Timestamp timeat; // time of tweet creation
	 String profilImage; // profile image of sender
	 String source; // source from which the sender accessed twitter
	 Double longitude= 0.0; 
	 Double latitude = 0.0;
	 String place; // place mentioned in the tweet (address)
	 String searchloc = ""; 
	 String TweetText; // original text of the tweet 
	 String annotations = " "; 
	 double sentiScore = 0;
	 String phrases = "  ";
	 
	 /**
	  * This method is responsible for inserting the TweetObject data 
	  * into dataextraction table.
	  * @param obj 
	  *        TweetObject of a single instance data
	  * @return void
	  * 
	  */
	
	public void insertTweetdataFinal(imp.centralApplication.TweetObject obj){
		Connection conn =dbconnection();
	     tweetID = obj.getTweetID() ; // twitter id 
	     userID =obj.getUserID(); // sender id
		  toUserid = obj.getToUserid(); // receiver user id 
		  userNme =obj.getUserNme(); // sender's username
		  toUser = obj.getToUser(); // receivers username
		  createdAt = obj.getCreatedAt(); // date of tweet creation
		  java.sql.Date createdAt1 = new java.sql.Date(createdAt.getTime());
		 timeat= obj.getTimeat(); // time of tweet creation
		  profilImage=obj.getProfilImage(); // profile image of sender
		 source=obj.getSource(); // source from which the sender accessed twitter
		 latitude = obj.getLatitude();
		 longitude= obj.getLongitude();
		 place = obj.getPlace(); // place mentioned in the tweet (address)
		  searchloc = obj.getSearchloc(); 
		 TweetText= obj.getTweetText(); // original text of the tweet 
		 annotations =obj.getAnnotations(); 
		 sentiScore = obj.getSentiScore();
		 phrases = obj.getPhrases();
		 java.sql.PreparedStatement st;
		 Statement stmt;	
		 
		 try { System.out.println("hi");
			 stmt = conn.createStatement();
				ResultSet res1= stmt.executeQuery("SELECT * from  dataextraction where tweet_id = '" + tweetID + "'");
				//checking for duplication of tweet through tweetID
				if(!res1.next())
				{        try{		
					//inserting to the tweetobject data into the dataextraction table
					st =  conn.prepareStatement("INSERT INTO `centraldatabase`.`dataextraction` (`tweet_id`,`username`, `userid`,`touser`,`touserid`,`createdat`,`time`,`profile_image_source`,`geolatitude`,`geolongitude`,`place`,`geolocation`,`source`,`tweettext`,`annotations`,`sentiscore`,`phrases`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					
					st.setLong(1, tweetID);
					st.setString(2, userNme);
					st.setLong(3, userID);
					st.setString(4, toUser);
					st.setLong(5, toUserid);
					st.setDate(6,   createdAt1 );
					st.setTimestamp(7, timeat );
					st.setString(8, profilImage);
					st.setDouble(9, latitude);
					st.setDouble(10, longitude);
					st.setString(11,place );
					st.setString(12,searchloc);
					st.setString(13, source);
					st.setString(14,TweetText );
					st.setString(15,annotations);
					st.setDouble(16,sentiScore);
					st.setString(17,phrases);
					
					st.executeUpdate();
					
							
					
				 
				     } catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
		               
				}
		 }
		 catch (Exception e) {
				System.out.println("connection failed" +e);
			}
			 
			
		 
try {
	conn.close();
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
		 
		  
	}
	/**
	 * Method for database connetion to centraldatabase
	 * @return Connection
	 *          return the connection variable
	 */
	
	public Connection dbconnection() 
	{           Connection conn= null;
		   try{
		   //java.sql.PreparedStatement st;
			   String url= " ";
			   
				   url = "jdbc:mysql://localhost/centraldatabase";
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

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
