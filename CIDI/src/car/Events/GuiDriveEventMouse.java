package car.Events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import car.Gui.GuiJFrameMain;
import car.Hilfsklassen.CIDIButton;

public class GuiDriveEventMouse implements MouseListener{

	private CIDIButton cb;
	private GuiJFrameMain guiMain;
	
	public GuiDriveEventMouse(CIDIButton cbExtern, GuiJFrameMain guiM){
		this.cb = cbExtern;
		this.guiMain = guiM;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.cb.bildSchaltenAn();
//		this.cb.pinTogglen();
		this.guiMain.requestFocus();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.cb.bildSchaltenAus();
//		this.cb.pinTogglen();
		this.guiMain.requestFocus();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
