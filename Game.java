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

public class Game {
	JFrame window;
	JPanel titleNamePanel, startButtonPanel, mainTextPanel, choiceButtonPanel, playerPanel;
	JLabel titleNameLabel, playerHpLabel, playerWeaponLabel;
	JButton startButton, choice1, choice2, choice3, choice4;
	JTextArea mainTextArea;
	TitleScreenHandler titleScreenHandler = new TitleScreenHandler();
	ChoiceHandler choiceHandler = new ChoiceHandler();
	
	Font titleFont = new Font("Times New Roman", Font.PLAIN, 80);
	Font startFont = new Font("Times New Roman", Font.PLAIN, 40);
	Font normalFont = new Font("Times New Roman", Font.PLAIN, 25);
	
	int playerHp;
	int playerBaseHp;
	String weapon;
	int enermyHp;
	String position;
	
	public static void main(String[] args){
		new Game();
	}
	
	public Game(){
		window = new JFrame();
		window.setSize(800, 600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setBackground(Color.black);
		window.setLayout(null);
		
		titleNamePanel = new JPanel();
		titleNamePanel.setBounds(50, 100, 690, 150);
		titleNamePanel.setBackground(Color.black);
		
		titleNameLabel = new JLabel("CAT ADVENTURE");
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
		startButton.addActionListener(titleScreenHandler);
		
		startButtonPanel.add(startButton);
		titleNamePanel.add(titleNameLabel);
		window.add(startButtonPanel);
		window.add(titleNamePanel);
		
		window.setVisible(true); 
		window.setResizable(false);
	}

	public void createGameScreen(){
		titleNamePanel.setVisible(false);
		titleNameLabel.setVisible(false);
		startButtonPanel.setVisible(false);
		
		playerPanel = new JPanel();
		playerPanel.setBounds(95, 15, 597, 50);
		playerPanel.setBackground(Color.black);
		playerPanel.setLayout(new GridLayout(1,2));
		window.add(playerPanel);
		
		playerHpLabel = new JLabel("");
		playerHpLabel.setForeground(Color.white);
		playerHpLabel.setFont(normalFont);
		playerPanel.add(playerHpLabel);
		
		playerWeaponLabel = new JLabel(); 
		playerWeaponLabel.setForeground(Color.white);
		playerWeaponLabel.setFont(normalFont);
		playerPanel.add(playerWeaponLabel);
		
		mainTextPanel = new JPanel();
		mainTextPanel.setBounds(50, 100, 686, 250);
		mainTextPanel.setBackground(Color.black);
		window.add(mainTextPanel);
		
		mainTextArea = new JTextArea();
		mainTextArea.setBounds(200, 100, 680, 250);
		mainTextArea.setBackground(Color.black);
		mainTextArea.setForeground(Color.white);
		mainTextArea.setFont(normalFont);
		mainTextArea.setLineWrap(true);
		mainTextArea.setWrapStyleWord(true);
		mainTextArea.setEditable(false);
		mainTextPanel.add(mainTextArea);
		
		choiceButtonPanel = new JPanel();
		choiceButtonPanel.setBounds(240, 350, 306, 150);
		choiceButtonPanel.setBackground(Color.black);
		choiceButtonPanel.setLayout(new GridLayout(4,1));
		window.add(choiceButtonPanel);
		
		choice1 = new JButton();
		choice1.setBackground(Color.black);
		choice1.setForeground(Color.white);
		choice1.setFont(normalFont);
		choiceButtonPanel.add(choice1);
		choice1.setFocusable(false);
		choice1.addActionListener(choiceHandler);
		choice1.setActionCommand("c1");
		
		choice2 = new JButton();
		choice2.setBackground(Color.black);
		choice2.setForeground(Color.white);
		choice2.setFont(normalFont);
		choiceButtonPanel.add(choice2);
		choice2.setFocusable(false);
		choice2.addActionListener(choiceHandler);
		choice2.setActionCommand("c2");
		
		choice3 = new JButton();
		choice3.setBackground(Color.black);
		choice3.setForeground(Color.white);
		choice3.setFont(normalFont);
		choiceButtonPanel.add(choice3);
		choice3.setFocusable(false);
		choice3.addActionListener(choiceHandler);
		choice3.setActionCommand("c3");
		
		choice4 = new JButton();
		choice4.setBackground(Color.black);
		choice4.setForeground(Color.white);
		choice4.setFont(normalFont);
		choiceButtonPanel.add(choice4);
		choice4.setFocusable(false);
		choice4.addActionListener(choiceHandler);
		choice4.setActionCommand("c4");
		
		playerSetup();
		window.setVisible(true);
		window.setResizable(false);
	}
	
	public class TitleScreenHandler implements ActionListener{
		
		public void actionPerformed(ActionEvent event){
			createGameScreen();	
		}
	}
	
	public class ChoiceHandler implements ActionListener{
		
		public void actionPerformed(ActionEvent event){
			String yourChoice = event.getActionCommand();
			switch(position){
			case "openGame":
				switch(yourChoice){
				case "c1":meowBravely();break;
				case "c2":meowSadly();break;
				case "c3":meowDeterminedly();break;
				case "c4":meow_1();break;
				} break;
			case "meowBravely":
				switch(yourChoice){
				case "c1":meow_2();break;
				case "c2":jumpOverTheGate();break;
				case "c3":attackGuard();break;
				case "c4":sneak();break;
				}break;
			case "meowSadly":
				switch(yourChoice){
				case "c1":meow_2();break;
				case "c2":jumpOverTheGate();break;
				case "c3":attackGuard();break;
				case "c4":sneak();break;
				} break;
			case "meowDeterminedly":
				switch(yourChoice){
				case "c1":meow_2();break;
				case "c2":jumpOverTheGate();break;
				case "c3":attackGuard();break;
				case "c4":sneak();break;
				} break;
			case "meow_1":
				switch(yourChoice){
				case "c1":meow_2();break;
				case "c2":jumpOverTheGate();break;
				case "c3":attackGuard();break;
				case "c4":sneak();break;
				} break;
			case "attackGuard":
				switch(yourChoice){
				case "c1":meow_2();break;
				case "c2":jumpOverTheGate();break;
				case "c3":attackGuard();break;
				case "c4":sneak();break;
				} break;		
			case "sneak":
				switch(yourChoice){
				case "c1":meow_2();break;
				case "c2":jumpOverTheGate();break;
				case "c3":attackGuard();break;
				case "c4":sneak();break;
				} break;
			case "meow_2":
				switch(yourChoice){
				case "c1":accept();break;
				case "c2":jumpOverTheGate();break;
				case "c3":sneak();break;
				case "c4":leave();break;
				} break;
			case "accept":
				switch(yourChoice){
				case "c4":leave();break;
				} break;
			case "jumpOverTheGate":
				switch(yourChoice){
				case "c1":lookAround();break;
				case "c2":drinkFromTheRiver();break;
				case "c3":runBack();break;
				case "c4":rest();break;
				} break;
			case "leave":
				switch(yourChoice){
				case "c1":lookAround();break;
				case "c2":drinkFromTheRiver();break;
				case "c3":runBack();break;
				case "c4":rest();break;
				} break;
			case "drinkFromTheRiver":
				switch(yourChoice){
				case "c1":lookAround();break;
				case "c2":drinkFromTheRiver();break;
				case "c3":runBack();break;
				case "c4":rest();break;
				} break;
			case "runBack":
				switch(yourChoice){
				case "c1":lookAround();break;
				case "c2":drinkFromTheRiver();break;
				case "c3":runBack();break;
				case "c4":rest();break;
				} break;
			case "rest":
				switch(yourChoice){
				case "c1":lookAround();break;
				case "c2":drinkFromTheRiver();break;
				case "c3":runBack();break;
				case "c4":rest();break;
				} break;
			case "lookAround":
				switch(yourChoice){
				case "c1":pickUpTheMap();break;
				case "c2":leaveItBe();break;
				} break;
			case "pickUpTheMap":
				switch(yourChoice){
				case "c1":lookAround();break;
				case "c2":drinkFromTheRiver();break;
				case "c3":runBack();break;
				case "c4":rest();break;
				} break;
			case "leaveItBe":
				switch(yourChoice){
				case "c1":lookAround();break;
				case "c2":drinkFromTheRiver();break;
				case "c3":runBack();break;
				case "c4":rest();break;
				} break;
			}
		}
	}
	
	
	public void playerSetup(){
		enermyHp = 15;
		playerHp = 10;
		playerBaseHp = 10;
		weapon = "Claw";
		playerHpLabel.setText("HP: " + playerHp);
		playerWeaponLabel.setText("Weapon: " + weapon);
		
		
		openGame();
	}
	
	public void openGame(){
		position = "openGame";
		mainTextArea.setText("You are a cat, you just woke up from a nap and found yourself in a new world. Will you able to find a way to go back home?");
		choice1.setText("Meow bravely");
		choice2.setText("Meow sadly");
		choice3.setText("Meow determinedly");
		choice4.setText("Meow");
	}
	
	public void meowBravely(){
		position = "meowBravely";
		mainTextArea.setText("You meow bravely, ready for the adventure ahead of you. You look around and see a town gate. There's a guard watching.\n" + "\nWhat do you want to do?");
		choice1.setText("Meow");
		choice2.setText("Jump over the gate");
		choice3.setText("Attack the guard");
		choice4.setText("Sneak");
	}	
	
	public void meowSadly(){
		position = "meowSadly";
		mainTextArea.setText("You meow sadly. As if you are unsure about the future. You look around and see a town gate. There's a guard watching.\n" + "\nWhat do you want to do?");
		choice1.setText("Meow");
		choice2.setText("Jump over the gate");
		choice3.setText("Attack the guard");
		choice4.setText("Sneak");
	}
	
	public void meowDeterminedly(){
		position = "meowDeterminedly";
		mainTextArea.setText("You meow and it fills you with determination. You look around and see a town gate. There's a guard watching.\n" + "\nWhat do you want to do?");
		choice1.setText("Meow");
		choice2.setText("Jump over the gate");
		choice3.setText("Attack the guard");
		choice4.setText("Sneak");
	}
	
	public void meow_1(){
		position = "meow_1";
		mainTextArea.setText("You meow. You look around and see a town gate. There's a guard watching.\n" + "\nWhat do you want to do?");
		choice1.setText("Meow");
		choice2.setText("Jump over the gate");
		choice3.setText("Attack the guard");
		choice4.setText("Sneak");
	}
	
	public void meow_2(){
		position = "meow_2";
		mainTextArea.setText("You meow. This alert the guard.\n" + "\nSir Meowsalot: Greetings young traveller to the Meow kingdom. Unfortunately I can't let you in because I don't know you yet. Perhaps you could earn our trust by helping with the mouse problem\n" + "\nYou need to catch us 5 mouses to enter the kingdom");
		choice1.setText("Accept");
		choice2.setText("Attack the guard");
		choice3.setText("Sneak");
		choice4.setText("Leave");
	}
	
	public void jumpOverTheGate(){
		position = "jumpOverTheGate";
		mainTextArea.setText("You try to jump over the gate. In your head you will land graciously on the other side. But before you do anything. You got distracted and land face first. Your body then lost the balance and you roll down hill.\n" + "\nYou continue to roll until you reach a nearby river.");
		choice1.setText("Look around");
		choice2.setText("Drink from the river");
		choice3.setText("Run back");
		choice4.setText("Rest");
	}
	
	public void attackGuard(){
		position = "attackGuard";
		mainTextArea.setText("You use your " + weapon + " and attack the guard\n" + "\nSir Meowsalot: You little!\n" + "\nYou get hit back by the guard and receive 3 damage. What are you going to do next?");
		playerHp = playerHp - 3;
		playerHpLabel.setText("HP: " + playerHp);
		choice1.setText("Meow");
		choice2.setText("Jump over the gate");
		choice3.setText("Attack the guard");
		choice4.setText("Sneak");
	}
	
	public void sneak(){
		position = "sneak";
		mainTextArea.setText("You sneak behind the guard, but were quickly yank back.\n" + "\nSir Meowsalot: Bad Cat don't get to go to the kindom. What are you going to do?" );
		choice1.setText("Meow");
		choice2.setText("Jump over the gate");
		choice3.setText("Attack the guard");
		choice4.setText("Sneak");
	}
	
	public void accept(){
		position = "accept";
		mainTextArea.setText("You accept. This please the guard.\n" + "\nSir Meowsalot: Good to hear that. Now got to the river and complete your task dear travaler");
		choice1.setText("");
		choice2.setText("");
		choice3.setText("");
		choice4.setText("Leave");
	}

	public void leave(){
		position = "leave";
		mainTextArea.setText("You leave and walk to the river\n" + "\nWhat are you goint to do next?");
		choice1.setText("Look around");
		choice2.setText("Drink from the river");
		choice3.setText("Run back");
		choice4.setText("Rest");
	}
	
	public void lookAround(){
		position = "lookAround";
		mainTextArea.setText("You look around the river. Near your place there is a hidden map. Pick it up?");
		choice1.setText("Pick up the map");
		choice2.setText("Leave it be");
		choice3.setText("");
		choice4.setText("");
	}
	
	public void drinkFromTheRiver(){
		position = "drinkFromTheRiver";
		mainTextArea.setText("You deciede to drink some water from the river. You gain 1 Hp\n" + "\nWhat are you going to do next?");
		choice1.setText("Look around");
		choice2.setText("Drink from the river");
		choice3.setText("Run back");
		choice4.setText("Rest");
	}
	
	public void runBack(){
		position = "runBack";
		mainTextArea.setText("You decided to run back to the gate");
		choice1.setText("Meow");
		choice2.setText("Jump over the gate");
		choice3.setText("Attack the guard");
		choice4.setText("Sneak");
	}
	
	public void rest(){
		position = "rest";
		mainTextArea.setText("You say it's nap time. You lay down in the river bank and close your eyes. You gain back all your Hp!\n" + "\nWhat is your next action?");
		playerHp = playerBaseHp;
		playerHpLabel.setText("HP: " + playerHp);
		choice1.setText("Look around");
		choice2.setText("Drink from the river");
		choice3.setText("Run back");
		choice4.setText("Rest");
	}
	
	public void pickUpTheMap(){
		position = "pickUpTheMap";
		mainTextArea.setText("You pick up the map. The map shows that ahead of you. On the left, in the deep forest, there is a secrete treasure\n" + "\nOn the right there is a small village. You see a big red circle around the village that mark [DANGER DO NOT COME]");
		choice1.setText("Go to the forest");
		choice2.setText("Go to the village");
		choice3.setText("Go back to the gate");
		choice4.setText("Stay");
	}
	
	public void leaveItBe(){
		position = "leaveItBe";
		mainTextArea.setText("Why be hurry? you think. You leave the map as it is.");
		choice1.setText("Look around");
		choice2.setText("Drink from the river");
		choice3.setText("Run back");
		choice4.setText("Rest");
	}
	
	public void forest(){
		position = "forest";
		mainTextArea.setText("You want to check the visit the forest first. You think to yourself there must be a reason the village was mark danger.\n" + "\nAfter walking for a while. You arrive at the forest, where the treasure are mark");
		choice1.setText("Search");
		choice2.setText("Look around");
		choice3.setText("Run back");
		choice4.setText("Meow");
	}
	
	public void village(){
		position = "village";
		mainTextArea.setText("Life is an adventure and you want the thrill. And you want it now. You walk, no...\n" + "\nYou ran to the village. Danger? it is in your middle name, it is in your blood. There you see a figure...");
		choice1.setText("Meow");
		choice2.setText("Attack");
		choice3.setText("Sneak");
		choice4.setText("Leave");
	}
	
	
	
	
	
	
	
	
	
	
	
		

}
