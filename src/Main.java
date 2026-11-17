import java.util.ArrayList;
import java.util.List;
import  java.util.Scanner;
import java.util.Map;
public class Main {
    static void main() {
        try {
            Map<String, Potato> potatoes = CsvReader.readPotatoes("input/potatoes.csv");
            Map<String, Launcher> launchers = CsvReader.readLaunchers("input/launchers.csv");
            List<LaunchAttempt> attempts = CsvReader.readAttempts("input/launch_attempts.csv", potatoes, launchers);

            System.out.println("Successfully loaded " + potatoes.size() + " potatoes!");
            System.out.println("Successfully loaded " + launchers.size() + " launchers!");
            System.out.println("Successfully loaded " + attempts.size() + " launch attempts!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
