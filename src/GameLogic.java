import java.util.Random;

public class GameLogic  {
	private int player;
	private int obstacle;
	private int posObstacle;
	private boolean crash;
	private Random rdm;
	private int lives;
	private int scoreu, scored, scorec;
	private panelCarro pc;
	
	
	
	public GameLogic(){
		rdm=new Random();
		this.lives=1;
		this.crash=true;
		player=rdm.nextInt(3);
		obstacle=rdm.nextInt(6);
		posObstacle=0;
		

	}
	
	public void moveObstacle(){
		if(this.crash==false){
			if(this.posObstacle<19 ){
				this.posObstacle++;
			}else{
				if(this.scoreu<=9 && this.scored<=9 && this.scorec<=9){
					this.scoreu++;
					this.pc.addScore();
					if(this.scoreu>9){
						this.scored++;
						this.scoreu=0;
						
						if(this.scored>9){
							this.scorec++;
							this.scored=0;
						}
					}
				}	
				this.obstacle=rdm.nextInt(6);
				this.posObstacle=0;
				
			}
			
			if(posObstacle>=13 && ( (player==0 && (obstacle==0 || obstacle==3 || obstacle==5)) ||
	                			  (player==1 && (obstacle==1 || obstacle==3 || obstacle==4)) || 
	                			  (player==2 && (obstacle==2 || obstacle==4 || obstacle==5)) ) ){
				crash=true;
				if(this.lives>0){
					this.lives--;
					this.pc.playFail();
				}
			}
	}
	}

	public int getPlayer(){
		return this.player;
	}
	
	public int getObstacle(){
		return this.obstacle;
	}
	
	public int getPosObstacle(){
		return posObstacle;
	}

	public boolean getCrash(){
		return this.crash;
	}
	
	public void setCrash(){
		if(this.lives>0){
			this.crash=false;
			this.obstacle=rdm.nextInt(6);
			this.posObstacle=0;
		}
	}
	
	public void reset(){
		this.crash=false;
		this.obstacle=rdm.nextInt(6);
		this.posObstacle=0;
		this.lives=1;
		this.scoreu=0;
		this.scored=0;
		this.scorec=0;
	}
	
	public int getLives(){
		return this.lives;
	}
	
	public int getScoreu(){
		return this.scoreu;
	}

	public int getScored(){
		return this.scored;
	}
	
	public int getScorec(){
		return this.scorec;
	}
	
	public void setPlayer(boolean dir){
		if(dir){
			if(player<2){
				player++;
			}
		}else{
			if(player>0){
				player--;
			}
		}
		
	}

	public void setPc(panelCarro pc){
		this.pc=pc;
	}
}
