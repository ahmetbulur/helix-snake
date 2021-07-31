
public class Snake {

	static SingleLinkedList snake;

	Screen screen = new Screen();

	// generate simple snake and assign first 3 letter
	public Snake() {

		snake = new SingleLinkedList();

		int x = 2;

		for (int i = 0; i < 3; i++) {

			char letter;

			int rnd = (int) (Math.random() * 4) + 1;

			if (rnd == 1)
				letter = 'A';
			else if (rnd == 2)
				letter = 'C';
			else if (rnd == 3)
				letter = 'G';
			else
				letter = 'T';

			snake.addToHead(letter, x++, 2);

		}

	}

	// assign each node of snake(SLL) to two-dimensional array 
	public void placeSnake() {

		SLLNode temp = SingleLinkedList.getHead();

		while (temp != null) {
			Screen.array[temp.getY_coordinate()][temp.getX_coordinate()] = (char) temp.getData();
			temp = temp.getLink();
		}

	}

	public void moveLeft() {

		SLLNode temp = SingleLinkedList.head;

		int tempX = 0, tempY = 0, previousX = 0, previousY = 0;

		int headX = temp.getX_coordinate() - 1;
		int headY = temp.getY_coordinate();

		temp = temp.getLink();
		while (temp != null) {

			// if snake bumps into its own body, game is over
			if (headX == temp.getX_coordinate() && headY == temp.getY_coordinate())
				screen.continueGame = false;
			temp = temp.getLink();

		}

		temp = SingleLinkedList.head;

		// if snake bumps into a wall, game is over
		if (screen.array[temp.getY_coordinate()][temp.getX_coordinate() - 1] == '#')
			screen.continueGame = false;

		if (Game.direction == "left") {

			char letter = screen.array[temp.getY_coordinate()][temp.getX_coordinate() - 1];

			// eating letter and score implementation
			while (letter != ' ' && letter != '#') {

				Snake.snake.addToHead(letter, temp.getX_coordinate() - 1, temp.getY_coordinate());

				temp = SingleLinkedList.head;

				letter = screen.array[temp.getY_coordinate()][temp.getX_coordinate() - 1];

				if (Snake.snake.size() % 3 == 0)
					Game.checkCodons = true;
				
				if (screen.continueGame)
					Screen.score += 5;
				
				screen.generateRandomLetters();

			}
		}

		// each node takes coordinate of node that comes before it
		while (temp != null) {

			if (temp == SingleLinkedList.getHead()) {

				tempX = temp.getX_coordinate();
				tempY = temp.getY_coordinate();
				temp.setX_coordinate(temp.getX_coordinate() - 1);

			} else {

				previousX = temp.getX_coordinate();
				previousY = temp.getY_coordinate();

				temp.setX_coordinate(tempX);
				temp.setY_coordinate(tempY);

				tempX = previousX;
				tempY = previousY;

			}

			temp = temp.getLink();
		}

		screen.array[tempY][tempX] = ' ';
	}

	public void moveRight() {

		SLLNode temp = SingleLinkedList.head;

		int tempX = 0, tempY = 0, previousX = 0, previousY = 0;

		int headX = temp.getX_coordinate() + 1;
		int headY = temp.getY_coordinate();

		temp = temp.getLink();
		while (temp != null) {

			// if snake bumps into its own body, game is over
			if (headX == temp.getX_coordinate() && headY == temp.getY_coordinate())
				screen.continueGame = false;
			temp = temp.getLink();

		}

		temp = SingleLinkedList.head;

		// if snake bumps into a wall, game is over
		if (screen.array[temp.getY_coordinate()][temp.getX_coordinate() + 1] == '#')
			screen.continueGame = false;

		if (Game.direction == "right") {

			char letter = screen.array[temp.getY_coordinate()][temp.getX_coordinate() + 1];

			// eating letter and score implementation
			while (letter != ' ' && letter != '#') {

				Snake.snake.addToHead(letter, temp.getX_coordinate() + 1, temp.getY_coordinate());

				temp = SingleLinkedList.head;

				letter = screen.array[temp.getY_coordinate()][temp.getX_coordinate() + 1];

				if (Snake.snake.size() % 3 == 0)
					Game.checkCodons = true;
				if (screen.continueGame)
					Screen.score += 5;
				screen.generateRandomLetters();
			}
		}

		// each node takes coordinate of node that comes before it
		while (temp != null) {

			if (temp == SingleLinkedList.getHead()) {

				tempX = temp.getX_coordinate();
				tempY = temp.getY_coordinate();

				temp.setX_coordinate(temp.getX_coordinate() + 1);

			} else {

				previousX = temp.getX_coordinate();
				previousY = temp.getY_coordinate();

				temp.setX_coordinate(tempX);
				temp.setY_coordinate(tempY);

				tempX = previousX;
				tempY = previousY;

			}

			temp = temp.getLink();
		}

		screen.array[tempY][tempX] = ' ';

	}

	public void moveUp() {

		SLLNode temp = SingleLinkedList.head;

		int tempX = 0, tempY = 0, previousX = 0, previousY = 0;

		int headX = temp.getX_coordinate();
		int headY = temp.getY_coordinate() - 1;

		temp = temp.getLink();
		while (temp != null) {

			// if snake bumps into its own body, game is over
			if (headX == temp.getX_coordinate() && headY == temp.getY_coordinate())
				screen.continueGame = false;
			temp = temp.getLink();

		}

		temp = SingleLinkedList.head;

		// if snake bumps into a wall, game is over
		if (screen.array[temp.getY_coordinate() - 1][temp.getX_coordinate()] == '#')
			screen.continueGame = false;

		if (Game.direction == "up") {

			char letter = screen.array[temp.getY_coordinate() - 1][temp.getX_coordinate()];

			// eating letter and score implementation
			while (letter != ' ' && letter != '#') {

				Snake.snake.addToHead(letter, temp.getX_coordinate(), temp.getY_coordinate() - 1);

				temp = SingleLinkedList.head;

				letter = screen.array[temp.getY_coordinate() - 1][temp.getX_coordinate()];

				if (Snake.snake.size() % 3 == 0)
					Game.checkCodons = true;
				if (screen.continueGame)
					Screen.score += 5;
				screen.generateRandomLetters();
			}
		}

		// each node takes coordinate of node that comes before it
		while (temp != null) {

			if (temp == SingleLinkedList.getHead()) {
				tempX = temp.getX_coordinate();
				tempY = temp.getY_coordinate();

				temp.setY_coordinate(temp.getY_coordinate() - 1);

			} else {

				previousX = temp.getX_coordinate();
				previousY = temp.getY_coordinate();

				temp.setX_coordinate(tempX);
				temp.setY_coordinate(tempY);

				tempX = previousX;
				tempY = previousY;

			}

			temp = temp.getLink();
		}

		screen.array[tempY][tempX] = ' ';

	}

	public void moveDown() {

		SLLNode temp = SingleLinkedList.head;

		int tempX = 0, tempY = 0, previousX = 0, previousY = 0;

		int headX = temp.getX_coordinate();
		int headY = temp.getY_coordinate() + 1;

		temp = temp.getLink();
		while (temp != null) {

			// if snake bumps into its own body, game is over
			if (headX == temp.getX_coordinate() && headY == temp.getY_coordinate())
				screen.continueGame = false;
			temp = temp.getLink();

		}

		temp = SingleLinkedList.head;

		// if snake bumps into a wall, game is over
		if (screen.array[temp.getY_coordinate() + 1][temp.getX_coordinate()] == '#')
			screen.continueGame = false;

		if (Game.direction == "down") {

			char letter = screen.array[temp.getY_coordinate() + 1][temp.getX_coordinate()];

			// eating letter and score implementation
			while (letter != ' ' && letter != '#') {

				Snake.snake.addToHead(letter, temp.getX_coordinate(), temp.getY_coordinate() + 1);

				temp = SingleLinkedList.head;

				letter = screen.array[temp.getY_coordinate() + 1][temp.getX_coordinate()];

				if (Snake.snake.size() % 3 == 0)
					Game.checkCodons = true;
				if (screen.continueGame)
					Screen.score += 5;
				screen.generateRandomLetters();
			}
		}

		// each node takes coordinate of node that comes before it
		while (temp != null) {

			if (temp == SingleLinkedList.getHead()) {

				tempX = temp.getX_coordinate();
				tempY = temp.getY_coordinate();

				temp.setY_coordinate(temp.getY_coordinate() + 1);

			} else {

				previousX = temp.getX_coordinate();
				previousY = temp.getY_coordinate();

				temp.setX_coordinate(tempX);
				temp.setY_coordinate(tempY);

				tempX = previousX;
				tempY = previousY;

			}

			temp = temp.getLink();
		}

		screen.array[tempY][tempX] = ' ';

	}

}
