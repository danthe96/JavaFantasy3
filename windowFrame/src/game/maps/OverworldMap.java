package game.maps;

import game.GamePanel;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
		System.out.println(setToCenter);
		
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
		for(int y = 0; y<3; y++){

			g.drawImage(mapsquares[(int)mapsquareindex[x][y].getX()][(int)mapsquareindex[x][y].getY()],(setToCenter+height*(x-1))-mapsquarewidth, 
					(height*(y-1))-mapsquareheight   , height ,height  ,null);
		
		}
		}
		
		
		
		
	}


	@Override
	public BufferedImage getShadowMap() {
		return shadow;
	}
		

}
