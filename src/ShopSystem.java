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
    private ArrayList<String> catalogs = new ArrayList<>();
    public static String role;

    public void processCommand(String command) {
        Scanner scanner = new Scanner(System.in);
        String cmd = command.trim(); // Убираем пробелы, аргументы не нужны

        switch (cmd) {
            case "help":
                printHelp();
                break;

            case "register":
                System.out.println("Введите роль: Покупатель, Продавец, Поставщик");
                role = scanner.nextLine().toLowerCase();
                registerUser(role);
                break;

            case "login":
                System.out.println("Введите роль: Покупатель, Продавец, Поставщик");
                role = scanner.nextLine().toLowerCase();
                System.out.println("Введите пароль: ");
                String password = scanner.nextLine();
                System.out.println("Введите имя: ");
                String name = scanner.nextLine();
                loginUser(role, password, name);
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
                System.out.println("Введите параметр (login, name, id): ");
                String param = scanner.nextLine();
                System.out.println("Введите значение: ");
                String value = scanner.nextLine();
                findUserByParam(param, value);
                break;

            // Команды поставщика
            case "add_item":
                if (!checkProvider()) return;
                System.out.println("Введите название товара: ");
                String itemName = scanner.nextLine();
                System.out.println("Введите цену: ");
                double price = Double.parseDouble(scanner.nextLine());
                System.out.println("Введите количество: ");
                int quantity = Integer.parseInt(scanner.nextLine());
                ((Provider) currentUser).addItem(itemName, price, quantity);
                allItems.add(new Item(itemName, price, quantity, (Provider) currentUser));
                break;

            case "add_shop":
                if (!checkProvider()) return;
                System.out.println("Введите название магазина: ");
                String shopName = scanner.nextLine();
                System.out.println("Введите адрес: ");
                String address = scanner.nextLine();
                ((Provider) currentUser).addShop(shopName, address);
                allShops.add(new Shop(shopName, address));
                break;

            case "info_shops":
                if (!checkProvider()) return;
                ((Provider) currentUser).infoShops();
                break;

            case "info_items":
                if (!checkProvider()) return;
                ((Provider) currentUser).infoItems();
                break;

            case "update_item":
                if (!checkProvider()) return;
                System.out.println("Введите название товара: ");
                String updateName = scanner.nextLine();
                System.out.println("Введите новую цену: ");
                double newPrice = Double.parseDouble(scanner.nextLine());
                System.out.println("Введите новое количество: ");
                int newQuantity = Integer.parseInt(scanner.nextLine());
                ((Provider) currentUser).updateItem(updateName, newPrice, newQuantity);
                break;

            case "add_post":
                if (!checkProvider()) return;
                System.out.println("Введите содержание поста: ");
                String content = scanner.nextLine();
                System.out.println("Введите тег: ");
                String tag = scanner.nextLine();
                ((Provider) currentUser).addPost(content, tag);
                allPosts.add(new Post(content, tag, currentUser));
                break;

            case "all_shop":
                if (!checkProvider()) return;
                ((Provider) currentUser).allShop();
                break;

            case "shop_by_id":
                if (!checkProvider()) return;
                System.out.println("Введите ID магазина: ");
                int shopId = Integer.parseInt(scanner.nextLine());
                Shop shop = findShopById(shopId);
                if (shop != null) {
                    System.out.println("Магазин: " + shop.getName() + ", адрес: " + shop.getAddress());
                } else {
                    System.out.println("Магазин не найден");
                }
                break;

            case "shops_by_parameter":
                if (!checkProvider()) return;
                System.out.println("Введите параметр (name, address): ");
                String shopParam = scanner.nextLine();
                System.out.println("Введите значение: ");
                String shopValue = scanner.nextLine();
                ArrayList<Shop> shops = findShopsByParam(shopParam, shopValue);
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
                if (!checkSalesman()) return;
                ((Salesman) currentUser).allItems(products);
                break;

            case "add_catalogs":
                if (!checkSalesman()) return;
                System.out.println("Введите название нового каталога: ");
                String catalogName = scanner.nextLine();
                if (catalogs.contains(catalogName)) {
                    System.out.println("Каталог с таким названием уже существует.");
                } else {
                    catalogs.add(catalogName);
                    System.out.println("Каталог \"" + catalogName + "\" добавлен.");
                }
                break;

            case "all_shops":
                if (!checkSalesman()) return;
                ((Salesman) currentUser).allShops(allShops);
                break;

            case "add_product":
                if (!checkSalesman()) return;
                System.out.println("Введите название продукта: ");
                String productName = scanner.nextLine();
                System.out.println("Введите цену: ");
                double productPrice = Double.parseDouble(scanner.nextLine());
                System.out.println("Введите количество: ");
                int productQuantity = Integer.parseInt(scanner.nextLine());
                products.add(new Product(productName, productPrice, productQuantity));
                ((Salesman) currentUser).add_product(productName, productQuantity);
                break;

            case "all_orders":
                if (!checkSalesman()) return;
                ((Salesman) currentUser).allOrders(allOrders);
                break;

            case "info_shop":
                if (!checkSalesman()) return; // Проверяем, что пользователь — продавец
                ((Salesman) currentUser).infoShop();
                break;

            case "all_provider":
                if (!checkSalesman()) return;
                ((Salesman) currentUser).allProviders(users);
                break;

            case "search_provider":
                if (!checkSalesman()) return;
                System.out.println("Введите параметр (login, name, id): ");
                String providerParam = scanner.nextLine();
                System.out.println("Введите значение: ");
                String providerValue = scanner.nextLine();
                ((Salesman) currentUser).searchProvider(users, providerParam, providerValue);
                break;

            case "all_news_posts":
                if (!checkSalesman()) return;
                ((Salesman) currentUser).allNewsPosts(allPosts);
                break;

            case "posts_by_login":
                if (!checkSalesman()) return;
                System.out.println("Введите логин: ");
                String login = scanner.nextLine();
                ((Salesman) currentUser).postsByLogin(allPosts, login);
                break;

            case "posts_by_id":
                if (!checkSalesman()) return;
                System.out.println("Введите ID поста: ");
                int postId = Integer.parseInt(scanner.nextLine());
                ((Salesman) currentUser).postsById(allPosts, postId);
                break;

            case "posts_by_tag":
                if (!checkSalesman()) return;
                System.out.println("Введите тег: ");
                String postTag = scanner.nextLine();
                ((Salesman) currentUser).postsByTag(allPosts, postTag);
                break;

            case "update_items":
                if (!checkSalesman()) return;
                System.out.println("Введите название товара: ");
                String itemNameUpdate = scanner.nextLine();
                System.out.println("Введите новую цену: ");
                double newItemPrice = Double.parseDouble(scanner.nextLine());
                System.out.println("Введите новое количество: ");
                int newItemQuantity = Integer.parseInt(scanner.nextLine());
                ((Salesman) currentUser).updateItem(allItems, itemNameUpdate, newItemPrice, newItemQuantity);
                break;

            case "make_request":
                if (!checkSalesman()) return;
                System.out.println("Введите название товара: ");
                String requestItem = scanner.nextLine();
                System.out.println("Введите количество: ");
                int requestQuantity = Integer.parseInt(scanner.nextLine());
                ((Salesman) currentUser).makeRequest(requestItem, requestQuantity);
                break;

            // Команды покупателя
            case "all_salesman":
                if (!checkCustomer()) return;
                ((Customer) currentUser).allSalesmen(users);
                break;

            case "catalogs":
                if (!checkCustomer()) return;
                ((Customer) currentUser).viewCatalogs(products);
                break;

            case "set_item":
                if (!checkCustomer()) return;
                System.out.println("Введите название товара: ");
                String setItemName = scanner.nextLine();
                ((Customer) currentUser).setItem(allItems, setItemName);
                break;

            case "cart":
                if (!checkCustomer()) return;
                ((Customer) currentUser).viewCart();
                break;

            case "check_cart":
                if (!checkCustomer()) return;
                ((Customer) currentUser).checkout(allOrders);
                break;

            case "orders":
                if (!checkCustomer()) return;
                ((Customer) currentUser).viewOrders();
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
                    System.out.println("Команды продавца: all_items, all_shops, add_product, all_orders, info_shop, all_provider, search_provider, all_news_posts, posts_by_login, posts_by_id, posts_by_tag, update_items, make_request, add_catalogs");
                    System.out.println("Общие команды: exit, help, register, login, get_all_users, info, logout, user_by_param");
                }
                case Customer customer -> {
                    System.out.println("Команды покупателя: all_items, all_shops, all_salesman, catalogs, set_item, cart, check_cart, orders");
                    System.out.println("Общие команды: exit, help, register, login, get_all_users, info, logout, user_by_param");
                }
                case null, default ->
                        System.out.println("Общие команды: exit, help, register, login, get_all_users, info, logout, user_by_param");
            }
        } catch (NullPointerException e) {
            System.out.println("Общие команды: exit, help, register, login, get_all_users, info, logout, user_by_param");
        }
    }

    private boolean checkProvider() {
        if (currentUser == null || !(currentUser instanceof Provider)) {
            System.out.println("Эта команда только для поставщиков. Войдите как поставщик.");
            return false;
        }
        return true;
    }

    private boolean checkSalesman() {
        if (currentUser == null || !(currentUser instanceof Salesman)) {
            System.out.println("Эта команда только для продавцов. Войдите как продавец.");
            return false;
        }
        return true;
    }

    private boolean checkCustomer() {
        if (currentUser == null || !(currentUser instanceof Customer)) {
            System.out.println("Эта команда только для покупателей. Войдите как покупатель.");
            return false;
        }
        return true;
    }

    private void registerUser(String role) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите пароль: ");
        String password = scanner.nextLine();
        System.out.println("Введите имя: ");
        String name = scanner.nextLine();

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
                System.out.println("Неверная роль. Используйте: Покупатель, Продавец, Поставщик");
                return;
        }
        users.add(user);
        System.out.println("Пользователь зарегистрирован: " + role);
    }

    private void loginUser(String login, String password, String name) {
        for (User user : users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password) && user.getName().equals(name)) {
                currentUser = user;
                System.out.println("Вход выполнен: " + login);
                return;
            }
        }
        System.out.println("Неверные данные для входа");
    }

    private void printAllUsers() {
        if (users.isEmpty()) {
            System.out.println("Пользователей нет");
        } else {
            System.out.println("Список всех пользователей:");
            System.out.printf("%-5s %-15s %-15s%n", "ID", "Имя", "Роль");
            for (User user : users) {
                System.out.printf("%-5d %-15s %-15s%n",
                        user.getId(), user.getName(), user.getRole());
            }
        }
    }

    private void findUserByParam(String param, String value) {
        boolean found = false;
        for (User user : users) {
            if ((param.equals("login") && user.getLogin().equals(value)) ||
                    (param.equals("name") && user.getName().equals(value)) ||
                    (param.equals("id") && String.valueOf(user.getId()).equals(value))) {
                System.out.println(user.getLogin() + " - " + user.getRole());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Пользователь не найден");
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