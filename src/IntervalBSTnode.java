///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Scheduler.java
// File:             IntervalBSTnode.java
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
// Online sources:   http://www.geeksforgeeks.org/interval-tree/ and piazza
//					 a significant amount
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * This class is the main form of node used in the Iterval BST class, it
 * creates a tree of degree two
 * 
 * @author Thomas Hansen
 *
 * @param <K> and extension of Interval ADT, usually an Event
 */
class IntervalBSTnode<K extends Interval> {
	// The following nodes
	private IntervalBSTnode<K> left;
	private IntervalBSTnode<K> right;
	// The data/key for the given node
	private K key;
	/*
	 * maxEnd can be used for pruning searches in the subtree, when looking for
	 * conflicts during addition of the events.
	 * So, based on your maxEnd value for a given node, you can decide whether
	 * to go down that  subtree or not when inserting a new node. For example,
	 * when newNode(start) <= maxEnd && maxEnd <= newNode(end), a conflicting
	 * event is already present in that subtree. This will make your searches
	 * for conflicting event to go faster.
	 * 
	 * However, for the purpose of this assignment, since we don't allow for
	 * overlaps, we do not necessarily need to use the MaxEnd feature of the
	 * IntervalBST, and your program will not necessarily need it to function
	 * well.  However, please do implement it and test it, since we will be
	 * testing it for grading purposes.
	 * */
	private long maxEnd;
 
	/**
	 * Constructs a BST node with the given key value and whose left and right
	 * children are null.
	 * 
	 * @param keyValue The value to be inserted into the node
	 */
    public IntervalBSTnode(K keyValue) {
    	key = keyValue;
    	// Sets the maxEnd to the start of the current Event
    	maxEnd = keyValue.getStart();
    }
    
    /**
     * Constructs a BST node with the given key value, left child, and right
     * child.  maxEnd is used to speed up catching of conflicting times
     * 
     * @param keyValue The value to be inserted into the node
     * @param leftChild The left node
     * @param rightChild The right node
     * @param maxEnd the max possible end for this branch
     */
    public IntervalBSTnode(K keyValue, IntervalBSTnode<K> leftChild, 
    		IntervalBSTnode<K> rightChild, long maxEnd) {
		left = leftChild;
		right = rightChild;
		key = keyValue;
		this.maxEnd = maxEnd;
    }

    /**
     * Returns the key value for this BST node.
     * 
     * @return The key, or K extends Interval
     */
    public K getKey() { 
		return key;
    }
    
    /**
     * Returns the left child for this BST node.
     * 
     * @return The left node
     */
    public IntervalBSTnode<K> getLeft() { 
		return left;
    }
  
    /**
     * Returns the right child for this BST node.
     * 
     * @return the right node
     */
    public IntervalBSTnode<K> getRight() { 
		return right;
    }
 
    /**
     * Returns the maximum end value of the intervals in this node's subtree.
     * 
     * @return the maxEnd time for this tree
     */
    public long getMaxEnd(){
    	return maxEnd;
    }
 
    /**
     * Changes the key value for this node to the one given.
     * 
     * @param newK the new Key for this node
     */
    public void setKey(K newK) { 
		key = newK;
    }
    
    /**
     * Changes the left child for this node to the one given.
     * 
     * @param newL sets the new left node & succucuent subtrees
     */
    public void setLeft(IntervalBSTnode<K> newL) { 
		left = newL;
    }
    
    /**
     * Changes the right child for this node to the one given.
     * 
     * @param newR new node for the right side
     */
    public void setRight(IntervalBSTnode<K> newR) { 
		right = newR;
    }
    
    /**
     * Changes the maxEnd to the updated maximum end in the subtree.
     * 
     * @param newEnd sets a new maxEnd
     */
    public void setMaxEnd(long newEnd) { 
		maxEnd = newEnd;
    }
    
    /**
     * Returns the start time stamp of this interval.
     * 
     * @return the start of the Event
     */
    public long getStart(){ 
		return key.getStart();
	}

    /**
     * Returns the end time stamp of this interval.
     * 
     * @return the end of the Event
     */
    public long getEnd(){
    	return key.getEnd();
	}

    /**
     * Returns the key value of this BST node.
     * 
     * @return returns the data in the node (Key)
     */
    public K getData(){
    	return this.getKey();
    }
}





