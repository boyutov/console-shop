import java.util.ArrayList;

public class Provider extends User {
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Shop> shops = new ArrayList<>();
    private ArrayList<Post> posts = new ArrayList<>();

    public Provider(String login, String password, String name) {
        super(login, password, name);
    }

    @Override
    public String getRole() {
        return "Поставщик";
    }

    @Override
    public void info() {
        System.out.println("Поставщик: " + getName() + ", ID: " + getId());
    }

    public void addItem(String name, double price, int quantity) {
        Item item = new Item(name, price, quantity, this);
        items.add(item);
        System.out.println("Товар добавлен: " + name);
    }

    public void addShop(String name, String address) {
        Shop shop = new Shop(name, address);
        shops.add(shop);
        System.out.println("Магазин добавлен: " + name);
    }

    public void infoShops() {
        if (shops.isEmpty()) {
            System.out.println("Магазины отсутствуют");
        } else {
            System.out.println("Магазины поставщика " + getName() + ":");
            for (Shop shop : shops) {
                System.out.println(" - " + shop.getName() + ", адрес: " + shop.getAddress());
            }
        }
    }

    public void infoItems() {
        if (items.isEmpty()) {
            System.out.println("Товары отсутствуют");
        } else {
            System.out.println("Товары поставщика " + getName() + ":");
            for (Item item : items) {
                System.out.println(" - " + item.getName() + ", цена: " + item.getPrice() + ", количество: " + item.getQuantity());
            }
        }
    }

    public void updateItem(String name, double newPrice, int newQuantity) {
        for (Item item : items) {
            if (item.getName().equals(name)) {
                item.update(newPrice, newQuantity);
                System.out.println("Товар обновлён: " + name);
                return;
            }
        }
        System.out.println("Товар не найден");
    }

    public void addPost(String content, String tag) {
        Post post = new Post(content, tag, this);
        posts.add(post);
        System.out.println("Публикация добавлена: " + content);
    }

    public void allShop() {
        infoShops();
    }
}