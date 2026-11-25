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
            ReportGenerator.generateLaunchResultsByPair(attempts, "output/03_launch_results_by_pair.csv");
            ReportGenerator.generatePayloadViolations(attempts, "output/04_payload_violations.csv");
            ReportGenerator.generateBestTestFlights(attempts, "output/05_best_test_flights.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
