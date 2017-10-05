import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;


public class Pictures {
	static Image deathIcon, Cherry, explo, plat, Jell, cloud, ground;
	static Image[][] llama = new Image[3][3];
	static Main mc;
	URL url;
	public Pictures(Main mc){
		try{
			url = mc.getDocumentBase();
		}catch(Exception e){}
		deathIcon = mc.getImage(url,"pictures/Mocker.png");
		Cherry = mc.getImage(url,"pictures/CherryBomba(dude).png");
		explo = mc.getImage(url,"pictures/Explo.png");
		plat = mc.getImage(url,"pictures/Platform.png");
		Jell = mc.getImage(url,"pictures/JellyOver.png");
		cloud = mc.getImage(url,"pictures/CandyCloud.png");
		llama[0][0] = mc.getImage(url,"pictures/Llama[0].png");
		llama[0][1] = mc.getImage(url,"pictures/Llama[1].png");
		llama[0][2] = mc.getImage(url,"pictures/Llama[2].png");
		llama[1][0] = mc.getImage(url,"pictures/LamCh[0].png");
		llama[1][1] = mc.getImage(url,"pictures/LamCh[1].png");
		llama[1][2] = mc.getImage(url,"pictures/LamCh[2].png");
		llama[2][0] = mc.getImage(url,"pictures/Tig[0].png");
		llama[2][1] = mc.getImage(url,"pictures/Tig[1].png");
		llama[2][2] = mc.getImage(url,"pictures/Tig[2].png");
		ground = mc.getImage(url,"pictures/Ground.png");

		
	}
}
