/********************************************************************************
 *   File Name:		Edge
 *   File Version:	2
 *   Created by:	Nakul Sharma
 *   Date Created:	Tue Jan 3, 2012
 ********************************************************************************/

/********************************************************************************
--| Module Body:
--|     Edge
--|
--| Implementation Notes:
--|     This Class is used to create Edges and extends the Main Class.
--|
--| Portability Issues:
--|		None
--|
 ********************************************************************************/
package JungLayouts;

import javax.swing.JScrollPane;

public class Edge{
	
	int number;
	double EdgeWeight;
	
	/*******************
	*   Constructor   *
	*******************/	
	public Edge(int weight) {
		
		this.EdgeWeight = weight;
	}
	
	/** toString Method **/
	public String toString() { 
		
		return "E" + Integer.toString(number); 
	}
	
	/** Get Method **/
	public static String getEdgeId(){
		
		GraphPlotter.edgeCount++;
		return "E" + Integer.toString(GraphPlotter.edgeCount);
		
	}

}
