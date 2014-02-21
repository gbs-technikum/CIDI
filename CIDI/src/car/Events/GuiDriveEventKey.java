package car.Events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import car.Gui.GuiJFrameMain;
import car.Gui.GuiJPLogin;

public class GuiDriveEventKey implements KeyListener {

	private GuiJFrameMain guiMain;
	private JPanel jpExtern;
	private GuiJPLogin gjl;
	
	
	public GuiDriveEventKey(GuiJFrameMain guiMain) {
		this.guiMain = guiMain;
	}

	public GuiDriveEventKey(GuiJPLogin gjl, GuiJFrameMain guiMain) {
		this.gjl = gjl;
		this.guiMain = guiMain;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_ENTER:
			gjl.getJpassword().requestFocus();
			break;
		default:
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}

