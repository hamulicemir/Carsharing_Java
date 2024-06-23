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
 * {@link TripStatus}.
 * 
 */
public class Trip implements Comparable<Trip> {
	private final Car car;
	private double distance;
	private Location endLocation;
	private DateTime endTime;
	private final Rate rate;
	private final Customer customer;
	private Location startLocation;
	private DateTime startTime;
	private TripStatus status;

	public Trip(Trip tr) {
		this.car = tr.car;
		this.distance = tr.distance;
		this.endLocation = tr.endLocation;
		this.endTime = tr.endTime;
		this.customer = tr.customer;
		this.startLocation = tr.startLocation;
		this.startTime = tr.startTime;
		this.status = tr.status;
        rate = null;
    }
	public Trip(Car car, Customer customer, Rate rate){
		if(car == null){
			throw new IllegalArgumentException("Car cannot be null");
		}
		if(customer == null){
			throw new IllegalArgumentException("Customer cannot be null");
		}
		if(rate == null){
			throw new IllegalArgumentException("Rate cannot be null");
		}
		this.car = car;
		this.customer = customer;
		this.rate = rate;
	}
	public Trip(Car car, Customer customer, Rate rate, Location startLocation, DateTime startTime){
		this(car, customer, rate);
		if(startLocation == null)
			throw new IllegalArgumentException("StartLocation cannot be null");
		else
			this.startLocation = startLocation;
		if(startTime == null)
			throw new IllegalArgumentException("StartTime cannot be null");
		else
			this.startTime = startTime;
		if(startLocation != null && startTime != null)
			this.status = TripStatus.STARTED;
	}
	public Trip(Car car, Customer customer, Rate rate, Location startLocation, DateTime startTime, Location endLocation, DateTime endTime, double distance) {
		this(car, customer, rate, startLocation, startTime);
		if(endLocation == null)
			throw new IllegalArgumentException("EndLocation cannot be null");
		else
			this.endLocation = endLocation;
		if(endTime == null)
			throw new IllegalArgumentException("EndTime cannot be null");
		else
			this.endTime = endTime;
		if(distance < 0)
			throw new IllegalArgumentException("Distance cannot be negative");
		else
			this.distance = distance;

		if(endLocation != null && endTime != null && distance > 0)
			this.status = TripStatus.COMPLETED;
	}

	public Trip start(Location startLocation, DateTime startTime){
		if(startLocation == null)
			throw new IllegalArgumentException("StartLocation cannot be null");
		else
			this.startLocation = startLocation;
		if(startTime == null)
			throw new IllegalArgumentException("StartTime cannot be null");
		else
			this.startTime = startTime;

		if(status == TripStatus.STARTED || status == TripStatus.COMPLETED)
			throw new IllegalStateException("Trip already started");
		else
			this.status = TripStatus.STARTED;

		return this;
	}

	public Trip end(Location endLocation, DateTime endTime, double distance){
		if(endLocation == null)
			throw new IllegalArgumentException("EndLocation cannot be null");
		else
			this.endLocation = endLocation;
		if(endTime == null)
			throw new IllegalArgumentException("EndTime cannot be null");
		else
			this.endTime = endTime;
		if(distance < 0)
			throw new IllegalArgumentException("Distance cannot be negative");
		else
			this.distance = distance;

		if(status == TripStatus.STARTED)
			this.status = TripStatus.COMPLETED;
		else
			throw new IllegalStateException("Trip is just created or already ended");

		return this;
	}

	public TripStatus getStatus() {
		return status;
	}

	public double getDistance() {
		return distance;
	}

	public Location getEndLocation() {
		return endLocation;
	}

	public DateTime getEndTime() {
		return endTime;
	}

	public Rate getRate() {
		return rate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Location getStartLocation() {
		return startLocation;
	}

	public DateTime getStartTime() {
		return startTime;
	}

	public final int total(){
		return getRate().total(this);
	}

	@Override
	public String toString() {
		return "Trip{" +
				"car=" + car +
				", distance=" + distance +
				", endLocation=" + endLocation +
				", endTime=" + endTime +
				", rate=" + rate +
				", customer=" + customer +
				", startLocation=" + startLocation +
				", startTime=" + startTime +
				", status=" + status +
				'}';
	}
	/**
	 * The duration of this trip in seconds.<br>
	 * 
	 * @ProgrammingProblem.Hint use {@link DateTime#diff(DateTime)}
	 * @return the difference in seconds if this trip is completed, zero otherwise
	 */
	public int duration() {
		if (status == TripStatus.COMPLETED)
			return (int) (startTime.diff(endTime));

		return 0;
	}

	@Override
	public int compareTo(Trip trip) {
		if(status == TripStatus.CREATED)
			return Integer.MAX_VALUE;
		else
			return this.startTime.compareTo(trip.startTime);
	}
}