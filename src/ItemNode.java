
public class ItemNode {
	private String ItemName;
	private ItemNode next;
	private int point;

	public ItemNode(String dataToAdd, int point) {
		ItemName = dataToAdd;
		this.point = point;
		next = null;
		
	}

	public String getItemName() {
		return ItemName;
	}

	public void setItemName(String data) {
		this.ItemName = data;
	}

	public ItemNode getNext() {
		return next;
	}

	public void setNext(ItemNode next) {
		this.next = next;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

}
