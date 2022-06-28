package models;
public class BillEntries {
    protected int itemId;
    protected String itemName;
    protected int price;
    protected int quantity;
    protected int totalPrice;
    protected String category;
    
    Item item;
    
    public BillEntries(int itemId, int quantity, Item item) {
        this.item = item;
        this.itemId = itemId;
        this.itemName = item.getItemName();
        this.quantity = quantity;
        this.price = item.getPrice();
        this.totalPrice = this.price * this.quantity;
        this.category = item.getCategory();
    }
    
    public int getItemId() {
        return this.itemId;
    }
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    
    
    public String getItemName() {
        return this.itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    
    public int getPrice() {
        return this.price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    
    
    public int getQuantity() {
    	return this.quantity;
    }
    public void setQuantity(int quantity) {
    	this.quantity = quantity;
    }
    
    
    public int getTotalPrice() {
        return this.totalPrice;
    }
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    
    public Item getItem() {
        return this.item;
    }
    public void setItem(Item item) {
        this.item = item;
    }
    

    public String getCategory() {
        return this.category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    
    
    @Override
    public String toString() {
        return "Item id: "+getItemId()+" Item Name: "+this.item.getItemName()+" Item Category: "+this.item.getCategory()+" Quantity: "+ getQuantity() +" Price: "+this.item.getPrice() +" Total Price: "+getTotalPrice();
    }
}
