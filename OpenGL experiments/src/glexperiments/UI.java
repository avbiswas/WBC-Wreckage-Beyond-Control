package glexperiments;

import java.awt.Toolkit;
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

public class UI {
	ShortBuffer indices;
	FloatBuffer triangle;
	FloatBuffer turboBar;
	FloatBuffer danger;
	FloatBuffer scope;
	FloatBuffer wbcWins;
	FloatBuffer germWins;
	FloatBuffer numbers;
	FloatBuffer WBCicon;
	FloatBuffer Germicon;
	FloatBuffer pauseMenu;
	FloatBuffer powerUp;
	int tempTurbo;
	Texture T;
	int texture;
	GameScreen game;
	GL2 gl;
	UI(GL2 gl, GameScreen g){
		this.gl=gl;
		float res= (float) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth())/(Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		tempTurbo=100;
		game=g;
		ByteBuffer pixeldata1;
		pixeldata1=ByteBuffer.allocateDirect(4*3*4).order(ByteOrder.nativeOrder());
		triangle=pixeldata1.asFloatBuffer();
		triangle.put(new float[]{0f,0.0f,0,0.72f,
				0.0f,0.03f,0,1,
				0.03f,0.015f,0.25f,0.853f});
		triangle.flip();
		
		pixeldata1=ByteBuffer.allocateDirect(4*4*4).order(ByteOrder.nativeOrder());
		turboBar=pixeldata1.asFloatBuffer();
		turboBar.put(new float[]{0f,0.0f,0,0.67f,
				0.0f,0.03f,0,0.73f,
				0.05f,0.03f,0.25f,0.73f,
				0.05f,0,0.25f,0.67f});
		turboBar.flip();
		
		pixeldata1=ByteBuffer.allocateDirect(4*4*4).order(ByteOrder.nativeOrder());
		germWins=pixeldata1.asFloatBuffer();
		germWins.put(new float[]{-0.5f,-0.50f,0,0.33f,
				-0.50f,0.5f,0,0.66f,
				0.5f,0.5f,0.5f,0.66f,
				0.5f,-0.5f,0.5f,0.33f});
		germWins.flip();
		pixeldata1=ByteBuffer.allocateDirect(4*4*4).order(ByteOrder.nativeOrder());
		wbcWins=pixeldata1.asFloatBuffer();
		wbcWins.put(new float[]{-0.5f,-0.50f,0,0.0f,
				-0.50f,0.5f,0,0.33f,
				0.5f,0.5f,0.5f,0.33f,
				0.5f,-0.5f,0.5f,0f});
		wbcWins.flip();
		
		pixeldata1=ByteBuffer.allocateDirect(4*4*4).order(ByteOrder.nativeOrder());
		pauseMenu=pixeldata1.asFloatBuffer();
		pauseMenu.put(new float[]{-0.25f,-0.250f,0.625f,0.13f,
				-0.250f,0.25f,0.625f,0.35f,
				0.25f,0.25f,1f,0.35f,
				0.25f,-0.25f,1f,0.13f});
		pauseMenu.flip();
		
		pixeldata1=ByteBuffer.allocateDirect(4*4*4).order(ByteOrder.nativeOrder());
		WBCicon=pixeldata1.asFloatBuffer();
		WBCicon.put(new float[]{-1f,0.920f,0.7f,0.67f,
				-1f,1f,0.7f,1f,
				-0.95f,1f,1f,1f,
				-0.95f,0.92f,1f,0.67f});
		WBCicon.flip();
		
		pixeldata1=ByteBuffer.allocateDirect(4*4*4).order(ByteOrder.nativeOrder());
		Germicon=pixeldata1.asFloatBuffer();
		Germicon.put(new float[]{0.94f,0.920f, 0.7f,0.33f,
				0.94f,1f, 0.7f,0.67f,
				1f,1f, 1f,0.67f,
				1f,0.92f,1f,0.33f});
		Germicon.flip();
		
		pixeldata1=ByteBuffer.allocateDirect(4*4*4).order(ByteOrder.nativeOrder());
		numbers=pixeldata1.asFloatBuffer();
		
		pixeldata1=ByteBuffer.allocateDirect(4*4*4).order(ByteOrder.nativeOrder());
		wbcWins=pixeldata1.asFloatBuffer();
		wbcWins.put(new float[]{-0.5f,-0.50f,0,0.0f,
				-0.50f,0.5f,0,0.33f,
				0.5f,0.5f,0.5f,0.33f,
				0.5f,-0.5f,0.5f,0f});
		wbcWins.flip();
		
		
		pixeldata1=ByteBuffer.allocateDirect(4*4*4).order(ByteOrder.nativeOrder());
		danger=pixeldata1.asFloatBuffer();
		danger.put(new float[]{0.0f,0.09f,0.25f,0.67f,
				0.01f,0.09f,0.25f,0.75f,
				0.01f,0.0f,0.5f,0.75f,
				0.0f,0,0.5f,0.67f});
		danger.flip();
		
		pixeldata1=ByteBuffer.allocateDirect(4*4*4).order(ByteOrder.nativeOrder());
		scope=pixeldata1.asFloatBuffer();
		float radius=0.27f;
		float height=radius*res;
		scope.put(new float[]{-radius,-height,0.275f,0.77f,
				-radius,height,0.275f,1,
				radius,height,0.5f,1,
				radius,-height,0.5f,0.77f});
		scope.flip();
		
		pixeldata1=ByteBuffer.allocateDirect(4*4*4).order(ByteOrder.nativeOrder());
		powerUp=pixeldata1.asFloatBuffer();
		powerUp.put(new float[]{-0.03f,-0.050f,0.5f,0.4f,
				-0.03f,0.05f,0.5f,0.67f,
				0.03f,0.05f,0.7f,0.67f,
				0.03f,-0.05f,0.7f,0.4f});
		powerUp.flip();
		
		pixeldata1=ByteBuffer.allocateDirect(6*2);
		pixeldata1.order(ByteOrder.nativeOrder());
		indices=pixeldata1.asShortBuffer();
		indices.put(new short[]{0,1,2,2,3,0});
		indices.flip();
		
		
		gl.glEnable(GL2.GL_TEXTURE_2D);
		
	   InputStream stream = null;
		stream = this.getClass().getClassLoader().getResourceAsStream("ui.png");
		    
            try {
            	TextureData data = TextureIO.newTextureData(GLProfile.getDefault(), stream, true, "png");
            	 
            	T = TextureIO.newTexture(data);
                 
            } catch (IOException e){
            	e.printStackTrace();
            	System.exit(1);
            }
            
            
            texture=T.getTextureObject();
            
        

	}
	void show(){	
		float x;
		float y;

		gl.glLoadIdentity();
		
		
		gl.glEnable(GL2.GL_TEXTURE_2D);
		T.bind(gl);
		
		if (game.gameState==game.PAUSED){
			wins(pauseMenu);
			return;
		}
		if (game.gameState==game.WBC_WON){
			wins(wbcWins);
			return;
		}
		if (game.gameState==game.GERMS_WON){
			wins(germWins);
			return;
		}
		if (game.powerUpmode){
			wins(powerUp);
		}
		showScore();
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		x=game.getCurrentWBCLocation().x;
		y=game.getCurrentWBCLocation().y;
		
		
		if (game.wbcs.get(game.wbc_ID).sashaMode){
		gl.glLoadIdentity();
		gl.glTranslatef(x+0.04f+(game.wbcs.get(game.wbc_ID).WBCwidth)/2,y+(game.wbcs.get(game.wbc_ID).WBCheight)/2,0);
		
		
		showsScope();
		}
		
		gl.glLoadIdentity();
		gl.glTranslatef(x-0.02f,y,0);
		
		
		showControl();
		
		
		for (int i=0;i<game.no_of_WBC;i++){
		
			if (game.wbcs.get(game.wbc_ID).danger){
			
			x=game.wbcs.get(game.wbc_ID).x;
			y=game.wbcs.get(game.wbc_ID).y;
			gl.glLoadIdentity();
			gl.glTranslatef(x-0.02f,y,0);
			showdanger();
			
			}
		}
	
		
		
		
		gl.glLoadIdentity();
		gl.glTranslatef(x,y+0.08f,0);
		
		
		if (game.wbcs.get(game.wbc_ID).turboON){
		showTurboBar();
		}
		
		if (game.gameState==game.go_Main_Menu){
			int [] textureIDS={texture};
			gl.glDeleteTextures(1, textureIDS, 0);
			
		}
		
	}
	
	private void showScore() {
		// TODO Auto-generated method stub
		wins(WBCicon);
		
		int score=game.no_of_WBC;
		int k=0;
		if (score>=10){
			k=1;
		}
		while (score>0){
			int temp=score%10;
			updateWBCScore(temp,k);
			k--;
			wins(numbers);
			score=score/10;
		}
		wins(Germicon);
		 score=game.no_of_germs;
		k=0;
		if (score>=10){
			k=1;
		}
		while (score>0){
			int temp=score%10;
			updateGermscore(temp,k);
			k--;
			wins(numbers);
			score=score/10;
		}
	}
	private void updateWBCScore(int score, float offset) {
		// TODO Auto-generated method stub
		numbers.clear();
		
			
			numbers.put(new float[]{-0.9f+offset*0.1f,0.88f,0.5f+(0.05f*score),0.05f,
					-0.9f+offset*0.1f,1f,0.5f+(0.05f*score),0.13f,
					-0.8f+offset*0.1f,1f,0.5f+(0.05f*(score+0.9f)),0.13f,
					-0.8f+offset*0.1f,0.88f,0.5f+(0.05f*(score+0.9f)),0.05f});
			
		
		numbers.flip();
	}
	private void updateGermscore(int score, float offset) {
		// TODO Auto-generated method stub
		
		numbers.clear();
		numbers.put(new float[]{0.7f+offset*0.1f,0.88f,0.5f+(0.05f*score),0.05f,
				0.7f+offset*0.1f,1f,0.5f+(0.05f*score),0.13f,
				0.8f+offset*0.1f,1f,0.5f+(0.05f*(score+0.9f)),0.13f,
				0.8f+offset*0.1f,0.88f,0.5f+(0.05f*(score+0.9f)),0.05f});
		
		numbers.flip();
	
	}
	private void wins(FloatBuffer wins) {
		// TODO Auto-generated method stub
		wins.position(0);
		gl.glVertexPointer(2, GL2.GL_FLOAT, 16, wins);
		wins.position(2);
		gl.glTexCoordPointer(2, GL2.GL_FLOAT, 16, wins);
					
		gl.glDrawElements(GL2.GL_TRIANGLES, 6, GL2.GL_UNSIGNED_SHORT, indices);
	}
	private void showsScope() {
		// TODO Auto-generated method stub
		scope.position(0);
		
		gl.glVertexPointer(2, GL2.GL_FLOAT, 16, scope);
		scope.position(2);
		gl.glTexCoordPointer(2, GL2.GL_FLOAT, 16, scope);
					
		gl.glDrawElements(GL2.GL_TRIANGLES, 6, GL2.GL_UNSIGNED_SHORT, indices);
		
	
	}
	private void showControl() {
		// TODO Auto-generated method stub
		triangle.position(0);
		
		gl.glVertexPointer(2, GL2.GL_FLOAT, 16, triangle);
		triangle.position(2);
		gl.glTexCoordPointer(2, GL2.GL_FLOAT, 16, triangle);
					
		
		gl.glDrawArrays(GL2.GL_TRIANGLES, 0, 3);
		
	}
	private void showdanger() {
		// TODO Auto-generated method stub
		danger.position(0);
		
		gl.glVertexPointer(2, GL2.GL_FLOAT, 16, danger);
		danger.position(2);
		gl.glTexCoordPointer(2, GL2.GL_FLOAT, 16, danger);
					
		gl.glDrawElements(GL2.GL_TRIANGLES, 6, GL2.GL_UNSIGNED_SHORT, indices);
		
	}
	private void showTurboBar(){
		
		updateTurboBar(game.wbcs.get(game.wbc_ID).turbo);
	turboBar.position(0);
	
	gl.glVertexPointer(2, GL2.GL_FLOAT, 16, turboBar);
	turboBar.position(2);
	gl.glTexCoordPointer(2, GL2.GL_FLOAT, 16, turboBar);
				
	gl.glDrawElements(GL2.GL_TRIANGLES, 6, GL2.GL_UNSIGNED_SHORT, indices);
	
	}
	private void updateTurboBar(int turbo) {
		// TODO Auto-generated method stub
		turboBar.clear();
		turboBar.put(new float[]{0f,0.0f,0,0.67f,
				0.0f,0.03f,0,0.72f,
				0.08f*(turbo/100.0f),0.03f,0.25f,0.72f,
				0.08f*(turbo/100.0f),0,0.25f,0.67f});
		turboBar.flip();
	}
}
