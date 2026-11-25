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

    public static void generateLaunchResultsByPair(List<LaunchAttempt> attempts, String filepath) throws FileNotFoundException {
        File file = new File(filepath);
        file.getParentFile().mkdirs();

        Map<String, int[]> pairDataMap = new LinkedHashMap<>();

        for (LaunchAttempt attempt : attempts) {
            Potato potato = attempt.getPotatoId();
            Launcher launcher = attempt.getLauncherId();
            if (potato == null || launcher == null) continue;

            String pId = potato.getPotatoId();
            String lId = launcher.getId();
            String key = pId + "#" + lId;

            pairDataMap.putIfAbsent(key, new int[]{0, 0});
            int[] data = pairDataMap.get(key);
            data[0] += 1;
            data[1] += attempt.getScore();
        }

        List<String> sortedKeys = new ArrayList<>(pairDataMap.keySet());
        Collections.sort(sortedKeys, new Comparator<String>() {
            @Override
            public int compare(String k1, String k2) {
                String[] parts1 = k1.split("#");
                String[] parts2 = k2.split("#");
                int potatoCompare = parts1[0].compareTo(parts2[0]);
                if (potatoCompare != 0) {
                    return potatoCompare;
                }
                return parts1[1].compareTo(parts2[1]);
            }
        });

        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("potatoId,launcherId,attemptCount,totalPoints");
            for (String key : sortedKeys) {
                String[] parts = key.split("#");
                writer.println(parts[0] + "," + parts[1] + "," + pairDataMap.get(key)[0] + "," + pairDataMap.get(key)[1]);
            }
        }
    }

    public static void generatePayloadViolations(List<LaunchAttempt> attempts, String filepath) throws FileNotFoundException {
        File file = new File(filepath);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        List<LaunchAttempt> violations = new ArrayList<>();
        for (LaunchAttempt attempt : attempts) {
            if (attempt == null) continue;
            Launcher launcher = attempt.getLauncherId();
            if (launcher != null && attempt.getPayloadKg() > launcher.getMaxPayloadKg()) {
                violations.add(attempt);
            }
        }

        violations.sort(new Comparator<LaunchAttempt>() {
            @Override
            public int compare(LaunchAttempt a1, LaunchAttempt a2) {
                int excess1 = (a1.getLauncherId() != null) ? (a1.getPayloadKg() - a1.getLauncherId().getMaxPayloadKg()) : 0;
                int excess2 = (a2.getLauncherId() != null) ? (a2.getPayloadKg() - a2.getLauncherId().getMaxPayloadKg()) : 0;
                int excessCompare = Integer.compare(excess2, excess1);
                if (excessCompare != 0) {
                    return excessCompare;
                }
                return a1.getAttemptId().compareTo(a2.getAttemptId());
            }
        });

        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("attemptId,potatoId,launcherId,payloadKg,maxPayloadKg,excessKg");
            for (LaunchAttempt attempt : violations) {
                String potatoId = (attempt.getPotatoId() != null) ? attempt.getPotatoId().getPotatoId() : "";
                String launcherId = (attempt.getLauncherId() != null) ? attempt.getLauncherId().getId() : "";
                int maxPayload = (attempt.getLauncherId() != null) ? attempt.getLauncherId().getMaxPayloadKg() : 0;
                int excess = attempt.getPayloadKg() - maxPayload;
                writer.println(attempt.getAttemptId() + "," + potatoId + "," + launcherId + "," + attempt.getPayloadKg() + "," + maxPayload + "," + excess);
            }
        }
    }

    public static void generateBestTestFlights(List<LaunchAttempt> attempts, String filepath) throws FileNotFoundException {
        File file = new File(filepath);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        List<LaunchAttempt> sortedAttempts = new ArrayList<>();
        for (LaunchAttempt attempt : attempts) {
            if (attempt != null) {
                sortedAttempts.add(attempt);
            }
        }

        Collections.sort(sortedAttempts);

        int limit = Math.min(3, sortedAttempts.size());

        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("attemptId,potatoName,launcherName,flightRating,payloadKg,points");
            for (int i = 0; i < limit; i++) {
                LaunchAttempt attempt = sortedAttempts.get(i);
                String potatoName = (attempt.getPotatoId() != null) ? attempt.getPotatoId().getName() : "";
                String launcherName = (attempt.getLauncherId() != null) ? attempt.getLauncherId().getName() : "";
                writer.println(attempt.getAttemptId() + "," + potatoName + "," + launcherName + "," + attempt.getFlightRating() + "," + attempt.getPayloadKg() + "," + attempt.getScore());
            }
        }
    }
}