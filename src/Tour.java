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
		first = null;

	}

	public Tour(Point a, Point b, Point c, Point d) {

	}

	void show() {
		Node temp = first;
		int i = 1;
		while (!(temp == first) || i == 1) {
			// TODO ������� ������
			StdOut.println("Point " + i + temp.p);
			i++;
			temp = temp.next;

		}

	}

	void draw() {
		// StdDraw.setXscale(0, 600);
		// StdDraw.setYscale(0, 600);
		Node temp = first;
		int i = 1;
		while (!(temp == first) || i == 1) {
			temp.p.draw();
			temp.p.drawTo(temp.next.p);
			i++;
			temp = temp.next;

		}

	}

	double distance() {
		Node temp = first;
		int i = 1;
		int distance = 0;
		while (!(temp == first) || i == 1) {
			distance += temp.p.distanceTo(temp.next.p);
			i++;
			temp = temp.next;

		}
		return distance;
	}

	int size() {
		Node temp = first;
		int i = 1;
		// TODO size-1
		while (!(temp == first) || i == 1) {
			i++;
			temp = temp.next;

		}
		return i;
	}

	void insertNearest(Point p) {
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

	void insertSmallest(Point p, Point b) {
	/*
	 * Ничем не отличается от другой, но теперь расстояние - это расстояние от p
	 * до temp.p и temp.next.p, потому что мы хотим получить наименьшее
	 * увеличение пути. А путь увеличится на эту сумму расстояний. Еще
	 * отличается тем, что теперь если пусто, то сразу создается первая и вторая
	 * точка (следующая по вооду) Это не противоречит эвристике - мы должны
	 * добавить точку после такой, что рост пути будет минимален. Для 2-ой точки
	 * у нас только один выбор - первая точка.
	 */

		if (first == null) {
			first = new Node(p);
			Node second = new Node(b);// Или другим способом добавить вторую
										// точку.
			first.next = second;
			second.next = first;
			return;
		}
		Node temp = first;
		Node best = first;
		double bestDistance = first.p.distanceTo(p) + second.p.distanceTo(p);
		int i = 1;
		while (!(temp == first) || i == 1) {
			double distance = temp.p.distanceTo(p) + temp.next.distanceTo(p);
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
		// TODO Auto-generated method stub

	}
}
