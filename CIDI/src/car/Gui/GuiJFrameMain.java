package car.Gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import car.Events.GuiDriveEventAction;
import car.Events.GuiDriveWindowEvent;

public class GuiJFrameMain extends JFrame{

	private static final long serialVersionUID = 1L;

	private Container c;
	private JPanel jpNorth, jpSouth;
	
	private GuiJPDrive gjd;
	private GuiJPStart gjs;
	private GuiJPLogin gjl;
	
	private GuiDriveEventAction gdea;
	private GuiDriveWindowEvent gdew;
	
	public GuiJFrameMain(){
    	this.setTitle("Connect it Drive it!");
//    	this.setSize(500,500);
    	this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);   
    	this.setLocation(200, 200);
		initComponent();
		initEvents();
		this.pack();
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
		gjs = new GuiJPStart(this);
		gjl = new GuiJPLogin(this);
		gjd = new GuiJPDrive(this);
		c.add(gjd, BorderLayout.CENTER);
	}
//****************************************************************
	
	private void initEvents() {
		gdew = new GuiDriveWindowEvent();
		gdea = new GuiDriveEventAction(this);
		
		this.addWindowListener(gdew);
		
			
	}	

	//Zum schalten des JPanels zwischen den "ProgrammOberflächen"
	public void jpNeuZeichnen(String oberFlaeche){
		switch (oberFlaeche) {
		case "ZurLoginOberflaeche":
			c.removeAll();
			standardJFrameBauen();
			c.add(gjl, BorderLayout.CENTER);
			repaint();
			setVisible(true);
			this.pack();
			break;
		case "ZurDriveOberflaeche":
			c.removeAll();
			standardJFrameBauen();
			c.add(gjd, BorderLayout.CENTER);
			repaint();
			setVisible(true);
			this.pack();
			break;
		case "ZurStartOberflaeche":
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
        JLabel jlCopyRight = new JLabel("© all rights reserved for the best Class - I4a!");
        jpSouth.add(jlCopyRight,BorderLayout.WEST);
		return jpSouth;
	}

	public static void main(String[] args) {
		GuiJFrameMain g = new GuiJFrameMain();
	}
	
}
