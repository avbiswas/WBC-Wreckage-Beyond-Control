package glexperiments;

import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Random;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class WBC {
	final float WBCheight=0.07f;
	final float WBCwidth=0.05f;
	boolean sashaMode=false;
	boolean engulfMode=false;
	final int VERTEX_SIZE=(4)*4;
	int turbo=100;
	boolean turboON=false;
	long time;
	boolean turboAnimator=false;
	boolean danger=false;
	boolean destroyed=false;
	public int dissolveAnimator=0;
	int angle_Of_Movement;
	FloatBuffer vertices;
	FloatBuffer vertices1;
	FloatBuffer vertices2;
	FloatBuffer vertices3;
	FloatBuffer turbo1R;
	FloatBuffer turbo2R;
	FloatBuffer turbo1L;
	FloatBuffer turbo2L;
	FloatBuffer glassL;
	FloatBuffer glassR;
	FloatBuffer glassF;
	FloatBuffer[] dissolve;
	ShortBuffer indices;
	Texture T;
	GameScreen game;
	FloatBuffer pixel1;
	FloatBuffer pixel2;
	int width,height;
	int stateX;
	int stateY;
	int texture;
	float maxVelocity;
	boolean stuckBottom;
	boolean stuckTop;
	boolean stuckLeft;
	boolean stuckRight;
	Grid bounds;
	float x,y;
	float Xvelocity;
	float Yvelocity;
	float bloodFlowVel;
	float angle;
	private int orientation;
	int hashPos;
	GL2 gl;
	public WBC(GL2 gl,GameScreen g,float inX,float inY){
		this.gl=gl;
		ByteBuffer pixeldata1;
		pixeldata1=ByteBuffer.allocateDirect(4*3).order(ByteOrder.nativeOrder());
		ByteBuffer pixeldata2=ByteBuffer.allocateDirect(4*3).order(ByteOrder.nativeOrder());
	    pixel1=pixeldata1.asFloatBuffer();
	    pixel2=pixeldata2.asFloatBuffer();
		game=g;
		x=inX;
		y=inY;
		angle_Of_Movement=315+randomNumberGenerator(90);
		if (angle_Of_Movement>360){
			angle_Of_Movement-=360;
		}
		changeStateXY(angle_Of_Movement);
		//maxVelocity=0.02f;
		hashPos=0;
		stateX=0;
		stateY=0;
		angle=0;
		stuckBottom=false;
		stuckTop=false;
		stuckLeft=false;
		stuckRight=false;
		height=(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		width=(int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		maxVelocity=28/(float)width;
		bloodFlowVel=14/(float)width;
		ByteBuffer byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		vertices1=byteBuffer.asFloatBuffer();
		vertices1.put(new float[]{0f,0.0f,0.0f,0.21f,
								WBCwidth,0.0f,1.0f,0.21f,
								WBCwidth,WBCheight,1.0f,0.31f,
								0.0f,WBCheight,0.0f,0.31f});
		vertices1.flip();
		
		byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		vertices=byteBuffer.asFloatBuffer();
		vertices.put(new float[]{0f,0.0f,0.0f,0.1f,
								WBCwidth,0.0f,1.0f,0.1f,
								WBCwidth,WBCheight,1.0f,0.2f,
								0.0f,WBCheight,0.0f,0.2f});
		vertices.flip();
		
		byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		vertices2=byteBuffer.asFloatBuffer();
		vertices2.put(new float[]{WBCwidth,00f,0.0f,0.0f,
				0,0,1,0.0f,
				0,WBCheight,1,0.1f,
				WBCwidth,WBCheight,0,0.1f});

		vertices2.flip();
		
		byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		vertices3=byteBuffer.asFloatBuffer();
		vertices3.put(new float[]{0f,0.0f,0.0f,0.0f,
								WBCwidth,0.0f,1.0f,0.0f,
								WBCwidth,WBCheight,1.0f,0.1f,
								0.0f,WBCheight,0.0f,0.1f});
		vertices3.flip();
		

		byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		glassR=byteBuffer.asFloatBuffer();
		glassR.put(new float[]{0f,0.0f,0.0f,0.32f,
								WBCwidth,0.0f,1.0f,0.32f,
								WBCwidth,WBCheight,1.0f,0.42f,
								0,WBCheight,0.0f,0.42f});
		glassR.flip();
		
		byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		glassF=byteBuffer.asFloatBuffer();
		glassF.put(new float[]{0f,0.0f,0.0f,0.43f,
								WBCwidth,0.0f,1.0f,0.43f,
								WBCwidth,WBCheight,1.0f,0.53f,
								0.0f,WBCheight,0.0f,0.53f});
		glassF.flip();
		
		byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		glassL=byteBuffer.asFloatBuffer();
		glassL.put(new float[]{WBCwidth,0.0f,0.0f,0.32f,
								0.0f,0.0f,1.0f,0.32f,
								0,WBCheight,1.0f,0.42f,
								WBCwidth,WBCheight,0.0f,0.42f});
		glassL.flip();
		
		
		byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		turbo1R=byteBuffer.asFloatBuffer();
		turbo1R.put(new float[]{0f,0.0f,0.0f,0.540f,
								WBCwidth,0.0f,1.0f,0.540f,
								WBCwidth,WBCheight,1.0f,0.63f,
								0.0f,WBCheight,0.0f,0.63f});
		turbo1R.flip();
		
		
		byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		turbo1L=byteBuffer.asFloatBuffer();
		turbo1L.put(new float[]{WBCwidth,0.0f,0.0f,0.540f,
								0,0.0f,1.0f,0.540f,
								0,WBCheight,1.0f,0.63f,
								WBCwidth,WBCheight,0.0f,0.63f});
		turbo1L.flip();
		

		byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		turbo2R=byteBuffer.asFloatBuffer();
		turbo2R.put(new float[]{0f,0.0f,0.1f,0.64f,
								WBCwidth,0.0f,1.0f,0.64f,
								WBCwidth,WBCheight,1.0f,0.72f,
								0.0f,WBCheight,0.1f,0.72f});
		turbo2R.flip();
		byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		turbo2L=byteBuffer.asFloatBuffer();
		turbo2L.put(new float[]{WBCwidth,0.0f,0.0f,0.64f,
								0,0.0f,1.0f,0.64f,
								0,WBCheight,1.0f,0.72f,
								WBCwidth,WBCheight,0.0f,0.72f});
		turbo2L.flip();
		
		dissolve=new FloatBuffer[3];
		for (int i=0;i<3;i++){
			byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
			byteBuffer.order(ByteOrder.nativeOrder());
			dissolve[i]=byteBuffer.asFloatBuffer();
			dissolve[i].put(new float[]{WBCwidth,0.0f,0.0f,0.72f+(0.1f*i),
									0,0.0f,1.0f,0.72f+(0.1f*i),
									0,WBCheight,1.0f,0.82f+(0.1f*i),
									WBCwidth,WBCheight,0.0f,0.80f+(0.1f*i)});
			dissolve[i].flip();
			
				
		}
		byteBuffer=ByteBuffer.allocateDirect(6*2);
		byteBuffer.order(ByteOrder.nativeOrder());
		indices=byteBuffer.asShortBuffer();
		indices.put(new short[]{0,1,2,2,3,0});
		indices.flip();
		
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glClearColor(0f, 0f, 0f, 0.0f);
		//gl.glClearDepth(1.0f);
		//gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LEQUAL);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
		
		gl.glEnable(GL2.GL_TEXTURE_2D);
		
	   InputStream stream = null;
		stream = this.getClass().getClassLoader().getResourceAsStream("wbc_full.png");
		
            
            try {
            	TextureData data = TextureIO.newTextureData(GLProfile.getDefault(), stream, true, "png");
            	 
            	T = TextureIO.newTexture(data);
                 
            } catch (IOException e){
            	e.printStackTrace();
            	System.exit(1);
            }
            
            
            texture=T.getTextureObject();
            
            orientation=1;
            bounds=game.hash;
    }
	public void setPresentOrientation(int o){
		this.orientation=o;
	}
	public void setStateX(int state){
		stateX=state;
		if (stateX==0){
			Xvelocity=0;
		}
		
	}
	public void toggleSpeed(){
		maxVelocity= (maxVelocity==0.02f)?0.035f:0.02f;
		if (maxVelocity==0.035f){
			time=System.currentTimeMillis();
			turboON=true;
		}
		else{
			turboON=false;
		}
	}
	public void setSpeed(float v){
		Xvelocity=v;
		Yvelocity=v;
	}
	public void setStateY(int state){
		stateY=state;
		if (stateY==0){
			Yvelocity=0;
		}
		
	}
	public void showWBC(){
		if (germsInRadius(0.4f).size()>=1){
			danger=true;
		}
		else{
			danger=false;
		}
		
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		gl.glTranslatef(x,y,0);
			//gl.glEnable(GL2.GL_TEXTURE_2D);
				T.bind(gl);
				
			if (engulfMode){
			display(dissolve[dissolveAnimator/3]);
			
			dissolveAnimator++;
			if (dissolveAnimator>8){
				destroyed=true;
			}
			return;
		}
		switch(orientation){
			case 1: if (turboON){
						if(turboAnimator){
							display(turbo1R);
						}
						else{
							display(turbo2R);
						}
						turboAnimator=!turboAnimator;
						}
				else if(sashaMode){
						display(glassR);
					}
					else{
					display(vertices3);
					}
					break;
			case 2: 
				if (turboON){
					if(turboAnimator){
						display(turbo1L);
					}
					else{
						display(turbo2L);
					}
					turboAnimator=!turboAnimator;
				}
				else if(sashaMode){
					display(glassL);
				}
				else{
					display(vertices2);
				}
					break;
			case 3: 
					if(sashaMode){
						display(glassR);
					}
						display(vertices);
					break;
			case 4: display(vertices1);
		}
		
		if (turboON){
			turbo();
		}
		if (game.gameState==game.go_Main_Menu){
			int [] textureIDS={texture};
			gl.glDeleteTextures(1, textureIDS, 0);
			
		}
				
}
	private void turbo() {
		// TODO Auto-generated method stub
		long tempTime=System.currentTimeMillis();
		
		if (tempTime-time>1000){
			
			turbo--;

			time=tempTime;
		}
		if (turbo==0){
			toggleSpeed();
		}
	}
	void display(FloatBuffer vertices){
		vertices.position(0);
		
		gl.glVertexPointer(2, GL2.GL_FLOAT, VERTEX_SIZE, vertices);
		vertices.position(2);
		gl.glTexCoordPointer(2, GL2.GL_FLOAT, VERTEX_SIZE, vertices);
		//gl.glEnable(GL2.GL_BLEND);
		//gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		
		// checkIt(pixel); 
					
		gl.glDrawElements(GL2.GL_TRIANGLES, 6, GL2.GL_UNSIGNED_SHORT, indices);
		
		
	}
	void computeX() {
		// TODO Auto-generated method stub
		switch(stateX){
		case 0:	Xvelocity=0;
				break;
			
			
		case 1: 
			
			Xvelocity+=(float) (0.6*Math.abs((double)Yvelocity)+0.005);
			if (Xvelocity>maxVelocity) {
				Xvelocity=(float) maxVelocity;
			}
			break;
		
		
		case -1:
			Xvelocity-=(float) (0.6*Math.abs((double)Yvelocity)+0.005);
			if (Xvelocity<-maxVelocity) {
				Xvelocity=(float) -maxVelocity;
			}
			break;
		
		}
		
	}
	private int gethashPos(float x, float y) {
		// TODO Auto-generated method stub
		int temp=7;
		float tempX, tempY;
		tempX=(x+1)/2;
		tempY=(y+1)/2;
		tempX=tempX*temp;
		tempY=tempY*temp;
		return ((int)tempY*temp+(int)tempX);
	}
	void computeY() {
		// TODO Auto-generated method stub
		switch(stateY){
		case 0:	
						Yvelocity=0;
				break;
			
			
		case 1: 
			
				Yvelocity+=(float) (0.6*Math.abs((double)Xvelocity)+0.005);
				if (Yvelocity>maxVelocity) {
					Yvelocity=(float) maxVelocity;
				}
				break;
			
			
		case -1:
			Yvelocity-=(float) (0.6*Math.abs((double)Xvelocity)+0.005);
			if (Yvelocity<-maxVelocity) {
				Yvelocity=(float) -maxVelocity;
			}
			break;
		}
		
		
	}
	public void changeX(){
		float tempX=x;
		
		boolean flag2=false,flag3=false;
		if (stateX==-1){
			tempX=x+WBCwidth;
		}
		tempX+=Xvelocity+bloodFlowVel;
		
		hashPos=gethashPos(tempX+(stateX)*WBCwidth,y);
		for (int i=0;i<bounds.grid.get(hashPos).size();i++){
			if (bounds.grid.get(hashPos).get(i).isWithin(tempX+(stateX)*WBCwidth,y)){
				
				flag2=true;				//y=y+Yvelocity;
			}
		}
		hashPos=gethashPos(tempX+(stateX)*WBCwidth,y+WBCheight);
		for (int i=0;i<bounds.grid.get(hashPos).size();i++){
			if (bounds.grid.get(hashPos).get(i).isWithin(tempX+(stateX)*WBCwidth,y+WBCheight)){
				
				flag3=true;				//y=y+Yvelocity;
			}
		}
		if (flag2 && flag3){
			x=x+Xvelocity;
		}
		if (!flag2){
			if (flag3){
				y=y+0.02f;
			}
			else{
			x=x-(stateX)*0.01f;
			setSpeed(0);
			}
		}
		if (!flag3){
			if (flag2){
				y=y-0.02f;
			}
			else{
			x=x-(stateX)*0.01f;
			setSpeed(0);
			}
		}
		hashPos=gethashPos(x,y);
		
	}
	public void changeY(){
		float tempY=y;
		boolean flag1=false,flag2=false;
		if (stateY==1){
			tempY=y+WBCheight;
		}
		tempY+=Yvelocity;
		angle=0;
		hashPos=gethashPos(x,tempY);
		

		
		for (int i=0;i<bounds.grid.get(hashPos).size();i++){
			
			if (bounds.grid.get(hashPos).get(i).isWithin(x,tempY)){
			
			flag1=true;
			}
		}
		
		hashPos=gethashPos(x+WBCwidth,tempY);
		for (int i=0;i<bounds.grid.get(hashPos).size();i++){
			
			if (bounds.grid.get(hashPos).get(i).isWithin(x+WBCwidth,tempY)){
				
				flag2=true;
				
			}
		}
		if (flag1 && flag2){
			y=y+Yvelocity;
		}
		if (!flag1){
			if (flag2){
				x=x+0.02f;
			}
			else{
			y=y-(stateY)*0.015f;
			}
			//System.out.println("flag1");
		}
		if (!flag2){
			//System.out.println("flag2");
			if (flag1){
				x=x-0.02f;
			}
			else{
			y=y-(stateY)*0.015f;
			}
		}
		hashPos=gethashPos(x,y);
		
	}
	
	public ArrayList<Integer> germsInRadius(float rad){
		Vector wbcPos=new Vector(x+WBCwidth/2,y+WBCheight/2);
		ArrayList<Vector> germLocations=game.getGermsLocation();
		ArrayList<Integer> germsInRadius=new ArrayList<Integer>();
		for (int i=0;i<germLocations.size();i++){
			if(wbcPos.EllipseChecker(germLocations.get(i),rad)){
				germsInRadius.add(i);
			}
		}
		
		
		return germsInRadius;
		
	}
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	public float getVel(){
		return Xvelocity;
	}
		public void sashaMode(){
		sashaMode=!sashaMode;
		if (turboON)toggleSpeed();
	}
	public ArrayList<Integer> activateEngulfMode() {
		// TODO Auto-generated method stub
		engulfMode=true;
		ArrayList<Integer> germsToKill=germsInRadius(0.27f);
		if (germsToKill.size()==0){
			engulfMode=false;
			game.stuff.audio.error();
		}
		
		return germsToKill;
	}
	private boolean checkCollision(Vector temp) {
		// TODO Auto-generated method stub
		
		
		int tempHash=gethashPos(temp.x,temp.y);

		for (int i=0;i<bounds.grid.get(tempHash).size();i++){
			
			if (bounds.grid.get(tempHash).get(i).isWithin(temp.x,temp.y)){
				return true;
			}
		}
		return false;
	}
	public void moveRandomly(){
		Vector tempLB=new Vector((float)(x+Math.cos(angle_Of_Movement)*bloodFlowVel),(float)((y+Math.sin(angle_Of_Movement)*bloodFlowVel)));
		Vector tempLT=new Vector((float)(x+Math.cos(angle_Of_Movement)*bloodFlowVel),(float)((y+WBCheight+Math.sin(angle_Of_Movement)*bloodFlowVel)));
		Vector tempRB=new Vector((float)(x+WBCwidth+Math.cos(angle_Of_Movement)*bloodFlowVel),(float)((y+Math.sin(angle_Of_Movement)*bloodFlowVel)));
		Vector tempRT=new Vector((float)(x+WBCwidth+Math.cos(angle_Of_Movement)*bloodFlowVel),(float)((y+WBCheight+Math.sin(angle_Of_Movement)*bloodFlowVel)));
		
		if (checkCollision(tempLB) && checkCollision(tempLT) && checkCollision(tempRB) && checkCollision(tempRT)){
			x=tempLB.x;
			y=tempLB.y;
		}
		else{
			angle_Of_Movement=randomNumberGenerator(360);
			changeStateXY(angle_Of_Movement);
			
		}
		
	}
	private int randomNumberGenerator(int max) {
		// TODO Auto-generated method stub
		Random rand=new Random();
		return (int)Math.abs(((rand.nextFloat()*System.currentTimeMillis())%max));
		
	}
	private void changeStateXY(int angle) {
		// TODO Auto-generated method stub
		if (angle<=0 && angle<90){
			stateX=-1;
			stateY=1;
		}
		if (angle<=90 && angle<180){
			stateX=1;
			stateY=1;
		}if (angle<=180 && angle<270){
			stateX=1;
			stateY=-1;
		}if (angle<=0 && angle<90){
			stateX=-1;
			stateY=-1;
		}
		if (angle>45 && angle<135){
			orientation=3;
		}
		if (angle>135 && angle<225){
			orientation=1;
		}if (angle>225 && angle<315){
			orientation=4;
		}if (angle>315 && angle<45){
			orientation=2;
		}
	}
	
}
