/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype3;

import java.sql.*;
import java.util.*;
/**
 *
 * @author Nantia
 */
public class MySQLAccess {
    private Connection connect = null;
    private Statement statement = null;
    private Statement dbStatement = null;
    private ResultSet resultSet = null;
    private String conString = "jdbc:mysql://localhost/twitter?user=root&password=password";
    
    public Statement connectDB() throws Exception{
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager.getConnection(conString);
            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            //searchDatabase(statement);
            // Result set get the result of the SQL query
        } catch (SQLException s){
            System.out.println("SQL Error: " + s.toString() + " " + s.getErrorCode() + " " + s.getSQLState());
            s.printStackTrace();
        } catch (Exception e){
            System.out.println("Error: " + e.toString() + " " + e.getMessage());
            e.printStackTrace();
        }
        return statement;
    }
    
    public String[] getPopular() throws Exception{
        ArrayList<String> myPopular = new ArrayList<String>();
        String[] myPopulars;
        dbStatement = connectDB();
        ResultSet rec = dbStatement.executeQuery("Select * From popoularSearch ORDER BY count DESC");
        while (rec.next()){
            myPopular.add(rec.getString(2));
        }
        myPopulars = myPopular.toArray(new String[myPopular.size()]);
        close();
        return myPopulars;
    }
    
    public double[][] findPoints(String search) throws Exception{
        ArrayList <Double> listLat = new ArrayList<Double>();
        ArrayList <Double> listLon = new ArrayList<Double>();
        double [][] findPoints;
        double lat, lon;
        dbStatement = connectDB();        
        ResultSet rec = dbStatement.executeQuery("Select * From tweetalldata WHERE " + search + " ORDER BY tweet_id");
        while (rec.next()){
        listLat.add(rec.getDouble(10));
        listLon.add(rec.getDouble(11));
        }
        findPoints= new double[listLat.size()][2];
        for (int i=0; i < listLat.size(); i++){
            lat = listLat.get(i);
            lon = listLon.get(i);
            findPoints[i][0] = lat;
            findPoints[i][1] = lon;
         }
        close();
        return findPoints;
    }
    
    public String[][] findTweets(String search) throws Exception{
        ArrayList <String> myTweet = new ArrayList <String> ();
        ArrayList <String> myUser = new ArrayList <String> ();
        ArrayList <String> myPicture = new ArrayList <String> ();
        ArrayList <String> myDate = new ArrayList <String> ();
        ArrayList<Double> myLatitude = new ArrayList<Double>();
        ArrayList<Double> myLongitute = new ArrayList<Double>();
        
        String [][] myDetails;
        dbStatement = connectDB();
        ResultSet rec = dbStatement.executeQuery("Select * From tweetalldata WHERE " + search + " ORDER BY tweet_id");
        while (rec.next()){
            myPicture.add(rec.getString(9));
            myTweet.add(rec.getString(15));
            myUser.add(rec.getString(3));
            myDate.add(rec.getString(8));
            myLatitude.add(rec.getDouble(10));
            myLongitute.add(rec.getDouble(11));        
        }
        myDetails = new String[myTweet.size()][6];
        for (int i=0 ; i < myTweet.size() ; i++){
            myDetails [i][0] = myUser.get(i);
            myDetails [i][1] = myDate.get(i);
            myDetails [i][2] = myPicture.get(i);
            myDetails [i][3] = myTweet.get(i);
            myDetails [i][4] = myLatitude.get(i).toString();
            myDetails [i][5] = myLongitute.get(i).toString();
            
        }
        close();
        return myDetails;
    }
    
    public void setPopular(String mySearch) throws Exception{
        int myCount = 0;
        String query;
        dbStatement = connectDB();
        ResultSet rec = dbStatement.executeQuery("Select * From popoularSearch WHERE popularName = '" + mySearch + "'");
        if (!rec.next()){
            query = "INSERT INTO popoularSearch(popularName, count) values ('" + mySearch + "', 1 )";
            PreparedStatement preparedStmt = connect.prepareStatement(query);
            preparedStmt.executeUpdate();
        } else {
            do{
                myCount = rec.getInt(3) + 1;
                query = "UPDATE popoularSearch SET count = " + myCount + " WHERE popularName = '" + mySearch + "'";
                PreparedStatement preparedStmt1 = connect.prepareStatement(query);
                preparedStmt1.executeUpdate();
            }while (rec.next());
        }
        close();
    }
    
    private void close() {
	try {
            if (resultSet != null) {
		resultSet.close();
            }
            if (statement != null) {
		statement.close();
            }
            if (connect != null) {
		connect.close();
            }
	} catch (Exception e) {

        }
    }
}


