package car.Events;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import car.Gui.GuiJPDrive;
import car.Hilfsklassen.Controller;

public class GuiDriveEventKeySteuerung extends KeyAdapter{

	private GuiJPDrive guiDrive;
	private Controller c;
	private boolean c_VK_UP,c_VK_DOWN,c_VK_LEFT,c_VK_RIGHT,c_VK_F,c_VK_A;
	
	public GuiDriveEventKeySteuerung(GuiJPDrive guiD)  {
		this.guiDrive = guiD;
		c = Controller.getInstance();
		c_VK_UP=c_VK_DOWN=c_VK_LEFT=c_VK_RIGHT=c_VK_F=c_VK_A=false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				c_VK_UP=true;
				this.guiDrive.getVorwarets().bildSchaltenAn(); //Vorw�rtsknopschalten
				System.out.println("Vorwärts schalten k");
				break;
			case KeyEvent.VK_DOWN:
				c_VK_DOWN=true;
				this.guiDrive.getRueckwaerts().bildSchaltenAn();
				System.out.println("Rückwärts schalten k");
				break;
			case KeyEvent.VK_LEFT:
				c_VK_LEFT=true;
				this.guiDrive.getLinks().bildSchaltenAn();
				System.out.println("Links schalten k");
				break;
			case KeyEvent.VK_RIGHT:
				c_VK_RIGHT=true;
				this.guiDrive.getRechts().bildSchaltenAn();
				System.out.println("Rechts schalten k");
				break;
			case KeyEvent.VK_F:
				if(c_VK_F==true) {
					c_VK_F=false;
				} else
					c_VK_F=true;
				this.guiDrive.getFernlichtButton().pinTogglen();  //Fernlicht an/aus
				break;
			case KeyEvent.VK_A:
				if(c_VK_A==true) {
					c_VK_A=false;
				}else
					c_VK_A=true;
				this.guiDrive.getAbblendlichtButton().pinTogglen();  //Abblendlicht an/aus
				break;
		}
		c.drive(c_VK_LEFT, c_VK_RIGHT, c_VK_UP, c_VK_DOWN, c_VK_F, c_VK_A);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				c_VK_UP=false;
				this.guiDrive.getVorwarets().bildSchaltenAus(); //Vorw�rtsknop schalten
				break;
			case KeyEvent.VK_DOWN:
				c_VK_DOWN=false;
				this.guiDrive.getRueckwaerts().bildSchaltenAus();
				break;
			case KeyEvent.VK_LEFT:
				c_VK_LEFT=false;
				this.guiDrive.getLinks().bildSchaltenAus();
				break;
			case KeyEvent.VK_RIGHT:
				c_VK_RIGHT=false;
				this.guiDrive.getRechts().bildSchaltenAus();
				break;
		}
		c.drive(c_VK_LEFT, c_VK_RIGHT, c_VK_UP, c_VK_DOWN, c_VK_F, c_VK_A);
	}
}
