import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class dbConnector {
	
	 public dbConnector(){
			Connection conn = null;
			   try{
				   String url= " ";
				   
				url = "jdbc:mysql://localhost/tweet_db";
				String driver = "com.mysql.jdbc.Driver";
		        String userName = "root";
		        String password = "";
		        System.out.println("1");
			    Class.forName("com.mysql.jdbc.Driver").newInstance();
			    System.out.println("2");
			     conn = DriverManager.getConnection (url,userName,password);
		        System.out.println("Connected to the database");
		        Statement s = conn.createStatement ();
		        s.executeQuery ("SELECT username,touser FROM dataextraction");
		        ResultSet rs = s.getResultSet ();
		        int count = 0;
		        while (rs.next ())
		        {
		            String UserNameVal = rs.getString ("username");
		            SAVI.LUserList.add(UserNameVal);
		            String toUserNameVal = rs.getString ("touser");
		            SAVI.LtoUserList.add(toUserNameVal);
		            ++count;
		            
		        }
		        rs.close ();
		        s.close ();
		        System.out.println (count + " rows were retrieved");
		        conn.close();

			   }
			   catch(Exception e){
				   System.out.println("Not Connected to the database");
			   }
		}

}