import menu.Menu;

import static menu.Menu.MainMenu;

public class MenuDemo {
    public static void main(String[] args) {

        try {
            Menu.MainMenu();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
