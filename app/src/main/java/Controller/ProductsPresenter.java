package Controller;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import Controller.CartRecyclerViewAdapter.ViewHolder;
import Model.Item;
import Model.Product.Product;

public class ProductsPresenter {

    OnGetProductsListener listener;

    public ProductsPresenter(OnGetProductsListener listener) {
        this.listener = listener;
    }

    private void getItemsFromFirebase(ViewHolder viewHolder, Item item, int position) {
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        fStore.collection("Categories")
                .document(item.getCategoryName())
                .collection("Products")
                .document(item.getProductId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot ds = task.getResult();
                            Product product = ds.toObject(Product.class);
                            product.setProductId(ds.getId());
                            listener.onSuccess(viewHolder, product, position);
                        } else {
                            listener.onFailed(task.getException());
                        }
                    }
                });
    }

    public void getProduct(ViewHolder viewHolder, Item item, int position) {
        getItemsFromFirebase(viewHolder, item, position);
    }
}