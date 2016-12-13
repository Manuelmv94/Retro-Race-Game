import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import sun.audio.*;
import java.io.*;

public class panelCarro extends JPanel implements Runnable{
	private int x,y,Y,z;
	private Color fondo;
	private Color cfondo;
	private boolean crash;
	private GameLogic Game;
	private int score;
	private int speed;
	private int extraspeed;
	private int calle;
	private InputStream inBackground,inBackground1,inBackground2,inBackground3,inCoin, inFail ;
	private AudioStream asBackground,asBackground1,asBackground2,asBackground3,asCoin, asFail ;
	private AudioData adBackground,adBackground1,adBackground2,adBackground3;
	private ContinuousAudioDataStream bgd,bgd1,bgd2,bgd3;
	
 	public panelCarro(GameLogic Game){
		super();
		this.Game=Game;
		
		//this.fondo=new Color(102,178,255);
		this.fondo=new Color(190,216,144);
		//this.cfondo=new Color(0,128,255);
		this.cfondo=new Color(143,171,121);
		
		this.setPreferredSize(new Dimension(640,480));

		try {
			inBackground=new FileInputStream("bass.AU");
			asBackground= new AudioStream(inBackground);
			adBackground=this.asBackground.getData();
			bgd=new ContinuousAudioDataStream(this.adBackground);
			
			inBackground1=new FileInputStream("bass2.AU");
			asBackground1= new AudioStream(inBackground1);
			adBackground1=this.asBackground1.getData();
			bgd1=new ContinuousAudioDataStream(this.adBackground1);
			
			inBackground2=new FileInputStream("bass3.AU");
			asBackground2= new AudioStream(inBackground2);
			adBackground2=this.asBackground2.getData();
			bgd2=new ContinuousAudioDataStream(this.adBackground2);
			
			inBackground3=new FileInputStream("bass4.AU");
			asBackground3= new AudioStream(inBackground3);
			adBackground3=this.asBackground3.getData();
			bgd3=new ContinuousAudioDataStream(this.adBackground3);
			
		} catch (FileNotFoundException e1) {
			
			e1.printStackTrace();
		} catch (IOException e1) {
		
			e1.printStackTrace();
		}
		
		this.setBackground(this.fondo);
		this.score=0;
		this.speed=3;
		this.crash=false;
		this.calle=0;
		this.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if(Game.getCrash()==false){
					if(e.getX()>320){
						Game.setPlayer(true);
					}else{
						Game.setPlayer(false);
					}
				}else{
					Game.setCrash();
					AudioPlayer.player.start(bgd);
				}
				if(e.getX()>620 && e.getY()<20){
					Game.reset();
					score=0;
					speed=3;
					extraspeed=0;
				}
				repaint();
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
				
				
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		this.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					if (Game.getCrash()==false){
						Game.setPlayer(true);
					}
				}
				else if(e.getKeyCode()==KeyEvent.VK_LEFT){
					if (Game.getCrash()==false){
						Game.setPlayer(false);
					}
				}
				else if(e.getKeyCode()==KeyEvent.VK_R){
					Game.reset();
					score=0;
					speed=3;
					extraspeed=0;
					
					
					AudioPlayer.player.stop(bgd);
					AudioPlayer.player.stop(bgd1);
					AudioPlayer.player.stop(bgd2);
					AudioPlayer.player.stop(bgd3);
					AudioPlayer.player.start(bgd);
					
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		this.setFocusable(true);
		
		Thread hilo=new Thread(this);
		hilo.start();
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		this.pintarCuadricula(g);
		this.pintarCalle(g);
		this.pintarCarro(g);
		this.pintaObstaculo(g);
		if(this.Game.getLives()==0){
			this.pintarGameOver(g);
			AudioPlayer.player.stop(bgd);
			AudioPlayer.player.stop(bgd1);
			AudioPlayer.player.stop(bgd2);
			AudioPlayer.player.stop(bgd3);
			
		}
		this.pintaMenu(g);
		this.pintaScore(g);
	}
	
	public void pintarCuadricula(Graphics g){
		
	
		g.setColor(this.cfondo);
			for(int i=0;i<this.getHeight();i+=30){
				g.drawLine(0, i, this.getWidth(), i);
			}
			for(int i=0;i<this.getWidth();i+=30){
				g.drawLine(i, 0, i, this.getHeight());
			}
			
			
			for (int i=6;i<this.getHeight();i+=30){
				for(int j=6;j<this.getWidth();j+=30){
					g.fillRect(j,i,18,18);
				}
			}
		
	}

	public void pintarCarro(Graphics g){
		x=Game.getPlayer()*90;
		
		g.setColor(Color.BLACK);
		g.fillRect(91+x,240+151,28,28);
		g.fillRect(91+x,300+151,28,28);
		g.fillRect(121+x,210+151,28,28);
		g.fillRect(121+x,240+151,28,28);
		g.fillRect(121+x,270+151,28,28);
		g.fillRect(151+x,240+151,28,28);
		g.fillRect(151+x,300+151,28,28);
		

		g.setColor(this.fondo);
		g.fillRect(93+x,240+153,24,24);
		g.fillRect(93+x,300+153,24,24);
		g.fillRect(123+x,210+153,24,24);
		g.fillRect(123+x,240+153,24,24);
		g.fillRect(123+x,270+153,24,24);
		g.fillRect(153+x,240+153,24,24);
		g.fillRect(153+x,300+153,24,24);
		
		g.setColor(Color.BLACK);
		g.fillRect(96+x,240+156,18,18);
		g.fillRect(96+x,300+156,18,18);
		g.fillRect(126+x,210+156,18,18);
		g.fillRect(126+x,240+156,18,18);
		g.fillRect(126+x,270+156,18,18);
		g.fillRect(156+x,240+156,18,18);
		g.fillRect(156+x,300+156,18,18);
	}

	public void pintaObstaculo(Graphics g){
		
		z=Game.getPosObstacle()*30-120;
		
		if(Game.getObstacle()<3){
			y=Game.getObstacle()*90;
			Y=0;
		}else{
			if(Game.getObstacle()==3){
				y=0;
				Y=90;
			}else if(Game.getObstacle()==4){
				y=90;
				Y=180;
			}else{
				y=0;
				Y=180;
			}
		}
			g.setColor(Color.BLACK);
			g.fillRect(91+y,31+z,28,28);
			g.fillRect(91+y,91+z,28,28);
			g.fillRect(121+y,1+z,28,28);
			g.fillRect(121+y,31+z,28,28);
			g.fillRect(121+y,61+z,28,28);
			g.fillRect(151+y,31+z,28,28);
			g.fillRect(151+y,91+z,28,28);
			
	
			g.setColor(this.fondo);
			g.fillRect(93+y,33+z,24,24);
			g.fillRect(93+y,93+z,24,24);
			g.fillRect(123+y,3+z,24,24);
			g.fillRect(123+y,33+z,24,24);
			g.fillRect(123+y,63+z,24,24);
			g.fillRect(153+y,33+z,24,24);
			g.fillRect(153+y,93+z,24,24);
			
			g.setColor(Color.BLACK);
			g.fillRect(96+y,36+z,18,18);
			g.fillRect(96+y,96+z,18,18);
			g.fillRect(126+y,6+z,18,18);
			g.fillRect(126+y,36+z,18,18);
			g.fillRect(126+y,66+z,18,18);
			g.fillRect(156+y,36+z,18,18);
			g.fillRect(156+y,96+z,18,18);
			
			if(Game.getObstacle()>2){
				g.setColor(Color.BLACK);
				g.fillRect(91+Y,31+z,28,28);
				g.fillRect(91+Y,91+z,28,28);
				g.fillRect(121+Y,1+z,28,28);
				g.fillRect(121+Y,31+z,28,28);
				g.fillRect(121+Y,61+z,28,28);
				g.fillRect(151+Y,31+z,28,28);
				g.fillRect(151+Y,91+z,28,28);
				
		
				g.setColor(this.fondo);
				g.fillRect(93+Y,33+z,24,24);
				g.fillRect(93+Y,93+z,24,24);
				g.fillRect(123+Y,3+z,24,24);
				g.fillRect(123+Y,33+z,24,24);
				g.fillRect(123+Y,63+z,24,24);
				g.fillRect(153+Y,33+z,24,24);
				g.fillRect(153+Y,93+z,24,24);
				
				g.setColor(Color.BLACK);
				g.fillRect(96+Y,36+z,18,18);
				g.fillRect(96+Y,96+z,18,18);
				g.fillRect(126+Y,6+z,18,18);
				g.fillRect(126+Y,36+z,18,18);
				g.fillRect(126+Y,66+z,18,18);
				g.fillRect(156+Y,36+z,18,18);
				g.fillRect(156+Y,96+z,18,18);
			}
	}
	
	public void pintarCalle(Graphics g){
		g.setColor(Color.black);
		g.fillRect(0, 0, 60, 480);
		g.fillRect(390, 0, 30, 480);
		
		for (int i=0;i<480;i+=90){
		
			g.fillRect(60, i+this.calle-30, 30, 60);
			g.fillRect(360, i+this.calle-30, 30, 60);
		}
		
	
		g.setColor(this.fondo);
		for (int i=3;i<480;i+=90){
			
			g.fillRect(63, i+this.calle-30, 24, 24);
			g.fillRect(63, i+this.calle, 24, 24);
			g.fillRect(363, i+this.calle-30, 24, 24);
			g.fillRect(363, i+this.calle, 24, 24);
			
		}
		
		g.setColor(Color.BLACK);
		for (int i=6;i<480;i+=90){
			
			g.fillRect(66, i+this.calle-30, 18, 18);
			g.fillRect(66, i+this.calle, 18, 18);
			g.fillRect(366, i+this.calle-30, 18, 18);
			g.fillRect(366, i+this.calle, 18, 18);
			
		}
		
	}

	public void pintarGameOver(Graphics g){
		int e=5;
		int E=15;
		g.setColor(Color.BLACK);
		g.fillRect(100, 120, 250, 180);
		g.setColor(this.fondo);
		
		g.fillRect(110+25, 140+e+E, 10, 30);
		g.fillRect(115+25, 135+e+E, 10, 5);
		g.fillRect(120+25, 130+e+E, 25, 5);
		g.fillRect(115+25, 170+e+E, 10, 5);
		g.fillRect(120+25, 175+e+E, 30, 5);
		g.fillRect(140+25, 155+e+E, 10, 20);
		g.fillRect(135+25, 155+e+E, 10, 5);
		
		g.fillRect(165+20,140+e+E,10,40);
		g.fillRect(170+20,135+e+E, 10, 5);
		g.fillRect(175+20,130+e+E, 15, 5);
		g.fillRect(185+20, 135+e+E, 10, 5);
		g.fillRect(190+20, 140+e+E, 10, 40);
		g.fillRect(175+20, 155+e+E, 15, 10);
		
		g.fillRect(215+15, 130+e+E, 10, 50);
		g.fillRect(225+15, 135+e+E, 5, 20);
		g.fillRect(230+15, 140+e+E, 5, 20);
		g.fillRect(235+15, 135+e+E, 5, 20);
		g.fillRect(240+15, 130+e+E, 10, 50);
		
		g.fillRect(265+10, 130+e+E, 10, 50);
		g.fillRect(275+10, 130+e+E, 25, 7);
		g.fillRect(275+10, 150+e+E, 20, 7);
		g.fillRect(275+10, 173+e+E, 25, 7);
		
		g.fillRect(135,205+E,10,40);
		g.fillRect(140,200+E,30,5);
		g.fillRect(165,205+E,10,40);
		g.fillRect(140,245+E,30,5);
		
		g.fillRect(185,200+E,10,35);
		g.fillRect(190,230+E,10,10);
		g.fillRect(195,235+E,15,10);
		g.fillRect(200, 245+E, 5, 5);
		g.fillRect(205,230+E,10,10);
		g.fillRect(210,200+E,10,35);
		
		g.fillRect(230,200+E,10,50);
		g.fillRect(240,200+E,25,7);
		g.fillRect(240,220+E,20,7);
		g.fillRect(240,243+E,25,7);
		
		g.fillRect(275,200+E,10,50);
		g.fillRect(285,200+E,20,5);
		g.fillRect(300,205+E,10,15);
		g.fillRect(295,220+E,10,5);
		g.fillRect(285,225+E,15,5);
		g.fillRect(295,230+E,10,10);
		g.fillRect(300,240+E,10,10);
		
	}
	
	public void pintaScore(Graphics g){
		int tempscore=0;
		g.setColor(this.fondo);
		
		g.fillRect(400, 305, 7, 3);
		g.fillRect(397, 308, 3, 7);
		g.fillRect(400, 315, 7, 3);
		g.fillRect(407, 318, 3, 7);
		g.fillRect(400, 325, 7, 3);
		
		g.fillRect(415, 315, 3, 10);
		g.fillRect(418, 312, 10, 3);
		g.fillRect(418, 325, 10, 3);
		
		g.fillRect(432, 315, 3, 10);
		g.fillRect(435, 312, 10, 3);
		g.fillRect(435, 325, 10, 3);
		g.fillRect(445, 315, 3, 10);
		
		g.fillRect(452, 312, 3, 16);
		g.fillRect(455, 315, 3, 3);
		g.fillRect(458, 312, 3, 3);
		
		g.fillRect(464, 319, 12, 3);
		g.fillRect(464, 314, 3, 11);
		g.fillRect(476, 314, 3, 6);
		g.fillRect(467, 312, 9, 3);
		g.fillRect(467, 325, 10, 3);
		
		for(int i=0;i<3;i++){
			switch(i){
			case 0:
				tempscore=this.Game.getScorec();
				break;
			case 1: 
				tempscore=this.Game.getScored();
				break;
			case 2:
				tempscore=this.Game.getScoreu();
				break;
			}
			
			if(tempscore==0 || tempscore==2 || tempscore==3 || tempscore==5 || tempscore==6 || tempscore==7 ||
			   tempscore==8 || tempscore==9)
				g.setColor(this.fondo);
			else{
				g.setColor(Color.BLACK);
			}
			g.fillRect(495+(35*i), 305, 15, 5);
			if(tempscore==0 || tempscore==4 || tempscore==5 || tempscore==6 || tempscore==8 || tempscore==9)
				g.setColor(this.fondo);
			else{
				g.setColor(Color.BLACK);
			}
			g.fillRect(490+(35*i), 310, 5, 15);
			if(tempscore==0 || tempscore==1 || tempscore==2 || tempscore==3 || tempscore==4 || tempscore==7 ||
			   tempscore==8 || tempscore==9)
				g.setColor(this.fondo);
			else{
				g.setColor(Color.BLACK);
			}
			g.fillRect(510+(35*i), 310, 5, 15);
			if(tempscore==2 || tempscore==3 || tempscore==4 || 	tempscore==5 || tempscore==6 || tempscore==8 ||
			   tempscore==9)
				g.setColor(this.fondo);
			else{
				g.setColor(Color.BLACK);
			}
			g.fillRect(495+(35*i), 325, 15, 5);
			if(tempscore==0 || tempscore==2 || tempscore==6 || tempscore==8)
				g.setColor(this.fondo);
			else{
				g.setColor(Color.BLACK);
			}
			g.fillRect(490+(35*i), 330, 5, 15);
			if(tempscore==0 || tempscore==1 || tempscore==3 || tempscore==4 || tempscore==5 || tempscore==6 ||
			   tempscore==7 || tempscore==8 || tempscore==9)
				g.setColor(this.fondo);
			else{
				g.setColor(Color.BLACK);
			}
			g.fillRect(510+(35*i), 330, 5, 15);
			if(tempscore==0 || tempscore==2 || tempscore==3 || tempscore==5 || tempscore==6 || tempscore==8)
				g.setColor(this.fondo);
			else{
				g.setColor(Color.BLACK);
			}
			g.fillRect(495+(35*i), 395-50, 15, 5);
		}
	}
	
	public void pintaMenu(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(420, 0, 220, 480);
		
		
		g.setColor(this.fondo);
	
		g.fillRect(450, 30, 17, 5);//
		g.fillRect(445, 35, 5, 15);//
		g.fillRect(450, 50, 15, 5);//
		g.fillRect(465, 55, 5, 15);//
		g.fillRect(448, 70, 17, 5);
		
		
		
		g.fillRect(475, 30, 5, 45);
		g.fillRect(475, 30, 20, 5);
		g.fillRect(475, 50, 20, 5);
		g.fillRect(495, 35, 5, 15);
		
		g.fillRect(505, 30, 5, 45);
		g.fillRect(505, 30, 20, 5);
		g.fillRect(505, 50, 20, 5);
		g.fillRect(505, 70, 20, 5);
		
		g.fillRect(530, 30, 5, 45);
		g.fillRect(530, 30, 20, 5);
		g.fillRect(530, 50, 20, 5);
		g.fillRect(530, 70, 20, 5);
		
		g.fillRect(555, 30, 5, 45);
		g.fillRect(555, 30, 20, 5);
		g.fillRect(555, 70, 20, 5);
		g.fillRect(575, 35, 5, 35);
		
	
	
		g.setColor(this.fondo);
		g.fillRect(480,100, 10, 40);
		g.setColor(new Color(20,20,20));
		g.fillRect(480,150, 10, 40);
	
		g.fillRect(490,90, 40, 10);
		g.setColor(this.fondo);
		g.fillRect(490,140, 40, 10);
		g.setColor(new Color(20,20,20));
		g.fillRect(490,190, 40, 10);
		g.setColor(this.fondo);
		g.fillRect(530,100, 10, 40);
		g.fillRect(530,150, 10, 40);
		
		//g.setColor(Color.WHITE);
		
		int x=10;
		if(this.Game.getLives()<1){
			g.setColor(Color.BLACK);
		}
		g.fillRect(480-50+x, 400, 5, 15);
		g.fillRect(485-50+x, 395, 15, 5);
		g.fillRect(500-50+x,400,5,5);
		g.fillRect(505-50+x, 395, 15, 5);
		g.fillRect(520-50+x, 400, 5, 15);
		g.fillRect(485-50+x,415,5,5);
		g.fillRect(490-50+x,420,5,5);
		g.fillRect(495-50+x,425,5,5);
		g.fillRect(500-50+x,430,5,5);
		g.fillRect(505-50+x,425,5,5);
		g.fillRect(510-50+x,420,5,5);
		g.fillRect(515-50+x,415,5,5);
		//
		if(this.Game.getLives()<2){
			g.setColor(Color.BLACK);
		}
		g.fillRect(480+x, 400, 5, 15);
		g.fillRect(485+x, 395, 15, 5);
		g.fillRect(500+x,400,5,5);
		g.fillRect(505+x, 395, 15, 5);
		g.fillRect(520+x, 400, 5, 15);
		g.fillRect(485+x,415,5,5);
		g.fillRect(490+x,420,5,5);
		g.fillRect(495+x,425,5,5);
		g.fillRect(500+x,430,5,5);
		g.fillRect(505+x,425,5,5);
		g.fillRect(510+x,420,5,5);
		g.fillRect(515+x,415,5,5);
		//
		if(this.Game.getLives()<3){
			g.setColor(Color.BLACK);
		}
		g.fillRect(480+50+x, 400, 5, 15);
		g.fillRect(485+50+x, 395, 15, 5);
		g.fillRect(500+50+x,400,5,5);
		g.fillRect(505+50+x, 395, 15, 5);
		g.fillRect(520+50+x, 400, 5, 15);
		g.fillRect(485+50+x,415,5,5);
		g.fillRect(490+50+x,420,5,5);
		g.fillRect(495+50+x,425,5,5);
		g.fillRect(500+50+x,430,5,5);
		g.fillRect(505+50+x,425,5,5);
		g.fillRect(510+50+x,420,5,5);
		g.fillRect(515+50+x,415,5,5);
		
		
	}

	public void addScore(){
		try {
			inCoin=new FileInputStream("coin.au");
			asCoin= new AudioStream(inCoin);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		AudioPlayer.player.start(asCoin);
		
		if(this.speed<8){
			if(this.score<1){
				this.score++;
				
			}else{
				this.score=0;
				this.speed++;
				if(this.speed==6){
					AudioPlayer.player.start(bgd1);
					AudioPlayer.player.stop(bgd);
					
				}
			}
		}else{
			if(this.extraspeed<7){
				this.extraspeed++;
				if(this.extraspeed==2){
					AudioPlayer.player.start(bgd2);
					AudioPlayer.player.stop(bgd1);
					
				}else if(this.extraspeed==7){
					AudioPlayer.player.start(bgd3);
					AudioPlayer.player.stop(bgd2);
					
				}
			}
		}
		System.out.println(this.speed+""+this.extraspeed);
	}
		
	public void moverCalle(){
		if(Game.getCrash()==false){
			if(this.calle==60){
				this.calle=0;
			}else{
				this.calle+=30;
			}
		}
	}
	
	public void playFail(){
		try {
			inFail=new FileInputStream("fail.au");
			asFail= new AudioStream(inFail);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		AudioPlayer.player.start(asFail);
	}
	
	@Override
	public void run() {
		try{
			while(true){
				Thread.sleep(200-(this.speed*20+this.extraspeed));
				Game.moveObstacle();
				this.moverCalle();
				this.repaint();
			}
		}catch(InterruptedException ex){
			System.out.println("Interrupción");
		}
		
	}
}



