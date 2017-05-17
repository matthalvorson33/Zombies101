import javax.swing.JFrame;

public class main extends JFrame {
	GamePanel gp = new GamePanel();

	main() {
		add(gp);
		setSize(1024, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	public static void main(String[] args) {
		main rz = new main();
	}// end main
}// end frame class
