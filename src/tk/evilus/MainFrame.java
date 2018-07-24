package tk.evilus;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2393251754087154721L;
	
	private SmartCardReader scReader = new SmartCardReader();
	
	private JLabel terminalInfo = new JLabel("Terminals: None");
	private JLabel cardInfo = new JLabel("Card: None");
	private JLabel atrInfo = new JLabel("ATR: None");
	private JLabel channelInfo = new JLabel("Channel: None");
	private JLabel idInfo = new JLabel("ID: None");
	
	private JLabel terminalIDInfo = new JLabel("Terminal ID: ");
	private JTextField terminalID = new JTextField();
	
	private GetCardIDButton scGetCardID = new GetCardIDButton("Get Card ID", scReader, terminalInfo, cardInfo, atrInfo, channelInfo, idInfo, terminalID);
	private ShowCertsFrameButton scShowCerts = new ShowCertsFrameButton("Show certs");

	public MainFrame() {
		super("Smart Card Reader");
		setVisible(true);
		
		JPanel scLabelPane = new JPanel();
		JPanel scButtonPane = new JPanel();
		terminalID.setText("0");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640,480);
		setLocation(50,50);
		
		
		scLabelPane.setLayout(new BoxLayout(scLabelPane, BoxLayout.PAGE_AXIS));
		scButtonPane.setLayout(new BoxLayout(scButtonPane, BoxLayout.LINE_AXIS));
		
		Container container = getContentPane();
		container.add(scLabelPane, BorderLayout.CENTER);
		container.add(scButtonPane, BorderLayout.PAGE_END);
		
		scLabelPane.add(terminalInfo);
		scLabelPane.add(cardInfo);
		scLabelPane.add(atrInfo);
		scLabelPane.add(channelInfo);
		scLabelPane.add(idInfo);
		
		scButtonPane.add(terminalIDInfo);
		scButtonPane.add(terminalID);
		scButtonPane.add(scGetCardID);
		scButtonPane.add(scShowCerts);
		
		scGetCardID.GetInfo();
	}

}
