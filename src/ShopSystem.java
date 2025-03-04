import javax.lang.model.type.ArrayType;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class ShopSystem {
    private ArrayList<User> users = new ArrayList<>();
    private User currentUser = null;
    private ArrayList<Item> allItems = new ArrayList<>();
    private ArrayList<Shop> allShops = new ArrayList<>();
    private ArrayList<Post> allPosts = new ArrayList<>();
    private ArrayList<Order> allOrders = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    public static String role;

    public void processCommand(String command) {
        String[] parts = command.split("\\s+");
        String cmd = parts[0];

        switch (cmd) {
            case "help":
                printHelp();
                break;

            case "register":
                Scanner scanner = new Scanner(System.in);
                System.out.println("Введите роль: Покупатель, Продавец, Поставщик.");
                role = scanner.nextLine();

                registerUser(role);
                break;

            case "login":
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Введите роль: Покупатель, Продавец, Поставщик.");
                role = scanner1.nextLine();
                System.out.println("Введите пароль: ");
                String password = scanner1.nextLine();
                System.out.println("Введите имя: ");
                String username = scanner1.nextLine();
                loginUser(role, password, username);
                break;

            case "get_all_users":
                printAllUsers();
                break;

            case "info":
                if (currentUser == null) {
                    System.out.println("Войдите в систему");
                } else {
                    currentUser.info();
                }
                break;

            case "logout":
                currentUser = null;
                System.out.println("Вы вышли из системы");
                break;

            case "user_by_param":
                if (parts.length < 3) {
                    System.out.println("Укажите параметр: login, name, id и значение");
                    return;
                }
                findUserByParam(parts[1], parts[2]);
                break;

            // Команды поставщика
            case "add_item":
                checkProviderCommand(parts, () -> {
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    int quantity = Integer.parseInt(parts[3]);
                    ((Provider) currentUser).addItem(name, price, quantity);
                    allItems.add(new Item(name, price, quantity, (Provider) currentUser));
                }, "add_item <name> <price> <quantity>");
                break;
            case "add_shop":
                checkProviderCommand(parts, () -> {
                    String shopName = parts[1];
                    String address = parts[2];
                    ((Provider) currentUser).addShop(shopName, address);
                    allShops.add(new Shop(shopName, address));
                }, "add_shop <name> <address>");
                break;
            case "info_shops":
                checkProviderCommand(parts, () -> ((Provider) currentUser).infoShops(), null);
                break;
            case "info_items":
                checkProviderCommand(parts, () -> ((Provider) currentUser).infoItems(), null);
                break;
            case "update_item":
                checkProviderCommand(parts, () -> {
                    String name = parts[1];
                    double newPrice = Double.parseDouble(parts[2]);
                    int newQuantity = Integer.parseInt(parts[3]);
                    ((Provider) currentUser).updateItem(name, newPrice, newQuantity);
                }, "update_item <name> <newPrice> <newQuantity>");
                break;
            case "add_post":
                checkProviderCommand(parts, () -> {
                    String content = parts[1];
                    String tag = parts[2];
                    ((Provider) currentUser).addPost(content, tag);
                    allPosts.add(new Post(content, tag, currentUser));
                }, "add_post <content> <tag>");
                break;
            case "all_shop":
                checkProviderCommand(parts, () -> ((Provider) currentUser).allShop(), null);
                break;
            case "shop_by_id":
                if (parts.length < 2) {
                    System.out.println("Укажите ID магазина");
                    return;
                }
                int shopId = Integer.parseInt(parts[1]);
                Shop shop = findShopById(shopId);
                if (shop != null) {
                    System.out.println("Магазин: " + shop.getName() + ", адрес: " + shop.getAddress());
                } else {
                    System.out.println("Магазин не найден");
                }
                break;
            case "shops_by_parameter":
                if (parts.length < 3) {
                    System.out.println("Укажите параметр и значение");
                    return;
                }
                ArrayList<Shop> shops = findShopsByParam(parts[1], parts[2]);
                if (shops.isEmpty()) {
                    System.out.println("Магазины не найдены");
                } else {
                    for (Shop s : shops) {
                        System.out.println(s.getName() + ", адрес: " + s.getAddress());
                    }
                }
                break;

            // Команды продавца
            case "all_items":
                checkSalesmanCommand(parts, () -> ((Salesman) currentUser).allItems(products), null);
                break;

            case "all_shops":
                checkSalesmanCommand(parts, () -> ((Salesman) currentUser).allShops(allShops), null);
                break;

            case "add_product":
            checkSalesmanCommand(parts, () -> {products.add(new Product(((Salesman) currentUser).add_product());}, "add_product");
            break;

            case "all_orders":
                checkSalesmanCommand(parts, () -> ((Salesman) currentUser).allOrders(allOrders), null);
                break;
            case "info_shop":
                checkSalesmanCommand(parts, () -> {
                    int id = Integer.parseInt(parts[1]);
                    ((Salesman) currentUser).infoShop(findShopById(id));
                }, "info_shop <id>");
                break;
            case "all_provider":
                checkSalesmanCommand(parts, () -> ((Salesman) currentUser).allProviders(users), null);
                break;
            case "search_provider":
                checkSalesmanCommand(parts, () -> {
                    String param = parts[1];
                    String value = parts[2];
                    ((Salesman) currentUser).searchProvider(users, param, value);
                }, "search_provider <param> <value>");
                break;
            case "all_news_posts":
                checkSalesmanCommand(parts, () -> ((Salesman) currentUser).allNewsPosts(allPosts), null);
                break;
            case "posts_by_login":
                checkSalesmanCommand(parts, () -> {
                    String login = parts[1];
                    ((Salesman) currentUser).postsByLogin(allPosts, login);
                }, "posts_by_login <login>");
                break;
            case "posts_by_id":
                checkSalesmanCommand(parts, () -> {
                    int postId = Integer.parseInt(parts[1]);
                    ((Salesman) currentUser).postsById(allPosts, postId);
                }, "posts_by_id <id>");
                break;
            case "posts_by_tag":
                checkSalesmanCommand(parts, () -> {
                    String tag = parts[1];
                    ((Salesman) currentUser).postsByTag(allPosts, tag);
                }, "posts_by_tag <tag>");
                break;
            case "update_items":
                checkSalesmanCommand(parts, () -> {
                    String name = parts[1];
                    double newPrice = Double.parseDouble(parts[2]);
                    int newQuantity = Integer.parseInt(parts[3]);
                    ((Salesman) currentUser).updateItem(allItems, name, newPrice, newQuantity);
                }, "update_item <name> <newPrice> <newQuantity>");
                break;
            case "make_request":
                checkSalesmanCommand(parts, () -> {
                    String itemName = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    ((Salesman) currentUser).makeRequest(itemName, quantity);
                }, "make_request <itemName> <quantity>");
                break;

            // Команды покупателя
            case "all_salesman":
                checkCustomerCommand(parts, () -> ((Customer) currentUser).allSalesmen(users), null);
                break;
            case "catalogs":
                checkCustomerCommand(parts, () -> ((Customer) currentUser).viewCatalogs(products), null);
                break;
            case "set_item":
                checkCustomerCommand(parts, () -> {
                    String itemName = parts[1];
                    ((Customer) currentUser).setItem(allItems, itemName);
                }, "set_item <itemName>");
                break;
            case "cart":
                checkCustomerCommand(parts, () -> ((Customer) currentUser).viewCart(), null);
                break;
            case "check_cart":
                checkCustomerCommand(parts, () -> ((Customer) currentUser).checkout(allOrders), null);
                break;
            case "orders":
                checkCustomerCommand(parts, () -> ((Customer) currentUser).viewOrders(), null);
                break;

            default:
                System.out.println("Неизвестная команда.");
        }
    }

    private void printHelp() {
        try {
            switch (currentUser) {
                case Provider provider -> {
                    System.out.println("Команды поставщика: add_item, add_shop, info_shops, info_items, update_item, add_post, all_shop, shop_by_id, shops_by_parameter");
                    System.out.println("Общие команды: exit, help, register, login, get_all_users, info, logout, user_by_param");
                }
                case Salesman salesman -> {
                    System.out.println("Команды продавца: all_items, all_shops, add_product, all_orders, info_shop, all_provider, search_provider, all_news_posts, add_post, posts_by_login, posts_by_id, posts_by_tag, update_item, make_request");
                    System.out.println("Общие команды: exit, help, register, login, get_all_users, info, logout, user_by_param");
                }
                case Customer customer -> {
                    System.out.println("Команды покупателя: all_items, all_shops, all_salesman, catalogs, set_item, cart, check_cart, orders, all_news_posts");
                    System.out.println("Общие команды: exit, help, register, login, get_all_users, info, logout, user_by_param");
                }
                case null, default ->
                        System.out.println("Общие команды: exit, help, register, login, get_all_users, info, logout, user_by_param");
            }
        } catch (NullPointerException e) {
            System.out.println("Общие команды: exit, help, register, login, get_all_users, info, logout, user_by_param");
        }
    }

    private void checkProviderCommand(String[] parts, Runnable action, String usage) {
        if (currentUser == null || !(currentUser instanceof Provider)) {
            System.out.println("Команда для поставщиков");
            return;
        }
        if (usage != null && parts.length < usage.split("\\s+").length) {
            System.out.println("Использование: " + usage);
            return;
        }
        try {
            action.run();
        } catch (Exception e) {
            System.out.println("Ошибка ввода: " + e.getMessage());
        }
    }

    private void checkSalesmanCommand(String[] parts, Runnable action, String usage) {
        if (currentUser == null || !(currentUser instanceof Salesman)) {
            System.out.println("Команда для продавцов");
            return;
        }
        if (usage != null && parts.length < usage.split("\\s+").length) {
            System.out.println("Использование: " + usage);
            return;
        }
        try {
            action.run();
        } catch (Exception e) {
            System.out.println("Ошибка ввода: " + e.getMessage());
        }
    }

    private void checkCustomerCommand(String[] parts, Runnable action, String usage) {
        if (currentUser == null || !(currentUser instanceof Customer)) {
            System.out.println("Команда для покупателей");
            return;
        }
        if (usage != null && parts.length < usage.split("\\s+").length) {
            System.out.println("Использование: " + usage);
            return;
        }
        try {
            action.run();
        } catch (Exception e) {
            System.out.println("Ошибка ввода: " + e.getMessage());
        }
    }

    private void registerUser(String role) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Пароль: ");
        String password = scanner.nextLine();
        System.out.println("Имя: ");
        String name = scanner.nextLine();
        role = role.toLowerCase();

        User user;
        switch (role) {
            case "покупатель":
                user = new Customer(role, password, name);
                break;
            case "продавец":
                user = new Salesman(role, password, name);
                break;
            case "поставщик":
                user = new Provider(role, password, name);
                break;
            default:
                System.out.println("Неверная роль");
                return;
        }
        users.add(user);
        System.out.println("Пользователь зарегистрирован: " + role);
    }

    private void loginUser(String login, String password, String name) {
        for (User user : users) {
            if (user.getLogin().equals(login.toLowerCase()) && user.getPassword().equals(password) && user.getName().equals(name)) {
                currentUser = user;
                System.out.println("Вход выполнен: " + login);
                return;
            }
        }
        System.out.println("Неверный логин или пароль");
    }

    private void printAllUsers() {
        if (users.isEmpty()) {
            System.out.println("Пользователей нет");
        } else {
            for (User user : users) {
                System.out.println(user.getLogin() + " - " + user.getRole());
            }
        }
    }

    private void findUserByParam(String param, String value) {
        for (User user : users) {
            if ((param.equals("login") && user.getLogin().equals(value)) ||
                    (param.equals("name") && user.getName().equals(value)) ||
                    (param.equals("id") && String.valueOf(user.getId()).equals(value))) {
                System.out.println(user.getLogin() + " - " + user.getRole());
            }
        }
    }

    private Shop findShopById(int id) {
        for (Shop shop : allShops) {
            if (shop.getId() == id) {
                return shop;
            }
        }
        return null;
    }

    private ArrayList<Shop> findShopsByParam(String param, String value) {
        ArrayList<Shop> result = new ArrayList<>();
        for (Shop shop : allShops) {
            if ((param.equals("name") && shop.getName().equals(value)) ||
                    (param.equals("address") && shop.getAddress().equals(value))) {
                result.add(shop);
            }
        }
        return result;
    }
}