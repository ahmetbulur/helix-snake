public class SingleLinkedList {

	static SLLNode head;

	public SingleLinkedList() {

		head = null;

	}

	public static SLLNode getHead() {
		return head;
	}

	public static void setHead(SLLNode head) {
		SingleLinkedList.head = head;
	}

	public void addToHead(Object data, int x_coordinate, int y_coordinate) {

		SLLNode newNode = new SLLNode(data, x_coordinate, y_coordinate);

		if (head == null) {
			head = newNode;
		} else {

			SLLNode temp = head;

			newNode.setLink(head);

			head = newNode;

		}

	}

	// returns last three letter of snake
	public String reverseTrio() {

		String snake = "";
		SLLNode temp = head;
		while (temp != null) {
			snake += temp.getData();
			temp = temp.getLink();
		}
		String trio = snake.substring(0, 3);
		String reverseTrio = "";
		for (int i = trio.length() - 1; i >= 0; i--) {
			reverseTrio = reverseTrio + trio.charAt(i);
		}
		return reverseTrio;
	}

	public int size() {

		SLLNode temp = head;
		int counter = 0;
		while (temp != null) {
			counter++;
			temp = temp.getLink();
		}
		return counter;

	}

}