package drk.menu;
import drk.game.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Menu extends KarnaughGame implements KeyListener{
	
	public JFrame frame;
	public static JFrame story;
	public JScrollPane scrollPane;
	public ImageIcon icon;
	public JFileChooser gameFile;
	public String newline;
	public JTextArea log;
	public static String userName = null;
	
	public Menu(){
		frame = new JFrame("Dr.Karnaugh's Laboratory");
		gameFile = new JFileChooser();
		icon = new ImageIcon("drk/menu/bg.jpg");
		newline = "\n";
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		try{
			new Robot().mouseMove((screenSize.width)/2,(screenSize.width)/2);
		} 
		catch(AWTException e) {
			System.out.println("Could not get mouse robot.");
		}
        frame.setBounds((screenSize.width-800)/2, (screenSize.height-600)/2, 800, 600);
		
		JLabel background = new JLabel(icon);
		//background.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
		//frame.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
		
		JPanel PanelF = new JPanel(new BorderLayout());
		JPanel panelG = new JPanel();
		JPanel panelB = new JPanel(new BorderLayout());
		
		PanelF.add(createMenuBar(),BorderLayout.NORTH);
		panelB.add(background,BorderLayout.SOUTH);
		PanelF.add(panelB,BorderLayout.CENTER);
		PanelF.add(panelG,BorderLayout.SOUTH);
		panelG.setOpaque(false);
		
		panelG.setLayout(new GridLayout(2,2));
		
		//Buttons
		JButton rankB, customB, resumeB, exitB; 
		rankB = new JButton("Start New Ranked Game");
		rankB.setVerticalTextPosition(AbstractButton.CENTER);
		rankB.setHorizontalTextPosition(AbstractButton.LEADING);
		rankB.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					System.out.println("Starting a new ranked game");
					
						//Adopted from http://www.java2s.com/Code/Java/Swing-JFC/Informationdialogwithcustomizedlogo.htm
    					do{
    						userName = (String)JOptionPane.showInputDialog(new JFrame(),
        										"Please enter your name",
        										"Player Name", JOptionPane.INFORMATION_MESSAGE);
    											System.out.println("User's name is: " + userName);
    					}while(userName == null);
					
						story = new JFrame();
						story.setUndecorated(true);
						story.setSize(800, 600);                
						Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        				story.setBounds((screenSize.width-800)/2, (screenSize.height-600)/2, 800, 600);
						story.setVisible(true);
						try{
							String lineRead = new String();
							BufferedReader br = new BufferedReader(new FileReader(new File("drk/menu/story.txt")));
							JTextArea storymode = new JTextArea();
							for(int i = 0; i < 11; i++){
								lineRead = br.readLine() + "\n";
								storymode.append(lineRead);
							}
							JPanel storyPanel = new JPanel();
							storyPanel.setLayout(new GridBagLayout());
							GridBagConstraints sconstraint = new GridBagConstraints();
							JLabel imgLabel = new JLabel(new ImageIcon("drk/menu/Mad.jpg"));
							
							storymode.setEnabled(false);
							storymode.setLineWrap(true);
							storymode.setMargin(new Insets(30,30,30,30));
							storymode.setForeground(Color.white);
							storymode.setBackground(Color.black);
							Font font = new Font("Serif", Font.ITALIC, 20);
        					storymode.setFont(font);
        					storymode.setEditable(false);
        					
        					sconstraint.fill = GridBagConstraints.HORIZONTAL;
							sconstraint.weighty = 3.0;   
							sconstraint.anchor = GridBagConstraints.PAGE_END; 
							sconstraint.gridx = 1;     
							sconstraint.gridwidth = 4;  
							sconstraint.gridy = 1;
			
							storyPanel.add(imgLabel, sconstraint);
			
							sconstraint.weighty = 7.0;  
							sconstraint.gridx = 1;    
							sconstraint.gridwidth = 4;
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
                		//File file = 
                			
                		//	gameFile.getSelectedFile().getName();
                		//drk.KarnaughLog.log("Opening " +file.getName());
  //              		log.append("Opening: " + file.getName() + "." + newline);
    //            		int index = file.getName().lastIndexOf('.'); //Gets just the filename without ext.
      //          		String fileName = file.getName();//.substring(0, index);
                		KarnaughGame single = new KarnaughGame();
                		single.setSingleMapCampaign(single, gameFile.getSelectedFile().getPath());	
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
					System.exit(0);
				}
			}
		);
		
		//System.out.println(super.onoff());
		resumeB = new JButton("Resume Game");
		resumeB.setEnabled(false);
		//if(1 == 1)
			resumeB.setEnabled(true);
		resumeB.setVerticalTextPosition(AbstractButton.CENTER);
		resumeB.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){	
					System.out.println("Resuming the game");
					//frameVisible(1);
				}
			}
		);
			
		panelG.add(rankB);
		panelG.add(customB);
		panelG.add(resumeB);      
 		panelG.add(exitB);
 		
		frame.setContentPane(PanelF);
		
		log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
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
		JMenuItem scoresItem = new JMenuItem("View High Scores");
		customItem.setMnemonic('S');
		file.add(scoresItem);	
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic('X');
		file.add(exitItem);
		
		JMenu options = new JMenu("Options");
		options.setMnemonic('O');
		JMenuItem saveGame = new JMenuItem("Save Game");
		saveGame.setMnemonic('S');
		options.add(saveGame);
		JMenuItem loadGame = new JMenuItem("Load Game");
		options.add(loadGame);
		
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
					
						//Adopted from http://www.java2s.com/Code/Java/Swing-JFC/Informationdialogwithcustomizedlogo.htm
    					do{
    						userName = (String)JOptionPane.showInputDialog(new JFrame(),
        										"Please enter your name",
        										"Player Name", JOptionPane.INFORMATION_MESSAGE);
    											System.out.println("User's name is: " + userName);
    					}while(userName == null);
    					
						story = new JFrame();
						story.setUndecorated(true);
						story.setSize(800, 600);                
						Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        				story.setBounds((screenSize.width-800)/2, (screenSize.height-600)/2, 800, 600);
						story.setVisible(true);
						try{
							String lineRead = new String();
							BufferedReader br = new BufferedReader(new FileReader(new File("drk/menu/story.txt")));
							JTextArea storymode = new JTextArea();
							for(int i = 0; i < 11; i++){
								lineRead = br.readLine() + "\n";
								storymode.append(lineRead);
							}
							JPanel storyPanel = new JPanel();
							storyPanel.setLayout(new GridBagLayout());
							GridBagConstraints sconstraint = new GridBagConstraints();
							JLabel imgLabel = new JLabel(new ImageIcon("drk/menu/Mad.jpg"));
							
							storymode.setEnabled(false);
							storymode.setLineWrap(true);
							storymode.setMargin(new Insets(30,30,30,30));
							storymode.setForeground(Color.white);
							storymode.setBackground(Color.black);
							Font font = new Font("Serif", Font.ITALIC, 20);
        					storymode.setFont(font);
        					storymode.setEditable(false);
        					
        					sconstraint.fill = GridBagConstraints.HORIZONTAL;
							sconstraint.weighty = 3.0;   
							sconstraint.anchor = GridBagConstraints.PAGE_END;
							sconstraint.gridx = 1;   
							sconstraint.gridwidth = 4; 
							sconstraint.gridy = 1;
			
							storyPanel.add(imgLabel, sconstraint);
			
							sconstraint.weighty = 7.0; 
							sconstraint.gridx = 1;  
							sconstraint.gridwidth = 4; 
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
                		//int index = file.getName().lastIndexOf('.'); //Gets just the filename without ext.
                		String fileName = file.getPath();	
                		KarnaughGame single = new KarnaughGame();
                		single.setSingleMapCampaign(single, fileName);	
                			
            		} 
            		else
                		log.append("Open command cancelled by user." + newline);
            		log.setCaretPosition(log.getDocument().getLength());
					
				}
			}
		);
		
		scoresItem.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){	
					System.out.println("Showing the high scores table");
					try{
						JEditorPane url = new JEditorPane();
						url.setPage("http://soapforge.com/rbs/KarnaughGame/highScores.php");
						url.setVisible(true);
						JFrame urlF = new JFrame("Dr. Karnaugh's Laboratory High Scores");
						urlF.add(url);
						urlF.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        				urlF.setVisible(true);
        				urlF.setResizable(false);
        				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        				urlF.setBounds((screenSize.width-800), (screenSize.height-600), 800, 600);
        				urlF.setSize(400, 400);
					}
					catch(IOException io){
						io.printStackTrace();
					}
				}
			}
		);
		
		exitItem.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){	
					System.out.println("Exiting the game");
					System.exit(0);
				}
			}
		);
		
		saveGame.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){	
					System.out.println("Saving the game");
				}
			}
		);
		
		loadGame.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){	
					System.out.println("Loading the game");
				}
			}
		);
		
		gameHelp.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					System.out.println("Loading the help file");
					
					try{
						Desktop desktop = Desktop.getDesktop();
						desktop.open(new File("KarnaughHelp/Test.chm"));
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
		menubar.add(options);
		menubar.add(help);
		
		return menubar;
	}
	
	public void GameGUI() {
        
		//this is never used, so it threw a warning
  //      JPanel foregroundPanel = new JPanel(new BorderLayout(100, 100));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setJMenuBar(createMenuBar());
		
        //Display the window.
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setResizable(false);
    }
	
	public static void main(String[] args)
	{
		Menu Test = new Menu();
		Test.GameGUI();
	}
}