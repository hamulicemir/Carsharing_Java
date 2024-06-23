package CarSharing.provided;

import CarSharing.entities.Trip;


/**
 * A formatter for trips.<br>
 * 
 * 
 * This formatter creates a string representation of a trip containing the following information
 * <ul>
 * <li>status</li>
 * <li>start time</li>
 * <li>end time</li>
 * <li>duration</li>
 * <li>distance</li>
 * <li>amount</li>
 * <li>rate</li>
 * </ul>
 * 
 * Unknown dates and locations are represented y the string "unknown"
 * 
 * @ProgrammingProblem.Hint use TripStatus.name() to convert status to String
 * @ProgrammingProblem.Hint "%10s %16.16s %16.16s %8d min %5.1f km %8d.%02d EUR (%s)"
 * 
 * 
 */
public class LongFormat extends Formatter<Trip> {
	@Override
	public String format(Trip trip) {
		String status = (trip.getStatus() != null) ? trip.getStatus().name() : "(unknown)";
		String startTime = (trip.getStartTime() != null) ? trip.getStartTime().toString() : "(unknown)";
		String endTime = (trip.getEndTime() != null) ? trip.getEndTime().toString() : "(unknown)";
		long duration = (trip.duration() >= 0) ? trip.duration() / 60 : 0;
		double distance = (trip.getDistance() >= 0) ? trip.getDistance() : 0;
		int totalEur = trip.total() / 100;
		int totalCents = trip.total() % 100;
		String rate = (trip.getRate() != null) ? trip.getRate().toString() : "(unknown)";

		return String.format("%10s %16.16s %16.16s %8d min %5.1f km %8d.%02d EUR (%s)",
				status,
				startTime,
				endTime,
				duration,
				distance,
				totalEur, totalCents, rate);
	}
}
