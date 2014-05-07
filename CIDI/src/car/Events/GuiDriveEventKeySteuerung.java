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
				this.guiDrive.getVorwarets().bildSchaltenAn(); //Vorwärtsknopschalten
				break;
			case KeyEvent.VK_DOWN:
				this.guiDrive.getRueckwaerts().bildSchaltenAn();
				break;
			case KeyEvent.VK_LEFT:
				this.guiDrive.getLinks().bildSchaltenAn();
				break;
			case KeyEvent.VK_RIGHT:
				this.guiDrive.getRechts().bildSchaltenAn();
				break;
			case KeyEvent.VK_H:
				this.guiDrive.getHupe().bildSchaltenAn();   //Hupe an
				break;
			case KeyEvent.VK_F:
				this.guiDrive.getFernlichtButton().bildToggln();  //Fernlicht an/aus
				break;
			case KeyEvent.VK_A:
				this.guiDrive.getAbblendlichtButton().bildToggln();  //Abblendlicht an/aus
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				this.guiDrive.getVorwarets().bildSchaltenAus(); //Vorwärtsknop schalten
				break;
			case KeyEvent.VK_DOWN:
				this.guiDrive.getRueckwaerts().bildSchaltenAus();
				break;
			case KeyEvent.VK_LEFT:
				this.guiDrive.getLinks().bildSchaltenAus();
				break;
			case KeyEvent.VK_RIGHT:
				this.guiDrive.getRechts().bildSchaltenAus();
				break;
			case KeyEvent.VK_H:
				this.guiDrive.getHupe().bildSchaltenAus();  //Hupe aus
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
