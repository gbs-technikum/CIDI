package car.Gui;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import car.Events.GuiDriveEventAction;

public class GuiJPStart extends JPanel{

	private static final long serialVersionUID = 1L;
	private JButton jbZurAnmeldung;
	private GuiDriveEventAction gdea;
	
	private GuiJFrameMain guiMain;
	
	public GuiJPStart(GuiJFrameMain guiMain){
		this.guiMain = guiMain;
		initComponents();
		initEvents();
	}

	private void initEvents() {
		gdea = new GuiDriveEventAction(this, guiMain);
		
		jbZurAnmeldung.addActionListener(gdea);
		
	}

	private void initComponents() {
		this.setLayout(new BorderLayout());
		
		JLabel jlbild = new JLabel(new ImageIcon("src/bilder/start.jpg"));
    	
		JPanel jpMittig = new JPanel();
    	jbZurAnmeldung = new JButton("Weiter zur Anmeldung!");
    	jbZurAnmeldung.setActionCommand("ZumLogin");
    	jpMittig.add(jbZurAnmeldung);
    	
    	this.add(jlbild,BorderLayout.NORTH);
    	this.add(jpMittig, BorderLayout.CENTER);
	}
}
