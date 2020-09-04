package cse2010.homework5;

import java.util.Stack;

public class SyntaxTree extends LinkedBinaryTree<String> {

	// check : String -> number / operator
	private static boolean numChecker(String input) {
		boolean check = true;
		for(char c : input.toCharArray()) {
			if(c>=48&&c<=57) {
				continue;
			} else {
				check = false;
				break;
			}
		}
		return check;
	}
	
	// build SyntaxTree
    public static SyntaxTree buildSyntaxTree(String[] expr) {
    	Stack<SyntaxTree> syntaxStack = new Stack<>();
    	 
    	for(int i=0; i<expr.length; i++) {
        	SyntaxTree temp = new SyntaxTree();
    		if (numChecker(expr[i])) {
    			temp.addRoot(expr[i]);
        		syntaxStack.push(temp);
    		} else {
    			LinkedBinaryTree<String> right = syntaxStack.pop();
    			LinkedBinaryTree<String> left = syntaxStack.pop();
    			Node<String> p = temp.addRoot(expr[i]);
    			temp.attach(p, left, right);
        		syntaxStack.push(temp);
    		}	
    	}
    	return syntaxStack.pop();
    }

    // parenthesize helper
    private String pString = "";
    private void pHelper(Node<String> n){
    	if (n.getLeft()!=null) {
    		pString = pString.concat("(");
    		pHelper(n.getLeft());
    	}
    	if (numChecker(n.getElement())) {
    		pString = pString.concat(n.getElement());
    	} else {
    		pString = pString.concat(" "+n.getElement()+" ");
    	}
    	
    	if (n.getRight()!=null) {
    		pHelper(n.getRight());
    		pString = pString.concat(")");
    	}
    }
    
    // make parenthesize expression
    public String parenthesize() {
    	pHelper(root());
    	String result = pString;
    	pString = "";
    	return result;
    }

    // evaluate helper
    private double eDouble = 0;
    private double eHelper(Node<String> n) {
    	if (n.getLeft() == null && n.getRight() == null) {
    		String s = n.getElement();
    		double num = Integer.parseInt(s);
    		return num;
    	}
    	else {
    		String op = n.getElement();
    		double x = eHelper(n.getLeft());
    		double y = eHelper(n.getRight());
    		if (op.equals("+")) {
				eDouble = x+y;
			} else if (op.equals("-")) {
				eDouble = x-y;
			} else if (op.equals("*")) {
				eDouble = x*y;
			} else if (op.equals("/")) {
				eDouble = x/y;
			} else {
				return 0;
			}
    	}
		return eDouble;
    }
    
    // calculate function
    public double evaluate() {
        double result = eHelper(root());
        eDouble = 0;
        return result;
    }

    // prefix helper
    private String pfString = "";
    private void pfHelper(Node<String> v) {
    	if (isRoot(v)) {
    		pfString = pfString.concat(v.getElement());
    	} else {
    		pfString = pfString.concat(" "+v.getElement());
    	}
    	if(v.getLeft() != null) {
    		pfHelper(v.getLeft());
    	}
    	if(v.getRight() != null) {
    		pfHelper(v.getRight());
    	}
    	
    }
    
    // print prefix function
    public String toPrefix() {
        pfHelper(root());
        String result = pfString;
        pfString = "";
    	return result;
    }

    // indentSyntaxTree helper
    private String blank(Node<String> v) {
    	String blank = "";
    	for (int i=0; i<depth(v); i++) {
    		blank = blank.concat("  ");
    	}
		return blank;
    	
    }   
    private String iString = "";
    private void iHelper(Node<String> v) {
    	iString = iString+blank(v);
    	iString = iString+v.getElement()+"\n";
    	if(v.getLeft() != null) {
    		iHelper(v.getLeft());
    	}
    	if(v.getRight() != null) {
    		iHelper(v.getRight());
    	}
    	
    }
    
    // show tree
    public String indentSyntaxTree() {
        iHelper(root());
        String result = iString;
        iString = "";
        return result;
    }

    public static void main(String... args) {

        System.out.println("Homework 5");
    }
}


