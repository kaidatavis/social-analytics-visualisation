/********************************************************************************
 *   File Name:		SocialNetworkVisualizer.java
 *   File Version:	2
 *   Created by:	Nakul Sharma
 *   Date Created:	Tue Jan 3, 2012
 ********************************************************************************/

/********************************************************************************
--| Module Body:
--|     SocialNetworkVisualizer
--|
--| Implementation Notes:
--|     This Class is is the Main Entry for the Application and Displays the User 
--|		Menu for the user.
--|
--| Portability Issues:
--|		None
--|
 ********************************************************************************/
package JungLayouts;



//import Project_Phase1.MainGUI;

import javax.swing.JScrollPane;

public class SocialNetworkVisualizer extends Thread{
	
        public static JScrollPane scrollPane;
	public static int userInputLayout;
	public static int userInputTweetCount;
	public static String searchKey;
	public static boolean searchFlag;
	
	final static int ISOMLayout = 1;
	final static int KKLayout = 2;
	final static int FRLayout = 3;
	final static int CircleLayout = 4;
	final static int LombardiLayout = 5;
        
        
    public SocialNetworkVisualizer(JScrollPane scrollPane, boolean isSearchTweet){
        this.scrollPane = scrollPane;
        this.searchFlag = isSearchTweet;
    }
    
    public void run() {
        synchronized (SocialNetworkVisualizer.class){
		
		//Initiate Process for Selected Layout
		switch (userInputLayout){
		
			//if User selected any Jung Layout
			case ISOMLayout:
			case KKLayout:
			case FRLayout:
			case CircleLayout:
				
				new GraphPlotter(userInputLayout);
				break;

			default:
				System.out.println("Incorrect Chioce for Layout.");
		}
        }
    }
}