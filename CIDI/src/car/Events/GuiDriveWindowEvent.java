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

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import car.Hilfsklassen.DAO;

public class GuiDriveWindowEvent implements WindowListener{

	private DAO db;
	
	public GuiDriveWindowEvent(DAO db){
		this.db = db;
	}
	
    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        int result = JOptionPane.showConfirmDialog(null, "Wollen Sie das Programm wirklich beenden?","Programm beenden?", JOptionPane.YES_NO_OPTION);
	if(result==JOptionPane.YES_OPTION){
		System.out.println("window Event");
		if(!this.db.abmelden()){
			System.out.println("window event - wAbrechen");
			this.db.wartenAbbrechen();
		}
		System.exit(NORMAL);
		}
    }

    @Override
    public void windowClosed(WindowEvent e) {
        
    }

    @Override
    public void windowIconified(WindowEvent e) {
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        
    }

    @Override
    public void windowActivated(WindowEvent e) {
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        
    }


}