package drk.menu;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class WinMenu extends JFrame{
	
	public static JFrame win;
	
	public static void WinGame(){
		win = new JFrame();
		win.setUndecorated(true);
		win.setSize(800, 600);                
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        win.setBounds((screenSize.width-800)/2, (screenSize.height-600)/2, 800, 600);
        
        JPanel wPanel = new JPanel();
        wPanel.setLayout(new BorderLayout());
        
        ImageIcon wire = new ImageIcon("drk/menu/Wire.jpg");
        JLabel wLabel = new JLabel(wire);
        
        wLabel.setBounds(0, 0, wire.getIconWidth(), wire.getIconHeight());
        wPanel.setBackground(Color.black);
       	
       	JTextArea winStory = new JTextArea("You made it back just in time to stop the machine. As"+
												"\nyou shut down the machines power you notice"+ 
												"\neverything in your laboratory has gone back to"+
												"\nnormal. Whew that was a close one. Just another"+
												"\nday in the lab I guess. No time to waste Dr.Karnaugh"+
												"\nmust get back to work on his next new experiment...\n");
		JTextArea winContinue = new JTextArea("\tPress SpaceBar to continue..."); 
       	
		winStory.setEnabled(false);
		winStory.setLineWrap(false);
		winStory.setMargin(new Insets(50,30,0,0));
		winStory.setForeground(Color.white);
		winStory.setBackground(Color.black);
		Font font = new Font("Arial", Font.BOLD, 28);
        winStory.setFont(font);
        winStory.setEditable(false);
        
		winContinue.setEnabled(false);
		winContinue.setLineWrap(false);
		winContinue.setMargin(new Insets(20,0,0,0));
		winContinue.setForeground(Color.white);
		winContinue.setBackground(Color.black);
        winContinue.setFont(font);
        winContinue.setEditable(false);
		
		wPanel.add(winStory, BorderLayout.NORTH);
		wPanel.add(wLabel, BorderLayout.CENTER);
		wPanel.add(winContinue, BorderLayout.SOUTH);
		
		win.setContentPane(wPanel);
		win.setVisible(true);
     	
		win.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent ke){
				if(ke.getKeyCode() == KeyEvent.VK_SPACE){
					win.dispose();
					Credits.mainCredit();
				}
			}
		});
		win.requestFocus();	
	} 
	
	public static void main(String args[]){
		WinGame();
	}
	
}