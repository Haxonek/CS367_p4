///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Scheduler.java
// File:             IntervalBST.java
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
import java.util.Iterator;

/**
 * The main class which controlls the BST, contains the insert/delete/size/etc
 * methods
 *
 * @author Thomas Hansen
 */
public class IntervalBST<K extends Interval> implements SortedListADT<K> {
	// holds the root node
	private IntervalBSTnode<K> root;
	private int size;
	// used to see if the delete method deletes the node
	
	/**
	 * The constructor class for IntervalBST
	 */
	public IntervalBST() {
		size = 0;
	}

	/**
	 * the complimentary method in which insert starts off in
	 * 
	 * @param key the value to be inserted
	 */
	public void insert(K key){
		if (key == null) {
			throw new IllegalArgumentException();
		}
		// checks against if the tree's already null
		if (root == null) {
			root = new IntervalBSTnode<K>(key);
			fixMaxEnd(root);
			++size;
		} else {
			if (lookup(key) == null) {
				insert(key, root);
				fixMaxEnd(root);
				++size;
			} else {
				throw new IntervalConflictException();
			}
		}
	}

	/**
	 * The main method that's used to insert values into the BST
	 * 
	 * @param key
	 * @param cur
	 */
	private void insert(K key, IntervalBSTnode<K> cur) {
		// check for Overlap
		if (key.overlap(cur.getKey())) {
			throw new IntervalConflictException();
		}
		// checks to see which side of the tree the node belongs on
		// does not take into account if ==
		if (cur.getKey().compareTo(key) < 0) {
			// left node
			if (cur.getLeft() == null) {
				// check for conflict with maxEnd
				cur.setLeft(new IntervalBSTnode<K>(key));
			} else {
				insert(key, cur.getLeft());
			}
		} else if(cur.getKey().compareTo(key) > 0) {
			// right node
			if (cur.getRight() == null) {
				cur.setRight(new IntervalBSTnode<K>(key));
			} else {
				insert(key, cur.getRight());
			}
		} else {
			throw new IntervalConflictException();
		}
	}
	
	/**
	 * Deletes the given key from the Sorted List. If the key is in the Sorted
	 * List, the key is deleted and true is returned. If the key is not in the
	 * Sorted List, the Sorted List is unchanged and false is returned.
	 * 
	 * @param key a node containing (what's usually) and Event node
	 * @return whether the item was deleted or not found
	 */
	public boolean delete(K key) {
		int orgSize = size;
		root = delete(root, key);
		// fixes the max end value
		fixMaxEnd(root);
		return (size + 1) == orgSize;
	}

	/** 
	 * The main method that performs the delete recursion
	 * 
	 * @param n The current node
	 * @param key the key used to find the node to delete
	 * @return The new root address
	 */
	private IntervalBSTnode<K> delete(IntervalBSTnode<K> n, K key) {
		if (n == null) {
			return null;
		}

		// If the node is found, can't use .equals()
		if (key.getStart() == n.getKey().getStart()) {
			// n is the node to be removed
			// decrement size
			--size;
			if (n.getLeft() == null && n.getRight() == null) {
				return null;
			}
			if (n.getLeft() == null) {
				return n.getRight();
			}
			if (n.getRight() == null) {
				return n.getLeft();
			}
			// Still has two children
			K smallVal = smallest(n.getRight());
			n.setKey(smallVal);
			n.setRight( delete(n.getRight(), smallVal) );
			return n; 
		} else if (key.compareTo(n.getKey()) < 0) {
			n.setLeft( delete(n.getLeft(), key) );
			return n;
		} else {
			n.setRight( delete(n.getRight(), key) );
			return n;
		}
	}

	/**
	 * auxillary method for the delete method, used to find the smallest node
	 * 
	 * @param n The current node
	 * @return the smallest value in the node(s)
	 */
	private K smallest(IntervalBSTnode<K> n) {
	    if (n.getLeft() == null) {
	        return n.getKey();
	    } else {
	        return smallest(n.getLeft());
	    }
	}

	/**
	 * Finds the key in the list of nodes and returns it if found, otherwise
	 * returns null
	 * 
	 * @return the key/data found
	 */
	public K lookup(K key) {
//		if (key == null) {
//			throw new IllegalArgumentException();
//		}
		return lookup(key, root);
	}
	
	/**
	 * The main lookup method, checks to see if the Event(Interval Object)
	 * already exists
	 * 
	 * @param key
	 * @param cur
	 * @return
	 */
	private K lookup(K key, IntervalBSTnode<K> cur) {
		if (cur == null) {
			return null;
		}
		if (key.getStart() == cur.getStart()) {
			// node was found
			return cur.getKey();
		} else if (cur.getKey().compareTo(key) > 0) {
			// right node
			return lookup(key, cur.getRight());
		} else {
			// left node
			return lookup(key, cur.getLeft());
		}
	}

	public int size() {
		return size;
	}

	/**
	 * returns whether the BST is empty or not
	 * 
	 * @return true if the tree contains no children or root
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * returns an iterator that may directly iterate through the BST
	 * 
	 * @return an iterator that returns a generic type
	 */
	public Iterator<K> iterator() {
		return new IntervalBSTIterator<K>(root);
	}

	
	////////////////////////// Max End method /////////////////////////////////
    private long fixMaxEnd(IntervalBSTnode<K> cur) {

    	if (cur.getRight() == null) {
    		return cur.getMaxEnd();
    	}
    	cur.setMaxEnd(fixMaxEnd(cur.getRight()));
    	if (cur.getLeft() != null) {
    		fixMaxEnd(cur.getLeft());
    	}
    	return cur.getMaxEnd();
    }


}