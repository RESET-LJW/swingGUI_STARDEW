import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;

public class BlocksInfo {
	protected static List<Block> homeBlocks = new ArrayList<>();
	protected static List<Block> barnBlocks = new ArrayList<>();
	
	public List<Block> mainBlocks() {
		List<Block> mainBlocks = new ArrayList<>();

		Block mainHome = new Block(40, 0, "메인_집", 6, 5);
		Block mainFarm = new Block(280, 0, "메인_농장", 6, 5);
		Block mainBarn = new Block(520, 0, "메인_축사", 6, 5);

		mainBlocks.add(mainHome);
		mainBlocks.add(mainFarm);
		mainBlocks.add(mainBarn);

		Block empty_leftwall = new Block(0, 0, "투명벽", 1, 8);
		Block empty_rightwall = new Block(760, 0, "투명벽", 1, 8);

		Block empty_water = new Block(0, 320, "절벽", 20, 1);
		Block water = new Block(0, 360, "얕은물", 20, 4);
		Block water_deep = new Block(0, 520, "깊은물", 20, 2);

		mainBlocks.add(empty_leftwall);
		mainBlocks.add(empty_rightwall);
		mainBlocks.add(empty_water);
		mainBlocks.add(water);
		mainBlocks.add(water_deep);

		return mainBlocks;
	}

	public List<Block> homeBlocks() {
//		Block bed = new Block(520, 120, "집_침대", 2, 4);
		Block store = new Block(440, 320, "상점", 1, 1);
		Block exit = new Block(680, 560, "집_출구", 2, 1);
		Block leftWall = new Block(0, 0, "집_왼쪽벽", 1, 15);
		Block rightWall = new Block(760, 0, "집_오른쪽벽", 1, 15);
		Block upWall = new Block(40, 0, "집_위쪽벽", 18, 1);
		Block downWall1 = new Block(40, 560, "집_아래쪽벽", 16, 1);
		Block window = new Block(600, 80, "집_창문", 1, 1);
		
		for (Furniture fur : FurnituresImpl.buyFurnitureList) {
			for (Block block : homeBlocks) {
				if (fur != null && !(fur.getBlock().getInfo().contains(block.getInfo()))) {
					homeBlocks.add(fur.getBlock());
				}

			}
		}
		
//	    homeBlocks.add(bed);
		homeBlocks.add(exit);
		homeBlocks.add(store);
		homeBlocks.add(leftWall);
		homeBlocks.add(rightWall);
		homeBlocks.add(downWall1);
		homeBlocks.add(upWall);
		homeBlocks.add(window);

		return homeBlocks;
	}

	public List<Block> farmBlocks() {
		List<Block> farmBlocks = new ArrayList<>();
		Block upWall = new Block(40, 0, "나무울타리_가로", 13, 1);
		Block downWall = new Block(40, 560, "나무울타리_가로", 13, 1);
		Block leftWall = new Block(0, 40, "나무울타리_세로", 1, 13);
		Block rightWall1 = new Block(560, 40, "나무울타리_세로", 1, 5);
		Block rightWall2 = new Block(560, 400, "나무울타리_세로", 1, 4);
		Block rightWallend = new Block(560, 360, "나무울타리_세로끝부분", 1, 1);
		Block cornerUpLeft = new Block(0, 0, "나무울타리_위_왼쪽", 1, 1);
		Block cornerUpRight = new Block(560, 0, "나무울타리_위_오른쪽", 1, 1);
		Block cornerDownLeft = new Block(0, 560, "나무울타리_아래_왼쪽", 1, 1);
		Block cornerDownRight = new Block(560, 560, "나무울타리_아래_오른쪽", 1, 1);

		farmBlocks.add(upWall);
		farmBlocks.add(downWall);
		farmBlocks.add(leftWall);
		farmBlocks.add(rightWall1);
		farmBlocks.add(rightWall2);
		farmBlocks.add(rightWallend);
		farmBlocks.add(cornerUpLeft);
		farmBlocks.add(cornerUpRight);
		farmBlocks.add(cornerDownLeft);
		farmBlocks.add(cornerDownRight);
		Block invisibleWall1 = new Block(600, 160, "투명벽", 1, 1);
		Block invisibleWall2 = new Block(680, 160, "투명벽", 2, 1);
		Block invisibleWall3 = new Block(800, 0, "투명벽", 1, 15);
		Block fence1 = new Block(640, 160, "울타리", 1, 1);
		Block fence2 = new Block(760, 160, "울타리", 1, 1);
		Block downExit = new Block(600, 600, "농장_아래출구", 5, 1);
		farmBlocks.add(invisibleWall1);
		farmBlocks.add(invisibleWall2);
		farmBlocks.add(invisibleWall3);
		farmBlocks.add(fence1);
		farmBlocks.add(fence2);
		farmBlocks.add(downExit);

		int x = 0;
		int y = 0;
		for (int i = 1; i <= 16; i++) {
			switch (i) {
			case 1:
				x = 80;
				y = 80;
				break;
			case 2:
				x = 200;
				y = 80;
				break;
			case 3:
				x = 80;
				y = 200;
				break;
			case 4:
				x = 200;
				y = 200;
				break;
			case 5:
				x = 80;
				y = 320;
				break;
			case 6:
				x = 80;
				y = 440;
				break;
			case 7:
				x = 200;
				y = 320;
				break;
			case 8:
				x = 200;
				y = 440;
				break;
			case 9:
				x = 320;
				y = 80;
				break;
			case 10:
				x = 440;
				y = 80;
				break;
			case 11:
				x = 320;
				y = 200;
				break;
			case 12:
				x = 440;
				y = 200;
				break;
			case 13:
				x = 320;
				y = 320;
				break;
			case 14:
				x = 440;
				y = 320;
				break;
			case 15:
				x = 320;
				y = 440;
				break;
			case 16:
				x = 440;
				y = 440;
				break;
			}
			Block field = new Block(x, y, "밭", 2, 2);
			farmBlocks.add(field);
		}
		return farmBlocks;
	}

	public List<Block> barnBlocks() {

		Block upEmptySpace = new Block(0, 0, "공백", 20, 2);
		Block leftEmptySpace = new Block(0, 80, "공백", 1, 11);
		Block rightEmptySpace = new Block(760, 80, "공백", 1, 11);
		Block downEmptySpace1 = new Block(0, 520, "공백", 13, 1);
		Block downEmptySpace2 = new Block(600, 520, "공백", 1, 1);
		Block downEmptySpace3 = new Block(720, 520, "공백", 2, 1);
		Block downEmptySpace4 = new Block(0, 560, "공백", 20, 1);
		Block downEmptySpace5 = new Block(0, 480, "공백", 16, 1);
		Block downEmptySpace6 = new Block(720, 480, "공백", 1, 1);

		Block exit = new Block(640, 520, "축사_출구", 2, 1);
		

		Block wall = new Block(40, 80, "축사_벽", 18, 2);
		for (int i = 0; i < 6; i++) {
			int x = 80 + i * 80;
			int y = 120;
			Block window = new Block(x, y, "축사_창문", 1, 1);
			barnBlocks.add(window);
		}

		Block oddments = new Block(40, 160, "잡동사니", 3, 1);
		Block oddments2 = new Block(680, 160, "잡동사니", 2, 1);
		Block oddments3 = new Block(720, 360, "잡동사니", 1, 3);
		Block feedLongBox = new Block(160, 160, "건초없음", 10, 1);
		Block feedInputBox = new Block(640, 120, "먹이넣는통", 1, 2);
		Block barnStore = new Block(600, 160, "상점", 1, 1);

		barnBlocks.add(upEmptySpace);
		barnBlocks.add(leftEmptySpace);
		barnBlocks.add(rightEmptySpace);
		barnBlocks.add(downEmptySpace1);
		barnBlocks.add(downEmptySpace2);
		barnBlocks.add(downEmptySpace3);
		barnBlocks.add(downEmptySpace4);
		barnBlocks.add(downEmptySpace5);
		barnBlocks.add(downEmptySpace6);
		barnBlocks.add(exit);
		barnBlocks.add(wall);
		barnBlocks.add(oddments);
		barnBlocks.add(oddments2);
		barnBlocks.add(oddments3);
		barnBlocks.add(feedLongBox);
		barnBlocks.add(feedInputBox);
		barnBlocks.add(barnStore);

		return barnBlocks;
	}
}
