package game.maps;

import game.GamePanel;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.HashMap;

public abstract class Maps {
	
	
	private static GamePanel gamePanel;
	public static OverworldMap overworldmap;
	public static Test test;
	public static Maps currentMap;
	static int height; 
	static int width;
	
	public abstract void doLogic();
	
	public abstract void drawObjects(Graphics g);
	
	public void changeMap(Point2D.Double position){
		currentMap = map.get(position) ;
		gamePanel.setPlayerposition(teleMap.get(position));
		
		
	}
	
	public static void initialize(GamePanel gameP){
		
		gamePanel		= gameP;
		overworldmap 	= new OverworldMap();			
		test			= new Test()		;
		
		height = gameP.getHeight();
		width = gameP.getWidth();
		
		
		
	}
	
	HashMap<Point2D.Double,Maps> map;
	HashMap<Point2D.Double,Point2D.Double> teleMap;
	
}
