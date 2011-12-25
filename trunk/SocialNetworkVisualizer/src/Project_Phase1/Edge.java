package Project_Phase1;

import java.util.ArrayList;
import java.util.Hashtable;

public class Edge {
	
	static ArrayList<Edge> edgeList = new ArrayList<Edge>();
	
	//static Classes classObj = new Classes();
	final Node p, q;
	float dist, factor,cx,cy,pcx,pcy, pa,qa,pf,qf;		//q rot force;		//ctrl pt
	/**
	 * Edge Constructor puts nodes in adjMat 
	 * @param p - a node id
	 * @param q - a node id
	 */
	public Edge(String p, String q)
	{
		dist = 0f;		//set on init
		factor = 0f; 	//set on init
		cx = 0f;		//ctrl pt
		cy = 0f;		//ctrl pt
		pcx = 0f;		//ctrl pt
		pcy = 0f;		//ctrl pt
		edgeList.add(this);
		
		Hashtable temp = null;
		
		//if p not in nodeDict: Node(p)
		if(Classes.nodeDict.containsKey(p)==false)
		{
			Node nodeObj = new Node(p);
			
		}
		//if q not in nodeDict: Node(q)
		if(Classes.nodeDict.containsKey(q)==false)
		{
			Node nodeObj = new Node(q);
			
		}
		
		if(Classes.adjMat.containsKey(p)==false)
		{
			temp = new Hashtable();
			Classes.adjMat.put(p, temp);
		}
		if(Classes.adjMat.containsKey(q)== false)
		{
			temp = new Hashtable();
			Classes.adjMat.put(q, temp);
		}
				
		this.p =  Classes.nodeDict.get(p);
  		this.q =  Classes.nodeDict.get(q);
		
		pa = 0f;		//p angle, set on init
		qa = 0f;		//q angle, set on init
		pf = 0f;		//p rot force
		qf = 0f;		//q rot force
		
		((Hashtable)Classes.adjMat.get(p)).put(q, this);
		((Hashtable)Classes.adjMat.get(q)).put(p, this);
		
	}
	
	public float tanAngle(Node n)	//:#n is from
	{
		return Classes.pie2(n.angle+tanAngleRel(n));
	}
	
	public float tanAngleRel(Node n)	//:#n is from
	{
		if (n.equals(this.p))
		{
			return this.pa;
		}
		else 
		{
			return this.qa;
		}
	}
	
	public float edgeAngle(Node n)//#n is from
	{
		Node n1, n2;
		
		if(n.equals(this.p))
		{
			n1 = this.p;
		}
		else
		{
			n1 = this.q;
		}
		
		if(n.equals(this.q))
		{
			n2 = this.p;
		}
		else
		{
			n2 = this.q;
		}
				
		return Classes.pie2((float)Math.atan2((n2.y-n1.y),(n2.x-n1.x)));
	}
	
	public float diffAngle(Node n)	
	{
		return Classes.pie(Classes.pie(this.tanAngle(n))-Classes.pie(this.edgeAngle(n)));
	}
	
	public void setAngle(Node n, float angle)	
	{	
		if (n.equals(this.p))
		{
			this.pa = angle;
		}
		else
			this.qa = angle;
		
	}
	
	public void addForce(Node n, float f)	
	{
		if(n.equals(this.p))
		{
			this.pf += f;
		}
		else
		{
			this.qf += f;
		}
	}
	
	
}
