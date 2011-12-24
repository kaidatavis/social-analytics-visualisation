/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype3;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.color.CMMException;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputAdapter;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.GeoPosition;

/**
 *
 * @author Nantia
 */
public class myList {
    Object [] myContained;
    
    public static ArrayList<String> containedTweets = new ArrayList<String>();
    public static ArrayList<String> containedPictures = new ArrayList<String>();
    public static ArrayList<String> containedDate = new ArrayList<String>();
    public static ArrayList<String> containedUser = new ArrayList<String>();
    
    Rectangle rect;
    public static Image icon;
    public static ImageIcon myIcon;
    
    public static DefaultListModel model = new DefaultListModel();
    ListSelectionModel listSelectionModel;
    
    public myList(final String[][] myResult, final double[][] myPoints, int myOption){
        model.clear();  
        
        if (myOption == 1){
            System.out.println("2. " + myPoints.length);
            mapMouse(myResult, myPoints);
        } else if (myOption == 2){
            riverMouse(myResult);
        } else {
            
            if (myPoints == null){
                riverMouse(myResult);
            } else {
                mapMouse(myResult, myPoints);
            }
        }
    }
    
    private void riverMouse(String[][] myDetails){
        containedTweets = new ArrayList<String>();
        containedPictures = new ArrayList<String>();
        containedDate = new ArrayList<String>();
        containedUser = new ArrayList<String>();
        
        Prototype3View.jLabel6.setText(null);
        Prototype3View.jLabel8.setText(null);
        Prototype3View.jLabel10.setText(null);
        Prototype3View.jLabel11.setText(null);
        Prototype3View.jLabel11.setIcon(null);
        
        if (proGraph.myMonth != 0 && proGraph.myDay == 0 && proGraph.myHour == 0){
            for (int i=0; i<myDetails.length; i++){
                if (myDetails[i][1].substring(5, 7).equals(Integer.toString(proGraph.myMonth)) || (myDetails[i][1].substring(6, 7).equals(Integer.toString(proGraph.myMonth)) && myDetails[i][1].substring(5, 6).equals("0"))){
                    containedUser.add(myDetails[i][0]);
                    containedDate.add(myDetails[i][1]);
                    containedPictures.add(myDetails[i][2]);
                    containedTweets.add(myDetails[i][0] + ": " + myDetails[i][3]);
                }
            }
        } else if (proGraph.myMonth == 0 && proGraph.myDay != 0 && proGraph.myHour == 0){
            for (int i=0; i<myDetails.length; i++){
                if (myDetails[i][1].substring(5, 7).equals(Integer.toString(proGraph.month)) || (myDetails[i][1].substring(6, 7).equals(Integer.toString(proGraph.month)) && myDetails[i][1].substring(5, 6).equals("0"))){
                    if(myDetails[i][1].substring(8, 10).equals(Integer.toString(proGraph.myDay)) || (myDetails[i][1].substring(9, 10).equals(Integer.toString(proGraph.myDay)) && (myDetails[i][1].substring(8, 9).equals("0")))){
                        containedUser.add(myDetails[i][0]);
                        containedDate.add(myDetails[i][1]);
                        containedPictures.add(myDetails[i][2]);
                        containedTweets.add(myDetails[i][0] + ": " + myDetails[i][3]);
                    }
                }
            }
        } else if (proGraph.myMonth == 0 && proGraph.myDay == 0 && proGraph.myHour != 0){
            for (int i=0; i<myDetails.length; i++){
                if (myDetails[i][1].substring(5, 7).equals(Integer.toString(proGraph.month)) || (myDetails[i][1].substring(6, 7).equals(Integer.toString(proGraph.month)) && myDetails[i][1].substring(5, 6).equals("0"))){
                    if(myDetails[i][1].substring(8, 10).equals(Integer.toString(proGraph.day)) || (myDetails[i][1].substring(9, 10).equals(Integer.toString(proGraph.day)) && (myDetails[i][1].substring(8, 9).equals("0")))){
                        if(myDetails[i][1].substring(11, 13).equals(Integer.toString(proGraph.myHour)) || (myDetails[i][1].substring(12, 13).equals(Integer.toString(proGraph.myHour)) && (myDetails[i][1].substring(11, 12).equals("0")))){
                            containedUser.add(myDetails[i][0]);
                            containedDate.add(myDetails[i][1]);
                            containedPictures.add(myDetails[i][2]);
                            containedTweets.add(myDetails[i][0] + ": " + myDetails[i][3]);
                        }
                    }
                }
            }
        }
        myContained = new String[containedTweets.size()];
        myContained = containedTweets.toArray(new String[containedTweets.size()]);
        for (int i=0 ; i < myContained.length ; i++){
            model.addElement(myContained[i]);
            
        }

//        listSelectionModel = Prototype3View.jList1.getSelectionModel();
//        listSelectionModel.addListSelectionListener(new SharedListSelectionHandler());
    }
    
    private void mapMouse(final String[][] myDetails, final double myPoints[][]){
        Prototype3View.jXMapKit1.getMainMap().addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JXMapViewer map = Prototype3View.jXMapKit1.getMainMap();
                ArrayList<Rectangle> rect1 = new ArrayList<Rectangle>();
                containedTweets = new ArrayList<String>();
                containedPictures = new ArrayList<String>();
                containedDate = new ArrayList<String>();
                containedUser = new ArrayList<String>();
                Point pt = e.getPoint();
                int mapZoom = map.getZoom();
                    
                model.clear();
                Prototype3View.jLabel6.setText(null);
                Prototype3View.jLabel8.setText(null);
                Prototype3View.jLabel10.setText(null);
                Prototype3View.jLabel11.setText(null);
                Prototype3View.jLabel11.setIcon(null);

                System.out.println("1. " + myPoints.length);
                for (int i=0; i<myPoints.length; i++){
                    GeoPosition gp = new GeoPosition(myPoints[i][1], myPoints[i][0]); 
                    Point2D gp_pt = map.getTileFactory().geoToPixel(gp, mapZoom);
                    rect = map.getViewportBounds();
                    
                    int x = (int)(gp_pt.getX() - rect.getX());
                    int y = (int)(gp_pt.getY() - rect.getY());
                    rect1.add(new Rectangle(x - (Prototype3View.disText.length()*6-10), y, Prototype3View.disText.length()*6+10, 30));
                }

                for (int i=0; i< myDetails.length; i++){
                    Rectangle myRect = rect1.get(i);
                    if (myRect.contains(pt)) {
                        containedTweets.add(myDetails[i][0] + ": " + myDetails[i][3]);
                        containedPictures.add(myDetails[i][2]);
                        containedDate.add(myDetails[i][1]);
                        containedUser.add(myDetails[i][0]);
                    }
                }
                
                myContained = new String[containedTweets.size()];
                myContained = containedTweets.toArray(new String[containedTweets.size()]);
                
                for (int i=0 ; i < myContained.length ; i++){
                    model.addElement(myContained[i]);
                }
//                listSelectionModel = Prototype3View.jList1.getSelectionModel();
//                listSelectionModel.addListSelectionListener(new SharedListSelectionHandler());
            }
        });
    }
}

class SharedListSelectionHandler implements ListSelectionListener{
        public void valueChanged(ListSelectionEvent e){
            ListSelectionModel lsm = (ListSelectionModel)e.getSource();
            String Date;
            String Time;
            String myUrl;
            int dLength;
            Prototype3View.jLabel6.setText(null);
            Prototype3View.jLabel8.setText(null);
            Prototype3View.jLabel10.setText(null);
            Prototype3View.jLabel11.setText(null);
            Prototype3View.jLabel11.setIcon(null);
            if (!(lsm.isSelectionEmpty())) {
                int minIndex = lsm.getMinSelectionIndex();
                int maxIndex = lsm.getMaxSelectionIndex();
                for (int i = minIndex; i <= maxIndex; i++) {
                    if (lsm.isSelectedIndex(i)) {
                        try{
                            dLength = myList.containedDate.get(i).length();
                            Date = myList.containedDate.get(i).substring(0,10);
                            Time = myList.containedDate.get(i).substring(10,dLength-2);
                            Prototype3View.jLabel6.setText(myList.containedUser.get(i));
                            Prototype3View.jLabel8.setText(Date);
                            Prototype3View.jLabel10.setText(Time);
                            myUrl = myList.containedPictures.get(i);
                            URL where = new URL(myUrl);
                            myList.icon = ImageIO.read(where);
                            myList.myIcon = new ImageIcon(myList.icon);
                            Prototype3View.jLabel11.setIcon(myList.myIcon);
                        } catch (IOException  u){
                            System.out.println("Error: " + u);
                        } catch (CMMException cm){
                            System.out.println("Error: " + cm);
                            Prototype3View.jLabel11.setText("N/A");
                        }
                    }
                }
            }
        }
}

 
