package game.maps;

import game.GamePanel;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;


public class OverworldMap extends Maps {

	BufferedImage[][] mapsquares = new BufferedImage[16][16];
	BufferedImage map;
	public BufferedImage shadow;
	
	Point[][] mapsquareindex={{new Point(),new Point(),new Point()},{new Point(),new Point(),new Point()},{new Point(),new Point(),new Point()}}; //new Point[3][3];
	
	int mapsquarewidth ;
	int mapsquareheight;
	int setToCenter    ;
	
	
	
	public OverworldMap(){		
		setToCenter=(width-height)/2;
		
		try {
			map = ImageIO.read(getClass().getClassLoader().getResource("pics/overworldmap.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	      for(int i=0;i<16;i++){
				for(int j=0;j<16;j++){
				   
					 mapsquares[i][j] = map.getSubimage(256*i, 256*j, 256, 256);
				}
			} 
	      map=null;
	      
	      try {
				shadow = ImageIO.read(getClass().getClassLoader().getResource("pics/overworldmapshadow.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
	      	
	      hashMapMaps=new HashMap<Point2D.Double,Maps>();
	      teleMap=new HashMap<Point2D.Double , Point2D.Double>();
	   
	      hashMapMaps.put(new Point2D.Double(73,93)  , overworldmap );
	      hashMapMaps.put(new Point2D.Double(75,102) , overworldmap );
	      hashMapMaps.put(new Point2D.Double(102,100) ,overworldmap );
	      hashMapMaps.put(new Point2D.Double(98,92)  , overworldmap );
	      hashMapMaps.put(new Point2D.Double(104,64) , overworldmap );
	      hashMapMaps.put(new Point2D.Double(165,34) , overworldmap );
	      hashMapMaps.put(new Point2D.Double(93,39)  , overworldmap );
	      hashMapMaps.put(new Point2D.Double(185,93) , overworldmap );
	      hashMapMaps.put(new Point2D.Double(214,148) , overworldmap );
	   
	      teleMap.put(new Point2D.Double(73,93)  , new Point2D.Double(75,103) );
	      teleMap.put(new Point2D.Double(75,102) , new Point2D.Double(73,94)  );
	      teleMap.put(new Point2D.Double(102,100), new Point2D.Double(98,93)  );
	      teleMap.put(new Point2D.Double(98,92)  , new Point2D.Double(102,101));
	      teleMap.put(new Point2D.Double(104,64) , new Point2D.Double(93, 40) );
	      teleMap.put(new Point2D.Double(165,35) , new Point2D.Double(104,65) );
	      teleMap.put(new Point2D.Double(93,39) , new Point2D.Double(165,36) );
	      teleMap.put(new Point2D.Double(185,93), new Point2D.Double(214, 149));
	      teleMap.put(new Point2D.Double(214,148), new Point2D.Double(185, 94));
	      
	}
	
	
	@Override
	public void doLogic(Point2D.Double playerposition,int bildx, int bildy) {
		int nextX;
		int nextY;
		bildx = ((int)(playerposition.getX()/256));
		bildy = ((int)(playerposition.getY()/256));
		for(int x =0;x<3;x++){
			for(int y =0;y<3;y++){
				nextX=bildx+x-1;
				nextY=bildy+y-1;		
				if(nextX<0){nextX+=16;}
				if(nextY<0){nextY+=16;}
				if(nextX>=16){nextX-=16;}
				if(nextY>=16){nextY-=16;}
				mapsquareindex[x][y].setLocation(nextX,nextY);
			}
		}
		
		mapsquarewidth  = (int)(GamePanel.stretch*((playerposition.getX()-((bildx)*256)-128)));
		mapsquareheight = (int)(GamePanel.stretch*((playerposition.getY()-((bildy)*256)-128)));
		
	}

	@Override
	public void drawObjects(Graphics g) {
		for(int x = 0;x<3;x++){
			for(int y = 0;y<3;y++){

				g.drawImage	(mapsquares[(int)mapsquareindex[x][y].getX()][(int)mapsquareindex[x][y].getY()],(setToCenter+height*(x-1))-mapsquarewidth, 
							(height*(y-1))-mapsquareheight   , height ,height  ,null);
		
			}
		}
		
		
		
		
	}


	@Override
	public void changeMap(Point2D.Double position){
		if(hashMapMaps.containsKey(position) && hashMapMaps.get(position)!= null)
		         currentMap = hashMapMaps.get(position);		
		if(teleMap.containsKey(position))
			gamePanel.setPlayerposition(new Point2D.Double((teleMap.get(position).getX()+0.5)*16,(teleMap.get(position).getY()+0.5)*16));
				
	}
	
	
	@Override
	public BufferedImage getShadowMap() {
		return shadow;
	}
		

}
