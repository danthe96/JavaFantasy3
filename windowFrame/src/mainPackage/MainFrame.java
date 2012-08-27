package mainPackage;

import game.GamePanel;
import interfaces.BoolKeyListener;

import java.awt.DefaultKeyboardFocusManager;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JPanel implements BoolKeyListener{

	private BoolKeyListener currentlyOpened;	
	public JFrame f;
	private static final long serialVersionUID = 1L;
	private KeyDispatcher keyList;
    static int WindowWidth;
	static int WindowHeight;
	private BufferedImage background;
	private MenüPanel mP;
	private MainFrame mF;
	private GamePanel gP;
	
	public static void main(String[] args){
		
		OptionTools.IOoptionloader();
		
		new MainFrame();
		
		}
	
	public MainFrame(){
		
        f=new JFrame("Mainframe");
        f.setLayout(null);
        f.setDefaultCloseOperation(/*JFrame.DO_NOTHING_ON_CLOSE*/JFrame.EXIT_ON_CLOSE);
    	f.setSize(WindowWidth+6, WindowHeight+28);
        f.setLocationRelativeTo(null);
       // f.addWindowListener(new WindowClosingListener());
        
        mF = this;
        mF.setLayout(null);
        mF.setBounds(0,0,WindowWidth,WindowHeight);
        mF.setDoubleBuffered(true); 

        mP = new MenüPanel(mF,gP);
        mP.init();
        mF.add(mP);
        
        f.add(mF);
        currentlyOpened = mF;
        
    	keyList=new KeyDispatcher(mF);
    	//keyList.start();
      	
      KeyEventDispatcher keyEdispatcher = keyList;
      
      DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEdispatcher);

      URL pic_url = getClass().getClassLoader().getResource("pics/openingscreen.png");
      
      try {
    	  background=ImageIO.read(pic_url);
      	  } catch (IOException e) {
      		  // TODO Auto-generated catch block
      		  e.printStackTrace();
      	  }

      f.setUndecorated(false);
      f.setResizable(false);
      f.setVisible(true);
	}
	

	
	@Override
	  protected void paintComponent( Graphics g )
	  {	    
		Graphics2D g2 =(Graphics2D)g;
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON );
	    	g2.drawImage(background,0,0,WindowWidth ,WindowHeight ,this );
	  }	


 @Override
 public boolean keyPressed(KeyEvent event) {
	return false;
	
 }

 @Override
 public boolean keyReleased(KeyEvent event) {
	 boolean isTreated=false;
	 
	 if(event.getKeyCode()==KeyEvent.VK_ESCAPE)
			if(gP!=null){
			mF.remove(mP);
			mF.add(gP);
			mF.setCurrentlyOpened(gP);
			mF.repaint();
			isTreated = true;
			}
	return isTreated;
	
 }

 @Override
 public boolean keyTyped(KeyEvent event) {
	return false;
	// TODO Auto-generated method stub
	
 }


//KeyListener currentlyOpened setter/getter methoden

 public BoolKeyListener getCurrentlyOpened() {
	 return currentlyOpened;
 }

 public void setCurrentlyOpened(BoolKeyListener currentlyOpened){
	 this.currentlyOpened=currentlyOpened;	
 }

public void addmP() {
	this.add(mP);// TODO Auto-generated method stub
	
}

public void setGamePanel(GamePanel gP) {
	this.gP = gP;
	
}


}
