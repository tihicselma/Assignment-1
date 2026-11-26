public class Launcher {
    private final String id;
    private final String name;
    private final int maxPayloadKg;
    public Launcher(String id, String name, int maxPayloadKg) {
        this.id=id;
        this.name=name;
        this.maxPayloadKg=maxPayloadKg;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxPayloadKg() {
        return maxPayloadKg;
    }
}
