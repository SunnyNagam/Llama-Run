import java.awt.Color;
import java.awt.Graphics;


public class Lazer extends Object{
	
	public Lazer(double x, double y){
		proj = true;
		walker = true;
		this.x = x;
		this.y = y;
		this.dx = 4;
		this.width = 20;
		this.height = 5;
	}

	public void getPosition(){
		///////////////////////// BOUNDS ////////////////////////////
		////////////////////////////////////////////////////////////
	}
	
	public void update(Main mc){
		getPosition();
		for(int x=0; x<mc.bombs.size();x++){
			if(getRect().intersectsLine(mc.bombs.get(x).x, mc.bombs.get(x).y+mc.bombs.get(x).radius, mc.bombs.get(x).x+mc.bombs.get(x).radius, mc.bombs.get(x).y)
					||getRect().intersectsLine(mc.bombs.get(x).x+mc.bombs.get(x).radius, mc.bombs.get(x).y+mc.bombs.get(x).radius*2, mc.bombs.get(x).x+mc.bombs.get(x).radius*2, mc.bombs.get(x).y+mc.bombs.get(x).radius)
					||getRect().intersectsLine(mc.bombs.get(x).x, mc.bombs.get(x).y+mc.bombs.get(x).radius, mc.bombs.get(x).x+mc.bombs.get(x).radius, mc.bombs.get(x).y+mc.bombs.get(x).radius*2)
					||getRect().intersectsLine(mc.bombs.get(x).x+mc.bombs.get(x).radius*2, mc.bombs.get(x).y+mc.bombs.get(x).radius, mc.bombs.get(x).x+mc.bombs.get(x).radius, mc.bombs.get(x).y)){
				mc.bombs.remove(x);
				alive = false;
			}
		}
		move(mc);
	}
	
	public void draw(Graphics g){
		g.setColor(Color.pink);
		g.fillOval((int)x, (int)y, (int)width, (int)height);
		//g.drawImage(img,(int)x,(int)y,(int)width, (int)height,null);
	}
	
	public double getX(){return x;}
}
