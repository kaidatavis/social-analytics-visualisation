import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class ProcessData {
	

	
	public static  List<String> newUniquify( List<String> SiS){
		
		String[] returnString;
		//ArrayList al = new ArrayList();
		//ArrayList<String> al = new ArrayList<String>();
		

		HashSet hs = new HashSet();
		hs.addAll(SiS);
		SiS.clear();
		SiS.addAll(hs);

		returnString = new String[SiS.size()];
	      for (int i = 0; i <SiS.size(); i++)
	      {		
	    	  returnString[i]= SiS.get(i);
		    	  
	      }
		
		
			return SiS;
		
	}
	
public static void getAverageWeight() {
	
	int[] In = new int[GraphPlotter.uCombinedList.length];
	int[] Out = new int[GraphPlotter.uCombinedList.length];
	int[] Combined = new int[GraphPlotter.uCombinedList.length];
		
		int AverageInWeight = 0;
		int count = 0;
		int AverageOutWeight = 0;
		
		
		for (int i = 0; i < GraphPlotter.uCombinedList.length;i++){
			count = 0;
			for(int j = 0; j < GraphPlotter.userList.length; j++){
				
				if(GraphPlotter.uCombinedList[i].matches(GraphPlotter.userList[j])){
					
					count++;
				}
			}
			In[i] = count;
					
		}
		
		for (int i = 0; i < GraphPlotter.uCombinedList.length;i++){
			count = 0;
			for(int j = 0; j < GraphPlotter.userList.length; j++){
				
				if(GraphPlotter.uCombinedList[i].matches(GraphPlotter.toList[j])){
					
					count++;
				}
			}
			Out[i] = count;
					
		}
		
			
	}	

}
