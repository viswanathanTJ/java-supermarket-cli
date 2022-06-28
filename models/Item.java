package models;
public class Item {
    protected int itemId;
    protected String itemName;
    protected String category;
    protected int price;
    protected int quantity;

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
    public String getCategory() {
        return this.category;
    }
    public void setCategory(String category) {
        this.category = category;
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

    public String getDetailsWithQuantity(int quantity) {
        return "Item ID: " + getItemId() + " Name: " + getItemName() + " Category: " + getCategory() + " Price: " + getPrice() + " Quantity: " + quantity;
    }

    @Override
    public String toString() {
        return "Item ID: " + getItemId() + " Name: " + getItemName() + " Category: " + getCategory() + " Price: " + getPrice() + " Quantity: " + getQuantity();
    }
}
