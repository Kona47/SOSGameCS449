package sos;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;

import sos.SOSGame.*;
import java.util.*;
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
	private JRadioButton bHuman;
	private JRadioButton rHuman;
	private JRadioButton bComputer;
	private JRadioButton rComputer;
	private JButton record;
	private JButton replay;

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
		//Human/Computer buttons
		bHuman = new JRadioButton("Human", true);
		rHuman = new JRadioButton("Human", true);
		bComputer = new JRadioButton("Computer", false);
		rComputer = new JRadioButton("Computer", false);

		bComputer.addActionListener(new SOSsoloListener());
		rComputer.addActionListener(new SOSsoloListener());
		bComputer.setForeground(Color.WHITE);
		rComputer.setForeground(Color.WHITE);
		
		bHuman.addActionListener(new SOSsoloListener());
		rHuman.addActionListener(new SOSsoloListener());
		bHuman.setForeground(Color.WHITE);
		rHuman.setForeground(Color.WHITE);

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
		
		//Record and replay
		record = new JButton("Record");
		replay = new JButton("Replay");
		replay.setEnabled(false);
		replay.setVisible(false);;
		record.addActionListener(new RecordListener());
		replay.addActionListener(new ReplayListener());
		
		//These groups are for S O buttons for blue/red players and for Human/Computer
		ButtonGroup moveGroupBlue = new ButtonGroup();
		moveGroupBlue.add(bs);
		moveGroupBlue.add(bo);
		
		ButtonGroup blueComputer = new ButtonGroup();
		blueComputer.add(bHuman);
		blueComputer.add(bComputer);
		
		ButtonGroup redComputer = new ButtonGroup();
		redComputer.add(rHuman);
		redComputer.add(rComputer);
		
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
		JLabel b = new JLabel("Blue Player");
		b.setForeground(Color.WHITE);
		b.setFont(new Font("Arial", Font.BOLD, 14));
        bluePanel.add(b);
        bluePanel.setPreferredSize(new Dimension(100, 300));
        bluePanel.add(bHuman);
		bluePanel.add(bs);
		bluePanel.add(bo);
		bluePanel.add(bComputer);
		
		//panel for red side
		JLabel r = new JLabel("Red Player");
		r.setForeground(Color.WHITE);
		r.setFont(new Font("Arial", Font.BOLD, 14));
        redPanel.add(r);
        redPanel.setPreferredSize(new Dimension(100, 300)); 
        redPanel.add(rHuman);
		redPanel.add(rs);
		redPanel.add(ro);
		redPanel.add(rComputer);
		//Record/Replay
		redPanel.add(record);
		redPanel.add(replay);

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
			bHuman.setSelected(true);
			rHuman.setSelected(true);
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
			if (game.getBSolo() && game.getRSolo()) 
			    CvCGameTimer();
			
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
	
	//Action listener for solo game vs computer. 
	//The downcasting code snippet I learned from ChatGPT, I did not know this was a thing in Java
	//Only way to call makeCMove() since game is viewed as just an SOSGame()
	private class SOSsoloListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			if(bComputer.isSelected()) {
				game.setBSolo(true);
				/*bs.setVisible(false);
				bo.setVisible(false);*/
				
				//If both computers selected, play computer game
				if(game.getRSolo() == true) {
					CvCGameTimer();
			        repaint();
				}
		        
		        // Check if a move needs to be made immediately
				else if(game.getTurn() == 'B' && game.getGameState() == GameState.PLAYING) {
		        	if (game instanceof SOSsimple) 
		        		((SOSsimple) game).makeCMove();
			        else if (game instanceof SOSgeneral) 
			            ((SOSgeneral) game).makeCMove();
		        	repaint();
		        }
			}
			
			if(rComputer.isSelected()) {
				game.setRSolo(true);
				/*rs.setVisible(false);
				ro.setVisible(false);*/
				
				//If both computers selected, play computer game
				if(game.getBSolo() == true) {
					CvCGameTimer();
			        repaint();
				}
				
				// Check if a move needs to be made immediately
				else if(game.getTurn() == 'R' && game.getGameState() == GameState.PLAYING) {
		        	
		        	if (game instanceof SOSsimple) 
		        		((SOSsimple) game).makeCMove();
			        else if (game instanceof SOSgeneral) 
			            ((SOSgeneral) game).makeCMove();
		        }
			}
			
			if(bHuman.isSelected()) {
				game.setBSolo(false);
				/*bs.setVisible(true);
				bo.setVisible(true);*/
			}
			if(rHuman.isSelected()) {
				game.setRSolo(false);
				/*rs.setVisible(true);
				ro.setVisible(true);*/
			}
		}
	}
	
	private class RecordListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			game.setRecording(true);
		    replay.setEnabled(true);
		    replay.setVisible(true);
		}
	}
	
	//Read in the recorded game to replay
	public void replayGame() {
		File file = new File(System.getProperty("user.home") + "/Desktop/lastGame.txt");
		Scanner input = null;
		try {
			input = new Scanner(file);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
			
		String mode = input.nextLine().trim(); // "Simple" or "General"
		int size = Integer.parseInt(input.nextLine().trim()); // Read the second line safely
		//Set the mode
		if(mode.charAt(0) == 'S') {
			new SOSsimple();
			simple.setSelected(true);
		}
		else {
			new SOSgeneral();
			general.setSelected(true);
		}
		//Set the size	
		game.setSize(size);
		sizeField.setSelectedIndex(size-3);
		adjustBoardSize();
		
		// Store moves in a list
		ArrayList<String> moveList = new ArrayList<>();
		while (input.hasNextLine()) {
			String line = input.nextLine().trim();
			if (!line.isEmpty()) moveList.add(line);
		}
		input.close();
		
		//Create timer for simulation of replay
		Timer timer = new Timer(500, null);
		int[] index = {0};
		//Variables for the details of the move. x y coords, t - turn, l - letter
		timer.addActionListener(e -> {
			if (index[0] >= moveList.size()) {
				((Timer)e.getSource()).stop();
				System.out.println("Replay finished!");
				return;
			}
		
			String line = moveList.get(index[0]);
			index[0]++;
				
			int x = Integer.parseInt(line.substring(1, 2));
			int y = Integer.parseInt(line.substring(3, 4));
			char t = line.charAt(6);
			char l = line.charAt(8);
			// Setting the right letter for the player on gui and in game	
			if(t == 'B') {
				if(l == 'S') {
					game.setBlueLetter(sORo.S);		
					bs.setSelected(true);
				}
				else {
					game.setBlueLetter(sORo.O);		
					bo.setSelected(true);
				}
			}
			else {
				if(l == 'S') {
					game.setRedLetter(sORo.S);		
					rs.setSelected(true);
				}
				else {
					game.setRedLetter(sORo.O);		
					ro.setSelected(true);
				}
			}
			
			game.makeMove(x,y);
			revalidate();
			repaint();
		});
			
		timer.start();
		System.out.println("Game Replayed Successfuly!");
	}
	
	private class ReplayListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			game.setRSolo(false);
			game.setBSolo(false);
			replayGame();	
			
		}
	}
	
	//This function is to run computer vs computer game with step by step moves
	private void CvCGameTimer() {
	    Timer timer = new Timer(500, null);
	    timer.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            if (game.getGameState() != GameState.PLAYING || game.isFull()) {
	                ((Timer) e.getSource()).stop();
	                return;
	            }

	            if (game.getTurn() == 'B' && game.getBSolo()) {
	                if (game instanceof SOSsimple)
	                    ((SOSsimple) game).makeCMove();
	                else if (game instanceof SOSgeneral)
	                    ((SOSgeneral) game).makeCMove();
	            } else if (game.getTurn() == 'R' && game.getRSolo()) {
	                if (game instanceof SOSsimple)
	                    ((SOSsimple) game).makeCMove();
	                else if (game instanceof SOSgeneral)
	                    ((SOSgeneral) game).makeCMove();
	            }

	            repaint();
	        }
	    });

	    timer.setInitialDelay(0);
	    timer.start();
	}
	
	class GameBoardCanvas extends JPanel{

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
					if (game.getBoardCell(row, col) == Cell.O) {
						g2d.setColor(Color.BLACK);
						g2d.drawString("O", x, y);
					} 
					else if (game.getBoardCell(row, col) == Cell.S) {
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
			if(game.getLastScorer() == 'B')
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
					/*if(game.getGameMode() == GameMode.GENERAL)
						gameStatusBar.setText("Blue's Turn" + ((SOSgeneral) game).getBScore() + " - "  + ((SOSgeneral) game).getRScore());
					else*/
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
			if (game.getBSolo() && game.getRSolo()) 
			    CvCGameTimer();
			
			
		}

	}
	
	public static void main(String args[]) {
		SOSGameGUI game2 = new SOSGameGUI();
		game2.setVisible(true);
	}
}
