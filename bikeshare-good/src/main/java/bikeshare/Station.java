package bikeshare;

import java.util.Locale;

public enum Station {
    LIBRARY, CANTEEN, DORM;

    public static Station parse(String text) {
        try {
            return valueOf(text.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Station must be library, canteen or dorm.");
        }
    }
}