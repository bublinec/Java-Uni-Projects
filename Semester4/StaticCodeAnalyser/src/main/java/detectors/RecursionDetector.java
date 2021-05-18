package detectors;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * This is a visitor that detects polymorphic recursion.
 *
 * @author Juraj Bublinec
 * ID: 2482963B
 */

public class RecursionDetector extends VoidVisitorAdapter<Breakpoints> {
	@Override
	public void visit(MethodDeclaration md, Breakpoints breakpoints) {
		breakpoints.setCurrenMethodName(md.getNameAsString());
		super.visit(md, breakpoints);
	}

	@Override
	public void visit(MethodCallExpr mc, Breakpoints breakpoints) {
		// check if recursion
		if (mc.getNameAsString().equals(breakpoints.getCurrenMethodName())) {
			breakpoints.add(mc.getRange().get().begin.line, mc.getRange().get().begin.line);
		}
		super.visit(mc, breakpoints);
	}

	@Override
	public void visit(ClassOrInterfaceDeclaration classOrInterface, Breakpoints breakpoints) {
		breakpoints.setCurrentClassName(classOrInterface.getNameAsString());
		super.visit(classOrInterface, breakpoints);
	}

}