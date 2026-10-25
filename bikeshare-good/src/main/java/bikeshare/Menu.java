package bikeshare;

import java.io.UncheckedIOException;
import java.util.Locale;
import java.util.Scanner;

public class Menu {
    private final BikeShare share;
    private final Scanner in;

    public Menu(BikeShare share, Scanner in) {
        this.share = share;
        this.in = in;
    }

    public void run() {
        System.out.println("Commands: rent, return, quit");
        while (true) {
            String command = ask("> ").toLowerCase(Locale.ROOT);
            if (command.equals("quit")) {
                return;
            }
            try {
                switch (command) {
                    case "rent" -> rent();
                    case "return" -> giveBack();
                    default -> System.out.println("Unknown command.");
                }
            } catch (IllegalArgumentException | IllegalStateException | UncheckedIOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void rent() {
        String memberId = ask("Member ID: ");
        MemberType type = MemberType.parse(ask("Member type (student/staff): "));
        Station station = Station.parse(ask("Station (library/canteen/dorm): "));
        System.out.println("Rented bike " + share.rent(memberId, type, station) + ".");
    }

    private void giveBack() {
        String memberId = ask("Member ID: ");
        Station station = Station.parse(ask("Return station (library/canteen/dorm): "));
        int minutes = parseMinutes(ask("Ride time in minutes: "));
        System.out.println(Receipt.format(share.returnBike(memberId, station, minutes)));
    }

    private int parseMinutes(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Minutes must be a positive whole number.");
        }
    }

    private String ask(String prompt) {
        System.out.print(prompt);
        return in.hasNextLine() ? in.nextLine().trim() : "quit";
    }
}