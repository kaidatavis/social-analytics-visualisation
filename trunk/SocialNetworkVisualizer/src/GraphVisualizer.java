import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
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
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;


public class GraphVisualizer {
	
	GraphVisualizer isom;
	GraphVisualizer kk;
	GraphVisualizer fr;
	GraphVisualizer circle;
	
	final int ISOMLayout = 1;
	final int KKLayout = 2;
	final int FRLayout = 3;
	final int CircleLayout = 4;
	
	private static Layout<String, String> layout;
	private static Graph g = null;

	//Empty Constructor to Create the Graph.
	protected GraphVisualizer() {
	   }
	 
	public GraphVisualizer(Graph g2, int Layout){
		
		this.g = g2;
		//svg = new ISOMGraphLayout(); // Creates the graph...
		
		switch (Layout) {
		
		case ISOMLayout:
			isom = new GraphVisualizer();
			layout = new ISOMLayout(isom.g);
			Visualizer();
			break;
			
		case KKLayout:
			kk = new GraphVisualizer();
			layout = new KKLayout(kk.g);
			Visualizer();
			break;	
			
		case FRLayout:
			fr = new GraphVisualizer();
			layout = new FRLayout(fr.g);
			Visualizer();
			break;
			
		case CircleLayout:
			circle = new GraphVisualizer();
			layout = new CircleLayout(circle.g);
			Visualizer();
			break;
		
		}
	}
	
	private void Visualizer(){
	
	
    layout.setSize(new Dimension(1350,700));
    VisualizationViewer<String,String> vv = new VisualizationViewer<String,String>(layout);
    vv.setPreferredSize(new Dimension(1400,700));
    Transformer<String, Paint> vertexPaint = new Transformer<String,Paint>() {
        public Paint transform(String i) {
            return Color.GREEN;
        }
    };  
    
    Transformer<String, Icon> vertexIcon = new Transformer<String,Icon>() {
        public Icon transform(String i) {

        	ImageIcon ic = new ImageIcon("index.png");
        	return ic;

        }
    }; 
    vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
    vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
    vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());

    PluggableGraphMouse gm = new PluggableGraphMouse(); 
    gm.add(new PickingGraphMousePlugin());
    gm.add(new RotatingGraphMousePlugin());
    gm.add(new ShearingGraphMousePlugin());
    gm.add(new LabelEditingGraphMousePlugin ());
    gm.add(new TranslatingGraphMousePlugin(MouseEvent.BUTTON1_MASK));
    gm.add(new ScalingGraphMousePlugin(new CrossoverScalingControl(), 0, 1.1f, 0.9f));
    

	vv.setGraphMouse(gm);
  
    JFrame frame = new JFrame("Social Network Visualizer");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(vv);
    
    frame.pack();
    frame.setVisible(true);
	
	}
}
