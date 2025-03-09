import java.util.ArrayList;
import java.util.Scanner;

public class Salesman extends User {
    private ArrayList<Product> managedCatalogs = new ArrayList<>();
    private Shop shop;

    public Salesman(String login, String password, String name) {
        super(login, password, name);
    }

    @Override
    public String getRole() {
        return "Продавец";
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public void info() {
        System.out.println("Продавец: " + getName() + ", ID: " + getId());
    }

    public void add_product(String name, int quantity) {
        System.out.println("Продукт добавлен: " + name + ", количество: " + quantity);
    }

    public void infoShop() {
        if (shop == null) {
            System.out.println("У вас нет назначенного магазина.");
        } else {
            System.out.println("Информация о магазине:");
            System.out.println("Имя магазина: " + shop.getName());
            System.out.println("Адрес: " + shop.getAddress());
            System.out.println("Общее количество товаров: " + shop.getItemCount());
            System.out.println("Количество сотрудников: " + shop.getEmployeeCount());
            System.out.println("Средняя выручка: " + shop.getAverageRevenue());
        }
    }

    public void allItems(ArrayList<Product> products) {
        if (products.isEmpty()) {
            System.out.println("Товаров нет");
        } else {
            for (Product p : products) {
                System.out.println(" - " + p.getName() + ", цена: " + p.getPrice());
            }
        }
    }

    public void allShops(ArrayList<Shop> allShops) {
        if (allShops.isEmpty()) {
            System.out.println("Магазинов нет");
        } else {
            System.out.println("Все магазины:");
            for (Shop shop : allShops) {
                System.out.println(" - " + shop.getName() + ", адрес: " + shop.getAddress());
            }
        }
    }

    public void allOrders(ArrayList<Order> allOrders) {
        if (allOrders.isEmpty()) {
            System.out.println("Заказов нет");
        } else {
            System.out.println("Все заказы:");
            for (Order order : allOrders) {
                System.out.println("Заказ ID: " + order.getId() + ", покупатель: " + order.getCustomer().getName());
            }
        }
    }

    public void infoShop(Shop shop) {
        if (shop == null) {
            System.out.println("Магазин не найден");
        } else {
            System.out.println("Магазин: " + shop.getName() + ", адрес: " + shop.getAddress());
        }
    }

    public void allProviders(ArrayList<User> users) {
        boolean found = false;
        for (User user : users) {
            if (user instanceof Provider) {
                System.out.println("Поставщик: " + user.getName() + ", ID: " + user.getId());
                found = true;
            }
        }
        if (!found) System.out.println("Поставщиков нет");
    }

    public void searchProvider(ArrayList<User> users, String param, String value) {
        boolean found = false;
        for (User user : users) {
            if (user instanceof Provider &&
                    ((param.equals("login") && user.getLogin().equals(value)) ||
                            (param.equals("name") && user.getName().equals(value)) ||
                            (param.equals("id") && String.valueOf(user.getId()).equals(value)))) {
                System.out.println("Поставщик: " + user.getName() + ", ID: " + user.getId());
                found = true;
            }
        }
        if (!found) System.out.println("Поставщики не найдены");
    }

    public void allNewsPosts(ArrayList<Post> allPosts) {
        if (allPosts.isEmpty()) {
            System.out.println("Публикаций нет");
        } else {
            System.out.println("Все публикации:");
            for (Post post : allPosts) {
                System.out.println(" - " + post.getContent() + " (тег: " + post.getTag() + ")");
            }
        }
    }

    public void addPost(String content, String tag) {
        System.out.println("Продавец " + getName() + " добавил публикацию: " + content);
    }

    public void postsByLogin(ArrayList<Post> allPosts, String login) {
        boolean found = false;
        for (Post post : allPosts) {
            if (post.getAuthor().getLogin().equals(login)) {
                System.out.println(" - " + post.getContent() + " (тег: " + post.getTag() + ")");
                found = true;
            }
        }
        if (!found) System.out.println("Публикации не найдены");
    }

    public void postsById(ArrayList<Post> allPosts, int id) {
        for (Post post : allPosts) {
            if (post.getAuthor().getId() == id) {
                System.out.println(" - " + post.getContent() + " (тег: " + post.getTag() + ")");
                return;
            }
        }
        System.out.println("Публикация не найдена");
    }

    public void postsByTag(ArrayList<Post> allPosts, String tag) {
        boolean found = false;
        for (Post post : allPosts) {
            if (post.getTag().equals(tag)) {
                System.out.println(" - " + post.getContent() + " (тег: " + tag + ")");
                found = true;
            }
        }
        if (!found) System.out.println("Публикации с тегом " + tag + " не найдены");
    }

    public void updateItem(ArrayList<Item> allItems, String name, double newPrice, int newQuantity) {
        for (Item item : allItems) {
            if (item.getName().equals(name)) {
                item.update(newPrice, newQuantity);
                System.out.println("Товар " + name + " обновлен");
                return;
            }
        }
        System.out.println("Товар не найден");
    }

    public void makeRequest(String itemName, int quantity) {
        System.out.println("Продавец " + getName() + " запросил " + quantity + " единиц товара " + itemName);
    }
}