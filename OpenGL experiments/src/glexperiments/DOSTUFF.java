package glexperiments;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class DOSTUFF {
	float[] position={0.0f,0.0f,0.0f,0.0f};
	Input input;
	Screen screen;
	Audio audio;
	 FPSAnimator animator;
	float x=0.0f,y=0.0f;
	 GL2 gl;
	public void start() {
		// TODO Auto-generated method stub
		input=new Input(this);
		audio=new Audio();
		audio.loadTheme();
		final GLProfile glP= GLProfile.get(GLProfile.GL2);
		final GLCapabilities glCap=new GLCapabilities(glP);
		final GLCanvas canvas= new GLCanvas(glCap);
		//screen=new GameScreen(this,5,16);
		screen=new MainMenu(this);
		canvas.addGLEventListener(new Renderer(this));
		canvas.setSize(1366, 768);
		final JFrame frame=new JFrame("WRECKAGE BEYOND CONTROL");
		frame.getContentPane().add(canvas);
		
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenSize);
		frame.addKeyListener(input);
		frame.setVisible(true);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        audio.stopTheme();    
		    	System.exit(0);
		        
		    }
		});
		animator= new FPSAnimator(canvas, 6,true );
		 animator.start();
		 
	}
	
	public Screen getScreen(){
		return screen;
	}
	/*public int[] getCamera(){
		return camera;
	}*/

	public float getX() {
		// TODO Auto-generated method stub
		
		return x;
	}
	public float getY(){
		return y;
	}
	public Input getInput() {
		// TODO Auto-generated method stub
		return input;
	}
	public void setCurrentScreen(Screen Screen) {
		// TODO Auto-generated method stub
		this.screen=Screen;
		
	}
	public void setGL(GL2 gl) {
		// TODO Auto-generated method stub
		this.gl=gl;
	}
	

}
