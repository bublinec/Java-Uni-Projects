package detectors;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

/**
 * This is a Client class for static code analyser.
 *
 * @author Juraj Bublinec 
 * ID: 2482963B
 */

public class Driver {

	public static void main(String args[]) {
		try {
			String filePath = args[0];
			CompilationUnit cu = JavaParser.parse(new FileInputStream(filePath));

			VoidVisitor<Breakpoints> UCFDVisitor = new UselessControlFlowDetector();
			Breakpoints breakpointsUCFD = new Breakpoints();
			UCFDVisitor.visit(cu, breakpointsUCFD);

			VoidVisitor<Breakpoints> RDVisitor = new RecursionDetector();
			Breakpoints breakpointsRD = new Breakpoints();
			RDVisitor.visit(cu, breakpointsRD);

			if (args.length == 2 && args[1].equals("-v")) {
				System.out.println("Useless Control Flows:");
				breakpointsUCFD.printVerbose();
				System.out.println("Recursion:");
				breakpointsRD.printVerbose();

			} else {
				System.out.println("Useless Control Flows:");
				breakpointsUCFD.print();
				System.out.println("Recursion:");
				breakpointsRD.print();
			}

		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File not found, please provide a valid path.");
		} catch (ParseProblemException e) {
			System.out.println("ERROR: The java source code file provided contains an error.");
		} catch (Exception e) {
			System.out.println("ERROR: Unknown Error: " + e);
		}
	}
}