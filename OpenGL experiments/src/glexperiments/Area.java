package glexperiments;

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

public class Area {
	final int VERTEX_SIZE=(4)*4;
	FloatBuffer vertices;
	ShortBuffer indices;
	int height; int width;
	int current_PositionX;
	int current_PositionY;
	Texture T;
	GameScreen game;
	int texture;
	float x,y;
	FloatBuffer pixel;
	GL2 gl;
	public Area(GL2 gl,GameScreen g,String filename){
		this.gl=gl;
		game=g;
		x=0;
		y=0;
		current_PositionX=0;
		current_PositionY=0;
		ByteBuffer byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		vertices=byteBuffer.asFloatBuffer();
		vertices.put(new float[]{-1.0f,-1.0f,0.0f,0.0f,
								1.0f,-1.0f,1.0f,0.0f,
								1.0f,1.0f,1.0f,1.0f,
								-1.0f,1.0f,0.0f,1.0f});
		vertices.flip();
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
		/*
	    try {
			stream = new FileInputStream(new File("C:/files/map20.png"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}
		*/
	    stream = this.getClass().getClassLoader().getResourceAsStream(filename);
		
            
            try {
            	TextureData data = TextureIO.newTextureData(GLProfile.getDefault(), stream, true, "png");
            	 
            	T = TextureIO.newTexture(data);
                 
            } catch (IOException e){
            	e.printStackTrace();
            	System.exit(1);
            }
            
                  
            texture=T.getTextureObject();
            showArea();
            
	}
	public void showArea(){
		
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
				
		
		
				T.bind(gl);
				
				vertices.position(0);
				
				gl.glVertexPointer(2, GL2.GL_FLOAT, VERTEX_SIZE, vertices);
				vertices.position(2);
				gl.glTexCoordPointer(2, GL2.GL_FLOAT, VERTEX_SIZE, vertices);
				
				if (game.gameState==game.GAME_RUNNING)
				gl.glDrawElements(GL2.GL_TRIANGLES, 6, GL2.GL_UNSIGNED_SHORT, indices);
				  
				  
				gl.glMatrixMode(GL2.GL_MODELVIEW);
				gl.glLoadIdentity();
				if (game.gameState==game.go_Main_Menu){
					int [] textureIDS={texture};
					gl.glDeleteTextures(1, textureIDS, 0);
					gl.glClearColor(0,0,0,1);
					
					gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
					
				}
				
				
				}
	
	
	
}
