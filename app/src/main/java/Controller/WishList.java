package Controller;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import Model.Item;
import Model.Product.CompleteProduct;

public class WishList {

    private final FirebaseAuth auth;
    private final FirebaseFirestore fStore;

    public WishList() {
        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }

    public Boolean addItem(CompleteProduct product) {
        Map<String, Integer> wishList = product.getWishes();
        wishList.put(auth.getUid(), 1);
        product.setWishes(wishList);

        Thread thread = new Thread() {
            @Override
            public boolean isInterrupted() {
                return fStore.collection("Categories")
                        .document(product.getCategoryName())
                        .collection("Products")
                        .document(product.getProductId())
                        .update("wishes", wishList).isSuccessful();
            }
        };


        Item item = new Item();
        item.setTime(Timestamp.now());
        item.setCategoryName(product.getCategoryName());
        item.setProductId(product.getProductId());
        item.setQuantity(1);

        Thread thread2 = new Thread() {
            @Override
            public boolean isInterrupted() {
                return fStore.collection("Users")
                        .document(auth.getUid())
                        .collection("WishList")
                        .document(product.getCategoryName() + product.getProductId())
                        .set(item).isSuccessful();
            }
        };

        thread.start();
        if (thread.isInterrupted()) {
            thread2.start();
            return thread2.isInterrupted();
        }
        return false;

    }

    public Boolean removeItem(CompleteProduct product) {
        Map<String, Integer> wishList = product.getWishes();
        wishList.remove(auth.getUid());

        product.setWishes(wishList);

        Thread thread = new Thread() {
            @Override
            public boolean isInterrupted() {
                return fStore.collection("Categories")
                        .document(product.getCategoryName())
                        .collection("Products")
                        .document(product.getProductId())
                        .update("wishes", wishList).isSuccessful();
            }
        };

        Thread thread2 = new Thread() {
            @Override
            public boolean isInterrupted() {
                return fStore.collection("Users")
                        .document(auth.getUid())
                        .collection("WishList")
                        .document(product.getCategoryName() + product.getProductId())
                        .delete().isSuccessful();
            }
        };

        thread.start();
        if (thread.isInterrupted()) {
            thread2.start();
            return thread2.isInterrupted();
        }
        return false;


    }
}
