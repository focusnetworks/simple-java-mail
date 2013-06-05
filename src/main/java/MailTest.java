import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.codemonkey.simplejavamail.Email;
import org.codemonkey.simplejavamail.Mailer;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * Demonstration program for the Simple Java Mail framework.
 * <p>
 * <b>IMPORTANT</b>: <br />
 * This testclass was designed to run from the commandline (or by Ant) and expects some system properties to be present. See
 * <b>Readme.txt</b> for instructions. Alternatively, you can assign the host, username and password a hard value and ignore the system
 * properties altogether.
 * 
 * @author Benny Bottema
 */
public class MailTest {

	/**
	 * Just run as Java application, but remember to set the JVM arguments first.
	 * 
	 * @param args should be empty, this demo uses JVM arguments only (-Dhost=value etc.).
	 * @throws GeneralSecurityException 
	 */
	public static void main(final String[] args) throws GeneralSecurityException {
		final Email email = new Email();
		email.setFromAddress("YazigiTravel", "noreply@yazigitravel.com.br");
		email.addRecipient("Jonatas", "jonatas.zanin@focusnetworks.com.br", RecipientType.TO);
		email.setText("We should meet up!");
		email.setTextHTML("<b>We should meet up!</b>");
		email.setSubject("hey");
		sendMail(email);
	}

	private static void sendMail(final Email email) throws GeneralSecurityException {
		final String username = "noreply@yazigitravel.com.br";
	    final String password = "sjk3021#";

	    Properties props = new Properties();
	    props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.yazigitravel.com.br");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "false"); 
        props.put("mail.transport.protocol","smtp");
        
        MailSSLSocketFactory sf=null;
        try
        {
             sf = new MailSSLSocketFactory();
        }
        catch (GeneralSecurityException e1)
        {
             e1.printStackTrace();
        }
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "false");
        props.put("mail.smtp.ssl.socketFactory", sf);

		
	    Session session = Session.getInstance(props,
	            new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(username, password);
	                }
	            });
//		Session session = Session.getInstance(props);
		Mailer mailer = new Mailer(session);
//		final String host = System.getProperty("host") != null ? System.getProperty("host") : "";
//		final int port = System.getProperty("port") != null ? Integer.parseInt(System.getProperty("port")) : 25;
//		final String username = System.getProperty("username") != null ? System.getProperty("username") : "";
//		final String password = System.getProperty("password") != null ? System.getProperty("password") : "";
		mailer.sendMail(email);
	}
}