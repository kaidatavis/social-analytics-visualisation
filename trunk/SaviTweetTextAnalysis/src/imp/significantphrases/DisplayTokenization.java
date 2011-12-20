package imp.significantphrases;


import imp.centralApplication.TextAnalysis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.StopTokenizerFactory;
import com.aliasi.tokenizer.Tokenization;
import com.aliasi.tokenizer.TokenizerFactory;
import com.aliasi.util.CollectionUtils;
/**
 * This class is a sub-system used to extract significant phrases 
 * froma given text.
 * 
 * @author Harish Muppalla
 */

public class DisplayTokenization extends TextAnalysis {
	/**
	 * static main method
	 * @param args
	 */
    public static void main(String[] args) {
    	DisplayTokenization dt =new DisplayTokenization ();
    	String ss = dt.phraseFinding(" hi  Am good, got job in Microsoft offered $40000 and relocating to California, peter is coming with me ,return back on 24/07/2011");
System.out.println(ss);
    }
    	
    /**
	 * This method phraseFinding used to extract phrases from a text, 
	 * by removing the list of words from stoplist in the given text 
	 * as primary task. and comparing the phrase in the phrases table
	 * to find the approprite matching of word. if it get mathes it will
	 * be added to the continues string of significant phrases.
	 *  
	 *  @param strtweeet
	 *          input text for annotation extraction
	 *  @return String
	 *          return the a continues string of phrases
	 *  
	 */	
    public String phraseFinding(String strtweet){
    	 Connection conn1 = dbconnection(); 
        String text = strtweet;
        String strRes = new String();
        // list of stop word defined to remove them from the given text 
        Set<String> stopSet = CollectionUtils.asSet("a","able","about","across","after","all","almost","also","am","among","an","and","any","good","Hi","hi",
        		"are","as","at","be","because","been","but","by","can","cannot","could","dear","did","Am",
        		"do","does","either","else","ever","every","for","from","get","got","had","has","have",
        		"he","her","hers","him","his","how","however","i","if","in","into","is","it","its",
        		"just","least","let","like","likely","may","me","might","most","must","my","neither",
        		"no","nor","not","of","off","often","on","only","or","other","our","own","rather",
        		"said","say","says","she","should","since","so","some","than","that","the","their",
        		"them","then","there","these","they","this","tis","to","too","twas","us","wants",
        		"was","we","were","what","when","where","which","while","who","whom","why","will",
        		"with","would","yet","you","your","can't","want","do","did","went","go","might",
        		"may","be","should","would","get","move","shall","will","knows","know","on","below",
        		"top","side","to.till","untill","at","on","for","ago","past","present","by","since","back",
        		"on","under","over","across","through","into","beside","next","towards","onto","front",
        		"after","already","during","finally","just","last","later","next","soon","now",
        		"always","every","never","often","rarely","usually","sometimes.except","like",
        		"between","as","around","among","times","off","save","outside","unlike","via","witj",
        		"without","during","but","plus","per","among","behind","before","following","along",
        		"inside","outside","round","much","some","thinks","makes","up","down","being","http","a",
        		"b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","x","y","z");
        //
        StandardAnalyzer analyzer
        = new StandardAnalyzer(Version.LUCENE_30);
       AnalyzerTokenizerFactory atok = new AnalyzerTokenizerFactory(analyzer,"foo");
        
	    //TokenizerFactory tok = IndoEuropeanTokenizerFactory.INSTANCE;
        TokenizerFactory  stok= new StopTokenizerFactory(new IndoEuropeanTokenizerFactory(),stopSet);
         // PorterStemmerTokenizerFactory ptok= new PorterStemmerTokenizerFactory(stok);
        Tokenization tokenization = new Tokenization(text,stok );

        for (int n = 0; n < tokenization.numTokens(); ++n) {
          
            String token = tokenization.token(n);
            Statement stmt1;
			try {
				stmt1 = conn1.createStatement();
				ResultSet res1 = stmt1.executeQuery("SELECT  list1 from  phrases where list1 = '"+StringEscapeUtils.escapeSql(token)+"'");
	            if(res1.next()){
	            	strRes = strRes + token + "  ";
	            }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
     try {
		dbconnection().close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return strRes;
    }
    /**
     * This method is responsible database connection 
     * @return Connection
     *         returns the connection variable for phrasedb database
     */
    
    public Connection dbconnection() 
    {           Connection conn= null;
 	   try{
 	   //java.sql.PreparedStatement st;
		    String url = "jdbc:mysql://localhost/phrasedb";
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