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

public class MainMenu implements Screen {
	final int Main_menu_mode=1;
	final int SinglePlayer_menu_mode=2;
	long time;
	final int START_GAME=3;
	final int chooseOptions_mode=4;
	final int CUSTOM_GAME=5;
	final int settingsMode=6;
	final int instructionMode=7;
	int state;
	int cursor_position;
	int VERTEX_SIZE=16;
	GL2 gl;
	float tempCursorY;
	float tempCursorX;
	FloatBuffer menu;
	FloatBuffer cursor;
	FloatBuffer Settings;
	FloatBuffer NewGame;
	FloatBuffer chooseGame;
	FloatBuffer numbers;
	FloatBuffer germNumbers;
	FloatBuffer germNumbers10;
	FloatBuffer rightWrong;
	
	int userInputWBC=0;
	int userInputGerms=0;
	ShortBuffer indices;
	Texture T;
	GameScreen game;
	int texture;
	DOSTUFF stuff;
	private boolean sound;
	
	public MainMenu(DOSTUFF stuff){
		this.stuff=stuff;
	}
	public void initialize(GL2 gl){
		stuff.audio.playTheme();
			cursor_position=1;
		tempCursorY=0.15f;
		tempCursorX=-0.3f;
		state=Main_menu_mode;
		sound=true;
		ByteBuffer byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		menu=byteBuffer.asFloatBuffer();
		menu.put(new float[]{-0.90f,-0.90f,0.0f,0.550f,
								0.90f,-0.90f,1.0f,0.550f,
								0.90f,.90f,1.0f,1.0f,
								-.90f,.90f,0.0f,1.0f});
		menu.flip();
		byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		NewGame=byteBuffer.asFloatBuffer();
		NewGame.put(new float[]{-0.350f,-0.350f,0.0f,0.0f,
								0.350f,-0.350f,0.33f,0.0f,
								0.350f,.350f,0.33f,0.1250f,
								-.350f,.350f,0.0f,0.1250f});
		NewGame.flip();
		
		byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		chooseGame=byteBuffer.asFloatBuffer();
		chooseGame.put(new float[]{-0.250f,-0.250f,0.50f,0.0f,
								0.250f,-0.250f,01.0f,0.0f,
								0.250f,.250f,01.0f,0.2f,
								-.250f,.250f,0.50f,0.2f});
		chooseGame.flip();
		
		byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		cursor=byteBuffer.asFloatBuffer();
		cursor.put(new float[]{-0.03f,-0.03f,0.0f,0.50f,
								0.03f,-0.03f,0.10f,0.50f,
								0.03f,.03f,0.10f,0.550f,
								-.03f,.03f,0.0f,0.55f});
		cursor.flip();
		byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		numbers=byteBuffer.asFloatBuffer();
		
		byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		germNumbers=byteBuffer.asFloatBuffer();
		
		byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		germNumbers10=byteBuffer.asFloatBuffer();
		
		
		byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		Settings=byteBuffer.asFloatBuffer();
		Settings.put(new float[]{-0.40f,-0.50f,0.50f,0.250f,
								0.40f,-0.50f,1.0f,0.250f,
								0.40f,.50f,1.0f,0.50f,
								-.40f,.50f,0.50f,0.50f});
		Settings.flip();
		
		byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		rightWrong=byteBuffer.asFloatBuffer();
		updateRightWrong();
			
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
		stream = this.getClass().getClassLoader().getResourceAsStream("menu.png");
		
            
            try {
            	TextureData data = TextureIO.newTextureData(GLProfile.getDefault(), stream, true, "png");
            	 
            	T = TextureIO.newTexture(data);
                 
            } catch (IOException e){
            	e.printStackTrace();
            	System.exit(1);
            }
            
                  
            texture=T.getTextureObject();
            time =System.currentTimeMillis();
           }
	@Override
	public void display(GL2 gl){
			gl.glEnable(GL2.GL_BLEND);
			gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		//current_PositionX=convertX(game.getWBCS()[0].getX());
		//current_PositionY=convertY(game.getWBCS()[0].getY());
			//gl.glMatrixMode(GL2.GL_MODELVIEW);
			gl.glLoadIdentity();
		
		
				gl.glEnable(GL2.GL_TEXTURE_2D);
				//T.bind(gl);
				gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);
				
				gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
				gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
				
				menu.position(0);
				
				gl.glVertexPointer(2, GL2.GL_FLOAT, VERTEX_SIZE, menu);
				menu.position(2);
				gl.glTexCoordPointer(2, GL2.GL_FLOAT, VERTEX_SIZE, menu);
				
				gl.glDrawElements(GL2.GL_TRIANGLES, 6, GL2.GL_UNSIGNED_SHORT, indices);
				gl.glLoadIdentity();
				if (state==settingsMode){
					displayMenu(gl, Settings);
					displayMenu(gl, rightWrong);
				}
				if (state==SinglePlayer_menu_mode||state==chooseOptions_mode){
					displayMenu(gl,NewGame);
					if (state==chooseOptions_mode){
						displayMenu(gl,chooseGame);
							displayMenu(gl,numbers);
							displayMenu(gl,germNumbers);
							displayMenu(gl, germNumbers10);
							
							
					}
				}
				if (stuff.getInput().getKey().size()>0){
				updateCursorPos();
				}
				
				gl.glMatrixMode(GL2.GL_MODELVIEW);
				gl.glLoadIdentity();
				gl.glTranslatef(tempCursorX,tempCursorY,0);

				cursor.position(0);
				
				gl.glVertexPointer(2, GL2.GL_FLOAT, VERTEX_SIZE, cursor);
				cursor.position(2);
				gl.glTexCoordPointer(2, GL2.GL_FLOAT, VERTEX_SIZE, cursor);
				gl.glDrawElements(GL2.GL_TRIANGLES, 6, GL2.GL_UNSIGNED_SHORT, indices);
				
				if (state==START_GAME){
					int [] textureIDS={texture};
					gl.glDeleteTextures(1, textureIDS, 0);
					game=new GameScreen(stuff,0);
					game.initialize(gl);
					stuff.setCurrentScreen(game);
					
				}
				if (state==CUSTOM_GAME){

					int [] textureIDS={texture};
					gl.glDeleteTextures(1, textureIDS, 0);
					game=new GameScreen(stuff,userInputWBC,userInputGerms);
					game.initialize(gl);
					stuff.setCurrentScreen(game);
				
				}
				if (state==instructionMode){
					int [] textureIDS={texture};
					gl.glDeleteTextures(1, textureIDS, 0);
					Instructions instructions=new Instructions(stuff);
					instructions.initialize(gl);
					stuff.setCurrentScreen(instructions);
				
				}
	}
	private void displayMenu(GL2 gl,FloatBuffer NewGame) {
		// TODO Auto-generated method stub
		NewGame.position(0);
		
		gl.glVertexPointer(2, GL2.GL_FLOAT, VERTEX_SIZE, NewGame);
		NewGame.position(2);
		gl.glTexCoordPointer(2, GL2.GL_FLOAT, VERTEX_SIZE, NewGame);
		
		gl.glDrawElements(GL2.GL_TRIANGLES, 6, GL2.GL_UNSIGNED_SHORT, indices);
	}
	private void updateCursorPos() {
		// TODO Auto-generated method stub
		int input=stuff.getInput().getKey().get(0);
		if(state==Main_menu_mode){
		switch(input){
			case KeyEvent.VK_UP: cursor_position--;

						if (cursor_position==2)cursor_position=1;
								stuff.audio.MenuSelect();
								if (cursor_position==0){cursor_position=4;}
								break;
								
			case KeyEvent.VK_DOWN: cursor_position++;
									if (cursor_position==2)cursor_position=3;
									stuff.audio.MenuSelect();
									
								if (cursor_position==5) cursor_position=1;
								break;
			case KeyEvent.VK_ENTER:stuff.audio.Enter();
			
							switch (cursor_position){
								case 1:state=SinglePlayer_menu_mode;
										tempCursorX=-0.3f;
										tempCursorY=0.1f;
										cursor_position=1;
										break;
								case 3:state=instructionMode;
										break;
								case 4: state=settingsMode;
										tempCursorY=0.3f;
										tempCursorX=-0.3f;
										break;
								}
							break;
			case KeyEvent.VK_ESCAPE:stuff.audio.stopTheme();
									System.exit(0);
									break;
		}
		if (state==Main_menu_mode){
		switch(cursor_position){
		case 1: tempCursorY=0.15f;break;
		case 2: cursor_position++;tempCursorY=-0.f;break;
		case 3: tempCursorY=-0.29f; break;
		case 4: tempCursorY=-0.51f;
		}
		}
		}
		else if (state==SinglePlayer_menu_mode){
			switch(input){
			case KeyEvent.VK_UP: cursor_position--;
								if(cursor_position==0)cursor_position=2;
								
								
								stuff.audio.MenuSelect();
								break;
			case KeyEvent.VK_DOWN: cursor_position++;
									if (cursor_position==3)cursor_position=1;
									
									
									stuff.audio.MenuSelect();
									break;
			case KeyEvent.VK_ESCAPE: tempCursorX=-0.3f;
									tempCursorY=0.15f;
									state=Main_menu_mode;
									stuff.audio.error();
									
									break;
			case KeyEvent.VK_ENTER: stuff.audio.Enter();
			
									switch(cursor_position){
									case 1: state=START_GAME;
											break;
									case 2:state=chooseOptions_mode;
											cursor_position=1;
											tempCursorX=-0.05f;
											tempCursorY=0.05f;
											break;
									}
			}
			if (state==SinglePlayer_menu_mode){
			switch(cursor_position){
			case 1: tempCursorY=0.1f;break;
			case 2: tempCursorY=-0.05f;break;
			}
			
			}
		}
		else if (state==settingsMode){
			switch(input){
			case KeyEvent.VK_ENTER: togglesound();
									break;
			case KeyEvent.VK_ESCAPE: tempCursorX=-0.3f;
									tempCursorY=0.15f;
									state=Main_menu_mode;
									stuff.audio.error();
			
			}
		}
		else if(state==chooseOptions_mode){
			switch(input){
			case KeyEvent.VK_UP:cursor_position--;
								stuff.audio.MenuSelect();
								if (cursor_position==0)cursor_position=2;break;

			case KeyEvent.VK_DOWN:cursor_position++;
									stuff.audio.MenuSelect();
								if (cursor_position==3)cursor_position=1;break;
			case KeyEvent.VK_ESCAPE:state=SinglePlayer_menu_mode;
									tempCursorX=-0.3f;
									tempCursorY=0.1f;
									cursor_position=1;
									stuff.audio.error();
									break;
			case KeyEvent.VK_ENTER:
				
				if(userInputWBC>0 && userInputGerms>0 && userInputGerms<=25){
					state=CUSTOM_GAME;
					stuff.audio.Enter();
				}
				else{
					stuff.audio.error();
				}
					break;
			}
			if (input>=KeyEvent.VK_0 && input<=KeyEvent.VK_9){
				stuff.audio.Enter();
				if (cursor_position==1){
					userInputWBC=(input-KeyEvent.VK_0);
					updateWBCNumber(userInputWBC,0);
				}
				if (cursor_position==2){
					userInputGerms=userInputGerms*10+(input-KeyEvent.VK_0);
					if(userInputGerms>25){
						userInputGerms=0;
					}
					
					updateGermNumber10(userInputGerms%10);
					updateGermNumber(userInputGerms/10);
				}
			}
			
			switch(cursor_position){
			case 1: tempCursorY=0.05f;break;
			case 2: tempCursorY=-0.15f;break;
			}
			
		}
	}
	private void togglesound() {
		// TODO Auto-generated method stub
		sound=!sound;
		updateRightWrong();
		stuff.audio.toggleMute();
	}
	private void updateRightWrong() {
		// TODO Auto-generated method stub
		rightWrong.clear();
		if (sound){
		rightWrong.put(new float[]{0f,0.3f,00,0.45f,
									0f,0.5f,0,0.5f,
									0.1f,0.5f,0.08f,0.5f,
									0.1f,0.3f,0.08f,0.45f});
		}
		else{
			rightWrong.put(new float[]{0.0f,0.3f,0.08f,0.45f,
					0.0f,0.5f,0.08f,0.5f,
					0.1f,0.5f,0.2f,0.5f,
					0.1f,0.3f,0.2f,0.45f});
	
		}
		rightWrong.flip();
								
	
	}
	private void updateWBCNumber(int score, float offset) {
		// TODO Auto-generated method stub
		numbers.clear();
			
			numbers.put(new float[]{0,0.0f,     0.49f+(0.05f*score),0.5f,
					0,0.1f,              0.49f+(0.05f*score),0.55f,
					0.05f,0.1f,     0.49f+(0.05f*(score+0.97f)),0.55f,
					0.05f,0.0f,    0.5f+(0.05f*(score+0.97f)),0.5f});
			
		
		numbers.flip();
	}
	private void updateGermNumber(int score) {
		// TODO Auto-generated method stub
		germNumbers.clear();
			
			germNumbers.put(new float[]{0,-0.20f,     0.49f+(0.05f*score),0.5f,
					0,-0.1f,              0.49f+(0.05f*score),0.55f,
					0.05f,-0.1f,     0.49f+(0.05f*(score+0.97f)),0.55f,
					0.05f,-0.20f,    0.5f+(0.05f*(score+0.97f)),0.5f});
			
		
		germNumbers.flip();
	}
	private void updateGermNumber10(int score) {
		// TODO Auto-generated method stub
		germNumbers10.clear();
			
			germNumbers10.put(new float[]{0.06f,-0.20f,     0.49f+(0.05f*score),0.5f,
					0.06f,-0.1f,              0.49f+(0.05f*score),0.55f,
					0.11f,-0.1f,     0.49f+(0.05f*(score+0.97f)),0.55f,
					0.11f,-0.20f,    0.5f+(0.05f*(score+0.97f)),0.5f});
			
		
		germNumbers10.flip();
	}
	@Override
	public void dispose(){
		//gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);
		//int [] textureIDS={texture};
		//gl.glDeleteTextures(1, textureIDS, 0);
	}
	
}
