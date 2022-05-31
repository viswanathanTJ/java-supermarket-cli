import java.util.*;

public class Sale {
    private int totalBill = 0;
    List<BillEntries> billEntries = new ArrayList<>();
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
        if(billEntries == null)
            billEntries = new ArrayList<>();
        billEntries.add(curProduct);
    }

    public boolean viewCart() {
        if(billEntries != null && billEntries.size() != 0) {
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
            System.out.println(b.toString());
        }
        System.out.println("\nTotal Price: "+this.totalBill);
    }

    public List<BillEntries> saleNow(Map<Integer, Item> itemMap, User user) {
        Scanner sn = new Scanner(System.in);
        System.out.println("Your cart items are:");
        if(viewCart() == false)
            return null;

        System.out.println("Do you want to confirm order[y/n]: ");
        char ch = sn.next().charAt(0);

        if(ch == 'Y' || ch == 'y') {
            System.out.println("Do you want to enter coupon code[y/n]: ");
            ch = sn.next().charAt(0);
            if(ch == 'Y' || ch == 'y') {
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
                } else if(coupon.equals("CLEAN10")) {
                    System.out.println("20% Offer coupon applied successfully for Soap Category");
                    applySpecificCoupon(itemMap, "Soap");
                }
                else {
                    System.out.println("Unable to apply promo code.");
                }
            } else {
                viewCart();
            }
            // Storing total bill for the current cart order
            if(Main.orderHistoryTotalPrice.get(user.getUsername()) == null)
                Main.orderHistoryTotalPrice.put(user.getUsername(), new ArrayList<>());
            Main.orderHistoryTotalPrice.get(user.getUsername()).add(this.totalBill);
            this.totalBill = 0;
            List<BillEntries> temp = billEntries;
            billEntries = null;
            return temp;
        } 
        else {
            System.out.println("Order unsuccessfull.");
            System.out.println("Do you want to clear cart Items[y/n]: ");
            ch = sn.next().charAt(0);
            if(ch == 'Y' || ch == 'y') {
                for(Map.Entry<Integer, Integer> entry : itemsInCart.entrySet()) {
                    int resetQuantity = entry.getValue() + itemMap.get(entry.getKey()).getQuantity();
                    itemMap.get(entry.getKey()).setQuantity(resetQuantity);
                }
                this.totalBill = 0;
                System.out.println("Cart cleared successfully.");                
            }
        }
        return null;
    }

}