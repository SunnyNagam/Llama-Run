import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;


public class CherryBomb extends Object{
	public boolean shouldRemove = false;
	public int radius = 0;
	private double moveSpeed = 0.12;
	public static Image Cherry = Pictures.Cherry;
	public CherryBomb(int x, int y, int rad, double dx){
		this.radius = rad;
		width = rad*2;
		height = rad*2;
		this.x = x;
		this.y = y;
		this.dx = dx;
		dy = rand(1,100)<=30?2:0;
	}
	
	public void getPosition(){
		///////////////////////// BOUNDS ////////////////////////////
		////////////////////////////////////////////////////////////
	}
	
	public void update(Main mc){
		getPosition();
		move(mc);
	}
	
	public void draw(Graphics g){
		g.setColor(Color.PINK);
		g.drawImage(Cherry,(int)x, (int)y-20, null);
	}
	
	public double getX(){return x;}
}
