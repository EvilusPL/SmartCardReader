package tk.evilus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GetCardIDButton extends JButton implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3206760074860515815L;
	private SmartCardReader scReader;
	private JLabel terminalInfo;
	private JLabel cardInfo;
	private JLabel atrInfo;
	private JLabel channelInfo;
	private JLabel idInfo;
	private JTextField terminalID;
	
	public GetCardIDButton(String label, SmartCardReader scReader, JLabel terminalInfo, JLabel cardInfo, JLabel atrInfo, JLabel channelInfo, JLabel idInfo, JTextField terminalID) {
		super(label);
		this.scReader = scReader;
		this.terminalInfo = terminalInfo;
		this.cardInfo = cardInfo;
		this.atrInfo = atrInfo;
		this.channelInfo = channelInfo;
		this.idInfo = idInfo;
		this.terminalID = terminalID;
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			int iTerminalID = Integer.parseInt(terminalID.getText());
			terminalInfo.setText("Terminals: "+scReader.ListTerminals());
			if (scReader.GetCardInfo(iTerminalID) != null) {
				cardInfo.setText("Card: "+scReader.GetCardInfo(iTerminalID));
			} else {
				JOptionPane.showMessageDialog(null, "Unable to read the card!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			atrInfo.setText("ATR: 0x"+scReader.GetCardATR(iTerminalID));
			channelInfo.setText("Channel: "+scReader.GetCardChannel(iTerminalID));
			idInfo.setText("ID: "+scReader.GetCardID(iTerminalID));
		} catch (IndexOutOfBoundsException ex) {
			JOptionPane.showMessageDialog(null, "Terminal does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void GetInfo() {
		actionPerformed(null);
	}

}
