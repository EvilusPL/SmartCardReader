package tk.evilus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ShowCertsFrameButton extends JButton implements ActionListener {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1656988679264648477L;

	public ShowCertsFrameButton(String label) {
		super(label);
		this.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame certsFrame = new CertsFrame();
		certsFrame.setVisible(true);
	}
}
