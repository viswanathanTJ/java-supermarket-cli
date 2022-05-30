import java.util.*;


public class Main {
    public static final Scanner sc = new Scanner(System.in);
    public static HashMap<String, User> userMap = new HashMap<>();
    public static HashMap<Integer, Item> itemMap = new HashMap<>();
    public static HashMap<String, List<String>> orderHistory = new HashMap<>();
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
                        addUser(customerId++, username, password, "customer");
                        System.out.println("Customer added successfully.");
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

    public static void customerMenu(String username) {
        boolean loggedIn = true;
        Sale sale = new Sale();
        while(loggedIn == true) {
            System.out.println("Press 1 to place an order\nPress 2 to view the order history\nPress 0 to logout");
            int choice = sc.nextInt();
            switch(choice) {
                case 0:
                    loggedIn = false;
                    break;
                case 1:
                    System.out.println("Press 1 to add item in cart\nPress 2 to view cart\nPress 3 to confirm order\nPress 4 to show menu");
                    int orderChoice = sc.nextInt();
                    switch(orderChoice) {
                        case 1:
                            System.out.println("Enter item id: ");
                            int itemId = sc.nextInt();
                            if(itemMap.containsKey(itemId)) {
                                System.out.println("Enter quantity: ");
                                int quantity = sc.nextInt();
                                Item curItem = itemMap.get(itemId);
                                if(quantity > curItem.getQuantity()) {
                                    System.out.println("Sorry. Number of quantity availablity is lower than you ordering.");
                                    System.out.println("We have the maximum quantity is " + curItem.getQuantity());
                                } else {
                                    sale.addToCart(itemId, quantity, curItem);
                                    curItem.setQuantity(curItem.getQuantity() - quantity);
                                    System.out.println("Item successfully added to the cart");
                                }
                            } else {
                                System.out.println("Invalid item id.");
                            }
                            break;
                        case 2:
                            sale.viewCart();
                            break;
                        case 3:
                            String res = sale.saleNow(itemMap, userMap.get(username));
                            if(res != "") {
                                if(orderHistory.containsKey(username))
                                    orderHistory.get(username).add(res);
                                else {
                                    orderHistory.put(username, new ArrayList<String>());
                                    orderHistory.get(username).add(res);
                                }
                                System.out.println("Thank you for your purchase :)");
                            }
                            break;
                        case 4:
                            for(Map.Entry<Integer, Item> entry: itemMap.entrySet())
                                System.out.println(entry.getValue());
                        break;
                    }
                    break;
                    case 2:
                    if(orderHistory.containsKey(username)) {
                        List<String> bills = orderHistory.get(username);
                        for(String s : bills)
                           System.out.println(s);
                    }
                    else
                        System.out.println("There is no previous purchase. Kindly make some orders.");
                    break;                
            }
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
                            customerMenu(username);
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
