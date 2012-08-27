package mainPackage;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

public class KeyDispatcher implements KeyEventDispatcher {
	
	private MainFrame mF;

	//Konstruktor
    	public KeyDispatcher(MainFrame mF) {
    		this.mF=mF;
    	}

    	//KeyListener Methoden
		public void keyPressed(KeyEvent e) {		
    		System.out.println("pressed : "+ e.getKeyChar());
    		System.out.println("pressed : "+ e.getKeyCode());
    	}

    	public void keyReleased(KeyEvent e) {
    		System.out.println("released: "+ e.getKeyChar());
    		System.out.println("released: "+ e.getKeyCode());
    	}

    	public void keyTyped(KeyEvent e) {
    		System.out.println("typed   : "+ e.getKeyChar());
    		System.out.println("typed   : "+ e.getKeyCode());
    	}
    	
    	//Dispatched Key Listener Methoden
		@Override
		public boolean dispatchKeyEvent(KeyEvent event) {
			boolean isTreated=false;

    		  if(event.getID() == KeyEvent.KEY_PRESSED){
    			  isTreated = mF.getCurrentlyOpened().keyPressed(event); 

    		  }
      		  
      		  if(event.getID() == KeyEvent.KEY_RELEASED){
      			  isTreated = mF.getCurrentlyOpened().keyReleased(event);
      			
      		  }
      		  
      		  if(event.getID() == KeyEvent.KEY_TYPED){
      			  isTreated = mF.getCurrentlyOpened().keyTyped(event); 

    		  }

			return isTreated;
		}

}


