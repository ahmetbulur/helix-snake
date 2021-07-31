
public class DLLNode {

	private Object data;
	private DLLNode next;
	private DLLNode prev;
	private String nickname;

	public DLLNode(String nickname, Object data) {

		this.data = data;
		prev = null;
		next = null;
		this.nickname = nickname;

	}

	public DLLNode getNext() {
		return next;
	}

	public void setNext(DLLNode next) {
		this.next = next;
	}

	public DLLNode getPrev() {
		return prev;
	}

	public void setPrev(DLLNode prev) {
		this.prev = prev;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
