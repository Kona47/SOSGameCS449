package sos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import sos.SOSGame.*;
import java.util.HashMap;
import java.util.Map;

//Implemented a lot of following code from TicTacToe example provided

@SuppressWarnings("serial")
public class SOSGameGUI extends JFrame{
	
	private SOSGame game = new SOSsimple();
	public int CELL_SIZE = 400 / game.getSize();
	public static final int GRID_WIDTH = 4;
	public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2;

	public int CELL_PADDING = CELL_SIZE / 6;
	public int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
	public static final int SYMBOL_STROKE_WIDTH = 1;
	
	private GameBoardCanvas gameBoardCanvas;
	private JLabel gameStatusBar;
	private JRadioButton bs;
	private JRadioButton bo;
	private JRadioButton rs;
	private JRadioButton ro;
	private JRadioButton simple;
	private JRadioButton general;

	private JLabel sizeLabel = new JLabel("    Board Size:");
	private Integer[] sizes = {3, 4, 5, 6, 7, 8, 9, 10};
	private JComboBox<Integer> sizeField;
	private JPanel panel = new JPanel();
	private JPanel gameType = new JPanel(new GridLayout(1,2));
	private JPanel bSize = new JPanel(new GridLayout(1,2));
	private JPanel grid = new JPanel();
	private JPanel bluePanel = new JPanel();
	private JPanel redPanel = new JPanel();
	private JLabel label = new JLabel("SOS      ");
	//Keep track of sos that have been drawn
	private Map<String, Color> drawnSOS = new HashMap<>();
	
	public SOSGameGUI() {
		//Initial things
		setTitle("SOS Game");
		setSize(700, 515);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//set buttons
		bs = new JRadioButton("S", true);
		bo = new JRadioButton("O");
		rs = new JRadioButton("S", true);
		ro = new JRadioButton("O");
		simple = new JRadioButton("Simple Game", true);
		general = new JRadioButton("General Game");
		sizeField = new JComboBox<>(sizes);

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
		
		panel.add(label);
		panel.add(gameType);
		panel.add(bSize);
		
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
		setBackground(Color.WHITE);
		
		//Set background colors
		panel.setBackground(Color.LIGHT_GRAY);
		bluePanel.setBackground(new Color(0, 0, 255, 150));
		redPanel.setBackground(new Color(255, 0, 0, 150));	
		
		//Add Board resizer
		addComponentListener(new ComponentAdapter() {
		    @Override
		    public void componentResized(ComponentEvent e) {
		        adjustBoardSize();
		    }
		});
	}
	//Board resizer function from ChatGPT. 
	//Chat prompt: "How can I make my board size adjust to the size of the screen rather than stay a set size?"
	private void adjustBoardSize() {
	    int availableWidth = getContentPane().getWidth() - 200;  // Leave space for side panels
	    int availableHeight = getContentPane().getHeight() - 100; // Leave space for top/bottom UI

	    int minSize = Math.min(availableWidth, availableHeight);
	    CELL_SIZE = minSize / game.getSize();

	    CELL_PADDING = CELL_SIZE / 6;
	    SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;

	    // Update board size
	    gameBoardCanvas.setPreferredSize(new Dimension(CELL_SIZE * game.getSize(), CELL_SIZE * game.getSize()));
	    gameBoardCanvas.revalidate();
	    gameBoardCanvas.repaint();
	}
	
	//Blue S-O selection
	private class BlueRadioButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(bs.isSelected()) {
				game.setBlueLetter(sORo.S);
			}
			else if(bo.isSelected()){
				game.setBlueLetter(sORo.O);
			}
		}
	}
	//Red S-O selection
	private class RedRadioButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(rs.isSelected()) {
				game.setRedLetter(sORo.S);
			}
			else if(ro.isSelected()){
				game.setRedLetter(sORo.O);
			}
		}
	}
	//Game mode listener
	private class gameModeListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(simple.isSelected()) {
				game = new SOSsimple();
			}
			else if(general.isSelected()){
				game = new SOSgeneral();
			}
			//reset
			game.resetGame();
			bs.setSelected(true);
			rs.setSelected(true);
			sizeField.setSelectedIndex(0);
			revalidate();
			repaint();
			adjustBoardSize();
			//Lines need to go away if previous game was active before mode changed
	        drawnSOS.clear();
		}
	}
	
	private class boardSizeListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Integer selection = (Integer) sizeField.getSelectedItem();
			game.setSize(selection);
			//Call board size changer
			adjustBoardSize();
			//reset
			game.resetGame();
			bs.setSelected(true);
			rs.setSelected(true);
	        //Remove old canvas and create a new one
	        gameBoardCanvas.setPreferredSize(new Dimension(CELL_SIZE * game.getSize(), CELL_SIZE * game.getSize()));
	        gameBoardCanvas.revalidate();
	        gameBoardCanvas.repaint();
	        //Lines need to go away if previous game was active before mode changed
	        drawnSOS.clear();
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
					}
					else {
						resetGameGUI();
					}
					repaint();
				}
			});
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(Color.WHITE);
			drawGridLines(g);
			drawBoard(g);
			drawSOSLines(g);
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
			int fontSize = CELL_SIZE / 2; // Want size to be half of grid
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
		//Function to draw lines through SOS'
		private void drawSOSLines(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(5));
			Color color;
			//Set color
			if(game.getTurn() == 'B')
				color = Color.BLUE;
			else
				color = Color.RED;
			
			Cell s = Cell.S;
			Cell o = Cell.O;
			int size = game.getSize();
			
			//Check rows and columns
			for(int i = 0; i < size; i++) {
				for(int j = 0; j < size-2; j++) {
					//check for S-O-S and draw line
					if(game.getBoardCell(i,j) == s && game.getBoardCell(i,j+1) == o && game.getBoardCell(i,j+2) == s) {
						String key = i + "," + j + " " + i + ',' + (j+2);
						if(!drawnSOS.containsKey(key)) {
							drawnSOS.put(key, color);
							drawLineThroughCells(g2d, i, j, i, j + 2);
						}
					}
					if(game.getBoardCell(j,i) == s && game.getBoardCell(j+1,i) == o && game.getBoardCell(j+2,i) == s) {
						String key = j + "," + i + " " + (j+2) + ',' + i;
						if(!drawnSOS.containsKey(key)) {
							drawnSOS.put(key, color);
							drawLineThroughCells(g2d, j, i, j + 2, i);
						}
					}
				}
			}
			//Check Diagonals for sos
			for(int x = 0; x < size-2; x++) {
				for(int y = 0; y < size-2; y++) {
					//check for S-O-S and draw line
					if(game.getBoardCell(x,y) == s && game.getBoardCell(x+1,y+1) == o && game.getBoardCell(x+2,y+2) == s) {
						String key = x + "," + y + " " + (x+2) + ',' + (y+2);
						if(!drawnSOS.containsKey(key)) {
							drawnSOS.put(key, color);
							drawLineThroughCells(g2d, x, y, x + 2, y + 2);
						}
					}
					if(game.getBoardCell(size-1-x,y) == s && game.getBoardCell(size-2-x,y+1) == o && game.getBoardCell(size-3-x,y+2) == s) {
						String key = (size-1-x) + "," + y + " " + (size-3-x) + ',' + (y+2);
						if(!drawnSOS.containsKey(key)) {
							drawnSOS.put(key, color);
							drawLineThroughCells(g2d, size-1-x, y, size - 3-x, y + 2);
						}
					}
				}
			}
			//This splits the map so the drawn lines can remain the correct color. split the string into the rows and columns
			for (Map.Entry<String, Color> entry : drawnSOS.entrySet()) {
		        String[] parts = entry.getKey().split(" ");
		        String[] startPoint = parts[0].split(",");
		        String[] endPoint = parts[1].split(",");
		        int row1 = Integer.parseInt(startPoint[0]);
		        int col1 = Integer.parseInt(startPoint[1]);
		        int row2 = Integer.parseInt(endPoint[0]);
		        int col2 = Integer.parseInt(endPoint[1]);

		        g2d.setColor(entry.getValue());
		        drawLineThroughCells(g2d, row1, col1, row2, col2);
		    }
		}
		//The actual draw line function
		private void drawLineThroughCells(Graphics2D g2d, int row1, int col1, int row2, int col2) {
		    int x1 = col1 * CELL_SIZE + CELL_SIZE / 2;
		    int y1 = row1 * CELL_SIZE + CELL_SIZE / 2;
		    int x2 = col2 * CELL_SIZE + CELL_SIZE / 2;
		    int y2 = row2 * CELL_SIZE + CELL_SIZE / 2;

		    g2d.drawLine(x1, y1, x2, y2);
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
			} 
			else if (game.getGameState() == GameState.DRAW) {
				gameStatusBar.setForeground(Color.MAGENTA);
				gameStatusBar.setText("It's a Draw! Click to play again.");
			}
			else if (game.getGameState() == GameState.BLUE_WON) {
				gameStatusBar.setForeground(Color.BLUE);
				gameStatusBar.setText("Blue Won! Click to play again.");
			}
			else if (game.getGameState() == GameState.RED_WON) {
				gameStatusBar.setForeground(Color.RED);
				gameStatusBar.setText("Red Won! Click to play again.");
			}
		}
		//reset GUI
		public void resetGameGUI() {
			drawnSOS.clear();
			game.resetGame();
			bs.setSelected(true);
			rs.setSelected(true);
		}

	}
	public static void main(String args[]) {
		SOSGameGUI game2 = new SOSGameGUI();
		game2.setVisible(true);
	}
}
