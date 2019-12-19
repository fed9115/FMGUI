package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javafx.util.Pair;

public class RobotTypeSelectorGUI {
	/**
	 * Lays out the GUI
	 */
	public RobotTypeSelectorGUI(Pair<String, String> paths) {
		JFrame frame = new JFrame();
		frame.setTitle("Robot Launcher");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 220);
		frame.setContentPane(makeContents(paths, frame));
		frame.setVisible(true);
	}

	/**
	 * Creates and returns a collection of components to display in the GUI
	 */
	public JPanel makeContents(Pair<String, String> paths, JFrame frame) {
		// Create components
		JLabel makerTakerLabel = new JLabel("Select maker or taker: ");
		String[] makerTakerStrs = { "maker", "taker" };
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox makerTakerChoice = new JComboBox(makerTakerStrs);
		JLabel robotModelLabel = new JLabel("Select the robot: ");
		String[] robotModelStrs = { "simple", "MVO", "OLS", "SLM" };
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox robotModelChoice = new JComboBox(robotModelStrs);
		JButton submit = new JButton("submit");
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				Pair<String, String> type = new Pair(String.valueOf(makerTakerChoice.getSelectedItem()),
						String.valueOf(robotModelChoice.getSelectedItem()));
				frame.setVisible(false);
				new RobotRunGUI(paths, type);
			}
		});

		JPanel root = new JPanel();
		/**
		 * Try arranging the components from top to bottom. This is a job for a
		 * BoxLayout.
		 */
		BoxLayout bl = new BoxLayout(root, BoxLayout.PAGE_AXIS);

		// Add the components to the panel
		root.setLayout(bl);

		root.add(makerTakerLabel);
		root.add(makerTakerChoice);
		root.add(robotModelLabel);
		root.add(robotModelChoice);
		root.add(submit);

		return root;
	}
}