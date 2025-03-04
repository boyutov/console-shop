import java.util.ArrayList;

public class Customer extends User {
    private ArrayList<Item> cart = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();

    public Customer(String login, String password, String name) {
        super(login, password, name);
    }

    @Override
    public String getRole() {
        return "Покупатель";
    }

    @Override
    public void info() {
        System.out.println("Покупатель: " + getName() + ", ID: " + getId());
    }

    public void allItems(ArrayList<Item> allItems) {
        if (allItems.isEmpty()) {
            System.out.println("Товаров нет");
        } else {
            System.out.println("Все товары:");
            for (Item item : allItems) {
                System.out.println(" - " + item.getName() + ", цена: " + item.getPrice());
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

    public void allSalesmen(ArrayList<User> users) {
        boolean found = false;
        for (User user : users) {
            if (user instanceof Salesman) {
                System.out.println("Продавец: " + user.getName() + ", ID: " + user.getId());
                found = true;
            }
        }
        if (!found) System.out.println("Продавцов нет");
    }

    public void viewCatalogs(ArrayList<Product> products) {
        if (products.isEmpty()) {
            System.out.println("Каталогов нет");
        } else {
            System.out.println("Каталоги:");
            for (Product product : products) {
                System.out.println(" - " + product.getName());
            }
        }
    }

    public void setItem(ArrayList<Item> allItems, String itemName) {
        for (Item item : allItems) {
            if (item.getName().equals(itemName)) {
                cart.add(item);
                System.out.println("Товар " + itemName + " добавлен в корзину");
                return;
            }
        }
        System.out.println("Товар не найден");
    }

    public void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Корзина пуста");
        } else {
            System.out.println("Товары в корзине:");
            for (Item item : cart) {
                System.out.println(" - " + item.getName() + ", цена: " + item.getPrice());
            }
        }
    }

    public void checkout(ArrayList<Order> allOrders) {
        if (cart.isEmpty()) {
            System.out.println("Корзина пуста");
            return;
        }
        Order order = new Order(this, cart);
        orders.add(order);
        allOrders.add(order);
        System.out.println("Заказ оформлен, ID: " + order.getId());
        cart.clear();
    }

    public void viewOrders() {
        if (orders.isEmpty()) {
            System.out.println("Заказов нет");
        } else {
            System.out.println("Ваши заказы:");
            for (Order order : orders) {
                System.out.println("Заказ ID: " + order.getId());
            }
        }
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
}