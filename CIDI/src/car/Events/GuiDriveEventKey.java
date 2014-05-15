package car.Events;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import car.Gui.GuiJPLogin;

public class GuiDriveEventKey extends KeyAdapter {

	private GuiJPLogin gjl;
	
	public GuiDriveEventKey(GuiJPLogin gjl) {
		this.gjl = gjl;
	}

	public GuiDriveEventKey() {
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_ENTER:
			sprungFocus();
			break;
		case KeyEvent.VK_TAB:
			sprungFocus();
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

}

