import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class test extends JFrame {
	public test() {
		getContentPane().setLayout(null);

		JPanel pnl = makePnl();
		getContentPane().add(pnl);
		showGUI();
	}

	private void showGUI() {
		setSize(816, 639);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public JPanel makePnl() {
		JPanel pnl = new JPanel();
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == 39) {
					System.out.println(arg0.getKeyCode());
					pnl.setBounds(pnl.getX() + 20, 0, 20, 20);
				} else if (arg0.getKeyCode() == 37) {
					System.out.println(arg0.getKeyCode());
					pnl.setBounds(pnl.getX() - 20, 0, 20, 20);
				} else if (arg0.getKeyCode() == 38) {
					System.out.println(arg0.getKeyCode());
					pnl.setBounds(0, pnl.getY() - 20, 20, 20);
				}  else if (arg0.getKeyCode() == 40) {
					System.out.println(arg0.getKeyCode());
					pnl.setBounds(0, pnl.getY() + 20, 20, 20);
				}
			}
		});
		pnl.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnl.setLocation(0, 0);
		pnl.setSize(20, 20);
		pnl.setLayout(null);
		pnl.setBackground(Color.WHITE);
		int x = 0;
		int y = 0;

		return pnl;
	}

	public static void main(String[] args) {
		new test();
	}
}