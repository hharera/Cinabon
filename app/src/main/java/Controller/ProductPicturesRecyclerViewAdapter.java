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

public class ProductPicturesRecyclerViewAdapter extends RecyclerView.Adapter<ProductPicturesRecyclerViewAdapter.ViewHolder> {
    private List<Blob> pics;
    private Context context;

    public ProductPicturesRecyclerViewAdapter(List<Blob> pics, Context context) {
        this.pics = pics;
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
        holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(pics.get(position)
                .toBytes(), 0, pics.get(position).toBytes().length));
    }

    @Override
    public int getItemCount() {
        return pics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
        }
    }
}