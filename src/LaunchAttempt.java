public class LaunchAttempt implements Comparable<LaunchAttempt> {
    private final String attemptId;
    private final Potato potato;
    private final Launcher launcher;
    private final int payloadKg;
    private final int flightRating;

    public LaunchAttempt(String attemptId, Potato potato, Launcher launcher, int payloadKg, int flightRating) {
        this.attemptId = attemptId;
        this.potato = potato;
        this.launcher = launcher;
        this.payloadKg = payloadKg;
        this.flightRating = flightRating;
    }

    public String getAttemptId() {
        return attemptId;
    }

    public Potato getPotato() {
        return potato;
    }

    public Launcher getLauncher() {
        return launcher;
    }

    public int getPayloadKg() {
        return payloadKg;
    }

    public int getFlightRating() {
        return flightRating;
    }

    public int getScore() {
        return potato.calculateScore(payloadKg, flightRating);
    }
    public boolean isOverLimit(){
        return payloadKg > launcher.getMaxPayloadKg();
    }
    public int getExcessKg(){
        if(isOverLimit()){
            return payloadKg - launcher.getMaxPayloadKg();
        }
        return 0;
    }

    @Override
    public int compareTo(LaunchAttempt other) {
        int ratingCompare = Integer.compare(other.flightRating, this.flightRating);
        if (ratingCompare != 0) {
            return ratingCompare;
        }

        int payloadCompare = Integer.compare(other.payloadKg, this.payloadKg);
        if (payloadCompare != 0) {
            return payloadCompare;
        }

        return this.attemptId.compareTo(other.attemptId);
    }
}