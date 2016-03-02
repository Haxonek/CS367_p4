///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Scheduler.java
// File:             Event.java
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
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Event class holds each event and the general data within it; mostly setter
 * and getter methods
 *
 * @author Thomas Hansen
 */
public class Event implements Interval{
	String name, resource, organization, description;
	long start, end;
	
	/**
	 * The constructor for the Event class
	 * 
	 * @param start
	 * @param end
	 * @param name
	 * @param resource
	 * @param organization
	 * @param description
	 */
	Event(long start, long end, String name, String resource, String organization, String description){
		this.start = start;
		this.end = end;
		this.name = name;
		this.resource = resource;
		this.organization = organization;
		this.description = description;
		if (start > end || resource == null) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * returns the start long of the Event
	 * 
	 * @return time of event start
	 */
	@Override
	public long getStart(){
		return start;
	}

	/**
	 * returns the end long of the Event
	 * 
	 * @return time of event end
	 */
	@Override
	public long getEnd(){
		return end;
	}

	/**
	 * returns the name of the Event
	 * 
	 * @return the Event name
	 */
	public String getName() {
		return name;
	}

	/**
	 * returns the resource of the Event
	 * 
	 * @return the Event resource
	 */
	public String getResource() {
		return resource;	
	}
	
	/**
	 * returns the organization of the Event
	 * 
	 * @return the Event organization
	 */
	public String getOrganization(){
		return organization;
	}

	/**
	 * returns the description of the Event
	 * 
	 * @return the Event description
	 */
	public String getDescription() {
		return description;	
	}

	/**
	 * returns the name of the Event
	 * 
	 * @return the Event name
	 */
	@Override
	public int compareTo(Interval i) {
		if (i.getStart() < start) {
			return -1;
		} else {
			return 1;
		}
	}
	
	/**
	 * returns true if the start times of each event are equivelent
	 * 
	 * @param the Event your comparing
	 * @return true if start times equal
	 */
	public boolean equals(Event e) {
		return e.getStart() == start;
	}

	/**
	 * returns true if the two Interval Objects overlap
	 * 
	 * @return true if the two objects overlap in time
	 */
	@Override
	public boolean overlap(Interval i) {
		//So if and end is after the begining while the start is before the end
		// Or if the start is before the end whie the end is after the start
		if (i.getEnd() > start && i.getEnd() < end) {
			// the end exists within the interval
			return true;
		} else if (i.getStart() < end && i.getStart() > start) {
			// the start exist within the interval
			return true;
		} else if (i.getStart() < start && i.getEnd() > end) {
			// the start and end go around the interval
			return true;
		}
		return false;
	}
	
	/**
	 * returns a string of the Event formatted to Skrentny's specifications
	 * 
	 * @return string of Event details
	 */
	@Override
	public String toString(){
//		<Event name>
//		By: <Organization name>
//		In: <Name of resource>
//		Start: <Start of event in mm/dd/yyyy,hh:mm>
//		End: <End of event in mm/dd/yyyy,hh:mm>
//		Description: <Description of event>
		String print = new String();
		
		print = name + "\n";
		print += "By: " + organization + "\n";
		print += "In: " + resource + "\n";
		print += "Start: " + getDate(start) + "\n";
		print += "End: " + getDate(end) + "\n";
		print += "Description: " + description;//  + "\n";
		
		return print;
	}
	
	/**
	 * returns the date in MM/dd/yyyy,HH:mm format
	 * 
	 * @param d the long used to store the date
	 * @return a String of the date in MM/dd/yyyy,HH:mm
	 */
	private String getDate(long d) {
		d *= 1000;
		Date date = new Date(d);		
		return new SimpleDateFormat("MM/dd/yyyy,HH:mm").format(date);
	}
}






