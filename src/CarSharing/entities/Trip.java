package CarSharing.entities;

import CarSharing.provided.Car;
import CarSharing.provided.Customer;
import CarSharing.provided.DateTime;
import CarSharing.provided.Location;
import CarSharing.provided.TripStatus;

/**
 * A trip in the car sharing system.<br>
 * 
 * A trip collects information about time, location, customer and car involved.
 * It also contains a rate responsible for calculation of the total amount charged for
 * a trip.
 * 
 * Some operations' behavior depends on this trip's
 * {@link CarSharing.provided.TripStatus}.
 * 
 */
public class Trip implements Comparable<Trip> {
	
	
	/**
	 * The duration of this trip in seconds.<br>
	 * 
	 * 
	 * 
	 * @ProgrammingProblem.Hint use {@link DateTime#diff(DateTime)}
	 * @return the difference in seconds if this trip is completed, zero otherwise
	 */
	public int duration() {
		if (status == TripStatus.COMPLETED)
			return (int) (startTime.diff(endTime));

		return 0;
	}
}
