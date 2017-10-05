import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.Clip;


public class Main extends Applet implements Runnable, KeyListener{
	private static final long serialVersionUID = 1L;
	private Image i;
	private Graphics doubleG;
	private Llama lam;
	private long timeAct = System.nanoTime(), timePlay = System.nanoTime(), timeStart = System.nanoTime();
	public double prevY = 0,prevYC=0, camSpeed = -2;
	private int maxCherry = 3, storeMarker = 0;
	double groundX = 0; 
	public double paintDeathDark = 0;
	public int gDistance =0, score=0, highScore = 0, tempy = 440;
	public long bgtime = 1;
	public ArrayList<Platform> plats;
	public ArrayList<Cloud> clouds;
	public ArrayList<CherryBomb> bombs;
	AudioClip[] clips = new AudioClip[1];
	public AudioClip beanCol;
	public Item[] items = new Item[4];
	//Menus
	public boolean mainMenu = true, game = false, pause = false, store = false, deathScreen = false, beansAdded = false,
			musicMute= false, sfxMute = false;
	
	public void init(){
		//Frame c = (Frame)this.getParent().getParent();
		//c.setTitle("Llama Run");
		lam = new Llama();
		setSize(1300,638);
		addKeyListener(this);
		setFocusable(true);
		new Sounds(this);
		new Pictures(this);
		requestFocusInWindow();
	}
	
	public void start(){
		beanCol = Sounds.CollectBean;
		score = 0;
		lam.beans = 0;
		camSpeed = -2;
		maxCherry = 3;
		gDistance = 0;
		plats = new ArrayList<Platform>();
		clouds = new ArrayList<Cloud>();
		bombs = new ArrayList<CherryBomb>();
		for(int x=0; x<7; x++){
			double tempY=0;
			do{
				tempY = rand(50, getHeight()-120);
			}while(!(Math.abs(tempY-prevY)>100));
			prevY = tempY;
			plats.add(new Platform((getWidth()/7)*x,(int)prevY,camSpeed));
		}
		for(int x=0; x<6; x++){
			double tempY=0;
			do{
				tempY = rand(0,150);
			}while(!(Math.abs(tempY-prevYC)>15));
			prevYC = tempY;
			clouds.add(new Cloud((getWidth()/6)*x,(int)prevYC));
		}
		for(int x=0; x<maxCherry; x++)
			bombs.add(new CherryBomb((getWidth()/maxCherry)*x,rand(5,getHeight()-120),40,camSpeed*2));
		// setting store items
		items[0] = new Item(300,"Lazer eyes!",false, "Lets you press \"enter\" to shoot lazers!");
		items[1] = new Item(0,Pictures.llama[0][2],"Llama",0,"Your typical Llama.");
		items[2] = new Item(100,Pictures.llama[1][2],"Cheese",1,"Smelly and tasty! Cheese Galore!");
		items[3] = new Item(100,Pictures.llama[2][2],"Tiger",2,"You're gonna hear me ROAAR!");
		// read save
		BufferedReader br;
		try {
			br = new BufferedReader(
					new FileReader(new File("files/save"))
					);
			String[] temp = br.readLine().split("highscore=");
			highScore = Integer.parseInt(temp[1]);
			temp = br.readLine().split("totalbeans=");
			lam.totalBean = Integer.parseInt(temp[1]);
			temp = br.readLine().split("lazMode=");
			lam.lazMode = Integer.parseInt(temp[1])==0?false:true;
			temp = br.readLine().split("cosInx=");
			lam.equipCos = Integer.parseInt(temp[1]);
			temp = br.readLine().split(" ");
			items[1].bought = Integer.parseInt(temp[1])==0?false:true;
			items[2].bought = Integer.parseInt(temp[2])==0?false:true;
			items[3].bought = Integer.parseInt(temp[3])==0?false:true;
			items[0].bought = lam.lazMode?true:false;
		} catch (Exception e) {
			e.printStackTrace();}
		clips[0]=Sounds.BgTrack1;
		clips[0].play();
		bgtime = System.nanoTime();
		/////////////////////////////////////
		Thread thread = new Thread(this);
		thread.start();
	}

	public void run(){
		while(true){
			if((System.nanoTime()-bgtime)/1000000000.0>=37&&!musicMute){
				clips[0].stop();
				clips[0].play();
				bgtime = System.nanoTime();
			}
			if(game){
				groundX += plats.get(0).dx;
				if(groundX<=-getWidth())
					groundX =0;
				lam.update(this);
				if(timePlay/1000000%20==0)
					score+=(-camSpeed)/2;
				timePlay = System.nanoTime();
				if(timePlay/1000000%50==0)
					camSpeed-=0.02;
				if(maxCherry<8&&timePlay/1000000%900==0)
					maxCherry++;
				if(bombs.size()<maxCherry)
					bombs.add(new CherryBomb(getWidth(),rand(5,getHeight()-120),40,camSpeed*2));
				for(int x=0; x<plats.size(); x++){
					plats.get(x).update(this);
					if(plats.get(x).getX()<0-plats.get(x).width){
						plats.remove(x);
						double tempY=0;
						do{
							tempY = rand(50, getHeight()-120);
						}while(!(Math.abs(tempY-prevY)>100));
						prevY = tempY;
						plats.add(new Platform(getWidth(),(int)prevY,camSpeed));
						x--;
					}
				}
				for(int x=0; x<clouds.size(); x++){
					clouds.get(x).update(this);
					if(clouds.get(x).getX()<0-clouds.get(x).width){
						clouds.remove(x);
						double tempY=0;
						do{
							tempY = rand(0,150);
						}while(!(Math.abs(tempY-prevYC)>15));
						prevYC = tempY;
						clouds.add(new Cloud(getWidth(),(int)prevYC));
						x--;
					}
				}
				for(int x=0; x<bombs.size(); x++){
					bombs.get(x).update(this);
					if(bombs.get(x).shouldRemove||bombs.get(x).getX()<0-bombs.get(x).width)
						bombs.remove(x);
				}
			}
			else if(mainMenu){
				if(paintDeathDark!=0)
					paintDeathDark = 0;
			}
			else if(store){
				
			}
			else if(pause){
				
			}
			else if(deathScreen){
				if(paintDeathDark <255)
					paintDeathDark+=1;
				else
					paintDeathDark = 255;
			}
			repaint();
			try{
				Thread.sleep(17);
			}
			catch(InterruptedException e){}
		}
	}

	public void paint(Graphics g){
		g.setColor(Color.cyan);
		g.fillRect(0, 0, getWidth(), getHeight()-80);
		lam.draw(g);
		for(int x=0; x<plats.size(); x++)
			plats.get(x).draw(g);
		for(int x=0; x<bombs.size(); x++)
			bombs.get(x).draw(g);
		g.setFont(new Font("Garamond", Font.BOLD, 30));
		g.setColor(Color.black);
		g.drawString("JellyBeans: "+String.valueOf(lam.beans), 50, 50);
		g.drawString("Score: "+String.valueOf(score), getWidth()-150, 50);
		for(int x=0; x<clouds.size(); x++)
			clouds.get(x).draw(g);
		g.drawImage(Pictures.ground,(int)groundX,getHeight()-100,this);
		g.drawImage(Pictures.ground,(int)groundX+getWidth(),getHeight()-100,this);
		if(mainMenu){
			g.setColor(new Color(0,0,0,180));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.white);
			g.drawString("Llama Run!", getWidth()/2-80, 100);
			g.drawString("Press any key to continue",getWidth()/2-160, getHeight()-100);
			g.drawString("Help Llama get back to the zoo!",getWidth()/2-205, getHeight()-130);
			g.setColor(Color.green);
			g.drawString("Controls:", getWidth()/2-60, 160);
			g.drawString("W-A-S-D:    Move", getWidth()/2-115, 190);
			g.drawString("Space:    Jump", getWidth()/2-90, 220);
			g.drawString("Esc:    Pause", getWidth()/2-80, 250);
		}
		else if(store){
			g.setColor(new Color(0,0,0,220));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.white);
			g.drawString("Total Jellybeans : "+String.valueOf(lam.totalBean), getWidth()-300, 30);
			g.drawString("Welcome to the Store!", getWidth()/2-80, 100);
			g.drawString("Use 'W' and 'S' to scroll and 'Enter' to buy an item or costume.", getWidth()/2-350, getHeight()-100);
			g.drawString("Press 'E' to equip a costume and press 'esc' to go to the Main Menu.", getWidth()/2-380, getHeight()-70);
			for(int x=0; x<items.length; x++){
				if(x==storeMarker){
					g.setColor(new Color(255,112,253,190));
					g.fillRect(130, 140+(x*70), 180, 60);
					g.setColor(Color.white);
					g.drawString(items[x].desc, 340, 160+(x*70)+10);
				}
				else
					g.drawString(items[x].name, 340, 160+(x*70)+10);
				if(items[x].cosInx == lam.equipCos && items[x].costume){
					g.drawString("Equiped", getWidth()- 160,160+(x*70)+10);
				}
				items[x].paint(g, 150, 160+(x*70));
			}
		}
		else if(pause){
			g.setColor(new Color(0,0,0,100));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.white);
			g.drawString("PAUSED", getWidth()/2-50, 80);
			g.drawString("Press 'M' to "+ (musicMute?"unmute":"mute")+" the music.",getWidth()/2-180, 150);
			g.drawString("Press 'S' to "+ (sfxMute?"unmute":"mute")+" the sound effects.",getWidth()/2-200, 190);
			g.drawString("Press 'esc' to unpause.",getWidth()/2-130, 230);
		}
		else if(deathScreen){
			if(!beansAdded){
				lam.totalBean += lam.beans;
				beansAdded = true;
			}
			if(score>highScore)
				highScore = score;
			g.setColor(new Color(0,0,0,(int)paintDeathDark));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.white);
			g.drawString("You DED :P", getWidth()/2-80, 80);
			g.drawString("Total Jellybeans : "+String.valueOf(lam.totalBean), getWidth()-300, 30);
			g.drawString("HIGHSCORE: "+highScore, 30, 30);
			g.drawString("Beans collected: "+String.valueOf(lam.beans), getWidth()/2-110, tempy);
			g.drawString("Score: "+String.valueOf(score), getWidth()/2-60, tempy+40);
			g.drawString("Press M to go to the main menu.", getWidth()/2-200, tempy+80);
			g.drawString("Press esc to exit.", getWidth()/2-110, tempy+120);
			g.drawString("Press S to go to the magical zoo store.", getWidth()/2-232, tempy+160);
			g.drawImage(Pictures.deathIcon,500,120,null);
		}
	}

	public void update(Graphics g){
		if(i==null){
			i = createImage(this.getSize().width, this.getSize().height);
			doubleG = i.getGraphics();
		}
		doubleG.setColor(getBackground());
		doubleG.fillRect(0, 0, this.getSize().width, this.getSize().height);

		doubleG.setColor(getForeground());
		paint(doubleG);
		g.drawImage(i,0,0,this);
	}
	public void destroy(){

	}
	public void stop(){

	}
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(mainMenu&&key!=KeyEvent.VK_ALT&&key!=KeyEvent.VK_TAB){
			mainMenu = false;
			game = true;
			beansAdded = false;
			return;
		}
		else if(game){
			if(key == KeyEvent.VK_SPACE && (System.nanoTime()-timeAct)/1000000>9.1){
				lam.Gup = true;
				timeAct = System.nanoTime();
			}
			else if(key == KeyEvent.VK_A){
				lam.Gleft = true;
			}
			else if(key == KeyEvent.VK_D)
				lam.Gright = true;
			else if(key == KeyEvent.VK_S)
				lam.Gdown = true;
			else if(key == KeyEvent.VK_ESCAPE){
				game = false;
				pause = true;
			}
			else if(key == KeyEvent.VK_ENTER && lam.lazMode){
				if((System.nanoTime()-lam.shootTimer)/1000000000>=3){
					lam.lazs.add(new Lazer(lam.x,lam.y));
					lam.shootTimer = System.nanoTime();
				}
			}
		}
		else if(deathScreen){
			if(key == KeyEvent.VK_M){
				deathScreen = false;
				mainMenu = true;
				resetGame();
			}
			else if(key == KeyEvent.VK_ESCAPE)
				System.exit(0);
			else if(key == KeyEvent.VK_S){
				deathScreen = false;
				store = true;
			}
		}
		else if(pause){
			if(key == KeyEvent.VK_ESCAPE){
				pause = false;
				game = true;
			}
			else if(key == KeyEvent.VK_M){
				if(musicMute)
					clips[0].play();
				else
					clips[0].stop();
				musicMute = !musicMute;
			}
			else if(key == KeyEvent.VK_S){
				sfxMute = !sfxMute;
			}
		}
		else if(store){
			if(key == KeyEvent.VK_ESCAPE){
				store = false;
				mainMenu = true;
				resetGame();
			}
			else if(key == KeyEvent.VK_S && storeMarker<items.length-1){
				storeMarker++;
			}
			else if(key == KeyEvent.VK_W && storeMarker>0){
				storeMarker--;
			}
			else if( key == KeyEvent.VK_E && items[storeMarker].costume && items[storeMarker].bought){
				lam.equipCos = items[storeMarker].cosInx;
			}
			else if(key == KeyEvent.VK_ENTER){
				if(!items[storeMarker].bought && lam.totalBean >= items[storeMarker].price){
					items[storeMarker].bought = true;
					if(storeMarker == 0)
						lam.lazMode = true;
					lam.totalBean -= items[storeMarker].price;
				}
			}
		}
	}
	
	public void resetGame(){
		score = 0;
		lam.beans = 0;
		lam.x = lam.y = 100;
		camSpeed = -2;
		timePlay = 0;
		maxCherry = 3;
		lam.exploded = false;
		gDistance = 0;
		plats = new ArrayList<Platform>();
		clouds = new ArrayList<Cloud>();
		bombs = new ArrayList<CherryBomb>();
		for(int x=0; x<7; x++){
			double tempY=0;
			do{
				tempY = rand(50, getHeight()-120);
			}while(!(Math.abs(tempY-prevY)>100));
			prevY = tempY;
			plats.add(new Platform((getWidth()/7)*x,(int)prevY,camSpeed));
		}
		for(int x=0; x<4; x++){
			double tempY=0;
			do{
				tempY = rand(0,100);
			}while(!(Math.abs(tempY-prevY)>100));
			prevYC = tempY;
			clouds.add(new Cloud((getWidth()/3)*x,(int)prevYC));
		}
		for(int x=0; x<maxCherry; x++)
			bombs.add(new CherryBomb((getWidth()/maxCherry)*x,rand(5,getHeight()-120),40,camSpeed*2));
	}
	
	public void saveGame(){
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter("files/save",false));
			out.write("highscore="+String.valueOf(highScore));
			out.newLine();
			out.write("totalbeans="+String.valueOf(lam.totalBean));
			out.newLine();
			out.write("lazMode="+(items[0].bought?"1":"0"));
			out.newLine();
			out.write("cosInx="+String.valueOf(lam.equipCos));
			out.newLine();
			for(int x=1; x<items.length;x++){
				out.write(items[x].bought?" 1":" 0");
			}
			out.close();
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public void keyReleased(KeyEvent e) {
		if(game){
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_SPACE)
				lam.Gup = false;
			else if(key == KeyEvent.VK_A)
				lam.Gleft = false;
			else if(key == KeyEvent.VK_D)
				lam.Gright = false;
			else if(key == KeyEvent.VK_S)
				lam.Gdown = false;
		}
	}

	public int rand(int min, int max){
		return min + (int)(Math.random()*((max-min)+1));
	}
}