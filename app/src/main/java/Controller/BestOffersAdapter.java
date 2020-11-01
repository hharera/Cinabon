package Controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elsafa.OfferView;
import com.example.elsafa.R;

import java.util.List;

import Model.Offer.BestOffer;
import Model.Offer.Offer;

public class BestOffersAdapter extends RecyclerView.Adapter<BestOffersAdapter.ViewHolder> {
    private List<BestOffer> offers;
    private Context context;

    public BestOffersAdapter(List<BestOffer> list, Context context) {
        this.offers = list;
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
        holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(offers.get(position)
                .getOfferPic().toBytes(), 0, offers.get(position).getOfferPic().toBytes().length));

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OfferView.class);
                intent.putExtra("OfferId", offers.get(position).getOfferId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
        }
    }
}