import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import java.awt.BorderLayout;

public class Inventory extends JPanel {
	private JPanel panel1, panel2, panel3;
	private static int deltaX, deltaY;
//	 Map<Integer, String> inven;
	static Map<Integer, ItemPair<String, JLabel, JLabel>> inven = new TreeMap<Integer, ItemPair<String, JLabel, JLabel>>();
	private JPanel panelTest;
	private static JLabel delete;
	private static String itemName;

	public Inventory(String str) {

		// 각 탭 별로 40*40 이미지
		String[] imagePaths = { "C:\\Users\\GGG\\Desktop\\가구메뉴.png", "C:\\Users\\GGG\\Desktop\\벽지메뉴.png",
				"C:\\Users\\GGG\\Desktop\\바닥메뉴.png" };
		ImageIcon[] icons = new ImageIcon[imagePaths.length];
		for (int i = 0; i < imagePaths.length; i++) {
			icons[i] = new ImageIcon(imagePaths[i]);
		}

		JTabbedPane jtp = new JTabbedPane(JTabbedPane.TOP);
		jtp.setBorder(new LineBorder(new Color(0, 0, 0)));
		jtp.setBackground(Color.WHITE);
		jtp.setOpaque(true);
		jtp.setBounds(110, 70, 510, 392);
		jtp.setUI(new ImageTabbedPaneUI(icons));
		add(jtp);
		// 추후 매서드로 바꾸기
		// 가구메뉴
		JLabel tabbHead = new JLabel();
		tabbHead.setPreferredSize(new Dimension(20, 30));
		JPanel spnl = new JPanel();
		spnl.setOpaque(false);
		JScrollPane jsp = new JScrollPane();
		jsp.setOpaque(false);
		jsp.setViewportView(spnl);
		spnl.setLayout(new BorderLayout(0, 0));

		panel1 = new JPanel();
		spnl.add(panel1, BorderLayout.SOUTH);
		panel1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel1.setLayout(null);
		panel1.setBackground(Color.WHITE);
		panel1.setPreferredSize(new Dimension(200, 180));

		JLabel lblNewLabel = new JLabel("설명");
		lblNewLabel.setBounds(23, 26, 57, 15);
		panel1.add(lblNewLabel);

		JButton sortButton = new JButton("정렬");
		sortButton.setBounds(420, 55, 80, 50);
		sortButton.setOpaque(false);
		sortButton.setBorderPainted(false);
		sortButton.setFocusPainted(false);
		sortButton.setBackground(new Color(0, 0, 0, 0));
		sortButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sortInventory();
			}
		});
		panel1.add(sortButton);
		
		JButton deleteButton = new JButton("삭제");
		deleteButton.setBounds(420, 0, 80, 50);
		deleteButton.setOpaque(false);
		deleteButton.setBorderPainted(false);
		deleteButton.setFocusPainted(false);
		deleteButton.setBackground(new Color(0, 0, 0, 0));
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deletInventory();
			}
		});
		panel1.add(deleteButton);

		JButton takeOuButton = new JButton("꺼내기");
		takeOuButton.setBounds(420, 110, 80, 50);
		takeOuButton.setOpaque(false);
		takeOuButton.setBorderPainted(false);
		takeOuButton.setFocusPainted(false);
		takeOuButton.setBackground(new Color(0, 0, 0, 0));
		takeOuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				takeOutItem();
			}
		});
		panel1.add(takeOuButton);

		JButton btnNewButton_1 = new JButton("구매하기");
		btnNewButton_1.setBounds(127, 390, 97, 23);
		panel1.add(btnNewButton_1);
		jsp.setSize(400, 420);
		jsp.setBackground(new Color(100, 0, 100, 255));

		panelTest = new JPanel();
		panelTest = inventoryBtn();

		for (int i = 0; i < 1; i++) {
			spnl.add(panelTest, BorderLayout.CENTER);
		}

		// 첫번째칸 테스트
//		ItemPair<String, JLabel, JLabel> pair = inven.get(2);
//		JLabel value1 = pair.getImage();
//		value1.setIcon(new ImageIcon("resource/집_장작.png"));
//		pair.setKey("의자");
//		ItemPair<String, JLabel, JLabel> pair2 = inven.get(1);
//		JLabel value2 = pair2.getImage();
//		value2.setIcon(new ImageIcon("resource/집_장작.png"));
//		pair2.setKey("의자");

		// 벽지메뉴
		JLabel tabbHead2 = new JLabel();
		tabbHead2.setPreferredSize(new Dimension(20, 30));
		JPanel spnl2 = new JPanel();
		spnl2.setOpaque(false);
		spnl2.setLayout(new BoxLayout(spnl2, BoxLayout.Y_AXIS));
		JScrollPane jsp2 = new JScrollPane();
		jsp2.setOpaque(false);
		jsp2.setViewportView(spnl2);
		jsp2.setSize(400, 420);
		jsp2.setBackground(new Color(100, 0, 100, 255));

		// 바닥메뉴
		JLabel tabbHead3 = new JLabel();
		tabbHead3.setPreferredSize(new Dimension(20, 20));
		JPanel spnl3 = new JPanel();
		spnl3.setOpaque(false);
		spnl3.setLayout(new BoxLayout(spnl3, BoxLayout.Y_AXIS));
		JScrollPane jsp3 = new JScrollPane();
		jsp3.setOpaque(false);
		jsp3.setViewportView(spnl3);
		jsp3.setSize(400, 420);
		jsp3.setBackground(new Color(100, 0, 100, 255));
//		for (int i = 0; i < 3; i++) {
//			spnl3.add(ItemPanel);
//		}

		jtp.addTab("테스트", jsp);
		jtp.addTab("테스트1", jsp2);
		jtp.addTab("테스트2", jsp3);
		jtp.setTabComponentAt(0, tabbHead);
		jtp.setTabComponentAt(1, tabbHead2);
		jtp.setTabComponentAt(2, tabbHead3);

		JButton btnNewButton = new JButton("X");
		btnNewButton.setFocusPainted(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setBackground(Color.WHITE); // Set background color
		btnNewButton.setBorder(new LineBorder(new Color(0, 0, 0), 3)); // Set border
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				takeOutItem();
				setVisible(false);
				TestFrame.character.getCharacter().setVisible(true);
				TestFrame.frame.repaint();
				TestFrame.homePnl.repaint();

			}
		});
		btnNewButton.setBounds(645, 23, 42, 37);
		add(btnNewButton);

		// 탭이 선택될 때마다 스크롤을 최상단으로 이동
		jtp.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JScrollPane selectedScrollPane = (JScrollPane) jtp.getSelectedComponent();
				if (selectedScrollPane != null) {
					selectedScrollPane.getVerticalScrollBar().setValue(0);
				}
			}
		});

		setLayout(null);
		setBackground(new Color(0, 0, 0, 0));
		setSize(750, 550);
	}

//	public static void main(String[] args) {
//		UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
//		UIManager.getDefaults().put("TabbedPane.tabsOverlapBorder", false);
//		JFrame jf = new JFrame();
//		jf.getContentPane().setBackground(Color.orange);
//		jf.getContentPane().setLayout(null);
//		jf.setSize(800, 600);
//		jf.getContentPane().add(new Inventory("소지품"));
//		jf.setVisible(true);
//
//	}

	class ImageTabbedPaneUI extends BasicTabbedPaneUI {
		private ImageIcon[] tabIcons;

		public ImageTabbedPaneUI(ImageIcon[] tabIcons) {
			this.tabIcons = tabIcons;
		}

		@Override
		protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
				boolean isSelected) {
//            g.setColor(Color.pink); // 탭 패널의 배경색으로 설정
			g.setColor(new Color(0, 0, 0, 0)); // 탭 패널의 배경색으로 설정
			g.fillRect(x, y, w, h); // 배경 그리기

//			if (isSelected) { // 선택된 탭일 때만 그라데이션 효과 적용
//				Graphics2D g2d = (Graphics2D) g.create();
//				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1)); // 투명도 설정
//				g2d.dispose();
//			}
		}

		@Override
		protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect,
				Rectangle textRect) {
			super.paintTab(g, tabPlacement, rects, tabIndex, iconRect, textRect);

			if (tabIcons != null && tabIcons.length > tabIndex && tabIcons[tabIndex] != null) {
				int x = rects[tabIndex].x + (rects[tabIndex].width - tabIcons[tabIndex].getIconWidth()) / 2;
				int y = rects[tabIndex].y + (rects[tabIndex].height - tabIcons[tabIndex].getIconHeight()) / 2;
				tabIcons[tabIndex].paintIcon(null, g, x, y); // 이미지 그리기
			}
		}
	}

	// 마지막으로 클릭한 아이템 페어 찾는 메소드
	public ItemPair<String, JLabel, JLabel> findItemPair(JLabel lbl) {
		for (Map.Entry<Integer, ItemPair<String, JLabel, JLabel>> entry : inven.entrySet()) {
			ItemPair<String, JLabel, JLabel> triple = entry.getValue();
			if (triple.getImage().equals(lbl)) {
				return triple;
			}
		}
		return null;
	}

	// 인벤 추가
	public void addInventory(Block block, JLabel image) {
		for (Map.Entry<Integer, ItemPair<String, JLabel, JLabel>> entry : inven.entrySet()) {
			ItemPair<String, JLabel, JLabel> triple = entry.getValue();
			if (triple.getKey().isEmpty()) {
				entry.getValue().setKey(block.getInfo());
				entry.getValue().getImage().setIcon(image.getIcon());
				System.out.println(entry.getValue().toString());
				System.out.println("좌표" + entry.getValue().getCoordinate().toString());
				System.out.println(entry.getValue().getImage().toString());
				this.repaint();
				return;
			}
			System.out.println("투스링" + entry.toString());
		}
	}

	// 선택된 칸에대해서 삭제 이미지,
	public void deletInventory() {
		try {
		findItemPair(delete).setKey("");
		delete.setIcon(null);
		for (int i = 0; i < FurnituresImpl.buyFurnitureList.size(); i++) {
			if(FurnituresImpl.buyFurnitureList.get(i).getName().equals(itemName)) {
				FurnituresImpl.buyFurnitureList.remove(i);
			}
			
		}
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
	}

	// 아이템 꺼내기
	public void takeOutItem() {
		if (TestFrame.currentFrame == 2) {
			for (Furniture f : FurnituresImpl.buyFurnitureList) {
				if (findItemPair(delete).getKey().equals(f.getName())) {
					FurnituresImpl.moveFurniture(f.getBlock(), FurnituresImpl.buyFurnitureList);
					findItemPair(delete).setKey("");
					delete.setIcon(null);
					TestFrame.in.setVisible(false);
				}
			}
		}
	}

	public static JPanel inventoryBtn() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(0, 0, 0, 0));
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 10; j++) {
				JLabel btn = new JLabel();
				btn.setFocusable(false);
				btn.setBackground(Color.WHITE);
				btn.setOpaque(false);
				btn.setBorder(new LineBorder(Color.black, 1));
				btn.setBounds(10 + (j * 41), 10 + (i * 50), 40, 40);

				ImageIcon icon = new ImageIcon("");
				JLabel itemlbl = new JLabel();

				inven.put((i * 10) + j, new ItemPair<>("", btn, itemlbl));
				itemlbl.setSize(40, 40);
				itemlbl.setBackground(Color.black);
				itemlbl.setLocation(btn.getX(), btn.getY());
				panel.add(btn);
				panel.add(itemlbl); // 아이템 라벨을 먼저 패널에 추가하여 버튼 위로 올라가게 함
				panel.setComponentZOrder(btn, 0);
				itemlbl.addMouseListener(new MouseAdapter() {

					private int beforeIndex;
					private int x;
					private int y;
					private int index;
				

					@Override
					public void mousePressed(MouseEvent e) {
						deltaX = e.getX();
						deltaY = e.getY();
						beforeIndex = ((itemlbl.getY() - deltaY) / 50) * 10 + ((itemlbl.getX() - deltaX) / 40);
						for (Map.Entry<Integer, ItemPair<String, JLabel, JLabel>> entry : inven.entrySet()) {
							ItemPair<String, JLabel, JLabel> triple = entry.getValue();
							if (triple.getImage().equals(itemlbl)) {
								index = entry.getKey();
								itemName = triple.getKey();
								System.out.println("인덱스" + entry.getKey() + "--------------");
							}
						}
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						x = itemlbl.getX();
						y = itemlbl.getY();
						int closestIndex;

						List<Double> distances = new ArrayList<>();
//						System.out.println("B"+beforeIndex);
						for (Map.Entry<Integer, ItemPair<String, JLabel, JLabel>> entry : inven.entrySet()) {
							ItemPair<String, JLabel, JLabel> triple = entry.getValue();
							JLabel label = triple.getCoordinate();
							if (triple.getKey() != null && triple.getKey().isEmpty()) {
								double distance = Math
										.sqrt(Math.pow(x - label.getX(), 2) + Math.pow(y - label.getY(), 2));
								distances.add(distance);
							} else if (triple.getKey().equals(inven.get(beforeIndex + 1).getKey())) {
								distances.add(0.0);
							} else {
								distances.add(Double.MAX_VALUE);
							}
						}
						while (true) {
							closestIndex = distances.indexOf(Collections.min(distances));
							ItemPair<String, JLabel, JLabel> closestPair = inven.get(closestIndex);
//							String beforeS = inven.get(beforeIndex).getKey();
							if (closestPair.getKey().isEmpty()) {
								closestPair.setKey(inven.get(index).getKey());
								closestPair.getImage().setIcon(inven.get(index).getImage().getIcon());
								itemlbl.setLocation(inven.get(index).getCoordinate().getX(),
										inven.get(index).getCoordinate().getY());
								// 삭제 메소드용 값저장
								delete = closestPair.getImage();
//								closestPair.getImage().setLocation(closestPair.getCoordinate().getX(), closestPair.getCoordinate().getY());
								inven.get(index).getImage().setIcon(null);
								inven.get(index).setKey("");
								System.out.println("놓은위치 키값 변경" + closestPair.getKey());
								System.out.println("놓은위치 인덱스" + closestIndex);
								System.out.println("과거인덱스" + index);
								System.out.println("값변경확인" + inven.get(index).getKey());

								break;
							} else {
								distances.set(closestIndex, Double.MAX_VALUE);
							}
						}

//						System.out.println("C"+closestIndex);
						for (Map.Entry<Integer, ItemPair<String, JLabel, JLabel>> entry : inven.entrySet()) {
							Integer key = entry.getKey();
							ItemPair<String, JLabel, JLabel> value = entry.getValue();
//							System.out.println("Key: " + key + ", Value: " + value.getKey());
						}

					}

				});

				itemlbl.addMouseMotionListener(new MouseMotionAdapter() {
					@Override
					public void mouseDragged(MouseEvent e) {
						int x = itemlbl.getX() + e.getX() - deltaX;
						int y = itemlbl.getY() + e.getY() - deltaY;
						itemlbl.setLocation(x, y);
						panel.repaint();
					}
				});
			}
		}
		return panel;
	}

	// 정렬
	public void sortInventory() {
		for (Map.Entry<Integer, ItemPair<String, JLabel, JLabel>> entry : inven.entrySet()) {
			for (Map.Entry<Integer, ItemPair<String, JLabel, JLabel>> entry2 : inven.entrySet()) {
				ItemPair<String, JLabel, JLabel> triple = entry.getValue();
				ItemPair<String, JLabel, JLabel> triple2 = entry2.getValue();
				if (!triple.getKey().isEmpty()) {
					if (triple2.getKey().isEmpty()) {
						triple2.setKey(triple.getKey());
						triple2.getImage().setIcon(triple.getImage().getIcon());
						triple.setKey("");
						triple.getImage().setIcon(null);

					}

				}
			}
		}

	}
}