package Controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elsafa.ProductView;
import com.example.elsafa.R;

import java.util.List;

import Model.Product.Product;

public class CategoryProductsRecyclerView extends RecyclerView.Adapter<CategoryProductsRecyclerView.ViewHolder> {
    private List<Product> products;
    private Context context;

    public CategoryProductsRecyclerView(List<Product> list, Context context) {
        this.products = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productMainImage.setImageBitmap(BitmapFactory.decodeByteArray(products.get(position)
                .getMainPic().toBytes(), 0, products.get(position).getMainPic().toBytes().length));

        holder.price.setText(this.products.get(position).getPrice() + " EGP");
        holder.title.setText(this.products.get(position).getTitle());

        holder.productCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(context, ProductView.class);
                intent.putExtra("productId", products.get(position).getProductId());
                intent.putExtra("categoryName", products.get(position).getCategoryName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productMainImage;
        private TextView title, price;
        private CardView productCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            productMainImage = itemView.findViewById(R.id.product_main_image);
            productCardView = itemView.findViewById(R.id.category_product_card_view);
        }
    }
}