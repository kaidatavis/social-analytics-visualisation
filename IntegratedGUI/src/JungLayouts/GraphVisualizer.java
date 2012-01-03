/*******************************************************************************
 *   File Name:		GraphVisualizer.java
 *   File Version:	2
 *   Created by:	Nakul Sharma
 *   Date Created:	Tue Jan 3, 2012
 *******************************************************************************/

/*******************************************************************************
--| Module Body:
--|     GraphVisualizer
--|
--| Implementation Notes:
--|     This Class is used to create the visualizer and dispaly's it in the 
--|		selected layout.
--|
--| Portability Issues:
--|		None
--|
 *******************************************************************************/
package JungLayouts;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.LabelEditingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.PluggableGraphMouse;
import edu.uci.ics.jung.visualization.control.RotatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ShearingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.TranslatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.decorators.DefaultVertexIconTransformer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import javax.swing.JPanel;


public class GraphVisualizer {
	
	private GraphVisualizer isom;
	private GraphVisualizer kk;
	private GraphVisualizer fr;
	private GraphVisualizer circle;
	
	final int ISOMLayout = 1;
	final int KKLayout = 2;
	final int FRLayout = 3;
	final int CircleLayout = 4;
	
	private static Layout<String, String> layout;
	@SuppressWarnings("unchecked")
	private static Graph localGraph = null;

	/*******************
	*Empty Constructor *
	*******************/
	protected GraphVisualizer() { }
	 
	/*******************
	*   Constructor   *
	*******************/
	@SuppressWarnings({ "unchecked", "static-access" })
	public GraphVisualizer(Graph graph, int Layout){
		
		//Getting a Main Graph in a local Graph
		this.localGraph = graph;
		
		//Selecting a layout based on the user's Choice
		switch (Layout) {
		
		case ISOMLayout:
			isom = new GraphVisualizer();
			layout = new ISOMLayout(isom.localGraph);
			Visualizer();
			break;
			
		case KKLayout:
			kk = new GraphVisualizer();
			layout = new KKLayout(kk.localGraph);
			Visualizer();
			break;	
			
		case FRLayout:
			fr = new GraphVisualizer();
			layout = new FRLayout(fr.localGraph);
			Visualizer();
			break;
			
		case CircleLayout:
			circle = new GraphVisualizer();
			layout = new CircleLayout(circle.localGraph);
			Visualizer();
			break;
		}
	}
	
    /*******************************************
    This method is used to create the visualizer
    based on the layout selected by the User.
    ********************************************/
    @SuppressWarnings("unchecked")
    private void Visualizer(){
	
    //Setting the Layout size	
    layout.setSize(new Dimension(650,650));
	
    //Creating the Visualizer
    VisualizationViewer<String,String> visualizer = new VisualizationViewer<String,String>(layout);
    visualizer.setPreferredSize(new Dimension(650,650));
    
    //Creating a hash-map to store Icons
    Map<String, Icon> iconsMap = new HashMap<String, Icon>();
    for (int i = 0; i < GraphPlotter.uCombinedList.length; i++) {
        String tempString = "images//UsersProfilePics//" + GraphPlotter.uCombinedList[i] + ".jpg";
        try {
        	Icon icon = new ImageIcon(tempString);
            iconsMap.put(GraphPlotter.uCombinedList[i], icon);
            
        } catch (Exception ex) {
        	
        	System.out.println("Error: Problem in creating a Hash-Map for Icons");
        }
    }
    
    //Creating a transformer for Painting Nodes
    @SuppressWarnings("unused")
	Transformer<String, Paint> NodePaint = new Transformer<String,Paint>() {
        public Paint transform(String i) {
            return Color.GREEN;
        }
    };      
    
    //Creating local Icon Transformer
    final myVertexIconTransformer<String> vertexIconTransformer = new myVertexIconTransformer<String>();
    
    //Setting Hash-Map for Icon on Transformer
    vertexIconTransformer.setIconMap(iconsMap);
  
    //A tool-tip Transformer, this will display tweets text on mouse-over.
    visualizer.setVertexToolTipTransformer(new ToolTipView());
  
    //Setting Visualizer's Renderer properties
    visualizer.getRenderContext().setVertexIconTransformer(vertexIconTransformer);
    //visualizer.getRenderContext().setVertexFillPaintTransformer(NodePaint);
    visualizer.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
    visualizer.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller()); 

    //PluggableGraphMouse properties
    PluggableGraphMouse graphMouse = new PluggableGraphMouse(); 
    graphMouse.add(new PickingGraphMousePlugin());	//Picking Mode is active
    graphMouse.add(new RotatingGraphMousePlugin());	//Rotating Graph mode is active
    graphMouse.add(new ShearingGraphMousePlugin());	//shearing Graph mode is active
    graphMouse.add(new LabelEditingGraphMousePlugin ()); //Label Editing mode is active
    graphMouse.add(new TranslatingGraphMousePlugin(MouseEvent.BUTTON1_MASK)); //Translating Graph mode is active
    graphMouse.add(new ScalingGraphMousePlugin(new CrossoverScalingControl(), 0, 1.1f, 0.9f)); //Scaling Graph mode is active.
    
    //Setting GraphMouse on Visualizer
    visualizer.setGraphMouse(graphMouse);
        
    JPanel JP = new JPanel();
    JP.setSize(200, 200);
    JP.add(visualizer);
    //visualizer.setSize(600, 600);
    SocialNetworkVisualizer.scrollPane.setViewportView(JP);
    SocialNetworkVisualizer.scrollPane.setEnabled(true);
  
	
	}
}

class myVertexIconTransformer<V> extends DefaultVertexIconTransformer<V> implements Transformer<V, Icon>{

	boolean Imagesfill = true;
	boolean Imagesoutline = false;

	public boolean isImagesfill() {
		return Imagesfill;
	}

	/**
	* @param Imagesfill The Imagesfill to set.
	*/
	public void setImagesfill(boolean Imagesfill) {
		this .Imagesfill = Imagesfill;
	}

	public boolean isImagesoutline() {
		return Imagesoutline;
	}
	
	public void setImagesoutline(boolean Imagesoutline) {
		this .Imagesoutline = Imagesoutline;
	}
	
	public Icon transform(V ver) {
		if (Imagesfill) {
			return (Icon) iconMap.get(ver);}
	 	else {
	 		return null;
	 		}
	 	
	}
}


