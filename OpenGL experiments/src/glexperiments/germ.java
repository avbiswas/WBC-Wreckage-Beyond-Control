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

public class germ {
	
	boolean engulf=false;
	boolean destroyed=false;
	final int VERTEX_SIZE=(4)*4;
	final float germHeight=0.11f;
	final float germWidth=0.08f;
	int id;
	int caughtWBC;
	GameScreen game;
	Random rand;
	int targetWBC;
	Vector lastKnownTargetLoc;
	FloatBuffer vertices;
	FloatBuffer vertices2;
	FloatBuffer verticesChase1;
	FloatBuffer verticesChase2;
	ShortBuffer indices;
	FloatBuffer pixel1;
	FloatBuffer pixel2;
	FloatBuffer[] EngulfAnimation;
	int animateState;
	int height;
	int width;
	int angle_Of_Movement;
	Texture T;
	int stateX;
	int stateY;
	int texture;
	int movementMode;
	int hashOrder;
	float x,y;
	float velocityX;
	float velocityY;
	float chaseVelocity;
	
	
	float bloodFlowVel=(float) 0.023;
	float angle;
	boolean seeMode;
	ArrayList<Vector>wbcsLocation;
	Grid hash;
	Boundary b;
	int hashPos;
	GL2 gl;
	boolean orientation;
	private int renderImmobile=0;
	public germ(GL2 gl,GameScreen G,int id){
		this.gl=gl;
		this.id=id;
		animateState=5; 
		height=(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		width=(int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		bloodFlowVel=28/(float)width;
		chaseVelocity=34/(float)width;
		caughtWBC=-1;
		hashOrder=7;
		game=G;
		movementMode=0;
		x=0.15f;
		y=0.5f;
		velocityX=0.02f;
		velocityY=0.02f;
		wbcsLocation=new ArrayList<Vector>();
		angle=0;
		seeMode=false;
		b=G.getBoundary();
		hash=G.hash;
		rand=new Random();
		if (id%2==0){
			orientation=false;
		}
		else{
			orientation=true;
		}
		height=(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		width=(int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		lastKnownTargetLoc=new Vector();
		stateX= randomNumberGenerator(2,0);
		stateY=randomNumberGenerator(2,0);
		angle_Of_Movement=randomNumberGenerator(360);
		changeStateXY(angle_Of_Movement);
		
		ByteBuffer pixeldata1;
		pixeldata1=ByteBuffer.allocateDirect(4*3).order(ByteOrder.nativeOrder());
		ByteBuffer pixeldata2=ByteBuffer.allocateDirect(4*3).order(ByteOrder.nativeOrder());
	    pixel1=pixeldata1.asFloatBuffer();
	    pixel2=pixeldata2.asFloatBuffer();
		
		
		
		
		
		ByteBuffer byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		vertices=byteBuffer.asFloatBuffer();
		vertices.put(new float[]{0f,0.0f,0.0f,0.30f,
								germWidth,0.0f,1.0f,0.30f,
								germWidth,germHeight,1.0f,0.405f,
								0.0f,germHeight,0.0f,0.405f});
		vertices.flip();
		byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		vertices2=byteBuffer.asFloatBuffer();
		vertices2.put(new float[]{0f,0.0f,0.0f,0.20f,
								germWidth,0.0f,1.0f,0.20f,
								germWidth,germHeight,1.0f,0.3f,
								0.0f,germHeight,0.0f,0.3f});
		vertices2.flip();
		

		byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		verticesChase1=byteBuffer.asFloatBuffer();
		verticesChase1.put(new float[]{0f,0.0f,0.0f,0.10f,
								germWidth,0.0f,1.0f,0.10f,
								germWidth,germHeight,1.0f,0.20f,
								0.0f,germHeight,0.0f,0.20f});
		verticesChase1.flip();

		byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		verticesChase2=byteBuffer.asFloatBuffer();
		verticesChase2.put(new float[]{0f,0.0f,0.0f,0.01f,
								germWidth,0.0f,1.0f,0.01f,
								germWidth,germHeight,1.0f,0.1f,
								0.0f,germHeight,0.0f,0.1f});
		verticesChase2.flip();
		
		EngulfAnimation=new FloatBuffer[6];
		for (int i=0;i<6;i++){
		byteBuffer=ByteBuffer.allocateDirect(4*(2+2)*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		EngulfAnimation[i]=byteBuffer.asFloatBuffer();
		EngulfAnimation[i].put(new float[]{0f,0.0f,0.0f,(0.4f+i*0.1f),
								germWidth,0.0f,1.0f,(0.4f+i*0.1f),
								germWidth,germHeight,1.0f,(0.5f+i*0.1f),
								0.0f,germHeight,0.0f,(0.5f+i*0.1f)});
		EngulfAnimation[i].flip();
		}
		
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
		stream = this.getClass().getClassLoader().getResourceAsStream("germ_full.png");
		
		
            try {
            	TextureData data = TextureIO.newTextureData(GLProfile.getDefault(), stream, true, "png");
            	 
            	T = TextureIO.newTexture(data);
                 
            } catch (IOException e){
            	e.printStackTrace();
            	System.exit(1);
            }
            
                  
            texture=T.getTextureObject();
          
         
            
	}
	private int randomNumberGenerator(int max, int except) {
		// TODO Auto-generated method stub
		
		long x;
		do{
		x=(rand.nextInt()*System.currentTimeMillis())%max;
		}
		while (x==except);
		
		
		
		return (int)x;
	}
	private int randomNumberGenerator(int max) {
		// TODO Auto-generated method stub
		
		return (int)Math.abs(((rand.nextFloat()*System.currentTimeMillis())%max));
		
		
		
		
		
	}
	public void setStateX(int state){
		this.stateX=state;
		
	}
	public void setStateY(int state){
		this.stateY=state;
	}
	
	
	public void showGerm(){
		gl.glEnable(GL2.GL_BLEND);
		//gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		gl.glTranslatef(x,y,0);
			//	gl.glEnable(GL2.GL_TEXTURE_2D);
				T.bind(gl);
				
				//gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
				//gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
			if (destroyed){
				return;
			}
			if (engulf){
				display(EngulfAnimation[animateState]);
				animateState--;
				if (animateState==0){
					destroyed=true;
				}
				return;
			}
				if (seeMode){
					if (orientation){
						display(verticesChase1);
					}
					else{
						display(verticesChase2);
					}
					
				}
				else{
					if (orientation){
						display(vertices);
					}
					else{
						display(vertices2);
					}
					
				}
			
				orientation=!orientation;
			
				if (game.gameState==game.go_Main_Menu){
					int [] textureIDS={texture};
					gl.glDeleteTextures(1, textureIDS, 0);
					
				}	
				
				
	}
	public void display(FloatBuffer vertices){
		vertices.position(0);
		//System.out.println("here");
		gl.glVertexPointer(2, GL2.GL_FLOAT, VERTEX_SIZE, vertices);
		vertices.position(2);
		gl.glTexCoordPointer(2, GL2.GL_FLOAT, VERTEX_SIZE, vertices);
		
		
		gl.glDrawElements(GL2.GL_TRIANGLES, 6, GL2.GL_UNSIGNED_SHORT, indices);

	}
	
	private int gethashPos(float x, float y) {
		// TODO Auto-generated method stub
		
		float tempX, tempY;
		tempX=(x+1)/2;
		tempY=(y+1)/2;
		tempX=tempX*hashOrder;
		tempY=tempY*hashOrder;
		
		return ((int)tempY*hashOrder+(int)tempX);
	}
	
	public void seenWBC() {
		// TODO Auto-generated method stub
		
				
		if (renderImmobile>0){
			renderImmobile--;
			return;
		}
		if (seeMode){
			follow();
			return;
		}
		


		wbcsLocation=game.getwbcsLocation();
		ArrayList<Integer> surroundingHashes;
		if (wbcsLocation.size()<4){
		surroundingHashes= getSurroundingHashes2(hashPos);
		}
		else{
		surroundingHashes= getSurroundingHashes(hashPos);
				
		}
		for (int i=0;i<wbcsLocation.size();i++){

			int wbcLoc=gethashPos(wbcsLocation.get(i).x,wbcsLocation.get(i).y);
			if (surroundingHashes.contains(wbcLoc)){
				if(isVisible(new Vector(x,y), new Vector(wbcsLocation.get(i).x,wbcsLocation.get(i).y),wbcLoc)){
						seeMode=true;
						targetWBC=i;
						follow();
						return;
				}
		}
		}
		
		move();
		hashPos=gethashPos(x, y);
		
	}
	public void move(){
		Vector tempLB=new Vector((float)(x+Math.cos(angle_Of_Movement*Math.PI/180)*bloodFlowVel),(float)((y+Math.sin(angle_Of_Movement*Math.PI/180)*bloodFlowVel)));
		Vector tempLT=new Vector((float)(x+Math.cos(angle_Of_Movement*Math.PI/180)*bloodFlowVel),(float)((y+germHeight+Math.sin(angle_Of_Movement*Math.PI/180)*bloodFlowVel)));
		Vector tempRB=new Vector((float)(x+germWidth+Math.cos(angle_Of_Movement*Math.PI/180)*bloodFlowVel),(float)((y+Math.sin(angle_Of_Movement*Math.PI/180)*bloodFlowVel)));
		Vector tempRT=new Vector((float)(x+germWidth+Math.cos(angle_Of_Movement*Math.PI/180)*bloodFlowVel),(float)((y+germHeight+Math.sin(angle_Of_Movement*Math.PI/180)*bloodFlowVel)));
		
		if (checkCollision(tempLB) && checkCollision(tempLT) && checkCollision(tempRB) && checkCollision(tempRT)){
			x=tempLB.x;
			y=tempLB.y;
		}
		else{
			
			angle_Of_Movement=randomNumberGenerator(360);
			changeStateXY(angle_Of_Movement);
			
		}
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
	}
	private boolean checkCollision(Vector temp) {
		// TODO Auto-generated method stub
		
		
		int tempHash=gethashPos(temp.x,temp.y);

		for (int i=0;i<hash.grid.get(tempHash).size();i++){
			
			if (hash.grid.get(tempHash).get(i).isWithin(temp.x,temp.y)){
				return true;
			}
		}
		return false;
	}
	private boolean isVisible(Vector vectorG, Vector vectorW, int wbcLoc) {
		// TODO Auto-generated method stub
		int m=10,n=1;
		boolean flag=false;
		//vectorG.show();
		//vectorW.show();
		while (m>=1){
			flag=false;
			for (int i=0;i<hash.grid.get(hashPos).size();i++){

				if ((hash.grid.get(hashPos).get(i).isWithin(vectorG.section(vectorW, m, n)))){
					
					flag=true;
					break;
					}
			}
			if (flag){
				m--;
				n++;
				continue;
			}
			if (hashPos==wbcLoc){
				m--;
				n++;
				
				continue;
			}
			for (int j=0;j<hash.grid.get(wbcLoc).size();j++){
				
				if ((hash.grid.get(wbcLoc).get(j).isWithin(vectorG.section(vectorW, m, n)))){
					flag=true;
					break;
					}
			}
			
			
			
			
			
			m--;
			n++;
			if (!flag) return false;
		}
		return flag;
	}
	
	private ArrayList<Integer> getSurroundingHashes(int hashPos) {
		// TODO Auto-generated method stub
		ArrayList<Integer> temp=new ArrayList<Integer>();
		temp.add(hashPos);
		if (!(hashPos%hashOrder==0)){
			temp.add(hashPos-1);
			
			if(!(hashPos/hashOrder==(hashOrder-1))){
				temp.add(hashPos+hashOrder-1);
			}
			if(!(hashPos/hashOrder==0)){
				temp.add(hashPos-hashOrder-1);
			}
		}
		if(!(hashPos%hashOrder==(hashOrder-1))){
			temp.add(hashPos+1);
			if(!(hashPos/hashOrder==(hashOrder-1))){
				temp.add(hashPos+hashOrder+1);
			}
			if(!(hashPos/hashOrder==0)){
				temp.add(hashPos-hashOrder+1);
			}
		}
		if(!(hashPos/hashOrder==0)){
			temp.add(hashPos-hashOrder);
		}	
		if(!(hashPos/hashOrder==(hashOrder-1))){
			temp.add(hashPos+hashOrder);
		}
		
		
		
			return temp;
	}
	
	private ArrayList<Integer> getSurroundingHashes2(int hashPos) {
		// TODO Auto-generated method stub
		ArrayList<Integer> temp=new ArrayList<Integer>();
		
		int startRow,endRow,startCol,endCol;
		if (isLegal(hashPos-2) && (hashPos/hashOrder== (hashPos-2)/hashOrder)){
			startRow=-2;
		}
		else if (isLegal(hashPos-1) && (hashPos/hashOrder== (hashPos-1)/hashOrder)){
			startRow=-1;
		}
		else{
			startRow=0;
		}
		
		if (isLegal(hashPos+2) && (hashPos/hashOrder== (hashPos+2)/hashOrder)){
			endRow=2;
		}
		else if (isLegal(hashPos+1) && (hashPos/hashOrder== (hashPos+1)/hashOrder)){
			endRow=1;
		}
		else{
			endRow=0;
		}
		
		if (isLegal(hashPos-2*hashOrder)){
			startCol=-2;
		}
		else if (isLegal(hashPos-hashOrder)){
			startCol=-1;
		}
		else{
			startCol=0;
		}
		if (isLegal(hashPos+2*hashOrder)){
			endCol=2;
		}
		else if (isLegal(hashPos+hashOrder)){
			endCol=1;
		}
		else{
			endCol=0;
		}
		for (int i=startCol;i<=endCol;i++){
			for (int j=startRow;j<=endRow;j++){
				temp.add(hashPos+j+hashOrder*i);
			}
		}
		return temp;
	}
	
	private boolean isLegal(int x){
		if (x>=0 && x<hashOrder*hashOrder){
			return true;
		}
		else{
			return false;
		}
	}
	public void immobile(){
		renderImmobile=6;
		seeMode=false;
	}
	private void follow() {
		// TODO Auto-generated method stub
		wbcsLocation=game.getwbcsLocation();
		
		float targetX,targetY;
		float angleOfMovt;
		Vector dir=new Vector(x,y);
		try{
		targetX=wbcsLocation.get(targetWBC).x;
		targetY=wbcsLocation.get(targetWBC).y;
		}
		catch(Exception E){
			seeMode=false;
			return;
		}
		int wbcLoc=gethashPos(targetX,targetY);
		if(isVisible(new Vector(x+germWidth/2,y+germHeight/2), new Vector(targetX,targetY),wbcLoc)){
			lastKnownTargetLoc.set(targetX,targetY);
			}
		dir=dir.subtract(lastKnownTargetLoc);
		
		
		if(Math.abs(dir.x)<0.03f && Math.abs(dir.y)<0.03f){
			x=lastKnownTargetLoc.x;
			y=lastKnownTargetLoc.y;
			if (caughtWBC!=(targetWBC) && x==wbcsLocation.get(targetWBC).x && y==wbcsLocation.get(targetWBC).y){
			caughtWBC=(targetWBC);
					
			}
					seeMode=false;
		}
		else{
			angleOfMovt=dir.angle();
			
			x=(float) (x+Math.cos(angleOfMovt)*chaseVelocity);
			y=(float) (y+Math.sin(angleOfMovt)*chaseVelocity);
			
		/*
		angleOfMovt=dir.angle();
		Vector tempLB=new Vector((float)(x+Math.cos(angleOfMovt)*chaseVelocity),(float)((y+Math.sin(angleOfMovt)*chaseVelocity)));
		Vector tempLT=new Vector((float)(x+Math.cos(angleOfMovt)*chaseVelocity),(float)((y+germHeight+Math.sin(angleOfMovt)*chaseVelocity)));
		Vector tempRB=new Vector((float)(x+germWidth+Math.cos(angleOfMovt)*chaseVelocity),(float)((y+Math.sin(angleOfMovt)*chaseVelocity)));
		Vector tempRT=new Vector((float)(x+germWidth+Math.cos(angleOfMovt)*chaseVelocity),(float)((y+germHeight+Math.sin(angleOfMovt)*chaseVelocity)));
		
		boolean LB=checkCollision(tempLB);
		boolean LT=checkCollision(tempLT);
		boolean RB=checkCollision(tempRB);
		boolean RT=checkCollision(tempRT);
		if (LB && LT && RB && RT){
			x=tempLB.x;
			y=tempLB.y;
		}
		else{
			angleOfMovt=angleOfMovt*180/6.28f;
			if(angleOfMovt<0){angleOfMovt+=360;}
				
			if (!LT){
			
					if(angleOfMovt<45||angleOfMovt>315){
						y=y-0.02f;
					}
					else{
						x=x+0.02f;
					}
				}
				if (!LB){
						if(angleOfMovt<45||angleOfMovt>315){
							y=y+0.02f;
						}
						else{
							x=x+0.02f;
						}
				}
				if (!RT){
					if(angleOfMovt<225 && angleOfMovt>135){
						y=y-0.02f;
					}
					else{
					x=x-0.02f;
					}
				}
				if (!RB){
					if(angleOfMovt<225 && angleOfMovt>135){
						y=y+0.02f;
					}
					else{
					x=x-0.02f;
					}
				}
				
			}
			*/
		}
		
	}
	public void engulf() {
		// TODO Auto-generated method stub
		engulf=true;
	}
	
}