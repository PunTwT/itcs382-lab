package bikeshare;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class CsvRideLog implements RideLog {
    private static final String HEADER = "bike,member,type,from,to,minutes,fee";

    private final Path file;

    public CsvRideLog(Path file) {
        this.file = file;
    }

    @Override
    public void save(Ride ride) {
        List<String> lines = new ArrayList<>();
        if (Files.notExists(file)) {
            lines.add(HEADER);
        }
        lines.add(String.join(",", ride.bikeId(), ride.memberId(), ride.type().name(),
                ride.from().name(), ride.to().name(),
                String.valueOf(ride.minutes()), String.valueOf(ride.fee())));
        try {
            Files.write(file, lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not write " + file, e);
        }
    }
}