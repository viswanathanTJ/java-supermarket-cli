
import java.util.*;

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
                // debug
                case 99:
                    System.out.println("USERS");
                    for(Map.Entry<String, User> entry: userMap.entrySet()) {
                        System.out.println(entry.getValue());
                    }
                    System.out.println("ITEMS");
                    for(Map.Entry<Integer, Item> entry: itemMap.entrySet())
                        System.out.println(entry.getValue());
                    break;
                //
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

    public static void preload() {
        addUser(customerId++, "admin", "admin123", "admin");
        addUser(customerId++, "kaushik", "kaushik123", "customer");
        addUser(customerId++, "vignesh", "vignesh123", "customer");
        addItem(itemId++, "Good day", "Biscuit", 90, 10);
        addItem(itemId++, "Pantene", "Conditioner", 110, 10);
        addItem(itemId++, "Lux", "Soap", 25, 10);
        addItem(itemId++, "Dove", "Soap", 45, 10);
    }
    
    public static void main(String[] args) {
        // Preloading Data
            preload();
        //

        // Login
        try {
            while(true) {
                System.out.println("Enter username and password: ");
                String username = sc.next();
                if(userMap.containsKey(username)) {
                    String password = sc.next();
                    User curUser = userMap.get(username);
                    if(auth.encrypt(password).equals(curUser.getPassword())) {
                        if(curUser.getRole() == "admin")
                            adminMenu();
                        else if(curUser.getRole() == "customer")
                            customerMenu();
                    } else {
                        System.out.println("Invalid password");
                    }   
                } else {
                    System.out.println("Invalid username");
                }
            }
        } catch (Exception e) { 
            System.out.println("Good bye..");
        }
        //
    }
}
