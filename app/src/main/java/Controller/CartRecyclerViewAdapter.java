package Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
                        holder.quantity.setText(String.valueOf(quantity));

                        float totalPrice = (product.getPrice() * quantity);
                        holder.total_price.setText(String.valueOf(totalPrice) + " EGP");

                        price[0] = product.getPrice();
                    }
                });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editClicked(holder, position, price[0]);
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCartFromProduct(position);
            }
        });
    }

    private void editClicked(ViewHolder holder, int position, float price) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        EditText editText = new EditText(context);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        builder.setView(editText);
        builder.setTitle("Enter Quantity");
        builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int quantity = Integer.parseInt(editText.getText().toString());
                holder.quantity.setText(String.valueOf(quantity));
                holder.total_price.setText(String.valueOf(quantity * price) + " EGP");
                setQuantity(position, quantity);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void setQuantity(int position, int quantity) {
        fStore.collection("Users")
                .document(auth.getUid())
                .collection("Cart")
                .document(list.get(position).getCategoryName() + list.get(position).getProductId())
                .update("quantity", quantity);
    }

    private void removeCartFromProduct(int position) {
        fStore.collection("Categories")
                .document(list.get(position).getCategoryName())
                .collection("Products")
                .document(list.get(position).getProductId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot ds) {
                        Map<String, Integer> map = (Map<String, Integer>) ds.get("carts");
                        map.remove(auth.getUid());
                        fStore.collection("Categories")
                                .document(list.get(position).getCategoryName())
                                .collection("Products")
                                .document(list.get(position).getProductId())
                                .update("carts", map);
                        removeProductFromCart(position);
                    }
                });
    }

    private void removeProductFromCart(int position) {
        fStore.collection("Users")
                .document(auth.getUid())
                .collection("Cart")
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
        ImageView imageView;
        TextView title, total_price, quantity;
        TextView edit, remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            title = itemView.findViewById(R.id.title);
            total_price = itemView.findViewById(R.id.total_price);
            quantity = itemView.findViewById(R.id.quantity);

            edit = itemView.findViewById(R.id.edit);
            remove = itemView.findViewById(R.id.remove);
        }
    }
}