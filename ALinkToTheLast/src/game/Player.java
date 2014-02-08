package game;

public class Player {

	private int posX, posY, velX, velY;
	private int speed;
	
	public Player(int posX, int posY){
		this.posX = posX;
		this.posY = posY;
		velX = 0;
		velY = 0;
		speed = 12;
	}
	
	public int getPosX(){
		return this.posX;
	}
	
	public int getPosY(){
		return this.posY;
	}
	
	public void moveX(int dir){
		velX = dir;
	}
	
	public void stopX(){
		velX = 0;
	}
	
	public void moveY(int dir){
		velY = dir;
	}
	
	public void stopY(){
		velY = 0;
	}
	
	public void update(){
		double scale = Math.sqrt(Math.pow(velY, 2) + Math.pow(velX, 2));
		if(scale!=0){
			posX += velX*speed/scale;
			posY += velY*speed/scale;
		}
	}
	
}
