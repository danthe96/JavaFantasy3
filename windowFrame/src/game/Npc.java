package game;

import java.awt.image.BufferedImage;

public class Npc extends Sprite {
	int direction;
	
	private static final long serialVersionUID = 1L;

	public Npc(BufferedImage[] images, long delay, GamePanel parent) {
		super(images, delay, parent);
		// TODO Auto-generated constructor stub
	}

	
	@Override public void doLogic(){
		super.doLogic();
		direction = (int)(Math.random()*3+0.5);
		for(int i=0;i<4;i++){
			if(checkcollision())
				direction++;
				direction=direction%4;
				if(i==3){
					System.out.println("Hey Sperr mich nicht ein"+direction);
					direction = 4;
				}
					
		}

	if(direction == 4)
		{dx=0;dy=0;}
		else{
			if(direction == 3)
			{dx=-64;dy=0;}
			if(direction == 2)
			{dx=0;dy=64;}
			if(direction == 1)
			{dx=64;dy=0;}
			if(direction == 0)
			{dx=0;dy=-64;}
			
		}
		
		
		
		
	}
	
	public boolean checkcollision(){
		boolean check=false;
			if(willupcoll&&direction==0){
				check=true;
			}
			if(willrightcoll&&direction==1){
				check=true;
			}
			if(willdowncoll&&direction==2){
				check=true;
			}
			if(willleftcoll&&direction==3){
				check=true;
			}
		return check;
	}
		

}
