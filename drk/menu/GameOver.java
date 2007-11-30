package drk.menu;
import drk.sound.*;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;

public class GameOver extends JFrame{
	
	public static JFrame gameover;

	public static void gameisOver(){
		int laugh = 0;
		gameover = new JFrame();
		gameover.setUndecorated(true);
		gameover.setSize(800, 600);                
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        gameover.setBounds((screenSize.width-800)/2, (screenSize.height-600)/2, 800, 600);
        
        ImageIcon gobunny = new ImageIcon("Bunny.jpg");
        JLabel goLabel = new JLabel(gobunny);
        goLabel.setBounds(0, 0, gobunny.getIconWidth(), gobunny.getIconHeight());
       
       	gameover.setContentPane(goLabel);
		gameover.setVisible(true);
		
		File haha = new File("drk/sound/music/Laugh.mp3");
		
		if(haha!=null){
		laugh = SoundStreamer.playThreadedStreamedLooped(haha);
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
	
}