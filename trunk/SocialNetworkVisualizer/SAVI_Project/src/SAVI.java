import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;

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

public class SAVI {
	
	//Local Variables
	public static String[] userList;
	public static String[] toList;
	public static String[] quserList;
	public static String[] qtoList;
	public static String[] CombinedList;
	public static String[] uCombinedList;
	public static int edgeCount = 0;
	static DirectedSparseGraph<String, String> g;
	static Factory<Integer> vertexFactory;
	static Factory<String> edgeFactory;
	
	public static List<String> LUserList = new ArrayList<String>();
	public static List<String> LtoUserList = new ArrayList<String>();
	
	static int vf = 0, ef = 0;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		new dbConnector();
		
		userList = new String[LUserList.size()];
	      for (int i = 0; i <LUserList.size(); i++)
	      {		
	    	  userList[i]= LUserList.get(i);
	    	  
	      }

	      toList = new String[LtoUserList.size()];
	      for (int i = 0; i <LtoUserList.size(); i++)
	      {		
	    	  toList[i]= LtoUserList.get(i);
		    	  
	      }

		quserList = ProcessData.Uniquify(userList);
		qtoList = ProcessData.Uniquify(toList);
		
		 List<String> list = new ArrayList<String>(Arrays.asList(quserList));     
		 list.addAll(Arrays.asList(qtoList));     		 
		 CombinedList = new String[list.size()];
	      for (int i = 0; i <list.size(); i++)
	      {		
	    	  CombinedList[i]= list.get(i);
	    	  
	      }
	      uCombinedList = ProcessData.Uniquify(CombinedList);
	      
		Node[] N = new Node[uCombinedList.length];
		g = new DirectedSparseGraph<String, String>();
		for (int i = 0; i < uCombinedList.length;i++){
			
				N[i] = new Node(uCombinedList[i]);
				g.addVertex(N[i].Name);	
		}
		
		Edge[] E = new Edge[userList.length];
		for (int i = 0; i < userList.length;i++){
				
				g.addEdge(Edge.getEdgeId(), userList[i], toList[i]);

		}
		
		SAVI sgv = new SAVI(); 
		Layout<String, String> layout = new ISOMLayout(sgv.g);
        layout.setSize(new Dimension(1350,650));
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
      
        JFrame frame = new JFrame("SAVI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        
        frame.pack();
        frame.setVisible(true); 
				
	}
		
}