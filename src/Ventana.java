import javax.swing.JFrame;

public class Ventana extends JFrame{
	public Ventana(){
		super("tetris car");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		GameLogic Game=new GameLogic();
		panelCarro pc=new panelCarro(Game);
		Game.setPc(pc);
		
		this.add(pc);
		
		this.pack();
		
		
		
		this.setVisible(true);
	}
	
	public static void main(String[] args){
		Ventana v=new Ventana();

	}
}
