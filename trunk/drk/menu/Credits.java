package drk.menu;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
 
/*This is just a test credits class. Feel free to change or use anything here.
 *I figured that at the end of the game we could show the final story and then
 *show something like this. I also plan on putting more credits and more elaborate
 *credits to the authors of the resources in the help file. Tommarrow I will work
 *on creating like a final story menu, sort of like I did with the intro menu.*/

public class Credits extends JPanel implements ActionListener{
	
	protected ArrayList buffer;
	protected int lines;
	protected int i;
 
	public Credits(String[] text, int lines, int interval){
		
		i = 0;
		buffer = new ArrayList(lines);
		for (int i = 0; i < 10; i++){
			buffer.add(" ");
		}
		for (int i = 0; i < lines; ++i){
			buffer.add(text[i]);
		}
		this.lines = lines;
		this.setBackground(Color.black);
		new javax.swing.Timer(interval, this).start();
	}
 
	public void paintComponent(Graphics g){
		
		if(i == lines*2){
			super.paintComponent(g);
			g.setColor(new Color(0.0f, 0.5f, 0.5f).brighter().brighter());
			g.setFont(new Font("Serif", Font.BOLD, 36));
			g.drawString("THE END", 200, 200);
		}
		
		else{
		super.paintComponent(g);
		
		Object first = buffer.remove(0);
		buffer.add(first);
 
		FontMetrics fm = getFontMetrics(getFont());
		int height = fm.getHeight()*3;
		int y = height;
 
		for (int j = 0; j < buffer.size(); j++){
			String letter = (String)buffer.get(j);
			int x = (50);
			g.setColor(new Color(0.0f, 0.5f, 0.5f).brighter());
			g.setFont(new Font("Serif", Font.BOLD, 20));
			g.drawString(letter, x, y);
			y += height;	
		}
		}
	}
 
	public void actionPerformed(ActionEvent e){
		i+=1;
		
		if(i == lines*2){
			repaint();
		}
		
		else if(i == (lines*2)-1){
			buffer.clear();
			for(int j = 0; j < lines; j++){
				buffer.add(" ");
			}
			repaint();
		}
		
		if(i > lines*2){
			
		}
		
		else
			repaint();
	}
 
	public static void main(String[] args){
		
		JFrame frame = new JFrame();
		String[] credits = {
		"Special thanks to the Creators",
		" ",
		"Steve",
		"Chris",
		"Heath",
		" ",
		"Special thanks to authors of the resources we used",
		"XXX",
		"XXX",
		"XXX",
		" ",
		"Thanks to everyone who helped with creating this game."};
		
		frame.setSize(600, 400);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new Credits(credits, 12, 700));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}