public class LaunchAttempt {
    private String attemptId;
    private Potato potatoId;
    private Launcher launcherId;
    private int payloadKg;
    private int flightRating;
    public LaunchAttempt(String attemptId, Potato potatoId, Launcher launcherId, int payloadKg, int flightRating) {
        this.attemptId = attemptId;
        this.potatoId = potatoId;
        this.launcherId = launcherId;
        this.payloadKg = payloadKg;
        this.flightRating = flightRating;
    }

    public String getAttemptId() {
        return attemptId;
    }

    public Potato getPotatoId() {
        return potatoId;
    }

    public Launcher getLauncherId() {
        return launcherId;
    }

    public int getPayloadKg() {
        return payloadKg;
    }

    public int getFlightRating() {
        return flightRating;
    }
    public int getScore(){
        return potatoId.calculateScore(payloadKg, flightRating);
    }
}
