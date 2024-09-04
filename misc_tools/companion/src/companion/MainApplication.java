package companion;

import javax.swing.JFrame;

public class MainApplication {

	public static void main(String[] args) {

		CompanionUI companionUI = new CompanionUI();

		// Create a new JFrame
		JFrame jFrame = new JFrame("Aquarius Companion");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(800, 600);
		jFrame.setVisible(true);

		companionUI.generateUI(jFrame);
	}
}
