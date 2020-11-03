package Controller;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elsafa.R;
import com.google.firebase.firestore.Blob;

import java.util.List;
import java.util.Map;

public class CartItemsRecyclerViewAdapter extends RecyclerView.Adapter<CartItemsRecyclerViewAdapter.ViewHolder> {
    private Map<String , Integer> offersCart;
    private Map<String , Integer> productCart;
    private Context context;

    public CartItemsRecyclerViewAdapter(Map<String, Integer> offersCart, Map<String, Integer> productCart, Context context) {
        this.offersCart = offersCart;
        this.productCart = productCart;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(pics.get(position)
//                .toBytes(), 0, pics.get(position).toBytes().length));
    }

    @Override
    public int getItemCount() {
        return this.offersCart.size() + this.productCart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
        }
    }
}