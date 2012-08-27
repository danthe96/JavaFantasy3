package interfaces;

import java.awt.event.KeyEvent;

public interface BoolKeyListener {
	
	public boolean keyPressed(KeyEvent event);
	public boolean keyReleased(KeyEvent event);
	public boolean keyTyped(KeyEvent event);

}
