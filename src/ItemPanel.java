import javax.swing.*;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

public class ItemPanel extends JPanel {
	private String itemTitle = null;

	public ItemPanel(String itemTitle, JPanel pnl, Furniture fur, Animal ani) {
		super();
		this.itemTitle = itemTitle;
		pnl.add(itemPnl(itemTitle, fur, ani));
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public static JPanel itemPnl(String item, Furniture fur, Animal ani) {
		ImageIcon resizedIcon2 = new ImageIcon();
//		String itemPath2= "resource/textbox.png";
//		ImageIcon icon2 = new ImageIcon(itemPath2);
//		Image image2 = icon2.getImage().getScaledInstance(280, 85, Image.SCALE_SMOOTH);
//		ImageIcon resizedIcon2 = new ImageIcon(image2);
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setPreferredSize(new Dimension(100, 80));
		panel.setLayout(null);
		
		JLabel help = new JLabel();
//		help.setOpaque(false);
		help.setSize(280, 85);
		help.setLocation(0, 0);
		help.setIcon(resizedIcon2);
		panel.add(help);
		
		JButton btnItem = new JButton();
		btnItem.setText(item);
		btnItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Store.lblNewLabel.setText(item);
				if (Store.jtp.getSelectedIndex() == 0) {
					Store.btnNewButton_1.setText(String.valueOf(fur.getPrice() + " G"));
				} 
				else if (Store.jtp.getSelectedIndex() == 1) 
					Store.btnNewButton_1.setText(String.valueOf(ani.getPurchasPrice() + " G"));
			}
		});

		btnItem.setIconTextGap(70);
		btnItem.setContentAreaFilled(false);
		btnItem.setFocusable(false);
		btnItem.setHorizontalAlignment(SwingConstants.LEFT);
		btnItem.setBounds(30, 10, 280, 80);
		btnItem.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		String itemPath = "resource/" + item + ".png";
		ImageIcon icon = new ImageIcon(itemPath);
		Image image = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(image);
		btnItem.setBorderPainted(false);
		btnItem.setOpaque(false);
		btnItem.setIcon(resizedIcon);

		btnItem.setHorizontalTextPosition(SwingConstants.RIGHT);
		help.add(btnItem);
		
		return panel;
	}
}
