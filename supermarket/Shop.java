package supermarket;

import java.util.*;


public class Shop {
    public List<List<String>> userData = new ArrayList<>();
    public List<List<String>> itemData = new ArrayList<>();
    public String currentUserAccess = "";
    public static final Scanner sc = new Scanner(System.in);
    
    public Shop() {}
    
    public boolean addUser(List<String> data) {
        int flag = 1;
        if (data.size() == 4) {
            userData.add(data);
            flag = 0;
        } else {
            flag = 1;
        }
        System.out.println(userData);
        return flag == 0 ? true : false;
    }

    public boolean addItem(List<String> data) {
        int flag = 1;
        if (data.size() == 5) {
            itemData.add(data);
            flag = 0;
        } else {
            flag = 1;
        }
        System.out.println(itemData);
        return flag == 0 ? true : false;
    }

    public boolean login() {
        String username = sc.next();
        String password = sc.next();
        for(List<String> s: userData) {
            System.out.println(s.get(0));
            System.out.println(s.get(4));
        }
        return false;
    }

    // public boolean execute(String command) {

    // }
}
