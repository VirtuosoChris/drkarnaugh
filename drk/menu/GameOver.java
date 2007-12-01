package drk.menu;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
/*public class GameOver extends JFrame{
	
	public static JFrame gameover;

	public static void gameisOver(){
		//int laugh = 0;
		gameover = new JFrame();
		gameover.setUndecorated(true);
		gameover.setSize(800, 600);                
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        gameover.setBounds((screenSize.width-800)/2, (screenSize.height-600)/2, 800, 600);
        
        ImageIcon gobunny = new ImageIcon("drk/menu/Bunny.jpg");
        JLabel goLabel = new JLabel(gobunny);
        goLabel.setBounds(0, 0, gobunny.getIconWidth(), gobunny.getIconHeight());
       
       	gameover.setContentPane(goLabel);
		gameover.setVisible(true);
		
	//	File haha = new File("drk/sound/music/Laugh.mp3");
		
		/*if(haha!=null){
	//	laugh = SoundStreamer.playThreadedStreamedLooped(haha);
		}
		else{
			System.out.println("The bunny cannot laugh.");
		}
     	
		gameover.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent ke){
				if(ke.getKeyCode() == KeyEvent.VK_SPACE){
					gameover.dispose();
					Menu mainMenu = new Menu();
					mainMenu.GameGUI();
				}
				else{
					
				}
			}
		});
		gameover.requestFocus();
		
	}
	
	public static void main(String args[]){
		gameisOver();
	}
	
}*/

/*
 * I fixed GameOver to be what I thought it should be, then I thought to myself "You know what, I'm a jerk.  There is a chance the static method thing was intentional, and even if it wasn't, I have no right to critize or modify your code without asking you, especially considering at this point I know so little about the way the menu is functioning anyway.
Please accept my apologies.  I left my changes because I think they are clearer and less error-prone, but feel free to ditch them.  Once again, I'm sorry I was a jerk
 */



//Modified so that GameOver will actually behave like a JFrame is supposed to, because it inherits it correctly,
//This is distinctly different than static members of a GameOver class.  
public class GameOver extends JFrame implements KeyListener{
	
	//public static JFrame gameover;

	public GameOver()
	{
		super();
		setUndecorated(true);
		setSize(800, 600);                
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((screenSize.width-800)/2, (screenSize.height-600)/2, 800, 600);
    
		ImageIcon gobunny = new ImageIcon("drk/menu/Bunny.gif");
		JLabel goLabel = new JLabel(gobunny);
		goLabel.setBounds(0, 0, gobunny.getIconWidth(), gobunny.getIconHeight());
   
		setContentPane(goLabel);
		addKeyListener(this);
	}
	
	public void keyPressed(KeyEvent ke){
			if(ke.getKeyCode() == KeyEvent.VK_SPACE){
				this.dispose();
				Menu mainMenu = new Menu();
				mainMenu.GameGUI();
			}
			else{
				
			}
	}
	
	public void keyReleased(KeyEvent ke){}
	public void keyTyped(KeyEvent ke){}
	
	public static void gameisOver()
	{
		GameOver go=new GameOver();
		go.setVisible(true);
		go.requestFocus();		
	}
	
	public static void main(String args[]){
		gameisOver();
	}
	
}