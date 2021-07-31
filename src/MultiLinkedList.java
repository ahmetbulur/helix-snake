import enigma.core.Enigma;

public class MultiLinkedList {

	private CategoryNode head;

	public void addCategory(String dataToAdd) {
		CategoryNode temp;
		if (head == null) {
			temp = new CategoryNode(dataToAdd);
			head = temp;
		} else {
			temp = head;
			while (temp.getDown() != null)
				temp = temp.getDown();
			CategoryNode newnode = new CategoryNode(dataToAdd);
			temp.setDown(newnode);
		}
	}

	public void addItem(String Category, String Item, int point) {
		if (head == null)
			System.out.println("Add a Category before Item");
		else {
			CategoryNode temp = head;
			while (temp != null) {
				if (Category.equals(temp.getCategoryName())) {
					ItemNode temp2 = temp.getRight();
					if (temp2 == null) {
						temp2 = new ItemNode(Item, point);
						temp.setRight(temp2);
					} else {
						while (temp2.getNext() != null)
							temp2 = temp2.getNext();
						ItemNode newnode = new ItemNode(Item, point);

						temp2.setNext(newnode);
					}
				}
				temp = temp.getDown();
			}
		}
	}

	public int sizeCategory() {
		int count = 0;
		if (head == null)
			System.out.println("linked list is empty");
		else {
			CategoryNode temp = head;
			while (temp != null) {
				count++;
				temp = temp.getDown();
			}
		}
		return count;
	}

	public int sizeItem() {
		int count = 0;
		if (head == null)
			System.out.println("linked list is empty");
		else {
			CategoryNode temp = head;
			while (temp != null) {
				ItemNode temp2 = temp.getRight();
				while (temp2 != null) {
					count++;
					temp2 = temp2.getNext();
				}
				temp = temp.getDown();
			}
		}
		return count;
	}

	static int count = 0;

	// checking last 3 letter(codon) and score implementation with codon points
	public void search(String codon) {

		if (head == null)
			System.out.println("linked list is empty");
		else {
			CategoryNode temp = head;
			while (temp != null) {

				ItemNode temp2 = temp.getRight();
				while (temp2 != null) {

					if (codon.equals(temp2.getItemName())) {

						Screen.score += temp2.getPoint();

						// add to array to print codons and codon points
						Game.pointCodons[count++] = "" + temp2.getItemName() + "-" + temp2.getPoint() + "";
						break;

					} else
						temp2 = temp2.getNext();

				}
				temp = temp.getDown();

			}
		}
		if (count == 3)
			count = 0;

	}

}
