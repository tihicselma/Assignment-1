public class PurplePotato extends Potato implements Scorable{
    public PurplePotato(String potatoId, String name)
    {
        super(potatoId, name, "PURPLE");
    }
    @Override
    public int calculateScore(int payloadKg, int flightRating) {
        return 4*payloadKg + flightRating;
    }
}
