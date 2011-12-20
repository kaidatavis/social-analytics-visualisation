package imp.sentiment;

import imp.centralApplication.TextAnalysis;
import java.lang.String;
import  java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * This class is sub-system, responsible for analysing the sentiment 
 * from a given text, this extends textAnalysis class.
 * 
 * @author Harish Muppalla
 */
public class SentiCalculate extends  TextAnalysis {

	
	public SentiCalculate() {

	}
	public static void main(String[] args) {
		
	}
	/**
	 * This method is meant for sentiment score calculation 
	 * @param st 
	 * @return double value of sentiment score 
	 */

	public  double scorecalculation(String st){
		String line= st;
		StringTokenizer token = new StringTokenizer(line," ");
		double Score =0.0;
		int count = 0;
		String value = null;
		Connection conn= dbconnection(); 
		while(token.hasMoreElements())
		{
			double posScore =0.0;
			double negScore =0.0;
			value = token.nextToken();
			//feching the individual score values of each token in a arraylist
			ArrayList<Double> al = fetchData(value, conn);
			posScore =   (Double) al.get(0);//positvie score of each tocken 
			negScore = -1*(Double) al.get(1);//negative score of each tocken 
			count+=1;// count of no of tokens wit hspecific score's assigned 
			Score += posScore+negScore;// sum of posive and negative score 
			al.clear();
		}
		// average of the sum of sccore and selected score values number
		double scoreCatch = Score/count;  
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return scoreCatch;
	}
	
	/**
	 * This method used to fetch positive and negative scores of each 
	 * token in the text.
	 * 
	 * @param value,conn1
	 * @return Arraylist 
	 */
	private  ArrayList<Double> fetchData(String value,Connection conn1) {
		// TODO Auto-generated method stub
		ArrayList<Double> al = new ArrayList<Double>();

		try{
			// ...
			Connection conn= conn1;     		   
			Statement stmt = conn.createStatement();
			String sql;
			String val = value;
			// if the value contains the special character like "'" ,building the specific sql query 
			if(val.contains("'")){
				sql= "SELECT pos,neg,word FROM sentiwordnet where word like '%"+StringEscapeUtils.escapeSql(val)+"#%'";
		     } 
            
			else{
				sql="SELECT pos,neg,word FROM sentiwordnet where word like '%"+StringHelper.escapeSQL(val)+"#%'";
			}

			ResultSet rs = stmt.executeQuery(sql);
			//System.out.println("The value "+rs.wasNull());  
			double posScore =0.0;
			double negScore =0.0;
			while(rs.next())
			{
				String strch = rs.getString(3);
				String strch1 = val.substring(0, 1);
				if(strch.startsWith(strch1))
				{
					posScore = rs.getDouble(1);
					negScore = rs.getDouble(2);
				}
			}
			al.add(posScore);
			al.add(negScore);
			
		}

		catch (SQLException e)
		{
			e.printStackTrace();
			System.exit(2);
		} 
		return al;
	}
	/**
	 * This methos is responsible for database connection 
	 * @return Connection
	 */
	public   Connection dbconnection() 
	{           Connection conn= null;
	try{
		//java.sql.PreparedStatement st;
		String url = "jdbc:mysql://localhost/sentimentsql";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root";
		String password = "";
		Class.forName(driver).newInstance();
		conn = DriverManager.getConnection(url,userName,password);

	}
	catch (Exception e) {
		System.out.println("connection failed" +e);
	}
	return conn;

	}
}
