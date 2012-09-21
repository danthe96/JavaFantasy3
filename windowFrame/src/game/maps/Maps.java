package game.maps;

import game.GamePanel;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public abstract class Maps {
	
	
	protected static GamePanel gamePanel;
	public static OverworldMap overworldmap;
	public static Test test;
	public static Maps currentMap;
	public BufferedImage shadow;
	static int height; 
	static int width;
	
	public abstract void doLogic(Point2D.Double playerposition,int bildx, int bildy);
	
	public abstract void drawObjects(Graphics g);
	
	public abstract BufferedImage getShadowMap();
	
	public void changeMap(Point2D.Double position){
		currentMap = map.get(position) ;
		gamePanel.setPlayerposition(teleMap.get(position));
		
		
	}
	
	public static void initialize(GamePanel gameP){
		height = gameP.getHeight();
		width  = gameP.getWidth() ;
		
		gamePanel		= gameP;
		overworldmap 	= new OverworldMap();			
		test			= new Test()		;
		

		System.out.println(width);
		
		
	}
	
	HashMap<Point2D.Double,Maps> map;
	HashMap<Point2D.Double,Point2D.Double> teleMap;
	
}
