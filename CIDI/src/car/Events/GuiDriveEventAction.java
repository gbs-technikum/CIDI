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
import car.Hilfsklassen.DAO;

public class GuiDriveEventAction implements ActionListener{
	
	private GuiJFrameMain guiMain;
	private GuiJPLogin guiLogin;
	private GuiJPDrive guiDrive;
	private DAO datenbank;
	
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
			guiMain.jpNeuZeichnen("ZurLoginOberflaeche", null);
			break;

		case "Abbrechen":
			//Felder löschen //dabenbank angelegtes zeuch löschen
			System.out.println("abbrechen");
			this.guiLogin.felderLoeschen();
			this.guiLogin.getDatenbank().wartenAbbrechen();
			break;
		
		case "Anmelden":
			//Überprüfen in GuiJPLogin ob Daten korrekt sind und sitzung belegt.
			System.out.println("in Actionlistener");
			if(this.guiLogin.checkLogin() && this.guiLogin.checkLoginDaten()){
				System.out.println("Anmelden korrekt -> AL");
				System.out.println(this.guiLogin.getDatenbank().getMaxWarteZeitsek());
				if(this.guiLogin.getDatenbank().getMaxWarteZeitsek() == 900){
					System.out.println("login korrekt prüfen ob man drann ist");
					this.guiLogin.goToDrive();
				}

			}

			break;
			
		case "Sitzung Beenden":
			this.guiDrive.goToLogin();
			break;
			
		case "Zum Webshop":
			//Start internet Explorer -> Amazon oder sonst was
			System.out.println("Ab zum Webshop!");
			break;
		
		case "Vorwaerts":
			System.out.println("Vorwärts schalten");
			break;

		case "Rueckwaerts":
			System.out.println("Rueckwaerts schalten");
			break;
			
		case "Links":
			System.out.println("links schalten");
			break;

		case "Rechts":
			System.out.println("rechts schalten");
			break;
			
		case "Fernlicht schalten":
			System.out.println("Fernlicht schalten");
			guiDrive.getFernlichtButton().pinTogglen();
			guiMain.requestFocus();
			break;
			
		case "Abblendlicht schalten":
			System.out.println("Abblendlicht schalten");
			guiDrive.getAbblendlichtButton().pinTogglen();
			guiMain.requestFocus();
			break;   
		
		default:
			break;
		}
	}
}
