public class RussetPotato extends Potato implements Scorable{
    public RussetPotato(String potatoId, String name, String type) {
        super(potatoId, name, "RUSSETyea");
    }

    @Override
    public int calculateScore(int payloadKg, int flightRating) {
        return 2*payloadKg + flightRating;
    }
}
