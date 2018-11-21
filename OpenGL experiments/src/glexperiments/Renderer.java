package glexperiments;

import java.util.Random;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

public class Renderer implements GLEventListener {
	DOSTUFF stuff;
	long time;
	Random random;
	public Renderer(DOSTUFF stuff){
		random=new Random();
		this.stuff=stuff;
		
		
	}
	@Override
	public void display(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		//System.out.println((System.currentTimeMillis()-time));
		
			GL2 gl=drawable.getGL().getGL2();
		stuff.getScreen().display(gl);
		
		
		
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		time=System.currentTimeMillis();
		
		final GL2 gl = drawable.getGL().getGL2();
		stuff.setGL(gl);
		stuff.getScreen().initialize(gl);
			}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		// TODO Auto-generated method stub
		
			
	}

}