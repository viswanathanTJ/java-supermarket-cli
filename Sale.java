import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Sale {
    private int totalBill = 0;
    StringBuilder bill = new StringBuilder();
    HashMap<Integer, Integer> itemsInCart = new HashMap<>();
    
    public int getTotalBill() {
        return this.totalBill;
    }
    public void setTotalBill(int totalBill) {
        this.totalBill = totalBill;
    }

    public void addToCart(int itemId, int quantity, Item item) {
        itemsInCart.put(itemId, quantity);
        int curPrice = item.getPrice() * quantity;
        this.totalBill += curPrice;
        bill.append(item.toString() + " Product Price: " + curPrice + "\n");
    }

    public void viewCart() {
        System.out.println(bill.toString() + "\nTotal Price: " + this.totalBill);
    }

    public boolean saleNow(Map<Integer, Item> itemMap) {
        System.out.println("Your cart items are:");
        viewCart();
        System.out.println("Do you want to confirm order[y/n]: ");
        Scanner sn = new Scanner(System.in);
        char ch = sn.next().charAt(0);
        if(ch == 'Y' || ch == 'y') {
            return true;
        }
        else {
            System.out.println("Order unsuccessfull.");
            System.out.println("Do you want to clear cart Items[y/n]: ");
            ch = sn.next().charAt(0);
            if(ch == 'Y' || ch == 'y') {
                for(Map.Entry<Integer, Integer> entry : itemsInCart.entrySet())
                    itemMap.get(entry.getKey()).setQuantity(entry.getValue()); 
                System.out.println("Cart cleared successfully.");                
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return bill.toString();
    }
}