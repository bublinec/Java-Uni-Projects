
class DynamicSetsListTest {
	public static void main(String args[]) {
		DynamicSetList A = new DynamicSetList();
		DynamicSetList B = new DynamicSetList();
		DynamicSetList C = new DynamicSetList();
		
		for (int i = 0; i < 10; i++) {
			A.add(i);
		}

		for (int i = 0; i < 20; i += 2) {
			B.add(i);
		}

		C.add(2);
		C.add(4);

		for (int i = 0; i < 6; i++) {
			A.remove(i);
		}

		System.out.println("Dynamic Sets:");
		System.out.println("----------------------");

		System.out.println("Set A: ");
		System.out.println(A);

		System.out.println("Set B: ");
		System.out.println(B);

		System.out.println("Set C: ");
		System.out.println(C);

		System.out.println("After removing elements from A: ");
		System.out.println(A);

		System.out.println("Union B, A: ");
		System.out.println(B.union(A));

		System.out.println("Intersection B, A: ");
		System.out.println(B.intersection(A));

		System.out.println("Difference B, A: ");
		System.out.println(B.difference(A));

		System.out.println("B is subset of A: ");
		System.out.println(B.subset(A));

		System.out.println("C is subset of B: ");
		System.out.println(C.subset(B));

	}
}