/********************************************************************************
 *   File Name:		SocialNetworkVisualizer.java
 *   File Version:	1
 *   Created by:	Nakul Sharma
 *   Date Created:	Thu Dec 22, 2011
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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;

import Project_Phase1.MainGUI;

import edu.uci.ics.jung.algorithms.filters.KNeighborhoodFilter.EdgeType;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AnimatedPickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.EditingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.LabelEditingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.PluggableGraphMouse;
import edu.uci.ics.jung.visualization.control.RotatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ShearingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.TranslatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.picking.PickedState;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import edu.uci.ics.jung.visualization3d.decorators.PickableVertexPaintTransformer;

public class SocialNetworkVisualizer {
	
	public static int userInputLayout;
	public static int userInputTweetCount;
	public static String searchKey;
	public static boolean searchFlag;
	
	final static int ISOMLayout = 1;
	final static int KKLayout = 2;
	final static int FRLayout = 3;
	final static int CircleLayout = 4;
	final static int LombardiLayout = 5;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		//Setting the Default User Choices
		searchFlag = false;
		userInputTweetCount = 100;
		searchKey = "";
		
		//Getting the User Choice for Layout
		getLayout();
		
		//Initiate Process for Selected Layout
		switch (userInputLayout){
		
			//if User selected any Jung Layout
			case ISOMLayout:
			case KKLayout:
			case FRLayout:
			case CircleLayout:
				
				/*Getting the Tweet Count from User, between [1 to 400].
				  By default Tweet Count is set to 100. */
				getTweetCount();
				
				/*Getting a Search Key from the User, by default
				  searchKey is set to "". */
				getSearchKey();
				
				/*Start processing based on the User Inputs.*/
				new GraphPlotter(userInputLayout);
				
				break;
				
	        //if User selected Lombardi Layout
			case LombardiLayout:
				javax.swing.SwingUtilities.invokeLater(new Runnable(){
					public void run() {MainGUI.createAndShowGUI();}});		
				break;
				
			default:
				System.out.println("Incorrect Chioce for Layout.");
		}
	}
	
	private static void getLayout(){
		
		 Scanner scan = new Scanner(System.in);
	 
	     System.out.println("Main Menu:");
	     System.out.println("Hit 1 for ISOM Layout");
	     System.out.println("Hit 2 for KK Layout");
	     System.out.println("Hit 3 for FR Layout");
	     System.out.println("Hit 4 for Circle Layout");
	     System.out.println("Hit 5 for Lombardi Layout");
	          
	     userInputLayout = scan.nextInt();
		
	}
	
	private static void getTweetCount(){
		
		Scanner scan = new Scanner(System.in);	 
		System.out.println("Enter Tweet Count: [1 to 400]");
		userInputTweetCount = scan.nextInt();
		
		}
	
	private static void getSearchKey(){
		
		String Input;
		
		Scanner scan = new Scanner(System.in);	 
		System.out.println("Do you want to search tweets with a keyword? [Y or N]");
		Input = scan.nextLine();
		System.out.println(Input);
		
		if(Input.matches("Y") || Input.matches("Yes") || Input.matches("Ya")){
			
			Scanner scaner = new Scanner(System.in);	 
			System.out.println("Enter the Keyword:");
			searchKey = scaner.nextLine();
			searchFlag = true;
		}
		else{
			System.out.println("No Keyword provided. Proceeding...");
			searchKey = ""; //Setting the Default Search Key, .i.e, ""
			searchFlag = false;
			
		}
	}
	
}