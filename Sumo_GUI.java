import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JLabel;	


public class Sumo_GUI {
	JFrame window;
	JPanel titleNamePanel, startButtonPanel, mainTextPanel, choiceButtonPanel, playerPanel;
	JLabel titleNameLabel, playerHpLabel, playerWeaponLabel;
	JButton startButton, choice1, choice2, choice3, choice4;
	JTextArea mainTextArea;
	//TitleScreenHandler titleScreenHandler = new TitleScreenHandler();
	//ChoiceHandler choiceHandler = new ChoiceHandler();
	
	Font titleFont = new Font("Times New Roman", Font.PLAIN, 80);
	Font startFont = new Font("Times New Roman", Font.PLAIN, 40);
	Font normalFont = new Font("Times New Roman", Font.PLAIN, 25);
	
	int playerHp;
	int playerBaseHp;
	String weapon;
	int enermyHp;
	String position;


    public static void main(String[] args){
		new Sumo_GUI();
	}

	public Sumo_GUI(){
		window = new JFrame();
		window.setSize(800, 600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setBackground(Color.black);
		window.setLayout(null);
		
		titleNamePanel = new JPanel();
		titleNamePanel.setBounds(50, 100, 690, 150);
		titleNamePanel.setBackground(Color.black);
		
		titleNameLabel = new JLabel("Sumo GUI");
		titleNameLabel.setForeground(Color.white);
		titleNameLabel.setFont(titleFont);
		
		startButtonPanel = new JPanel();
		startButtonPanel.setBounds(293, 400, 200, 100);
		startButtonPanel.setBackground(Color.black);
		
		startButton = new JButton("START");
		startButton.setBackground(Color.black);
		startButton.setForeground(Color.white);
		startButton.setFont(startFont);
		startButton.setFocusable(false);
		//startButton.addActionListener(titleScreenHandler);
		
		startButtonPanel.add(startButton);
		titleNamePanel.add(titleNameLabel);
		window.add(startButtonPanel);
		window.add(titleNamePanel);
		
		window.setVisible(true); 
		window.setResizable(false);
	}
}
