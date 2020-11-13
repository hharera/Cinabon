package Controller;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elsafa.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import Model.Item;
import Model.Product.Product;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> {

    private FirebaseFirestore fStore;
    private FirebaseAuth auth;
    private List<Item> list;
    private Context context;


    public CartRecyclerViewAdapter(List<Item> list, Context context) {
        this.list = list;
        this.context = context;
        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public int getItemViewType(int position) {
        return position == list.size() ? 2 : 1;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new TopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_card_view, parent, false));
        } else {
            return new EndViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_end_recycler_view, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == list.size()) {
            if (list.isEmpty()) {
                holder.cart_end_card_view.setVisibility(View.INVISIBLE);
            } else {
                holder.cart_end_card_view.setVisibility(View.VISIBLE);
                holder.total_bill.setText(" EGP");
            }
            return;
        }

        final float[] price = new float[1];
        fStore.collection("Categories")
                .document(list.get(position).getCategoryName())
                .collection("Products")
                .document(list.get(position).getProductId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot ds) {
                        Product product = ds.toObject(Product.class);
                        product.setProductId(ds.getId());

                        holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(product.getMainPic().toBytes()
                                , 0, product.getMainPic().toBytes().length));
                        holder.title.setText(product.getTitle());

                        int quantity = list.get(position).getQuantity();
                        holder.quantity.setValue(quantity);

                        float totalPrice = (product.getPrice() * quantity);
                        holder.total_price.setText(String.valueOf(totalPrice) + " EGP");

                        price[0] = product.getPrice();
                    }
                });

        holder.quantity.setOnValueChangedListener (new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int value) {
                holder.quantity.setValue(value);
                float totalPrice = (price[0] * value);
                holder.total_price.setText(String.valueOf(totalPrice) + " EGP");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, total_price;
        NumberPicker quantity;
        TextView total_bill, bill_cost_word;
        LinearLayout cart_end_card_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class TopViewHolder extends ViewHolder {

        public TopViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            title = itemView.findViewById(R.id.title);
            total_price = itemView.findViewById(R.id.total_price);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }

    public class EndViewHolder extends ViewHolder {

        public EndViewHolder(@NonNull View itemView) {
            super(itemView);
            total_bill = itemView.findViewById(R.id.bill_cost);
            bill_cost_word = itemView.findViewById(R.id.total_bill);
            cart_end_card_view = itemView.findViewById(R.id.cart_end_card_view);
        }
    }
}