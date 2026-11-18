import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class ReportGenerator {

    public static void generatePotatoFlightLog(Map<String, Potato> potatoMap, List<LaunchAttempt> attempts, String filepath) throws FileNotFoundException {
        File file = new File(filepath);
        file.getParentFile().mkdirs();

        Map<String, Integer> attemptCounts = new HashMap<>();
        Map<String, Integer> totalPoints = new HashMap<>();

        for (String id : potatoMap.keySet()) {
            attemptCounts.put(id, 0);
            totalPoints.put(id, 0);
        }

        for (LaunchAttempt attempt : attempts) {
            Potato potato = attempt.getPotatoId();
            if (potato == null) continue;

            String id = potato.getPotatoId();
            attemptCounts.put(id, attemptCounts.get(id) + 1);
            totalPoints.put(id, totalPoints.get(id) + attempt.getScore());
        }

        List<String> sortedIds = new ArrayList<>(potatoMap.keySet());
        Collections.sort(sortedIds);

        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("potatoId,name,type,attemptCount,totalPoints");
            for (String id : sortedIds) {
                Potato p = potatoMap.get(id);
                writer.println(id + "," + p.getName() + "," + p.getType() + "," +
                        attemptCounts.get(id) + "," + totalPoints.get(id));
            }
        }
    }

    public static void generateLauncherActivity(Map<String, Launcher> launcherMap, List<LaunchAttempt> attempts, String filepath) throws FileNotFoundException {
        File file = new File(filepath);
        file.getParentFile().mkdirs();

        Map<String, Integer> attemptCounts = new HashMap<>();
        Map<String, Integer> totalPayloads = new HashMap<>();
        Map<String, Integer> overLimitCounts = new HashMap<>();

        for (String id : launcherMap.keySet()) {
            attemptCounts.put(id, 0);
            totalPayloads.put(id, 0);
            overLimitCounts.put(id, 0);
        }

        for (LaunchAttempt attempt : attempts) {
            Launcher launcher = attempt.getLauncherId();
            if (launcher == null) continue;

            String id = launcher.getId();
            attemptCounts.put(id, attemptCounts.get(id) + 1);
            totalPayloads.put(id, totalPayloads.get(id) + attempt.getPayloadKg());

            if (attempt.getPayloadKg() > launcher.getMaxPayloadKg()) {
                overLimitCounts.put(id, overLimitCounts.get(id) + 1);
            }
        }

        List<String> sortedIds = new ArrayList<>(launcherMap.keySet());
        Collections.sort(sortedIds);

        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("launcherId,name,attemptCount,totalPayloadKg,overLimitCount");
            for (String id : sortedIds) {
                Launcher l = launcherMap.get(id);
                writer.println(id + "," + l.getName() + "," +
                        attemptCounts.get(id) + "," +
                        totalPayloads.get(id) + "," +
                        overLimitCounts.get(id));
            }
        }
    }
}