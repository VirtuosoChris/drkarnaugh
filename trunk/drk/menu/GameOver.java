package drk.menu;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.KeyEvent;

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