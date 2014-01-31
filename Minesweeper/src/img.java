import java.awt.Graphics;
import java.awt.Color;


public class img {
	
	public static void drawBomb(int x, int y, int size, Graphics g){
		g.setColor(Color.black);
		g.fillOval(x+size/4, y+size/4, size/2+1, size/2+1);
		g.drawLine(x+size/4+1, y+size/4+1, x+3*size/4, y+3*size/4);
		g.drawLine(x+3*size/4, y+size/4+1, x+size/4+1, y+3*size/4);
		g.drawLine(x+size/2, y+size/8+2, x+size/2, + y+7*size/8-1);
		g.drawLine(x+size/8+2, y+size/2, x+7*size/8-1, y+size/2);
		g.setColor(Color.white);
		g.fillOval(x+size/2-3, y+size/2-3, 3, 3);
	}
	
	public static void drawWin(int height, int width, long time, Graphics g){
		g.setColor(Color.white);
		g.fillRect(width/2-100, height/2 - 40, 200, 80);
		g.setColor(Color.black);
		g.drawRect(width/2-100, height/2 - 40, 200, 80);
		g.drawString("Congrats, you finished in", width/2-90, height/2-10);
		g.drawString(String.valueOf(time) + " seconds.", width/2-40, height/2+15);
	}
	
	public static void drawFlag(int x, int y, int size, Graphics g){
		g.setColor(Color.black);
		int[] Px = {x+size/10+2, x+size/10+2, x+size/2+1, x+9*size/10, x+9*size/10};
		int[] Py = {y+9*size/10, y+8*size/10, y+7*size/10-1, y+8*size/10, y+9*size/10};
		g.fillPolygon(Px, Py, 5);
		g.drawLine(x+size/2+1, y + 5*size/6, x+size/2+1, y + size/4);
		g.setColor(Color.red);
		int[] Ptx = {x+size/2+2, x+size/2+2, x+size/10+2};
		int[] Pty = {y + size/4-2, y + size/2+1, y+ 3*size/8};
		g.fillPolygon(Ptx, Pty, 3);
	}
	
}
