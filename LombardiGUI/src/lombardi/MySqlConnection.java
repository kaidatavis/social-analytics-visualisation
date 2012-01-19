package lombardi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MySqlConnection {
	
	public static ArrayList<TweetData> allTweetData = new ArrayList<TweetData>();
	String dbUrl = "jdbc:mysql://localhost/twitter?user=root&password=password";
	String dbClass = "com.mysql.jdbc.Driver";
	
	String query = "SELECT username, touser FROM tweetalldata" +
			" where  tweettext like ('%reading%')";
	
        String mainQuery = "SELECT username FROM tweetalldata where  tweettext like (?) " + 
                            " group by username order by count(username) desc";
        String userQuery = "select username, touser, tweettext, createdat, profile_image_source from tweetalldata " +
                " where username = ? and tweettext like (?) limit 50";
	
        String toUserQuery = "select  profile_image_source from tweetalldata " +
                " where username = ? limit 1";
        
	
	ArrayList<String> dbData = new ArrayList<String>();
	
        
        
        
//        public void createNodesFromDateFilter(int month){
//            
//            for (TweetData tweetData : allTweetData) {
//                SimpleDateFormat sdf;
//
//                sdf = new SimpleDateFormat("MM");
//                
//                int tweetMonth = new Integer(sdf.format(tweetData.getCreateDate()));
//                
//                if(tweetMonth == month){
//                    new Edge(tweetData.getUserName(), tweetData.getToUser());
//                }
//            }
//            
//        }
    

        
	public void createNodesFromDB(String tweetText)throws SQLException,ClassNotFoundException {
            
            allTweetData.clear();
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection (dbUrl);
            
            PreparedStatement stmt_mainQuery = con.prepareStatement(mainQuery);
            
//            System.out.println(mainQuery + "; "+ tweetText);
            
            PreparedStatement stmt_userQuery = con.prepareStatement(userQuery);
            
            PreparedStatement stmt_toUserQuery = con.prepareStatement(toUserQuery);
            
            stmt_mainQuery.setString(1, "%" + tweetText + "%");
            ResultSet rs_mainQuery = stmt_mainQuery.executeQuery();
            
            ArrayList<String> usersWithMostTweets = new ArrayList<String>();
            
            ArrayList<String> allToUsers = new ArrayList<String>();
            
            boolean dataFound = false;
            while (rs_mainQuery.next()) {
                String userName =  rs_mainQuery.getString(1);
                usersWithMostTweets.add(userName);
//                System.out.println(userName);
               dataFound = true;
            }
            if(dataFound == false){
                new Edge("No Graph Data", "No Graph Data");
                return;
            }
            
            int i = 0;
            for(String users : usersWithMostTweets){
                stmt_userQuery.setString(1, users);
                stmt_userQuery.setString(2, "%" + tweetText + "%");
                ResultSet rs_userQuery = stmt_userQuery.executeQuery();
                

                
                while(rs_userQuery.next()){
                    String userName = rs_userQuery.getString(1);
                    String toUser = rs_userQuery.getString(2);
                    String userTweetText = rs_userQuery.getString(3);                    
                    Date createDate = rs_userQuery.getDate(4);
                    String imageUrl = rs_userQuery.getString(5);
                    
                    if(i == 0)
                        allToUsers.add(toUser);
                    else if(allToUsers.contains(toUser)==false)
                        continue;
                    
                    stmt_toUserQuery.setString(1, toUser);
                    ResultSet rs_toUserQuery = stmt_toUserQuery.executeQuery();
                    String toUserImageUrl = null;
                    
                    if(rs_toUserQuery.next()){
                        toUserImageUrl = rs_toUserQuery.getString(1);
                    }
                    
                    TweetData tweetData = new TweetData();
                    tweetData.setUserName(userName);
                    tweetData.setToUser(toUser);
                    tweetData.setTweetText(userTweetText);
                    tweetData.setImageUrl(imageUrl);
                    tweetData.setCreateDate(createDate);
                    tweetData.setImageUrlToUser(toUserImageUrl);
                    
                    allTweetData.add(tweetData);
//                    System.out.println(++i + "Creating Edge " + userName + ", " + toUser) ;
//                    new Edge(userName, toUser);
                }
                i++;
            }
            
            PreparedStatement stmt4 = con.prepareStatement(userQuery);
            
            PreparedStatement stmt5 = con.prepareStatement(toUserQuery);
            
            for(String toUsers: allToUsers){
                stmt4.setString(1, toUsers);
                stmt4.setString(2, "%" + tweetText + "%");
                ResultSet rs4 = stmt4.executeQuery();
                
                while(rs4.next()){
                    String userName = rs4.getString(1);
                    String toUser = rs4.getString(2);
                    String userTweetText = rs4.getString(3);                    
                    Date createDate = rs4.getDate(4);
                    String imageUrl = rs4.getString(5);
                    
                    stmt5.setString(1, toUser);
                    ResultSet rs5 = stmt5.executeQuery();
                    String toUserImageUrl = null;
                    
                    if(rs5.next()){
                        toUserImageUrl = rs5.getString(1);
                    }
                    
                    TweetData tweetData = new TweetData();
                    tweetData.setUserName(userName);
                    tweetData.setToUser(toUser);
                    tweetData.setTweetText(userTweetText);
                    tweetData.setImageUrl(imageUrl);
                    tweetData.setCreateDate(createDate);
                    tweetData.setImageUrlToUser(toUserImageUrl);
                    
                    allTweetData.add(tweetData);
                    
                    allTweetData.add(tweetData);
                   // System.out.println(++i + "Creating Edge " + userName + ", " + toUser) ;
                   // new Edge(userName, toUser);
                    
                }
                
                
            }
            
            con.close();
        }

	
	/*public void parseDBData(String username) throws SQLException{
		try {
		
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection (dbUrl);
			//Statement stmt = con.createStatement();
			
			PreparedStatement stmt = con.prepareStatement(query);
			
			//stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery();
	
			ArrayList<HashMap<String, ArrayList<String>>> allData = new ArrayList<HashMap<String, ArrayList<String>>>();
			
			int i = 0;
			while (rs.next()) {
                            String sender, receiver;
				sender = rs.getString(1);
				dbData.add(sender);
				receiver = rs.getString(2);
				dbData.add(receiver);
				System.out.println(++i + " - " +sender+" ------------------- "+receiver);
                                if(allData.size() > 0){
                                    boolean found = false;
                                    for(HashMap<String, ArrayList<String>> userNodes: allData){
                                        found = false;
                                        if(userNodes.containsKey(sender)){
                                            ArrayList<String> allSenders = userNodes.get(sender);
                                            if(allSenders.contains(receiver)==false){
                                                allSenders.add(receiver);
                                                found = true;
                                                break;
                                            }
                                            
                                        }
                                    }
                                    if(found == false){
                                        HashMap<String, ArrayList<String>> userNodes = new HashMap<String, ArrayList<String>>();
                                        ArrayList<String> allReceivers = new ArrayList<String>();
                                        allReceivers.add(receiver);
                                        userNodes.put(sender, allReceivers);
                                        allData.add(userNodes);
                                    }
                                }
                                else{
                                      HashMap<String, ArrayList<String>> userNodes = new HashMap<String, ArrayList<String>>();
                                        ArrayList<String> allReceivers = new ArrayList<String>();
                                        allReceivers.add(receiver);
                                        userNodes.put(sender, allReceivers);
                                        allData.add(userNodes);
                                }
                                
			// new Edge(sender, receiver);
			} //end while
                        
                        // now create graph with more than 10 tweets
                        for(HashMap<String, ArrayList<String>> userNodes: allData){
                            for(String sender: userNodes.keySet()){
                                if(userNodes.get(sender).size()>=10){
                                    ArrayList<String> allReceivers = userNodes.get(sender);
                                    for(String receiver: allReceivers){
                                        new Edge(sender, receiver);
                                    }
                                }
                            }
                        }
                        
			//System.out.println(dbData.get(0)+"---------------------"+dbData.get(1));
			con.close();
		} //end try

		catch(ClassNotFoundException e) {
		e.printStackTrace();
		}
	}*/	
}
