package cinema;

public enum CinemaMenu {
    SHOW_SEATS ("1","Show the seats") ,
    BUY_TICKETS("2","Buy a ticket") ,
    STATISTICS ("3","Statistics"),
    EXIT       ("0","Exit");

    private final String input;
    private final String text;

    CinemaMenu(String input, String text) {
        this.input = input;
        this.text = text;
    }
    public static void printMenu() {
        System.out.println();
        CinemaMenu[] menuItems = CinemaMenu.values();
        for (CinemaMenu menuItem : menuItems) {
            System.out.println(menuItem.getInput() + ". " + menuItem.getText());
        }
    }
    public static CinemaMenu getMenuItemFromInput(String input) {
        CinemaMenu[] menuItems = CinemaMenu.values();
        for (CinemaMenu menuItem : menuItems) {
            if (menuItem.getInput().equals(input)) {
                return menuItem;
            }
        }
        throw new RuntimeException("Couldn't find the option for input: " + input);
    }

    public String getInput() {
        return input;
    }

    public String getText() {
        return text;
    }
}

