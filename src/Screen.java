import java.awt.Color;

import enigma.console.TextAttributes;
import enigma.core.Enigma;

public class Screen {

	static boolean continueGame = true;

	static boolean firstGenerate = true;

	static int score = 0;
	static int level = 0;

	static char[][] array = new char[25][60];

	enigma.console.Console console = Enigma.getConsole("HELIX SNAKE", 90, 30, 20, 4);

	public void generateRandomLetters() {

		if (firstGenerate) {

			// generate 3 letter on game area at the beginning
			int random = 0, randomCoordinateX = 0, randomCoordinateY = 0;
			for (int i = 0; i < 3; i++) {

				random = (int) ((Math.random() * 4)) + 1;

				char letter = ' ';

				if (random == 1)
					letter = 'A';
				else if (random == 2)
					letter = 'C';
				else if (random == 3)
					letter = 'G';
				else if (random == 4)
					letter = 'T';

				do {

					randomCoordinateX = (int) (Math.random() * 21) + 2;
					randomCoordinateY = (int) (Math.random() * 56) + 2;

				} while (array[randomCoordinateX][randomCoordinateY] != ' ');

				array[randomCoordinateX][randomCoordinateY] = letter;

				firstGenerate = false;

			}
		} else {

			int random = 0, randomCoordinateX = 0, randomCoordinateY = 0;

			random = (int) ((Math.random() * 4)) + 1;

			char letter = ' ';

			if (random == 1)
				letter = 'A';
			else if (random == 2)
				letter = 'C';
			else if (random == 3)
				letter = 'G';
			else if (random == 4)
				letter = 'T';

			do {

				randomCoordinateX = (int) (Math.random() * 21) + 2;
				randomCoordinateY = (int) (Math.random() * 56) + 2;

			} while (array[randomCoordinateX][randomCoordinateY] != ' ');

			array[randomCoordinateX][randomCoordinateY] = letter;

		}

	}

	public void generateWall() {

		int randomCoordinateX = 0;
		int randomCoordinateY = 0;

		do {

			randomCoordinateX = (int) (Math.random() * 23) + 1;
			randomCoordinateY = (int) (Math.random() * 58) + 1;

		} while (array[randomCoordinateX][randomCoordinateY] != ' ');

		array[randomCoordinateX][randomCoordinateY] = '#';

	}

	public void generateGameArea() {

		for (int i = 0; i < 25; i++) {

			for (int j = 0; j < 60; j++) {

				array[i][j] = ' ';
				array[0][j] = '#';
				array[24][j] = '#';

			}
			array[i][0] = '#';
			array[i][59] = '#';

		}
		array[0][0] = '#';
		array[0][59] = '#';
		array[24][0] = '#';
		array[24][59] = '#';

	}

	// print letters, walls, score and level
	public void printScreen() {

		for (int i = 0; i < 25; i++) {

			for (int j = 0; j < 60; j++) {

				console.getTextWindow().setCursorPosition(j + 1, i + 1);
				if (array[i][j] == ' ')
					changeColor(Color.BLACK);
				else if (array[i][j] == 'A')
					changeColor(Color.YELLOW);
				else if (array[i][j] == 'C')
					changeColor(Color.GREEN);
				else if (array[i][j] == 'G')
					changeColor(Color.BLUE);
				else if (array[i][j] == 'T')
					changeColor(Color.RED);
				else if (array[i][j] == '#')
					changeColor(Color.LIGHT_GRAY);

				System.out.print(array[i][j]);

			}
			System.out.println();

			console.setTextAttributes(new TextAttributes(Color.WHITE, Color.BLACK));

			console.getTextWindow().setCursorPosition(65, 2);
			System.out.println("Score : " + score);

			console.getTextWindow().setCursorPosition(65, 22);
			System.out.println("Level : " + level);

		}

	}

	public void printCodons() {

		for (int i = 0; i < Game.pointCodons.length; i++) {

			if (Game.pointCodons[i] != null) {

				console.getTextWindow().setCursorPosition(67, i + 4);
				System.out.println(Game.pointCodons[i]);

			}

		}
	}

	public void clearConsole(int x, int y) {

		for (int i = 0; i < y; i++) {

			for (int j = 0; j < x; j++) {

				console.getTextWindow().setCursorPosition(j, i);
				System.out.println(" ");

			}

		}

	}

	public void changeColor(Color x) {

		console.setTextAttributes(new TextAttributes(x));

	}

}
