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
package i3a.car;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import i3a.car.GuiStart;


public class GuiDriveEvent implements ActionListener{
	
	private GuiDrive jf;
	private GuiStart gs;
	
	public GuiDriveEvent(JFrame jfextern) {
		if(jfextern.getTitle().contains("Login")){
			this.gs = (GuiStart) jfextern;			
		} else {	
			this.jf = (GuiDrive) jfextern;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String event = e.getActionCommand();
		
		System.out.println(event);
//		System.out.println(e);
		
		switch (event) {
		case "Weiter zur Anmeldung!":
			jf.setVisible(false);
			break;

		case "Sitzung Beenden":
			jf.setVisible(false);
			logout();
			break;
			
		case "Zum Webshop":
			//Start internet Explorer -> Amazon oder sonst was
			System.out.println("Ab zum Webshop!");
			break;
			
		case "Hupe":
			//Bild ändern
			System.out.println("Hup Hup");
			break;
			
		case "Fernlicht schalten":
			jf.fernlichtSchalten();
			break;
			
		case "Abblendlicht schalten":
			//Bild ändern
			System.out.println("Hup Hup");
			break;
		default:
			break;
		}
		
	}

	private void logout() {
		// sitzung Infos in Datenbank speichern!
		// Stream etc beenden
		// Sitzung für nächsten Freimachen!"
		System.out.println("logout");
		
	}


}
