import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;


public class Jellybean extends Object{
	public static final int JELLYHEIGHT = 25;
	public boolean coinCon = false;
	public int value = 0;
	private double moveSpeed = 0.12;
	private Color col = new Color(rand(0,255),rand(0,255),rand(0,255));
	private static Image img = Pictures.Jell;
	public Jellybean(int x, int y, int value, double Dx){
		width = 15;
		height = JELLYHEIGHT;
		this.x = x;
		this.y = y;
		dx = Dx;
		this.value = value;
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
		g.setColor(col);
		g.fillOval((int)x, (int)y, (int)width, (int)height);
		g.drawImage(img,(int)x,(int)y,(int)width, (int)height,null);
	}
	
	public double getX(){return x;}
}
