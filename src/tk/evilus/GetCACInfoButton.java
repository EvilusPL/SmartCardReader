package tk.evilus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GetCACInfoButton extends JButton implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6136092249994765740L;
	private JTextArea certInfo;
	private JTextField scPIN;
	
	public GetCACInfoButton(String label, JTextArea certInfo, JTextField scPIN) {
		super(label);
		this.certInfo = certInfo;
		this.scPIN = scPIN;
		this.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		SmartCardReader scReader = new SmartCardReader();
		KeyStore cac = scReader.InitializeCAC(scPIN.getText().toCharArray());
		certInfo.setText("");
		certInfo.append(String.format("cac = %s\n", cac));
		if (cac != null) {
			List<X509Certificate[]> chain;
			try {
				chain = scReader.ShowInfoAboutCAC(cac);
				certInfo.append(String.format("chain.size() = %d\n", chain.size()));
				for (int j=0; j<chain.size(); j++) {
					certInfo.append(String.format("Chain no. %d\n", j));
					for (int i=0; i<chain.get(j).length; i++) {
						certInfo.append(String.format("chain.get(%d).length = %d\n", j, chain.get(j).length));
						certInfo.append(String.format("Serial number: %s\n", chain.get(j)[i].getSerialNumber()));
						certInfo.append(String.format("\n"));
						certInfo.append(String.format("Signing algorithm name: %s\n", chain.get(j)[i].getSigAlgName()));
						certInfo.append(String.format("Critical extension OIDs: %s\n", chain.get(j)[i].getCriticalExtensionOIDs()));
						certInfo.append(String.format("Non-critical extension OIDs: %s\n", chain.get(j)[i].getNonCriticalExtensionOIDs()));
						certInfo.append(String.format("Extended key usage: %s\n", chain.get(j)[i].getExtendedKeyUsage()));
						certInfo.append(String.format("Valid from: %s\n", chain.get(j)[i].getNotBefore()));
						certInfo.append(String.format("Valid to: %s\n", chain.get(j)[i].getNotAfter()));
						certInfo.append(String.format("Subject DN: %s\n", chain.get(j)[i].getSubjectDN()));
						certInfo.append(String.format("Issuer DN: %s\n", chain.get(j)[i].getIssuerDN()));
						certInfo.append(String.format("Public key: %s\n", chain.get(j)[i].getPublicKey()));
						
						FileOutputStream stream = new FileOutputStream("chain"+j+"_cert"+i+".cer");
						stream.write(chain.get(j)[i].getEncoded());
						stream.close();
					}
				}
			} catch (KeyStoreException e1) {
				JOptionPane.showMessageDialog(null, "Error while trying to access key store!", "Error", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			} catch (CertificateException e1) {
				JOptionPane.showMessageDialog(null, "Error while trying to access certificate!", "Error", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(null, "Error while trying to access certificate file!", "Error", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Error while trying to save certificate file!", "Error", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
			
		} else {
			JOptionPane.showMessageDialog(null, "Invalid PIN! Type again", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
