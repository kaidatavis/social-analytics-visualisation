package Project_Phase1;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class Node {
	final String id;
	float x,y,rf,fx,fy,tx,ty,angle, aIncrement;
	int degree, curTan;
	
	static ArrayList<Node> nodeList = new ArrayList<Node>();
//	static Classes classObj = new Classes();
	/**
	 * Node Constructor
	 * sets up all the Node variable
	 * @param id - a String containing the name of the node
	 */
	public Node(String id)
	{
		this.id = id;
		x = 0f;
		y = 0f;
		rf = 0f;
		fx = 0f;
		fy = 0f;
		tx = 0f;		//tangential
		ty = 0f;		//tangential
		degree = 0;	//assigned on init
		curTan = -1;
		angle = 0f;
		aIncrement = 0;	//assigned on init
		Classes.nodeDict.put(id, this);
		nodeList.add(this);
	}
	/**
	 * 
	 * @return ArrayList<Node> containing node neighbours
	 */
	public ArrayList<Node> nodes()
	{
		Object value = Classes.adjMat.get(this.id);
		
		ArrayList<Node> AllNeighbours = null;
		if(value instanceof Hashtable){
					 
			//for(Iterator lo_Iterator = ((HashMap)value).keySet().i
			
			AllNeighbours = new ArrayList<Node>();
			
			Set<String> keySet = ((Hashtable)value).keySet();
			
			for(String key : keySet){				
				AllNeighbours.add(Classes.nodeDict.get(key));
			}
		}
	
		return AllNeighbours;
	}
	
	public float nextTanAngle()
	{	this.curTan+=1;
		return this.aIncrement*this.curTan;
	}
	
	public float tanAngle(Node n)	//:#n is to
	{
		return Classes.getEdge(this,n).tanAngle(this);
	}
	
	public float tanAngleRel(Node n)	//:#n is to
	{
		return Classes.getEdge(this ,n).tanAngleRel(this);
	}
	
	public float edgeAngle(Node n)//#n is to
	{
		return Classes.getEdge(this ,n).edgeAngle(this);
	}
	
	public float diffAngle(Node n)	//:#n is to
	{
		return Classes.getEdge(this,n).diffAngle(this);
	}
	
	public void setAngle(Node n, Float angle)	//:#n is to
	{
		Classes.getEdge(this,n).setAngle(this, angle);
	}
	
	public void addForce(Node n, float f)	//:#n is to
	{
		Classes.getEdge(this ,n).addForce(this, f);
	}
	@Override
	public boolean equals(Object obj) {

		if(obj == null)
			return false;
		
		if(obj instanceof Node){
			return this.id.equals(((Node)obj).id);
		}
		return false;
	}
	

}
