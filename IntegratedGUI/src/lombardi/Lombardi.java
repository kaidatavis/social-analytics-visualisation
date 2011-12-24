package lombardi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

public class Lombardi extends JPanel implements Scrollable, MouseMotionListener {

    /**
     * 
     */
    private ArrayList<String> Nodeimages = new ArrayList<String>();
    private static final long serialVersionUID = 1L;
    private final int screenWidth = 600;
    private final int screenHeight = 600;
    int sw = 500, sh = 500;	//#screen width&height, should be square
    private float maxIters = 600;
    private int totalNodes = 0;
    private int totalEdges = 0;
    private ScrollablePane scrollPane;
    private Rule columnView;
    private Rule rowView;
    boolean allDone = false;
    String fileName;
    JFrame jFrameRef;
    final float Nodexy = 0;
    final float Dxy = 1;
//	static Classes classObj = new Classes();
//	Vectors vectorObj = new Vectors();
    int count = 1;
    float scale = 0;
    boolean allGreen = false;
    float EXPFACTOR;
    float K;
    float K2;
    boolean useNew;
    float lineWidth = 2f;
    // lombardi settings
    float maxDiff;  	// #max difference between two tangent diffs, for arc to be possible, in radians
    float rfKopp;	 	//#rotational force constant, for how opp tangents affect my rotation(want my tan to match opp)
    float rfKadj; 			//#rotational force constant, for how my tangents affect my rotation(want my tan close to my edge)
    float tangentialK; 		//#tangential force constant
    float finalRound;		//#reserve a percent of iterations at the end just for my forces
    float shufflePercent;		//#percentage of iterations to shuffle on
    int shuffleSamples;	//#max number of shuffle attempts
    boolean modeLombardi;
    // LIVE display settings
    boolean live = true;
    int fps = 100;
    boolean everyOtherFrame = false;
    int tanLen = 30;			// #screen px
    boolean drawEdges = false;
    boolean drawTans = false;
    //#global algorithm vars
    //int screen;
    int clock;
    int padding;	//#some padding for the screen
    float[] bl = new float[2];			//#bounding box list
    float[] tr = new float[2];		//#bounding box list
    int shuffleEvery;	//#iteration mod to shuffle on
    int maxDeterministicShuffle;
    ArrayList<Float> sz = new ArrayList<Float>();//null;
    JProgressBar progressBar;
    JLabel progressLabel;
    int searchMonth;

    //constructor
    public Lombardi() {
        
        this.setLayout(null);
        
        progressLabel = new JLabel("Generating graph please wait...");
        progressBar = new JProgressBar();
        
        this.add(progressLabel);
        this.add(progressBar);
        
        progressLabel.setBounds(200, 300, 200, 50);
        progressBar.setBounds(200, 350, 150, 30);

        // progressBar.setSize(600, 400);



        // progressBar.setBounds(100, 100, 600, 300);
        //  this.revalidate();

        
        EXPFACTOR = 1.2f;
        K = 0.3f;
        K2 = K * K;
        useNew = true;
        
        
        maxDiff = .1f; 	// #max difference between two tangent diffs, for arc to be possible, in radians
        rfKopp = .5f;	 	//#rotational force constant, for how opp tangents affect my rotation(want my tan to match opp)
        rfKadj = .1f; 			//#rotational force constant, for how my tangents affect my rotation(want my tan close to my edge)
        tangentialK = .9f; 		//#tangential force constant
        finalRound = .03f;		//#reserve a percent of iterations at the end just for my forces
        shufflePercent = .4f;		//#percentage of iterations to shuffle on
        shuffleSamples = 20;	//#max number of shuffle attempts
        modeLombardi = true;

        // LIVE display settings
        live = true;
        fps = 100;
        everyOtherFrame = false;
        tanLen = 30;			// #screen px
        drawEdges = false;
        drawTans = false;

        //#global algorithm vars

        clock = 0;
        
        padding = 10;	//#some padding for the screen

        shuffleEvery = (int) Math.round(1 / shufflePercent);	//#iteration mod to shuffle on
        maxDeterministicShuffle = 1;
        
        while (Classes.factorial(maxDeterministicShuffle + 1) < shuffleSamples) {
            maxDeterministicShuffle += 1;
        }
    }

    /**
     * init function
     * 
     **/
    public void init() {
        
        
        totalNodes = Classes.nodes().size();
        totalEdges = Edge.edgeList.size();
        float Wd = EXPFACTOR * (K / 2);
        float Ht = EXPFACTOR * (K / 2);
        int nodeSize = Classes.nodes().size();
        int i = 1;
        for (Node n : Classes.nodes()) {
            //System.out.println("Init Node " + i++ + " / " + nodeSize);
            float random = 0;
//			
            while (random == 0) {
                random = (float) Math.random();
            }
//			
            n.x = Wd * (2 * random - 1);
//			//n.x = 0;
//			
            random = 0;
            while (random == 0) {
                random = (float) Math.random();
            }
//			
            n.y = Ht * (2 * random - 1);
            //n.y=0;

            //	n.x=n.y=this.Nodexy;
            n.degree = (n.nodes()).size();//len([x for x in n.nodes()])<<<<<<<<<<<<<<<<<<<<ISSUE>>>>>>>>>>>>>>>>>>>>>>>>>>
            n.aIncrement = (float) (2 * Math.PI) / n.degree;
        }
        int edgeSize = Edge.edgeList.size();
        i = 1;
        for (Edge e : Edge.edgeList) {
//			System.out.println("Init Edge " + i++ + " / " + edgeSize);
            e.factor = 1;
            e.dist = K;
            e.pa = e.p.nextTanAngle();
            e.qa = e.q.nextTanAngle();
        }
        if (live) {	/* probably drawing stuff
            pygame.init()
            screen = pygame.display.set_mode([sw+padding*2,sh+padding*2])
            screen.fill([255,255,255])
            clock = pygame.time.Clock()*/
            
        }
    }
    
    public float cool(float t0, int i) {
        return (t0 * (maxIters - i)) / maxIters;
    }
    
    public void adjust(float temp, int iteration) {
        
        if (temp <= 0) {
            System.out.println("frozen!");
            return;
        }
        // for(Node n : Node.nodeList)
        for (Node n : Classes.nodes()) {
            n.fx = 0;
            n.fy = 0;
            n.tx = 0;
            n.ty = 0;
            n.rf = 0;
        }
        if (iteration < (maxIters * (1 - finalRound)) || !(modeLombardi)) {
            for (int i = 0; i < Classes.nodes().size(); i++) {
                for (int j = 1; j < Classes.nodes().size(); j++) {
                    //System.out.println("Adjust i - j " + i + " - " + j);
                    repApply(Node.nodeList.get(i), Node.nodeList.get(j));// static
                }
            }
            for (Edge e : Edge.edgeList) {
                attrApply(e.p, e.q, e);
            }
        }
        if (modeLombardi) {
            if (iteration % shuffleEvery == 0) {
                for (Node n : Classes.nodes()) {
                    shuffleTans(n);
                }
            }
            for (Node n : Classes.nodes()) {
                rfApply(n);
            }
            for (Node n : Classes.nodes()) {
                tfApply(n);
            }
            
            updateAngle(temp);
        }
        updatePos(temp);
        
    }
    
    public void repApply(Node p, Node q) {
        float dx = q.x - p.x;
        float dy = q.y - p.y;
        float dist2 = dx * dx + dy * dy;
        
        while (dist2 == 0) {
            
            float random = 0;
            while (random == 0) {
                random = (float) Math.random();
            }
            
            dx = 5 - Math.round(random * 10);
            
            
            random = 0;
            while (random == 0) {
                random = (float) Math.random();
            }
            
            dy = 5 - Math.round(random * 10);

//			dx=dy=this.Dxy;

            dist2 = dx * dx + dy * dy;
        }
        
        float force = 0;
        
        if (useNew) {
            force = (K2 / (float) (Math.sqrt(dist2) * dist2));//this is dist^3			
        } else {
            force = (K2 / dist2);
        }
        q.fx += dx * force;
        q.fy += dy * force;
        p.fx -= dx * force;
        p.fy -= dy * force;
    }
    
    public void attrApply(Node p, Node q, Edge e) {
        float dx = q.x - p.x;
        float dy = q.y - p.y;
        float dist2 = dx * dx + dy * dy;
        while (dist2 == 0) {
            
            dx = 5 - Math.round((float) Math.random() * 10);
            dy = 5 - Math.round((float) Math.random() * 10);
            //dx=dy=this.Dxy;
            dist2 = dx * dx
                    + dy * dy;
        }
        float dist = (float) Math.sqrt(dist2);
        
        float force = 0;
        if (useNew) {
            force = ((e.factor * (dist - e.dist)) / dist);
        } else {
            force = ((e.factor * dist) / e.dist);
        }
        q.fx -= dx * force;
        q.fy -= dy * force;
        p.fx += dx * force;
        p.fy += dy * force;
    }
    
    public void shuffleTans(Node n) {
        float[] angles = new float[n.degree];
        float[] angles2 = new float[n.degree];
        float[] bestCombo = new float[n.degree];
        float[] bestCombo1 = new float[n.degree];
        ArrayList<Node> neighbors = n.nodes();
        //System.out.println(neighbours+"THIS IS NEIGHBOURS");
        float bestVal = rfComputeTot(n);
        for (int ii = 0; ii < neighbors.size(); ii++) {
            
            Node nn = neighbors.get(ii);
            float tanAngleRel = n.tanAngleRel(nn);
            bestCombo[ii] = tanAngleRel;
            bestCombo1[ii] = tanAngleRel;
            
        }


        //bestCombo1 = bestCombo;
        ArrayList<Float> allAngles = new ArrayList<Float>();
        if (n.degree <= maxDeterministicShuffle) {
            
            
            int tuple_size = n.degree;
            
            ArrayList<Float> forPerm = new ArrayList<Float>();
            for (int i = 0; i < bestCombo.length; i++) {
                forPerm.add(bestCombo[i]);
            }
            
            allAngles = Classes.permutations(forPerm);
            for (int kk = 0; kk < allAngles.size();) {
                
                for (int s = 0; s < tuple_size; s++, kk++) {
                    angles[s] = allAngles.get(kk);
                }
                
                for (int i = 0; i < (n.degree); i++) {
                    n.setAngle(neighbors.get(i), angles[i]);
                }
                
                float val = rfComputeTot(n);
                if (val < bestVal) {
                    bestVal = val;
                    
                    for (int i = 0; i < angles.length; i++) {
                        
                        bestCombo[i] = angles[i];
                        
                    }
                }
            }
        } else //#random shuffle
        {
            
            
            for (int k = 0; k < (n.degree); k++) {
                for (int j = 0; j < (n.degree); j++) {
                    if (k != j) {
                        
                        
                        for (int i = 0; i < bestCombo.length; i++) {
                            angles2[i] = bestCombo[i];
                        }
                        
                        angles2[k] = bestCombo[j];
                        angles2[j] = bestCombo[k];
                        
                        
                        for (int i = 0; i < (n.degree); i++) {
                            n.setAngle(neighbors.get(i), angles2[i]);
                        }
                        float val = rfComputeTot(n);
                        if (val < bestVal) {
                            bestVal = val;
                            
                            for (int i = 0; i < angles2.length; i++) {
                                bestCombo[i] = angles2[i];
                            }
                        }
                    }
                }
            }
            //	angles = bestCombo;
            for (int z = 0; z < shuffleSamples; z++) {
                
                ArrayList<Float> tempShuffle = new ArrayList<Float>();
                for (int i = 0; i < angles2.length; i++) {
                    tempShuffle.add(angles2[i]);
                }
                Collections.shuffle(tempShuffle);
                
                for (int i = 0; i < tempShuffle.size(); i++) {
                    angles2[i] = tempShuffle.get(i);
                }
                
                
                for (int i = 0; i < (n.degree); i++) {
                    n.setAngle(neighbors.get(i), angles2[i]);
                }
                float val = rfComputeTot(n);
                if (val < bestVal) {
                    bestVal = val;
                    
                    for (int i = 0; i < angles2.length; i++) {
                        bestCombo[i] = angles2[i];
                    }
                }
            }
        }
        for (int i = 0; i < (n.degree); i++) {
            n.setAngle(neighbors.get(i), bestCombo[i]);
            //System.out.println(n.id + ", " + neighbors.get(i).id + ", " + bestCombo[i]);
        }
        
    }
    
    public float rfComputeTot(Node n) {
        float rf = 0f;
        for (Node nn : n.nodes()) {
            float opti = Classes.pie(n.edgeAngle(nn) - nn.diffAngle(n));
            float rot = Classes.pie(opti - n.tanAngle(nn));
            rf += Math.abs(rot) * rfKadj;
            rot = n.diffAngle(nn);
            rf += Math.abs(rot) * rfKopp;
        }
        return rf;
    }
    
    public float rfComputeNet(Node n) {
        float rf = 0f;
        for (Node nn : n.nodes()) {
            float opti = Classes.pie(n.edgeAngle(nn) - nn.diffAngle(n));
            float rot = Classes.pie(opti - n.tanAngle(nn));
            rf += rot * rfKopp;
            rf -= n.diffAngle(nn) * rfKadj;
        }
        return rf;
    }
    
    public void rfApply(Node n) {
        n.rf = rfComputeNet(n);
    }
    
    public void tfApply(Node n) {
        float[] optip = new float[2];
        for (Node nn : n.nodes()) //move n according to nn
        {
            float avg = (n.diffAngle(nn) - nn.diffAngle(n)) / 2;
            float aopti = Classes.pie2(nn.tanAngle(n) + avg);	//absolute optimal edge angle
            float rot = Classes.pie(aopti - nn.edgeAngle(n));
            float len = Classes.dist(n, nn);
            //optip = (nn.x+Math.cos(aopti)*len, nn.y+Math.sin(aopti)*len);
            optip[0] = (float) (nn.x + Math.cos(aopti) * len);
            optip[1] = (float) (nn.y + Math.sin(aopti) * len);
            //#make force proprtional to rotation proposed
            n.tx += (optip[0] - n.x) * tangentialK;	//#*(abs(rot)/m.pi)
            n.ty += (optip[1] - n.y) * tangentialK;	//#*(abs(rot)/m.pi)
        }
    }
    
    public void updateAngle(float temp) {
        for (Node n : Classes.nodes()) {
            n.angle += n.rf * temp;
        }
    }
    
    public void updatePos(float temp) {
        
        float temp2 = temp * temp;
        //for i in range(2): tr[i] = 0
        for (int i = 0; i < 2; i++) {
            tr[i] = 0;
        }
        //for i in range(2): bl[i] = 0
        for (int i = 0; i < 2; i++) {
            bl[i] = 0;
        }

        //for n in nodes():
        for (Node n : Classes.nodes()) {
            n.fx += n.tx;	//#some other factor mb?
            n.fy += n.ty;	//#some other factor mb?
            float len2 = n.fx * n.fx + n.fy * n.fy;
            
            if (len2 < temp2) {
                n.x += n.fx;
                n.y += n.fy;
            } else //#limit by temp
            {
                float fact = (float) (temp / Math.sqrt(len2));
                n.x += n.fx * fact;
                n.y += n.fy * fact;
            }
            //#bounding box
            if (n.x < bl[0]) {
                bl[0] = n.x;
            } else if (n.x > tr[0]) {
                tr[0] = n.x;
            }
            
            if (n.y < bl[1]) {
                bl[1] = n.y;
            } else if (n.y > tr[1]) {
                tr[1] = n.y;
            }
            //System.out.println("node = " + n.id + " n.x = " + n.x + " , " + n.y );
            //System.out.println("node = " + n.id + " tr = " + tr[0] + " , " + tr[1] +  " bl = " + bl[0] + " , " + bl[1]);
        }
    }
    
    public boolean finalStep() {
        boolean legit = true;
        //for e in edges():
        for (Edge e : Classes.edges()) {
            float pd = e.diffAngle(e.p);
            float qd = e.diffAngle(e.q);
            float optidiff = (pd - qd) / 2;
            float adjust = Math.abs(pd + qd) / 2;
            
            if (adjust > .01) {
                legit = false;
            }
            if (optidiff > pd) // #increase
            {
                if (e.p.degree == 1) {
                    e.pa = Classes.pie2(e.pa + adjust * 2);
                } else if (e.q.degree == 1) {
                    e.qa = Classes.pie2(e.qa + adjust * 2);
                } else {
                    e.pa = Classes.pie2(e.pa + adjust);
                    e.qa = Classes.pie2(e.qa + adjust);
                }
            } else {
                if (e.p.degree == 1) {
                    e.pa = Classes.pie2(e.pa - adjust * 2);
                } else if (e.q.degree == 1) {
                    e.qa = Classes.pie2(e.qa - adjust * 2);
                } else {
                    e.pa = Classes.pie2(e.pa - adjust);
                    e.qa = Classes.pie2(e.qa - adjust);
                }
            }
            float dd = Math.abs(e.diffAngle(e.p) + e.diffAngle(e.q));
            if (dd > .000001) {
                //System.out.println(e.diffAngle(e.p)+", "+e.diffAngle(e.q));
            }
        }
        return legit;
    }
    
    public int[] toScreen(Node n, float scale) {
        //float scale = preToScreen();
        int[] lineCoordinates = new int[2];
        lineCoordinates[0] = (int) (Math.round((n.x - bl[0]) * scale) + padding);
        lineCoordinates[1] = (int) (Math.round((n.y - bl[1]) * scale) + padding);
        return lineCoordinates;//{(int(Math.round((n.x-bl.get(0))*scale)+this.padding), int(Math.round((n.y-bl.get(1))*scale)+padding))};
    }
    
    public int[] toScreen(float[] n, float scale) {
        //float scale = preToScreen();
        int[] lineCoordinates = new int[2];
        lineCoordinates[0] = (int) (Math.round((n[0] - bl[0]) * scale) + padding);
        lineCoordinates[1] = (int) (Math.round((n[1] - bl[1]) * scale) + padding);
        return lineCoordinates;//{(int(Math.round((n.x-bl.get(0))*scale)+this.padding), int(Math.round((n.y-bl.get(1))*scale)+padding))};
    }
    float global_i = -1, global_temp = 0, old_i = -1;
    
    public void draw(float i, float temp) {
        
        global_i = i;
        global_temp = temp;
        
        
        
        scale = preToScreen();



//		repaint();
//		try {
//			Thread.sleep(10);
//		} catch (InterruptedException e) {
//			
//			e.printStackTrace();
//		}
        if (i + 1 >= maxIters) {
            repaint();
            progressBar.setVisible(false);
            progressLabel.setVisible(false);
        }
        
    }
    
    private float preToScreen() {
        float[] sz = {tr[0] - bl[0], tr[1] - bl[1]};
        
        
        if (sz[0] > sz[1]) {
            tr[1] += (sz[0] - sz[1]) / 2;
            bl[1] -= (sz[0] - sz[1]) / 2;
        } else {
            tr[0] += (sz[1] - sz[0]) / 2;
            bl[0] -= (sz[1] - sz[0]) / 2;
        }
        
        return sw / max(sz);
    }
    
    public float max(float[] array) {
        
        float max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        
        return max;
    }
    
    public void paintComponent(Graphics g1) {
        
        super.paintComponent(g1);
        //    this.revalidate();

        
        
        
        
        
        
       

        //progressLabel.setLocation(350, 370);
        // progressBar.setLocation(350, 400);
//        this.setEnabled(false);
        this.revalidate();
        if (allDone == false) {
            return;
        }
//		this.setEnabled(true);

        synchronized (Lombardi.class) {
            
            progressBar.setVisible(false);
            boolean isLinesDrawn = false;



            //System.out.println("paint called i = " + global_i);
            Graphics2D g2d = (Graphics2D) g1;



            //#Edges
            if (drawEdges || !modeLombardi) {
                for (Edge e : Classes.edges()) {

                    //pygame.draw.line(screen, (200,200,200), toScreen(e.p), toScreen(e.q), 1);
                    int x1y1[] = toScreen(e.p, scale);
                    int x2y2[] = toScreen(e.q, scale);
                    //g.drawLine(x1y1[0], x1y1[1], x2y2[0], x2y2[1]);
                    g2d.setColor(Color.GRAY);
                    g2d.setStroke(new BasicStroke(lineWidth));
                    //g2d.draw(new Line2D.Float(x1y1[0], x1y1[1], x2y2[0], x2y2[1]));

                }
            }
            
            if (modeLombardi) {
                //if (global_i<maxIters /*&& global_i != maxIters-2*/) //#Curves
                if (global_i <= maxIters) {
                    
                    allGreen = true;
                    float ctrlPtK = 0.4f;
                    //System.out.println(Classes.edges().size());
                    for (Edge e : Classes.edges()) {
                        int[] start = toScreen(e.p, scale);
                        int[] end = toScreen(e.q, scale);
                        float startLen = Classes.dist(start, end) * Math.abs(e.diffAngle(e.p)) * ctrlPtK;
                        float endLen = Classes.dist(start, end) * Math.abs(e.diffAngle(e.q)) * ctrlPtK;
                        float[] startCtrl = new float[2];
                        startCtrl[0] = start[0] + startLen * (float) Math.cos(e.tanAngle(e.p));
                        startCtrl[1] = start[1] + startLen * (float) Math.sin(e.tanAngle(e.p));
                        //startCtrl = {(start[0]+startLen*Math.cos(e.tanAngle(e.p)), start[1]+startLen*Math.sin(e.tanAngle(e.p)))};
                        float[] endCtrl = new float[2];
                        endCtrl[0] = (end[0] + endLen * (float) Math.cos(e.tanAngle(e.q)));
                        endCtrl[1] = (end[1] + endLen * (float) Math.sin(e.tanAngle(e.q)));

                        //System.out.println(e.p.id + " - " + e.q.id + "  "  + e.tanAngle(e.p) + " , " + e.tanAngle(e.q));

                        //endCtrl = (end[0]+endLen*m.cos(e.tanAngle(e.q)), end[1]+endLen*m.sin(e.tanAngle(e.q)))
                        boolean arcPoss = Math.abs(e.diffAngle(e.p) + e.diffAngle(e.q)) < maxDiff;
                        
                        
                        Vectors start_vectors = new Vectors(start[0], start[1]);
                        Vectors startCtrl_vectors = new Vectors(startCtrl[0], startCtrl[1]);
                        Vectors endCtrl_vectors = new Vectors(endCtrl[0], endCtrl[1]);
                        Vectors end_vectors = new Vectors(end[0], end[1]);

                        //	ArrayList<Vectors> bezierReturn = Classes.bezier(start_vectors,startCtrl_vectors,endCtrl_vectors,end_vectors);

                        
                        
                        Shape bezier = new CubicCurve2D.Float(start_vectors.x, start_vectors.y,
                                startCtrl_vectors.x, startCtrl_vectors.y, endCtrl_vectors.x, endCtrl_vectors.y, end_vectors.x, end_vectors.y);
                        
                        if (arcPoss) {
                            g2d.setColor(Color.GREEN);
                        } else {
                            g2d.setColor(Color.GREEN);
                            allGreen = false;
                        }
                        g2d.setStroke(new BasicStroke(lineWidth));
                        //g2d.draw(bezier);
                        g2d.draw(new Line2D.Float(start[0], start[1], end[0], end[1]));
                        isLinesDrawn = true;
//					float center[] = getcenter(e);

//					System.out.println("Center " + e.p.id + " -  " + e.q.id + " (" + center[0] + "," + center[1] + ")" );

                    }
                } else // #Arcs
                {
                    for (Edge e : Classes.edges()) {
                        if (Math.abs(e.diffAngle(e.p)) < (Math.PI / 40)) {
                            int x1y1[] = toScreen(e.p, scale);
                            int x2y2[] = toScreen(e.q, scale);
                            //g.drawLine(x1y1[0], x1y1[1], x2y2[0], x2y2[1]);
                            g2d.setColor(Color.GREEN);
                            g2d.setStroke(new BasicStroke(lineWidth));
                            g2d.draw(new Line2D.Float(x1y1[0], x1y1[1], x2y2[0], x2y2[1]));
                        } else {
                            float len = Classes.dist(e.p, e.q) / 10;
                            
                            float[] vectraArg = new float[2];
                            vectraArg[0] = e.p.x + (float) Math.cos(e.tanAngle(e.p)) * len;
                            vectraArg[1] = e.p.y + (float) Math.sin(e.tanAngle(e.p)) * len;
                            Vectors pCtrl = new Vectors(vectraArg[0], vectraArg[1]);
                            //float [] qCtrl = Vector(e.p.x+Math.cos(e.tanAngle(e.p))*len, e.p.y+Math.sin(e.tanAngle(e.p))*len);
                            //float [] pCtrl = vectorObj.vectora(vectraArg);
                            vectraArg[0] = e.q.x + (float) Math.cos(e.tanAngle(e.q)) * len;
                            vectraArg[1] = e.q.y + (float) Math.sin(e.tanAngle(e.q)) * len;
                            Vectors qCtrl = new Vectors(vectraArg[0], vectraArg[1]);
                            //float [] qCtrl = Vector(e.q.x+Math.cos(e.tanAngle(e.q))*len, e.q.y+Math.sin(e.tanAngle(e.q))*len);
                            //float [] qCtrl = vectorObj.vectora(vectraArg);

                            float[] intersectionArg1 = new float[2];
                            intersectionArg1[0] = (-pCtrl.y + (e.p.x + e.p.y));
                            intersectionArg1[1] = (pCtrl.x + (e.p.y - e.p.x));
                            
                            float[] intersectionArg2 = new float[2];
                            intersectionArg2[0] = (-qCtrl.y + (e.q.x + e.q.y));
                            intersectionArg2[1] = (qCtrl.x + (e.q.y - e.q.x));
                            //center = intersection((-pCtrl.y+(e.p.x+e.p.y), pCtrl.x+(e.p.y-e.p.x)),e.p,(-qCtrl.y+(e.q.x+e.q.y), qCtrl.x+(e.q.y-e.q.x)),e.q)
                            Vectors center = Classes.intersection(intersectionArg1, e.p, intersectionArg2, e.q);
                            
                            float temp_xy[] = {center.x, center.y};
                            
                            float radius = Classes.dist(toScreen(temp_xy, scale), toScreen(e.p, scale));
                            //#pygame.draw.circle(screen, (0,0,255), toScreen(center), int(round(radius)), 1)
                            //#big arc or small arc?
                            intersectionArg1[0] = pCtrl.x;
                            intersectionArg1[1] = pCtrl.y;
                            
                            intersectionArg2[0] = qCtrl.x;
                            intersectionArg2[1] = qCtrl.y;
                            
                            Vectors intr = Classes.intersection(e.p, intersectionArg1, e.q, intersectionArg2);
                            
                            temp_xy[0] = intr.x;
                            temp_xy[1] = intr.y;
                            
                            float temp_pctrl[] = {pCtrl.x, pCtrl.y};
                            
                            boolean bigArc = (Classes.dist(temp_xy, temp_pctrl) > Classes.dist(temp_xy, e.p));
                            
                            float pa = -1 * Classes.fromToAngle(center, e.p);
                            float qa = -1 * Classes.fromToAngle(center, e.q);
                            if (qa < pa) {
                                qa += Math.PI * 2;
                            }
                            
                            if ((bigArc && qa - pa < Math.PI) || (!bigArc && qa - pa > Math.PI)) {
                                float tmp = pa;
                                pa = qa;
                                qa = tmp;
                                qa += Math.PI * 2;
                            }
                            
                            temp_xy[0] = center.x;
                            temp_xy[1] = center.y;
                            g2d.setColor(Color.BLUE);
                            //g.drawArc((int)(toScreen(temp_xy)[0]-radius), (int)(toScreen(temp_xy)[1]-radius),
                            //		(int)radius*2, (int)radius*2, (int)pa, (int)qa);

                            g2d.setStroke(new BasicStroke(lineWidth));
                            //g2d.draw(new Line2D.float(x1y1[0], x1y1[1], x2y2[0], x2y2[1]));
                            Arc2D.Float arc = new Arc2D.Float((toScreen(temp_xy, scale)[0] - radius), (toScreen(temp_xy, scale)[1] - radius),
                                    radius * 2, radius * 2, pa, qa, Arc2D.CHORD);
                            g2d.draw(arc);

//						System.out.println(e.p.id + " - " + e.q.id + " - center " +  center.x + "," + center.y);

                            
                        }
                    }
                }
            }
            //#Tangents
            if (drawTans) {
                for (Node n : Classes.nodes()) {
                    int[] s = toScreen(n, scale);
                    for (Node nn : n.nodes()) {
                        float endx = s[0] + tanLen * (float) Math.cos(n.tanAngle(nn));
                        float endy = s[1] + tanLen * (float) Math.sin(n.tanAngle(nn));
                        //	g.drawLine(s[0], s[1], (int)endx, (int)endy);
                        g2d.setColor(Color.BLACK);
                        g2d.setStroke(new BasicStroke(lineWidth));
                        g2d.draw(new Line2D.Float(s[0], s[1], (int) endx, (int) endy));
                        //pygame.draw.line(screen, (100,100,100), s, (endx,endy), 1)
                    }
                }
            }
            //#Nodes
            int radius = 3;
            boolean displayLabel = true;
            if (isLinesDrawn) {
                for (Node n : Classes.nodes()) {
                    //Shape circle = new Ellipse2D.Float(100ff, 100ff, 100ff, 100ff);
                    int[] xy = toScreen(n, scale);
                    // Assume x, y, and diameter are instance variables.
                    g2d.setColor(Color.BLACK);
                    Ellipse2D.Float circle = new Ellipse2D.Float(xy[0] - radius, xy[1] - radius, radius * 2, radius * 2);
                    // g2d.fill(circle);
                    //if(displayLabel)
                    //g2d.drawString(n.id, xy[0] + 22, xy[1] + 22);

                    try {
                        String imageURL = getUserIcon(n.id);
                        if (imageURL != null) {
                            if (Nodeimages.contains(n.id) == false) {
//                                System.out.println("Node: " + n.id + "url = " + imageURL);
                                java.net.URL imgURL = new java.net.URL(imageURL);
                                ImageIcon icon = new ImageIcon(imgURL);
                                JLabel label1 = new JLabel(n.id, icon, JLabel.TRAILING);
                                //JLabel label1 = new JLabel(icon);
                                label1.setToolTipText(getIconToolTip(n.id));
                                this.add(label1);
                                
                                label1.setBounds(xy[0] - 70, xy[1] - 20, icon.getIconWidth() + 100, icon.getIconHeight());
                                label1.setVisible(true);
                                Nodeimages.add(n.id);
                                
                                this.revalidate();
                            }
                            
                        } else {
                            g2d.fill(circle);
                            g2d.drawString(n.id, xy[0] + 22, xy[1] + 22);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //System.out.println("Node: " + n.id + " - "  + (xy[0]-radius) + "," + (xy[1]-radius));

                    
                }
            }
        }
        
    }
    
    public String getIconToolTip(String userName) {
        
        for (TweetData tweetData : MySqlConnection.allTweetData) {
            if (tweetData.getToUser().equals(userName)) {
                return tweetData.getTweetText();
            }
        }
        
        return null;
        
    }
    
    public String getUserIcon(String userName) {
        
        for (TweetData tweetData : MySqlConnection.allTweetData) {
            if (tweetData.getToUser().equals(userName)) {
                return tweetData.getImageUrlToUser();
            }
        }
        if (MySqlConnection.allTweetData.size() > 0) {
            
            return MySqlConnection.allTweetData.get(0).imageUrl;
        }
        return null;
    }

    /*	
    public void adjust2(int ij)
    {	
    ArrayList iter = new ArrayList();
    float fMult = 0.5;
    //for n in nodes():
    for(Node n : Classes.nodes())
    {
    if (n.degree==1)
    {
    continue;
    }
    iter = sorted([(n.tanAngle(nn), nn) for nn in n.nodes()], key = lambda x: x[0])
    //iter.insert(0, iter[n.degree-1])
    iter.add(0,iter.get(n.degree-1));
    //iter.append(iter[1])
    iter.add(iter.get(1));
    //for i in range(1,n.degree+1):
    for(int i=1; i <= n.degree; i++)
    {	
    float tmp = Classes.pie2(iter[i+1][0]-iter[i-1][0]);
    if (Math.abs(tmp) < .0001) 
    {
    tmp = Math.PI*2-.0001;
    }
    float optiTanAngle = Classes.pie2(iter[i-1][0]+tmp/2);
    float rf = pie(optiTanAngle-iter[i][0])**3;
    nn = iter[i][1];
    //#optiDiff = -nn.diffAngle(n)#rem!
    //#rf += pie(optiDiff - n.diffAngle(nn))*combineK
    n.addForce(nn, rf*fMult);
    nn.addForce(n, -rf*fMult);
    }	
    }
    //for e in edges():
    for(Edge e :  Classes.edges())
    {
    e.pa += e.pf;
    e.qa += e.qf;
    e.pf = 0f;
    e.qf = 0f;
    }	
    }
     */
    public float getScore() {
        ArrayList<Float> angles = new ArrayList<Float>();
        int cnt = 0;
        float tot = 0f;

        //for n in nodes():
        for (Node n : Classes.nodes()) {
            if (n.degree == 1) {
                continue;
            }
            //for(Node nn : n.nodes())
            //angles = sorted([n.tanAngle(nn) for nn in n.nodes()])
            for (Node nn : n.nodes()) {
                angles.add(n.tanAngle(nn));
            }
            Collections.sort(angles);
            //angles.append(angles[0])
            angles.add(angles.get(0));
            
            for (int j = 0; j < n.degree; j++) {
                cnt += 1;
                tot += Math.abs(n.aIncrement - Classes.pie2((angles.get(j + 1) - angles.get(j))));//#squared takes care of neg sign
            }
        }
        tot /= cnt;
        return (float) (100 * (1 - tot / Math.PI));
    }

    /** 
     * reads the *.dot file
     * 
     * @param fileName
     */
    //public void paint(Graphics g)
    public void doo(boolean createFullGraph) {
     
        
       
        
        allDone = false;
        float one, two = 0;
        //creating nodes
        
        boolean isDataAvailable = false;
        if (createFullGraph) {
           isDataAvailable =  createNodesFromAllData();            
        } else {
           isDataAvailable = createNodesFromDateFilter(searchMonth);
        }
        
        if(isDataAvailable==false){
             progressLabel.setText("No Data available for your selection");
             progressLabel.setVisible(true);
             progressBar.setVisible(false);
             repaint();
            return;
        }
        
         progressLabel.setText("Generating graph please wait...");
         progressLabel.setVisible(true);
         progressBar.setStringPainted(true);
         progressBar.setVisible(true);
        init();

        //T0 = K * m.sqrt(len(nodeList)) / 5.0
        float T0 = K * (float) Math.sqrt(Node.nodeList.size()) / 5f;
        
        progressBar.setValue(0);
        progressBar.setMaximum((int) maxIters);
        
        for (int i = 0; i < maxIters; i++) {
//			if(i == maxIters+1 && allGreen==false)
//				maxIters++;
            global_i = i;
            progressBar.setValue(i + 1);
            adjust(cool(T0, i), i);
//			System.out.println("Iteration " + i + "/" + maxIters);
            if (i >= maxIters - 1) {
                allDone = true;
            }
            if (live) {
                //draw related
                if (!everyOtherFrame || i % 2 == 0) {
                    draw(i, cool(T0, i));
                }

                //if checkQuit(): return
            }
        }
        if (modeLombardi) {
            boolean legit = finalStep();
            one = getScore();
            //System.out.println(one);
            if (!legit) {
                //#Save (Cache current pa and qa vals!)(use unused vars or somn, mb ctrl pt)
                if (live) {	//for i in range(100):
                    for (int i = 0; i < 100; i++) {
                        //System.out.println("MAXITERS"+maxIters);
                        draw((i / (100 / maxIters)), 0);
                        //	if checkQuit(): return
                    }
                }
                for (int i = 0; i < 200; i++) {
                    //adjust2(i);
                    if (live) {
                        draw((i / (200 / maxIters)), 0);
                        //if checkQuit(): return
                    }
                }
                two = getScore();
                
                if (two < one) {	//#restorePrev()
                    //pass;
                }
                //#float check circleness
                //for e in edges():
                for (Edge e : Classes.edges()) {
                    float dd = Math.abs(e.diffAngle(e.p) + e.diffAngle(e.q));
                    if (dd > .000001) {
                        //System.out.println(e.diffAngle(e.p)+", "+e.diffAngle(e.q)+", "+dd);
                    }
                }
            }
            if (live && false) {
                draw(maxIters, 0);
                //pygame.display.set_caption("%f, %f"%(one, two))
            }
        } else // #set tans to edges
        {
            //for e in edges():
            for (Edge e : Classes.edges()) {
                e.pa = e.edgeAngle(e.p);
                e.qa = e.edgeAngle(e.q);
            }
            if (live) {
                draw(0, 0);
                
            }
        }
        if (live) {
            //waitQuit();
        }
        
        
    }

   
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public JFrame getjFrameRef() {
        return jFrameRef;
    }
    
    public void setjFrameRef(JFrame jFrameRef) {
        this.jFrameRef = jFrameRef;
    }
    private int maxUnitIncrement = 1;

    //Methods required by the MouseMotionListener interface:
    public void mouseMoved(MouseEvent e) {
    }
    
    public void mouseDragged(MouseEvent e) {
        //The user is dragging us, so scroll!
        Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);
        scrollRectToVisible(r);
    }
    
    public Dimension getPreferredSize() {
//        if (missingPicture) {
//            return new Dimension(screenWidth, screenHeight);
//        } else {
        return super.getPreferredSize();
//        }
    }
    
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }
    
    public int getScrollableUnitIncrement(Rectangle visibleRect,
            int orientation,
            int direction) {
        //Get the current position.
        int currentPosition = 0;
        if (orientation == SwingConstants.HORIZONTAL) {
            currentPosition = visibleRect.x;
        } else {
            currentPosition = visibleRect.y;
        }

        //Return the number of pixels between currentPosition
        //and the nearest tick mark in the indicated direction.
        if (direction < 0) {
            int newPosition = currentPosition
                    - (currentPosition / maxUnitIncrement)
                    * maxUnitIncrement;
            return (newPosition == 0) ? maxUnitIncrement : newPosition;
        } else {
            return ((currentPosition / maxUnitIncrement) + 1)
                    * maxUnitIncrement
                    - currentPosition;
        }
    }
    
    public int getScrollableBlockIncrement(Rectangle visibleRect,
            int orientation,
            int direction) {
        if (orientation == SwingConstants.HORIZONTAL) {
            return visibleRect.width - maxUnitIncrement;
        } else {
            return visibleRect.height - maxUnitIncrement;
        }
    }
    
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }
    
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
    
    public void setMaxUnitIncrement(int pixels) {
        maxUnitIncrement = pixels;
    }
    
    public JProgressBar getProgressBar() {
        return progressBar;
    }
    
    public void setProgressBar(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }
    
    public void reset() {
        Classes.edges().clear();
        Classes.nodes().clear();
        Classes.adjMat.clear();
        Classes.nodeDict.clear();
        Node.nodeList.clear();
        Edge.edgeList.clear();
        
        global_i = 0;
        repaint();
    }
    
    public void addEdge() {
    }
    
    public float getMaxIters() {
        return maxIters;
    }
    
    public void setMaxIters(float maxIters) {
        this.maxIters = maxIters;
    }
    
    public int getSw() {
        return sw;
    }
    
    public void setSw(int sw) {
        this.sw = sw;
    }
    
    public int getSh() {
        return sh;
    }
    
    public void setSh(int sh) {
        this.sh = sh;
    }
    
    public int getShuffleSamples() {
        return shuffleSamples;
    }
    
    public void setShuffleSamples(int shuffleSamples) {
        this.shuffleSamples = shuffleSamples;
    }
    
    private float[] getcenter(Edge e) {
        float len = Classes.dist(e.p, e.q) / 10;
        
        float[] vectraArg = new float[2];
        vectraArg[0] = e.p.x + (float) Math.cos(e.tanAngle(e.p)) * len;
        vectraArg[1] = e.p.y + (float) Math.sin(e.tanAngle(e.p)) * len;
        Vectors pCtrl = new Vectors(vectraArg[0], vectraArg[1]);
        
        vectraArg[0] = e.q.x + (float) Math.cos(e.tanAngle(e.q)) * len;
        vectraArg[1] = e.q.y + (float) Math.sin(e.tanAngle(e.q)) * len;
        Vectors qCtrl = new Vectors(vectraArg[0], vectraArg[1]);
        
        
        float[] intersectionArg1 = new float[2];
        intersectionArg1[0] = (-pCtrl.y + (e.p.x + e.p.y));
        intersectionArg1[1] = (pCtrl.x + (e.p.y - e.p.x));
        
        float[] intersectionArg2 = new float[2];
        intersectionArg2[0] = (-qCtrl.y + (e.q.x + e.q.y));
        intersectionArg2[1] = (qCtrl.x + (e.q.y - e.q.x));
        
        Vectors center = Classes.intersection(intersectionArg1, e.p, intersectionArg2, e.q);
        
        float temp_xy[] = {center.x, center.y};
        return temp_xy;
    }
    
    public boolean createNodesFromDateFilter(int month) {
        
        boolean returnValue = false;
        
        for (TweetData tweetData : MySqlConnection.allTweetData) {            
            SimpleDateFormat sdf;
            
            sdf = new SimpleDateFormat("MM");
            
            int tweetMonth = new Integer(sdf.format(tweetData.getCreateDate()));
            
            if (tweetMonth == month) {
                returnValue = true;
                new Edge(tweetData.getUserName(), tweetData.getToUser());
            }
        }
        
        return returnValue;
        
    }

    public boolean  createNodesFromAllData() {
        
         if (MySqlConnection.allTweetData.size() <= 0) {        
            return false;
        }
        
        for (TweetData tweetData : MySqlConnection.allTweetData) {
            String userName = tweetData.getUserName();
            String toUser = tweetData.getToUser();
            new Edge(userName, toUser);
        }
        
        return true;
    }

    public int getSearchMonth() {
        return searchMonth;
    }

    public void setSearchMonth(int searchMonth) {
        this.searchMonth = searchMonth;
    }
}
