package bikeshare;

import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BikeShare share = new BikeShare(new CsvRideLog(Path.of("rides.csv")), 2);
        new Menu(share, new Scanner(System.in)).run();
    }
}