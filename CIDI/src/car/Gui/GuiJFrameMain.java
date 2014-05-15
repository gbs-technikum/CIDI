package car.Gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import car.Events.GuiDriveEventAction;
import car.Events.GuiDriveWindowEvent;
import car.Hilfsklassen.DAO;

public class GuiJFrameMain extends JFrame{

	private static final long serialVersionUID = 1L;

	private Container c;
	private JPanel jpNorth, jpSouth;
	private DAO db;
	private GuiJPDrive gjd;
	private GuiJPStart gjs;
	private GuiJPLogin gjl;
	
	
	public GuiJFrameMain(){
    	this.setTitle("Connect it Drive it!");
//    	this.setSize(800,550);
    	this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);   
    	this.setLocation(200, 200);
    	
    	db = new DAO();
		this.db.verbindungAufbauen("jdbc:mysql://localhost:3306/cidi", "root", "mysql");
    	
		initComponent();
		initEvents();
		this.pack();
//		this.setResizable(false);
		this.setVisible(true);
	}

	private void initComponent() {
		c = this.getContentPane();
		
		// Containerbereich Norden
        jpNorth = fcontainerNorden();
		// Containerbereich Süden
        jpSouth = fcontainerSouth();
		
        //****************
        dynamischerTeil();
        //****************
        
        //Auf Container bastln
        standardJFrameBauen();        
	}

	private void standardJFrameBauen() {
		c.add(jpNorth,BorderLayout.NORTH);
        c.add(jpSouth,BorderLayout.SOUTH);
	}
	
//****************************************************************
	private void dynamischerTeil() {
		c.add(new GuiJPStart(this), BorderLayout.CENTER);
	}
//****************************************************************
	
	private void initEvents() {
		new GuiDriveEventAction(this);

		this.addWindowListener(new GuiDriveWindowEvent(this.db));
	}	

	//Zum schalten des JPanels zwischen den "ProgrammOberflächen"
	public void jpNeuZeichnen(String oberFlaeche, DAO db, Canvas cInput){
		switch (oberFlaeche) {
		case "ZurLoginOberflaeche":
			gjl = new GuiJPLogin(this);
			c.removeAll();
			standardJFrameBauen();
			c.add(gjl, BorderLayout.CENTER);
			repaint();
			setVisible(true);
			this.setSize(650, 438);
//			this.setResizable(false);
			break;
		case "ZurDriveOberflaeche":
			gjd = new GuiJPDrive(this, db, cInput);
			c.removeAll();
			standardJFrameBauen();
			c.add(gjd, BorderLayout.CENTER);
			repaint();
			setVisible(true);
			this.setSize(800, 600);
			break;
		case "ZurStartOberflaeche":
			gjs = new GuiJPStart(this);
			c.removeAll();
			standardJFrameBauen();
			c.add(gjs, BorderLayout.CENTER);
			repaint();
			setVisible(true);
			this.pack();
			break;
		default:
			break;
		}
	}
	
	private JPanel fcontainerNorden() {
		JPanel jpNorth = new JPanel();
        JLabel jlSlogen = new JLabel("Connect it, Drive it!");
        jlSlogen.setFont(new Font("Arial", 12, 35));
        jpNorth.add(jlSlogen);
		return jpNorth;
	}
	
	private JPanel fcontainerSouth() {
		JPanel jpSouth = new JPanel(new BorderLayout());
        JLabel jlCopyRight = new JLabel("© all rights reserved for almost the best Class - I4a!");
        jpSouth.add(jlCopyRight,BorderLayout.WEST);
		return jpSouth;
	}
	

	public static void main(String[] args) {
		GuiJFrameMain g = new GuiJFrameMain();
	}

	public DAO getDatenbank() {
		return this.db;
	}
}
