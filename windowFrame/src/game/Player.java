package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player extends Sprite {
	boolean isplayermoveable;
	
	private static final long serialVersionUID = -396974336584652524L;
	public Player (BufferedImage[] images, long delay, GamePanel p) {
	    super(images,delay,p);
	    setLoop(0,2);
	    }
	
	@Override
	public void drawObjects(Graphics g) {
		g.drawImage(pics[currentpic], (int)(position.getX()+0.5), (int) (position.getY()+0.5), (int)(width*stretch+0.5), (int) (height*stretch+0.5), null);
		//spritegröße muss sich nach auflösung bemessen, bei 256*256 ist es Originalgröße
	}
	  
    public void setHorizontalSpeed(double d){
    	super.setHorizontalSpeed(d);
    }
    public void setVerticalSpeed(double d){
    	super.setVerticalSpeed(d);
    }
    
    @Override
    public void move(){
    	if(willupcoll&&dy<0)
    		dy=0; 
    	     
    	if(willrightcoll&&dx>0)
    		dx=0; 
    	    
    	if(willdowncoll&&dy>0)
    		dy=0; 
    	    
    	if(willleftcoll&&dx<0)
    		dx=0;
    	
		if(isplayermoveable){
    		    if(dx!=0)          
    		      position.setLocation(position.getX()+dx*((System.nanoTime()-last2)/1e9), position.getY()); 
    		    if(dy!=0)
    		    	position.setLocation(position.getX() , position.getY()+dy*((System.nanoTime()-last2)/1e9));
    		    
    	}
		else {
			if(dx!=0||dy!=0)
			parent.setPlayerPositionDelta(dx*((System.nanoTime()-last2)/1e9),dy*((System.nanoTime()-last2)/1e9));
		}
		last2 = System.nanoTime();	
    }
    
    @Override public void doLogic(){
    	super.doLogic();
    }
}