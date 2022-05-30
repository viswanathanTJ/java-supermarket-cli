import supermarket.PreLoadData;
import supermarket.Shop;

public class Main {
    public static void main(String[] args) {
        PreLoadData pl = new PreLoadData();
        Shop shop = new Shop();
        
        if(pl.loadUserData() == false)
            System.out.println("Error at preloading user data");
        if(pl.loadItemData() == false)
            System.out.println("Error at preloading items data");
        // SetData sd = new SetData();
        // System.out.println(sd.itemData);
    }
}
