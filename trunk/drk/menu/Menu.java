package drk.menu;
import drk.game.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Menu extends drk.game.KarnaughGame implements KeyListener{
	
	JFrame frame, story;
	JScrollPane scrollPane;
	ImageIcon icon;
	JFileChooser gameFile;
	String newline;
	JTextArea log;
	
	public Menu(){
		frame = new JFrame("Dr.Karnaugh's Laboratory");
		gameFile = new JFileChooser();
		icon = new ImageIcon("bg.jpg");
		newline = "\n";
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds((screenSize.width-800)/2, (screenSize.height-600)/2, 800, 600);
		
		JLabel background = new JLabel(icon);
		background.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
		frame.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
		
		JPanel panelG = new JPanel();
		panelG.setOpaque(false);
		
		panelG.setLayout(new GridBagLayout());
		GridBagConstraints constraint = new GridBagConstraints();
		
		//Buttons
		JButton rankB, customB, exitB, blank; 
		rankB = new JButton("Start New Ranked Game");
		rankB.setVerticalTextPosition(AbstractButton.CENTER);
		rankB.setHorizontalTextPosition(AbstractButton.LEADING);
		rankB.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					System.out.println("Starting a new ranked game");
						story = new JFrame();
						story.setUndecorated(true);
						story.setSize(800, 600);                
						Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        				story.setBounds((screenSize.width-800)/2, (screenSize.height-600)/2, 800, 600);
						story.setVisible(true);
						try{
							String lineRead = new String();
							BufferedReader br = new BufferedReader(new FileReader(new File("story.txt")));
							JTextArea storymode = new JTextArea();
							for(int i = 0; i < 11; i++){
								lineRead = br.readLine() + "\n";
								storymode.append(lineRead);
							}
							JPanel storyPanel = new JPanel();
							storyPanel.setLayout(new GridBagLayout());
							GridBagConstraints sconstraint = new GridBagConstraints();
							JLabel imgLabel = new JLabel(new ImageIcon("Mad.jpg"));
							
							storymode.setLineWrap(true);
							storymode.setMargin(new Insets(30,30,30,30));
							storymode.setForeground(Color.white);
							storymode.setBackground(Color.black);
							Font font = new Font("Serif", Font.ITALIC, 20);
        					storymode.setFont(font);
        					storymode.setEditable(false);
        					
        					sconstraint.fill = GridBagConstraints.HORIZONTAL;
							sconstraint.weighty = 3.0;   //request any extra vertical space
							sconstraint.anchor = GridBagConstraints.PAGE_END; //bottom of space
							sconstraint.gridx = 1;       //aligned with button 2
							sconstraint.gridwidth = 4;   //2 columns wide
							sconstraint.gridy = 1;
			
							storyPanel.add(imgLabel, sconstraint);
			
							sconstraint.weighty = 7.0;   //request any extra vertical space
							sconstraint.gridx = 1;       //aligned with button 2
							sconstraint.gridwidth = 4;   //2 columns wide
							sconstraint.gridy = 2;
		
							storyPanel.add(storymode, sconstraint);
							story.add(storyPanel);
						}
						
						catch(FileNotFoundException storyF){
							storyF.printStackTrace();
						}
						catch(IOException storyT){
							storyT.printStackTrace();
						}
						
						frame.setVisible(false);
						story.setVisible(true);
						
						story.addKeyListener(new Menu());
						story.requestFocus();
						
				}
			}
		);
		
		customB = new JButton("Start New Custom Game");
		customB.setVerticalTextPosition(AbstractButton.CENTER);
		customB.setHorizontalTextPosition(AbstractButton.CENTER);
		customB.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){	
					System.out.println("Starting a new custom game");
					System.out.println("Finding the map file");
					
					FileNameExtensionFilter ext = new FileNameExtensionFilter("KAR MAP FILES ONLY", "kar");
    				gameFile.setAcceptAllFileFilterUsed(false);
    				gameFile.setFileFilter(ext);
    				
					int returnVal = gameFile.showOpenDialog(frame);
            		if(returnVal == JFileChooser.APPROVE_OPTION){
                		File file = gameFile.getSelectedFile();
                		log.append("Opening: " + file.getName() + "." + newline);
                		int index = file.getName().lastIndexOf('.'); //Gets just the filename without ext.
                		String fileName = file.getName().substring(0, index);
                		KarnaughGame single = new KarnaughGame();
                		single.setSingleMapCampaign(single, fileName);	
            		} 
            		else
                		log.append("Open command cancelled by user." + newline);
            		log.setCaretPosition(log.getDocument().getLength());
					
				}
			}
		);
		
		exitB = new JButton("Exit Game");
		exitB.setVerticalTextPosition(AbstractButton.CENTER);
		exitB.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){	
					System.out.println("Exiting the game");
					frame.dispose(); frame = null;
				}
			}
		);
		
		constraint.fill = GridBagConstraints.HORIZONTAL;
		constraint.weighty = 1.0;   //request any extra vertical space
		constraint.anchor = GridBagConstraints.PAGE_END; //bottom of space
		constraint.gridx = 1;       //aligned with button 2
		constraint.gridwidth = 4;   //2 columns wide
		constraint.gridy = 1;
		constraint.ipady = 20;
		constraint.ipadx = 600; 
			
		panelG.add(rankB, constraint);
			
		constraint.weighty = 0.0;   //request any extra vertical space
		constraint.gridx = 1;       //aligned with button 2
		constraint.gridwidth = 4;   //2 columns wide
		constraint.gridy = 2;
		
		panelG.add(customB, constraint);
		
		constraint.weighty = 0.0;   //request any extra vertical space
		constraint.gridx = 1;       //aligned with button 2
		constraint.gridwidth = 4;   //2 columns wide
		constraint.gridy = 3;       //third row
		
 		panelG.add(exitB, constraint);
 		
		frame.setContentPane(panelG);
		
		log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
		
		GameGUI();
	}
	
	public void keyPressed(KeyEvent ke){
		if(ke.getKeyCode() == KeyEvent.VK_SPACE){
			mainGame();
		}
	}
	
	public JMenuBar createMenuBar(){
		
		JMenu file = new JMenu("File");
		file.setMnemonic('F');
		JMenuItem rankedItem = new JMenuItem("New Ranked Game");
		rankedItem.setMnemonic('R');
		file.add(rankedItem);
		JMenuItem customItem = new JMenuItem("Open Custom Game");
		customItem.setMnemonic('C');
		file.add(customItem);	
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic('X');
		file.add(exitItem);
		
		JMenu help = new JMenu("Help");
		help.setMnemonic('H');
		JMenuItem gameHelp = new JMenuItem("Game Help");
		gameHelp.setMnemonic('G');
		help.add(gameHelp);
		JMenuItem about = new JMenuItem("About Dr.Karnaugh's Laboratory");
		help.add(about);

		rankedItem.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					System.out.println("Starting a new ranked game");
						story = new JFrame();
						story.setUndecorated(true);
						story.setSize(800, 600);                
						Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        				story.setBounds((screenSize.width-800)/2, (screenSize.height-600)/2, 800, 600);
						story.setVisible(true);
						try{
							String lineRead = new String();
							BufferedReader br = new BufferedReader(new FileReader(new File("story.txt")));
							JTextArea storymode = new JTextArea();
							for(int i = 0; i < 11; i++){
								lineRead = br.readLine() + "\n";
								storymode.append(lineRead);
							}
							JPanel storyPanel = new JPanel();
							storyPanel.setLayout(new GridBagLayout());
							GridBagConstraints sconstraint = new GridBagConstraints();
							JLabel imgLabel = new JLabel(new ImageIcon("Mad.jpg"));
							
							storymode.setLineWrap(true);
							storymode.setMargin(new Insets(30,30,30,30));
							storymode.setForeground(Color.white);
							storymode.setBackground(Color.black);
							Font font = new Font("Serif", Font.ITALIC, 20);
        					storymode.setFont(font);
        					storymode.setEditable(false);
        					
        					sconstraint.fill = GridBagConstraints.HORIZONTAL;
							sconstraint.weighty = 3.0;   //request any extra vertical space
							sconstraint.anchor = GridBagConstraints.PAGE_END; //bottom of space
							sconstraint.gridx = 1;       //aligned with button 2
							sconstraint.gridwidth = 4;   //2 columns wide
							sconstraint.gridy = 1;
			
							storyPanel.add(imgLabel, sconstraint);
			
							sconstraint.weighty = 7.0;   //request any extra vertical space
							sconstraint.gridx = 1;       //aligned with button 2
							sconstraint.gridwidth = 4;   //2 columns wide
							sconstraint.gridy = 2;
		
							storyPanel.add(storymode, sconstraint);
							story.add(storyPanel);
						}
						
						catch(FileNotFoundException storyF){
							storyF.printStackTrace();
						}
						catch(IOException storyT){
							storyT.printStackTrace();
						}
						
						frame.setVisible(false);
						story.setVisible(true);
						
						story.addKeyListener(new Menu());
						story.requestFocus();
				}
			}
		);
		
		customItem.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){	
					System.out.println("Starting a new custom game");
					System.out.println("Finding the map file");
					
					FileNameExtensionFilter ext = new FileNameExtensionFilter("KAR MAP FILES ONLY", "kar");
    				gameFile.setAcceptAllFileFilterUsed(false);
    				gameFile.setFileFilter(ext);
					
					int returnVal = gameFile.showOpenDialog(frame);
            		if(returnVal == JFileChooser.APPROVE_OPTION){
                		File file = gameFile.getSelectedFile();
                		log.append("Opening: " + file.getName() + "." + newline);
                		int index = file.getName().lastIndexOf('.'); //Gets just the filename without ext.
                		String fileName = file.getName().substring(0, index);	
                		KarnaughGame single = new KarnaughGame();
                		single.setSingleMapCampaign(single, fileName);	
                			
            		} 
            		else
                		log.append("Open command cancelled by user." + newline);
            		log.setCaretPosition(log.getDocument().getLength());
					
				}
			}
		);
		
		exitItem.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){	
					System.out.println("Exiting the game");
					frame.dispose(); frame = null;
				}
			}
		);
		
		gameHelp.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					System.out.println("Loading the help file");
					
					try{
						Desktop desktop = Desktop.getDesktop();
						desktop.open(new File("Test.chm"));
					}
					catch(IOException ex){ ex.printStackTrace(); }
					
				}
			}
		);
		
		about.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					System.out.println("Loading the about file");
					JOptionPane.showMessageDialog(frame, 
						"Dr. Karnaughs's Laboratory is an edutainment game created to help everyone of all ages\nto learn about "+
						"computer architecture components while solving puzzles. The objective of\nthis game is to create a fun "+
						"learning enviroment for everyone, whether you have computer\nlogic experience or not.\n\n" +
						"Dr.Karnaugh's Laboratory was created by Steve, Chris, and Heath.\n");
				}
			}
		);
								
		JMenuBar menubar = new JMenuBar();
		menubar.add(file);
		menubar.add(help);
		
		return menubar;
	}
	
	public void GameGUI() {
        
        JPanel foregroundPanel = new JPanel(new BorderLayout(100, 100));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(createMenuBar());
		
        //Display the window.
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setResizable(false);
    }
	
	public static void main(String[] args)
	{
		Menu Test = new Menu();
	}
}