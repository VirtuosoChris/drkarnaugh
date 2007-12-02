package drk.menu;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;


public class Credits extends JPanel implements ActionListener{
	
	protected static JFrame frame;
	protected ArrayList<String> buffer;
	protected int lines;
	protected int i;
	protected Timer timer;
 	
 	//Constructor that addeds the credits in properly and sets up the timer.
	public Credits(String[] text, int lines, int interval){
		
		i = 0;
		buffer = new ArrayList<String>(lines); //Add in the credits.
		for (int i = 0; i < 10; i++){
			buffer.add(" ");
		}
		for (int i = 0; i < lines; ++i){
			buffer.add(text[i]);
		}
		this.lines = lines;
		this.setBackground(Color.black);
		timer = new javax.swing.Timer(interval, this); //Setup the timer and start it.
		timer.start();
	}
 
	public void paintComponent(Graphics g){
		
		if(i == lines*2){ //If its towards the end Show "THE END" as the last credit.
			timer.stop();
			super.paintComponent(g);
			g.setColor(new Color(0.0f, 0.5f, 0.5f).brighter().brighter());
			g.setFont(new Font("Serif", Font.BOLD, 36));
			g.drawString("THE END", 200, 200);
		}
		
		else{
		super.paintComponent(g);
		
		String first = buffer.remove(0);
		buffer.add(first); //This is so the credits would loop if needed.
 
		FontMetrics fm = getFontMetrics(getFont());
		int height = fm.getHeight()*3; //Get the height inbetween credits.
		int y = height;
 
		for (int j = 0; j < buffer.size(); j++){
			String letter = (String)buffer.get(j);
			int x = 50;
			g.setColor(new Color(0.0f, 0.5f, 0.5f).brighter());
			g.setFont(new Font("Serif", Font.BOLD, 20));
			g.drawString(letter, x, y); //Draw the text at certain locations.
			y += height;	
		}
		}
	}
 	
 	//To be done at each Time interval.
	public void actionPerformed(ActionEvent e){
		i+=1;
		if(i == lines*2){
			repaint(); //Repaint the ending credit.
		}
		else if(i == (lines*2)-1){ //Rewrite the credits accordingly.
			buffer.clear();
			for(int j = 0; j < lines; j++){
				buffer.add(" ");
			}
			repaint();
		}
		
		if(i > lines*2){ //If it goes over, then do nothing, if not make sure it keeps repainting.
			
		}
		else
			repaint();
	}
 
 	public static void mainCredit(){
		
		frame = new JFrame("Dr.Karnaugh's Laboratory");
		String[] credits = { //List of all the credits or text we will be using.
		"Special thanks to the Creators",
		" ",
		"Steve Braeger",
		"Chris Pugh",
		"Heath Washburn",
		" ",
		"Special thanks to authors of the resources we used",
		"XXX",
		"XXX",
		"XXX",
		" ",
		" ",
		"Thanks to everyone who helped with creating this game."};
		
		frame.setSize(600, 400);
		frame.setResizable(false); //Make sure user cant change the screen size.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //For now just exit the game when you win.
		frame.getContentPane().add(new Credits(credits, 13, 700)); //Set up the text, the number of lines of text, and the time interval.
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
 	}
 
	public static void main(String[] args){
		mainCredit();
	}
}