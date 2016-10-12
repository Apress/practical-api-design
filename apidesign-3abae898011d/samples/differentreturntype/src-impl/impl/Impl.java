package impl;
import api.API;
import javax.swing.Icon;

public class Impl extends API {
    public static void main(String[] args) {
        Icon icon = API.getIcon();
        System.err.println("OK: " + icon);
    }
}
