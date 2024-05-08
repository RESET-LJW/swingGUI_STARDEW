import javax.swing.ImageIcon;

public class Furniture {
	private String name;
	private int price;
	private Block block;
	
	public Furniture(String name, int price, Block block) {
		super();
		this.name = name;
		this.price = price;
		this.block = block;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}
	
	
	
	
}
