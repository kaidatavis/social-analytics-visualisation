/********************************************************************************
 *   File Name:		ToolTipView.java
 *   File Version:	1
 *   Created by:	Nakul Sharma
 *   Date Created:	Thu Dec 22, 2011
 ********************************************************************************/

/********************************************************************************
--| Module Body:
--|     ToolTipView
--|
--| Implementation Notes:
--|     This Class is used to Display the Tooltip on Mouse Over
--|
--| Portability Issues:
--|		None
--|
 ********************************************************************************/
package JungLayouts;
import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class ToolTipView<V> implements Transformer<String, String> {

	 public String transform(String s) {
		 
		 String toReturn = "Tweet Text can't be fetched.";
		 
		 for(int i = 0; i < GraphPlotter.quserList.length; i++){
			 
			 if(GraphPlotter.quserList[i].matches(s)){
				 
				 toReturn = GraphPlotter.UserTweets.get(i);
				 return toReturn;
				 
			 }else if(i < GraphPlotter.qtoList.length && GraphPlotter.qtoList[i].matches(s)){
					 
					 if(GraphPlotter.qtoList[i].matches(s)){
						 
						 toReturn = GraphPlotter.UserTweets.get(i);
						 return toReturn;
					 }
				 }
			 else{
				 
				 toReturn = "Tweet Text can't be fetched.";
			 }	 
		 }
		 return toReturn;
             
	}


}
