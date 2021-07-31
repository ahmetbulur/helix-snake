
public class SLLNode {

	private Object data;
	private int x_coordinate;
	private int y_coordinate;
	private SLLNode link;

	public SLLNode(Object data, int x_coordinate, int y_coordinate) {

		this.data = data;
		this.x_coordinate = x_coordinate;
		this.y_coordinate = y_coordinate;
		this.link = link;

	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getX_coordinate() {
		return x_coordinate;
	}

	public void setX_coordinate(int x_coordinate) {
		this.x_coordinate = x_coordinate;
	}

	public int getY_coordinate() {
		return y_coordinate;
	}

	public void setY_coordinate(int y_coordinate) {
		this.y_coordinate = y_coordinate;
	}

	public SLLNode getLink() {
		return link;
	}

	public void setLink(SLLNode link) {
		this.link = link;
	}

}
