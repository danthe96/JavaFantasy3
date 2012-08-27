package game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import interfaces.Drawable;
import interfaces.Movable;

public abstract class Sprite extends Rectangle2D.Double implements Drawable,Movable{

  /**
	 * 
	 */
  private static final long serialVersionUID = 9063262585555746595L;
  GamePanel parent;
  BufferedImage[] pics;
  int currentpic = 0;    
  protected double dx;       
  protected double dy;       
  int loop_from;
  int loop_to;
  int parentheight;
  int parentwidth; 
  
  long delay;
  long animation = 0;
  boolean animationwaschanged;
  long last;
  long last2;
  double delta;
  double stretch;
  
  Point2D.Double position = new Point2D.Double();
  Point2D.Double playerposition = new Point2D.Double();
  Point2D.Double checkPoint1 = new Point2D.Double();
  Point2D.Double checkPoint2 = new Point2D.Double();
  BufferedImage shadow;
  Color c1;
  Color c2;
  
  boolean willupcoll;
  boolean willrightcoll;
  boolean willleftcoll;
  boolean willdowncoll;
  

  public Sprite(BufferedImage[] images, long delay, GamePanel parent){

    pics = images;
    this.width = pics[0].getWidth();
    this.height = pics[0].getHeight();
    this.parent = parent;
	this.parentheight=parent.getHeight();  
	this.parentwidth = parent.getWidth();
	stretch= (0.0+parentheight)/256;
    this.delay = delay;
    System.out.println(getHeight());
    this.position.setLocation(0.0+parentwidth/2-(width/2*stretch), 0.0+parentheight/2-(height/2*stretch));     //problematisch
    System.out.println(position+" "+parentwidth+" "+parentheight+" "+ height +" "+width+" "+ stretch);
    loop_from = 0;
    loop_to = pics.length-1;
    shadow = parent.loadPics("pics/overworldmapshadow.png",1)[0];
  }

  public void drawObjects(Graphics g) {
    g.drawImage(pics[currentpic], (int)(position.getX()+0.5), (int) (position.getY()+0.5), (int)(width*stretch+0.5), (int) (height*stretch+0.5), null);
    g.setColor(Color.RED);
    }
    
  public void doLogic(){

	playerposition  = parent.playerposition;
	delta =((System.nanoTime()-last)/1e9);
    animation += delta*1000;
    last = System.nanoTime();
    if(animation > delay || animationwaschanged){
      animation = 0;
      animationwaschanged = false;
      computeAnimation();
    }
    last = System.nanoTime();
    
    if(delta>0.25)
    	delta=0.25;
    
    
    colldown();                                                                 //starten der voids zum überprüfen der kollidierung
    collright();
    collleft();
    collup();
    
  }
  
  
  private void collcolor(){                                                     //void zur errechnung der farbe am collisionsüberprüfungspunkt (=checkPoint)
	    int rgbcol1  = shadow.getRGB((int)(checkPoint1.x/16), (int)(checkPoint1.y/16));
	    int rgbcol2  = shadow.getRGB((int)(checkPoint2.x/16), (int)(checkPoint2.y/16));
	    c1 = new Color(rgbcol1);
	    c2 = new Color(rgbcol2);
	  }   // /////Ende\\\\\\
	  
	  
	  private void colldown(){                                                      //void zum überprüfen ob man mit dem boden kolidiert
	    checkPoint1 .setLocation(playerposition.getX() - width/2 + 1, playerposition.getY() + height/2+(64*delta));
	    checkPoint2 .setLocation(playerposition.getX() + width/2 - 1, playerposition.getY() + height/2+(64*delta));
	    
	    collcolor();                                                                //gehe zu collcolor

	    if(c1.equals(Color.RED)||c2.equals(Color.RED)){                 //vergleichen der Punkte
	      willdowncoll = true;
	    }
	    else {willdowncoll = false;}
	  }     //  /////Ende\\\\\\
	  
	  
	  public void collright(){     //void zum überprüfen ob man rechts kolidiert

	    checkPoint1  .setLocation(playerposition.getX()+ width/2 - 1 + (64*delta), playerposition.getY() - 2);
	    checkPoint2  .setLocation(playerposition.getX()+ width/2 - 1 + (64*delta), playerposition.getY() + height/2);

	    collcolor();
	    
	   if(c1.equals(Color.RED)||c2.equals(Color.RED)){

	      willrightcoll =true ;

	    }

	    else{willrightcoll =false ;}

	  }      //  /////Ende\\\\\\
	  

	  
	  public void collleft(){                                                       //void zum überprüfen ob man links kolidiert

	    checkPoint1   .setLocation(playerposition.getX() - width/2 + 1 - (64*delta), playerposition.getY() - 2);
	    checkPoint2   .setLocation(playerposition.getX() - width/2 + 1 - (64*delta), playerposition.getY() + height/2);

	    collcolor();
	    
	    if(c1.equals(Color.RED)||c2.equals(Color.RED)){

	      willleftcoll=true;
	    }

	    else {willleftcoll=false;}
	  }     //  /////Ende\\\\\\
	  
	  public void collup(){                                                         //void zum überprüfen ob man oben kollidiert

	    checkPoint1   .setLocation(playerposition.getX() - width/2 + 1, playerposition.getY() + -2 -(64*delta));
	    checkPoint2   .setLocation(playerposition.getX() + width/2 - 1, playerposition.getY() + -2 -(64*delta));

	    collcolor();

	    if(c1.equals(Color.RED)||c2.equals(Color.RED)){

	      willupcoll=true;
	    }

	    else {willupcoll=false;}
	  } 
  
  
  
  
  
  
  
 public void move(){
	if(willupcoll&&dy<0)
		dy=0; 
	     
	if(willrightcoll&&dx>0)
		dx=0; 
	    
	if(willdowncoll&&dy>0)
		dy=0; 
	    
	if(willleftcoll&&dx<0)
		dx=0; 
	 
    if(dx!=0)          
    	position.setLocation(position.getX()+dx*((System.nanoTime()-last2)/1e9), position.getY()); 
    if(dy!=0)
    	position.setLocation(position.getX() , position.getY()+dy*((System.nanoTime()-last2)/1e9));
    last2 = System.nanoTime();
  }

  private void computeAnimation(){
	if(dx == 0 && dy == 0){
		currentpic = loop_from + 1;
	}
	else{
    currentpic++;
    if(currentpic>loop_to || currentpic<loop_from)
      currentpic = loop_from;
    }
  }
  public void setLoop(int from, int to){
	if(loop_from != from)
		animationwaschanged = true;
	loop_from = from;
    loop_to = to;
  }
  
  public double getHorizontalSpeed(){
    return dx;
  }
  public void setHorizontalSpeed(double dx){
    this.dx = dx;
  }
  public double getVerticalSpeed() {
    return dy;
  }
  public void setVerticalSpeed(double dy){
    this.dy = dy;
  }
}