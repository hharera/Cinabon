package Controller;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elsafa.CategoryProducts;
import com.example.elsafa.R;

import java.util.List;

import Model.Category;

public class CategoriesRecyclerViewAdapter extends RecyclerView.Adapter<CategoriesRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private String[] categoryName;
    private TypedArray categoryDrawable;

    public CategoriesRecyclerViewAdapter(Context context) {
        this.context = context;
        categoryName = context.getResources().getStringArray(R.array.categories);
        categoryDrawable = context.getResources().obtainTypedArray(R.array.categories_drawables);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.category_title.setText(categoryName[position]);
        holder.category_img.setImageResource(categoryDrawable.getResourceId(position, 0));

        holder.category_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CategoryProducts.class);
                intent.putExtra("category", categoryName[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryName.length;
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
