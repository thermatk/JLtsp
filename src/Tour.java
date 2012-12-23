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

	private class Node {
		private Point p;
		private Node next;

		public Node(Point p) {
			this.p = p;
			this.next = null;

		}
	}

	public Tour() {
		Point p = new Point(1, 1);
		first = new Node(p);

	}

	public Tour(Point a, Point b, Point c, Point d) {

	}

	void show() {
		Node temp = first;
		int i = 1;
		while (!(temp == first) || i==1) {
			//TODO  Вынести наружу
			StdOut.println("Point " + i + temp.p);
			i++;
			temp=temp.next;

		}

	}

	void draw() {
		Node temp = first;
		int i = 1;
		while (!(temp == first) || i==1) {
			temp.p.draw();
			
			i++;
			temp=temp.next;

		}

	}

	double distance() {

	}

	int size() {

	}

	void insertSmallest(Point p) {

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
