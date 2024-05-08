import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class gui_window extends JFrame {

	public gui_window() {
		
		JPanel pnl = makePnl();
		add(pnl);
		showGUI();
	}
	
	private void showGUI() {
		setSize(816, 639);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public JPanel makePnl() {
		JPanel pnl = new JPanel();
		pnl.setSize(800,600);
		pnl.setLayout(null);
		int x = 0;
		int y = 0;
		List<JLabel> lblList = new ArrayList<>();
		while(lblList.size() < 300) {
			JLabel lbl = new JLabel();
			lbl.setSize(40, 40);
			lbl.setLocation(x, y);
			lbl.setBackground(new Color(0,0,0));
			lbl.setBorder(new LineBorder(Color.BLACK, 1));
			lblList.add(lbl);
			pnl.add(lbl);
			if(x == 760) {
				x = 0;
				y += 40;
			} else {
				x += 40;
			}
			System.out.println(lblList.size());
		}
		return pnl;
	}
	
	public static void main(String[] args) {
		new gui_window();
	}
}