package Model;

import java.util.List;

public class Category {

    private String title;
    private int resDrawable;
    private List<Item> itemList;

    public Category() {
    }

    public Category(String title, int resDrawable) {
        this.title = title;
        this.resDrawable = resDrawable;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setResDrawable(int resDrawable) {
        this.resDrawable = resDrawable;
    }

    public int getResDrawable() {
        return resDrawable;
    }

    public String getTitle() {
        return title;
    }

}
