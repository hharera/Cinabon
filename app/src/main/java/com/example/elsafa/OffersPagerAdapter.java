package com.example.elsafa;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elsafa.Offer.OfferView;

import java.util.List;

import Model.Offer;

public class OffersPagerAdapter extends RecyclerView.Adapter<OffersPagerAdapter.ViewHolder> {
    private final List<Offer> offers;
    private final Context context;

    public OffersPagerAdapter(List<Offer> list, Context context) {
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
                intent.putExtra("productId", offers.get(position).getProductId());
                intent.putExtra("categoryName", offers.get(position).getCategoryName());
                intent.putExtra("offerId", offers.get(position).getOfferId());
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