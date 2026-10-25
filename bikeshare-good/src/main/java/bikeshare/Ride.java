package bikeshare;

public record Ride(String bikeId, String memberId, MemberType type,
                   Station from, Station to, int minutes, int fee) {
}