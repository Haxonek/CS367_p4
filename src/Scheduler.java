///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p4
// Files:            Event Interval IntervalBST IntervalBSTnode
//					 IntervalBSTIterator IntervalConflictException Resource
//					 Scheduler SchedulerDB SortedListADT
// Semester:         CS367 Fall 2015
//
// Author:           Thomas Hansen
// Email:            thansen8@wisc.edu
// CS Login:         thansen
// Lecturer's Name:  Jim Skrentny
// Lab Section:      n/a
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
//                   CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//                   If pair programming is allowed:
//                   1. Read PAIR-PROGRAMMING policy (in cs302 policy) 
//                   2. choose a partner wisely
//                   3. REGISTER THE TEAM BEFORE YOU WORK TOGETHER 
//                      a. one partner creates the team
//                      b. the other partner must join the team
//                   4. complete this section for each program file.
//
// Pair Partner:     none
// Email:            none
// CS Login:         none
// Lecturer's Name:  none
// Lab Section:      none
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   must fully acknowledge and credit those sources of help.
//                   Instructors and TAs do not have to be credited here,
//                   but tutors, roommates, relatives, strangers, etc do.
//
// Persons:          none
//
// Online sources:   none
//////////////////////////// 80 columns wide //////////////////////////////////
import java.io.File;
import java.io.FileNotFoundException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * The main class for the entire application; where the file is uploaded, 
 * sorted and the input for the commands are taken
 *
 * @author Thomas Hansen
 */
public class Scheduler {
	// Holds all of the Resources which in turn hold all of the Events
	private static SchedulerDB schedulerDB = new SchedulerDB();
	// The scanner used for the general GUI menu
	private static Scanner scanner = null;
	
	/**
	 * The main class for Scheduler.java and where the entire program begins
	 * 
	 * @param args The file containing all of the current Events/Resources
	 */
	public static void main(String args[]) {
		///////////////////////////////////////////////////////////////////////
		
//		IntervalBST<Event> tst = new IntervalBST<Event>();
//		tst.insert(new Event(5000000, 0, "", "", "", ""));
//		tst.insert(new Event(3000000, 0, "", "", "", ""));
//		tst.insert(new Event(7500000, 0, "", "", "", ""));
//		tst.insert(new Event(2000000, 0, "", "", "", ""));
//		tst.insert(new Event(5000000, 0, "", "", "", ""));
//		tst.insert(new Event(5000000, 0, "", "", "", ""));
//		tst.insert(new Event(5000000, 0, "", "", "", ""));
//		tst.insert(new Event(5000000, 0, "", "", "", ""));
//		tst.insert(new Event(5000000, 0, "", "", "", ""));
		
		
		///////////////////////////////////////////////////////////////////////
		if (args.length != 1) {			
			System.err.println("Usage: java Scheduler <resourceListFile>");
			System.exit(1);
		}
				
		boolean didInitialize = 
				initializeFromInputFile(args[0]);
		
		if(!didInitialize) {
			System.err.println("Failed to initialize the application!");
			System.exit(1);
		}
		
		System.out.println("Welcome to the UW-Madison Scheduling Client");

		processUserCommands();
	}
	
	/**
	 * Checks whether the initialization files exist and is readable; if not,
	 * displays: "File <Filename> not found" and quit.
	 * 
	 * @param resourceListFile Name of file being initialized
	 * @return true if initialized
	 */
	private static boolean initializeFromInputFile(String resourceListFile) {
		try {
			File resourcesFile = new File(resourceListFile);
			Scanner in = new Scanner(resourcesFile);
			
			if (in.hasNextLine())
				in.nextLine(); // #Resource:
			
			while (in.hasNextLine()) {
				String resource = in.nextLine();
				schedulerDB.addResource(resource);
				
				getEvents(in, resource);
			}
		} catch (FileNotFoundException e) {
			// prints the error message if the file isn't found
			System.out.println("File " + resourceListFile + " not found");
			return false;
		}
		return true;
	}
	
	/**
	 * Used to recursevely find each Event and add it to the DB
	 * 
	 * @param in the scanner used to Iterate through the file
	 * @param resource The name of the current resource we're under
	 * @return false if found the end or a new Resource
	 */
	private static boolean getEvents(Scanner in, String resource) {
		String name, organization, description;
		long start, end;
		
		name = in.nextLine();
		if (name.trim().equals("#Resource:"))
			return false;
		
		start = convertToTime(in.nextLine());
		end = convertToTime(in.nextLine());
		organization = in.nextLine();
		description = in.nextLine();
		
		schedulerDB.addEvent(resource, start, end, name, organization,
				description);		
		
		if (!in.hasNext())
			return false;
		
		return getEvents(in, resource);
	}
	
	/**
	 * Processes the User commands entered from the terminal
	 */
	private static void processUserCommands() {
		scanner = new Scanner(System.in);
		String command = null;		
		do {
			System.out.print("\nPlease Enter a command ([H]elp):");
			command = scanner.next();
			switch(command.toLowerCase()) {							
				case "v":
					processDisplayCommand();
					break;
				case "a":
					processAddCommand();
					break;
				case "d":
					processDeleteCommand();
					break;
				case "q":
					System.out.println("Quit");
					break;
				case "h":
					System.out.println("\nThis scheduler has commands that are entered as a single character indicated in [].\n"+
							"The main commands are to view, add, delete, or quit.\n"+
							"The first three main commands need a secondary command possibly with additional input.\n"+
							"A secondary command's additional input is described within <>.\n"+
							"Please note that a comma (,) in the add event command represents a need to press\n"+
							"the return character during the command. Also note that times must be in the format\n"+
							"of mm/dd/yyyy,hh:mm.\n"+
							"[v]iew\n"+
							"	[r] = view all resources\n"+
							"	[e] = view all events\n"+
							"	[t] <resource name> = view events in a resource\n"+
							"	[o] <organization name> = view events with an organization\n"+
							"	[n] <start time> <end time> = view events within a time range\n"+
							"	[s] <start time> <end time> <resource name> = view events within in a time range in a resource\n"+
							"[a]dd\n"+
							"	[r] <resource name> = add a resource\n"+
							"	[e] <resource name>, = add an event\n"+
							"		      <start time> <end time> <event name>, \n"+
							"		      <organization name>, \n"+
							"		      <event description>\n"+
							"[d]elete\n"+
							"	[r] <resource name> = delete a resource\n"+
							"	[e] <event start time> <resource name> = delete an event\n"+
							"[q]uit\n");
					break;
				default:
					System.out.println("Unrecognized Command!");
					break;
			}
		} while (!command.equalsIgnoreCase("q"));
		scanner.close();
	}
	
	/**
	 * Displays the sub commands from 'v'
	 */
	private static void processDisplayCommand() {
		String restOfLine = scanner.next();
		Scanner in = new Scanner(restOfLine);
		String subCommand = in.next();
		switch(subCommand.toLowerCase()) {
		 //additional input in comments (comma means return)
			case "r": 
				printResourceList(schedulerDB.getResources());
				break;
			case "e": 
				printEventList(schedulerDB.getAllEvents());
				break;
			case "t": // resource,
				printEventList(schedulerDB.getEventsInResource(scanner.nextLine().trim()));
				break;
			case "s": // start end resource,
				printEventList(schedulerDB.getEventsInRangeInResource(
						convertToTime(scanner.next()), convertToTime(scanner.next()), 
						scanner.nextLine().trim()));
				break;
			case "o": // organization
				printEventList(schedulerDB.getEventsForOrg(scanner.nextLine().trim()));
				break;
			case "n": // start end
				printEventList(schedulerDB.getEventsInRange(convertToTime(scanner.next()),
						convertToTime(scanner.next())));
				break;
			default: 
				System.out.println("Unrecognized Command!");
		}
		in.close();
	}
	
	/**
	 * 
	 */
	private static void processAddCommand(){
		String restOfLine = scanner.next();
		Scanner in = new Scanner(restOfLine);
		String subCommand = in.next();
		switch(subCommand.toLowerCase()) {
			case "r": //resource
				if(!schedulerDB.addResource(scanner.nextLine().trim())){
					System.out.println("Could not add: no two resources may have the same name.");
				}else{
					System.out.println("Successfully added resource.");
				}
				break;
			case "e": //resource, start end name, organization, description
				try{
					if(!schedulerDB.addEvent(scanner.nextLine().trim(), 
							convertToTime(scanner.next()), convertToTime(scanner.next()), 
							scanner.nextLine().trim(), scanner.nextLine().trim(), scanner.nextLine().trim())){
						System.out.println("Could not add: resource not found.");
					}else{
						System.out.println("Successfully added event.");
					}
				}catch(IntervalConflictException expt){
					System.out.println("Could not add: this event conflicted with an existing event.");
				}
				break;
			default: 
				System.out.println("Unrecognized Command!");
		}
		in.close();
	}
	
	private static void processDeleteCommand(){
		String restOfLine = scanner.next();
		Scanner in = new Scanner(restOfLine);
		String subCommand = in.next();
		switch(subCommand.toLowerCase()) {
			case "r": // resource
				if(!schedulerDB.removeResource(scanner.nextLine().trim())){
					System.out.println("Could not delete. Resource not found.");
				}else{
					System.out.println("Successfully deleted resource.");
				}
				break;
			case "e":  // resource, start
				if(!schedulerDB.deleteEvent(convertToTime(scanner.next().trim()), 
						scanner.nextLine().trim())){
					System.out.println("Could not delete. Resource not found.");
				}else{
					System.out.println("Successfully deleted event.");
				}
				break;
			default: 
				System.out.println("Unrecognized Command!");
		}
		in.close();
	}
	
	/**
	 * Prints out a list of all of the Resources, or "No resources to display."
	 * if the list is empty
	 * 
	 * @param list The given list to be printed
	 */
	private static void printResourceList(List<Resource> list){
		Iterator<Resource> itr = list.iterator();
		if(!itr.hasNext()){
			System.out.println("No resources to display.");
		}
		while(itr.hasNext()){
			System.out.println(itr.next().getName());
		}
	}
	
	/**
	 * Prints out a list of all of the Events, or "No events to display." if
	 * the list is empty
	 * 
	 * @param list The given list to be printed
	 */
	private static void printEventList(List<Event> list){
		Iterator<Event> itr = list.iterator();
		if(!itr.hasNext()){
			System.out.println("No events to display.");
		}
		while(itr.hasNext()){
			System.out.println("\n" + itr.next().toString());
		}
	}
	
	/**
	 * Processes the given time in a string format into a long / 1000 
	 * representing the date
	 * 
	 * @param time A string format of the date
	 * @return a long / 1000 of the date
	 */
	private static long convertToTime(String time){
		long result = 0;
    	Format format = new SimpleDateFormat("MM/dd/yyyy,HH:mm");
	    try{
	    	Date date = (Date) format.parseObject(time);
	    	result = date.getTime()/1000;
		}catch(Exception e){
			System.out.println("Dates are not formatted correctly.  "+
					"Must be \"MM/dd/yyyy,HH:mm\"");
		}
	    return result;
	}

}
