import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class AnimalImpl {
	private List<Animal> animalList = new ArrayList<Animal>();
	List<Animal> myAnimalList = new ArrayList<Animal>(10);
	private List<JLabel> barnImgList = new ArrayList<JLabel>();
	private List<AnimalThread> threads = Store.threads;

	public AnimalImpl() {
		// 종, 성장기간, 생산기간, 생산품, 구매가격, 판매가격
		createAnimal(new Animal("소", "cow", 1, 1, "우유", 227, 150, -1));
	}

	private void createAnimal(Animal ani) {
		animalList.add(ani);
	}

	public Animal addMyAnimal(Animal ani) {
		Animal a = new Animal();
		a.setSpecies(ani.getSpecies());
		a.setSpeciesEng(ani.getSpeciesEng());
		a.setGrowthPeriod(ani.getGrowthPeriod());
		a.setName(ani.getName());
		a.setAge(ani.getAge());
		a.setFriendshipGrade(ani.getFriendshipGrade());
		a.setGrowthStage(ani.getGrowthStage());
		a.setGrowthPeriod(ani.getGrowthPeriod());
		a.setProductionPeriod(ani.getProductionPeriod());
		a.setProducts(ani.getProducts());
		a.setPurchasPrice(ani.getPurchasPrice());
		a.setSalePrice(ani.getSalePrice());
		a.setPurchaseDay(ani.getPurchaseDay());
		myAnimalList.add(a);
		return a;
	}

	public Animal loadMyAnimal(Animal ani) {
		ani.setAge(ani.getAge());
		ani.setFriendshipGrade(ani.getFriendshipGrade());
		ani.setGrowthPeriod(ani.getGrowthPeriod());
		ani.setGrowthStage(ani.getGrowthStage());
		ani.setName(ani.getName());
		ani.setProductionPeriod(ani.getProductionPeriod());
		ani.setProducts(ani.getProducts());
		ani.setPurchaseDay(ani.getPurchaseDay());
		ani.setPurchasPrice(ani.getPurchasPrice());
		ani.setSalePrice(ani.getSalePrice());
		ani.setSpecies(ani.getSpecies());
		ani.setSpeciesEng(ani.getSpeciesEng());
		return ani;

	}

	public void UpdateAnimalName(Animal a, String name) {
		for (Animal animal : myAnimalList) {
			if (animal.equals(a)) {
				a.setName(name);
				myAnimalList.set(myAnimalList.indexOf(animal), a);
			}
		}
	}

	public void UpdatePurchaseDay(Animal a) {
		for (Animal animal : myAnimalList) {
			if (animal.equals(a)) {
				a.setPurchaseDay(TestFrame.frame.day);
				myAnimalList.set(myAnimalList.indexOf(animal), a);
			}
		}
	}

	public void UpdateAnimalFriendship(Animal a, int num) {
		if (a.getFriendshipGrade() < 5 || a.getFriendshipGrade() > -10) {
			a.setFriendshipGrade(a.getFriendshipGrade() + num);
			if (a.getFriendshipGrade() > 5) {
				a.setFriendshipGrade(5);
			} else if (a.getFriendshipGrade() < -10) {
				a.setFriendshipGrade(-10);
			}
		}
	}

	public void UpdateAnimalGrowthPeriod(Animal a) {
		for (Animal animal : myAnimalList) {
			if (animal.equals(a)) {
				if (a.getGrowthStage() < 2) {
					if (a.getAge() < a.getGrowthPeriod()) {
						UpdateAnimalAge(a);
					} else {
						UpdateAnimalAge(a);
						a.setGrowthStage(a.getGrowthStage() + 1);
						a.setSpeciesEng("big" + a.getSpeciesEng());
						System.out.println(a.toString());
					}
				} else {
					UpdateAnimalAge(a);
				}
				a.setFriendshipGrade(a.getFriendshipGrade() + 1);
				myAnimalList.set(myAnimalList.indexOf(animal), a);
			}
		}
	}

	private void UpdateAnimalAge(Animal a) {
		for (Animal animal : myAnimalList) {
			if (animal.equals(a)) {
				a.setAge(a.getAge() + 1);
				myAnimalList.set(myAnimalList.indexOf(animal), a);
			}
		}
	}

	// 성장단계가 2단계 && 생산기간이 지났고 && 우정등급이 3이상 => 생산
	public void createProducts(Animal a) {
		Random random = new Random();
		for (Animal animal : myAnimalList) {
			if (animal.equals(a)) {
				if (a.getGrowthStage() == 2 && a.getAge() >= a.getGrowthPeriod() + a.getProductionPeriod()
						&& (a.getAge() - a.getGrowthPeriod()) % a.getProductionPeriod() == 0
						&& a.getFriendshipGrade() >= -10) {
//					&& a.getFriendshipGrade() >= 3) {
					int xRange = (680 - 40) / 40;
					int randomX = random.nextInt(xRange + 1) * 40 + 80;
					int yRange = (400 - 200) / 40;
					int randomY = random.nextInt(yRange + 1) * 40 + 200;
					boolean exist = false;

					for (Block existingBlock : TestFrame.blocks) {
						if (existingBlock.getX() == randomX && existingBlock.getY() == randomY) {
							exist = true;
							break;
						}
					}
					if (!exist) {
						Block productBlock = new Block(randomX, randomY, a.getProducts());
						ImageIcon icon = new ImageIcon("resource/milk.png");
						JLabel productImg = new JLabel(icon);
						productImg.setBounds(productBlock.getX(), productBlock.getY(), 40, 40);
						TestFrame.barnPnl.add(productImg, JLayeredPane.PALETTE_LAYER);
						TestFrame.blocks.add(productBlock);
						barnImgList.add(productImg);
						TestFrame.barnPnl.revalidate();
						TestFrame.barnPnl.repaint();
						break;
					}
				}
			}
		}
	}

	// 건초를 먹으면 우정등급 +1, 건초를 먹지 못하면 우정등급 -3
	public void feedHay() {
		if (getAllMyAnimal().size() > 0) {
			boolean hayFound = false;
			Block hayBlock = null;
			for (Block elem : TestFrame.blocks) {
				if (elem.getInfo().equals("건초있음")) {
					hayBlock = elem;
					hayFound = true;
					System.out.println(hayBlock.toString());
					break;
				}
			}
			if (!hayFound) {
				for (Animal elem : myAnimalList) {
					UpdateAnimalFriendship(elem, 0);
				}
			} else {
				hayBlock.setInfo("건초없음");
				TestFrame.character.getFunction().removeBarnItemImg(hayBlock);
				for (Animal elem : myAnimalList) {
					UpdateAnimalFriendship(elem, 0);
				}
			}
		}
	}

	public List<Animal> getAllAnimal() {
		return animalList;
	}

	public List<Animal> getAllMyAnimal() {
		return myAnimalList;
	}

	public List<JLabel> getAllBarnImg() {
		return barnImgList;
	}

	public void loadAnimal(List<Animal> list) {
		try {
			Iterator<Animal> iterator = list.iterator();
			while (iterator.hasNext()) {
				Animal elem = iterator.next();
				JLabel lblAni = new JLabel();
				Animal a = loadMyAnimal(elem);
				AnimalMouseListener mouseListener = new AnimalMouseListener(lblAni, a);
				lblAni.addMouseListener(mouseListener);
				lblAni.setBounds(200, 200, 40, 80);

				AnimalThread AniThread = new AnimalThread(lblAni, a);
				Thread thread = new Thread(AniThread);
				synchronized (threads) { // 동기화 블록 추가
					threads.add(AniThread);
				}
				thread.start();
				TestFrame.barnPnl.add(lblAni, JLayeredPane.MODAL_LAYER);
			}
		} catch (Exception e) {
		} finally {
			System.out.println("내 동물리스트" + getAllMyAnimal().toString());
		}
	}

}