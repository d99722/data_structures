package cse2010.homework2;

import java.util.Arrays;
import java.util.stream.Collectors;

/*
 * © 2020 CSE2010 HW #2
 *
 * You can freely modify this class except the signatures of the public methods!!
 */
public class DLinkedPolynomial implements Polynomial {
	
    private DLinkedList<Term> list = null;

    public DLinkedPolynomial() {
        list = new DLinkedList<>();
    }

    public int getDegree() {
    	if (list.isEmpty()) {
    		return 0;
    	}
    	return list.getFirstNode().getInfo().expo;
    }

    @Override
    public double getCoefficient(final int expo) {
    	if (list.isEmpty()) {
    		return 0;
    	}
        Node<Term> term = list.find(new Term(0.0, expo), Term::compareExponents);
        return term.getInfo().coeff;
    }

    private Term addTerms(Term x, Term y) {
        return new Term(x.coeff + y.coeff, x.expo);
    }

    @Override
    public Polynomial add(final Polynomial p) {
        Polynomial addResult = new DLinkedPolynomial();
        DLinkedPolynomial castThis = (DLinkedPolynomial) this;
        DLinkedPolynomial castP = (DLinkedPolynomial) p;

        if(castP.list.isEmpty()) {
        	return this;
        }
        
        if (castThis.list.isEmpty()) {
        	return p;
        }
        
        Node<Term> curA = castThis.list.getFirstNode();
        Node<Term> curB = castP.list.getFirstNode();
        
        while (curA!=null && curB!=null) {
        	int testNum = Term.compareExponents(curA.getInfo(), curB.getInfo());
       	        	
        	if(testNum == 1) {
        		addResult.addTerm(curA.getInfo().coeff, curA.getInfo().expo);
        		curA = list.getNextNode(curA);
        	} else if (testNum == -1) {
        		addResult.addTerm(curB.getInfo().coeff, curB.getInfo().expo);
        		curB = list.getNextNode(curB);
        	} else {
        		Term newInfo = addTerms(curA.getInfo(), curB.getInfo());
        		addResult.addTerm(newInfo.coeff, newInfo.expo);
        		curA = castThis.list.getNextNode(curA);
        		curB = castP.list.getNextNode(curB);
        	}
        	if (curA != null || curB != null) {
        		if(curA==null) {
            		addResult.addTerm(curB.getInfo().coeff, curB.getInfo().expo);
            		curB = castP.list.getNextNode(curB);
            	}
            	if(curB==null) {
            		addResult.addTerm(curA.getInfo().coeff, curA.getInfo().expo);
            		curA = castThis.list.getNextNode(curA);
            	}
        	}
        	
        }
        return addResult;
    }

    private Term multTerms(Term x, Term y) {
        return new Term(x.coeff * y.coeff, x.expo + y.expo);
    }

    @Override
    public Polynomial mult(final Polynomial p) {
        Polynomial multiResult = new DLinkedPolynomial();
        DLinkedPolynomial castP = (DLinkedPolynomial)p; 
        DLinkedPolynomial castThis = (DLinkedPolynomial)this;
      
        Node<Term> curA = castThis.list.getFirstNode();
        Node<Term> curB = castP.list.getFirstNode(); 
        
        while (curA!=null) {
        	while(curB!=null) {
    			Term mulTerm = this.multTerms(curA.getInfo(), curB.getInfo());
    			multiResult.addTerm(mulTerm.coeff, mulTerm.expo);
        		curB = castP.list.getNextNode(curB);
        	}
        	curB = castP.list.getFirstNode();
        	curA = castThis.list.getNextNode(curA);        	
        }
        return multiResult;
    }

    @Override
    public void addTerm(final double coeff, final int expo) {
    	Term info = new Term(coeff, expo);
		Node<Term> n = new Node<Term>(info);
		
    	if (list.isEmpty()) {	
    		list.addFirst(n);
    	} else {
	    	Node<Term> current = list.getFirstNode();
	        while (current != null) {
	        	int TestNum = Term.compareExponents(info, current.getInfo());
	            if (TestNum == 1) {
	            	list.addBefore(current, n);
	            	break;
	            } else if (TestNum == 0) {
	            	Term newInfo = addTerms(info, current.getInfo());
	            	current.setInfo(newInfo);
	            	break;
	            } else {
	            	current = list.getNextNode(current);
	            }
            	if(current == null) {
            		list.addLast(n);
            		break;
            	}
	        }
    	}
    }

    @Override
    public void removeTerm(final int expo) {
        Node<Term> node = list.find(new Term(0, expo), Term::compareExponents);
        if (node == null) {
        	throw new NoSuchTermExistsException();
        }
        list.remove(node);
    }

    @Override
    public int termCount() {
    	if (list.isEmpty()) {
    		return 0;
    	}
        return list.size();
    }

    @Override
    public double evaluate(final double val) {
        double result = 0;
        double sum;
        
        Node<Term> cur = this.list.getFirstNode();
        while(cur!=null) {
        	double curCoeff = cur.getInfo().coeff;
        	double curExp = cur.getInfo().expo;
        	sum = Math.pow(val, curExp) * curCoeff;
        	result = result + sum;
        	cur = list.getNextNode(cur);
        }
        
        return result;
    }

    @Override
    public String toString() {
        if (list.isEmpty())
            return "Empty Polynomial";
        else {
            String[] terms = new String[termCount()];
            int i = 0;
            Node<Term> current = list.getFirstNode();
            do {
                if (current.getInfo().expo == 0) {
                    terms[i++] = String.valueOf(current.getInfo().coeff);
                } else if (current.getInfo().expo == 1) {
                    terms[i++] = current.getInfo().coeff + "x";
                } else {
                    terms[i++] = current.getInfo().coeff + "x^" + current.getInfo().expo;
                }
                current = list.getNextNode(current);
            } while (current != null);
            return String.join("+", terms);
        }
    }

}

