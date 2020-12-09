package Controller.Cart;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elsafa.R;
import com.example.elsafa.ui.Cart.CartFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

import Model.Item;
import Model.Product.Product;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> implements OnGetProductsListener {

    private final CartFragment cartFragment;
    private final FirebaseFirestore fStore;
    private final FirebaseAuth auth;
    private final List<Item> list;
    private final Context context;
    private final ProductsPresenter presenter;


    public CartRecyclerViewAdapter(List<Item> list, Context context, CartFragment cartFragment) {
        this.list = list;
        this.context = context;
        this.cartFragment = cartFragment;
        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        presenter = new ProductsPresenter(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        presenter.getProduct(holder, list.get(position), position);

        setRemoveListener(holder, position);
    }

    private void addPrice(float v) {
        cartFragment.editTotalBill(v);
    }

    private void setRemoveListener(ViewHolder holder, int position) {
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCartFromProduct(position);
            }
        });
    }

    private void setEditListener(ViewHolder holder, int position, float price) {
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editClicked(holder, position, price);
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
                holder.total_price.setText(quantity * price + " EGP");
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

    private void removeProductFromBill(Double price) {
        cartFragment.editTotalBill(-1 * price);
    }

    private void removeProductFromCart(int position) {
        fStore.collection("Users")
                .document(auth.getUid())
                .collection("Cart")
                .document(list.get(position).getCategoryName() + list.get(position).getProductId())
                .delete();

        cartFragment.updateView();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onSuccess(ViewHolder holder, Product product, int position) {
        holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(product.getMainPic().toBytes()
                , 0, product.getMainPic().toBytes().length));
        holder.title.setText(product.getTitle());

        int quantity = list.get(position).getQuantity();
        holder.quantity.setText(String.valueOf(quantity));

        float totalPrice = (product.getPrice() * quantity);
        holder.total_price.setText(totalPrice + " EGP");

        setEditListener(holder, position, product.getPrice());
    }

    @Override
    public void onFailed(Exception e) {

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