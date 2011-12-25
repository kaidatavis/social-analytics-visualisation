
/*******************************************************************************
 *   File Name:		GraphPlotter.java
 *   File Version:	1
 *   Created by:	Nakul Sharma
 *   Date Created:	Thu Dec 22, 2011
 *******************************************************************************/

/*******************************************************************************
--| Module Body:
--|     GraphPlotter
--|
--| Implementation Notes:
--|     This Class is used to create the graph with the selected layout. Also,
--|		this Class is used to create Nodes and Edges.
--|
--| Portability Issues:
--|		None
--|
 *******************************************************************************/
package JungLayouts;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

public class GraphPlotter {
	
	//Declaring a Graph to be used to represent Nodes & Edges.
	static DirectedSparseGraph<String, String> graph;
	
	//Local Variables
	public static int edgeCount = 0;
	public static String[] userList, toList, quserList, qtoList, CombinedList, uCombinedList, UserIconName;

	//Array of Nodes & Edges
	public static Node[] N;
	public static Edge[] E;
	
	//List to be used for data processing
	public static List<String> UserIconNList = new ArrayList<String>();
	
	public static List<String> UserTweets = new ArrayList<String>();
	
	public static List<String> LUserList = new ArrayList<String>();
	public static List<String> LtoUserList = new ArrayList<String>();
	
	public static List<String> LLUserList = new ArrayList<String>();
	public static List<String> LLtoUserList = new ArrayList<String>();
	
	public static List<String> list = new ArrayList<String>();
	public static List<String> ulist = new ArrayList<String>();

	/*******************
	*   Constructor   *
	*******************/
	GraphPlotter(int layout){
		
		//Connecting to the Database
		new dbConnector();
		
		//Converting Consolidated User List into Array of String
		userList = new String[LUserList.size()];
		for (int i = 0; i <LUserList.size(); i++)
		{		
			userList[i]= LUserList.get(i);	  
		}

		//Converting Consolidated toUser List into Array of String
	    toList = new String[LtoUserList.size()];
	    for (int i = 0; i <LtoUserList.size(); i++)
	    {		
	    	toList[i]= LtoUserList.get(i);
	    }
	      
	    //Removing the Duplicates from User ton toUser Arrays
	    LLUserList = ProcessData.newUniquify(LUserList);
	    LLtoUserList = ProcessData.newUniquify(LtoUserList);
	      
		//Converting Unique User List into Array of String
	    quserList = new String[LLUserList.size()];
	    for (int i = 0; i <LLUserList.size(); i++)
	    {		
	    	quserList[i]= LLUserList.get(i);
	    }
	      
		//Converting Unique toUser List into Array of String	    
	    qtoList = new String[LLtoUserList.size()];
	    for (int i = 0; i <LLtoUserList.size(); i++)
	    {		
	    	qtoList[i]= LLtoUserList.get(i);
	    }		

	    //Adding both the lists to create Nodes
	    list.addAll(LLUserList);
	    list.addAll(LLtoUserList);
	      
	    //Removing the Duplicates from final consolidated list
	    ulist = ProcessData.newUniquify(list);
		 
	    //Converting final consolidated list into Array
		uCombinedList = new String[ulist.size()];
		for (int i = 0; i <ulist.size(); i++)
	    {		
			uCombinedList[i]= ulist.get(i);
		}

		try {
			System.out.println("Extracting Images... Please Wait!");
			
			//Extract user's profile images from url's.
			ProcessData.getAndsaveImages(dbConnector.returnString);
			
			//Set default twitter image for user's not having profile pic
			ProcessData.getAndsaveRestImages(uCombinedList);
			
			System.out.println("Done!");
			
		} catch (IOException e) {
			System.out.println("Problem: Error in Extracting user Images");
		}
		
		System.out.println("Creating the Graph...");
		
		// Drawing the Graph
		createGraph();
		
		System.out.println("Visualizing...");
		
		//Opening a Visualizer
		new GraphVisualizer(graph, layout);
		
	}
	
	/*****************************************
	This method is used to create the graph
	based on the Data collected from Database
	******************************************/
	private void createGraph(){
		
		//Creating a graph
		graph = new DirectedSparseGraph<String, String>();
		
		//Creating an array of Node objects
	  	N = new Node[uCombinedList.length];
	  	
	  	//Creating Node objects
		for (int i = 0; i < uCombinedList.length;i++){
			
				//N[i] = new Node(uCombinedList[i]);
		}
		
		//Creating an array of Edge objects
		E = new Edge[userList.length];
		
		//Adding nodes & edges to the Graph
		for (int i = 0; i < userList.length;i++){
				
			graph.addEdge(Edge.getEdgeId(), userList[i], toList[i]);
		}		
	}
}
