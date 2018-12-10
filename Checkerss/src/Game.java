/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	
	private Canvas canvas;
	private TurnBox turnbox;
	private ScoreBox scorebox;
	
	private boolean redTurn = true;
	private boolean notChecker = false;
	private boolean firstPress = false;
	private boolean notJump = false;
	private boolean notMove = false;
	private boolean jumpAvailableYellow = false;
	private boolean jumpAvailableRed = false;
	private boolean gameOver = false;
	private boolean moreJumps = false;
	private boolean redWin = false;

	private UndoObject tempUndo;
	private List<UndoObject> undo = new LinkedList<>();
	private int finalScore = 0;
	private int highscore1 = 0;
	private int highscore2 = 0;
	private int highscore3 = 0;
	private String person1 = "";
	private String person2 = "";
	private String person3 = "";
	private String player1 = "player1";
	private String player2 = "player2";
	private int storedX;
	private int storedY;
	private Gameboard checkerBoard = new Gameboard();
	
	@SuppressWarnings("serial")
	private class Canvas extends JPanel {

		@Override
		public void paintComponent(Graphics gc) {
			
			//setup Game board
			super.paintComponent(gc);
			gc.setColor(Color.WHITE);
			for (int i = 0; i < 400; i = i + 100) {
				for (int j = 0; j < 400; j = j + 100) {
					gc.fillRect(i, j, 50, 50);
				}	
			}
			for (int i = 50; i < 400; i = i + 100) {
				for (int j = 50; j < 400; j = j + 100) {
					gc.fillRect(i, j, 50, 50);
				}	
			}
			
			gc.setColor(Color.BLACK);
			for (int i = 50; i < 400; i = i + 100) {
				for (int j = 0; j < 400; j = j + 100) {
					gc.fillRect(i, j, 50, 50);
				}	
			}
			for (int i = 0; i < 400; i = i + 100) {
				for (int j = 50; j < 400; j = j + 100) {
					gc.fillRect(i, j, 50, 50);
				}	
			}
			
			// other repaint stuff later
			if (!gameOver) {
				checkerBoard.draw(gc);
			}
			else {
				gc.setColor(Color.WHITE);
				gc.fillRect(0, 0, 400, 400);
				checkerBoard.drawGameOver(gc);
				gc.setColor(Color.ORANGE);
				
				// add high scores and show who wins 
				if (redWin) {
					//red
					finalScore = checkerBoard.numRedPieces() + 2 * checkerBoard.numRedKings();
					drawString(gc, player1 + " Wins!" + "\nFinal Score:" + finalScore, 170, 230);
				}
				else {
					//yellow
					finalScore = checkerBoard.numYellowPieces() + 2 * checkerBoard.numYellowKings();
					drawString(gc, player2 + " Wins!" + "\nFinal Score:" + finalScore, 170, 230);
				}
				
				writeOut();
			}
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(400, 400);
		}

		public Canvas() {
			super();
		}
	}
	
	private void writeOut() {
		
		// potentially update high scores
		boolean notWork = true;
		if (finalScore > highscore1 && notWork) {
			person3 = person2;
			highscore3 = highscore2;
			person2 = person1;
			highscore2 = highscore1;
			highscore1 = finalScore;
			if (redWin) {
				person1 = player1;
			}
			else {
				person1 = player2;
			}
			notWork = false;
		}
		else if (finalScore > highscore2 && notWork) {
			person3 = person2;
			highscore3 = highscore2;
			highscore2 = finalScore;
			if (redWin) {
				person2 = player1;
			}
			else {
				person2 = player2;
			}
			notWork = false;
		}
		else if (finalScore > highscore3 && notWork) {
			highscore3 = finalScore;
			if (redWin) {
				person3 = player1;
			}
			else {
				person3 = player2;
			}
		}
		else {
			
		}
		//write out stuff to file with IO
		FileWriter writer;
		try {
			writer = new FileWriter("files/highscores.txt", false);
			String person1Caps = person1.substring(0,1).toUpperCase() + person1.substring(1).toLowerCase();
			String person2Caps = person2.substring(0,1).toUpperCase() + person2.substring(1).toLowerCase();
			String person3Caps = person3.substring(0,1).toUpperCase() + person3.substring(1).toLowerCase();
			writer.write(person1Caps + "\n");
			writer.write(highscore1 + "\n");
			writer.write(person2Caps + "\n");
			writer.write(highscore2 + "\n");
			writer.write(person3Caps + "\n");
			writer.write(highscore3 + "\n");
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("serial")
	private class TurnBox extends JPanel {
		@Override
		public void paintComponent(Graphics gc) {
			
			//setup Game board
			super.paintComponent(gc);
			if (redTurn) {
				gc.setColor(Color.RED);
				gc.fillRect(0, 0, 400, 20);
			}
			else {
				gc.setColor(Color.YELLOW);
				gc.fillRect(0, 0, 400, 20);
			}
			
			gc.setColor(Color.BLACK);
			
			if (notChecker) {
				notChecker = false;
				gc.drawString("Please Select a Piece to Move", 50, 10);
			}
			if (notJump) {
				notJump = false;
				gc.drawString("Please Select a Jump Because One is Available", 50, 10);
			}
			if (notMove) {
				notMove = false;
				gc.drawString("This is Not a Valid Move", 50, 10);
			}
			if (jumpAvailableRed || jumpAvailableYellow) {
				jumpAvailableRed = false;
				jumpAvailableYellow = false;
				gc.drawString("There is a Jump Available that You Must Take", 50, 10);
			}
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(400, 25);
		}

		public TurnBox() {
			super();
		}
	}
	
	private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
	
	@SuppressWarnings("serial")
	private class ScoreBox extends JPanel {
		
		
		
		@Override
		public void paintComponent(Graphics gc) {
			
			//setup Game board
			super.paintComponent(gc);
			gc.setColor(Color.BLACK);
			drawString(gc, "Yellow\nPieces:\n" + checkerBoard.numYellow(), 5, 50);
			drawString(gc, "Red\nPieces:\n" + checkerBoard.numRed(), 5, 300);
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(50, 400);
		}

		public ScoreBox() {
			super();
		}
	}
	
	private JPanel createToolbar() {
		JPanel toolbar = new JPanel();
		
		JButton undoButton = new JButton("Undo");
		toolbar.add(undoButton);
		undoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (undo.size() > 0) {
					UndoObject o = ((LinkedList<UndoObject>) undo).removeLast();
					List<TakenObject> l = o.getTaken();
					while (l.size() != 0) {
						TakenObject t = l.remove(0);
						checkerBoard.putPiece(t.getLoc(), t.getColor());
					}
					checkerBoard.remove(o.getEnd().getLoc());
					checkerBoard.putPiece(o.getStart().getLoc(), o.getStart().getColor());
					

					redTurn = !redTurn;
					firstPress = false;
					moreJumps = false;
					
					canvas.repaint();
					turnbox.repaint();
					scorebox.repaint();
				}
			}
		});
		
		JButton undoClick = new JButton("Reset Piece Selection");
		toolbar.add(undoClick);
		undoClick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firstPress = false;
				turnbox.repaint();
			}
		});

		// exiting the program
		JButton quit = new JButton("Quit");
		toolbar.add(quit);
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		return toolbar;
	}
	
	private class Mouse extends MouseAdapter {

		/**
		 * Code to execute when the button is pressed while the mouse is in the
		 * canvas.
		 *
		 */
		@Override
		public void mousePressed(MouseEvent arg0) {
			Point p = arg0.getPoint();
			int x = p.x / 50;
			int y = p.y / 50;
			
			if (redTurn) {
				if (!firstPress) {
					if (checkerBoard.isRedSquare(x,y)) {
						// check if possible jump square is available
						if (checkerBoard.jumpAvailableRed() && checkerBoard.getpossibleJumps(x, y).isEmpty()) {
							//error
							jumpAvailableRed = true;
							turnbox.repaint();
						}
						else {
							storedX = x;
							storedY = y;
							firstPress = true;
							turnbox.repaint();
						}
					}
					else {
						notChecker = true;
						turnbox.repaint();
					}
				}
				// selecting move location. prioritize jump
				else {
					// there are possible jumps
					if (!checkerBoard.getpossibleJumps(storedX,storedY).isEmpty()) {
						// selected second mouse click is a jump
						if (checkerBoard.getpossibleJumps(storedX,storedY).containsKey
								(new Location(x, y))) {
							Location l = checkerBoard.getpossibleJumps(storedX, storedY).get(new Location(x, y));
							int takenColor = checkerBoard.getColor(l);
							int startColor = checkerBoard.getColor(new Location(storedX, storedY));
							int endColor = checkerBoard.getColor(new Location(x, y));
							checkerBoard.move(storedX, storedY, x, y);
							
							//undo part
							if (!moreJumps) {
								tempUndo = new UndoObject(new TakenObject(new Location(storedX, storedY), startColor)
										, new TakenObject(new Location(x, y), endColor), new TakenObject(l, takenColor));
							}
							else {
								tempUndo.addTo(new TakenObject(l, takenColor));
								tempUndo.updateEnd(new TakenObject(new Location(x, y), endColor));
							}
							
							//check if game is over
							if (checkerBoard.numYellow() == 0) {
								gameOver = true;
								redWin = true;
							}
							canvas.repaint();
							// if more jumps
							if (!checkerBoard.getpossibleJumps(x, y).isEmpty()) {
								storedX = x;
								storedY = y;
								moreJumps = true;
							}
							else {
								undo.add(tempUndo);
								moreJumps = false;
								redTurn = false;
								firstPress = false;
								turnbox.repaint();
							}
							scorebox.repaint();
						}
						// they need to select a jump because one is available
						else {
							notJump = true;
							turnbox.repaint();
						}
					}
					// there are no possible jumps
					else {
						if (checkerBoard.getPossibleMoves(storedX, storedY).contains
								(new Location(x, y))) {
							int startColor = checkerBoard.getColor(new Location(storedX, storedY));
							int endColor = checkerBoard.getColor(new Location(x, y));
							tempUndo = new UndoObject(new TakenObject(new Location(storedX, storedY), startColor), 
									new TakenObject(new Location(x, y), endColor), null);
							undo.add(tempUndo);
							checkerBoard.move(storedX, storedY, x, y);
							canvas.repaint();
							redTurn = false;
							firstPress = false;
							turnbox.repaint();
							scorebox.repaint();
						}
						else {
							notMove = true;
							turnbox.repaint();
						}
					}
				}
			}
			else {
				if (!firstPress) {
					if (checkerBoard.isYellowSquare(x,y)) {
						// check if possible jump square is available
						if (checkerBoard.jumpAvailableYellow() && checkerBoard.getpossibleJumps(x, y).isEmpty()) {
							//error
							jumpAvailableYellow = true;
							turnbox.repaint();
						}
						else {
							storedX = x;
							storedY = y;
							firstPress = true;
							turnbox.repaint();
						}
					}
					else {
						notChecker = true;
						turnbox.repaint();
					}
				}
				// selecting move location. prioritize jump
				else {
					// there are possible jumps
					if (!checkerBoard.getpossibleJumps(storedX,storedY).isEmpty()) {
						// selected second mouse click is a jump
						if (checkerBoard.getpossibleJumps(storedX,storedY).containsKey
								(new Location(x, y))) {
							Location l = checkerBoard.getpossibleJumps(storedX, storedY).get(new Location(x, y));
							int takenColor = checkerBoard.getColor(l);
							int startColor = checkerBoard.getColor(new Location(storedX, storedY));
							int endColor = checkerBoard.getColor(new Location(x, y));
							checkerBoard.move(storedX, storedY, x, y);
							
							//undo part
							if (!moreJumps) {
								tempUndo = new UndoObject(new TakenObject(new Location(storedX, storedY), startColor)
										, new TakenObject(new Location(x, y), endColor), new TakenObject(l, takenColor));
							}
							else {
								tempUndo.addTo(new TakenObject(l, takenColor));
								tempUndo.updateEnd(new TakenObject(new Location(x, y), endColor));
							}
							
							//check if game is over
							if (checkerBoard.numRed() == 0) {
								gameOver = true;
								redWin = false;
							}
							canvas.repaint();
							// if more jumps
							if (!checkerBoard.getpossibleJumps(x, y).isEmpty()) {
								storedX = x;
								storedY = y;
								moreJumps = true;
							}
							else {
								undo.add(tempUndo);
								moreJumps = false;
								redTurn = true;
								firstPress = false;
								turnbox.repaint();
							}
							scorebox.repaint();
						}
						// they need to select a jump because one is available
						else {
							notJump = true;
							turnbox.repaint();
						}
					}
					// there are no possible jumps
					else {
						if (checkerBoard.getPossibleMoves(storedX, storedY).contains
								(new Location(x, y))) {
							int startColor = checkerBoard.getColor(new Location(storedX, storedY));
							int endColor = checkerBoard.getColor(new Location(x, y));
							tempUndo = new UndoObject(new TakenObject(new Location(storedX, storedY), startColor), 
									new TakenObject(new Location(x, y), endColor), null);
							undo.add(tempUndo);
							checkerBoard.move(storedX, storedY, x, y);
							canvas.repaint();
							redTurn = true;
							firstPress = false;
							turnbox.repaint();
							scorebox.repaint();
						}
						else {
							notMove = true;
							turnbox.repaint();
						}
					}
				}
			}
		}
	}
	
	private JLabel instrucLabel() {
		JLabel x = new JLabel("<html> <b> Instructions for Checkers Game: "
				+ "</b> <br> <br> "
				+ "This is the normal checkers game. A normal piece (circles) <br>"
				+ "can only move (diagonally) in one direction. <br>"
				+ "A king piece (squarish shape) can move in both <br>"
				+ "directions. A piece turns into a king by reaching <br>"
				+ "the last row on the other side of the board. <br>"
				+ "You take someone else's piece by jumping over it. <br>"
				+ "You are allowed to double and triple jump in one turn. <br>"
				+ "You must jump and take a piece if a jump is available. <br>"
				+ "The top bar above the board shows <br>"
				+ "whose turn it is. Once you click on a piece, <br> "
				+ "the next click is where you want to move that "
				+ "<br> piece. If you want to move a different piece <br> after clicking on a piece or "
				+ "you are unable to <br> move your selected piece, you can click <br> the reselect piece button."
				+ " You can also undo your <br> move with the undo button. <br> <br>"
				+ "You win by taking all the other color's pieces. <br>"
				+ "The winner's final score is the number of normal <br>"
				+ "pieces + 2 * number of king pieces</html>",
				SwingConstants.CENTER);
		return x;
	}

    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for local variables.

        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
    	JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new BorderLayout());

		canvas = this.new Canvas();
		turnbox = this.new TurnBox();
		scorebox = this.new ScoreBox();
		
		Reader reader;
		BufferedReader br;
		//reading in all the values from file
		try {
			reader = new FileReader("files/highscores.txt");
			br = new BufferedReader(reader);
			
			person1 = br.readLine().trim();
            highscore1 = Integer.parseInt(br.readLine());
            
            person2 = br.readLine().trim();
            highscore2 = Integer.parseInt(br.readLine());
			
            person3 = br.readLine().trim();
            highscore3 = Integer.parseInt(br.readLine()); 
			
		} catch (FileNotFoundException e) {
			System.out.println("No File");
		} catch (IOException e) {
			System.out.println("There aren't three highscores");
		}

		// mouse listener
		Mouse mouseListener = new Mouse();
		canvas.addMouseListener(mouseListener); // press/release events
		
		// game panel
		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new BorderLayout());
		
		gamePanel.add(turnbox, BorderLayout.PAGE_START);
		gamePanel.add(canvas, BorderLayout.CENTER);
		gamePanel.add(createToolbar(), BorderLayout.PAGE_END);
		gamePanel.add(scorebox, BorderLayout.EAST);
		JPanel wrapperPanel = new JPanel(new GridBagLayout());
		wrapperPanel.add(gamePanel);
		
		// instructions panel
		JPanel instrucPanel = new JPanel();
		instrucPanel.setLayout(new BorderLayout());
		JLabel instruc = instrucLabel();
		instrucPanel.add(instruc, BorderLayout.CENTER);
		
		//high scores panel
		JPanel scoresPanel = new JPanel();
		scoresPanel.setLayout(new BorderLayout());
		String person1Caps = person1.substring(0,1).toUpperCase() + person1.substring(1).toLowerCase();
		String person2Caps = person2.substring(0,1).toUpperCase() + person2.substring(1).toLowerCase();
		String person3Caps = person3.substring(0,1).toUpperCase() + person3.substring(1).toLowerCase();
		JLabel scores = new JLabel("<html>" + "High Scores" + "<br><br>" + person1Caps + ": " + 
				highscore1 + "<br>" + person2Caps + ": " + highscore2 + "<br>" + person3Caps + ": " +
				highscore3 + "<br>" + "</html>", SwingConstants.CENTER);
		scoresPanel.add(scores);
		
		//player input names
		player1 = JOptionPane.showInputDialog("Player 1 Name (Red Color):");
		player2 = JOptionPane.showInputDialog("Player 2 Name (Yellow Color):");
		
		// adding panes to frame
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Instructions", instrucPanel);
		tabbedPane.addTab("Game", wrapperPanel);
		tabbedPane.addTab("High Scores", scoresPanel);
		
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
