package game;

import java.awt.geom.Point2D;

public class SaveDataLib {
	

	static public Point2D.Double playerposition;
	@SuppressWarnings("unused")
	private static Object[] saveList={playerposition};
	
	
	static void loadGame(){
			
		
	}
	
	static void loadNewGame(){
		playerposition= new Point2D.Double(3000, 700);
		
	}
	
	
	static void saveGame(){
		
	}
	
}
