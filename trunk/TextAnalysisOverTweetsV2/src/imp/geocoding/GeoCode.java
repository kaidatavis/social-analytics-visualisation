package imp.geocoding;
import imp.centralApplication.TextAnalysis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * this class is a sub-system meant for finding the geographic 
 * coordinates of a physical address 
 * 
 * @author Harish Muppalla
 *
 */
public class GeoCode extends TextAnalysis {
	public GeoCode()
	{   
		
		
		
		
	}
	/**
	 * static main method
	 * @param args
	 */
	public static void main(String arg) throws SQLException{
		
		
		
		
		
	}
	/**
	 * This method used to find the geo-coorintes a given string
	 * @param str       physical address string
	 * @return Double[] latitude and longitude values
	 */
	public double[] findGeoCord(String str) {
		Connection conn1 = dbconnection();  
		    
		    
		    //list of query result variables 
		     double lat;
		     double lon;
		     String locationString;
		     String sublocationString;
		     
		    //longitude and location variables  
		        Double latitude = 0.0;
			    Double	longitude =0.0;
			    double[] ett = new double[2]; 
			    ett[0]=0.0; ett[1]=0.0;
			String place = str;
			if(place.length()==1){
				return ett;
			}
			if(place.equalsIgnoreCase("london")){
				latitude = 51.500152;
		     	longitude =-0.126236;
				
			}
			else if((place.length()>2)&&(place.substring(0, 3)).equals("ÜT:"))
			{
				
			String splitstr = place.substring(3);
			  String lonLatStr[] = splitstr.split(",");
			  
			    latitude = Double.parseDouble(lonLatStr[0]);
		     	longitude =Double.parseDouble(lonLatStr[1]);
			// System.out.println(latitude);
			 }
			else if((place.length()>6) && (place.substring(0, 7)).equals("iPhone:")){
				    String splitstr = place.substring(7);
				    String lonLatStr[] = splitstr.split(",");
		     	    latitude = Double.parseDouble(lonLatStr[0]);
			     	longitude =Double.parseDouble(lonLatStr[1]);
				    //System.out.println(latitude);

				
			 }
			else {  
			        
				    place = place.replace(" UK ","");
				    place = place.replace(" london ","");
				    String[] strToken = place.split(",");
				    int len = strToken.length;
				    for(int i = 0;i<len;i++){
				    	if(strToken[i].contains("."))
				    			{
				    		strToken[i]=strToken[i].replace(".","");
				    			}
				    	
				    }
				    		    //System.out.println(strToken[0]+len);
				    		    
				    		    if(strToken.length==1){
				    		    	strToken[0]=strToken[0].trim();
				    		    	ResultSet res1;
									try {
										
									    Statement stmt1;
										
										stmt1 = conn1.createStatement();
										strToken[0]= StringEscapeUtils.escapeSql(strToken[0]);
									
										res1 = stmt1.executeQuery("SELECT * from  locationdata where location like '%"+strToken[0]+"%' || sublocation like '%"+strToken[0]+"%'");
									
				    		    	
				    		    		 
				    		    	     if(res1.first()){
				    		    	    	 latitude =  res1.getDouble(2);
				    		    	    	 longitude = res1.getDouble(3);
				    		    	    	 locationString = res1.getString(4);
					    		    	     sublocationString = res1.getString(5);
					    		    	    // System.out.println("" +latitude+" "+longitude+" "+locationString+""+sublocationString);
				    		    	     }
				    		    	     
				    		    	     
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
				    		    	     
				    		    	     
					                
				    		    }
				    		    else {
				    		    	strToken[0]=strToken[0].trim();
				    		    	strToken[1]=strToken[1].trim();
				    		    	strToken[0]= StringEscapeUtils.escapeSql(strToken[0]);
									strToken[1]= StringEscapeUtils.escapeSql(strToken[1]);
				    		    	ResultSet res1;
									try {
											
									    Statement stmt1;
										
										stmt1 = conn1.createStatement();
										res1 = stmt1.executeQuery("SELECT * from  locationdata where location like '%"+strToken[0]+"%' || location like '%"+strToken[1]+"%' || sublocation like  '%"+strToken[0]+"%' || sublocation like '%"+strToken[1]+"%'");
									
				    		    	if(res1.getFetchSize()==1){
				    		    		 if(res1.first()){
				    		    			 latitude =  res1.getDouble(2);
				    		    			 longitude = res1.getDouble(3);
				    		    			 locationString = res1.getString(4);
				    		    			 sublocationString = res1.getString(5);
				    		    			// System.out.println("" +latitude+" "+longitude+" "+locationString+""+sublocationString);
				    		    	      }
			    		    	     }else{
				    		    	             while(res1.next())
					                             {   
				    		    		             lat = res1.getDouble(2);
				    		    		             lon = res1.getDouble(3);;
				    		    		             locationString = res1.getString(4);
				    		    		             sublocationString = res1.getString(5);
				    		    		             if(locationString.contains(strToken[0]) && locationString.contains(strToken[1]) ){
				    		    		            	  latitude =lat;
				    		    		            	  longitude =lon;
				    		    		             }else if(locationString.contains(strToken[0]) || locationString.contains(strToken[1]))
				    		    		             {
				    		    		            	  latitude =lat;
				    		    		            	  longitude =lon;
				    		    		             }else if(sublocationString.contains(strToken[0]) && sublocationString.contains(strToken[1]))
				    		    		             {
				    		    		            	  latitude =lat;
				    		    		            	  longitude =lon;
				    		    		             }else if(sublocationString.contains(strToken[0]) || sublocationString.contains(strToken[1]))
				    		    		             {
				    		    		            	  latitude =lat;
				    		    		            	  longitude =lon;
				    		    		             }		    		    		             
				    		    		              else
				    		    		             {
				    		    		            	  latitude =lat;
				    		    		            	  longitude =lon;
				    		    		             }
				    		    		
					               // System.out.println("" +lat+" "+lon+" "+locationString+""+sublocationString);
					                }
			    		    	     }
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
				    		    }
				    		    
			}
			
			
		double[] geoCorder = new double[2];
		geoCorder[0]= latitude;
		geoCorder[1]= longitude;
		
		try {
			conn1.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return geoCorder;
		
	}
	
	
	/**
	 * This method used for databse connection
	 * @param args
	 * @return Connection  
	 * 
	 */
	public Connection dbconnection() 
    {           Connection conn= null;
 	   try{
 	   //java.sql.PreparedStatement st;
		    String url = "jdbc:mysql://localhost/geodata";
		   			        String driver = "com.mysql.jdbc.Driver";
	        String userName = "root";
	        String password = "";
 	     Class.forName(driver).newInstance();
	        conn = DriverManager.getConnection(url,userName,password);
	      //  System.out.println("Connected to the database");
	         
	       
    
    }
    catch (Exception e) {
	    	System.out.println("connection failed" +e);
	    }
    return conn;
	
    }
	
	

}
