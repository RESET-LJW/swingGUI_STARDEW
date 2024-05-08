import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

public class FurnitureTotal {
	protected static List<Furniture> furnitureTotalList = new ArrayList<Furniture>();
	protected static List<Furniture> furniturewallPaperList = new ArrayList<Furniture>();
	protected static List<Furniture> furnitureFLoorList = new ArrayList<Furniture>();
	
	public FurnitureTotal() {
		Map<String, ImageIcon> imgMap = Function.imgMap();
		// 벽지
		
		
		String name = "";
		Furniture wallPaper = new Furniture("집_벽지", 2000, new Block(0, 40, "집_벽지", 20, 3));
		Furniture wallPaper2 = new Furniture("집_벽지2", 2000, new Block(0, 40, "집_벽지2", 20, 3));
		// 바닥
		Furniture floor = new Furniture("집_바닥", 2000, new Block(0, 0, "집_바닥", 20, 15));
		Furniture floor2 = new Furniture("집_바닥2", 2000, new Block(0, 0, "집_바닥2", 20, 15));
		// 가구
		Furniture bed = new Furniture("집_침대", 2000, new Block(520, 120, "집_침대", 2, 4));
		Furniture pot = new Furniture("집_화분", 2000, new Block(160, 160, "집_화분", 1, 2));
		Furniture Fireplace = new Furniture("집_난로", 2000, new Block(80, 40, "집_난로", 2, 3));
		Furniture firewood = new Furniture("집_장작", 2000, new Block(160, 120, "집_장작", 1, 1));
		Furniture bearStatue = new Furniture("집_곰석상", 2000, new Block(40, 40, "집_곰석상", 2, 3));
		Furniture sofa = new Furniture("집_쇼파", 2000, new Block(40, 40, "집_쇼파", 1, 2));
		Furniture sofa2 = new Furniture("집_쇼파2", 2000, new Block(40, 40, "집_쇼파2", 1, 2));
		Furniture tabletop = new Furniture("집_탁상", 2000, new Block(0, 0, "집_탁상", 1, 2));
		Furniture light = new Furniture("집_등", 2000, new Block(40, 40, "집_등", 1, 1));
		Furniture axe = new Furniture("집_도끼", 2000, new Block(40, 40, "집_도끼", 1, 1));
		Furniture sofaright = new Furniture("집_쇼파우", 2000, new Block(40, 40, "집_쇼파우", 1, 2));
		Furniture sofaleft = new Furniture("집_쇼파좌", 2000, new Block(40, 40, "집_쇼파좌", 1, 2));
		Furniture bookshelf = new Furniture("집_책꽂이", 2000, new Block(40, 120, "집_책꽂이", 2, 2));
		Furniture pot2 = new Furniture("집_화분2", 2000, new Block(40, 40, "집_화분2", 1, 1));
		
		// 벽지
		furniturewallPaperList.add(wallPaper);
		furniturewallPaperList.add(wallPaper2);

		// 바닥
		furnitureFLoorList.add(floor);
		furnitureFLoorList.add(floor2);
		
		// 가구
		furnitureTotalList.add(bed);
		furnitureTotalList.add(bookshelf);
		furnitureTotalList.add(pot);
		furnitureTotalList.add(Fireplace);
		furnitureTotalList.add(firewood);
		furnitureTotalList.add(bearStatue);
		furnitureTotalList.add(sofa);
		furnitureTotalList.add(sofa2);
		furnitureTotalList.add(tabletop);
		furnitureTotalList.add(light);
		furnitureTotalList.add(axe);
		furnitureTotalList.add(sofaright);
		furnitureTotalList.add(sofaleft);
		furnitureTotalList.add(pot2);
		
		
	}
}
