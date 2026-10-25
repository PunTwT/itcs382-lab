package bikeshare;

public final class Receipt {
    private Receipt() {
    }

    public static String format(Ride ride) {
        return """
                --- Receipt ---
                Bike:    %s
                Minutes: %d
                Fee:     %d baht
                ---------------""".formatted(ride.bikeId(), ride.minutes(), ride.fee());
    }
}