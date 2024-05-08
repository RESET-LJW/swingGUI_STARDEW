import java.awt.Color;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Background {

	private BlocksInfo bInfo;
	
	public Background() {
		super();
		this.bInfo = new BlocksInfo();
	}

	public JPanel mainBackground() {
		JPanel pnl = new JPanel();
		pnl.setSize(800, 600);
		pnl.setLayout(null);

		JLabel lblbg = new JLabel();
		lblbg.setSize(800, 600);
		lblbg.setLocation(0, 0);

		lblbg.setBackground(new Color(255, 255, 255));
		lblbg.setOpaque(true);

		lblbg.setVisible(true);

		JLabel lblhome = new JLabel();
		lblhome.setSize(160, 120);
		lblhome.setLocation(120, 120);

		lblhome.setBackground(new Color(255, 0, 0));
		lblhome.setOpaque(true);

		lblhome.setVisible(true);
		
		JLabel lblfarm = new JLabel();
		lblfarm.setSize(160, 120);
		lblfarm.setLocation(320, 320);

		lblfarm.setBackground(new Color(0, 255, 0));
		lblfarm.setOpaque(true);

		lblfarm.setVisible(true);

		JLabel lblbarn = new JLabel();
		lblbarn.setSize(160, 120);
		lblbarn.setLocation(560, 240);

		lblbarn.setBackground(new Color(0, 0, 255));
		lblbarn.setOpaque(true);

		lblbarn.setVisible(true);

		pnl.add(lblhome);
		pnl.add(lblfarm);
		pnl.add(lblbarn);
		pnl.add(lblbg);

		return pnl;
	}

//	public JLayeredPane homeBackground() {
//		JLayeredPane home = new JLayeredPane();
//		home.setLayout(null);
//		home.setSize(800, 600);
//		
//
//		return home;
//	}

	public JPanel farmBackground() {
		JPanel pnl = new JPanel();
		pnl.setSize(800, 600);
		pnl.setLayout(null);
		ImageIcon farmBG = new ImageIcon("resource/farm_20_16.png");
		ImageIcon fenceWidth = new ImageIcon("resource/fence_width.png");
		ImageIcon fenceHeight = new ImageIcon("resource/fence_Height.png");
		ImageIcon exitWidth = new ImageIcon("resource/exit_farm_width.png");
		ImageIcon exitHeight = new ImageIcon("resource/exit_farm_height.png");
		ImageIcon fieldEmpty = new ImageIcon("resource/field_empty.png");

		JLabel lblBG = new JLabel();
		lblBG.setSize(800, 600);
		lblBG.setLocation(0, 0);

		lblBG.setIcon(farmBG);

		lblBG.setVisible(true);

		JLabel lblFenceWidthUp = new JLabel();
		lblFenceWidthUp.setSize(800, 40);
		lblFenceWidthUp.setLocation(0, 0);

		lblFenceWidthUp.setIcon(fenceWidth);

		lblFenceWidthUp.setVisible(true);

		JLabel lblFenceWidthDown = new JLabel();
		lblFenceWidthDown.setSize(800, 40);
		lblFenceWidthDown.setLocation(-160, 560);

		lblFenceWidthDown.setIcon(fenceWidth);

		lblFenceWidthDown.setVisible(true);

		JLabel lblFenceHeightLeft = new JLabel();
		lblFenceHeightLeft.setSize(40, 600);
		lblFenceHeightLeft.setLocation(0, 0);

		lblFenceHeightLeft.setIcon(fenceHeight);

		lblFenceHeightLeft.setVisible(true);

		JLabel lblFenceHeightRight = new JLabel();
		lblFenceHeightRight.setSize(40, 600);
		lblFenceHeightRight.setLocation(760, -120);

		lblFenceHeightRight.setIcon(fenceHeight);

		lblFenceHeightRight.setVisible(true);

		JLabel exitRight = new JLabel();
		exitRight.setSize(40, 80);
		exitRight.setLocation(760, 480);

		exitRight.setIcon(exitHeight);

		exitRight.setVisible(true);

		JLabel exitDown = new JLabel();
		exitDown.setSize(160, 40);
		exitDown.setLocation(640, 560);

		exitDown.setIcon(exitWidth);

		exitDown.setVisible(true);

		for (int i = 1; i <= 15; i++) {
			int x = 0;
			int y = 0;
			switch (i) {
			case 1:
				x = 80;
				y = 80;
				addFarmField(x, y, pnl, fieldEmpty);
				break;
			case 2:
				x = 200;
				y = 80;
				addFarmField(x, y, pnl, fieldEmpty);
				break;
			case 3:
				x = 80;
				y = 200;
				addFarmField(x, y, pnl, fieldEmpty);
				break;
			case 4:
				x = 200;
				y = 200;
				addFarmField(x, y, pnl, fieldEmpty);
				break;
			case 5:
				x = 360;
				y = 160;
				addFarmField(x, y, pnl, fieldEmpty);
				break;
			case 6:
				x = 480;
				y = 120;
				addFarmField(x, y, pnl, fieldEmpty);
				break;
			case 7:
				x = 600;
				y = 120;
				addFarmField(x, y, pnl, fieldEmpty);
				break;
			case 8:
				x = 480;
				y = 240;
				addFarmField(x, y, pnl, fieldEmpty);
				break;
			case 9:
				x = 600;
				y = 240;
				addFarmField(x, y, pnl, fieldEmpty);
				break;
			case 10:
				x = 120;
				y = 320;
				addFarmField(x, y, pnl, fieldEmpty);
				break;
			case 11:
				x = 240;
				y = 320;
				addFarmField(x, y, pnl, fieldEmpty);
				break;
			case 12:
				x = 120;
				y = 440;
				addFarmField(x, y, pnl, fieldEmpty);
				break;
			case 13:
				x = 240;
				y = 440;
				addFarmField(x, y, pnl, fieldEmpty);
				break;
			case 14:
				x = 400;
				y = 400;
				addFarmField(x, y, pnl, fieldEmpty);
				break;
			case 15:
				x = 560;
				y = 360;
				addFarmField(x, y, pnl, fieldEmpty);
				break;
			}
		}

		pnl.add(lblFenceWidthUp);
		pnl.add(lblFenceWidthDown);
		pnl.add(lblFenceHeightLeft);
		pnl.add(lblFenceHeightRight);
		pnl.add(exitDown);
		pnl.add(exitRight);
		pnl.add(lblBG);

		pnl.setVisible(true);

		return pnl;
	}

	public JPanel barnBackground() {
		JPanel pnl = new JPanel();
		pnl.setSize(800, 600);
		pnl.setLayout(null);

		JLabel lblEmptywidthUp = new JLabel();
		lblEmptywidthUp.setSize(800, 80);
		lblEmptywidthUp.setLocation(0, 0);

		lblEmptywidthUp.setBackground(new Color(0, 0, 0));
		lblEmptywidthUp.setOpaque(true);

		lblEmptywidthUp.setVisible(true);

		JLabel lblEmptywidthDown = new JLabel();
		lblEmptywidthDown.setSize(800, 120);
		lblEmptywidthDown.setLocation(0, 480);

		lblEmptywidthDown.setBackground(new Color(0, 0, 0));
		lblEmptywidthDown.setOpaque(true);

		lblEmptywidthDown.setVisible(true);

		JLabel lblEmptyHeightLeft = new JLabel();
		lblEmptyHeightLeft.setSize(40, 600);
		lblEmptyHeightLeft.setLocation(0, 0);

		lblEmptyHeightLeft.setBackground(new Color(0, 0, 0));
		lblEmptyHeightLeft.setOpaque(true);

		lblEmptyHeightLeft.setVisible(true);

		JLabel lblEmptyHeightRight = new JLabel();
		lblEmptyHeightRight.setSize(40, 600);
		lblEmptyHeightRight.setLocation(760, 0);

		lblEmptyHeightRight.setBackground(new Color(0, 0, 0));
		lblEmptyHeightRight.setOpaque(true);

		lblEmptyHeightRight.setVisible(true);

		JLabel lblExit = new JLabel();
		lblExit.setSize(80, 40);
		lblExit.setLocation(680, 480);

		lblExit.setBackground(new Color(255, 255, 255));
		lblExit.setOpaque(true);

		lblExit.setVisible(true);

		JLabel lblStair = new JLabel();
		lblStair.setSize(80, 40);
		lblStair.setLocation(560, 480);

		lblStair.setBackground(new Color(255, 255, 255));
		lblStair.setOpaque(true);

		lblStair.setVisible(true);

		JLabel lblWall = new JLabel();
		lblWall.setSize(720, 120);
		lblWall.setLocation(40, 80);

		lblWall.setBackground(Color.gray);
		lblWall.setOpaque(true);

		lblWall.setVisible(true);

		for (int i = 1; i <= 6; i++) {
			JLabel lblWindow = new JLabel();
			lblWindow.setSize(40, 40);
			switch (i) {
			case 1:
				lblWindow.setLocation(80, 120);
				break;
			case 2:
				lblWindow.setLocation(200, 120);
				break;
			case 3:
				lblWindow.setLocation(320, 120);
				break;
			case 4:
				lblWindow.setLocation(440, 120);
				break;
			case 5:
				lblWindow.setLocation(560, 120);
				break;
			case 6:
				lblWindow.setLocation(680, 120);
				break;
			}

			lblWindow.setBackground(Color.WHITE);
			lblWindow.setOpaque(true);

			lblWindow.setVisible(true);

			pnl.add(lblWindow);
		}

		JLabel lblFeeder = new JLabel();
		lblFeeder.setSize(520, 40);
		lblFeeder.setLocation(80, 200);

		lblFeeder.setBackground(Color.cyan);
		lblFeeder.setOpaque(true);

		lblFeeder.setVisible(true);

		JLabel lblFeederInput = new JLabel();
		lblFeederInput.setSize(80, 40);
		lblFeederInput.setLocation(640, 200);

		lblFeederInput.setBackground(Color.cyan);
		lblFeederInput.setOpaque(true);

		lblFeederInput.setVisible(true);

		pnl.add(lblExit);
		pnl.add(lblStair);
		pnl.add(lblWall);
		pnl.add(lblEmptywidthUp);
		pnl.add(lblEmptywidthDown);
		pnl.add(lblEmptyHeightLeft);
		pnl.add(lblEmptyHeightRight);
		pnl.add(lblFeeder);
		pnl.add(lblFeederInput);

		pnl.setVisible(true);

		return pnl;
	}

	public void addFarmField(int x, int y, JPanel pnl, ImageIcon fieldEmpty) {
		int tempX = 0;
		int tempY = 0;
		for (int i = 1; i <= 4; i++) {
			switch (i) {
			case 1:
				tempX = x;
				tempY = y;
				break;
			case 2:
				tempX = x + 40;
				tempY = y;
				break;
			case 3:
				tempX = x;
				tempY = y + 40;
				break;
			case 4:
				tempX = x + 40;
				tempY = y + 40;
				break;
			}
			JLabel lblField = new JLabel();
			lblField.setSize(40, 40);
			lblField.setLocation(tempX, tempY);
//			lblField.setIcon(fieldEmpty);
			pnl.add(lblField);
		}
	}
}