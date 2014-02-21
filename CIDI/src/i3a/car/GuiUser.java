/**
 * This file is part of the I3A project.
 *
 *
 * @copyright  	(c) the I3A GBS Muenchen
 * @author     	i3a <it@lala-rob.de>
 * @license    	GPLv3 http://www.gnu.org/licenses/gpl.html
 * @version		0.0.1
 *
 */
package i3a.car;


import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;

public class GuiUser extends JFrame {
    
	private static final long serialVersionUID = 1L;
	private Container c;
    private JTextField jtuser;
    private JPasswordField jpassword;
    private JButton jbanmelden, jbabbrechen;

    private int wartezeitMin, wartezeitSek;
    private JLabel jlwartenzeit;
    boolean loginOk;
    private String sessionID;


    public GuiUser() {
        setTitle("\"Connect it, Drive it!\" - Login");
        setSize(600, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponent();
        initEvents();
        setVisible(true);
    }



	private void initComponent() {
	   getTimesql();
       c = getContentPane();
       this.setLayout(new GridLayout(1, 2));

       //Linke Seite
       JPanel jpleft = new JPanel();
       //jpleft.setLayout(new GridLayout(2, 1));
       JLabel jlconnect = new JLabel("\"Connect it, Drive it!\"");
       jlconnect.setBorder(new CompoundBorder(jlconnect.getBorder(), new LineBorder(Color.gray,3)));
       jlconnect.setFont(new Font("Arial", Font.PLAIN, 24));
       jpleft.add(jlconnect);
       
       
       JPanel jplefti = new JPanel();
       jplefti.setLayout(new GridLayout(3, 1));
       jplefti.setBorder(new CompoundBorder(jplefti.getBorder(), new LineBorder(Color.gray,3)));
       
       JPanel jpluser = new JPanel();
       JLabel jluser = new JLabel(" Benutzer ");
       jluser.setBorder(new CompoundBorder(jluser.getBorder(), new LineBorder(Color.gray,3)));
       jtuser = new JTextField(12);
       jtuser.setBorder(new CompoundBorder(jluser.getBorder(), new LineBorder(Color.gray,3)));
       jpluser.add(jluser);
       jpluser.add(jtuser);
       
       JPanel jplpassword = new JPanel();
       JLabel jlpassword = new JLabel(" Passwort ");
       jlpassword.setBorder(new CompoundBorder(jlpassword.getBorder(), new LineBorder(Color.gray,3)));
       jpassword = new JPasswordField(12);
       jpassword.setEditable(false);
       jpassword.setBorder(new CompoundBorder(jpassword.getBorder(), new LineBorder(Color.gray,3)));
       jplpassword.add(jlpassword);
       jplpassword.add(jpassword);      
       
       JPanel jpbutton = new JPanel();
       
       jbabbrechen = new JButton("Abbrechen");
       jbanmelden = new JButton("Anmelden");
       
       jpbutton.add(jbanmelden);
       jpbutton.add(jbabbrechen);
       
       jplefti.add(jpluser);
       jplefti.add(jplpassword);
       jplefti.add(jpbutton);
       
       jpleft.add(jplefti);
       
       //Rechte Seite
       JPanel jpright = new JPanel();
       
       JPanel jprighti = new JPanel();
       jprighti.setLayout(new GridLayout(2, 1));
       JLabel jlcam = new JLabel(" Live-View ");
       jlcam.setBorder(new CompoundBorder(jlcam.getBorder(), new LineBorder(Color.gray,3)));
       jpright.add(jlcam);
       
       JLabel jlimg = new JLabel();
//       ImageIcon img1 = new ImageIcon(getClass().getResource("test.jpg"));
//       jlimg.setIcon(img1);
       
       jpright.add(jlimg);
       
       JPanel jprightzeit = new JPanel();
       JLabel jlwartentext = new JLabel("Verbleibende Wartezeit ");
       jlwartentext.setBorder(new CompoundBorder(jlwartentext.getBorder(), new LineBorder(Color.gray,3)));
       jlwartenzeit = new JLabel("15:00");
       jlwartenzeit.setBorder(new CompoundBorder(jlwartenzeit.getBorder(), new LineBorder(Color.gray,3)));
       jprightzeit.add(jlwartentext);
       jprightzeit.add(jlwartenzeit);
       
       jprighti.add(jprightzeit);
       
       jpright.add(jprighti);
       
       c.add(jpleft);
       c.add(jpright);
    }
    
    
    private void getTimesql() {
    	wartezeitMin=0;
    	wartezeitSek=5;
		
	}

	private void initEvents() {
		// TODO Auto-generated method stub
		jbabbrechen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(sessionID != null) {
					jbanmelden.setEnabled(true);
					jbabbrechen.setText("Abbrechen");
					jtuser.setEditable(true);
					jpassword.setEditable(true);
					loginOk=false;
				}
				
				jtuser.setText("");
				jpassword.setText("");
				jtuser.requestFocus();
			}
		});
		
		jbanmelden.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isEmpty((JTextField) jtuser))
					JOptionPane.showMessageDialog(null, "Bitte Alle Felder ausüllen!","Fehler", JOptionPane.OK_OPTION);
				else if(isEmpty((JTextField) jpassword))
					JOptionPane.showMessageDialog(null, "Bitte Passwort eingben!","Fehler", JOptionPane.OK_OPTION);
				else
					checkLogin();
			}
		});
		
		jtuser.setInputVerifier(new InputVerifier() {
			
			@Override
			public boolean verify(JComponent arg0) {
				if(isEmpty(jtuser) && isEmpty(jpassword)){
					return false;
				} else {
					jpassword.setEditable(true);
				}
				return true;
			}
		});
		
		jtuser.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					jpassword.requestFocus();
				}
			}
		});

		jpassword.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					jbanmelden.requestFocus();
				}
			}
		});
		
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
		          jlwartenzeit.setText(wMin+":"+wSek);
		          if(wartezeitMin==0 && wartezeitSek==0 && loginOk){
		        	  setVisible(false);
//		        	  new GuiDrive(GuiUser.this);			// Eventuallität (loginKorrekt && warteZeitAbgelaufen ) -> Dann Login????
		          }else if(wartezeitMin==0 && wartezeitSek==0){
		        	  wartezeitMin=15;
		        	  wartezeitSek=00;
		          }
		      }
		  };
		  new Timer(delay, taskPerformer).start();
	}


	private boolean checkLogin() {
			if(sqlLogin((JTextField) jtuser,(JTextField) jpassword)){
				jbanmelden.setEnabled(false);
				jtuser.setEditable(false);
				jpassword.setEditable(false);
				return true;
			}
			else
				JOptionPane.showMessageDialog(null, "Die angegbene Daten sind Falsch! Bitte noch einmal Versuchen.","Fehler", JOptionPane.OK_OPTION);
		return false;
	}


	private boolean sqlLogin(JTextField user, JTextField password) {
		sessionID="sadfg58sd4fg4sdf";
		return true;
	}

	private boolean isEmpty(JTextField input) {
		if("".equals((String)input.getText()))
			return true;
		return false;
	}

	public String getNutzerName(){
		return jtuser.getText();
	}
	
	public static void main(String[] args) {
		GuiUser gu = new GuiUser();
	}
}
