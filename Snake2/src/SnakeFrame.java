import javax.swing.JFrame;

public class SnakeFrame extends JFrame {
	
	public SnakeFrame(SnakeViewer sv) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Snake");
		add(sv);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
}
