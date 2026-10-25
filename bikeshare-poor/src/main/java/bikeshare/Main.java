import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static final int STUDENT = 0;
    public static final int STAFF = 1;

    public static String[] stations = {"Library", "Canteen", "Dorm"};
    public static int[] bikeStation = {0, 0, 1, 1, 2, 2};
    public static String[] bikeMember = new String[6];
    public static int[] bikeType = new int[6];
    public static Scanner in;

    public static void main(String[] args) {
        try (Scanner s = new Scanner(System.in)) {
            in = s;
            System.out.println("Commands: rent, return, quit");
            while (true) {
                String cmd = ask("> ");
                if (cmd.equals("quit")) {
                    break;
                } else if (cmd.equals("rent")) {
                    handle(true);
                } else if (cmd.equals("return")) {
                    handle(false);
                } else {
                    System.out.println("Unknown command");
                }
            }
        }
    }

    public static String ask(String prompt) {
        System.out.print(prompt);
        return in.hasNextLine() ? in.nextLine().trim() : "quit";
    }

    public static int find(String key, boolean byMember) {
        for (int i = 0; i < bikeMember.length; i++) {
            if (byMember && key.equals(bikeMember[i])) {
                return i;
            }
            if (!byMember && bikeMember[i] == null && stations[bikeStation[i]].equalsIgnoreCase(key)) {
                return i;
            }
        }
        return -1;
    }

    public static void handle(boolean rent) {
        String id = ask("Member ID: ");
        int bike = find(id, true);
        if (rent) {
            String t = ask("Type (student/staff): ").toLowerCase();
            int type = t.equals("student") ? STUDENT : t.equals("staff") ? STAFF : -1;
            if (bike >= 0 || type < 0) {
                System.out.println("Member already has a bike or type is invalid");
                return;
            }
            bike = find(ask("Station: "), false);
            if (bike < 0) {
                System.out.println("No bike available there");
                return;
            }
            bikeMember[bike] = id;
            bikeType[bike] = type;
            System.out.println("Rented bike " + (bike + 1));
            return;
        }
        if (bike < 0) {
            System.out.println("No active rental for this member");
            return;
        }
        String st = ask("Return station: ");
        int station = -1;
        for (int i = 0; i < stations.length; i++) {
            if (stations[i].equalsIgnoreCase(st)) {
                station = i;
            }
        }
        int minutes;
        try {
            minutes = Integer.parseInt(ask("Ride minutes: "));
        } catch (NumberFormatException e) {
            minutes = -1;
        }
        if (station < 0 || minutes < 0) {
            System.out.println("Invalid station or minutes");
            return;
        }
        int fee = minutes * (bikeType[bike] == STUDENT ? 2 : 3);
        System.out.printf("Receipt: bike %d, %d min, %d baht%n", bike + 1, minutes, fee);
        try (FileWriter w = new FileWriter("rides.csv", true)) {
            w.write(String.format("%d,%s,%s,%s,%d,%d%n", bike + 1, id,
                    bikeType[bike] == STUDENT ? "student" : "staff", stations[station], minutes, fee));
        } catch (IOException e) {
            System.out.println("Could not save ride: " + e.getMessage());
        }
        bikeStation[bike] = station;
        bikeMember[bike] = null;
    }
}