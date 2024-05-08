import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class AnimalThread implements Runnable {
	private JLabel lbl;
	private Animal a;
	private int targetX;
	private int targetY;
	private boolean isMoving = false;
	private boolean isRunning = false;

	public AnimalThread(JLabel lbl, Animal a) {
		this.lbl = lbl;
		this.a = a;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	@Override
	public void run() {
		Random random = new Random();
		while (true) {
			if (isRunning) {
				int keyCode = random.nextInt(4) + 37;
				System.out.println(keyCode);
				Move(false, 2, keyCode); // 속도
			}
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void Move(Boolean isCh, int speed, int keyCode) {
		if (isMoving) { // 이동 중일 때는 방향 전환을 무시
			return;
		}
		if (!isRunning) {
			return;
		}

		isMoving = true;
		int startX = lbl.getX();
		int startY = lbl.getY();

		targetX = startX;
		targetY = startY;
		// 키 코드에 따라 목표 위치 설정
		switch (keyCode) {
		case 37:
			targetX = Math.max(targetX - 40, 40);
			break;
		case 38:
			targetY = Math.max(targetY - 40, 200);
			break;
		case 39:
			targetX = Math.min(targetX + 40, 720 - lbl.getWidth());
			break;
		case 40:
			targetY = Math.min(targetY + 40, 400 - lbl.getHeight());
			break;

		}

		Timer timer = new Timer(100, new ActionListener() {
			int currentX = startX;
			int currentY = startY;
			int imageIndex = 1;
			String direction = null;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (targetX >= 0 && targetX + lbl.getWidth() <= 800 && targetY >= 0
						&& targetY + lbl.getHeight() <= 600) {
				} else {
					targetX = Math.max(0, Math.min(targetX, 800 - lbl.getWidth()));
					targetY = Math.max(0, Math.min(targetY, 600 - lbl.getHeight()));
				}
				if (currentX != targetX || currentY != targetY) {
					if (currentX < targetX) {
						currentX = Math.min(currentX + speed, targetX);
					} else if (currentX > targetX) {
						currentX = Math.max(currentX - speed, targetX);
					}
					if (currentY < targetY) {
						currentY = Math.min(currentY + speed, targetY);
					} else if (currentY > targetY) {
						currentY = Math.max(currentY - speed, targetY);
					}
					lbl.setLocation(currentX, currentY);

					if (keyCode == 37) {
						direction = "left";
					} else if (keyCode == 38) {
						direction = "up";
					} else if (keyCode == 39) {
						direction = "right";
					} else if (keyCode == 40) {
						direction = "down";
					}
					lbl.setIcon(null);
					String imagePath = "resource/bigcow_" + direction + "_" + imageIndex
							+ ".png";
					lbl.setIcon(new ImageIcon(imagePath));
					imageIndex = (imageIndex % 4) + 1;
				} else {
					isMoving = false;
					((Timer) e.getSource()).stop();
				}
			}
		});
		timer.start();
	}
}