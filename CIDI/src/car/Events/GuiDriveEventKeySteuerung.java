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
				System.out.println("Vorwärts schalten k");
				break;
			case KeyEvent.VK_DOWN:
				this.guiDrive.getRueckwaerts().bildSchaltenAn();
				System.out.println("Rückwärts schalten k");
				break;
			case KeyEvent.VK_LEFT:
				this.guiDrive.getLinks().bildSchaltenAn();
				System.out.println("Links schalten k");
				break;
			case KeyEvent.VK_RIGHT:
				this.guiDrive.getRechts().bildSchaltenAn();
				System.out.println("Rechts schalten k");
				break;
			case KeyEvent.VK_F:
				this.guiDrive.getFernlichtButton().pinTogglen();  //Fernlicht an/aus
				break;
			case KeyEvent.VK_A:
				this.guiDrive.getAbblendlichtButton().pinTogglen();  //Abblendlicht an/aus
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
		/*	case KeyEvent.VK_F:
				this.guiDrive.getFernlichtButton().bildToggln();  //Fernlicht aus
				break;
			case KeyEvent.VK_A:
				this.guiDrive.getAbblendlichtButton().bildToggln();  //Abblendlicht aus
				break;*/
		}
	}

	
}
