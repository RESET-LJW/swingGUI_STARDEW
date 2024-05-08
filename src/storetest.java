import javax.swing.*;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class storetest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Button with Image and Text Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // 이미지를 포함한 버튼 생성
        JButton btnItem = new JButton("item");
        btnItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		System.out.println("dsfdfsd");
        	}
        });
        btnItem.setContentAreaFilled(false);
        btnItem.setIconTextGap(100);
        btnItem.setFocusable(false);
        btnItem.setBackground(Color.PINK);
        btnItem.setHorizontalAlignment(SwingConstants.LEFT);
        btnItem.setBounds(12, 10, 40, 40);
        btnItem.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        
        
        
        ImageIcon icon = new ImageIcon("C:\\\\Users\\\\GGG\\\\Desktop\\\\춘식1.png");
        Image image = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(image);
        btnItem.setIcon(resizedIcon);
        
//        frame.getContentPane().add(panel);
        
        
//        btnItem.setIcon(new ImageIcon("C:\\Users\\GGG\\Desktop\\춘식1.png")); // 이미지 설정
        btnItem.setHorizontalTextPosition(SwingConstants.RIGHT);
//        btnItem.setMargin(new Insets(10, 10, 10, 10));
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBounds(60, 142, 560, 193);
        panel.setLayout(null);
        panel.add(btnItem);
        
        
        JButton btnItem_1 = new JButton("item");
        btnItem_1.setIconTextGap(100);
        btnItem_1.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnItem_1.setHorizontalAlignment(SwingConstants.LEFT);
        btnItem_1.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        btnItem_1.setFocusable(false);
        btnItem_1.setContentAreaFilled(false);
        btnItem_1.setBackground(Color.PINK);
        btnItem_1.setBounds(59, 10, 40, 40);
        panel.add(btnItem_1);
        
        JButton btnItem_2 = new JButton("item");
        btnItem_2.setIconTextGap(100);
        btnItem_2.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnItem_2.setHorizontalAlignment(SwingConstants.LEFT);
        btnItem_2.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        btnItem_2.setFocusable(false);
        btnItem_2.setContentAreaFilled(false);
        btnItem_2.setBackground(Color.PINK);
        btnItem_2.setBounds(106, 10, 40, 40);
        panel.add(btnItem_2);
        
        JButton btnItem_3 = new JButton("item");
        btnItem_3.setIconTextGap(100);
        btnItem_3.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnItem_3.setHorizontalAlignment(SwingConstants.LEFT);
        btnItem_3.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        btnItem_3.setFocusable(false);
        btnItem_3.setContentAreaFilled(false);
        btnItem_3.setBackground(Color.PINK);
        btnItem_3.setBounds(150, 10, 40, 40);
        panel.add(btnItem_3);
        
        JButton btnItem_4 = new JButton("item");
        btnItem_4.setIconTextGap(100);
        btnItem_4.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnItem_4.setHorizontalAlignment(SwingConstants.LEFT);
        btnItem_4.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        btnItem_4.setFocusable(false);
        btnItem_4.setContentAreaFilled(false);
        btnItem_4.setBackground(Color.PINK);
        btnItem_4.setBounds(195, 10, 40, 40);
        panel.add(btnItem_4);
        
        JButton btnItem_5 = new JButton("item");
        btnItem_5.setIconTextGap(100);
        btnItem_5.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnItem_5.setHorizontalAlignment(SwingConstants.LEFT);
        btnItem_5.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        btnItem_5.setFocusable(false);
        btnItem_5.setContentAreaFilled(false);
        btnItem_5.setBackground(Color.PINK);
        btnItem_5.setBounds(240, 10, 40, 40);
        panel.add(btnItem_5);
        
        JButton btnItem_6 = new JButton("item");
        btnItem_6.setIconTextGap(100);
        btnItem_6.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnItem_6.setHorizontalAlignment(SwingConstants.LEFT);
        btnItem_6.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        btnItem_6.setFocusable(false);
        btnItem_6.setContentAreaFilled(false);
        btnItem_6.setBackground(Color.PINK);
        btnItem_6.setBounds(291, 10, 40, 40);
        panel.add(btnItem_6);
        
        JButton btnItem_7 = new JButton("item");
        btnItem_7.setIconTextGap(100);
        btnItem_7.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnItem_7.setHorizontalAlignment(SwingConstants.LEFT);
        btnItem_7.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        btnItem_7.setFocusable(false);
        btnItem_7.setContentAreaFilled(false);
        btnItem_7.setBackground(Color.PINK);
        btnItem_7.setBounds(343, 10, 40, 40);
        panel.add(btnItem_7);
        
        JButton btnItem_8 = new JButton("item");
        btnItem_8.setIconTextGap(100);
        btnItem_8.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnItem_8.setHorizontalAlignment(SwingConstants.LEFT);
        btnItem_8.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        btnItem_8.setFocusable(false);
        btnItem_8.setContentAreaFilled(false);
        btnItem_8.setBackground(Color.PINK);
        btnItem_8.setBounds(400, 10, 40, 40);
        panel.add(btnItem_8);
        
        JButton btnItem_9 = new JButton("item");
        btnItem_9.setIconTextGap(100);
        btnItem_9.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnItem_9.setHorizontalAlignment(SwingConstants.LEFT);
        btnItem_9.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        btnItem_9.setFocusable(false);
        btnItem_9.setContentAreaFilled(false);
        btnItem_9.setBackground(Color.PINK);
        btnItem_9.setBounds(450, 10, 40, 40);
        panel.add(btnItem_9);
        
        JButton btnItem_10 = new JButton("item");
        btnItem_10.setIconTextGap(100);
        btnItem_10.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnItem_10.setHorizontalAlignment(SwingConstants.LEFT);
        btnItem_10.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        btnItem_10.setFocusable(false);
        btnItem_10.setContentAreaFilled(false);
        btnItem_10.setBackground(Color.PINK);
        btnItem_10.setBounds(508, 10, 40, 40);
        panel.add(btnItem_10);
        
        JButton btnNewButton = new JButton("X");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.dispose();
        	}
        });
        btnNewButton.setBounds(546, 100, 52, 51);
        frame.getContentPane().add(btnNewButton);
        frame.setBounds(100, 100, 800, 600);
        
        frame.setVisible(true);
    }
}
