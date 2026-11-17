public class YukonGoldPotato extends Potato implements Scorable{
    public YukonGoldPotato(String potatoId, String name) {
        super(potatoId, name, "YukonGoldPotato");
    }
    @Override
    public int calculateScore(int payloadKg, int flightRating) {
        return 2*payloadKg + 3*flightRating;
    }
}
