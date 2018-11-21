package glexperiments;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class Instructions implements Screen {
	FloatBuffer instructions1;
	FloatBuffer instructions2;
	ShortBuffer indices;
	Texture T;
	int VERTEX_SIZE=16;
	int texture;
	GL2 gl;
	int page=1;
	final int GoMainMenu=3;
	private DOSTUFF stuff;
	public Instructions(DOSTUFF stuff) {
		// TODO Auto-generated constructor stub
		this.stuff=stuff;
	}
	@Override
	public void display(GL2 gl) {
		// TODO Auto-generated method stub
		//gl.glEnable(GL2.GL_BLEND);
		//gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
			gl.glLoadIdentity();
	
	
			//gl.glEnable(GL2.GL_TEXTURE_2D);
			gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);
			
			//gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
			//gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
	
		if (page==1){
			show(instructions1);
		}
		else if (page==2){
			show(instructions2);
		}
		else if (page==GoMainMenu){
			int [] textureIDS={texture};
			gl.glDeleteTextures(1, textureIDS, 0);
			gl.glClearColor(0,0,0,1);
			
			gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
			MainMenu menu=new MainMenu(stuff);
			menu.initialize(gl);
			stuff.setCurrentScreen(menu);
			return;
		}
		if (stuff.getInput().getKey().size()>0)
		update();
	}

	@Override
	public void initialize(GL2 gl) {
		// TODO Auto-generated method stub
		this.gl=gl;
		ByteBuffer byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		instructions1=byteBuffer.asFloatBuffer();
		instructions1.put(new float[]{-0.80f,-1.0f,0.50f,0.0f,
								0.80f,-1.0f,1.0f,0.0f,
								0.80f,1.0f,1.0f,1.0f,
								-.80f,1.0f,0.50f,1.0f});
		instructions1.flip();
		byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		instructions2=byteBuffer.asFloatBuffer();
		instructions2.put(new float[]{-0.80f,-1.0f,0.0f,0.0f,
								0.80f,-1.0f,0.50f,0.0f,
								0.80f,1.0f,.50f,1.0f,
								-.80f,1.0f,0.0f,1.0f});
		instructions2.flip();
		
		byteBuffer=ByteBuffer.allocateDirect(6*2);
		byteBuffer.order(ByteOrder.nativeOrder());
		indices=byteBuffer.asShortBuffer();
		indices.put(new short[]{0,1,2,2,3,0});
		indices.flip();
		
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glClearColor(0f, 0f, 0f, 0.0f);
		gl.glDepthFunc(GL2.GL_LEQUAL);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
		
		gl.glEnable(GL2.GL_TEXTURE_2D);
		
	    InputStream stream = null;
		stream = this.getClass().getClassLoader().getResourceAsStream("instructions.png");
		
            
            try {
            	TextureData data = TextureIO.newTextureData(GLProfile.getDefault(), stream, true, "png");
            	 
            	T = TextureIO.newTexture(data);
                 
            } catch (IOException e){
            	e.printStackTrace();
            	System.exit(1);
            }
            
                  
            texture=T.getTextureObject();
    
	}
	
	private void show(FloatBuffer page) {
		// TODO Auto-generated method stub
		page.position(0);
		gl.glVertexPointer(2, GL2.GL_FLOAT, 16, page);
		page.position(2);
		gl.glTexCoordPointer(2, GL2.GL_FLOAT, 16, page);
					
		gl.glDrawElements(GL2.GL_TRIANGLES, 6, GL2.GL_UNSIGNED_SHORT, indices);
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	private void update() {
		// TODO Auto-generated method stub
		int input=stuff.getInput().getKey().get(0);
		switch(input){
			case KeyEvent.VK_UP: 
								
								
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_RIGHT: 
						
			case KeyEvent.VK_DOWN:page=(page==1)?2:1;
									break;
			
			case KeyEvent.VK_ENTER:	page=(page==1)?2:GoMainMenu;
									break;
			case KeyEvent.VK_ESCAPE:
									
									page=GoMainMenu;
									break;
			
		}
		
	}
	
}
