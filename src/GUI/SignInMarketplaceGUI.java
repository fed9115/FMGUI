package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import javafx.util.Pair;

public class SignInMarketplaceGUI {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new SignInMarketplaceGUI());
	}

	/**
	 * Lays out the GUI
	 */
	public SignInMarketplaceGUI() {
		JFrame frame = new JFrame();
		frame.setTitle("Create credential file and endpoint file");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 220);
		frame.setContentPane(makeContents(frame));
		frame.setVisible(true);
	}

	/**
	 * Creates and returns a collection of components to display in the GUI
	 */
	public JPanel makeContents(JFrame frame) {
		// Create components
		JLabel accountLabel = new JLabel("account name: ");
		JLabel emailLabel = new JLabel("email address: ");
		JLabel passwordLabel = new JLabel("password: ");
		JTextField accountField = new JTextField(10);
		accountField.setText("my-account-name");
		JTextField emailField = new JTextField(10);
		emailField.setText("my.email@address");
		JPasswordField passwordField = new JPasswordField(10);
		JLabel marketplaceIDLabel = new JLabel("marketplace ID: ");
		JTextField marketplaceIDField = new JTextField(10);
		marketplaceIDField.setText("<your-marketplace-id>");
		JButton submit = new JButton("submit");
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Pair<String, String> paths = null;
				try {
					paths = createFiles(accountField.getText(), emailField.getText(), passwordField.getPassword(),
							marketplaceIDField.getText());
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(new JFrame(), e1.getMessage());
				}

				frame.setVisible(false);
				new RobotTypeSelectorGUI(paths);
			}
		});

		// Create a panel to hold the five components
		JPanel root = new JPanel();

		/**
		 * Try arranging the components from top to bottom. This is a job for a
		 * BoxLayout.
		 */
		BoxLayout bl = new BoxLayout(root, BoxLayout.PAGE_AXIS);

		// Add the components to the panel
		root.setLayout(bl);
		root.add(accountLabel);
		root.add(accountField);
		root.add(emailLabel);
		root.add(emailField);
		root.add(passwordLabel);
		root.add(passwordField);
		root.add(marketplaceIDLabel);
		root.add(marketplaceIDField);
		root.add(submit);

		// Return the panel
		return root;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Pair<String, String> createFiles(String account, String email, char[] password, String marketplaceID) throws IOException {
		if (account.length() == 0 || email.length() == 0 || password.length == 0 || marketplaceID.length() == 0) {
			JOptionPane.showMessageDialog(new JFrame(), "Entries cannot be empty");
		}

		File saveFolder = new File(System.getProperty("user.dir") + "/.fm");
		saveFolder.mkdirs();

		File credentialFile = new File(saveFolder.getPath() + "/credential." + account);
		FileWriter credential = new FileWriter(credentialFile);
		credential.write("account=" + account + "\nemail=" + email + "\npassword=" + String.copyValueOf(password));
		credential.close();
		
		File endpointFile = new File(saveFolder.getPath() + "/endpoint." + marketplaceID);
		FileWriter endpoint = new FileWriter(endpointFile);
		endpoint.write("endpoint=https://guarded-ridge-89710.herokuapp.com/api/marketplaces/" + marketplaceID);
		endpoint.close();
		return new Pair(credentialFile.getAbsolutePath(), endpointFile.getAbsolutePath());
	}
}
