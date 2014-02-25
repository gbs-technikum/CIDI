package car.Events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import car.Gui.GuiJPDrive;
import car.Gui.GuiJPLogin;

public class GuiDriveEventKey implements KeyListener {

	private GuiJPLogin gjl;
//	private GuiJPDrive gjd;
	
	public GuiDriveEventKey(GuiJPLogin gjl) {
		this.gjl = gjl;
	}

	public GuiDriveEventKey() {
//		this.gjd = gjd;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		System.out.println(keyCode);
		switch (keyCode) {
		case KeyEvent.VK_ENTER:
			sprungFocus();
			break;
		case KeyEvent.VK_TAB:
			sprungFocus();
			break;
		case KeyEvent.VK_UP:
			System.out.println("->Vorwärts");
			break;
		case KeyEvent.VK_DOWN:
			System.out.println("->Rückwärts");
			break;
		case KeyEvent.VK_LEFT:
			System.out.println("-> Links");
			break;
		case KeyEvent.VK_RIGHT:
			System.out.println("-> Rechts");
			break;
		default:
			break;
		}
		
	}

	public void sprungFocus() {
		if(gjl.getJpassword().isFocusOwner()){
			gjl.getJbanmelden().requestFocus();
		} else if(gjl.getJUserTextField().isFocusOwner()) {
			gjl.getJpassword().requestFocus();
		} 
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}

