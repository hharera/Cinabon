package com.example.elsafa.Cart;

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

import com.example.elsafa.Product.OnGetProductListener;
import com.example.elsafa.Product.ProductPresenter;
import com.example.elsafa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import Model.Item;
import Model.Product;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> implements OnRemoveCartItem {

    private final CartFragment cartFragment;
    private final FirebaseFirestore fStore;
    private final FirebaseAuth auth;
    private final List<Item> list;
    private final Context context;
    private final CartPresenter cartPresenter;


    public CartRecyclerViewAdapter(List<Item> list, Context context, CartFragment cartFragment) {
        this.list = list;
        this.context = context;
        this.cartFragment = cartFragment;
        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        cartPresenter = new CartPresenter();
        cartPresenter.setOnRemoveCartItem(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductPresenter productPresenter = new ProductPresenter();
        productPresenter.setListener(new OnGetProductListener() {
            @Override
            public void onGetProductSuccess(Product product) {
                holder.imageView.setImageBitmap(BitmapFactory.
                        decodeByteArray(product.getMainPic().toBytes(), 0, product.getMainPic().toBytes().length));
                holder.title.setText(product.getTitle());

                int quantity = list.get(position).getQuantity();
                holder.quantity.setText(String.valueOf(quantity));

                float totalPrice = (product.getPrice() * quantity);
                holder.total_price.setText(totalPrice + " EGP");

                setListener(holder, position, product);
            }

            @Override
            public void onGetProductFailed(Exception e) {

            }
        });

        productPresenter.getProduct(list.get(position));
    }

    private void setListener(ViewHolder holder, int position, Product product) {
        setEditListener(holder, position, product.getPrice());
        setRemoveListener(holder, product);
    }

    private void setRemoveListener(ViewHolder holder, Product product) {
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartPresenter.removeItem(product);
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

                cartPresenter.updateQuantity(list.get(position), quantity);
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



    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onRemoveCartItemSuccess() {
        cartFragment.updateView();
    }

    @Override
    public void onRemoveCartItemFailed(Exception e) {

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