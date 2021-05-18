
public class DynamicSetList<E> {
	private Node head;
	private Node tail;

	private class Node {
		E key;
		Node previous;
		Node next;

		public Node(Node previous, E key, Node next) {
			this.previous = previous;
			this.key = key;
			this.next = next;
		}

		public Node() {
		}

		@Override
		public String toString() {
			return "[" + this.key + "]";
		}
	}

	public DynamicSetList() {
		this.head = new Node();
		this.tail = this.head;
	};

	public boolean add(E element) {
		Node n = this.head;
		// if set is empty, assign element to the first node
		if (this.isEmpty()) {
			n.key = element;
			return true;
		}
		while (n.next != null) {
			// if element already in the set, return false
			if (n.key == element) {
				return false;
			}
			n = n.next;
		}
		if (n.key == element) {
			return false;
		}
		// create node and insert it at the tail
		Node newNode = new Node(n, element, null);
		n.next = newNode;
		return true;
	}

	public boolean remove(E element) {
		Node n = this.head;
		while (n != null) {
			if (n.key == element) {
				// if first element
				if (n.previous == null) {
					if (n.next != null) {
						this.head = n.next;
						this.head.previous = null;
					} else {
						this.head.key = null;
					}
				} else {

					n.previous.next = n.next;
				}
				return true;
			}
			n = n.next;
		}
		return false;
	}

	public boolean isElement(E element) {
		Node n = this.head;
		while (n != null) {
			if (n.key == element) {
				return true;
			}
			n = n.next;
		}
		return false;
	}

	public boolean isEmpty() {
		return this.head.key == null;
	}

	public int size() {
		int size = 0;
		Node n = this.head;
		while (n != null && n.key != null) {
			size += 1;
			n = n.next;
		}
		return size;
	}

	public DynamicSetList<E> union(DynamicSetList<E> otherSet) {
		DynamicSetList<E> union = new DynamicSetList<>();
		// add elements from this set
		Node n = this.head;
		while (n != null && n.key != null) {
			union.add(n.key);
			n = n.next;
		}
		// add elements from otherSet
		n = otherSet.head;
		while (n != null && n.key != null) {
			if (!union.isElement(n.key)) {
				union.add(n.key);
			}
			n = n.next;
		}
		return union;
	}

	public DynamicSetList<E> intersection(DynamicSetList<E> otherSet) {
		DynamicSetList<E> intersection = new DynamicSetList<>();
		Node n = this.head;
		while (n != null && n.key != null) {
			if (otherSet.isElement(n.key)) {
				intersection.add(n.key);
			}
			n = n.next;
		}
		return intersection;
	}

	public DynamicSetList<E> difference(DynamicSetList<E> otherSet) {
		DynamicSetList<E> difference = new DynamicSetList<>();
		Node n = this.head;
		while (n != null && n.key != null) {
			if (!otherSet.isElement(n.key)) {
				difference.add(n.key);
			}
			n = n.next;
		}
		return difference;
	}

	public boolean subset(DynamicSetList<E> otherSet) {
		Node n = this.head;
		while (n != null && n.key != null) {
			if (!otherSet.isElement(n.key)) {
				return false;
			}
			n = n.next;
		}
		return true;
	}

	@Override
	public String toString() {
		String str = "";
		Node n = this.head;
		while (n != null) {
			str += n + " -> ";
			n = n.next;
		}
		return str.substring(0, str.length() - 4);
	}
	
	public void addArray(E[] arr) {
		for (int i = 0; i < arr.length; i++) {
			this.add(arr[i]);
		}
	}
}
