package CarSharing.util;

import CarSharing.entities.*;

import java.util.Comparator;

public class StatusComparator implements Comparator<Trip> {
    public StatusComparator() {}

    @Override
    public int compare(Trip o1, Trip o2) {
        if(o1 != null || o2 != null)
            return o1.getStatus().compareTo(o2.getStatus());
        else
            return 0;
    }
}
