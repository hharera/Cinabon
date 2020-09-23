package Controller;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import Model.Offer;

public class OffersViewFlipperAdapter implements Adapter {
    List<Offer> offers;
    Context context;
    private StorageReference sRef;

    public OffersViewFlipperAdapter(List<Offer> list, Context context) {
        this.offers = list;
        this.context = context;
        sRef = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return offers.size();
    }

    @Override
    public Object getItem(int position) {
        return offers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(offers.get(position).getBitmap());
        return imageView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return offers.isEmpty();
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
