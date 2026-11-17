import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class ReportGenerator {

    // Izvještaj 1: Potato flight log (01_potato_flight_log.csv)
    public static void generatePotatoFlightLog(Map<String, Potato> potatoMap, List<LaunchAttempt> attempts, String filepath) throws FileNotFoundException {
        File file = new File(filepath);
        file.getParentFile().mkdirs(); // Kreira output/ folder ako ne postoji

        Map<String, Integer> attemptCountMap = new HashMap<>();
        Map<String, Integer> totalPointsMap = new HashMap<>();

        // Inicijalizujemo sve krompire na 0
        for (String id : potatoMap.keySet()) {
            attemptCountMap.put(id, 0);
            totalPointsMap.put(id, 0);
        }

        for (LaunchAttempt attempt : attempts) {
            Potato potato = attempt.getPotatoId(); // Koristimo tvoju metodu iz LaunchAttempt-a
            if (potato == null) continue;

            // Pretpostavljamo da Potato klasa ima getId() ili getPotatoId() za tekstualni ID
            String pId = potato.getPotatoId(); // Ako je ovdje greška, provjeri metodu za ID u Potato klasi

            attemptCountMap.put(pId, attemptCountMap.get(pId) + 1);

            int points = attempt.getScore(); // Možemo direktno koristiti i tvoju getScore() metodu iz LaunchAttempt-a!
            totalPointsMap.put(pId, totalPointsMap.get(pId) + points);
        }

        List<String> sortedPotatoIds = new ArrayList<>(potatoMap.keySet());
        Collections.sort(sortedPotatoIds);

        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("potatoId,name,type,attemptCount,totalPoints");
            for (String id : sortedPotatoIds) {
                Potato p = potatoMap.get(id);
                int count = attemptCountMap.get(id);
                int points = totalPointsMap.get(id);
                writer.println(id + "," + p.getName() + "," + p.getType() + "," + count + "," + points);
            }
        }
    }

    // Izvještaj 2: Launcher activity (02_launcher_activity.csv)
    public static void generateLauncherActivity(Map<String, Launcher> launcherMap, List<LaunchAttempt> attempts, String filepath) throws FileNotFoundException {
        File file = new File(filepath);
        file.getParentFile().mkdirs();

        Map<String, Integer> attemptCountMap = new HashMap<>();
        Map<String, Integer> totalPayloadMap = new HashMap<>();
        Map<String, Integer> overLimitMap = new HashMap<>();

        for (String id : launcherMap.keySet()) {
            attemptCountMap.put(id, 0);
            totalPayloadMap.put(id, 0);
            overLimitMap.put(id, 0);
        }

        for (LaunchAttempt attempt : attempts) {
            Launcher launcher = attempt.getLauncherId(); // Koristimo tvoju metodu iz LaunchAttempt-a
            if (launcher == null) continue;

            String lId = launcher.getId(); // Pretpostavka za ID lansera
            attemptCountMap.put(lId, attemptCountMap.get(lId) + 1);
            totalPayloadMap.put(lId, totalPayloadMap.get(lId) + attempt.getPayloadKg());

            if (attempt.getPayloadKg() > launcher.getMaxPayloadKg()) {
                overLimitMap.put(lId, overLimitMap.get(lId) + 1);
            }
        }

        List<String> sortedLauncherIds = new ArrayList<>(launcherMap.keySet());
        Collections.sort(sortedLauncherIds);

        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("launcherId,name,attemptCount,totalPayloadKg,overLimitCount");
            for (String id : sortedLauncherIds) {
                Launcher l = launcherMap.get(id);
                int count = attemptCountMap.get(id);
                int payload = totalPayloadMap.get(id);
                int overLimit = overLimitMap.get(id);
                writer.println(id + "," + l.getName() + "," + count + "," + payload + "," + overLimit);
            }
        }
    }
}