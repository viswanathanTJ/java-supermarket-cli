package supermarket;
import java.util.*;


public class PreLoadData {
    public static final Shop preShop = new Shop();

    public static boolean setUserData(String[] data) {
        if (data.length != 4)
            return false;
        ArrayList<String> UserData = new ArrayList<>();
        for (String s : data)
            UserData.add(s);
        preShop.addUser(UserData);
        return true;
    }

    public static boolean setItemData(String[] data) {
        if (data.length != 5)
            return false;
        ArrayList<String> ItemData = new ArrayList<>();
        for (String s : data) {
            ItemData.add(s);
        }
        boolean res = preShop.addItem(ItemData);
        return res;
    }

    public boolean loadUserData() {
        Authentication ad = new Authentication();
        String s1Pass = ad.encrypt("admin123");
        String s2Pass = ad.encrypt("kaushik123");
        String s3Pass = ad.encrypt("vignesh123");
        String[] s1 = { "100", "admin", s1Pass, "admin" };
        String[] s2 = { "101", "kaushik", s2Pass, "customer" };
        String[] s3 = { "102", "vignesh", s3Pass, "customer" };
        int flag = 0;
        flag = (setUserData(s1)) ? 1 : 0;
        flag = (setUserData(s2)) ? 1 : 0;
        flag = (setUserData(s3)) ? 1 : 0;
        return flag == 1 ? true : false;
    }

    public Boolean loadItemData() {
        int flag = 1;
        String[] s1 = { "100", "Good day", "Biscuit", "90", "10" };
        String[] s2 = { "101", "Pantene", "Conditioner", "110", "10" };
        String[] s3 = { "102", "Lux", "Soap", "25", "10" };
        String[] s4 = { "103", "Dove", "Soap", "45", "10" };

        flag = (setItemData(s1)) ? 0 : 1;
        flag = (setItemData(s2)) ? 0 : 1;
        flag = (setItemData(s3)) ? 0 : 1;
        flag = (setItemData(s4)) ? 0 : 1;
        return flag == 1 ? false : true;
    }
}