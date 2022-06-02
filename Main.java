import java.util.*;

public class Main {
    // GLOBAL VARIABLES
    public static final Scanner sc = new Scanner(System.in);
    /* Username:User */
    public static HashMap<String, User> userMap = new HashMap<>(); 
    /* Itemid:Item */
    public static HashMap<Integer, Item> itemMap = new HashMap<>(); 
    /* Username:Billentries */
    public static HashMap<String, List<List<BillEntries>>> orderHistory = new HashMap<>(); 
    /* Username:BillTotalPrices */
    public static HashMap<String, List<Integer>> orderHistoryTotalPrice = new HashMap<>(); 
    /* ItemId:SoldQuantity */
    public static LinkedHashMap<Integer, Integer> topSellingItems = new LinkedHashMap<>();

    public static Authentication auth = new Authentication();
    public static int customerId = 100;
    public static int itemId = 100;
    //

    public static void addItem(int itemId, String itemName, String category, int price, int quantity) {
        Item item = new Item();
        item.setItemId(itemId);
        item.setItemName(itemName);
        item.setCategory(category);
        item.setPrice(price);
        item.setQuantity(quantity);
        itemMap.put(item.getItemId(), item);
    }

    public static void addUser(int userId, String username, String password, String email, String role) {
        User user = new User();
        user.setCid(userId);
        user.setUsername(username);
        user.setPassword(auth.encrypt(password));
        user.setRole(role);
        user.setEmail(email);
        userMap.put(user.getUsername(), user);
    }

    public static void showTopSellingItems(LinkedHashMap<Integer, Integer> sellingItems) {
        if(topSellingItems.size() == 0) {
            System.out.println("There is no products sold yet.");
            return;
        }
        List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(sellingItems.size());
        entries.addAll(sellingItems.entrySet());
        int max;
        Map.Entry<Integer, Integer> temp1 = null;
        for(int i = 0; i < entries.size(); i++) {
            max = i;
            for(int j = i + 1; j < entries.size(); j++) {
                if (entries.get(j).getValue() > (entries.get(max).getValue()))
                    max = j;
            }
            if(max != i) {
                temp1 = entries.get(i);
                entries.set(i, entries.get(max));
                entries.set(max, temp1);
            }
        }
        max = 3;
        for(Map.Entry<Integer, Integer> m : entries) {
            if(max-- == 0) break;
            Item curItem = itemMap.get(m.getKey());
            System.out.println(curItem.getDetailsWithQuantity(m.getValue()));
        }
    }
    
    public static void showItems() {
        System.out.println("------------------------------ITEMS------------------------------");
        for(Item item : itemMap.values())
            System.out.println(item);
    }
    public static void addItems() {
        String name, category;
        int price, quantity;
        System.out.println("Enter item name: ");
        name = sc.nextLine();
        while(name == "") {
            System.out.println("Name cannot be empty!\nEnter item name:");
            name = sc.nextLine();
        }
        System.out.println("Enter item category: ");
        category = sc.nextLine();
        while(category == "") {
            System.out.println("Category cannot be empty!\nEnter item category:");
            category = sc.nextLine();
        }
        System.out.println("Enter item price: ");
        price = sc.nextInt();
        System.out.println("Enter item quantity: ");
        quantity = sc.nextInt();
        addItem(itemId++, name, category, price, quantity);
        System.out.println("[+] Item added successfully");
    }

    public static void adminMenu() {
        boolean loggedIn = true;
        System.out.println("\nWelcome admin!!");
        while(loggedIn == true) {
            System.out.println("\n* Waiting for your commands...");
            System.out.println("Press 1 to update item\nPress 2 to list the top selling items");
            System.out.println("Press 3 to add a new customer\nPress 4 to show all users");
            System.out.println("Press 5 to show all items\nPress 6 to add items\nPress 0 to logout");
            String choice;
            choice = sc.next();
            switch(choice) {
                case "1":
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
                case "2":
                    // OWN SORTING
                    showTopSellingItems(topSellingItems);

                    // COLLECTIONS SORT
                    // List<Map.Entry<Integer, Integer>> list =
                    // new LinkedList<Map.Entry<Integer, Integer>>(topSellingItems.entrySet());
                    
                    
                    // Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
                    //     @Override
                    //     public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                    //         return (o2.getValue()).compareTo(o1.getValue());
                    //     }
                    // });

                    // int max = 3;
                    // for (Map.Entry<Integer, Integer> entry : list) {
                    //     if(max-- == 0) break;
                    //     Item curItem = itemMap.get(entry.getKey());
                    //     System.out.println(curItem.getDetailsWithQuantity(entry.getValue()));
                    // }
                    // JAVA 8
                    // Map<Integer, Integer> top = topSellingItems.entrySet()
                    //     .stream()
                    //     .sorted(Collections.reverseOrder(Entry.comparingByValue())).limit(3)
                    //     .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue(),
                    //             (entry1, entry2) -> entry2, LinkedHashMap::new));
                    // System.out.println("Top Selling Items are");
                    //     System.out.println("Item id " + key + " Sold " + top.get(key));
                    break;
                case "3":
                    System.out.println("Enter username: ");
                    String username = sc.next();
                    if(userMap.containsKey(username))
                    System.out.println("Username already exists.");
                    else {
                        System.out.println("Enter email id: ");
                        String email = sc.next();

                        System.out.println("Enter password: ");
                        String password = sc.next();
                        addUser(customerId++, username, password, email, "customer");
                        System.out.println("Customer added successfully.");
                    }
                    break;
                case "4":
                    System.out.println("USERS");
                    for(User user : userMap.values())
                        System.out.println(user);
                    break;
                case "5":
                    showItems();
                    break;
                case "6":
                    addItems();
                    break;
                case "0":
                    System.out.println("Good Bye admin, See you later\n");
                    loggedIn = false;
                    break;
                default:
                    System.out.println("[-] Invalid Input");
                    break;            
            }
        }
    }

    public static void customerMenu(String username) {
        boolean loggedIn = true;
        Sale sale = new Sale();
        System.out.println("\nWelcome "+username);
        String choice;
        while(loggedIn == true) {
            System.out.println("\n* Waiting for your commands...");
            System.out.println("Press 1 to show menu\nPress 2 place an order\nPress 3 to view current cart");
            System.out.println("Press 4 to confirm/clear order\nPress 5 to view the order history");
            System.out.println("Press 0 to logout");
            choice = sc.next();
            switch(choice) {
                case "0":
                    System.out.println("Good Bye " + username + ", thanks for visiting :)\n");
                    loggedIn = false;
                    break;
                case "1":
                    showItems();
                    break;
                case "2":
                    showItems();
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
                            System.out.println("[+] Item successfully added to the cart");
                        }
                    } else {
                        System.out.println("[-] Invalid item id.");
                    }
                    break;
                case "3":
                    sale.viewCart();
                    break;
                case "4":
                    List<BillEntries> bill = sale.saleNow(itemMap, userMap.get(username));
                    if(bill != null) {
                        if(orderHistory.get(username) == null)
                            orderHistory.put(username, new ArrayList<>());
                        orderHistory.get(username).add(bill);
                        for(BillEntries b : bill) {
                            int t = b.getItemId();
                            int newItemSoldCount = topSellingItems.getOrDefault(t, 0)+b.getQuantity();
                            topSellingItems.put(t, newItemSoldCount);
                        }
                        System.out.println("[*] Thank you for your purchase :)\n");
                    }
                    break;
                case "5":
                    if(orderHistory.containsKey(username)) {
                        List<List<BillEntries>> bills = orderHistory.get(username);
                        int index = 0;
                        for(List<BillEntries> ls : bills) {
                            for(BillEntries b : ls) {
                                System.out.println(b.toString());
                            }
                            System.out.println("Total Bill Price: " + orderHistoryTotalPrice.get(username).get(index++));
                        }
                    }
                    else
                        System.out.println("[-] There is no previous purchase. Kindly make some orders.");
                    break;
                default:
                    System.out.println("[-] Invalid Choice");
            }
        }
    }

    public static void preload() {
        addUser(customerId++, "admin", "admin123", "admin@gmail.com", "admin");
        addUser(customerId++, "kaushik", "kaushik123", "kaush@gmail.com", "customer");
        addUser(customerId++, "vignesh", "vignesh123", "default@gmail.com", "customer");
        addUser(customerId++, "viswa", "viswa", "viswa@gmail.com", "customer");
        addItem(itemId++, "Good day", "Biscuit", 90, 10);
        addItem(itemId++, "Pantene", "Conditioner", 110, 10);
        addItem(itemId++, "Lux", "Soap", 25, 10);
        addItem(itemId++, "Dove", "Soap", 45, 10);
    }
    
    public static void main(String[] args) {
        // Preloading Data
        preload();

        // Login
        System.out.println("[*] Welcome to JAVA CLI E-Commerce Application [*]\n");
        try {
            while(true) {
                System.out.print("Enter username: ");
                String username = sc.next();
                if(userMap.containsKey(username)) {
                    System.out.print("Enter password: ");
                    String password = sc.next();
                    User curUser = userMap.get(username);
                    String userPassword = auth.decrypt(curUser.getPassword());
                    if(password.equals(userPassword)) {
                        if(curUser.getRole() == "admin")
                            adminMenu();
                        else if(curUser.getRole() == "customer")
                            customerMenu(username);
                    } else {
                        System.out.println("[-] Invalid password");
                    }   
                } else {
                    System.out.println("[-] Invalid username");
                }
            }
        } catch (Exception e) { 
            System.out.println(e);
            System.out.println("Good bye..");
        }
    }
}
