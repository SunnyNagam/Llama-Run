import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;


public class Cloud extends Object{
	public Color col = new Color(255, 175, 175, 160);
	public static Image img = Pictures.cloud;
	public Cloud(int x, int y){
		width = 160;
		height = 50;
		this.x = x;
		this.y = y;
		this.dx = -1.7;
	}
	
	public void update(Main mc){
		move(mc);
	}
	
	public void draw(Graphics g){
		g.setColor(col);
		g.drawImage(img,(int)x, (int)y, null);
	}
	
	public double getX(){return x;}
}
