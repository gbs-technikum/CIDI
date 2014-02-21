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
package car.Events;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import car.Gui.GuiJFrameMain;
import car.Gui.GuiJPLogin;

public class GuiDriveEventAction implements ActionListener{
	
	private GuiJFrameMain guiMain;
	private JPanel jpExtern;
	private GuiJPLogin guiLogin;
	
	public GuiDriveEventAction(GuiJFrameMain guiMain) {
		this.guiMain = guiMain;
	}

	public GuiDriveEventAction(GuiJPLogin guiLogin, GuiJFrameMain guiMain) {
		this.guiLogin = guiLogin;
		this.guiMain = guiMain;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String event = e.getActionCommand();
		
		System.out.println(event);
//		System.out.println(e);
		
		switch (event) {
		case "ZumLogin":
			System.out.println("-> Zum Login");
			guiMain.jpNeuZeichnen("ZurLoginOberflaeche");
			break;

		case "Abbrechen":
			System.out.println("-> Abbrechen");
//			(GuiJPLogin)jpExtern.
			
			break;
		
		case "Anmelden":
			if(this.guiLogin.checkLogin()){
				guiMain.jpNeuZeichnen("ZurDriveOberflaeche");
			} 			
			break;
			
		case "Sitzung Beenden":
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
//			jf.fernlichtSchalten();
			break;
			
		case "Abblendlicht schalten":
//			jf.abblendlichtSchalten();
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
