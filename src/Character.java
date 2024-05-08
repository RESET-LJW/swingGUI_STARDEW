import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.Timer;

public class Character {

	private TestFrame frame;
	private JLabel character;
	private Function function;
	private Timer timer;
	private ImageIcon[][] moveImages;
	private int currentDirection = 2;
	private int currentImageIndex = 0;
	private int movementDistance = 10; // 속도
	private int inputKeys;
	private boolean isMoving = false;
	protected static KeyAdapter l;

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public TestFrame getFrame() {
		return frame;
	}

	public void setFrame(TestFrame frame) {
		this.frame = frame;
	}

	public JLabel getCharacter() {
		return character;
	}

	public void setCharacter(JLabel character) {
		this.character = character;
	}

	public int getInputKeys() {
		return inputKeys;
	}

	public void setInputKeys(int inputKeys) {
		this.inputKeys = inputKeys;
	}

	public Character(TestFrame frame) {
		this.frame = frame;
		this.function = new Function(this);
		character = createCharacter();
		frame.add(character);
		frame.addKeyListener(extracted());
		frame.revalidate();
		frame.repaint();
	}

	// 캐릭터 생성 메서드
	public JLabel createCharacter() {
		loadMoveImages();
		character = new JLabel(moveImages[currentDirection][currentImageIndex]);
		character.setBounds(0, 0, 40, 80);
		return character;
	}

	public void loadMoveImages() {
		moveImages = new ImageIcon[4][4]; // 4개의 방향에 각각 4개의 이미지
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				String imageName = "resource/f_" + i + "_" + j + ".png";
				moveImages[i][j] = new ImageIcon(imageName);
			}
		}
	}

	public KeyAdapter extracted() {
		KeyAdapter l = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (!isMoving) {
					timer = new Timer(100, event -> {
						// 상호작용 E
						if (e.getKeyCode() == 69) {
							function.interact();
						}
						// 삭제 D
						else if (e.getKeyCode() == 68) {
							function.deleteBlock();
						}
						// 세이브 S
						else if (e.getKeyCode() == KeyEvent.VK_S) {
							DB.save();
						}
						else if (e.getKeyCode() == KeyEvent.VK_I) {
							if (character.isVisible()) {
								character.setVisible(false);
							} else {
								character.setVisible(true);
							}
							if (TestFrame.in.isVisible()) {
								TestFrame.in.setVisible(false);
								TestFrame.in.setFocusable(false);
							} else {
								TestFrame.in.setVisible(true);
								TestFrame.in.setFocusable(true);
							}

						}

						int newX = character.getX();
						int newY = character.getY();
						if (e.getKeyCode() == 37 && e.getKeyCode() == inputKeys) {
							currentDirection = 0; // 좌측 방향
							if (function.isValidMove(newX - 40, newY)) {
								newX -= movementDistance;
							}
						} else if (e.getKeyCode() == 38 && e.getKeyCode() == inputKeys) {
							currentDirection = 1; // 상단 방향
							if (function.isValidMove(newX, newY - 40)) {
								newY -= movementDistance;
							}
						} else if (e.getKeyCode() == 39 && e.getKeyCode() == inputKeys) {
							currentDirection = 2; // 우측 방향
							if (function.isValidMove(newX + 40, newY)) {
								newX += movementDistance;
							}
						} else if (e.getKeyCode() == 40 && e.getKeyCode() == inputKeys) {
							currentDirection = 3; // 하단 방향
							if (function.isValidMove(newX, newY + 40)) {
								newY += movementDistance;
							}

						} else if (e.getKeyCode() != inputKeys && e.getKeyCode() >= 37 && e.getKeyCode() <= 40) {
							inputKeys = e.getKeyCode();
						}
						if (function.isValidMove(newX, newY)) {
							character.setLocation(newX, newY);
							character.setIcon(moveImages[currentDirection][currentImageIndex]);
							currentImageIndex = (currentImageIndex + 1) % 4;
						}
						if (newX % 40 == 0 && newY % 40 == 0) {
							timer.stop();
							isMoving = false;
							character.setIcon(moveImages[currentDirection][0]);
						}

					});
					timer.start();
					isMoving = true;
				}
			}
		};
		return l;
	}
}
