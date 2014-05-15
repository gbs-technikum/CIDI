package car.Gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import car.Events.GuiDriveEventAction;
import car.Events.GuiDriveEventKeySteuerung;
import car.Events.GuiDriveEventMouse;
import car.Hilfsklassen.CIDIButton;
import car.Hilfsklassen.Controller;
import car.Hilfsklassen.DAO;
import car.Hilfsklassen.Player;

public class GuiJPDrive extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private GuiJFrameMain guiMain;
    private int wartezeitMin, wartezeitSek;
	
	private CIDIButton cbVorwaerts, cbRueckwaerts, cbRechts, cbLinks, cbAbblendlicht, cbFernlicht; 
    private JButton jbVerbindungBeenden;
    private JLabel jlZahlenVerbZeit, jlNutzerName;
    private DAO datenbank;
    private Canvas cStreambereich;
//	private Controller c;
    
    private ImageIcon[] iiObenArray, iiUntenArray, iiLinksArray, iiRechtsArray;
    private ImageIcon[] iiFernlichtArray, iiAbblendlichtArray;

    public GuiJPDrive(GuiJFrameMain guiMain, DAO db, Canvas cStreambereich){
//    	this.cStreambereich = new Canvas();
//    	this.cStreambereich.setSize(300, 200);
//    	this.cStreambereich.setBackground(Color.RED);
//    	this.cStreambereich = cStreambereich;

//    	c=Controller.getInstance();
    	
    	this.setLayout(new BorderLayout());
		this.guiMain = guiMain;
		this.datenbank = db;
		
		initComponents();
    	initEvents();
    }

	private void initEvents() {
		
		countDownZaehler();
		
		GuiDriveEventAction gdea = new GuiDriveEventAction(this, guiMain);
		
		//GuiDriveEventAciton - Breich
		cbFernlicht.getButton().addActionListener(gdea);
		cbAbblendlicht.getButton().addActionListener(gdea);
		cbVorwaerts.getButton().addActionListener(gdea);
		cbLinks.getButton().addActionListener(gdea);
		cbRueckwaerts.getButton().addActionListener(gdea);
		cbRechts.getButton().addActionListener(gdea);
		jbVerbindungBeenden.addActionListener(gdea);
		
		//GuiDriveEventMouse - Breich	
		cbFernlicht.getButton().addMouseListener(new GuiDriveEventMouse(cbFernlicht, guiMain));
		cbAbblendlicht.getButton().addMouseListener(new GuiDriveEventMouse(cbAbblendlicht, guiMain));
		cbVorwaerts.getButton().addMouseListener(new GuiDriveEventMouse(cbVorwaerts, guiMain));
		cbLinks.getButton().addMouseListener(new GuiDriveEventMouse(cbLinks, guiMain));
		cbRueckwaerts.getButton().addMouseListener(new GuiDriveEventMouse(cbRueckwaerts, guiMain));
		cbRechts.getButton().addMouseListener(new GuiDriveEventMouse(cbRechts, guiMain));
		
		//GuiDriveEventKeySteuerung - Bereich 
		GuiDriveEventKeySteuerung gdeks = new GuiDriveEventKeySteuerung(this);
		guiMain.addKeyListener(gdeks);
		guiMain.requestFocus();
	}

	private void initComponents() {
		
		initBilder();
		
		JPanel jpRechts = fMainPanelRechts();
		
		JPanel jpLinks = fMainPanelLinks();
		
		//Auf KlassenPanel Bauen
		this.add(jpRechts,BorderLayout.EAST);
		this.add(jpLinks,BorderLayout.CENTER);
	}
	
	private JPanel fMainPanelLinks() {
		JPanel jplinks = new JPanel();
        jplinks.setBorder(new CompoundBorder(jplinks.getBorder(), new LineBorder(Color.red,3)));
        jplinks.setLayout(new BoxLayout(jplinks, BoxLayout.Y_AXIS));
        
        //Stream von Webcam
        cStreambereich = new Canvas();
        cStreambereich.setBackground(Color.GREEN);
//      Player.startPlayer(cStreambereich);
        
        //BewegungsTasten
        JPanel jpBewegungsTasten = fSteuertasten();
        
    	//Funktionstasten
    	JPanel jpFunktionen = fFunktionstasten();
		
    	//Auf jplinks bauen
    	jplinks.add(cStreambereich);
    	jplinks.add(jpFunktionen);
    	jplinks.add(jpBewegungsTasten);
    	
		return jplinks;
	}

	private JPanel fMainPanelRechts() {
		JPanel jprechts = new JPanel();
        jprechts.setLayout(new BoxLayout(jprechts, BoxLayout.Y_AXIS));
        
        JPanel jpSitzungsInfo = new JPanel();
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
        setjlNutzername();
        jpNutzerName.add(this.jlNutzerName);
        
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

	private void setjlNutzername() {
        this.jlNutzerName = new JLabel(datenbank.getNutzername());  // -> Nutzername vom Login soll übergeben werden
		jlNutzerName.setFont(new Font("Arial", Font.PLAIN, 40));
	}

	private void countDownZaehler() {
		this.zeitSetzen();
		
		int delay = 1000; //milliseconds
		  ActionListener taskPerformer = new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
		    	  wartezeitSek--;
		    	  if(wartezeitSek<0){
		    		  wartezeitSek=59;
		    		  wartezeitMin--;
		    	  }
		    	  String wMin=""+wartezeitMin,wSek=""+wartezeitSek;
		    	  if(wartezeitMin<10){
		    		  wMin = "0"+wartezeitMin;
		    	  }
		    	  if(wartezeitSek<10){
		    		  wSek="0"+wartezeitSek;
		    	  }
		    	  
		    	  jlZahlenVerbZeit.setText(wMin+":"+wSek);
		          
		    	  if(wartezeitMin==0 && wartezeitSek==0){
		        	  if(datenbank.getMaxWarteZeitsek() != -1){ //Abfrage ob jeamnd in warteschlange sitzt -> -1 wenn Schlange leer
		        		  goToLogin();
		        	  } else {
		        		  wartezeitMin=14;
		        		  wartezeitSek=59;
		        	  }
		          }
		      }
		  };
		  new Timer(delay, taskPerformer).start();
	}
	
	public void goToLogin() {
		this.datenbank.abmelden();

//		c.close();
		
		// Stream etc beenden
		guiMain.jpNeuZeichnen("ZurLoginOberflaeche", null, null);
	}
	
	private JPanel fFunktionstasten() {
		JPanel jpFunktionen = new JPanel();
    	jpFunktionen.setLayout(new BoxLayout(jpFunktionen,BoxLayout.X_AXIS));
    	jpFunktionen.setBorder(new CompoundBorder(jpFunktionen.getBorder(), new LineBorder(Color.yellow,3)));
    	cbAbblendlicht = new CIDIButton(iiAbblendlichtArray, "Abblendlicht schalten");
    	cbFernlicht = new CIDIButton(iiFernlichtArray, "Fernlicht schalten");
    	
    	jpFunktionen.add(cbFernlicht.getButton());
    	jpFunktionen.add(cbAbblendlicht.getButton());
		
    	return jpFunktionen;
	}

	private JPanel fSteuertasten() {
		JPanel jpBewegungsTasten = new JPanel();
        jpBewegungsTasten.setBorder(new CompoundBorder(jpBewegungsTasten.getBorder(), new LineBorder(Color.green,3)));
        GridBagConstraints bgc = new GridBagConstraints();
    	jpBewegungsTasten.setLayout(new GridBagLayout());
       
        cbVorwaerts = new CIDIButton(iiObenArray, "Vorwaerts");
    	bgc.fill = GridBagConstraints.HORIZONTAL;
    	bgc.gridx = 1;
    	bgc.gridy = 0;
    	jpBewegungsTasten.add(cbVorwaerts.getButton(), bgc);
        
        cbLinks = new CIDIButton(iiLinksArray, "Links");
    	bgc.fill = GridBagConstraints.HORIZONTAL;
    	bgc.gridx = 0;
    	bgc.gridy = 1;
    	jpBewegungsTasten.add(cbLinks.getButton(), bgc);
    	
        cbRueckwaerts = new CIDIButton(iiUntenArray, "Rueckwaerts");
    	bgc.fill = GridBagConstraints.HORIZONTAL;
    	bgc.gridx = 1;
    	bgc.gridy = 1;
    	jpBewegungsTasten.add(cbRueckwaerts.getButton(), bgc);
    	
        cbRechts = new CIDIButton(iiRechtsArray, "Rechts");
    	bgc.fill = GridBagConstraints.HORIZONTAL;
    	bgc.gridx = 2;
    	bgc.gridy = 1;
    	jpBewegungsTasten.add(cbRechts.getButton(), bgc);
    	
		return jpBewegungsTasten;
	}
	
	private void initBilder() {
	    iiObenArray = new ImageIcon[2];
	    iiObenArray[0] = new ImageIcon(getClass().getResource("/buttons/oben_inaktiv.png"));
	    iiObenArray[1] = new ImageIcon(getClass().getResource("/buttons/oben_aktiv.png"));
	    
	    iiUntenArray = new ImageIcon[2];
	    iiUntenArray[0] = new ImageIcon(getClass().getResource("/buttons/unten_inaktiv.png"));
	    iiUntenArray[1] = new ImageIcon(getClass().getResource("/buttons/unten_aktiv.png"));
	    
	    iiLinksArray = new ImageIcon[2];
	    iiLinksArray[0] = new ImageIcon(getClass().getResource("/buttons/links_inaktiv.png"));
	    iiLinksArray[1] = new ImageIcon(getClass().getResource("/buttons/links_aktiv.png"));
	    
	    iiRechtsArray = new ImageIcon[2];
	    iiRechtsArray[0] = new ImageIcon(getClass().getResource("/buttons/rechts_inaktiv.png"));
	    iiRechtsArray[1] = new ImageIcon(getClass().getResource("/buttons/rechts_aktiv.png"));
	    
	    iiFernlichtArray = new ImageIcon[2];
	    iiFernlichtArray[0] = new ImageIcon(getClass().getResource("/buttons/fernlicht_inaktiv.png"));
	    iiFernlichtArray[1] = new ImageIcon(getClass().getResource("/buttons/fernlicht_aktiv.png"));
	    
	    iiAbblendlichtArray = new ImageIcon[2];
	    iiAbblendlichtArray[0] = new ImageIcon(getClass().getResource("/buttons/abblendlicht_inaktiv.png"));
	    iiAbblendlichtArray[1] = new ImageIcon(getClass().getResource("/buttons/abblendlicht_aktiv.png"));
	}
	
	//Setzte Zeit zum Fahren 
	private void zeitSetzen(){
       	this.wartezeitSek=59;
       	this.wartezeitMin=14; 
	}
	
	public CIDIButton getFernlichtButton(){
		return this.cbFernlicht;
	}
	
	public CIDIButton getAbblendlichtButton(){
		return this.cbAbblendlicht;
	}

	public CIDIButton getVorwarets(){
		return this.cbVorwaerts;
	}
	
	public CIDIButton getLinks(){
		return this.cbLinks;
	}
	
	public CIDIButton getRueckwaerts(){
		return this.cbRueckwaerts;
	}
	
	public CIDIButton getRechts(){
		return this.cbRechts;
	}	
}

