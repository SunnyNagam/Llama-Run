import java.applet.AudioClip;
import java.net.URL;


public class Sounds {
	static AudioClip BgTrack1;
	static AudioClip Jump;
	static AudioClip CollectBean, Death;
	static Main mc;
	URL url;
	public Sounds(Main mc){
		try{
			url = mc.getDocumentBase();
		}catch(Exception e){e.printStackTrace();}
		Jump = mc.getAudioClip(url, "sounds/jump.wav");
		Death = mc.getAudioClip(url, "sounds/death.wav");
		CollectBean = mc.getAudioClip(url, "sounds/jelly.wav");
		BgTrack1 = mc.getAudioClip(url, "sounds/Bg1.wav");
		this.mc = mc;
	}
}
