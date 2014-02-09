package game;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.RenderingHints;

public class GWindow extends JPanel implements KeyListener, ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9204760016006593620L;
	private final int WINDOW_WIDTH;
	private final int WINDOW_HEIGHT;
	private final int CELL = 20;
	private final Timer time = new Timer(50, this);
	private final Player player;
	
	public GWindow(int width, int height){
		WINDOW_WIDTH = width;
		WINDOW_HEIGHT = height;
		player = new Player(WINDOW_WIDTH/2, WINDOW_HEIGHT/2);
		setFocusable(true);
		addKeyListener(this);
		setDoubleBuffered(true);
		setBackground(Color.WHITE);
		time.start();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		drawWindow(g2);
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	
	private void drawWindow(Graphics g){
		g.drawRect(1, 1, 797, 597);
		drawMap(g);
		drawPlayer(g, player);
	}
	
	private void drawMap(Graphics g){
		
	}
	
	private void drawPlayer(Graphics g, Player player){
		int x = player.getPosX();
		int y = player.getPosY();
		g.setColor(Color.GREEN);
		g.fillOval(x - CELL/2, y - CELL/2, CELL, CELL);
	}
	
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getKeyCode()){
			case KeyEvent.VK_UP:{
				player.moveY(-1);
				break;
			} 
			case KeyEvent.VK_DOWN:{
				player.moveY(1);
				break;
			}
			case KeyEvent.VK_RIGHT:{
				player.moveX(1);
				break;
			}
			case KeyEvent.VK_LEFT:{
				player.moveX(-1);
				break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getKeyCode()){
		case KeyEvent.VK_UP:{
			player.stopY();
			break;
		} 
		case KeyEvent.VK_DOWN:{
			player.stopY();
			break;
		}
		case KeyEvent.VK_RIGHT:{
			player.stopX();
			break;
		}
		case KeyEvent.VK_LEFT:{
			player.stopX();
			break;
		}
	}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		player.update();
		repaint();
	}

}
