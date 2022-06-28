
import java.util.*;

import models.BillEntries;
import models.Item;
import models.User;

public class Sale {
    private int totalBill = 0;
    List<BillEntries> billEntries = new ArrayList<>();
    HashMap<Integer, Integer> billEntryDup = new HashMap<>();
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
        BillEntries curProduct = new BillEntries(itemId, quantity, item);
        if(billEntryDup.containsKey(itemId)) {
            int index = billEntryDup.get(itemId);
            int prevQuantity = billEntries.get(index).getQuantity();
            billEntries.get(index).setQuantity(quantity+prevQuantity);
        } else {
            billEntries.add(curProduct);
            billEntryDup.put(itemId, billEntryDup.size());
        }
    }

    public boolean viewCart() {
        if(billEntries.size() != 0) {
            this.totalBill = 0;
            for(BillEntries b : billEntries) {
                this.totalBill += b.getTotalPrice();
                System.out.println(b.toString());
            }
            System.out.println("\nTotal Price: " + this.totalBill);
            return true;
        }
        else {
            System.out.println("Cart is empty. Kindly add some products.");
            return false;
        }
    }

    public void clearCart(Map<Integer, Item> itemMap) {
        for(Map.Entry<Integer, Integer> entry : itemsInCart.entrySet())
            itemMap.get(entry.getKey()).setQuantity(entry.getValue());
        this.totalBill = 0;
    }
            
    public void applySpecificCoupon(Map<Integer, Item> itemMap, String category) {
        for(BillEntries b : billEntries) {
            if(b.getCategory().equals(category)) {
                int temp = b.getTotalPrice();
                temp -= (temp * 0.20);
                b.setTotalPrice(temp);
            }
            this.totalBill += b.getTotalPrice();
        }
    }

    public List<BillEntries> saleNow(Map<Integer, Item> itemMap, User user) {
        Scanner sn = Main.sc;
        if(billEntries.size() == 0) {
            System.out.println("[-] Cart is empty. Kindly add some products.");
            return null;
        }
        viewCart();
        System.out.println("Do you want to confirm order[y/n]: ");
        char ch = sn.next().charAt(0);
        if(ch == 'Y' || ch == 'y') {
            System.out.println("Do you want to enter coupon code[y/n]: ");
            ch = sn.next().charAt(0);
            while(ch == 'Y' || ch == 'y') {
                System.out.println("Enter your coupon code: ");
                String coupon = sn.next();
                if(user.getCoupon() == true && coupon.equals("PROMO010")) {
                    System.out.println("Coupon applied successfully.");
                    this.totalBill = 0;
                    for(BillEntries b : billEntries) {
                        this.totalBill += b.getTotalPrice();
                        System.out.println(b.toString());
                    }
                    this.totalBill -= (this.totalBill / 10);
                    System.out.println("\nTotal Price: " + this.totalBill);
                    user.setCoupon(false);
                    ch = 'n';
                } else if(coupon.equals("CLEAN10")) {
                    System.out.println("20% Offer coupon applied successfully for Soap Category");
                    applySpecificCoupon(itemMap, "Soap");
                    ch = 'n';
                }
                else {
                    System.out.println("[-] Invalid coupon code.");
                    System.out.println("Do you want to enter another coupon code[y/n]: ");
                    ch = sn.next().charAt(0);
                }
            }
            
            viewCart();
            // Storing total bill for the current cart order
            if(Main.orderHistoryTotalPrice.get(user.getUsername()) == null)
                Main.orderHistoryTotalPrice.put(user.getUsername(), new ArrayList<>());
            Main.orderHistoryTotalPrice.get(user.getUsername()).add(this.totalBill);
            List<BillEntries> temp = billEntries;
            billEntries = new ArrayList<>();
            billEntryDup = new HashMap<>();
            return temp;
        }
        else {
            System.out.println("[-] Order unsuccessfull.");
            System.out.println("Do you want to clear cart Items[y/n]: ");
            ch = sn.next().charAt(0);
            if(ch == 'Y' || ch == 'y') {
                for(Map.Entry<Integer, Integer> entry : itemsInCart.entrySet()) {
                    billEntryDup.remove(entry.getKey());
                    int resetQuantity = entry.getValue() + itemMap.get(entry.getKey()).getQuantity();
                    itemMap.get(entry.getKey()).setQuantity(resetQuantity);
                }
                this.totalBill = 0;
                billEntries = new ArrayList<>();

                System.out.println("[*] Cart cleared successfully.");                
            }
        }
        return null;
    }

}