package game;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import interfaces.BoolKeyListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ListIterator;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import mainPackage.MainFrame;
import sounds.SoundLib;


public class GamePanel extends JPanel implements Runnable, BoolKeyListener {
	
	/*
     * Attribute (Variablen)
     * Methoden
     * Konstruktoren
     * Klassen- sowie Exemplarinitialisierer
     * innere Klassen, innere Schnittstellen und innere Aufzählungen
     */
	
	private static final long serialVersionUID = 1552746400473185110L;
	Thread gameProzess;
	SaveDataLib saveDataLib;
	BufferedImage[][] mapsquares = new BufferedImage[16][16];
	BufferedImage map;
	BufferedImage shadow;
	BufferedImage[] terrasprite;
	
	Point[][] mapsquareindex={{new Point(),new Point(),new Point()},{new Point(),new Point(),new Point()},{new Point(),new Point(),new Point()}}; //new Point[3][3];
	int mapsquarewidth;
	int mapsquareheight;
	int setToCenter;
	int bildx;
	int bildy;
	
	int speed;
	int bildstart;
	boolean started;
	boolean up;
	boolean down;
	boolean left;
	boolean right;
	boolean iscollision;
	int loopfrom;
	
	double stretch;
	
	
	
	boolean debug; //debug

	Point2D.Double playerposition;
	SoundLib soundlib;
	Player terra;
	MainFrame mF;
	Vector<Sprite> actors;
    Vector<Sprite> painter; 


	public GamePanel(SaveDataLib saveDataLib,int width, int height, SoundLib soundlib, MainFrame mF){ 
      gameProzess = new Thread(this);
      this.mF=mF;
      mF.setGamePanel(this);
      this.soundlib = soundlib;
      this.saveDataLib = saveDataLib;
      this.setBounds(0,0,width,height);
      this.setLayout(null);
      this.setDoubleBuffered(true);
      this.setBackground(Color.BLACK);
      gameProzess.start();	
      setToCenter=(getWidth()-getHeight())/2;
      initialize();

	}
	
	public void initialize(){
		stretch=(0.0+getHeight())/256;
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
	      soundlib.loadSound("maintheme","sounds/01-Terra.mp3");
	      soundlib.loopSound("maintheme");
	      actors = new Vector<Sprite>();                                       
          painter = new Vector<Sprite>(); 
	      //terrasprite = loadPics("pics/Terra.png",12);
	      terrasprite = loadPics("pics/Ghost.png",12);
	      try {
			shadow = ImageIO.read(getClass().getClassLoader().getResource("pics/overworldmapshadow.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      playerposition = new Point2D.Double(3000 , 700);    //muss absolute Position benutzen
	      terra = new Player(terrasprite,150,this);
	      actors.add(terra);
	      up = false; down = false; right = false; left = false;  
	      speed = 48;   //speed muss konstant sein
	      setStarted(true);
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		if(isStarted()){
			for(int x = 0;x<3;x++){
			for(int y = 0; y<3; y++){

				g.drawImage(mapsquares[(int)mapsquareindex[x][y].getX()][(int)mapsquareindex[x][y].getY()],(setToCenter+getHeight()*(x-1))-mapsquarewidth, 
						(getHeight()*(y-1))-mapsquareheight   , getHeight() ,getHeight()  ,this);
			}
			}
		}
		if(debug){
		g.drawImage(shadow, (int)(getWidth()/2-playerposition.getX()*stretch), (int)(getHeight()/2-playerposition.getY()*stretch), (int)(4096*stretch), (int)(4096*stretch), this);
		g.drawLine(getWidth()/2, 0, getWidth()/2, getHeight());
		g.drawLine(getWidth()/2-1, 0, getWidth()/2-1, getHeight());
		g.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
		g.setColor(Color.BLUE);
		g.drawString("x: "+playerposition.getX(), 10, 10);
		g.drawString("y: "+playerposition.getY(), 10, 20);
		g.drawString("Keine Kollision: "+ iscollision, 10, 30);
		}
		
		for(ListIterator<Sprite> it = painter.listIterator();it.hasNext();){
            Sprite r = it.next();                                                           
            r.drawObjects(g);   
        }
			
		
		
	
		
		
	}
		
	
	@Override
	public void run() {
		while(this.isVisible()){
		if(isStarted()){	
		doLogic();
		checkKeys();
		moveObjects();
		cloneVectors();
		}
		repaint();

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {e.printStackTrace();}
		}
	}

	@SuppressWarnings("unchecked")
	private void cloneVectors(){
        painter = (Vector<Sprite>) actors.clone();
      }
	
	private void checkKeys(){
		if(!up && !down)
			terra.setVerticalSpeed(0);
		if(!left && !right)
			terra.setHorizontalSpeed(0);
		if(up && !down){
			terra.setVerticalSpeed(-speed);
			if(!right && !left ){
			terra.setLoop(0, 2);
			}
		}
		if(down&&!up){
			terra.setVerticalSpeed(speed);
			if(!right && !left){
			terra.setLoop(6, 8);
			}
		}
		if(left&&!right){
			terra.setHorizontalSpeed(-speed);
			if(!up && !down){
			terra.setLoop(9, 11);
			}
		}
		if(right&&!left){
			terra.setHorizontalSpeed(speed);
			if(!up && !down){
			terra.setLoop(3, 5);
			}
		}
			
	}
	
	private void doLogic(){
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
		
		mapsquarewidth  = (int)(stretch*((playerposition.getX()-((bildx)*256)-128)));
		mapsquareheight = (int)(stretch*((playerposition.getY()-((bildy)*256)-128)));

		
		
		for(ListIterator<Sprite> it = actors.listIterator(); it.hasNext();){                        
            Sprite r = it.next();                                                                      
            r.doLogic();                                                                          
          }
		
	}
	
	private void moveObjects(){                                   
        for(ListIterator<Sprite> it = actors.listIterator(); it.hasNext();){                        
          Sprite r = it.next();                                                                     
          r.move();                                                                            
        }
        
      }
	
	 public BufferedImage[] loadPics(String path, int pics){                                    
         BufferedImage[] anim = new BufferedImage[pics];
         BufferedImage source = null;

         URL pic_url = getClass().getClassLoader().getResource(path);

         try{
           source = ImageIO.read(pic_url);
         } catch (IOException e) {}

         for(int x=0;x<pics;x++){
           anim[x] = source.getSubimage(x*source.getWidth()/pics, 0,
                source.getWidth()/pics, source.getHeight());
         }
         return anim;
         }
	
	private boolean isStarted() {
			return started;
	}
	
	public void setStarted(boolean started){
        this.started = started;
      }
	
	public void setPlayerPositionDelta(double dx, double dy) {
		if ((playerposition.getX()+dx)<0)
		      dx+=4096;
		    if ((playerposition.getY()+dy)<0)
		      dy+=4096;
		    if ((playerposition.getX()+dx)>=4096)
		      dx-=4096;
		    if ((playerposition.getY()+dy)>=4096)
		      dy-=4096;
		    playerposition.setLocation(playerposition.getX()+dx, playerposition.getY()+dy);
	}

	//KeyListener
	@Override
	public boolean keyPressed(KeyEvent event) {
		boolean isTreated= false;

		  if(event.getKeyCode()==KeyEvent.VK_ENTER){
			  if(debug==false){
			  debug=true;}
			  else {
				  debug=false;}
          }
		  
		  if(event.getKeyCode()==KeyEvent.VK_BACK_SPACE){
			  if(iscollision==false){
				  setIsCollision(true);
				  speed=256;}
			  else {
				  setIsCollision(false);
				  speed=48;}
          }
		
		  if(event.getKeyCode()==KeyEvent.VK_UP)
		  {
			  up = true;
				isTreated= true;
		  }
          if(event.getKeyCode()==KeyEvent.VK_LEFT)
          {
        	  left = true;
  			isTreated= true;
          }
          if(event.getKeyCode()==KeyEvent.VK_RIGHT)
          {
        	  right = true; 
  			isTreated= true;
          }
          if(event.getKeyCode()==KeyEvent.VK_DOWN)
          {
        	  down = true;	
  			isTreated= true;
          }
          
          return isTreated;
	}

	@Override
	public boolean keyReleased(KeyEvent event) {
		
		boolean isTreated= false;
		
		if(event.getKeyCode()==KeyEvent.VK_ESCAPE){
			mF.remove(this);
			mF.addmP();
			mF.setCurrentlyOpened(mF);
			mF.repaint();
			isTreated= true;
		}
		
		if(event.getKeyCode()==KeyEvent.VK_UP)
		{
            up = false;
			isTreated= true;
		}
		
        if(event.getKeyCode()==KeyEvent.VK_LEFT)
        {  
        	left = false;
          	isTreated= true;
        }
        
        if(event.getKeyCode()==KeyEvent.VK_RIGHT)
        {
        	right = false;
          	isTreated= true;
	    }
        
        if(event.getKeyCode()==KeyEvent.VK_DOWN)
        {
        	down = false;
          	isTreated= true;
        }

          
          return isTreated;
	}
	
	@Override
	public boolean keyTyped(KeyEvent event) {
		boolean isTreated= false;
		
		return isTreated;
	}

	public void setIsCollision(boolean iscollision){
		this.iscollision = iscollision;
		terra.setIsCollision(iscollision);
	}
	


}
