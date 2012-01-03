/********************************************************************************
 *   File Name:		NodeWeight.java
 *   File Version:	1
 *   Created by:	Nakul Sharma
 *   Date Created:	Thu Dec 22, 2011
 ********************************************************************************/

/********************************************************************************
--| Module Body:
--|     NodeWeight
--|
--| Implementation Notes:
--|     This Class is used calculate the Weight on Node/Edge.
--|
--| Portability Issues:
--|		None
--|
 ********************************************************************************/
package JungLayouts;

public class NodeWeight {
	
	public static int NodeInWeight;
	public static int NodeOutWeight;
	public static int NodeCombinedWeight;
	
	NodeWeight(final String NodeName){
		
		CalNodeInWeight(NodeName);
		CalNodeOutWeight(NodeName);
		CalNodeComWeight();
		
	}
	
	/** This function Calculates the Incoming Weight on the Node. **/
	private void CalNodeInWeight(String InNode){
		
		int Count = 0;
		
		for (int i = 0; i < GraphPlotter.userList.length; i++){
			
			if(GraphPlotter.userList[i] == InNode){
				Count++;
			}
		}
		
		NodeInWeight = Count;
	}

	/** This function Calculates the Outgoing Weight from the Node. **/
	private void CalNodeOutWeight(String OutNode){
		
		int Count = 0;
		
		for (int i = 0; i < GraphPlotter.toList.length; i++){
			
			if(GraphPlotter.toList[i] == OutNode){
				Count++;
			}
		}
		
		NodeOutWeight = Count;
	}
	
	/** This function Calculates the Combined Weight on the Node. **/
	private void CalNodeComWeight(){
		
		int Count = 0;
		Count = NodeInWeight + NodeOutWeight;
		NodeCombinedWeight = Count;
	}	
		
	/** This function returns the Incoming Weight on the Node. **/
	public int getNodeInWeight(){
		
		return NodeInWeight;
	}	
	
	/** This function returns the Outgoing Weight on the Node. **/
	public int getNodeOutWeight(){
		
		return NodeOutWeight;
	}	
	
	/** This function returns the Outgoing Weight on the Node. **/
	public int getNodeComWeight(){
		
		return NodeCombinedWeight;
	}	
	
	
}
