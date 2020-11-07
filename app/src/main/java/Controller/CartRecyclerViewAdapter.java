package Controller;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elsafa.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.List;

import Model.CartItem;
import Model.Product.Product;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> {

    private FirebaseFirestore fStore;
    private FirebaseAuth auth;
    private List<CartItem> list;
    private Context context;

    public CartRecyclerViewAdapter(List<CartItem> list, Context context) {
        this.list = list;
        this.context = context;
        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        fStore.collection("Categories")
                .document(list.get(position).getCategoryName())
                .collection("Products")
                .document(list.get(position).getProductId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot ds) {
                        Product product = ds.toObject(Product.class);
                        product.setProductId(list.get(position).getProductId());

                        holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(product.getMainPic().toBytes()
                                , 0, product.getMainPic().toBytes().length));
                        holder.title.setText(product.getTitle());
                        int quantity = list.get(position).getQuantity();
                        float totalPrice = (product.getPrice() * quantity);
                        holder.total_price.setText(String.valueOf(totalPrice) + " EGP");
                        holder.quantity.setValue(quantity);
                    }
                });
    }

    private void getInfo(ViewHolder holder, int position, Product product) {


//        holder.quantity.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                holder.total_price.setText((newVal * product.getPrice()) + " EGP");
//
//                fStore.collection("Users")
//                        .document(auth.getUid())
//                        .collection("Cart");
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
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