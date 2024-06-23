package CarSharing.app;

import CarSharing.entities.*;
import CarSharing.provided.*;
import CarSharing.provided.Formatter;
import CarSharing.util.AmountComparator;
import CarSharing.util.QuickFormat;
import CarSharing.util.StatusComparator;
import CarSharing.util.StatusMatcher;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * A demo app.
 */
public class Main {

	private static List<Car> cars;
	private static List<Customer> customers;
	private static List<Rate> rates;
	private static List<Trip> trips;

	/**
	 * Create some test data.
	 */
	static {
		cars = Arrays.asList(new Car("Mercedes", "450 SEL 6.9", "W-I8NY"), //
				new Car("Volvo", "960", "W-I8LA"), //
				new Car("Volvo", "850", "W-ACAB"), //
				new Car("Volvo", "760", "W-LOL"), //
				new Car("Chevrolet", "G30", "W-JAVA RULEZ") //
		);

		customers = Arrays.asList(new Customer("John Doe"), //
				new Customer("Jane Doe")//
		);

		rates = Arrays.asList(new LongTermRate(12000, 3000), //
				new LongTermRate(15000, 3000), //
				new LongTermRate(25000, 5000), //
				new LongTermRate(9000, 100), //
				new PerMinuteRate(40), //
				new PerMinuteRate(27), //
				new PerMinuteRate(75), //
				new PerMinuteRate(23) //
		);

		trips = Arrays.asList(

				new Trip(cars.get(2), customers.get(0), rates.get(0)) //
				, //

				new Trip(cars.get(3), customers.get(0), rates.get(1)). //
						start(new Location(48.238938, 16.378749), new DateTime(2021, 6, 23, 15, 17)) //
				, //

				new Trip(cars.get(0), customers.get(0), rates.get(2), //
						new Location(48.238938, 16.378749), new DateTime(2021, 1, 1, 0, 0))//
				, //

				new Trip(cars.get(4), customers.get(0), rates.get(3)). //
						start(new Location(48.238938, 16.378749), new DateTime(2021, 1, 1, 0, 0)). //
						end(new Location(48.20613882633994, 16.422068310841116), new DateTime(2021, 1, 1, 0, 23), 10.2)//
				, //

				new Trip(cars.get(1), customers.get(0), rates.get(4), //
						new Location(48.20613882633994, 16.422068310841116), new DateTime(2021, 1, 1, 15, 23), //
						new Location(48.238938, 16.378749), new DateTime(2021, 1, 3, 15, 17), 5.2) //
				, //

				new Trip(cars.get(0), customers.get(1), rates.get(5), //
						new Location(48.238938, 16.378749), new DateTime(2021, 05, 23, 11, 15)) //
				, //

				new Trip(cars.get(3), customers.get(0), rates.get(6)), //

				new Trip(cars.get(4), customers.get(0), rates.get(3)). //
						start(new Location(48.238938, 16.378749), new DateTime(2021, 1, 2, 3, 12)). //
						end(new Location(48.20613882637994, 16.422068310841416), new DateTime(2021, 1, 2, 3, 23), 10.2)//

		);

	}

	/**
	 * A simple demo app which
	 * 
	 * <ul>
	 * <li>prints the trips test data in tabular long format</li>
	 * <li>sorts the trips</li>
	 * <li>prints the trips in tabular long format again</li>
	 * <li>sorts the trips by amount charged</li>
	 * <li>prints the trips in tabular quick format again</li>
	 * <li>filters the trips by status, keeping only completed trips</li>
	 * <li>prints the completed trips in tabular long format again</li>
	 * <li>sorts the filtered trips by duration</li>
	 * <li>exports the filtered and sorted trips to file "completed_trips.txt"</li>
	 * <li>prints the number of trips exported</li>
	 * <li>provoke an exception in one of the trip methods using an existing trip
	 * object and <strong>handle</strong> that exception in main</li>
	 * 
	 * </ul>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 1. Print the trips in tabular long format
		tabularPrint(trips, new LongFormat());

		// 2. Sort the trips
		try {
			trips.sort(new StatusComparator());
		} catch (NullPointerException e) {
			System.err.println("NullPointerException: Some trips might have null status.");
		}

		// 3. Print the trips in tabular long format again
		tabularPrint(trips, new LongFormat());

		// 4. Sort the trips by amount charged
		trips.sort(new AmountComparator());

		// 5. Print the trips in tabular quick format
		tabularPrint(trips, new QuickFormat());

		// 6. Filter the trips by status, keeping only completed trips
		List<Trip> completedTrips = filter(trips, new StatusMatcher(TripStatus.COMPLETED));

		// 7. Print the completed trips in tabular long format
		tabularPrint(completedTrips, new LongFormat());

		// 8. Sort the filtered trips by duration
		completedTrips.sort(new DurationComparator());

		// 9. Export the filtered and sorted trips to file "completed_trips.txt"
		export(completedTrips, "completed_trips.txt");

		// 10. Print the number of trips exported
		System.out.printf("Number of trips exported: %d\n", completedTrips.size());

		// 11. Provoke and handle an exception
		provokeException();
	}

	public static int export(List<Trip> trips, String fileName) {
		int counter = 0;
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))){
			for(Trip trip : trips){
				bw.write(trip.toString());
				bw.newLine();
				counter++;
			}
		}
		catch (Exception e){
			System.err.println(e.getMessage());
		}
		return counter;
	}

	public static List<Trip> filter(List<Trip> trips, Matcher<Trip> matcher) {
		List<Trip> filtered = new LinkedList<>();
		for(Trip trip : trips){
			if(matcher.matches(trip))
				filtered.add(trip);
		}
		return filtered;
	}
	
	private static void tabularPrint(List<Trip> trips) {
		System.out.printf("---\n");
		for (Trip t : trips) {
			System.out.printf("%s\n", t);
		}
		System.out.printf("---\n");
	}
	private static void tabularPrint(List<Trip> trips, Formatter formatter) {
		System.out.println("---");
		for (Trip t : trips) {
			System.out.println(formatter.format(t));
		}
		System.out.println("---");
	}
	private static void provokeException() {
		try {
			Trip invalidTrip = trips.get(0);
			invalidTrip.end(null, null, -1);
		} catch (IllegalArgumentException e) {
			System.err.println("Exception handled: " + e.getMessage());
		}
	}
}