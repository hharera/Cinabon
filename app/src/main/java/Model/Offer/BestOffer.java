package Model.Offer;

public class BestOffer extends Offer implements Comparable<BestOffer>  {

    public BestOffer() {
    }

    @Override
    public int compareTo(BestOffer o) {
        return (int) (o.discountPercentage - this.discountPercentage);
    }
}