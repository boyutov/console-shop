public class Item {
    private static int nextId = 1;
    private int id;
    private String name;
    private double price;
    private int quantity;
    private Provider provider;

    public Item(String name, double price, int quantity, Provider provider) {
        this.id = nextId++;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.provider = provider;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public Provider getProvider() { return provider; }

    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}