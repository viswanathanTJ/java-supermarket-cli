
import java.util.*;
class User {
    protected int cid;
    protected String username;
    protected String password;
    protected String role;

    public int getCid() {
        return this.cid;
    }
    public void setCid(int cid) {
        this.cid = cid;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return this.role;
    }
    public void setRole(String role) {
        this.role = role;
    }

}
class Item {
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
}


public class Main {
    public static final Scanner sc = new Scanner(System.in);
    public static Map<String, User> userMap = new HashMap<>();
    public static Map<Integer, Item> itemMap = new HashMap<>();
    public static Authentication auth = new Authentication();
    public static int customerId = 100;
    public static int itemId = 100;

    public static void addItem(int itemId, String itemName, String category, int price, int quantity) {
        Item item = new Item();
        item.setItemId(itemId);
        item.setItemName(itemName);
        item.setCategory(category);
        item.setPrice(price);
        item.setQuantity(quantity);
        itemMap.put(item.getItemId(), item);
    }

    public static void addUser(int userId, String username, String password, String role) {
        User user = new User();
        user.setCid(userId);
        user.setUsername(username);
        user.setPassword(auth.encrypt(password));
        user.setRole(role);
        userMap.put(user.getUsername(), user);
    }

    public static void adminMenu() {
        boolean loggedIn = true;
        while(loggedIn == true) {
            System.out.println("Press 1 to update item\nPress 2 to list the top selling items");
            System.out.println("Press 3 to add a new customer\nPress 0 to logout");
            int choice = sc.nextInt();
            switch(choice) {
                case 1:
                    System.out.println("Enter item id: ");
                    int itemId = sc.nextInt();
                    if(itemMap.containsKey(itemId)) {
                        System.out.println("Enter new price: ");
                            itemMap.get(itemId).setPrice(sc.nextInt());
                            System.out.println("Enter available quantity: ");
                            itemMap.get(itemId).setQuantity(sc.nextInt());
                        }
                    else {
                        System.out.println("Invalid item id");
                    }
                    break;
                case 3:
                    System.out.println("Enter username: ");
                    String username = sc.next();
                    if(userMap.containsKey(username))
                        System.out.println("Username already exists.");
                    else {
                        System.out.println("Enter password: ");
                        String password = sc.next();
                        addUser(customerId++, username, auth.encrypt(password), "customer");
                    }
                    break;
                case 0:
                    loggedIn = false;                
            }
        }
    }

    public static void customerMenu() {
        boolean loggedIn = true;
        while(loggedIn == true) {
            System.out.println("Press 1 to place an order\nPress 2 to view the order history\nPress 0 to logout");

        }

    }
    public static void main(String[] args) {
        // Preloading Data
        addUser(customerId++, "admin", "admin123", "admin");
        addUser(customerId++, "kaushik", "kaushik123", "customer");
        addUser(customerId++, "vignesh", "vignesh123", "customer");
        addItem(itemId++, "Good day", "Biscuit", 90, 10);
        addItem(itemId++, "Pantene", "Conditioner", 110, 10);
        addItem(itemId++, "Lux", "Soap", 25, 10);
        addItem(itemId++, "Dove", "Soap", 45, 10);
        //

        // Login
        while(true) {
            System.out.println("Enter username and password: ");
            String username = sc.next();
            if(userMap.containsKey(username)) {
                String password = sc.next();
                User curUser = userMap.get(username);
                if(auth.encrypt(password).equals(curUser.getPassword())) {
                    if(curUser.getRole() == "admin") {  
                        adminMenu();
                    } else if(curUser.getRole() == "customer") {
                        customerMenu();
                    }
                } else {
                    System.out.println("Invalid password");
                }   
            } else {
                System.out.println("Invalid username");
            }
        }
    }
}
