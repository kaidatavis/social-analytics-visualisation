package imp.ner;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.util.Triple;



/** 
 * this class is a sub-system responsible for NER (Name Entity recognition).
 * which extracts 7 types of annotation from a given text, thwy are Location,
 * Organisation, Person, Money, Time, Date, Persentage. 
 * 
 * @author Harish Muppalla.
 */

public class NERextraction {
	//traing data file path for Annotation extraction
	String serializedClassifier = "classifiers/muc.7class.distsim.crf.ser.gz";
	// instantiation of a classifier
	AbstractSequenceClassifier<?> classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
	public   NERextraction()
	{
	}

	public static void main(String[] args) throws IOException {
	}

	@SuppressWarnings("null")
	/**
	 * static main method
	 * @param stTweet 
	 * @return String  
	 */
	public  String annotationGen(String stTweet)
	{      
		//listing the triple's of extracted annotation information 
		List<Triple<String, Integer, Integer>> l1 = classifier.classifyToCharacterOffsets(stTweet);
		//creating iterater listance to iterate the list 
		ListIterator<Triple<String,Integer,Integer>> li= l1.listIterator();
		String  st[][] = new String[15][2];
		int i = 0;
		while(li.hasNext()){

			Triple<String, Integer, Integer> tst =li.next();
			st[i][0]=tst.first();// annotation type
			st[i][1]=stTweet.substring(tst.second(), tst.third());// annotation value
			i++;
		}
		String str2 = " ";
		for(int j=0;j<st.length;j++){
			if(j< (st.length -1)){
				if(st[j][0]!=null)
					str2 = str2 + st[j][0]+"\t"+st[j][1]+"\n";
			}
		}
		return str2;
	}
}
