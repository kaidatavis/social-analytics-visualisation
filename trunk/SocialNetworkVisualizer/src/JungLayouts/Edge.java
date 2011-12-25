/********************************************************************************
 *   File Name:		Edge
 *   File Version:	1
 *   Created by:	Nakul Sharma
 *   Date Created:	Thu Dec 22, 2011
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

public class Edge extends SocialNetworkVisualizer{
	
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
