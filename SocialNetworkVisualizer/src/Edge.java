
public class Edge extends SocialNetworkVisualizer{
	
	int id;
	double weight; // should be private for good practice
	
	
	public Edge(int weight) {
		
	//this.id = MyNodes.edgeCount++; // This is defined in the outer class.
	this.weight = weight;
	//System.out.println(Integer.toString(this.id) + "   " + weight);
	}
	
	public String toString() { 
	return "E"+id; 
	}
	
	public static String getEdgeId(){
		
		GraphPlotter.edgeCount++;
		return "E" + Integer.toString(GraphPlotter.edgeCount);
		
	}

}
