package Project_Phase1;

public class Vectors {
	
	float x,y;
	
	public Vectors(float[] x_or_pair){
		this.x = x_or_pair[0];
		this.y = x_or_pair[1];
	}
	
	public Vectors(float x_or_pair, float y){
		this.x = x_or_pair;
		this.y = y;
	}
	
	public float getItem(int key){
		if(key ==0)
			return x;
		
		return y;
	}
	
	public int len(){
		return 2;
	}
	
	public Vectors add(Vectors v){
		if(v!= null){
			return new Vectors(this.x + v.x, this.y + v.y);
		}
		
		return null;
	}
	
	public Vectors sub(Vectors v){
		if(v!= null){
			return new Vectors(this.x - v.x, this.y - v.y);
		}
		
		return null;
	}
	
	public Vectors mul(float factor){
		return new Vectors(this.x * factor, this.y * factor);	
	}
	
	public Vectors truediv(float factor){
		return new Vectors(this.x / factor, this.y / factor);	
	}
	
	public Vectors normalize(){
		int first[] = {0,0};
		int second[] = {(int)x,(int)y};
		float scale_factor = Classes.dist(first, second);
		return scale(1/scale_factor);	
	}
	
	public Vectors scale(float factor){
		return new Vectors(this.x * factor, this.y * factor);	
	}
	
	 public float angle()
	{
		 Classes classObj = new Classes();
		 return classObj.pie2((float)Math.atan2(y, x));
	}	
}
