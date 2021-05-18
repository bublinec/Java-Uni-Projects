package detectors;

import java.util.ArrayList;
import java.util.List;

/**
 * This is container class that collects breakpoints.
 *
 * @author Juraj Bublinec
 * ID: 2482963B
 */

public class Breakpoints {
	List<Breakpoint> breakpointsList;
	String currentClassName;
	String currentMethodName;

	public Breakpoints() {
		this.breakpointsList = new ArrayList<Breakpoint>();
	}

	public void add(int startLine, int endLine) {
		Breakpoint breakpoint = new Breakpoint(currentClassName, currentMethodName, startLine, endLine);
		breakpointsList.add(breakpoint);
	}

	public void setCurrentClassName(String className) {
		this.currentClassName = className;
	}

	public void setCurrenMethodName(String methodName) {
		this.currentMethodName = methodName;
	}
	
	public String getCurrenMethodName() {
		return this.currentMethodName;
	}

	public void print() {
		this.breakpointsList.forEach(bp -> {
			System.out.println(bp);
		});
	}

	public void printVerbose() {
		this.breakpointsList.forEach(bp -> {
			System.out.println(bp.toStringVerbose());
		});
	}

}
