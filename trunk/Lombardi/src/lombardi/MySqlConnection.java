package lombardi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MySqlConnection {
	
	String sender, receiver;
	String dbUrl = "jdbc:mysql://localhost/twitter?user=root&password=password";
	String dbClass = "com.mysql.jdbc.Driver";
	//String query = "Select userid,touser FROM dataextraction";
	String query = "SELECT distinct username as sender, touser as receiver FROM tweetalldata" +
			" where username <> touser and username = ? ";
	
	String testQuery = "SELECT  username as sender, touser as receiver FROM tweetalldata" +
	" where  tweettext like (?)";
//"or username in (select  touser from tweetalldata where username = 'ZedBooks')";
	
	String nodeQuery = "select distinct username, count(distinct username, touser) as count from tweetalldata" +
			" group by username having count(distinct username,touser) > 20 order by count";

	
	ArrayList<String> dbData = new ArrayList<String>();
	
	
	public ArrayList<Item> getNodeCount() throws SQLException{
		
		ArrayList<Item> userCountData = new ArrayList<Item>();
		
		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection (dbUrl);
			Statement stmt = con.createStatement();
			System.out.println("Loading node data plase wait...");
			ResultSet rs = stmt.executeQuery(nodeQuery);
	
			String userName = "";
			Integer userCount = 0;
			
			int i = 0;
			while (rs.next()) {
				userName = rs.getString(1);
				
				userCount = rs.getInt(2);
				
				userCountData.add(new Item(userName, userCount));
			}
			//System.out.println(dbData.get(0)+"---------------------"+dbData.get(1));
			con.close();
		} //end try

		catch(ClassNotFoundException e) {
		e.printStackTrace();
		}
		
		return userCountData;
	}
	
	public void parseDBData(String username) throws SQLException{
		try {
		
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection (dbUrl);
			//Statement stmt = con.createStatement();
			
			//PreparedStatement stmt = con.prepareStatement(query);
			PreparedStatement stmt = con.prepareStatement(testQuery);
			
			stmt.setString(1, "%reading%");
			
			ResultSet rs = stmt.executeQuery();
	
			
			
			int i = 0;
			while (rs.next()) {
				sender = rs.getString(1);
				dbData.add(sender);
				receiver = rs.getString(2);
				dbData.add(receiver);
				System.out.println(++i + " - " +sender+" ------------------- "+receiver);
			 new Edge(sender, receiver);
			} //end while
			//System.out.println(dbData.get(0)+"---------------------"+dbData.get(1));
			con.close();
		} //end try

		catch(ClassNotFoundException e) {
		e.printStackTrace();
		}
	}	
}
