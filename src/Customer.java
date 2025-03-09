import java.util.ArrayList;

public class Customer extends User {
    private ArrayList<Item> cart = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();

    public Customer(String login, String password, String name) {
        super(login, password, name, "Покупатель");
    }

    @Override
    public void info() {
        System.out.println("Покупатель: ID=" + getId() + ", Имя=" + getName());
    }

    public void addToCart(Item item) {
        cart.add(item);
    }

    public void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Корзина пуста.");
        } else {
            System.out.println("Товары в корзине:");
            for (Item item : cart) {
                System.out.println(" - " + item.getName() + ", цена: " + item.getPrice());
            }
        }
    }

    public Order checkout() {
        if (cart.isEmpty()) {
            System.out.println("Корзина пуста.");
            return null;
        }
        Order order = new Order(this, cart);
        orders.add(order);
        cart.clear();
        return order;
    }

    public void viewOrders() {
        if (orders.isEmpty()) {
            System.out.println("Нет заказов.");
        } else {
            System.out.println("Ваши заказы:");
            for (Order order : orders) {
                System.out.println(" - ID: " + order.getId());
            }
        }
    }
}