package CarSharing.entities;

import CarSharing.provided.TripStatus;

public class PerMinuteRate extends Rate {

    private int perMinute;
    public PerMinuteRate(int perMinute) {
        if(perMinute < 0)
            throw new IllegalArgumentException();
        else
            this.perMinute = perMinute;
    }
    @Override
    public int total(Trip t) {
        if(t.getStatus() == TripStatus.COMPLETED)
            return perMinute * t.duration();
        else
            return 0;
    }

    @Override
    public String toString() {
        return "PerMinuteRate{" +
                "perMinute=" + perMinute +
                '}';
    }
}