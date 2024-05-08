import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class FurnituresImpl {
	protected static List<Furniture> buyFurnitureList = new ArrayList<Furniture>();
	protected static List<Furniture> buyWallPaperList = new ArrayList<Furniture>();
	private static KeyAdapter l;
	private static Block furnitureInfo = new Block();

	public FurnituresImpl() {
		super();
	}

	// 리스트에 가구 객체 추가, 내 구매 모록에 추가
	public static void addFurniture(Furniture fur) {
		buyFurnitureList.add(fur);
	}

	// 리스트에서 가구 객체 삭제, 내 구매물품 삭제에 사용 가능성 있음
	public void deleteFurniture(Furniture fur) {
		for (Furniture f : buyFurnitureList) {
			if (f.getName().equals(fur.getName())) {
				buyFurnitureList.remove(f);
			}
		}
	}

	// 구매한 리스트 또는 전체 가구에 접근
	public static Furniture findFurniture(Block block, List<Furniture> list) {
		for (Furniture f : list) {
			String fBlock = f.getBlock().getInfo();
			if (fBlock.equals(block.getInfo())) {
				return f;
			}
		}
		return null;
	}

	// 가구 상점에서 구매 금액 고려 안 함 소지금 출력방식 등 확인 필요 테스트필요, 리스트에 등록해야함 이미지 보관하는곳에서 이미지 담아야함
	public void BuyFurniture(String furnitureName) {
		for (Furniture fur : FurnitureTotal.furnitureTotalList) {
			if (fur.getName().contains(furnitureName)) {
				// 구매 리스트에 추가 메소드
				addFurniture(fur);
				// 구매한 가구 바로 가구 배치
				moveFurniture(fur.getBlock(), buyFurnitureList);
			}
		}
	}

	// 이미지만 변경 벽지
	public static void changeFurniture(String wallPaper) {
		for (JLabel l : TestFrame.character.getFunction().furImageList) {
			if(l.getName().equals(wallPaper)) {
				l.setIcon(new ImageIcon("resource/" + wallPaper + ".png"));
			}
		}
	}

	// 장판
	public static void changeFloor(String floor) {
		Addonhome.bgLbl.setIcon(new ImageIcon("resource/" + floor + ".png"));
	}

	// 내가 구매한 가구에서 또는 구매하고 배치할떄 가구 이동
	public static void moveFurniture(Block block, List<Furniture> list) {
		// 캐릭터 키 리스너 제거
		TestFrame.frame.removeKeyListener(Character.l);
		// 이동 불가 위치기 빨간색 나타내는 라벨
		List<JLabel> guid = new ArrayList<>();
		// 가구이동 임시 가이드 패널
		JPanel moveFurniture = new JPanel();
		moveFurniture.setLayout(null);
		moveFurniture.setSize(800, 600);
		moveFurniture.setVisible(true);
		moveFurniture.setBackground(new Color(0, 0, 0, 0));

		// 목록에서 가져온 가구 저장
		Furniture furniture = findFurniture(block, list);

		// 가구이동 가이드 라벨
		JLabel targetFurniture = new JLabel();
		targetFurniture.setBounds(furniture.getBlock().getX(), furniture.getBlock().getY(),
				furniture.getBlock().getSizeX() * 40, furniture.getBlock().getSizeY() * 40);
		targetFurniture.setBackground(new Color(0, 255, 0, 150));
		targetFurniture.setOpaque(true);

		// 임시패널에 임시 라벨 붙이기
		moveFurniture.add(targetFurniture);

		// 임시 패널 메인 프레임에 추가, 포커서 이동, z축 수정
		TestFrame.frame.add(moveFurniture);
		// 캐릭터 비활성화
		TestFrame.character.getCharacter().setVisible(false);
		moveFurniture.requestFocus();
		moveFurniture.setFocusable(true);

		TestFrame.frame.setComponentZOrder(moveFurniture, 0);

		// 키 리스너
		l = new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				int newX = targetFurniture.getX();
				int newY = targetFurniture.getY();
				// 캐릭터 비활성화 패널 새로 그리기
				TestFrame.frame.repaint();
				moveFurniture.repaint();
//							
				// 임의로 범위 지정
				if (newX == 800 || newY == 600 || newX == -40 || newY == -40) {
					newX = 0;
					newY = 0;
				}
				// 왼쪽
				if (e.getKeyCode() == 37) {
					newX -= 40;
				}
				// 위쪽
				else if (e.getKeyCode() == 38) {
					newY -= 40;
				}
				// 오른쪽
				else if (e.getKeyCode() == 39) {
					newX += 40;
				}
				// 아래쪽
				else if (e.getKeyCode() == 40) {
					newY += 40;
				}
				// 현 위치에 가구 생성
				else if (e.getKeyCode() == 69) {
					// 겹치는게 없을시 가구 배치? 생성
					System.out.println("배치확인");
					if (guid.size() == 0) {
						TestFrame.character.getFrame().requestFocus();

						TestFrame.character.getFunction().deleteFurnitureBlock(furniture);
						TestFrame.character.getFunction().createFurnitureBlock(furniture);

						List<Furniture> reverseList = new ArrayList<Furniture>(FurnituresImpl.buyFurnitureList);
						Collections.reverse(reverseList);
						for (Furniture f : FurnituresImpl.buyFurnitureList) {
							TestFrame.character.getFunction().deleteFurnitureBlock(f);
						}
						for (Furniture f : reverseList) {
							TestFrame.character.getFunction().createFurnitureBlock(f);
						}
						moveFurniture.removeKeyListener(l);

						TestFrame.frame.repaint();
						moveFurniture.setVisible(false);
						TestFrame.character.getCharacter().setVisible(true);
					}
				}
				// 임시 가이드라벨 위치 이동
				targetFurniture.setLocation(newX, newY);
				//
				furniture.getBlock().setX(targetFurniture.getX());
				furniture.getBlock().setY(targetFurniture.getY());
				// 이동 불가 위치 표시
				for (JLabel l : guid) {
					moveFurniture.remove(l);
					moveFurniture.repaint();
				}
				guid.clear();
				// 이동 불가 위치 표시 블럭 생성
				for (int i = 0; i < furniture.getBlock().getSizeX(); i++) {
					for (int j = 0; j < furniture.getBlock().getSizeY(); j++) {
						Block b = new Block();
						b.setInfo(block.getInfo());
						b.setSize(40, 40);
						b.setX(furniture.getBlock().getX() + i * 40);
						b.setY(furniture.getBlock().getY() + j * 40);
						if (!TestFrame.character.getFunction().isValidMove(b.getX(), b.getY())) {
							b.setBorder(new LineBorder(Color.BLACK, 20)); // 제거
							guid.add(b);
							moveFurniture.add(b);
						}
					}
				}
			}

		};
		moveFurniture.addKeyListener(l);
		TestFrame.frame.addKeyListener(Character.l);
	}

}