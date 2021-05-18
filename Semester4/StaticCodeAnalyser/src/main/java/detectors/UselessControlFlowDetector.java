package detectors;

import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * This is a visitor that detects Useless control flows
 *
 * @author Juraj Bublinec
 * ID: 2482963B
 */

public class UselessControlFlowDetector extends VoidVisitorAdapter<Breakpoints> {

	// Utils
	public List<Node> removeFirst(List<Node> list) {
		if (list.size() > 0) {
			list = list.subList(1, list.size());
		}
		return list;
	}

	private boolean isUseless(List<Node> nodes) {
		// if any of the nodes is not comment, return false
		for (int i = 0; i < nodes.size(); i++) {
			if (!(nodes.get(i) instanceof Comment)) {
				return false;
			}
		}
		return true;
	}

	private boolean isUseless(Node block) {
		return isUseless(block.getChildNodes());
	}

	private boolean checkStmt(Node node, Breakpoints breakpoints) {
		if (isUseless(node)) {
			addBreakpoint(node, breakpoints);
			return true;
		}
		return false;
	}

	private boolean checkStmtRemoveFirst(Node node, Breakpoints breakpoints) {
		List<Node> childNodes = removeFirst(node.getChildNodes());
		if (isUseless(childNodes)) {
			addBreakpoint(node, breakpoints);
			return true;
		}
		return false;
	}

	private void addBreakpoint(Node node, Breakpoints breakpoints) {
		int startLine = node.getRange().get().begin.line;
		int endLine = node.getRange().get().end.line;
		breakpoints.add(startLine, endLine);
	}

	@Override
	public void visit(IfStmt ifstmt, Breakpoints breakpoints) {
		checkStmt(ifstmt.getThenStmt(), breakpoints);
		Statement elseStmt = ifstmt.getElseStmt().orElse(null);
		if (elseStmt != null) {
			checkStmt(elseStmt, breakpoints);
		}
		super.visit(ifstmt, breakpoints);
	}

	@Override
	public void visit(ForStmt forStmt, Breakpoints breakpoints) {
		checkStmt(forStmt.getBody(), breakpoints);
		super.visit(forStmt, breakpoints);
	}

	@Override
	public void visit(DoStmt doStmt, Breakpoints breakpoints) {
		checkStmt(doStmt.getBody(), breakpoints);
		super.visit(doStmt, breakpoints);
	}

	@Override
	public void visit(WhileStmt whileStmt, Breakpoints breakpoints) {
		checkStmt(whileStmt.getBody(), breakpoints);
		super.visit(whileStmt, breakpoints);
	}

	@Override
	public void visit(SwitchStmt switchStmt, Breakpoints breakpoints) {
		// check if the switch is empty
		boolean empty = checkStmtRemoveFirst(switchStmt, breakpoints);
		// check each case
		if (!empty) {
			List<Node> caseNodes = removeFirst(switchStmt.getChildNodes());
			caseNodes.forEach(node -> {
				if (!(node instanceof Comment)) {
					checkStmtRemoveFirst(node, breakpoints);
				}
			});
		}
		super.visit(switchStmt, breakpoints);
	}

	@Override
	public void visit(ClassOrInterfaceDeclaration classOrInterface, Breakpoints breakpoints) {
		breakpoints.setCurrentClassName(classOrInterface.getNameAsString());
		super.visit(classOrInterface, breakpoints);
	}

	@Override
	public void visit(MethodDeclaration method, Breakpoints breakpoints) {
		breakpoints.setCurrenMethodName(method.getNameAsString());
		super.visit(method, breakpoints);
	}
}
