import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;


public class Platform extends Object{
	public boolean coinCon = false;
	public int jumpStart = 7;
	private double moveSpeed = 0.12;
	public ArrayList<Jellybean> jellys = new ArrayList<Jellybean>();
	public static Image img = Pictures.plat;
	public Platform(int x, int y, double dx){
		width = 350;
		height = 20;
		this.x = x;
		this.y = y;
		this.dx = dx;
		if(rand(0,100)<30)
			coinCon = true;
		if(coinCon)
			for(int z=0; z<7; z++)
				jellys.add(new Jellybean(x+(z*50)+7,y-Jellybean.JELLYHEIGHT,1,dx));
	}
	
	public void getPosition(){
		///////////////////////// BOUNDS ////////////////////////////
		////////////////////////////////////////////////////////////
	}
	public void update(Main mc){
		getPosition();
		move(mc);
		for(int x=0; x<jellys.size(); x++)
			jellys.get(x).update(mc);
	}
	
	public void draw(Graphics g){
		g.setColor(Color.RED);
		g.drawImage(img,(int)x, (int)y, null);
		for(int x=0; x<jellys.size(); x++)
			jellys.get(x).draw(g);
	}
	
	public double getX(){return x;}
}
