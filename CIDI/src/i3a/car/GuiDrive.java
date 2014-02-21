/**
 * This file is part of the I3A project.
 *
 *
 * @copyright  (c) the I3A GBS Muenchen
 * @author     i3a <it@lala-rob.de>
 * @license    GPLv3 http://www.gnu.org/licenses/gpl.html
 * @version 0.0.1
 *
 */
package i3a.car;//test

import i3a.car.GuiUser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class GuiDrive extends JFrame{
    
    private Container c;
    private JButton jbVorwaerts, jbRueckwaerts, jbRechts, jbLinks, jbHupe, jbAbblendlicht, jbFernlicht;
    private JButton jbZumWebshop, jbVerbindungBeenden;
    private JLabel jlZahlenVerbZeit;
    private int restMin=15, restSek=0;
    private JPanel jpSitzungsInfo;
    private String nutzerName;
    private Boolean bfernlicht = false, babblendlicht = false;

 //Action zeug Listener
    private GuiDriveEventAction gde;
    private GuiDriveEventWindow gdew;
    private GuiDriveEventMouse gdem;
    

    public GuiDrive(/*GuiUser aktuelleSitzung*/) {
//    	this.gu = aktuelleSitzung;
//    	nutzerName = aktuelleSitzung.getNutzerName(); 
    	nutzerName = "Hans Wurscht";
    	this.setTitle("Steuerung - Sitzung von " + nutzerName);
//    	this.setSize(500,500);
    	this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);   
    	this.setLocationRelativeTo(null);
    	this.setLocation(200, 200);
		initComponent();
		initEvents();
		this.pack();
		this.setVisible(true);
    }

    private void initEvents() {
    	
    	gdew = new GuiDriveEventWindow();
    	gde = new GuiDriveEventAction(this);
    	gdem = new GuiDriveEventMouse(this);
    	
        this.addWindowListener(gdew);
    	
    	jbVerbindungBeenden.addActionListener(gde);
    	jbZumWebshop.addActionListener(gde);
    	
    	jbFernlicht.addActionListener(gde);
    	jbAbblendlicht.addActionListener(gde);
    	jbHupe.addMouseListener(gdem);
//    	jbHupe.addActionListener(gde);
    	
    	
    	jbVorwaerts.addActionListener(gde);
    	jbRueckwaerts.addActionListener(gde);
    	jbLinks.addActionListener(gde);    	
    	jbRechts.addActionListener(gde);
    	
    	int delay = 1000; //milliseconds
		ActionListener taskPerformer = new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
		    	  restSek--;
		    	  if(restSek<0){
		    		  restSek=59;
		    		  restMin--;
		    	  }
		    	  String wMin=""+restMin,wSek=""+restSek;
		    	  if(restMin<10)
		    		  wMin = "0"+restMin;
		    	  if(restSek<10)
		    		  wSek="0"+restSek;
		          jlZahlenVerbZeit.setText(wMin+":"+wSek);
		          System.out.println(wMin+":"+wSek);
		          if(restMin==0 && restSek==0){
		        	  jbVerbindungBeenden.addActionListener(gde);
		     
		          }else if(restMin==0 && restSek==0){
		        	  restMin=15;
		        	  restSek=00;
		          }
		      }
		  };
		  new Timer(delay, taskPerformer).start();
	}

	private void initComponent() {
        c= this.getContentPane();

// Containerbereich Norden
        JPanel jpNorth = fcontainerNorden();
        
//Containerbereich Links
        JPanel jplinks = new JPanel();
        jplinks.setBorder(new CompoundBorder(jplinks.getBorder(), new LineBorder(Color.red,3)));
        jplinks.setLayout(new BoxLayout(jplinks, BoxLayout.Y_AXIS));
       
        //Stream von Webcam
        JPanel jpStream = fStream();
        
        //BewegungsTasten
        JPanel jpBewegungsTasten = fSteuertasten();
        
    	//Funktionstasten
    	JPanel jpFunktionen = fFunktionstasten();
    	
    	
        jplinks.add(jpStream);
    	jplinks.add(jpFunktionen);
    	jplinks.add(jpBewegungsTasten);
    	
//Containerbereich Rechts
        JPanel jprechts = fcontainerRechts();
               

//Containerbereich South
        JPanel jpSouth = fcontainerSouth();
        
 //Auf Container zambauen
        c.add(jpSouth,BorderLayout.SOUTH);
        c.add(jpNorth,BorderLayout.NORTH);
        c.add(jprechts,BorderLayout.EAST);
        c.add(jplinks,BorderLayout.CENTER);

    }

	private JPanel fcontainerSouth() {
		JPanel jpSouth = new JPanel(new BorderLayout());
        JLabel jlCopyRight = new JLabel("© all rights reserved for the best Class - I4a!");
        jbZumWebshop = new JButton("Zum WebShop");
        
        jpSouth.add(jlCopyRight,BorderLayout.WEST);
    	jpSouth.add(jbZumWebshop,BorderLayout.EAST);
		return jpSouth;
	}

	private JPanel fcontainerRechts() {
		JPanel jprechts = new JPanel();
        jprechts.setLayout(new BoxLayout(jprechts, BoxLayout.Y_AXIS));
        jpSitzungsInfo = new JPanel();
        jpSitzungsInfo.setLayout(new BoxLayout(jpSitzungsInfo,BoxLayout.Y_AXIS));
        jpSitzungsInfo.setBorder(new CompoundBorder(jpSitzungsInfo.getBorder(), new LineBorder(Color.gray,3)));
        JPanel jpTextVerbZeit = new JPanel();
        JLabel jlTextVerbZeit = new JLabel("Verbleibende Zeit:");
        jlTextVerbZeit.setFont(new Font("Arial", Font.PLAIN, 25));
        jpTextVerbZeit.add(jlTextVerbZeit);

        JPanel jpZahlenVerbZeit = new JPanel();
        jlZahlenVerbZeit = new JLabel("15:00");
        jlZahlenVerbZeit.setFont(new Font("Arial", Font.BOLD, 35));
        jpZahlenVerbZeit.add(jlZahlenVerbZeit);
        
        JPanel jpNutzerName = new JPanel();
        JLabel jlNutzername = new JLabel(this.nutzerName);
        jlNutzername.setFont(new Font("Arial", Font.PLAIN, 25));
        jpNutzerName.add(jlNutzername);
        
        jpSitzungsInfo.add(jpTextVerbZeit);
        jpSitzungsInfo.add(jpZahlenVerbZeit);
        jpSitzungsInfo.add(jpNutzerName);
        
        JPanel jpButtonVerbindungBeenden = new JPanel();
        jbVerbindungBeenden = new JButton("Sitzung Beenden");
        jpButtonVerbindungBeenden.add(jbVerbindungBeenden,BorderLayout.CENTER);
        
        jprechts.add(jpSitzungsInfo);
        jprechts.add(jpButtonVerbindungBeenden);
		return jprechts;
	}

	private JPanel fFunktionstasten() {
		JPanel jpFunktionen = new JPanel();
    	jpFunktionen.setLayout(new BoxLayout(jpFunktionen,BoxLayout.X_AXIS));
    	jpFunktionen.setBorder(new CompoundBorder(jpFunktionen.getBorder(), new LineBorder(Color.yellow,3)));
    	jbAbblendlicht = new JButton(new ImageIcon("src/buttons/abblendlichtDeakt.png"));
    	jbAbblendlicht.setActionCommand("Abblendlicht schalten");
    	jbFernlicht = new JButton(new ImageIcon("src/buttons/fernlichtDeakt.png"));
    	jbFernlicht.setActionCommand("Fernlicht schalten");
    	jbHupe = new JButton(new ImageIcon("src/buttons/hupe.png"));
    	jbHupe.setActionCommand("Hupe");
    	jpFunktionen.add(jbHupe);
    	jpFunktionen.add(jbFernlicht);
    	jpFunktionen.add(jbAbblendlicht);
		return jpFunktionen;
	}

	private JPanel fSteuertasten() {
		JPanel jpBewegungsTasten = new JPanel();
        jpBewegungsTasten.setBorder(new CompoundBorder(jpBewegungsTasten.getBorder(), new LineBorder(Color.green,3)));
        GridBagConstraints bgc = new GridBagConstraints();
    	jpBewegungsTasten.setLayout(new GridBagLayout());
       
        jbVorwaerts = new JButton(new ImageIcon("src/buttons/up.png"));
    	bgc.fill = GridBagConstraints.HORIZONTAL;
    	bgc.gridx = 1;
    	bgc.gridy = 0;
    	jpBewegungsTasten.add(jbVorwaerts, bgc);
        
        jbLinks = new JButton(new ImageIcon("src/buttons/left.png"));
    	bgc.fill = GridBagConstraints.HORIZONTAL;
    	bgc.gridx = 0;
    	bgc.gridy = 1;
    	jpBewegungsTasten.add(jbLinks, bgc);
    	
        jbRueckwaerts = new JButton(new ImageIcon("src/buttons/down.png"));
    	bgc.fill = GridBagConstraints.HORIZONTAL;
    	bgc.gridx = 1;
    	bgc.gridy = 1;
    	jpBewegungsTasten.add(jbRueckwaerts, bgc);
    	
        jbRechts = new JButton(new ImageIcon("src/buttons/right.png"));
    	bgc.fill = GridBagConstraints.HORIZONTAL;
    	bgc.gridx = 2;
    	bgc.gridy = 1;
    	jpBewegungsTasten.add(jbRechts, bgc);
		return jpBewegungsTasten;
	}

	private JPanel fStream() {
		JLabel jlTestBild = new JLabel(new ImageIcon("src/i3a/car/bilder/test.jpg"));
        JPanel jpStream = new JPanel();
        jpStream.add(jlTestBild);
		return jpStream;
	}

	private JPanel fcontainerNorden() {
		JPanel jpNorth = new JPanel();
        JLabel jlSlogen = new JLabel("Connect it, Drive it!");
        jlSlogen.setFont(new Font("Arial", 12, 35));
        jpNorth.add(jlSlogen);
		return jpNorth;
	}
	
    protected void fernlichtSchalten() {
		if(!(bfernlicht)){
			this.jbFernlicht.setIcon(new ImageIcon("src/buttons/fernlichtAkt.png"));
			this.bfernlicht = true;
		}else{
			this.jbFernlicht.setIcon(new ImageIcon("src/buttons/fernlichtDeakt.png"));
			this.bfernlicht = false;
		}
	}
    
    protected void abblendlichtSchalten() {
		if(!(babblendlicht)){
			this.jbAbblendlicht.setIcon(new ImageIcon("src/buttons/abblendlichtAkt.png"));
			this.babblendlicht = true;
		}else{
			this.jbAbblendlicht.setIcon(new ImageIcon("src/buttons/abblendlichtDeakt.png"));
			this.babblendlicht = false;
		}
	}
    
    protected void hupenAn(){
    	this.jbHupe.setIcon(new ImageIcon("src/buttons/huperot.png"));
    	System.out.println("Hup Hup!");
    }
    
    protected void hupenAus(){
    	this.jbHupe.setIcon(new ImageIcon("src/buttons/hupe.png"));
    }
    
    public static void main(String[] args) {
    //	GuiUser gu = new GuiUser();
		GuiDrive gd = new GuiDrive();
	}
       
    
}
/*
button.setBackground(null);
button.setBorder(null);
button.setBordercolor(null)
*/
