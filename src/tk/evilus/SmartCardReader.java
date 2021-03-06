package tk.evilus;

import java.awt.EventQueue;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Provider;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.smartcardio.ATR;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;

import sun.security.pkcs11.SunPKCS11;

public class SmartCardReader {

	public List<CardTerminal> ListTerminals() {
		try {
			TerminalFactory factory = TerminalFactory.getDefault();
			List<CardTerminal> terminals = factory.terminals().list();
			return terminals;
		} catch (CardException e) {
			return null;
		}
	}
	
	public String GetCardInfo(int TerminalID) {
		CardTerminal terminal = (new SmartCardReader()).ListTerminals().get(TerminalID);
		try {
			Card card = terminal.connect("*");
			String result = card.toString();
			card.disconnect(false);
			return result;
		} catch (CardException e) {
			return null;
		}
	}
	
	public String GetCardATR(int TerminalID) {
		CardTerminal terminal = (new SmartCardReader()).ListTerminals().get(TerminalID);
		try {
			Card card = terminal.connect("*");
			ATR atr = card.getATR();
			byte[] bATR = atr.getBytes();
			StringBuilder result = new StringBuilder();
			for (int i=0; i<bATR.length; i++) {
				result.append(String.format("%02X", bATR[i]));
			}
			card.disconnect(false);
			return result.toString();	
		} catch(CardException e) {
			return null;
		}
	}
	
	public String GetCardChannel(int TerminalID) {
		CardTerminal terminal = (new SmartCardReader()).ListTerminals().get(TerminalID);
		try {
			Card card = terminal.connect("*");
			CardChannel channel = card.getBasicChannel();
			String result = channel.toString();
			card.disconnect(false);
			return result;
		} catch (CardException e) {
			return null;
		}
	}
	
	public String GetCardID(int TerminalID) {
		CardTerminal terminal = (new SmartCardReader()).ListTerminals().get(TerminalID);
		try {
			Card card = terminal.connect("*");	
			CardChannel channel = card.getBasicChannel();
			StringBuilder result = new StringBuilder();
			byte[] cmdGetCardID = new byte[] {
					(byte)0xFF, (byte)0xCA, (byte)0x00, (byte)0x00, (byte)0x00
			};
			
			ResponseAPDU respAPDU = channel.transmit(new CommandAPDU(cmdGetCardID));
			
			if (respAPDU.getSW1() == 0x90 && respAPDU.getSW2() == 0x00) {
				byte[] bCardUID = respAPDU.getData();
				
				for (int i=0; i<bCardUID.length; i++) {
					result.append(String.format("%02X", bCardUID[i]));
				}
			}
			card.disconnect(false);
			return result.toString();
		} catch (CardException e) {
			return null;
		}
	}
	
	public KeyStore InitializeCAC(char[] pin) {
		try
	       {   
	         String configName = "card.config";
	         Provider p = new SunPKCS11(configName);
	         Security.addProvider(p);
	         
	         KeyStore cac = null;

	         cac = KeyStore.getInstance("PKCS11");
	         cac.load(null, pin);
	         return cac;
	      }
	      catch(Exception ex)
	      {
	         return null;
	      }
	}
	
	public List<X509Certificate[]> ShowInfoAboutCAC(KeyStore ks) throws KeyStoreException, CertificateException
	   {
	      Enumeration<String> aliases = ks.aliases();
	      List<X509Certificate[]> chain = new ArrayList<X509Certificate[]>();
	      while (aliases.hasMoreElements()) 
	      {
	         String alias = aliases.nextElement();
	         X509Certificate[] cchain = (X509Certificate[]) ks.getCertificateChain(alias);

	         /*System.out.println("Certificate Chain for : " + alias);
	         for (int i = 0; i < cchain.length; i ++)
	         {
	            System.out.println(i + " SubjectDN: " + cchain[i].getSubjectDN());
	            System.out.println(i + " IssuerDN:  " + cchain[i].getIssuerDN());
	         }*/
	         chain.add(cchain);
	      }
	      
	      return chain;
	   }
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainFrame();
			}
		});
	}
	
}
