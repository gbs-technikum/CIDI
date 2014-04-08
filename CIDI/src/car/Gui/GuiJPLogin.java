package car.Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import car.Hilfsklassen.SQL;

public class GuiJPLogin extends JPanel{

	private static final long serialVersionUID = 1L;
    private JTextField jtuser;
    private JPasswordField jpassword;
    private JButton jbanmelden, jbabbrechen;
    private LineBorder lbKastl;
    private int wartezeitMin, wartezeitSek;
    private JLabel jlWarteZeit;
    private SQL mysql;
	
    private GuiJFrameMain guiMain;
    
    public GuiJPLogin(GuiJFrameMain guiMain){

    	this.setLayout(new BorderLayout());
		this.guiMain = guiMain;
		this.mysql = new SQL();
		
    	initComponents();
    	initEvents();
    }

	private void initEvents() {
		countDownZaehler();

		GuiDriveEventAction gdea = new GuiDriveEventAction(this, guiMain);
		jbabbrechen.addActionListener(gdea);
		jbanmelden.addActionListener(gdea);
		
		GuiDriveEventKey gdek = new GuiDriveEventKey(this);
		jtuser.addKeyListener(gdek);
		jpassword.addKeyListener(gdek);
		
	}

	private void countDownZaehler() {
		int delay = 1000; //milliseconds
		  ActionListener taskPerformer = new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
		    	  
		    	  wartezeitSek--;
		    	  if(wartezeitSek<0){
		    		  wartezeitSek=59;
		    		  wartezeitMin--;
		    	  }
		    	  String wMin=""+wartezeitMin,wSek=""+wartezeitSek;
		    	  if(wartezeitMin<10)
		    		  wMin = "0"+wartezeitMin;
		    	  if(wartezeitSek<10)
		    		  wSek="0"+wartezeitSek;
		          jlWarteZeit.setText(wMin+":"+wSek);
		          if(wartezeitMin==0 && wartezeitSek==0 && checkLogin()){
		        	  //-> vorheriger Benutzer soll ausgeloggt werden
		        	  System.out.println("-> Login alles ok! -> aufgehts");
		          }else if(wartezeitMin==0 && wartezeitSek==0){
		        	  System.out.println("-> Zeitabgelaufen");
		        	  wartezeitMin=15;
		        	  wartezeitSek=00;
		          }
		      }
		  };
		  new Timer(delay, taskPerformer).start();
	}

	private void initComponents() {
	
		//Hole Zeit von SQL-Datenbank
		getTimesql();
		
		//Design 
		lbKastl = new LineBorder(Color.gray,3);

		//Panels zerteilen
		JPanel jpleft = fjpLinks();
		JPanel jprigth = fjpRechts();
		
		//This - Panel Zambauen
        this.add(jpleft,BorderLayout.WEST);
        this.add(jprigth,BorderLayout.EAST);
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
    
    private void getTimesql() {
    	wartezeitMin=0;
    	wartezeitSek=5;
		
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
			return true;
		}
		return false;
	}

	public void checkLoginDaten(){
		String status = this.mysql.pruefeLogin(jtuser.getText(), new String(jpassword.getPassword()));
		
		System.out.println(status);
		
		switch (status) {
		case "Logindaten korrekt | Sitzung wird belegt": {
			jbanmelden.setEnabled(false);
			jtuser.setEditable(false);
			jpassword.setEditable(false);
			this.guiMain.jpNeuZeichnen("ZurDriveOberflaeche");
			break;
			}
		case "Logindaten korrekt | Sitzung belegt":{
			System.out.println("Bitte warten bis Sitzung freigegeben ist!");
			jbanmelden.setEnabled(false);
			jtuser.setEditable(false);
			jpassword.setEditable(false);
			break;
			}
		case "Logindaten falsch":{
			JOptionPane.showMessageDialog(null, "Die angegbene Daten sind Falsch! Bitte noch einmal Versuchen.","Fehler", JOptionPane.OK_OPTION);
			break;
			}
		}
	}

	private boolean sqlLogin(String user, String passwort) {
		//-> Abfrage SQL ob Logindaten ok sind und ob nicht besetzt ist.
		return true;
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
	}

}
