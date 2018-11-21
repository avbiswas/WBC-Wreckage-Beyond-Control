package glexperiments;

import java.io.BufferedInputStream;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio {
	boolean mute;
	Clip theme;
	Clip select;
	Clip hurt;
	Clip turbo;
	Clip engulf;
	Clip powerUp;
	Clip error;
	Clip enter;
	public void loadTheme(){
		try {
			mute=false;
			BufferedInputStream myStream = new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("Untitled.wav")); 
			AudioInputStream audio2 = AudioSystem.getAudioInputStream(myStream);
			theme=AudioSystem.getClip();
			theme.open(audio2);
			
			myStream = new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("menuSelect.wav")); 
			audio2 = AudioSystem.getAudioInputStream(myStream);
			select=AudioSystem.getClip();
			select.open(audio2);

			myStream = new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("hurt.wav")); 
			audio2 = AudioSystem.getAudioInputStream(myStream);
			hurt=AudioSystem.getClip();
			hurt.open(audio2);
			
			myStream = new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("engulf.wav")); 
			audio2 = AudioSystem.getAudioInputStream(myStream);
			engulf=AudioSystem.getClip();
			engulf.open(audio2);
			
			myStream = new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("error.wav")); 
			audio2 = AudioSystem.getAudioInputStream(myStream);
			error=AudioSystem.getClip();
			error.open(audio2);
			
			myStream = new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("powerUp.wav")); 
			audio2 = AudioSystem.getAudioInputStream(myStream);
			powerUp=AudioSystem.getClip();
			powerUp.open(audio2);
			
			myStream = new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("turbo.wav")); 
			audio2 = AudioSystem.getAudioInputStream(myStream);
			turbo=AudioSystem.getClip();
			turbo.open(audio2);
			
			myStream = new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("enter.wav")); 
			audio2 = AudioSystem.getAudioInputStream(myStream);
			enter=AudioSystem.getClip();
			enter.open(audio2);
			
			


			


					} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			theme=null;
			engulf=null;
			powerUp=null;
			turbo=null;
			enter=null;
			select=null;
			hurt=null;
			mute=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			theme=null;
			engulf=null;
			powerUp=null;
			turbo=null;
			enter=null;
			select=null;
			hurt=null;
			mute=true;
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			theme=null;
			engulf=null;
			powerUp=null;
			turbo=null;
			enter=null;
			select=null;
			hurt=null;
			mute=true;
		}
        
	}
	public void toggleMute(){
		mute=!mute;
		if (!mute){
			playTheme();
		}
		else{
			stopTheme();
		}
	}
	public void playTheme(){
		if(!mute){
		theme.setFramePosition(0);  
        
		theme.loop(2);
		}
	}
	public void hurt(){
		if (!mute){
		hurt.setFramePosition(0);
		hurt.start();
		}
	}
	public void engulf(){
		if (!mute){
		engulf.setFramePosition(0);
		engulf.start();
		}
	}
	public void scope(){
		if (!mute){
		powerUp.setFramePosition(0);
		powerUp.start();
		}
	}
	public void turbo(){
		if (!mute){
		turbo.setFramePosition(0);
		turbo.start();
		}
	}
	public void MenuSelect(){
		if (!mute){
		select.setFramePosition(0);
		select.start();
		}
	}
	public void Enter(){
		if (!mute){
		select.setFramePosition(0);
		select.start();
		}
	}
	public void error(){
		if (!mute){
		error.setFramePosition(0);
		error.start();
		}
	}
	public void stopTheme(){
		theme.stop();
	
	}
}
