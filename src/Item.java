import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;


public class Item {
	public int price, cosInx;
	public Image img;
	public boolean bought = false, costume = true;
	public String name, desc;
	
	public Item(int price, Image img, String name, int cos, String des){
		this.price = price;
		this.img = img;
		this.cosInx = cos;
		this.name = name;
		this.desc = des;
	}
	
	public Item(int price, String name, boolean b, String des){
		this.price  = price;
		//this.img = img;
		costume = b;
		this.name = name;
		this.desc = des;
	}
	
	public void equip(Llama p){
		if(costume)
			p.equipCos = cosInx;
	}
	
	public void paint(Graphics g, int x, int y){
		if(costume)
			g.drawImage(img, x, y, null);
		else{
			g.setColor(Color.pink);
			g.fillRect(x,y,30,5);
		}
		g.setColor(Color.white);
		g.drawString(bought?"OWNED":String.valueOf("$"+price), x+40, y+20);
	}
}
