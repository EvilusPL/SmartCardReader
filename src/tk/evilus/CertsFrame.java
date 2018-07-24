package tk.evilus;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CertsFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1179348035245314471L;
	private JTextArea certInfo = new JTextArea();
	
	private JLabel pinInfo = new JLabel("Smart card PIN: ");
	
	private JTextField scPIN = new JTextField();
	private JScrollPane scScrollCertInfo = new JScrollPane(certInfo);
	
	private GetCACInfoButton scGetCACInfo = new GetCACInfoButton("Get certs", certInfo, scPIN);
	
	public CertsFrame() {
		super("Certificates on Smart Card");
		setVisible(true);
		
		JPanel scLabelPane = new JPanel();
		JPanel scButtonPane = new JPanel();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(640,480);
		setLocation(50,50);
		
		certInfo.setEditable(false);
		certInfo.setLineWrap(true);
		
		scLabelPane.setLayout(new BoxLayout(scLabelPane, BoxLayout.PAGE_AXIS));
		scButtonPane.setLayout(new BoxLayout(scButtonPane, BoxLayout.LINE_AXIS));
		
		Container container = getContentPane();
		container.add(scLabelPane, BorderLayout.CENTER);
		container.add(scButtonPane, BorderLayout.PAGE_END);
		
		scLabelPane.add(scScrollCertInfo);
		
		scButtonPane.add(pinInfo);
		scButtonPane.add(scPIN);
		scButtonPane.add(scGetCACInfo);
		
	}
}
