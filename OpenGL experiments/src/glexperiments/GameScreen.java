package glexperiments;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

public class GameScreen implements Screen{
	public int startingWBC_NO;
	public long time;
	final int GAME_RUNNING=0;
	final int WBC_WON=2;
	final int GERMS_WON=1;
	final int PAUSED=3;
	final int go_Main_Menu=4;
	final int nextLevel=5;
	final int replayLevel=6;
	final int GAME_OVER=7;
	long Updatetime;
	GL2 gl;
	ArrayList<ArrayList<Integer>> levels;
	ArrayList<String> mapNames;
	int currentLevel;
	int no_of_WBC=1;
	int no_of_germs=1;
	int gameState;
	DOSTUFF stuff;
	ArrayList<Vector>wbcsLocation;
	ArrayList<Vector>germsLocation;
	ArrayList<WBC> wbcs;
	int userWBC;
	int userGerms;
	Area area;
	int screenWidth;
	int screenHeight;
	float x,y;
	int wbc_ID;
	Texture T;
	int texture;
	Boundary b;
	ArrayList<germ> germs;
	Grid hash;
	UI ui;
	boolean powerUpmode;
	
	public GameScreen(DOSTUFF stuff,int level){
		getLevelData();
		no_of_WBC=levels.get(level).get(0);
		startingWBC_NO=no_of_WBC;
		no_of_germs=levels.get(level).get(1);
		currentLevel=level;
		getMapData();
		this.stuff=stuff;
		gameState=GAME_RUNNING;
		screenWidth=(int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		screenHeight=(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		wbcsLocation=new ArrayList<Vector>();
		germsLocation=new ArrayList<Vector>();
		for (int i=1;i<=no_of_WBC;i++){
			wbcsLocation.add(new Vector());
			
		}
		for (int i=1;i<=no_of_germs;i++){
			germsLocation.add(new Vector(0,0));
		}
		wbcs=new ArrayList<WBC>();
		germs=new ArrayList<germ>();
		wbc_ID=0;
		//if (currentLevel>3){
			//powerUp=new PowerUp(currentLevel);
		//}
		
	}
	public GameScreen(DOSTUFF stuff, int userInputWBC, int userInputGerms) {
		// TODO Auto-generated constructor stub
		no_of_WBC=userInputWBC;
		no_of_germs=userInputGerms;
		currentLevel=-1;
		userWBC=userInputWBC;
		userGerms=userInputWBC;
		getMapData();
		this.stuff=stuff;
		screenWidth=(int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		screenHeight=(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		wbcsLocation=new ArrayList<Vector>();
		germsLocation=new ArrayList<Vector>();
		for (int i=1;i<=no_of_WBC;i++){
			wbcsLocation.add(new Vector());
			
		}
		for (int i=1;i<=no_of_germs;i++){
			germsLocation.add(new Vector(0,0));
		}
		wbcs=new ArrayList<WBC>();
		germs=new ArrayList<germ>();
		wbc_ID=0;
	
	}
	private void getMapData(){
		mapNames=new ArrayList<String>();
		mapNames.add("map1.png");
		mapNames.add("map2.png");
		mapNames.add("map3.png");
		mapNames.add("map4.png");

		mapNames.add("map5.png");
		mapNames.add("map6.png");
		mapNames.add("map7.png");
		mapNames.add("map8.png");
		mapNames.add("map9.png");
		mapNames.add("map10.png");
	}
	private void getLevelData() {
		// TODO Auto-generated method stub
		levels=new ArrayList<ArrayList<Integer>>();
		for (int i=0;i<10;i++){
			levels.add(new ArrayList<Integer>());
			
			
		}
		levels.get(0).add(1);
		levels.get(0).add(1);
		levels.get(1).add(1);
		levels.get(1).add(2);
		levels.get(2).add(3);
		levels.get(2).add(5);
		levels.get(3).add(3);
		levels.get(3).add(7);
		levels.get(4).add(4);
		levels.get(4).add(10);
		levels.get(5).add(3);
		levels.get(5).add(12);
		levels.get(6).add(4);
		levels.get(6).add(14);
		levels.get(7).add(4);
		levels.get(7).add(15);
		levels.get(8).add(5);
		levels.get(8).add(20);
		levels.get(9).add(4);
		levels.get(9).add(20);
		
		
	}
	@Override
	public void initialize(GL2 gl){
		this.gl=gl;
		Random rand=new Random();
		long n=(rand.nextInt()*System.currentTimeMillis());
		int mapId=(int) Math.abs(n%10);
		
		if (currentLevel==-1){
			area=new Area(gl,this,mapNames.get(mapId));
		}
		else{
		
		area=new Area(gl,this,mapNames.get(currentLevel));
		}
		b=new Boundary();
        b.mapFinder(gl);
        hash=new Grid(b.map);
		
        for (int i=0;i<no_of_WBC;i++){
        	int temp=(mapId+i)%4;
        	switch (temp){
        	case 0:wbcs.add(new WBC(gl,this,-0.9f,-0.97f));
    				break;
        	case 1:wbcs.add(new WBC(gl,this,-0.9f,-0.92f));
    				break;
        	case 2:wbcs.add(new WBC(gl,this,-0.95f,-0.97f));
    				break;
        	case 3:wbcs.add(new WBC(gl,this,-0.94f,-0.92f));
    				break;
        	}
        }
		 
		for (int i=0;i<no_of_germs;i++){
		germs.add(new germ(gl,this,i));
		}
		
		ui=new UI(gl,this);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0,400,0,400,1,-1);
		
		stuff.audio.stopTheme();
		Updatetime=System.currentTimeMillis();
	}
	@Override
	public void display(GL2 gl){
			
		
		
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		gl.glLoadIdentity();
		
		area.showArea();
		gl.glLoadIdentity();
		
		if(gameState==GAME_RUNNING){
			
			wbcs.get(wbc_ID).changeX();
			wbcs.get(wbc_ID).changeY();
			
		for (int i=0;i<no_of_WBC;i++){
			//if (System.currentTimeMillis()-time>100){
				
			if (i!=wbc_ID){
			wbcs.get(i).moveRandomly();
			}
			
			wbcs.get(i).showWBC();
		}	
		}
		gl.glLoadIdentity();
		
		ui.show();
		
		if (gameState==GAME_RUNNING){
		for (int i=0;i<no_of_germs;i++){
			//if (System.currentTimeMillis()-time>100){
			if (!germs.get(i).engulf)
				germs.get(i).seenWBC();
			
			germs.get(i).showGerm();
		}
		
		}
		try{
			//if (System.currentTimeMillis()-time>100){
				//time=System.currentTimeMillis();
				update();
			//}
		}
		catch(Exception e){
			System.exit(0);
		}
		if (gameState==GAME_OVER){
			if (no_of_germs>0){
				gameState=GERMS_WON;
			}
			else{
				gameState=WBC_WON;
			}
		}
		if (gameState==go_Main_Menu){
			MainMenu menu=new MainMenu(stuff);
			menu.initialize(gl);
			stuff.setCurrentScreen(menu);
		}
		if (gameState==replayLevel){
			if (currentLevel==-1){
				GameScreen game=new GameScreen(stuff,userWBC,userGerms);
				game.initialize(gl);
				stuff.setCurrentScreen(game);
					
			}
			else{
			GameScreen game=new GameScreen(stuff,currentLevel);
			game.initialize(gl);
			stuff.setCurrentScreen(game);
			}
		}
		if (gameState==nextLevel){
			if (currentLevel==-1){
				GameScreen game=new GameScreen(stuff,userWBC+1,userGerms+3);
				game.initialize(gl);
				stuff.setCurrentScreen(game);
					
			}
			else{
			if (currentLevel==9){
				gameState=go_Main_Menu;
				return;
			}
			else{
				
			
			GameScreen game=new GameScreen(stuff,currentLevel+1);
			game.initialize(gl);
			stuff.setCurrentScreen(game);
			}
		}
		}
		
	}
	
	public void update(){
		processInput(stuff.getInput().getKey());
		
		if (gameState==GAME_RUNNING){
		

		if (no_of_WBC==startingWBC_NO/2){
				powerUpmode=true;
				time=System.currentTimeMillis();
				startingWBC_NO=0;
			}
		
		if (powerUpmode){
			if (System.currentTimeMillis()-time>10000){
				powerUpmode=false;
			}
		}
		
		for (int i=0;i<no_of_germs;i++){
			if (germs.get(i).destroyed){
			
				germs.remove(i);
				no_of_germs--;
				if (no_of_germs==0){
					gameState=WBC_WON;
					return;
				}
			}
		}
		/*
		ArrayList<Integer> temp=new ArrayList<Integer>();
		germsLocation.clear();
		for (int i=0;i<no_of_germs;i++){
			germsLocation.add(new Vector(germs.get(i).x,germs.get(i).y));
			
			if (germs.get(i).caughtWBC.size()>0){
				for (int k=0;k<germs.get(i).caughtWBC.size();k++){
					wbcs.get(germs.get(i).caughtWBC.get(k)).destroyed=true;
					temp.add(k);
					
				}
				germs.get(i).caughtWBC.clear();
			}
		}
		if (temp.size()>0) {
			destroyWBC(temp);
		}
		*/
			germsLocation.clear();
		for (int i=0;i<no_of_germs;i++){
			if (germs.get(i).engulf){
				continue;
			}
			germsLocation.add(new Vector(germs.get(i).x,germs.get(i).y));
			
			if (germs.get(i).caughtWBC>-1){
				
				destroyWBC(germs.get(i).caughtWBC);
				germs.get(i).caughtWBC=-1;
				germs.get(i).immobile();
					for (int k=0;k<germs.size();k++){
						germs.get(i).seeMode=false;
					}
				break;
			}
		}
		wbcsLocation.clear();
		
		for (int i=0;i<no_of_WBC;i++){
			if (wbcs.get(i).destroyed){
				destroyWBC(i);
				break;
			}
		}
		boolean powerUpActive=false;
		for (int i=0;i<no_of_WBC;i++){
			if(wbcs.get(i).stateX!=0)
			wbcs.get(i).computeX();
			if(wbcs.get(i).stateY!=0)
			wbcs.get(i).computeY();
			if (powerUpmode){
				if (wbcs.get(i).x>-0.1f && wbcs.get(i).x<0.1f && wbcs.get(i).y>-0.1f && wbcs.get(i).y<0.1f){
					powerUpActive=true;
				}
			}
			wbcsLocation.add(new Vector(wbcs.get(i).x,wbcs.get(i).y));
		}
		if (powerUpActive){
			powerUpmode=false;
			wbcs.add(new WBC(gl,this,-0.95f,-0.95f));
			no_of_WBC=wbcs.size();
			wbcsLocation.add(new Vector(-0.95f,-0.95f));
		}
		
		}
	}
	/*
	private void destroyWBC(ArrayList<Integer> temp) {
		// TODO Auto-generated method stub
		for (int i=0;i<temp.size();i++){
			wbcs.remove(temp.get(i));
		}
		if (no_of_WBC==0 && no_of_germs>0){
			gameState=GAME_OVER;
			return;
		}
		no_of_WBC=wbcs.size();
		wbc_ID++;
		wbc_ID=wbc_ID%(no_of_WBC);
		
	}
*/
	public void destroyWBC(int i){
		stuff.audio.hurt();
		wbcs.remove(i);
		no_of_WBC--;
		if (no_of_WBC==0 && no_of_germs>0){
			gameState=GAME_OVER;
			return;
		}
		wbc_ID++;
		wbc_ID=wbc_ID%(no_of_WBC);
		
		
	}
	public void processInput(ArrayList<Integer> x){
		
		if (gameState==GERMS_WON){
			if (x.size()>0){
				switch(x.get(0)){
					case KeyEvent.VK_ENTER: gameState=replayLevel;
									break;
					case KeyEvent.VK_ESCAPE: gameState=go_Main_Menu;
					}
			}
			return;
		}
		
		
		
		if (x.size()==0){
    	wbcs.get(wbc_ID).setStateX(0);
    	wbcs.get(wbc_ID).setStateY(0);
    	return;
		}
	
	if (x.size()==1){
		if (x.get(0)==KeyEvent.VK_UP||x.get(0)==KeyEvent.VK_DOWN){
			wbcs.get(wbc_ID).setStateX(0);
		}
		if (x.get(0)==KeyEvent.VK_LEFT||x.get(0)==KeyEvent.VK_RIGHT){
			wbcs.get(wbc_ID).setStateY(0);
		}
	}
		
		
	for (int i=0;i<x.size();i++){
		switch(x.get(i)){
		case KeyEvent.VK_UP:
			wbcs.get(wbc_ID).setPresentOrientation(4);
			wbcs.get(wbc_ID).setStateY(1);
			break;
        case KeyEvent.VK_DOWN:

			wbcs.get(wbc_ID).setPresentOrientation(3);
        	wbcs.get(wbc_ID).setStateY(-1);
			break;
        
        case KeyEvent.VK_LEFT:

			wbcs.get(wbc_ID).setPresentOrientation(2);
        	wbcs.get(wbc_ID).setStateX(-1);
            break;
        case KeyEvent.VK_RIGHT :

			wbcs.get(wbc_ID).setPresentOrientation(1);
        	wbcs.get(wbc_ID).setStateX(1);
            break;
        
        case KeyEvent.VK_SHIFT:
        	wbc_ID++;
			wbc_ID=wbc_ID%(no_of_WBC);
			break;
        case KeyEvent.VK_CONTROL:
        	if (wbcs.get(wbc_ID).turbo>0){
        	stuff.audio.turbo();
        	wbcs.get(wbc_ID).toggleSpeed();
        	}
        	else{
        		stuff.audio.error();
        	}
        	break;
       
        case KeyEvent.VK_X:
        	ArrayList<Integer> germsToKill=wbcs.get(wbc_ID).activateEngulfMode();
        	
        	for (int j=0;j<germsToKill.size();j++){
        		stuff.audio.engulf();
        		germs.get(j).engulf();
        	}
        	break;
        
		case KeyEvent.VK_SPACE:
			wbcs.get(wbc_ID).sashaMode();
			stuff.audio.scope();
			break;
		case KeyEvent.VK_ENTER:
			if (gameState==PAUSED){
				gameState=GAME_RUNNING;
			}
			if (gameState==WBC_WON){
				gameState=nextLevel;
			}
			if (gameState==GERMS_WON){
				gameState=replayLevel;
			}
			break;
		case KeyEvent.VK_ESCAPE:
			if (gameState==GAME_RUNNING){
				gameState=PAUSED;
				return;
				}
			if (gameState==PAUSED||gameState==WBC_WON||gameState==GERMS_WON){
				gameState=go_Main_Menu;
			}
		}
	}
	}
	
	public ArrayList<WBC> getWBCS(){
		return wbcs;
	}
	public Area getArea(){
		return area;
	}
	public ArrayList<germ> getGerms(){
		return germs;
	}
	public int getWBCNo(){
		return no_of_WBC;
	}
	public Boundary getBoundary(){
		return b;
		
	}
	public Vector getCurrentWBCLocation(){
		
		return new Vector(wbcs.get(wbc_ID).x,wbcs.get(wbc_ID).y);
	}
	public ArrayList<Vector> getwbcsLocation() {
		// TODO Auto-generated method stub
		return wbcsLocation;
	}
	public ArrayList<Vector> getGermsLocation() {
		// TODO Auto-generated method stub
		return germsLocation;
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}
