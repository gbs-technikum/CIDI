package i3a.car;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class GuiStart extends JFrame{ 

	private Container c;
	private JButton jbanmelden;
	
	private static final long serialVersionUID = 1L;

	public GuiStart () {
		this.setTitle("\"Connect it, Drive it!\" - Login");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLocation(200,200);
        this.initComponent();
        this.initEvents();
        this.pack();
        this.setResizable(false);
        this.setVisible(true);

    }
    
    private void initEvents() {
    	
    	GuiDriveEvent gde = new GuiDriveEvent(this);
    	GuiDriveEventWindow gdew = new GuiDriveEventWindow();
    	
    	this.addWindowListener(gdew);    	
    	jbanmelden.addActionListener(gde);
	}

	private void initComponent() {
    	c = getContentPane();
    	JPanel jpNorth = new JPanel();
    	JPanel jpCenter = new JPanel();
    	JPanel jpSouth = new JPanel();
  
    	JLabel jlbild = new JLabel(new ImageIcon(getClass().getResource("bilder/start.jpg")));
    	
    	jbanmelden = new JButton("Weiter zur Anmeldung!");
  	
        //Unten -> Webshop Copyright etc
        jpSouth.setLayout(new BorderLayout());
        JLabel jlCopyRight = new JLabel("© all rights reserved for the best Class!");
        jpSouth.add(jlCopyRight,BorderLayout.WEST);
    	
        jpNorth.add(jlbild);
    	jpCenter.add(jbanmelden);
    	jpSouth.add(jlCopyRight);
    	
    	c.add(jlbild,BorderLayout.NORTH);
    	c.add(jpCenter,BorderLayout.CENTER);
    	c.add(jpSouth,BorderLayout.SOUTH);
		
	}

	
	public static void main (String [] args) {
        new GuiStart();
    }
     
}