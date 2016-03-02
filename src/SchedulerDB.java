///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Scheduler.java
// File:             SchedulerDB.java
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
import java.util.*;

/**
 * Mostly holds a place for the Resources List, which holds the events.  
 *
 * @author Thomas Hansen
 */
public class SchedulerDB {
	private List<Resource> resources;

	/**
	 * The constructor method for the SchedulerDB class
	 */
	SchedulerDB() {
		resources = new ArrayList<Resource>();
	}

	/**
	 * Adds a resource to the resource list, returns false if it already
	 * exists
	 * 
	 * @param resource The specific type of object to be added
	 * @return true if it's added
	 */
	public boolean addResource(String resource){
		Resource addMe = new Resource(resource);
		if (this.findResource(resource) != null) {
			// return false if the resource already exists
			return false;
		}
		resources.add(addMe);
		return true;
	}

	/**
	 * removes a resource from the list
	 * 
	 * @param r the resource to be removed
	 * @return true if the resource is removed
	 */
	public boolean removeResource(String r){
		Iterator<Resource> itr = resources.iterator();
		for (int i = 0; itr.hasNext(); ++i) {
			if (itr.next().getName().equals(r)) {
				resources.remove(i);
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Adds an event to the correct resource
	 * 
	 * @param r the name of the Resource
	 * @param start the long representing the start date
	 * @param end the long representing the end date
	 * @param name the name of the Event
	 * @param organization the org hosting the Event
	 * @param description The Event description
	 * @return true if the Event is added properly
	 */
	public boolean addEvent(String r, long start, long end, String name, 
			String organization, String description){
		// create the Event to add
		Event addMe = new Event(start, end, name, r, organization,description);

		// find correct resource
		Iterator<Resource> itr = resources.iterator();
		while (itr.hasNext()) {
			Resource cur = itr.next();
			if (cur.getName().equals(r)) {
				// add event to resource
				cur.addEvent(addMe);
				return true;
			}
		}
		return false;
	}

	/**
	 * Deletes a specific Event from a resource
	 * 
	 * @param start the start date
	 * @param resource the name of the resource
	 * @return true if the Event is deleted
	 */
	public boolean deleteEvent(long start, String resource){
		Iterator<Resource> itr = resources.iterator();
		while (itr.hasNext()) {
			Resource cur = itr.next();
			if (cur.getName().equals(resource)) {
				// remove event from resource
				cur.deleteEvent(start);
				// returned value not used; does not agree with error message
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns a list of events for the given resource.  Returns null if 
	 * the resource doesn't exist
	 * 
	 * @param r the name of the Resource instance
	 * @return the Resource or null
	 */
	public Resource findResource(String r){
		Iterator<Resource> itr = resources.iterator();
		while (itr.hasNext()) {
			Resource cur = itr.next();
			if (cur.getName().equals(r)) {
				return cur;
			}
		}

		return null;
	}

	public List<Resource> getResources(){
		return resources;
	}

	/**
	 * Returns a List of all the Events in a specific resource, otherwise
	 * return null if the resource doesn't exist.
	 * 
	 * @param resource the name of the resource you want
	 * @return A List object of the Events in the Resource
	 */
	public List<Event> getEventsInResource(String resource){
		Iterator<Resource> itr = resources.iterator();
		while (itr.hasNext()) {
			Resource cur = itr.next();
			if (cur.getName().equals(resource)) {
				List<Event> returnVal = new LinkedList<Event>();
				Iterator<Event> itrE = cur.iterator();
				while (itrE.hasNext()) {
					returnVal.add(itrE.next());
				}
				return returnVal;
			}
		}

		return null;
	}

	/**
	 * returns a range of Events occurring during a specific range
	 * 
	 * @param start When the range starts
	 * @param end When the range ends
	 * @return The List of Events in the given range
	 */
	public List<Event> getEventsInRange(long start, long end){
		List<Event> results = new LinkedList<Event>();
		Iterator<Resource> itrR = resources.iterator();
		while(itrR.hasNext()) {
			Iterator<Event> itrE = itrR.next().iterator();
			while (itrE.hasNext()) {
				Event curE = itrE.next();
				// add if start is within time frame
				if (curE.getStart() > start && curE.getStart() < end) {
					results.add(curE);
					// add if end is within time frame
				} else if (curE.getEnd() > start && curE.getEnd() < end) {
					results.add(curE);
					// add if surrounds time frame
				} else if (curE.getStart() < start && curE.getEnd() > end) {
					results.add(curE);
				}
			}
		}
		
		
		return results;
	}	

	/**
	 * Returns a List of Events that occur during a specific range of time
	 * and in a given Resource
	 * 
	 * @param start when the range of time starts
	 * @param end when the range of time ends
	 * @param resource The given resource
	 * @return The List<Event> within the time frame and resource
	 */
	public List<Event> getEventsInRangeInResource(long start, long end,
			String resource) {
		resource = resource.trim();
		List<Event> inRange = this.getEventsInRange(start, end);
		Iterator<Event> itr = inRange.iterator();
		List<Event> results = new LinkedList<Event>();
		Event cur;
		
		while (itr.hasNext()) {
			cur = itr.next();
			if (cur.getResource().equals(resource)) {
				results.add(cur);
			}
		}
		return results;
	}

	/**
	 * Returns a List<Event> of all the Events
	 * 
	 * @return A List of all the Events in the Resources
	 */
	public List<Event> getAllEvents() {
		List<Event> results = new LinkedList<Event>();
		Iterator<Resource> itrR = resources.iterator();
		while (itrR.hasNext()) {
			Iterator<Event> itrE = itrR.next().iterator();
			while (itrE.hasNext()) {
				results.add(itrE.next());
			}
		}
		return results;
	}

	/**
	 * Returns all the Events hosted by a given organization
	 * 
	 * @param org the organization
	 * @return A List of Events hosted by the given org
	 */
	public List<Event> getEventsForOrg(String org){
		// removes any white space surrounding the org name
		org = org.trim();
		// sets up the lists of Events and an iterator
		List<Event> results = new LinkedList<Event>();
		List<Event> events = this.getAllEvents();
		Iterator<Event> itr = events.iterator();
		// temp Event
		Event tmp;
		while (itr.hasNext()) {
			tmp = itr.next();
			if (tmp.getOrganization().equals(org)) {
				results.add(tmp);
			}
		}
		return results;
	}
}







