package car.Hilfsklassen;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CIDIButton {

	private JButton jbButton;
	private ImageIcon[] iiBilder;
	private Boolean pinStatus;

	public CIDIButton(ImageIcon[] buttonBilder, String actionCommand){
		this.iiBilder = buttonBilder;
		this.jbButton = new JButton(buttonBilder[0]);
		this.jbButton.setActionCommand(actionCommand);
		this.pinStatus=false;
	}
	
	public void bildSchaltenAn(){
		this.jbButton.setIcon(this.iiBilder[1]);
	}
	
	public void bildSchaltenAus(){
		this.jbButton.setIcon(this.iiBilder[0]);
	}
	
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
