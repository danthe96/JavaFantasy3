package game.maps;

import java.awt.Graphics;


public class OverworldMap extends Maps {

	@Override
	public void doLogic() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawObjects(Graphics g) {

		for(int x = 0;x<3;x++){
		for(int y = 0; y<3; y++){

			g.drawImage(mapsquares[(int)mapsquareindex[x][y].getX()][(int)mapsquareindex[x][y].getY()],(setToCenter+getHeight()*(x-1))-mapsquarewidth, 
					(getHeight()*(y-1))-mapsquareheight   , getHeight() ,getHeight()  ,this);
		}
		}
		
		
	}
		

}
