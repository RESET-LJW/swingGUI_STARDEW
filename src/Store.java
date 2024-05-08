import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class Store extends JPanel {
	static JTabbedPane jtp;
	private JPanel panel1;
	private JPanel spnl;
	private JPanel spnl2;
	private JPanel spnl3;
	static JLabel lblNewLabel;
	static JButton btnNewButton_1;

	public static List<AnimalThread> threads = new ArrayList<>();
	private AnimalImpl aniImpl = TestFrame.aniImpl;

	public Store(String str) {

		// 각 탭 별로 40*40 이미지
		String[] imagePaths = { "resource/가구메뉴.png", "resource/bigcow_right_3.png"};
		ImageIcon[] icons = new ImageIcon[imagePaths.length];
		for (int i = 0; i < imagePaths.length; i++) {
			icons[i] = new ImageIcon(imagePaths[i]);
		}

		showGUI(icons);
		// 24.03.05 맵에따라 상점 수정
		// 가구리스트 상점에 추가
		for (int i = 0; i < FurnitureTotal.furnitureTotalList.size(); i++) {
			new ItemPanel(FurnitureTotal.furnitureTotalList.get(i).getName(), spnl,
					FurnitureTotal.furnitureTotalList.get(i), null);
		}
		// 동물리스트 상점에 추가
		for (int i = 0; i < TestFrame.aniImpl.getAllAnimal().size(); i++) {
			System.out.println(aniImpl.getAllAnimal());
			new ItemPanel(TestFrame.aniImpl.getAllAnimal().get(i).getSpecies(), spnl2, null,
					TestFrame.aniImpl.getAllAnimal().get(i));
		}
	}

	// 2024.03.05
	// 구매버튼 액션리스너
	private ActionListener btnBuyActionListener(JTabbedPane jtp) {
		ActionListener l = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 각 메뉴별 인덱스
				if (jtp.getSelectedIndex() == 0) {
					for (Furniture f : FurnitureTotal.furnitureTotalList) {
						if (f.getName().equals(lblNewLabel.getText())) {
							if (!(FurnituresImpl.buyFurnitureList.contains(f))) {
								if(TestFrame.money>= f.getPrice()) {
									TestFrame.money -= f.getPrice();
									System.out.println(f.getPrice());
								} else {
									 JOptionPane.showMessageDialog(null, "구매 불가.", "경고", JOptionPane.WARNING_MESSAGE);
									 System.out.println("실패");
									 return;
								}
								FurnituresImpl.buyFurnitureList.add(f);
								System.out.println("가구 등록 확인");
							} else {
								int option = JOptionPane.showConfirmDialog(null, "이미 있는 가구입니다. 이동하시겠습니까?", "확인",
										JOptionPane.YES_NO_OPTION);
								// 사용자의 선택에 따라 처리
								if (option == JOptionPane.YES_OPTION) {
									FurnituresImpl.moveFurniture(f.getBlock(), FurnituresImpl.buyFurnitureList);
									TestFrame.s.setVisible(false);
									System.out.println("이동확인JOPtion");
									return;
								} else {
									return;
								}
							}
							FurnituresImpl.moveFurniture(f.getBlock(), FurnituresImpl.buyFurnitureList);
							TestFrame.s.setVisible(false);
							return;
							
						}
					}
				} else if (jtp.getSelectedIndex() == 1) {
					buyAnimals();
				}
			}

		};return l;

	}
	// 2024.03.05
	// 구매버튼 액션리스너
//	private ActionListener btnBuyActionListener(JTabbedPane jtp) {
//		ActionListener l = new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				// 각 메뉴별 인덱스
//				if (jtp.getSelectedIndex() == 0) {
//					for (Furniture f : FurnitureTotal.furnitureTotalList) {
//						if (f.getName().equals(l
//	blNewLabel.getText())) {
//							if (!(FurnituresImpl.buyFurnitureList.contains(f))) {
//								FurnituresImpl.buyFurnitureList.add(f);
//							}
//							System.out.println(FurnituresImpl.buyFurnitureList.toString());
//							FurnituresImpl.moveFurniture(f.getBlock(), FurnituresImpl.buyFurnitureList);
//							TestFrame.s.setVisible(false);
//							return;
//						}
//					}
//				} else if (jtp.getSelectedIndex() == 1) {
//					buyAnimals();
//				}
//			}
//
//		};
//		return l;
//	}

	// 동물구매시 알림창
	private void buyAnimals() {
		while (true) {
			if (TestFrame.aniImpl.getAllMyAnimal().size() < 10) {
				String input = JOptionPane.showInputDialog(Store.this, "이름을 입력하세요:", "입력",
						JOptionPane.QUESTION_MESSAGE);
				if (input != null && !input.isEmpty()) {
					JOptionPane.showMessageDialog(Store.this, input + "(이)가 태어났습니다!", "결과",
							JOptionPane.INFORMATION_MESSAGE);
					buyAnimal(input);
					break;
				} else if (input == null) {
					JOptionPane.showMessageDialog(Store.this, "이름을 지어주세요!", "결과", JOptionPane.INFORMATION_MESSAGE);
					break;
				}

			} else {
				JOptionPane.showMessageDialog(Store.this, "최대 10마리까지 키울 수 있습니다.", "결과",
						JOptionPane.INFORMATION_MESSAGE);
				break;
			}
		}
	}

	// 동물구매시 이미지 라벨과 스레드 생성
	private void buyAnimal(String input) {
		for (Animal elem : TestFrame.aniImpl.getAllAnimal()) {
			if (lblNewLabel.getText().equals(elem.getSpecies())) {
				JLabel lblAni = new JLabel();
				Animal a = TestFrame.aniImpl.addMyAnimal(elem);
				AnimalMouseListener mouseListener = new AnimalMouseListener(lblAni, a);
				lblAni.addMouseListener(mouseListener);
				lblAni.setBounds(200, 200, 40, 80);

				AnimalThread AniThread = new AnimalThread(lblAni, a);
				Thread thread = new Thread(AniThread);
				threads.add(AniThread);
				thread.start();

				TestFrame.barnPnl.add(lblAni, JLayeredPane.MODAL_LAYER);
				stopAllThreads();
				aniImpl.UpdateAnimalName(a, input);
				aniImpl.UpdatePurchaseDay(a);
				
				TestFrame.money -= elem.getPurchasPrice();
				System.out.println("동물리스트" + aniImpl.getAllAnimal().toString());
				System.out.println("내 동물리스트" + aniImpl.getAllMyAnimal().toString());
			}
		}
	}

	private void showGUI(ImageIcon[] icons) {
		jtp = new JTabbedPane(JTabbedPane.LEFT);
		jtp.setBorder(null);
		jtp.setBackground(null);
//		jtp.setOpaque(false);
		jtp.setBounds(110, 100, 330, 440);
		jtp.setUI(new ImageTabbedPaneUI(icons));
		add(jtp);

		// 가구메뉴
		JLabel tabbHead = new JLabel();
		tabbHead.setPreferredSize(new Dimension(30, 30));
		spnl = new JPanel();
		spnl.setOpaque(false);
		spnl.setLayout(new BoxLayout(spnl, BoxLayout.Y_AXIS));
		spnl.setBackground(null);
		JScrollPane jsp = new JScrollPane();
		jsp.setBorder(null);
		jsp.setBackground(null);
		jsp.setOpaque(false);
		jsp.setViewportView(spnl);
		jsp.getViewport().setOpaque(false);
		jsp.getViewport().setBackground(null);
		jsp.setSize(400, 420);

		// 축사메뉴
		JLabel tabbHead2 = new JLabel();
		tabbHead2.setPreferredSize(new Dimension(30, 30));
		spnl2 = new JPanel();
		spnl2.setOpaque(false);
		spnl2.setLayout(new BoxLayout(spnl2, BoxLayout.Y_AXIS));
		JScrollPane jsp2 = new JScrollPane();
		jsp2.setBorder(null);
		jsp2.setOpaque(false);
		jsp2.setViewportView(spnl2);
		jsp2.getViewport().setOpaque(false);
		jsp2.setSize(400, 420);
		jsp2.setBackground(new Color(100, 0, 100, 255));

		// 바닥메뉴
		JLabel tabbHead3 = new JLabel();
		tabbHead3.setPreferredSize(new Dimension(30, 30));
		spnl3 = new JPanel();
		spnl3.setOpaque(false);
		spnl3.setLayout(new BoxLayout(spnl3, BoxLayout.Y_AXIS));
		JScrollPane jsp3 = new JScrollPane();
		jsp3.setOpaque(false);
		jsp3.setViewportView(spnl3);
		jsp3.setSize(400, 420);
		jsp3.setBackground(new Color(100, 0, 100, 255));

		jtp.addTab("테스트", jsp);
		jtp.setTabComponentAt(0, tabbHead);

		jtp.addTab("테스트1", jsp2);
		jtp.setTabComponentAt(1, tabbHead2);

		panel1 = new JPanel();
		panel1.setBorder(null);
		panel1.setLayout(null);
		panel1.setBounds(440, 100, 250, 440);
		add(panel1);

		JLabel help = new JLabel();
		help.setSize(250, 440);
		help.setLocation(0, 0);
		panel1.add(help);

		lblNewLabel = new JLabel();
		lblNewLabel.setBounds(23, 30, 60, 20);
//		lblNewLabel.setOpaque(false);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
		panel1.add(lblNewLabel);

		btnNewButton_1 = new JButton("구매하기");
		btnNewButton_1.setBounds(127, 390, 97, 23);
		btnNewButton_1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnNewButton_1.setFocusPainted(false);
		ActionListener l = btnBuyActionListener(jtp);
		btnNewButton_1.addActionListener(l);
		help.add(btnNewButton_1);

		JButton btnNewButton = new JButton("X");
		btnNewButton.setFocusPainted(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 상점메뉴 종료시 설정 초기화
				jtp.setSelectedIndex(0);
				lblNewLabel.setText("");
				btnNewButton_1.setText("구매하기");
				setVisible(false);
				TestFrame.character.getCharacter().setVisible(true);
				startAllThreads();
			}
		});
		btnNewButton.setBounds(180, 20, 42, 37);
		help.add(btnNewButton);

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
		setBackground(null);
		setOpaque(false);
		setSize(750, 550);
		setVisible(false);
	}

	public void startAllThreads() {
		for (AnimalThread thread : threads) {
			thread.setRunning(true);
		}
	}

	public void stopAllThreads() {
		for (AnimalThread thread : threads) {
			thread.setRunning(false);
		}
	}

	class ImageTabbedPaneUI extends BasicTabbedPaneUI {
		private ImageIcon[] tabIcons;

		public ImageTabbedPaneUI(ImageIcon[] tabIcons) {
			this.tabIcons = tabIcons;
		}

		@Override
		protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
				boolean isSelected) {
//            g.setColor(Color.pink); // 탭 패널의 배경색으로 설정
			g.setColor(new Color(240, 240, 240)); // 탭 패널의 배경색으로 설정
//			g.setColor(null);
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
}
