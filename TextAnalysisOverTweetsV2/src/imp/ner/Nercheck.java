package imp.ner;

public class Nercheck {
 public static void main(String[] arg){
	 NERextraction ner = new NERextraction();
	 String ss = ner.annotationGen("hi  Am good, got job in Microsoft offered $40000 and relocating to California, Mr.peter is coming with me ,return back next month’");
	 System.out.println(ss);
	
	}
 

}
