import java.io.IOException;

import static menu.Menu.MainMenu;

public class Main {
    public static void main(String[] args) {
        try {
            MainMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}