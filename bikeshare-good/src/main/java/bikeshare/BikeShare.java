package bikeshare;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class BikeShare {
    private record Rental(String bikeId, MemberType type, Station from) {
    }

    private final Map<Station, Deque<String>> available = new EnumMap<>(Station.class);
    private final Map<String, Rental> rentals = new HashMap<>();
    private final RideLog log;

    public BikeShare(RideLog log, int bikesPerStation) {
        this.log = log;
        int number = 1;
        for (Station station : Station.values()) {
            Deque<String> bikes = new ArrayDeque<>();
            for (int i = 0; i < bikesPerStation; i++) {
                bikes.add("B" + number++);
            }
            available.put(station, bikes);
        }
    }

    public String rent(String memberId, MemberType type, Station station) {
        if (memberId.isBlank() || memberId.contains(",")) {
            throw new IllegalArgumentException("Invalid member ID.");
        }
        if (rentals.containsKey(memberId)) {
            throw new IllegalStateException("Member " + memberId + " already has a bike.");
        }
        String bikeId = available.get(station).poll();
        if (bikeId == null) {
            throw new IllegalStateException("No bike available at " + station + ".");
        }
        rentals.put(memberId, new Rental(bikeId, type, station));
        return bikeId;
    }

    public Ride returnBike(String memberId, Station station, int minutes) {
        if (minutes <= 0) {
            throw new IllegalArgumentException("Minutes must be a positive whole number.");
        }
        Rental rental = rentals.get(memberId);
        if (rental == null) {
            throw new IllegalStateException("No active rental for member " + memberId + ".");
        }
        Ride ride = new Ride(rental.bikeId(), memberId, rental.type(),
                rental.from(), station, minutes, rental.type().fee(minutes));
        log.save(ride);
        rentals.remove(memberId);
        available.get(station).add(rental.bikeId());
        return ride;
    }
}