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
	
	
	
	void show() {
		/*
		Node temp = first;
		int i = 1;
		while (!(temp == first) || i == 1) {
			// TODO iterator
			StdOut.println("Point " + i + temp.p);
			i++;
			temp = temp.next;

		}
		*/
		AlternateIterator alterIter = new AlternateIterator();
		while (alterIter.hasNext()) {
			StdOut.println("Point " + alterIter.getCounter() + " " + alterIter.getNode().p);
		}
	}

	void draw() {
		// StdDraw.setXscale(0, 600);
		// StdDraw.setYscale(0, 600);
		/*
		Node temp = first;
		int i = 1;
		while (!(temp == first) || i == 1) {
			temp.p.draw();
			temp.p.drawTo(temp.next.p);
			i++;
			temp = temp.next;

		}
		*/
		AlternateIterator alterIter = new AlternateIterator();
		while (alterIter.hasNext()) {
			Node current = alterIter.getNode();
			current.p.draw();
			current.p.drawTo(current.next.p);			
		}
	}

	double distance() {
		/*
		Node temp = first;
		int i = 1;
		int distance = 0;
		while (!(temp == first) || i == 1) {
			distance += temp.p.distanceTo(temp.next.p);
			i++;
			temp = temp.next;

		}
		*/
		AlternateIterator alterIter = new AlternateIterator();
		double distance = 0;
		while (alterIter.hasNext()) {
			Node current = alterIter.getNode();
			distance += current.p.distanceTo(current.next.p);			
		}
		return distance;
	}

	int size() {
		/*
		Node temp = first;
		int i = 1;
		// TODO size-1
		while (!(temp == first) || i == 1) {
			i++;
			temp = temp.next;

		}
		return i;
		*/
		return currentSize;
	}

	void insertNearest(Point p) {
		// anyway
		currentSize++;
		
		if (first == null) {
			first = new Node(p);
			first.next = first;
			return;
		}
		Node temp = first;
		Node best = first;
		
		double bestDistance = first.p.distanceTo(p);
		
		int i = 1;
		while (!(temp == first) || i == 1) {
			double distance = temp.p.distanceTo(p);
			if (distance < bestDistance) {
				bestDistance = distance;
				best = temp;
			}
			i++;
			temp = temp.next;
		}
		Node insert = new Node(p);
		insert.next = best.next;
		best.next = insert;
	}

	void insertSmallest(Point p) {
		
		// anyway
		currentSize++;
		
		// TODO insert between, insert first in smallest and nearest
		if (first == null) {
			first = new Node(p);
			first.next = first;
			return;
		}
		
		if (first.next == first) {
			first.next = new Node(p);
			first.next.next = first;
			return;
		}
		
		
		Node temp = first;
		Node best = first;
		// We do need only the delta
		double bestDistance = first.p.distanceTo(p) + first.next.p.distanceTo(p);
		
		int i = 1;
		while (!(temp == first) || i == 1) {
			double distance = temp.p.distanceTo(p) + temp.next.p.distanceTo(p);
			
			
			if (distance < bestDistance) {
				bestDistance = distance;
				best = temp;
			}
			
			
			i++;
			temp = temp.next;
		}
		
		
		Node insert = new Node(p);
		insert.next = best.next;
		best.next = insert;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

}
