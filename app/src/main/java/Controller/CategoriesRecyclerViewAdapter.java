package Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elsafa.R;

import java.util.List;

import Model.Category;

public class CategoriesRecyclerViewAdapter extends RecyclerView.Adapter<CategoriesRecyclerViewAdapter.ViewHolder> {

    Context context;
    List<Category> list;

    public CategoriesRecyclerViewAdapter(Context context, List<Category> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.category_title.setText(list.get(position).getTitle());
        holder.category_img.setImageResource(list.get(position).getResDrawable());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView category_title;
        ImageView category_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category_title = itemView.findViewById(R.id.category_title);
            category_img = itemView.findViewById(R.id.category_img);
        }
    }
}
