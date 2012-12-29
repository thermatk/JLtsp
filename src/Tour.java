/**
 * 
 */

/**
 * @author thermatk
 * @author remkvadriga
 * 
 */
public class Tour {

	private Node first;
	
	private int currentSize;

	private class Node {
		private Point p;
		private Node next;

		public Node(Point p) {
			this.p = p;
			this.next = null;

		}
	}
	
	private class AlternateIterator {
		private Node current;
		private int counter; 
		private boolean isFirstRun;
		
		
		public AlternateIterator() {
			current = first;
			isFirstRun = true;
			counter = 1;
		}
		
		public boolean hasNext() {
			if (isFirstRun) {
				isFirstRun = false;
				return true;
			}
			
			if (current == first) {
				return false;
			}
			
			return true;
		}
		
		public int getCounter() {
			return counter++;
		}
		
		public Node getNode() {
			Node result = current;
			current = current.next;
			return result;
		}
	}

	public Tour() {
		first = null;
		currentSize = 0;
	}
	
	
	
	public void show() {
		AlternateIterator alterIter = new AlternateIterator();
		while (alterIter.hasNext()) {
			StdOut.println("Point " + alterIter.getCounter() + " " + alterIter.getNode().p);
		}
	}

	public void draw() {
		AlternateIterator alterIter = new AlternateIterator();
		while (alterIter.hasNext()) {
			Node current = alterIter.getNode();
			current.p.draw();
			current.p.drawTo(current.next.p);			
		}
	}

	public double distance() {
		AlternateIterator alterIter = new AlternateIterator();
		double distance = 0;
		while (alterIter.hasNext()) {
			Node current = alterIter.getNode();
			distance += current.p.distanceTo(current.next.p);			
		}
		return distance;
	}

	public int size() {
		return currentSize;
	}
	
	private void insertFirst(Point p) {
		first = new Node(p);
		first.next = first;
	}
	
	private void insertAfter(Node where, Point what) {
		Node insert = new Node(what);
		insert.next = where.next;
		where.next = insert;
	}

	public void insertNearest(Point p) {
		// anyway
		currentSize++;
		
		if (first == null) {
			insertFirst(p);
			return;
		}
			
		
		Node best = first;
		double bestDistance = first.p.distanceTo(p);
		
		AlternateIterator alterIter = new AlternateIterator();
		while (alterIter.hasNext()) {
			Node current = alterIter.getNode();
			double distance = current.p.distanceTo(p);
			if (distance < bestDistance) {
				bestDistance = distance;
				best = current;
			}		
		}
		
		insertAfter(best, p);
	}

	public void insertSmallest(Point p) {
		
		// anyway
		currentSize++;
		
		if (first == null) {
			insertFirst(p);
			return;
		}

		Node best = first;
		double bestDistance = first.p.distanceTo(p) + first.next.p.distanceTo(p);
		
		AlternateIterator alterIter = new AlternateIterator();
		while (alterIter.hasNext()) {
			Node current = alterIter.getNode();
			double distance = current.p.distanceTo(p) + current.next.p.distanceTo(p);
			
			if (distance < bestDistance) {
				bestDistance = distance;
				best = current;
			}		
		}
		
		insertAfter(best, p);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

}
