/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lombardi;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.SQLException;
import javax.swing.JScrollPane;

/**
 *
 * @author Desktop
 */
public class LombardiView extends Thread {

    JScrollPane scrollPane;

    String searchTweet;
    int searchMonth;
    boolean isSearchTweet;
    
    public LombardiView(JScrollPane scrollPane, boolean isSearchTweet){
        this.scrollPane = scrollPane;
        this.isSearchTweet = isSearchTweet;
    }
    
    public void run() {
        synchronized (LombardiView.class) {

            MySqlConnection sqlConn = new MySqlConnection();;
            Lombardi LombardiContentPane = new Lombardi();
            LombardiContentPane.setPreferredSize(new Dimension(1500, 1500));
            LombardiContentPane.setBackground(Color.WHITE);
            LombardiContentPane.reset();    //reset all variable in lombardi
            LombardiContentPane.setSw(1000);
            LombardiContentPane.setSh(1000);
            LombardiContentPane.setShuffleSamples(20);
            LombardiContentPane.setMaxIters(600);

            scrollPane.setViewportView(LombardiContentPane);    //scrolpane obj to display lomb with scrolls
            scrollPane.setEnabled(false);
            
            try {
                if(isSearchTweet)
                    sqlConn.createNodesFromDB(searchTweet);
                else
                    LombardiContentPane.setSearchMonth(searchMonth);
                LombardiContentPane.doo(isSearchTweet);
                System.out.println("selected value " + searchTweet);
            } catch (SQLException e1) {

                e1.printStackTrace();
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }
            scrollPane.setEnabled(true);
            
        }
    }
    
    
    	public String getSearchTweet() {
		return searchTweet;
	}
	public void setSearchTweet(String searchTweet) {
		this.searchTweet = searchTweet;
	}
	public int getSearchMonth() {
		return searchMonth;
	}
	public void setSearchMonth(int searchMonth) {
		this.searchMonth = searchMonth;
	}
	public boolean isSearchTweet() {
		return isSearchTweet;
	}
	public void setSearchTweet(boolean isSearchTweet) {
		this.isSearchTweet = isSearchTweet;
	}
}
