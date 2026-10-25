package bikeshare;

import java.util.Locale;

public enum MemberType {
    STUDENT(2), STAFF(3);

    private final int ratePerMinute;

    MemberType(int ratePerMinute) {
        this.ratePerMinute = ratePerMinute;
    }

    public int fee(int minutes) {
        return ratePerMinute * minutes;
    }

    public static MemberType parse(String text) {
        try {
            return valueOf(text.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Member type must be student or staff.");
        }
    }
}