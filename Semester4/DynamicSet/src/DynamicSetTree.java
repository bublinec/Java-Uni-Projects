public class DynamicSetTree<E> {
	private Node root;
	private int size = 0;
	private static boolean isSubset = true;

	private class Node {
		E key;
		Node parent;
		Node left;
		Node right;

		public Node(Node parent, E key, Node left, Node right) {
			this.parent = parent;
			this.key = key;
			this.left = left;
			this.right = right;
		}

		public Node() {
		}

		@Override
		public String toString() {
			return "[" + this.key + "]";
		}
	}

	public DynamicSetTree() {
		this.root = null;
	}

	// UTILS

	public void addArray(E[] arr) {
		for (int i = 0; i < arr.length; i++) {
			this.add(arr[i]);
		}
	}

	private void transplant(Node removedTree, Node newTree) {
		// if root
		if (removedTree.parent == null) {
			this.root = newTree;
		}
		// if it is to the left of the parent
		else if (removedTree == removedTree.parent.left) {
			removedTree.parent.left = newTree;
		} else {
			removedTree.parent.right = newTree;
		}
		if (newTree != null) {
			newTree.parent = removedTree.parent;
		}
	}

	private Node search(E element, Node root) {
		if (root == null || element == root.key) {
			return root;
		}
		if ((int) element < (int) root.key) {
			return search(element, root.left);
		} else {
			return search(element, root.right);
		}
	}

	private Node minimum(Node tree) {
		Node currentNode = tree;
		while (currentNode.left != null) {
			currentNode = currentNode.left;
		}
		return currentNode;
	}

	// logic borrowed from stack overflow
	private void printBinaryTreeTraverse(Node root, int level) {
		if (root == null)
			return;
		printBinaryTreeTraverse(root.right, level + 1);
		if (level != 0) {
			for (int i = 0; i < level - 1; i++)
				System.out.print("|\t");
			System.out.println("|-------" + root.key);
		} else
			System.out.println(root.key);
		printBinaryTreeTraverse(root.left, level + 1);
	}

	public void printBinaryTree() {
		printBinaryTreeTraverse(this.root, 0);
	}

	private void printTraverse(Node tree) {
		if (tree == null) {
			return;
		}
		printTraverse(tree.left);
		System.out.print(tree.key + ", ");
		printTraverse(tree.right);
	};

	public void print() {
		System.out.print("[");
		printTraverse(this.root);
		System.out.println("]\n");
	}
	

	private int heightTraverse(Node node)
	    {
	        if (node == null)
	            return 0;
	        else
	        {
	            int leftHegiht = heightTraverse(node.left);
	            int rightHegiht  = heightTraverse(node.right);
	  
	            if (leftHegiht > rightHegiht )
	                return (leftHegiht + 1);
	             else
	                return (rightHegiht  + 1);
	        }
	    }
	
	public int height() {
		return heightTraverse(this.root);
	}

	
	// -------------------------------------------------------
	// SET OPERATIONS
	// -------------------------------------------------------

	
	// ADD
	public boolean add(E element) {
		Node parent = null;
		Node currentNode = this.root;
		while (currentNode != null) {
			parent = currentNode;
			// if element already in the set, return
			if (element == currentNode.key) {
				return false;
			} else if ((int) element < (int) currentNode.key) {
				currentNode = currentNode.left;
			} else {
				currentNode = currentNode.right;
			}
		}
		Node newNode = new Node();
		newNode.parent = parent;
		newNode.key = element;

		// if adding into an empty tree
		if (parent == null) {
			this.root = newNode;
		} else if ((int) element < (int) parent.key) {
			parent.left = newNode;
		} else {
			parent.right = newNode;
		}
		this.size++;
		return true;
	}

	// REMOVE
	public boolean remove(E element) {
		Node removedNode = search(element, this.root);
		// return false if not present
		if (removedNode == null) {
			return false;
		}
		// has no left child (can be a leaf)
		if (removedNode.left == null) {
			transplant(removedNode, removedNode.right);
		}
		// has left child but no right
		else if (removedNode.right == null) {
			transplant(removedNode, removedNode.left);
		} else {
			Node minOfRightSubtree = minimum(removedNode.right);
			// if the minimum is the right child of removed node, transplant and update
			// pointers
			if (!(minOfRightSubtree.parent == removedNode)) {
				transplant(minOfRightSubtree, minOfRightSubtree.right);
				minOfRightSubtree.right = removedNode.right;
				minOfRightSubtree.right.parent = minOfRightSubtree;
			}
			transplant(removedNode, minOfRightSubtree);
			minOfRightSubtree.left = removedNode.left;
			removedNode.left.parent = minOfRightSubtree;
		}
		this.size--;
		return true;
	}

	
	// IS ELEMENT
	public boolean isElement(E element) {
		Node foundElement = search(element, this.root);
		return !(foundElement == null);
	}

	public boolean isElement(Node node) {
		Node foundElement = search(node.key, this.root);
		return !(foundElement == null);
	}
	
	
	// IS EMPTY
	public boolean isEmpty() {
		return this.root == null;
	}

	// SIZE
	public int size() {
		// other option is get the size dynamically each time by traversing the tree and
		// counting
		// storing size is much more time efficient, and only a little less memory
		// efficient
		return this.size;
	}

	// UNION
	private void unionTraverse(Node node, DynamicSetTree<E> union) {
		if (node == null) {
			return;
		}
		unionTraverse(node.left, union);
		union.add(node.key);
		unionTraverse(node.right, union);
	}
	
	public DynamicSetTree<E> union(DynamicSetTree<E> otherSet) {
		DynamicSetTree<E> union = new DynamicSetTree<>();
		// add all elements of this set
		unionTraverse(this.root, union);
		// add all elements of the other set 
		unionTraverse(otherSet.root, union);
		return union;
	}

	
	// INTERSECTION
	private void intersectionTraverse(Node node, DynamicSetTree<E> otherSet, DynamicSetTree<E> intersection) {
		if (node == null) {
			return;
		}
		intersectionTraverse(node.left, otherSet, intersection);
		if (otherSet.isElement(node)) {
			intersection.add(node.key);
		}
		intersectionTraverse(node.right, otherSet, intersection);
	}

	public DynamicSetTree<E> intersection(DynamicSetTree<E> otherSet) {
		DynamicSetTree<E> intersection = new DynamicSetTree<>();
		intersectionTraverse(this.root, otherSet, intersection);
		return intersection;
	}

	// DIFFERENCE
	private void differenceTraverse(Node node, DynamicSetTree<E> otherSet, DynamicSetTree<E> intersection) {
		if (node == null) {
			return;
		}
		differenceTraverse(node.left, otherSet, intersection);
		if (!otherSet.isElement(node)) {
			intersection.add(node.key);
		}
		differenceTraverse(node.right, otherSet, intersection);
	}

	public DynamicSetTree<E> difference(DynamicSetTree<E> otherSet) {
		DynamicSetTree<E> difference = new DynamicSetTree<>();
		differenceTraverse(this.root, otherSet, difference);
		return difference;
	}

	// SUBSET
	private void subsetTraverse(Node node, DynamicSetTree<E> otherSet) {
		if (node == null) {
			return;
		}
		subsetTraverse(node.left, otherSet);
		if (!otherSet.isElement(node)) {
			otherSet.isSubset = false;
		}
		subsetTraverse(node.right, otherSet);
	}


	public boolean subset(DynamicSetTree<E> otherSet) {
		subsetTraverse(this.root, otherSet);
		boolean temp = otherSet.isSubset;
		otherSet.isSubset = true;
		return temp;
	}

}