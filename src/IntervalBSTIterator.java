///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Scheduler.java
// File:             IntervalBSTIterator.java
// Semester:         CS367 Fall 2015
//
// Author:           Thomas Hansen thansen8@wisc.edu
// CS Login:         thansen
// Lecturer's Name:  Jim Skrentny
// Lab Section:      none
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     n/a
// Email:            n/a
// CS Login:         n/a
// Lecturer's Name:  n/a
// Lab Section:      n/a
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          none
//
// Online sources:   none
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.*;

/**
 * The iterator for the ItervalBST
 *
 * @author Thomas Hansen
 */
public class IntervalBSTIterator<K extends Interval> implements Iterator<K> {

	private Stack<IntervalBSTnode<K>> stack; //for keeping track of nodes
	
	/**
	 * General constructor for the IntervalBSTIterator class
	 * 
	 * @param root The first item in the tree which will be iterable
	 */
	public IntervalBSTIterator(IntervalBSTnode<K> root) {
		stack = new Stack<IntervalBSTnode<K>>();
		
		inOrder(root);
	}
	
	/**
	 * adds the items recursevly in the correct order
	 * 
	 * @param cur the current node
	 */
	private void inOrder(IntervalBSTnode<K> cur) {
		if (cur == null) return;
		// Checks right branch, adds value and then checks left branch
		inOrder(cur.getRight());
		stack.push(cur);
		inOrder(cur.getLeft());
	}
	
	/**
	 * returns if there is a next value
	 * 
	 * @return true if next value exists
	 */
    public boolean hasNext() {
    	// if it is empty we don't have a next val
		return !stack.isEmpty();
    }

    /**
     * Returns the next item in the tree
     * 
     * @return the next Event/Other Object
     */
    public K next() {
		return stack.pop().getData();
    }

    /**
     * an unimplemented method
     */
    public void remove() {
        // DO NOT CHANGE: you do not need to implement this method
        throw new UnsupportedOperationException();
    }
}