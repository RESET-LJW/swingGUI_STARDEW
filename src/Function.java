import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.LabelUI;
import javax.swing.text.StyledEditorKit.BoldAction;

public class Function {
	private Character character;
//	private Timer t;
	private int timerT = 0;
	private int inputKeys;
	private List<JLabel> lblImageList;
	private Block furnitureInfo = null;
	private JPanel moveFurniture = new JPanel();
	private JLabel targetFurniture;
	private KeyListener l;;
	private int newX;
	private int newY;
	public static int fieldID = 1;
	public static Map<String, JLabel> plantImageMap;
	private List<JLabel> guid = new ArrayList<>();
	private FurnituresImpl FurImpl = new FurnituresImpl();

	public Map<String, ImageIcon> imgMap = new HashMap<>();
	private List<JPanel> furnitureList = new ArrayList<>();
	private JPanel test = new JPanel();
	private JLabel homelbl = new JLabel();
	protected List<JLabel> furImageList = new ArrayList<JLabel>();
	private List<Block> eachBlockFurList = new ArrayList<Block>();

	private static AnimalImpl aniImpl = TestFrame.aniImpl;

	public static Thread sleepThread;
	public static Runnable sleepRunnable;

	public List<JLabel> getLblImageList() {
		return lblImageList;
	}

	public void setLblImageList(List<JLabel> lblImageList) {
		this.lblImageList = lblImageList;
	}

	public Function(Character character) {
		super();
		this.character = character;
		this.lblImageList = new ArrayList<>();
		Function.plantImageMap = new HashMap<>();
//		sleepRunnable = new Runnable() {
//			@Override
//			public void run() {
//				TestFrame.pnlBlack.setVisible(true);
//				TestFrame.pnlBlack.setBackground(new Color(0, 0, 0, 0));
//				// TODO Auto-generated method stub
//				t = new Timer(120, new ActionListener() {
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						time++;
//						TestFrame.pnlBlack.setBackground(new Color(50, 50, 50, time));
//						if (time == 50) {
//							TestFrame.isSleeping = false;
//							time = 0;
//							t.stop();
//							TestFrame.pnlBlack.setVisible(false);
//							TestFrame.frame.setEnabled(true);
//							TestFrame.frame.requestFocus();
//							return;
//						}
//
//					}
//				});
//				t.start();
//			}
//		};
//		sleepThread = new Thread(sleepRunnable);
	}

	// 가장 가까운 블럭과 상호작용하는 메소드
	public void interact() {
		try {
			Block nearestBlock = findNearestBlock(this.character.getCharacter());
			nearestBlock.setInteracting(false);
			if (nearestBlock != null) {
				nearestBlock.setInteracting(true);
				interactAction(nearestBlock);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	// 블록 생성 메서드
	public void createBlock(Block block, List<Block> blockList) {
		for (int i = 0; i < block.getSizeX(); i++) {
			for (int j = 0; j < block.getSizeY(); j++) {
				Block b = new Block();
				b.setInfo(block.getInfo());
				b.setX(block.getX() + i * 40);
				b.setY(block.getY() + j * 40);

				if (block.getInfo().contains("밭")) { // 밭에 넘버링(식물 관리하려고)
					b.setInfo(block.getInfo() + fieldID);
					fieldID++;
				}

				addBlockList(b, blockList);
			}
		}
		setImage(block);
	}

	// 블록을 리스트에 등록하는 메소드
	public void addBlockList(Block b, List<Block> blockList) {
		if (b.getInfo().contains("집")) {
			b.setBounds(b.getX(), b.getY(), 40, 40);
			blockList.add(b);
			TestFrame.homePnl.add(b, JLayeredPane.MODAL_LAYER);
		} else {
			b.setBounds(b.getX(), b.getY(), 40, 40);
			blockList.add(b);
			character.getFrame().add(b);
		}
	}

	//////////////////
	// 가구 1*1 블럭 생성
	public void createFurnitureBlock(Furniture fur) {
		for (int i = 0; i < fur.getBlock().getSizeX(); i++) {
			for (int j = 0; j < fur.getBlock().getSizeY(); j++) {
				Block b = new Block();
				b.setInfo(fur.getBlock().getInfo());
				b.setX(fur.getBlock().getX() + i * 40);
				b.setY(fur.getBlock().getY() + j * 40);
				eachBlockFurList.add(b);
				TestFrame.blocks.add(b);
			}
		}
		createBigFurnitureImage(fur);
	}

	// 가구 이미지 생성
	public JLabel createBigFurnitureImage(Furniture fur) {
		JLabel lblImage = new JLabel();
		lblImage.setBounds(fur.getBlock().getX(), fur.getBlock().getY(), fur.getBlock().getSizeX() * 40,
				fur.getBlock().getSizeY() * 40);
		
		lblImage.setIcon(imgMap().get(fur.getName()));
		lblImage.setName(fur.getBlock().getInfo());
		if (lblImage.getName().contains("벽지")) {
			TestFrame.homePnl.add(lblImage, JLayeredPane.MODAL_LAYER);
		} else {
			TestFrame.homePnl.add(lblImage, JLayeredPane.POPUP_LAYER);
		}
		furImageList.add(lblImage);

		return lblImage;
	}

	// 가구1*1블럭 삭제, 이미지 삭제
	public void deleteFurnitureBlock(Furniture fur) {
		String info = fur.getName();
		int size = TestFrame.blocks.size();
		for (int i = size - 1; i >= 0; i--) {
			Block block = TestFrame.blocks.get(i);
			if (block.getInfo().equals(info)) {
				TestFrame.blocks.remove(block);
				eachBlockFurList.remove(block);
			}
		}
		for (JLabel lbl : furImageList) {
			if (lbl.getName().equals(fur.getBlock().getInfo())) {
//				furImageList.remove(lbl); // 굳이 제거를 해야할까? 최적화?
				TestFrame.homePnl.remove(lbl);
			}
		}
	}

	public static JLabel createBarnItemsImage(Block item, String product) {
		JLabel lblImage = new JLabel();
		lblImage.setBounds(item.getX(), item.getY(), 40 * item.getSizeX(), 40 * item.getSizeY());
		lblImage.setIcon(new ImageIcon("resource/" + product + ".png"));
		lblImage.setVisible(true);
		TestFrame.barnPnl.add(lblImage, JLayeredPane.PALETTE_LAYER);
		aniImpl.getAllBarnImg().add(lblImage);

		return lblImage;
	}

	public static Map<String, ImageIcon> imgMap() {
		Map<String, ImageIcon> imgMap = new HashMap<>();
		// 침대 ImageIcon
		ImageIcon field_empty = new ImageIcon("resource/farm_field_empty.png");
		ImageIcon field_hoe = new ImageIcon("resource/farm_field_hoe.png");
		ImageIcon field_water = new ImageIcon("resource/farm_field_water.png");
		ImageIcon fence1 = new ImageIcon("resource/farm_fence1.png");
		ImageIcon fence2 = new ImageIcon("resource/farm_fence2.png");
		ImageIcon fence3 = new ImageIcon("resource/farm_fence3.png");
		ImageIcon farm_exit_right = new ImageIcon("resource/farm_exit_right.png");
		ImageIcon farm_exit_down = new ImageIcon("resource/farm_exit_down.png");
		ImageIcon wheat_stage1 = new ImageIcon("resource/Wheat_Stage_1.png");
		ImageIcon wheat_stage2 = new ImageIcon("resource/Wheat_Stage_2.png");
		ImageIcon wheat_stage3 = new ImageIcon("resource/Wheat_Stage_3.png");
		ImageIcon woodfence_width = new ImageIcon("resource/wood_fence_width.png");
		ImageIcon woodfence_height = new ImageIcon("resource/wood_fence_height.png");
		ImageIcon woodfence_upleft = new ImageIcon("resource/wood_fence_upleft.png");
		ImageIcon woodfence_upright = new ImageIcon("resource/wood_fence_upright.png");
		ImageIcon woodfence_downleft = new ImageIcon("resource/wood_fence_downleft.png");
		ImageIcon woodfence_downright = new ImageIcon("resource/wood_fence_downright.png");
		ImageIcon woodfence_alone = new ImageIcon("resource/wood_fence_alone.png");

		ImageIcon leftWall = new ImageIcon("resource/집_왼쪽벽.png");
		ImageIcon rightWall = new ImageIcon("resource/집_오른쪽벽.png");
		ImageIcon upWall = new ImageIcon("resource/집_위쪽벽.png");
		ImageIcon downWall = new ImageIcon("resource/집_아래쪽벽.png");
		ImageIcon store = new ImageIcon("resource/상점.png");
		ImageIcon window = new ImageIcon("resource/집_창문.png");
//		ImageIcon bed = new ImageIcon("resource/집_침대.png");
		ImageIcon house_3 = new ImageIcon("resource/house_3.png");
		ImageIcon farm_3 = new ImageIcon("resource/farm_3.png");
		ImageIcon barn_3 = new ImageIcon("resource/barn_3.png");

		ImageIcon wallPaper = new ImageIcon("resource/집_벽지.png");
		ImageIcon wallPaper2 = new ImageIcon("resource/집_벽지2.png");
		ImageIcon floor = new ImageIcon("resource/집_바닥.png");
		ImageIcon floor2 =new ImageIcon("resource/집_바닥2.png");
		ImageIcon bed = new ImageIcon("resource/집_침대.png");
		ImageIcon pot = new ImageIcon("resource/집_화분.png");
		ImageIcon Fireplace = new ImageIcon("resource/잡_난로.png");
		ImageIcon firewood = new ImageIcon("resource/집_장작.png");
		ImageIcon bearStatue = new ImageIcon("resource/집_곰석상.png");
		ImageIcon sofa = new ImageIcon("resource/집_쇼파.png");
		ImageIcon sofa2 = new ImageIcon("resource/집_쇼파2.png");
		ImageIcon tabletop = new ImageIcon("resource/집_탁상.png");
		ImageIcon light = new ImageIcon("resource/집_등.png");
		ImageIcon axe= new ImageIcon("resource/집_도끼.png");
		ImageIcon sofaright= new ImageIcon("resource/집_쇼파우.png");
		ImageIcon sofaleft= new ImageIcon("resource/집_쇼파좌.png");
		ImageIcon bookshelf= new ImageIcon("resource/집_책꽂이.png");
		ImageIcon pot2= new ImageIcon("resource/집_화분2.png");
		
		ImageIcon water = new ImageIcon("resource/water.png");
		ImageIcon water_deep = new ImageIcon("resource/water_deep.png");

		// imgMap.put("침대",침대이미지아이콘이름);
		imgMap.put("밭", field_empty);
		imgMap.put("괭이질된밭", field_hoe);
		imgMap.put("물준밭", field_water);
		imgMap.put("울타리1", fence1);
		imgMap.put("울타리2", fence2);
		imgMap.put("울타리3", fence3);
		imgMap.put("농장_오른쪽출구", farm_exit_right);
		imgMap.put("농장_아래출구", farm_exit_down);
		imgMap.put("밀_1", wheat_stage1);
		imgMap.put("밀_2", wheat_stage2);
		imgMap.put("밀_3", wheat_stage3);
		imgMap.put("나무울타리_가로", woodfence_width);
		imgMap.put("나무울타리_세로", woodfence_height);
		imgMap.put("나무울타리_위_왼쪽", woodfence_upleft);
		imgMap.put("나무울타리_위_오른쪽", woodfence_upright);
		imgMap.put("나무울타리_아래_왼쪽", woodfence_downleft);
		imgMap.put("나무울타리_아래_오른쪽", woodfence_downright);
		imgMap.put("나무울타리_세로끝부분", woodfence_alone);

//		imgMap.put("집_침대", bed);
		imgMap.put("상점", store);
		imgMap.put("집_창문", window);
		imgMap.put("집_왼쪽벽", leftWall);
		imgMap.put("집_위쪽벽", upWall);
		imgMap.put("집_오른쪽벽", rightWall);
		imgMap.put("집_아래쪽벽", downWall);

		imgMap.put("집_벽지", wallPaper);
		imgMap.put("집_벽지2", wallPaper2);
		imgMap.put("집_바닥", floor);
		imgMap.put("집_바닥2", floor2);
		imgMap.put("집_침대", bed);
		imgMap.put("집_화분", pot);
		imgMap.put("집_난로", Fireplace);
		imgMap.put("집_장작", firewood);
		imgMap.put("집_곰석상", bearStatue);
		imgMap.put("집_쇼파", sofa);
		imgMap.put("집_쇼파2", sofa2);
		imgMap.put("집_탁상", tabletop);
		imgMap.put("집_등", light);
		imgMap.put("집_도끼", axe);
		imgMap.put("집_쇼파우", sofaright);
		imgMap.put("집_쇼파좌", sofaleft);
		imgMap.put("집_책꽂이", bookshelf);
		imgMap.put("집_화분2", pot2);

		imgMap.put("집", house_3);
		imgMap.put("농장", farm_3);
		imgMap.put("축사", barn_3);

		imgMap.put("얕은물", water);
		imgMap.put("깊은물", water_deep);

		return imgMap;
	}

	// 블록에 이미지 아이콘을 설정하는 메소드
	public void setImage(Block block) {
		String info = block.getInfo();
		if (info.contains("밭")) {
			info = "밭";
		}
		if (info.contains("나무울타리")) {
			info = "나무울타리";
		}

		// 임시용
		if (info.contains("메인_")) {
			info = info.substring(3);
			System.out.println(info);
		}
		Map<String, ImageIcon> imgMap = imgMap();
		switch (info) {
		case "먹이통":
			setBigImage(block, imgMap.get(info));
			break;
		case "농장_오른쪽출구":
			setBigImage(block, imgMap.get(info));
			break;
		case "농장_아래출구":
			setBigImage(block, imgMap.get(info));
			break;
		case "밭":
			setLoopImage(block, imgMap.get(info));
			break;
		case "울타리":
			setRandomImage(block);
			break;
		case "나무울타리":
			setLoopBigImage(block, imgMap);
			break;
		case "집_위쪽벽":
			setBigImage(block, imgMap.get(info));
			break;
		case "집_오른쪽벽":
			setBigImage(block, imgMap.get(info));
			break;
		case "집_왼쪽벽":
			setBigImage(block, imgMap.get(info));
			break;
		case "집_아래쪽벽":
			setBigImage(block, imgMap.get(info));
			break;
		case "상점":
			setBigImage(block, imgMap.get(info));
			break;
		case "집_창문":
			setBigImage(block, imgMap.get(info)).setBounds(600, 60, 40, 40);
			break;
//		case "집_침대":
//			setBigImage(block, imgMap.get(info));
//			break;
		case "집":
			setBigImage(block, imgMap.get(info));
			break;
		case "농장":
			setBigImage(block, imgMap.get(info));
			break;
		case "축사":
			setBigImage(block, imgMap.get(info));
			break;
		case "얕은물":
			setLoopImage(block, imgMap.get(info));
			break;
		case "깊은물":
			setLoopImage(block, imgMap.get(info));
			break;
		default:
			// 정의되지않은블럭, error처리
			break;
		}
	}

	public void setRandomImage(Block block) {
		Random rand = new Random();
		int randomInt = -1;
		Map<String, ImageIcon> imgMap = imgMap();
		String info = block.getInfo();
		for (Block b : TestFrame.blocks) {
			if (b.getInfo().equals(info)) {
				randomInt = rand.nextInt(3) + 1;
				b.setIcon(imgMap.get(info + randomInt));
			}
		}
	}

	public void setLoopImage(Block block, ImageIcon img) {
		for (Block b : TestFrame.blocks) {
			if (b.getInfo().contains(block.getInfo())) {
				b.setIcon(img);
			}
			if (b.getInfo().contains("밭")) {
				TestFrame.farmPnl.add(b, JLayeredPane.POPUP_LAYER);
			}
			if (b.getInfo().equals("얕은물") || b.getInfo().equals("깊은물")) {
				b.setVisible(false);
				JLabel temp = new JLabel();
				temp.setBounds(b.getX(), b.getY(), 40, 40);
				temp.setIcon(img);
				lblImageList.add(temp);
				TestFrame.fishingPnl.add(temp, JLayeredPane.PALETTE_LAYER);
			}
		}
	}

	public JLabel setBigImage(Block block, ImageIcon img) {
		JLabel lblImage = new JLabel();
		lblImage.setBounds(block.getX(), block.getY(), block.getSizeX() * 40, block.getSizeY() * 40);
		lblImage.setIcon(img);
		character.getFrame().add(lblImage);
		lblImageList.add(lblImage);

		return lblImage;
	}

	public void setLoopBigImage(Block block, Map<String, ImageIcon> imgMap) {
		for (int i = TestFrame.blocks.size() - 1; i >= 0; i--) {
			Block b = TestFrame.blocks.get(i);
			if (b.getInfo().equals(block.getInfo())) {
				b.setBackground(Color.black);
				b.setOpaque(true);
				JLabel lblImage = new JLabel();
				lblImage.setBounds(b.getX(), b.getY() - 40, 40, 80);
				lblImage.setIcon(imgMap.get(b.getInfo()));
				lblImageList.add(lblImage);
				if (b.getInfo().equals("나무울타리_세로끝부분") || b.getInfo().contains("위")) {
					character.getFrame().farmPnl.add(lblImage, JLayeredPane.PALETTE_LAYER);
				} else if (b.getInfo().equals("나무울타리_세로")) {
					character.getFrame().farmPnl.add(lblImage, JLayeredPane.MODAL_LAYER);
				} else {
					character.getFrame().farmPnl.add(lblImage, JLayeredPane.POPUP_LAYER);
				}
			}
		}
	}

	public void replaceImage(Block block) {
		String info = block.getInfo();
		String input = "";
		if (info.contains("밭")) {
			input = info.replaceAll("[0-9]", "");
		} else {
			input = info;
		}
		Map<String, ImageIcon> imgMap = imgMap();
		switch (input) {
		case "밭":
			block.setIcon(imgMap.get(input));
			break;
		case "괭이질된밭":
			block.setIcon(imgMap.get(input));
			break;
		case "물준밭":
			block.setIcon(imgMap.get(input));
			break;
		default:
			// 정의되지않은블럭, error처리
			break;
		}
	}

	public void plantInfoChange(Block b) {
		int ID = Integer.valueOf(b.getInfo().replaceAll("[^0-9]", ""));
		if (TestFrame.plantMap.get(ID).equals("빈땅")) {
			TestFrame.plantMap.put(ID, "밀_1");
		} else if (TestFrame.plantMap.get(ID).equals("밀_3")) {
			b.setInfo("밭" + ID);
			replaceImage(b);
			TestFrame.plantMap.put(ID, "빈땅");
		} else if (TestFrame.plantMap.get(ID).equals("밀_2")) {
			b.setInfo("괭이질된밭" + ID);
			TestFrame.plantMap.put(ID, "밀_3");
		} else if (TestFrame.plantMap.get(ID).contains("밀")) {
			int growth = Integer.valueOf(TestFrame.plantMap.get(ID).replaceAll("[^0-9]", ""));
			growth += 1;
			TestFrame.plantMap.put(ID, "밀_" + growth);
		}
		randSetting(b);
		plantSetting(b);
		TestFrame.farmPnl.repaint();
	}

	public void randSetting(Block b) {
		b.setVisible(false);
		Map<String, ImageIcon> imgMap = imgMap();
		ImageIcon field_empty = imgMap.get("밭");
		ImageIcon field_hoe = imgMap.get("괭이질된밭");
		ImageIcon field_water = imgMap.get("물준밭");

		int ID = Integer.valueOf(b.getInfo().replaceAll("[^0-9]", ""));
		JLabel bgImage = null;
		if (plantImageMap.get(ID + "배경") != null) {
			bgImage = plantImageMap.get(ID + "배경");
		} else {
			bgImage = new JLabel();
			bgImage.setBounds(b.getX(), b.getY(), 40, 40);
			plantImageMap.put(ID + "배경", bgImage);
			TestFrame.farmPnl.add(bgImage, JLayeredPane.PALETTE_LAYER);
		}
		if (b.getInfo().contains("괭이질된밭")) {
			bgImage.setIcon(field_hoe);
		} else if (b.getInfo().contains("물준밭")) {
			bgImage.setIcon(field_water);
		} else {
			bgImage.setIcon(field_empty);
		}
	}

	public void plantSetting(Block b) {
		Map<String, ImageIcon> imgMap = imgMap();
		ImageIcon wheat_1 = imgMap.get("밀_1");
		ImageIcon wheat_2 = imgMap.get("밀_2");
		ImageIcon wheat_3 = imgMap.get("밀_3");

		int ID = Integer.valueOf(b.getInfo().replaceAll("[^0-9]", ""));

		JLabel plantImage = null;
		if (plantImageMap.get(ID + "식물") != null) {
			plantImage = plantImageMap.get(ID + "식물");
		} else {
			plantImage = new JLabel();
			plantImageMap.put(ID + "식물", plantImage);
			if (ID % 2 == 0) {
				TestFrame.farmPnl.add(plantImage, JLayeredPane.DRAG_LAYER);
			} else {
				TestFrame.farmPnl.add(plantImage, JLayeredPane.POPUP_LAYER);
			}
		}
		int growth = -1;
		if (TestFrame.plantMap.get(ID).contains("밀")) {
			growth = Integer.valueOf(TestFrame.plantMap.get(ID).replaceAll("[^0-9]", ""));
		}
		plantImage.setBounds(b.getX(), b.getY() - 40, 40, 80);
		switch (growth) { // 성장 정도 체크
		case 1:
			plantImage.setIcon(wheat_1);
			break;
		case 2:
			plantImage.setIcon(wheat_2);
			break;
		case 3:
			plantImage.setIcon(wheat_3);
			break;
		default:
			plantImage.setBounds(b.getX(), b.getY(), 0, 0);
			break;
		}
		if (TestFrame.plantMap.get(ID).equals("빈땅")) {
			plantImage.setVisible(false);
		} else if (TestFrame.plantMap.get(ID).contains("밀")) {
			plantImage.setVisible(true);
		}
	}

	public void plantGrow() {
		List<Block> bList = TestFrame.blockList.get(2);
		for (Block b : bList) {
			if (b.getInfo().contains("물준밭")) {
				int ID = Integer.valueOf(b.getInfo().replaceAll("[^0-9]", ""));
				b.setInfo("괭이질된밭" + ID);
				if (!TestFrame.plantMap.get(ID).equals("빈땅")) {
					plantInfoChange(b);
				} else {
					randSetting(b);
				}
				TestFrame.frame.repaint();
			}
		}
	}

	public void fishing() {
		Random rand = new Random();
		int randNum = rand.nextInt(100);
		JLabel chara = TestFrame.character.getCharacter();
		JLabel resultLbl = new JLabel();
		TestFrame.fishingPnl.add(resultLbl, JLayeredPane.MODAL_LAYER);

		ImageIcon img1 = new ImageIcon("resource/water_wave1.png");
		ImageIcon img2 = new ImageIcon("resource/water_wave2.png");
		ImageIcon img3 = new ImageIcon("resource/water_wave3.png");

		Timer t1 = new Timer(100, new ActionListener() {
			int i = 1;

			@Override
			public void actionPerformed(ActionEvent e) {
				i++;
				if (i == 45) {
					resultLbl.setBounds(chara.getX(), chara.getY() + 220, 40, 40);
					resultLbl.setIcon(img2); // 물웅덩이?이미지
					resultLbl.setVisible(true);
				} else if (i >= 50) {
					resultLbl.setLocation(resultLbl.getX(), resultLbl.getY() - 3);
					switch (i % 3) {
					case 0:
						resultLbl.setIcon(img1); // 물웅덩이?이미지
						break;
					case 1:
						resultLbl.setIcon(img2); // 물웅덩이?이미지
						break;
					case 2:
						resultLbl.setIcon(img3); // 물웅덩이?이미지
						break;
					}
				}
				if (resultLbl.getY() == chara.getY() + 130) {
					resultLbl.setVisible(false);
					((Timer) e.getSource()).stop();
					if (randNum >= 90) {
						// 희귀물고기
						System.out.println("1");
					} else if (randNum >= 20) {
						// 일반물고기
						System.out.println("2");
					} else {
						// 실패
						System.out.println("3");
					}
				}
			}
		});
		t1.start();
	}

	// 블록 삭제 메서드
	public void deleteBlock() {
		try {
			Block nearestBlock = findNearestBlock(this.character.getCharacter());
			nearestBlock.setInteracting(false);

			if (nearestBlock != null) {
				nearestBlock.setInteracting(true);
				TestFrame.blocks.remove(nearestBlock);
				character.getFrame().remove(nearestBlock);
			}
		} catch (NullPointerException e) {
		}
	}

//	// 캐릭터 멈춤
//	public void dark(Block block) {
//		TestFrame.frame.setEnabled(false);
//		String info = block.getInfo();
//		JPanel pnlBlack = new JPanel();
//		TestFrame.frame.add(pnlBlack);
//		pnlBlack.setBounds(0, 0, 800, 600);
//		if (info.equals("집_침대")) {
//			t = new Timer(120, new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					time++;
//					pnlBlack.setBackground(new Color(50, 50, 50, time));
//					if (time == 50) {
//						time = 0;
//						t.stop();
//						TestFrame.frame.remove(pnlBlack);
//						TestFrame.frame.setEnabled(true);
//						TestFrame.frame.requestFocus();
//						TestFrame.frame.repaint();
//						return;
//					}

	// 캐릭터 멈춤
	public void dark() {
		TestFrame.frame.setEnabled(false);
		TestFrame.isSleeping = true;
		TestFrame.isWorking = true;
		TestFrame.time = 50000;

		sleepRunnable = new Runnable() {
			@Override
			public void run() {
				TestFrame.pnlBlack.setVisible(true);
				TestFrame.pnlBlack.setBackground(new Color(0, 0, 0, 0));
				// TODO Auto-generated method stub
				Timer t = new Timer(120, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println(timerT);
						timerT++;
						TestFrame.pnlBlack.setBackground(new Color(50, 50, 50, timerT));
						if (timerT == 50) {
							((Timer) e.getSource()).stop();
							TestFrame.isSleeping = false;
							timerT = 0;
							TestFrame.pnlBlack.setVisible(false);
							TestFrame.frame.setEnabled(true);
							TestFrame.frame.requestFocus();
							System.out.println("작동끝");
						}

					}
				});
				t.start();
			}
		};
		sleepThread = new Thread(sleepRunnable);

		sleepThread.start();
//		TestFrame.frame.setEnabled(false);
//		JPanel pnlBlack = new JPanel();
//		TestFrame.frame.add(pnlBlack);
//		pnlBlack.setBounds(0, 0, 800, 600);
//		t = new Timer(120, new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				time++;
//				pnlBlack.setBackground(new Color(50, 50, 50, time));
//				if (time == 50) {
//					time = 0;
//					t.stop();
//					TestFrame.frame.remove(pnlBlack);
//					TestFrame.frame.setEnabled(true);
//					TestFrame.frame.requestFocus();
//					return;
//				}
//
//			}
//		});
//		t.start();
	}

	public void interactAction(Block block) {
		FurnituresImpl fur = new FurnituresImpl();
		String info = block.getInfo();
		String input = "";
		String ID = "";
		if (info.contains("출구")) {
			input += "출구";
		} else if (info.contains("밭")) {
			String main = block.getInfo().replaceAll("[0-9]", "");
			ID = block.getInfo().replaceAll("[^0-9]", "");
			input += main;
		} else {
			input += info;
		}

		switch (input) {
		case "집_침대":
			int choice = JOptionPane.showConfirmDialog(null, "주무시겠습니까?", "", JOptionPane.YES_NO_OPTION);
			if (choice == JOptionPane.YES_OPTION) {
				// 사용자가 '예'를 선택한 경우에 대한 처리
//				dark();
				TestFrame.time = 3000;
			} else {
				// 사용자가 '아니오'를 선택한 경우에 대한 처리
			}
			break;
		case "집_화분":
			fur.moveFurniture(block, fur.buyFurnitureList);
			break;
		case "집_난로":
			fur.moveFurniture(block, fur.buyFurnitureList);
			break;
		case "집_등":
			fur.moveFurniture(block, fur.buyFurnitureList);
			break;
		case "집_쇼파":
			fur.moveFurniture(block, fur.buyFurnitureList);
			break;
		case "집_쇼파2":
			fur.moveFurniture(block, fur.buyFurnitureList);
			break;
		case "집_쇼파우":
			fur.moveFurniture(block, fur.buyFurnitureList);
			break;
		case "집_쇼파좌":
			fur.moveFurniture(block, fur.buyFurnitureList);
			break;
		case "집_책꽂이":
			fur.moveFurniture(block, fur.buyFurnitureList);
			break;
		case "집_탁상":
			fur.moveFurniture(block, fur.buyFurnitureList);
			break;
		case "집_화분2":
			fur.moveFurniture(block, fur.buyFurnitureList);
			break;
		case "집_장작":
			pickUpItem(block);
			break;
		case "집_도끼":
			pickUpItem(block);
			break;
		case "상점":
			if (TestFrame.character.getCharacter().isVisible()) {
				TestFrame.character.getCharacter().setVisible(false);
			} else {
				TestFrame.character.getCharacter().setVisible(true);
			}
			if (TestFrame.s.isVisible()) {
				TestFrame.s.setVisible(false);
				TestFrame.s.setFocusable(false);
			} else {
				TestFrame.s.setVisible(true);
				TestFrame.s.setFocusable(true);
			}

			break;
		case "밭":
			// 조건문필요 : 퀵슬롯 아이템
			block.setInfo("괭이질된밭" + ID);
			randSetting(block);
			break;
		case "괭이질된밭":
			if (TestFrame.plantMap.get(Integer.valueOf(ID)).equals("밀_3")) {
				plantInfoChange(block);
			} else {
				block.setInfo("물준밭" + ID);
				randSetting(block);
			}
			break;
		case "물준밭":
			// 조건문필요 : 퀵슬롯 아이템
			plantInfoChange(block);
			// 조건문필요 : 시간경과
			// block.setInfo("괭이질된밭");
			// replaceImage(block);
			break;
		case "건초없음":
			for (Block b : TestFrame.blocks) {
				if (b.getInfo().equals("건초없음")) {
					b.setInfo("건초있음");
					createBarnItemsImage(b, "hay");
				}
			}
//			createBarnItemsImage(TestFrame.barnblockList.get(20), "먹이줌3");
			break;
		// 테스트용 건초있음
//		case "건초있음":
//			block.setInfo("건초없음");
//			removeBarnItemImg(block);
//			boolean isEmpty2 = true;
//			for (JLabel elem : aniImpl.getAllBarnImg()) {
//				if (elem.getX() >= 160 && elem.getX() <= 560 && elem.getY() == 160) {
//					isEmpty2 = false;
//					break;
//				}
//			}
//			if (!isEmpty2) {
////				createBarnItemsImage(TestFrame.barnblockList.get(20), "먹이줌3");
//			} else {
////				removeBarnItemImg(TestFrame.barnblockList.get(20));
//			}
//			break;
		case "우유":
			removeBarnItemImgWithBlock(block);
			// 인벤토리에 우유추가
			break;

		case "출구":
			this.character.getFrame().changeFrame(1);
			TestFrame.isPlaying = false;
			for (JPanel j : furnitureList) {
				TestFrame.frame.remove(furnitureList.get((furnitureList.indexOf(j))));
			}
			TestFrame.frame.remove(homelbl);
			break;
		case "메인_집":
			this.character.getFrame().changeFrame(2);
			for (Furniture f : FurnituresImpl.buyFurnitureList) {
				createFurnitureBlock(f);
			}
			TestFrame.isPlaying = true;
			break;
		case "메인_농장":
			this.character.getFrame().changeFrame(3);
			TestFrame.isPlaying = true;
			break;
		case "메인_축사":
			this.character.getFrame().changeFrame(4);
			TestFrame.isPlaying = true;
			break;
		case "절벽":
			fishing();
			break;
		default:
			// 정의되지 않은 가구, 동작x
			break;
		}
	}

	// 한칸짜리 아이템 줍기
	public void pickUpItem(Block block) {
		// 홈 블럭, 이미지 찾고 제거
		for (Furniture f : FurnituresImpl.buyFurnitureList) {
			if (f.getBlock().getInfo().equals(block.getInfo())) {
				deleteFurnitureBlock(f);
			}
		}
		// 해당 이미지 인벤토리에 등록
		for (JLabel lbl : furImageList) {
			if (lbl.getName().equals(block.getInfo())) {
				TestFrame.in.addInventory(block, lbl);
				break;
			}
		}

	}

	public void removeBarnItemImgWithBlock(Block block) {
		boolean existImg = false;
		boolean existBlock = false;

		for (JLabel img : aniImpl.getAllBarnImg()) {
			if (block.getX() == img.getX() && block.getY() == img.getY()) {
				existImg = true;
				TestFrame.barnPnl.remove(img);
				break;
			}
		}
		for (Block elem : TestFrame.blocks) {
			if (block.getX() == elem.getX() && block.getY() == elem.getY()) {
				existBlock = true;
				TestFrame.blocks.remove(elem);
				break;
			}
		}
		if (existImg && existBlock) {
			TestFrame.barnPnl.revalidate();
			TestFrame.barnPnl.repaint();
		}
	}

	public void removeBarnItemImg(Block block) {
		for (JLabel img : aniImpl.getAllBarnImg()) {
			if (block.getX() == img.getX() && block.getY() == img.getY()) {
				TestFrame.barnPnl.remove(img);
			}
		}
	}

	// 가장 가까운 블럭을 찾는 메소드
	public Block findNearestBlock(JLabel pnl) {
		Block nearestBlock = null;
		int charX = pnl.getX();
		int charY = pnl.getY();
		for (Block block : TestFrame.blocks) {
			int blockX = block.getX();
			int blockY = block.getY();
			int distanceX = (charX - blockX);
			int distanceY = (charY - blockY);
			int inputKeys = character.getInputKeys();
			if (inputKeys == 37 && distanceX == 40 && distanceY == -40) {
				nearestBlock = block;
			} else if (inputKeys == 38 && distanceX == 0 && distanceY == 0) {
				nearestBlock = block;
			} else if (inputKeys == 39 && distanceX == -40 && distanceY == -40) {
				nearestBlock = block;
			} else if (inputKeys == 40 && distanceX == 0 && distanceY == -80) {
				nearestBlock = block;
			}
		}
		return nearestBlock;
	}

	// 이동 가능여부를 따지는 메소드
	public boolean isValidMove(int newX, int newY) {
		for (Block block : TestFrame.blocks) {
			if (block.getX() == newX && block.getY() == newY + 40) {
				block.setBackground(Color.blue);
				System.out.println(block.getInfo());
				return false;
			}
		}
		return true;
	}
}