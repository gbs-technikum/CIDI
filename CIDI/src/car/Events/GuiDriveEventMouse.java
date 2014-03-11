package car.Events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import car.Elemente.CIDIButton;

public class GuiDriveEventMouse implements MouseListener{

	private CIDIButton cb;
	private String actionCommand;
	
	public GuiDriveEventMouse(CIDIButton cbExtern){
		this.cb = cbExtern;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.cb.bildSchalten();
		this.cb.pinTogglen();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.cb.bildSchalten();
		this.cb.pinTogglen();
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
