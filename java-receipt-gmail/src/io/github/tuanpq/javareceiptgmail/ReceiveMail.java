package io.github.tuanpq.javareceiptgmail;

import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;

import javax.mail.Session;
import javax.mail.Store;

public class ReceiveMail {

	public static void main(String[] args) throws Exception {
		// For Gmail, you must generate application password for using this application.
		readGmail("yourmail@gmail.com", "your gmail app password"); // 16-digit app password on Gmail's Account Settings
	}

	public static void readGmail(String userName, String password) {
		String receivingHost = "imap.gmail.com";
		Properties properties = System.getProperties();
		properties.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getDefaultInstance(properties, null);

		try {
			Store store = session.getStore("imaps");
			store.connect(receivingHost, userName, password);
			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);
			Message message[] = folder.getMessages();

			for (int i = 0; i < message.length; i++) {
				System.out.println(message[i].getSubject());
			}

			folder.close(true);
			store.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}