package Controller;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elsafa.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

import Model.Item;
import Model.Product.Product;

public class WishListRecyclerViewAdapter extends RecyclerView.Adapter<WishListRecyclerViewAdapter.ViewHolder> {

    private FirebaseFirestore fStore;
    private List<Item> list;
    private Context context;
    private FirebaseAuth auth;


    public WishListRecyclerViewAdapter(List<Item> list, Context context) {
        this.list = list;
        this.context = context;
        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_card_view, parent, false));
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
                        product.setProductId(ds.getId());

                        holder.itemImage.setImageBitmap(BitmapFactory.
                                decodeByteArray(product.getMainPic().toBytes(), 0, product.getMainPic().toBytes().length));
                        holder.title.setText(product.getTitle());

                        int quantity = list.get(position).getQuantity();
                        float totalPrice = (product.getPrice() * quantity);
                        holder.price.setText(String.valueOf(totalPrice) + " EGP");
                    }
                });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeWishFromProduct(position);
            }
        });


        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCartToProduct(position);
            }
        });
    }

    private void removeWishFromProduct(int position) {
        fStore.collection("Categories")
                .document(list.get(position).getCategoryName())
                .collection("Products")
                .document(list.get(position).getProductId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot ds) {
                        Map<String, Integer> map = (Map<String, Integer>) ds.get("wishes");
                        map.remove(auth.getUid());
                        fStore.collection("Categories")
                                .document(list.get(position).getCategoryName())
                                .collection("Products")
                                .document(list.get(position).getProductId())
                                .update("wishes", map);
                        removeProductFromWishList(position);
                    }
                });
    }

    private void addCartToProduct(int position) {
        fStore.collection("Categories")
                .document(list.get(position).getCategoryName())
                .collection("Products")
                .document(list.get(position).getProductId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot ds) {
                        Map<String, Integer> map = (Map<String, Integer>) ds.get("carts");
                        map.put(auth.getUid(), 1);
                        fStore.collection("Categories")
                                .document(list.get(position).getCategoryName())
                                .collection("Products")
                                .document(list.get(position).getProductId())
                                .update("carts", map);

                        addProductToCart(position);
                    }
                });
    }

    private void addProductToCart(int position) {
        fStore.collection("Users")
                .document(auth.getUid())
                .collection("Cart")
                .document(list.get(position).getCategoryName() + list.get(position).getProductId())
                .set(list.get(position));

        removeWishFromProduct(position);
    }

    private void removeProductFromWishList(int position) {
        fStore.collection("Users")
                .document(auth.getUid())
                .collection("WishList")
                .document(list.get(position).getCategoryName() + list.get(position).getProductId())
                .delete();

        list.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView title, price, remove, addToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            remove = itemView.findViewById(R.id.remove);
            addToCart = itemView.findViewById(R.id.add_to_cart);
        }
    }
}