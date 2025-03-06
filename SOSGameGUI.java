package sos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import sos.SOSGame.*;

@SuppressWarnings("serial")
public class SOSGameGUI extends JFrame{
	
	private SOSGame game = new SOSGame();
	public int CELL_SIZE = 300 / game.getSize();
	public static final int GRID_WIDTH = 4;
	public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2;

	public int CELL_PADDING = CELL_SIZE / 6;
	public int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
	public static final int SYMBOL_STROKE_WIDTH = 1;
	
	private GameBoardCanvas gameBoardCanvas;
	private JLabel gameStatusBar;
	private JRadioButton bs = new JRadioButton("S", true);
	private JRadioButton bo = new JRadioButton("O");
	private JRadioButton rs = new JRadioButton("S", true);
	private JRadioButton ro = new JRadioButton("O");
	private JRadioButton simple = new JRadioButton("Simple Game", true);
	private JRadioButton general = new JRadioButton("General Game");

	private JLabel sizeLabel = new JLabel("Board Size:");
	private Integer[] sizes = {3, 4, 5, 6, 7, 8, 9, 10};
	private JComboBox<Integer> sizeField = new JComboBox<>(sizes);
	private JPanel panel = new JPanel();
	private JPanel gameType = new JPanel(new GridLayout(1,2));
	private JPanel bSize = new JPanel(new GridLayout(1,2));
	private JPanel grid = new JPanel();
	private JPanel bluePanel = new JPanel();
	private JPanel redPanel = new JPanel();
	private JLabel label = new JLabel("Welcome to SOS");
	
	public SOSGameGUI() {
		//Initial things
		this.game = new SOSGame();
		setTitle("SOS");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		bSize.setOpaque(false);
		gameType.setOpaque(false);
		
		panel.setPreferredSize(new Dimension(300, 50));
		
		gameBoardCanvas = new GameBoardCanvas();
		grid.add(gameBoardCanvas);
		gameBoardCanvas.setPreferredSize(new Dimension(CELL_SIZE * game.getSize(), CELL_SIZE * game.getSize()));
		
		gameStatusBar = new JLabel("  ");
		gameStatusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
		gameStatusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
		
		
		//S-O buttons for blue and red players. Text color set to white
		bs.setForeground(Color.WHITE);
		bo.setForeground(Color.WHITE);
		rs.setForeground(Color.WHITE);
		ro.setForeground(Color.WHITE);
		rs.addActionListener(new RedRadioButtonListener());
		ro.addActionListener(new RedRadioButtonListener());
		bs.addActionListener(new BlueRadioButtonListener());
		bo.addActionListener(new BlueRadioButtonListener());
		
		
		//These groups are for S O buttons for blue/red players
		ButtonGroup moveGroupBlue = new ButtonGroup();
		moveGroupBlue.add(bs);
		moveGroupBlue.add(bo);
		
		ButtonGroup moveGroupRed = new ButtonGroup();
		moveGroupRed.add(rs);
		moveGroupRed.add(ro);
		
		//Game mode button group
		ButtonGroup gameGroup = new ButtonGroup();
		gameGroup.add(simple);
		gameGroup.add(general);
		
		simple.addActionListener(new gameModeListener());
		general.addActionListener(new gameModeListener());

		gameType.add(simple);
		gameType.add(general);
		
		sizeField.addActionListener(new boardSizeListener());
		
		bSize.add(sizeLabel);
		bSize.add(sizeField);
		
		panel.add(label, BorderLayout.NORTH);
		panel.add(gameType, BorderLayout.WEST);
		panel.add(bSize, BorderLayout.EAST);
		
		//panel for blue side
        bluePanel.add(new JLabel("Blue Player"));
        bluePanel.setPreferredSize(new Dimension(100, 300));
		bluePanel.add(bs);
		bluePanel.add(bo);
		
		//panel for red side
        redPanel.add(new JLabel("Red Player"));
        redPanel.setPreferredSize(new Dimension(100, 300)); 
		redPanel.add(rs);
		redPanel.add(ro);

		//Add panels to the frame
		add(panel, BorderLayout.NORTH);
		add(grid, BorderLayout.CENTER);
		add(bluePanel, BorderLayout.WEST);
		add(redPanel, BorderLayout.EAST);
		add(gameStatusBar, BorderLayout.SOUTH);
		setBackground(Color.LIGHT_GRAY);
		
		//Set background colors
		panel.setBackground(Color.LIGHT_GRAY);
		bluePanel.setBackground(new Color(0, 0, 255, 150));
		redPanel.setBackground(new Color(255, 0, 0, 150));	
	}
	
	private class BlueRadioButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(bs.isSelected()) {
				game.blue = sORo.S;
			}
			else if(bo.isSelected()){
				game.blue = sORo.O;
			}
		}
	}
	
	private class RedRadioButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(rs.isSelected()) {
				game.red = sORo.S;
			}
			else if(ro.isSelected()){
				game.red = sORo.O;
			}
		}
	}
	
	private class gameModeListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(simple.isSelected()) {
				game.setGameMode(gameMode.SIMPLE);
			}
			else if(general.isSelected()){
				game.setGameMode(gameMode.GENERAL);
			}
		}
	}
	
	private class boardSizeListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Integer selection = (Integer) sizeField.getSelectedItem();
			game = new SOSGame();
			game.setSize(selection);
			CELL_SIZE = 300 / game.getSize(); 
	        CELL_PADDING = CELL_SIZE / 6;
	        SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;

	        // Remove old canvas and create a new one
	        gameBoardCanvas.setPreferredSize(new Dimension(CELL_SIZE * game.getSize(), CELL_SIZE * game.getSize()));
	        gameBoardCanvas.revalidate();
	        gameBoardCanvas.repaint();
		}
	}
	
	
	class GameBoardCanvas extends JPanel {

		GameBoardCanvas() {
			addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (game.getGameState() == GameState.PLAYING) {
						int rowSelected = e.getY() / CELL_SIZE;
						int colSelected = e.getX() / CELL_SIZE;
						game.makeMove(rowSelected, colSelected);
					}/* else {
						game.resetGame();
						;
					}*/
					repaint();
				}
			});
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(Color.LIGHT_GRAY);
			drawGridLines(g);
			drawBoard(g);
			printStatusBar();
		}

		private void drawGridLines(Graphics g) {
			g.setColor(Color.BLACK);
			for (int row = 1; row < game.getSize(); ++row) {
				g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDHT_HALF, CELL_SIZE * game.getSize() - 1, GRID_WIDTH,
						GRID_WIDTH, GRID_WIDTH);
			}
			for (int col = 1; col < game.getSize(); ++col) {
				g.fillRoundRect(CELL_SIZE * col - GRID_WIDHT_HALF, 0, GRID_WIDTH,
						CELL_SIZE * game.getSize() - 1, GRID_WIDTH, GRID_WIDTH);
			}
		}

		private void drawBoard(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			int fontSize = CELL_SIZE / 2;  // Adjust this ratio as needed
		    g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
			for (int row = 0; row < game.getSize(); ++row) {
				for (int col = 0; col < game.getSize(); ++col) {
					int x = col * CELL_SIZE + CELL_SIZE / 3;
					int y = row * CELL_SIZE + 2 * CELL_SIZE / 3;
					if (game.getCell(row, col) == Cell.O) {
						g2d.setColor(Color.BLACK);
						g2d.drawString("O", x, y);
					} 
					else if (game.getCell(row, col) == Cell.S) {
						g2d.setColor(Color.BLACK);
						g2d.drawString("S", x, y);
					}
				}
			}
		}

		private void printStatusBar() {
			if (game.getGameState() == GameState.PLAYING) {
				gameStatusBar.setForeground(Color.BLACK);
				gameStatusBar.setHorizontalAlignment(SwingConstants.CENTER);;
				if (game.getTurn() == 'B') {
					gameStatusBar.setText("Blue's Turn");
				} else {
					gameStatusBar.setText("Red's Turn");
				}
			} else if (game.getGameState() == GameState.DRAW) {
				gameStatusBar.setForeground(Color.RED);
				gameStatusBar.setText("It's a Draw! Click to play again.");
			} else if (game.getGameState() == GameState.BLUE_WON) {
				gameStatusBar.setForeground(Color.RED);
				gameStatusBar.setText("Blue Won! Click to play again.");
			} else if (game.getGameState() == GameState.RED_WON) {
				gameStatusBar.setForeground(Color.RED);
				gameStatusBar.setText("Red Won! Click to play again.");
			}
		}

	}
	public static void main(String args[]) {
		SOSGameGUI game2 = new SOSGameGUI();
		game2.setVisible(true);
	}
}
