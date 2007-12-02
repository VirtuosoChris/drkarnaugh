package drk.menu;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class WinMenu extends JFrame implements KeyListener{
	
	public WinMenu(){
		super(); //Get a jframe.
		setUndecorated(true);
		setSize(800, 600);                
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-800)/2, (screenSize.height-600)/2, 800, 600); //Set size and position.
        
        JPanel wPanel = new JPanel();
        wPanel.setLayout(new BorderLayout());
        
        ImageIcon wire = new ImageIcon("drk/menu/Wire.jpg"); //Add the picture image.
        JLabel wLabel = new JLabel(wire);
        
        wLabel.setBounds(0, 0, wire.getIconWidth(), wire.getIconHeight());
        wPanel.setBackground(Color.black); //set background color to black.
       	
       	//Text for the story line of beating the game.
       	JTextArea winStory = new JTextArea("You made it back just in time to stop the machine. As"+
												"\nyou shut down the machines power you notice"+ 
												"\neverything in your laboratory has gone back to"+
												"\nnormal. Whew that was a close one. Just another"+
												"\nday in the lab I guess. No time to waste Dr.Karnaugh"+
												"\nmust get back to work on his next new experiment...\n");
		JTextArea winContinue = new JTextArea("\tPress SpaceBar to continue..."); 
       	
		winStory.setEnabled(false); //Settings for the text component
		winStory.setLineWrap(false);
		winStory.setMargin(new Insets(50,30,0,0));
		winStory.setForeground(Color.white);
		winStory.setBackground(Color.black);
		Font font = new Font("Arial", Font.BOLD, 28);
        winStory.setFont(font);
        winStory.setEditable(false);
        
		winContinue.setEnabled(false); //Settings for the space bar text.
		winContinue.setLineWrap(false);
		winContinue.setMargin(new Insets(20,0,0,0));
		winContinue.setForeground(Color.white);
		winContinue.setBackground(Color.black);
        winContinue.setFont(font);
        winContinue.setEditable(false);
		
		wPanel.add(winStory, BorderLayout.NORTH); //Add everything correctly.
		wPanel.add(wLabel, BorderLayout.CENTER);
		wPanel.add(winContinue, BorderLayout.SOUTH);
		
		setContentPane(wPanel); //Add them.
		addKeyListener(this); //Set the key listener to the jframe.
	}
		
		public void keyPressed(KeyEvent ke){
			if(ke.getKeyCode() == KeyEvent.VK_SPACE){
				this.dispose(); //If you hit space this jframe will close and the credits will show.
				Credits.mainCredit();
			}
			else{
				
			}
		}
	
		public void keyReleased(KeyEvent ke){} //Defaults
		public void keyTyped(KeyEvent ke){}
		
		public static void WinGame(){
			WinMenu wm = new WinMenu(); //Create a new wingame, set it visible and make sure it has focus.
			wm.setVisible(true);
			wm.requestFocus();		
		}
	
		public static void main(String args[]){
			WinGame();
		}
	
}