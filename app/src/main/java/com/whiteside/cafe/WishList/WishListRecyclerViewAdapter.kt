package com.whiteside.cafe.WishList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.whiteside.cafe.Cart.CartPresenter;
import com.whiteside.cafe.Cart.OnAddCartItem;
import com.whiteside.cafe.Product.OnGetProductListener;
import com.whiteside.cafe.Product.ProductPresenter;
import com.whiteside.cafe.R;

import java.util.List;

import Model.Item;
import Model.Product;

public class WishListRecyclerViewAdapter extends RecyclerView.Adapter<WishListRecyclerViewAdapter.ViewHolder>
        implements OnRemoveWishListItemListener, OnAddCartItem {

    private final FirebaseFirestore fStore;
    private final List<Item> list;
    private final Context context;
    private final FirebaseAuth auth;
    private final WishListPresenter wishListPresenter;
    private final CartPresenter cartPresenter;
    private final WishListFragment wishListFragment;

    public WishListRecyclerViewAdapter(List<Item> list, Context context, WishListFragment wishListFragment) {
        this.list = list;
        this.context = context;
        this.wishListFragment = wishListFragment;

        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        wishListPresenter = new WishListPresenter();
        wishListPresenter.setOnRemoveWishListItemListener(this);

        cartPresenter = new CartPresenter();
        cartPresenter.setOnAddCartItem(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductPresenter productPresenter = new ProductPresenter();
        productPresenter.setListener(new OnGetProductListener() {
            @Override
            public void onGetProductSuccess(Product product) {
                holder.itemImage.setImageBitmap(BitmapFactory.
                        decodeByteArray(product.getMainPic().toBytes(), 0, product.getMainPic().toBytes().length));
                holder.title.setText(product.getTitle());

                int quantity = list.get(position).getQuantity();
                float totalPrice = (product.getPrice() * quantity);

                holder.price.setText(totalPrice + " EGP");

                setListeners(holder, product);
            }

            @Override
            public void onGetProductFailed(Exception e) {

            }
        });

        productPresenter.getProduct(list.get(position));
    }

    private void setListeners(ViewHolder holder, Product product) {
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishListPresenter.removeItem(product);
            }
        });

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishListPresenter.removeItem(product);
                cartPresenter.addItem(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onRemoveWishListItemSuccess() {
        wishListFragment.updateView();
    }

    @Override
    public void onRemoveWishListItemFailed(Exception e) {

    }


    @Override
    public void onAddCartItemSuccess() {

    }

    @Override
    public void onAddCartItemFailed(Exception e) {

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