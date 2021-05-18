package detectors;

/**
 * This is a class that stores single occurrence of the behaviour defined in
 * visitor.
 *
 * @author Juraj Bublinec
 * ID: 2482963B
 */

public class Breakpoint {
	private String className;
	private String methodName;
	private int startLine;
	private int endLine;

	public Breakpoint(String className, String methodName, int startLine, int endLine) {
		this.className = className;
		this.methodName = methodName;
		this.startLine = startLine;
		this.endLine = endLine;
	}

	@Override
	public String toString() {
		return className + "|" + methodName + "|" + startLine + ":" + endLine;
	}

	public String toStringVerbose() {
		return ("className=" + className + "," + "methodName=" + methodName + "," + "startline=" + startLine + ","
				+ "endline=" + endLine);
	}
}