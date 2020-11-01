package Model.Offer;

public class LastOffer extends Offer implements Comparable<LastOffer> {

    @Override
    public int compareTo(LastOffer o) {
        return o.startTime.compareTo(this.startTime);
    }
}