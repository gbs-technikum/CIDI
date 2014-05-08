package car.Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import car.Events.GuiDriveEventAction;
import car.Events.GuiDriveEventKey;
import car.Hilfsklassen.DAO;

public class GuiJPLogin extends JPanel{

	private static final long serialVersionUID = 1L;
    private JTextField jtuser;
    private JPasswordField jpassword;
    private JButton jbanmelden, jbabbrechen;
    private LineBorder lbKastl;
    private int wartezeitMin, wartezeitSek;
    private JLabel jlWarteZeit;
    private DAO datenbank;
	
    private GuiJFrameMain guiMain;
    
    public GuiJPLogin(GuiJFrameMain guiMain){

    	this.setLayout(new BorderLayout());
		this.guiMain = guiMain;
		this.datenbank = new DAO();
		this.datenbank.verbindungAufbauen("jdbc:mysql://localhost:3306/cidi", "root", "mysql");
		
    	initComponents();
    	initEvents();
    }

	private void initEvents() {

		GuiDriveEventAction gdea = new GuiDriveEventAction(this, guiMain);
		jbabbrechen.addActionListener(gdea);
		jbanmelden.addActionListener(gdea);
		
		GuiDriveEventKey gdek = new GuiDriveEventKey(this);
		jtuser.addKeyListener(gdek);
		jpassword.addKeyListener(gdek);
		
	}

	private void countDownZaehler() {
		int delay = 1000; //milliseconds  (zieht millisekunden ab)
		
		  ActionListener taskPerformer = new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
		    	  System.out.println(wartezeitMin + " " + wartezeitSek);
		          
		          if(wartezeitMin==0 && wartezeitSek==0 || wartezeitMin==-666){
		        	  System.out.println("Zum prüfen der logindaten");
		        	  if(checkLogin()){
		        		  System.out.println("in ZeitMethode");
		        		  goToDrive();
		        	  }
		          } else {
//		        	  System.out.println("-> Bitte warten bis Zeit abgelaufen ist"); //Jede sekunde hier
		          }
		    	  
		    	  wartezeitSek--;
		    	  if(wartezeitSek<0){
		    		  wartezeitSek=59;
		    		  wartezeitMin--;
		    	  }
		    	  String wMin=""+wartezeitMin,wSek=""+wartezeitSek;
		    	  
		    	  if(wartezeitMin<10){ //nullstelle Minuten
		    		  wMin = "0"+wartezeitMin;
		    	  }
		    	  if(wartezeitSek<10){	//nullstelle sekunden
		    		  wSek="0"+wartezeitSek;
		    	  }
		          jlWarteZeit.setText(wMin+":"+wSek);
		          
		          if(wartezeitSek%5 == 0){
		        	  getTimesql();
		          }
		      }
		  };
		  new Timer(delay, taskPerformer).start();
	}

	private void initComponents() {
		//Design 
		lbKastl = new LineBorder(Color.gray,3);

		//Panels zerteilen
		JPanel jpleft = fjpLinks();
		JPanel jprigth = fjpRechts();
		
		//This - Panel Zambauen
        this.add(jpleft,BorderLayout.WEST);
        this.add(jprigth,BorderLayout.EAST);
        
        //Timer
		startTimer();
	}

    private void getTimesql() {
    	System.out.println("getTimeSQL");
    	int i = datenbank.getMaxWarteZeitsek();
    	
    	if(i != -1){    	
    		this.wartezeitSek = i%60;
    		this.wartezeitMin = i/60;
    	} else {
    		this.jlWarteZeit.setText("Sitzung ist frei!");
    		this.wartezeitMin = -666;
    	}
	}
	
	private void startTimer() {
    	int i = datenbank.getMaxWarteZeitsek();
    	System.out.println("in startTimer");
    	System.out.println(i);
    	if(i != -1){    	
    		this.wartezeitSek = i%60;
    		this.wartezeitMin = i/60;
    		System.out.println(wartezeitMin + " : " + wartezeitSek );
    		countDownZaehler();
    	} else {
    		this.jlWarteZeit.setText("Sitzung ist frei!");
    		this.wartezeitMin = -666;
    		System.out.println("sitzung frei -> start Timer");
    	}
	}

	private JPanel fjpRechts() {
		JPanel jptempright = new JPanel();
	    jptempright.setLayout(new BorderLayout());
	    
	    JPanel jpText = new JPanel();
	    JLabel jlStreamText = new JLabel("-  Live-View  -");
	    jlStreamText.setFont(new Font("Arial", Font.BOLD , 15));
	    jpText.add(jlStreamText);
	    jpText.setBorder(new CompoundBorder(jpText.getBorder(), lbKastl));
	    
	    JLabel jlStream = new JLabel(new ImageIcon("src/bilder/test.jpg"));
	    
	    JPanel jpWarteZeit = new JPanel();
	    JLabel jlWartenText = new JLabel("Verbleibende Wartezeit ");
	    jlWartenText.setBorder(new CompoundBorder(jlWartenText.getBorder(), lbKastl));
	    jlWarteZeit = new JLabel("15:00");
	    jlWarteZeit.setBorder(new CompoundBorder(jlWarteZeit.getBorder(), lbKastl));
	    jpWarteZeit.add(jlWartenText);
	    jpWarteZeit.add(jlWarteZeit);
	     
	    jptempright.add(jpText, BorderLayout.NORTH);
	    jptempright.add(jlStream, BorderLayout.CENTER);
	    jptempright.add(jpWarteZeit, BorderLayout.SOUTH);
	       
	    return jptempright;
	}

	private JPanel fjpLinks() {
			
		JPanel jplefti = new JPanel();
        jplefti.setLayout(new GridLayout(3, 1));
        jplefti.setBorder(new CompoundBorder(jplefti.getBorder(), lbKastl ));
        
        JPanel jpuser = new JPanel();
        JLabel jluser = new JLabel(" Benutzer ");
        jluser.setBorder(new CompoundBorder(jluser.getBorder(), lbKastl));
        jtuser = new JTextField(12);
        jtuser.setBorder(new CompoundBorder(jluser.getBorder(), lbKastl));
        jpuser.add(jluser);
        jpuser.add(jtuser);
        
        JPanel jplpassword = new JPanel();
        JLabel jlpassword = new JLabel(" Passwort ");
        jlpassword.setBorder(new CompoundBorder(jlpassword.getBorder(), lbKastl));
        jpassword = new JPasswordField(12);
//        jpassword.setEditable(false);
        jpassword.setBorder(new CompoundBorder(jpassword.getBorder(), lbKastl));
        jplpassword.add(jlpassword);
        jplpassword.add(jpassword);      
        
        JPanel jpbutton = new JPanel();
        
        jbabbrechen = new JButton("Abbrechen");
        jbanmelden = new JButton("Anmelden");
        
        jpbutton.add(jbanmelden);
        jpbutton.add(jbabbrechen);
        
        jplefti.add(jpuser);
        jplefti.add(jplpassword);
        jplefti.add(jpbutton);
		
        return jplefti;
	}
  
    public void goToDrive() {
		felderLoeschen();
		System.out.println("go to Drive");
		this.guiMain.jpNeuZeichnen("ZurDriveOberflaeche", this.datenbank);
	}

	private boolean isEmpty(JTextField input) {
		if("".equals((String)input.getText()))
			return true;
		return false;
	}
    
	public boolean checkLogin() {
		if(isEmpty((JTextField) jtuser)) {
			JOptionPane.showMessageDialog(null, "Bitte Alle Felder ausüllen!","Fehler", JOptionPane.OK_OPTION);
		} else if(isEmpty((JTextField) jpassword)) {
			JOptionPane.showMessageDialog(null, "Bitte Passwort eingben!","Fehler", JOptionPane.OK_OPTION);
		} else {
			if(checkLoginDaten()){
				System.out.println("Zeile 232 -> checklogin");
				//logindaten korrekt -> entweder in Schlange anstellen oder sofort dran
				return true;
			}
		}
		return false;
	}

	public boolean checkLoginDaten(){
		String tempPW = new String(this.jpassword.getPassword()); //zwischenspeichern für mehtode anmelden(String user, String pw)
		
		if(datenbank.anmelden(this.jtuser.getText(), tempPW)){
//			this.guiMain.jpNeuZeichnen("zuDriveOberflaeche", this.datenbank);
			jbanmelden.setEnabled(false);
			jtuser.setEditable(false);
			jpassword.setEditable(false);
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "Die angegbene Daten sind Falsch! Bitte noch einmal Versuchen.","Fehler", JOptionPane.OK_OPTION);
			return false;
		}
	}

	public JPasswordField getJpassword() {
		return jpassword;
	}

	public JTextField getJUserTextField() {
		return this.jtuser;
	}

	public JButton getJbanmelden() {
		return jbanmelden;
	}

	public void felderLoeschen() {
		jtuser.setText("");
		jtuser.setEnabled(true);
		jpassword.setText("");
		jpassword.setEnabled(true);
		jtuser.requestFocus();
		jtuser.setEnabled(true);
		jbanmelden.setEnabled(true);
	}

	public DAO getDatenbank() {
		return this.datenbank;
	}


	
}
