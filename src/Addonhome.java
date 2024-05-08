import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class Addonhome {
	private static boolean show = false;
	static JPanel helpPnl = new JPanel();
	protected static JLabel bgLbl;
	

	public static JLayeredPane homeFurniturePanel() {
		JLayeredPane homePnl = new JLayeredPane();
		homePnl.setBounds(0, 0, 800, 600);
		homePnl.setLayout(null);
		homePnl.setVisible(true);

		bgLbl = new JLabel();
		bgLbl.setSize(800, 600);
		bgLbl.setLocation(0, 0);
		bgLbl.setIcon(new ImageIcon("resource/집_바닥.png"));
		
		JLabel question = new JLabel();
		question.setSize(45, 45);
		question.setLocation(35, 520);
		question.setIcon(new ImageIcon("resource/question.png"));
		question.setHorizontalAlignment(SwingConstants.CENTER);

		JButton questionButton = new JButton();
		questionButton.setBounds(0, 0, 45, 45);
		questionButton.setBorder(null);
		questionButton.setBorderPainted(false);
		questionButton.setContentAreaFilled(false);
		questionButton.setFocusPainted(false);
		questionButton.setIcon(new ImageIcon("resource/question.png"));
		questionButton.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				questionButton.setIcon(new ImageIcon("resource/question_hover.png"));
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				questionButton.setIcon(new ImageIcon("resource/question.png"));
			}
		});
		questionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				show = !show;
				if (show) {
					helpPnl.setBounds(0, 0, 800, 600);
					homePnl.add(helpPnl, JLayeredPane.DRAG_LAYER);
					helpPnl.setVisible(true);
				} else {
					helpPnl.setVisible(false);
					homePnl.remove(helpPnl);
				}
				homePnl.revalidate();
				homePnl.repaint();
				TestFrame.frame.requestFocus();

			}
		});
		question.add(questionButton);

		JLabel help = new JLabel();
		help.setForeground(Color.RED);
		help.setSize(800, 600);
		help.setIcon(new ImageIcon("resource/집_help.png"));
		help.setOpaque(false);
		helpPnl.setOpaque(false);
		helpPnl.add(help);

		homePnl.add(bgLbl, JLayeredPane.DEFAULT_LAYER);
		homePnl.add(question, JLayeredPane.PALETTE_LAYER);

		return homePnl;
	}
}
