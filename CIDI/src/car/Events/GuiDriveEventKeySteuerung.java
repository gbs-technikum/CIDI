package car.Events;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import car.Gui.GuiJPDrive;

public class GuiDriveEventKeySteuerung extends KeyAdapter{

	private GuiJPDrive guiDrive;
	
	public GuiDriveEventKeySteuerung(GuiJPDrive guiD)  {
		this.guiDrive = guiD;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				this.guiDrive.getVorwarets().bildSchalten(); //Vorwärtsknopschalten
				break;
			case KeyEvent.VK_DOWN:
				this.guiDrive.getRueckwaerts().bildSchalten();
				break;
			case KeyEvent.VK_LEFT:
				this.guiDrive.getLinks().bildSchalten();
				break;
			case KeyEvent.VK_RIGHT:
				this.guiDrive.getRechts().bildSchalten();
				break;
			case KeyEvent.VK_H:
				this.guiDrive.getHupe().bildSchalten();   //Hupe an
				break;
			case KeyEvent.VK_F:
				this.guiDrive.getFernlichtButton().bildSchalten();  //Fernlicht an/aus
				break;
			case KeyEvent.VK_A:
				this.guiDrive.getAbblendlichtButton().bildSchalten();  //Abblendlicht an/aus
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				this.guiDrive.getVorwarets().bildSchalten(); //Vorwärtsknop schalten
				break;
			case KeyEvent.VK_DOWN:
				this.guiDrive.getRueckwaerts().bildSchalten();
				break;
			case KeyEvent.VK_LEFT:
				this.guiDrive.getLinks().bildSchalten();
				break;
			case KeyEvent.VK_RIGHT:
				this.guiDrive.getRechts().bildSchalten();
				break;
			case KeyEvent.VK_H:
				this.guiDrive.getHupe().bildSchalten();  //Hupe aus
				break;
			case KeyEvent.VK_F:
//				this.guiDrive.getFernlichtButton().bildSchalten();  //Fernlicht aus
				break;
			case KeyEvent.VK_A:
//				this.guiDrive.getAbblendlichtButton().bildSchalten();  //Abblendlicht aus
				break;
		}
	}

	
}
