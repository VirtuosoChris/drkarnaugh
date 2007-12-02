package drk.menu;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.KeyEvent;

//Modified so that GameOver will actually behave like a JFrame is supposed to, because it inherits it correctly,
//This is distinctly different than static members of a GameOver class.

//Calls a GameOver screen when the player dies (aka, gets hit by the bunny)  
public class GameOver extends JFrame implements KeyListener{
	
	//public static JFrame gameover;

	public GameOver()
	{
		super(); //Create the new jframe.
		setUndecorated(true);
		setSize(800, 600);                
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((screenSize.width-800)/2, (screenSize.height-600)/2, 800, 600); //Set the size and position.
    
		ImageIcon gobunny = new ImageIcon("drk/menu/Bunny.gif"); //Create the bunny picture.
		JLabel goLabel = new JLabel(gobunny);
		goLabel.setBounds(0, 0, gobunny.getIconWidth(), gobunny.getIconHeight());
   
		setContentPane(goLabel); //Add the bunny.
		addKeyListener(this); //Add the key listener to the jframe.
	}
	
	public void keyPressed(KeyEvent ke){
			if(ke.getKeyCode() == KeyEvent.VK_SPACE){
				this.dispose(); //If space is pressed, get rid of the game over menu and produce a new main menu.
				Menu mainMenu = new Menu();
				mainMenu.GameGUI();
			}
			else{
				//If anything else is pressed do nothing.
			}
	}
	
	public void keyReleased(KeyEvent ke){} //Default.
	public void keyTyped(KeyEvent ke){}
	
	public static void gameisOver()
	{
		GameOver go=new GameOver(); //Create a new game over menu.
		go.setVisible(true); //Set it visible and request the focus.
		go.requestFocus();		
	}
	
	public static void main(String args[]){
		gameisOver();
	}
	
}