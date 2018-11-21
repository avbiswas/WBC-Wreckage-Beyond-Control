package glexperiments;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Input implements KeyListener {
	DOSTUFF stuff;
	ArrayList<Integer> key;
	Input(DOSTUFF stuff){
		this.stuff=stuff;
		key=new ArrayList<Integer>();
	}
	
	@Override
	public void keyPressed(KeyEvent v) {
		// TODO Auto-generated method stub
		if (!key.contains(v.getKeyCode()))
			key.add(v.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent v) {
		// TODO Auto-generated method stub
		
		key.remove(key.indexOf(v.getKeyCode()));
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	public ArrayList<Integer> getKey(){
		return key;
	}
	public void setKey(){
		if (key.size()!=0)
		key.clear();
	}
}
