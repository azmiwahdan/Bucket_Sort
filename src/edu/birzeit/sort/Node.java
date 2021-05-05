package edu.birzeit.sort;

public class Node {
	protected String data;
	protected int next;
	
	
	public Node(String data, int next) {
		
		this.data = data;
		this.next = next;
	}


	@Override
	public String toString() {
		return   data + "";
	}
	
}