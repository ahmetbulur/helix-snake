
public class CategoryNode {

	private String CategoryName;
	private CategoryNode down;
	private ItemNode right;
	public CategoryNode(String dataToAdd) 
	{
	CategoryName = dataToAdd;
	down = null;
	right = null;
	}
	public Object getCategoryName() { return CategoryName; }
	public void setCategoryName(String data) { this.CategoryName = data; }
	public CategoryNode getDown() { return down; }
	public void setDown(CategoryNode down) { this.down = down; }
	public ItemNode getRight() { return right; }
	public void setRight(ItemNode right) { this.right = right; }
}
