/*************************************************************************
 *  Authors: Руслан Бойцов & Арсений Самсонов
 *  Compilation: javac -cp .:stdlib.jar Tour.java
 *  Execution: via client
 * 
 *	Вспомогательный класс для программы, решающей задачу коммивояжёра. 
 *	Реазилуют эвристики наименьшего увеличения и ближайшего соседа.
 *	Создан по данному описанию API.
 *  
 *  % java  -cp .:stdlib.jar NearestInsertion < testing/tsp10.txt
 *  Tour distance =  1566.1363
 *  Number of points = 10
 *  Point 1 (110.0, 225.0)
 *  Point 2 (161.0, 280.0)
 *  Point 3 (157.0, 443.0)
 *  Point 4 (283.0, 379.0)
 *  Point 5 (306.0, 360.0)
 *  Point 6 (325.0, 554.0)
 *  Point 7 (397.0, 566.0)
 *  Point 8 (490.0, 285.0)
 *  Point 9 (552.0, 199.0)
 *  Point 10 (343.0, 110.0)
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
			StdOut.println("Point " + alterIter.getCounter() + " "
					+ alterIter.getNode().p);
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
		currentSize++;

		if (first == null) {
			insertFirst(p);
			return;
		}

		Node best = first;
		double bestDistance = first.p.distanceTo(p)
				+ first.next.p.distanceTo(p) - first.p.distanceTo(first.next.p);

		AlternateIterator alterIter = new AlternateIterator();
		while (alterIter.hasNext()) {
			Node current = alterIter.getNode();
			double distance = current.p.distanceTo(p)
					+ current.next.p.distanceTo(p)
					- current.p.distanceTo(current.next.p);

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
