package car.Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

public class GuiJPDrive extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private GuiJFrameMain guiMain;
	
	private JButton jbVorwaerts, jbRueckwaerts, jbRechts, jbLinks, jbHupe, jbAbblendlicht, jbFernlicht;
    private JButton jbZumWebshop, jbVerbindungBeenden;
    private JLabel jlZahlenVerbZeit;
    
    private ImageIcon[] iiObenArray, iiUntenArray, iiLinksArray, iiRechtsArray;
    private ImageIcon[] iiFernlichtArray, iiAbblendlichtArray, iiHupeArray;
	
    public GuiJPDrive(GuiJFrameMain guiMain){
    	this.setLayout(new BorderLayout());
    	
		this.guiMain = guiMain;
    	
    	initComponents();
    	initEvents();
    }

	private void initEvents() {
		// TODO Auto-generated method stub
		
	}

	private void initComponents() {
		
		initBilder();
		
		JPanel jpRechts = fMainPanelRechts();
		
		JPanel jpLinks = fMainPanelLinks();
		
		//Auf KlassenPanel Bauen
		this.add(jpRechts,BorderLayout.EAST);
		this.add(jpLinks,BorderLayout.WEST);
	}
	


	private JPanel fMainPanelLinks() {
		JPanel jplinks = new JPanel();
        jplinks.setBorder(new CompoundBorder(jplinks.getBorder(), new LineBorder(Color.red,3)));
        jplinks.setLayout(new BoxLayout(jplinks, BoxLayout.Y_AXIS));
        
      //Stream von Webcam
        JPanel jpStream = fStream();
		
        //BewegungsTasten
        JPanel jpBewegungsTasten = fSteuertasten();
        
    	//Funktionstasten
    	JPanel jpFunktionen = fFunktionstasten();
		
    	//Auf jplinks bauen
    	jplinks.add(jpStream);
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
        JLabel jlNutzername = new JLabel("Hans Meier");  // -> Nutzername vom Login soll übergeben werden
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

	private JPanel fStream() {
		JLabel jlTestBild = new JLabel(new ImageIcon("src/bilder/test.jpg"));
        JPanel jpStream = new JPanel();
        jpStream.add(jlTestBild);
		return jpStream;
	}
	
	private JPanel fFunktionstasten() {
		JPanel jpFunktionen = new JPanel();
    	jpFunktionen.setLayout(new BoxLayout(jpFunktionen,BoxLayout.X_AXIS));
    	jpFunktionen.setBorder(new CompoundBorder(jpFunktionen.getBorder(), new LineBorder(Color.yellow,3)));
    	jbAbblendlicht = new JButton(new ImageIcon("src/buttons/abblendlicht_inaktiv.png"));
    	jbAbblendlicht.setActionCommand("Abblendlicht schalten");
    	jbFernlicht = new JButton(new ImageIcon("src/buttons/fernlicht_inaktiv.png"));
    	jbFernlicht.setActionCommand("Fernlicht schalten");
    	jbHupe = new JButton(new ImageIcon("src/buttons/hupe_inaktiv.png"));
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
       
        jbVorwaerts = new JButton(new ImageIcon("src/buttons/oben_inaktiv.png"));
    	bgc.fill = GridBagConstraints.HORIZONTAL;
    	bgc.gridx = 1;
    	bgc.gridy = 0;
    	jpBewegungsTasten.add(jbVorwaerts, bgc);
        
        jbLinks = new JButton(new ImageIcon("src/buttons/links_inaktiv.png"));
    	bgc.fill = GridBagConstraints.HORIZONTAL;
    	bgc.gridx = 0;
    	bgc.gridy = 1;
    	jpBewegungsTasten.add(jbLinks, bgc);
    	
        jbRueckwaerts = new JButton(new ImageIcon("src/buttons/unten_inaktiv.png"));
    	bgc.fill = GridBagConstraints.HORIZONTAL;
    	bgc.gridx = 1;
    	bgc.gridy = 1;
    	jpBewegungsTasten.add(jbRueckwaerts, bgc);
    	
        jbRechts = new JButton(new ImageIcon("src/buttons/rechts_inaktiv.png"));
    	bgc.fill = GridBagConstraints.HORIZONTAL;
    	bgc.gridx = 2;
    	bgc.gridy = 1;
    	jpBewegungsTasten.add(jbRechts, bgc);
    	
		return jpBewegungsTasten;
	}
	
	private void initBilder() {
	    iiObenArray = new ImageIcon[2];
	    iiObenArray[0] = new ImageIcon("src/buttons/oben_inaktiv.png");
	    iiObenArray[1] = new ImageIcon("src/buttons/oben_aktiv.png");
	    
	    iiUntenArray = new ImageIcon[2];
	    iiUntenArray[0] = new ImageIcon("src/buttons/unten_inaktiv.png");
	    iiUntenArray[1] = new ImageIcon("src/buttons/unten_aktiv.png");
	    
	    iiLinksArray = new ImageIcon[2];
	    iiLinksArray[0] = new ImageIcon("src/buttons/links_inaktiv.png");
	    iiLinksArray[1] = new ImageIcon("src/buttons/links_aktiv.png");
	    
	    iiRechtsArray = new ImageIcon[2];
	    iiRechtsArray[0] = new ImageIcon("src/buttons/rechts_inaktiv.png");
	    iiRechtsArray[1] = new ImageIcon("src/buttons/rechts_aktiv.png");
	    
	    iiFernlichtArray = new ImageIcon[2];
	    iiFernlichtArray[0] = new ImageIcon("src/buttons/fernlicht_inaktiv.png");
	    iiFernlichtArray[1] = new ImageIcon("src/buttons/fernlicht_aktiv.png");
	    
	    iiAbblendlichtArray = new ImageIcon[2];
	    iiAbblendlichtArray[0] = new ImageIcon("src/buttons/abblendlicht_inaktiv.png");
	    iiAbblendlichtArray[1] = new ImageIcon("src/buttons/abblendlicht_aktiv.png");
	    
	    iiHupeArray = new ImageIcon[2];
	    iiHupeArray[0] = new ImageIcon("src/buttons/hupe_inaktiv.png");
	    iiHupeArray[1] = new ImageIcon("src/buttons/hupe_aktiv.png");
	}
	
}
