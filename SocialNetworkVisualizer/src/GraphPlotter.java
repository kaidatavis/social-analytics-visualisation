import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.graph.DirectedSparseGraph;


public class GraphPlotter {
	
	//Local Variables
	public static String[] userList;
	public static String[] toList;
	public static String[] quserList;
	public static String[] qtoList;
	public static String[] CombinedList;
	public static String[] uCombinedList;
	public static int edgeCount = 0;
	static Factory<Integer> vertexFactory;
	static Factory<String> edgeFactory;
	static DirectedSparseGraph<String, String> g;
	
	public static List<String> LUserList = new ArrayList<String>();
	public static List<String> LtoUserList = new ArrayList<String>();
	
	public static List<String> LLUserList = new ArrayList<String>();
	public static List<String> LLtoUserList = new ArrayList<String>();
	
	public static List<String> list = new ArrayList<String>();
	public static List<String> ulist = new ArrayList<String>();
	
	//Vertex & Edge Factories
	static int vf = 0, ef = 0;
	
	GraphPlotter(int layout){
		
		//Connecting to the Database
		new dbConnector();
		
		  userList = new String[LUserList.size()];
	      for (int i = 0; i <LUserList.size(); i++)
	      {		
	    	  userList[i]= LUserList.get(i);
	    	  
	      }

	      toList = new String[LtoUserList.size()];
	      for (int i = 0; i <LtoUserList.size(); i++)
	      {		
	    	  toList[i]= LtoUserList.get(i);
		    	  
	      }
	      
	      LLUserList = ProcessData.newUniquify(LUserList);
	      LLtoUserList = ProcessData.newUniquify(LtoUserList);
	      
	      quserList = new String[LLUserList.size()];
	      for (int i = 0; i <LLUserList.size(); i++)
	      {		
	    	  quserList[i]= LLUserList.get(i);
	    	  
	      }
	      
	      qtoList = new String[LLtoUserList.size()];
	      for (int i = 0; i <LLtoUserList.size(); i++)
	      {		
	    	  qtoList[i]= LLtoUserList.get(i);
	    	  
	      }		

	      list.addAll(LLUserList);
	      list.addAll(LLtoUserList);
	      
	      
	      ulist = ProcessData.newUniquify(list);
		 
		 uCombinedList = new String[ulist.size()];
		 
	      for (int i = 0; i <ulist.size(); i++)
	      {		
	    	  uCombinedList[i]= ulist.get(i);
	    	  
	      }

	    // Drawing the Graph
		createGraph();
		
		System.out.println("Visualizing...");
		
		//Opening a Visualizer
		new GraphVisualizer(g, layout);
		
	}
	
	private void createGraph(){
		

	  	//Node[] N = new Node[uCombinedList.length];
	  	Node[] N = new Node[uCombinedList.length];
		g = new DirectedSparseGraph<String, String>();
		for (int i = 0; i < uCombinedList.length;i++){
			
				N[i] = new Node(uCombinedList[i]);

				//g.addVertex(N[i].Name);	
				//System.out.println(NWeight[i].getNodeComWeight());
				//System.out.println(uCombinedList[i]);
		}
		
		
		@SuppressWarnings("unused")
		Edge[] E = new Edge[userList.length];
		for (int i = 0; i < userList.length;i++){
				
				g.addEdge(Edge.getEdgeId(), userList[i], toList[i]);

		}
		
		ProcessData.getAverageWeight();
		
	}
	
	
	public static void createFactories() {
		vertexFactory = new Factory<Integer>() {
			public Integer create() {
				return new Integer(vf++);
			}
		};
		edgeFactory = new Factory<String>() {
			public String create() {
				return "" + ef++;
			}
		};
	}

}
