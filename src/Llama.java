import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Llama extends Object{
	public boolean Gdown = false, Gup = false, Gleft = false, Gright = false, douJump = false, lazMode = false;
	public int jumpStart = 6, totalBean = 0, numSprites = 3, equipCos = 0;
	public double spriteMark = 0;
	private double moveSpeed = 0.12, stopSp = 0.13, timeTol = 0;
	public boolean exploded = false;
	public Image img[] = new Image[3], tempS;
	public ArrayList<Lazer> lazs;
	public long shootTimer = System.nanoTime();
	
	public Llama(){
		lazs = new ArrayList<Lazer>();
		/////////////////////////////////////////////////////////////////////////////////////
		width = 40;
		height = 40;
		x = 100;
		y = 100;
		dx = 1;
		dy = 1;
		maxDx = 4;
		maxDy = 8;
		walker = true;
		player = true;
	}
	
	public void getPosition(Main mc){
		if(Gleft)
			dx-=(moveSpeed-mc.camSpeed);
		else if(Gright)
			dx+=moveSpeed;
		else{
			if(dx>0){
				dx-=stopSp;
				if(dx<0)
					dx=0;
			}
			else if(dx<0){
				dx+=stopSp;
				if(dx>0)
					dx=0;
			}
		}
		if(Gup&&(!jumping||!douJump)){
			if(jumping)
				douJump = true;
			if(!mc.sfxMute)
				Sounds.Jump.play();
			dy-=jumpStart;
			jumping = true;
		}
		if(dy==0&&jumping){
			jumping = false;
			douJump = false;
		}
		if(falling)
			dy+=fallSpeed;
		else
			jumping = true;
		///////////////////////// BOUNDS ////////////////////////////
		if(dy>maxDy)
			dy=maxDy;
		else if(dy<-maxDy)
			dy=-maxDy;
		if(dx>maxDx)
			dx = maxDx;
		else if(dx<-maxDx)
			dx =-maxDx;
		////////////////////////////////////////////////////////////
	}
	
	public void update(Main mc){
		getPosition(mc);
		move(mc);
		for(int x=0; x<lazs.size(); x++){
			lazs.get(x).update(mc);
			if(!lazs.get(x).alive){
				lazs.remove(x);
				x--;
			}
		}
		for(int x=0; x<mc.bombs.size();x++){
			if(getRect().intersectsLine(mc.bombs.get(x).x, mc.bombs.get(x).y+mc.bombs.get(x).radius, mc.bombs.get(x).x+mc.bombs.get(x).radius, mc.bombs.get(x).y)
					||getRect().intersectsLine(mc.bombs.get(x).x+mc.bombs.get(x).radius, mc.bombs.get(x).y+mc.bombs.get(x).radius*2, mc.bombs.get(x).x+mc.bombs.get(x).radius*2, mc.bombs.get(x).y+mc.bombs.get(x).radius)
					||getRect().intersectsLine(mc.bombs.get(x).x, mc.bombs.get(x).y+mc.bombs.get(x).radius, mc.bombs.get(x).x+mc.bombs.get(x).radius, mc.bombs.get(x).y+mc.bombs.get(x).radius*2)
					||getRect().intersectsLine(mc.bombs.get(x).x+mc.bombs.get(x).radius*2, mc.bombs.get(x).y+mc.bombs.get(x).radius, mc.bombs.get(x).x+mc.bombs.get(x).radius, mc.bombs.get(x).y)){
				mc.game = false;
				mc.deathScreen = true;
				mc.bombs.remove(x);
				if(!mc.sfxMute)
					Sounds.Death.play();
				exploded = true;
				mc.saveGame();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		if(x<0)
			x=0;
		else if(x>mc.getWidth()-width)
			x=mc.getWidth()-width;
		if(dx == 0)
			spriteMark = 1;
		else
			spriteMark+=0.1;
		if(spriteMark>=3)
			spriteMark = 0;
	}
	
	public void draw(Graphics g){
		for(int x=0; x<lazs.size(); x++)
			lazs.get(x).draw(g);
		g.setColor(Color.blue);
		if(dx>=0)
			g.drawImage(Pictures.llama[equipCos][(int)spriteMark], (int)x, (int)y, null);
		else
			g.drawImage(Pictures.llama[equipCos][(int)spriteMark], (int)(x+width), (int)y,(int)-width,(int)height, null);
		if(exploded)
			g.drawImage(Pictures.explo,(int)x-60,(int)y-60,null);
	}
}