import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;

public class AnimalMouseListener implements MouseListener {
	private JLabel lblAni;
	private Animal ani;

	private JLabel lblAniInfo = new JLabel();
	private JLabel lbl = new JLabel("동물이름", SwingConstants.CENTER);
	private String itemPath = "resource/textbox.png";

	private ImageIcon icon = new ImageIcon(itemPath);
	private Image image = icon.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH);
	private ImageIcon resizedIcon = new ImageIcon(image);
	private JLabel lblGrade = new JLabel("우정등급", SwingConstants.CENTER);

	public AnimalMouseListener(JLabel lblAni, Animal ani) {
		this.lblAni = lblAni;
		this.ani = ani;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
		lblAniInfo.setVisible(false);
	}

	@Override
	public void mouseEntered(MouseEvent e) { 
		lblAniInfo.setIcon(resizedIcon);
		lblAniInfo.setSize(150, 100);
		lblAniInfo.setLocation(e.getX() + lblAni.getX(), e.getY() + lblAni.getY());
		lblAniInfo.setLayout(null);
		lblAniInfo.add(lbl);
		lbl.setBounds(0, 10, 150, 50);

		lbl.setText(ani.getName() + "(" + ani.getAge() + "일)");
		TestFrame.barnPnl.add(lblAniInfo, JLayeredPane.DRAG_LAYER);
		lblGrade.setBounds(0, 40, 150, 50);
		lblGrade.setIcon(new ImageIcon("resource/heart.png"));
		lblGrade.setText(String.valueOf(ani.getFriendshipGrade()));
		lblAniInfo.add(lblGrade);
		lblAniInfo.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("x" + e.getX());
		System.out.println("y" + e.getY());
	}
}
