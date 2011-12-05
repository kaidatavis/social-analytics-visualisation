package imp.DataTier;

import java.sql.Connection;
/**
 * This is an interface with a a set of abstact classes to implement
 * @author Harish Muppalla
 *
 */
public interface DataInterfaceDAO {
	//Abstract methods for Data tansaction between the application and database
	public void insertTweetdataFinal(imp.centralApplication.TweetObject obj);
	//Method declaraton for database connection
	Connection dbconnection();

}
