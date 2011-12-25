/********************************************************************************
 *   File Name:		Node.java
 *   File Version:	1
 *   Created by:	Nakul Sharma
 *   Date Created:	Thu Dec 22, 2011
 ********************************************************************************/

/********************************************************************************
--| Module Body:
--|     Node
--|
--| Implementation Notes:
--|     This Class is used to create Nodes.
--|
--| Portability Issues:
--|		None
--|
 ********************************************************************************/
package JungLayouts;

public class Node {
	
	String Name;
	
	public Node(String Name) {
	this.Name = Name;
	toString();
	}
	
	public String toString() { 
	return Name; 
	}

}
