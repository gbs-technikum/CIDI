/**
 * This file is part of the I3A project.
 *
 *
 * @copyright  (c) the I4A GBS Muenchen
 * @author     huberchris
 * @license    GPLv3 http://www.gnu.org/licenses/gpl.html
 * @version 0.0.1
 *
 */
package car.Events;

import static java.awt.Frame.NORMAL;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

import car.Hilfsklassen.DAO;

public class GuiDriveWindowEvent extends WindowAdapter{

	private DAO db;
	
	public GuiDriveWindowEvent(DAO db){
		this.db = db;
	}

    @Override
    public void windowClosing(WindowEvent e) {
        int result = JOptionPane.showConfirmDialog(null, "Wollen Sie das Programm wirklich beenden?","Programm beenden?", JOptionPane.YES_NO_OPTION);
	if(result==JOptionPane.YES_OPTION){
		if(!this.db.abmelden()){
			this.db.wartenAbbrechen();
		}
		this.db.verbindungAbbauen();
		System.exit(NORMAL);
		}
    }
}