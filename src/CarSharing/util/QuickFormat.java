package CarSharing.util;

import CarSharing.entities.Trip;
import CarSharing.provided.Formatter;

public class QuickFormat extends Formatter<Trip> {
    public QuickFormat() {

    }

    @Override
    public String format(Trip trip) {
        if(trip == null)
            return "unknown";
        else {
            int duration = trip.duration();
            double distance = trip.getDistance();
            int amount = trip.total();
            return String.format("%8d min %5.1f km %8d.%02d EUR", duration, distance, amount, amount);
        }
    }
}
