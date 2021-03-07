package Model

class Category {
    private var title: String? = null
    private var resDrawable = 0
    private var itemList: MutableList<Product?>? = null

    constructor()
    constructor(title: String?, resDrawable: Int) {
        this.title = title
        this.resDrawable = resDrawable
    }

    fun getItemList(): MutableList<Product?>? {
        return itemList
    }

    fun setItemList(itemList: MutableList<Product?>?) {
        this.itemList = itemList
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun setResDrawable(resDrawable: Int) {
        this.resDrawable = resDrawable
    }

    fun getResDrawable(): Int {
        return resDrawable
    }

    fun getTitle(): String? {
        return title
    }
}