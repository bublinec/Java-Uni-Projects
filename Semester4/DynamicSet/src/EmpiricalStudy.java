import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class EmpiricalStudy {
	public static void main(String args[]) {
		System.out.println("Empirical study on various Dynamic Set ADT implementations");
		System.out.println("----------------------\n");
		
		// get input
		String dirName = "input";
		Object[] inputArr = getInput(dirName).get(0).toArray();
		
		// instantiate
		DynamicSetTree treeSet = new DynamicSetTree<>();
		DynamicSetList listSet = new DynamicSetList<>();
		
		// populate 
		System.out.print("Populating...");
		treeSet.addArray(inputArr);
		listSet.addArray(inputArr);
		System.out.println(" -> DONE");
//		System.out.println("S tree height: " + treeSet.height());
		
		
//		treeSet.printBinaryTree(); // quite cool! 
		
		// generate test array
		System.out.print("Generating test array...");
		int testArrSize = 100;
		int upperRange = 50000;
		int[] testArr = new int[testArrSize]; 
		Random rand =  new Random();
		for(int i=0; i<testArrSize; i++) {
			testArr[i] = rand.nextInt(upperRange); 
		}
		System.out.println(" -> DONE");
		
		// time inserting testArr into both sets
		System.out.print("Testing tree set...");
		long startTime, endTime, durationTreeSet, durationListSet;
		startTime = System.nanoTime();
		for(int i=0; i<testArr.length; i++) {
			treeSet.isElement(testArr[i]);
		}		
//		listSet.size();
		endTime = System.nanoTime();
		durationTreeSet = (endTime - startTime) / 1000; // in microseconds 
		System.out.println(" -> DONE");
		
		System.out.print("Testing list set...");
		startTime = System.nanoTime();
		for(int i=0; i<testArr.length; i++) {
			listSet.isElement(testArr[i]);
		}
//		listSet.size();
		endTime = System.nanoTime();
		durationListSet = (endTime - startTime) / 1000; // in microseconds 
		System.out.println(" -> DONE");
		
		System.out.println("\n\nResults: ");
		System.out.println("----------------------");
		System.out.println("\nTree Implementation:\nTotal time: " + durationTreeSet + " µs\nAverage time: " + (durationTreeSet / 100) + " µs");
		System.out.println("\nList Implementation:\nTotal time: " + durationListSet + " µs \nAverage time: " + (durationListSet / 100) + " µs");		
	}

	// UTILS
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
	
}
