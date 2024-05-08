import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

class ClockPanel extends JPanel {
	public static int angle = 0;

	public ClockPanel() {
		super();
		setSize(160, 120);
		setLayout(null);
		setBackground(new Color(0, 0, 0, 0));
//		JLabel clock = new JLabel();
//		clock.setBounds(0, 0, 160, 120);
//		clock.setIcon(new ImageIcon("resource/clock.png"));
//		add(clock);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // 부모의 paintComponent 호출
		ImageIcon imgIconClock = new ImageIcon("resource/clock.png");
		Image imgClock = imgIconClock.getImage();
		Graphics2D g2dClock = (Graphics2D) g.create();
		g2dClock.drawImage(imgClock, 0, 0, this);

		ImageIcon imgIconClockHand = new ImageIcon("resource/clockHand.png");
		Image imgClockHand = imgIconClockHand.getImage();
		Graphics2D g2dHand = (Graphics2D) g.create();
		g2dHand.rotate(Math.toRadians(angle), 46, 40);
		g2dHand.drawImage(imgClockHand, 0, -8, this);

		String dayStr = TestFrame.day + " 일";
		g.setFont(new Font(null, Font.BOLD, 15));
		g.drawString(dayStr, 91, 23);

		// 시간 연동 필요함
		boolean isPM = (6 + TestFrame.time / 150) >= 13 && (6 + TestFrame.time / 150) <= 23;
		int hour = (6 + TestFrame.time / 150) % 12; // 6 ~ 다음날 새벽 2시까지
		int min = TestFrame.time / 25 % 6; // 10분단위
		String hourStr = "";
		String minStr = "";
		String tail = "";
		if (hour / 10 == 0) {
			hourStr = "0" + hour;
		} else {
			hourStr = "" + hour;
		}
		if (min == 0) {
			minStr = "00";
		} else {
			minStr = min + "0";
		}
		if (isPM) {
			tail = " PM";
		} else {
			tail = " AM";
		}
		String timeStr = hourStr + ":" + minStr + tail;
		g.setFont(new Font(null, Font.BOLD, 15));
		g.drawString(timeStr, 74, 70);

		String moneyStr = Integer.toString(TestFrame.money);
		while (moneyStr.length() < 8) {
			moneyStr = 0 + moneyStr;
		}
		g.setFont(new Font(null, Font.BOLD, 15));
		boolean isZero = true;
		for (int i = 0; i < moneyStr.length(); i++) {
			String c = "" + moneyStr.charAt(i);
			if (!c.equals("0") || (c.equals("0") && !isZero)) {
				isZero = false;
				g.drawString(c, 37 + i * 14, 111);
			}
		}
//		String moneyStr = Integer.toString(TestFrame.money);
//		g.setFont(new Font(null, Font.BOLD, 15));
//		g.drawString(moneyStr, 37, 111);
	}
}

public class Addon {

	public static JPanel makeQuickSlot() {
		JPanel quickSlot = new JPanel();
		quickSlot.setSize(800, 100);
		quickSlot.setLayout(null);

		JLabel bgLbl = new JLabel();
		bgLbl.setBounds(0, 0, 800, 100);

		bgLbl.setBackground(Color.BLACK);
		bgLbl.setOpaque(true);
		bgLbl.setBorder(new LineBorder(Color.BLACK, 5));
//		bgLbl.setIcon("퀵슬롯배경");

		for (int i = 0; i < 10; i++) {
			JLabel slot = new JLabel();
			slot.setBounds(i * 80, 10, 80, 80);

			slot.setBackground(new Color(165, 42, 42));
			slot.setOpaque(true);
			slot.setBorder(new LineBorder(Color.YELLOW, 5));
//			slot.setIcon("빈칸이미지");

			quickSlot.add(slot);
		}

		quickSlot.add(bgLbl);

		return quickSlot;
	}

	public static JPanel makeRightPnl() {
		JPanel rightPnl = new JPanel();
		rightPnl.setSize(200, 221);
		rightPnl.setLayout(null);

		JLabel helpLbl = new JLabel();
		helpLbl.setBounds(0, 0, 200, 221);
		helpLbl.setIcon(new ImageIcon("resource/rightPnl.png"));

		rightPnl.add(helpLbl);

		return rightPnl;
	}

	public static JLayeredPane farmPlantPanel() {
		JLayeredPane plantPnl = new JLayeredPane();
		plantPnl.setSize(800, 600);
		plantPnl.setLayout(null);

		JLabel bgLbl = new JLabel();
		bgLbl.setSize(800, 600);
		bgLbl.setLocation(0, 0);
		bgLbl.setBackground(new Color(0, 0, 0, 0));
		bgLbl.setIcon(new ImageIcon("resource/farm_20_15.png"));

		plantPnl.add(bgLbl, JLayeredPane.DEFAULT_LAYER);

		return plantPnl;
	}

	public static void addPlant() {
		for (Block b : TestFrame.blocks) {
			if (b.getInfo().contains("밭")) {
				TestFrame.character.getFunction().plantSetting(b);
			}
		}
	}

	public static JLayeredPane fishingPanel() {
		JLayeredPane fishingPnl = new JLayeredPane();
		fishingPnl.setSize(800, 600);
		fishingPnl.setLayout(null);
		fishingPnl.setBackground(new Color(0, 0, 0, 0));

		JLabel bgLbl = new JLabel();
		bgLbl.setSize(800, 600);
		bgLbl.setLocation(0, 0);
		bgLbl.setBackground(new Color(0, 0, 0, 0));
		bgLbl.setIcon(new ImageIcon("resource/main_20_15.png"));

		fishingPnl.add(bgLbl, JLayeredPane.DEFAULT_LAYER);

		return fishingPnl;
	}

}