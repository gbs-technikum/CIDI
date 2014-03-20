package car.Hilfsklassen;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CIDIButton {

	private JButton jbButton;
	private ImageIcon[] iiBilder;
	private Boolean stauts;
	private Boolean pinStatus;

	public CIDIButton(ImageIcon[] buttonBilder, String actionCommand){
		this.iiBilder = buttonBilder;
		this.jbButton = new JButton(buttonBilder[0]);
		this.jbButton.setActionCommand(actionCommand);
		this.stauts = false;
		this.pinStatus=false;
	}
	
	public void bildSchalten(){
		if(this.stauts){
			this.jbButton.setIcon(this.iiBilder[0]);
			this.stauts = false;
		} else if(!(this.stauts)){
			this.jbButton.setIcon(this.iiBilder[1]);
			this.stauts = true;			
		}
	}
	
	public void pinTogglen(){
		if(!this.pinStatus){
			System.out.println("An " + jbButton.getActionCommand());
			this.pinStatus = true;
		} else if (this.pinStatus){
			System.out.println("Aus " + jbButton.getActionCommand());
			this.pinStatus = false;
		}
	}
	
	public JButton getButton(){
		return this.jbButton;
	}
	
}
