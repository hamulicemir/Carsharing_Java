package CarSharing.entities;

import CarSharing.provided.TripStatus;

public class LongTermRate extends Rate {
    private int baseAmount;
    private int baseDuration;
    private int perDay;

    public LongTermRate(int baseAmount, int perDay) {
        if(baseAmount > 0)
            this.baseAmount = baseAmount;
        if(perDay > 0)
            this.perDay = perDay;
    }

    @Override
    int total(Trip trip) {
        if(trip.getStatus() == TripStatus.COMPLETED){
            if(trip.duration() > baseDuration)
                return baseAmount + (perDay * (trip.duration() - baseDuration));
            else
                return baseAmount;
        }
        else
            return 0;
    }
}
