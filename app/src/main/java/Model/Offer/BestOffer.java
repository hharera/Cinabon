package Model.Offer;

public class BestOffer extends Offer implements Comparable<BestOffer> {

    public BestOffer() {
        super();
    }

    @Override
    public int compareTo(BestOffer o) {
        return (int) (o.getDiscountPercentage() - this.getDiscountPercentage());
    }
}