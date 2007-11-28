package drk.menu;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import drk.menu.*;

public class Credits extends JPanel implements ActionListener{
	
	protected static JFrame frame;
	protected ArrayList<String> buffer;
	protected int lines;
	protected int i;
	protected Timer timer;
 	
	public Credits(String[] text, int lines, int interval){
		
		i = 0;
		buffer = new ArrayList<String>(lines);
		for (int i = 0; i < 10; i++){
			buffer.add(" ");
		}
		for (int i = 0; i < lines; ++i){
			buffer.add(text[i]);
		}
		this.lines = lines;
		this.setBackground(Color.black);
		timer = new javax.swing.Timer(interval, this);
		timer.start();
	}
 
	public void paintComponent(Graphics g){
		
		if(i == lines*2){
			timer.stop();
			super.paintComponent(g);
			g.setColor(new Color(0.0f, 0.5f, 0.5f).brighter().brighter());
			g.setFont(new Font("Serif", Font.BOLD, 36));
			g.drawString("THE END", 200, 200);
			
			try{
				Thread.sleep(5000); //For some reason it does not draw THE END like its suppose to.
				//It is suppose to hold THE END for roughly 5 seconds then send you back to the main menu...
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
			frame.dispose();
			Menu mainMenu = new Menu();
			mainMenu.GameGUI();
		}
		
		else{
		super.paintComponent(g);
		
		String first = buffer.remove(0);
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
 
 	public static void mainCredit(){
		
		frame = new JFrame("Dr.Karnaugh's Laboratory");
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
 
	public static void main(String[] args){
		mainCredit();
	}
}