package lombardi;

public class Item {

	private int nodeCount;
	private String userName;

	public Item(String userName, int nodeCount) {
		this.nodeCount = nodeCount;
		this.userName = userName;
	}



	public String toString() {
		return userName + " - " + nodeCount + " (tweets)";
	}



	public int getNodeCount() {
		return nodeCount;
	}



	public void setNodeCount(int nodeCount) {
		this.nodeCount = nodeCount;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return super.equals(arg0);
	}

}
