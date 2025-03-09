import java.util.ArrayList;
import java.util.Scanner;

public class ShopSystem {
    private ArrayList<User> users = new ArrayList<>();
    private User currentUser = null;
    private ArrayList<Item> allItems = new ArrayList<>();
    private ArrayList<Shop> allShops = new ArrayList<>();
    private ArrayList<Post> allPosts = new ArrayList<>();
    private ArrayList<Order> allOrders = new ArrayList<>();
    private ArrayList<Catalog> catalogs = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void run() {
        System.out.println("Добро пожаловать в онлайн-магазин!");
        while (true) {
            System.out.print("Введите команду (help для списка): ");
            String input = scanner.nextLine().trim();
            if (input.equals("exit")) {
                System.out.println("До свидания!");
                break;
            }
            processCommand(input);
        }
    }

    private void processCommand(String input) {
        String[] parts = input.split("\\s+", 2);
        String command = parts[0];
        String args = parts.length > 1 ? parts[1] : "";

        switch (command) {
            case "help":
                printHelp();
                break;

            case "register":
                registerUser();
                break;

            case "login":
                loginUser();
                break;

            case "logout":
                logoutUser();
                break;

            case "info":
                showUserInfo();
                break;

            case "get_all_users":
                getAllUsers();
                break;

            case "user_by_param":
                userByParam(args);
                break;

            // Команды поставщика
            case "add_item":
                addItem();
                break;

            case "add_shop":
                addShop();
                break;

            case "info_shops":
                infoShops();
                break;

            case "info_items":
                infoItems();
                break;

            case "update_item":
                updateItem();
                break;

            case "add_post":
                addPost();
                break;

            case "all_shop":
                allShop();
                break;

            case "shop_by_id":
                shopById(args);
                break;

            case "shops_by_parameter":
                shopsByParameter(args);
                break;

            // Команды продавца
            case "all_items":
                allItems();
                break;

            case "all_shops":
                allShops();
                break;

            case "add_catalogs":
                addCatalogs();
                break;

            case "all_orders":
                allOrders();
                break;

            case "info_shop":
                infoShop(args);
                break;

            case "all_provider":
                allProvider();
                break;

            case "search_provider":
                searchProvider(args);
                break;

            case "all_news_posts":
                allNewsPosts();
                break;

            case "posts_by_login":
                postsByLogin(args);
                break;

            case "posts_by_id":
                postsById(args);
                break;

            case "posts_by_tag":
                postsByTag(args);
                break;

            case "update_items":
                updateItems();
                break;

            case "make_request":
                makeRequest();
                break;

            case "add_product":
                if (!checkSalesman()) return;
                System.out.println("Введите название продукта: ");
                String productName = scanner.nextLine();
                System.out.println("Введите цену: ");
                double productPrice = Double.parseDouble(scanner.nextLine());
                System.out.println("Введите количество: ");
                int productQuantity = Integer.parseInt(scanner.nextLine());
                Item newItem = new Item(productName, productPrice, productQuantity, null); // provider = null для продавца
                allItems.add(newItem);
                System.out.println("Продукт добавлен: " + productName);
                break;

            case "add_new_post":
                if (!(currentUser instanceof Salesman || currentUser instanceof Provider)) {
                    System.out.println("Эта команда доступна только для продавцов и поставщиков.");
                    return;
                }
                System.out.println("Введите содержание поста: ");
                String content = scanner.nextLine();
                System.out.println("Введите тег: ");
                String tag = scanner.nextLine();
                Post newPost = new Post(content, tag, currentUser);
                allPosts.add(newPost);
                System.out.println("Публикация добавлена.");
                break;

            // Команды покупателя
            case "all_salesman":
                allSalesman();
                break;

            case "catalogs":
                viewCatalogs();
                break;

            case "set_item":
                setItem(args);
                break;

            case "cart":
                viewCart();
                break;

            case "check_cart":
                checkCart();
                break;

            case "orders":
                viewOrders();
                break;

            default:
                System.out.println("Неизвестная команда. Введите 'help' для списка.");
        }
    }

    private void printHelp() {
        System.out.println("Общие команды:");
        System.out.println("  exit - Завершение программы");
        System.out.println("  help - Список команд");
        System.out.println("  register - Регистрация пользователя");
        System.out.println("  login - Вход в систему");
        System.out.println("  logout - Выход из системы");
        System.out.println("  info - Информация о текущем пользователе");
        System.out.println("  get_all_users - Список всех пользователей");
        System.out.println("  user_by_param [role, name, id] - Поиск пользователя по параметрам");
        if (currentUser != null) {
            if (currentUser instanceof Provider) {
                System.out.println("Команды поставщика:");
                System.out.println("  add_item - Добавить товар");
                System.out.println("  add_shop - Добавить магазин");
                System.out.println("  info_shops - Информация о магазинах");
                System.out.println("  info_items - Информация о товарах");
                System.out.println("  update_item - Обновить товар");
                System.out.println("  add_post - Добавить публикацию");
                System.out.println("  all_shop - Все магазины");
                System.out.println("  shop_by_id [id] - Магазин по ID");
                System.out.println("  shops_by_parameter [param] [value] - Магазины по параметру");
            } else if (currentUser instanceof Salesman) {
                System.out.println("Команды продавца:");
                System.out.println("  all_items - Все товары");
                System.out.println("  all_shops - Все магазины");
                System.out.println("  add_catalogs - Добавить каталог");
                System.out.println("  all_orders - Все заказы");
                System.out.println("  info_shop [id] - Информация о магазине");
                System.out.println("  all_provider - Все поставщики");
                System.out.println("  search_provider [param] [value] - Поиск поставщика");
                System.out.println("  all_news_posts - Все новости");
                System.out.println("  posts_by_login [login] - Публикации по логину");
                System.out.println("  posts_by_id [id] - Публикация по ID");
                System.out.println("  posts_by_tag [tag] - Публикации по тегу");
                System.out.println("  update_items - Обновить товары");
                System.out.println("  make_request - Сделать запрос");
                System.out.println("  add_product - Добавить товар");
                System.out.println("  add_new_post - Добавить пост");
            } else if (currentUser instanceof Customer) {
                System.out.println("Команды покупателя:");
                System.out.println("  all_items - Все товары");
                System.out.println("  all_shops - Все магазины");
                System.out.println("  all_salesman - Все продавцы");
                System.out.println("  catalogs - Каталоги");
                System.out.println("  set_item [id] - Добавить товар в корзину");
                System.out.println("  cart - Просмотр корзины");
                System.out.println("  check_cart - Оформить заказ");
                System.out.println("  orders - Просмотр заказов");
            }
        }
    }

    private void registerUser() {
        System.out.println("Выберите роль: 1 - Покупатель, 2 - Продавец, 3 - Поставщик");
        String roleChoice = scanner.nextLine().trim();
        String role;
        switch (roleChoice) {
            case "1": role = "Покупатель"; break;
            case "2": role = "Продавец"; break;
            case "3": role = "Поставщик"; break;
            default:
                System.out.println("Ошибка: неверная роль.");
                return;
        }

        System.out.print("Введите логин: ");
        String login = scanner.nextLine().trim();
        if (isLoginTaken(login)) {
            System.out.println("Ошибка: логин уже занят.");
            return;
        }

        System.out.print("Введите пароль: ");
        String password = scanner.nextLine().trim();
        System.out.print("Введите имя: ");
        String name = scanner.nextLine().trim();

        User user;
        switch (role) {
            case "Покупатель":
                user = new Customer(login, password, name);
                break;
            case "Продавец":
                user = new Salesman(login, password, name);
                break;
            case "Поставщик":
                user = new Provider(login, password, name);
                break;
            default:
                return;
        }
        users.add(user);
        System.out.println("Пользователь " + name + " (" + role + ") успешно зарегистрирован.");
    }

    private boolean isLoginTaken(String login) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    private void loginUser() {
        if (currentUser != null) {
            System.out.println("Вы уже авторизованы как " + currentUser.getName());
            return;
        }
        System.out.print("Введите логин: ");
        String login = scanner.nextLine().trim();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine().trim();

        for (User user : users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                currentUser = user;
                System.out.println("Вход выполнен: " + user.getName() + " (" + user.getRole() + ")");
                return;
            }
        }
        System.out.println("Ошибка: неверный логин или пароль.");
    }

    private void logoutUser() {
        if (currentUser == null) {
            System.out.println("Вы не авторизованы.");
        } else {
            System.out.println("Выход выполнен: " + currentUser.getName());
            currentUser = null;
        }
    }

    private void showUserInfo() {
        if (currentUser == null) {
            System.out.println("Вы не авторизованы.");
        } else {
            currentUser.info();
        }
    }

    private void getAllUsers() {
        if (users.isEmpty()) {
            System.out.println("В системе нет пользователей.");
        } else {
            System.out.println("Список пользователей:");
            for (User user : users) {
                System.out.println("ID: " + user.getId() + ", Имя: " + user.getName() + ", Роль: " + user.getRole());
            }
        }
    }

    private void userByParam(String args) {
        if (args.isEmpty()) {
            System.out.println("Укажите параметр и значение, например: role Покупатель");
            return;
        }
        String[] paramParts = args.split("\\s+", 2);
        if (paramParts.length < 2) {
            System.out.println("Укажите параметр и значение.");
            return;
        }
        String param = paramParts[0];
        String value = paramParts[1];
        for (User user : users) {
            if ((param.equals("role") && user.getRole().equalsIgnoreCase(value)) ||
                    (param.equals("name") && user.getName().equalsIgnoreCase(value)) ||
                    (param.equals("id") && String.valueOf(user.getId()).equals(value))) {
                System.out.println("ID: " + user.getId() + ", Логин: " + user.getLogin() + ", Имя: " + user.getName() + ", Роль: " + user.getRole());
            }
        }
    }

    private void addItem() {
        if (!(currentUser instanceof Provider)) {
            System.out.println("Эта команда доступна только для поставщиков.");
            return;
        }
        System.out.print("Введите название товара: ");
        String name = scanner.nextLine().trim();
        System.out.print("Введите цену: ");
        double price = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Введите количество: ");
        int quantity = Integer.parseInt(scanner.nextLine().trim());
        Item item = new Item(name, price, quantity, (Provider) currentUser);
        allItems.add(item);
        System.out.println("Товар добавлен: " + name);
    }

    private void addShop() {
        if (!(currentUser instanceof Provider)) {
            System.out.println("Эта команда доступна только для поставщиков.");
            return;
        }
        System.out.print("Введите название магазина: ");
        String name = scanner.nextLine().trim();
        System.out.print("Введите адрес: ");
        String address = scanner.nextLine().trim();
        Shop shop = new Shop(name, address);
        allShops.add(shop);
        System.out.println("Магазин добавлен: " + name);
    }

    private void infoShops() {
        if (!(currentUser instanceof Provider)) {
            System.out.println("Эта команда доступна только для поставщиков.");
            return;
        }
        System.out.println("Магазины поставщика:");
        for (Shop shop : allShops) {
            System.out.println(" - " + shop.getName() + ", адрес: " + shop.getAddress());
        }
    }

    private void infoItems() {
        if (!(currentUser instanceof Provider)) {
            System.out.println("Эта команда доступна только для поставщиков.");
            return;
        }
        System.out.println("Товары поставщика:");
        for (Item item : allItems) {
            if (item.getProvider().equals(currentUser)) {
                System.out.println(" - " + item.getName() + ", цена: " + item.getPrice() + ", количество: " + item.getQuantity());
            }
        }
    }

    private void updateItem() {
        if (!(currentUser instanceof Provider)) {
            System.out.println("Эта команда доступна только для поставщиков.");
            return;
        }
        System.out.print("Введите ID товара: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        for (Item item : allItems) {
            if (item.getId() == id && item.getProvider().equals(currentUser)) {
                System.out.print("Введите новую цену: ");
                double newPrice = Double.parseDouble(scanner.nextLine().trim());
                System.out.print("Введите новое количество: ");
                int newQuantity = Integer.parseInt(scanner.nextLine().trim());
                item.setPrice(newPrice);
                item.setQuantity(newQuantity);
                System.out.println("Товар обновлён.");
                return;
            }
        }
        System.out.println("Товар не найден.");
    }

    private void addPost() {
        if (!(currentUser instanceof Provider)) {
            System.out.println("Эта команда доступна только для поставщиков.");
            return;
        }
        System.out.print("Введите содержание публикации: ");
        String content = scanner.nextLine().trim();
        System.out.print("Введите тег: ");
        String tag = scanner.nextLine().trim();
        Post post = new Post(content, tag, currentUser);
        allPosts.add(post);
        System.out.println("Публикация добавлена.");
    }

    private void allShop() {
        if (!(currentUser instanceof Provider)) {
            System.out.println("Эта команда доступна только для поставщиков.");
            return;
        }
        System.out.println("Все магазины:");
        for (Shop shop : allShops) {
            System.out.println(" - " + shop.getName() + ", адрес: " + shop.getAddress());
        }
    }

    private void shopById(String args) {
        if (!(currentUser instanceof Provider)) {
            System.out.println("Эта команда доступна только для поставщиков.");
            return;
        }
        try {
            int id = Integer.parseInt(args.trim());
            for (Shop shop : allShops) {
                if (shop.getId() == id) {
                    System.out.println("Магазин: " + shop.getName() + ", адрес: " + shop.getAddress());
                    return;
                }
            }
            System.out.println("Магазин не найден.");
        } catch (NumberFormatException e) {
            System.out.println("Неверный ID.");
        }
    }

    private void shopsByParameter(String args) {
        if (!(currentUser instanceof Provider)) {
            System.out.println("Эта команда доступна только для поставщиков.");
            return;
        }
        String[] paramParts = args.split("\\s+", 2);
        if (paramParts.length < 2) {
            System.out.println("Укажите параметр и значение.");
            return;
        }
        String param = paramParts[0];
        String value = paramParts[1];
        for (Shop shop : allShops) {
            if ((param.equals("name") && shop.getName().equalsIgnoreCase(value)) ||
                    (param.equals("address") && shop.getAddress().equalsIgnoreCase(value))) {
                System.out.println(" - " + shop.getName() + ", адрес: " + shop.getAddress());
            }
        }
    }

    private void allItems() {
        if (!(currentUser instanceof Salesman)) {
            System.out.println("Эта команда доступна только для продавцов.");
            return;
        }
        System.out.println("Все товары:");
        for (Item item : allItems) {
            System.out.println(" - ID: " + item.getId() + ", Название: " + item.getName() + ", Цена: " + item.getPrice());
        }
    }

    private void allShops() {
        if (!(currentUser instanceof Salesman)) {
            System.out.println("Эта команда доступна только для продавцов.");
            return;
        }
        System.out.println("Все магазины:");
        for (Shop shop : allShops) {
            System.out.println(" - ID: " + shop.getId() + ", Название: " + shop.getName() + ", Адрес: " + shop.getAddress());
        }
    }

    private void addCatalogs() {
        if (!(currentUser instanceof Salesman)) {
            System.out.println("Эта команда доступна только для продавцов.");
            return;
        }
        System.out.print("Введите название каталога: ");
        String name = scanner.nextLine().trim();
        catalogs.add(new Catalog(name));
        System.out.println("Каталог добавлен: " + name);
    }

    private void allOrders() {
        if (!(currentUser instanceof Salesman)) {
            System.out.println("Эта команда доступна только для продавцов.");
            return;
        }
        System.out.println("Все заказы:");
        for (Order order : allOrders) {
            System.out.println(" - ID: " + order.getId() + ", Покупатель: " + order.getCustomer().getName());
        }
    }

    private void infoShop(String args) {
        if (!(currentUser instanceof Salesman)) {
            System.out.println("Эта команда доступна только для продавцов.");
            return;
        }
        try {
            int id = Integer.parseInt(args.trim());
            for (Shop shop : allShops) {
                if (shop.getId() == id) {
                    System.out.println("Информация о магазине: " + shop.getName() + ", адрес: " + shop.getAddress());
                    return;
                }
            }
            System.out.println("Магазин не найден.");
        } catch (NumberFormatException e) {
            System.out.println("Неверный ID.");
        }
    }

    private void allProvider() {
        if (!(currentUser instanceof Salesman)) {
            System.out.println("Эта команда доступна только для продавцов.");
            return;
        }
        System.out.println("Все поставщики:");
        for (User user : users) {
            if (user instanceof Provider) {
                System.out.println(" - ID: " + user.getId() + ", Имя: " + user.getName());
            }
        }
    }

    private void searchProvider(String args) {
        if (!(currentUser instanceof Salesman)) {
            System.out.println("Эта команда доступна только для продавцов.");
            return;
        }
        String[] paramParts = args.split("\\s+", 2);
        if (paramParts.length < 2) {
            System.out.println("Укажите параметр и значение.");
            return;
        }
        String param = paramParts[0];
        String value = paramParts[1];
        for (User user : users) {
            if (user instanceof Provider) {
                if ((param.equals("id") && String.valueOf(user.getId()).equals(value)) ||
                        (param.equals("name") && user.getName().equalsIgnoreCase(value)) ||
                        (param.equals("login") && user.getLogin().equalsIgnoreCase(value))) {
                    System.out.println(" - ID: " + user.getId() + ", Имя: " + user.getName());
                }
            }
        }
    }

    private void allNewsPosts() {
        if (!(currentUser instanceof Salesman)) {
            System.out.println("Эта команда доступна только для продавцов.");
            return;
        }
        System.out.println("Все новости:");
        for (Post post : allPosts) {
            System.out.println(" - " + post.getContent() + " (тег: " + post.getTag() + ")");
        }
    }

    private void postsByLogin(String args) {
        if (!(currentUser instanceof Salesman)) {
            System.out.println("Эта команда доступна только для продавцов.");
            return;
        }
        String login = args.trim();
        for (Post post : allPosts) {
            if (post.getAuthor().getLogin().equalsIgnoreCase(login)) {
                System.out.println(" - " + post.getContent() + " (тег: " + post.getTag() + ")");
            }
        }
    }

    private void postsById(String args) {
        if (!(currentUser instanceof Salesman)) {
            System.out.println("Эта команда доступна только для продавцов.");
            return;
        }
        try {
            int id = Integer.parseInt(args.trim());
            for (Post post : allPosts) {
                if (post.getAuthor().getId() == id) {
                    System.out.println(" - " + post.getContent() + " (тег: " + post.getTag() + ")");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный ID.");
        }
    }

    private void postsByTag(String args) {
        if (!(currentUser instanceof Salesman)) {
            System.out.println("Эта команда доступна только для продавцов.");
            return;
        }
        String tag = args.trim();
        for (Post post : allPosts) {
            if (post.getTag().equalsIgnoreCase(tag)) {
                System.out.println(" - " + post.getContent() + " (тег: " + tag + ")");
            }
        }
    }

    private void updateItems() {
        if (!(currentUser instanceof Salesman)) {
            System.out.println("Эта команда доступна только для продавцов.");
            return;
        }
        System.out.print("Введите ID товара: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        for (Item item : allItems) {
            if (item.getId() == id) {
                System.out.print("Введите новую цену: ");
                double newPrice = Double.parseDouble(scanner.nextLine().trim());
                System.out.print("Введите новое количество: ");
                int newQuantity = Integer.parseInt(scanner.nextLine().trim());
                item.setPrice(newPrice);
                item.setQuantity(newQuantity);
                System.out.println("Товар обновлён.");
                return;
            }
        }
        System.out.println("Товар не найден.");
    }

    private void makeRequest() {
        if (!(currentUser instanceof Salesman)) {
            System.out.println("Эта команда доступна только для продавцов.");
            return;
        }
        System.out.print("Введите ID товара: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Введите количество: ");
        int quantity = Integer.parseInt(scanner.nextLine().trim());
        System.out.println("Запрос на товар ID " + id + " в количестве " + quantity + " отправлен.");
    }

    private void allSalesman() {
        if (!(currentUser instanceof Customer)) {
            System.out.println("Эта команда доступна только для покупателей.");
            return;
        }
        System.out.println("Все продавцы:");
        for (User user : users) {
            if (user instanceof Salesman) {
                System.out.println(" - ID: " + user.getId() + ", Имя: " + user.getName());
            }
        }
    }

    private void viewCatalogs() {
        if (!(currentUser instanceof Customer)) {
            System.out.println("Эта команда доступна только для покупателей.");
            return;
        }
        System.out.println("Каталоги:");
        for (Catalog catalog : catalogs) {
            System.out.println(" - " + catalog.getName());
        }
    }

    private void setItem(String args) {
        if (!(currentUser instanceof Customer)) {
            System.out.println("Эта команда доступна только для покупателей.");
            return;
        }
        try {
            int id = Integer.parseInt(args.trim());
            for (Item item : allItems) {
                if (item.getId() == id) {
                    ((Customer) currentUser).addToCart(item);
                    System.out.println("Товар " + item.getName() + " добавлен в корзину.");
                    return;
                }
            }
            System.out.println("Товар не найден.");
        } catch (NumberFormatException e) {
            System.out.println("Неверный ID.");
        }
    }

    private void viewCart() {
        if (!(currentUser instanceof Customer)) {
            System.out.println("Эта команда доступна только для покупателей.");
            return;
        }
        ((Customer) currentUser).viewCart();
    }

    private void checkCart() {
        if (!(currentUser instanceof Customer)) {
            System.out.println("Эта команда доступна только для покупателей.");
            return;
        }
        Order order = ((Customer) currentUser).checkout();
        if (order != null) {
            allOrders.add(order);
            System.out.println("Заказ оформлен, ID: " + order.getId());
        }
    }

    private void viewOrders() {
        if (!(currentUser instanceof Customer)) {
            System.out.println("Эта команда доступна только для покупателей.");
            return;
        }
        ((Customer) currentUser).viewOrders();
    }

    private boolean checkSalesman() {
        if (currentUser == null || !(currentUser instanceof Salesman)) {
            System.out.println("Эта команда доступна только для продавцов.");
            return false;
        }
        return true;
    }
}