import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {

            Map<String, Potato> potatoes = CsvReader.readPotatoes("input/potatoes.csv");
            Map<String, Launcher> launchers = CsvReader.readLaunchers("input/launchers.csv");
            List<LaunchAttempt> attempts = CsvReader.readAttempts("input/launch_attempts.csv", potatoes, launchers);

            ReportGenerator.generatePotatoFlightLog(potatoes, attempts, "output/01_potato_flight_log.csv");
            ReportGenerator.generateLauncherActivity(launchers, attempts, "output/02_launcher_activity.csv");

            System.out.println("Uspješno generisani izvještaji 1 i 2 u output/ folderu!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
