package game.maps;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class OverworldMap extends Maps {

	BufferedImage[][] mapsquares = new BufferedImage[16][16];
	BufferedImage map;
	BufferedImage shadow;
	
	Point[][] mapsquareindex={{new Point(),new Point(),new Point()},{new Point(),new Point(),new Point()},{new Point(),new Point(),new Point()}}; //new Point[3][3];
	
	int mapsquarewidth;
	int mapsquareheight;
	int setToCenter;
	
	
	
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
	      
	}
	
	
	@Override
	public void doLogic() {
		
		
	}

	@Override
	public void drawObjects(Graphics g) {

		for(int x = 0;x<3;x++){
		for(int y = 0; y<3; y++){

			g.drawImage(mapsquares[(int)mapsquareindex[x][y].getX()][(int)mapsquareindex[x][y].getY()],(setToCenter+height*(x-1))-mapsquarewidth, 
					(height*(y-1))-mapsquareheight   , height ,height  ,null);
		}
		}
		
		
		
		
	}
		

}
