package lombardi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

public class MainGUI extends JFrame{

	Lombardi LombardiContentPane;
	JProgressBar progressBar;
	//private String[] GraphOptions = {"with n Nodes", "N+1 Nodes"};
	JComboBox comboBox = null;
	
	MySqlConnection sqlConn;
	
	MainGUI(){
		JPanel lmbMainPane = new JPanel();
		LombardiContentPane = new Lombardi();
		LombardiContentPane.setBackground(Color.WHITE);
//		Lombardi lmbBottomPane = new Lombardi();
//		lmbBottomPane.setBackground(Color.RED);
		lmbMainPane.setLayout(new BorderLayout());
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		
		LombardiContentPane.setBorder(new TitledBorder("Lombardi Graph"));
		LombardiContentPane.setOpaque(true);
		
//		LombardiContentPane.setSize(900,900);
		LombardiContentPane.setPreferredSize(new Dimension(2010,2010));
		
//		JScrollPane pictureScrollPane = new JScrollPane(LombardiContentPane);
		JScrollPane pictureScrollPane = new JScrollPane();
		pictureScrollPane.setViewportView(LombardiContentPane);
		//pictureScrollPane.addComponentListener(l)(LombardiContentPane);
//		pictureScrollPane.setPreferredSize(new Dimension(2000, 2000));
//		pictureScrollPane.setViewportBorder(BorderFactory
//				.createLineBorder(Color.black));
	//	pictureScrollPane.setd
		

//	        
//	    pictureScrollPane.setMinimumSize(minimumSize);
//	    lmbBottomPane.setMinimumSize(new Dimension(800,100));
		
		
        
		
		
		
		
		lmbMainPane.add(pictureScrollPane, BorderLayout.CENTER);
	//	lmbMainPane.add(new JScrollPane(LombardiContentPane), BorderLayout.CENTER);
		
		
		
		//ProgressBar progBar = new ProgressBar();
		progressBar = new JProgressBar();
		
	//	progressBar.setPreferredSize(new Dimension(99,20));
		progressBar.setStringPainted(true); 
		
		JButton startButton = new JButton("Generate Graph");
		
		 startButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				
				Thread t = new Thread(){
					
					public void run(){
						synchronized(MainGUI.class){
							LombardiContentPane.reset();
							LombardiContentPane.setSw(1000);
							LombardiContentPane.setSh(1000);
							LombardiContentPane.setShuffleSamples(20);
							LombardiContentPane.setMaxIters(600);
							Item selectedItem = (Item)comboBox.getSelectedItem();
							String userName = selectedItem.getUserName();
							try {
								sqlConn.parseDBData(userName);							
								LombardiContentPane.doo();
								System.out.println("selected value " + userName);
							} catch (SQLException e1) {
								
								e1.printStackTrace();
							}
						}
						
					}
					
				};
				
				t.start();
				
				
				
				
			}
		});
		// populate combobox
		
		sqlConn = new MySqlConnection();
		
		try {
			ArrayList<Item> userData = sqlConn.getNodeCount();
			
			comboBox = new JComboBox(userData.toArray());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		southPanel.add(comboBox);
		southPanel.add(startButton);
		southPanel.add(progressBar);
		
		
		
		southPanel.setBorder(new TitledBorder("Progress Bar"));
		lmbMainPane.add(southPanel, BorderLayout.SOUTH);

	    add(lmbMainPane, BorderLayout.CENTER);
	    
		
	
	}
	
	
	private static void createAndShowGUI() {
		MainGUI frame = new MainGUI();
		frame.setTitle("Test Area");
		frame.setLocationRelativeTo(null); // Frame Location
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(1280, 800));
		frame.setResizable(false);
		frame.setLocation(1, 1);
		frame.setVisible(true);
	//	frame.LombardiContentPane.setProgressBar(frame.progressBar);

	}
	
  public static void main(String[] args) {
	  	

	
	 javax.swing.SwingUtilities.invokeLater(new Runnable() {
         public void run() {
             createAndShowGUI();
         }
     });

  }
}


