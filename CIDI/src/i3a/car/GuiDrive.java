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

class GuiDrive extends JFrame{
    
    private Container c;
    private JButton jbVorwaerts, jbRueckwaerts, jbRechts, jbLinks, jbHupe, jbAbblendlicht, jbFernlicht;
    private JButton jbZumWebshop, jbVerbindungBeenden;
    private JLabel jlZahlenVerbZeit;
    private int restMin=15, restSek=0;
    private JPanel jpSitzungsInfo;
    private String nutzerName;
//    private GuiUser gu;
    

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
    	
    	GuiDriveEvent gde = new GuiDriveEvent();
    	
        this.addWindowListener(gde);
    	
    	jbVerbindungBeenden.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				logout();
				System.out.println("Beenden");
			}
		});
    	
    	jbZumWebshop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Verlinkung zu Browser -> Webshop
				System.out.println("-> Ab zum Shopping");
			}
		});
    	
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
		        	  logout();
		     
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
//        c.setLayout(new GridLayout(2, 1));


// Containerbereich Norden
        JPanel jpNorth = new JPanel();
        JLabel jlSlogen = new JLabel("Connect it, Drive it!");
        jlSlogen.setFont(new Font("Arial", 12, 35));
        jpNorth.add(jlSlogen);
        
//Containerbereich Links
        JPanel jplinks = new JPanel();
        jplinks.setBorder(new CompoundBorder(jplinks.getBorder(), new LineBorder(Color.red,3)));
//        jplinks.setLayout(new BoxLayout(jplinks, BoxLayout.Y_AXIS));
        jplinks.setLayout(new BorderLayout());
       
        //Stream von Webcam
        JLabel jlTestBild = new JLabel(new ImageIcon("src/i3a/car/bilder/test.jpg"));
        JPanel jpStream = new JPanel();
        jpStream.add(jlTestBild);
        jplinks.add(jpStream,BorderLayout.NORTH);
        
        //Steuerbereich
        JPanel jpSteuerbereich = new JPanel();
        
        //BewegungsTasten
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
        
    	jpSteuerbereich.add(jpBewegungsTasten,BorderLayout.WEST);
        
    	//Funktionstasten
    	JPanel jpFunktionen = new JPanel();
    	jpFunktionen.setLayout(new BoxLayout(jpFunktionen,BoxLayout.Y_AXIS));
    	jpFunktionen.setBorder(new CompoundBorder(jpFunktionen.getBorder(), new LineBorder(Color.yellow,3)));
    	jbAbblendlicht = new JButton("Abblendlicht");
    	jbFernlicht = new JButton("Fernlicht");
    	jbHupe = new JButton(new ImageIcon("src/buttons/hupe.png"));
    	jpFunktionen.add(jbHupe);
    	jpFunktionen.add(jbFernlicht);
    	jpFunktionen.add(jbAbblendlicht);
    	
    	jpSteuerbereich.add(jpFunktionen,BorderLayout.EAST);
    	
    	jplinks.add(jpSteuerbereich,BorderLayout.CENTER);
    	
//Containerbereich Rechts
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
        jlZahlenVerbZeit.setFont(new Font("Arial", Font.PLAIN, 25));
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
               

//Containerbereich South
        JPanel jpSouth = new JPanel(new BorderLayout());
        JLabel jlCopyRight = new JLabel("© all rights reserved for the best Class!");
        jbZumWebshop = new JButton("Zum WebShop");
        
        jpSouth.add(jlCopyRight,BorderLayout.WEST);
    	jpSouth.add(jbZumWebshop,BorderLayout.EAST);
        
 //Auf Container zambauen
        c.add(jpSouth,BorderLayout.SOUTH);
        c.add(jpNorth,BorderLayout.NORTH);
        c.add(jprechts,BorderLayout.EAST);
        c.add(jplinks,BorderLayout.CENTER);

        

    }
    
    public static void main(String[] args) {
    //	GuiUser gu = new GuiUser();
		GuiDrive gd = new GuiDrive();
	}
    
	private void logout() {
		//Sitzung soll abgebrochen werden
		System.out.println("Benutzer soll ausgeloggt werden!");
		this.setVisible(false);
		// GuiUser.setVisible(true);
		//Verbindung mit SQL abgebaut bla bla usw... 
	}
}
/*
button.setBackground(null);
button.setBorder(null);
button.setBordercolor(null)
*/
