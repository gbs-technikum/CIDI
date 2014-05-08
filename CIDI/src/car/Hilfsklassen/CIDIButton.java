package car.Hilfsklassen;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CIDIButton {

	private JButton jbButton;
	private ImageIcon[] iiBilder;
	private Boolean status;
	private Boolean pinStatus;

	public CIDIButton(ImageIcon[] buttonBilder, String actionCommand){
		this.iiBilder = buttonBilder;
		this.jbButton = new JButton(buttonBilder[0]);
		this.jbButton.setActionCommand(actionCommand);
		this.status = false;
		this.pinStatus=false;
	}
	
	public void bildSchaltenAn(){
		this.jbButton.setIcon(this.iiBilder[1]);
	}
	
	public void bildSchaltenAus(){
		this.jbButton.setIcon(this.iiBilder[0]);
	}
	
//	public void bildToggln(){
//		if(this.status == false){
//			this.jbButton.setIcon(this.iiBilder[0]);
//			this.status = true;
//		}
//		
//		if(this.status == true){
//			this.jbButton.setIcon(this.iiBilder[1]);
//			this.status = false;
//		}
//	}
	
	public void pinTogglen(){
		if(!this.pinStatus){
			System.out.println("An " + jbButton.getActionCommand());
			this.jbButton.setIcon(this.iiBilder[1]);
			this.pinStatus = true;
		} else if (this.pinStatus){
			System.out.println("Aus " + jbButton.getActionCommand());
			this.jbButton.setIcon(this.iiBilder[0]);
			this.pinStatus = false;
		}
	}
	
	public JButton getButton(){
		return this.jbButton;
	}
	
}
