// imports necessary libraries for Java swing
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Gameboard {
	
	private int[][] board;
	private static final int yellow = 1;
	private static final int red = 2;
	private static final int yellowKing = 3;
	private static final int redKing = 4;
	
	public Gameboard () {
		board = new int[8][8];
		for (int i = 0; i < 3; i++) {
			if (i == 0 || i == 2) {
				for (int j = 1; j < 8; j = j + 2) {
					board[j][i] = yellow;
				}
			}
			else {
				for (int j = 0; j < 8; j = j + 2) {
					board[j][i] = yellow;
				}
			}	
		}
		for (int i = 5; i < 8; i++) {
			if (i == 5 || i == 7) {
				for (int j = 0; j < 8; j = j + 2) {
					board[j][i] = red;
				}
			}
			else {
				for (int j = 1; j < 8; j = j + 2) {
					board[j][i] = red;
				}
			}	
		}
	}
	
	public Set<Location> getPossibleMoves(int x, int y) {
		Set<Location> set = new TreeSet<>();
		if (x < 0 || x >= 8 || y < 0 || y >= 8) {
			return set;
		}
		
		int color = board[x][y];
		
		//initial/normal moves
		if (color == yellow) {
			if ((x+1) < 8 && board[x+1][y+1] == 0) {
				set.add(new Location(x+1, y+1));
			}
			if ((x-1) >= 0 && board[x-1][y+1] == 0) {
				set.add(new Location(x-1, y+1));
			}
		}
		else if (color == red) {
			if ((x+1) < 8 && board[x+1][y-1] == 0) {
				set.add(new Location(x+1, y-1));
			}
			if ((x-1) >= 0 && board[x-1][y-1] == 0) {
				set.add(new Location(x-1, y-1));
			}
		}
		else if (color == yellowKing || color == redKing) {
			if ((x+1) < 8 && (y+1) < 8 && board[x+1][y+1] == 0) {
				set.add(new Location(x+1, y+1));
			}
			if ((x-1) >= 0 && (y+1) < 8 && board[x-1][y+1] == 0) {
				set.add(new Location(x-1, y+1));
			}
			if ((x+1) < 8 && (y-1) >= 0 && board[x+1][y-1] == 0) {
				set.add(new Location(x+1, y-1));
			}
			if ((x-1) >= 0 && (y-1) >= 0 && board[x-1][y-1] == 0) {
				set.add(new Location(x-1, y-1));
			}
		}
		
		return set;
	}
	
	// returns map of jump destination points and the piece taken in the corresponding jump
	
	public Map<Location, Location> getpossibleJumps(int x, int y) {
		int color = board[x][y];
		Map<Location, Location> map = new TreeMap<>();
		if (color == yellow || color == yellowKing) {
			if (isJump(yellow, x, y, x+1, y+1, x+2, y+2)) {
				map.put(new Location(x+2, y+2), new Location(x+1, y+1));
			}
			if (isJump(yellow, x, y, x-1, y+1, x-2, y+2)) {
				map.put(new Location(x-2, y+2), new Location(x-1, y+1));
			}
			
			//backward stuff
			if (color == yellowKing) {
				if (isJump(yellowKing, x, y, x-1, y-1, x-2, y-2)) {
					map.put(new Location(x-2, y-2), new Location(x-1, y-1));
				}
				if (isJump(yellowKing, x, y, x+1, y-1, x+2, y-2) ) {
					map.put(new Location(x+2, y-2), new Location(x+1, y-1));
				}
			}
		}
		if (color == red || color == redKing) {
			if (isJump(red, x, y, x-1, y-1, x-2, y-2)) {
				map.put(new Location(x-2, y-2), new Location(x-1, y-1));
			}
			if (isJump(red, x, y, x+1, y-1, x+2, y-2)) {
				map.put(new Location(x+2, y-2), new Location(x+1, y-1));
			}
			
			//backward stuff
			if (color == redKing) {
				if (isJump(redKing, x, y, x+1, y+1, x+2, y+2)) {
					map.put(new Location(x+2, y+2), new Location(x+1, y+1));
				}
				if (isJump(redKing, x, y, x-1, y+1, x-2, y+2)) {
					map.put(new Location(x-2, y+2), new Location(x-1, y+1));
				}
			}
				
		}
		return map;
	}
	
	
	public boolean isJump(int color, int x1, int y1, int x2, int y2, int x3, int y3) {
		
		if (x3 >= 8 || x3 < 0 || y3 >= 8 || y3 < 0 || board[x3][y3] != 0) {
			return false;
		}
		
		if (color == yellow || color == yellowKing) {
			if (board[x2][y2] == red || board[x2][y2] == redKing) {
				return true;
			}
		}
		else {
			if (board[x2][y2] == yellow || board[x2][y2] == yellowKing) {
				return true;
			}
		}
		return false;
	}
	
	public boolean jumpAvailableRed() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == red || board[i][j] == redKing) {
					if (!getpossibleJumps(i, j).isEmpty()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean jumpAvailableYellow() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == yellow || board[i][j] == yellowKing) {
					if (!getpossibleJumps(i, j).isEmpty()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void move(int xCurr, int yCurr, int xDest, int yDest) {
		int color = board[xCurr][yCurr];
		
		board[xDest][yDest] = color;
		board[xCurr][yCurr] = 0;
		if (Math.abs(xCurr - xDest) == 2) {
			board[(xDest + xCurr)/2][(yDest + yCurr)/2] = 0;
		}
		
		// check if turned into king
		if (color == red && yDest == 0) {
			board[xDest][yDest] = redKing;
		}
		else if (color == yellow && yDest == 7) {
			board[xDest][yDest] = yellowKing;
		}
	}
	
	
	
	public boolean isRedSquare(int x, int y) {
		
		return board[x][y] == red || board[x][y] == redKing;
	}
	
	public boolean isYellowSquare(int x, int y) {
		
		return board[x][y] == yellow || board[x][y] == yellowKing;
	}
	
	public void draw(Graphics gc0) {
		Graphics2D gc = (Graphics2D) gc0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == red) {
					gc.setColor(Color.RED);
					gc.fillOval(i * 50, j * 50, 50, 50);
				}
				else if (board[i][j] == yellow) {
					gc.setColor(Color.YELLOW);
					gc.fillOval(i * 50, j * 50, 50, 50);
				}
				else if (board[i][j] == redKing) {
					gc.setColor(Color.RED);
					gc.fillRoundRect(i * 50, j * 50, 50, 50, 30, 30);
				}
				else if (board[i][j] == yellowKing) {
					gc.setColor(Color.YELLOW);
					gc.fillRoundRect(i * 50, j * 50, 50, 50, 30, 30);
				}
				
			}
		}
	}
	
	public int numRed() {
		int sum = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == red || board[i][j] == redKing) {
					sum ++;
				}
			}
			
		}
		return sum;
	}
	
	public int numYellow() {
		int sum = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == yellow || board[i][j] == yellowKing) {
					sum ++;
				}
			}
			
		}
		return sum;
	}
	
	public void putPiece(Location l, int color) {
		board[l.getX()][l.getY()] = color;

	}
	
	public void remove(Location l) {
		board[l.getX()][l.getY()] = 0;
	}

	
	public void drawGameOver(Graphics gc0) {
		Graphics2D gc = (Graphics2D) gc0;
		gc.setColor(Color.ORANGE);
		gc.drawString("GAME OVER", 170, 200);
	}
	
	public int getColor(Location l) {
		return board[l.getX()][l.getY()];
	}
	
	public void emptyGameBoard() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = 0;
			}
		}
	}
	
	public int numRedKings() {
		int sum = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == redKing) {
					sum++;
				}
			}
		}
		return sum;
	}
	
	public int numRedPieces() {
		int sum = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == red) {
					sum++;
				}
			}
		}
		return sum;
	}
	
	public int numYellowKings() {
		int sum = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == yellowKing) {
					sum++;
				}
			}
		}
		return sum;
	}
	
	public int numYellowPieces() {
		int sum = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == yellow) {
					sum++;
				}
			}
		}
		return sum;
	}
	
	public boolean isGameOver() {
		if (numRed() == 0 || numYellow() == 0) {
			return true;
		}
		return false;
	}
	
}
