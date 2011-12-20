package imp.centralApplication;

import java.sql.Connection;
/**
* This is an abstract class with an abstract method
* declared  for database connection. 
* 
* @author Harish Muppalla
*/
public abstract class TextAnalysis {
	// method declaration for database connection	
 public abstract Connection dbconnection();
}
