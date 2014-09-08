package classes;

public class CFGNode {
	
	int x, y, z, index, numOfStmts, numOfAppear, numOfInvokeStmts;
	
	public int getNumOfInvokeStmts() {
		return numOfInvokeStmts;
	}

	public void setNumOfInvokeStmts(int numOfInvokeStmts) {
		this.numOfInvokeStmts = numOfInvokeStmts;
	}
	
	public int getNumOfStmts() {
		return numOfStmts;
	}

	public void setNumOfStmts(int numOfStmts) {
		this.numOfStmts = numOfStmts;
	}
	
	public int getNumOfAppear() {
		return numOfAppear;
	}

	public void setNumOfAppear(int numOfAppear) {
		this.numOfAppear = numOfAppear;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public CFGNode(){
		
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

}
