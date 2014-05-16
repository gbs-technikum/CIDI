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

public class GuiDriveEventAction implements ActionListener{
	
	private GuiJFrameMain guiMain;
	private GuiJPLogin guiLogin;
	private GuiJPDrive guiDrive;
	
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
		
		switch (event) {
		case "ZumLogin":
			guiMain.jpNeuZeichnen("ZurLoginOberflaeche", null, null);
			break;

		case "Abbrechen":
			//Felder löschen //dabenbank angelegtes zeuch löschen
			this.guiLogin.felderLoeschen();
			this.guiMain.getDatenbank().wartenAbbrechen();
			break;
		
		case "Anmelden":
			this.guiLogin.checkLogin();
			//Überprüfen in GuiJPLogin ob Daten korrekt sind und sitzung belegt.
			if(this.guiLogin.checkLoginDaten()){
				if(this.guiMain.getDatenbank().getMaxWarteZeitsek() == -1 ){		//Zeit ist auch abgelaufen daher situng frei und SCHUBRACKETE
					this.guiLogin.getMyTimer().stop();
					this.guiLogin.goToDrive();
				}
			}
			break;
			
		case "Sitzung Beenden":
			this.guiDrive.goToLogin();
			break;
						
		case "Fernlicht schalten":
//			guiDrive.getFernlichtButton().pinTogglen();
			break;
			
		case "Abblendlicht schalten":
//			guiDrive.getAbblendlichtButton().pinTogglen();
			break;   
		
		default:
			break;
		}
	}
}
