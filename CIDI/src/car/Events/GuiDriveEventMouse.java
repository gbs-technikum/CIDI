package car.Events;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import car.Gui.GuiJFrameMain;
import car.Hilfsklassen.CIDIButton;
import car.Hilfsklassen.Controller;

public class GuiDriveEventMouse extends MouseAdapter{

	private CIDIButton cb;
	private GuiJFrameMain guiMain;
//	private Controller c;
	private boolean c_VK_UP,c_VK_DOWN,c_VK_LEFT,c_VK_RIGHT,c_VK_F,c_VK_A;
	
	public GuiDriveEventMouse(CIDIButton cbExtern, GuiJFrameMain guiM){
//		c = Controller.getInstance();
		c_VK_UP=c_VK_DOWN=c_VK_LEFT=c_VK_RIGHT=c_VK_F=c_VK_A=false;
		this.cb = cbExtern;
		this.guiMain = guiM;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		switch(cb.getButton().getActionCommand()) {
		case "Abblendlicht schalten":
			if(c_VK_A==true) {
				c_VK_A=false;
			} else
				c_VK_A=true;
			cb.pinTogglen();
			break;
		case "Fernlicht schalten":
			if(c_VK_F==true) {
				c_VK_F=false;
			} else
				c_VK_F=true;
			cb.pinTogglen();
			break;
		}
//		c.drive(c_VK_LEFT, c_VK_RIGHT, c_VK_UP, c_VK_DOWN, c_VK_F, c_VK_A);
		this.guiMain.requestFocus();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch(cb.getButton().getActionCommand()) {
		case "Vorwaerts":
			c_VK_UP=true;
			cb.bildSchaltenAn();
			break;
		case "Rueckwaerts":
			c_VK_DOWN=true;
			cb.bildSchaltenAn();
			break;
		case "Links":
			c_VK_LEFT=true;
			cb.bildSchaltenAn();
			break;
		case "Rechts":
			c_VK_RIGHT=true;
			cb.bildSchaltenAn();
			break;	
		}
		
//		c.drive(c_VK_LEFT, c_VK_RIGHT, c_VK_UP, c_VK_DOWN, c_VK_F, c_VK_A);
//		this.cb.bildSchaltenAn();
//		this.cb.pinTogglen();
		this.guiMain.requestFocus();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch(cb.getButton().getActionCommand()) {
		case "Vorwaerts":
			c_VK_UP=false;
			cb.bildSchaltenAus();
			break;
		case "Rueckwaerts":
			c_VK_DOWN=false;
			cb.bildSchaltenAus();
			break;
		case "Links":
			c_VK_LEFT=false;
			cb.bildSchaltenAus();
			break;
		case "Rechts":
			c_VK_RIGHT=false;
			cb.bildSchaltenAus();
			break;	
		}
		
//		c.drive(c_VK_LEFT, c_VK_RIGHT, c_VK_UP, c_VK_DOWN, c_VK_F, c_VK_A);
//		this.cb.bildSchaltenAus();
//		this.cb.pinTogglen();
		this.guiMain.requestFocus();
	}
}
