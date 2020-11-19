package Controller;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private float totalBill = 0;


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
            return new EndViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_tail, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == list.size()) {
            if (list.isEmpty()) {
                holder.cart_tail.setVisibility(View.INVISIBLE);
            } else {
                holder.cart_tail.setVisibility(View.VISIBLE);
                holder.total_bill.setText(totalBill + " EGP");
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
                        holder.quantity.setText(String.valueOf(quantity));

                        float totalPrice = (product.getPrice() * quantity);
                        holder.total_price.setText(String.valueOf(totalPrice) + " EGP");

                        price[0] = product.getPrice();
                        totalBill += totalPrice;
                    }
                });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editClicked(holder, position, price[0]);
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

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, total_price, quantity;
        TextView total_bill, bill_cost_word, edit, remove;
        RelativeLayout cart_tail;


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

            edit = itemView.findViewById(R.id.edit);
            remove = itemView.findViewById(R.id.remove);
        }
    }

    public class EndViewHolder extends ViewHolder {

        public EndViewHolder(@NonNull View itemView) {
            super(itemView);
            total_bill = itemView.findViewById(R.id.bill_cost);
            bill_cost_word = itemView.findViewById(R.id.total_bill);
            cart_tail = itemView.findViewById(R.id.cart_tail);
        }
    }
}