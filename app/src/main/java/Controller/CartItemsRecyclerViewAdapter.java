package Controller;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elsafa.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Model.Offer.CartItem;

public class CartItemsRecyclerViewAdapter extends RecyclerView.Adapter<CartItemsRecyclerViewAdapter.ViewHolder> {

    private FirebaseFirestore fStore;
    private Map<String, Integer> offers;
    private Map<String, Integer> products;
    private Context context;
    private List<String> offersIDs;
    private List<String> productsIDs;

    public CartItemsRecyclerViewAdapter(Map<String, Integer> offersCart, Map<String, Integer> productCart, Context context) {
        this.offers = offersCart;
        this.products = productCart;
        this.context = context;
        fStore = FirebaseFirestore.getInstance();
        offersIDs = new ArrayList<>();
        productsIDs = new ArrayList<>();
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_card_view, parent, false);
        return new ViewHolder(view);
    }

    public void setOffers(Map<String, Integer> offers) {
        this.offers = offers;
        offersIDs.addAll(offers.keySet());
        notifyDataSetChanged();
    }

    public void setProducts(Map<String, Integer> products) {
        this.products = products;
        productsIDs.addAll(products.keySet());
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String offerId = offersIDs.get(position);
        if (position <= offers.size()) {
            fStore.collection("Offers")
                    .document(offersIDs.get(position))
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot ds) {
                            CartItem item = ds.toObject(CartItem.class);
                            holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(item.getOfferPic().toBytes()
                            , 0 , item.getOfferPic().toBytes().length));
                            holder.title.setText(item.getTitle());
                            int quantity = offers.get(offerId).intValue();
                            float totalPrice = (item.getPrice() * quantity);
                            holder.total_price.setText(String.valueOf(totalPrice));
                            holder.quantity.setValue(offers.get(offersIDs.get(position)));
                        }
                    });
        } else {
            fStore.collection("")
                    .document(offersIDs.get(position))
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot ds) {
                            CartItem item = ds.toObject(CartItem.class);
                            holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(item.getOfferPic().toBytes()
                                    , 0 , item.getOfferPic().toBytes().length));
                            holder.title.setText(item.getTitle());
                            float totalPrice = item.getPrice() * offers.get(offersIDs.get(position)).intValue();
                            holder.total_price.setText(String.valueOf(totalPrice));
                            holder.quantity.setValue(offers.get(offersIDs.get(position)));
                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return this.offers.size() + this.products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, total_price;
        NumberPicker quantity;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            title = itemView.findViewById(R.id.title);
            total_price = itemView.findViewById(R.id.total_price);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}