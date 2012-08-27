package mainPackage;

import game.GamePanel;
import game.SaveDataLib;
import sounds.SoundLib;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MenüPanel extends JPanel
{
	  private static final long serialVersionUID = 2294685016438617741L;
	  private MainFrame mF;
	  private GamePanel gP;
	  private MenüPanel mP = this;
	  private SoundLib soundlib;
	  
	public MenüPanel(MainFrame mF,GamePanel gP){
		this.mF=mF;
		this.gP=gP;
		soundlib = new SoundLib();
	}

  @Override
  protected void paintComponent( Graphics g )
  {
    super.paintComponent( g ); 
    g.fillRect(2, 2, 600, 600);
  }
  
  public void init() {
	  this.setDoubleBuffered(true);
	  this.setLayout(null);
	  this.setBackground(Color.LIGHT_GRAY);
	  int mPwidth=208;
	  int mPheight=272;
	  this.setBounds(0, 0, mPwidth, mPheight);
	  this.setLocation((MainFrame.WindowWidth-mPwidth)/2, (MainFrame.WindowHeight-mPheight)/2);
	  this.setBorder(BorderFactory.createCompoundBorder(
			  BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
	  // Button 1 New Game
	  final JButton buttonMenü = new JButton("New Game");
	  buttonMenü.setBounds(getWidth()/2-96, getHeight()/2-128, 192, 48);
	  this.add(buttonMenü);
	  
	  ActionListener al = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			buttonMenü.setIcon(null);
			soundlib.stopLoopingSound();
			gP = new GamePanel(new SaveDataLib(), MainFrame.WindowWidth, MainFrame.WindowHeight, soundlib,mF);
			mF.add(gP);
			mF.remove(mP);
			mF.setCurrentlyOpened(gP);
			mF.repaint();
		}
		  
		  
	  };
	  buttonMenü.addActionListener(al);
	  //button2 Speichern
	  final JButton buttonSave = new JButton("Save");
	  buttonSave.setBounds(getWidth()/2-96, getHeight()/2-76, 192, 48);
	  this.add(buttonSave);
	  buttonSave.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				buttonSave.setIcon(null);		
			}			  
		  });
	  //button3 Load
	  final JButton buttonLoad = new JButton("Load");
	  buttonLoad.setBounds(getWidth()/2-96, getHeight()/2-24, 192, 48);
	  this.add(buttonLoad);
	  buttonLoad.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				buttonLoad.setIcon(null);		
			}			  
		  });
	  //button4 Options
	  final JButton buttonOptions = new JButton("Options");
	  buttonOptions.setBounds(getWidth()/2-96, getHeight()/2+28, 192, 48);
	  this.add(buttonOptions);
	  buttonOptions.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				buttonOptions.setIcon(null);		
			}			  
		  });
	  //button5 
	  final JButton buttonEnd = new JButton("Exit");
	  buttonEnd.setBounds(getWidth()/2-96, getHeight()/2+80, 192, 48);
	  this.add(buttonEnd);
	  buttonEnd.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int option =JOptionPane.showConfirmDialog(null, "Applikation beenden?");
				if (option == JOptionPane.OK_OPTION )
					System.exit(0);				
			}			  
		  });
	 // soundlib.loadSound("Opening", "sounds/01-Opening.mp3");
	  soundlib.loadSound("ThePrelude", "sounds/16-ThePrelude.mp3");
	  soundlib.loopSound("ThePrelude");
}

/*@Override
public void menüClose() {
	// TODO Auto-generated method stub
	mF.remove(this);
	mF.repaint();
	mF.setOpenCloseableMenü(mF);
}*/


}