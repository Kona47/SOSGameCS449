package sos;

import javax.swing.*;
import java.awt.*;

public class SOSGameGUI extends JFrame{
	
	
	public SOSGameGUI() {
		
		setTitle("SOS");
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		JPanel panel = new JPanel(new GridLayout(2,3));
		JPanel grid = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				int size = 300 / 3;
				
				for (int i=1; i < 3; i++) {
					g.drawLine(0,  i*size, 300,  i*size);
				}
				
				for (int i=1; i < 3; i++) {
					g.drawLine(i*size,  0,  i*size, 300);
				}
			}
		};
		
		grid.setPreferredSize(new Dimension(200, 200));
		
		JLabel label = new JLabel("Welcome to SOS");
		
		JCheckBox checkEasy = new JCheckBox("Easy");
		JCheckBox checkHard = new JCheckBox("Hard");
		
		JRadioButton s = new JRadioButton("S");
		JRadioButton o = new JRadioButton("O");
		
		ButtonGroup group = new ButtonGroup();
		group.add(o);
		group.add(s);
		
		panel.add(label);
		panel.add(checkEasy);
		panel.add(checkHard);
		panel.add(s);
		panel.add(o);
		
		add(panel, BorderLayout.NORTH);
		add(grid, BorderLayout.CENTER);
		
		panel.setBackground(Color.GRAY);
		
	}
	
	public static void main(String args[]) {
		SOSGameGUI game = new SOSGameGUI();
		game.setVisible(true);
		
	}
}
