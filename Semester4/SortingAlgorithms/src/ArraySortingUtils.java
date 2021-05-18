import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ArraySortingUtils {
//	Note: this could be split into more classes, it would be a better practise
//	however, the aim of this exercise is to practise algorithms not OOP 
//	it would be a overkill for me to split it

	public static void main(String args[]) {

		System.out.println("Quick Sort Algorithms ");
		System.out.println("----------------------");

//		load the input
		List<ArrayList<Integer>> input = getInput("inputFiles");

//		specify sorting algorithms to time
		String[] sortingAlgs = { "insertionSort", "mergeSort", "quickSort", "insertionQuickSort",
				"medianOfThreeQuickSort", "threeWayQuickSort" };

//		specify max array length to apply sorting)
//		DISCLAIMER: high values cause stackoverflow error, 
//		or your CPU might explode and your fan might cause your pc to take off
		int maxLen = 10000;
		int minSubArrayLen = 50;

		try {
//			optimiseInsertionQuickSort(input, minSubArrayLen, maxLen);
			compareSortingAlgorithms(input, sortingAlgs, maxLen);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int optimiseInsertionQuickSort(List<ArrayList<Integer>> input, int maxSubArray, int maxLen)
			throws Exception {
		int best = 1;
		long topTime = 10000000;
		long currentTime;

		for (ArrayList<Integer> array : input) {
			if (array.size() > maxLen) {
				continue;
			}
			System.out.println("\nArray of len: " + array.size());
			System.out.println("--------------------------------------------------");
			for (int i = best; i < maxSubArray; i++) {

				System.out.println("Min Subarray Len: " + i);
				currentTime = timeSorting(convertIntegers(array), "insertionQuickSort");
				if (currentTime < topTime) {
					topTime = currentTime;
					best = i;
				}
			}
		}
		return best;
	}

	public static void compareSortingAlgorithms(List<ArrayList<Integer>> input, String[] sortingAlgs, int maxLen)
			throws Exception {

		for (ArrayList<Integer> array : input) {
//			skip if array is larger than maxLen
			if (array.size() > maxLen) {
				continue;
			}

//			loop over sorting algs and time each one
			System.out.println("\nArray of len: " + array.size());
			System.out.println("--------------------------------------------------");
			for (String sortingAlg : sortingAlgs) {
				timeSorting(convertIntegers(array), sortingAlg);
			}

		}

	}

	public static long timeSorting(int[] arr, String sortingAlg) throws Exception {

		long startTime = System.nanoTime();
//		could be done nicer with polymorphism/passing function
//		would be overkill for this project
//		also, switch might add some nanosec, that's ok, we are interested in relative times
		switch (sortingAlg) {
		case "insertionSort":
			insertionSort(arr);
		case "mergeSort":
			mergeSort(arr, 0, arr.length - 1);
		case "quickSort":
			quickSort(arr, 0, arr.length - 1);
		case "insertionQuickSort":
			insertionQuickSort(arr, 5);
		case "medianOfThreeQuickSort":
			medianOfThreeQuickSort(arr, 0, arr.length - 1);
		case "threeWayQuickSort":
			threeWayQuickSort(arr, 0, arr.length - 1);
		}
		long endTime = System.nanoTime();

//		check if sorted
		if (!isSorted(arr)) {
			System.out.println(arr);
			throw new Exception(sortingAlg + " not working properly!");
		}
		long duration = (endTime - startTime) / 1000;

		System.out.printf("%-23s %23s ms %n", sortingAlg, duration);
		return duration;
	}

	private static int medianOfThree(int[] arr, int a, int b, int c) {
//		take three indices of array, and return index of median of the three
		int x = arr[a] - arr[b];
		int y = arr[b] - arr[c];
		int z = arr[a] - arr[c];
		if (x * y > 0)
			return b;
		if (x * z > 0)
			return c;
		return a;
	}

	public static ArrayList<ArrayList<Integer>> getInput(String dirName) {
//		a utility to get the list of arrays from files 
		ArrayList<ArrayList<Integer>> input = new ArrayList<ArrayList<Integer>>();

		File dir = new File(dirName);
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				ArrayList<Integer> arr = new ArrayList<Integer>();
				try {
					Scanner myReader = new Scanner(child);
					while (myReader.hasNextLine()) {
						String data = myReader.nextLine();
						arr.add(Integer.parseInt(data));
					}
					myReader.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				input.add(arr);
			}
		} else {
			System.out.println("Input directory is empty!");
		}

		return input;
	}

//	convert List to a int array
	public static int[] convertIntegers(List<Integer> integers) {
		int[] ret = new int[integers.size()];
		Iterator<Integer> iterator = integers.iterator();
		for (int i = 0; i < ret.length; i++) {
			ret[i] = iterator.next().intValue();
		}
		return ret;
	}

	private static void swap(int[] arr, int a, int b) {
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}

	public static boolean isSorted(int a[]) {
		int n = a.length;
		for (int i = 0; i < n - 1; i++) {
			if (a[i] > a[i + 1]) {
				return false;
			}
		}
		return true;
	}

	public static void insertionSort(int arr[]) {
		int n = arr.length;
		for (int j = 1; j < n; j++) {
			int key = arr[j];
			int i = j - 1;
			while ((i >= 0) && (arr[i] > key)) {
				arr[i + 1] = arr[i];
				i--;
			}
			arr[i + 1] = key;
		}
	}

	private static void merge(int a[], int p, int q, int r) {
		int n1 = q - p + 1;
		int n2 = r - q;
		int[] L = new int[n1 + 1];
		int[] R = new int[n2 + 1];

		for (int i = 0; i < n1; i++)
			L[i] = a[p + i];
		for (int j = 0; j < n2; j++)
			R[j] = a[q + 1 + j];
		L[n1] = Integer.MAX_VALUE;
		R[n2] = Integer.MAX_VALUE;

		int i = 0;
		int j = 0;
		for (int k = p; k <= r; k++) {
			if (L[i] <= R[j]) {
				a[k] = L[i];
				i++;
			} else {
				a[k] = R[j];
				j++;
			}
		}
	}

	public static void mergeSort(int a[], int p, int r) {
		if (p < r) {
			int q = (p + r) / 2;
			mergeSort(a, p, q);
			mergeSort(a, q + 1, r);
			merge(a, p, q, r);
		}
	}

//	QUICK SORT VARIANTS 
	public static int partition(int[] arr, int startIndex, int pivotIndex) {
		int pivot = arr[pivotIndex];
		int greenUpperBound = startIndex - 1;

		for (int j = startIndex; j < pivotIndex; j++) {
			// if is smaller/equal to pivot, swap with the first after green partition
			if (arr[j] <= pivot) {
				swap(arr, ++greenUpperBound, j);
			}
		}
		// swap pivot with the first after green partition
		swap(arr, ++greenUpperBound, pivotIndex);
		return greenUpperBound;
	}

	public static void quickSort(int[] arr, int greenLowerBound, int pivotIndex) {
		if (greenLowerBound < pivotIndex) {
			int greenUpperBound = partition(arr, greenLowerBound, pivotIndex);
			quickSort(arr, greenLowerBound, greenUpperBound - 1);
			quickSort(arr, greenUpperBound + 1, pivotIndex);
		}
	}

	public static void approxQuickSort(int[] arr, int greenLowerBound, int pivotIndex, int k) {
		int subArrayLen = pivotIndex - greenLowerBound + 1;
		if (subArrayLen <= k) {
			return;
		} else if (greenLowerBound < pivotIndex) {
			int greenUpperBound = partition(arr, greenLowerBound, pivotIndex);
			approxQuickSort(arr, greenLowerBound, greenUpperBound - 1, k);
			approxQuickSort(arr, greenUpperBound + 1, pivotIndex, k);
		}
	}

	public static void insertionQuickSort(int[] arr, int k) {
		approxQuickSort(arr, 0, arr.length - 1, k);
		insertionSort(arr);
	}

	public static void medianOfThreeQuickSort(int[] arr, int greenLowerBound, int pivotIndex) {
//		find the index of median from three indices
		int middle = (greenLowerBound + pivotIndex) % 2;
		int medianIndex = medianOfThree(arr, greenLowerBound, middle, pivotIndex);

//		swap the median with the last element - which is used as pivot 
		swap(arr, medianIndex, pivotIndex);

		if (greenLowerBound < pivotIndex) {
			int greenUpperBound = partition(arr, greenLowerBound, pivotIndex);
			quickSort(arr, greenLowerBound, greenUpperBound - 1);
			quickSort(arr, greenUpperBound + 1, pivotIndex);
		}

	}

	public static int[] threeWayPartition(int[] arr, int startIndex, int pivotIndex) {
		int pivot = arr[pivotIndex];
		int greenUpperBound = startIndex - 1;
		int blueLowerBound = pivotIndex;
		int i = startIndex;

		// iterate over the array while current index is lower than blue lower bound
		while (i < blueLowerBound) {

			// if higher than pivot, put it to the blue partition from the left
			if (arr[i] > pivot) {
				swap(arr, i, --blueLowerBound);
			}
			// if lower than pivot, put it to the left partition from the right, move on
			else if (arr[i] < pivot) {
				swap(arr, i, ++greenUpperBound);
				i++;
			}
			// if equal, just move on
			else {
				i++;
			}
		}

		// swap pivot with the first one in the blue partition
		swap(arr, pivotIndex, blueLowerBound);

		int[] bounds = { greenUpperBound, blueLowerBound };
		return bounds;
	}

	public static void threeWayQuickSort(int[] arr, int greenLowerBound, int pivotIndex) {
		if (greenLowerBound < pivotIndex) {
			int[] bounds = threeWayPartition(arr, greenLowerBound, pivotIndex);
			int greenUpperBound = bounds[0];
			int blueLowerBound = bounds[1];
			threeWayQuickSort(arr, greenLowerBound, greenUpperBound);
			threeWayQuickSort(arr, blueLowerBound + 1, pivotIndex);
		}
	}

//	PATHOLOGICAL INPUT GENERATOR - not finished 
	public static int[] generateArray(int len) {
		Random random = new Random();
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = random.nextInt();
		}
		return arr;
	}
}
