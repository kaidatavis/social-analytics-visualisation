package imp.centralApplication;

import imp.significantphrases.DisplayTokenization;
import imp.geocoding.GeoCode;
import imp.ner.NERextraction;
import imp.sentiment.SentiCalculate;
/**
* This class is meant to implement sigleton design pattern,
* provides single instane in return to the callling classes.
* it provides getter method to access information extracted 
* for a given text through  text analysis conponents like 
* GeoCode, SentiCalculate, DisplayTokenization and NERextraction.
* 
* 
* @author Harish Muppalla
*/
public class TextAnalysisComponents {
	//unigue instance variable of TextAnalysisComponents
	private static TextAnalysisComponents uniqueIns;
	// declaration of concrete classes text anlysis sub-systems
	 NERextraction ner;
     SentiCalculate scal;
     DisplayTokenization disp;
     GeoCode gcode;
	private TextAnalysisComponents(){
		  gcode = new GeoCode();
		  ner = new NERextraction();
	      scal = new SentiCalculate();
	      disp = new DisplayTokenization();
	}
	/**
	 * static method to return single unique instance,
	* lasy instantiation of a sigleton design pattern 
	* 
	* @return  TextAnalysisComponents
	*           unique instance 
	*/
	public static  TextAnalysisComponents getInstance(){
		if(uniqueIns == null)
		{
			uniqueIns = new TextAnalysisComponents();
		}
		return uniqueIns;
	}
	/**
	 * Getter method to get access findGeoCord method 
	 * to feths the latitude and longitude coordinate 
	 * values from a given string of place
	 * @param str
	 *     physical address
	 * @return double[]
	 *         value of latitude and longitude
	 */
	public double[] getGeoCode(String str){
		
		
		
		return gcode.findGeoCord(str);
		
	}
	/**
	 * Getter method to get access annotationGen method 
	 * to fetch annotations from a given string of text
	 * @param str1
	 *         tweet text 
	 * @return String
	 *         string of annotations
	 */
	public String  getAnnotations(String str1){
		
		return  ner.annotationGen(str1);
		
	}
	/**
	 * Getter method to get access getSentiment method 
	 * to find out sentiment score from a given string
	 * of tweet text
	 * @param str3
	 *        tweet text
	 * @return double
	 *         value sentiment scores
	 */
	public double getSentiment(String str3){
		
			
		return scal.scorecalculation(str3);
		
		
	}
	/**
	 * Getter method to get access getSignificantphrases method 
	 * to fetch the extracted phrases from a string of text 
	 * @param str4
	 *        tweet text 
	 * @return String 
	 *         continious text of significant phrases
	 */
	public String getSignificantphrases(String str4){
		
		return disp.phraseFinding(str4);
		
		
	}
	
	
}
