import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

import enigma.console.TextAttributes;
import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

public class Game {

	public TextMouseListener tmlis;

	// ------ Standard variables for mouse ------
	public static int mousepr; // mouse pressed?
	public static int mousex; // mouse text coords.
	public static int mousey;
	// ----------------------------------------------------

	public KeyListener klis;

	// ------ Standard variables for keyboard ------
	public int keypr; // key pressed?
	public int rkey; // key (for press/release)
	// ----------------------------------------------------

	enigma.console.Console console = Enigma.getConsole("HELIX SNAKE", 85, 30, 21, 4);

	public static String choice = "";

	static String direction;

	String nickname;

	DoubleLinkedList scoreTable;

	MultiLinkedList mll = new MultiLinkedList();

	// create objects to call methods from other classes

	Aminoacid a = new Aminoacid();

	Screen screen = new Screen();

	static boolean checkCodons = true;

	boolean playSounds = true;

	static String[] pointCodons;

	int x = 20;
	int y = 100;

	public Game() throws InterruptedException, IOException {

		// write data which taken from "aminoacids.txt" file to Multi Linked List
		a.takeCodonsFromTxt();

		while (true) {

			scoreTable = new DoubleLinkedList();

			// write high scores ehich taken from "HighScoreTable.txt" file to
			// scoreTable(DLL)
			addScoresFromTxt();

			// mouse controlled menu
			menu();

			sound(playSounds, 1);

			long time1 = 0;
			long time2 = System.currentTimeMillis();

			screen.score = 0;
			screen.level = 0;

			screen.continueGame = true;

			screen.firstGenerate = true;

			checkCodons = true;

			// take input(nickname) from user
			takeInput();

			screen.generateGameArea();

			// generate simple snake(SLL)
			Snake s = new Snake();

			// assign each node of snake(SLL) to two-dimensional array
			s.placeSnake();

			screen.generateRandomLetters();

			direction = "right";

			MultiLinkedList.count = 0;

			int count = 0;

			// array keeps codons and points to print when user plays game and gets codon
			// points
			pointCodons = new String[100];

			while (Screen.continueGame) {

				// when snake eats 3 letter search method check last three letter and increases
				// score
				if (checkCodons == true) {

					Aminoacid.mll.search(s.snake.reverseTrio());

					checkCodons = false;

				}

				s.placeSnake();

				screen.printScreen();

				screen.printCodons();

				if (Snake.snake.size() % 9 == 2) {

					pointCodons[0] = "      ";
					pointCodons[1] = "      ";
					pointCodons[2] = "      ";

				}

				keyboardControl();

				if (direction == "left")
					s.moveLeft();
				if (direction == "right")
					s.moveRight();
				if (direction == "up")
					s.moveUp();
				if (direction == "down")
					s.moveDown();

				time1 = System.currentTimeMillis();

				if (time1 - time2 >= 1000) {

					count++;
					console.getTextWindow().setCursorPosition(65, 24);
					System.out.println("Time : " + count);

					// easy mode : x = 20, y = 100
					// normal mode : x = 10, y= 50
					// hard mode : x = 2, y = 10

					if (count % x == 0) {

						screen.generateWall();
						Screen.level++;

					}
					time2 = time1;
				}

				Thread.sleep(y);

				if (screen.continueGame)
					screen.clearConsole(64, 29);

			}

			// stop playing sounds
			sound(playSounds, 2);

			console.setTextAttributes(new TextAttributes(Color.WHITE, Color.RED));
			console.getTextWindow().setCursorPosition(25, 10);
			System.out.print("Game Over!");
			console.getTextWindow().setCursorPosition(25, 13);
			System.out.print("Your Score : " + screen.score);
			console.setTextAttributes(new TextAttributes(Color.WHITE, Color.BLACK));

			Thread.sleep(2000);

			if (screen.score > lowestHighScore)
				scoreTable.Add(nickname, screen.score);

			// after playing game, write updated high score list to "HighScoreTable.txt" file
			writeScoresToTxt();

			screen.clearConsole(84, 29);

			// print High Scores(Top-10 list)
			scoreTable.Display();

			while (true) {

				console.getTextWindow().setCursorPosition(0, 15);
				System.out.println("| Restart |  | Back to Menu |  | Exit |");
				mouseControl();
				if (choice.equals("start"))
					break;
				else if (choice.equals("go to menu")) {

					screen.clearConsole(64, 29);
					break;

				} else if (choice.equals("exit"))
					System.exit(0);

			}

			screen.clearConsole(64, 29);

		}

	}

	int lowestHighScore;
	String[] nicknames = new String[10];

	public void addScoresFromTxt() throws IOException, InterruptedException {

		int nameCounter = 0;

		try {

			File file = new File("HighScoreTable.txt");

			BufferedReader br = new BufferedReader(new FileReader(file));

			String st;
			String temp[];

			while ((st = br.readLine()) != null) {

				temp = st.split(";");
				scoreTable.Add(temp[0], Integer.valueOf(temp[1]));
				nicknames[nameCounter++] = temp[0];
				lowestHighScore = Integer.valueOf(temp[1]);

			}

		} catch (Exception e) {

			screen.changeColor(Color.GREEN);
			console.getTextWindow().setCursorPosition(10, 27);
			System.out.println("File is being created for the first time");
			Thread.sleep(3000);
			screen.changeColor(Color.WHITE);
		}
	}

	DoubleLinkedList d = new DoubleLinkedList();

	public void writeScoresToTxt() {

		try {

			DLLNode temp = DoubleLinkedList.head;

			File fout = new File("HighScoreTable.txt");
			FileOutputStream fos = new FileOutputStream(fout);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

			int scoreNumber = 1;
			while (temp != null && scoreNumber <= 10) {

				bw.write((temp.getNickname() + ";" + temp.getData()));
				bw.newLine();

				temp = temp.getNext();
				scoreNumber++;
			}

			bw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// keyboard control method for movement of snake
	public String keyboardControl() throws InterruptedException {

		// ------ Standard code for mouse and keyboard ------ Do not change

		klis = new KeyListener() {

			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (keypr == 0) {
					keypr = 1;
					rkey = e.getKeyCode();
				}
			}

			public void keyReleased(KeyEvent e) {
			}
		};
		console.getTextWindow().addKeyListener(klis);
		// ----------------------------------------------------

		boolean horizontal;

		if (direction == "left" || direction == "right") {
			horizontal = true;
		} else
			horizontal = false;

		while (true) {

			if (keypr == 1) { // if keyboard button pressed

				if (horizontal == true) {

					if (direction == "left") {

						if (rkey == KeyEvent.VK_LEFT)
							direction = "down";
						if (rkey == KeyEvent.VK_RIGHT)
							direction = "up";

					}
					if (direction == "right") {

						if (rkey == KeyEvent.VK_LEFT)
							direction = "up";
						if (rkey == KeyEvent.VK_RIGHT)
							direction = "down";

					}

					horizontal = false;

				} else {

					if (direction == "up") {

						if (rkey == KeyEvent.VK_LEFT)
							direction = "left";
						if (rkey == KeyEvent.VK_RIGHT)
							direction = "right";

					}
					if (direction == "down") {

						if (rkey == KeyEvent.VK_LEFT)
							direction = "right";
						if (rkey == KeyEvent.VK_RIGHT)
							direction = "left";

					}

					horizontal = true;

				}

				keypr = 0; // last action

			}

			Thread.sleep(20);

			return direction;
		}

	}

	// mouse control method for menu
	public String mouseControl() throws InterruptedException {

		// ------ Standard code for mouse ------

		tmlis = new TextMouseListener() {
			public void mouseClicked(TextMouseEvent arg0) {
			}

			public void mousePressed(TextMouseEvent arg0) {
				if (mousepr == 0) {
					mousepr = 1;
					mousex = arg0.getX();
					mousey = arg0.getY();
				}
			}

			public void mouseReleased(TextMouseEvent arg0) {
			}
		};
		console.getTextWindow().addTextMouseListener(tmlis);

		// ----------------------------------------------------

		while (true) {

			if (mousepr == 1) { // if mouse button pressed

				if (mousex >= 5 && mousex <= 18 && mousey == 3) //
					choice = "start";
				if (mousex >= 0 && mousex <= 11 && mousey == 15)
					choice = "start";
				if (mousex >= 5 && mousex <= 15 && mousey == 5) //
					choice = "options";
				if (mousex >= 5 && mousex <= 18 && mousey == 4)
					choice = "changeDifficulty";
				if (mousex >= 5 && mousex <= 13 && mousey == 6)
					choice = "sound-On/Off";
				if (mousex >= 5 && mousex <= 20 && mousey == 10)
					choice = "options -> menu";
				if (mousex >= 5 && mousex <= 20 && mousey == 7) //
					choice = "instructions";
				if (mousex >= 0 && mousex <= 15 && mousey == 17)
					choice = "instructions -> menu";
				if (mousex >= 5 && mousex <= 12 && mousey == 9) //
					choice = "showHighScores";
				if (mousex >= 10 && mousex <= 25 && mousey == 16) //
					choice = "highScores -> menu";
				if (mousex >= 5 && mousex <= 12 && mousey == 11) //
					choice = "exit";
				if (mousex >= 31 && mousex <= 37 && mousey == 15)
					choice = "exit";
				if (mousex >= 39 && mousex <= 47 && mousey == 20)
					choice = "go to menu";
				if (mousex >= 13 && mousex <= 29 && mousey == 15)
					choice = "go to menu";

				mousepr = 0; // last action

			}

			Thread.sleep(20);
			return choice;
		}
	}

	boolean showLogo = true;

	int counter = 0;
	int counter1 = 0;

	public void menu() throws InterruptedException, IOException {

		if (showLogo) {
			console.getTextWindow().setCursorPosition(0, 10);
			System.out.println(" __ __    ___  _      ____  __ __       _____ ____    ____  __  _    ___ \r\n"
					+ "|  T  T  /  _]| T    l    j|  T  T     / ___/|    \\  /    T|  l/ ]  /  _]\r\n"
					+ "|  l  | /  [_ | |     |  T |  |  |    (   \\_ |  _  YY  o  ||  ' /  /  [_ \r\n"
					+ "|  _  |Y    _]| l___  |  | l_   _j     \\__  T|  |  ||     ||    \\ Y    _]\r\n"
					+ "|  |  ||   [_ |     T |  | |     |     /  \\ ||  |  ||  _  ||     Y|   [_ \r\n"
					+ "|  |  ||     T|     | j  l |  |  |     \\    ||  |  ||  |  ||  .  ||     T\r\n"
					+ "l__j__jl_____jl_____j|____j|__j__|      \\___jl__j__jl__j__jl__j\\_jl_____j\r\n"
					+ "                                                                         ");

			console.getTextWindow().setCursorPosition(30, 20);
			System.out.println("Click to | Start |");

			while (!choice.equals("go to menu"))
				mouseControl();

			sound(playSounds, 0);
			showLogo = false;

			screen.clearConsole(25, 100);
		}

		while (!choice.equals("start")) {

			screen.clearConsole(10, 50);

			console.getTextWindow().setCursorPosition(5, 3);
			System.out.println("| Start Game |");

			console.getTextWindow().setCursorPosition(5, 5);
			System.out.println("| Options |");

			console.getTextWindow().setCursorPosition(5, 7);
			System.out.println("| Instructions |");

			console.getTextWindow().setCursorPosition(5, 9);
			System.out.println("| High Scores |");

			console.getTextWindow().setCursorPosition(5, 11);
			System.out.println("| Exit |");

			mouseControl();

			if (choice.equals("options")) {

				sound(playSounds, 0);

				String[] difficulties = { "Easy", "Normal", "Hard" };
				String[] sound = { "On", "Off" };

				screen.clearConsole(21, 12);

				while (true) {

					console.getTextWindow().setCursorPosition(5, 4);
					System.out.println("| Difficulty | : " + difficulties[counter] + "  ");

					console.getTextWindow().setCursorPosition(5, 6);
					System.out.println("| Sound | : " + sound[counter1] + " ");

					console.getTextWindow().setCursorPosition(5, 10);
					System.out.println("| Back to Menu |");

					mouseControl();

					if (choice.equals("changeDifficulty")) {

						sound(playSounds, 0);

						choice = "";

						counter++;

						if (counter == 3)
							counter = 0;

						if (counter == 0) {

							x = 20;
							y = 100;

						}
						if (counter == 1) {

							x = 10;
							y = 50;

						}
						if (counter == 2) {

							x = 2;
							y = 10;

						}

					}
					if (choice.equals("sound-On/Off")) {

						sound(playSounds, 0);

						choice = "";

						counter1++;
						if (counter1 == 2)
							counter1 = 0;

						if (counter1 == 0) {

							playSounds = true;
							sound(playSounds, 0);

						}

						if (counter1 == 1)
							playSounds = false;

					}
					if (choice.equals("options -> menu")) {

						sound(playSounds, 0);
						break;

					}

				}
			}
			if (choice.equals("instructions")) {

				sound(playSounds, 0);

				screen.clearConsole(10, 20);
				console.getTextWindow().setCursorPosition(0, 0);
				System.out.println();
				console.getTextWindow().setCursorPosition(0, 1);
				System.out.println(
						"The game is played controlling a moving snake in 25*60 area. There are four letters which are A (Adenine),\r\n"
								+ "C (Cytosine), G (Guanine) and T (Thymine) in the game area. The snake starts with 3 letters assigned\r\n"
								+ "randomly out of four letter (A, C, G, T). When the snake eats these letters, it becomes longer. If the snake\r\n"
								+ "bumps into a wall or its own body the game is over.\r\n" + "- Amino Acids\r\n"
								+ "There are twenty amino acids found in proteins. Amino acids are represented with codons. Each codon\r\n"
								+ "contains 3 letter of the DNA coding units T, C, A and G. All 64 possible 3-letter combinations of T, C, A and\r\n"
								+ "G are used either to encode one of these amino acids or as one of the three stop codons that signals the\r\n"
								+ "end of a sequence.");
				System.out.println();
				System.out.println("| Back to Menu |");
				while (true) {

					mouseControl();
					if (choice.equals("instructions -> menu")) {

						screen.clearConsole(32, 100);
						sound(playSounds, 0);
						break;
					}

				}
			}
			if (choice.equals("showHighScores")) {

				sound(playSounds, 0);

				screen.clearConsole(21, 10);

				scoreTable.Display();

				console.getTextWindow().setCursorPosition(10, 16);
				System.out.println("| Back to Menu |");

				while (true) {

					mouseControl();
					if (choice.equals("highScores -> menu")) {

						screen.clearConsole(32, 100);
						sound(playSounds, 0);
						break;
					}

				}

			}
			if (choice.equals("exit"))
				System.exit(0);

		}

		sound(playSounds, 0);
		choice = "";

		screen.clearConsole(10, 40);

	}

	// method for taking input(nickname) from user
	public void takeInput() throws InterruptedException {

		boolean sameName;

		Scanner input = new Scanner(System.in);

		// if entered nickname is taken, give error and take input(nickname) again
		do {

			sameName = false;

			console.getTextWindow().setCursorPosition(10, 11);
			System.out.print("                   ");

			console.getTextWindow().setCursorPosition(10, 5);
			System.out.print("Please Enter Nickname");
			console.getTextWindow().setCursorPosition(10, 7);
			System.out.println("+-------------------+");
			console.getTextWindow().setCursorPosition(11, 8);
			System.out.print("                   |");
			console.getTextWindow().setCursorPosition(10, 9);
			System.out.println("+-------------------+");
			console.getTextWindow().setCursorPosition(10, 8);
			System.out.print("| ");

			nickname = input.next();

			for (int i = 0; i < nicknames.length; i++) {

				if (nickname.equalsIgnoreCase(nicknames[i]))
					sameName = true;

			}

			if (sameName) {

				console.getTextWindow().setCursorPosition(10, 11);
				System.out.print("Nickname has taken!");
				Thread.sleep(1000);

			}

		} while (sameName);

		input.close();

		screen.clearConsole(64, 29);

	}

	// method for playing sounds
	
	private Clip clip0;
	private Clip clip1;

	public void sound(Boolean playSounds, int x) {

		if (x == 0) {

			if (playSounds) {

				try {

					// Open an audio input stream.
					File soundFile = new File("click.wav"); // you could also get the sound file with an URL
					AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
					// Get a sound clip resource.
					clip0 = AudioSystem.getClip();
					// Open audio clip and load samples from the audio input stream.
					clip0.open(audioIn);
					clip0.start();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		}
		if (x == 1) {

			if (playSounds) {

				try {

					// Open an audio input stream.
					File soundFile = new File("ingame.wav"); // you could also get the sound file with an URL
					AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
					// Get a sound clip resource.
					clip1 = AudioSystem.getClip();
					// Open audio clip and load samples from the audio input stream.
					clip1.open(audioIn);
					clip1.start();

				} catch (Exception e) {

				}
			}

		}
		if (x == 2) {

			try {
				clip0.stop();
				clip1.stop();
			} catch (Exception e) {

			}

		}

	}

}
