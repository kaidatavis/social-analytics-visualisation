import java.util.ArrayList;
import java.util.List;


public class ProcessData {
	
	
	
	public static String[] Uniquify(String[] S){
		
		String returnString[] = null;
		final List<String> StrList = new ArrayList<String>();
		for (int i = 0; i < S.length;i++){
			
				if(SearchInString(StrList,S[i]) == -1){
					
					//returnString[i] = S[i];
					StrList.add(S[i]);
				}
				
			}
		
	  returnString = new String[StrList.size()];
      for (int i = 0; i <StrList.size(); i++)
      {		
    	  returnString[i]= StrList.get(i);
      }
		return returnString;
		
		
	}
	
	public static int  SearchInString(List<String> SiS, String S){
		
		if(SiS.size() != 0){
		for (int i = 0; i < SiS.size(); i++){
			
			if (SiS.get(i) == S){
				
				return i;
			}
		}
	}
		return -1;
}

}
