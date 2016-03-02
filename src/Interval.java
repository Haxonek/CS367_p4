///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Scheduler.java
// File:             Interval.java
// Semester:         CS367 Fall 2015
//
// Author:           Jim Skrentny
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

public interface Interval extends Comparable<Interval>{
	/**
     * Returns the start of the interval.
     * @return the start
     */
	long getStart();
	
	/**
     * Returns the end of the interval.
     * @return the end
     */
	long getEnd();
	
	/**
     * Returns whether there is an overlap between the two intervals.
     * @return if there is an overlap between the intervals.
     */
	boolean overlap(Interval otherInterval);
}