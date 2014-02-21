package car.Events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GuiDriveEventMouse implements MouseListener{

	private GuiDrive gd;
	
	public GuiDriveEventMouse(GuiDrive gdextern){
		this.gd = gdextern;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
//		this.gd.hupenAn();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		this.gd.hupenAn();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.gd.hupenAus();
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
