import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Point;

public class Fractals {
	
	public static int iterate(double x, double y, CPoint c){
		int i = 0;
		while(i<100 && Math.pow(x, 2) + Math.pow(y, 2)< 4){
			double xn = Math.pow(x, 2) - Math.pow(y, 2) + c.x;
			y = 2*x*y + c.y;
			x = xn;
			i++;
		}
		return i;
	}
	
	public static class CPoint{
		double x;
		double y;
		
		public CPoint(double x, double y){
			this.x = x;
			this.y = y;
		}
		
	}
	
	public static class Fractal{
		private int size;
		private Dimension dim = new Dimension(400,400);	//Window dimension
		private CPoint TL = new CPoint(-2, 2);			//Top left window point
		private CPoint BR = new CPoint(2, -2);			//Bottom right window point
		private CPoint center = new CPoint(0,0);
		private CPoint c = new CPoint(0,1);
		private Double stepX, stepY;
		private int[][] iterTable = new int[dim.height][dim.width];
		
		public Fractal(){
			this(400, new CPoint(-2,2), new CPoint(2,-2), new CPoint(0,1));
		}
		
		public Fractal(int size, CPoint TL, CPoint BR, CPoint c){
			this.size = size;
			this.dim = new Dimension(size, size);
			this.TL = TL;
			this.BR = BR;
			this.c = c;
			stepX = (BR.x - TL.x)/dim.width;
			stepY = (TL.y - BR.y)/dim.height;
		}

		private void recalculate(){
			for(int i = 0; i < dim.width; i++){
				for(int j = 0; j < dim.height; j++){
					iterTable[i][j] = iterate(TL.x + i*stepX, TL.y - j*stepY, c);
				}
			}
		}
		
		public void draw(Graphics g, Point offset){
			for(int i = 0; i < dim.width; i++){
				for(int j = 0; j < dim.height; j++){
					if(i-offset.x<0||j-offset.y<0||i-offset.x>=dim.width||j-offset.y>=dim.height){
						g.setColor(Color.black);
					}
					else{
						g.setColor(new Color(iterTable[i-offset.x][j-offset.y]*255/100, 0, 0) );
					}
					g.drawLine(i, j, i, j);
				}
			}
		}
		
		public Dimension getDimension(){
			return dim;
		}
		
		public void setC(CPoint c){
			this.c = c;
			center = new CPoint(0,0);
			TL = new CPoint(-2, 2);
			BR = new CPoint(2, -2);
			stepX = (BR.x - TL.x)/dim.width;
			stepY = (TL.y - BR.y)/dim.height;
			recalculate();
		}
		
		public void moveCenter(Point p){
			center.x = center.x - p.x*stepX;
			center.y = center.y + p.y*stepY;
			moveWindow();
		}
		
		public void zoomIn(Point p){
			center.x = TL.x + p.x*stepX;
			center.y = TL.y - p.y*stepY;
			stepX = stepX/2;
			stepY = stepY/2;
			moveWindow();
		}
		
		private void moveWindow(){
			TL = new CPoint(center.x - stepX*dim.width/2, center.y + stepY*dim.height/2);
			BR = new CPoint(center.x + stepX*dim.width/2, center.y - stepY*dim.height/2);
			recalculate();
		}
		
	}
	
	public static class Board extends JPanel implements MouseListener, MouseMotionListener{
		/**
		 * 
		 */
		private static final long serialVersionUID = 8674756555959859080L;
		private Fractal fractal = new Fractal();
		private Point start = new Point(0,0);
		private Point end = new Point(0,0);
		private Point offset = new Point(0,0);
		
		public Board(){
			addMouseListener(this);
			addMouseMotionListener(this);
			setDoubleBuffered(true);
			fractal.setC(new CPoint(0.285, 0.01));
		}
		
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			fractal.draw(g, offset);
		}
		
		public Dimension getPreferredSize(){
			return fractal.getDimension();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			Point p = e.getPoint();
			int button = e.getButton();
			if(button==1){
				//fractal.setNewCenter(p);
			}
			else{
				fractal.zoomIn(p);
			}
			repaint();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent e) {
			start = e.getPoint();
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			fractal.moveCenter(offset);
			offset = new Point(0,0);
			repaint();
		}
		
		@Override
		public void mouseMoved(MouseEvent e){}
		
		@Override
		public void mouseDragged(MouseEvent e){
			end = e.getPoint();
			offset = new Point(end.x - start.x, end.y - start.y);
			repaint();
		}
		
		public void newc(CPoint p){
			fractal.setC(p);
			repaint();
		}
		
	} 
	
	public static class Container extends JPanel{
		Board board = new Board();
		JTextField input = new JTextField("0.285 0.01");
		TextField text = new TextField();
		
		public class TextField implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String num = arg0.getActionCommand();
				int i = num.indexOf(" ");
				double x = Double.valueOf(num.substring(0, i));
				double y = Double.valueOf(num.substring(i+1));
				board.newc(new CPoint(x,y));
			}
		}
		
		public Container(){
			input.addActionListener(text);
			setLayout(new BorderLayout());
			add(board, BorderLayout.CENTER);
			add(input, BorderLayout.SOUTH);
		}

	}
	
	public static void main(String[] args) {
		JFrame window = new JFrame("Fractals");		
		Container panel = new Container();
		window.setContentPane(panel);
		window.pack();
		window.setVisible(true);
	}

}
