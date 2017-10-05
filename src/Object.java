import java.awt.Rectangle;


public abstract class Object {
	protected double x, y, dx, dy, width, height, maxDx, maxDy, fallSpeed = 0.19;
	protected boolean alive = true, falling = true, jumping = false, player = false;
	protected boolean walker = false, proj = false;
	protected int beans = 0;
	
	public Object(){
		
	}
	
	public void move(Main mc){
		double tempx = x, tempy = y;
		tempx += dx;
		if(walker&&(x>=mc.getWidth()||x<=0))
			dx*=-1;
		if(walker){
		// platform detection
				for(int x=0; x< mc.plats.size(); x++)
					if(new Rectangle((int)mc.plats.get(x).x,(int)mc.plats.get(x).y,(int)mc.plats.get(x).width,20).intersects(
							new Rectangle((int)(tempx),(int)(tempy),1,1))){
						tempx-=dx;
						dx=0;
					}
		tempy += dy;
		if(walker&&(y>=mc.getHeight()||y<=0))
			dy*=-1;
		// platform detection
				for(int x=0; x< mc.plats.size(); x++)
					if(new Rectangle((int)mc.plats.get(x).x,(int)mc.plats.get(x).y,(int)mc.plats.get(x).width,20).intersects(
							new Rectangle((int)(tempx),(int)(tempy),(int)width,(int)height))){
						if(dy>0){
							tempy=mc.plats.get(x).y-height;
							dy=0;
							if(player){
								for(int q=0; q<mc.plats.get(x).jellys.size(); q++){
									if(new Rectangle((int)mc.plats.get(x).jellys.get(q).x,(int)mc.plats.get(x).jellys.get(q).y,(int)mc.plats.get(x).jellys.get(q).width,20).intersects(
											new Rectangle((int)(tempx),(int)(tempy),(int)width,(int)height))){
										beans += mc.plats.get(x).jellys.get(q).value;
										mc.plats.get(x).jellys.remove(q);
										mc.score++;
										if(!mc.sfxMute){
											mc.beanCol.stop();
											mc.beanCol.play();
										}
										q--;
									}
								}
							}
						}
						else{
							tempy=mc.plats.get(x).y+20;
							dy=0.1;
						}
					}
		}
		// test
		if(tempy<0){
			tempy-=dy;
			dy = 0.1;
		}
		else if(tempy>(mc.getHeight()-height-80)){
			tempy-=dy;
			dy=0;
		}
		if(proj && tempx == x)
			alive = false;
		x = tempx;
		y = tempy;
	}
	
	public Rectangle getRect(){
		return(new Rectangle((int)x,(int)y,(int)width,(int)height));
	}
	
	public int rand(int min, int max){
		return min + (int)(Math.random()*((max-min)+1));
	}
}
