import java.util.Objects;

import javax.swing.JLabel;

public class Block extends JLabel {
	int x;
	int y;
	int sizeX = 1;
	int sizeY = 1;
	boolean interacting;
	String info;

	public Block() {
	}

	public Block(int x, int y, String info) {
		super();
		this.x = x;
		this.y = y;
		this.info = info;
	}

	public Block(int x, int y, String info, int sizeX, int sizeY) {
		super();
		this.x = x;
		this.y = y;
		this.interacting = false;
		this.info = info;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}

	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isInteracting() {
		return interacting;
	}

	public void setInteracting(boolean interacting) {
		this.interacting = interacting;
	}

	@Override
	public String toString() {
		return "Block [x=" + x + ", y=" + y + ", info=" + info + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(info);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Block other = (Block) obj;
		return Objects.equals(info, other.info);
	}

}