package Project_Phase1;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Classes {
	
	
	static Hashtable adjMat = new Hashtable();
	static Hashtable<String,Node> nodeDict = new Hashtable<String,Node>();
	
	public static ArrayList<Node> nodes()
	{	
		return Node.nodeList;
	}
	
	public static ArrayList<Edge> edges()
	{	
		return Edge.edgeList;
	}
	
	public static int factorial(int n)
	{
		if (n == 0) 
			return 1;
		else 
			return n * factorial(n - 1);
	}
	
	public static Edge getEdge(Node p, Node q)
	{
		Hashtable tmp = null;
		tmp = (Hashtable) adjMat.get(p.id);
		return (Edge) tmp.get(q.id);
		
	}
	/////dist
	public static float dist(Node n1, Node n2)
	{
		return (float)Math.sqrt(dist2(n1,n2));
	}
	
	public static float dist2(Node n1, Node n2)
	{
		return (n1.x-n2.x)*(n1.x-n2.x)+(n1.y-n2.y)*(n1.y-n2.y);
	}
	
	public static float dist(int[] n1, int[] n2)
	{
		return (float)Math.sqrt(dist2(n1,n2));
	}
	
	public static float dist2(int []n1, int[] n2)
	{
		return (n1[0]-n2[0])*(n1[0]-n2[0])+(n1[1]-n2[1])*(n1[1]-n2[1]);
		
	}
	
	
	public static float dist(float[] n1, float[] n2)
	{
		return (float)Math.sqrt(dist2(n1,n2));
	}
	
	public static float dist2(float []n1, float[] n2)
	{
		return (n1[0]-n2[0])*(n1[0]-n2[0])+(n1[1]-n2[1])*(n1[1]-n2[1]);
	}
	
	public static float dist(float[] n1, Node n2)
	{
		return (float)Math.sqrt(dist2(n1,n2));
	}
	
	public static float dist2(float []n1,Node n2)
	{
		return (n1[0]-n2.x)*(n1[0]-n2.x)+(n1[1]-n2.y)*(n1[1]-n2.y);
	}
/////dist end
	public static float fromToAngle(final Vectors f, final Node t)
	{
		Vectors nodeVector = new Vectors(t.x, t.y);
		
		return (float)nodeVector.sub(f).angle();		
	}
	
	public static float pie(float rad)	//:#-180 t 180
	{	
		rad = pie2(rad);
		if (rad<=Math.PI)
		{
			return rad;
		}
		else 
		{
			return (float)(rad-2*Math.PI);
		}
		
	}
	
	public static float pie2(float rad)//:#0 to 360
	{
		return (float)(rad%(2*Math.PI));
	}
	
	public static Vectors intersection(float[] p1,Node p2,float[] p3,Node p4)
	{
		float x1,x2,x3,x4; 
		x1 = p1[0];
		x2 = p2.x;
		x3 = p3[0];
		x4 = p4.x;
		float y1,y2,y3,y4;
		y1 = p1[1];
		y2 = p2.y;
		y3 = p3[1];
		y4 = p4.y;
		float x = ((x1*y2-y1*x2)*(x3-x4)-(x1-x2)*(x3*y4-y3*x4))/((x1-x2)*(y3-y4)-(y1-y2)*(x3-x4));
		float y = ((x1*y2-y1*x2)*(y3-y4)-(y1-y2)*(x3*y4-y3*x4))/((x1-x2)*(y3-y4)-(y1-y2)*(x3-x4));
		return   new Vectors(x,y);
	}
	
	public static Vectors intersection(Node p1,float[] p2,Node p3,float[] p4)
	{
		float x1,x2,x3,x4; 
		x1 = p1.x;
		x2 = p2[0];
		x3 = p3.x;
		x4 = p4[0];
		float y1,y2,y3,y4;
		y1 = p1.y;
		y2 = p2[1];
		y3 = p3.y;
		y4 = p4[1];
		float x = ((x1*y2-y1*x2)*(x3-x4)-(x1-x2)*(x3*y4-y3*x4))/((x1-x2)*(y3-y4)-(y1-y2)*(x3-x4));
		float y = ((x1*y2-y1*x2)*(y3-y4)-(y1-y2)*(x3*y4-y3*x4))/((x1-x2)*(y3-y4)-(y1-y2)*(x3-x4));
		return   new Vectors(x,y);
	}
	
	/**
	 * reads file to parse nodes and edges
	 * @param fileName
	 */
	public void parseFile(String fileName)
	{
		this.adjMat.clear();
		this.nodeDict.clear();
		
		boolean exists = (new File(fileName)).exists();
		if(exists)
		{
			System.out.println("File Exists!");
		}
		else
		{
			System.out.println("File not Found!");
			System.exit(0);
		}
		FileReader file;
		Scanner scan;
		try
		{
			file = new FileReader(fileName);
			scan = new Scanner(file);
			scan.useDelimiter("\n");
			while(scan.hasNext())
			{
				String line = scan.nextLine().trim();
				String[] bits = line.split("\n");
				
				for(int i =0; i< bits.length; i++)
				{
					String myreg = "(\\w+) -- (\\w+)";
					Pattern pattern = Pattern.compile(myreg,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
					Matcher match = pattern.matcher(bits[i]);
					
					if (match.find())
				    {
				        System.out.println("valid");
				        
					    String re1="([a-z0-9]+)";	// Any Single Word Character (Not Whitespace) 1
					    String re2=".*?";	// Non-greedy match on filler
					    String re3="([a-z0-9]+)";	// Any Single Word Character (Not Whitespace) 2

					    pattern = Pattern.compile(re1+re2+re3,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
					    match = pattern.matcher(bits[i]);
					    if (match.find())
					    {
					        String p=match.group(1);
					        String q=match.group(2);
					        System.out.println("p = "+p.toString()+"; q = "+q.toString());
					       //
					        Edge edgeObj = new Edge(p, q);
					       
					    }
				    }
					
				}// end of for
				
			}// end of while
			
			file.close();
			//System.out.println(nodeDict); 
		}
		catch(FileNotFoundException e)
		{
			System.out.println("FILE NOT FOUND!!!");
			System.out.println("Provide a valid File Name...");
			
		} catch (IOException e)
		{
			
			e.printStackTrace();
		}
					
	}
		
	public static ArrayList<Float> permutations(ArrayList<Float> iterable, int...r1)	//:#from py site
	{
		ArrayList<Float> pool = new ArrayList<Float>();
		
		PermutationGenerator x = new PermutationGenerator (iterable.size());
		int[] indices;
		while (x.hasMore ()) {
			  
			  indices = x.getNext ();
			  for (int i = 0; i < indices.length; i++) {			    
				  pool.add(iterable.get(indices[i]));
			  }
			}
		
		return pool;
	} 
	
	public static ArrayList<Vectors> bezier(Vectors p0,Vectors p1,Vectors p2,Vectors p3){
		
		int steps = 30;
		float t = 1/steps;
		float temp = t*t;
		
		Vectors f = p0;
		
				
		Vectors fd =   (p1.sub(p0)).mul(3 * t);
		Vectors fdd_per_2 = (p0.sub(p1.mul(2)).add(p2)).mul(temp * 3);
		Vectors fddd_per_2 = p1.sub(p2).mul(3).add(p3).sub(p0).mul(3 * temp * t);
		
		Vectors fddd  = fddd_per_2.mul(2);
		Vectors fdd =  fdd_per_2.mul(2);		
		Vectors fddd_per_6 = fddd_per_2.truediv(3);
		
		ArrayList<Vectors> points = new ArrayList<Vectors>();
		
		for(int i = 0; i < steps; i++){
			points.add(f);
			f = f.add(fd).add(fdd_per_2).add(fddd_per_6);
			fd = fd.add(fdd).add(fddd_per_2);
			fdd = fdd.add(fddd);
			fdd_per_2 = fdd_per_2.add(fddd_per_2);
		}
		points.add(f);
		
		return points;
	}
		
}
