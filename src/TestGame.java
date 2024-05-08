import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class Blocks extends JPanel {
	XY xy;
	boolean interacting;
	Color c;

	public Blocks() {
	}

	public Blocks(XY xy) {
		super();
		this.xy = xy;
		this.interacting = false; // �ʱⰪ�� false�� ����

	}

	public XY getXy() {
		return xy;
	}

	public void setXy(XY xy) {
		this.xy = xy;
	}

	public boolean isInteracting() {
		return interacting;
	}

	public void setInteracting(boolean interacting) {
		this.interacting = interacting;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((xy == null) ? 0 : xy.hashCode());
		return result;
	}

	// ���� ���� �޼��� �߰�
	public void setBackgroundColor(Color color) {
		this.setBackground(color);
	}

	@Override
	public String toString() {
		return "Block [x=" + xy.getX() + ", y=" + xy.getY() + "]";
	}

}
class XY {
	int x;
	int y;

	public XY() {
	}

	public XY(int x, int y) {
		super();
		this.x = x;
		this.y = y;
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

	@Override
	public String toString() {
		return "XY [x=" + x + ", y=" + y + "]";
	}

}

public class TestGame extends JFrame {

	public static TestGame frame;
	private JPanel contentPane;
	public static XY xy = new XY();
	public static List<Block> blocks = new ArrayList<Block>();
	public static JPanel pnl;
	private int inputKeys;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					frame = new TestGame();
					frame.setVisible(true);
					frame.requestFocusInWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestGame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setLayout(null);
		setUndecorated(true);
//		MouseListener l = extracted();
		KeyAdapter key = extracted2();

		addKeyListener(key); // �гο� Ű ������ ���

//		addMouseListener(l);

		pnl = createCharacter();

		add(pnl);
		revalidate();
		repaint();

	}

	private KeyAdapter extracted2() {
		KeyAdapter l = new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				int newX = pnl.getX();
				int newY = pnl.getY();
//					System.out.println(e.getKeyCode());
				// 왼
				if (e.getKeyCode() == 37) {
					newX -= 40;
					inputKeys = 37;
				}
				// 위
				else if (e.getKeyCode() == 38) {
					newY -= 40;
					inputKeys = 38;
				}
				// 오
				else if (e.getKeyCode() == 39) {
					newX += 40;
					inputKeys = 39;
				}
				// 아
				else if (e.getKeyCode() == 40) {
					newY += 40;
					inputKeys = 40;
				} else if (e.getKeyCode() == 81) {
					createBlock(inputKeys);
				}
				// ��ȣ�ۿ� E
				else if (e.getKeyCode() == 69) {
					interact();

				}
				// ���� D
				else if (e.getKeyCode() == 68) {
					deleteBlock();
				}
				if (isValidMove(newX, newY)) {
					pnl.setLocation(newX, newY);
				}
				revalidate(); // ���̾ƿ� �籸��
				repaint();
			}
		};
		return l;
	}

	public boolean isValidMove(int newX, int newY) {
		if (newX == getWidth() || newX < 0 || newY == getHeight() - 100 || newY < 0) {
			System.out.println();
			return false;
		}

		for (Block block : blocks) {
			if (block.getXy().getX() == newX && block.getXy().getY() == newY + 40) {
				block.setBackgroundColor(Color.blue);
				return false;
			}
		}
		return true;
	}

	// 캐릭터와 가까운 블록을 찾는 메서드
	private Block findNearestBlock() {
		Block nearestBlock = null;

		// 캐릭터 위치
		int charX = pnl.getX();
		int charY = pnl.getY();

		for (Block block : blocks) {
			int blockX = block.getXy().getX();
			int blockY = block.getXy().getY();

			int distanceX = (charX - blockX);
			int distanceY = (charY - blockY);
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

	// 블록 삭제 메서드
	public void deleteBlock() {
		try {
			Block nearestBlock = findNearestBlock();
			nearestBlock.setInteracting(false);

			if (nearestBlock != null) {
				nearestBlock.setInteracting(true);
				blocks.remove(nearestBlock);
				remove(nearestBlock);
			}
			System.out.println(nearestBlock.toString());
			System.out.println(blocks.toString());
		} catch (NullPointerException e) {
		}
	}

	// 가장 가까운 블록이 존재하고 상호작용 상태를 변경
	private void interact() {
		try {
			// 캐릭터와 가장 가까운 블록을 찾음
			Block nearestBlock = findNearestBlock();
			nearestBlock.setInteracting(false);

			if (nearestBlock != null) {
				nearestBlock.setInteracting(true);
				nearestBlock.setBackground(Color.RED);
			}
			System.out.println(nearestBlock.toString());
			System.out.println(blocks.toString());

		} catch (NullPointerException e) {
		}
	}

//	private MouseListener extracted() {
//		MouseListener l = new MouseListener() {
//
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void mousePressed(MouseEvent e) {
//				System.out.println(e.getX());
//
//				xy.setX(e.getX());
//				xy.setY(e.getY());
//				System.out.println("mouse" + xy.getX() + "/" + xy.getY());
//
//			}
//
//			@Override
//			public void mouseExited(MouseEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//		};
//		return l;
//	}

//	public void round20(XY xy) {
//		int remainderX = xy.getX() % 40;
//		if (remainderX >= 20) {
//			xy.setX((xy.getX() + (40 - remainderX)));
//		} else {
//			xy.setX((xy.getX() - remainderX));
//		}
//		int remainderY = xy.getY() % 40;
//		if (remainderY >= 20) {
//			xy.setY((xy.getY() + (40 - remainderY)));
//		} else {
//			xy.setY((xy.getY() - remainderY));
//		}
//	}

	// 블록 생성 메서드
	private Block createBlock(int inputKeys) {
		int charX = pnl.getX();
		int charY = pnl.getY();
		int blockX = charX;
		int blockY = charY;

		Block block = null;
		try {
			if (inputKeys == 37) {
				blockX -= 40;
				blockY += 40;
			} else if (inputKeys == 38) {
				blockY -= 0;
			} else if (inputKeys == 39) {
				blockX += 40;
				blockY += 40;
			} else if (inputKeys == 40) {
				blockY += 80;
			}
			XY blockXY = new XY(blockX, blockY);
			block = new Block(blockXY);
			if (!blocks.contains(block)) {

				block.setBounds(blockX, blockY, 40, 40);
				block.setBackground(Color.GREEN);
				frame.add(block);
				frame.requestFocusInWindow();
				blocks.add(block);
				System.out.println(blocks.toString());
				return block; // ������ Block ��ü ��ȯ
			}

		} catch (

		NullPointerException e) {
		}
		return null;
	}

	// 블록 존재 확인하는 메서드
	private boolean checkBlock(Block block) {

		int index = 0;
		for (Block b : blocks) {
			if (blocks.get(index).equals(block)) {
				return true;
			}
			index++;
		}
		return false;
	}

	// 캐릭터 생성 메서드
	public JPanel createCharacter() {
		JPanel pnl = new JPanel();
		pnl.setBounds(0, 0, 40, 80);
		pnl.setBackground(Color.ORANGE);
		return pnl;
	}
}
