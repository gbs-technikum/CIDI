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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import car.Gui.GuiJFrameMain;
import car.Gui.GuiJPDrive;
import car.Gui.GuiJPLogin;
import car.Hilfsklassen.SQL;

public class GuiDriveEventAction implements ActionListener{
	
	private GuiJFrameMain guiMain;
	private GuiJPLogin guiLogin;
	private GuiJPDrive guiDrive;
	private SQL mysql;
	
	public GuiDriveEventAction(GuiJFrameMain guiMain) {
		this.guiMain = guiMain;
	}

	public GuiDriveEventAction(GuiJPLogin guiLogin, GuiJFrameMain guiMain) {
		this.guiLogin = guiLogin;
		this.guiMain = guiMain;
	}

	public GuiDriveEventAction(GuiJPDrive guiDrive, GuiJFrameMain guiMain) {
		this.guiDrive = guiDrive;
		this.guiMain = guiMain;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String event = e.getActionCommand();
		
//		System.out.println(event);
//		System.out.println(e);
		
		switch (event) {
		case "ZumLogin":
			System.out.println("-> Zum Login");
			guiMain.jpNeuZeichnen("ZurLoginOberflaeche");
			break;

		case "Abbrechen":
			//Felder löschen
			System.out.println("abbrechen");
			this.guiLogin.felderLoeschen();
			break;
		
		case "Anmelden":
			//Überprüfen in GuiJPLogin ob Daten korrekt sind und sitzung belegt.
			if(this.guiLogin.checkLogin()){
				this.guiLogin.checkLoginDaten();
				//guiMain.jpNeuZeichnen("ZurDriveOberflaeche");
			} 			
			break;
			
		case "Sitzung Beenden":
			logout();
			break;
			
		case "Zum Webshop":
			//Start internet Explorer -> Amazon oder sonst was
			System.out.println("Ab zum Webshop!");
			break;
			
		case "Fernlicht schalten":
			System.out.println("Fernlicht schalten");
			guiDrive.getFernlichtButton().bildSchalten();
			guiMain.requestFocus();
			break;
			
		case "Abblendlicht schalten":
			System.out.println("Abblendlicht schalten");
			guiDrive.getAbblendlichtButton().bildSchalten();
			guiMain.requestFocus();
			break;
		
		default:
			break;
		}
		
	}

	private void logout() {
		this.mysql = new SQL();
		if(mysql.logout()){
			// Stream etc beenden
			guiMain.jpNeuZeichnen("ZurLoginOberflaeche");
		} else {
			System.out.println("FEHLER -> Logout nicht möglich gdea methode logout");
		}
		
	}


}
