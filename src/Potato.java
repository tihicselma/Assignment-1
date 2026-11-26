public abstract class Potato implements Scorable{
    private final String potatoId;
    private final String name;
    private final String type;
   public Potato(String potatoId, String name, String type){
        this.potatoId = potatoId;
        this.name = name;
        this.type = type;
    }

    public String getPotatoId() {
        return potatoId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
