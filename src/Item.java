public class Item {
    private String name;
    private double price;
    private int quantity;
    private Provider provider;

    public Item(String name, double price, int quantity, Provider provider) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.provider = provider;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public Provider getProvider() { return provider; }

    public void update(double newPrice, int newQuantity) {
        this.price = newPrice;
        this.quantity = newQuantity;
    }
}