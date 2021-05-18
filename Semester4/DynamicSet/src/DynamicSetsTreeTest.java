
class DynamicSetsTreeTest {
	public static void main(String args[]) {
		System.out.println("Dynamic Set Binary Search Tree:");
		System.out.println("----------------------");
		
		DynamicSetTree A = new DynamicSetTree();
		DynamicSetTree B = new DynamicSetTree();
		DynamicSetTree C = new DynamicSetTree();

		// populate
		Object[] setA = {8, 4, 9, 3, 5, 12, 7, 6};
		A.addArray(setA);
		
		System.out.println("\n\nSet A: ");
		B.printBinaryTree();
		A.print();
		
		System.out.println("\n\n7 is element of set A: " + A.isElement(7));
		System.out.println("120 is element of set A: " + A.isElement(120));
		
		int toRemove = 8;
		System.out.println("\n\nSet A with removed element " + toRemove);
		A.remove(toRemove);
		A.printBinaryTree();
		A.print();

		
		
		System.out.println("Set B: ");
		Object[] setB = {8, 4, 12, 3, 5, 9, 7, 6};
		System.out.println("Set B is empty: " + B.isEmpty());
		B.addArray(setB);
		B.printBinaryTree();
		B.print();
		
		toRemove = 8;
		System.out.println("\n\nSet B with removed element " + toRemove);
		B.remove(toRemove);
		B.printBinaryTree();
		B.print();
		System.out.println("Size of set B: " + B.size());
		
		
		C.add(4);
		System.out.print("\nC is a subset of A: " + C.subset(A));
		
		C.add(40);
		System.out.print("\nC is a subset of A: " + C.subset(A));
		

		C.add(1);
		C.add(12);
		C.add(5);
		C.add(100);
		
		System.out.print("\nIntersection C and A: ");
		DynamicSetTree intersection =C.intersection(A);
		intersection.print();
		intersection.printBinaryTree();
		
		System.out.print("\nDifference C and A: ");
		DynamicSetTree difference =C.difference(A);
		C.difference(A).print();
		C.difference(A).printBinaryTree();
		
		
		System.out.println("Union C, A: ");
		DynamicSetTree union = C.union(A);
		union.print();
		union.printBinaryTree();

	}
}