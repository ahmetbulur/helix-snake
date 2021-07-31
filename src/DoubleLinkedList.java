import enigma.core.Enigma;

public class DoubleLinkedList {

	enigma.console.Console console = Enigma.getConsole("HELIX SNAKE", 90, 30, 21, 4);

	static DLLNode head;
	static DLLNode tail;

	public DoubleLinkedList() {

		head = null;
		tail = null;

	}

	public DLLNode getHead() {
		return head;
	}

	public void setHead(DLLNode head) {
		this.head = head;
	}

	public void Add(String nickname, Integer dataToAdd) {

		DLLNode newNode = new DLLNode(nickname, dataToAdd);
		if (head == null) {

			head = newNode;
			tail = newNode;

		} else {

			if (dataToAdd > (int) head.getData()) {

				newNode.setNext(head);
				head.setPrev(newNode);
				head = newNode;

			} else {

				DLLNode temp = head;
				while (temp.getNext() != null && dataToAdd < (int) temp.getNext().getData()) {

					temp = temp.getNext();

				}
				newNode.setPrev(temp);
				newNode.setNext(temp.getNext());
				if (temp.getNext() != null) // adding between nodes
					temp.getNext().setPrev(newNode);
				else // adding to the end
					tail = newNode;
				temp.setNext(newNode);

			}

		}

	}

	public int Size() {

		int count = 0;

		if (head == null)
			System.out.println("empty");
		else {

			DLLNode temp = head;
			while (temp != null) {

				count++;
				temp = temp.getNext();

			}

		}

		return count;

	}

	// method for printing High Score Table (Top-10 list)
	public void Display() {

		console.getTextWindow().setCursorPosition(0, 0);

		int scoreNumber = 1;
		if (head == null)
			System.out.println("empty");
		else {

			System.out.println("+----------- High Scores -------------+");
			System.out.println("|                                     |");
			DLLNode temp = head;
			while (temp != null && scoreNumber <= 10) {

				if (scoreNumber < 10)
					System.out.print("|      " + scoreNumber++ + " : " + temp.getNickname() + " - " + temp.getData());
				else
					System.out.print("|     " + scoreNumber++ + " : " + temp.getNickname() + " - " + temp.getData());

				console.getTextWindow().setCursorPosition(38, scoreNumber);
				System.out.println("|");

				temp = temp.getNext();

			}

			System.out.println("|                                     |");
			System.out.println("+-------------------------------------+");

		}

	}

}
