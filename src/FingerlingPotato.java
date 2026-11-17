public class FingerlingPotato extends Potato implements Scorable{
    public FingerlingPotato(String potatoId, String name, int maxPayloadKg)
    {
        super(potatoId, name, "FINGERLING");
    }
@Override
    public int calculateScore(int payloadKg, int flightRating) {
        return 3*payloadKg+2*flightRating;
}
}
