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
 * Собственно класс Tour - тип данных, хранящий путь коммивояжёра
 */
public class Tour {
	/**
	 * Указывает на начало пути
	 */
	private Node first;
	
	/**
	 * Текущая длина пути в точках
	 */
	private int currentSize;
	
	/**
	 * Текущее расстояние
	 */
	private double currentDistance;

	/**
	 * Класс - связанный список из точек пути
	 *
	 */
	private class Node {
		/**
		 * Собственно, координаты точки
		 */
		private Point p;
		/**
		 * Ссылка на следующую точку
		 */
		private Node next;

		/**
		 * Конструктор
		 * @param p
		 * точка
		 */
		public Node(Point p) {
			this.p = p;
			this.next = null;

		}
	}

	/**
	 * Реализовывать обычный Iterable сложнее, чем придумать свой :)
	 * Работает по принципу - при каждом вызове указатель возвращает текущий объект и сразу переключается на следующий.
	 * То же с счётчиком
	 */
	private class AlternateIterator {
		/**
		 * Куда сейчас указывает итератор
		 */
		private Node current;
		/**
		 * Номер, может иметь разные смыслы по использованию
		 */
		private int counter;
		/**
		 * Это первый запуск?
		 */
		private boolean isFirstRun;

		/**
		 * Конструктор
		 */
		public AlternateIterator() {
			current = first;
			isFirstRun = true;
			counter = 1;
		}

		/**
		 * Есть ли следующий элемент?
		 * @return
		 * да или нет
		 */
		public boolean hasNext() {
			// если первый запуск, то есть. Верно только если точек больше 1
			if (isFirstRun) {
				isFirstRun = false;
				return true;
			}
			// если не первый запуск, а указывает на first - значит конец
			if (current == first) {
				return false;
			}
			
			return true;
		}

		/**
		 * Получить номер и увеличить его для следующего вызова
		 * @return
		 */
		public int getCounter() {
			return counter++;
		}

		/**
		 * Получить текущее положение и сдвинуть его для следующего вызова
		 * @return
		 */
		public Node getNode() {
			Node result = current;
			current = current.next;
			return result;
		}
	}

	/**
	 * Конструктор
	 */
	public Tour() {
		first = null;
		currentSize = 0;
		currentDistance = 0.0;
	}

	/**
	 * Выводит все точки маршрута, используя AlternateIterator
	 */
	public void show() {
		AlternateIterator alterIter = new AlternateIterator();
		while (alterIter.hasNext()) {
			StdOut.println("Point " + alterIter.getCounter() + " "
					+ alterIter.getNode().p);
		}
	}

	/**
	 * Рисует маршрут, используя AlternateIterator
	 */
	public void draw() {
		AlternateIterator alterIter = new AlternateIterator();
		while (alterIter.hasNext()) {
			Node current = alterIter.getNode();
			current.p.draw();
			current.p.drawTo(current.next.p);
		}
	}

	/**
	 * Возвращает длину пути
	 * @return
	 */
	public double distance() {
		return currentDistance;
	}

	/**
	 * Возвращает размер маршрута в точках
	 * @return
	 */
	public int size() {
		return currentSize;
	}

	/**
	 * Добавляет первую точку в маршрут
	 * @param p
	 * точка-старт
	 */
	private void insertFirst(Point p) {
		currentSize++;
		
		first = new Node(p);
		first.next = first;
	}

	/** 
	 * Добавить после
	 * @param where
	 * этого узла
	 * @param what
	 * эту точку
	 */
	private void insertAfter(Node where, Point what) {
		currentSize++;
		currentDistance += deltaDistance(where, what);
	
		Node insert = new Node(what);
		insert.next = where.next;
		where.next = insert;
	}
	
	/**
	 * Вычисляет разницу в расстояниях, если 
	 * @param where
	 * псоле этого узла
	 * @param what
	 * добавить эту точку
	 * @return
	 * +разница
	 */
	private double deltaDistance(Node where, Point what) {
		double deltaDistance = where.p.distanceTo(what) + where.next.p.distanceTo(what)
				- where.p.distanceTo(where.next.p);
		
		return deltaDistance;
	}

	/**
	 * Реализация эвристики ближайшего соседа
	 * @param p
	 */
	public void insertNearest(Point p) {
		// если первая точка, просто добавить 
		if (first == null) {
			insertFirst(p);
			return;
		}
		
		// разыгрываем после какой лучше всего добавить, первая будет победителем по умолчанию
		Node best = first;
		double bestDistance = first.p.distanceTo(p);
		
		// пройти по всем
		AlternateIterator alterIter = new AlternateIterator();
		while (alterIter.hasNext()) {
			Node current = alterIter.getNode();
			// собственно эвристика - найти расстояние от текущей точки в цикле до данной
			double distance = current.p.distanceTo(p);
			
			// если нашлась лучше текущего победителя - она победитель
			if (distance < bestDistance) {
				bestDistance = distance;
				best = current;
			}
		}
		
		// добавить после лучшей
		insertAfter(best, p);
	}

	/**
	 * Реализация эвристики наименьшего увеличения
	 * @param p
	 */
	public void insertSmallest(Point p) {
		// если первая точка, просто добавить 
		if (first == null) {
			insertFirst(p);
			return;
		}
		
		// разыгрываем после какой лучше всего добавить, первая будет победителем по умолчанию
		Node best = first;
		double bestDistance = deltaDistance(first, p);
		
		// пройти по всем
		AlternateIterator alterIter = new AlternateIterator();
		while (alterIter.hasNext()) {
			Node current = alterIter.getNode();
			// собственно эвристика - найти разницу расстояний, если добавить данную точку после текущей в цикле
			double distance = deltaDistance(current, p);
			
			// если нашлась лучше текущего победителя - она победитель
			if (distance < bestDistance) {
				bestDistance = distance;
				best = current;
			}
		}
		
		// добавить после лучшей
		insertAfter(best, p);
	}

	/**
	 * Тестировать нечего, лучше сразу пользоваться клиентами /.
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
