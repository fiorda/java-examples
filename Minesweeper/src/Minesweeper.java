import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.*;
import java.util.Calendar;
import java.util.Random;

import javax.swing.*;


public class Minesweeper{

	/**
	 * @param args
	 */
	
	private static class Grid{
		private int COLS;
		private int ROWS;
		private int n_mines;
		private int SIZE = 21;
		private int length, height;
		private long start, end, time;
		private int revealed;
		private int[][] cells;
		private boolean[][] clicked;
		private boolean[][] flagged;
		private boolean win;
		private boolean newgame = true;
		private boolean inGame = true;
		private Calendar calendar = Calendar.getInstance();
		private Color[] colors = {Color.blue, Color.green, Color.red,
				new Color(10,14,125), new Color(77,11,11), Color.cyan, Color.black, Color.darkGray};
		
		private Grid(int COLS, int ROWS, int n_mines){
			this.COLS = COLS;
			this.ROWS = ROWS;
			this.n_mines = n_mines;
			length = SIZE*COLS;
			height = SIZE*ROWS;
			cells = new int[COLS][ROWS];
			clicked = new boolean[COLS][ROWS];
			flagged = new boolean[COLS][ROWS];
			revealed = ROWS*COLS - n_mines;
		}
		
		public Dimension getDimension(){
			return new Dimension(length, height);
		}
		
		public void click(Point p, int button){
			int x = (p.x)/SIZE;
			int y = (p.y)/SIZE;
			if(button == 3){
				flagged[x][y] = !flagged[x][y];
				return;
			}
			if(inGame){					//Game in progress
				clickCell(x, y);
			}
			else{						//Click after game ended starts new game
				newgame = true;
				clicked = new boolean[COLS][ROWS];
				flagged = new boolean[COLS][ROWS];
				win = false;
				inGame = true;
				cells = new int[COLS][ROWS];
				revealed = COLS*ROWS - n_mines;
			}
		}
		
		private void setMines(){		//Called after first click to randomly select mine spots
			int[] mines = new int[n_mines];
			Random generator = new Random();
			int i = 0;
			while(i < n_mines){
				int m = generator.nextInt(COLS*ROWS);
				boolean isIn = false;
				for(int j=0; j < i; j++){
					if(mines[j] == m){
						isIn = true;
					}
				}
				if(clicked[m%COLS][m/COLS]){
					isIn = true;
				}
				if(!isIn){
					mines[i] = m;
					i++;
				}
			}
			for(int j=0; j<i; j++){		//Places hints on the board
				int x = mines[j]%COLS;
				int y = mines[j]/COLS;
				if(x>0){
					cells[x-1][y]++;
					if(y>0){
						cells[x-1][y-1]++;
					}
					if(y<ROWS-1){
						cells[x-1][y+1]++;
					}
				}
				if(x<COLS-1){
					cells[x+1][y]++;
					if(y>0){
						cells[x+1][y-1]++;
					}
					if(y<ROWS-1){
						cells[x+1][y+1]++;
					}
				}
				if(y>0){
					cells[x][y-1]++;
				}
				if(y<ROWS-1){
					cells[x][y+1]++;
				}
			}
			for(int j=0; j<i; j++){
				int x = mines[j]%COLS;
				int y = mines[j]/COLS;
				cells[x][y] = -1;
			}
		}
		
		private void clickCell(int x, int y){
			if(x<0||x>=COLS||y<0||y>=ROWS||clicked[x][y]||flagged[x][y]){
				return;
			}
			clicked[x][y] = true;
			if(newgame){			//Sets the mines after first click
				newgame = false;
				start = calendar.getTimeInMillis();
				this.setMines();
			}
			if(cells[x][y]==0){		//Hit a zero mines spot
				for(int i = -1; i < 2; i++){
					for(int j = -1; j < 2; j++){
						clickCell(x+i, y+j);
					}
				}
			}
			if(cells[x][y]>=0){		//Hit safe spot
				revealed--;
				if(revealed==0){
					victory();
				}
			}
			if(cells[x][y]==-1){	//Hit mine
				cells[x][y]=-2;
				inGame = false;
				for(int i = 0; i < COLS; i++){
					for (int j = 0; j < ROWS; j++){
						if(cells[i][j]==-1){
							clicked[i][j]=true;
						}
					}
				}
			}
		}

		public void drawGrid(Graphics g){
			g.setColor(Color.gray);
			for(int i = 0; i < COLS; i++){
				g.drawLine(i*SIZE, 0 ,i*SIZE ,height );
			}
			for(int i = 0; i < ROWS; i++){
				g.drawLine(0, i*SIZE, length, i*SIZE);
			}
			for(int i = 0; i < ROWS; i++){
				for(int j = 0; j< COLS; j++){
					if(clicked[i][j]){
						drawNum(i, j, g);
					}
					else{
						drawCell(i, j, g);
					}
				}
			}
			if(win){
				img.drawWin(length, height, time, g);
			}
		}
		
		private void drawNum(int i, int j, Graphics g){
			int x = cells[i][j];
			if(x == -2){
				g.setColor(Color.RED);
				g.fillRect(i*SIZE+1, j*SIZE+1, SIZE, SIZE);
			}
			if(x < 0){
				img.drawBomb(i*SIZE, j*SIZE, SIZE, g);
			}
			else if(x!=0){
				g.setColor(colors[x-1]);
				g.drawString(String.valueOf(x), i*SIZE+7, j*SIZE+15);
			}
		}
		
		private void drawCell(int i, int j, Graphics g){
			for(int z = 0; z<3; z++){
				g.setColor(Color.WHITE);
				g.drawLine(i*SIZE, j*SIZE+z, (i+1)*SIZE-z-1, j*SIZE+z);
				g.drawLine(i*SIZE+z, j*SIZE, i*SIZE+z, (j+1)*SIZE-z-1);
				g.setColor(Color.GRAY);
				g.drawLine(i*SIZE+z+1, (j+1)*SIZE-z-1, (i+1)*SIZE-1, (j+1)*SIZE-z-1);
				g.drawLine((i+1)*SIZE-z-1, j*SIZE+z+1, (i+1)*SIZE-z-1, (j+1)*SIZE-1);
			}
			if(flagged[i][j]){
				img.drawFlag(i*SIZE, j*SIZE, SIZE, g);
			}
		}
		
		private void victory(){
			inGame = false;
			win = true;
			calendar = Calendar.getInstance();
			end = calendar.getTimeInMillis();
			time = (end-start)/1000;
			
		}
		
	}

	
	
	private static class Board extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Dimension dim;
		private Grid grid;
		
		private Board(int cols, int rows, int mines){
			grid = new Grid(cols, rows, mines);
			dim = grid.getDimension();
			this.setBackground(Color.lightGray);
			this.addMouseListener(new MouseClick());
		}
		
		public Dimension getPreferredSize(){
			return dim;
		}
		
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.setFont(new Font("SansSerif", Font.BOLD, 12));
			grid.drawGrid(g);
		}
		
		public void click(Point p, int button){
			grid.click(p, button);
		}

	}
	
	private static class MouseClick implements MouseListener{

		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {
			Point p = e.getPoint();
			int button = e.getButton();
			Board source = (Board)e.getSource();
			source.click(p, button);
			source.repaint();
		}
		public void mouseReleased(MouseEvent e) {}
		
	}
	
	public static void main(String[] args) {
		// main
		JFrame window = new JFrame("Minesweeper!");
		Board board = new Board(16,16,10);
		window.setContentPane(board);
		window.pack();
		window.setVisible(true);
	}

}
