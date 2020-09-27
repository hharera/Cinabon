package Model;

public class Category {

    String title;
    int resDrawable;

    public Category() {
    }

    public Category(String title, int resDrawable) {
        this.title = title;
        this.resDrawable = resDrawable;
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
