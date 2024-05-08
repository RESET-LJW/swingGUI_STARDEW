import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//import javafx.scene.transform.Rotate;

public class TestFrame extends JFrame {
	public static TestFrame frame;
	public static List<List<Block>> blockList;
	public static List<List<JLabel>> imageList;
	public static List<Block> blocks;
	public static List<JLabel> images;
	public static List<Block> blocks2;
	public static Character character;
	public static JLayeredPane farmPnl;
	public static JLayeredPane fishingPnl;
	public static Map<Integer, String> plantMap = new HashMap<>();
	public static List<Block> furnitureList;
//	public static List<Block> barnblockList;
	public static int currentFrame = 1;
	public static int time;
	public static int playtime;
	public static boolean isPlaying = false;
	public static int day = 1;
	public static int money = 50000;
	public static ClockPanel clock;
	protected static JLayeredPane barnPnl;
	public static AnimalImpl aniImpl = new AnimalImpl();
	protected static JLayeredPane homePnl;
	public static Inventory in;
	public static Store s;

	public static JPanel pnlBlack;
	public static boolean isSleeping = false;
	public static boolean isWorking = false;

	public static Thread thread;
	public static Main imagePanel;
	public static JFrame frame1;
	public static FurnitureTotal furTotal = new FurnitureTotal();

	public static String username;
	public static String password;
	public static int userid;

	public static void main(String[] args) {
		String baseImagePath = "resource/day.png";

		BufferedImage baseImage = null;
		try {
			baseImage = ImageIO.read(new File(baseImagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (baseImage == null) {
			System.err.println("기본 이미지를 불러올 수 없습니다.");
			return;
		}

		frame1 = new JFrame("Slow Image Thread");
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		imagePanel = new Main(baseImage);
		frame1.setUndecorated(true);
		frame1.getContentPane().add(imagePanel);
		frame1.pack();
		frame1.setVisible(true);
		frame1.setLocationRelativeTo(null);
		thread = new Thread(imagePanel);
		thread.start();

//		frame = new TestFrame();
		// 오프닝 작업(로그인이라던가 게임 시작 전화면, 끝나면 메인창으로 이동 : changeFrame(1)
//		changeFrame(1);
//		frame.setVisible(true);
	}

	public static void changeFrame(int i) {
		// images, blocks 저장
		imageList.remove(currentFrame - 1);
		imageList.add(currentFrame - 1, images);
		blockList.remove(currentFrame - 1);
		blockList.add(currentFrame - 1, blocks);
		switch (i) {
		case 1:
			switch (currentFrame) {
			case 2:
				character.getCharacter().setLocation(160, 160);
				break;
			case 3:
				character.getCharacter().setLocation(480, 160);
				break;
			case 4:
				character.getCharacter().setLocation(560, 160);
				break;
			default:
				character.getCharacter().setLocation(40, 200);
				break;
			}
			currentFrame = 1;
			break;
		case 2:
			character.getCharacter().setLocation(720, 480);
			currentFrame = 2;
			break;
		case 3:
			character.getCharacter().setLocation(680, 520);
			currentFrame = 3;
			break;
		case 4:
			character.getCharacter().setLocation(680, 440);
			currentFrame = 4;
			break;
		}
		if (currentFrame == 2) {
			s.jtp.setEnabledAt(0, true);
			s.jtp.setEnabledAt(1, false);
			s.jtp.setSelectedIndex(0);

		} else if (currentFrame == 4) {
			s.jtp.setEnabledAt(1, true);
			s.jtp.setEnabledAt(0, false);
			s.jtp.setSelectedIndex(1);
		}

		// 이동전 화면의 충돌블럭, 이미지라벨 안보이게하기
		for (JLabel lbl : images) {
			lbl.setVisible(false);
		}
		for (Block b : blocks) {
			String Info = b.getInfo();
			if (Function.plantImageMap.get(Info + "배경") != null) { // 농장 식물 배경제거
				Function.plantImageMap.get(Info + "배경").setVisible(false);
			}
			if (Function.plantImageMap.get(Info + "식물") != null) { // 농장 식물 제거
				Function.plantImageMap.get(Info + "식물").setVisible(false);
			}
			b.setVisible(false);
		}
		// 이동한 화면의 충돌블럭, 이미지라벨 보이게하기
		blocks = new ArrayList<>(blockList.get(i - 1));
		images = new ArrayList<>(imageList.get(i - 1));
		for (Block b : blocks) {
			b.setVisible(true);
			if (b.getInfo().contains("밭")) {
				character.getFunction().randSetting(b);
				character.getFunction().plantSetting(b);
				b.setVisible(false);
			}
			if (b.getInfo().contains("나무울타리")) {
				b.setVisible(false);
			}
			if (b.getInfo().equals("절벽") || b.getInfo().equals("얕은물") || b.getInfo().equals("깊은물")) {
				b.setVisible(false);
			}
		}
		for (JLabel lbl : images) {
			lbl.setVisible(true);
		}
		Function.fieldID = 1;
		if (i == 1) { // 메인창일때, 낚시 패널 붙임
			fishingPnl.setVisible(true);
			frame.add(fishingPnl);
		} else {
			fishingPnl.setVisible(false);
			frame.remove(fishingPnl);
		}
		if (i == 2) {
			homePnl.setVisible(true);
			frame.add(homePnl);
		} else {
			homePnl.setVisible(false);
			frame.remove(homePnl);
		}
		if (i == 3) { // 농장일때, 식물 패널 붙임
			farmPnl.setVisible(true);
			frame.add(farmPnl);
		} else {
			farmPnl.setVisible(false);
			frame.remove(farmPnl);
		}
		if (i == 4) {
			frame.add(barnPnl);
			barnPnl.setVisible(true);
//			aniImpl.myAnimalList.add(new Animal("소", "cow", 1, 2, "우유", 230, 150, -1));
//			aniImpl.loadAnimal(aniImpl.getAllMyAnimal());
		} else {
			barnPnl.setVisible(false);
			frame.remove(barnPnl);
		}
		frame.revalidate();
		frame.repaint();
	}

	public TestFrame() {
		character = new Character(this);
		pnlBlack = new JPanel();
		pnlBlack.setBounds(0, 0, 800, 600);
		pnlBlack.setVisible(false);
		add(pnlBlack);

		s = new Store("상점");
		in = new Inventory("인벤");
		add(s);
		add(in);
		s.setVisible(false);
		in.setVisible(false);
		fishingPnl = Addon.fishingPanel();
		farmPnl = Addon.farmPlantPanel();
		barnPnl = Addonbarn.barnPanel();
		homePnl = Addonhome.homeFurniturePanel();
//		plantMap = new HashMap<>(); // 농장 밭의 식물정보를 기록하는 Map
		for (int i = 1; i <= 64; i++) { // 초기값은 모두 식물이 없는 빈땅
			plantMap.put(i, "빈땅");
		}
//		JPanel quickSlot = Addon.makeQuickSlot();
//		quickSlot.setLocation(0, 600);
//		quickSlot.setVisible(true);
//		add(quickSlot);
		JPanel rightPnl = Addon.makeRightPnl();
		rightPnl.setLocation(800, 0);
		rightPnl.setVisible(true);
		add(rightPnl);
		clock = new ClockPanel();
		clock.setLocation(640, 0);
		clock.setVisible(true);
		add(clock);
		blockList = new ArrayList<>();
		imageList = new ArrayList<>();
		blocks = new ArrayList<>();
		BlocksInfo bInfo = new BlocksInfo();

		furnitureList = new ArrayList<>(bInfo.homeBlocks());
//		barnblockList = new ArrayList<>(bInfo.barnBlocks());

		startSetting(bInfo.mainBlocks()); // 인덱스 0 : 메인
		startSetting(bInfo.homeBlocks()); // 인덱스 1 : 집
		startSetting(bInfo.farmBlocks()); // 인덱스 2 : 농장
		startSetting(bInfo.barnBlocks()); // 인덱스 3 : 축사

		// 초기화 끝난 후, 기본세팅
		blocks = blockList.get(0);
		images = imageList.get(0);

		FurnituresImpl.buyFurnitureList.add(FurnitureTotal.furnitureTotalList.get(0));
		FurnituresImpl.buyFurnitureList.add(FurnitureTotal.furnitureTotalList.get(1));
		FurnituresImpl.buyFurnitureList.add(FurnitureTotal.furniturewallPaperList.get(0));

		System.out.println(FurnitureTotal.furnitureTotalList.toString());

		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(frame1.getX(), frame1.getY(), 1000, 600);
		setLayout(null);
		setFocusable(true);
	}

	public void startSetting(List<Block> bList) {
		List<Block> copyBlockList = null;
		List<JLabel> copyImageList = null;
		for (Block b : bList) {
			character.getFunction().createBlock(b, blocks);
		}
		copyBlockList = new ArrayList<>(blocks);
		copyImageList = new ArrayList<>(character.getFunction().getLblImageList());
		blockList.add(copyBlockList);
		imageList.add(copyImageList);
		for (Block b : blocks) {
			String Info = b.getInfo();
			if (Function.plantImageMap.get(Info + "배경") != null) { // 농장 식물 배경제거
				Function.plantImageMap.get(Info + "배경").setVisible(false);
			}
			if (Function.plantImageMap.get(Info + "식물") != null) { // 농장 식물 제거
				Function.plantImageMap.get(Info + "식물").setVisible(false);
			}
			b.setVisible(false);
		}
		for (JLabel lbl : character.getFunction().getLblImageList()) {
			lbl.setVisible(false);
		}
		blocks.clear();
		character.getFunction().getLblImageList().clear();
	}
}

class Main extends JPanel implements Runnable {
	private BufferedImage baseImage;
	private BufferedImage titleImage;
	private int x, y; // 이미지의 왼쪽 상단 모서리 좌표
	private int dx = 1; // 이동 거리f
	private Timer t;
	private int time = 0;
	private boolean threadPaused = false;
	private JButton newLabel;
	private JButton loadLabel;
	private JButton exitLabel;
	private static Main imagePanel = TestFrame.imagePanel;
	private static JFrame frame1 = TestFrame.frame1;

	public static boolean isPlaying = false;

	public static JPanel pnlBlack;

	public Main(BufferedImage baseImage) {
		this.baseImage = baseImage;
		x = 0;
		y = 0;
		setPreferredSize(new Dimension(800, 600));
		setOpaque(false);
		setLayout(null);

		String titleImagePath = "resource/maintitle.png";
		try {
			titleImage = ImageIO.read(new File(titleImagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		titleImage = resizeImage(titleImage, 550, 260);

		JLabel titleLabel = new JLabel(new ImageIcon(titleImage));
		titleLabel.setBounds(0, 50, 800, titleImage.getHeight());
		add(titleLabel, SwingConstants.CENTER);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel newTemp = new JLabel();
		JLabel loadTemp = new JLabel();
		JLabel exitTemp = new JLabel();
		add(newTemp);
		add(loadTemp);
		add(exitTemp);
		newLabel = new JButton();
		newLabel.setBounds(100, 390, 180, 140);
		newLabel.setIcon(new ImageIcon("new.png"));
		newLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				newTemp.setVisible(false);
				newLabel.setIcon(new ImageIcon("resource/new_hover.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				newLabel.setIcon(new ImageIcon("resource/new.png"));
			}

		});
		newLabel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TestFrame.imagePanel.setVisible(false);
				toggleThreadPause();
				JPanel pnlBlack = new JPanel();
				frame1.add(pnlBlack);
				pnlBlack.setBounds(0, 0, 800, 600);
				setEnabled(false);

				Timer t = new Timer(50, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						time++;
						System.out.println(time);
						if (time <= 38) {
							pnlBlack.setBackground(new Color(0, 0, 0, time)); // 배경색을 어둡게 설정
						}
//						pnlBlack.repaint();
						if (time == 40) {
							((Timer) e.getSource()).stop();
							boolean isValid = false;
							while (true) {
								String inputname = JOptionPane.showInputDialog(frame1, "아이디: (4~6자의 영문자 또는 숫자)", "회원가입",
										JOptionPane.PLAIN_MESSAGE);
								String inputpassword = JOptionPane.showInputDialog(frame1, "비밀번호: (4자 이상, 영문자와 숫자 포함)",
										"회원가입", JOptionPane.PLAIN_MESSAGE);

								isValid = isValidUsername(inputname) && isValidPassword(inputpassword);
								if (isValid && !DB.isExist(inputname, inputpassword)) {
									TestFrame.username = inputname;
									TestFrame.password = inputpassword;
									frame1.setVisible(false);
									TestFrame.thread.stop();
									DB.register();
									startPrograme();
									break;
								} else {
									if (!isValidUsername(inputname) && !isValidPassword(inputpassword)) {
										JOptionPane.showMessageDialog(frame1, "유효한 아이디와 비밀번호를 입력하세요", "알림",
												JOptionPane.INFORMATION_MESSAGE);
									} else if (!isValidPassword(inputpassword)) {
										JOptionPane.showMessageDialog(frame1, "유효한 비밀번호를 입력하세요", "알림",
												JOptionPane.INFORMATION_MESSAGE);

									} else if (!isValidUsername(inputname)) {
										JOptionPane.showMessageDialog(frame1, "유효한 아이디를 입력하세요", "알림",
												JOptionPane.INFORMATION_MESSAGE);
									}
								}
							}
						}

					}
				});
				t.start();
//				String username = "";
//				String password = "";
//				if (time == 50) {
//					username = JOptionPane.showInputDialog(frame1, "Enter your username:", "Login",
//							JOptionPane.PLAIN_MESSAGE);
//					password = JOptionPane.showInputDialog(frame1, "Enter your password:", "Login",
//							JOptionPane.PLAIN_MESSAGE);
//				}
//				// 쿼리문
//				t.stop();
//				frame1.setVisible(false);
//				TestFrame.thread.stop();
//				startPrograme();

			}
		});

		add(newLabel);

		loadLabel = new JButton();
		loadLabel.setBounds(310, 390, 180, 140);
		loadLabel.setIcon(new ImageIcon("load.png"));
		loadLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				loadTemp.setVisible(false);
				loadLabel.setIcon(new ImageIcon("resource/load_hover.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				loadLabel.setIcon(new ImageIcon("resource/load.png"));
			}

		});
		loadLabel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TestFrame.imagePanel.setVisible(false);
				toggleThreadPause();
				JPanel pnlBlack = new JPanel();
				frame1.add(pnlBlack);
				pnlBlack.setBounds(0, 0, 800, 600);
				setEnabled(false);
				Timer t = new Timer(50, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						time++;
						System.out.println(time);
						if (time <= 38) {
							pnlBlack.setBackground(new Color(0, 0, 0, time)); // 배경색을 어둡게 설정
						}
//						pnlBlack.repaint();
						if (time == 40) {
							((Timer) e.getSource()).stop();
							while (true) {
								String inputname = JOptionPane.showInputDialog(frame1, "아이디: (4~6자의 영문자 또는 숫자)", "로그인",
										JOptionPane.PLAIN_MESSAGE);
								String inputpassword = JOptionPane.showInputDialog(frame1, "비밀번호: (4자 이상, 영문자와 숫자 포함)",
										"로그인", JOptionPane.PLAIN_MESSAGE);
								// 쿼리문
								if (!(inputname == "" || inputpassword == "")) {
									if (DB.isExist(inputname, inputpassword)) {
										startPrograme();
										frame1.setVisible(false);
										try {
											TimeUnit.SECONDS.sleep(2);
										} catch (InterruptedException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										TestFrame.thread.stop();
										DB.load(inputname, inputpassword);
										break;
									} else {
										JOptionPane.showMessageDialog(frame1, "아이디 또는 비밀번호를 확인하세요", "알림",
												JOptionPane.INFORMATION_MESSAGE);
									}
								}
								// 여기에 일치하는 이름, 비번정보 없다고 말해주기(로그인 실패)
							}
						}
					}
				});
				t.start();
			}
		});
		add(loadLabel);

		exitLabel = new JButton();
		exitLabel.setBounds(520, 390, 180, 140);
		exitLabel.setIcon(new ImageIcon("exit.png"));
		exitLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				exitTemp.setVisible(false);
				exitLabel.setIcon(new ImageIcon("resource/exit_hover.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				exitLabel.setIcon(new ImageIcon("resource/exit.png"));
			}

		});
		exitLabel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		add(exitLabel);

		newTemp.setBounds(newLabel.getX(), newLabel.getY(), newLabel.getWidth(), newLabel.getHeight());
		loadTemp.setBounds(loadLabel.getX(), loadLabel.getY(), loadLabel.getWidth(), loadLabel.getHeight());
		exitTemp.setBounds(exitLabel.getX(), exitLabel.getY(), exitLabel.getWidth(), exitLabel.getHeight());
		newTemp.setIcon(new ImageIcon("resource/new.png"));
		loadTemp.setIcon(new ImageIcon("resource/load.png"));
		exitTemp.setIcon(new ImageIcon("resource/exit.png"));
	}

	public static boolean isValidUsername(String username) {
		// 정규 표현식: 4~6자의 영문자 또는 숫자
		String regex = "^[a-zA-Z0-9]{4,6}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(username);
		return matcher.matches();
	}

	public static boolean isValidPassword(String password) {
		// 정규 표현식: 8자 이상, 영문자와 숫자 포함
		String regex = "^(?=.*[A-Za-z])(?=.*\\d).{4,}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

	// 이미지 크기 조정 메서드
	private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
		BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
		g.dispose();
		return resizedImage;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 기본 이미지 그리기
		if (baseImage != null) {
			g.drawImage(baseImage, x, y, null);
		}
	}

	@Override
	public void run() {
		while (true) {
			if (!threadPaused) {
				x -= dx; // 이미지를 오른쪽으로 이동
				if (x + baseImage.getWidth() - 800 < 0) {
					x = getWidth() - 800; // 이미지가 화면을 벗어나면 다시 오른쪽 끝으로 이동
				}
				repaint();
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void toggleThreadPause() {
		threadPaused = !threadPaused; // 스레드 일시 중지 상태 토글
	}

	public void startPrograme() {
		Runnable timeCheck = new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				while (true) {
					if (!TestFrame.isSleeping) {
						try {
							TimeUnit.MILLISECONDS.sleep(100);
							TestFrame.frame.repaint();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (!isPlaying) { // 프레임 이동하는 사이에는 시간 흐름이 2배 느려짐
							try {
								TimeUnit.MILLISECONDS.sleep(100);
								TestFrame.frame.repaint();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						TestFrame.time++;
						TestFrame.playtime++;
						// 시계 침 재배치 : 하루의 20분의 1이 경과하면 시계 움직임
						ClockPanel.angle = (9 * (TestFrame.time / 150));
						if (ClockPanel.angle >= 180) {
							ClockPanel.angle = 0;
						}
						TestFrame.frame.repaint();
					} else {
						try {
							TimeUnit.MILLISECONDS.sleep(400);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (TestFrame.time >= 3000) { // 테스트땐 임시로 시간 조절, 300초 기준임
						// 날짜 하루 경과
						TestFrame.isSleeping = true;
						TestFrame.character.getFunction().dark();
					}
					if (TestFrame.isSleeping && TestFrame.isWorking) {
						TestFrame.isWorking = false;
						TestFrame.time = 0;
						ClockPanel.angle = 0;
						// 집으로 보내버리기
						TestFrame.changeFrame(2);
						TestFrame.character.getCharacter().setLocation(520, 240);
						// 농장 식물 업데이트
						TestFrame.character.getFunction().plantGrow();
						TestFrame.day++;
						System.out.println(TestFrame.day + "일 경과");
						// 축사 업데이트
						for (Animal elem : TestFrame.aniImpl.getAllMyAnimal()) {
							TestFrame.aniImpl.UpdateAnimalGrowthPeriod(elem);
							TestFrame.aniImpl.createProducts(elem);
						}
						TestFrame.aniImpl.feedHay();
					}
				}
			}
		};
		Runnable createFrame = new Runnable() {
			@Override
			public void run() {
				UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
				UIManager.getDefaults().put("TabbedPane.tabsOverlapBorder", false);

				TestFrame.frame = new TestFrame();
				TestFrame.frame.setBackground(new Color(0, 0, 0, 0));
				// 오프닝 작업(로그인이라던가 게임 시작 전화면, 끝나면 메인창으로 이동 : changeFrame(1)
				TestFrame.changeFrame(1);
				TestFrame.frame.setVisible(true);
			}
		};
		Thread thread1 = new Thread(timeCheck);
		Thread thread2 = new Thread(createFrame);

		thread1.start();
		thread2.start();

	}
}
