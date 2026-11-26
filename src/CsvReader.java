import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CsvReader {
    public static Map<String, Potato> readPotatoes(String filepath) throws FileNotFoundException {
        Map<String, Potato> potatoMap = new HashMap<>();
        File file = new File(filepath);

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    String type = parts[2].trim();

                    Potato potato;
                    switch (type) {
                        case "FINGERLING":
                            potato = new FingerlingPotato(id, name);
                            break;
                        case "PURPLE":
                            potato = new PurplePotato(id, name);
                            break;
                        case "RUSSET":
                            potato = new RussetPotato(id, name);
                            break;
                        case "YUKON_GOLD":
                            potato = new YukonGoldPotato(id, name);
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown potato type: " + type);
                    }
                    potatoMap.put(id, potato);
                }
            }
        }
        return potatoMap;
    }

    public static Map<String, Launcher> readLaunchers(String filepath) throws FileNotFoundException {
        Map<String, Launcher> launcherMap = new HashMap<>();
        File file = new File(filepath);

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    int maxPayload = Integer.parseInt(parts[2].trim());

                    Launcher launcher = new Launcher(id, name, maxPayload);
                    launcherMap.put(id, launcher);
                }
            }
        }
        return launcherMap;
    }

    public static List<LaunchAttempt> readAttempts(String filepath, Map<String, Potato> potatoMap, Map<String, Launcher> launcherMap) throws FileNotFoundException {
        List<LaunchAttempt> attemptsList = new ArrayList<>();
        File file = new File(filepath);

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String attemptId = parts[0].trim();
                    String potatoId = parts[1].trim();
                    String launcherId = parts[2].trim();
                    int payloadKg = Integer.parseInt(parts[3].trim());
                    int flightRating = Integer.parseInt(parts[4].trim());

                    Potato potato = potatoMap.get(potatoId);
                    if (potato == null) {
                        throw new IllegalArgumentException("Unknown potatoId: " + potatoId);
                    }

                    Launcher launcher = launcherMap.get(launcherId);
                    if (launcher == null) {
                        throw new IllegalArgumentException("Unknown launcherId: " + launcherId);
                    }

                    LaunchAttempt attempt = new LaunchAttempt(attemptId, potato, launcher, payloadKg, flightRating);
                    attemptsList.add(attempt);
                }
            }
        }
        return attemptsList;
    }
}